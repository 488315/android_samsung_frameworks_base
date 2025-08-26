package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Outline;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class AuthPanelController extends ViewOutlineProvider {
    public int mContainerHeight;
    public int mContainerWidth;
    public int mContentHeight;
    public int mContentWidth;
    public final Context mContext;
    public float mCornerRadius;
    public int mMargin;
    public final View mPanelView;
    public final int mPosition = 1;
    public boolean mUseFullScreen;

    public AuthPanelController(Context context, View view) {
        this.mContext = context;
        this.mPanelView = view;
        this.mCornerRadius = context.getResources().getDimension(R.dimen.biometric_dialog_corner_size);
        this.mMargin = (int) context.getResources().getDimension(R.dimen.biometric_dialog_border_padding);
        view.setOutlineProvider(this);
        view.setClipToOutline(true);
    }

    public final int getLeftBound(int i) {
        if (i == 1) {
            return (this.mContainerWidth - this.mContentWidth) / 2;
        }
        if (i == 2) {
            if (this.mUseFullScreen) {
                return this.mMargin;
            }
            return this.mMargin + Utils.getNavbarInsets(this.mContext).left;
        }
        if (i == 3) {
            return (this.mContainerWidth - this.mContentWidth) - this.mMargin;
        }
        Log.e("BiometricPrompt/AuthPanelController", "Unrecognized position: " + i);
        return getLeftBound(1);
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        int i;
        int iMax;
        int iMin;
        int i2;
        int i3;
        int leftBound = getLeftBound(this.mPosition);
        int i4 = this.mPosition;
        if (this.mUseFullScreen) {
            i = this.mContentWidth + leftBound;
        } else {
            Insets navbarInsets = Utils.getNavbarInsets(this.mContext);
            if (i4 == 3) {
                i2 = this.mContentWidth + leftBound;
                i3 = navbarInsets.right;
            } else {
                if (i4 == 2) {
                    i2 = this.mContentWidth + leftBound;
                    i3 = navbarInsets.left;
                }
                i = this.mContentWidth + leftBound;
            }
            i = i2 - i3;
        }
        int i5 = this.mPosition;
        if (i5 == 2 || i5 == 3) {
            iMax = Math.max((this.mContainerHeight - this.mContentHeight) / 2, this.mMargin);
        } else {
            int i6 = this.mContainerHeight - this.mContentHeight;
            int i7 = this.mMargin;
            iMax = Math.max(i6 - i7, i7);
        }
        int i8 = iMax;
        if (this.mUseFullScreen) {
            iMin = Math.min(this.mContentHeight + i8, this.mContainerHeight - this.mMargin);
        } else {
            Insets navbarInsets2 = Utils.getNavbarInsets(this.mContext);
            int i9 = this.mContentHeight + i8;
            int i10 = navbarInsets2.bottom;
            iMin = Math.min(i9 - i10, (this.mContainerHeight - this.mMargin) - i10);
        }
        outline.setRoundRect(leftBound, i8, i, iMin, this.mCornerRadius);
    }
}
