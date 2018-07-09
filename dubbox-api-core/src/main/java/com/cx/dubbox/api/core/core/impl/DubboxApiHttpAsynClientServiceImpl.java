package com.cx.dubbox.api.core.core.impl;

import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.core.core.DubboxApiHttpClientService;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.codecs.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.nio.codecs.DefaultHttpResponseParser;
import org.apache.http.impl.nio.codecs.DefaultHttpResponseParserFactory;
import org.apache.http.impl.nio.conn.ManagedNHttpClientConnectionFactory;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.nio.NHttpMessageParser;
import org.apache.http.nio.NHttpMessageParserFactory;
import org.apache.http.nio.NHttpMessageWriterFactory;
import org.apache.http.nio.conn.ManagedNHttpClientConnection;
import org.apache.http.nio.conn.NHttpConnectionFactory;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.SessionInputBuffer;
import org.apache.http.nio.util.HeapByteBufferAllocator;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import javax.annotation.PostConstruct;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
public class DubboxApiHttpAsynClientServiceImpl implements DubboxApiHttpClientService {

    private CloseableHttpAsyncClient httpAsyncClient;

    private CountDownLatch latch;

    @PostConstruct
    public void init() {
        try {
            initHttpAsynClient();
        } catch (IOReactorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        DubboxApiHttpAsynClientServiceImpl p = new DubboxApiHttpAsynClientServiceImpl();

        try {
            p.test();
        } catch (Exception e1) {
            // TODO Auto-generated catch
            e1.printStackTrace();
        }
        try {
            System.in.read();
        } catch (IOException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // p.init();
        // System.out.println(p.doGet("https://www.baidu.com/", "1000"));
    }

    public void test() throws IOException, InterruptedException {
        try {
            init();
            HttpGet httpGet = new HttpGet("https://www.baidu.com/");
            String body = "";
            httpAsyncClient.start();
            latch = new CountDownLatch(1);
            httpAsyncClient.execute(httpGet, new FutureCallbackImpl(body, latch));
            latch.await();
        } finally {
            httpAsyncClient.close();
        }
    }



    private SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    private void initHttpAsynClient() throws IOReactorException {
        NHttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

            @Override
            public NHttpMessageParser<HttpResponse> create(final SessionInputBuffer buffer,
                                                           final MessageConstraints constraints) {
                LineParser lineParser = new BasicLineParser() {

                    @Override
                    public Header parseHeader(final CharArrayBuffer buffer) {
                        try {
                            return super.parseHeader(buffer);
                        } catch (ParseException ex) {
                            return new BasicHeader(buffer.toString(), null);
                        }
                    }

                };
                return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE,
                        constraints);
            }

        };
        NHttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

        NHttpConnectionFactory<ManagedNHttpClientConnection> connFactory = new ManagedNHttpClientConnectionFactory(
                requestWriterFactory, responseParserFactory, HeapByteBufferAllocator.INSTANCE);

        SSLContext sslcontext=null;
        try {
            sslcontext = this.createIgnoreVerifySSL();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();

        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy> create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                // .register("https", new SSLIOSessionStrategy(sslcontext, hostnameVerifier)).build();
                .register("https", new SSLIOSessionStrategy(sslcontext)).build();
        DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

            @Override
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("myhost")) {
                    return new InetAddress[] { InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }) };
                } else {
                    return super.resolve(host);
                }
            }

        };

        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors()).setConnectTimeout(30000)
                .setSoTimeout(30000).build();

        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);

        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor,
                connFactory, sessionStrategyRegistry, dnsResolver);

        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
                .setMaxLineLength(2000).build();
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
        connManager.setDefaultConnectionConfig(connectionConfig);

        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(10);

        CookieStore cookieStore = new BasicCookieStore();
        RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

        httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connManager)
                .build();
    }


    private static class FutureCallbackImpl implements FutureCallback<HttpResponse> {

        private String body;
        private CountDownLatch latch;

        public FutureCallbackImpl(String body, CountDownLatch latch) {
            this.body = body;
            this.latch = latch;
        }

        public void completed(final HttpResponse response) {
            // System.out.println(httpget.getRequestLine() + "->" +
            // response.getStatusLine())
            try {
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    if (entity != null) {
                        body = EntityUtils.toString(entity, "utf-8");
                        System.out.println("body======" + body);
                    }
                    EntityUtils.consume(entity);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            latch.countDown();
        }

        public void failed(final Exception ex) {

            System.out.println("failed.....");
            latch.countDown();
        }

        public void cancelled() {
            System.out.println(" cancelled....");
            latch.countDown();
        }

    }




    @Override
    public String doGet(String webUrl, String traceId) {
        final HttpGet httpget = new HttpGet(webUrl);
        httpget.setHeader(CommonUtil.TRACE_ID, traceId);

        String body = "";
        try {
            httpAsyncClient.start();
            Future<HttpResponse> r = httpAsyncClient.execute(httpget, null);
            try {
                HttpResponse response = r.get();
                try {
                    HttpEntity entity = response.getEntity();
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        if (entity != null) {
                            body = EntityUtils.toString(entity, "utf-8");
                            // System.out.println("body======" + body);
                        }
                        EntityUtils.consume(entity);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            /*
             * try { this.httpAsyncClient.close(); } catch (IOException e) { //
             * TODO Auto-generated catch block e.printStackTrace(); }
             */
        }
        return body;
    }

    @Override
    public String doGet(String webUrl, Map<String, String> paramMap, String traceId) {
        return null;
    }

    @Override
    public String doHttpsGet(String webUrl, String traceId) {
        return doGet(webUrl, traceId);
    }

    @Override
    public String doHttpsGet(String webUrl, Map<String, String> paramMap, String traceId) {
        return null;
    }

    @Override
    public String doHttpsPost(String url, String content, String contentType, String traceId) {
        return null;
    }

    @Override
    public String doPost(String url, String reqData, String contentType, String traceId) {
        return null;
    }
}
