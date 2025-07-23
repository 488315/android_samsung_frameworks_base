package com.android.systemui.dagger;

import java.util.Map;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ContextComponentResolver implements ContextComponentHelper {
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
