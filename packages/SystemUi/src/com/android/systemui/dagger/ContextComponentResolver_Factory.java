package com.android.systemui.dagger;

import java.util.Map;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ContextComponentResolver_Factory implements Provider {
    public final Provider activityCreatorsProvider;
    public final Provider broadcastReceiverCreatorsProvider;
    public final Provider recentsCreatorsProvider;
    public final Provider serviceCreatorsProvider;

    public ContextComponentResolver_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.activityCreatorsProvider = provider;
        this.serviceCreatorsProvider = provider2;
        this.recentsCreatorsProvider = provider3;
        this.broadcastReceiverCreatorsProvider = provider4;
    }

    public static ContextComponentResolver newInstance(Map map, Map map2, Map map3, Map map4) {
        return new ContextComponentResolver(map, map2, map3, map4);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ContextComponentResolver((Map) this.activityCreatorsProvider.get(), (Map) this.serviceCreatorsProvider.get(), (Map) this.recentsCreatorsProvider.get(), (Map) this.broadcastReceiverCreatorsProvider.get());
    }
}
