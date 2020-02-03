package com.rcggs.confluent.tools.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class SimpleHttpServer {

	public static void main(String[] args) throws Exception {

		try {
			// setup the socket address
			InetSocketAddress address = new InetSocketAddress(8000);

			// initialise the HTTPS server
			HttpsServer httpsServer = HttpsServer.create(address, 0);
			SSLContext sslContext = SSLContext.getInstance("TLS");

			// initialise the keystore
			char[] password = "password".toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			InputStream fis = SimpleHttpServer.class.getResourceAsStream(("/keystore.jks"));
			ks.load(fis, password);

			// setup the key manager factory
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, password);

			// setup the trust manager factory
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);

			// setup the HTTPS context and parameters
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
				public void configure(HttpsParameters params) {
					try {
						// initialise the SSL context
						SSLContext context = getSSLContext();
						SSLEngine engine = context.createSSLEngine();
						params.setNeedClientAuth(false);
						params.setCipherSuites(engine.getEnabledCipherSuites());
						params.setProtocols(engine.getEnabledProtocols());

						// Set the SSL parameters
						SSLParameters sslParameters = context.getSupportedSSLParameters();
						params.setSSLParameters(sslParameters);

					} catch (Exception ex) {
						System.out.println("Failed to create HTTPS port");
					}
				}
			});


			httpsServer.setExecutor(null); 

			httpsServer.createContext("/static", new DefaultHandler());
			httpsServer.createContext("/api/topics/create", new HttpHandler() {

				@Override
				public void handle(HttpExchange t) throws IOException {
					String response = null;
					try {
						response = "YOU SUCK";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					InputStream in = t.getRequestBody();
					t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
					t.sendResponseHeaders(200, response.getBytes().length);
					OutputStream os = t.getResponseBody();
					os.write(response.getBytes());
					os.close();
				}
			});

			httpsServer.start();
			System.out.println("https server started...");

		} catch (Exception exception) {
			System.out.println("Failed to create HTTPS server on port " + 8000 + " of localhost");
			exception.printStackTrace();

		}
	}
}