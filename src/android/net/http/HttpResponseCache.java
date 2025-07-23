package android.net.http;

import com.android.okhttp.internalandroidapi.AndroidResponseCacheAdapter;
import com.android.okhttp.internalandroidapi.HasCacheHolder;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class HttpResponseCache extends ResponseCache implements HasCacheHolder, Closeable {
    private final AndroidResponseCacheAdapter mDelegate;

    private HttpResponseCache(AndroidResponseCacheAdapter androidResponseCacheAdapter) {
        this.mDelegate = androidResponseCacheAdapter;
    }

    public static HttpResponseCache getInstalled() {
        ResponseCache responseCache = ResponseCache.getDefault();
        if (responseCache instanceof HttpResponseCache) {
            return (HttpResponseCache) responseCache;
        }
        return null;
    }

    public static synchronized HttpResponseCache install(File file, long j) throws IOException {
        synchronized (HttpResponseCache.class) {
            ResponseCache responseCache = ResponseCache.getDefault();
            if (responseCache instanceof HttpResponseCache) {
                HttpResponseCache httpResponseCache = (HttpResponseCache) responseCache;
                if (httpResponseCache.getCacheHolder().isEquivalent(file, j)) {
                    return httpResponseCache;
                }
                httpResponseCache.close();
            }
            HttpResponseCache httpResponseCache2 = new HttpResponseCache(new AndroidResponseCacheAdapter(HasCacheHolder.CacheHolder.create(file, j)));
            ResponseCache.setDefault(httpResponseCache2);
            return httpResponseCache2;
        }
    }

    @Override // java.net.ResponseCache
    public CacheResponse get(URI uri, String str, Map<String, List<String>> map) throws IOException {
        return this.mDelegate.get(uri, str, map);
    }

    @Override // java.net.ResponseCache
    public CacheRequest put(URI uri, URLConnection uRLConnection) throws IOException {
        return this.mDelegate.put(uri, uRLConnection);
    }

    public long size() {
        try {
            return this.mDelegate.getSize();
        } catch (IOException unused) {
            return -1L;
        }
    }

    public long maxSize() {
        return this.mDelegate.getMaxSize();
    }

    public void flush() {
        try {
            this.mDelegate.flush();
        } catch (IOException unused) {
        }
    }

    public int getNetworkCount() {
        return this.mDelegate.getNetworkCount();
    }

    public int getHitCount() {
        return this.mDelegate.getHitCount();
    }

    public int getRequestCount() {
        return this.mDelegate.getRequestCount();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (ResponseCache.getDefault() == this) {
            ResponseCache.setDefault(null);
        }
        this.mDelegate.close();
    }

    public void delete() throws IOException {
        if (ResponseCache.getDefault() == this) {
            ResponseCache.setDefault(null);
        }
        this.mDelegate.delete();
    }

    public HasCacheHolder.CacheHolder getCacheHolder() {
        return this.mDelegate.getCacheHolder();
    }
}
