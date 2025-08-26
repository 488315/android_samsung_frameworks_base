package android.media;

import android.content.IntentFilter;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.IMediaHTTPConnection;
import android.net.InetAddresses;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.share.SemShareConstants;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class MediaHTTPConnection extends IMediaHTTPConnection.Stub {
    private static final int CONNECT_TIMEOUT_MS = 30000;
    private static final int HTTP_TEMP_REDIRECT = 307;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "MediaHTTPConnection";
    private static final boolean VERBOSE = false;
    private long mNativeContext;
    private long mCurrentOffset = -1;
    private URL mURL = null;
    private Map<String, String> mHeaders = null;
    private volatile HttpURLConnection mConnection = null;
    private long mTotalSize = -1;
    private InputStream mInputStream = null;
    private boolean mAllowCrossDomainRedirect = true;
    private boolean mAllowCrossProtocolRedirect = true;
    private final AtomicInteger mNumDisconnectingThreads = new AtomicInteger(0);
    private int mResponse = 0;
    private Object mIsDisconnecting = new Object();

    private final native void native_finalize();

    private final native IBinder native_getIMemory();

    private static final native void native_init();

    private final native int native_readAt(long j, int i);

    private final native void native_setup();

    public MediaHTTPConnection() {
        if (CookieHandler.getDefault() == null) {
            Log.w(TAG, "MediaHTTPConnection: Unexpected. No CookieHandler found.");
        }
        native_setup();
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized IBinder connect(String str, String str2) {
        IBinder iBinderNative_getIMemory;
        synchronized (this.mIsDisconnecting) {
            try {
                disconnect();
                this.mAllowCrossDomainRedirect = true;
                this.mURL = new URL(str);
                this.mHeaders = convertHeaderStringToMap(str2);
                iBinderNative_getIMemory = native_getIMemory();
            } catch (MalformedURLException unused) {
                return null;
            }
        }
        return iBinderNative_getIMemory;
    }

    private static boolean parseBoolean(String str) {
        try {
            return Long.parseLong(str) != 0;
        } catch (NumberFormatException unused) {
            return "true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str);
        }
    }

    private synchronized boolean filterOutInternalHeaders(String str, String str2) {
        if (!"android-allow-cross-domain-redirect".equalsIgnoreCase(str)) {
            return false;
        }
        boolean z = parseBoolean(str2);
        this.mAllowCrossDomainRedirect = z;
        this.mAllowCrossProtocolRedirect = z;
        return true;
    }

    private synchronized Map<String, String> convertHeaderStringToMap(String str) {
        HashMap map;
        map = new HashMap();
        for (String str2 : str.split("\r\n")) {
            int iIndexOf = str2.indexOf(":");
            if (iIndexOf >= 0) {
                String strSubstring = str2.substring(0, iIndexOf);
                String strSubstring2 = str2.substring(iIndexOf + 1);
                if (!filterOutInternalHeaders(strSubstring, strSubstring2)) {
                    map.put(strSubstring, strSubstring2);
                }
            }
        }
        return map;
    }

    @Override // android.media.IMediaHTTPConnection
    public void disconnect() {
        this.mNumDisconnectingThreads.incrementAndGet();
        try {
            HttpURLConnection httpURLConnection = this.mConnection;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            synchronized (this.mIsDisconnecting) {
                teardownConnection();
                this.mHeaders = null;
                this.mURL = null;
            }
        } finally {
            this.mNumDisconnectingThreads.decrementAndGet();
        }
    }

    private void teardownConnection() {
        synchronized (this.mIsDisconnecting) {
            if (this.mConnection != null) {
                InputStream inputStream = this.mInputStream;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                    }
                    this.mInputStream = null;
                }
                this.mConnection.disconnect();
                this.mConnection = null;
                this.mCurrentOffset = -1L;
            }
        }
    }

    private static final boolean isLocalHost(URL url) {
        String host;
        if (url == null || (host = url.getHost()) == null) {
            return false;
        }
        if (host.equalsIgnoreCase("localhost")) {
            return true;
        }
        return InetAddresses.parseNumericAddress(host).isLoopbackAddress();
    }

    private synchronized void seekTo(long j) throws IOException {
        int iLastIndexOf;
        teardownConnection();
        try {
            URL url = this.mURL;
            boolean zIsLocalHost = isLocalHost(url);
            int i = 0;
            while (this.mNumDisconnectingThreads.get() <= 0) {
                if (zIsLocalHost) {
                    this.mConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
                } else {
                    this.mConnection = (HttpURLConnection) url.openConnection();
                }
                if (this.mNumDisconnectingThreads.get() > 0) {
                    throw new IOException("concurrently disconnecting");
                }
                Log.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + j + "] - setReadTimeout and setConnectTimeout with 8000ms");
                this.mConnection.setReadTimeout(8000);
                this.mConnection.setConnectTimeout(8000);
                this.mConnection.setInstanceFollowRedirects(this.mAllowCrossDomainRedirect);
                Map<String, String> map = this.mHeaders;
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        this.mConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                if (j > 0) {
                    this.mConnection.setRequestProperty("Range", "bytes=" + j + NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                }
                int responseCode = this.mConnection.getResponseCode();
                Log.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + j + "] - response code = " + responseCode);
                if (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307) {
                    i++;
                    if (i > 20) {
                        throw new NoRouteToHostException("Too many redirects: " + i);
                    }
                    String requestMethod = this.mConnection.getRequestMethod();
                    if (responseCode == 307 && !requestMethod.equals(SemShareConstants.HTTP_CONN_REQUEST_METHOD) && !requestMethod.equals("HEAD")) {
                        throw new NoRouteToHostException("Invalid redirect");
                    }
                    String headerField = this.mConnection.getHeaderField("Location");
                    if (headerField == null) {
                        throw new NoRouteToHostException("Invalid redirect");
                    }
                    URL url2 = new URL(this.mURL, headerField);
                    if (!url2.getProtocol().equals(IntentFilter.SCHEME_HTTPS) && !url2.getProtocol().equals(IntentFilter.SCHEME_HTTP)) {
                        throw new NoRouteToHostException("Unsupported protocol redirect");
                    }
                    boolean zEquals = this.mURL.getProtocol().equals(url2.getProtocol());
                    if (!this.mAllowCrossProtocolRedirect && !zEquals) {
                        throw new NoRouteToHostException("Cross-protocol redirects are disallowed");
                    }
                    boolean zEquals2 = this.mURL.getHost().equals(url2.getHost());
                    if (!this.mAllowCrossDomainRedirect && !zEquals2) {
                        throw new NoRouteToHostException("Cross-domain redirects are disallowed");
                    }
                    if (responseCode != 307) {
                        this.mURL = url2;
                    }
                    url = url2;
                } else {
                    if (this.mAllowCrossDomainRedirect) {
                        this.mURL = this.mConnection.getURL();
                    }
                    if (responseCode == 206) {
                        String headerField2 = this.mConnection.getHeaderField("Content-Range");
                        this.mTotalSize = -1L;
                        if (headerField2 != null && (iLastIndexOf = headerField2.lastIndexOf(47)) >= 0) {
                            try {
                                this.mTotalSize = Long.parseLong(headerField2.substring(iLastIndexOf + 1));
                            } catch (NumberFormatException unused) {
                            }
                        }
                    } else {
                        if (responseCode != 200) {
                            this.mResponse = responseCode;
                            throw new IOException();
                        }
                        this.mTotalSize = this.mConnection.getContentLength();
                    }
                    if (j > 0 && responseCode != 206) {
                        throw new ProtocolException();
                    }
                    this.mInputStream = new BufferedInputStream(this.mConnection.getInputStream());
                    this.mCurrentOffset = j;
                }
            }
            throw new IOException("concurrently disconnecting");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            this.mTotalSize = -1L;
            teardownConnection();
            this.mCurrentOffset = -1L;
            throw e;
        }
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized int readAt(long j, int i) {
        return native_readAt(j, i);
    }

    private synchronized int readAt(long j, byte[] bArr, int i) {
        int i2;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        try {
            try {
                try {
                    if (j != this.mCurrentOffset) {
                        seekTo(j);
                    }
                    i2 = 0;
                    int i3 = this.mInputStream.read(bArr, 0, i);
                    if (i3 != -1) {
                        i2 = i3;
                    }
                    this.mCurrentOffset += i2;
                } catch (NoRouteToHostException e) {
                    Log.w(TAG, "readAt " + j + " / " + i + " => " + e);
                    return -1010;
                }
            } catch (ProtocolException e2) {
                Log.w(TAG, "readAt " + j + " / " + i + " => " + e2);
                return -1010;
            } catch (IOException unused) {
                return -1;
            }
        } catch (UnknownServiceException e3) {
            Log.w(TAG, "readAt " + j + " / " + i + " => " + e3);
            return -1010;
        } catch (Exception unused2) {
            return -1;
        }
        return i2;
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized long getSize() {
        if (this.mConnection == null) {
            try {
                seekTo(0L);
            } catch (IOException unused) {
                return -1L;
            }
        }
        return this.mTotalSize;
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized String getMIMEType() {
        if (this.mConnection == null) {
            try {
                seekTo(0L);
            } catch (IOException unused) {
                if (this.mResponse / 100 >= 4) {
                    Log.w(TAG, "request failed with error => " + this.mResponse);
                    return "MEDIA_ERROR_IO";
                }
                return "application/octet-stream";
            }
        }
        return this.mConnection.getContentType();
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized String getUri() {
        return this.mURL.toString();
    }

    protected void finalize() {
        native_finalize();
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }
}
