package com.android.systemui;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.ScreenDecorations.ComponentCallbacksC09765;
import com.android.systemui.ScreenDecorations.CoverValidatingPreDrawListener;
import com.android.systemui.decor.CoverPrivacyDotViewController;
import com.android.systemui.decor.CoverRoundedCornerDecorProviderFactory;
import com.android.systemui.decor.CoverViewState;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DecorProvider;
import com.android.systemui.decor.OverlayWindow;
import com.android.systemui.decor.RoundedCornerDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerResDelegate;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.settings.SettingsProxy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenDecorations f$0;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda1(ScreenDecorations screenDecorations, int i) {
        this.$r8$classId = i;
        this.f$0 = screenDecorations;
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0213, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r10 != null ? r10 : null, r6) != false) goto L101;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        View coverOverlayView;
        View coverOverlayView2;
        View coverOverlayView3;
        View coverOverlayView4;
        Rect rect;
        DisplayCutout cutout;
        switch (this.$r8$classId) {
            case 0:
                ScreenDecorations screenDecorations = this.f$0;
                screenDecorations.getClass();
                Trace.beginSection("ScreenDecorations#onConfigurationChanged");
                screenDecorations.mPendingConfigChange = false;
                screenDecorations.updateConfiguration();
                screenDecorations.setupDecorations();
                if (screenDecorations.mOverlays != null) {
                    screenDecorations.updateLayoutParams();
                }
                Trace.endSection();
                return;
            case 1:
                ScreenDecorations screenDecorations2 = this.f$0;
                screenDecorations2.getClass();
                Trace.beginSection("ScreenDecorations#addTunable");
                screenDecorations2.mTunerService.addTunable(screenDecorations2, "sysui_rounded_size");
                Trace.endSection();
                return;
            case 2:
                ScreenDecorations screenDecorations3 = this.f$0;
                screenDecorations3.getClass();
                Trace.beginSection("ScreenDecorations#removeTunable");
                screenDecorations3.mTunerService.removeTunable(screenDecorations3);
                Trace.endSection();
                return;
            case 3:
                final ScreenDecorations screenDecorations4 = this.f$0;
                screenDecorations4.getClass();
                Trace.beginSection("ScreenDecorations#startOnScreenDecorationsThread");
                screenDecorations4.mWindowManager = (WindowManager) screenDecorations4.mContext.getSystemService(WindowManager.class);
                screenDecorations4.mContext.getDisplay().getDisplayInfo(screenDecorations4.mDisplayInfo);
                DisplayInfo displayInfo = screenDecorations4.mDisplayInfo;
                screenDecorations4.mRotation = displayInfo.rotation;
                screenDecorations4.mDisplayMode = displayInfo.getMode();
                DisplayInfo displayInfo2 = screenDecorations4.mDisplayInfo;
                screenDecorations4.mDisplayUniqueId = displayInfo2.uniqueId;
                screenDecorations4.mDisplayCutout = displayInfo2.displayCutout;
                RoundedCornerResDelegate roundedCornerResDelegate = new RoundedCornerResDelegate(screenDecorations4.mContext.getResources(), screenDecorations4.mDisplayUniqueId);
                screenDecorations4.mRoundedCornerResDelegate = roundedCornerResDelegate;
                float physicalPixelDisplaySizeRatio = screenDecorations4.getPhysicalPixelDisplaySizeRatio();
                if (!(roundedCornerResDelegate.physicalPixelDisplaySizeRatio == physicalPixelDisplaySizeRatio)) {
                    roundedCornerResDelegate.physicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                    roundedCornerResDelegate.reloadMeasures();
                }
                RoundedCornerResDelegate roundedCornerResDelegate2 = screenDecorations4.mRoundedCornerResDelegate;
                boolean displayAspectRatioChanged = screenDecorations4.getDisplayAspectRatioChanged();
                if (roundedCornerResDelegate2.displayAspectRatioChanged != displayAspectRatioChanged) {
                    roundedCornerResDelegate2.displayAspectRatioChanged = displayAspectRatioChanged;
                    roundedCornerResDelegate2.reloadMeasures();
                }
                screenDecorations4.mRoundedCornerFactory = new RoundedCornerDecorProviderFactory(screenDecorations4.mRoundedCornerResDelegate);
                Context context = screenDecorations4.mContext;
                screenDecorations4.mCutoutFactory = new CutoutDecorProviderFactory(context.getResources(), context.getDisplay());
                screenDecorations4.mHwcScreenDecorationSupport = screenDecorations4.mContext.getDisplay().getDisplayDecorationSupport();
                screenDecorations4.updateHwLayerRoundedCornerDrawable();
                screenDecorations4.setupDecorations();
                Context context2 = screenDecorations4.mContext;
                if (context2.getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection) || BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
                    CameraAvailabilityListener.Factory factory = CameraAvailabilityListener.Factory;
                    ExecutorImpl executorImpl = screenDecorations4.mExecutor;
                    Handler handler = screenDecorations4.mHandler;
                    factory.getClass();
                    CameraAvailabilityListener build = CameraAvailabilityListener.Factory.build(context2, executorImpl, handler);
                    screenDecorations4.mCameraListener = build;
                    ((ArrayList) build.listeners).add(screenDecorations4.mCameraTransitionCallback);
                    CameraAvailabilityListener cameraAvailabilityListener = screenDecorations4.mCameraListener;
                    cameraAvailabilityListener.cameraManager.registerSemCameraDeviceStateCallback(cameraAvailabilityListener.cameraDeviceStateCallback, cameraAvailabilityListener.handler);
                }
                Display display = ((DisplayManager) screenDecorations4.mContext.getSystemService(DisplayManager.class)).getDisplay(1);
                if (display != null) {
                    Context createWindowContext = screenDecorations4.mContext.createWindowContext(display, 2024, null);
                    screenDecorations4.mCoverWindowContext = createWindowContext;
                    createWindowContext.getDisplay().getDisplayInfo(screenDecorations4.mCoverDisplayInfo);
                    screenDecorations4.mCoverRotation = screenDecorations4.mCoverDisplayInfo.rotation;
                    CoverRoundedCornerDecorProviderFactory coverRoundedCornerDecorProviderFactory = new CoverRoundedCornerDecorProviderFactory(screenDecorations4.mContext.getResources());
                    screenDecorations4.mCoverRoundedCornerFactory = coverRoundedCornerDecorProviderFactory;
                    if (coverRoundedCornerDecorProviderFactory.hasProviders || screenDecorations4.isCoverPrivacyDotEnabled()) {
                        if (!screenDecorations4.hasCoverOverlay()) {
                            screenDecorations4.mCoverOverlay = new OverlayWindow(screenDecorations4.mCoverWindowContext);
                            ArrayList arrayList = new ArrayList();
                            CoverRoundedCornerDecorProviderFactory coverRoundedCornerDecorProviderFactory2 = screenDecorations4.mCoverRoundedCornerFactory;
                            if (coverRoundedCornerDecorProviderFactory2 == null ? false : coverRoundedCornerDecorProviderFactory2.hasProviders) {
                                arrayList.addAll(coverRoundedCornerDecorProviderFactory2.getProviders());
                            }
                            if (screenDecorations4.isCoverPrivacyDotEnabled()) {
                                arrayList.addAll(screenDecorations4.mDotFactory.getProviders());
                            }
                            if (!screenDecorations4.mCoverOverlay.hasSameProviders(arrayList)) {
                                arrayList.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ScreenDecorations screenDecorations5 = ScreenDecorations.this;
                                        DecorProvider decorProvider = (DecorProvider) obj;
                                        if (screenDecorations5.mCoverOverlay.getView(decorProvider.getViewId()) != null) {
                                            return;
                                        }
                                        OverlayWindow overlayWindow = screenDecorations5.mCoverOverlay;
                                        int i = screenDecorations5.mCoverRotation;
                                        int i2 = screenDecorations5.mTintColor;
                                        overlayWindow.viewProviderMap.put(Integer.valueOf(decorProvider.getViewId()), new Pair(decorProvider.inflateView(overlayWindow.context, overlayWindow.rootView, i, i2), decorProvider));
                                    }
                                });
                            }
                            RegionInterceptingFrameLayout regionInterceptingFrameLayout = screenDecorations4.mCoverOverlay.rootView;
                            regionInterceptingFrameLayout.setVisibility(0);
                            regionInterceptingFrameLayout.setSystemUiVisibility(256);
                            regionInterceptingFrameLayout.setForceDarkAllowed(false);
                            ((WindowManager) screenDecorations4.mCoverWindowContext.getSystemService(WindowManager.class)).addView(regionInterceptingFrameLayout, screenDecorations4.getCoverWindowLayoutParams());
                            regionInterceptingFrameLayout.getRootView().getViewTreeObserver().addOnPreDrawListener(screenDecorations4.new CoverValidatingPreDrawListener(regionInterceptingFrameLayout.getRootView()));
                            if (screenDecorations4.isCoverPrivacyDotEnabled() && (coverOverlayView = screenDecorations4.getCoverOverlayView(R.id.privacy_dot_top_left_container)) != null && (coverOverlayView2 = screenDecorations4.getCoverOverlayView(R.id.privacy_dot_top_right_container)) != null && (coverOverlayView3 = screenDecorations4.getCoverOverlayView(R.id.privacy_dot_bottom_left_container)) != null && (coverOverlayView4 = screenDecorations4.getCoverOverlayView(R.id.privacy_dot_bottom_right_container)) != null) {
                                final CoverPrivacyDotViewController coverPrivacyDotViewController = screenDecorations4.mCoverDotViewController;
                                final Context context3 = screenDecorations4.mCoverWindowContext;
                                View view = coverPrivacyDotViewController.f280tl;
                                if (view != null && coverPrivacyDotViewController.f281tr != null && coverPrivacyDotViewController.f278bl != null && coverPrivacyDotViewController.f279br != null && Intrinsics.areEqual(view, coverOverlayView)) {
                                    View view2 = coverPrivacyDotViewController.f281tr;
                                    if (view2 == null) {
                                        view2 = null;
                                    }
                                    if (Intrinsics.areEqual(view2, coverOverlayView2)) {
                                        View view3 = coverPrivacyDotViewController.f278bl;
                                        if (view3 == null) {
                                            view3 = null;
                                        }
                                        if (Intrinsics.areEqual(view3, coverOverlayView3)) {
                                            View view4 = coverPrivacyDotViewController.f279br;
                                            break;
                                        }
                                    }
                                }
                                coverPrivacyDotViewController.f280tl = coverOverlayView;
                                coverPrivacyDotViewController.f281tr = coverOverlayView2;
                                coverPrivacyDotViewController.f278bl = coverOverlayView3;
                                coverPrivacyDotViewController.f279br = coverOverlayView4;
                                coverPrivacyDotViewController.dotContainerWidth = context3.getResources().getDimensionPixelSize(R.dimen.cover_privacy_dot_container_width);
                                coverPrivacyDotViewController.dotContainerHeight = context3.getResources().getDimensionPixelSize(R.dimen.cover_privacy_dot_container_height);
                                Display display2 = context3.getDisplay();
                                if (display2 == null || (cutout = display2.getCutout()) == null || (rect = cutout.getBoundingRectBottom()) == null) {
                                    rect = new Rect();
                                }
                                coverPrivacyDotViewController.cutoutHeight = rect.height();
                                boolean isLayoutRtl = ((ConfigurationControllerImpl) coverPrivacyDotViewController.configurationController).isLayoutRtl();
                                View selectDesignatedCorner = coverPrivacyDotViewController.selectDesignatedCorner(0, isLayoutRtl);
                                int cornerForView = selectDesignatedCorner != null ? coverPrivacyDotViewController.cornerForView(selectDesignatedCorner) : -1;
                                coverPrivacyDotViewController.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController$initialize$5
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CoverPrivacyDotViewController coverPrivacyDotViewController2 = CoverPrivacyDotViewController.this;
                                        ((SystemStatusAnimationSchedulerImpl) coverPrivacyDotViewController2.animationScheduler).addCallback(coverPrivacyDotViewController2.systemStatusAnimationCallback);
                                        CoverPrivacyDotViewController coverPrivacyDotViewController3 = CoverPrivacyDotViewController.this;
                                        CameraAvailabilityListener.Factory factory2 = CameraAvailabilityListener.Factory;
                                        Context context4 = context3;
                                        DelayableExecutor delayableExecutor = coverPrivacyDotViewController3.uiExecutor;
                                        Intrinsics.checkNotNull(delayableExecutor);
                                        Handler handler2 = CoverPrivacyDotViewController.this.handler;
                                        Intrinsics.checkNotNull(handler2);
                                        factory2.getClass();
                                        coverPrivacyDotViewController3.cameraListener = CameraAvailabilityListener.Factory.build(context4, delayableExecutor, handler2);
                                        CameraAvailabilityListener cameraAvailabilityListener2 = CoverPrivacyDotViewController.this.cameraListener;
                                        Intrinsics.checkNotNull(cameraAvailabilityListener2);
                                        ((ArrayList) cameraAvailabilityListener2.listeners).add(CoverPrivacyDotViewController.this.cameraTransitionCallback);
                                        CameraAvailabilityListener cameraAvailabilityListener3 = CoverPrivacyDotViewController.this.cameraListener;
                                        Intrinsics.checkNotNull(cameraAvailabilityListener3);
                                        cameraAvailabilityListener3.cameraManager.registerSemCameraDeviceStateCallback(cameraAvailabilityListener3.cameraDeviceStateCallback, cameraAvailabilityListener3.handler);
                                    }
                                });
                                coverPrivacyDotViewController.updateRotations(0);
                                coverPrivacyDotViewController.setCornerSizes(coverPrivacyDotViewController.nextViewState);
                                synchronized (coverPrivacyDotViewController.lock) {
                                    coverPrivacyDotViewController.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, true, false, false, isLayoutRtl, 0, cornerForView, selectDesignatedCorner, null, 150));
                                    Unit unit = Unit.INSTANCE;
                                }
                            }
                        }
                        if (screenDecorations4.hasCoverOverlay()) {
                            if (!screenDecorations4.mIsRegistered) {
                                screenDecorations4.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda1(screenDecorations4, 4));
                                if (screenDecorations4.mColorInversionSetting == null) {
                                    screenDecorations4.mColorInversionSetting = new SettingObserver(screenDecorations4.mSecureSettings, screenDecorations4.mHandler, "accessibility_display_inversion_enabled", ((UserTrackerImpl) screenDecorations4.mUserTracker).getUserId()) { // from class: com.android.systemui.ScreenDecorations.7
                                        public C09787(SettingsProxy settingsProxy, Handler handler2, String str, int i) {
                                            super(settingsProxy, handler2, str, i);
                                        }

                                        @Override // com.android.systemui.qs.SettingObserver
                                        public final void handleValueChanged(int i, boolean z) {
                                            ScreenDecorations screenDecorations5 = ScreenDecorations.this;
                                            boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                                            screenDecorations5.updateColorInversion(i);
                                        }
                                    };
                                }
                                screenDecorations4.mColorInversionSetting.setListening(true);
                                screenDecorations4.mColorInversionSetting.onChange(false);
                                screenDecorations4.updateColorInversion(screenDecorations4.mColorInversionSetting.getValue());
                                ((UserTrackerImpl) screenDecorations4.mUserTracker).addCallback(screenDecorations4.mUserChangedCallback, screenDecorations4.mExecutor);
                                screenDecorations4.mIsRegistered = true;
                            }
                        } else if (!screenDecorations4.hasOverlays()) {
                            screenDecorations4.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda1(screenDecorations4, 5));
                            SettingObserver settingObserver = screenDecorations4.mColorInversionSetting;
                            if (settingObserver != null) {
                                settingObserver.setListening(false);
                            }
                            ((UserTrackerImpl) screenDecorations4.mUserTracker).removeCallback(screenDecorations4.mUserChangedCallback);
                            screenDecorations4.mIsRegistered = false;
                        }
                    }
                    screenDecorations4.mCoverWindowContext.registerComponentCallbacks(screenDecorations4.new ComponentCallbacksC09765());
                }
                final PrivacyDotViewController privacyDotViewController = screenDecorations4.mDotViewController;
                privacyDotViewController.getClass();
                privacyDotViewController.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$addSystemAnimationCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrivacyDotViewController privacyDotViewController2 = PrivacyDotViewController.this;
                        ((SystemStatusAnimationSchedulerImpl) privacyDotViewController2.animationScheduler).addCallback(privacyDotViewController2.systemStatusAnimationCallback);
                    }
                });
                screenDecorations4.mDotViewController.createListener = screenDecorations4.mPrivacyDotCreateListener;
                if (ScreenDecorations.DEBUG_PRIVACY_INDICATOR) {
                    Log.d("ScreenDecorations", "addSystemAnimationCallback & setCreateListener");
                }
                DisplayTracker.Callback c09776 = new DisplayTracker.Callback() { // from class: com.android.systemui.ScreenDecorations.6
                    public C09776() {
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:17:0x00c9, code lost:
                    
                        if (((r2 != null ? r0 != null && r2.format == r0.format && r2.alphaInterpretation == r0.alphaInterpretation : r0 == null) ? r13 : false) == false) goto L124;
                     */
                    @Override // com.android.systemui.settings.DisplayTracker.Callback
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onDisplayChanged(int i) {
                        boolean z;
                        int i2;
                        ScreenDecorations screenDecorations5 = ScreenDecorations.this;
                        screenDecorations5.mContext.getDisplay().getDisplayInfo(screenDecorations5.mDisplayInfo);
                        DisplayInfo displayInfo3 = screenDecorations5.mDisplayInfo;
                        int i3 = displayInfo3.rotation;
                        Display.Mode mode = displayInfo3.getMode();
                        if (!(screenDecorations5.mOverlays == null && screenDecorations5.mScreenDecorHwcWindow == null) && (screenDecorations5.mRotation != i3 || ScreenDecorations.displayModeChanged(screenDecorations5.mDisplayMode, mode))) {
                            screenDecorations5.mPendingConfigChange = true;
                            if (screenDecorations5.mOverlays != null) {
                                for (int i4 = 0; i4 < 4; i4++) {
                                    OverlayWindow overlayWindow = screenDecorations5.mOverlays[i4];
                                    if (overlayWindow != null) {
                                        RegionInterceptingFrameLayout regionInterceptingFrameLayout2 = overlayWindow.rootView;
                                        regionInterceptingFrameLayout2.getViewTreeObserver().addOnPreDrawListener(new RestartingPreDrawListener(ScreenDecorations.this, regionInterceptingFrameLayout2, i4, i3, mode, 0));
                                    }
                                }
                            }
                            ViewGroup viewGroup = screenDecorations5.mScreenDecorHwcWindow;
                            if (viewGroup != null) {
                                z = true;
                                i2 = 4;
                                viewGroup.getViewTreeObserver().addOnPreDrawListener(new RestartingPreDrawListener(screenDecorations5, screenDecorations5.mScreenDecorHwcWindow, -1, i3, mode, 0));
                            } else {
                                z = true;
                                i2 = 4;
                            }
                            ScreenDecorHwcLayer screenDecorHwcLayer = screenDecorations5.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer != null) {
                                screenDecorHwcLayer.pendingConfigChange = z;
                            }
                        } else {
                            z = true;
                            i2 = 4;
                        }
                        String str = screenDecorations5.mDisplayInfo.uniqueId;
                        if (!Objects.equals(str, screenDecorations5.mDisplayUniqueId)) {
                            screenDecorations5.mDisplayUniqueId = str;
                            DisplayDecorationSupport displayDecorationSupport = screenDecorations5.mContext.getDisplay().getDisplayDecorationSupport();
                            screenDecorations5.mRoundedCornerResDelegate.updateDisplayUniqueId(str, null);
                            if (screenDecorations5.hasSameProviders(screenDecorations5.getProviders(displayDecorationSupport != null ? z : false))) {
                                DisplayDecorationSupport displayDecorationSupport2 = screenDecorations5.mHwcScreenDecorationSupport;
                            }
                            screenDecorations5.mHwcScreenDecorationSupport = displayDecorationSupport;
                            if (screenDecorations5.mOverlays != null) {
                                for (int i5 = 0; i5 < i2; i5++) {
                                    OverlayWindow overlayWindow2 = screenDecorations5.mOverlays[i5];
                                    if (overlayWindow2 != null) {
                                        screenDecorations5.mWindowManager.removeViewImmediate(overlayWindow2.rootView);
                                        screenDecorations5.mOverlays[i5] = null;
                                    }
                                }
                                screenDecorations5.mOverlays = null;
                            }
                            screenDecorations5.setupDecorations();
                            return;
                        }
                        if (i == z && screenDecorations5.hasCoverOverlay()) {
                            Display display3 = screenDecorations5.mCoverWindowContext.getDisplay();
                            DisplayInfo displayInfo4 = screenDecorations5.mCoverDisplayInfo;
                            display3.getDisplayInfo(displayInfo4);
                            int i6 = displayInfo4.rotation;
                            int i7 = screenDecorations5.mCoverRotation;
                            if (i6 != i7) {
                                LogBuffer logBuffer = screenDecorations5.mLogger.logBuffer;
                                LogLevel logLevel = LogLevel.DEBUG;
                                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onDisplayChanged, displayId=", i, ", ", i7, " -> ");
                                m45m.append(i6);
                                LogBuffer.log$default(logBuffer, "ScreenDecorationsLog", logLevel, m45m.toString(), null, 8, null);
                                screenDecorations5.mCoverPendingConfigChange = z;
                                RegionInterceptingFrameLayout regionInterceptingFrameLayout3 = screenDecorations5.mCoverOverlay.rootView;
                                regionInterceptingFrameLayout3.getViewTreeObserver().addOnPreDrawListener(new CoverRestartingPreDrawListener(screenDecorations5, regionInterceptingFrameLayout3, i6));
                            }
                        }
                    }
                };
                screenDecorations4.mDisplayListener = c09776;
                ((DisplayTrackerImpl) screenDecorations4.mDisplayTracker).addDisplayChangeCallback(c09776, new HandlerExecutor(screenDecorations4.mHandler));
                screenDecorations4.updateConfiguration();
                if (screenDecorations4.hasCoverOverlay()) {
                    screenDecorations4.mSettingsHelper.registerCallback(screenDecorations4.mAODStateSettingsCallback, Settings.System.getUriFor("aod_show_state"));
                }
                if (screenDecorations4.mIndicatorCutoutUtil.isUDCModel) {
                    screenDecorations4.updateFillUDCDisplayCutout();
                    screenDecorations4.mSettingsHelper.registerCallback(screenDecorations4.mFillUDCSettingsCallback, Settings.Global.getUriFor("fill_udc_display_cutout"));
                }
                Trace.endSection();
                return;
            case 4:
                ScreenDecorations screenDecorations5 = this.f$0;
                screenDecorations5.getClass();
                Trace.beginSection("ScreenDecorations#addTunable");
                screenDecorations5.mTunerService.addTunable(screenDecorations5, "sysui_rounded_size");
                Trace.endSection();
                return;
            default:
                ScreenDecorations screenDecorations6 = this.f$0;
                screenDecorations6.getClass();
                Trace.beginSection("ScreenDecorations#removeTunable");
                screenDecorations6.mTunerService.removeTunable(screenDecorations6);
                Trace.endSection();
                return;
        }
    }
}
