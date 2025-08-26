package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.shade.ShadeExpandsOnStatusBarLongPress;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerExt;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Arrays;
import java.util.Objects;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: classes3.dex */
public class PhoneStatusBarView extends FrameLayout implements KnoxStatusBarViewControl {
    public final CommandQueue mCommandQueue;
    public View mCutoutSpace;
    public int mDensity;
    public DisplayCutout mDisplayCutout;
    public Rect mDisplaySize;
    public float mFontScale;
    public boolean mHiddenByKnox;
    public final QuickPanelLogger mQuickPanelLogger;
    public int mRotationOrientation;
    public final StatusBarWindowControllerStore mStatusBarWindowControllerStore;
    public PhoneStatusBarViewController.PhoneStatusBarViewTouchHandler mTouchEventHandler;

    public PhoneStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotationOrientation = -1;
        this.mHiddenByKnox = false;
        this.mStatusBarWindowControllerStore = (StatusBarWindowControllerStore) Dependency.sDependency.getDependencyInner(StatusBarWindowControllerStore.class);
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
        if (updateDisplayParameters()) {
            updateWindowHeight();
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
        if (updateDisplayParameters()) {
            requestLayout();
        }
        updateWindowHeight();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDisplayCutout = null;
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        this.mCutoutSpace = findViewById(R.id.cutout_space_view);
        findViewById(R.id.battery).setTag("PhoneStatusBarView");
        getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        CommandQueue commandQueue = this.mCommandQueue;
        if (commandQueue != null && !commandQueue.panelsEnabled()) {
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.onInterceptTouchEvent(motionEvent, "!panelsEnabled()", true);
            }
            return true;
        }
        QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
        if (quickPanelLogger2 != null) {
            quickPanelLogger2.onInterceptTouchEvent(motionEvent);
        }
        this.mTouchEventHandler.onInterceptTouchEvent(motionEvent);
        return super.onInterceptTouchEvent(motionEvent);
    }

    public final boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(accessibilityEventObtain);
        dispatchPopulateAccessibilityEvent(accessibilityEventObtain);
        accessibilityEvent.appendRecord(accessibilityEventObtain);
        return true;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int i = ShadeExpandsOnStatusBarLongPress.$r8$clinit;
        if (this.mTouchEventHandler == null) {
            Log.w("PhoneStatusBarView", String.format("onTouch: No touch handler provided; eating gesture at (%d,%d)", Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY())));
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.onTouchEvent(motionEvent, "mTouchEventHandler == null", true);
            }
            return true;
        }
        QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
        if (quickPanelLogger2 != null) {
            quickPanelLogger2.onTouchEvent(motionEvent);
        }
        PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
        QuickPanelLogger quickPanelLogger3 = phoneStatusBarViewController.getQuickPanelLogger();
        if (quickPanelLogger3 != null) {
            quickPanelLogger3.quickPanelLoggerHelper.onTouchEventLogger.log(motionEvent, quickPanelLogger3.tag, "");
        }
        phoneStatusBarViewController.onTouch$1(motionEvent);
        if (!((CentralSurfacesImpl) phoneStatusBarViewController.centralSurfaces).mCommandQueue.panelsEnabled()) {
            if (motionEvent.getAction() == 0) {
                int i2 = StringCompanionObject.$r8$clinit;
                String.format(MutableVectorKt$$ExternalSyntheticOutline0.m((int) motionEvent.getX(), (int) motionEvent.getY(), "onTouchForwardedFromStatusBar: panel disabled, ignoring touch at (", ",", ")"), Arrays.copyOf(new Object[0], 0));
            }
            QuickPanelLogger quickPanelLogger4 = phoneStatusBarViewController.getQuickPanelLogger();
            if (quickPanelLogger4 != null) {
                quickPanelLogger4.onTouchEvent(motionEvent, "!centralSurfaces.commandQueuePanelsEnabled", false);
            }
            return false;
        }
        int action = motionEvent.getAction();
        ShadeViewController shadeViewController = phoneStatusBarViewController.shadeViewController;
        if (action != 0 || shadeViewController.isViewEnabled()) {
            return shadeViewController.handleExternalTouch(motionEvent);
        }
        phoneStatusBarViewController.shadeLogger.logMotionEvent(motionEvent, "onTouchForwardedFromStatusBar: panel view disabled");
        QuickPanelLogger quickPanelLogger5 = phoneStatusBarViewController.getQuickPanelLogger();
        if (quickPanelLogger5 == null) {
            return true;
        }
        quickPanelLogger5.onTouchEvent(motionEvent, "!shadeViewController.isViewEnabled", true);
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

    public final void updateWindowHeight() {
        StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) ((StatusBarWindowController) this.mStatusBarWindowControllerStore.getDefaultDisplay());
        statusBarWindowControllerImpl.getClass();
        Trace.beginSection("StatusBarWindowController#refreshStatusBarHeight");
        try {
            StatusBarWindowControllerExt statusBarWindowControllerExt = statusBarWindowControllerImpl.mExt;
            Context context = statusBarWindowControllerImpl.mContext;
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
            StatusBarWindowControllerImpl.State state = statusBarWindowControllerImpl.mCurrentState;
            if (z && statusBarWindowControllerImpl.mIsAttached) {
                statusBarWindowControllerImpl.mBarHeight = SystemBarUtils.getStatusBarHeight(statusBarWindowControllerImpl.mContext);
                statusBarWindowControllerImpl.mLp = new WindowManager.LayoutParams();
                statusBarWindowControllerImpl.apply(state);
            } else {
                int statusBarHeight = SystemBarUtils.getStatusBarHeight(statusBarWindowControllerImpl.mContext);
                if (statusBarWindowControllerImpl.mBarHeight != statusBarHeight) {
                    statusBarWindowControllerImpl.mBarHeight = statusBarHeight;
                    statusBarWindowControllerImpl.apply(state);
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final View getStatusBarView() {
        return this;
    }
}
