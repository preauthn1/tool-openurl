package com.sz.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STVerticalAlignRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Word 模板工具类
 * 支持：
 *  - 批量替换占位符（{{key}} 格式）
 *  - 同时替换段落和表格内容
 *  - 自动保存到新文件
 */
@Slf4j
@Component
public class DocxTemplateUtil {

    private final static List<String> textKeys = List.of(new String[]{
            "[Q1]", "[Q需]", "[Q供]", "[Q修]", "[h摩]", "[h局]", "[h总]", "[Q修s]", "[mend]", "[count]",
            "[Q1人]", "[Q2-1-t]", "[Q2-1-G]", "[Q2-1-A]", "[Q2-1-Φ]", "[Q2-1-b]", "[L风管]", "[LK]",
            "[Q4-A]", "[hU]", "[hS]", "[hα]", "[hgValue]", "[p100]", "[QCH4]", "[h高]", "[L风管]", "[nᵢ]"
    });

    /**
     * 替换 docx 中所有文本（普通文本 + 公式 MathML）
     */
    public void replaceAll(String srcPath, String destPath, Map<String, String> params) throws Exception {
        log.info("【进入替换模版】");
        // 加载模板
        InputStream fis = null;
        File file = new File(srcPath);
        if (file.exists()) {
            log.info("1");
            fis = new FileInputStream(file);
        } else {
            log.info("2");
            // fallback classpath
            fis = DocxTemplateUtil.class.getClassLoader()
                    .getResourceAsStream("templates/" + srcPath);
        }
        XWPFDocument doc = null;
        if (fis != null) {
            doc = new XWPFDocument(fis);
        }


        // 替换段落中的文本
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            replaceInParagraph(paragraph, params);
        }

        // 替换表格中的文本
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceInParagraph(paragraph, params);
                    }
                }
            }
        }

        // 替换段落/表格文本占位符
        replaceAllPlaceholders(doc, params);

        // 替换上标
        fixSuperscriptPlaceholders(doc);
        // 替换下标
        fixSubscriptTexts(doc);


        // 添加表格数据
        JSONArray array = JSONUtil.parseArray(params.get("[table]"));
        List<List<String>> list = new ArrayList<>();
        for (Object row : array) {
            JSONArray rowArr = (JSONArray) row;
            List<String> inner = new ArrayList<>();
            for (Object item : rowArr) {
                inner.add(item.toString());
            }
            list.add(inner);
        }
        // ======== 2. 创建表格（11 行 + 1 表头，共 12 行）========
        int cols = 8;
        XWPFTable table = doc.createTable(); // 默认创建1行1列

        for (int i = 0; i < list.size(); i++) {
            XWPFTableRow tableRow;
            if (i == 0) {
                tableRow = table.getRow(0); // 第1行
            } else {
                tableRow = table.createRow(); // 新增行
            }

            List<String> row = list.get(i);
            // 确保单元格数量足够
            while (tableRow.getTableCells().size() < cols) {
                tableRow.createCell();
            }

            for (int j = 0; j < cols; j++) {
                XWPFTableCell cell = tableRow.getCell(j);
                cell.removeParagraph(0);
                // 创建段落
                XWPFParagraph paragraph = cell.addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER); // 水平居中
                XWPFRun run = paragraph.createRun();
                run.setText(row.get(j));
                // 红色字体
                run.setColor("FF0000"); // HEX: 红色
                // 字体大小
                run.setFontSize(10); // 默认见字体大小，也可修改

                // 垂直居中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
        }


        FileOutputStream fos = new FileOutputStream(destPath);
        doc.write(fos);

        fos.close();
        fis.close();
        doc.close();
    }

    /**
     * 替换段落（普通 run + MathML）
     */
    private static void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> params) {
        for (XWPFRun run : paragraph.getRuns()) {

            // ===== 普通文本 =====
            String text = run.getText(0);
            if (text != null) {
                String replaced = replaceParams(text, params);
                if (!text.equals(replaced)) {
                    run.setText(replaced, 0);
                }
            }

            // ===== MathML/公式文本处理 =====
            XmlCursor cursor = run.getCTR().newCursor();
            cursor.selectPath(".//*");

            while (cursor.hasNextSelection()) {
                cursor.toNextSelection();
                try {
                    String localName = cursor.getName() == null ? null : cursor.getName().getLocalPart();
                    if (localName == null)
                        continue;

                    // 公式中文本节点：m:t
                    if ("t".equals(localName)) {
                        XmlObject obj = cursor.getObject();
                        if (obj instanceof CTText ctText) {
                            String val = ctText.getStringValue();
                            String newVal = replaceParams(val, params);
                            if (!val.equals(newVal)) {
                                ctText.setStringValue(newVal);
                            }
                        }
                    }
                } catch (Exception ignore) {}
            }

            cursor.dispose();
        }
    }

    /**
     * 替换占位符 {{key}} 或 [key] 或 key
     */
    private static String replaceParams(String text, Map<String, String> params) {
        if (text == null) return null;

        String result = text;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            result = result
                    .replace("{{" + key + "}}", value)
                    .replace("[" + key + "]", value)
                    .replace(key, value);  // 兜底，普通文本也能 replace
        }

        return result;
    }

    // ============================================
    //        文本段落/表格占位符替换
    // ============================================
    private static void replaceInRuns(List<XWPFRun> runs, Map<String, String> params) {
        if (runs == null || runs.isEmpty()) return;

        // 拼接全文
        StringBuilder full = new StringBuilder();
        for (XWPFRun run : runs) {
            StringBuilder sb = new StringBuilder();
            for (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText t : run.getCTR().getTList()) {
                if (t.getStringValue() != null) sb.append(t.getStringValue());
            }
            String t0 = sb.length() > 0 ? sb.toString() : run.getText(0);
            if (t0 != null) full.append(t0);
        }

        String paragraphText = full.toString();
        if (paragraphText.isEmpty()) return;

        // ===================================================
        // ① 必须匹配 textKeys 中的 key 才执行替换（你需要的）
        // ===================================================
        boolean hit = false;
        for (String key : textKeys) {
            if (paragraphText.contains(key)) {
                hit = true;
                break;
            }
        }

        // 不包含 textKeys → 不改变 run、不改变样式
        if (!hit) return;

        // ===================================================
        // ② 进行替换，只替换 textKeys 内部的 key
        // ===================================================
        String replaced = paragraphText;

        for (String key : textKeys) {
            if (params.containsKey(key)) {
                replaced = replaced.replace(key, params.get(key));
            }
        }

        // ===================================================
        // ③ 用第一个 run 的样式输出新文本，其余 run 清空
        // ===================================================
        XWPFParagraph paragraph = runs.get(0).getParagraph();
        XWPFRun styleRun = runs.get(0);

        // 清空所有 run（保留样式）
        for (XWPFRun r : runs) {
            r.setText("", 0);
            for (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText t : r.getCTR().getTList()) {
                t.setStringValue("");
            }
        }

        // 用第一个 run 写入替换后的完整文本（样式保留）
        XWPFRun newRun = runs.get(0);
        try {
            newRun.getCTR().setRPr(styleRun.getCTR().getRPr());
        } catch (Exception ignore) { }

        newRun.setText(replaced, 0);
    }


    public static void replaceAllPlaceholders(XWPFDocument doc, Map<String, String> params) {
        // 替换段落
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            replaceInRuns(paragraph.getRuns(), params);  // run 合并替换
            replaceMathText(paragraph, params);          // MathML 替换
        }

        // 替换表格
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph para : cell.getParagraphs()) {
                        replaceInRuns(para.getRuns(), params);
                    }
                }
            }
        }
    }

    private static void replaceMathText(XWPFParagraph paragraph, Map<String, String> params) {
        for (XWPFRun run : paragraph.getRuns()) {
            XmlCursor cursor = run.getCTR().newCursor();
            cursor.selectPath(".//*");
            while (cursor.hasNextSelection()) {
                cursor.toNextSelection();
                XmlObject obj = cursor.getObject();
                if (obj instanceof CTText ctText) {
                    String val = ctText.getStringValue();
                    String newVal = replaceParams(val, params);
                    if (!val.equals(newVal)) {
                        ctText.setStringValue(newVal);
                    }
                }
            }
            cursor.dispose();
        }
    }
    public static void fixSubscriptTexts(XWPFDocument doc) {
        // 需要修复的内容映射：文档中的标记 → 修复后的正常字符 & 下标
        Map<String, String[]> subscriptMap = new HashMap<>();
        subscriptMap.put("[Q_1]", new String[]{"Q", "1"});
        subscriptMap.put("[p_100]", new String[]{"P", "100"});
        subscriptMap.put("[L_风管]", new String[]{"L", "风管"});
        subscriptMap.put("[h_供]", new String[]{"h", "供"});
        subscriptMap.put("[h_总]", new String[]{"h", "总"});

        // 处理所有段落
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            processParagraph(paragraph, subscriptMap);
        }

        // 处理所有表格内的段落
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        processParagraph(p, subscriptMap);
                    }
                }
            }
        }
    }

    private static void processParagraph(XWPFParagraph paragraph, Map<String, String[]> subscriptMap) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) return;

        // 1️⃣ 拼接所有 run 内容
        StringBuilder sb = new StringBuilder();
        for (XWPFRun r : runs) {
            String txt = r.getText(0);
            if (txt != null) sb.append(txt);
        }
        String combined = sb.toString();
        if (combined.isEmpty()) return;

        // 检查是否包含待替换内容
        boolean hit = false;
        for (String key : subscriptMap.keySet()) {
            if (combined.contains(key)) {
                hit = true;
                break;
            }
        }
        if (!hit) return;

        // 2️⃣ 使用标记分割被替换内容
        String marker = "\u0001";
        for (String key : subscriptMap.keySet()) {
            if (combined.contains(key)) {
                String[] pair = subscriptMap.get(key);
                combined = combined.replace(key, pair[0] + marker + pair[1] + marker);
            }
        }

        // 保存第一个 run 样式
        CTRPr firstRpr = null;
        if (!runs.isEmpty()) {
            try {
                firstRpr = runs.get(0).getCTR().getRPr();
            } catch (Exception ignored) {}
        }

        // 3️⃣ 删除所有旧 runs（一定要从后到前）
        for (int i = runs.size() - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        // 4️⃣ 按 marker 切割，重新创建 run
        String[] parts = combined.split(marker, -1);
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) continue;
            XWPFRun newRun = paragraph.createRun();

            // 恢复原 run 样式
            if (firstRpr != null) {
                try {
                    newRun.getCTR().setRPr((CTRPr) firstRpr.copy());
                } catch (Exception ignored) {}
            }

            newRun.setText(parts[i]);
            if (i % 2 == 1) { // 奇数 index → 下标部分
                newRun.setSubscript(VerticalAlign.SUBSCRIPT);
            }
        }
    }

    /**
     * 修复占位符上标（例如 [m-2] / [m-3] 或 m^2 / m2 的情况）
     * 使用：在替换占位符（params）之后、写入文件之前调用：
     *    fixSuperscriptPlaceholders(doc);
     */
    public static void fixSuperscriptPlaceholders(XWPFDocument doc) {
        // 定义占位符映射：占位符 -> {normalText, superscriptChar}
        Map<String, String[]> placeholderMap = new LinkedHashMap<>();
        placeholderMap.put("[m-2]", new String[]{"m", "2"});
        placeholderMap.put("[m-3]", new String[]{"m", "3"});
        // 如果还有别的占位符，继续 put

        // 处理所有段落
        for (XWPFParagraph p : doc.getParagraphs()) {
            processParagraphForSuperscript(p, placeholderMap);
        }

    }

    private static void processParagraphForSuperscript(XWPFParagraph paragraph, Map<String, String[]> placeholderMap) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) return;

        // 1) 拼接全文（保留原顺序），并记录每个 run 在 combined 中的 start index（用于定位）
        StringBuilder combinedSb = new StringBuilder();
        List<Integer> runStart = new ArrayList<>(runs.size()); // runStart[i] = combined index where runs.get(i) starts
        for (XWPFRun r : runs) {
            runStart.add(combinedSb.length());
            String t = r.text();
            if (t != null) combinedSb.append(t);
        }
        String combined = combinedSb.toString();
        if (combined.isEmpty()) return;

        boolean anyChange = false;

        // 2) 首先用 marker 替换所有占位符（确保不会与文档文本冲突）
        //    marker 用来区分普通文本与上标文本：normal + MARK + supers + MARK
        final String MARK = "\u0001";
        String working = combined;

        for (Map.Entry<String, String[]> e : placeholderMap.entrySet()) {
            String ph = e.getKey();
            String normal = e.getValue()[0];
            String sup = e.getValue()[1];
            if (working.contains(ph)) {
                working = working.replace(ph, normal + MARK + sup + MARK);
                anyChange = true;
            }
        }

        // 3) 还要处理两种已被替换成 m^2 或 m2 的常见情况
        //    a) 带 ^ 的形式： m^2 或 m^3
        //    b) 直接 m2 或 m3（但要谨慎，以避免把 "m2" 的合法文本误判 —— 我们只处理发生在占位符附近或明确的 pattern）
        // 处理 m^2 / m^3：把它替换为 m + MARK + 2 + MARK
        Pattern caretPattern = Pattern.compile("([mM])\\^(2|3)");
        Matcher mCaret = caretPattern.matcher(working);
        StringBuffer sbCaret = new StringBuffer();
        while (mCaret.find()) {
            String normal = mCaret.group(1);
            String sup = mCaret.group(2);
            mCaret.appendReplacement(sbCaret, normal + MARK + sup + MARK);
            anyChange = true;
        }
        mCaret.appendTail(sbCaret);
        working = sbCaret.toString();

        // 处理直接 m2/m3：我们只在以下场景转换以降低误判风险：
        // - 当 m2/m3 与数字或空格、单位符号组合出现时
        // - 或者原始 combined 包含占位符（说明用户目标就是这些位置）
        if (anyChange || combined.contains("[m-2]") || combined.contains("[m-3]")) {
            // 安全替换 m2/m3：使用 regex，要求前面为字母 m 或 M
            Pattern plainPattern = Pattern.compile("([mM])(2|3)");
            Matcher mPlain = plainPattern.matcher(working);
            StringBuffer sbPlain = new StringBuffer();
            while (mPlain.find()) {
                String normal = mPlain.group(1);
                String sup = mPlain.group(2);
                mPlain.appendReplacement(sbPlain, normal + MARK + sup + MARK);
                anyChange = true;
            }
            mPlain.appendTail(sbPlain);
            working = sbPlain.toString();
        }

        if (!anyChange) return; // 没有需要替换的内容

        // 4) 删除原 runs（从后往前）
        for (int i = runs.size() - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        // 5) 用 marker 切分并重建 run；奇数段为上标（superscript）
        //    为了基本保持样式，我们复制 original first run 的样式（如果存在）
        CTRPr firstRpr = null;
        try {
            firstRpr = runs.get(0).getCTR().getRPr();
        } catch (Exception ignored) {}

        String[] parts = working.split(MARK, -1); // 保留空串
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part == null || part.isEmpty()) continue;
            XWPFRun newRun = paragraph.createRun();
            // 复制样式
            if (firstRpr != null) {
                try {
                    newRun.getCTR().setRPr((CTRPr) firstRpr.copy());
                } catch (Exception ignored) {}
            }
            newRun.setText(part, 0);
            // 奇数段当作上标
            if (i % 2 == 1) {
                newRun.setSubscript(VerticalAlign.SUPERSCRIPT);
            }
        }
    }






    public static void main(String[] args) throws Exception {
        // 模板路径
        String templatePath = "template.docx";
        // 输出路径
        String outputPath = "report_final.docx";

        // 占位符映射
        Map<String, String> data = new HashMap<>();
        data.put("[Q1]", "1.2×50×3=675");
        data.put("[P]", "(1-0.02×(1000÷100))⁻¹=1.2500");
        data.put("[L]", "12.5×((280×40×0.4)÷(107.5×1.2500²))=333.3953");
        data.put("[Q2-1]", "2.5÷30×3√((280×0.8×40×107.5²×333.3953²)÷1.2500²)=1621.4120");
        data.put("[Q2-2]", "7.8÷30×3√(280×(107.5×333.3953)²)=1849.0046");
        data.put("[Q3]", "570×4.5=2565");
        data.put("[Q4]", "60×0.5×107.5=3225.0000");
        data.put("[Q5]", "(60×0.07717×2)÷0.005=1852.0800");
        data.put("[Q需]", "3225.0000");
        data.put("[Q供]", "1.5×1.25×(100/82.9)×3225=7294.18");
        data.put("[Q修]", "100×4725÷84=5625");
        data.put("[h摩]", "(0.0013×1000×5.65×(3225.0000÷60)²)÷2.54³=1294.9339");
        data.put("[h局]", "0.612×0.359×((3225.0000÷60)²÷2.54²)=98.3865");
        data.put("[h总]", "1294.9339＋98.3865=1393.3204");
        data.put("[Q修s]", "5652÷60=93.75");

        // 调用工具类方法
        new DocxTemplateUtil().replaceAll(templatePath, outputPath, data);
    }



}
