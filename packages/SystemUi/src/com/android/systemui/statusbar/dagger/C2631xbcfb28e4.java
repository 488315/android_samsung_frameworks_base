package com.android.systemui.statusbar.dagger;

import android.app.WallpaperManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.MediaArtworkProcessor;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideNotificationMediaManagerFactory */
/* loaded from: classes2.dex */
public final class C2631xbcfb28e4 implements Provider {
    public final Provider centralSurfacesOptionalLazyProvider;
    public final Provider colorExtractorProvider;
    public final Provider contextProvider;
    public final Provider displayManagerProvider;
    public final Provider dumpManagerProvider;
    public final Provider keyguardBypassControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mediaArtworkProcessorProvider;
    public final Provider mediaDataManagerProvider;
    public final Provider notifCollectionProvider;
    public final Provider notifPipelineProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider visibilityProvider;
    public final Provider wallpaperManagerProvider;

    public C2631xbcfb28e4(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.contextProvider = provider;
        this.centralSurfacesOptionalLazyProvider = provider2;
        this.notificationShadeWindowControllerProvider = provider3;
        this.visibilityProvider = provider4;
        this.mediaArtworkProcessorProvider = provider5;
        this.keyguardBypassControllerProvider = provider6;
        this.notifPipelineProvider = provider7;
        this.notifCollectionProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.mediaDataManagerProvider = provider10;
        this.statusBarStateControllerProvider = provider11;
        this.colorExtractorProvider = provider12;
        this.keyguardStateControllerProvider = provider13;
        this.dumpManagerProvider = provider14;
        this.wallpaperManagerProvider = provider15;
        this.displayManagerProvider = provider16;
    }

    public static NotificationMediaManager provideNotificationMediaManager(Context context, Lazy lazy, Lazy lazy2, NotificationVisibilityProvider notificationVisibilityProvider, MediaArtworkProcessor mediaArtworkProcessor, KeyguardBypassController keyguardBypassController, NotifPipeline notifPipeline, NotifCollection notifCollection, DelayableExecutor delayableExecutor, MediaDataManager mediaDataManager, StatusBarStateController statusBarStateController, SysuiColorExtractor sysuiColorExtractor, KeyguardStateController keyguardStateController, DumpManager dumpManager, WallpaperManager wallpaperManager, DisplayManager displayManager) {
        return new NotificationMediaManager(context, lazy, lazy2, notificationVisibilityProvider, mediaArtworkProcessor, keyguardBypassController, notifPipeline, notifCollection, delayableExecutor, mediaDataManager, statusBarStateController, sysuiColorExtractor, keyguardStateController, dumpManager, wallpaperManager, displayManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNotificationMediaManager((Context) this.contextProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), DoubleCheck.lazy(this.notificationShadeWindowControllerProvider), (NotificationVisibilityProvider) this.visibilityProvider.get(), (MediaArtworkProcessor) this.mediaArtworkProcessorProvider.get(), (KeyguardBypassController) this.keyguardBypassControllerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (MediaDataManager) this.mediaDataManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (SysuiColorExtractor) this.colorExtractorProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (WallpaperManager) this.wallpaperManagerProvider.get(), (DisplayManager) this.displayManagerProvider.get());
    }
}
