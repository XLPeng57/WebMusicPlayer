<template>
    <div class="container">
        <div class="handle-box">
            <el-button @click="deleteAll">Delete Selected</el-button>
            <el-input v-model="searchWord" placeholder="Filter"></el-input>
        </div>
  
        <el-table height="550px" border size="small" :data="data" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40" align="center"></el-table-column>
            <el-table-column label="ID" prop="id" width="50" align="center"></el-table-column>

            <el-table-column label="Photo" width="102" align="center">
                <template v-slot="scope">
                    <img :src="attachImageUrl(scope.row.avator)" style="width: 80px" />
                </template>
            </el-table-column>

            <el-table-column label="Username" prop="username" width="80" align="center"></el-table-column>

            <el-table-column label="Gender" width="50" align="center">
                <template v-slot="scope">
                    <div>{{ changeSex(scope.row.sex) }}</div>
                </template>
            </el-table-column>

            <el-table-column label="Phone" prop="phoneNum" width="120" align="center"></el-table-column>
            <el-table-column label="Email" prop="email" width="120" align="center"></el-table-column>

            <el-table-column label="DOB" width="120" align="center">
                <template v-slot="scope">
                    <div>{{ getBirth(scope.row.birth) }}</div>
                </template>
            </el-table-column>

            <el-table-column label="Motto" prop="introduction"></el-table-column>
            <el-table-column label="Area" prop="location" width="70" align="center"></el-table-column>

            <el-table-column label="Collect" width="90" align="center">
                <template v-slot="scope">
                    <el-button @click="goCollectPage(scope.row.id)">Collect</el-button>
                </template>
            </el-table-column>

            <el-table-column label="Delete" width="90" align="center">
                <template v-slot="scope">
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
  
    <!-- 删除提示框 -->
    <Dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event"></Dialog>
  </template>

<script lang="ts">
import { defineComponent, getCurrentInstance, watch, ref, reactive, computed } from "vue";
import mixin from "@/mixins/mixin";
import { HttpManager } from "@/api";
import { RouterName } from "@/enums";
import Dialog from "@/components/Dialog.vue";
import { getBirth } from "@/utils";

export default defineComponent({

    components: {
        Dialog,
    },
    setup() {
        const { proxy } = getCurrentInstance() as any;
        const { changeSex, routerManager } = mixin();

        const tableData = ref([]); // 记录歌曲，用于显示
        const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
        const pageSize = ref(5); // 页数
        const currentPage = ref(1); // 当前页

        const data = computed(() => {
            return tableData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
        });

        const searchWord = ref(""); // 记录输入框输入的内容
            watch(searchWord, () => {
            if (searchWord.value === "") {
                tableData.value = tempDate.value;
            } else {
                tableData.value = [];
                for (let item of tempDate.value) {
                    if (item.username.includes(searchWord.value)) {
                        tableData.value.push(item);
                    }
                }
            }
        });

        getData();

        async function getData() {
            tableData.value = [];
            tempDate.value = [];
            const result = (await HttpManager.getAllUser()) as ResponseBody;
            tableData.value = result.data;
            tempDate.value = result.data;
            currentPage.value = 1;
        }

        function handleCurrentChange(val) {
            currentPage.value = val;
        }


        function goCollectPage(id) {
            const breadcrumbList = reactive([
                {
                    path: RouterName.Consumer,
                    name: "User Management",
                },
                {
                    path: "",
                    name: "Collection",
                },
            ]);

            proxy.$store.commit("setBreadcrumbList", breadcrumbList);
            routerManager(RouterName.Collect, { path: RouterName.Collect, query: { id } });
            
        }

        
        const idx = ref(-1); // 记录当前要删除的行
        const multipleSelection = ref([]); // 记录当前要删除的列表
        const delVisible = ref(false); // 显示删除框

        async function confirm() {
            const result = (await HttpManager.deleteUser(idx.value)) as ResponseBody;
            (proxy as any).$message({
                message: result.message,
                type: result.type,
            });
            if (result) getData();
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
            delVisible,
            pageSize,
            currentPage,
            deleteAll,
            handleSelectionChange,
            handleCurrentChange,
            changeSex,
            getBirth,
            deleteRow,
            confirm,
            goCollectPage,
            attachImageUrl: HttpManager.attachImageUrl,
        };

    }
})

</script>