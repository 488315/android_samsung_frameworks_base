package com.android.systemui.facewidget.plugin;

import android.R;
import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.audio.soundcraft.SoundCraftNowBarController;
import com.android.systemui.audio.soundcraft.SoundCraftNowBarView;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.blur.di.SecPanelCapturedBlurBinding;
import com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper.AnonymousClass1;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda4;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.media.MediaOutputView;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.mediaoutput.MediaOutputController;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.monet.ColorScheme;
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
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutModel;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PluginFaceWidgetManager implements PluginListener, PluginKeyguardStatusView.Callback {
    public int mAppPluginVersion;
    public BatteryMeterViewController mBatteryMeterViewController;
    public BatteryMeterViewController.Factory mBatteryMeterViewControllerFactory;
    public final AnonymousClass3 mConfigurationListener;
    public View mContainerView;
    public final FaceWidgetDisplayLifeCycleWrapper mDisplayLifeCycleWrapper;
    public final DozeParameters mDozeParameters;
    public final ExternalClockProvider mExternalClockProvider;
    public FaceWidgetContainerWrapper mFaceWidgetContainerWrapper;
    public final FaceWidgetKnoxStateMonitorWrapper mFaceWidgetKnoxStateMonitorWrapper;
    public PluginKeyguardStatusView mFaceWidgetPlugin;
    public final KeyguardFastBioUnlockController mFastBioUnlockController;
    public boolean mIsConnected;
    public boolean mIsReconnected;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardStatusBarNioLayoutRepository mKeyguardStatusBarNioLayoutRepository;
    public final FaceWidgetKeyguardStatusCallbackWrapper mKeyguardStatusCallbackWrapper;
    public final KeyguardStatusViewAlphaChangeControllerWrapper mKeyguardStatusViewAlphaChangeControllerWrapper;
    public final FaceWidgetKeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;
    public final FaceWidgetLockPatternUtilsWrapper mLockPatternUtils;
    public final FaceWidgetLockscreenShadeTransitionControllerWrapper mLockscreenShadeTransitionControllerWrapper;
    public AnonymousClass4 mMediaDataListener;
    public final MediaDataManager mMediaDataManager;
    public final Map mMediaDataMap;
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
    public final SecQpBlurController mSecQpBlurController;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final Provider mSettingsHelperProvider;
    public SoundCraftNowBarController mSoundCraftController;
    public final Provider mSoundCraftControllerProvider;
    public final Context mSysuiContext;
    public final AnonymousClass5 mUiHandler;
    public final FaceWidgetWakefulnessLifecycleWrapper mWakefullnessLifecycleWrapper;
    public final FaceWidgetWallpaperUtilsWrapper mWallpaperUtilsWrapper;
    public String mediaPackageName = "";
    public int mPreviousPaddingLeft = 0;
    public int mPreviousPaddingRight = 0;
    public int mPreviousTotalHeight = 0;
    public int mPreviousTopMargin = 0;
    public int mPreviousBottomMargin = 0;
    public int mPreviousContainerStartX = 0;
    public int mPreviousContainerEndX = 0;
    public int mPreviousIconSize = 0;
    public float mPreviousIconScaleRatio = 0.0f;
    public float mPreviousAlpha = 0.0f;
    public int mPreviousVisibility = 0;
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$4] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$2] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$5] */
    public PluginFaceWidgetManager(Context context, PluginManager pluginManager, KeyguardFoldController keyguardFoldController, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, FaceWidgetContainerWrapper faceWidgetContainerWrapper, FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper, FaceWidgetKeyguardUpdateMonitorWrapper faceWidgetKeyguardUpdateMonitorWrapper, FaceWidgetDisplayLifeCycleWrapper faceWidgetDisplayLifeCycleWrapper, FaceWidgetWakefulnessLifecycleWrapper faceWidgetWakefulnessLifecycleWrapper, FaceWidgetKnoxStateMonitorWrapper faceWidgetKnoxStateMonitorWrapper, FaceWidgetLockPatternUtilsWrapper faceWidgetLockPatternUtilsWrapper, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, FaceWidgetPluginLockManagerWrapper faceWidgetPluginLockManagerWrapper, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, FaceWidgetLockscreenShadeTransitionControllerWrapper faceWidgetLockscreenShadeTransitionControllerWrapper, ExternalClockProvider externalClockProvider, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, MediaDataManager mediaDataManager, BootAnimationFinishedCache bootAnimationFinishedCache, KeyguardWallpaper keyguardWallpaper, KeyguardEditModeController keyguardEditModeController, DozeParameters dozeParameters, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper, IndicatorGardenPresenter indicatorGardenPresenter, MediaDataRepository mediaDataRepository, Provider provider, Provider provider2, SecQpBlurController secQpBlurController, ConfigurationController configurationController, KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository, Provider provider3, JavaAdapter javaAdapter, SelectedUserInteractor selectedUserInteractor, KeyguardInteractor keyguardInteractor) {
        ?? r2 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                Context context2 = pluginFaceWidgetManager.mPluginContext;
                if (context2 == null || pluginFaceWidgetManager.mSysuiContext == null) {
                    return;
                }
                int hashCode = context2.getResources().getConfiguration().hashCode();
                int hashCode2 = pluginFaceWidgetManager.mSysuiContext.getResources().getConfiguration().hashCode();
                if (hashCode != hashCode2) {
                    Log.w("PluginFaceWidgetManager", MutableVectorKt$$ExternalSyntheticOutline0.m(hashCode, hashCode2, "onConfigurationChanged: changed [", " -> ", "]"));
                    if (((SettingsHelper) pluginFaceWidgetManager.mSettingsHelperProvider.get()).isLockScreenRotationAllowed()) {
                        Log.w("PluginFaceWidgetManager", "onConfigurationChanged: try plugin connect again");
                        pluginFaceWidgetManager.reconnectPluginModule();
                        return;
                    }
                }
                if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                    Log.d("PluginFaceWidgetManager", "onConfigurationChanged: ");
                    pluginFaceWidgetManager.mFaceWidgetPlugin.onConfigurationChanged(configuration);
                }
            }
        };
        this.mConfigurationListener = r2;
        PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = new PluginFaceWidgetManager$$ExternalSyntheticLambda0(this);
        this.mIsConnected = false;
        this.mIsReconnected = false;
        this.mMediaDataMap = new HashMap();
        this.mMediaDataListener = new MediaDataManager.Listener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.4
            @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
            public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
                PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                if (str2 != null) {
                    ((HashMap) pluginFaceWidgetManager.mMediaDataMap).remove(str2);
                }
                ((HashMap) pluginFaceWidgetManager.mMediaDataMap).put(str, mediaData);
            }

            @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
            public final void onMediaDataRemoved(String str, boolean z) {
                PluginFaceWidgetManager.this.mMediaDataMap.remove(str);
            }
        };
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
        this.mMediaOutputControllerProvider = provider;
        this.mSoundCraftControllerProvider = provider2;
        this.mKeyguardStatusBarNioLayoutRepository = keyguardStatusBarNioLayoutRepository;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mUiHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.5
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                KeyguardTouchAnimator keyguardTouchAnimator;
                int i = message.what;
                int i2 = 0;
                final PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                if (i == 0) {
                    Log.d("PluginFaceWidgetManager", "Init Plugin Wrapper started");
                    final PluginKeyguardStatusView pluginKeyguardStatusView = (PluginKeyguardStatusView) message.obj;
                    pluginFaceWidgetManager.getClass();
                    Objects.requireNonNull(pluginKeyguardStatusView);
                    Supplier supplier = new Supplier() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda6
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
                        ArrayList arrayList = (ArrayList) externalClockProvider2.mClockCallbacks;
                        int size = arrayList.size();
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            PluginClockProvider.ClockCallback clockCallback = (PluginClockProvider.ClockCallback) obj;
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
                    if (i != 2) {
                        if (i != 3) {
                            return;
                        }
                        Log.i("PluginFaceWidgetManager", "MSG_SHOW_BUDS_INFO");
                        if (pluginFaceWidgetManager.mSoundCraftController == null) {
                            SoundCraftNowBarController soundCraftNowBarController = (SoundCraftNowBarController) pluginFaceWidgetManager.mSoundCraftControllerProvider.get();
                            pluginFaceWidgetManager.mSoundCraftController = soundCraftNowBarController;
                            soundCraftNowBarController.listener = new PluginFaceWidgetManager$$ExternalSyntheticLambda4(pluginFaceWidgetManager);
                        }
                        if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                            Log.i("PluginFaceWidgetManager", "createFullNowBar");
                            PluginKeyguardStatusView pluginKeyguardStatusView4 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                            SoundCraftNowBarController soundCraftNowBarController2 = pluginFaceWidgetManager.mSoundCraftController;
                            soundCraftNowBarController2.getClass();
                            SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING, SoundCraftSALogging.Event.SHOW, null, 12);
                            Object obj2 = soundCraftNowBarController2.soundCraftNowBarViewProvider.get();
                            ((SoundCraftNowBarView) obj2).getClass();
                            pluginKeyguardStatusView4.createFullNowBar((View) obj2);
                            return;
                        }
                        return;
                    }
                    Log.i("PluginFaceWidgetManager", "MSG_SHOW_MEDIA_OUTPUT");
                    if (pluginFaceWidgetManager.mMediaOutputController == null) {
                        MediaOutputController mediaOutputController = (MediaOutputController) pluginFaceWidgetManager.mMediaOutputControllerProvider.get();
                        pluginFaceWidgetManager.mMediaOutputController = mediaOutputController;
                        mediaOutputController.listener = new PluginFaceWidgetManager$$ExternalSyntheticLambda4(pluginFaceWidgetManager);
                    }
                    if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                        Log.i("PluginFaceWidgetManager", "createFullNowBar");
                        PluginKeyguardStatusView pluginKeyguardStatusView5 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                        final MediaOutputController mediaOutputController2 = pluginFaceWidgetManager.mMediaOutputController;
                        String str = pluginFaceWidgetManager.mediaPackageName;
                        Object obj3 = mediaOutputController2.mediaOutputViewProvider.get();
                        Feature.Builder builder = new Feature.Builder();
                        builder.getFeature().packageName = str;
                        builder.getFeature().from = 10;
                        builder.getFeature().dismissCallback = new Function0() { // from class: com.android.systemui.media.mediaoutput.MediaOutputController$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                PluginFaceWidgetManager$$ExternalSyntheticLambda4 pluginFaceWidgetManager$$ExternalSyntheticLambda4 = MediaOutputController.this.listener;
                                if (pluginFaceWidgetManager$$ExternalSyntheticLambda4 != null) {
                                    StringBuilder sb = new StringBuilder("destroyFullNowBar mFaceWidgetPlugin");
                                    PluginFaceWidgetManager pluginFaceWidgetManager2 = pluginFaceWidgetManager$$ExternalSyntheticLambda4.f$0;
                                    sb.append(pluginFaceWidgetManager2.mFaceWidgetPlugin);
                                    Log.i("PluginFaceWidgetManager", sb.toString());
                                    if (pluginFaceWidgetManager2.mFaceWidgetPlugin != null) {
                                        Log.i("PluginFaceWidgetManager", "destroyFullNowBar");
                                        pluginFaceWidgetManager2.mFaceWidgetPlugin.destroyFullNowBar();
                                        pluginFaceWidgetManager2.mNPVController.mMediaOutputDetailShowing = false;
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        builder.getFeature().anchorViewTag = "tag_facewidget_music_container_small_expand";
                        ((MediaOutputView) obj3).feature = builder.getFeature();
                        pluginKeyguardStatusView5.createFullNowBar((View) obj3);
                        pluginFaceWidgetManager.mNPVController.mMediaOutputDetailShowing = true;
                        return;
                    }
                    return;
                }
                Log.d("PluginFaceWidgetManager", "Attach container started");
                if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                    NotificationPanelViewController notificationPanelViewController = pluginFaceWidgetManager.mNPVController;
                    int indexOfChild = notificationPanelViewController == null ? 0 : notificationPanelViewController.mView.indexOfChild(notificationPanelViewController.mEditModeContainer) + 1;
                    PluginKeyguardStatusView pluginKeyguardStatusView6 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    NotificationPanelViewController notificationPanelViewController2 = pluginFaceWidgetManager.mNPVController;
                    pluginKeyguardStatusView6.attachFaceWidgetContainer(notificationPanelViewController2 != null ? notificationPanelViewController2.mView : null, pluginFaceWidgetManager.mContainerView, indexOfChild);
                    View containerView = pluginFaceWidgetManager.mFaceWidgetPlugin.getContainerView();
                    pluginFaceWidgetManager.mContainerView = containerView;
                    PluginKeyguardStatusView pluginKeyguardStatusView7 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    pluginFaceWidgetManager.mFaceWidgetContainerWrapper.initPlugin(pluginKeyguardStatusView7, containerView, pluginKeyguardStatusView7.getContentsContainers());
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
                        ArrayList arrayList2 = (ArrayList) list;
                        int size2 = arrayList2.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            Object obj4 = arrayList2.get(i3);
                            i3++;
                            ((Runnable) obj4).run();
                        }
                        ((ArrayList) pluginAODManager.mConnectionRunnableList).clear();
                    }
                    ((PluginAODManager) lazy2.get()).mPluginAODStateProvider = pluginFaceWidgetManager.mFaceWidgetPlugin.getAODStateProvider();
                    FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper2 = pluginFaceWidgetManager.mNotificationControllerWrapper;
                    if (faceWidgetNotificationControllerWrapper2.mMediaDataListener == null) {
                        faceWidgetNotificationControllerWrapper2.mMediaDataListener = faceWidgetNotificationControllerWrapper2.new AnonymousClass1();
                    }
                    final FaceWidgetNotificationControllerWrapper.AnonymousClass1 anonymousClass1 = faceWidgetNotificationControllerWrapper2.mMediaDataListener;
                    MediaDataManager mediaDataManager2 = pluginFaceWidgetManager.mMediaDataManager;
                    mediaDataManager2.addListener(anonymousClass1);
                    if (pluginFaceWidgetManager.mIsReconnected) {
                        if (!((HashMap) pluginFaceWidgetManager.mMediaDataMap).isEmpty()) {
                            ((HashMap) pluginFaceWidgetManager.mMediaDataMap).forEach(new BiConsumer() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda5
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj5, Object obj6) {
                                    PluginFaceWidgetManager pluginFaceWidgetManager2 = PluginFaceWidgetManager.this;
                                    MediaDataManager.Listener listener = anonymousClass1;
                                    String str2 = (String) obj5;
                                    MediaData mediaData = (MediaData) obj6;
                                    if (pluginFaceWidgetManager2.mSelectedUserInteractor.getSelectedUserId() == mediaData.userId) {
                                        listener.onMediaDataLoaded(str2, "", mediaData, false);
                                    }
                                }
                            });
                        }
                        mediaDataManager2.removeListener(pluginFaceWidgetManager.mMediaDataListener);
                        ((HashMap) pluginFaceWidgetManager.mMediaDataMap).clear();
                    }
                    pluginFaceWidgetManager.mIsReconnected = false;
                    PluginAODManager pluginAODManager2 = (PluginAODManager) lazy2.get();
                    PluginKeyguardStatusView pluginKeyguardStatusView8 = pluginFaceWidgetManager.mFaceWidgetPlugin;
                    if (pluginKeyguardStatusView8 == null) {
                        pluginAODManager2.getClass();
                    } else {
                        pluginKeyguardStatusView8.setDozing(pluginAODManager2.mDozing);
                        pluginKeyguardStatusView8.setDarkAmount(pluginAODManager2.mStatusBarStateController.getDozeAmount());
                    }
                    ((ArrayList) ((KeyguardEditModeControllerImpl) pluginFaceWidgetManager.mKeyguardEditModeController).listeners).add(pluginFaceWidgetManager.mEditModeListener);
                    Prefs.get(pluginFaceWidgetManager.mSysuiContext).registerOnSharedPreferenceChangeListener(pluginFaceWidgetManager.mSharedPrefListener);
                    KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = pluginFaceWidgetManager.mKeyguardStatusBarNioLayoutRepository.nioLayoutModel;
                    try {
                        pluginFaceWidgetManager.mFaceWidgetPlugin.onNioLayoutUpdated(keyguardStatusBarNioLayoutModel.paddingLeft, keyguardStatusBarNioLayoutModel.paddingRight, keyguardStatusBarNioLayoutModel.totalHeight, keyguardStatusBarNioLayoutModel.topMargin, keyguardStatusBarNioLayoutModel.bottomMargin, keyguardStatusBarNioLayoutModel.containerStartX, keyguardStatusBarNioLayoutModel.containerEndX, keyguardStatusBarNioLayoutModel.iconSize, keyguardStatusBarNioLayoutModel.iconScaleRatio);
                    } catch (Throwable unused) {
                        Log.e("PluginFaceWidgetManager", "onNioLayoutUpdated: NoSuchMethodError");
                    }
                }
            }
        };
        this.mFastBioUnlockController = keyguardFastBioUnlockController;
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda1
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                final PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    Log.i("PluginFaceWidgetManager", "Do not  initPluginModule in safe mode");
                } else {
                    pluginFaceWidgetManager.mPluginManager.addPluginListener(PluginKeyguardStatusView.ACTION, pluginFaceWidgetManager, PluginKeyguardStatusView.class, false, true, 0);
                }
                try {
                    ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager.6
                        public final void onUserSwitchComplete(int i) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitchComplete() ", "PluginFaceWidgetManager");
                            PluginFaceWidgetManager.this.reconnectPluginModule();
                        }
                    }, "PluginFaceWidgetManager");
                } catch (RemoteException e) {
                    e.rethrowAsRuntimeException();
                }
            }
        });
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.LOCKUI_SUB_DISPLAY_COVER) {
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new PluginFaceWidgetManager$$ExternalSyntheticLambda4(this), 5, true);
        }
        this.mKeyguardEditModeController = keyguardEditModeController;
        if (LsRune.SECURITY_CAPTURED_BLUR) {
            this.mSecQpBlurController = secQpBlurController;
        }
        keyguardStatusBarNioLayoutRepository.printLog("addNioLayoutCallback(" + pluginFaceWidgetManager$$ExternalSyntheticLambda0 + ") size[" + ((ArrayList) keyguardStatusBarNioLayoutRepository.listeners).size() + "]  ");
        synchronized (keyguardStatusBarNioLayoutRepository.listeners) {
            ((ArrayList) keyguardStatusBarNioLayoutRepository.listeners).add(pluginFaceWidgetManager$$ExternalSyntheticLambda0);
        }
        pluginFaceWidgetManager$$ExternalSyntheticLambda0.onNioLayoutUpdated(keyguardStatusBarNioLayoutRepository.nioLayoutModel);
        ((ConfigurationControllerImpl) configurationController).addCallback(r2);
        this.mSettingsHelperProvider = provider3;
        javaAdapter.alwaysCollectFlow(keyguardInteractor.dozeTimeTick, new Consumer() { // from class: com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PluginFaceWidgetManager pluginFaceWidgetManager = PluginFaceWidgetManager.this;
                pluginFaceWidgetManager.getClass();
                Log.d("PluginFaceWidgetManager", "dozeTimeTick: time=" + ((Long) obj));
                PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.dozeTimeTick();
                }
            }
        });
        Log.d("PluginFaceWidgetManager", "PluginFaceWidgetManager() started");
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void applyBlur(int i) {
        SecQpBlurController secQpBlurController;
        if (!LsRune.SECURITY_CAPTURED_BLUR || (secQpBlurController = this.mSecQpBlurController) == null) {
            return;
        }
        ((SecCapturedBlurRepositoryImpl) ((SecCapturedBlurContainerBinder) ((SecPanelCapturedBlurBinding) secQpBlurController.panelBlurBinding)).secCapturedBlurInteractor.secCapturedBlurRepository)._fullScreenBlurShowing.updateState(null, Boolean.valueOf(i > 0));
        secQpBlurController.doBlur(i, SecPanelBlurBinding.BlurType.FULL_SCREEN);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean canBeSkipOnWakeAndUnlock() {
        return this.mFastBioUnlockController.isFastWakeAndUnlockMode();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final View createBatteryMeterView() {
        BatteryMeterView batteryMeterView = new BatteryMeterView(this.mSysuiContext, null);
        batteryMeterView.setTag("PluginFaceWidgetManager");
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
        return this.mSysuiContext.getResources().getDimensionPixelSize(R.dimen.seekbar_track_progress_height_material);
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
        ColorScheme colorScheme = new ColorScheme(wallpaperColors, true, i == 0 ? 2 : 6);
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
        boolean z = false;
        boolean z2 = notificationPanelViewController.mKeyguardStateController.mSecure && notificationPanelViewController.mPluginLockViewMode == 0;
        if (z2) {
            PluginLockData pluginLockData = notificationPanelViewController.mPluginLockData;
            if (pluginLockData.isAvailable()) {
                if (pluginLockData.getVisibility(7) == 0) {
                    z = true;
                }
                return !z;
            }
        }
        z = z2;
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
        com.android.systemui.keyguard.Log.d("PluginFaceWidgetManager", "onPluginConnected() app version = " + this.mAppPluginVersion + ", sysui version = 4006, config=" + context.getResources().getConfiguration().hashCode());
        this.mPluginContext = context;
        AnonymousClass5 anonymousClass5 = this.mUiHandler;
        anonymousClass5.sendMessage(anonymousClass5.obtainMessage(0, pluginKeyguardStatusView));
        anonymousClass5.sendMessage(anonymousClass5.obtainMessage(1));
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        KeyguardTouchAnimator keyguardTouchAnimator;
        AnonymousClass5 anonymousClass5 = this.mUiHandler;
        if (anonymousClass5.hasMessages(0)) {
            Log.d("PluginFaceWidgetManager", "Remove 'init plugin wrapper' message");
            anonymousClass5.removeMessages(0);
        }
        if (anonymousClass5.hasMessages(1)) {
            Log.d("PluginFaceWidgetManager", "Remove 'attach container view' message");
            anonymousClass5.removeMessages(1);
        }
        com.android.systemui.keyguard.Log.d("PluginFaceWidgetManager", "onPluginDisconnected()");
        if (this.mFaceWidgetPlugin != null && !((PluginManager) Dependency.sDependency.getDependencyInner(PluginManager.class)).isValidClassLoader(this.mFaceWidgetPlugin.getClass().getClassLoader())) {
            this.mFaceWidgetPlugin.onClassLoaderDiscarded();
        }
        this.mFaceWidgetContainerWrapper.initPlugin(null, null, null);
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
            ArrayList arrayList = (ArrayList) externalClockProvider.mClockCallbacks;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                PluginClockProvider.ClockCallback clockCallback = (PluginClockProvider.ClockCallback) obj;
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

    public final void reconnectPluginModule() {
        this.mIsReconnected = true;
        this.mMediaDataManager.addListener(this.mMediaDataListener);
        this.mPluginManager.removePluginListener(this);
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            Log.i("PluginFaceWidgetManager", "Do not  initPluginModule in safe mode");
        } else {
            this.mPluginManager.addPluginListener(PluginKeyguardStatusView.ACTION, this, PluginKeyguardStatusView.class, false, true, 0);
        }
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
        NotificationPanelViewController notificationPanelViewController = this.mNPVController;
        if (notificationPanelViewController != null) {
            notificationPanelViewController.mMediaOutputDetailShowing = false;
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldControlScreenOff() : ", "PluginFaceWidgetManager", z);
        return z;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final boolean shouldEnableKeyguardScreenRotation() {
        return DeviceState.shouldEnableKeyguardScreenRotation(this.mSysuiContext);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void showBudsInfo() {
        AnonymousClass5 anonymousClass5 = this.mUiHandler;
        if (anonymousClass5.hasMessages(3)) {
            Log.d("PluginFaceWidgetManager", "Remove 'MSG_SHOW_BUDS_INFO' message");
            anonymousClass5.removeMessages(3);
        }
        anonymousClass5.sendMessage(anonymousClass5.obtainMessage(3));
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void showMediaOutput(String str) {
        this.mediaPackageName = str;
        AnonymousClass5 anonymousClass5 = this.mUiHandler;
        if (anonymousClass5.hasMessages(2)) {
            Log.d("PluginFaceWidgetManager", "Remove 'MSG_SHOW_MEDIA_OUTPUT' message");
            anonymousClass5.removeMessages(2);
        }
        anonymousClass5.sendMessage(anonymousClass5.obtainMessage(2));
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

    public final void updateNowBarVisibility(int i) {
        PluginKeyguardStatusView pluginKeyguardStatusView = this.mFaceWidgetPlugin;
        if (pluginKeyguardStatusView != null) {
            pluginKeyguardStatusView.updateNowBarVisibility(i);
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
    public final void applyBlur(View view, int i) {
        SecQpBlurController secQpBlurController;
        BitmapDrawable blurredBitmapWithEffect;
        if (!LsRune.SECURITY_CAPTURED_BLUR || (secQpBlurController = this.mSecQpBlurController) == null) {
            return;
        }
        float f = i;
        secQpBlurController.getClass();
        if (view == null || f <= 0.0f || (blurredBitmapWithEffect = secQpBlurController.secCapturedBlurBitmapGenerator.getBlurredBitmapWithEffect(SecPanelBlurBinding.BlurType.ALT_VIEW)) == null) {
            return;
        }
        view.setAlpha(f);
        view.setBackgroundDrawable(blurredBitmapWithEffect);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void applyBlur(float f) {
        applyBlur((int) (f * 255.0f));
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView.Callback
    public final void updateNIOShortcutFingerPrintVisibility(boolean z) {
    }
}
