package com.android.systemui.dagger;

import java.util.Map;
import javax.inject.Provider;

public final class ContextComponentResolver implements ContextComponentHelper {
    public final Map mActivityCreators;
    public final Map mBroadcastReceiverCreators;
    public final Map mRecentsCreators;
    public final Map mServiceCreators;

    public ContextComponentResolver(Map<Class<?>, Provider> map, Map<Class<?>, Provider> map2, Map<Class<?>, Provider> map3, Map<Class<?>, Provider> map4) {
        this.mActivityCreators = map;
        this.mServiceCreators = map2;
        this.mRecentsCreators = map3;
        this.mBroadcastReceiverCreators = map4;
    }

    public static Object resolve(String str, Map map) {
        try {
            Provider provider = (Provider) map.get(Class.forName(str));
            if (provider == null) {
                return null;
            }
            return provider.get();
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }
}
