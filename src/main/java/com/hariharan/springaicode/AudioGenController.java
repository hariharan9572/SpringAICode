package com.hariharan.springaicode;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AudioGenController {

    private OpenAiAudioTranscriptionModel audioModel;

    private OpenAiAudioSpeechModel audioSpeechModel;

    public AudioGenController(OpenAiAudioTranscriptionModel audioModel, OpenAiAudioSpeechModel speechModel){
        this.audioModel = audioModel;
        this.audioSpeechModel = speechModel;
    }

    @PostMapping("api/stt")
    public String speechToText(@RequestParam MultipartFile file){

        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .language("ta")
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.SRT)
                .build();

        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(file.getResource(), options);

        return audioModel.call(prompt).getResult().getOutput();
    }

    @PostMapping("api/tts")
    public byte[] textToSpeech(@RequestParam String text){
        return audioSpeechModel.call(text);
    }

}
