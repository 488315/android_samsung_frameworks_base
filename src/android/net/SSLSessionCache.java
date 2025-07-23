package android.net;

import android.content.Context;
import android.util.Log;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.FileClientSessionCache;
import com.android.org.conscrypt.SSLClientSessionCache;
import java.io.File;
import java.io.IOException;
import javax.net.ssl.SSLContext;

/* loaded from: classes3.dex */
public final class SSLSessionCache {
    private static final String TAG = "SSLSessionCache";
    final SSLClientSessionCache mSessionCache;

    public static void install(SSLSessionCache sSLSessionCache, SSLContext sSLContext) {
        ClientSessionContext clientSessionContext = sSLContext.getClientSessionContext();
        if (clientSessionContext instanceof ClientSessionContext) {
            clientSessionContext.setPersistentCache(sSLSessionCache == null ? null : sSLSessionCache.mSessionCache);
        } else {
            throw new IllegalArgumentException("Incompatible SSLContext: " + sSLContext);
        }
    }

    public SSLSessionCache(Object obj) {
        this.mSessionCache = (SSLClientSessionCache) obj;
    }

    public SSLSessionCache(File file) throws IOException {
        this.mSessionCache = FileClientSessionCache.usingDirectory(file);
    }

    public SSLSessionCache(Context context) {
        SSLClientSessionCache sSLClientSessionCache;
        File dir = context.getDir("sslcache", 0);
        try {
            sSLClientSessionCache = FileClientSessionCache.usingDirectory(dir);
        } catch (IOException e) {
            Log.w(TAG, "Unable to create SSL session cache in " + dir, e);
            sSLClientSessionCache = null;
        }
        this.mSessionCache = sSLClientSessionCache;
    }
}
