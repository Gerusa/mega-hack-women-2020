/**
 *
 */
package br.com.otto.play.util;

/**
 * Classe utilitária para armazenar as constantes do projeto.
 *
 * @author time21
 *
 */
public class ConstantUtil {

	public static final String RESOURCES_LIVRO = "/resources/livro/";

	// propriedades acesso
	public static final String CHAVE_ACESSO = "1edfdd82d9254ad5bd1a63fa80b56e09"; // chave de acesso pessoal. Expira em 30 dias a partir de 05/09/2020.
	public static final String URI_FREE_TOKEN = "https://westus.api.cognitive.microsoft.com/sts/v1.0/issueToken";
	public static final String URI_FREE_SERVICE = "https://westus.tts.speech.microsoft.com/cognitiveservices/v1";

	// propriedades audio
	public static final String MALE = "male";
	public static final String AUDIO_OUTPUT_FORMAT = "riff-24khz-16bit-mono-pcm";
	public static final String PT_BR = "pt-BR";
	public static final String VOICE_NAME = "pt-BR-Daniel-Apollo";

}
