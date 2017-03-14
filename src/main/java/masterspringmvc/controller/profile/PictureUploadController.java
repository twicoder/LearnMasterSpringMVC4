package masterspringmvc.controller.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

/**
 * Created by I302636 on 3/14/2017.
 */
@Controller
public class PictureUploadController {
    public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");

    @RequestMapping("upload")
    public String uploadPage(){
        return "profile/uploadPage";
    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs) throws IOException {
        if(file.isEmpty() || !isImage(file)){
            redirectAttrs.addFlashAttribute("error","Incorrect file. Please upload a picture.");
            return "redirect:/upload";
        }

        copyFileToPictures(file);

        return "profile/uploadPage";
    }

    @RequestMapping(value="/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/images/anonymous.png");
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(classPathResource.getFilename()));
        IOUtils.copy(classPathResource.getInputStream(),response.getOutputStream());
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic",fileExtension,PICTURES_DIR.getFile());
        try(InputStream in = file.getInputStream();OutputStream out = new FileOutputStream(tempFile)){
            IOUtils.copy(in,out);
        }

        return new FileSystemResource(tempFile);
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name){
        return name.substring(name.lastIndexOf("."));
    }
}
