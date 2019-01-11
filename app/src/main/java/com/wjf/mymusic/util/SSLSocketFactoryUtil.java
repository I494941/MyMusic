package com.wjf.mymusic.util;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by wangshuai on 2018/6/22 0022 19:13.
 */
public class SSLSocketFactoryUtil {
    /*
     * 默认信任所有的证书
     * todo 最好加上证书认证，主流App都有自己的证书
     * */
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{createTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return sslSocketFactory;
    }


    public static X509TrustManager createTrustAllManager() {
        X509TrustManager tm = null;
        try {
            tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意客户端证书
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意服务端证书
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        } catch (Exception e) {

        }
        return tm;
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
