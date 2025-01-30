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
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.net.Uri;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.decor.CoverPrivacyDotViewController;
import com.android.systemui.decor.CoverRoundedCornerDecorProviderFactory;
import com.android.systemui.decor.CoverViewState;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DecorProvider;
import com.android.systemui.decor.DecorProviderFactory;
import com.android.systemui.decor.FaceScanningProviderFactory;
import com.android.systemui.decor.OverlayWindow;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.decor.RoundedCornerResDelegate;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.ViewState;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.view.SemWindowManager;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScreenDecorations implements CoreStartable, TunerService.Tunable, Dumpable {
    public static final boolean DEBUG_COLOR;
    public static final boolean DEBUG_DISABLE_SCREEN_DECORATIONS = SystemProperties.getBoolean("debug.disable_screen_decorations", false);
    public static final boolean DEBUG_PRIVACY_INDICATOR;
    public static final boolean DEBUG_SCREENSHOT_ROUNDED_CORNERS;
    public static final int[] DISPLAY_CUTOUT_IDS;
    public final ScreenDecorations$$ExternalSyntheticLambda2 mAODStateSettingsCallback;
    public final AuthController mAuthController;
    public CameraAvailabilityListener mCameraListener;
    public SettingObserver mColorInversionSetting;
    public final Context mContext;
    public final CoverPrivacyDotViewController mCoverDotViewController;
    public boolean mCoverPendingConfigChange;
    public int mCoverRotation;
    public CoverRoundedCornerDecorProviderFactory mCoverRoundedCornerFactory;
    public CutoutDecorProviderFactory mCutoutFactory;
    public DisplayCutout mDisplayCutout;
    DisplayTracker.Callback mDisplayListener;
    public Display.Mode mDisplayMode;
    public final DisplayTracker mDisplayTracker;
    String mDisplayUniqueId;
    public final PrivacyDotDecorProviderFactory mDotFactory;
    public final PrivacyDotViewController mDotViewController;
    public ExecutorImpl mExecutor;
    public final FaceScanningProviderFactory mFaceScanningFactory;
    public final ScreenDecorations$$ExternalSyntheticLambda2 mFillUDCSettingsCallback;
    public Handler mHandler;
    protected DisplayDecorationSupport mHwcScreenDecorationSupport;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public boolean mIsDotViewVisible;
    protected boolean mIsRegistered;
    public final ScreenDecorationsLogger mLogger;
    public final Executor mMainExecutor;
    public boolean mPendingConfigChange;
    public int mRotation;
    protected DecorProviderFactory mRoundedCornerFactory;
    protected RoundedCornerResDelegate mRoundedCornerResDelegate;
    ScreenDecorHwcLayer mScreenDecorHwcLayer;
    ViewGroup mScreenDecorHwcWindow;
    public final SecureSettings mSecureSettings;
    public final SettingsHelper mSettingsHelper;
    public final ThreadFactory mThreadFactory;
    public final TunerService mTunerService;
    public final UserTracker mUserTracker;
    public WindowManager mWindowManager;
    public int mProviderRefreshToken = 0;
    protected OverlayWindow[] mOverlays = null;
    public int mTintColor = EmergencyPhoneWidget.BG_COLOR;
    protected DisplayInfo mDisplayInfo = new DisplayInfo();
    public Context mCoverWindowContext = null;
    public final DisplayInfo mCoverDisplayInfo = new DisplayInfo();
    public OverlayWindow mCoverOverlay = null;
    public boolean blockUpdateStatusIconContainerLayout = false;
    public final C09711 mCameraTransitionCallback = new CameraAvailabilityListener.CameraTransitionCallback() { // from class: com.android.systemui.ScreenDecorations.1
        @Override // com.android.systemui.CameraAvailabilityListener.CameraTransitionCallback
        public final void onApplyCameraProtection(Path path, Rect rect) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            LogBuffer.log$default(screenDecorations.mLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "onApplyCameraProtection", null, 8, null);
            IndicatorCutoutUtil indicatorCutoutUtil = screenDecorations.mIndicatorCutoutUtil;
            boolean z = true;
            char c = 1;
            if (!indicatorCutoutUtil.isUDCModel) {
                screenDecorations.mCutoutFactory.isCameraProtectionVisible = true;
                screenDecorations.setupDecorations();
                screenDecorations.showCameraProtection(path, rect);
            } else if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
                if (indicatorCutoutUtil.isUDCMainDisplay()) {
                    screenDecorations.blockUpdateStatusIconContainerLayout = true;
                }
                if (screenDecorations.mCutoutFactory.shouldFillUDCDisplayCutout) {
                    return;
                }
                screenDecorations.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(screenDecorations, z, c == true ? 1 : 0));
            }
        }

        @Override // com.android.systemui.CameraAvailabilityListener.CameraTransitionCallback
        public final void onHideCameraProtection() {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            LogBuffer.log$default(screenDecorations.mLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "onHideCameraProtection", null, 8, null);
            boolean z = false;
            if (!screenDecorations.mIndicatorCutoutUtil.isUDCModel) {
                screenDecorations.mCutoutFactory.isCameraProtectionVisible = false;
                screenDecorations.setupDecorations();
                screenDecorations.hideCameraProtection();
            } else if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
                screenDecorations.blockUpdateStatusIconContainerLayout = false;
                if (screenDecorations.mCutoutFactory.shouldFillUDCDisplayCutout) {
                    return;
                }
                screenDecorations.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(screenDecorations, z, 1));
            }
        }
    };
    PrivacyDotViewController.CreateListener mPrivacyDotCreateListener = new C09732();
    PrivacyDotViewController.ShowingListener mPrivacyDotShowingListener = new C09743();
    public final C09754 mAuthControllerCallback = new C09754();
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.ScreenDecorations.10
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            screenDecorations.mColorInversionSetting.setUserId(i);
            screenDecorations.updateColorInversion(screenDecorations.mColorInversionSetting.getValue());
        }
    };
    public final int mFaceScanningViewId = R.id.face_scanning_anim;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.ScreenDecorations$2 */
    public final class C09732 implements PrivacyDotViewController.CreateListener {
        public C09732() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.ScreenDecorations$3 */
    public final class C09743 implements PrivacyDotViewController.ShowingListener {
        public C09743() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.ScreenDecorations$4 */
    public final class C09754 implements AuthController.Callback {
        public C09754() {
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onFaceSensorLocationChanged() {
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            LogBuffer.log$default(screenDecorations.mLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "AuthControllerCallback in ScreenDecorations triggered", null, 8, null);
            ExecutorImpl executorImpl = screenDecorations.mExecutor;
            if (executorImpl != null) {
                executorImpl.execute(new ScreenDecorations$4$$ExternalSyntheticLambda0(this, 0));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CoverRestartingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final int mTargetRotation;
        public final View mView;

        public /* synthetic */ CoverRestartingPreDrawListener(ScreenDecorations screenDecorations, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i) {
            this((View) regionInterceptingFrameLayout, i);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            int i;
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            int i2 = this.mTargetRotation;
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (i2 == screenDecorations.mCoverRotation) {
                return true;
            }
            screenDecorations.mCoverPendingConfigChange = false;
            Preconditions.checkState(screenDecorations.mHandler.getLooper().getThread() == Thread.currentThread(), "must call on " + screenDecorations.mHandler.getLooper().getThread() + ", but was " + Thread.currentThread());
            screenDecorations.mCoverWindowContext.getDisplay().getDisplayInfo(screenDecorations.mCoverDisplayInfo);
            int i3 = screenDecorations.mCoverDisplayInfo.rotation;
            CoverPrivacyDotViewController coverPrivacyDotViewController = screenDecorations.mCoverDotViewController;
            synchronized (coverPrivacyDotViewController.lock) {
                CoverViewState coverViewState = coverPrivacyDotViewController.nextViewState;
                if (i3 != coverViewState.rotation) {
                    boolean z = coverViewState.layoutRtl;
                    Unit unit = Unit.INSTANCE;
                    Iterator it = coverPrivacyDotViewController.getViews().iterator();
                    while (it.hasNext()) {
                        ((View) it.next()).setVisibility(4);
                    }
                    View selectDesignatedCorner = coverPrivacyDotViewController.selectDesignatedCorner(i3, z);
                    int cornerForView = selectDesignatedCorner != null ? coverPrivacyDotViewController.cornerForView(selectDesignatedCorner) : -1;
                    synchronized (coverPrivacyDotViewController.lock) {
                        coverPrivacyDotViewController.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, false, false, false, false, i3, cornerForView, selectDesignatedCorner, null, 143));
                    }
                }
            }
            if (!screenDecorations.mCoverPendingConfigChange && i3 != (i = screenDecorations.mCoverRotation)) {
                LogBuffer.log$default(screenDecorations.mLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, AbstractC0970x34f4116a.m94m("updateCoverRotation, ", i, " -> ", i3), null, 8, null);
                screenDecorations.mCoverRotation = i3;
                if (screenDecorations.hasCoverOverlay()) {
                    screenDecorations.mCoverOverlay.onReloadResAndMeasure(null, 0, screenDecorations.mCoverRotation, screenDecorations.mTintColor, screenDecorations.mCoverDisplayInfo.uniqueId);
                }
            }
            this.mView.invalidate();
            return false;
        }

        private CoverRestartingPreDrawListener(View view, int i) {
            this.mView = view;
            this.mTargetRotation = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CoverValidatingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final View mView;

        public CoverValidatingPreDrawListener(View view) {
            this.mView = view;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            ScreenDecorations.this.mCoverWindowContext.getDisplay().getDisplayInfo(ScreenDecorations.this.mCoverDisplayInfo);
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (screenDecorations.mCoverDisplayInfo.rotation == screenDecorations.mCoverRotation || screenDecorations.mCoverPendingConfigChange) {
                return true;
            }
            this.mView.invalidate();
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class DisplayCutoutView extends DisplayCutoutBaseView {
        public final List mBounds;
        public int mColor;
        public final int mInitialPosition;
        public int mPosition;
        public int mRotation;
        public final Rect mTotalBounds;

        public DisplayCutoutView(Context context, int i) {
            super(context);
            this.mBounds = new ArrayList();
            this.mTotalBounds = new Rect();
            this.mColor = EmergencyPhoneWidget.BG_COLOR;
            this.mInitialPosition = i;
            this.paint.setColor(EmergencyPhoneWidget.BG_COLOR);
            this.paint.setStyle(Paint.Style.FILL);
            this.paintForCameraProtection.setColor(this.mColor);
            this.paintForCameraProtection.setStyle(Paint.Style.FILL_AND_STROKE);
            this.paintForCameraProtection.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.camera_protection_stroke_width));
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

        /* JADX WARN: Removed duplicated region for block: B:17:0x0156  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x01c5  */
        /* JADX WARN: Removed duplicated region for block: B:36:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x01be  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x0097  */
        @Override // com.android.systemui.DisplayCutoutBaseView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void updateCutout() {
            boolean z;
            boolean isEmpty;
            boolean z2;
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
            if (DisplayCutout.getFillBuiltInDisplayCutout(context.getResources(), context.getDisplay().getUniqueId()) || getContext().getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection)) {
                DisplayCutout displayCutout = this.displayInfo.displayCutout;
                if (displayCutout != null) {
                    int i2 = this.mPosition;
                    if (i2 == 0) {
                        isEmpty = displayCutout.getBoundingRectLeft().isEmpty();
                    } else if (i2 == 1) {
                        isEmpty = displayCutout.getBoundingRectTop().isEmpty();
                    } else if (i2 == 3) {
                        isEmpty = displayCutout.getBoundingRectBottom().isEmpty();
                    } else if (i2 == 2) {
                        isEmpty = displayCutout.getBoundingRectRight().isEmpty();
                    }
                    z = !isEmpty;
                    if (z) {
                        ((ArrayList) this.mBounds).addAll(this.displayInfo.displayCutout.getBoundingRects());
                        Rect rect = this.mBoundingRect;
                        DisplayCutout displayCutout2 = this.displayInfo.displayCutout;
                        boundsFromDirection(getGravity(displayCutout2), rect, displayCutout2);
                        if (getContext().getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection)) {
                            int ceil = (int) Math.ceil(getResources().getDimensionPixelSize(R.dimen.camera_protection_stroke_width) / 2.0d);
                            int i3 = this.mPosition;
                            if (i3 == 0) {
                                this.mBoundingRect.right += ceil;
                            } else if (i3 == 1) {
                                this.mBoundingRect.bottom += ceil;
                            } else if (i3 == 2) {
                                this.mBoundingRect.left -= ceil;
                            } else if (i3 == 3) {
                                this.mBoundingRect.top -= ceil;
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
                        if (!(!(this instanceof FaceScanningOverlay)) || i == getVisibility()) {
                            return;
                        }
                        setVisibility(i);
                        return;
                    }
                }
                z = false;
                if (z) {
                }
            }
            if (this.cutoutUtil.isUDCMainDisplay()) {
                if (this.settingsHelper.mItemLists.get("fill_udc_display_cutout").getIntValue() != 0) {
                    z2 = true;
                    if (z2) {
                        i = 8;
                    } else {
                        Path path = this.protectionPathOrig;
                        Resources resources = getResources();
                        DisplayInfo displayInfo = this.displayInfo;
                        int i4 = this.initialDisplayWidth;
                        int i5 = this.initialDisplayDensity;
                        int i6 = displayInfo.logicalWidth;
                        int i7 = displayInfo.logicalHeight;
                        int i8 = displayInfo.rotation;
                        boolean z3 = i8 == 1 || i8 == 3;
                        int i9 = z3 ? i7 : i6;
                        if (!z3) {
                            i6 = i7;
                        }
                        path.set(DisplayCutout.pathFromResourcesForUDC(resources, displayInfo.uniqueId, i9, i6, i4 <= 0 ? DisplayMetrics.DENSITY_DEVICE_STABLE : (i5 * i9) / i4, false));
                        RectF rectF = this.protectionRectOrig;
                        Path path2 = this.protectionPathOrig;
                        RectF rectF2 = new RectF();
                        path2.computeBounds(rectF2, false);
                        rectF.set(new Rect(MathKt__MathJVMKt.roundToInt(rectF2.left), MathKt__MathJVMKt.roundToInt(rectF2.top), MathKt__MathJVMKt.roundToInt(rectF2.right), MathKt__MathJVMKt.roundToInt(rectF2.bottom)));
                        invalidate();
                    }
                    if (!(this instanceof FaceScanningOverlay)) {
                        return;
                    } else {
                        return;
                    }
                }
            }
            z2 = false;
            if (z2) {
            }
            if (!(this instanceof FaceScanningOverlay)) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RestartingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final Display.Mode mTargetDisplayMode;
        public final int mTargetRotation;
        public final View mView;

        public /* synthetic */ RestartingPreDrawListener(ScreenDecorations screenDecorations, View view, int i, int i2, Display.Mode mode, int i3) {
            this(view, i, i2, mode);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
            int i = this.mTargetRotation;
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (i == screenDecorations.mRotation && !ScreenDecorations.displayModeChanged(screenDecorations.mDisplayMode, this.mTargetDisplayMode)) {
                return true;
            }
            ScreenDecorations screenDecorations2 = ScreenDecorations.this;
            screenDecorations2.mPendingConfigChange = false;
            screenDecorations2.updateConfiguration();
            this.mView.invalidate();
            return false;
        }

        private RestartingPreDrawListener(View view, int i, int i2, Display.Mode mode) {
            this.mView = view;
            this.mTargetRotation = i2;
            this.mTargetDisplayMode = mode;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ValidatingPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public final View mView;

        public ValidatingPreDrawListener(View view) {
            this.mView = view;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            ScreenDecorations.this.mContext.getDisplay().getDisplayInfo(ScreenDecorations.this.mDisplayInfo);
            DisplayInfo displayInfo = ScreenDecorations.this.mDisplayInfo;
            int i = displayInfo.rotation;
            Display.Mode mode = displayInfo.getMode();
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if ((i == screenDecorations.mRotation && !ScreenDecorations.displayModeChanged(screenDecorations.mDisplayMode, mode)) || ScreenDecorations.this.mPendingConfigChange) {
                return true;
            }
            this.mView.invalidate();
            return false;
        }
    }

    static {
        boolean z = SystemProperties.getBoolean("debug.screenshot_rounded_corners", false);
        DEBUG_SCREENSHOT_ROUNDED_CORNERS = z;
        DEBUG_COLOR = z;
        DISPLAY_CUTOUT_IDS = new int[]{R.id.display_cutout, R.id.display_cutout_left, R.id.display_cutout_right, R.id.display_cutout_bottom};
        DEBUG_PRIVACY_INDICATOR = DeviceType.isEngOrUTBinary();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.ScreenDecorations$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2] */
    public ScreenDecorations(Context context, Executor executor, SecureSettings secureSettings, TunerService tunerService, UserTracker userTracker, DisplayTracker displayTracker, PrivacyDotViewController privacyDotViewController, ThreadFactory threadFactory, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, FaceScanningProviderFactory faceScanningProviderFactory, ScreenDecorationsLogger screenDecorationsLogger, AuthController authController, SettingsHelper settingsHelper, CoverPrivacyDotViewController coverPrivacyDotViewController, IndicatorCutoutUtil indicatorCutoutUtil, IndicatorGardenPresenter indicatorGardenPresenter) {
        final int i = 0;
        this.mAODStateSettingsCallback = new SettingsHelper.OnChangedCallback(this) { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenDecorations f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                final View view;
                int i2 = i;
                ScreenDecorations screenDecorations = this.f$0;
                switch (i2) {
                    case 0:
                        screenDecorations.getClass();
                        if (uri.equals(Settings.System.getUriFor("aod_show_state")) && screenDecorations.hasCoverOverlay()) {
                            CoverRoundedCornerDecorProviderFactory coverRoundedCornerDecorProviderFactory = screenDecorations.mCoverRoundedCornerFactory;
                            final int i3 = 0;
                            if ((coverRoundedCornerDecorProviderFactory == null ? false : coverRoundedCornerDecorProviderFactory.hasProviders) && (view = screenDecorations.mCoverOverlay.getView(R.id.rounded_corner_cover)) != null) {
                                if (!screenDecorations.mSettingsHelper.isAODShown() || screenDecorations.mTintColor != -1) {
                                    if (view.getVisibility() != 0) {
                                        final int i4 = 1;
                                        screenDecorations.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i4) {
                                                    case 0:
                                                        view.setVisibility(4);
                                                        break;
                                                    default:
                                                        view.setVisibility(0);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                    }
                                } else {
                                    screenDecorations.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i3) {
                                                case 0:
                                                    view.setVisibility(4);
                                                    break;
                                                default:
                                                    view.setVisibility(0);
                                                    break;
                                            }
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        screenDecorations.getClass();
                        if (uri.equals(Settings.Global.getUriFor("fill_udc_display_cutout"))) {
                            Log.d("ScreenDecorations", uri.toString() + " changed");
                            screenDecorations.updateFillUDCDisplayCutout();
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mFillUDCSettingsCallback = new SettingsHelper.OnChangedCallback(this) { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenDecorations f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                final View view;
                int i22 = i2;
                ScreenDecorations screenDecorations = this.f$0;
                switch (i22) {
                    case 0:
                        screenDecorations.getClass();
                        if (uri.equals(Settings.System.getUriFor("aod_show_state")) && screenDecorations.hasCoverOverlay()) {
                            CoverRoundedCornerDecorProviderFactory coverRoundedCornerDecorProviderFactory = screenDecorations.mCoverRoundedCornerFactory;
                            final int i3 = 0;
                            if ((coverRoundedCornerDecorProviderFactory == null ? false : coverRoundedCornerDecorProviderFactory.hasProviders) && (view = screenDecorations.mCoverOverlay.getView(R.id.rounded_corner_cover)) != null) {
                                if (!screenDecorations.mSettingsHelper.isAODShown() || screenDecorations.mTintColor != -1) {
                                    if (view.getVisibility() != 0) {
                                        final int i4 = 1;
                                        screenDecorations.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i4) {
                                                    case 0:
                                                        view.setVisibility(4);
                                                        break;
                                                    default:
                                                        view.setVisibility(0);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                    }
                                } else {
                                    screenDecorations.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i3) {
                                                case 0:
                                                    view.setVisibility(4);
                                                    break;
                                                default:
                                                    view.setVisibility(0);
                                                    break;
                                            }
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        screenDecorations.getClass();
                        if (uri.equals(Settings.Global.getUriFor("fill_udc_display_cutout"))) {
                            Log.d("ScreenDecorations", uri.toString() + " changed");
                            screenDecorations.updateFillUDCDisplayCutout();
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mTunerService = tunerService;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mDotViewController = privacyDotViewController;
        this.mThreadFactory = threadFactory;
        this.mDotFactory = privacyDotDecorProviderFactory;
        this.mFaceScanningFactory = faceScanningProviderFactory;
        this.mLogger = screenDecorationsLogger;
        this.mAuthController = authController;
        this.mSettingsHelper = settingsHelper;
        this.mCoverDotViewController = coverPrivacyDotViewController;
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        }
    }

    public static boolean displayModeChanged(Display.Mode mode, Display.Mode mode2) {
        return (mode != null && mode.getPhysicalWidth() == mode2.getPhysicalWidth() && mode.getPhysicalHeight() == mode2.getPhysicalHeight()) ? false : true;
    }

    public static int getBoundPositionFromRotation(int i, int i2) {
        int i3 = i - i2;
        return i3 < 0 ? i3 + 4 : i3;
    }

    public static WindowManager.LayoutParams getWindowLayoutBaseParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 545259832, -3);
        int i = layoutParams.privateFlags | 80 | QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT;
        layoutParams.privateFlags = i;
        if (!DEBUG_SCREENSHOT_ROUNDED_CORNERS) {
            layoutParams.privateFlags = i | QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING;
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
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("unknown bound position: ", i));
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
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
        asIndenting.println("mIsPrivacyDotEnabled:" + this.mDotFactory.getHasProviders());
        asIndenting.println("shouldOptimizeOverlayVisibility:false");
        FaceScanningProviderFactory faceScanningProviderFactory = this.mFaceScanningFactory;
        boolean hasProviders = faceScanningProviderFactory.getHasProviders();
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("supportsShowingFaceScanningAnim:", hasProviders, asIndenting);
        if (hasProviders) {
            asIndenting.increaseIndent();
            StringBuilder sb2 = new StringBuilder("canShowFaceScanningAnim:");
            boolean hasProviders2 = faceScanningProviderFactory.getHasProviders();
            KeyguardUpdateMonitor keyguardUpdateMonitor = faceScanningProviderFactory.keyguardUpdateMonitor;
            sb2.append(hasProviders2 && keyguardUpdateMonitor.mIsFaceEnrolled);
            asIndenting.println(sb2.toString());
            StringBuilder sb3 = new StringBuilder("shouldShowFaceScanningAnim (at time dump was taken):");
            sb3.append((faceScanningProviderFactory.getHasProviders() && keyguardUpdateMonitor.mIsFaceEnrolled) && (keyguardUpdateMonitor.isFaceDetectionRunning() || faceScanningProviderFactory.authController.isShowing()));
            asIndenting.println(sb3.toString());
            asIndenting.decreaseIndent();
        }
        FaceScanningOverlay faceScanningOverlay = (FaceScanningOverlay) getOverlayView(this.mFaceScanningViewId);
        if (faceScanningOverlay != null) {
            faceScanningOverlay.dump(asIndenting);
        }
        asIndenting.println("mRotation:" + this.mRotation);
        asIndenting.println("mPendingConfigChange:" + this.mPendingConfigChange);
        if (hasCoverOverlay()) {
            StringBuilder sb4 = new StringBuilder("hasCoverRoundedCorners:");
            CoverRoundedCornerDecorProviderFactory coverRoundedCornerDecorProviderFactory = this.mCoverRoundedCornerFactory;
            sb4.append(coverRoundedCornerDecorProviderFactory == null ? false : coverRoundedCornerDecorProviderFactory.hasProviders);
            asIndenting.println(sb4.toString());
            asIndenting.println("isCoverPrivacyDotEnabled:" + isCoverPrivacyDotEnabled());
            asIndenting.println("mCoverPendingConfigChange:" + this.mPendingConfigChange);
            asIndenting.println("mCoverRotation:" + this.mCoverRotation);
        }
        if (this.mHwcScreenDecorationSupport != null) {
            asIndenting.increaseIndent();
            asIndenting.println("mHwcScreenDecorationSupport:");
            asIndenting.increaseIndent();
            asIndenting.println("format=" + PixelFormat.formatToString(this.mHwcScreenDecorationSupport.format));
            StringBuilder sb5 = new StringBuilder("alphaInterpretation=");
            int i = this.mHwcScreenDecorationSupport.alphaInterpretation;
            sb5.append(i != 0 ? i != 1 ? AbstractC0000x2c234b15.m0m("Unknown: ", i) : "MASK" : "COVERAGE");
            asIndenting.println(sb5.toString());
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
            StringBuilder sb6 = new StringBuilder("mOverlays(left,top,right,bottom)=(");
            sb6.append(this.mOverlays[0] != null);
            sb6.append(",");
            sb6.append(this.mOverlays[1] != null);
            sb6.append(",");
            sb6.append(this.mOverlays[2] != null);
            sb6.append(",");
            sb6.append(this.mOverlays[3] != null);
            sb6.append(")");
            asIndenting.println(sb6.toString());
            for (int i2 = 0; i2 < 4; i2++) {
                OverlayWindow overlayWindow = this.mOverlays[i2];
                if (overlayWindow != null) {
                    overlayWindow.dump(printWriter, getWindowTitleByPos(i2));
                }
            }
        }
        if (hasCoverOverlay()) {
            this.mCoverOverlay.dump(printWriter, getCoverWindowLayoutParams().getTitle().toString());
        }
        this.mRoundedCornerResDelegate.dump(printWriter, strArr);
        if (hasCoverOverlay()) {
            CoverPrivacyDotViewController coverPrivacyDotViewController = this.mCoverDotViewController;
            coverPrivacyDotViewController.getClass();
            printWriter.println("CoverPrivacyDotViewController state:");
            printWriter.println("  currentViewState=" + coverPrivacyDotViewController.currentViewState);
        }
    }

    public final View getCoverOverlayView(int i) {
        View view;
        OverlayWindow overlayWindow = this.mCoverOverlay;
        if (overlayWindow == null || (view = overlayWindow.getView(i)) == null) {
            return null;
        }
        return view;
    }

    public WindowManager.LayoutParams getCoverWindowLayoutParams() {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        windowLayoutBaseParams.width = -1;
        windowLayoutBaseParams.height = -1;
        windowLayoutBaseParams.setTitle("ScreenDecorOverlayCover");
        windowLayoutBaseParams.gravity = 17;
        return windowLayoutBaseParams;
    }

    public final boolean getDisplayAspectRatioChanged() {
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(this.mDisplayInfo.supportedModes);
        return (maximumResolutionDisplayMode == null || Float.compare(((float) this.mDisplayInfo.getNaturalWidth()) / ((float) this.mDisplayInfo.getNaturalHeight()), ((float) maximumResolutionDisplayMode.getPhysicalWidth()) / ((float) maximumResolutionDisplayMode.getPhysicalHeight())) == 0) ? false : true;
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

    public final List getProviders(boolean z) {
        ArrayList arrayList = new ArrayList(this.mDotFactory.getProviders());
        arrayList.addAll(this.mFaceScanningFactory.getProviders());
        if (!z) {
            arrayList.addAll(this.mRoundedCornerFactory.getProviders());
            arrayList.addAll(this.mCutoutFactory.getProviders());
        }
        return arrayList;
    }

    public WindowManager.LayoutParams getWindowLayoutParams(int i) {
        WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
        int boundPositionFromRotation = getBoundPositionFromRotation(i, this.mRotation);
        int i2 = -2;
        int i3 = 3;
        windowLayoutBaseParams.width = (boundPositionFromRotation == 1 || boundPositionFromRotation == 3) ? -1 : -2;
        int boundPositionFromRotation2 = getBoundPositionFromRotation(i, this.mRotation);
        if (boundPositionFromRotation2 != 1 && boundPositionFromRotation2 != 3) {
            i2 = -1;
        }
        windowLayoutBaseParams.height = i2;
        windowLayoutBaseParams.setTitle(getWindowTitleByPos(i));
        int boundPositionFromRotation3 = getBoundPositionFromRotation(i, this.mRotation);
        if (boundPositionFromRotation3 != 0) {
            if (boundPositionFromRotation3 == 1) {
                i3 = 48;
            } else if (boundPositionFromRotation3 == 2) {
                i3 = 5;
            } else {
                if (boundPositionFromRotation3 != 3) {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("unknown bound position: ", i));
                }
                i3 = 80;
            }
        }
        windowLayoutBaseParams.gravity = i3;
        return windowLayoutBaseParams;
    }

    public boolean hasCoverOverlay() {
        return this.mCoverOverlay != null;
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
            faceScanningOverlay.hideOverlayRunnable = new ScreenDecorations$$ExternalSyntheticLambda0(this, faceScanningOverlay, 1);
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

    public final boolean isCoverPrivacyDotEnabled() {
        return this.mContext.getResources().getBoolean(R.bool.config_enableCoverScreenPrivacyDot);
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda1(this, 0));
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(final String str, final String str2) {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
        } else {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenDecorations screenDecorations = ScreenDecorations.this;
                    String str3 = str;
                    String str4 = str2;
                    if (screenDecorations.mOverlays == null || !"sysui_rounded_size".equals(str3)) {
                        return;
                    }
                    Trace.beginSection("ScreenDecorations#onTuningChanged");
                    try {
                        int parseInt = Integer.parseInt(str4);
                        RoundedCornerResDelegate roundedCornerResDelegate = screenDecorations.mRoundedCornerResDelegate;
                        Integer valueOf = Integer.valueOf(parseInt);
                        if (!Intrinsics.areEqual(roundedCornerResDelegate.tuningSizeFactor, valueOf)) {
                            roundedCornerResDelegate.tuningSizeFactor = valueOf;
                            roundedCornerResDelegate.reloadMeasures();
                        }
                    } catch (NumberFormatException unused) {
                        RoundedCornerResDelegate roundedCornerResDelegate2 = screenDecorations.mRoundedCornerResDelegate;
                        if (!Intrinsics.areEqual(roundedCornerResDelegate2.tuningSizeFactor, null)) {
                            roundedCornerResDelegate2.tuningSizeFactor = null;
                            roundedCornerResDelegate2.reloadMeasures();
                        }
                    }
                    screenDecorations.updateOverlayProviderViews(new Integer[]{Integer.valueOf(R.id.rounded_corner_top_left), Integer.valueOf(R.id.rounded_corner_top_right), Integer.valueOf(R.id.rounded_corner_bottom_left), Integer.valueOf(R.id.rounded_corner_bottom_right)});
                    screenDecorations.updateHwLayerRoundedCornerExistAndSize();
                    Trace.endSection();
                }
            });
        }
    }

    public void setSize(View view, Size size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = size.getWidth();
        layoutParams.height = size.getHeight();
        view.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Code restructure failed: missing block: B:157:0x025a, code lost:
    
        if (r11.shouldFillUDCDisplayCutout == false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x025c, code lost:
    
        if (r10 != 1) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x03b0, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r11 != null ? r11 : null, r6) != false) goto L211;
     */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x032e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setupDecorations() {
        Object obj;
        Integer num;
        View overlayView;
        View overlayView2;
        View overlayView3;
        OverlayWindow[] overlayWindowArr;
        OverlayWindow overlayWindow;
        boolean z;
        boolean z2;
        Trace.beginSection("ScreenDecorations#setupDecorations");
        int i = 1;
        int i2 = 2;
        if (this.mRoundedCornerFactory.getHasProviders() || this.mCutoutFactory.getHasProviders() || this.mDotFactory.getHasProviders() || this.mFaceScanningFactory.getHasProviders()) {
            List providers = getProviders(this.mHwcScreenDecorationSupport != null);
            if (this.mOverlays != null) {
                int[] array = providers.stream().mapToInt(new ScreenDecorations$$ExternalSyntheticLambda7()).toArray();
                for (OverlayWindow overlayWindow2 : this.mOverlays) {
                    if (overlayWindow2 != null) {
                        Iterator it = CollectionsKt___CollectionsKt.toList(((LinkedHashMap) overlayWindow2.viewProviderMap).keySet()).iterator();
                        while (it.hasNext()) {
                            int intValue = ((Number) it.next()).intValue();
                            if (array == null || !ArraysKt___ArraysKt.contains(intValue, array)) {
                                View view = overlayWindow2.getView(intValue);
                                if (view != null) {
                                    overlayWindow2.rootView.removeView(view);
                                    overlayWindow2.viewProviderMap.remove(Integer.valueOf(intValue));
                                }
                            }
                        }
                    }
                }
            }
            if (this.mHwcScreenDecorationSupport == null || FactoryTest.isFactoryBinary()) {
                ViewGroup viewGroup = this.mScreenDecorHwcWindow;
                if (viewGroup != null) {
                    this.mWindowManager.removeViewImmediate(viewGroup);
                    this.mScreenDecorHwcWindow = null;
                    this.mScreenDecorHwcLayer = null;
                }
            } else if (this.mScreenDecorHwcWindow == null) {
                Context context = this.mContext;
                this.mScreenDecorHwcWindow = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.screen_decor_hwc_layer, (ViewGroup) null);
                ScreenDecorHwcLayer screenDecorHwcLayer = new ScreenDecorHwcLayer(context, this.mHwcScreenDecorationSupport);
                this.mScreenDecorHwcLayer = screenDecorHwcLayer;
                this.mScreenDecorHwcWindow.addView(screenDecorHwcLayer, new FrameLayout.LayoutParams(-1, -1, 8388659));
                WindowManager windowManager = this.mWindowManager;
                ViewGroup viewGroup2 = this.mScreenDecorHwcWindow;
                WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
                windowLayoutBaseParams.width = -1;
                windowLayoutBaseParams.height = -1;
                windowLayoutBaseParams.setTitle("ScreenDecorHwcOverlay");
                windowLayoutBaseParams.gravity = 8388659;
                if (!DEBUG_COLOR) {
                    windowLayoutBaseParams.setColorMode(4);
                }
                windowManager.addView(viewGroup2, windowLayoutBaseParams);
                updateHwLayerRoundedCornerExistAndSize();
                updateHwLayerRoundedCornerDrawable();
                this.mScreenDecorHwcWindow.getViewTreeObserver().addOnPreDrawListener(new ValidatingPreDrawListener(this.mScreenDecorHwcWindow));
            }
            boolean[] zArr = new boolean[4];
            while (true) {
                if (providers.isEmpty()) {
                    num = null;
                } else {
                    Iterator it2 = providers.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            obj = it2.next();
                            if (((DecorProvider) obj).getAlignedBounds().size() == 1) {
                                break;
                            }
                        } else {
                            obj = null;
                            break;
                        }
                    }
                    DecorProvider decorProvider = (DecorProvider) obj;
                    if (decorProvider != null) {
                        num = (Integer) decorProvider.getAlignedBounds().get(0);
                    } else {
                        int[] iArr = {0, 0, 0, 0};
                        Iterator it3 = providers.iterator();
                        while (it3.hasNext()) {
                            Iterator it4 = ((DecorProvider) it3.next()).getAlignedBounds().iterator();
                            while (it4.hasNext()) {
                                int intValue2 = ((Number) it4.next()).intValue();
                                iArr[intValue2] = iArr[intValue2] + 1;
                            }
                        }
                        Integer[] numArr = {1, 3, 0, 2};
                        Integer num2 = null;
                        int i3 = 0;
                        for (int i4 = 0; i4 < 4; i4++) {
                            int intValue3 = numArr[i4].intValue();
                            if (iArr[intValue3] > i3) {
                                num2 = Integer.valueOf(intValue3);
                                i3 = iArr[intValue3];
                            }
                        }
                        num = num2;
                    }
                }
                if (num == null) {
                    break;
                }
                int intValue4 = num.intValue();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : providers) {
                    if (((DecorProvider) obj2).getAlignedBounds().contains(Integer.valueOf(intValue4))) {
                        arrayList.add(obj2);
                    } else {
                        arrayList2.add(obj2);
                    }
                }
                Pair pair = new Pair(arrayList, arrayList2);
                List list = (List) pair.getSecond();
                int intValue5 = num.intValue();
                DisplayCutout displayCutout = this.mDisplayCutout;
                Rect[] boundingRectsAll = displayCutout == null ? null : displayCutout.getBoundingRectsAll();
                int boundPositionFromRotation = getBoundPositionFromRotation(intValue5, this.mRotation);
                Context context2 = this.mContext;
                if (!DisplayCutout.getFillBuiltInDisplayCutout(context2.getResources(), context2.getDisplay().getUniqueId()) || boundingRectsAll == null || boundingRectsAll[boundPositionFromRotation].isEmpty()) {
                    CutoutDecorProviderFactory cutoutDecorProviderFactory = this.mCutoutFactory;
                    if (!cutoutDecorProviderFactory.isCameraProtectionVisible) {
                    }
                }
                if (this.mHwcScreenDecorationSupport == null) {
                    z = true;
                    if (!z) {
                        if (!(this.mRoundedCornerFactory.getHasProviders() && this.mHwcScreenDecorationSupport == null)) {
                            if (!(this.mIsDotViewVisible && this.mDotFactory.getHasProviders())) {
                                z2 = false;
                                if (z2) {
                                    zArr[num.intValue()] = true;
                                    int intValue6 = num.intValue();
                                    List list2 = (List) pair.getFirst();
                                    OverlayWindow[] overlayWindowArr2 = this.mOverlays;
                                    Context context3 = this.mContext;
                                    if (overlayWindowArr2 == null) {
                                        this.mOverlays = new OverlayWindow[4];
                                        context3.getDisplay().getDisplayInfo(this.mDisplayInfo);
                                        DisplayInfo displayInfo = this.mDisplayInfo;
                                        this.mRotation = displayInfo.rotation;
                                        this.mDisplayMode = displayInfo.getMode();
                                        this.mDisplayCutout = this.mDisplayInfo.displayCutout;
                                        this.mDotViewController.setNewRotation(this.mRotation);
                                    }
                                    OverlayWindow[] overlayWindowArr3 = this.mOverlays;
                                    final OverlayWindow overlayWindow3 = overlayWindowArr3[intValue6];
                                    if (overlayWindow3 != null) {
                                        if (!overlayWindow3.hasSameProviders(list2)) {
                                            list2.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda6
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj3) {
                                                    View view2;
                                                    ScreenDecorations screenDecorations = ScreenDecorations.this;
                                                    OverlayWindow overlayWindow4 = overlayWindow3;
                                                    DecorProvider decorProvider2 = (DecorProvider) obj3;
                                                    screenDecorations.getClass();
                                                    if (overlayWindow4.getView(decorProvider2.getViewId()) != null) {
                                                        return;
                                                    }
                                                    int viewId = decorProvider2.getViewId();
                                                    OverlayWindow[] overlayWindowArr4 = screenDecorations.mOverlays;
                                                    if (overlayWindowArr4 != null) {
                                                        for (OverlayWindow overlayWindow5 : overlayWindowArr4) {
                                                            if (overlayWindow5 != null && (view2 = overlayWindow5.getView(viewId)) != null) {
                                                                overlayWindow5.rootView.removeView(view2);
                                                                overlayWindow5.viewProviderMap.remove(Integer.valueOf(viewId));
                                                            }
                                                        }
                                                    }
                                                    overlayWindow4.viewProviderMap.put(Integer.valueOf(decorProvider2.getViewId()), new Pair(decorProvider2.inflateView(overlayWindow4.context, overlayWindow4.rootView, screenDecorations.mRotation, screenDecorations.mTintColor), decorProvider2));
                                                }
                                            });
                                        }
                                        overlayWindow3.rootView.setVisibility(0);
                                    } else {
                                        overlayWindowArr3[intValue6] = new OverlayWindow(context3);
                                        final OverlayWindow overlayWindow4 = this.mOverlays[intValue6];
                                        if (!overlayWindow4.hasSameProviders(list2)) {
                                            list2.forEach(new Consumer() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda6
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj3) {
                                                    View view2;
                                                    ScreenDecorations screenDecorations = ScreenDecorations.this;
                                                    OverlayWindow overlayWindow42 = overlayWindow4;
                                                    DecorProvider decorProvider2 = (DecorProvider) obj3;
                                                    screenDecorations.getClass();
                                                    if (overlayWindow42.getView(decorProvider2.getViewId()) != null) {
                                                        return;
                                                    }
                                                    int viewId = decorProvider2.getViewId();
                                                    OverlayWindow[] overlayWindowArr4 = screenDecorations.mOverlays;
                                                    if (overlayWindowArr4 != null) {
                                                        for (OverlayWindow overlayWindow5 : overlayWindowArr4) {
                                                            if (overlayWindow5 != null && (view2 = overlayWindow5.getView(viewId)) != null) {
                                                                overlayWindow5.rootView.removeView(view2);
                                                                overlayWindow5.viewProviderMap.remove(Integer.valueOf(viewId));
                                                            }
                                                        }
                                                    }
                                                    overlayWindow42.viewProviderMap.put(Integer.valueOf(decorProvider2.getViewId()), new Pair(decorProvider2.inflateView(overlayWindow42.context, overlayWindow42.rootView, screenDecorations.mRotation, screenDecorations.mTintColor), decorProvider2));
                                                }
                                            });
                                        }
                                        overlayWindow4.rootView.setVisibility(0);
                                        RegionInterceptingFrameLayout regionInterceptingFrameLayout = this.mOverlays[intValue6].rootView;
                                        regionInterceptingFrameLayout.setSystemUiVisibility(256);
                                        regionInterceptingFrameLayout.setForceDarkAllowed(false);
                                        this.mWindowManager.addView(regionInterceptingFrameLayout, getWindowLayoutParams(intValue6));
                                        regionInterceptingFrameLayout.getRootView().getViewTreeObserver().addOnPreDrawListener(new ValidatingPreDrawListener(regionInterceptingFrameLayout.getRootView()));
                                    }
                                }
                                providers = list;
                            }
                        }
                    }
                    z2 = true;
                    if (z2) {
                    }
                    providers = list;
                }
                z = false;
                if (!z) {
                }
                z2 = true;
                if (z2) {
                }
                providers = list;
            }
            for (int i5 = 0; i5 < 4; i5++) {
                if (!zArr[i5] && (overlayWindowArr = this.mOverlays) != null && (overlayWindow = overlayWindowArr[i5]) != null) {
                    this.mWindowManager.removeViewImmediate(overlayWindow.rootView);
                    this.mOverlays[i5] = null;
                }
            }
            this.mDotViewController.showingListener = null;
            View overlayView4 = getOverlayView(R.id.privacy_dot_top_left_container);
            if (overlayView4 != null && (overlayView = getOverlayView(R.id.privacy_dot_top_right_container)) != null && (overlayView2 = getOverlayView(R.id.privacy_dot_bottom_left_container)) != null && (overlayView3 = getOverlayView(R.id.privacy_dot_bottom_right_container)) != null) {
                PrivacyDotViewController privacyDotViewController = this.mDotViewController;
                View view2 = privacyDotViewController.f349tl;
                if (view2 != null && privacyDotViewController.f350tr != null && privacyDotViewController.f347bl != null && privacyDotViewController.f348br != null && Intrinsics.areEqual(view2, overlayView4)) {
                    View view3 = privacyDotViewController.f350tr;
                    if (view3 == null) {
                        view3 = null;
                    }
                    if (Intrinsics.areEqual(view3, overlayView)) {
                        View view4 = privacyDotViewController.f347bl;
                        if (view4 == null) {
                            view4 = null;
                        }
                        if (Intrinsics.areEqual(view4, overlayView2)) {
                            View view5 = privacyDotViewController.f348br;
                        }
                    }
                }
                privacyDotViewController.f349tl = overlayView4;
                privacyDotViewController.f350tr = overlayView;
                privacyDotViewController.f347bl = overlayView2;
                privacyDotViewController.f348br = overlayView3;
                boolean isLayoutRtl = ((ConfigurationControllerImpl) privacyDotViewController.configurationController).isLayoutRtl();
                View selectDesignatedCorner = privacyDotViewController.selectDesignatedCorner(privacyDotViewController.nextViewState.rotation, isLayoutRtl);
                int cornerForView = selectDesignatedCorner != null ? privacyDotViewController.cornerForView(selectDesignatedCorner) : -1;
                Rect statusBarContentAreaForRotation = privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(3);
                Rect statusBarContentAreaForRotation2 = privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(0);
                Rect statusBarContentAreaForRotation3 = privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(1);
                Rect statusBarContentAreaForRotation4 = privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(2);
                int statusBarPaddingTop = privacyDotViewController.contentInsetsProvider.getStatusBarPaddingTop();
                synchronized (privacyDotViewController.lock) {
                    privacyDotViewController.currentViewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262143, null);
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, true, false, false, false, statusBarContentAreaForRotation2, statusBarContentAreaForRotation3, statusBarContentAreaForRotation4, statusBarContentAreaForRotation, isLayoutRtl, 0, statusBarPaddingTop, cornerForView, selectDesignatedCorner, null, 0, 0, 0, 0, 254478));
                    Unit unit = Unit.INSTANCE;
                }
            }
        } else {
            if (this.mOverlays != null) {
                for (int i6 = 0; i6 < 4; i6++) {
                    OverlayWindow overlayWindow5 = this.mOverlays[i6];
                    if (overlayWindow5 != null) {
                        this.mWindowManager.removeViewImmediate(overlayWindow5.rootView);
                        this.mOverlays[i6] = null;
                    }
                }
                this.mOverlays = null;
            }
            ViewGroup viewGroup3 = this.mScreenDecorHwcWindow;
            if (viewGroup3 != null) {
                this.mWindowManager.removeViewImmediate(viewGroup3);
                this.mScreenDecorHwcWindow = null;
                this.mScreenDecorHwcLayer = null;
            }
        }
        if (!hasOverlays()) {
            if (!(this.mScreenDecorHwcWindow != null)) {
                if (!hasCoverOverlay()) {
                    this.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda1(this, i2));
                    SettingObserver settingObserver = this.mColorInversionSetting;
                    if (settingObserver != null) {
                        settingObserver.setListening(false);
                    }
                    ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
                    this.mIsRegistered = false;
                }
                Trace.endSection();
            }
        }
        if (!this.mIsRegistered) {
            this.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda1(this, i));
            SettingObserver settingObserver2 = this.mColorInversionSetting;
            if (settingObserver2 == null) {
                this.mColorInversionSetting = new SettingObserver(this.mSecureSettings, this.mHandler, "accessibility_display_inversion_enabled", ((UserTrackerImpl) this.mUserTracker).getUserId()) { // from class: com.android.systemui.ScreenDecorations.8
                    @Override // com.android.systemui.qs.SettingObserver
                    public final void handleValueChanged(int i7, boolean z3) {
                        ScreenDecorations screenDecorations = ScreenDecorations.this;
                        boolean z4 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                        screenDecorations.updateColorInversion(i7);
                    }
                };
            } else if (settingObserver2.mUserId != ((UserTrackerImpl) this.mUserTracker).getUserId()) {
                this.mColorInversionSetting.setUserId(((UserTrackerImpl) this.mUserTracker).getUserId());
            }
            this.mColorInversionSetting.setListening(true);
            this.mColorInversionSetting.onChange(false);
            updateColorInversion(this.mColorInversionSetting.getValue());
            ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mExecutor);
            this.mIsRegistered = true;
        }
        Trace.endSection();
    }

    public void showCameraProtection(Path path, Rect rect) {
        FaceScanningProviderFactory faceScanningProviderFactory = this.mFaceScanningFactory;
        boolean hasProviders = faceScanningProviderFactory.getHasProviders();
        KeyguardUpdateMonitor keyguardUpdateMonitor = faceScanningProviderFactory.keyguardUpdateMonitor;
        boolean z = (hasProviders && keyguardUpdateMonitor.mIsFaceEnrolled) && (keyguardUpdateMonitor.isFaceDetectionRunning() || faceScanningProviderFactory.authController.isShowing());
        ScreenDecorationsLogger screenDecorationsLogger = this.mLogger;
        if (z) {
            int i = this.mFaceScanningViewId;
            DisplayCutoutView displayCutoutView = (DisplayCutoutView) getOverlayView(i);
            if (displayCutoutView != null) {
                screenDecorationsLogger.cameraProtectionBoundsForScanningOverlay(rect);
                displayCutoutView.setProtection(path, rect);
                displayCutoutView.enableShowProtection(true);
                updateOverlayWindowVisibilityIfViewExists(displayCutoutView.findViewById(i));
                return;
            }
        }
        if (this.mScreenDecorHwcLayer != null) {
            screenDecorationsLogger.hwcLayerCameraProtectionBounds(rect);
            this.mScreenDecorHwcLayer.setProtection(path, rect);
            this.mScreenDecorHwcLayer.enableShowProtection(true);
            return;
        }
        int i2 = 0;
        for (int i3 : DISPLAY_CUTOUT_IDS) {
            View overlayView = getOverlayView(i3);
            if (overlayView instanceof DisplayCutoutView) {
                i2++;
                screenDecorationsLogger.dcvCameraBounds(i3, rect);
                ((DisplayCutoutView) overlayView).enableShowProtection(true);
            }
        }
        if (i2 == 0) {
            LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.ERROR, "CutoutView not initialized showCameraProtection", null, 8, null);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
            return;
        }
        ThreadFactoryImpl threadFactoryImpl = (ThreadFactoryImpl) this.mThreadFactory;
        threadFactoryImpl.getClass();
        HandlerThread handlerThread = new HandlerThread("ScreenDecorations");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mHandler = handler;
        threadFactoryImpl.getClass();
        ExecutorImpl executorImpl = new ExecutorImpl(handler.getLooper());
        this.mExecutor = executorImpl;
        executorImpl.execute(new ScreenDecorations$$ExternalSyntheticLambda1(this, 3));
        ExecutorImpl executorImpl2 = this.mExecutor;
        this.mDotViewController.uiExecutor = executorImpl2;
        Handler handler2 = this.mHandler;
        CoverPrivacyDotViewController coverPrivacyDotViewController = this.mCoverDotViewController;
        coverPrivacyDotViewController.uiExecutor = executorImpl2;
        coverPrivacyDotViewController.handler = handler2;
        this.mAuthController.addCallback(this.mAuthControllerCallback);
    }

    public final void updateColorInversion(int i) {
        this.mTintColor = i != 0 ? -1 : EmergencyPhoneWidget.BG_COLOR;
        if (DEBUG_COLOR) {
            this.mTintColor = -65536;
        }
        updateOverlayProviderViews(new Integer[]{Integer.valueOf(this.mFaceScanningViewId), Integer.valueOf(R.id.display_cutout), Integer.valueOf(R.id.display_cutout_left), Integer.valueOf(R.id.display_cutout_right), Integer.valueOf(R.id.display_cutout_bottom), Integer.valueOf(R.id.rounded_corner_top_left), Integer.valueOf(R.id.rounded_corner_top_right), Integer.valueOf(R.id.rounded_corner_bottom_left), Integer.valueOf(R.id.rounded_corner_bottom_right)});
        Integer[] numArr = {Integer.valueOf(R.id.rounded_corner_cover)};
        if (hasCoverOverlay()) {
            this.mCoverOverlay.onReloadResAndMeasure(numArr, 0, this.mCoverRotation, this.mTintColor, this.mCoverDisplayInfo.uniqueId);
        }
    }

    public void updateConfiguration() {
        Preconditions.checkState(this.mHandler.getLooper().getThread() == Thread.currentThread(), "must call on " + this.mHandler.getLooper().getThread() + ", but was " + Thread.currentThread());
        this.mContext.getDisplay().getDisplayInfo(this.mDisplayInfo);
        int i = this.mDisplayInfo.rotation;
        if (this.mRotation != i) {
            this.mDotViewController.setNewRotation(i);
        }
        Display.Mode mode = this.mDisplayInfo.getMode();
        DisplayCutout displayCutout = this.mDisplayInfo.displayCutout;
        IndicatorCutoutUtil indicatorCutoutUtil = this.mIndicatorCutoutUtil;
        if (indicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
        }
        if (!this.mPendingConfigChange && (i != this.mRotation || displayModeChanged(this.mDisplayMode, mode) || !Objects.equals(displayCutout, this.mDisplayCutout))) {
            this.mRotation = i;
            this.mDisplayMode = mode;
            this.mDisplayCutout = displayCutout;
            RoundedCornerResDelegate roundedCornerResDelegate = this.mRoundedCornerResDelegate;
            float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio();
            if (!(roundedCornerResDelegate.physicalPixelDisplaySizeRatio == physicalPixelDisplaySizeRatio)) {
                roundedCornerResDelegate.physicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                roundedCornerResDelegate.reloadMeasures();
            }
            RoundedCornerResDelegate roundedCornerResDelegate2 = this.mRoundedCornerResDelegate;
            boolean displayAspectRatioChanged = getDisplayAspectRatioChanged();
            if (roundedCornerResDelegate2.displayAspectRatioChanged != displayAspectRatioChanged) {
                roundedCornerResDelegate2.displayAspectRatioChanged = displayAspectRatioChanged;
                roundedCornerResDelegate2.reloadMeasures();
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
            faceScanningOverlay.faceScanningAnimColor = Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColorAccent, faceScanningOverlay.getContext(), 0);
        }
        if (indicatorCutoutUtil.isUDCModel) {
            updateFillUDCDisplayCutout();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateFillUDCDisplayCutout() {
        boolean z;
        CutoutDecorProviderFactory cutoutDecorProviderFactory;
        int i = 1;
        int i2 = 0;
        if (this.mIndicatorCutoutUtil.isUDCMainDisplay()) {
            if (this.mSettingsHelper.mItemLists.get("fill_udc_display_cutout").getIntValue() != 0) {
                z = true;
                cutoutDecorProviderFactory = this.mCutoutFactory;
                if (z != cutoutDecorProviderFactory.shouldFillUDCDisplayCutout) {
                    cutoutDecorProviderFactory.shouldFillUDCDisplayCutout = z;
                    this.mHandler.post(new ScreenDecorations$$ExternalSyntheticLambda3(this, z, i2));
                }
                if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC || this.blockUpdateStatusIconContainerLayout) {
                }
                this.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(this, this.mCutoutFactory.shouldFillUDCDisplayCutout, i));
                return;
            }
        }
        z = false;
        cutoutDecorProviderFactory = this.mCutoutFactory;
        if (z != cutoutDecorProviderFactory.shouldFillUDCDisplayCutout) {
        }
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
        }
    }

    public final void updateHwLayerRoundedCornerDrawable() {
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer == null) {
            return;
        }
        RoundedCornerResDelegate roundedCornerResDelegate = this.mRoundedCornerResDelegate;
        Drawable drawable = roundedCornerResDelegate.topRoundedDrawable;
        Drawable drawable2 = roundedCornerResDelegate.bottomRoundedDrawable;
        if (drawable == null || drawable2 == null) {
            return;
        }
        screenDecorHwcLayer.roundedCornerDrawableTop = drawable;
        screenDecorHwcLayer.roundedCornerDrawableBottom = drawable2;
        int i = screenDecorHwcLayer.roundedCornerTopSize;
        drawable.setBounds(0, 0, i, i);
        Drawable drawable3 = screenDecorHwcLayer.roundedCornerDrawableBottom;
        if (drawable3 != null) {
            int i2 = screenDecorHwcLayer.roundedCornerBottomSize;
            drawable3.setBounds(0, 0, i2, i2);
        }
        screenDecorHwcLayer.invalidate();
        screenDecorHwcLayer.invalidate();
    }

    public final void updateHwLayerRoundedCornerExistAndSize() {
        ScreenDecorHwcLayer screenDecorHwcLayer = this.mScreenDecorHwcLayer;
        if (screenDecorHwcLayer == null) {
            return;
        }
        RoundedCornerResDelegate roundedCornerResDelegate = this.mRoundedCornerResDelegate;
        boolean z = roundedCornerResDelegate.hasTop;
        boolean z2 = roundedCornerResDelegate.hasBottom;
        int width = roundedCornerResDelegate.topRoundedSize.getWidth();
        int width2 = this.mRoundedCornerResDelegate.bottomRoundedSize.getWidth();
        if (screenDecorHwcLayer.hasTopRoundedCorner == z && screenDecorHwcLayer.hasBottomRoundedCorner == z2 && screenDecorHwcLayer.roundedCornerTopSize == width && screenDecorHwcLayer.roundedCornerBottomSize == width2) {
            return;
        }
        screenDecorHwcLayer.hasTopRoundedCorner = z;
        screenDecorHwcLayer.hasBottomRoundedCorner = z2;
        screenDecorHwcLayer.roundedCornerTopSize = width;
        screenDecorHwcLayer.roundedCornerBottomSize = width2;
        Drawable drawable = screenDecorHwcLayer.roundedCornerDrawableTop;
        if (drawable != null) {
            drawable.setBounds(0, 0, width, width);
        }
        Drawable drawable2 = screenDecorHwcLayer.roundedCornerDrawableBottom;
        if (drawable2 != null) {
            int i = screenDecorHwcLayer.roundedCornerBottomSize;
            drawable2.setBounds(0, 0, i, i);
        }
        screenDecorHwcLayer.invalidate();
        screenDecorHwcLayer.requestLayout();
    }

    public final void updateLayoutParams() {
        Trace.beginSection("ScreenDecorations#updateLayoutParams");
        ViewGroup viewGroup = this.mScreenDecorHwcWindow;
        if (viewGroup != null) {
            WindowManager windowManager = this.mWindowManager;
            WindowManager.LayoutParams windowLayoutBaseParams = getWindowLayoutBaseParams();
            windowLayoutBaseParams.width = -1;
            windowLayoutBaseParams.height = -1;
            windowLayoutBaseParams.setTitle("ScreenDecorHwcOverlay");
            windowLayoutBaseParams.gravity = 8388659;
            if (!DEBUG_COLOR) {
                windowLayoutBaseParams.setColorMode(4);
            }
            windowManager.updateViewLayout(viewGroup, windowLayoutBaseParams);
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
        OverlayWindow[] overlayWindowArr = this.mOverlays;
        if (overlayWindowArr == null) {
            return;
        }
        this.mProviderRefreshToken++;
        for (OverlayWindow overlayWindow : overlayWindowArr) {
            if (overlayWindow != null) {
                overlayWindow.onReloadResAndMeasure(numArr, this.mProviderRefreshToken, this.mRotation, this.mTintColor, this.mDisplayUniqueId);
            }
        }
    }

    public void updateOverlayWindowVisibilityIfViewExists(View view) {
        if (view == null) {
            return;
        }
        this.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda0(this, view, 0));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.ScreenDecorations$5 */
    public final class ComponentCallbacksC09765 implements ComponentCallbacks {
        public ComponentCallbacksC09765() {
        }

        @Override // android.content.ComponentCallbacks
        public final void onConfigurationChanged(Configuration configuration) {
            if (ScreenDecorations.this.hasCoverOverlay()) {
                ScreenDecorations.this.mExecutor.execute(new ScreenDecorations$4$$ExternalSyntheticLambda0(this, 1));
            }
        }

        @Override // android.content.ComponentCallbacks
        public final void onLowMemory() {
        }
    }
}
