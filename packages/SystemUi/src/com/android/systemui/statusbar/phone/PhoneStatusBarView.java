package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.shade.SecPanelBlockExpandingHelper;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PhoneStatusBarView extends FrameLayout implements KnoxStatusBarViewControl {
    public DarkIconDispatcher.DarkReceiver mBattery;
    public DarkIconDispatcher.DarkReceiver mClock;
    public View mCutoutSpace;
    public DisplayCutout mDisplayCutout;
    public Rect mDisplaySize;
    public boolean mHiddenByKnox;
    public NetspeedView mNetspeedView;
    public final SecPanelBlockExpandingHelper mPanelBlockExpandHelper;
    public final SecPanelLogger mPanelLogger;
    public int mRotationOrientation;
    public Gefingerpoken mTouchEventHandler;

    public PhoneStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotationOrientation = -1;
        this.mHiddenByKnox = false;
        this.mPanelBlockExpandHelper = (SecPanelBlockExpandingHelper) Dependency.get(SecPanelBlockExpandingHelper.class);
        this.mPanelLogger = (SecPanelLogger) Dependency.get(SecPanelLogger.class);
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
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mBattery);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mClock);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && this.mNetspeedView != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this.mNetspeedView);
        }
        updateDisplayParameters();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
        if (updateDisplayParameters()) {
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mBattery);
        ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mClock);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && this.mNetspeedView != null) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this.mNetspeedView);
        }
        this.mDisplayCutout = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        DarkIconDispatcher.DarkReceiver darkReceiver = (DarkIconDispatcher.DarkReceiver) findViewById(R.id.battery);
        this.mBattery = darkReceiver;
        ((View) darkReceiver).setTag("PhoneStatusBarView");
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
        if (!(!this.mPanelBlockExpandHelper.mCommandQueue.panelsEnabled())) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) this.mPanelLogger;
        secPanelLoggerImpl.getClass();
        secPanelLoggerImpl.addPanelLog(motionEvent, "[PSBV]|[InterceptTouch]", new StringBuilder("blocked by command queue"), true);
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
        Gefingerpoken gefingerpoken = this.mTouchEventHandler;
        if (gefingerpoken != null) {
            return gefingerpoken.onTouchEvent(motionEvent);
        }
        Log.w("PhoneStatusBarView", String.format("onTouch: No touch handler provided; eating gesture at (%d,%d)", Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY())));
        SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) this.mPanelLogger;
        secPanelLoggerImpl.getClass();
        secPanelLoggerImpl.addPanelLog(motionEvent, "[PSBV]|[onTouch]", new StringBuilder("mTouchEventHandler is null"), true);
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
        Rect maxBounds = ((FrameLayout) this).mContext.getResources().getConfiguration().windowConfiguration.getMaxBounds();
        if (Objects.equals(maxBounds, this.mDisplaySize)) {
            return z;
        }
        this.mDisplaySize = maxBounds;
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final View getStatusBarView() {
        return this;
    }
}
