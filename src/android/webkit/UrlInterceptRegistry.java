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
        LinkedList linkedList;
        synchronized (UrlInterceptRegistry.class) {
            if (mHandlerList == null) {
                mHandlerList = new LinkedList();
            }
            linkedList = mHandlerList;
        }
        return linkedList;
    }

    @Deprecated
    public static synchronized void setUrlInterceptDisabled(boolean z) {
        synchronized (UrlInterceptRegistry.class) {
            mDisabled = z;
        }
    }

    @Deprecated
    public static synchronized boolean urlInterceptDisabled() {
        boolean z;
        synchronized (UrlInterceptRegistry.class) {
            z = mDisabled;
        }
        return z;
    }

    @Deprecated
    public static synchronized boolean registerHandler(UrlInterceptHandler urlInterceptHandler) {
        synchronized (UrlInterceptRegistry.class) {
            if (getHandlers().contains(urlInterceptHandler)) {
                return false;
            }
            getHandlers().addFirst(urlInterceptHandler);
            return true;
        }
    }

    @Deprecated
    public static synchronized boolean unregisterHandler(UrlInterceptHandler urlInterceptHandler) {
        boolean remove;
        synchronized (UrlInterceptRegistry.class) {
            remove = getHandlers().remove(urlInterceptHandler);
        }
        return remove;
    }

    @Deprecated
    public static synchronized CacheManager.CacheResult getSurrogate(String str, Map<String, String> map) {
        synchronized (UrlInterceptRegistry.class) {
            if (urlInterceptDisabled()) {
                return null;
            }
            ListIterator listIterator = getHandlers().listIterator();
            while (listIterator.hasNext()) {
                CacheManager.CacheResult service = ((UrlInterceptHandler) listIterator.next()).service(str, map);
                if (service != null) {
                    return service;
                }
            }
            return null;
        }
    }

    @Deprecated
    public static synchronized PluginData getPluginData(String str, Map<String, String> map) {
        synchronized (UrlInterceptRegistry.class) {
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
}
