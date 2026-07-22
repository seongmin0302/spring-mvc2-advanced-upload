package seongmin0302.upload.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("upload") 
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("upload")
    public String saveFile1(HttpServletRequest request) throws IOException, ServletException {
        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            log.info("========== PART ==========");
            log.info("part.getName()={}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            log.info("이건 뭐가 나올까요 => headerNames={}", headerNames);
            for (String headerName : headerNames) {
                log.info("headerName 은 {} 일때, part.getHeader(headerName) = {}",headerName, part.getHeader(headerName));
            }

            log.info("part.getSubmittedFileName()={}",part.getSubmittedFileName());
            log.info("part.getSize()={}", part.getSize());

            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body를 출력하는 거는 생략할게요 ㅠㅠㅠ");
            //log.info("body={}", body);

            if(StringUtils.hasText(part.getSubmittedFileName())) {
                String fulPath = fileDir + part.getSubmittedFileName();
                log.info("fulPath={}", fulPath);
                part.write(fulPath);
            }
        }

        return "upload-form";
    }
    
}
