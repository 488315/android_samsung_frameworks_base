package com.android.systemui.biometrics;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.android.systemui.R;

public final class AuthPanelController extends ViewOutlineProvider {
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getOutline(android.view.View r7, android.graphics.Outline r8) {
        /*
            r6 = this;
            int r7 = r6.mPosition
            int r1 = r6.getLeftBound(r7)
            int r7 = r6.mPosition
            boolean r0 = r6.mUseFullScreen
            r2 = 2
            r3 = 3
            if (r0 != 0) goto L25
            android.content.Context r0 = r6.mContext
            android.graphics.Insets r0 = com.android.systemui.biometrics.Utils.getNavbarInsets(r0)
            if (r7 != r3) goto L1d
            int r7 = r6.mContentWidth
            int r7 = r7 + r1
            int r0 = r0.right
        L1b:
            int r7 = r7 - r0
            goto L28
        L1d:
            if (r7 != r2) goto L25
            int r7 = r6.mContentWidth
            int r7 = r7 + r1
            int r0 = r0.left
            goto L1b
        L25:
            int r7 = r6.mContentWidth
            int r7 = r7 + r1
        L28:
            int r0 = r6.mPosition
            if (r0 == r2) goto L3c
            if (r0 == r3) goto L3c
            int r0 = r6.mContainerHeight
            int r2 = r6.mContentHeight
            int r0 = r0 - r2
            int r2 = r6.mMargin
            int r0 = r0 - r2
            int r0 = java.lang.Math.max(r0, r2)
        L3a:
            r2 = r0
            goto L49
        L3c:
            int r0 = r6.mContainerHeight
            int r3 = r6.mContentHeight
            int r0 = r0 - r3
            int r0 = r0 / r2
            int r2 = r6.mMargin
            int r0 = java.lang.Math.max(r0, r2)
            goto L3a
        L49:
            boolean r0 = r6.mUseFullScreen
            if (r0 != 0) goto L65
            android.content.Context r0 = r6.mContext
            android.graphics.Insets r0 = com.android.systemui.biometrics.Utils.getNavbarInsets(r0)
            int r3 = r6.mContentHeight
            int r3 = r3 + r2
            int r0 = r0.bottom
            int r3 = r3 - r0
            int r4 = r6.mContainerHeight
            int r5 = r6.mMargin
            int r4 = r4 - r5
            int r4 = r4 - r0
            int r0 = java.lang.Math.min(r3, r4)
        L63:
            r4 = r0
            goto L72
        L65:
            int r0 = r6.mContentHeight
            int r0 = r0 + r2
            int r3 = r6.mContainerHeight
            int r4 = r6.mMargin
            int r3 = r3 - r4
            int r0 = java.lang.Math.min(r0, r3)
            goto L63
        L72:
            float r5 = r6.mCornerRadius
            r0 = r8
            r3 = r7
            r0.setRoundRect(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.AuthPanelController.getOutline(android.view.View, android.graphics.Outline):void");
    }
}
