package android.media;

import android.media.IMediaHTTPService;
import android.os.IBinder;
import android.util.Log;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class MediaHTTPService extends IMediaHTTPService.Stub {
    private static final String TAG = "MediaHTTPService";
    private List<HttpCookie> mCookies;
    private final Object mCookieStoreInitializedLock = new Object();
    private boolean mCookieStoreInitialized = false;

    public MediaHTTPService(List<HttpCookie> list) {
        this.mCookies = list;
        Log.v(TAG, "MediaHTTPService(" + this + "): Cookies: " + list);
    }

    @Override // android.media.IMediaHTTPService
    public IMediaHTTPConnection makeHTTPConnection() {
        synchronized (this.mCookieStoreInitializedLock) {
            if (!this.mCookieStoreInitialized) {
                CookieHandler cookieHandler = CookieHandler.getDefault();
                if (cookieHandler == null) {
                    cookieHandler = new CookieManager();
                    CookieHandler.setDefault(cookieHandler);
                    Log.v(TAG, "makeHTTPConnection: CookieManager created: " + cookieHandler);
                } else {
                    Log.v(TAG, "makeHTTPConnection: CookieHandler (" + cookieHandler + ") exists.");
                }
                if (this.mCookies != null) {
                    if (cookieHandler instanceof CookieManager) {
                        CookieStore cookieStore = ((CookieManager) cookieHandler).getCookieStore();
                        Iterator<HttpCookie> it = this.mCookies.iterator();
                        while (it.hasNext()) {
                            try {
                                cookieStore.add(null, it.next());
                            } catch (Exception e) {
                                Log.v(TAG, "makeHTTPConnection: CookieStore.add" + e);
                            }
                        }
                    } else {
                        Log.w(TAG, "makeHTTPConnection: The installed CookieHandler is not a CookieManager. Can’t add the provided cookies to the cookie store.");
                    }
                }
                this.mCookieStoreInitialized = true;
                Log.v(TAG, "makeHTTPConnection(" + this + "): cookieHandler: " + cookieHandler + " Cookies: " + this.mCookies);
            }
        }
        return new MediaHTTPConnection();
    }

    static IBinder createHttpServiceBinderIfNecessary(String str) {
        return createHttpServiceBinderIfNecessary(str, null);
    }

    static IBinder createHttpServiceBinderIfNecessary(String str, List<HttpCookie> list) {
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return new MediaHTTPService(list).asBinder();
        }
        if (!str.startsWith("widevine://")) {
            return null;
        }
        Log.d(TAG, "Widevine classic is no longer supported");
        return null;
    }
}
