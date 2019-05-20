package com.example.a11810745.demomaps;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RequisicaoJson {
	private static final int TimeoutConexao = 20000;
	private static final int TimeoutLeitura = 20000;

	public interface Callback<T> {
		void concluido(int status, T objetoRetornado, String stringErro, Exception excecaoOcorrida);

	}

	private static final class RawByteArrayOutputStream extends ByteArrayOutputStream {
		public RawByteArrayOutputStream() {
			super();
		}

		public RawByteArrayOutputStream(int size) {
			super(size);
		}

		public byte[] rawBuffer() {
			return buf;
		}
	}

	private static class Tarefa<T> extends AsyncTask<Object, Object, T> {
		private Callback<T> callback;
		private volatile int status;
		private volatile String stringErro;
		private volatile Exception excecaoOcorrida;

		private static HttpURLConnection criarConexao(String method, String url, boolean temCorpo) throws IOException {
			HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url)).openConnection();
			urlConnection.setInstanceFollowRedirects(true);
			urlConnection.setConnectTimeout(TimeoutConexao);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(temCorpo);
			urlConnection.setReadTimeout(TimeoutLeitura);
			urlConnection.setRequestMethod(method);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("Accept-Charset", "utf-8");
			return urlConnection;
		}

		public Tarefa(Callback<T> callback) {
			this.callback = callback;
		}

		@Override
		protected T doInBackground(Object... objects) {
			try {
				return enviar(objects[0].toString(), objects[1].toString(), (byte[])objects[2], (int)objects[3], (String)objects[4], (Class<T>)objects[5]);
			} catch (Exception ex) {
				excecaoOcorrida = ex;
				return null;
			}
		}

		@Override
		protected void onPostExecute(T resultado) {
			callback.concluido(status, resultado, stringErro, excecaoOcorrida);
		}

		private T enviar(String url, String metodo, byte[] corpo, int tamanhoCorpo, String contentTypeCorpo, Class<T> classeDoObjetoRetornado) throws IOException {
			InputStream inputStream = null;
			RawByteArrayOutputStream outputStream = null;
			HttpURLConnection urlConnection = null;
			try {
				urlConnection = criarConexao(metodo, url, corpo != null && tamanhoCorpo > 0);
				if (corpo != null && tamanhoCorpo > 0) {
					urlConnection.setRequestProperty("Content-Type", contentTypeCorpo);
					urlConnection.getOutputStream().write(corpo, 0, tamanhoCorpo);
				}

				status = urlConnection.getResponseCode();

				if (status == 204) {
					// A resposta foi vazia!
					return null;
				} else if (status >= 200 && status <= 299) {
					inputStream = urlConnection.getInputStream();
				} else {
					inputStream = urlConnection.getErrorStream();
				}

				int maxLength = urlConnection.getContentLength();
				if (maxLength <= 0)
					maxLength = 32 * 1024;
				outputStream = new RawByteArrayOutputStream(maxLength);
				byte[] buffer = new byte[1024];
				int len;

				//Java >= 0 (-1 = EOF)
				//C# > 0 (0 = EOF)
				while ((len = inputStream.read(buffer, 0, 1024)) >= 0) {
					outputStream.write(buffer, 0, len);
				}

				buffer = outputStream.rawBuffer();
				len = ((buffer[0] == (byte)0xEF && buffer[1] == (byte)0xBB && buffer[2] == (byte)0xBF) ? 3 : 0);

				String str = new String(outputStream.rawBuffer(), len, outputStream.size() - len);

				if (status >= 200 && status <= 299) {
					return (new Gson()).fromJson(str, classeDoObjetoRetornado);
				} else {
					stringErro = str;
					return null;
				}
			} finally {
				// Vamos fazer a limpeza final
				try {
					if (urlConnection != null)
						urlConnection.disconnect();
				} catch (Exception ex) {
					// Apenas ignora...
				}
				try {
					if (outputStream != null)
						outputStream.close();
				} catch (Exception ex) {
					// Apenas ignora...
				}
				try {
					if (inputStream != null)
						inputStream.close();
				} catch (Exception ex) {
					// Apenas ignora...
				}
			}
		}
	}

	public static String montarURL(String host, String recurso, String... paresNomeValor) {
		final StringBuilder builder = new StringBuilder(host.length() + 2 + recurso.length() + (paresNomeValor.length << 3));
		builder.append(host);
		if (host.charAt(host.length() - 1) != '/' && recurso.charAt(0) != '/')
			builder.append('/');
		builder.append(recurso);
		try {
			if (paresNomeValor.length > 0) {
				if (recurso.charAt(recurso.length() - 1) != '?')
					builder.append('?');
				builder.append(URLEncoder.encode(paresNomeValor[0], "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(paresNomeValor[1] == null ? "null" : paresNomeValor[1], "UTF-8"));
				for (int i = 2; i < paresNomeValor.length; i += 2) {
					builder.append('&');
					builder.append(URLEncoder.encode(paresNomeValor[i], "UTF-8"));
					builder.append('=');
					builder.append(URLEncoder.encode(paresNomeValor[i + 1] == null ? "null" : paresNomeValor[i + 1], "UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException ex) {
			// Não tem problema ignorar essa exceção, porque o Android garante suportar UTF-8
		}
		return builder.toString();
	}

	public static <T> void get(Callback<T> callback, String url, Class<T> classeDoObjetoRetornado) {
		enviar(callback, url, "GET", null, 0, null, classeDoObjetoRetornado);
	}

	public static <T> void put(Callback<T> callback, String url, Class<T> classeDoObjetoRetornado, byte[] corpo, int tamanhoCorpo, String contentTypeCorpo) {
		enviar(callback, url, "PUT", corpo, tamanhoCorpo, contentTypeCorpo, classeDoObjetoRetornado);
	}

	public static <T> void put(Callback<T> callback, String url, Class<T> classeDoObjetoRetornado, Object objeto) {
		Gson gson = new Gson();
		String json = gson.toJson(objeto);
		try {
			byte[] bytesJson = json.getBytes("UTF-8");
			enviar(callback, url, "PUT", bytesJson, bytesJson.length, "application/json", classeDoObjetoRetornado);
		} catch (UnsupportedEncodingException ex) {
			// Não tem problema ignorar essa exceção, porque o Android garante suportar UTF-8
		}
	}

	public static <T> void post(Callback<T> callback, String url, Class<T> classeDoObjetoRetornado, byte[] corpo, int tamanhoCorpo, String contentTypeCorpo) {
		enviar(callback, url, "POST", corpo, tamanhoCorpo, contentTypeCorpo, classeDoObjetoRetornado);
	}

	public static <T> void post(Callback<T> callback, String url, Class<T> classeDoObjetoRetornado, Object objeto) {
		Gson gson = new Gson();
		String json = gson.toJson(objeto);
		try {
			byte[] bytesJson = json.getBytes("UTF-8");
			enviar(callback, url, "POST", bytesJson, bytesJson.length, "application/json", classeDoObjetoRetornado);
		} catch (UnsupportedEncodingException ex) {
			// Não tem problema ignorar essa exceção, porque o Android garante suportar UTF-8
		}
	}

	private static <T> void enviar(Callback<T> callback, String url, String metodo, byte[] corpo, int tamanhoCorpo, String contentTypeCorpo, Class<T> classeDoObjetoRetornado) {
		(new Tarefa<T>(callback)).execute(url, metodo, corpo, tamanhoCorpo, contentTypeCorpo, classeDoObjetoRetornado);
	}
}
