package masterspringmvc.controller.profile;

import masterspringmvc.config.PictureUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by I302636 on 3/14/2017.
 */
@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
    public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");

    private final Resource picturesDir;
    private final Resource anonymousPicture;

    @Autowired
    public PictureUploadController(PictureUploadProperties uploadProperties){
        picturesDir = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
    }

    @ModelAttribute("picturePath")
    public Resource picturePath(){
        return anonymousPicture;
    }

    @RequestMapping("upload")
    public String uploadPage(){
        return "profile/uploadPage";
    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException {
        if(file.isEmpty() || !isImage(file)){
            redirectAttrs.addFlashAttribute("error","Incorrect file. Please upload a picture.");
            return "redirect:/upload";
        }

        Resource picturePath = copyFileToPictures(file);
        model.addAttribute("picturePath",picturePath);

        return "profile/uploadPage";
    }

    @RequestMapping(value="/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response,@ModelAttribute("picturePath") Resource picturePath) throws IOException {
//        ClassPathResource classPathResource = new ClassPathResource("/images/anonymous.png");
//        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(classPathResource.getFilename()));
//        IOUtils.copy(classPathResource.getInputStream(),response.getOutputStream());
        response.setHeader("Content-Type",URLConnection.guessContentTypeFromName(picturePath.toString()));
        Files.copy(picturePath.getFile().toPath(),response.getOutputStream());
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic",fileExtension,picturesDir.getFile());
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
