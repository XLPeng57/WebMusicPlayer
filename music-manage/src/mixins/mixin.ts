import { getCurrentInstance, ref } from "vue";
import { LocationQueryRaw } from "vue-router";
import { RouterName } from "@/enums";

interface routerOptions {
    path?: string;
    query?: LocationQueryRaw;
}

export default function() {

    const { proxy } = getCurrentInstance() as any;
    const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);

    function changeSex(value) {
        if (value == 0) return "Female";
        else if (value == 1) return "Male";
        else if (value == 2) return "Group";
        else if (value == 3) return "Unknown";
        else if (value == "男") return "Male";
        else if (value == "女") return "Female";
    }

    function  beforeImgUpload(file) {
        
        const limit = 2;
        const isLt2M = file.size / 1024 / 1024 < limit;

        const isExistFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

        if (!isExistFileType) {
            (proxy as any).$message.error(`Only support file in ${uploadTypes.value.join(",")}} format.` );

        }

        if (!isLt2M) {
            (proxy as any).$message.error(`File size should not exceed ${limit}MB.`);
        }

        return isExistFileType && isLt2M;
    }

    function beforeSongUpload(file) {
        const limit = 10;
        const isLt10M = file.size / 1024 / 1024 < limit;
        const testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
        const extension = testmsg === "mp3";
    
        if (!extension) {
            (proxy as any).$message({
                message: "Only accepting mp3 file.",
                type: "error",
            });
        }
        if (!isLt10M) {
            (proxy as any).$message.error(`File size should not exceed ${limit}MB!`);
        }
    
        return extension && isLt10M;
    }

    function routerManager(routerName: string | number, options: routerOptions) {
        switch (routerName) {
            case RouterName.Song:
            case RouterName.ListSong:
            case RouterName.Comment:
            case RouterName.Consumer:
            case RouterName.Collect:
                proxy.$router.push({ path: options.path, query: options.query });
                break;
            case RouterName.Home:
            case RouterName.SignIn:
            case RouterName.SignOut:
            case RouterName.Info:
            case RouterName.Singer:
            case RouterName.SongList:
            case RouterName.Error:
            default:
                proxy.$router.push({ path: options.path });
                break;
        }
    }

    function goBack(step = -1) {
        proxy.$router.go(step);
    }

    return { changeSex, routerManager, goBack, beforeImgUpload, beforeSongUpload };

}