<template>
  <div class="search-song-list">
    <PlayList :playList="playList" path="song-sheet-detail"></PlayList>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, watch, onMounted, getCurrentInstance } from "vue";
import { useStore } from "vuex";
import PlayList from "@/components/PlayList.vue";
import { HttpManager } from "@/api";

export default defineComponent({
  components: {
    PlayList,
  },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const store = useStore();

    const playList = ref([]);
    const searchWord = computed(() => store.getters.searchWord);
    watch(searchWord, (value) => {
      getSearchList(value);
    });

    async function getSearchList(value) {
      if (!value) return;
      const result = (await HttpManager.getSongListOfLikeTitle(value)) as ResponseBody;
      if (!result.data.length) {
        (proxy as any).$message({
          message: "暂无该歌曲内容",
          type: "warning",
        });
      } else {
        playList.value = result.data;
      }
    }

    onMounted(() => {
      getSearchList(proxy.$route.query.keywords);
    });

    return {
      playList,
    };
  },
});
</script>
