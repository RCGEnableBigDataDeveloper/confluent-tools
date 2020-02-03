package com.rcggs.confluent.tools.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.rcggs.confluent.tools.core.Accepts;
import com.rcggs.confluent.tools.core.ContentTypes;

public class HttpUtil {

	public static String get(final String resource) {
		return get(resource, null, null, null);
	}

	public static String get(final String resource, final String contentType) {
		return get(resource, contentType, null, null);
	}

	public static String get(final String resource, final String contentType, final String user,
			final String password) {		
		CloseableHttpClient client = HttpClients.createMinimal();
		String responseString = null;	
		try {
			HttpGet httpGet = new HttpGet(resource);
			if (contentType != null)
				httpGet.setHeader(ContentTypes.KEY, contentType);

			if (user != null)
				setCredentials(httpGet, user, password);
			
			CloseableHttpResponse response = client.execute(httpGet);
			responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

		} catch (AuthenticationException | ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responseString;
	}

	public static String post(final String resource, final String body) {
		return post(resource, body, null, null, null, null);
	}

	public static String post(final String resource, final String body, final String contentType) {
		return post(resource, body, contentType, null, null, null);
	}

	public static String post(final String resource, final String body, final String contentType,
			final String accepts) {
		return post(resource, body, contentType, accepts, null, null);
	}

	public static String post(final String resource, final String body, final String contentType, final String accepts,
			final String user, final String password) {
		CloseableHttpClient client = HttpClients.createMinimal();
		HttpPost httpPost = new HttpPost(resource);
		try {
			httpPost.setEntity(new StringEntity(body));
			if (contentType != null)
				httpPost.addHeader(ContentTypes.KEY, contentType);

			if (accepts != null)
				httpPost.addHeader(Accepts.KEY, accepts);

			if (user != null)
				setCredentials(httpPost, user, password);

			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();

			return EntityUtils.toString(entity, StandardCharsets.UTF_8);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static void setCredentials(final HttpRequest httpPost, final String user, final String password)
			throws AuthenticationException {

		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, password);
		httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
	}
}
