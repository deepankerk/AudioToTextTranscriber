package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the Employee service.
 */
@RestController
@RequestMapping(value = "/service")
public class ExampleController {
	
	public static final String uploadingdir =  "E:/Uploads/";
		
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public List<String> uploadFileMulti(@RequestParam("files") MultipartFile[] uploadfiles) throws Exception {
    	
    	List<String> resultText = new ArrayList<>();
    
        for(MultipartFile uploadedFile : uploadfiles) {
            File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);    
            resultText.add(RecognizeSpeech.recognizeAudio(uploadedFile.getOriginalFilename()));
        }
        
        return resultText;
    }
    
}