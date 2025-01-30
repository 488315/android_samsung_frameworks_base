package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.DualToneHandler;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarMobileView extends BaseStatusBarFrameLayout implements DarkIconDispatcher.DarkReceiver {
    public StatusBarIconView mDotView;
    public DualToneHandler mDualToneHandler;
    public ImageView mIn;
    public View mInoutContainer;
    public ImageView mMobile;
    public SignalDrawable mMobileDrawable;
    public LinearLayout mMobileGroup;
    public ImageView mMobileRoaming;
    public View mMobileRoamingSpace;
    public ImageView mMobileType;
    public ImageView mOut;
    public String mSlot;
    public StatusBarSignalPolicy.MobileIconState mState;
    public int mVisibleState;

    public StatusBarMobileView(Context context) {
        super(context);
        this.mVisibleState = 2;
    }

    public final void applyMobileState(StatusBarSignalPolicy.MobileIconState mobileIconState) {
        boolean z;
        if (mobileIconState == null) {
            r0 = getVisibility() != 8;
            setVisibility(8);
            this.mState = null;
        } else {
            StatusBarSignalPolicy.MobileIconState mobileIconState2 = this.mState;
            if (mobileIconState2 == null) {
                StatusBarSignalPolicy.MobileIconState copy = mobileIconState.copy();
                this.mState = copy;
                setContentDescription(copy.contentDescription);
                if (this.mState.visible) {
                    this.mMobileGroup.setVisibility(0);
                } else {
                    this.mMobileGroup.setVisibility(8);
                }
                this.mMobileDrawable.setLevel(this.mState.strengthId);
                StatusBarSignalPolicy.MobileIconState mobileIconState3 = this.mState;
                if (mobileIconState3.typeId > 0) {
                    this.mMobileType.setContentDescription(mobileIconState3.typeContentDescription);
                    this.mMobileType.setImageResource(this.mState.typeId);
                    this.mMobileType.setVisibility(0);
                } else {
                    this.mMobileType.setVisibility(8);
                }
                this.mMobile.setVisibility(this.mState.showTriangle ? 0 : 8);
                this.mMobileRoaming.setVisibility(this.mState.roaming ? 0 : 8);
                this.mMobileRoamingSpace.setVisibility(this.mState.roaming ? 0 : 8);
                this.mIn.setVisibility(this.mState.activityIn ? 0 : 8);
                this.mOut.setVisibility(this.mState.activityOut ? 0 : 8);
                View view = this.mInoutContainer;
                StatusBarSignalPolicy.MobileIconState mobileIconState4 = this.mState;
                view.setVisibility((mobileIconState4.activityIn || mobileIconState4.activityOut) ? 0 : 8);
            } else if (mobileIconState2.equals(mobileIconState)) {
                r0 = false;
            } else {
                StatusBarSignalPolicy.MobileIconState copy2 = mobileIconState.copy();
                setContentDescription(copy2.contentDescription);
                int i = copy2.visible ? 0 : 8;
                if (i == this.mMobileGroup.getVisibility() || this.mVisibleState != 0) {
                    z = false;
                } else {
                    this.mMobileGroup.setVisibility(i);
                    z = true;
                }
                int i2 = this.mState.strengthId;
                int i3 = copy2.strengthId;
                if (i2 != i3) {
                    this.mMobileDrawable.setLevel(i3);
                }
                int i4 = this.mState.typeId;
                int i5 = copy2.typeId;
                if (i4 != i5) {
                    z |= i5 == 0 || i4 == 0;
                    if (i5 != 0) {
                        this.mMobileType.setContentDescription(copy2.typeContentDescription);
                        this.mMobileType.setImageResource(copy2.typeId);
                        this.mMobileType.setVisibility(0);
                    } else {
                        this.mMobileType.setVisibility(8);
                    }
                }
                this.mMobile.setVisibility(copy2.showTriangle ? 0 : 8);
                this.mMobileRoaming.setVisibility(copy2.roaming ? 0 : 8);
                this.mMobileRoamingSpace.setVisibility(copy2.roaming ? 0 : 8);
                this.mIn.setVisibility(copy2.activityIn ? 0 : 8);
                this.mOut.setVisibility(copy2.activityOut ? 0 : 8);
                this.mInoutContainer.setVisibility((copy2.activityIn || copy2.activityOut) ? 0 : 8);
                boolean z2 = copy2.roaming;
                StatusBarSignalPolicy.MobileIconState mobileIconState5 = this.mState;
                if (z2 == mobileIconState5.roaming && copy2.activityIn == mobileIconState5.activityIn && copy2.activityOut == mobileIconState5.activityOut && copy2.showTriangle == mobileIconState5.showTriangle) {
                    r0 = false;
                }
                r0 |= z;
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

    public StatusBarSignalPolicy.MobileIconState getState() {
        return this.mState;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final int getVisibleState() {
        return this.mVisibleState;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final boolean isIconVisible() {
        return this.mState.visible;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        if (!DarkIconDispatcher.isInAreas(arrayList, this)) {
            f = 0.0f;
        }
        this.mMobileDrawable.setTintList(ColorStateList.valueOf(this.mDualToneHandler.getSingleColor(f)));
        ColorStateList valueOf = ColorStateList.valueOf(DarkIconDispatcher.getTint(arrayList, this, i));
        this.mIn.setImageTintList(valueOf);
        this.mOut.setImageTintList(valueOf);
        this.mMobileType.setImageTintList(valueOf);
        this.mMobileRoaming.setImageTintList(valueOf);
        this.mDotView.setDecorColor(i);
        this.mDotView.setIconColor(i, false);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setDecorColor(int i) {
        this.mDotView.setDecorColor(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setStaticDrawableColor(int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        this.mMobileDrawable.setTintList(valueOf);
        this.mIn.setImageTintList(valueOf);
        this.mOut.setImageTintList(valueOf);
        this.mMobileType.setImageTintList(valueOf);
        this.mMobileRoaming.setImageTintList(valueOf);
        this.mDotView.setDecorColor(i);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setVisibleState(int i, boolean z) {
        if (i == this.mVisibleState) {
            return;
        }
        this.mVisibleState = i;
        if (i == 0) {
            this.mMobileGroup.setVisibility(0);
            this.mDotView.setVisibility(8);
        } else if (i != 1) {
            this.mMobileGroup.setVisibility(4);
            this.mDotView.setVisibility(4);
        } else {
            this.mMobileGroup.setVisibility(4);
            this.mDotView.setVisibility(0);
        }
    }

    @Override // android.view.View
    public final String toString() {
        return "StatusBarMobileView(slot=" + this.mSlot + " state=" + this.mState + ")";
    }

    public StatusBarMobileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mVisibleState = 2;
    }

    public StatusBarMobileView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVisibleState = 2;
    }
}
