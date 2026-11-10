import http from '@/api';
import { ADMIN_MODULE } from '@/api/helper/prefix';

const apiList = {
    // 获取字符名称列表
    getSymbol: '/acCalculateSymbol/getList',
    // 新增字符名称
    addSymbol: '/acCalculateSymbol/insert',
    // 修改字符名称
    editSymbol: '/acCalculateSymbol/update',
    // 删除字符名称
    delSymbol: '/acCalculateSymbol/remove',
    // 获取公式列表
    getFormula: '/acCalculateFormula/getList',
    // 新增公式
    addFormula: '/acCalculateFormula/insert',
    // 编辑公式
    editFormula: '/acCalculateFormula/update',
    // 删除公式
    delFormula: '/acCalculateFormula/remove',
    // 获取计算符列表
    getMathCode: '/acCalculateMathCode/getList',
    // 新增计算符
    addMathCode: '/acCalculateMathCode/insert',
    // 更新计算符
    editMathCode: '/acCalculateMathCode/update',
    // 删除计算符
    delMathCode: '/acCalculateMathCode/remove',
    // 获取计算符
    getMathCodeList: '/acCalculateMathCode/getAllList',
    // 获取字符名称
    getSymbolList: '/acCalculateSymbol/getAllList',
    // 获取公式
    getFormulaList: '/acCalculateFormula/getAllList',
    // 获取计算符分组
    getTypeGroup: '/acCalculateMathCode/getTypeGroup',
    // 校验变量名的合法性
    validLabel: '/acCalculateSymbol/validLabel',
    // 获取显示的公式
    getViewFormula: '/acCalculateFormula/getViewFormula',
    // 运算公式结果
    coreFunction: '/acCalculateFormula/coreFunction',
    // 生成计算报告
    getPrintWord: '/acCalculateFormula/printWord',
    // 计算结果列表
    getPrintList: '/acResultOutput/getList',
    // 获取详细输出
    getOutputInfo: '/acResultOutput/getInfo',
    // 设备列表
    getFacilitys: '/acFacility/getList',
    // 设备分类
    getFacilityDict: '/acFacility/getTypeDict',
    // 添加设备
    addFacility: '/acFacility/insert',
    // 编辑设备
    editFacility: '/acFacility/update',
    // 删除设备
    delFacility: '/acFacility/remove',
    // 生成文档
    initDocx: '/acResultOutput/initDocx',
    // 获取平台列表
    getPlatforms: '/platform/pageList',
    // 添加平台
    addPlatform: '/platform/create',
    // 编辑平台
    editPlatform: '/platform/update',
    // 更改状态
    editPlatStatus: '/platform/editStatus',
    // 删除
    delPlatform: '/platform/remove',
    // 获取平台
    getPlatAll: '/platform/dict',
    // 链接列表
    getUrlList: '/platformUrl/pageList',
    // 添加链接
    addPlatformUrl: '/platformUrl/create',
    // 编辑链接
    editPlatformUrl: '/platformUrl/update',
    // 删除链接
    delPlatformUrl: '/platformUrl/remove',

}

export default {
    apiList,
    getSymbol: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getSymbol,
            params
        )
    },
    addSymbol: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.addSymbol,
            params
        )
    },
    editSymbol: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editSymbol,
            params
        )
    },
    delSymbol: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.delSymbol,
            params
        )
    },
    getFormula: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getFormula,
            params
        )
    },
    addFormula: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.addFormula,
            params
        )
    },
    editFormula: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editFormula,
            params
        )
    },
    delFormula: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.delFormula,
            params
        )
    },
    getMathCode: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getMathCode,
            params
        )
    },
    addMathCode: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.addMathCode,
            params
        )
    },
    editMathCode: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editMathCode,
            params
        )
    },
    delMathCode: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.delMathCode,
            params
        )
    },
    getMathCodeList: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getMathCodeList,
            params
        )
    },
    getSymbolList: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getSymbolList,
            params
        )
    },
    getFormulaList: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getFormulaList,
            params
        )
    },
    getTypeGroup: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getTypeGroup,
            params
        )
    },
    validLabel: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.validLabel,
            params
        )
    },
    getViewFormula: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getViewFormula,
            params
        )
    },
    coreFunction: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.coreFunction,
            params
        )
    },
    getPrintWord: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getPrintWord,
            params
        )
    },
    getPrintList: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getPrintList,
            params
        )
    },
    getOutputInfo: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getOutputInfo,
            params
        )
    },
    getFacilitys: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getFacilitys,
            params
        )
    },
    getFacilityDict: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getFacilityDict,
            params
        )
    },
    addFacility: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.addFacility,
            params
        )
    },
    editFacility: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editFacility,
            params
        )
    },
    delFacility: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.delFacility,
            params
        )
    },
    initDocx: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.initDocx,
            params
        )
    },
    getPlatforms: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getPlatforms,
            params
        )
    },
    addPlatform: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.addPlatform,
            params
        )
    },
    editPlatform: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editPlatform,
            params
        )
    },
    editPlatStatus: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editPlatStatus,
            params
        )
    },
    delPlatform: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.delPlatform,
            params
        )
    },
    getPlatAll: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.getPlatAll,
            params
        )
    },
    getUrlList: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.getUrlList,
            params
        )
    },
    addPlatformUrl: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.addPlatformUrl,
            params
        )
    },
    editPlatformUrl: (params?: object) => {
        return http.post (
            ADMIN_MODULE + apiList.editPlatformUrl,
            params
        )
    },
    delPlatformUrl: (params?: object) => {
        return http.get (
            ADMIN_MODULE + apiList.delPlatformUrl,
            params
        )
    },
}