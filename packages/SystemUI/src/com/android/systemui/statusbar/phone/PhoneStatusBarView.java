package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerExt;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class PhoneStatusBarView extends FrameLayout implements KnoxStatusBarViewControl {
    public DarkIconDispatcher.DarkReceiver mBattery;
    public DarkIconDispatcher.DarkReceiver mClock;
    public final CommandQueue mCommandQueue;
    public View mCutoutSpace;
    public int mDensity;
    public DisplayCutout mDisplayCutout;
    public Rect mDisplaySize;
    public float mFontScale;
    public boolean mHiddenByKnox;
    public NetspeedView mNetspeedView;
    public final QuickPanelLogger mQuickPanelLogger;
    public int mRotationOrientation;
    public final StatusBarWindowController mStatusBarWindowController;
    public Gefingerpoken mTouchEventHandler;

    public PhoneStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotationOrientation = -1;
        this.mHiddenByKnox = false;
        this.mStatusBarWindowController = (StatusBarWindowController) Dependency.sDependency.getDependencyInner(StatusBarWindowController.class);
        this.mQuickPanelLogger = new QuickPanelLogger("PSBV");
        this.mCommandQueue = (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (updateDisplayParameters()) {
            requestLayout();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).addDarkReceiver(this.mBattery);
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).addDarkReceiver(this.mClock);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && this.mNetspeedView != null) {
            ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).addDarkReceiver(this.mNetspeedView);
        }
        if (updateDisplayParameters()) {
            Flags.FEATURE_FLAGS.getClass();
            StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
            statusBarWindowController.getClass();
            Trace.beginSection("StatusBarWindowController#refreshStatusBarHeight");
            try {
                StatusBarWindowControllerExt statusBarWindowControllerExt = statusBarWindowController.mExt;
                Context context = statusBarWindowController.mContext;
                statusBarWindowControllerExt.getClass();
                boolean z = false;
                for (int i = 0; i <= 3; i++) {
                    int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(context, i);
                    int[] iArr = statusBarWindowControllerExt.heights;
                    if (iArr[i] != statusBarHeightForRotation) {
                        iArr[i] = statusBarHeightForRotation;
                        z = true;
                    }
                }
                StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
                if (z && statusBarWindowController.mIsAttached) {
                    statusBarWindowController.mBarHeight = SystemBarUtils.getStatusBarHeight(statusBarWindowController.mContext);
                    statusBarWindowController.mLp = new WindowManager.LayoutParams();
                    statusBarWindowController.apply(state);
                } else {
                    int statusBarHeight = SystemBarUtils.getStatusBarHeight(statusBarWindowController.mContext);
                    if (statusBarWindowController.mBarHeight != statusBarHeight) {
                        statusBarWindowController.mBarHeight = statusBarHeight;
                        statusBarWindowController.apply(state);
                    }
                }
                Trace.endSection();
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
        if (updateDisplayParameters()) {
            requestLayout();
        }
        Flags.FEATURE_FLAGS.getClass();
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        statusBarWindowController.getClass();
        Trace.beginSection("StatusBarWindowController#refreshStatusBarHeight");
        try {
            StatusBarWindowControllerExt statusBarWindowControllerExt = statusBarWindowController.mExt;
            Context context = statusBarWindowController.mContext;
            statusBarWindowControllerExt.getClass();
            boolean z = false;
            for (int i = 0; i <= 3; i++) {
                int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(context, i);
                int[] iArr = statusBarWindowControllerExt.heights;
                if (iArr[i] != statusBarHeightForRotation) {
                    iArr[i] = statusBarHeightForRotation;
                    z = true;
                }
            }
            StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
            if (z && statusBarWindowController.mIsAttached) {
                statusBarWindowController.mBarHeight = SystemBarUtils.getStatusBarHeight(statusBarWindowController.mContext);
                statusBarWindowController.mLp = new WindowManager.LayoutParams();
                statusBarWindowController.apply(state);
            } else {
                int statusBarHeight = SystemBarUtils.getStatusBarHeight(statusBarWindowController.mContext);
                if (statusBarWindowController.mBarHeight != statusBarHeight) {
                    statusBarWindowController.mBarHeight = statusBarHeight;
                    statusBarWindowController.apply(state);
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).removeDarkReceiver(this.mBattery);
        ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).removeDarkReceiver(this.mClock);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && this.mNetspeedView != null) {
            ((DarkIconDispatcher) Dependency.sDependency.getDependencyInner(DarkIconDispatcher.class)).removeDarkReceiver(this.mNetspeedView);
        }
        this.mDisplayCutout = null;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBattery = (DarkIconDispatcher.DarkReceiver) findViewById(R.id.battery);
        this.mClock = (DarkIconDispatcher.DarkReceiver) findViewById(R.id.clock);
        this.mCutoutSpace = findViewById(R.id.cutout_space_view);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.system_icons);
            NetspeedView netspeedView = (NetspeedView) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.samsung_status_bar_network_speed_view, (ViewGroup) null);
            this.mNetspeedView = netspeedView;
            if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                netspeedView.mInStatusBar = true;
            }
            viewGroup.addView(netspeedView, 0);
        }
        getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.mTouchEventHandler.onInterceptTouchEvent(motionEvent);
        CommandQueue commandQueue = this.mCommandQueue;
        if (commandQueue == null || commandQueue.panelsEnabled()) {
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.onInterceptTouchEvent(motionEvent);
            }
            return super.onInterceptTouchEvent(motionEvent);
        }
        QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
        if (quickPanelLogger2 != null) {
            quickPanelLogger2.onInterceptTouchEvent(motionEvent, "!panelsEnabled()", true);
        }
        return true;
    }

    public final boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(obtain);
        dispatchPopulateAccessibilityEvent(obtain);
        accessibilityEvent.appendRecord(obtain);
        return true;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchEventHandler != null) {
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.onTouchEvent(motionEvent);
            }
            return this.mTouchEventHandler.onTouchEvent(motionEvent);
        }
        Log.w("PhoneStatusBarView", String.format("onTouch: No touch handler provided; eating gesture at (%d,%d)", Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY())));
        QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
        if (quickPanelLogger2 != null) {
            quickPanelLogger2.onTouchEvent(motionEvent, "mTouchEventHandler == null", true);
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final void setHiddenByKnox(boolean z) {
        this.mHiddenByKnox = z;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (i != 8 && this.mHiddenByKnox) {
            i = 8;
        }
        super.setVisibility(i);
    }

    public final boolean updateDisplayParameters() {
        boolean z;
        int exactRotation = RotationUtils.getExactRotation(((FrameLayout) this).mContext);
        if (exactRotation != this.mRotationOrientation) {
            this.mRotationOrientation = exactRotation;
            z = true;
        } else {
            z = false;
        }
        if (!Objects.equals(getRootWindowInsets().getDisplayCutout(), this.mDisplayCutout)) {
            this.mDisplayCutout = getRootWindowInsets().getDisplayCutout();
            z = true;
        }
        Configuration configuration = ((FrameLayout) this).mContext.getResources().getConfiguration();
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        if (!Objects.equals(maxBounds, this.mDisplaySize)) {
            this.mDisplaySize = maxBounds;
            z = true;
        }
        int i = configuration.densityDpi;
        if (i != this.mDensity) {
            this.mDensity = i;
            z = true;
        }
        float f = configuration.fontScale;
        if (f == this.mFontScale) {
            return z;
        }
        this.mFontScale = f;
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final View getStatusBarView() {
        return this;
    }
}
