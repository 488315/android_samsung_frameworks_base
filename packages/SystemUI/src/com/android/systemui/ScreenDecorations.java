package com.android.systemui;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DebugRoundedCornerDelegate;
import com.android.systemui.decor.DecorProvider;
import com.android.systemui.decor.DecorProviderFactory;
import com.android.systemui.decor.FaceScanningProviderFactory;
import com.android.systemui.decor.OverlayWindow;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerResDelegateImpl;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.view.SemWindowManager;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenDecorations implements ConfigurationController.ConfigurationListener, Dumpable {
    public CameraAvailabilityListener mCameraListener;
    public final CameraProtectionLoader mCameraProtectionLoader;
    public AnonymousClass6 mColorInversionSetting;
    public final CommandRegistry mCommandRegistry;
    public final Context mContext;
    public CutoutDecorProviderFactory mCutoutFactory;
    public CutoutDecorProviderFactory mDebugCutoutFactory;
    public RoundedCornerDecorProviderFactory mDebugRoundedCornerFactory;
    public DisplayCutout mDisplayCutout;
    DisplayTracker.Callback mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    String mDisplayUniqueId;
    public final PrivacyDotDecorProviderFactory mDotFactory;
    public final PrivacyDotViewController mDotViewController;
    public DelayableExecutor mExecutor;
    public final FacePropertyRepository mFacePropertyRepository;
    public final FaceScanningProviderFactory mFaceScanningFactory;
    public Handler mHandler;
    protected DisplayDecorationSupport mHwcScreenDecorationSupport;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public boolean mIsDotViewVisible;
    protected boolean mIsRegistered;
    public boolean mIsSmartViewFitToActiveDisplay;
    public final JavaAdapter mJavaAdapter;
    public final ScreenDecorationsLogger mLogger;
    public final Executor mMainExecutor;
    public boolean mPendingConfigChange;
    public boolean mPendingManualConfigUpdate;
    public int mRotation;
    protected DecorProviderFactory mRoundedCornerFactory;
    protected RoundedCornerResDelegateImpl mRoundedCornerResDelegate;
    ScreenDecorHwcLayer mScreenDecorHwcLayer;
    ViewGroup mScreenDecorHwcWindow;
    public final SecureSettings mSecureSettings;
    private final SettingsHelper mSettingsHelper;
    public final ThreadFactory mThreadFactory;
    public final UserTracker mUserTracker;
    public WindowManager mWindowManager;
    public static final boolean DEBUG_DISABLE_SCREEN_DECORATIONS = SystemProperties.getBoolean("debug.disable_screen_decorations", false);
    public static final boolean DEBUG_SCREENSHOT_ROUNDED_CORNERS = SystemProperties.getBoolean("debug.screenshot_rounded_corners", false);
    public static final boolean sToolkitSetFrameRateReadOnly = android.view.flags.Flags.toolkitSetFrameRateReadOnly();
    public static final int[] DISPLAY_CUTOUT_IDS = {R.id.display_cutout, R.id.display_cutout_left, R.id.display_cutout_right, R.id.display_cutout_bottom};
    public boolean mDebug = DEBUG_SCREENSHOT_ROUNDED_CORNERS;
    public int mDebugColor = -65536;
    protected DebugRoundedCornerDelegate mDebugRoundedCornerDelegate = new DebugRoundedCornerDelegate();
    public int mProviderRefreshToken = 0;
    protected OverlayWindow[] mOverlays = null;
    public int mTintColor = -16777216;
    public final Point mDisplaySize = new Point();
    protected DisplayInfo mDisplayInfo = new DisplayInfo();
    public boolean blockUpdateStatusIconContainerLayout = false;
    public final AnonymousClass1 mCameraTransitionCallback = new AnonymousClass1();
    PrivacyDotViewController.ShowingListener mPrivacyDotShowingListener = new AnonymousClass2();
    PrivacyDotViewController.CreateListener mPrivacyDotCreateListener = new AnonymousClass3();
    public final ScreenDecorations$$ExternalSyntheticLambda0 mScreenDecorCommandCallback = new ScreenDecorations$$ExternalSyntheticLambda0(this);
    private SettingsHelper.OnChangedCallback mFillUDCSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            screenDecorations.getClass();
            if (uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_FILL_UDC_DISPLAY_CUTOUT))) {
                Log.d("ScreenDecorations", uri.toString() + " changed");
                screenDecorations.updateFillUDCDisplayCutout();
            }
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.ScreenDecorations.8
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            screenDecorations.mLogger.logUserSwitched(i);
            screenDecorations.mColorInversionSetting.setUserId(i);
            screenDecorations.updateColorInversion(screenDecorations.mColorInversionSetting.getValue());
        }
    };
    public final int mFaceScanningViewId = R.id.face_scanning_anim;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.ScreenDecorations$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onApplyCameraProtection(Path path, Rect rect) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
            screenDecorationsLogger.getClass();
            screenDecorationsLogger.logBuffer.log("ScreenDecorationsLog", LogLevel.DEBUG, "onApplyCameraProtection", null);
            IndicatorCutoutUtil indicatorCutoutUtil = screenDecorations.mIndicatorCutoutUtil;
            boolean z = true;
            if (!indicatorCutoutUtil.isUDCModel) {
                screenDecorations.mCutoutFactory.isCameraProtectionVisible = true;
                if (screenDecorations.mDebug) {
                    screenDecorations.mDebugCutoutFactory.isCameraProtectionVisible = true;
                }
                screenDecorations.setupDecorations();
                screenDecorations.showCameraProtection(path, rect);
                return;
            }
            if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
                if (indicatorCutoutUtil.isUDCMainDisplay()) {
                    screenDecorations.blockUpdateStatusIconContainerLayout = true;
                }
                if (screenDecorations.mCutoutFactory.shouldFillUDCDisplayCutout) {
                    return;
                }
                screenDecorations.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda4(screenDecorations, z, 1));
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.ScreenDecorations$2, reason: invalid class name */
    public final class AnonymousClass2 implements PrivacyDotViewController.ShowingListener {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.ScreenDecorations$3, reason: invalid class name */
    public final class AnonymousClass3 implements PrivacyDotViewController.CreateListener {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class DisplayCutoutView extends DisplayCutoutBaseView {
        public final Rect mBoundingRect;
        public final List mBounds;
        public int mColor;
        public final int mInitialPosition;
        public int mPosition;
        public int mRotation;
        public final Rect mTotalBounds;

        public DisplayCutoutView(Context context, int i) {
            super(context);
            this.mBounds = new ArrayList();
            this.mBoundingRect = new Rect();
            this.mTotalBounds = new Rect();
            this.mColor = -16777216;
            this.mInitialPosition = i;
            this.paint.setColor(-16777216);
            this.paint.setStyle(Paint.Style.FILL);
            this.paintForCameraProtection.setColor(this.mColor);
            this.paintForCameraProtection.setStyle(Paint.Style.FILL_AND_STROKE);
            SemWindowManager semWindowManager = SemWindowManager.getInstance();
            Point point = new Point();
            semWindowManager.getInitialDisplaySize(point);
            this.initialDisplayWidth = point.x;
            this.initialDisplayDensity = semWindowManager.getInitialDensity();
        }

        public static void boundsFromDirection(int i, Rect rect, DisplayCutout displayCutout) {
            if (i == 3) {
                rect.set(displayCutout.getBoundingRectLeft());
                return;
            }
            if (i == 5) {
                rect.set(displayCutout.getBoundingRectRight());
                return;
            }
            if (i == 48) {
                rect.set(displayCutout.getBoundingRectTop());
            } else if (i != 80) {
                rect.setEmpty();
            } else {
                rect.set(displayCutout.getBoundingRectBottom());
            }
        }

        public final int getGravity(DisplayCutout displayCutout) {
            int i = this.mPosition;
            return i == 0 ? !displayCutout.getBoundingRectLeft().isEmpty() ? 3 : 0 : i == 1 ? !displayCutout.getBoundingRectTop().isEmpty() ? 48 : 0 : i == 3 ? !displayCutout.getBoundingRectBottom().isEmpty() ? 80 : 0 : (i != 2 || displayCutout.getBoundingRectRight().isEmpty()) ? 0 : 5;
        }

        @Override // android.view.View
        public void onMeasure(int i, int i2) {
            if (((ArrayList) this.mBounds).isEmpty()) {
                super.onMeasure(i, i2);
                return;
            }
            if (!this.showProtection) {
                setMeasuredDimension(View.resolveSizeAndState(this.mBoundingRect.width(), i, 0), View.resolveSizeAndState(this.mBoundingRect.height(), i2, 0));
                return;
            }
            this.mTotalBounds.set(this.mBoundingRect);
            Rect rect = this.mTotalBounds;
            RectF rectF = this.protectionRect;
            rect.union((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            setMeasuredDimension(View.resolveSizeAndState(this.mTotalBounds.width(), i, 0), View.resolveSizeAndState(this.mTotalBounds.height(), i2, 0));
        }

        public void setColor$1(int i) {
            if (i == this.mColor) {
                return;
            }
            this.mColor = i;
            this.paint.setColor(i);
            this.paintForCameraProtection.setColor(this.mColor);
            invalidate();
        }

        /* JADX WARN: Removed duplicated region for block: B:44:0x008a  */
        @Override // com.android.systemui.DisplayCutoutBaseView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void updateCutout() {
            /*
                Method dump skipped, instructions count: 418
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorations.DisplayCutoutView.updateCutout():void");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class RestartingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final Point mTargetDisplaySize;
        public final int mTargetRotation;
        public final View mView;

        public /* synthetic */ RestartingPreDrawListener(ScreenDecorations screenDecorations, View view, int i, int i2, Point point, int i3) {
            this(view, i, i2, point);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            int i = this.mTargetRotation;
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (i == screenDecorations.mRotation && screenDecorations.mDisplaySize.equals(this.mTargetDisplaySize)) {
                return true;
            }
            ScreenDecorations screenDecorations2 = ScreenDecorations.this;
            screenDecorations2.mPendingConfigChange = false;
            screenDecorations2.updateConfiguration();
            this.mView.invalidate();
            return false;
        }

        private RestartingPreDrawListener(View view, int i, int i2, Point point) {
            this.mView = view;
            this.mTargetRotation = i2;
            this.mTargetDisplaySize = point;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ValidatingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final View mView;

        public ValidatingPreDrawListener(View view) {
            this.mView = view;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            ScreenDecorations.this.mContext.getDisplay().getDisplayInfo(ScreenDecorations.this.mDisplayInfo);
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            DisplayInfo displayInfo = screenDecorations.mDisplayInfo;
            if ((displayInfo.rotation == screenDecorations.mRotation && !ScreenDecorations.displaySizeChanged(screenDecorations.mDisplaySize, displayInfo)) || ScreenDecorations.this.mPendingConfigChange) {
                return true;
            }
            this.mView.invalidate();
            return false;
        }
    }

    /* renamed from: $r8$lambda$06HZTYiZHuzOTp6D2dnb_-0cn6o, reason: not valid java name */
    public static void m879$r8$lambda$06HZTYiZHuzOTp6D2dnb_0cn6o(ScreenDecorations screenDecorations) {
        screenDecorations.getClass();
        Trace.beginSection("ScreenDecorations#onConfigurationChanged");
        screenDecorations.mContext.getDisplay().getDisplayInfo(screenDecorations.mDisplayInfo);
        if (!(screenDecorations.mIndicatorCutoutUtil.isUDCModel && screenDecorations.mSettingsHelper.isFillUDCDisplayCutoutEnabled()) && displaySizeChanged(screenDecorations.mDisplaySize, screenDecorations.mDisplayInfo)) {
            screenDecorations.mPendingManualConfigUpdate = true;
            return;
        }
        int i = screenDecorations.mRotation;
        screenDecorations.mPendingConfigChange = false;
        screenDecorations.updateConfiguration();
        int i2 = screenDecorations.mRotation;
        if (i != i2) {
            screenDecorations.mLogger.logRotationChanged(i, i2);
        }
        screenDecorations.setupDecorations();
        if (screenDecorations.mOverlays != null) {
            screenDecorations.updateLayoutParams();
        }
        Trace.endSection();
    }

    public ScreenDecorations(Context context, SecureSettings secureSettings, CommandRegistry commandRegistry, UserTracker userTracker, DisplayTracker displayTracker, PrivacyDotViewController privacyDotViewController, ThreadFactory threadFactory, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, FaceScanningProviderFactory faceScanningProviderFactory, ScreenDecorationsLogger screenDecorationsLogger, FacePropertyRepository facePropertyRepository, JavaAdapter javaAdapter, CameraProtectionLoader cameraProtectionLoader, SettingsHelper settingsHelper, IndicatorCutoutUtil indicatorCutoutUtil, IndicatorGardenPresenter indicatorGardenPresenter, Executor executor, ConfigurationController configurationController) {
        this.mContext = context;
        this.mSecureSettings = secureSettings;
        this.mCommandRegistry = commandRegistry;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mDotViewController = privacyDotViewController;
        this.mThreadFactory = threadFactory;
        this.mDotFactory = privacyDotDecorProviderFactory;
        this.mFaceScanningFactory = faceScanningProviderFactory;
        this.mCameraProtectionLoader = cameraProtectionLoader;
        this.mLogger = screenDecorationsLogger;
        this.mFacePropertyRepository = facePropertyRepository;
        this.mJavaAdapter = javaAdapter;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mSettingsHelper = settingsHelper;
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        this.mMainExecutor = executor;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        }
    }

    public static boolean displaySizeChanged(Point point, DisplayInfo displayInfo) {
        return (point.x == displayInfo.getNaturalWidth() && point.y == displayInfo.getNaturalHeight()) ? false : true;
    }

    public static int getBoundPositionFromRotation(int i, int i2) {
        int i3 = i - i2;
        return i3 < 0 ? i3 + 4 : i3;
    }

    public static WindowManager.LayoutParams getWindowLayoutBaseParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 545259832, -3);
        int i = layoutParams.privateFlags;
        layoutParams.privateFlags = 536870992 | i;
        if (!DEBUG_SCREENSHOT_ROUNDED_CORNERS) {
            layoutParams.privateFlags = i | 537919568;
        }
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= 16777216;
        return layoutParams;
    }

    public static String getWindowTitleByPos(int i) {
        if (i == 0) {
            return "ScreenDecorOverlayLeft";
        }
        if (i == 1) {
            return "ScreenDecorOverlay";
        }
        if (i == 2) {
            return "ScreenDecorOverlayRight";
        }
        if (i == 3) {
            return "ScreenDecorOverlayBottom";
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown bound position: "));
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ScreenDecorations state:");
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        StringBuilder sb = new StringBuilder("DEBUG_DISABLE_SCREEN_DECORATIONS:");
        boolean z = DEBUG_DISABLE_SCREEN_DECORATIONS;
        sb.append(z);
        asIndenting.println(sb.toString());
        if (z) {
            return;
        }
        asIndenting.println("mDebug:" + this.mDebug);
        asIndenting.println("mIsPrivacyDotEnabled:" + this.mDotFactory.getHasProviders());
        asIndenting.println("shouldOptimizeOverlayVisibility:false");
        FaceScanningProviderFactory faceScanningProviderFactory = this.mFaceScanningFactory;
        boolean hasProviders = faceScanningProviderFactory.getHasProviders();
        asIndenting.println("supportsShowingFaceScanningAnim:" + hasProviders);
        if (hasProviders) {
            asIndenting.increaseIndent();
            StringBuilder sb2 = new StringBuilder("canShowFaceScanningAnim:");
            boolean hasProviders2 = faceScanningProviderFactory.getHasProviders();
            KeyguardUpdateMonitor keyguardUpdateMonitor = faceScanningProviderFactory.keyguardUpdateMonitor;
            sb2.append(hasProviders2 && keyguardUpdateMonitor.isFaceEnabledAndEnrolled());
            asIndenting.println(sb2.toString());
            StringBuilder sb3 = new StringBuilder("shouldShowFaceScanningAnim (at time dump was taken):");
            sb3.append(faceScanningProviderFactory.getHasProviders() && keyguardUpdateMonitor.isFaceEnabledAndEnrolled() && (keyguardUpdateMonitor.isFaceDetectionRunning() || faceScanningProviderFactory.authController.isShowing()));
            asIndenting.println(sb3.toString());
            asIndenting.decreaseIndent();
        }
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.dump(asIndenting);
        }
        asIndenting.println("mPendingConfigChange:" + this.mPendingConfigChange);
        if (this.mHwcScreenDecorationSupport != null) {
            asIndenting.increaseIndent();
            asIndenting.println("mHwcScreenDecorationSupport:");
            asIndenting.increaseIndent();
            asIndenting.println("format=" + PixelFormat.formatToString(this.mHwcScreenDecorationSupport.format));
            StringBuilder sb4 = new StringBuilder("alphaInterpretation=");
            int i = this.mHwcScreenDecorationSupport.alphaInterpretation;
            sb4.append(i != 0 ? i != 1 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown: ") : "MASK" : "COVERAGE");
            asIndenting.println(sb4.toString());
            asIndenting.decreaseIndent();
            asIndenting.decreaseIndent();
        } else {
            asIndenting.increaseIndent();
            printWriter.println("mHwcScreenDecorationSupport: null");
            asIndenting.decreaseIndent();
        }
        if (this.mScreenDecorHwcLayer != null) {
            asIndenting.increaseIndent();
            this.mScreenDecorHwcLayer.dump(asIndenting);
            asIndenting.decreaseIndent();
        } else {
            asIndenting.println("mScreenDecorHwcLayer: null");
        }
        if (this.mOverlays != null) {
            StringBuilder sb5 = new StringBuilder("mOverlays(left,top,right,bottom)=(");
            sb5.append(this.mOverlays[0] != null);
            sb5.append(",");
            sb5.append(this.mOverlays[1] != null);
            sb5.append(",");
            sb5.append(this.mOverlays[2] != null);
            sb5.append(",");
            sb5.append(this.mOverlays[3] != null);
            sb5.append(")");
            asIndenting.println(sb5.toString());
            for (int i2 = 0; i2 < 4; i2++) {
                OverlayWindow overlayWindow = this.mOverlays[i2];
                if (overlayWindow != null) {
                    printWriter.println("  " + getWindowTitleByPos(i2) + "=");
                    RegionInterceptingFrameLayout regionInterceptingFrameLayout = overlayWindow.rootView;
                    printWriter.println("    rootView=" + regionInterceptingFrameLayout);
                    int childCount = regionInterceptingFrameLayout.getChildCount();
                    for (int i3 = 0; i3 < childCount; i3++) {
                        View childAt = regionInterceptingFrameLayout.getChildAt(i3);
                        Pair pair = (Pair) ((LinkedHashMap) overlayWindow.viewProviderMap).get(Integer.valueOf(childAt.getId()));
                        printWriter.println("    child[" + i3 + "]=" + childAt + " " + (pair != null ? (DecorProvider) pair.getSecond() : null));
                    }
                }
            }
        }
        this.mRoundedCornerResDelegate.dump(printWriter, strArr);
        DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
        debugRoundedCornerDelegate.getClass();
        printWriter.println("DebugRoundedCornerDelegate state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  hasTop=", debugRoundedCornerDelegate.hasTop, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  hasBottom=", debugRoundedCornerDelegate.hasBottom, printWriter);
        printWriter.println(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(debugRoundedCornerDelegate.topRoundedSize.getWidth(), debugRoundedCornerDelegate.topRoundedSize.getHeight(), "  topRoundedSize(w,h)=(", ",", ")"));
        printWriter.println(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(debugRoundedCornerDelegate.bottomRoundedSize.getWidth(), debugRoundedCornerDelegate.bottomRoundedSize.getHeight(), "  bottomRoundedSize(w,h)=(", ",", ")"));
        printWriter.println("  physicalPixelDisplaySizeRatio=" + debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio);
    }

    public WindowManager.LayoutParams getCoverWindowLayoutParams() {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        windowLayoutBaseParams.width = -1;
        windowLayoutBaseParams.height = -1;
        windowLayoutBaseParams.setTitle("ScreenDecorOverlayCover");
        windowLayoutBaseParams.gravity = 17;
        return windowLayoutBaseParams;
    }

    public final WindowManager.LayoutParams getHwcWindowLayoutParams() {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        windowLayoutBaseParams.width = -1;
        windowLayoutBaseParams.height = -1;
        windowLayoutBaseParams.setTitle("ScreenDecorHwcOverlay");
        if (sToolkitSetFrameRateReadOnly) {
            windowLayoutBaseParams.setFrameRateBoostOnTouchEnabled(false);
            windowLayoutBaseParams.setFrameRatePowerSavingsBalanced(false);
        }
        windowLayoutBaseParams.gravity = 8388659;
        if (!this.mDebug) {
            windowLayoutBaseParams.setColorMode(4);
        }
        return windowLayoutBaseParams;
    }

    public View getOverlayView(int i) {
        View view;
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null) {
            return null;
        }
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null && (view = overlayWindow.getView(i)) != null) {
                return view;
            }
        }
        return null;
    }

    public float getPhysicalPixelDisplaySizeRatio() {
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(this.mDisplayInfo.supportedModes);
        if (maximumResolutionDisplayMode == null) {
            return 1.0f;
        }
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode.getPhysicalHeight(), this.mDisplayInfo.getNaturalWidth(), this.mDisplayInfo.getNaturalHeight());
    }

    public List<DecorProvider> getProviders(boolean z) {
        ArrayList arrayList = new ArrayList(this.mDotFactory.getProviders());
        arrayList.addAll(this.mFaceScanningFactory.getProviders());
        if (!z && !DeviceType.isFactoryBinary() && !this.mIsSmartViewFitToActiveDisplay) {
            if (this.mDebug && this.mDebugRoundedCornerFactory.getHasProviders()) {
                arrayList.addAll(this.mDebugRoundedCornerFactory.getProviders());
            } else {
                arrayList.addAll(this.mRoundedCornerFactory.getProviders());
            }
            if (this.mDebug) {
                arrayList.addAll(this.mDebugCutoutFactory.getProviders());
            } else {
                arrayList.addAll(this.mCutoutFactory.getProviders());
            }
        }
        return arrayList;
    }

    public WindowManager.LayoutParams getWindowLayoutParams(int i) {
        if (isCoverDisplay().booleanValue()) {
            return getCoverWindowLayoutParams();
        }
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        int boundPositionFromRotation = getBoundPositionFromRotation(i, this.mRotation);
        int i2 = 3;
        windowLayoutBaseParams.width = (boundPositionFromRotation == 1 || boundPositionFromRotation == 3) ? -1 : -2;
        int boundPositionFromRotation2 = getBoundPositionFromRotation(i, this.mRotation);
        windowLayoutBaseParams.height = (boundPositionFromRotation2 == 1 || boundPositionFromRotation2 == 3) ? -2 : -1;
        windowLayoutBaseParams.setTitle(getWindowTitleByPos(i));
        int boundPositionFromRotation3 = getBoundPositionFromRotation(i, this.mRotation);
        if (boundPositionFromRotation3 != 0) {
            if (boundPositionFromRotation3 == 1) {
                i2 = 48;
            } else if (boundPositionFromRotation3 == 2) {
                i2 = 5;
            } else {
                if (boundPositionFromRotation3 != 3) {
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown bound position: "));
                }
                i2 = 80;
            }
        }
        windowLayoutBaseParams.gravity = i2;
        return windowLayoutBaseParams;
    }

    public boolean hasOverlays() {
        if (this.mOverlays == null) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (this.mOverlays[i] != null) {
                return true;
            }
        }
        this.mOverlays = null;
        return false;
    }

    public final boolean hasRoundedCorners() {
        return this.mRoundedCornerFactory.getHasProviders() || this.mDebugRoundedCornerFactory.getHasProviders();
    }

    public boolean hasSameProviders(List<DecorProvider> list) {
        ArrayList arrayList = new ArrayList();
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr != null) {
            for (OverlayWindow overlayWindow : overlayWindowArr) {
                if (overlayWindow != null) {
                    arrayList.addAll(CollectionsKt___CollectionsKt.toList(((LinkedHashMap) overlayWindow.viewProviderMap).keySet()));
                }
            }
        }
        if (arrayList.size() != list.size()) {
            return false;
        }
        Iterator<DecorProvider> it = list.iterator();
        while (it.hasNext()) {
            if (!arrayList.contains(Integer.valueOf(it.next().getViewId()))) {
                return false;
            }
        }
        return true;
    }

    public void hideCameraProtection() {
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.hideOverlayRunnable = new ScreenDecorations$$ExternalSyntheticLambda2(this, faceScanningOverlay);
            faceScanningOverlay.enableShowProtection(false);
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer != null) {
            screenDecorHwcLayer.enableShowProtection(false);
            return;
        }
        int i = 0;
        for (int i2 : DISPLAY_CUTOUT_IDS) {
            View overlayView = getOverlayView(i2);
            if (overlayView instanceof DisplayCutoutView) {
                i++;
                ((DisplayCutoutView) overlayView).enableShowProtection(false);
            }
        }
        if (i == 0) {
            Log.e("ScreenDecorations", "CutoutView not initialized hideCameraProtection");
        }
    }

    public final void initOverlay(final OverlayWindow overlayWindow, List list) {
        overlayWindow.getClass();
        if (list.size() == overlayWindow.viewProviderMap.size()) {
            List list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (overlayWindow.getView(((DecorProvider) it.next()).getViewId()) != null) {
                    }
                }
            }
            overlayWindow.rootView.setVisibility(0);
        }
        list.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                View view;
                ScreenDecorations screenDecorations = ScreenDecorations.this;
                OverlayWindow overlayWindow2 = overlayWindow;
                DecorProvider decorProvider = (DecorProvider) obj;
                screenDecorations.getClass();
                if (overlayWindow2.getView(decorProvider.getViewId()) != null) {
                    return;
                }
                int viewId = decorProvider.getViewId();
                OverlayWindow[] overlayWindowArr = screenDecorations.mOverlays;
                if (overlayWindowArr != null) {
                    for (OverlayWindow overlayWindow3 : overlayWindowArr) {
                        if (overlayWindow3 != null && (view = overlayWindow3.getView(viewId)) != null) {
                            overlayWindow3.rootView.removeView(view);
                            overlayWindow3.viewProviderMap.remove(Integer.valueOf(viewId));
                        }
                    }
                }
                overlayWindow2.viewProviderMap.put(Integer.valueOf(decorProvider.getViewId()), new Pair(decorProvider.inflateView(overlayWindow2.context, overlayWindow2.rootView, screenDecorations.mRotation, screenDecorations.mTintColor), decorProvider));
            }
        });
        overlayWindow.rootView.setVisibility(0);
    }

    public final Boolean isCoverDisplay() {
        return Boolean.valueOf(this.mContext.getDisplayId() == 1);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(this, 0));
        }
    }

    public void onFaceSensorLocationChanged(Point point) {
        ScreenDecorationsLogger screenDecorationsLogger = this.mLogger;
        screenDecorationsLogger.getClass();
        screenDecorationsLogger.logBuffer.log("ScreenDecorationsLog", LogLevel.DEBUG, "AuthControllerCallback in ScreenDecorations triggered", null);
        DelayableExecutor delayableExecutor = this.mExecutor;
        if (delayableExecutor != null) {
            delayableExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(this, 4));
        }
    }

    public final void removeAllOverlays() {
        if (this.mOverlays == null) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            OverlayWindow[] overlayWindowArr = this.mOverlays;
            OverlayWindow overlayWindow = overlayWindowArr[i];
            if (overlayWindow != null && overlayWindowArr != null && overlayWindow != null) {
                this.mWindowManager.removeViewImmediate(overlayWindow.rootView);
                this.mOverlays[i] = null;
            }
        }
        this.mOverlays = null;
    }

    public final void removeHwcOverlay() {
        ViewGroup viewGroup = this.mScreenDecorHwcWindow;
        if (viewGroup == null) {
            return;
        }
        this.mWindowManager.removeViewImmediate(viewGroup);
        this.mScreenDecorHwcWindow = null;
        this.mScreenDecorHwcLayer = null;
    }

    public void setDebug(boolean z) {
        if (this.mDebug == z) {
            return;
        }
        this.mDebug = z;
        if (!z) {
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
            debugRoundedCornerDelegate.hasTop = false;
            debugRoundedCornerDelegate.topRoundedDrawable = null;
            debugRoundedCornerDelegate.topRoundedSize = new Size(0, 0);
            debugRoundedCornerDelegate.hasBottom = false;
            debugRoundedCornerDelegate.bottomRoundedDrawable = null;
            debugRoundedCornerDelegate.bottomRoundedSize = new Size(0, 0);
        }
        this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(this, 5));
    }

    public void setSize(View view, Size size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = size.getWidth();
        layoutParams.height = size.getHeight();
        view.setLayoutParams(layoutParams);
    }

    public final void setupCameraListener() {
        boolean z = this.mDebug ? this.mDebugCutoutFactory.isCameraProtectionEnabled : this.mContext.getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection);
        CameraAvailabilityListener cameraAvailabilityListener = this.mCameraListener;
        AnonymousClass1 anonymousClass1 = this.mCameraTransitionCallback;
        if (cameraAvailabilityListener != null) {
            ((ArrayList) cameraAvailabilityListener.listeners).remove(anonymousClass1);
            CameraAvailabilityListener cameraAvailabilityListener2 = this.mCameraListener;
            cameraAvailabilityListener2.cameraManager.unregisterSemCameraDeviceStateCallback(cameraAvailabilityListener2.cameraDeviceStateCallback);
        }
        if (z || BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            CameraAvailabilityListener.Factory factory = CameraAvailabilityListener.Factory;
            Context context = this.mContext;
            DelayableExecutor delayableExecutor = this.mExecutor;
            Handler handler = this.mHandler;
            factory.getClass();
            CameraAvailabilityListener cameraAvailabilityListener3 = new CameraAvailabilityListener((CameraManager) context.getSystemService("camera"), ((CameraProtectionLoaderImpl) this.mCameraProtectionLoader).loadCameraProtectionInfoList(), context.getResources().getString(R.string.config_cameraProtectionExcludedPackages), delayableExecutor, handler);
            this.mCameraListener = cameraAvailabilityListener3;
            ((ArrayList) cameraAvailabilityListener3.listeners).add(anonymousClass1);
            CameraAvailabilityListener cameraAvailabilityListener4 = this.mCameraListener;
            cameraAvailabilityListener4.cameraManager.registerSemCameraDeviceStateCallback(cameraAvailabilityListener4.cameraDeviceStateCallback, cameraAvailabilityListener4.handler);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:128:0x025f, code lost:
    
        if (r14.mHwcScreenDecorationSupport == null) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0264, code lost:
    
        if (r14.mIsDotViewVisible == false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x026a, code lost:
    
        if (r2.getHasProviders() == false) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0259, code lost:
    
        if (r11.shouldFillUDCDisplayCutout == false) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x025b, code lost:
    
        if (r10 != 1) goto L122;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02bc  */
    /* JADX WARN: Type inference failed for: r0v33, types: [com.android.systemui.ScreenDecorations$6] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setupDecorations() {
        /*
            Method dump skipped, instructions count: 947
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorations.setupDecorations():void");
    }

    public final boolean shouldDrawCutout() {
        return this.mDebug ? this.mHwcScreenDecorationSupport != null ? this.mDebugCutoutFactory.getHasProviders() || this.mDebugCutoutFactory.isCameraProtectionEnabled : this.mDebugCutoutFactory.getHasProviders() : this.mCutoutFactory.getHasProviders();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showCameraProtection(android.graphics.Path r9, android.graphics.Rect r10) {
        /*
            r8 = this;
            com.android.systemui.decor.FaceScanningProviderFactory r9 = r8.mFaceScanningFactory
            boolean r0 = r9.getHasProviders()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L22
            com.android.keyguard.KeyguardUpdateMonitor r0 = r9.keyguardUpdateMonitor
            boolean r3 = r0.isFaceEnabledAndEnrolled()
            if (r3 == 0) goto L22
            boolean r0 = r0.isFaceDetectionRunning()
            if (r0 != 0) goto L20
            com.android.systemui.biometrics.AuthController r9 = r9.authController
            boolean r9 = r9.isShowing()
            if (r9 == 0) goto L22
        L20:
            r9 = r2
            goto L23
        L22:
            r9 = r1
        L23:
            com.android.systemui.log.ScreenDecorationsLogger r0 = r8.mLogger
            if (r9 == 0) goto L3f
            int r9 = r8.mFaceScanningViewId
            android.view.View r3 = r8.getOverlayView(r9)
            com.android.systemui.ScreenDecorations$DisplayCutoutView r3 = (com.android.systemui.ScreenDecorations.DisplayCutoutView) r3
            if (r3 == 0) goto L3f
            r0.cameraProtectionBoundsForScanningOverlay(r10)
            r3.enableShowProtection(r2)
            android.view.View r9 = r3.findViewById(r9)
            r8.updateOverlayWindowVisibilityIfViewExists(r9)
            return
        L3f:
            com.android.systemui.ScreenDecorHwcLayer r9 = r8.mScreenDecorHwcLayer
            if (r9 == 0) goto L4c
            r0.hwcLayerCameraProtectionBounds(r10)
            com.android.systemui.ScreenDecorHwcLayer r8 = r8.mScreenDecorHwcLayer
            r8.enableShowProtection(r2)
            return
        L4c:
            int[] r9 = com.android.systemui.ScreenDecorations.DISPLAY_CUTOUT_IDS
            int r3 = r9.length
            r4 = r1
        L50:
            if (r1 >= r3) goto L6a
            r5 = r9[r1]
            android.view.View r6 = r8.getOverlayView(r5)
            boolean r7 = r6 instanceof com.android.systemui.ScreenDecorations.DisplayCutoutView
            if (r7 != 0) goto L5d
            goto L67
        L5d:
            int r4 = r4 + 1
            com.android.systemui.ScreenDecorations$DisplayCutoutView r6 = (com.android.systemui.ScreenDecorations.DisplayCutoutView) r6
            r0.dcvCameraBounds(r5, r10)
            r6.enableShowProtection(r2)
        L67:
            int r1 = r1 + 1
            goto L50
        L6a:
            if (r4 != 0) goto L7b
            r0.getClass()
            com.android.systemui.log.core.LogLevel r8 = com.android.systemui.log.core.LogLevel.ERROR
            r9 = 0
            com.android.systemui.log.LogBuffer r10 = r0.logBuffer
            java.lang.String r0 = "ScreenDecorationsLog"
            java.lang.String r1 = "CutoutView not initialized showCameraProtection"
            r10.log(r0, r8, r1, r9)
        L7b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ScreenDecorations.showCameraProtection(android.graphics.Path, android.graphics.Rect):void");
    }

    public final void startOnScreenDecorationsThread() {
        Trace.beginSection("ScreenDecorations#startOnScreenDecorationsThread");
        this.mWindowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        DisplayInfo displayInfo = this.mDisplayInfo;
        this.mRotation = displayInfo.rotation;
        this.mDisplaySize.x = displayInfo.getNaturalWidth();
        this.mDisplaySize.y = this.mDisplayInfo.getNaturalHeight();
        DisplayInfo displayInfo2 = this.mDisplayInfo;
        this.mDisplayUniqueId = displayInfo2.uniqueId;
        this.mDisplayCutout = displayInfo2.displayCutout;
        RoundedCornerResDelegateImpl roundedCornerResDelegateImpl = new RoundedCornerResDelegateImpl(this.mContext.getResources(), this.mDisplayUniqueId);
        this.mRoundedCornerResDelegate = roundedCornerResDelegateImpl;
        float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio();
        if (roundedCornerResDelegateImpl.physicalPixelDisplaySizeRatio != physicalPixelDisplaySizeRatio) {
            roundedCornerResDelegateImpl.physicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
            roundedCornerResDelegateImpl.reloadMeasures();
        }
        this.mRoundedCornerFactory = new RoundedCornerDecorProviderFactory(this.mRoundedCornerResDelegate);
        this.mDebugRoundedCornerFactory = new RoundedCornerDecorProviderFactory(this.mDebugRoundedCornerDelegate);
        this.mCutoutFactory = new CutoutDecorProviderFactory(this.mContext.getResources(), this.mContext.getDisplay());
        if (this.mDebugCutoutFactory == null) {
            this.mDebugCutoutFactory = new CutoutDecorProviderFactory(this.mContext.getResources(), this.mContext.getDisplay());
        }
        this.mHwcScreenDecorationSupport = this.mContext.getDisplay().getDisplayDecorationSupport();
        updateHwLayerRoundedCornerDrawable();
        setupDecorations();
        setupCameraListener();
        if (isCoverDisplay().booleanValue()) {
            this.mContext.registerComponentCallbacks(new AnonymousClass4());
        }
        final PrivacyDotViewController privacyDotViewController = this.mDotViewController;
        privacyDotViewController.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$addSystemAnimationCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                PrivacyDotViewController privacyDotViewController2 = PrivacyDotViewController.this;
                ((SystemStatusAnimationSchedulerImpl) privacyDotViewController2.animationScheduler).addCallback(privacyDotViewController2.systemStatusAnimationCallback);
            }
        });
        privacyDotViewController.createListener = this.mPrivacyDotCreateListener;
        DisplayTracker.Callback callback = new DisplayTracker.Callback() { // from class: com.android.systemui.ScreenDecorations.5
            @Override // com.android.systemui.settings.DisplayTracker.Callback
            public final void onDisplayChanged(int i) {
                ScreenDecorations screenDecorations = ScreenDecorations.this;
                if (i != screenDecorations.mContext.getDisplayId()) {
                    return;
                }
                screenDecorations.mContext.getDisplay().getDisplayInfo(screenDecorations.mDisplayInfo);
                DisplayInfo displayInfo3 = screenDecorations.mDisplayInfo;
                int i2 = displayInfo3.rotation;
                if ((screenDecorations.mOverlays != null || screenDecorations.mScreenDecorHwcWindow != null) && (screenDecorations.mRotation != i2 || ScreenDecorations.displaySizeChanged(screenDecorations.mDisplaySize, displayInfo3))) {
                    Point point = new Point();
                    point.x = screenDecorations.mDisplayInfo.getNaturalWidth();
                    point.y = screenDecorations.mDisplayInfo.getNaturalHeight();
                    screenDecorations.mPendingConfigChange = true;
                    int i3 = screenDecorations.mRotation;
                    ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
                    if (i3 != i2) {
                        screenDecorationsLogger.logRotationChangeDeferred(i3, i2);
                    }
                    if (!screenDecorations.mDisplaySize.equals(point)) {
                        screenDecorationsLogger.logDisplaySizeChanged(screenDecorations.mDisplaySize, point);
                    }
                    if (screenDecorations.mOverlays != null) {
                        for (int i4 = 0; i4 < 4; i4++) {
                            OverlayWindow overlayWindow = screenDecorations.mOverlays[i4];
                            if (overlayWindow != null) {
                                RegionInterceptingFrameLayout regionInterceptingFrameLayout = overlayWindow.rootView;
                                regionInterceptingFrameLayout.getViewTreeObserver().addOnPreDrawListener(new RestartingPreDrawListener(ScreenDecorations.this, regionInterceptingFrameLayout, i4, i2, point, 0));
                            }
                        }
                    }
                    ViewGroup viewGroup = screenDecorations.mScreenDecorHwcWindow;
                    if (viewGroup != null) {
                        viewGroup.getViewTreeObserver().addOnPreDrawListener(new RestartingPreDrawListener(screenDecorations, screenDecorations.mScreenDecorHwcWindow, -1, i2, point, 0));
                    }
                    ScreenDecorHwcLayer screenDecorHwcLayer = screenDecorations.mScreenDecorHwcLayer;
                    if (screenDecorHwcLayer != null) {
                        screenDecorHwcLayer.pendingConfigChange = true;
                    }
                }
                String str = screenDecorations.mDisplayInfo.uniqueId;
                if (!Objects.equals(str, screenDecorations.mDisplayUniqueId)) {
                    screenDecorations.mDisplayUniqueId = str;
                    DisplayDecorationSupport displayDecorationSupport = screenDecorations.mContext.getDisplay().getDisplayDecorationSupport();
                    screenDecorations.mRoundedCornerResDelegate.updateDisplayUniqueId(str, null);
                    if (screenDecorations.hasSameProviders(screenDecorations.getProviders(displayDecorationSupport != null))) {
                        DisplayDecorationSupport displayDecorationSupport2 = screenDecorations.mHwcScreenDecorationSupport;
                        if (displayDecorationSupport != null ? displayDecorationSupport2 != null && displayDecorationSupport.format == displayDecorationSupport2.format && displayDecorationSupport.alphaInterpretation == displayDecorationSupport2.alphaInterpretation : displayDecorationSupport2 == null) {
                            if (screenDecorations.mPendingManualConfigUpdate) {
                                screenDecorations.mPendingManualConfigUpdate = false;
                                screenDecorations.onConfigChanged(screenDecorations.mContext.getResources().getConfiguration());
                            }
                        }
                    }
                    screenDecorations.mHwcScreenDecorationSupport = displayDecorationSupport;
                    screenDecorations.removeAllOverlays();
                    screenDecorations.setupDecorations();
                    return;
                }
                boolean semIsFitToActiveDisplay = screenDecorations.mDisplayManager.semIsFitToActiveDisplay();
                if (screenDecorations.mIsSmartViewFitToActiveDisplay != semIsFitToActiveDisplay) {
                    screenDecorations.mIsSmartViewFitToActiveDisplay = semIsFitToActiveDisplay;
                    screenDecorations.removeAllOverlays();
                    screenDecorations.setupDecorations();
                }
            }
        };
        this.mDisplayListener = callback;
        ((DisplayTrackerImpl) this.mDisplayTracker).addDisplayChangeCallback(callback, new HandlerExecutor(this.mHandler));
        updateConfiguration();
        this.mJavaAdapter.alwaysCollectFlow(((FacePropertyRepositoryImpl) this.mFacePropertyRepository).sensorLocation, new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ScreenDecorations.this.onFaceSensorLocationChanged((Point) obj);
            }
        });
        if (this.mIndicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
            this.mSettingsHelper.registerCallback(this.mFillUDCSettingsCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_FILL_UDC_DISPLAY_CUTOUT));
        }
        Trace.endSection();
    }

    public final void updateColorInversion(int i) {
        this.mTintColor = i != 0 ? -1 : -16777216;
        if (this.mDebug) {
            int i2 = this.mDebugColor;
            this.mTintColor = i2;
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
            if (debugRoundedCornerDelegate.color != i2) {
                debugRoundedCornerDelegate.color = i2;
                debugRoundedCornerDelegate.paint.setColor(i2);
            }
        }
        updateOverlayProviderViews(new Integer[]{Integer.valueOf(this.mFaceScanningViewId), Integer.valueOf(R.id.display_cutout), Integer.valueOf(R.id.display_cutout_left), Integer.valueOf(R.id.display_cutout_right), Integer.valueOf(R.id.display_cutout_bottom), Integer.valueOf(R.id.rounded_corner_top_left), Integer.valueOf(R.id.rounded_corner_top_right), Integer.valueOf(R.id.rounded_corner_bottom_left), Integer.valueOf(R.id.rounded_corner_bottom_right)});
    }

    public void updateConfiguration() {
        Preconditions.checkState(this.mHandler.getLooper().getThread() == Thread.currentThread(), "must call on " + this.mHandler.getLooper().getThread() + ", but was " + Thread.currentThread());
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        int i = this.mDisplayInfo.rotation;
        if (this.mRotation != i) {
            this.mDotViewController.setNewRotation(i);
        }
        DisplayCutout displayCutout = this.mDisplayInfo.displayCutout;
        IndicatorCutoutUtil indicatorCutoutUtil = this.mIndicatorCutoutUtil;
        if (indicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
        }
        if (!this.mPendingConfigChange && (i != this.mRotation || displaySizeChanged(this.mDisplaySize, this.mDisplayInfo) || !Objects.equals(displayCutout, this.mDisplayCutout))) {
            this.mRotation = i;
            this.mDisplaySize.x = this.mDisplayInfo.getNaturalWidth();
            this.mDisplaySize.y = this.mDisplayInfo.getNaturalHeight();
            this.mDisplayCutout = displayCutout;
            float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio();
            RoundedCornerResDelegateImpl roundedCornerResDelegateImpl = this.mRoundedCornerResDelegate;
            if (roundedCornerResDelegateImpl.physicalPixelDisplaySizeRatio != physicalPixelDisplaySizeRatio) {
                roundedCornerResDelegateImpl.physicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                roundedCornerResDelegateImpl.reloadMeasures();
            }
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
            if (debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio != physicalPixelDisplaySizeRatio) {
                debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                Drawable drawable = debugRoundedCornerDelegate.topRoundedDrawable;
                if (drawable != null) {
                    debugRoundedCornerDelegate.topRoundedSize = new Size(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                }
                Drawable drawable2 = debugRoundedCornerDelegate.bottomRoundedDrawable;
                if (drawable2 != null) {
                    debugRoundedCornerDelegate.bottomRoundedSize = new Size(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                }
                if (debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio != 1.0f) {
                    if (debugRoundedCornerDelegate.topRoundedSize.getWidth() != 0) {
                        debugRoundedCornerDelegate.topRoundedSize = new Size((int) ((debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio * debugRoundedCornerDelegate.topRoundedSize.getWidth()) + 0.5f), (int) ((debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio * debugRoundedCornerDelegate.topRoundedSize.getHeight()) + 0.5f));
                    }
                    if (debugRoundedCornerDelegate.bottomRoundedSize.getWidth() != 0) {
                        debugRoundedCornerDelegate.bottomRoundedSize = new Size((int) ((debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio * debugRoundedCornerDelegate.bottomRoundedSize.getWidth()) + 0.5f), (int) ((debugRoundedCornerDelegate.physicalPixelDisplaySizeRatio * debugRoundedCornerDelegate.bottomRoundedSize.getHeight()) + 0.5f));
                    }
                }
            }
            ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
            if (screenDecorHwcLayer != null) {
                screenDecorHwcLayer.pendingConfigChange = false;
                screenDecorHwcLayer.updateConfiguration(this.mDisplayUniqueId);
                updateHwLayerRoundedCornerExistAndSize();
                updateHwLayerRoundedCornerDrawable();
            }
            updateLayoutParams();
            updateOverlayProviderViews(null);
        }
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.faceScanningAnimColor = Utils.getColorAttrDefaultColor(faceScanningOverlay.getContext(), R.attr.wallpaperTextColorAccent, 0);
        }
        if (indicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
        }
    }

    public final void updateFillUDCDisplayCutout() {
        boolean z = this.mIndicatorCutoutUtil.isUDCMainDisplay() && this.mSettingsHelper.isFillUDCDisplayCutoutEnabled();
        CutoutDecorProviderFactory cutoutDecorProviderFactory = this.mCutoutFactory;
        if (z != cutoutDecorProviderFactory.shouldFillUDCDisplayCutout) {
            cutoutDecorProviderFactory.shouldFillUDCDisplayCutout = z;
            this.mHandler.post(new ScreenDecorations$$ExternalSyntheticLambda4(this, z, 0));
        }
        if (!BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC || this.blockUpdateStatusIconContainerLayout) {
            return;
        }
        this.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda4(this, this.mCutoutFactory.shouldFillUDCDisplayCutout, 1));
    }

    public final void updateHwLayerRoundedCornerDrawable() {
        if (this.mScreenDecorHwcLayer == null) {
            return;
        }
        RoundedCornerResDelegateImpl roundedCornerResDelegateImpl = this.mRoundedCornerResDelegate;
        Drawable drawable = roundedCornerResDelegateImpl.topRoundedDrawable;
        Drawable drawable2 = roundedCornerResDelegateImpl.bottomRoundedDrawable;
        if (this.mDebug && this.mDebugRoundedCornerFactory.getHasProviders()) {
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
            drawable = debugRoundedCornerDelegate.topRoundedDrawable;
            drawable2 = debugRoundedCornerDelegate.bottomRoundedDrawable;
        }
        if (drawable == null && drawable2 == null) {
            return;
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        screenDecorHwcLayer.roundedCornerDrawableTop = drawable;
        screenDecorHwcLayer.roundedCornerDrawableBottom = drawable2;
        if (drawable != null) {
            int i = screenDecorHwcLayer.roundedCornerTopSize;
            drawable.setBounds(0, 0, i, i);
        }
        Drawable drawable3 = screenDecorHwcLayer.roundedCornerDrawableBottom;
        if (drawable3 != null) {
            int i2 = screenDecorHwcLayer.roundedCornerBottomSize;
            drawable3.setBounds(0, 0, i2, i2);
        }
        screenDecorHwcLayer.invalidate();
        screenDecorHwcLayer.invalidate();
    }

    public final void updateHwLayerRoundedCornerExistAndSize() {
        if (this.mScreenDecorHwcLayer == null) {
            return;
        }
        if (this.mDebug && this.mDebugRoundedCornerFactory.getHasProviders()) {
            ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
            screenDecorHwcLayer.updateRoundedCornerExistenceAndSize(debugRoundedCornerDelegate.topRoundedSize.getWidth(), this.mDebugRoundedCornerDelegate.bottomRoundedSize.getWidth(), debugRoundedCornerDelegate.hasTop, debugRoundedCornerDelegate.hasBottom);
            return;
        }
        ScreenDecorHwcLayer screenDecorHwcLayer2 = this.mScreenDecorHwcLayer;
        RoundedCornerResDelegateImpl roundedCornerResDelegateImpl = this.mRoundedCornerResDelegate;
        screenDecorHwcLayer2.updateRoundedCornerExistenceAndSize(roundedCornerResDelegateImpl.topRoundedSize.getWidth(), this.mRoundedCornerResDelegate.bottomRoundedSize.getWidth(), roundedCornerResDelegateImpl.hasTop, roundedCornerResDelegateImpl.hasBottom);
    }

    public final void updateLayoutParams() {
        Trace.beginSection("ScreenDecorations#updateLayoutParams");
        ViewGroup viewGroup = this.mScreenDecorHwcWindow;
        if (viewGroup != null) {
            this.mWindowManager.updateViewLayout(viewGroup, getHwcWindowLayoutParams());
        }
        if (this.mOverlays != null) {
            for (int i = 0; i < 4; i++) {
                OverlayWindow overlayWindow = this.mOverlays[i];
                if (overlayWindow != null) {
                    this.mWindowManager.updateViewLayout(overlayWindow.rootView, getWindowLayoutParams(i));
                }
            }
        }
        Trace.endSection();
    }

    public void updateOverlayProviderViews(Integer[] numArr) {
        String str;
        Unit unit;
        int i;
        int i2;
        String str2;
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null || this.mPendingConfigChange) {
            return;
        }
        this.mProviderRefreshToken++;
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null) {
                int i3 = this.mProviderRefreshToken;
                int i4 = this.mRotation;
                int i5 = this.mTintColor;
                String str3 = this.mDisplayUniqueId;
                if (numArr != null) {
                    int length = numArr.length;
                    int i6 = 0;
                    while (i6 < length) {
                        Pair pair = (Pair) ((LinkedHashMap) overlayWindow.viewProviderMap).get(Integer.valueOf(numArr[i6].intValue()));
                        if (pair != null) {
                            i = i6;
                            i2 = length;
                            str2 = str3;
                            ((DecorProvider) pair.getSecond()).onReloadResAndMeasure((View) pair.getFirst(), i3, i4, i5, str3);
                        } else {
                            i = i6;
                            i2 = length;
                            str2 = str3;
                        }
                        i6 = i + 1;
                        length = i2;
                        str3 = str2;
                    }
                    str = str3;
                    unit = Unit.INSTANCE;
                } else {
                    str = str3;
                    unit = null;
                }
                if (unit == null) {
                    for (Pair pair2 : ((LinkedHashMap) overlayWindow.viewProviderMap).values()) {
                        ((DecorProvider) pair2.getSecond()).onReloadResAndMeasure((View) pair2.getFirst(), i3, i4, i5, str);
                    }
                }
            }
        }
    }

    public void updateOverlayWindowVisibilityIfViewExists(View view) {
        if (view == null) {
            return;
        }
        this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda2(this, view));
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.ScreenDecorations$4, reason: invalid class name */
    public final class AnonymousClass4 implements ComponentCallbacks {
        public AnonymousClass4() {
        }

        @Override // android.content.ComponentCallbacks
        public final void onConfigurationChanged(Configuration configuration) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (screenDecorations.mOverlays[1] != null) {
                screenDecorations.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(this, 6));
            }
        }

        @Override // android.content.ComponentCallbacks
        public final void onLowMemory() {
        }
    }
}
