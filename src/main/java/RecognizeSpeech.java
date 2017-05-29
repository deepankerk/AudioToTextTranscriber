package main.java;


import com.google.cloud.speech.spi.v1.SpeechClient;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RecognizeSpeech {
	  public static String recognizeAudio(String fileName) throws Exception {
		  
		StringBuilder transcribedText = new StringBuilder();  
	    // Instantiates a client
	    SpeechClient speech = SpeechClient.create();

	    // The path to the audio file to transcribe
	    String filePath = "E:/Uploads/" + fileName; // for example "./resources/audio.raw";

	    // Reads the audio file into memory
	    Path path = Paths.get(filePath);
	    byte[] data = Files.readAllBytes(path);
	    ByteString audioBytes = ByteString.copyFrom(data);

	    // Builds the sync recognize request
	    RecognitionConfig config = RecognitionConfig.newBuilder()
	        .setEncoding(AudioEncoding.LINEAR16)
	        .setSampleRateHertz(16000)
	        .setLanguageCode("en-US")
	        .build();
	    RecognitionAudio audio = RecognitionAudio.newBuilder()
	        .setContent(audioBytes)
	        .build();

	    // Performs speech recognition on the audio file
	    RecognizeResponse response = speech.recognize(config, audio);
	    List<SpeechRecognitionResult> results = response.getResultsList();
	    
	    File file = new File("E:/newfile.txt");

		// If file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		
		// true = append file
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		System.out.print("Response String = ");
		System.out.println(response.toString());
		System.out.print("Size of results = ");
		System.out.println(results.size());

	    for (SpeechRecognitionResult result: results) {
	      List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
	      for (SpeechRecognitionAlternative alternative: alternatives) {
	    	  bufferedWriter.write(alternative.getTranscript());
	      }
	    }
	    
	    transcribedText.append(response.toString());
		bufferedWriter.flush();
		bufferedWriter.close();
	    speech.close();
	    
	    return transcribedText.toString();
	  }
	}