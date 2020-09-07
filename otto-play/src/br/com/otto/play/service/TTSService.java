//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license.
//
// Microsoft Cognitive Services (formerly Project Oxford): https://www.microsoft.com/cognitive-services
//
// Microsoft Cognitive Services (formerly Project Oxford) GitHub:
// https://github.com/Microsoft/Cognitive-Speech-TTS
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

package br.com.otto.play.service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import br.com.otto.play.comum.Authentication;
import br.com.otto.play.comum.HttpsConnection;
import br.com.otto.play.util.ByteArrayUtil;
import br.com.otto.play.util.ConstantUtil;
import br.com.otto.play.util.XmlDomUtil;

/**
 * * Classe de serviço responsável por realizar a comunicação, via rest, com a api
 * 'text-to-speech' da microsoft, para que este texto seja sintetizado. 
 * Classe de exemplo extraída da documentação da microsoft, contendo pequenas
 * alterações para atender o projeto.
 * 
 * @author Microsoft
 * @author time21
 *
 */
public class TTSService {

	/**
	 * Método responsável por realizar a sintetização de um texto - transformar
	 * texto em áudio.
	 * 
	 * @param textToSynthesize
	 */
	public static void textToSpeech(final String[] textToSynthesize) {
		
		if(textToSynthesize == null || textToSynthesize.length == 0){
			return;
		}
		
		final String outputFormat = ConstantUtil.AUDIO_OUTPUT_FORMAT;
		final String deviceLanguage = ConstantUtil.PT_BR;
		final String genderName = ConstantUtil.Female;
		final String voiceName = ConstantUtil.VOICE_NAME;

		try {
			
			byte[] audioBuffer = TTSService.Synthesize(textToSynthesize, outputFormat, deviceLanguage, genderName,
					voiceName);

			toSpeech(audioBuffer);

		} catch (Exception e) {
			System.err.println("Erro ao tentar converter texto em áudio: " + e.getMessage());
		}
	}

	/**
	 * @param audioBuffer
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	private static void toSpeech(byte[] audioBuffer) throws FileNotFoundException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		
		// write the pcm data to the file
		String outputWave = ".\\output.pcm";
		File outputAudio = new File(outputWave);
		FileOutputStream fstream = new FileOutputStream(outputAudio);
		fstream.write(audioBuffer);
		fstream.flush();
		fstream.close();

		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(outputWave));

		AudioFormat audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,AudioSystem.NOT_SPECIFIED);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();
		System.out.println("start to play the wave:");
		
		/*
		 * read the audio data and send to mixer
		 */
		int count;
		byte tempBuffer[] = new byte[4096];
		while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) > 0) {
			sourceDataLine.write(tempBuffer, 0, count);
		}

		sourceDataLine.drain();
		sourceDataLine.close();
		audioInputStream.close();
	}

	/**
	 * Método responsável por especificar o formato de um áudio.
	 * 
	 * @return AudioFormat
	 */
	private static AudioFormat getAudioFormat() {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 24000, 16, 1, 1 * 2, 24000, false);
	}
	
    /**
     * Synthesize the voice through the specified parameters.
     */
    private static byte[] Synthesize(String[] textToSynthesize, String outputFormat, String locale, String genderName, String voiceName) throws Exception {

        // Note: new unified SpeechService API key and issue token uri is per region
        // New unified SpeechService key
        // Free: https://azure.microsoft.com/en-us/try/cognitive-services/?api=speech-services
        // Paid: https://go.microsoft.com/fwlink/?LinkId=872236

    	Authentication auth = new Authentication(ConstantUtil.CHAVE_ACESSO);
        String accessToken = auth.GetAccessToken();

        HttpsURLConnection webRequest = HttpsConnection.getHttpsConnection(ConstantUtil.URI_FREE_SERVICE);
        webRequest.setDoInput(true);
        webRequest.setDoOutput(true);
        webRequest.setConnectTimeout(5000);
        webRequest.setReadTimeout(15000);
        webRequest.setRequestMethod("POST");

        webRequest.setRequestProperty("Content-Type", "application/ssml+xml");
        webRequest.setRequestProperty("X-Microsoft-OutputFormat", outputFormat);
        webRequest.setRequestProperty("Authorization", "Bearer " + accessToken);
        webRequest.setRequestProperty("X-Search-AppId", "07D3234E49CE426DAA29772419F436CA");
        webRequest.setRequestProperty("X-Search-ClientID", "1ECFAE91408841A480F00935DC390960");
        webRequest.setRequestProperty("User-Agent", "TTSAndroid");
        webRequest.setRequestProperty("Accept", "*/*");

        String body = XmlDomUtil.createDom(locale, genderName, voiceName, textToSynthesize);
        byte[] bytes = body.getBytes();
        webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
        webRequest.connect();

        DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
        dop.write(bytes);
        dop.flush();
        dop.close();

        InputStream inSt = webRequest.getInputStream();
        ByteArrayUtil ba = new ByteArrayUtil();
        
        int rn2 = 0;
        int bufferLength = 4096;
        byte[] buf2 = new byte[bufferLength];
        while ((rn2 = inSt.read(buf2, 0, bufferLength)) > 0) {
            ba.cat(buf2, 0, rn2);
        }

        inSt.close();
        webRequest.disconnect();

        return ba.getArray();
    }

}
