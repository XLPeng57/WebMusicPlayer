package com.example.musicserver.controller;


import com.example.musicserver.common.R;
import com.example.musicserver.model.request.SingerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.musicserver.service.ISingerService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
@RestController
@CrossOrigin
public class SingerController {

    @Autowired
    private ISingerService singerService;

    @GetMapping("/singer/delete")
    public R deleteSinger(@RequestParam int id) {
        return singerService.deleteSinger(id);
    }

    @GetMapping("/singer")
    public R allSinger() {
        return singerService.allSinger();
    }

    @GetMapping("/singer/name/detail")
    public R singerOfName(@RequestParam String name) {
        return singerService.singerOfName(name);
    }

    @GetMapping("/singer/sex/detail")
    public R singerOfName(@RequestParam int sex) {
        return singerService.singerOfSex(sex);
    }

    @PostMapping("/singer/add")
    public R addSinger(@RequestBody SingerRequest addSingerRequest) {
        return singerService.addSinger(addSingerRequest);
    }

    @PostMapping("/singer/update")
    public R updateSingerMsg(@RequestBody SingerRequest updateSingerRequest) {
        return singerService.updateSingerMsg(updateSingerRequest);
    }

    @PostMapping("/singer/avatar/update")
    public R updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        return singerService.updateSingerPic(avatorFile, id);
    }


}

