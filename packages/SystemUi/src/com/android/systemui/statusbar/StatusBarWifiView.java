package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarWifiView extends BaseStatusBarFrameLayout implements DarkIconDispatcher.DarkReceiver {
    public View mAirplaneSpacer;
    public StatusBarIconView mDotView;
    public ImageView mIn;
    public View mInoutContainer;
    public ImageView mOut;
    public View mSignalSpacer;
    public String mSlot;
    public StatusBarSignalPolicy.WifiIconState mState;
    public int mVisibleState;
    public LinearLayout mWifiGroup;
    public ImageView mWifiIcon;

    public StatusBarWifiView(Context context) {
        super(context);
        this.mVisibleState = 2;
    }

    public static StatusBarWifiView fromContext(Context context, String str) {
        StatusBarWifiView statusBarWifiView = (StatusBarWifiView) LayoutInflater.from(context).inflate(R.layout.status_bar_wifi_group, (ViewGroup) null);
        statusBarWifiView.mSlot = str;
        statusBarWifiView.mWifiGroup = (LinearLayout) statusBarWifiView.findViewById(R.id.wifi_group);
        statusBarWifiView.mWifiIcon = (ImageView) statusBarWifiView.findViewById(R.id.wifi_signal);
        statusBarWifiView.mIn = (ImageView) statusBarWifiView.findViewById(R.id.wifi_in);
        statusBarWifiView.mOut = (ImageView) statusBarWifiView.findViewById(R.id.wifi_out);
        statusBarWifiView.mSignalSpacer = statusBarWifiView.findViewById(R.id.wifi_signal_spacer);
        statusBarWifiView.mAirplaneSpacer = statusBarWifiView.findViewById(R.id.wifi_airplane_spacer);
        statusBarWifiView.mInoutContainer = statusBarWifiView.findViewById(R.id.inout_container);
        StatusBarIconView statusBarIconView = new StatusBarIconView(((FrameLayout) statusBarWifiView).mContext, statusBarWifiView.mSlot, null);
        statusBarWifiView.mDotView = statusBarIconView;
        statusBarIconView.setVisibleState(1);
        int dimensionPixelSize = ((FrameLayout) statusBarWifiView).mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_size);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.gravity = 8388627;
        statusBarWifiView.addView(statusBarWifiView.mDotView, layoutParams);
        statusBarWifiView.setVisibleState(0, false);
        return statusBarWifiView;
    }

    public final void applyWifiState(StatusBarSignalPolicy.WifiIconState wifiIconState) {
        if (wifiIconState == null) {
            r0 = getVisibility() != 8;
            setVisibility(8);
            this.mState = null;
        } else {
            StatusBarSignalPolicy.WifiIconState wifiIconState2 = this.mState;
            if (wifiIconState2 == null) {
                StatusBarSignalPolicy.WifiIconState copy = wifiIconState.copy();
                this.mState = copy;
                setContentDescription(copy.contentDescription);
                int i = this.mState.resId;
                if (i >= 0) {
                    this.mWifiIcon.setImageDrawable(((FrameLayout) this).mContext.getDrawable(i));
                }
                this.mIn.setVisibility(this.mState.activityIn ? 0 : 8);
                this.mOut.setVisibility(this.mState.activityOut ? 0 : 8);
                View view = this.mInoutContainer;
                StatusBarSignalPolicy.WifiIconState wifiIconState3 = this.mState;
                view.setVisibility((wifiIconState3.activityIn || wifiIconState3.activityOut) ? 0 : 8);
                this.mAirplaneSpacer.setVisibility(this.mState.airplaneSpacerVisible ? 0 : 8);
                this.mSignalSpacer.setVisibility(this.mState.signalSpacerVisible ? 0 : 8);
                setVisibility(this.mState.visible ? 0 : 8);
            } else if (wifiIconState2.equals(wifiIconState)) {
                r0 = false;
            } else {
                StatusBarSignalPolicy.WifiIconState copy2 = wifiIconState.copy();
                setContentDescription(copy2.contentDescription);
                int i2 = this.mState.resId;
                int i3 = copy2.resId;
                if (i2 != i3 && i3 >= 0) {
                    this.mWifiIcon.setImageDrawable(((FrameLayout) this).mContext.getDrawable(i3));
                }
                this.mIn.setVisibility(copy2.activityIn ? 0 : 8);
                this.mOut.setVisibility(copy2.activityOut ? 0 : 8);
                this.mInoutContainer.setVisibility((copy2.activityIn || copy2.activityOut) ? 0 : 8);
                this.mAirplaneSpacer.setVisibility(copy2.airplaneSpacerVisible ? 0 : 8);
                this.mSignalSpacer.setVisibility(copy2.signalSpacerVisible ? 0 : 8);
                boolean z = copy2.activityIn;
                StatusBarSignalPolicy.WifiIconState wifiIconState4 = this.mState;
                if (z == wifiIconState4.activityIn && copy2.activityOut == wifiIconState4.activityOut) {
                    r0 = false;
                }
                boolean z2 = wifiIconState4.visible;
                boolean z3 = copy2.visible;
                if (z2 != z3) {
                    r0 |= true;
                    setVisibility(z3 ? 0 : 8);
                }
                this.mState = copy2;
            }
        }
        if (r0) {
            requestLayout();
        }
    }

    @Override // android.view.View
    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        float translationX = getTranslationX();
        float translationY = getTranslationY();
        rect.left = (int) (rect.left + translationX);
        rect.right = (int) (rect.right + translationX);
        rect.top = (int) (rect.top + translationY);
        rect.bottom = (int) (rect.bottom + translationY);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final String getSlot() {
        return this.mSlot;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final int getVisibleState() {
        return this.mVisibleState;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final boolean isIconVisible() {
        StatusBarSignalPolicy.WifiIconState wifiIconState = this.mState;
        return wifiIconState != null && wifiIconState.visible;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        ColorStateList valueOf = ColorStateList.valueOf(tint);
        this.mWifiIcon.setImageTintList(valueOf);
        this.mIn.setImageTintList(valueOf);
        this.mOut.setImageTintList(valueOf);
        this.mDotView.setDecorColor(tint);
        this.mDotView.setIconColor(tint, false);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setDecorColor(int i) {
        this.mDotView.setDecorColor(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setStaticDrawableColor(int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        this.mWifiIcon.setImageTintList(valueOf);
        this.mIn.setImageTintList(valueOf);
        this.mOut.setImageTintList(valueOf);
        this.mDotView.setDecorColor(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setVisibleState(int i, boolean z) {
        if (i == this.mVisibleState) {
            return;
        }
        this.mVisibleState = i;
        if (i == 0) {
            this.mWifiGroup.setVisibility(0);
            this.mDotView.setVisibility(8);
        } else if (i != 1) {
            this.mWifiGroup.setVisibility(8);
            this.mDotView.setVisibility(8);
        } else {
            this.mWifiGroup.setVisibility(8);
            this.mDotView.setVisibility(0);
        }
    }

    @Override // android.view.View
    public final String toString() {
        return "StatusBarWifiView(slot=" + this.mSlot + " state=" + this.mState + ")";
    }

    public StatusBarWifiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mVisibleState = 2;
    }

    public StatusBarWifiView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVisibleState = 2;
    }
}
