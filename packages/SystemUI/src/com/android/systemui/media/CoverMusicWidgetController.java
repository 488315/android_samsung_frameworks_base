package com.android.systemui.media;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.subscreen.SubScreenManager;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class CoverMusicWidgetController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Consumer addVisibilityListenerConsumer;
    public boolean enabled;
    public final WakefulnessLifecycle lifecycle;
    public final Handler mediaPauseTimerHandler;
    public final CoverMusicWidgetController$observer$1 observer;
    public final Lazy onPlayerVisibilityListener$delegate;
    public long pauseTimerStartedTime;
    public boolean playerVisible;
    public final Consumer removeVisibilityListenerConsumer;
    public final SubScreenManager subScreenManager;
    public final CoverMusicWidgetController$widgetDisableRunnable$1 widgetDisableRunnable;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CoverMusicWidgetController(Consumer<SecMediaHost.MediaPanelVisibilityListener> consumer, Consumer<SecMediaHost.MediaPanelVisibilityListener> consumer2, SubScreenManager subScreenManager, WakefulnessLifecycle wakefulnessLifecycle) {
        this.addVisibilityListenerConsumer = consumer;
        this.removeVisibilityListenerConsumer = consumer2;
        this.subScreenManager = subScreenManager;
        this.lifecycle = wakefulnessLifecycle;
        Looper myLooper = Looper.myLooper();
        Intrinsics.checkNotNull(myLooper);
        this.mediaPauseTimerHandler = new Handler(myLooper);
        this.widgetDisableRunnable = new Runnable() { // from class: com.android.systemui.media.CoverMusicWidgetController$widgetDisableRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                CoverMusicWidgetController coverMusicWidgetController = CoverMusicWidgetController.this;
                int i = CoverMusicWidgetController.$r8$clinit;
                coverMusicWidgetController.enableWidget(false);
            }
        };
        this.enabled = true;
        this.onPlayerVisibilityListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CoverMusicWidgetController$onPlayerVisibilityListener$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final CoverMusicWidgetController coverMusicWidgetController = CoverMusicWidgetController.this;
                return new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.media.CoverMusicWidgetController$onPlayerVisibilityListener$2.1
                    @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
                    public final void onMediaVisibilityChanged(boolean z) {
                        CoverMusicWidgetController coverMusicWidgetController2 = CoverMusicWidgetController.this;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onPlayerVisibilityChanged before: ", " after: ", "CoverMusicWidgetController", coverMusicWidgetController2.playerVisible, z);
                        if (coverMusicWidgetController2.playerVisible == z) {
                            return;
                        }
                        coverMusicWidgetController2.playerVisible = z;
                        if (z) {
                            return;
                        }
                        coverMusicWidgetController2.enableWidget(false);
                        coverMusicWidgetController2.mediaPauseTimerHandler.removeCallbacks(coverMusicWidgetController2.widgetDisableRunnable);
                    }
                };
            }
        });
        this.observer = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.media.CoverMusicWidgetController$observer$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                CoverMusicWidgetController coverMusicWidgetController = CoverMusicWidgetController.this;
                if (coverMusicWidgetController.pauseTimerStartedTime == 0) {
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis() - coverMusicWidgetController.pauseTimerStartedTime;
                if (currentTimeMillis > 120000) {
                    Log.d("CoverMusicWidgetController", "Timer is exceed during sleep");
                    coverMusicWidgetController.enableWidget(false);
                    return;
                }
                long j = 120000 - currentTimeMillis;
                Log.d("CoverMusicWidgetController", "Timer should be set, " + (j / 1000) + "sec is left");
                Handler handler = coverMusicWidgetController.mediaPauseTimerHandler;
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(coverMusicWidgetController.widgetDisableRunnable, j);
            }
        };
    }

    public final void enableWidget(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("enabled: ", ", enable : ", "CoverMusicWidgetController", this.enabled, z);
        if (this.enabled == z) {
            return;
        }
        this.enabled = z;
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("com.samsung.android.widgetProviderName", "MusicTile");
        SubScreenManager subScreenManager = this.subScreenManager;
        if (z) {
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "enableTask() no plugin");
                return;
            }
            Log.i("SubScreenManager", "enableTask() " + m);
            subScreenManager.mSubScreenPlugin.enableTask(m);
            return;
        }
        if (subScreenManager.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "disableTask() no plugin");
        } else {
            Log.i("SubScreenManager", "disableTask() " + m);
            subScreenManager.mSubScreenPlugin.disableTask(m);
        }
        Bundle bundle = new Bundle();
        bundle.putString("com.samsung.android.widgetComponentName", "com.samsung.android.app.aodservice/MusicTile");
        bundle.putBoolean("visible", false);
        PluginSubScreen pluginSubScreen = subScreenManager.mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "updateCapsule() no plugin");
        } else {
            pluginSubScreen.updateCapsule(bundle);
        }
    }
}
