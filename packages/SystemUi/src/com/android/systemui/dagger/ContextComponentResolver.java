package com.android.systemui.dagger;

import java.util.Map;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        Provider provider;
        try {
            provider = (Provider) map.get(Class.forName(str));
        } catch (ClassNotFoundException unused) {
        }
        if (provider == null) {
            return null;
        }
        return provider.get();
    }
}
