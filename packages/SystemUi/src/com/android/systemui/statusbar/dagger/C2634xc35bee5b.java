package com.android.systemui.statusbar.dagger;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory */
/* loaded from: classes2.dex */
public final class C2634xc35bee5b implements Provider {
    public final Provider clickNotifierProvider;
    public final Provider dumpManagerProvider;
    public final Provider statusBarServiceProvider;
    public final Provider visibilityProvider;

    public C2634xc35bee5b(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.dumpManagerProvider = provider;
        this.visibilityProvider = provider2;
        this.statusBarServiceProvider = provider3;
        this.clickNotifierProvider = provider4;
    }

    public static SmartReplyController provideSmartReplyController(DumpManager dumpManager, NotificationVisibilityProvider notificationVisibilityProvider, IStatusBarService iStatusBarService, NotificationClickNotifier notificationClickNotifier) {
        return new SmartReplyController(dumpManager, notificationVisibilityProvider, iStatusBarService, notificationClickNotifier);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SmartReplyController((DumpManager) this.dumpManagerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get());
    }
}
