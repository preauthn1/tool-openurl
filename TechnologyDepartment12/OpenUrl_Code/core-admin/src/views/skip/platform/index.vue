<template>
    <div class="table-box">

        <div class="card table-main">
            <el-row :gutter="24">
                <el-col :span="4">
                    <el-select v-model="search.id" placeholder="请选择类型" style="width: 200px">
                        <el-option
                            v-for="item in dictList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        />
                    </el-select>
                </el-col>
                <el-col :span="4">
                    <el-select v-model="search.status" placeholder="请选择状态" style="width: 200px">
                        <el-option :key="0" label="关闭" :value="0"/>
                        <el-option :key="1" label="开启" :value="1"/>
                    </el-select>
                </el-col>
                <el-col :span="4">
                    <div class="operation">
                        <el-button type="primary" :icon="Search" @click="getList"> 搜索 </el-button>
                        <el-button :icon="Refresh" @click="resetFunc"> 重置 </el-button>
                    </div>
                </el-col>
            </el-row>

            <!-- 表格 header 按钮 -->
            <div class="operation" style="margin-top: 10px;">
                <el-button v-auth="'setting.formula.insert'" type="success" :icon="Plus" @click="openAddModal"> 添加 </el-button>
            </div>

            <el-table :data="tableData" style="width: 100%" border c>
                <el-table-column prop="id" label="编号" width="60"  header-align="center" align="center"/>
                <el-table-column prop="name" label="名称"  header-align="center" align="center"/>
                <el-table-column prop="imgUrl" label="图标" width="80"  header-align="center" align="center">
                    <template #default="scope">
                        <el-image v-if="scope.row.imgUrl" :src="scope.row.imgUrl" fit="cover"
                                :preview-src-list="[scope.row.imgUrl]" :preview-teleported="true">
                                <template #error>
                                    <div class="image-slot">
                                        <el-icon>
                                            <ele-Picture />
                                        </el-icon>
                                    </div>
                                </template>
                            </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间"  header-align="center" align="center"/>
                <el-table-column prop="status" label="状态"  header-align="center" align="center" width="180">
                    <template #default="scope">
                        <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" @change="cutStatus(scope.row)" />
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" min-width="120"  header-align="center" align="center" width="240">
                    <template #default="scope">
                        <el-button v-auth="'setting.formula.update'" link type="primary" size="small" @click="editModal(scope.row)">编辑</el-button>
                        <el-button v-auth="'setting.formula.delete'" link type="primary" size="small" @click="formulaTableDel(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页组件 -->
            <el-pagination
                :background="true"
                :current-page="search.current"
                :page-size="search.size"
                :page-sizes="[10, 25, 50, 100]"
                :total="total"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
            />

        </div>



        <!-- 弹窗插件 -->
        <el-dialog v-model="dialogVisible"  :title="title" width="600" :destroy-on-close="true">
            <el-form ref="ruleFormRef" 
                :model="ruleForm"
                :rules="rules"
                label-width="auto"
                class="demo-ruleForm"
                status-icon
            >
                <el-form-item label="名称：" prop="name">
                    <el-input v-model="ruleForm.name" placeholder="请输入名称" :maxlength="32"/>
                </el-form-item>
                <el-form-item label="公式图片：" prop="imgUrl">
                    <div v-for="(item, index) in images" :key="index" class="preview-item" >
                        <img :src="item?.url" />
                        <span class="preview-item-operation">
                            <span style="margin-right: 20px" @click="handlePictureCardPreview(item)">
                                <el-icon><Plus /></el-icon>
                            </span>
                            <span @click="handleRemove(item)">
                                <el-icon>
                                    <Delete />
                                </el-icon>
                            </span>
                        </span>
                    </div>
                    <el-upload
                            ref="upload"
                            class="avatar-uploader"
                            v-model:file-list="images"
                            list-type="picture-card"
                            :limit="1"
                            accept="image/*"
                            :action="actionUrl"
                            :headers="headers"
                            :show-file-list="false"
                            :on-preview="handlePictureCardPreview"
                            :on-exceed="handleExceed"
                            :on-remove="handleRemove"
                            :on-success="imagesOnSuccess"
                            v-show="!images || (images && !images.length)"
                        >
                    </el-upload>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button v-if="title != '查看公式'" type="primary" @click="submitFormula(ruleFormRef)"> 提交</el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog v-model="dialogVisibleImages" fullscreen>
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
        </el-dialog>
    </div>
</template>

<script lang="ts">
import {onMounted, reactive, toRefs, ref} from 'vue'
import { genFileId ,ElMessage} from 'element-plus'
import { Search, Plus, Refresh, Upload, Delete, ZoomIn } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/modules/user';
import api from '@/api/apiList'

export default {
    name: '',
    components: {
        
    },
    setup() {
        const ruleFormRef = ref()
        const ruleFormRefTwo = ref()
        const upload = ref()
        const state = reactive({
            actionUrl: import.meta.env.VITE_API_URL+'/admin/filaAttach/upload',
            headers: {
                'Authorization': 'Bearer ' + useUserStore().token
            },
            images: [],
            dialogVisibleImages: false,
            dialogImageUrl: '',
            total: 0,
            title: '添加公式',
            childTitle: '添加条目',
            dialogVisible: false,
            dialogChildVisible: false,
            mathTypes: [],
            search: {
                current: 1,
                size: 10,
                id: null,
                status: null
            },
            typeList: [],
            ruleForm: {
                id: null,
                type: 1,
                name: '',
                imgUrl: ''
            },
            rules: {
                name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                imgUrl: [{ required: true, message: '请上传图片', trigger: 'blur' }],
                // value: [{ required: true, message: '请输入参数', trigger: 'blur' }],
                // formulaId: [{required: true, message: '请选择类型', trigger: 'change'}],
            },
            childRuleForm: {
                formulaId: null,
                goalId: null,
                sort: 0,
                name: null,
                alias: '',
                grouping: '',
                parentSort: null,
                value: '',
                vals: '',
                type: 1,
                remark: '',
            },
            childRules: {
                type: [{ required: true, message: '请选择类型', trigger: 'blur' }],
                name: [{ required: true, message: '请输入符号', trigger: 'blur' }],
                // grouping: [{ required: true, message: '请选择分组', trigger: 'blur' }],
                sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
                value: [{ required: true, message: '请输入排序', trigger: 'blur' }],
            },
            tableData: [],
            dictList: [],
            dictMap: {
                1: '工作的最多人数需风量',
                2: '爆破排烟需风量',
                3: '作业机械需风量',
                4: '洞内最小风速需风量',
                5: '绝对瓦斯涌出量',
                6: '风压计算',
                7: '供风量',
            },
            addFormulaList: [
                { label: '自设', value: 1 },
                { label: '计算符', value: 2 },
                { label: '符号配置', value: 3 },
                { label: '公式配置', value: 4 },
            ],
            addFormulaMap: {
                1: '自设',
                2: '计算符',
                3: '符号配置',
                4: '公式配置',
            },
            list: [],
        })

        const getList = () => {
            api.getPlatforms(state.search).then(res => {
                if(res.code == '0') {
                    state.tableData = res.data?.records
                    state.total = res.data?.total
                }
            })
        }





        const openAddModal = () => {
            state.images = []
            state.ruleForm = {
                id: null,
                type: 1,
                name: '',
                imgUrl: ''
            }
            state.title = '添加平台'
            state.dialogVisible = true
        }

        const handlePictureCardPreview = (uploadFile) => {
            state.dialogImageUrl = uploadFile.url
            state.dialogVisibleImages = true
        }
        const handleExceed = (files) => {
            upload.value.clearFiles()
            const file = files[0]
            file.uid = genFileId()
            upload.value.handleStart(file)
        }
        const handleRemove = (file) => {
            // form.value.icon = ''
            upload.value.clearFiles()
        }
        const imagesOnSuccess = (response, uploadFiles) => {
            console.log(response);
            state.ruleForm.imgUrl = response.data.src
        }

        const selectChildTypeFunc = (e) => {
            console.log(e);
            state.childRuleForm.goalId = null
            state.childRuleForm.value = null
            state.childRules['value'] = null
            if (e == 1) {
                state.typeList = []
                state.childRules['value'] = [{ required: true, message: '请输入自设参数', trigger: 'blur' }]
            }else if (e == 2) {
                api.getMathCodeList().then(res => {
                    if(res.code == '0'){
                        state.typeList = res.data as any[]
                    }
                })
            }else if (e == 3) {
                api.getSymbolList().then(res => {
                    if(res.code == '0'){
                        state.typeList = res.data as any[]
                    }
                })
            }else if (e == 4) {
                api.getFormulaList().then(res => {
                    if(res.code == '0'){
                        // TODO 测试数据
                        state.typeList = res.data as any[]
                    }
                })
            }
            if(ruleFormRefTwo.value) {
                ruleFormRefTwo.value.clearValidate()
            }
        }

        const selectChildValueFunc = (e) => {
            console.log(e);
            const data = state.typeList.find(item => item?.id == e)
            state.childRuleForm.goalId = data?.id
            state.childRuleForm.name = data?.name
            state.childRuleForm.remark = data?.cnName
            if (state.childRuleForm.type == 3) {
                state.childRuleForm.alias = data.alias
                state.childRuleForm.value = data?.value
                state.childRuleForm.vals = data?.vals
            }else if (state.childRuleForm.type == 2 && data?.type == 2) {
                state.childRuleForm.value = '2'
            }
        }

        const resetFunc = () => {
            state.search = {
                current: 1,
                size: 10,
                id: null,
                status: null,
            }
            getList()
        }

        const handleSizeChange = e => {
            state.search.size = e
            getList()
        }
        const handleCurrentChange = e => {
            state.search.current = e
            getList()
        }

        const openChildModal = (data, type) => {
            if(type == 1) {
                state.childRuleForm = {
                    formulaId: null,
                    goalId: null,
                    sort: 0,
                    name: null,
                    alias: '',
                    grouping: '',
                    parentSort: null,
                    value: '',
                    vals: '',
                    type: 1,
                    remark: '',
                }
                state.childRules['value'] = [{ required: true, message: '请输入自设参数', trigger: 'blur' }]
                state.childTitle = "添加条目"
            }else {
                state.childRuleForm = data
                if(data['type'] == 1) {
                    state.childRules['value'] = [{ required: true, message: '请输入自设参数', trigger: 'blur' }]
                }else {
                    state.childRules['value'] = null
                }
                state.childTitle = "编辑条目"
            }
            if(ruleFormRefTwo.value) {
                ruleFormRefTwo.value.clearValidate()
            }
            state.dialogChildVisible = true
        }

        const submitFormula = async (formEl) => {
            if (!formEl) return
            await formEl.validate((valid) => {
                if (valid) {
                    console.log(state.ruleForm);
                    if (state.title == "添加平台") {
                        api.addPlatform(state.ruleForm).then(res => {
                            if (res.code == '0') {
                                getList()
                                ElMessage.success("添加平台【" + state.ruleForm.name + "】成功")
                            }
                        })
                    }else {
                        api.editPlatform(state.ruleForm).then(res => {
                            if (res.code == '0') {
                                getList()
                                ElMessage.success("编辑公式【" + state.ruleForm.name + "】成功")
                            }
                        })
                    }

                    getTypeGroup()
                    state.dialogVisible = false
                }
            })
        }


        const formulaFunc = (data) => {
            let result = ""
            for (let item of data) {
                // 公式结果
                if (item['type'] == 4) {
                    result = result + item['name']
                }
                // 符号设置
                if (item['type'] == 3) {
                    result = result + item['name']
                }
                // 自设参数
                if (item['type'] == 1) {
                    result = result + " " + item['value']
                }
                // 计算符
                if (item['type'] == 2) {
                    // 计算符
                    let staticStr = state.mathTypes['1']
                    if (staticStr.includes(item['name'])) {
                        if (["(", ")"].includes(item['name'])) {
                            result = result + item['name']
                        }else {
                            result = result + " " + item['name'] + " "
                        }
                    }
                    // 变量符
                    staticStr = state.mathTypes['2']
                    if (staticStr.includes(item['name'])) {
                        let str =  JSON.parse(JSON.stringify(item['name']))
                        if (item['name'] == 'n^ⁿ' || item['name'] == ')^ⁿ') {
                            if (item['value'] > 9) {
                                let nums = item['value'].split('')
                                let strNum = ''
                                for (let n of nums) {
                                    strNum = strNum + toSuperscript(n)
                                }
                                str = str.replace("^ⁿ", strNum)
                            }else {
                                str = str.replace("^ⁿ", toSuperscript(item['value']))
                            }
                            str = str.replace("n", "")
                        }else {
                            str = str.replace("n", item['value'])
                            str = str.replace("^ⁿ", item['value'])
                        }

                        result = result + str
                    }
                }
            }

            console.log(result);
            return result
        }


        const toSuperscript = (num) => {
            const superscripts = {
                "0": "⁰",
                "1": "¹",
                "2": "²",
                "3": "³",
                "4": "⁴",
                "5": "⁵",
                "6": "⁶",
                "7": "⁷",
                "8": "⁸",
                "9": "⁹",
                "-": "⁻"
            };
            return String(num).split("").map(ch => superscripts[ch] || ch).join("");
        }


        const getTypeGroup = () => {
            api.getPlatAll().then(res => {
                if(res.code == '0') {
                    state.dictList = res.data as []
                }
            })
        }


        const formulaTableDel = (data) => {
            api.delPlatform({id: data.id}).then(res => {
                if(res.code == '0') {
                    getList()
                    ElMessage.success("删除成功")
                }
            })
        }

        const editFormulaDetail = (data) => {
            selectChildTypeFunc(data.type)
            state.childRuleForm = data
            if(data.type == 1) {
                state.childRules['value'] = [{ required: true, message: '请输入自设参数', trigger: 'blur' }]
            }else {
                state.childRules['value'] = null
            }
            state.childTitle = '编辑条目'
            if(ruleFormRefTwo.value) {
                ruleFormRefTwo.value.clearValidate()
            }
            state.dialogChildVisible = true

        }


        const viewModal = (data) => {
            state.ruleForm = data
            state.title = '查看平台'
            if(data.imgUrl){
                state.images = [{url:data.imgUrl}]
            }
            state.dialogVisible = true

            formulaFunc(data.childList)
        }

        const editModal = (data) => {
            state.ruleForm = data
            state.title = '编辑平台'
            if(data.imgUrl){
                state.images = [{url:data.imgUrl}]
            }
            state.dialogVisible = true
        }

        const cutStatus = (data) => {
            api.editPlatStatus(data).then(res => {
                if(res.code == '0') {
                    ElMessage.success("状态已更改")
                }
            })
        }



        onMounted(() => {
            getList()
            getTypeGroup()

            // setTimeout(formulaFunc, 1000)
        })

        return {
            ...toRefs(state),
            onMounted,
            Search,
            Plus,
            ZoomIn,
            Refresh,
            Upload,
            Delete,
            upload,
            getList,
            cutStatus,
            viewModal,
            editModal,
            resetFunc,
            formulaFunc,
            ruleFormRef,
            openAddModal,
            getTypeGroup,
            handleExceed,
            handleRemove,
            submitFormula,
            toSuperscript,
            ruleFormRefTwo,
            openChildModal,
            formulaTableDel,
            imagesOnSuccess,
            handleSizeChange,
            editFormulaDetail,
            handleCurrentChange,
            selectChildTypeFunc,
            selectChildValueFunc,
            handlePictureCardPreview
        }
    }
}

</script>

<style scoped lang="scss">
.avatar-uploader .avatar {
  width: 148px;
  height: 148px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 148px;
  height: 148px;
  text-align: center;
}

.preview-item {
	overflow: hidden;
	background-color: #fff;
	border: 1px solid #dcdfe6;
	border-radius: 6px;
	width: 148px;
	height: 148px;
	margin: 0px 8px 8px 0px;
	padding: 0px;
	display: inline-flex;
	position: relative;

	>img {
		width: 100%;
		height: 100%;
		object-fit: contain;
	}

	.preview-item-operation {
		position: absolute;
        width: 100%;
        height: 100%;
        left: 0;
        top: 0;
        cursor: default;
        display: inline-flex;
        justify-content: center;
        align-items: center;
        color: #fff;
        opacity: 0;
        font-size: 20px;
        background-color: rgba(0, 0, 0, 0.5);
        transition: opacity 0.3s;

		>span {
			cursor: pointer;
		}
	}

	.preview-item-operation:hover {
		opacity: 1;
	}
}
</style>