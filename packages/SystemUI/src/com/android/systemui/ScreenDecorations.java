package com.android.systemui;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import android.util.DisplayMetrics;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.decor.CoverRoundedCornerDecorProviderFactory;
import com.android.systemui.decor.CoverRoundedCornerResDelegate;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DebugRoundedCornerDelegate;
import com.android.systemui.decor.DecorProvider;
import com.android.systemui.decor.DecorProviderFactory;
import com.android.systemui.decor.FaceScanningProviderFactory;
import com.android.systemui.decor.FaceScanningProviderFactoryImpl;
import com.android.systemui.decor.OverlayWindow;
import com.android.systemui.decor.PathDrawable;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerResDelegate;
import com.android.systemui.decor.RoundedCornerResDelegateImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.log.ScreenDecorationsLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.UserSettingObserver;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl;
import com.android.systemui.statusbar.events.ViewState;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.unfold.system.DeviceStateManagerFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
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
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public class ScreenDecorations implements ConfigurationController.ConfigurationListener, Dumpable {
    private SettingsHelper.OnChangedCallback mAODStateSettingsCallback;
    public CameraAvailabilityListener mCameraListener;
    public final CameraProtectionLoader mCameraProtectionLoader;
    public AnonymousClass7 mColorInversionSetting;
    public final CommandRegistry mCommandRegistry;
    public final Context mContext;
    public CutoutDecorProviderFactory mCutoutFactory;
    public CutoutDecorProviderFactory mDebugCutoutFactory;
    public RoundedCornerDecorProviderFactory mDebugRoundedCornerFactory;
    public final DeviceStateManagerFoldProvider mDeviceStateManagerFoldProvider;
    public DisplayCutout mDisplayCutout;
    DisplayTracker.Callback mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    String mDisplayUniqueId;
    public final PrivacyDotDecorProviderFactory mDotFactory;
    public final PrivacyDotViewController mDotViewController;
    public final DelayableExecutor mExecutor;
    public final FacePropertyRepository mFacePropertyRepository;
    public final FaceScanningProviderFactory mFaceScanningFactory;
    private SettingsHelper.OnChangedCallback mFillUDCSettingsCallback;
    public final Handler mHandler;
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
    protected RoundedCornerResDelegate mRoundedCornerResDelegate;
    ScreenDecorHwcLayer mScreenDecorHwcLayer;
    ViewGroup mScreenDecorHwcWindow;
    public final SecureSettings mSecureSettings;
    private final SettingsHelper mSettingsHelper;
    public final UserTracker mUserTracker;
    public WindowManager mWindowManager;
    public static final boolean DEBUG_DISABLE_SCREEN_DECORATIONS = SystemProperties.getBoolean("debug.disable_screen_decorations", false);
    public static final boolean DEBUG_SCREENSHOT_ROUNDED_CORNERS = SystemProperties.getBoolean("debug.screenshot_rounded_corners", false);
    public static final boolean sToolkitSetFrameRateReadOnly = true;
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
    PrivacyDotViewController.ShowingListener mPrivacyDotShowingListener = new PrivacyDotViewController.ShowingListener() { // from class: com.android.systemui.ScreenDecorations.2
    };
    PrivacyDotViewController.CreateListener mPrivacyDotCreateListener = new AnonymousClass3();
    public final ScreenDecorations$$ExternalSyntheticLambda0 mScreenDecorCommandCallback = new ScreenDecorations$$ExternalSyntheticLambda0(this);
    public final ScreenDecorations$$ExternalSyntheticLambda2 mFoldCallback = new FoldProvider.FoldCallback() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2
        @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
        public final void onFoldUpdated(boolean z) {
            PrivacyDotViewController privacyDotViewController = this.f$0.mDotViewController;
            if (privacyDotViewController != null) {
                for (View view : ((PrivacyDotViewControllerImpl) privacyDotViewController).getViews()) {
                    view.clearAnimation();
                    view.setVisibility(4);
                }
            }
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.ScreenDecorations.9
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
            screenDecorationsLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda0 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(9);
            LogBuffer logBuffer = screenDecorationsLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            screenDecorations.mColorInversionSetting.setUserId(i);
            screenDecorations.updateColorInversion(screenDecorations.mColorInversionSetting.getValue());
        }
    };
    public final int mFaceScanningViewId = R.id.face_scanning_anim;

    /* renamed from: com.android.systemui.ScreenDecorations$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onApplyCameraProtection(Path path, Rect rect) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
            screenDecorationsLogger.getClass();
            LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "onApplyCameraProtection");
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
                screenDecorations.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda6(screenDecorations, z, 1));
            }
        }
    }

    /* renamed from: com.android.systemui.ScreenDecorations$3, reason: invalid class name */
    public class AnonymousClass3 implements PrivacyDotViewController.CreateListener {
        public AnonymousClass3() {
        }
    }

    public class DisplayCutoutView extends DisplayCutoutBaseView {
        public final Rect mBoundingRect;
        public final List mBounds;
        public int mColor;
        public boolean mDebug;
        public final int mInitialPosition;
        public int mPosition;
        public int mRotation;
        public final Rect mTotalBounds;

        public DisplayCutoutView(Context context, int i) {
            super(context);
            this.mBounds = new ArrayList();
            this.mBoundingRect = new Rect();
            this.mTotalBounds = new Rect();
            this.mDebug = false;
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

        public void setColor(int i) {
            if (i == this.mColor) {
                return;
            }
            this.mColor = i;
            this.paint.setColor(i);
            this.paintForCameraProtection.setColor(this.mColor);
            invalidate();
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x008a  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x0114  */
        @Override // com.android.systemui.DisplayCutoutBaseView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void updateCutout() {
            boolean zIsEmpty;
            boolean z;
            if (!isAttachedToWindow() || this.pendingConfigChange) {
                return;
            }
            this.mPosition = ScreenDecorations.getBoundPositionFromRotation(this.mInitialPosition, this.mRotation);
            requestLayout();
            getDisplay().getDisplayInfo(this.displayInfo);
            ((ArrayList) this.mBounds).clear();
            this.mBoundingRect.setEmpty();
            this.cutoutPath.reset();
            Context context = getContext();
            int i = 0;
            boolean z2 = true;
            if (DisplayCutout.getFillBuiltInDisplayCutout(context.getResources(), context.getDisplay().getUniqueId()) || this.isCameraProtectionEnabled) {
                DisplayCutout displayCutout = this.displayInfo.displayCutout;
                if (displayCutout != null) {
                    int i2 = this.mPosition;
                    if (i2 == 0) {
                        zIsEmpty = displayCutout.getBoundingRectLeft().isEmpty();
                    } else if (i2 == 1) {
                        zIsEmpty = displayCutout.getBoundingRectTop().isEmpty();
                    } else if (i2 == 3) {
                        zIsEmpty = displayCutout.getBoundingRectBottom().isEmpty();
                    } else {
                        if (i2 == 2) {
                            zIsEmpty = displayCutout.getBoundingRectRight().isEmpty();
                        }
                        z = false;
                        if (z) {
                            ((ArrayList) this.mBounds).addAll(this.displayInfo.displayCutout.getBoundingRects());
                            Rect rect = this.mBoundingRect;
                            DisplayCutout displayCutout2 = this.displayInfo.displayCutout;
                            boundsFromDirection(getGravity(displayCutout2), rect, displayCutout2);
                            if (this.isCameraProtectionEnabled) {
                                int iCeil = (int) Math.ceil(this.cameraProtectionStrokeWidth / 2.0d);
                                int i3 = this.mPosition;
                                if (i3 == 0) {
                                    this.mBoundingRect.right += iCeil;
                                } else if (i3 == 1) {
                                    this.mBoundingRect.bottom += iCeil;
                                } else if (i3 == 2) {
                                    this.mBoundingRect.left -= iCeil;
                                } else if (i3 == 3) {
                                    this.mBoundingRect.top -= iCeil;
                                }
                            }
                            ViewGroup.LayoutParams layoutParams = getLayoutParams();
                            if (layoutParams instanceof FrameLayout.LayoutParams) {
                                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                                int gravity = getGravity(this.displayInfo.displayCutout);
                                if (layoutParams2.gravity != gravity) {
                                    layoutParams2.gravity = gravity;
                                    setLayoutParams(layoutParams2);
                                }
                            }
                            Path cutoutPath = this.displayInfo.displayCutout.getCutoutPath();
                            if (cutoutPath != null) {
                                this.cutoutPath.set(cutoutPath);
                            } else {
                                this.cutoutPath.reset();
                            }
                            invalidate();
                        } else {
                            if (this.cutoutUtil.isUDCMainDisplay() && this.settingsHelper.isFillUDCDisplayCutoutEnabled()) {
                                Path path = this.protectionPathOrig;
                                Resources resources = getResources();
                                DisplayInfo displayInfo = this.displayInfo;
                                int i4 = this.initialDisplayWidth;
                                int i5 = this.initialDisplayDensity;
                                int i6 = displayInfo.logicalWidth;
                                int i7 = displayInfo.logicalHeight;
                                int i8 = displayInfo.rotation;
                                if (i8 != 1 && i8 != 3) {
                                    z2 = false;
                                }
                                int i9 = z2 ? i7 : i6;
                                if (!z2) {
                                    i6 = i7;
                                }
                                path.set(DisplayCutout.pathFromResourcesForUDC(resources, displayInfo.uniqueId, i9, i6, i4 <= 0 ? DisplayMetrics.DENSITY_DEVICE_STABLE : (i5 * i9) / i4, false));
                                RectF rectF = this.protectionRectOrig;
                                Path path2 = this.protectionPathOrig;
                                RectF rectF2 = new RectF();
                                path2.computeBounds(rectF2, false);
                                rectF.set(new Rect(MathKt__MathJVMKt.roundToInt(rectF2.left), MathKt__MathJVMKt.roundToInt(rectF2.top), MathKt__MathJVMKt.roundToInt(rectF2.right), MathKt__MathJVMKt.roundToInt(rectF2.bottom)));
                                invalidate();
                            } else {
                                i = 8;
                            }
                        }
                    }
                    z = !zIsEmpty;
                    if (z) {
                    }
                } else {
                    z = false;
                    if (z) {
                    }
                }
            }
            if ((this instanceof FaceScanningOverlay) || i == getVisibility()) {
                return;
            }
            setVisibility(i);
        }
    }

    public class RestartingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final Point mTargetDisplaySize;
        public final int mTargetRotation;
        public final View mView;

        public /* synthetic */ RestartingPreDrawListener(ScreenDecorations screenDecorations, View view, int i, int i2, Point point, int i3) {
            this(view, i, i2, point);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() throws Throwable {
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

    public class ValidatingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
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

    public static void $r8$lambda$7pjpOGOJ1T5anTLKcozNmi9iJVc(ScreenDecorations screenDecorations, Uri uri) {
        OverlayWindow[] overlayWindowArr;
        OverlayWindow overlayWindow;
        final View view;
        if (!uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE)) || !screenDecorations.hasOverlays() || !screenDecorations.mRoundedCornerFactory.getHasProviders() || (overlayWindowArr = screenDecorations.mOverlays) == null || (overlayWindow = overlayWindowArr[1]) == null || (view = overlayWindow.getView(R.id.rounded_corner_cover)) == null) {
            return;
        }
        boolean zIsAODShown = screenDecorations.mSettingsHelper.isAODShown();
        DelayableExecutor delayableExecutor = screenDecorations.mExecutor;
        if (zIsAODShown && screenDecorations.mTintColor == -1) {
            final int i = 0;
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = i;
                    View view2 = view;
                    switch (i2) {
                        case 0:
                            boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            view2.setVisibility(4);
                            break;
                        default:
                            boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            view2.setVisibility(0);
                            break;
                    }
                }
            });
        } else if (view.getVisibility() != 0) {
            final int i2 = 1;
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i2;
                    View view2 = view;
                    switch (i22) {
                        case 0:
                            boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            view2.setVisibility(4);
                            break;
                        default:
                            boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            view2.setVisibility(0);
                            break;
                    }
                }
            });
        }
    }

    /* renamed from: $r8$lambda$Ko-NSRBrMuoZsUyWIX90PQvGYvA, reason: not valid java name */
    public static void m999$r8$lambda$KoNSRBrMuoZsUyWIX90PQvGYvA(ScreenDecorations screenDecorations) throws Throwable {
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
            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
            screenDecorationsLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda0 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(8);
            LogBuffer logBuffer = screenDecorationsLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = i;
            logMessageImpl.int2 = i2;
            logBuffer.commit(logMessageObtain);
        }
        screenDecorations.setupDecorations();
        if (screenDecorations.mOverlays != null) {
            screenDecorations.updateLayoutParams();
        }
        Trace.endSection();
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2] */
    public ScreenDecorations(Context context, SecureSettings secureSettings, CommandRegistry commandRegistry, UserTracker userTracker, DisplayTracker displayTracker, PrivacyDotViewController privacyDotViewController, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, FaceScanningProviderFactory faceScanningProviderFactory, ScreenDecorationsLogger screenDecorationsLogger, FacePropertyRepository facePropertyRepository, JavaAdapter javaAdapter, CameraProtectionLoader cameraProtectionLoader, WindowManager windowManager, Handler handler, DelayableExecutor delayableExecutor, SettingsHelper settingsHelper, IndicatorCutoutUtil indicatorCutoutUtil, IndicatorGardenPresenter indicatorGardenPresenter, Executor executor, ConfigurationController configurationController, DeviceStateManagerFoldProvider deviceStateManagerFoldProvider) {
        final int i = 0;
        this.mAODStateSettingsCallback = new SettingsHelper.OnChangedCallback(this) { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1
            public final /* synthetic */ ScreenDecorations f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                ScreenDecorations screenDecorations = this.f$0;
                switch (i) {
                    case 0:
                        ScreenDecorations.$r8$lambda$7pjpOGOJ1T5anTLKcozNmi9iJVc(screenDecorations, uri);
                        break;
                    default:
                        boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                        screenDecorations.getClass();
                        if (uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_FILL_UDC_DISPLAY_CUTOUT))) {
                            Log.d("ScreenDecorations", uri.toString() + " changed");
                            screenDecorations.updateFillUDCDisplayCutout();
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mFillUDCSettingsCallback = new SettingsHelper.OnChangedCallback(this) { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1
            public final /* synthetic */ ScreenDecorations f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                ScreenDecorations screenDecorations = this.f$0;
                switch (i2) {
                    case 0:
                        ScreenDecorations.$r8$lambda$7pjpOGOJ1T5anTLKcozNmi9iJVc(screenDecorations, uri);
                        break;
                    default:
                        boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                        screenDecorations.getClass();
                        if (uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_FILL_UDC_DISPLAY_CUTOUT))) {
                            Log.d("ScreenDecorations", uri.toString() + " changed");
                            screenDecorations.updateFillUDCDisplayCutout();
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        this.mSecureSettings = secureSettings;
        this.mCommandRegistry = commandRegistry;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mDotViewController = privacyDotViewController;
        this.mDotFactory = privacyDotDecorProviderFactory;
        this.mFaceScanningFactory = faceScanningProviderFactory;
        this.mCameraProtectionLoader = cameraProtectionLoader;
        this.mLogger = screenDecorationsLogger;
        this.mFacePropertyRepository = facePropertyRepository;
        this.mJavaAdapter = javaAdapter;
        this.mWindowManager = windowManager;
        this.mHandler = handler;
        this.mExecutor = delayableExecutor;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && !isCoverDisplay().booleanValue()) {
            this.mDeviceStateManagerFoldProvider = deviceStateManagerFoldProvider;
        }
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

    public static WindowManager.LayoutParams getWindowLayoutBaseParams(boolean z) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 536871224, -3);
        int i = layoutParams.privateFlags;
        layoutParams.privateFlags = 536870992 | i;
        if (!DEBUG_SCREENSHOT_ROUNDED_CORNERS && z) {
            layoutParams.privateFlags = 537919568 | i;
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x00af  */
    @Override // com.android.systemui.Dumpable
    @NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ScreenDecorations state:");
        PrintWriter printWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        printWriterAsIndenting.increaseIndent();
        StringBuilder sb = new StringBuilder("DEBUG_DISABLE_SCREEN_DECORATIONS:");
        boolean z = DEBUG_DISABLE_SCREEN_DECORATIONS;
        sb.append(z);
        printWriterAsIndenting.println(sb.toString());
        if (z) {
            return;
        }
        printWriterAsIndenting.println("mDebug:" + this.mDebug);
        printWriterAsIndenting.println("mIsPrivacyDotEnabled:" + this.mDotFactory.getHasProviders());
        printWriterAsIndenting.println("shouldOptimizeOverlayVisibility:false");
        FaceScanningProviderFactoryImpl faceScanningProviderFactoryImpl = (FaceScanningProviderFactoryImpl) this.mFaceScanningFactory;
        boolean hasProviders = faceScanningProviderFactoryImpl.getHasProviders();
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("supportsShowingFaceScanningAnim:", hasProviders, printWriterAsIndenting);
        if (hasProviders) {
            printWriterAsIndenting.increaseIndent();
            StringBuilder sb2 = new StringBuilder("canShowFaceScanningAnim:");
            sb2.append(faceScanningProviderFactoryImpl.getHasProviders() && faceScanningProviderFactoryImpl.keyguardUpdateMonitor.isFaceEnabledAndEnrolled());
            printWriterAsIndenting.println(sb2.toString());
            StringBuilder sb3 = new StringBuilder("shouldShowFaceScanningAnim (at time dump was taken):");
            if (faceScanningProviderFactoryImpl.getHasProviders()) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = faceScanningProviderFactoryImpl.keyguardUpdateMonitor;
                boolean z2 = keyguardUpdateMonitor.isFaceEnabledAndEnrolled() && (keyguardUpdateMonitor.isFaceDetectionRunning() || faceScanningProviderFactoryImpl.authController.isShowing());
                sb3.append(z2);
                printWriterAsIndenting.println(sb3.toString());
                printWriterAsIndenting.decreaseIndent();
            }
        }
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.dump(printWriterAsIndenting);
        }
        printWriterAsIndenting.println("mPendingConfigChange:" + this.mPendingConfigChange);
        if (this.mHwcScreenDecorationSupport != null) {
            printWriterAsIndenting.increaseIndent();
            printWriterAsIndenting.println("mHwcScreenDecorationSupport:");
            printWriterAsIndenting.increaseIndent();
            printWriterAsIndenting.println("format=" + PixelFormat.formatToString(this.mHwcScreenDecorationSupport.format));
            StringBuilder sb4 = new StringBuilder("alphaInterpretation=");
            int i = this.mHwcScreenDecorationSupport.alphaInterpretation;
            sb4.append(i != 0 ? i != 1 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown: ") : "MASK" : "COVERAGE");
            printWriterAsIndenting.println(sb4.toString());
            printWriterAsIndenting.decreaseIndent();
            printWriterAsIndenting.decreaseIndent();
        } else {
            printWriterAsIndenting.increaseIndent();
            printWriter.println("mHwcScreenDecorationSupport: null");
            printWriterAsIndenting.decreaseIndent();
        }
        if (this.mScreenDecorHwcLayer != null) {
            printWriterAsIndenting.increaseIndent();
            this.mScreenDecorHwcLayer.dump(printWriterAsIndenting);
            printWriterAsIndenting.decreaseIndent();
        } else {
            printWriterAsIndenting.println("mScreenDecorHwcLayer: null");
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
            printWriterAsIndenting.println(sb5.toString());
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
        RoundedCornerResDelegate roundedCornerResDelegate = this.mRoundedCornerResDelegate;
        if (roundedCornerResDelegate != null) {
            roundedCornerResDelegate.dump(printWriter, strArr);
        }
        this.mDebugRoundedCornerDelegate.dump(printWriter, strArr);
    }

    public WindowManager.LayoutParams getCoverWindowLayoutParams() {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams(true);
        windowLayoutBaseParams.width = -1;
        windowLayoutBaseParams.height = -1;
        windowLayoutBaseParams.setTitle("ScreenDecorOverlayCover");
        windowLayoutBaseParams.gravity = 17;
        return windowLayoutBaseParams;
    }

    public final WindowManager.LayoutParams getHwcWindowLayoutParams() {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams(true);
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
        arrayList.addAll(((FaceScanningProviderFactoryImpl) this.mFaceScanningFactory).getProviders());
        if (!z && !DeviceType.isFactoryBinary() && !this.mIsSmartViewFitToActiveDisplay) {
            if (this.mDebug && this.mDebugRoundedCornerFactory.getHasProviders()) {
                arrayList.addAll(this.mDebugRoundedCornerFactory.getProviders());
            } else {
                arrayList.addAll(this.mRoundedCornerFactory.getProviders());
            }
            if (this.mDebug) {
                arrayList.addAll(this.mDebugCutoutFactory.getProviders());
                return arrayList;
            }
            arrayList.addAll(this.mCutoutFactory.getProviders());
        }
        return arrayList;
    }

    public WindowManager.LayoutParams getWindowLayoutParams(int i) {
        if (isCoverDisplay().booleanValue()) {
            return getCoverWindowLayoutParams();
        }
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams(true);
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
        if (arrayList.size() == list.size()) {
            Iterator<DecorProvider> it = list.iterator();
            while (it.hasNext()) {
                if (!arrayList.contains(Integer.valueOf(it.next().getViewId()))) {
                }
            }
            return true;
        }
        return false;
    }

    public void hideCameraProtection() {
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.hideOverlayRunnable = new ScreenDecorations$$ExternalSyntheticLambda4(this, faceScanningOverlay);
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
        if (list.size() != overlayWindow.viewProviderMap.size()) {
            list.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    View view;
                    ScreenDecorations screenDecorations = this.f$0;
                    OverlayWindow overlayWindow2 = overlayWindow;
                    DecorProvider decorProvider = (DecorProvider) obj;
                    boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
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
            break;
        }
        List list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                if (overlayWindow.getView(((DecorProvider) it.next()).getViewId()) == null) {
                    list.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            View view;
                            ScreenDecorations screenDecorations = this.f$0;
                            OverlayWindow overlayWindow2 = overlayWindow;
                            DecorProvider decorProvider = (DecorProvider) obj;
                            boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
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
                    break;
                }
            }
        }
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
            this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda5(this, 0));
        }
    }

    public void onFaceSensorLocationChanged(Point point) {
        ScreenDecorationsLogger screenDecorationsLogger = this.mLogger;
        screenDecorationsLogger.getClass();
        LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "AuthControllerCallback in ScreenDecorations triggered");
        DelayableExecutor delayableExecutor = this.mExecutor;
        if (delayableExecutor != null) {
            delayableExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda5(this, 4));
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
                WindowManager windowManager = this.mWindowManager;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(overlayWindow.rootView);
                }
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
        WindowManager windowManager = this.mWindowManager;
        if (windowManager != null) {
            windowManager.removeViewImmediate(viewGroup);
        }
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
        this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda5(this, 5));
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
            factory.getClass();
            CameraAvailabilityListener cameraAvailabilityListener3 = new CameraAvailabilityListener((CameraManager) context.getSystemService("camera"), ((CameraProtectionLoaderImpl) this.mCameraProtectionLoader).loadCameraProtectionInfoList(), context.getResources().getString(R.string.config_cameraProtectionExcludedPackages), this.mExecutor, this.mHandler);
            this.mCameraListener = cameraAvailabilityListener3;
            ((ArrayList) cameraAvailabilityListener3.listeners).add(anonymousClass1);
            CameraAvailabilityListener cameraAvailabilityListener4 = this.mCameraListener;
            cameraAvailabilityListener4.cameraManager.registerSemCameraDeviceStateCallback(cameraAvailabilityListener4.cameraDeviceStateCallback, cameraAvailabilityListener4.handler);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0293  */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.ScreenDecorations$7] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setupDecorations() {
        Object next;
        Integer num;
        PrivacyDotViewController privacyDotViewController;
        View overlayView;
        View overlayView2;
        View overlayView3;
        OverlayWindow[] overlayWindowArr;
        OverlayWindow overlayWindow;
        ScreenDecorHwcLayer screenDecorHwcLayer;
        ScreenDecorations screenDecorations;
        Trace.beginSection("ScreenDecorations#setupDecorations");
        boolean zHasRoundedCorners = hasRoundedCorners();
        PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory = this.mDotFactory;
        if (zHasRoundedCorners || shouldDrawCutout() || privacyDotDecorProviderFactory.getHasProviders() || ((FaceScanningProviderFactoryImpl) this.mFaceScanningFactory).getHasProviders()) {
            List<DecorProvider> providers = getProviders(this.mHwcScreenDecorationSupport != null);
            if (this.mOverlays != null) {
                int[] array = providers.stream().mapToInt(new ScreenDecorations$$ExternalSyntheticLambda15()).toArray();
                for (OverlayWindow overlayWindow2 : this.mOverlays) {
                    if (overlayWindow2 != null) {
                        Iterator it = CollectionsKt___CollectionsKt.toList(((LinkedHashMap) overlayWindow2.viewProviderMap).keySet()).iterator();
                        while (it.hasNext()) {
                            int iIntValue = ((Number) it.next()).intValue();
                            if (array == null || ArraysKt___ArraysKt.indexOf(iIntValue, array) < 0) {
                                View view = overlayWindow2.getView(iIntValue);
                                if (view != null) {
                                    overlayWindow2.rootView.removeView(view);
                                    overlayWindow2.viewProviderMap.remove(Integer.valueOf(iIntValue));
                                }
                            }
                        }
                    }
                }
            }
            if (this.mHwcScreenDecorationSupport == null || DeviceType.isFactoryBinary() || this.mIsSmartViewFitToActiveDisplay || !(hasRoundedCorners() || shouldDrawCutout())) {
                removeHwcOverlay();
            } else if (this.mScreenDecorHwcWindow == null) {
                this.mScreenDecorHwcWindow = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.screen_decor_hwc_layer, (ViewGroup) null);
                if (isCoverDisplay().booleanValue()) {
                    this.mScreenDecorHwcLayer = new CoverScreenDecorHwcLayer(this.mContext, this.mHwcScreenDecorationSupport, this.mDebug);
                } else {
                    this.mScreenDecorHwcLayer = new ScreenDecorHwcLayer(this.mContext, this.mHwcScreenDecorationSupport, this.mDebug);
                }
                this.mScreenDecorHwcWindow.addView(this.mScreenDecorHwcLayer, new FrameLayout.LayoutParams(-1, -1, 8388659));
                WindowManager windowManager = this.mWindowManager;
                if (windowManager != null) {
                    windowManager.addView(this.mScreenDecorHwcWindow, getHwcWindowLayoutParams());
                }
                updateHwLayerRoundedCornerExistAndSize();
                updateHwLayerRoundedCornerDrawable();
                boolean z = this.mDebug;
                if (z && (screenDecorHwcLayer = this.mScreenDecorHwcLayer) != null && z) {
                    screenDecorHwcLayer.isCameraProtectionEnabled = this.mDebugCutoutFactory.isCameraProtectionEnabled;
                    screenDecorHwcLayer.updateCutout();
                    ScreenDecorHwcLayer screenDecorHwcLayer2 = this.mScreenDecorHwcLayer;
                    int i = this.mDebugCutoutFactory.cameraProtectionStrokeWidth;
                    screenDecorHwcLayer2.cameraProtectionStrokeWidth = i;
                    screenDecorHwcLayer2.paintForCameraProtection.setStrokeWidth(i);
                    screenDecorHwcLayer2.updateCutout();
                }
                this.mScreenDecorHwcWindow.getViewTreeObserver().addOnPreDrawListener(new ValidatingPreDrawListener(this.mScreenDecorHwcWindow));
            }
            boolean[] zArr = new boolean[4];
            while (true) {
                if (providers.isEmpty()) {
                    num = null;
                } else {
                    Iterator<T> it2 = providers.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            next = it2.next();
                            if (((DecorProvider) next).getAlignedBounds().size() == 1) {
                                break;
                            }
                        } else {
                            next = null;
                            break;
                        }
                    }
                    DecorProvider decorProvider = (DecorProvider) next;
                    if (decorProvider != null) {
                        num = (Integer) decorProvider.getAlignedBounds().get(0);
                    } else {
                        int[] iArr = new int[4];
                        iArr[0] = 0;
                        iArr[1] = 0;
                        iArr[2] = 0;
                        iArr[3] = 0;
                        Iterator<DecorProvider> it3 = providers.iterator();
                        while (it3.hasNext()) {
                            Iterator it4 = it3.next().getAlignedBounds().iterator();
                            while (it4.hasNext()) {
                                int iIntValue2 = ((Number) it4.next()).intValue();
                                iArr[iIntValue2] = iArr[iIntValue2] + 1;
                            }
                        }
                        Integer[] numArr = {1, 3, 0, 2};
                        int i2 = 0;
                        Integer num2 = null;
                        for (int i3 = 0; i3 < 4; i3++) {
                            Integer num3 = numArr[i3];
                            int i4 = iArr[num3.intValue()];
                            if (i4 > i2) {
                                num2 = num3;
                                i2 = i4;
                            }
                        }
                        num = num2;
                    }
                }
                privacyDotViewController = this.mDotViewController;
                if (num == null) {
                    break;
                }
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : providers) {
                    if (((DecorProvider) obj).getAlignedBounds().contains(num)) {
                        arrayList.add(obj);
                    } else {
                        arrayList2.add(obj);
                    }
                }
                Pair pair = new Pair(arrayList, arrayList2);
                List<DecorProvider> list = (List) pair.getSecond();
                int iIntValue3 = num.intValue();
                if (isCoverDisplay().booleanValue()) {
                    if (iIntValue3 == 1 && (this.mRoundedCornerFactory.getHasProviders() || privacyDotDecorProviderFactory.getHasProviders())) {
                        zArr[num.intValue()] = true;
                        int iIntValue4 = num.intValue();
                        List list2 = (List) pair.getFirst();
                        if (this.mOverlays == null) {
                            this.mOverlays = new OverlayWindow[4];
                            this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
                            DisplayInfo displayInfo = this.mDisplayInfo;
                            this.mRotation = displayInfo.rotation;
                            this.mDisplaySize.x = displayInfo.getNaturalWidth();
                            this.mDisplaySize.y = this.mDisplayInfo.getNaturalHeight();
                            this.mDisplayCutout = this.mDisplayInfo.displayCutout;
                            ((PrivacyDotViewControllerImpl) privacyDotViewController).setNewRotation(this.mRotation);
                        }
                        OverlayWindow[] overlayWindowArr2 = this.mOverlays;
                        OverlayWindow overlayWindow3 = overlayWindowArr2[iIntValue4];
                        if (overlayWindow3 != null) {
                            initOverlay(overlayWindow3, list2);
                        } else {
                            overlayWindowArr2[iIntValue4] = new OverlayWindow(this.mContext);
                            initOverlay(this.mOverlays[iIntValue4], list2);
                            RegionInterceptingFrameLayout regionInterceptingFrameLayout = this.mOverlays[iIntValue4].rootView;
                            regionInterceptingFrameLayout.setSystemUiVisibility(256);
                            regionInterceptingFrameLayout.setForceDarkAllowed(false);
                            WindowManager windowManager2 = this.mWindowManager;
                            if (windowManager2 != null) {
                                windowManager2.addView(regionInterceptingFrameLayout, getWindowLayoutParams(iIntValue4));
                            }
                            regionInterceptingFrameLayout.getRootView().getViewTreeObserver().addOnPreDrawListener(new ValidatingPreDrawListener(regionInterceptingFrameLayout.getRootView()));
                        }
                    }
                } else if (!hasRoundedCorners() || this.mHwcScreenDecorationSupport != null) {
                    DisplayCutout displayCutout = this.mDisplayCutout;
                    Rect[] boundingRectsAll = displayCutout == null ? null : displayCutout.getBoundingRectsAll();
                    int boundPositionFromRotation = getBoundPositionFromRotation(iIntValue3, this.mRotation);
                    if (!shouldDrawCutout() || boundingRectsAll == null || boundingRectsAll[boundPositionFromRotation].isEmpty()) {
                        CutoutDecorProviderFactory cutoutDecorProviderFactory = this.mCutoutFactory;
                        if ((cutoutDecorProviderFactory.isCameraProtectionVisible || cutoutDecorProviderFactory.shouldFillUDCDisplayCutout) && iIntValue3 == 1) {
                            if (this.mHwcScreenDecorationSupport != null) {
                                if (!this.mIsDotViewVisible || !privacyDotDecorProviderFactory.getHasProviders()) {
                                }
                            }
                        }
                    }
                }
                providers = list;
            }
            for (int i5 = 0; i5 < 4; i5++) {
                if (!zArr[i5] && (overlayWindowArr = this.mOverlays) != null && (overlayWindow = overlayWindowArr[i5]) != null) {
                    WindowManager windowManager3 = this.mWindowManager;
                    if (windowManager3 != null) {
                        windowManager3.removeViewImmediate(overlayWindow.rootView);
                    }
                    this.mOverlays[i5] = null;
                }
            }
            ((PrivacyDotViewControllerImpl) privacyDotViewController).getClass();
            View overlayView4 = getOverlayView(R.id.privacy_dot_top_left_container);
            if (overlayView4 != null && (overlayView = getOverlayView(R.id.privacy_dot_top_right_container)) != null && (overlayView2 = getOverlayView(R.id.privacy_dot_bottom_left_container)) != null && (overlayView3 = getOverlayView(R.id.privacy_dot_bottom_right_container)) != null) {
                privacyDotViewController.initialize(overlayView4, overlayView, overlayView2, overlayView3);
            }
        } else {
            removeAllOverlays();
            removeHwcOverlay();
        }
        boolean zHasOverlays = hasOverlays();
        UserTracker.Callback callback = this.mUserChangedCallback;
        UserTracker userTracker = this.mUserTracker;
        if (!zHasOverlays && this.mScreenDecorHwcWindow == null) {
            AnonymousClass7 anonymousClass7 = this.mColorInversionSetting;
            if (anonymousClass7 != null) {
                anonymousClass7.setListening(false);
            }
            ((UserTrackerImpl) userTracker).removeCallback(callback);
            this.mIsRegistered = false;
        } else if (!this.mIsRegistered) {
            AnonymousClass7 anonymousClass72 = this.mColorInversionSetting;
            if (anonymousClass72 == null) {
                screenDecorations = this;
                screenDecorations.mColorInversionSetting = new UserSettingObserver(this.mSecureSettings, this.mHandler, SettingsHelper.INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.ScreenDecorations.7
                    @Override // com.android.systemui.qs.UserSettingObserver
                    public final void handleValueChanged(int i6, boolean z2) {
                        ScreenDecorations screenDecorations2 = ScreenDecorations.this;
                        boolean z3 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                        screenDecorations2.updateColorInversion(i6);
                    }
                };
            } else {
                screenDecorations = this;
                int i6 = anonymousClass72.mUserId;
                UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
                if (i6 != userTrackerImpl.getUserId()) {
                    screenDecorations.mColorInversionSetting.setUserId(userTrackerImpl.getUserId());
                }
            }
            screenDecorations.mColorInversionSetting.setListening(true);
            screenDecorations.mColorInversionSetting.onChange(false);
            screenDecorations.updateColorInversion(screenDecorations.mColorInversionSetting.getValue());
            ((UserTrackerImpl) userTracker).addCallback(callback, screenDecorations.mExecutor);
            screenDecorations.mIsRegistered = true;
        }
        Trace.endSection();
    }

    public final boolean shouldDrawCutout() {
        return this.mDebug ? this.mHwcScreenDecorationSupport != null ? this.mDebugCutoutFactory.getHasProviders() || this.mDebugCutoutFactory.isCameraProtectionEnabled : this.mDebugCutoutFactory.getHasProviders() : this.mCutoutFactory.getHasProviders();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void showCameraProtection(Path path, Rect rect) {
        boolean z = this.mDebug;
        ScreenDecorationsLogger screenDecorationsLogger = this.mLogger;
        if (!z) {
            FaceScanningProviderFactoryImpl faceScanningProviderFactoryImpl = (FaceScanningProviderFactoryImpl) this.mFaceScanningFactory;
            if (faceScanningProviderFactoryImpl.getHasProviders()) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = faceScanningProviderFactoryImpl.keyguardUpdateMonitor;
                if (keyguardUpdateMonitor.isFaceEnabledAndEnrolled() && (keyguardUpdateMonitor.isFaceDetectionRunning() || faceScanningProviderFactoryImpl.authController.isShowing())) {
                    int i = this.mFaceScanningViewId;
                    DisplayCutoutView displayCutoutView = (DisplayCutoutView) getOverlayView(i);
                    if (displayCutoutView != null) {
                        screenDecorationsLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda0 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(2);
                        LogBuffer logBuffer = screenDecorationsLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) logMessageObtain).str1 = rect.toShortString();
                        logBuffer.commit(logMessageObtain);
                        displayCutoutView.enableShowProtection(true);
                        updateOverlayWindowVisibilityIfViewExists(displayCutoutView.findViewById(i));
                        return;
                    }
                }
            }
        }
        if (this.mScreenDecorHwcLayer != null) {
            screenDecorationsLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda02 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer2 = screenDecorationsLogger.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("ScreenDecorationsLog", logLevel2, screenDecorationsLogger$$ExternalSyntheticLambda02, null);
            ((LogMessageImpl) logMessageObtain2).str1 = rect.toShortString();
            logBuffer2.commit(logMessageObtain2);
            this.mScreenDecorHwcLayer.enableShowProtection(true);
            return;
        }
        int i2 = 0;
        for (int i3 : DISPLAY_CUTOUT_IDS) {
            View overlayView = getOverlayView(i3);
            if (overlayView instanceof DisplayCutoutView) {
                i2++;
                screenDecorationsLogger.getClass();
                LogLevel logLevel3 = LogLevel.DEBUG;
                ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda03 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(5);
                LogBuffer logBuffer3 = screenDecorationsLogger.logBuffer;
                LogMessage logMessageObtain3 = logBuffer3.obtain("ScreenDecorationsLog", logLevel3, screenDecorationsLogger$$ExternalSyntheticLambda03, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain3;
                logMessageImpl.str1 = rect.toShortString();
                logMessageImpl.int1 = i3;
                logBuffer3.commit(logMessageObtain3);
                ((DisplayCutoutView) overlayView).enableShowProtection(true);
            }
        }
        if (i2 == 0) {
            screenDecorationsLogger.getClass();
            LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.ERROR, "CutoutView not initialized showCameraProtection");
        }
    }

    public final void startOnScreenDecorationsThread() throws Throwable {
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
        if (isCoverDisplay().booleanValue()) {
            this.mRoundedCornerResDelegate = new CoverRoundedCornerResDelegate(this.mContext.getResources());
        } else {
            RoundedCornerResDelegateImpl roundedCornerResDelegateImpl = new RoundedCornerResDelegateImpl(this.mContext.getResources(), this.mDisplayUniqueId);
            this.mRoundedCornerResDelegate = roundedCornerResDelegateImpl;
            roundedCornerResDelegateImpl.setPhysicalPixelDisplaySizeRatio(getPhysicalPixelDisplaySizeRatio());
        }
        if (isCoverDisplay().booleanValue()) {
            this.mRoundedCornerFactory = new CoverRoundedCornerDecorProviderFactory(this.mRoundedCornerResDelegate);
        } else {
            this.mRoundedCornerFactory = new RoundedCornerDecorProviderFactory(this.mRoundedCornerResDelegate);
        }
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
        final PrivacyDotViewControllerImpl privacyDotViewControllerImpl = (PrivacyDotViewControllerImpl) this.mDotViewController;
        privacyDotViewControllerImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$addSystemAnimationCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                PrivacyDotViewControllerImpl privacyDotViewControllerImpl2 = privacyDotViewControllerImpl;
                ((SystemStatusAnimationSchedulerImpl) privacyDotViewControllerImpl2.animationScheduler).addCallback(privacyDotViewControllerImpl2.systemStatusAnimationCallback);
            }
        });
        privacyDotViewControllerImpl.createListener = this.mPrivacyDotCreateListener;
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && !isCoverDisplay().booleanValue()) {
            this.mDeviceStateManagerFoldProvider.registerCallback(this.mFoldCallback, this.mExecutor);
        }
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
                        screenDecorationsLogger.getClass();
                        LogLevel logLevel = LogLevel.INFO;
                        ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda0 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(10);
                        LogBuffer logBuffer = screenDecorationsLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = i3;
                        logMessageImpl.int2 = i2;
                        logBuffer.commit(logMessageObtain);
                    }
                    if (!screenDecorations.mDisplaySize.equals(point)) {
                        Point point2 = screenDecorations.mDisplaySize;
                        screenDecorationsLogger.getClass();
                        LogLevel logLevel2 = LogLevel.INFO;
                        ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda02 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(1);
                        LogBuffer logBuffer2 = screenDecorationsLogger.logBuffer;
                        LogMessage logMessageObtain2 = logBuffer2.obtain("ScreenDecorationsLog", logLevel2, screenDecorationsLogger$$ExternalSyntheticLambda02, null);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                        logMessageImpl2.str1 = point2.flattenToString();
                        logMessageImpl2.str2 = point.flattenToString();
                        logBuffer2.commit(logMessageObtain2);
                    }
                    if (screenDecorations.mOverlays != null) {
                        for (int i4 = 0; i4 < 4; i4++) {
                            OverlayWindow overlayWindow = screenDecorations.mOverlays[i4];
                            if (overlayWindow != null) {
                                RegionInterceptingFrameLayout regionInterceptingFrameLayout = overlayWindow.rootView;
                                int i5 = i2;
                                i2 = i5;
                                regionInterceptingFrameLayout.getViewTreeObserver().addOnPreDrawListener(new RestartingPreDrawListener(ScreenDecorations.this, regionInterceptingFrameLayout, i4, i5, point, 0));
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
                boolean zSemIsFitToActiveDisplay = screenDecorations.mDisplayManager.semIsFitToActiveDisplay();
                if (screenDecorations.mIsSmartViewFitToActiveDisplay == zSemIsFitToActiveDisplay || screenDecorations.isCoverDisplay().booleanValue()) {
                    return;
                }
                screenDecorations.mIsSmartViewFitToActiveDisplay = zSemIsFitToActiveDisplay;
                screenDecorations.removeAllOverlays();
                screenDecorations.setupDecorations();
            }
        };
        this.mDisplayListener = callback;
        ((DisplayTrackerImpl) this.mDisplayTracker).addDisplayChangeCallback(callback, new HandlerExecutor(this.mHandler));
        updateConfiguration();
        this.mJavaAdapter.alwaysCollectFlow(((FacePropertyRepositoryImpl) this.mFacePropertyRepository).sensorLocation, new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.onFaceSensorLocationChanged((Point) obj);
            }
        });
        if (isCoverDisplay().booleanValue()) {
            this.mContext.registerComponentCallbacks(new AnonymousClass6());
            this.mSettingsHelper.registerCallback(this.mAODStateSettingsCallback, Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE));
        }
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
        if (isCoverDisplay().booleanValue()) {
            updateOverlayProviderViews(new Integer[]{Integer.valueOf(R.id.rounded_corner_cover)});
        } else {
            updateOverlayProviderViews(new Integer[]{Integer.valueOf(this.mFaceScanningViewId), Integer.valueOf(R.id.display_cutout), Integer.valueOf(R.id.display_cutout_left), Integer.valueOf(R.id.display_cutout_right), Integer.valueOf(R.id.display_cutout_bottom), Integer.valueOf(R.id.rounded_corner_top_left), Integer.valueOf(R.id.rounded_corner_top_right), Integer.valueOf(R.id.rounded_corner_bottom_left), Integer.valueOf(R.id.rounded_corner_bottom_right)});
        }
    }

    public void updateConfiguration() throws Throwable {
        Object obj;
        Preconditions.checkState(this.mHandler.getLooper().getThread() == Thread.currentThread(), "must call on " + this.mHandler.getLooper().getThread() + ", but was " + Thread.currentThread());
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        int i = this.mDisplayInfo.rotation;
        if (this.mRotation != i) {
            ((PrivacyDotViewControllerImpl) this.mDotViewController).setNewRotation(i);
        }
        if (displaySizeChanged(this.mDisplaySize, this.mDisplayInfo)) {
            PrivacyDotViewController privacyDotViewController = this.mDotViewController;
            Point point = new Point(this.mDisplayInfo.getNaturalWidth(), this.mDisplayInfo.getNaturalHeight());
            PrivacyDotViewControllerImpl privacyDotViewControllerImpl = (PrivacyDotViewControllerImpl) privacyDotViewController;
            privacyDotViewControllerImpl.setCornerVisibilities();
            Object obj2 = privacyDotViewControllerImpl.lock;
            synchronized (obj2) {
                try {
                    obj = obj2;
                    try {
                        privacyDotViewControllerImpl.setNextViewState(ViewState.copy$default(privacyDotViewControllerImpl.nextViewState, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, point, 524287));
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    obj = obj2;
                }
            }
        }
        DisplayCutout displayCutout = this.mDisplayInfo.displayCutout;
        if (this.mIndicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
        }
        if (!this.mPendingConfigChange && (i != this.mRotation || displaySizeChanged(this.mDisplaySize, this.mDisplayInfo) || !Objects.equals(displayCutout, this.mDisplayCutout))) {
            this.mRotation = i;
            this.mDisplaySize.x = this.mDisplayInfo.getNaturalWidth();
            this.mDisplaySize.y = this.mDisplayInfo.getNaturalHeight();
            this.mDisplayCutout = displayCutout;
            float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio();
            RoundedCornerResDelegate roundedCornerResDelegate = this.mRoundedCornerResDelegate;
            if (roundedCornerResDelegate != null) {
                roundedCornerResDelegate.setPhysicalPixelDisplaySizeRatio(physicalPixelDisplaySizeRatio);
            }
            this.mDebugRoundedCornerDelegate.setPhysicalPixelDisplaySizeRatio(physicalPixelDisplaySizeRatio);
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
        if (this.mIndicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
        }
    }

    public final void updateFillUDCDisplayCutout() {
        boolean z = this.mIndicatorCutoutUtil.isUDCMainDisplay() && this.mSettingsHelper.isFillUDCDisplayCutoutEnabled();
        CutoutDecorProviderFactory cutoutDecorProviderFactory = this.mCutoutFactory;
        if (z != cutoutDecorProviderFactory.shouldFillUDCDisplayCutout) {
            cutoutDecorProviderFactory.shouldFillUDCDisplayCutout = z;
            this.mHandler.post(new ScreenDecorations$$ExternalSyntheticLambda6(this, z, 0));
        }
        if (!BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC || this.blockUpdateStatusIconContainerLayout) {
            return;
        }
        this.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda6(this, this.mCutoutFactory.shouldFillUDCDisplayCutout, 1));
    }

    public final void updateHwLayerRoundedCornerDrawable() {
        RoundedCornerResDelegate roundedCornerResDelegate;
        if (this.mScreenDecorHwcLayer == null || (roundedCornerResDelegate = this.mRoundedCornerResDelegate) == null) {
            return;
        }
        Drawable topRoundedDrawable = roundedCornerResDelegate.getTopRoundedDrawable();
        Drawable bottomRoundedDrawable = this.mRoundedCornerResDelegate.getBottomRoundedDrawable();
        if (this.mDebug && this.mDebugRoundedCornerFactory.getHasProviders()) {
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = this.mDebugRoundedCornerDelegate;
            PathDrawable pathDrawable = debugRoundedCornerDelegate.topRoundedDrawable;
            bottomRoundedDrawable = debugRoundedCornerDelegate.bottomRoundedDrawable;
            topRoundedDrawable = pathDrawable;
        }
        if (topRoundedDrawable == null && bottomRoundedDrawable == null) {
            return;
        }
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        screenDecorHwcLayer.roundedCornerDrawableTop = topRoundedDrawable;
        screenDecorHwcLayer.roundedCornerDrawableBottom = bottomRoundedDrawable;
        screenDecorHwcLayer.updateRoundedCornerDrawableBounds();
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
        RoundedCornerResDelegate roundedCornerResDelegate = this.mRoundedCornerResDelegate;
        if (roundedCornerResDelegate != null) {
            this.mScreenDecorHwcLayer.updateRoundedCornerExistenceAndSize(this.mRoundedCornerResDelegate.getTopRoundedSize().getWidth(), this.mRoundedCornerResDelegate.getBottomRoundedSize().getWidth(), roundedCornerResDelegate.getHasTop(), this.mRoundedCornerResDelegate.getHasBottom());
        }
    }

    public final void updateLayoutParams() {
        WindowManager windowManager;
        WindowManager windowManager2;
        Trace.beginSection("ScreenDecorations#updateLayoutParams");
        ViewGroup viewGroup = this.mScreenDecorHwcWindow;
        if (viewGroup != null && (windowManager2 = this.mWindowManager) != null) {
            windowManager2.updateViewLayout(viewGroup, getHwcWindowLayoutParams());
        }
        if (this.mOverlays != null) {
            for (int i = 0; i < 4; i++) {
                OverlayWindow overlayWindow = this.mOverlays[i];
                if (overlayWindow != null && (windowManager = this.mWindowManager) != null) {
                    windowManager.updateViewLayout(overlayWindow.rootView, getWindowLayoutParams(i));
                }
            }
        }
        Trace.endSection();
    }

    public void updateOverlayProviderViews(Integer[] numArr) {
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null || this.mPendingConfigChange) {
            return;
        }
        this.mProviderRefreshToken++;
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null) {
                int i = this.mProviderRefreshToken;
                int i2 = this.mRotation;
                int i3 = this.mTintColor;
                String str = this.mDisplayUniqueId;
                if (numArr != null) {
                    for (Integer num : numArr) {
                        Pair pair = (Pair) ((LinkedHashMap) overlayWindow.viewProviderMap).get(Integer.valueOf(num.intValue()));
                        if (pair != null) {
                            ((DecorProvider) pair.getSecond()).onReloadResAndMeasure((View) pair.getFirst(), i, i2, i3, str);
                        }
                    }
                } else {
                    for (Pair pair2 : ((LinkedHashMap) overlayWindow.viewProviderMap).values()) {
                        ((DecorProvider) pair2.getSecond()).onReloadResAndMeasure((View) pair2.getFirst(), i, i2, i3, str);
                    }
                }
            }
        }
    }

    public void updateOverlayWindowVisibilityIfViewExists(View view) {
        if (view == null) {
            return;
        }
        this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda4(this, view));
    }

    /* renamed from: com.android.systemui.ScreenDecorations$4, reason: invalid class name */
    public class AnonymousClass4 implements ComponentCallbacks {
        public AnonymousClass4() {
        }

        @Override // android.content.ComponentCallbacks
        public final void onConfigurationChanged(Configuration configuration) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            OverlayWindow[] overlayWindowArr = screenDecorations.mOverlays;
            if (overlayWindowArr == null || overlayWindowArr[1] == null) {
                return;
            }
            screenDecorations.mExecutor.execute(new ScreenDecorations$4$$ExternalSyntheticLambda0(this));
        }

        @Override // android.content.ComponentCallbacks
        public final void onLowMemory() {
        }
    }

    /* renamed from: com.android.systemui.ScreenDecorations$6, reason: invalid class name */
    public class AnonymousClass6 implements ComponentCallbacks {
        public AnonymousClass6() {
        }

        @Override // android.content.ComponentCallbacks
        public final void onConfigurationChanged(Configuration configuration) {
            if (ScreenDecorations.this.hasOverlays()) {
                ScreenDecorations.this.mExecutor.execute(new ScreenDecorations$4$$ExternalSyntheticLambda0(this));
            }
        }

        @Override // android.content.ComponentCallbacks
        public final void onLowMemory() {
        }
    }
}
