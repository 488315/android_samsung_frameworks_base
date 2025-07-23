package android.net;

import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.os.RoSystemProperties;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.SSLClientSessionCache;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Deprecated
/* loaded from: classes3.dex */
public class SSLCertificateSocketFactory extends SSLSocketFactory {
    private static final TrustManager[] INSECURE_TRUST_MANAGER = {new X509TrustManager() { // from class: android.net.SSLCertificateSocketFactory.1
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};
    private static final String TAG = "SSLCertificateSocketFactory";
    private byte[] mAlpnProtocols;
    private PrivateKey mChannelIdPrivateKey;
    private final int mHandshakeTimeoutMillis;
    private SSLSocketFactory mInsecureFactory;
    private KeyManager[] mKeyManagers;
    private byte[] mNpnProtocols;
    private final boolean mSecure;
    private SSLSocketFactory mSecureFactory;
    private final SSLClientSessionCache mSessionCache;
    private TrustManager[] mTrustManagers;

    @Deprecated
    public SSLCertificateSocketFactory(int i) {
        this(i, null, true);
    }

    private SSLCertificateSocketFactory(int i, SSLSessionCache sSLSessionCache, boolean z) {
        this.mInsecureFactory = null;
        this.mSecureFactory = null;
        this.mTrustManagers = null;
        this.mKeyManagers = null;
        this.mNpnProtocols = null;
        this.mAlpnProtocols = null;
        this.mChannelIdPrivateKey = null;
        this.mHandshakeTimeoutMillis = i;
        this.mSessionCache = sSLSessionCache != null ? sSLSessionCache.mSessionCache : null;
        this.mSecure = z;
    }

    public static SocketFactory getDefault(int i) {
        return new SSLCertificateSocketFactory(i, null, true);
    }

    public static SSLSocketFactory getDefault(int i, SSLSessionCache sSLSessionCache) {
        return new SSLCertificateSocketFactory(i, sSLSessionCache, true);
    }

    public static SSLSocketFactory getInsecure(int i, SSLSessionCache sSLSessionCache) {
        return new SSLCertificateSocketFactory(i, sSLSessionCache, false);
    }

    @Deprecated
    public static org.apache.http.conn.ssl.SSLSocketFactory getHttpSocketFactory(int i, SSLSessionCache sSLSessionCache) {
        return new org.apache.http.conn.ssl.SSLSocketFactory(new SSLCertificateSocketFactory(i, sSLSessionCache, true));
    }

    public static void verifyHostname(Socket socket, String str) throws IOException {
        if (!(socket instanceof SSLSocket)) {
            throw new IllegalArgumentException("Attempt to verify non-SSL socket");
        }
        if (isSslCheckRelaxed()) {
            return;
        }
        SSLSocket sSLSocket = (SSLSocket) socket;
        sSLSocket.startHandshake();
        SSLSession session = sSLSocket.getSession();
        if (session == null) {
            throw new SSLException("Cannot verify SSL socket without session");
        }
        if (HttpsURLConnection.getDefaultHostnameVerifier().verify(str, session)) {
            return;
        }
        throw new SSLPeerUnverifiedException("Cannot verify hostname: " + str);
    }

    private SSLSocketFactory makeSocketFactory(KeyManager[] keyManagerArr, TrustManager[] trustManagerArr) {
        try {
            SSLContext sSLContext = SSLContext.getInstance(org.apache.http.conn.ssl.SSLSocketFactory.TLS, "AndroidOpenSSL");
            sSLContext.init(keyManagerArr, trustManagerArr, null);
            sSLContext.getClientSessionContext().setPersistentCache(this.mSessionCache);
            return sSLContext.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException e) {
            Log.wtf(TAG, e);
            return (SSLSocketFactory) SSLSocketFactory.getDefault();
        }
    }

    private static boolean isSslCheckRelaxed() {
        return RoSystemProperties.DEBUGGABLE && SystemProperties.getBoolean("socket.relaxsslcheck", false);
    }

    private synchronized SSLSocketFactory getDelegate() {
        if (this.mSecure && !isSslCheckRelaxed()) {
            if (this.mSecureFactory == null) {
                this.mSecureFactory = makeSocketFactory(this.mKeyManagers, this.mTrustManagers);
            }
            return this.mSecureFactory;
        }
        if (this.mInsecureFactory == null) {
            if (this.mSecure) {
                Log.w(TAG, "*** BYPASSING SSL SECURITY CHECKS (socket.relaxsslcheck=yes) ***");
            } else {
                Log.w(TAG, "Bypassing SSL security checks at caller's request");
            }
            this.mInsecureFactory = makeSocketFactory(this.mKeyManagers, INSECURE_TRUST_MANAGER);
        }
        return this.mInsecureFactory;
    }

    public void setTrustManagers(TrustManager[] trustManagerArr) {
        this.mTrustManagers = trustManagerArr;
        this.mSecureFactory = null;
    }

    public void setNpnProtocols(byte[][] bArr) {
        this.mNpnProtocols = toLengthPrefixedList(bArr);
    }

    public void setAlpnProtocols(byte[][] bArr) {
        this.mAlpnProtocols = toLengthPrefixedList(bArr);
    }

    public static byte[] toLengthPrefixedList(byte[]... bArr) {
        if (bArr.length == 0) {
            throw new IllegalArgumentException("items.length == 0");
        }
        int i = 0;
        for (byte[] bArr2 : bArr) {
            if (bArr2.length == 0 || bArr2.length > 255) {
                throw new IllegalArgumentException("s.length == 0 || s.length > 255: " + bArr2.length);
            }
            i += bArr2.length + 1;
        }
        byte[] bArr3 = new byte[i];
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            byte[] bArr4 = bArr[i2];
            int i4 = i3 + 1;
            bArr3[i3] = (byte) bArr4.length;
            int length2 = bArr4.length;
            int i5 = 0;
            while (i5 < length2) {
                bArr3[i4] = bArr4[i5];
                i5++;
                i4++;
            }
            i2++;
            i3 = i4;
        }
        return bArr3;
    }

    public byte[] getNpnSelectedProtocol(Socket socket) {
        return castToOpenSSLSocket(socket).getNpnSelectedProtocol();
    }

    public byte[] getAlpnSelectedProtocol(Socket socket) {
        return castToOpenSSLSocket(socket).getAlpnSelectedProtocol();
    }

    public void setKeyManagers(KeyManager[] keyManagerArr) {
        this.mKeyManagers = keyManagerArr;
        this.mSecureFactory = null;
        this.mInsecureFactory = null;
    }

    public void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.mChannelIdPrivateKey = privateKey;
    }

    public void setUseSessionTickets(Socket socket, boolean z) {
        castToOpenSSLSocket(socket).setUseSessionTickets(z);
    }

    public void setHostname(Socket socket, String str) {
        castToOpenSSLSocket(socket).setHostname(str);
    }

    public void setSoWriteTimeout(Socket socket, int i) throws SocketException {
        castToOpenSSLSocket(socket).setSoWriteTimeout(i);
    }

    private static OpenSSLSocketImpl castToOpenSSLSocket(Socket socket) {
        if (!(socket instanceof OpenSSLSocketImpl)) {
            throw new IllegalArgumentException("Socket not created by this factory: " + socket);
        }
        return (OpenSSLSocketImpl) socket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        OpenSSLSocketImpl createSocket = getDelegate().createSocket(socket, str, i, z);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        OpenSSLSocketImpl createSocket = getDelegate().createSocket();
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        OpenSSLSocketImpl createSocket = getDelegate().createSocket(inetAddress, i, inetAddress2, i2);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        OpenSSLSocketImpl createSocket = getDelegate().createSocket(inetAddress, i);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        OpenSSLSocketImpl createSocket = getDelegate().createSocket(str, i, inetAddress, i2);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i) throws IOException {
        OpenSSLSocketImpl createSocket = getDelegate().createSocket(str, i);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return getDelegate().getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return getDelegate().getSupportedCipherSuites();
    }
}
