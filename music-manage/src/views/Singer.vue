<template>
    <div class="container">
        <div class="handle-box">
            <el-button @click="deleteAll">Delete Selected</el-button>
            <el-input v-model="searchWord" placeholder="Filter"></el-input>
            <el-button type="primary" @click="centerDialogVisible = true">Add</el-button>
        </div>


        <el-table height="550px" border size="small" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40" align="center"></el-table-column>
            <el-table-column label="ID" prop="id" width="50" align="center"></el-table-column>

            <el-table-column label="Photo" prop="pic" width="110" align="center">
                <template v-slot="scope">
                    <div class="singer-img">
                        <img :src="attachImageUrl(scope.row.pic)" style="width: 100%" />
                    </div>
                    <el-upload :action="uploadUrl(scope.row.id)" :show-file-list="false" :on-success="handleImgSuccess" :before-upload="beforeImgUpload">
                        <el-button>Upload</el-button>
                    </el-upload>
                </template>
            </el-table-column>

            <el-table-column label="Name" prop="name" width="120" align="center"></el-table-column>
            <el-table-column label="Gender" prop="sex" width="60" align="center">
                <template v-slot="scope">
                    <div>{{ changeSex(scope.row.sex) }}</div>
                </template>
            </el-table-column>

            <el-table-column label="DOB" prop="birth" width="120" align="center">
                <template v-slot="scope">
                    <div>{{ getBirth(scope.row.birth) }}</div>
                </template>
            </el-table-column>

            <el-table-column label="Area" prop="location" width="100" align="center"></el-table-column>
            <el-table-column label="Intro" prop="introduction">
                <template v-slot="scope">
                    <p style="height: 100px; overflow: scroll">
                        {{ scope.row.introduction }}
                    </p>
                </template>
            </el-table-column>

            <el-table-column label="Management" width="100" align="center">
                <template v-slot="scope">
                    <el-button @click="goSongPage(scope.row)">Songs</el-button>
                </template>
            </el-table-column>

            <el-table-column label="Operation" width="180" align="center">
                <template v-slot="scope">
                    <el-button @click="editRow(scope.row)">Edit</el-button>
                    <el-button type="danger" @click="deleteRow(scope.row.id)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
        class="pagination"
        background
        layout="total, prev, pager, next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="tableData.length"
        @current-change="handleCurrentChange"
        >
        </el-pagination>
    </div>


    <el-dialog title="Add Singer" v-model="centerDialogVisible">
        <el-form label-width="80px" :model="registerForm" :rules="singerRule">
        <el-form-item label="Name" prop="name">
            <el-input v-model="registerForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Gender" prop="sex">
            <el-radio-group v-model="registerForm.sex">
                <el-radio :label="0">Female</el-radio>
                <el-radio :label="1">Male</el-radio>
                <el-radio :label="2">Declined to tell</el-radio>
                <el-radio :label="2">Group</el-radio>
                <el-radio :label="3">Unknown</el-radio>
            </el-radio-group>
        </el-form-item>

        <el-form-item label="Honetown" prop="location">
            <el-input v-model="registerForm.location"></el-input>
        </el-form-item>
        <el-form-item label="DOB" prop="birth">
            <el-date-picker type="date" v-model="registerForm.birth"></el-date-picker>
        </el-form-item>
        <el-form-item label="Intro" prop="introduction">
            <el-input type="textarea" v-model="registerForm.introduction"></el-input>
        </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="centerDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="addsinger">Yes.</el-button>
            </span>
        </template>
    </el-dialog>

    <!-- ??????????????? -->
    <el-dialog title="Edit" v-model="editVisible">
        <el-form label-width="60px" :model="editForm" :rules="singerRule">
        <el-form-item label="Name" prop="name">
            <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Gender" prop="sex">
            <el-radio-group v-model="editForm.sex">
                <el-radio :label="0">Female</el-radio>
                <el-radio :label="1">Male</el-radio>
                <el-radio :label="2">Declined to tell</el-radio>
                <el-radio :label="2">Group</el-radio>
                <el-radio :label="3">Unknown</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="DOB" prop="birth">
            <el-date-picker type="date" v-model="editForm.birth"></el-date-picker>
        </el-form-item>
        <el-form-item label="Hometown" prop="location">
            <el-input v-model="editForm.location"></el-input>
        </el-form-item>
        <el-form-item label="Intro" prop="introduction">
            <el-input type="textarea" v-model="editForm.introduction"></el-input>
        </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="editVisible = false">Cancel</el-button>
                <el-button type="primary" @click="saveEdit">Yes</el-button>
            </span>
        </template>
    </el-dialog>

    <Dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event"></Dialog>

</template>

<script lang="ts">
import { defineComponent, getCurrentInstance, watch, ref, reactive, computed } from "vue";
import mixin from "@/mixins/mixin";
import Dialog from "@/components/Dialog.vue";
import { HttpManager } from "@/api/index";
import { RouterName } from "@/enums";
import { getBirth } from "@/utils"

export default defineComponent({
    components: {
        Dialog,
    },

    setup(){

        const { proxy } = getCurrentInstance() as any;
        const { changeSex, routerManager, beforeImgUpload } = mixin();

        const tableData = ref([]); // ???????????????????????????
        const tempDate = ref([]); // ???????????????????????????????????????????????????????????????
        const pageSize = ref(5); // ??????
        const currentPage = ref(1); // ?????????
        
        const data = computed(() => {
            return tableData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
        });

        const searchWord = ref(""); // ??????????????????????????????
        watch(searchWord, () => {
        if (searchWord.value === "") {
            tableData.value = tempDate.value;
        } else {
            tableData.value = [];
            for (let item of tempDate.value) {
                if (item.name.includes(searchWord.value)) {
                    tableData.value.push(item);
                }
            }
        }
        });

        getData();

        async function getData() {
            tableData.value = [];
            tempDate.value = [];
            const result = (await HttpManager.getAllSinger()) as ResponseBody;
            tableData.value = result.data;
            tempDate.value = result.data;
            currentPage.value = 1;
        }
        // ???????????????
        function handleCurrentChange(val) {
            currentPage.value = val;
        }
        function uploadUrl(id) {
            return HttpManager.attachImageUrl(`/singer/avatar/update?id=${id}`);
        }

        function goSongPage(row) {
            const breadcrumbList = reactive([
                {
                    path: RouterName.Singer,
                    name: "????????????",
                },
                {
                    path: "",
                    name: "????????????",
                },
            ]);
            proxy.$store.commit("setBreadcrumbList", breadcrumbList);
            routerManager(RouterName.Song, {
                path: RouterName.Song,
                query: { id: row.id, name: row.name },
            });
        }

        const centerDialogVisible = ref(false);
        const registerForm = reactive({
            name: "",
            sex: "",
            birth: new Date(),
            location: "",
            introduction: "",
        });
        const singerRule = reactive({
            name: [{ required: true, trigger: "change" }],
            sex: [{ required: true, trigger: "change" }],
        });

        async function addsinger() {
            let datetime = getBirth(registerForm.birth);
            let name = registerForm.name;
            let sex = registerForm.sex;
            let birth = datetime;
            let location = registerForm.location;
            let introduction = registerForm.introduction;

            const result = (await HttpManager.addSinger({name,sex,birth,location,introduction})) as ResponseBody;
            (proxy as any).$message({
                message: result.message,
                type: result.type,
            });

            if (result.success) {
                getData();
                registerForm.birth = new Date();
                registerForm.name = "";
                registerForm.sex = "";
                registerForm.location = "";
                registerForm.introduction = "";
            }
            centerDialogVisible.value = false;

        }

        const editVisible = ref(false);
        const editForm = reactive({
            id: "",
            name: "",
            sex: "",
            pic: "",
            birth: new Date(),
            location: "",
            introduction: "",
        });

        function editRow(row) {
            editVisible.value = true;
            editForm.id = row.id;
            editForm.name = row.name;
            editForm.sex = row.sex;
            editForm.pic = row.pic;
            editForm.birth = row.birth;
            editForm.location = row.location;
            editForm.introduction = row.introduction;
        }

        async function saveEdit() {
            try {
                let datetime = getBirth(new Date(editForm.birth));
                let id = editForm.id;
                let name = editForm.name;
                let sex = editForm.sex;
                let birth = datetime;
                let location = editForm.location;
                let introduction = editForm.introduction;

                const result = (await HttpManager.updateSingerInfo({id,name,sex,birth,location,introduction})) as ResponseBody;
                (proxy as any).$message({
                    message: result.message,
                    type: result.type,
                });

                if (result.success) getData();
                editVisible.value = false;
            } catch (error) {
                console.error(error);
            }
        }

        function handleImgSuccess(response, file) {
            (proxy as any).$message({
                message: response.message,
                type: response.type,
            });
            if (response.success) getData();
        }

        const idx = ref(-1); // ???????????????????????????
        const multipleSelection = ref([]); // ??????????????????????????????
        const delVisible = ref(false); // ???????????????

        async function confirm() {
        const result = (await HttpManager.deleteSinger(idx.value)) as ResponseBody;
        (proxy as any).$message({
            message: result.message,
            type: result.type,
        });

        if (result.success) getData();
            delVisible.value = false;
        }
        function deleteRow(id) {
            idx.value = id;
            delVisible.value = true;
        }
        function handleSelectionChange(val) {
            multipleSelection.value = val;
        }
        function deleteAll() {
            for (let item of multipleSelection.value) {
                deleteRow(item.id);
                confirm();
            }
            multipleSelection.value = [];
        }

        return {
            searchWord,
            data,
            tableData,
            centerDialogVisible,
            editVisible,
            delVisible,
            pageSize,
            currentPage,
            registerForm,
            editForm,
            singerRule,
            deleteAll,
            handleSelectionChange,
            handleImgSuccess,
            beforeImgUpload,
            saveEdit,
            attachImageUrl: HttpManager.attachImageUrl,
            changeSex,
            getBirth,
            uploadUrl,
            goSongPage,
            editRow,
            handleCurrentChange,
            addsinger,
            confirm,
            deleteRow,
        };


    },
})

</script>

<style scoped>
.singer-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
</style>
