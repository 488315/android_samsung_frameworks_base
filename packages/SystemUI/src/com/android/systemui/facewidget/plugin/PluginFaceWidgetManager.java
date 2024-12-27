package com.android.systemui.facewidget.plugin;

import android.R;
import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.audio.soundcraft.SoundCraftNowBarController;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper.AnonymousClass1;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.mediaoutput.MediaOutputController;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.annotations.VersionCheckingProxy;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorScheme;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardSidePadding;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusViewAlphaChangeController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardUpdateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils;
import com.android.systemui.plugins.keyguardstatusview.PluginLockscreenShadeTransitionController;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginSecKeyguardClockPositionAlgorithm;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorGardenModel;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginFaceWidgetManager implements PluginListener, PluginKeyguardStatusView.Callback {
    public int mAppPluginVersion;
    public BatteryMeterViewController mBatteryMeterViewController;
    public BatteryMeterViewController.Factory mBatteryMeterViewControllerFactory;
    public final CapturedBlurContainerController mCapturedBlurContainerController;
    public View mContainerView;
    public final FaceWidgetDisplayLifeCycleWrapper mDisplayLifeCycleWrapper;
    public final DozeParameters mDozeParameters;
    public final ExternalClockProvider mExternalClockProvider;
    public FaceWidgetContainerWrapper mFaceWidgetContainerWrapper;
    public final FaceWidgetKnoxStateMonitorWrapper mFaceWidgetKnoxStateMonitorWrapper;
    public PluginKeyguardStatusView mFaceWidgetPlugin;
    public final KeyguardFastBioUnlockController mFastBioUnlockController;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public boolean mIsConnected;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final FaceWidgetKeyguardStatusCallbackWrapper mKeyguardStatusCallbackWrapper;
    public final KeyguardStatusViewAlphaChangeControllerWrapper mKeyguardStatusViewAlphaChangeControllerWrapper;
    public final FaceWidgetKeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;
    public final FaceWidgetLockPatternUtilsWrapper mLockPatternUtils;
    public final FaceWidgetLockscreenShadeTransitionControllerWrapper mLockscreenShadeTransitionControllerWrapper;
    public final MediaDataManager mMediaDataManager;
    public final MediaDataRepository mMediaDataRepository;
    public MediaOutputController mMediaOutputController;
    public final Provider mMediaOutputControllerProvider;
    public NotificationPanelViewController mNPVController;
    public final FaceWidgetNotificationControllerWrapper mNotificationControllerWrapper;
    public final Lazy mPluginAODManagerLazy;
    public Context mPluginContext;
    public PluginKeyguardSidePadding mPluginKeyguardSidePadding;
    public final FaceWidgetPluginLockManagerWrapper mPluginLockManagerWrapper;
    public final PluginManager mPluginManager;
    public final KeyguardClockPositionAlgorithm mPositionAlgorithm;
    public SoundCraftNowBarController mSoundCraftController;
    public final Provider mSoundCraftControllerProvider;
    public final Context mSysuiContext;
    public final AnonymousClass3 mUiHandler;
    public final FaceWidgetWakefulnessLifecycleWrapper mWakefullnessLifecycleWrapper;
    public final FaceWidgetWallpaperUtilsWrapper mWallpaperUtilsWrapper;
    public String mediaPackageName = "";
    public final AnonymousClass1 mEditModeListener = new KeyguardEditModeController.Listener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.1
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

        @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
        public final void onTouchDownCanceled() {
            PluginKeyguardStatusView pluginKeyguardStatusView = PluginFaceWidgetManager.this.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.onStartingEditModeTouchDownCanceled();
            }
        }

        @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
        public final void onTouchDownStarted() {
            PluginKeyguardStatusView pluginKeyguardStatusView = PluginFaceWidgetManager.this.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.onStartingEditModeTouchDownStarted();
            }
        }
    };
    public final AnonymousClass2 mSharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.2
        @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
        public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
            if (PluginFaceWidgetManager.this.mFaceWidgetPlugin == null || !"QsMediaPlayerLastExpanded".equals(str)) {
                return;
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onSharedPreferenceChanged, key = ", str, "PluginFaceWidgetManager");
            PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
            pluginFaceWidgetManager.mFaceWidgetPlugin.onMediaPlayerLastExpandedPrefChanged(Prefs.getBoolean(pluginFaceWidgetManager.mSysuiContext, "QsMediaPlayerLastExpanded", true));
        }
    };

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$2] */
    /* JADX WARN: Type inference failed for: r3v23, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$3] */
    public PluginFaceWidgetManager(Context context, PluginManager pluginManager, KeyguardFoldController keyguardFoldController, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, FaceWidgetContainerWrapper faceWidgetContainerWrapper, FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper, FaceWidgetKeyguardUpdateMonitorWrapper faceWidgetKeyguardUpdateMonitorWrapper, FaceWidgetDisplayLifeCycleWrapper faceWidgetDisplayLifeCycleWrapper, FaceWidgetWakefulnessLifecycleWrapper faceWidgetWakefulnessLifecycleWrapper, FaceWidgetKnoxStateMonitorWrapper faceWidgetKnoxStateMonitorWrapper, FaceWidgetLockPatternUtilsWrapper faceWidgetLockPatternUtilsWrapper, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, FaceWidgetPluginLockManagerWrapper faceWidgetPluginLockManagerWrapper, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, FaceWidgetLockscreenShadeTransitionControllerWrapper faceWidgetLockscreenShadeTransitionControllerWrapper, ExternalClockProvider externalClockProvider, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, MediaDataManager mediaDataManager, BootAnimationFinishedCache bootAnimationFinishedCache, KeyguardWallpaper keyguardWallpaper, KeyguardEditModeController keyguardEditModeController, DozeParameters dozeParameters, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper, IndicatorGardenPresenter indicatorGardenPresenter, MediaDataRepository mediaDataRepository, Provider provider, Provider provider2, CapturedBlurContainerController capturedBlurContainerController) {
        PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = new PluginFaceWidgetManager$$ExternalSyntheticLambda0(this);
        this.mIsConnected = false;
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
        this.mPluginLockManagerWrapper = faceWidgetPluginLockManagerWrapper;
        this.mNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.mLockscreenShadeTransitionControllerWrapper = faceWidgetLockscreenShadeTransitionControllerWrapper;
        this.mExternalClockProvider = externalClockProvider;
        this.mPluginAODManagerLazy = lazy;
        this.mMediaDataManager = mediaDataManager;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardStatusViewAlphaChangeControllerWrapper = keyguardStatusViewAlphaChangeControllerWrapper;
        this.mMediaDataRepository = mediaDataRepository;
        this.mMediaOutputControllerProvider = provider;
        this.mSoundCraftControllerProvider = provider2;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        this.mUiHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                KeyguardTouchAnimator keyguardTouchAnimator;
                int i = message.what;
                PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                if (i == 0) {
                    Log.d("PluginFaceWidgetManager", "Init Plugin Wrapper started");
                    final PluginKeyguardStatusView pluginKeyguardStatusView = (PluginKeyguardStatusView) message.obj;
                    pluginFaceWidgetManager.getClass();
                    Objects.requireNonNull(pluginKeyguardStatusView);
                    Supplier supplier = new Supplier() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda4
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
                        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm2 = pluginFaceWidgetManager.mPositionAlgorithm;
                        if (keyguardClockPositionAlgorithm2 instanceof FaceWidgetPositionAlgorithmWrapper) {
                            FaceWidgetPositionAlgorithmWrapper faceWidgetPositionAlgorithmWrapper = (FaceWidgetPositionAlgorithmWrapper) keyguardClockPositionAlgorithm2;
                            Context context2 = pluginFaceWidgetManager.mSysuiContext;
                            faceWidgetPositionAlgorithmWrapper.mPositionAlgorithm = (PluginSecKeyguardClockPositionAlgorithm) new VersionCheckingProxy(PluginSecKeyguardClockPositionAlgorithm.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getPositionAlgorithm(), supplier).get();
                            if (context2 != null) {
                                faceWidgetPositionAlgorithmWrapper.loadDimens(context2, context2.getResources());
                            } else {
                                faceWidgetPositionAlgorithmWrapper.loadDimens(null, null);
                            }
                        }
                        pluginFaceWidgetManager.mNotificationControllerWrapper.initPlugin((PluginNotificationController) new VersionCheckingProxy(PluginNotificationController.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getNotificationController(), supplier).get(), pluginFaceWidgetManager.mPluginContext);
                        PluginClockProvider pluginClockProvider = (PluginClockProvider) new VersionCheckingProxy(PluginClockProvider.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getClockProvider(), supplier).get();
                        ExternalClockProvider externalClockProvider2 = pluginFaceWidgetManager.mExternalClockProvider;
                        externalClockProvider2.mClockProvider = pluginClockProvider;
                        Iterator it = ((ArrayList) externalClockProvider2.mClockCallbacks).iterator();
                        while (it.hasNext()) {
                            PluginClockProvider.ClockCallback clockCallback = (PluginClockProvider.ClockCallback) it.next();
                            if (clockCallback != null) {
                                externalClockProvider2.mClockProvider.registerClockChangedCallback(clockCallback);
                            }
                        }
                        pluginFaceWidgetManager.mPluginKeyguardSidePadding = (PluginKeyguardSidePadding) new VersionCheckingProxy(PluginKeyguardSidePadding.class, pluginFaceWidgetManager.mFaceWidgetPlugin.getSecKeyguardSidePadding(), supplier).get();
                        return;
                    }
                    return;
                }
                if (i != 1) {
                    if (i == 2) {
                        Log.i("PluginFaceWidgetManager", "MSG_SHOW_MEDIA_OUTPUT");
                        if (pluginFaceWidgetManager.mMediaOutputController == null) {
                            MediaOutputController mediaOutputController = (MediaOutputController) pluginFaceWidgetManager.mMediaOutputControllerProvider.get();
                            pluginFaceWidgetManager.mMediaOutputController = mediaOutputController;
                            mediaOutputController.listener = new PluginFaceWidgetManager$$ExternalSyntheticLambda0(pluginFaceWidgetManager);
                        }
                        if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                            Log.i("PluginFaceWidgetManager", "createFullNowBar");
                            pluginFaceWidgetManager.mFaceWidgetPlugin.createFullNowBar(pluginFaceWidgetManager.mMediaOutputController.createView(pluginFaceWidgetManager.mediaPackageName));
                            pluginFaceWidgetManager.mNPVController.mMediaOutputDetailShowing = true;
                            return;
                        }
                        return;
                    }
                    if (i != 3) {
                        return;
                    }
                    Log.i("PluginFaceWidgetManager", "MSG_SHOW_BUDS_INFO");
                    if (pluginFaceWidgetManager.mSoundCraftController == null) {
                        SoundCraftNowBarController soundCraftNowBarController = (SoundCraftNowBarController) pluginFaceWidgetManager.mSoundCraftControllerProvider.get();
                        pluginFaceWidgetManager.mSoundCraftController = soundCraftNowBarController;
                        soundCraftNowBarController.listener = new PluginFaceWidgetManager$$ExternalSyntheticLambda0(pluginFaceWidgetManager);
                    }
                    if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                        Log.i("PluginFaceWidgetManager", "createFullNowBar");
                        pluginFaceWidgetManager.mFaceWidgetPlugin.createFullNowBar(pluginFaceWidgetManager.mSoundCraftController.createView());
                        return;
                    }
                    return;
                }
                Log.d("PluginFaceWidgetManager", "Attach container started");
                if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                    NotificationPanelViewController notificationPanelViewController = pluginFaceWidgetManager.mNPVController;
                    int indexOfChild = notificationPanelViewController == null ? 0 : notificationPanelViewController.mView.indexOfChild(notificationPanelViewController.mEditModeContainer) + 1;
                    PluginKeyguardStatusView pluginKeyguardStatusView4 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    NotificationPanelViewController notificationPanelViewController2 = pluginFaceWidgetManager.mNPVController;
                    pluginKeyguardStatusView4.attachFaceWidgetContainer(notificationPanelViewController2 == null ? null : notificationPanelViewController2.mView, pluginFaceWidgetManager.mContainerView, indexOfChild);
                    View containerView = pluginFaceWidgetManager.mFaceWidgetPlugin.getContainerView();
                    pluginFaceWidgetManager.mContainerView = containerView;
                    PluginKeyguardStatusView pluginKeyguardStatusView5 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    List<View> contentsContainers = pluginKeyguardStatusView5.getContentsContainers();
                    FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
                    faceWidgetContainerWrapper2.mPluginKeyguardStatusView = pluginKeyguardStatusView5;
                    faceWidgetContainerWrapper2.mFaceWidgetContainer = containerView;
                    faceWidgetContainerWrapper2.mContentsContainerList = contentsContainers;
                    if (contentsContainers == null || contentsContainers.size() <= 0) {
                        faceWidgetContainerWrapper2.mClockContainer = null;
                    } else {
                        faceWidgetContainerWrapper2.mClockContainer = (View) faceWidgetContainerWrapper2.mContentsContainerList.get(0);
                    }
                    NotificationPanelViewController notificationPanelViewController3 = pluginFaceWidgetManager.mNPVController;
                    if (notificationPanelViewController3 != null && (keyguardTouchAnimator = notificationPanelViewController3.mKeyguardTouchAnimator) != null) {
                        keyguardTouchAnimator.views.remove(1);
                        keyguardTouchAnimator.views.remove(3);
                        keyguardTouchAnimator.views.remove(8);
                        keyguardTouchAnimator.views.remove(12);
                    }
                    pluginFaceWidgetManager.updateFaceWidgetArea();
                    pluginFaceWidgetManager.mIsConnected = true;
                    Lazy lazy2 = pluginFaceWidgetManager.mPluginAODManagerLazy;
                    PluginAODManager pluginAODManager = (PluginAODManager) lazy2.get();
                    List list = pluginAODManager.mConnectionRunnableList;
                    if (list != null) {
                        Iterator it2 = ((ArrayList) list).iterator();
                        while (it2.hasNext()) {
                            ((Runnable) it2.next()).run();
                        }
                        ((ArrayList) pluginAODManager.mConnectionRunnableList).clear();
                    }
                    ((PluginAODManager) lazy2.get()).mPluginAODStateProvider = pluginFaceWidgetManager.mFaceWidgetPlugin.getAODStateProvider();
                    FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper2 = pluginFaceWidgetManager.mNotificationControllerWrapper;
                    if (faceWidgetNotificationControllerWrapper2.mMediaDataListener == null) {
                        faceWidgetNotificationControllerWrapper2.mMediaDataListener = faceWidgetNotificationControllerWrapper2.new AnonymousClass1();
                    }
                    final FaceWidgetNotificationControllerWrapper.AnonymousClass1 anonymousClass1 = faceWidgetNotificationControllerWrapper2.mMediaDataListener;
                    pluginFaceWidgetManager.mMediaDataManager.addListener(anonymousClass1);
                    Map map = (Map) pluginFaceWidgetManager.mMediaDataRepository.mediaEntries.$$delegate_0.getValue();
                    if (!map.isEmpty()) {
                        map.forEach(new BiConsumer() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                MediaDataManager.Listener.this.onMediaDataLoaded((String) obj, "", (MediaData) obj2, false, 0, false);
                            }
                        });
                    }
                    ((ArrayList) ((KeyguardEditModeControllerImpl) pluginFaceWidgetManager.mKeyguardEditModeController).listeners).add(pluginFaceWidgetManager.mEditModeListener);
                    Prefs.get(pluginFaceWidgetManager.mSysuiContext).registerOnSharedPreferenceChangeListener(pluginFaceWidgetManager.mSharedPrefListener);
                    PluginKeyguardStatusView pluginKeyguardStatusView6 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    IndicatorGardenModel indicatorGardenModel = pluginFaceWidgetManager.mIndicatorGardenPresenter.cachedGardenModel;
                    pluginKeyguardStatusView6.onIndicatorGardenUpdated(indicatorGardenModel.paddingLeft, indicatorGardenModel.paddingRight, indicatorGardenModel.totalHeight, indicatorGardenModel.cameraTopMargin);
                }
            }
        };
        this.mFastBioUnlockController = keyguardFastBioUnlockController;
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda1
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                final PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                pluginFaceWidgetManager.getClass();
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    Log.i("PluginFaceWidgetManager", "Do not  initPluginModule in safe mode");
                } else {
                    pluginFaceWidgetManager.mPluginManager.addPluginListener(PluginKeyguardStatusView.ACTION, pluginFaceWidgetManager, PluginKeyguardStatusView.class, false, true, 0);
                }
                try {
                    ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.4
                        public final void onUserSwitchComplete(int i) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitchComplete() ", "PluginFaceWidgetManager");
                            PluginFaceWidgetManager pluginFaceWidgetManager2 = PluginFaceWidgetManager.this;
                            pluginFaceWidgetManager2.mPluginManager.removePluginListener(pluginFaceWidgetManager2);
                            if (SafeUIState.isSysUiSafeModeEnabled()) {
                                Log.i("PluginFaceWidgetManager", "Do not  initPluginModule in safe mode");
                            } else {
                                pluginFaceWidgetManager2.mPluginManager.addPluginListener(PluginKeyguardStatusView.ACTION, pluginFaceWidgetManager2, PluginKeyguardStatusView.class, false, true, 0);
                            }
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
        if (LsRune.SECURITY_CAPTURED_BLUR) {
            this.mCapturedBlurContainerController = capturedBlurContainerController;
        }
        synchronized (indicatorGardenPresenter.listeners) {
            ((ArrayList) indicatorGardenPresenter.listeners).add(pluginFaceWidgetManager$$ExternalSyntheticLambda0);
        }
        IndicatorGardenModel indicatorGardenModel = indicatorGardenPresenter.cachedGardenModel;
        PluginKeyguardStatusView pluginKeyguardStatusView = this.mFaceWidgetPlugin;
        if (pluginKeyguardStatusView != null) {
            pluginKeyguardStatusView.onIndicatorGardenUpdated(indicatorGardenModel.paddingLeft, indicatorGardenModel.paddingRight, indicatorGardenModel.totalHeight, indicatorGardenModel.cameraTopMargin);
        }
        Log.d("PluginFaceWidgetManager", "PluginFaceWidgetManager() started");
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void applyBlur(int i) {
        CapturedBlurContainerController capturedBlurContainerController;
        if (!LsRune.SECURITY_CAPTURED_BLUR || (capturedBlurContainerController = this.mCapturedBlurContainerController) == null) {
            return;
        }
        boolean z = i > 0;
        if (capturedBlurContainerController.mIsFaceWidgetShowing != z) {
            capturedBlurContainerController.mIsFaceWidgetShowing = z;
            capturedBlurContainerController.updateContainerVisibility();
        }
        capturedBlurContainerController.setAlpha(i, CapturedBlurContainerController.BlurType.FACE_WIDGET);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean canBeSkipOnWakeAndUnlock() {
        return this.mFastBioUnlockController.isFastWakeAndUnlockMode();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final View createBatteryMeterView() {
        BatteryMeterView batteryMeterView = new BatteryMeterView(this.mSysuiContext, null);
        BatteryMeterViewController create = this.mBatteryMeterViewControllerFactory.create(batteryMeterView, StatusBarLocation.AOD);
        this.mBatteryMeterViewController = create;
        create.init();
        this.mBatteryMeterViewController.setAodScaleFactor();
        return batteryMeterView;
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
    public final PluginDisplayLifeCycle getDisplayLifeCycle() {
        return this.mDisplayLifeCycleWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getFloatingShortcutRotation() {
        return 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final String getHomeCityTimeZoneDeviceProvisionedFromPrefs() {
        return Prefs.get(this.mSysuiContext).getString("HomecityTimezoneDeviceProvisioned", "");
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getInDisplayFingerprintHeight() {
        return DeviceState.getInDisplayFingerprintHeight();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getInDisplayFingerprintImageSize() {
        return DeviceState.getInDisplayFingerprintImageSize();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginKeyguardStatusCallback getKeyguardStatusCallback() {
        return this.mKeyguardStatusCallbackWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final PluginKeyguardStatusViewAlphaChangeController getKeyguardStatusViewAlphaChangeController() {
        return this.mKeyguardStatusViewAlphaChangeControllerWrapper;
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
    public final PluginLockscreenShadeTransitionController getLockscreenShadeTransitionController() {
        return this.mLockscreenShadeTransitionControllerWrapper;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean getMediaPlayerLastExpandedFromPrefs() {
        return Prefs.getBoolean(this.mSysuiContext, "QsMediaPlayerLastExpanded", true);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final int getNavigationBarHeight() {
        return this.mSysuiContext.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height);
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
    public final PluginFaceWidgetColorScheme getPluginFaceWidgetColorScheme(WallpaperColors wallpaperColors, int i) {
        ColorScheme colorScheme = new ColorScheme(wallpaperColors, true, i == 0 ? Style.VIBRANT : Style.CONTENT);
        TonalPalette tonalPalette = colorScheme.mAccent2;
        int s800 = tonalPalette.getS800();
        TonalPalette tonalPalette2 = colorScheme.mAccent1;
        return new PluginFaceWidgetColorScheme(s800, tonalPalette2.getS100(), tonalPalette2.getS200(), tonalPalette.getS700(), tonalPalette2.getS700());
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
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode, "PluginFaceWidgetManager");
        return ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isInDisplayFingerprintSupported() {
        return DeviceType.isInDisplayFingerprintSupported();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isLockScreenDisabled() {
        return this.mLockPatternUtils.mUpdateMonitor.isLockscreenDisabled();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isMultiSimSupported() {
        return DeviceType.isMultiSimSupported();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean isNoLockIcon() {
        NotificationPanelViewController notificationPanelViewController = this.mNPVController;
        boolean z = notificationPanelViewController.mKeyguardStateController.mSecure && notificationPanelViewController.mPluginLockViewMode == 0;
        if (z) {
            PluginLockData pluginLockData = notificationPanelViewController.mPluginLockData;
            if (pluginLockData.isAvailable()) {
                z = pluginLockData.getVisibility(7) == 0;
            }
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
        com.android.systemui.keyguard.Log.d("PluginFaceWidgetManager", "onPluginConnected() app version = " + this.mAppPluginVersion + ", sysui version = 3024");
        this.mPluginContext = context;
        AnonymousClass3 anonymousClass3 = this.mUiHandler;
        anonymousClass3.sendMessage(anonymousClass3.obtainMessage(0, pluginKeyguardStatusView));
        anonymousClass3.sendMessage(anonymousClass3.obtainMessage(1));
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        KeyguardTouchAnimator keyguardTouchAnimator;
        AnonymousClass3 anonymousClass3 = this.mUiHandler;
        if (anonymousClass3.hasMessages(0)) {
            Log.d("PluginFaceWidgetManager", "Remove 'init plugin wrapper' message");
            anonymousClass3.removeMessages(0);
        }
        if (anonymousClass3.hasMessages(1)) {
            Log.d("PluginFaceWidgetManager", "Remove 'attach container view' message");
            anonymousClass3.removeMessages(1);
        }
        com.android.systemui.keyguard.Log.d("PluginFaceWidgetManager", "onPluginDisconnected()");
        if (this.mFaceWidgetPlugin != null && !((PluginManager) Dependency.sDependency.getDependencyInner(PluginManager.class)).isValidClassLoader(this.mFaceWidgetPlugin.getClass().getClassLoader())) {
            this.mFaceWidgetPlugin.onClassLoaderDiscarded();
        }
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mFaceWidgetContainerWrapper;
        faceWidgetContainerWrapper.mPluginKeyguardStatusView = null;
        faceWidgetContainerWrapper.mFaceWidgetContainer = null;
        faceWidgetContainerWrapper.mContentsContainerList = null;
        faceWidgetContainerWrapper.mClockContainer = null;
        this.mWakefullnessLifecycleWrapper.mPluginKeyguardStatusView = null;
        this.mFaceWidgetKnoxStateMonitorWrapper.mPluginKeyguardStatusView = null;
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mPositionAlgorithm;
        if (keyguardClockPositionAlgorithm instanceof FaceWidgetPositionAlgorithmWrapper) {
            FaceWidgetPositionAlgorithmWrapper faceWidgetPositionAlgorithmWrapper = (FaceWidgetPositionAlgorithmWrapper) keyguardClockPositionAlgorithm;
            faceWidgetPositionAlgorithmWrapper.mPositionAlgorithm = null;
            faceWidgetPositionAlgorithmWrapper.loadDimens(null, null);
        }
        FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = this.mNotificationControllerWrapper;
        faceWidgetNotificationControllerWrapper.initPlugin(null, null);
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
            keyguardTouchAnimator.views.remove(1);
            keyguardTouchAnimator.views.remove(3);
            keyguardTouchAnimator.views.remove(8);
            keyguardTouchAnimator.views.remove(12);
        }
        this.mFaceWidgetPlugin = null;
        this.mIsConnected = false;
        this.mAppPluginVersion = 0;
        MediaDataManager mediaDataManager = this.mMediaDataManager;
        if (faceWidgetNotificationControllerWrapper.mMediaDataListener == null) {
            faceWidgetNotificationControllerWrapper.mMediaDataListener = faceWidgetNotificationControllerWrapper.new AnonymousClass1();
        }
        mediaDataManager.removeListener(faceWidgetNotificationControllerWrapper.mMediaDataListener);
        ((ArrayList) ((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).listeners).remove(this.mEditModeListener);
        Prefs.get(this.mSysuiContext).unregisterOnSharedPreferenceChangeListener(this.mSharedPrefListener);
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
        if (Prefs.getBoolean(this.mSysuiContext, "QsMediaPlayerLastExpanded", true) == z) {
            return;
        }
        Prefs.putBoolean(this.mSysuiContext, "QsMediaPlayerLastExpanded", z);
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
                mediaDataManager.dismissMediaData(str, 0L, true);
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
        this.mNPVController.mMediaOutputDetailShowing = false;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("shouldControlScreenOff() : ", "PluginFaceWidgetManager", z);
        return z;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean shouldEnableKeyguardScreenRotation() {
        return DeviceState.shouldEnableKeyguardScreenRotation(this.mSysuiContext);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void showBudsInfo() {
        AnonymousClass3 anonymousClass3 = this.mUiHandler;
        if (anonymousClass3.hasMessages(3)) {
            Log.d("PluginFaceWidgetManager", "Remove 'MSG_SHOW_BUDS_INFO' message");
            anonymousClass3.removeMessages(3);
        }
        anonymousClass3.sendMessage(anonymousClass3.obtainMessage(3));
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void showMediaOutput(String str) {
        this.mediaPackageName = str;
        AnonymousClass3 anonymousClass3 = this.mUiHandler;
        if (anonymousClass3.hasMessages(2)) {
            Log.d("PluginFaceWidgetManager", "Remove 'MSG_SHOW_MEDIA_OUTPUT' message");
            anonymousClass3.removeMessages(2);
        }
        anonymousClass3.sendMessage(anonymousClass3.obtainMessage(2));
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
    public final void sendEventCDLog(String str, String str2, String str3, String str4, String str5, String str6) {
        SystemUIAnalytics.sendEventCDLog(str, str2, str3, str4, str5, str6);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void sendEventLog(String str, String str2, String str3) {
        SystemUIAnalytics.sendEventLog(str, str2, str3);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void applyBlur(float f) {
        applyBlur((int) (f * 255.0f));
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void updateNIOShortcutFingerPrintVisibility(boolean z) {
    }
}
