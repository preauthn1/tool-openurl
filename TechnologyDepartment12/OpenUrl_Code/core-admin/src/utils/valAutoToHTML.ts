import assemblingHTML from "./assemblingHTML";


const coreAutoValToHtml = (data) => {
    let html = assemblingHTML.title

    // 组装公式
    let printVal = data['printVal']
    for (let i = 1; i < 7; i++) {
        if(printVal[i].length == 0) continue
        // 标题头
        let typeNameVal = assemblingHTML.typeNameVal.replace('{title}', titleMap[i])
        html = html + typeNameVal

        // 遍历子公式
        for (let item of printVal[i]) {
            // 公式图片
            if (item.viewUrl) {
                let viewUrm = assemblingHTML.imageVal.replace('{imgUrl}', item.viewUrl)
                html = html + viewUrm
            }

            // 公式
            let formulaLabelVal = assemblingHTML.formulaLabelVal.replace('{formula}', item.centent)
            html = html + formulaLabelVal

            // 子符号表格头
            let tableTitleVal = assemblingHTML.tableTitleVal
            let colVal = '<th style="background: #EBEEF5">' + item.name + '</th>'
            // 表格参数
            let tableDataVal = assemblingHTML.tableDataVal
            let tVal = '<th>' + item['value'] + '</th>'
            // 符号说明
            let symbolVal = assemblingHTML.symbolVal
            let sVal = ""
            for (let child of item.childList) {
                // 表格头
                colVal = colVal + '<th style="background: ' + switchBgColor(child) +'">' + child['name'] + '</th>'
                // 表格参数
                tVal = tVal + '<th>' + child['value'] + '</th>'
                // 符号说明
                sVal = sVal + '<li><span class="symbol">' + child['name'] + '</span> — ' + child['remark'] + '；</li>'
            }
            tableTitleVal = tableTitleVal.replace('{tableTitleCol}', colVal)
            html = html + tableTitleVal

            // 表格参数
            let tableValCol = tableDataVal.replace('{tableColVal}', tVal)
            html = html + tableValCol

            // 带参计算
            let valStatic = assemblingHTML.staticVal.replace('{valStatic}', item.valStatic)
            html = html + valStatic

            // 符号说明
            let strSym = symbolVal.replace('{strSym}', sVal)
            html = html + strSym

        }

        
    }

    // 组装计算结果
    let resultVal = assemblingHTML.resultVal
    resultVal = resultVal.replace('{value1}', data['ruleForm']['value1']).replace('{value2}', data['ruleForm']['value2']).replace('{value3}', data['ruleForm']['value3']).replace('{value4}', data['ruleForm']['value4']).replace('{value5}', data['ruleForm']['value5']).replace('{value6}', data['ruleForm']['value6'])
    html = html + resultVal

    html = html + assemblingHTML.end

    return html
}


// 点击按钮计算
const calculateMax = (ruleForm) => {
    const entries = Object.entries(ruleForm);
    let max = -Infinity;
    entries.forEach(([k, v]) => {
        if (k != 'value6') {
            const num = v as number; // 断言 v 是 number
            if (Number(num) > Number(max)) {
                max = num;
            }
        }
    });
    return Number(max)

};


// 表格头上色
const switchBgColor = (data) => {
    let type = 2
    if (data.type == 1) {
        type = 2
    }else if (data.type == 4) {
        type = 3
    }else {
        type = data.isStaticVal == 1 ? 2 : 1
    }


    let color = "#EBEEF5"
    switch (type) {
        case 1:
            color = "#67C23A"
            break;
        case 2:
            color = "#409EFF"
            break;
        case 3:
            color = "#E6A23C"
            break;
        default:
            break;
    }

    return color;
}


// 公式类型标题
const titleMap = {
    1: '隧道内工作的最多人数需风量',
    2: '爆破排烟需风量',
    3: '洞内作业内燃机机械需风量',
    4: '洞内最小风速所需风量',
    5: '稀释瓦斯绝对涌出量至允许浓度',
    6: '隧道需风量的确定'
}


// 海拔高度的大气压强
const heightTable =[{
    '海拔高度（m）': '大气压强',
    0: 101,
    500: 95,
    1000: 88,
    1500: 84,  
    2000: 78, 
    2500: 73.2,
    3000: 68.8, 
    3500: 64.6, 
    4000: 60.8, 
    4500: 57.0, 
    5000: 53.6 
}]


// 表格数据参数
const tableStatic = `
            <table>
                <thead>
                    <tr>
                        <th>海拔高度（m）</th>
                        <th>0</th>
                        <th>500</th>
                        <th>1000</th>
                        <th>1500</th>
                        <th>2000</th>
                        <th>2500</th>
                        <th>3000</th>
                        <th>3500</th>
                        <th>4000</th>
                        <th>4500</th>
                        <th>5000</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>大气压强（kpa）</th>
                        <th>101</th>
                        <th>95</th>
                        <th>88</th>
                        <th>84</th>
                        <th>78</th>
                        <th>73.2</th>
                        <th>68.8</th>
                        <th>64.6</th>
                        <th>60.8</th>
                        <th>57</th>
                        <th>53.6</th>
                    </tr>
                </tbody>
            </table>`



export default {
    coreAutoValToHtml,
    calculateMax,
    heightTable,
    tableStatic,
}