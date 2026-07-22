package seongmin0302.upload.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload") 
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName, @RequestParam MultipartFile file) throws IllegalStateException, IOException {
        
        log.info("itemName={}", itemName);
        log.info("file={}", file);

        if(!file.isEmpty()) {
            String fulPath = fileDir + file.getOriginalFilename();
            log.info("fulPath={}", fulPath);
            file.transferTo(new File(fulPath));
        }
        return "upload-form";

    }
}
