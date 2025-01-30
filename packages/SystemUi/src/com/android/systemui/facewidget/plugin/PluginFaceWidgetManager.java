package com.android.systemui.facewidget.plugin;

import android.R;
import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper.C13851;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.pluginlock.PluginLockDataImpl;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.annotations.VersionCheckingProxy;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeController;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardSidePadding;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginSecKeyguardClockPositionAlgorithm;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PluginFaceWidgetManager implements PluginListener, PluginKeyguardStatusView.Callback {
    public int mAppPluginVersion;
    public final FaceWidgetColorSchemeControllerWrapper mColorSchemeControllerWrapper;
    public View mContainerView;
    public final FaceWidgetDisplayLifeCycleWrapper mDisplayLifeCycleWrapper;
    public final DozeParameters mDozeParameters;
    public final ExternalClockProvider mExternalClockProvider;
    public final FaceWidgetContainerWrapper mFaceWidgetContainerWrapper;
    public final FaceWidgetKnoxStateMonitorWrapper mFaceWidgetKnoxStateMonitorWrapper;
    public PluginKeyguardStatusView mFaceWidgetPlugin;
    public final KeyguardFastBioUnlockController mFastBioUnlockController;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final FaceWidgetKeyguardStatusCallbackWrapper mKeyguardStatusCallbackWrapper;
    public final FaceWidgetKeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final FaceWidgetLockPatternUtilsWrapper mLockPatternUtils;
    public final MediaDataManager mMediaDataManager;
    public NotificationPanelViewController mNPVController;
    public final FaceWidgetNotificationControllerWrapper mNotificationControllerWrapper;
    public final Lazy mPluginAODManagerLazy;
    public Context mPluginContext;
    public PluginKeyguardSidePadding mPluginKeyguardSidePadding;
    public final FaceWidgetPluginLockManagerWrapper mPluginLockManagerWrapper;
    public final PluginManager mPluginManager;
    public final KeyguardClockPositionAlgorithm mPositionAlgorithm;
    public final Context mSysuiContext;
    public final FaceWidgetWakefulnessLifecycleWrapper mWakefullnessLifecycleWrapper;
    public final FaceWidgetWallpaperUtilsWrapper mWallpaperUtilsWrapper;
    public final C13871 mEditModeListener = new KeyguardEditModeController.Listener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.1
        @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
        public final void onAnimationEnded() {
            PluginKeyguardStatusView pluginKeyguardStatusView = PluginFaceWidgetManager.this.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.onCancelEditMode();
            }
        }

        @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
        public final void onAnimationStarted(boolean z) {
            PluginKeyguardStatusView pluginKeyguardStatusView = PluginFaceWidgetManager.this.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.onStartingEditModeAnimation(z);
            }
        }
    };
    public final SharedPreferencesOnSharedPreferenceChangeListenerC13882 mSharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.2
        @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
        public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
            if (PluginFaceWidgetManager.this.mFaceWidgetPlugin == null || !"QsMediaPlayerLastExpanded".equals(str)) {
                return;
            }
            AbstractC0000x2c234b15.m3m("onSharedPreferenceChanged, key = ", str, "PluginFaceWidgetManager");
            PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
            pluginFaceWidgetManager.mFaceWidgetPlugin.onMediaPlayerLastExpandedPrefChanged(Prefs.getBoolean(pluginFaceWidgetManager.mSysuiContext, "QsMediaPlayerLastExpanded", true));
        }
    };
    public final HandlerC13893 mUiHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.3
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            KeyguardTouchAnimator keyguardTouchAnimator;
            int i = message.what;
            if (i == 0) {
                Log.d("PluginFaceWidgetManager", "Init Plugin Wrapper started");
                final PluginKeyguardStatusView pluginKeyguardStatusView = (PluginKeyguardStatusView) message.obj;
                PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                pluginFaceWidgetManager.getClass();
                Objects.requireNonNull(pluginKeyguardStatusView);
                Supplier supplier = new Supplier() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return Integer.valueOf(PluginKeyguardStatusView.this.getVersion());
                    }
                };
                PluginKeyguardStatusView pluginKeyguardStatusView2 = (PluginKeyguardStatusView) new VersionCheckingProxy(PluginKeyguardStatusView.class, pluginKeyguardStatusView, supplier).get();
                pluginFaceWidgetManager.mFaceWidgetPlugin = pluginKeyguardStatusView2;
                if (pluginKeyguardStatusView2 != null) {
                    pluginKeyguardStatusView2.setPluginFaceWidgetCallback(pluginFaceWidgetManager);
                    PluginKeyguardStatusView pluginKeyguardStatusView3 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    pluginFaceWidgetManager.mWakefullnessLifecycleWrapper.mPluginKeyguardStatusView = pluginKeyguardStatusView3;
                    pluginFaceWidgetManager.mFaceWidgetKnoxStateMonitorWrapper.mPluginKeyguardStatusView = pluginKeyguardStatusView3;
                    KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = pluginFaceWidgetManager.mPositionAlgorithm;
                    if (keyguardClockPositionAlgorithm instanceof FaceWidgetPositionAlgorithmWrapper) {
                        FaceWidgetPositionAlgorithmWrapper faceWidgetPositionAlgorithmWrapper = (FaceWidgetPositionAlgorithmWrapper) keyguardClockPositionAlgorithm;
                        Resources resources = pluginFaceWidgetManager.mSysuiContext.getResources();
                        faceWidgetPositionAlgorithmWrapper.mPositionAlgorithm = (PluginSecKeyguardClockPositionAlgorithm) new VersionCheckingProxy(PluginSecKeyguardClockPositionAlgorithm.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getPositionAlgorithm(), supplier).get();
                        faceWidgetPositionAlgorithmWrapper.loadDimens(resources);
                    }
                    PluginNotificationController pluginNotificationController = (PluginNotificationController) new VersionCheckingProxy(PluginNotificationController.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getNotificationController(), supplier).get();
                    Context context = pluginFaceWidgetManager.mPluginContext;
                    FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = pluginFaceWidgetManager.mNotificationControllerWrapper;
                    faceWidgetNotificationControllerWrapper.mNotificationController = pluginNotificationController;
                    if (pluginNotificationController != null) {
                        pluginNotificationController.init(null);
                    }
                    faceWidgetNotificationControllerWrapper.mContext = context;
                    PluginClockProvider pluginClockProvider = (PluginClockProvider) new VersionCheckingProxy(PluginClockProvider.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getClockProvider(), supplier).get();
                    ExternalClockProvider externalClockProvider = pluginFaceWidgetManager.mExternalClockProvider;
                    externalClockProvider.mClockProvider = pluginClockProvider;
                    Iterator it = ((ArrayList) externalClockProvider.mClockCallbacks).iterator();
                    while (it.hasNext()) {
                        PluginClockProvider.ClockCallback clockCallback = (PluginClockProvider.ClockCallback) it.next();
                        if (clockCallback != null) {
                            externalClockProvider.mClockProvider.registerClockChangedCallback(clockCallback);
                        }
                    }
                    pluginFaceWidgetManager.mPluginKeyguardSidePadding = (PluginKeyguardSidePadding) new VersionCheckingProxy(PluginKeyguardSidePadding.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getSecKeyguardSidePadding(), supplier).get();
                    return;
                }
                return;
            }
            if (i != 1) {
                return;
            }
            Log.d("PluginFaceWidgetManager", "Attach container started");
            PluginFaceWidgetManager pluginFaceWidgetManager2 = PluginFaceWidgetManager.this;
            if (pluginFaceWidgetManager2.mFaceWidgetPlugin != null) {
                NotificationPanelViewController notificationPanelViewController = pluginFaceWidgetManager2.mNPVController;
                int indexOfChild = notificationPanelViewController == null ? 0 : notificationPanelViewController.mView.indexOfChild(notificationPanelViewController.mEditModeContainer) + 1;
                PluginKeyguardStatusView pluginKeyguardStatusView4 = pluginFaceWidgetManager2.mFaceWidgetPlugin;
                NotificationPanelViewController notificationPanelViewController2 = pluginFaceWidgetManager2.mNPVController;
                pluginKeyguardStatusView4.attachFaceWidgetContainer(notificationPanelViewController2 != null ? notificationPanelViewController2.mView : null, pluginFaceWidgetManager2.mContainerView, indexOfChild);
                View containerView = pluginFaceWidgetManager2.mFaceWidgetPlugin.getContainerView();
                pluginFaceWidgetManager2.mContainerView = containerView;
                PluginKeyguardStatusView pluginKeyguardStatusView5 = pluginFaceWidgetManager2.mFaceWidgetPlugin;
                pluginFaceWidgetManager2.mFaceWidgetContainerWrapper.initPlugin(pluginKeyguardStatusView5, containerView, pluginKeyguardStatusView5.getContentsContainers());
                NotificationPanelViewController notificationPanelViewController3 = pluginFaceWidgetManager2.mNPVController;
                if (notificationPanelViewController3 != null && (keyguardTouchAnimator = notificationPanelViewController3.mKeyguardTouchAnimator) != null) {
                    SparseArray sparseArray = keyguardTouchAnimator.views;
                    sparseArray.remove(1);
                    sparseArray.remove(3);
                    sparseArray.remove(8);
                }
                pluginFaceWidgetManager2.updateFaceWidgetArea();
                Lazy lazy = pluginFaceWidgetManager2.mPluginAODManagerLazy;
                PluginAODManager pluginAODManager = (PluginAODManager) lazy.get();
                List list = pluginAODManager.mConnectionRunnableList;
                if (list != null) {
                    Iterator it2 = ((ArrayList) list).iterator();
                    while (it2.hasNext()) {
                        ((Runnable) it2.next()).run();
                    }
                    ((ArrayList) pluginAODManager.mConnectionRunnableList).clear();
                }
                ((PluginAODManager) lazy.get()).mPluginAODStateProvider = pluginFaceWidgetManager2.mFaceWidgetPlugin.getAODStateProvider();
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper2 = pluginFaceWidgetManager2.mNotificationControllerWrapper;
                if (faceWidgetNotificationControllerWrapper2.mMediaDataListener == null) {
                    faceWidgetNotificationControllerWrapper2.mMediaDataListener = faceWidgetNotificationControllerWrapper2.new C13851();
                }
                final FaceWidgetNotificationControllerWrapper.C13851 c13851 = faceWidgetNotificationControllerWrapper2.mMediaDataListener;
                MediaDataManager mediaDataManager = pluginFaceWidgetManager2.mMediaDataManager;
                mediaDataManager.mediaDataFilter._listeners.add(c13851);
                LinkedHashMap linkedHashMap = mediaDataManager.mediaDataFilter.userEntries;
                if (!linkedHashMap.isEmpty()) {
                    linkedHashMap.forEach(new BiConsumer() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            MediaDataManager.Listener.this.onMediaDataLoaded((String) obj, "", (MediaData) obj2, false, 0, false);
                        }
                    });
                }
                ((ArrayList) ((KeyguardEditModeControllerImpl) pluginFaceWidgetManager2.mKeyguardEditModeController).listeners).add(pluginFaceWidgetManager2.mEditModeListener);
                Context context2 = pluginFaceWidgetManager2.mSysuiContext;
                context2.getSharedPreferences(context2.getPackageName(), 0).registerOnSharedPreferenceChangeListener(pluginFaceWidgetManager2.mSharedPrefListener);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$2] */
    /* JADX WARN: Type inference failed for: r1v21, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$3] */
    public PluginFaceWidgetManager(Context context, PluginManager pluginManager, KeyguardFoldController keyguardFoldController, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, FaceWidgetContainerWrapper faceWidgetContainerWrapper, FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper, FaceWidgetKeyguardUpdateMonitorWrapper faceWidgetKeyguardUpdateMonitorWrapper, FaceWidgetDisplayLifeCycleWrapper faceWidgetDisplayLifeCycleWrapper, FaceWidgetWakefulnessLifecycleWrapper faceWidgetWakefulnessLifecycleWrapper, FaceWidgetKnoxStateMonitorWrapper faceWidgetKnoxStateMonitorWrapper, FaceWidgetLockPatternUtilsWrapper faceWidgetLockPatternUtilsWrapper, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, FaceWidgetColorSchemeControllerWrapper faceWidgetColorSchemeControllerWrapper, FaceWidgetPluginLockManagerWrapper faceWidgetPluginLockManagerWrapper, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, ExternalClockProvider externalClockProvider, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, MediaDataManager mediaDataManager, BootAnimationFinishedCache bootAnimationFinishedCache, KeyguardWallpaper keyguardWallpaper, KeyguardEditModeController keyguardEditModeController, DozeParameters dozeParameters) {
        this.mSysuiContext = context;
        this.mPluginManager = pluginManager;
        this.mPositionAlgorithm = keyguardClockPositionAlgorithm;
        this.mFaceWidgetContainerWrapper = faceWidgetContainerWrapper;
        this.mKeyguardStatusCallbackWrapper = faceWidgetKeyguardStatusCallbackWrapper;
        this.mKeyguardUpdateMonitorWrapper = faceWidgetKeyguardUpdateMonitorWrapper;
        this.mDisplayLifeCycleWrapper = faceWidgetDisplayLifeCycleWrapper;
        this.mWakefullnessLifecycleWrapper = faceWidgetWakefulnessLifecycleWrapper;
        this.mFaceWidgetKnoxStateMonitorWrapper = faceWidgetKnoxStateMonitorWrapper;
        this.mLockPatternUtils = faceWidgetLockPatternUtilsWrapper;
        this.mWallpaperUtilsWrapper = faceWidgetWallpaperUtilsWrapper;
        this.mColorSchemeControllerWrapper = faceWidgetColorSchemeControllerWrapper;
        this.mPluginLockManagerWrapper = faceWidgetPluginLockManagerWrapper;
        this.mNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.mExternalClockProvider = externalClockProvider;
        this.mPluginAODManagerLazy = lazy;
        this.mMediaDataManager = mediaDataManager;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mDozeParameters = dozeParameters;
        this.mFastBioUnlockController = keyguardFastBioUnlockController;
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda1
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                final PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                pluginFaceWidgetManager.mPluginManager.addPluginListener(PluginKeyguardStatusView.ACTION, pluginFaceWidgetManager, PluginKeyguardStatusView.class, false, true, 0);
                try {
                    ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.4
                        public final void onUserSwitchComplete(int i) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("onUserSwitchComplete() ", i, "PluginFaceWidgetManager");
                            PluginFaceWidgetManager pluginFaceWidgetManager2 = PluginFaceWidgetManager.this;
                            pluginFaceWidgetManager2.mPluginManager.removePluginListener(pluginFaceWidgetManager2);
                            pluginFaceWidgetManager2.mPluginManager.addPluginListener(PluginKeyguardStatusView.ACTION, pluginFaceWidgetManager2, PluginKeyguardStatusView.class, false, true, 0);
                        }
                    }, "PluginFaceWidgetManager");
                } catch (RemoteException e) {
                    e.rethrowAsRuntimeException();
                }
            }
        });
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.LOCKUI_SUB_DISPLAY_COVER) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda2
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    PluginKeyguardStatusView pluginKeyguardStatusView = PluginFaceWidgetManager.this.mFaceWidgetPlugin;
                    if (pluginKeyguardStatusView != null) {
                        pluginKeyguardStatusView.onFolderStateChanged(z);
                    }
                }
            }, 5, true);
        }
        this.mKeyguardEditModeController = keyguardEditModeController;
        Log.d("PluginFaceWidgetManager", "PluginFaceWidgetManager() started");
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void applyBlur(float f) {
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean canBeSkipOnWakeAndUnlock() {
        return this.mFastBioUnlockController.isFastWakeAndUnlockMode();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final View getAODClockView(boolean z) {
        PluginAOD pluginAOD = ((PluginAODManager) this.mPluginAODManagerLazy.get()).mAODPlugin;
        if (pluginAOD == null) {
            return null;
        }
        return pluginAOD.getAODClockContainer(z);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final Point getAODZigzagPosition() {
        PluginAODManager pluginAODManager = (PluginAODManager) this.mPluginAODManagerLazy.get();
        PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
        if (pluginAOD != null) {
            pluginAODManager.mZigzagPosition = pluginAOD.getZigzagPosition();
        }
        return pluginAODManager.mZigzagPosition;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int[] getAdaptiveColorResult() {
        return null;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginFaceWidgetColorSchemeController getColorSchemeController() {
        return this.mColorSchemeControllerWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginDisplayLifeCycle getDisplayLifeCycle() {
        return this.mDisplayLifeCycleWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getFloatingShortcutRotation() {
        return 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final String getHomeCityTimeZoneDeviceProvisionedFromPrefs() {
        return Prefs.getString(this.mSysuiContext, "HomecityTimezoneDeviceProvisioned", "");
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getInDisplayFingerprintHeight() {
        return DeviceState.sInDisplayFingerprintHeight;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getInDisplayFingerprintImageSize() {
        return DeviceState.sInDisplayFingerprintImageSize;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginKeyguardStatusCallback getKeyguardStatusCallback() {
        return this.mKeyguardStatusCallbackWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginKeyguardUpdateMonitor getKeyguardUpdateMonitor() {
        return this.mKeyguardUpdateMonitorWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginKnoxStateMonitor getKnoxStateMonitor() {
        return this.mFaceWidgetKnoxStateMonitorWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginLockPatternUtils getLockPatternUtils() {
        return this.mLockPatternUtils;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean getMediaPlayerLastExpandedFromPrefs() {
        return Prefs.getBoolean(this.mSysuiContext, "QsMediaPlayerLastExpanded", true);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getNavigationBarHeight() {
        return this.mSysuiContext.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginNotificationController.Callback getNotificationControllerCallback() {
        return this.mNotificationControllerWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getNotificationPanelViewHeight() {
        NotificationPanelViewController notificationPanelViewController = this.mNPVController;
        if (notificationPanelViewController == null) {
            return 0;
        }
        return notificationPanelViewController.mView.getHeight();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginFaceWidgetLockManager getPluginLockManager() {
        return this.mPluginLockManagerWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getSystemUIPluginVersion() {
        return PluginKeyguardStatusView.VERSION;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginSystemUIWallpaperUtils getWallpaperUtils() {
        return this.mWallpaperUtilsWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean hasAdaptiveColorResult() {
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isBlurSupported() {
        return LsRune.LOCKUI_BLUR;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isCMASSupported() {
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isCapturedBlurSupported() {
        return LsRune.LOCKUI_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isEditMode() {
        StringBuilder sb = new StringBuilder("isEditMode = ");
        KeyguardEditModeController keyguardEditModeController = this.mKeyguardEditModeController;
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode, "PluginFaceWidgetManager");
        return ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isInDisplayFingerprintSupported() {
        if (DeviceType.supportInDisplayFingerprint == -1) {
            DeviceType.supportInDisplayFingerprint = 1;
        }
        return DeviceType.supportInDisplayFingerprint == 1;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isLockScreenDisabled() {
        return this.mLockPatternUtils.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isMultiSimSupported() {
        return DeviceType.isMultiSimSupported();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isNoLockIcon() {
        boolean z;
        NotificationPanelViewController notificationPanelViewController = this.mNPVController;
        if (notificationPanelViewController.mKeyguardStateController.mSecure) {
            if (notificationPanelViewController.mPluginLockViewMode == 0) {
                z = true;
                if (z) {
                    PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) notificationPanelViewController.mPluginLockData;
                    if (pluginLockDataImpl.isAvailable()) {
                        z = pluginLockDataImpl.getVisibility(7) == 0;
                    }
                }
                return !z;
            }
        }
        z = false;
        if (z) {
        }
        return !z;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isOpenThemeSupported() {
        return true;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isPresidentialCMASSupported() {
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isSubDisplay() {
        return DeviceState.isSubDisplay(this.mSysuiContext);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isUIBiometricsSupported() {
        return true;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isWhiteKeyguardWallpaper(String str) {
        return WallpaperUtils.isWhiteKeyguardWallpaper(str);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isWiFiOnlyDevice() {
        return DeviceType.isWiFiOnlyDevice();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void onClockPageTransitionEnded() {
        PluginAODManager pluginAODManager = (PluginAODManager) this.mPluginAODManagerLazy.get();
        if (pluginAODManager.mAODPlugin == null) {
            return;
        }
        pluginAODManager.onTransitionEnded();
        pluginAODManager.mAODPlugin.getFaceWidgetManager().onClockPageTransitionEnded();
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        PluginKeyguardStatusView pluginKeyguardStatusView = (PluginKeyguardStatusView) plugin;
        this.mAppPluginVersion = pluginKeyguardStatusView.getVersion();
        com.android.systemui.keyguard.Log.m138d("PluginFaceWidgetManager", "onPluginConnected() app version = " + this.mAppPluginVersion + ", sysui version = 2014");
        this.mPluginContext = context;
        HandlerC13893 handlerC13893 = this.mUiHandler;
        handlerC13893.sendMessage(handlerC13893.obtainMessage(0, pluginKeyguardStatusView));
        handlerC13893.sendMessage(handlerC13893.obtainMessage(1));
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        KeyguardTouchAnimator keyguardTouchAnimator;
        HandlerC13893 handlerC13893 = this.mUiHandler;
        if (handlerC13893.hasMessages(0)) {
            Log.d("PluginFaceWidgetManager", "Remove 'init plugin wrapper' message");
            handlerC13893.removeMessages(0);
        }
        if (handlerC13893.hasMessages(1)) {
            Log.d("PluginFaceWidgetManager", "Remove 'attach container view' message");
            handlerC13893.removeMessages(1);
        }
        com.android.systemui.keyguard.Log.m138d("PluginFaceWidgetManager", "onPluginDisconnected()");
        if (this.mFaceWidgetPlugin != null && !((PluginManager) Dependency.get(PluginManager.class)).isValidClassLoader(this.mFaceWidgetPlugin.getClass().getClassLoader())) {
            this.mFaceWidgetPlugin.onClassLoaderDiscarded();
        }
        this.mFaceWidgetContainerWrapper.initPlugin(null, null, null);
        this.mWakefullnessLifecycleWrapper.mPluginKeyguardStatusView = null;
        this.mFaceWidgetKnoxStateMonitorWrapper.mPluginKeyguardStatusView = null;
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (keyguardClockPositionAlgorithm instanceof FaceWidgetPositionAlgorithmWrapper) {
            FaceWidgetPositionAlgorithmWrapper faceWidgetPositionAlgorithmWrapper = (FaceWidgetPositionAlgorithmWrapper) keyguardClockPositionAlgorithm;
            faceWidgetPositionAlgorithmWrapper.mPositionAlgorithm = null;
            faceWidgetPositionAlgorithmWrapper.loadDimens(null);
        }
        FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = this.mNotificationControllerWrapper;
        faceWidgetNotificationControllerWrapper.mNotificationController = null;
        faceWidgetNotificationControllerWrapper.mContext = null;
        ExternalClockProvider externalClockProvider = this.mExternalClockProvider;
        if (externalClockProvider.mClockProvider != null) {
            Iterator it = ((ArrayList) externalClockProvider.mClockCallbacks).iterator();
            while (it.hasNext()) {
                PluginClockProvider.ClockCallback clockCallback = (PluginClockProvider.ClockCallback) it.next();
                if (clockCallback != null) {
                    externalClockProvider.mClockProvider.unregisterClockChangedCallback(clockCallback);
                }
            }
            externalClockProvider.mClockProvider = null;
        }
        this.mPluginKeyguardSidePadding = null;
        NotificationPanelViewController notificationPanelViewController = this.mNPVController;
        if (notificationPanelViewController != null && (keyguardTouchAnimator = notificationPanelViewController.mKeyguardTouchAnimator) != null) {
            SparseArray sparseArray = keyguardTouchAnimator.views;
            sparseArray.remove(1);
            sparseArray.remove(3);
            sparseArray.remove(8);
        }
        this.mFaceWidgetPlugin = null;
        this.mAppPluginVersion = 0;
        if (faceWidgetNotificationControllerWrapper.mMediaDataListener == null) {
            faceWidgetNotificationControllerWrapper.mMediaDataListener = faceWidgetNotificationControllerWrapper.new C13851();
        }
        this.mMediaDataManager.mediaDataFilter._listeners.remove(faceWidgetNotificationControllerWrapper.mMediaDataListener);
        ((ArrayList) ((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).listeners).remove(this.mEditModeListener);
        Context context = this.mSysuiContext;
        context.getSharedPreferences(context.getPackageName(), 0).unregisterOnSharedPreferenceChangeListener(this.mSharedPrefListener);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void putHomeCityTimeZoneDeviceProvisionedToPrefs(String str) {
        Prefs.putString(this.mSysuiContext, "HomecityTimezoneDeviceProvisioned", str);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void putHomeCityTimeZoneSetToPrefs(String str) {
        Prefs.putString(this.mSysuiContext, "HomecityTimezoneSet", str);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void putMediaPlayerLastExpandedToPrefs(boolean z) {
        Context context = this.mSysuiContext;
        if (Prefs.getBoolean(context, "QsMediaPlayerLastExpanded", true) == z) {
            return;
        }
        Prefs.putBoolean(context, "QsMediaPlayerLastExpanded", z);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void removeMediaData(List list) {
        MediaDataManager mediaDataManager = this.mMediaDataManager;
        if (mediaDataManager == null || list == null || list.isEmpty()) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!TextUtils.isEmpty(str)) {
                mediaDataManager.onMediaDataRemoved(str);
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void sendEventCDLog(String str, String str2, Map map) {
        SystemUIAnalytics.sendEventCDLog(str, str2, map);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void sendEventLog(String str, String str2) {
        SystemUIAnalytics.sendEventLog(str, str2);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean shouldControlScreenOff() {
        boolean z = this.mDozeParameters.mControlScreenOffAnimation;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("shouldControlScreenOff() : ", z, "PluginFaceWidgetManager");
        return z;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean shouldEnableKeyguardScreenRotation() {
        return DeviceState.shouldEnableKeyguardScreenRotation(this.mSysuiContext);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void updateAnimateScreenOff() {
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void updateFaceWidgetArea() {
        NotificationPanelViewController notificationPanelViewController = this.mNPVController;
        if (notificationPanelViewController != null) {
            notificationPanelViewController.positionClockAndNotifications(false);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void applyBlur(int i) {
        ((KeyguardWallpaperController) this.mKeyguardWallpaper).applyBlur(i);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void sendEventCDLog(String str, String str2, String str3, String str4, String str5, String str6) {
        SystemUIAnalytics.sendEventCDLog(str, str2, str3, str4, str5, str6);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void sendEventLog(String str, String str2, String str3) {
        SystemUIAnalytics.sendEventLog(str, str2, str3);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void updateNIOShortcutFingerPrintVisibility(boolean z) {
    }
}
