package android.webkit;

import android.webkit.CacheManager;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

@Deprecated
/* loaded from: classes4.dex */
public final class UrlInterceptRegistry {
    private static final String LOGTAG = "intercept";
    private static boolean mDisabled = false;
    private static LinkedList mHandlerList;

    private static synchronized LinkedList getHandlers() {
        if (mHandlerList == null) {
            mHandlerList = new LinkedList();
        }
        return mHandlerList;
    }

    @Deprecated
    public static synchronized void setUrlInterceptDisabled(boolean z) {
        mDisabled = z;
    }

    @Deprecated
    public static synchronized boolean urlInterceptDisabled() {
        return mDisabled;
    }

    @Deprecated
    public static synchronized boolean registerHandler(UrlInterceptHandler urlInterceptHandler) {
        if (getHandlers().contains(urlInterceptHandler)) {
            return false;
        }
        getHandlers().addFirst(urlInterceptHandler);
        return true;
    }

    @Deprecated
    public static synchronized boolean unregisterHandler(UrlInterceptHandler urlInterceptHandler) {
        return getHandlers().remove(urlInterceptHandler);
    }

    @Deprecated
    public static synchronized CacheManager.CacheResult getSurrogate(String str, Map<String, String> map) {
        if (urlInterceptDisabled()) {
            return null;
        }
        ListIterator listIterator = getHandlers().listIterator();
        while (listIterator.hasNext()) {
            CacheManager.CacheResult cacheResultService = ((UrlInterceptHandler) listIterator.next()).service(str, map);
            if (cacheResultService != null) {
                return cacheResultService;
            }
        }
        return null;
    }

    @Deprecated
    public static synchronized PluginData getPluginData(String str, Map<String, String> map) {
        if (urlInterceptDisabled()) {
            return null;
        }
        ListIterator listIterator = getHandlers().listIterator();
        while (listIterator.hasNext()) {
            PluginData pluginData = ((UrlInterceptHandler) listIterator.next()).getPluginData(str, map);
            if (pluginData != null) {
                return pluginData;
            }
        }
        return null;
    }
}
