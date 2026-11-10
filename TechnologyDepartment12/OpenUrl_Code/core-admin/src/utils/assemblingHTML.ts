const title = `<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>通风计算公式文档</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background-color: #f5f7f9;
            color: #333;
            line-height: 1.6;
            padding: 20px;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
            padding: 30px;
        }
        header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eaeaea;
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 10px;
            font-size: 32px;
        }
        h2 {
            color: #3498db;
            margin: 25px 0 15px;
            padding-bottom: 10px;
            border-bottom: 2px solid #eaeaea;
        }
        h3 {
            color: #2c3e50;
            margin: 20px 0 15px;
        }
        .formula-container {
            background: #f8f9fa;
            border-left: 4px solid #3498db;
            padding: 20px;
            margin: 20px 0;
            border-radius: 0 8px 8px 0;
            overflow-x: auto;
        }
        .formula {
            font-size: 22px;
            font-family: 'Cambria Math', serif;
            color: #2c3e50;
            text-align: center;
            padding: 10px;
        }
        .formula sup, .formula sub {
            font-size: 0.8em;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            box-shadow: 0 2px 3px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #ddd !important;
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #eaeaea;
        }
        th {
            /* background-color: #3498db;
            color: white; */
            font-weight: 600;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #f1f8ff;
        }
        .symbols-list {
            list-style-type: none;
            margin: 20px 0;
        }
        .symbols-list li {
            padding: 8px 0;
            border-bottom: 1px dashed #eaeaea;
            margin-left: 20px;
        }
        .symbols-list li:last-child {
            border-bottom: none;
        }
        .symbol {
            font-weight: 600;
            color: #e74c3c;
        }
        .color-code {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin: 20px 0;
        }
        .color-item {
            display: flex;
            align-items: center;
            margin-right: 20px;
        }
        .color-box {
            width: 20px;
            height: 20px;
            margin-right: 10px;
            border-radius: 3px;
        }
        .current-result {
            background-color: #EBEEF5;
        }
        .readable-data {
            background-color: #67C23A;
        }
        .fixed-data {
            background-color: #409EFF;
        }
        .formula-result {
            background-color: #E6A23C;
        }
        .reference {
            background: #e8f5e9;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .wind-results {
            background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin: 20px 0;
        }
        .wind-results h2 {
            color: #1565c0;
            text-align: center;
            margin-bottom: 25px;
        }
        .wind-item {
            display: flex;
            justify-content: space-between;
            padding: 15px;
            margin: 12px 0;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            transition: transform 0.2s;
        }
        .wind-item:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .wind-name {
            font-weight: 600;
            color: #333;
            flex: 2;
        }
        .wind-value {
            font-weight: 700;
            color: #1976d2;
            flex: 1;
            text-align: right;
        }
        .pressure-item {
            display: flex;
            flex-direction: column;
            background: linear-gradient(135deg, #ffebee 0%, #ffcdd2 100%);
            border-left: 5px solid #d32f2f;
        }
        .pressure-item .wind-value {
            color: #d32f2f;
        }
        footer {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eaeaea;
            color: #7f8c8d;
            font-size: 14px;
        }
        @media (max-width: 768px) {
            .container {
                padding: 15px;
            }
            .formula {
                font-size: 18px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>通风计算公式文档</h1>
            <p>用于计算通风量的专业公式及其参数说明</p>
        </header>`


// 计算结果
const resultVal = `<!-- 新添加的风量结果部分 -->
        <section class="wind-results">
            <h2>隧道需风量与风压计算结果</h2>
            
            <div class="wind-item">
                <div class="wind-name">隧道内工作的最多人数需风量：</div>
                <div class="wind-value">{value1} m³/min</div>
            </div>
            
            <div class="wind-item">
                <div class="wind-name">爆破排烟需风量：</div>
                <div class="wind-value">{value2} m³/min</div>
            </div>
            
            <div class="wind-item">
                <div class="wind-name">洞内作业内燃机机械需风量：</div>
                <div class="wind-value">{value3} m³/min</div>
            </div>
            
            <div class="wind-item">
                <div class="wind-name">洞内最小风速所需风量：</div>
                <div class="wind-value">{value4} m³/min</div>
            </div>
            
            <div class="wind-item">
                <div class="wind-name">稀释瓦斯绝对涌出量至允许浓度需风量：</div>
                <div class="wind-value">{value5} m³/min</div>
            </div>
            
            <div class="wind-item">
                <div class="wind-name">风压计算：</div>
                <div class="wind-value">{value6} Pa</div>
            </div>

            <div class="wind-item pressure-item">
                {endTableHtml}
                {endValHtml}
            </div>
        </section>`


// 公式名称
const typeNameVal = `<section>
            <h2>{title}</h2>
            <div class="formula-container">`

// 公式图片
const imageVal = `<div class="formula">
                    <img src="{imgUrl}" />
                </div>`


// 替换公式
const formulaLabelVal = `
            <div class="formula">
                {formula}
            </div>
            `    


// 表格头
const tableTitleVal = `<table>
                <thead>
                    <tr>
                        {tableTitleCol}
                    </tr>
                </thead>`


// 表格参数
const tableDataVal = `<tbody>
                    <tr>
                        {tableColVal}
                    </tr>
                </tbody>
            </table>
        </section>`


// 带参计算
const staticVal = `<section class="reference">
            <h2>参考计算：</h2>
            <p>{valStatic}</p>
            
            <h3>色彩说明：</h3>
            <div class="color-code">
                <div class="color-item">
                    <div class="color-box current-result"></div>
                    <span>当前结果</span>
                </div>
                <div class="color-item">
                    <div class="color-box readable-data"></div>
                    <span>可读数据</span>
                </div>
                <div class="color-item">
                    <div class="color-box fixed-data"></div>
                    <span>固定数据</span>
                </div>
                <div class="color-item">
                    <div class="color-box formula-result"></div>
                    <span>公式结果</span>
                </div>
            </div>
        </section>`


// 符号说明
const symbolVal = `<section>
            <h2>符号说明：</h2>
            <ul class="symbols-list">
                {strSym}
            </ul>
        </section>
        <br></br><br></br><br></br>`


// 文档结尾
const end = `<footer>
            <p>© 2023 通风计算文档 | 专业工程计算公式</p>
        </footer>
    </div>
</body>
</html>`





export default {title, resultVal, typeNameVal, imageVal, formulaLabelVal, tableTitleVal, tableDataVal, staticVal, symbolVal, end}

















