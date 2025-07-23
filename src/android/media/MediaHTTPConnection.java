package android.media;

import android.media.IMediaHTTPConnection;
import android.net.InetAddresses;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
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
        IBinder native_getIMemory;
        synchronized (this.mIsDisconnecting) {
            try {
                disconnect();
                this.mAllowCrossDomainRedirect = true;
                this.mURL = new URL(str);
                this.mHeaders = convertHeaderStringToMap(str2);
                native_getIMemory = native_getIMemory();
            } catch (MalformedURLException unused) {
                return null;
            }
        }
        return native_getIMemory;
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
        boolean parseBoolean = parseBoolean(str2);
        this.mAllowCrossDomainRedirect = parseBoolean;
        this.mAllowCrossProtocolRedirect = parseBoolean;
        return true;
    }

    private synchronized Map<String, String> convertHeaderStringToMap(String str) {
        HashMap hashMap;
        hashMap = new HashMap();
        for (String str2 : str.split("\r\n")) {
            int indexOf = str2.indexOf(":");
            if (indexOf >= 0) {
                String substring = str2.substring(0, indexOf);
                String substring2 = str2.substring(indexOf + 1);
                if (!filterOutInternalHeaders(substring, substring2)) {
                    hashMap.put(substring, substring2);
                }
            }
        }
        return hashMap;
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

    /* JADX WARN: Code restructure failed: missing block: B:98:0x01dc, code lost:
    
        r9.mURL = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void seekTo(long r10) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaHTTPConnection.seekTo(long):void");
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
                    int read = this.mInputStream.read(bArr, 0, i);
                    if (read != -1) {
                        i2 = read;
                    }
                    this.mCurrentOffset += i2;
                } catch (UnknownServiceException e) {
                    Log.w(TAG, "readAt " + j + " / " + i + " => " + e);
                    return -1010;
                } catch (Exception unused) {
                    return -1;
                }
            } catch (ProtocolException e2) {
                Log.w(TAG, "readAt " + j + " / " + i + " => " + e2);
                return -1010;
            } catch (IOException unused2) {
                return -1;
            }
        } catch (NoRouteToHostException e3) {
            Log.w(TAG, "readAt " + j + " / " + i + " => " + e3);
            return -1010;
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
