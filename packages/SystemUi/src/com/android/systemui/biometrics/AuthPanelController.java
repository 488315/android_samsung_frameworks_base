package com.android.systemui.biometrics;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Outline;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthPanelController extends ViewOutlineProvider {
    public int mContainerHeight;
    public int mContainerWidth;
    public int mContentHeight;
    public int mContentWidth;
    public final Context mContext;
    public float mCornerRadius;
    public int mMargin;
    public final View mPanelView;
    public int mPosition = 1;
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0063  */
    @Override // android.view.ViewOutlineProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getOutline(View view, Outline outline) {
        int i;
        int i2;
        int max;
        int min;
        int i3;
        int i4;
        int leftBound = getLeftBound(this.mPosition);
        int i5 = this.mPosition;
        if (!this.mUseFullScreen) {
            Insets navbarInsets = Utils.getNavbarInsets(this.mContext);
            if (i5 == 3) {
                i3 = this.mContentWidth + leftBound;
                i4 = navbarInsets.right;
            } else if (i5 == 2) {
                i3 = this.mContentWidth + leftBound;
                i4 = navbarInsets.left;
            }
            i = i3 - i4;
            i2 = this.mPosition;
            if (i2 != 2 || i2 == 3) {
                max = Math.max((this.mContainerHeight - this.mContentHeight) / 2, this.mMargin);
            } else {
                int i6 = this.mContainerHeight - this.mContentHeight;
                int i7 = this.mMargin;
                max = Math.max(i6 - i7, i7);
            }
            int i8 = max;
            if (this.mUseFullScreen) {
                Insets navbarInsets2 = Utils.getNavbarInsets(this.mContext);
                int i9 = this.mContentHeight + i8;
                int i10 = navbarInsets2.bottom;
                min = Math.min(i9 - i10, (this.mContainerHeight - this.mMargin) - i10);
            } else {
                min = Math.min(this.mContentHeight + i8, this.mContainerHeight - this.mMargin);
            }
            outline.setRoundRect(leftBound, i8, i, min, this.mCornerRadius);
        }
        i = this.mContentWidth + leftBound;
        i2 = this.mPosition;
        if (i2 != 2) {
        }
        max = Math.max((this.mContainerHeight - this.mContentHeight) / 2, this.mMargin);
        int i82 = max;
        if (this.mUseFullScreen) {
        }
        outline.setRoundRect(leftBound, i82, i, min, this.mCornerRadius);
    }

    public final void updateForContentDimensions(int i, int i2, int i3) {
        if (this.mContainerWidth == 0 || this.mContainerHeight == 0) {
            Log.w("BiometricPrompt/AuthPanelController", "Not done measuring yet");
            return;
        }
        final int i4 = 0;
        int dimension = this.mUseFullScreen ? 0 : (int) this.mContext.getResources().getDimension(R.dimen.biometric_dialog_border_padding);
        float dimension2 = this.mUseFullScreen ? 0.0f : this.mContext.getResources().getDimension(R.dimen.biometric_dialog_corner_size);
        if (i3 <= 0) {
            this.mMargin = dimension;
            this.mCornerRadius = dimension2;
            this.mContentWidth = i;
            this.mContentHeight = i2;
            this.mPanelView.invalidateOutline();
            return;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mMargin, dimension);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda0
            public final /* synthetic */ AuthPanelController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i4) {
                    case 0:
                        AuthPanelController authPanelController = this.f$0;
                        authPanelController.getClass();
                        authPanelController.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                    case 1:
                        AuthPanelController authPanelController2 = this.f$0;
                        authPanelController2.getClass();
                        authPanelController2.mCornerRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        break;
                    case 2:
                        AuthPanelController authPanelController3 = this.f$0;
                        authPanelController3.getClass();
                        authPanelController3.mContentHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        authPanelController3.mPanelView.invalidateOutline();
                        break;
                    default:
                        AuthPanelController authPanelController4 = this.f$0;
                        authPanelController4.getClass();
                        authPanelController4.mContentWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                }
            }
        });
        final int i5 = 2;
        final int i6 = 1;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mCornerRadius, dimension2);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda0
            public final /* synthetic */ AuthPanelController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i6) {
                    case 0:
                        AuthPanelController authPanelController = this.f$0;
                        authPanelController.getClass();
                        authPanelController.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                    case 1:
                        AuthPanelController authPanelController2 = this.f$0;
                        authPanelController2.getClass();
                        authPanelController2.mCornerRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        break;
                    case 2:
                        AuthPanelController authPanelController3 = this.f$0;
                        authPanelController3.getClass();
                        authPanelController3.mContentHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        authPanelController3.mPanelView.invalidateOutline();
                        break;
                    default:
                        AuthPanelController authPanelController4 = this.f$0;
                        authPanelController4.getClass();
                        authPanelController4.mContentWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                }
            }
        });
        ValueAnimator ofInt2 = ValueAnimator.ofInt(this.mContentHeight, i2);
        ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda0
            public final /* synthetic */ AuthPanelController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i5) {
                    case 0:
                        AuthPanelController authPanelController = this.f$0;
                        authPanelController.getClass();
                        authPanelController.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                    case 1:
                        AuthPanelController authPanelController2 = this.f$0;
                        authPanelController2.getClass();
                        authPanelController2.mCornerRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        break;
                    case 2:
                        AuthPanelController authPanelController3 = this.f$0;
                        authPanelController3.getClass();
                        authPanelController3.mContentHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        authPanelController3.mPanelView.invalidateOutline();
                        break;
                    default:
                        AuthPanelController authPanelController4 = this.f$0;
                        authPanelController4.getClass();
                        authPanelController4.mContentWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                }
            }
        });
        ValueAnimator ofInt3 = ValueAnimator.ofInt(this.mContentWidth, i);
        final int i7 = 3;
        ofInt3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda0
            public final /* synthetic */ AuthPanelController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i7) {
                    case 0:
                        AuthPanelController authPanelController = this.f$0;
                        authPanelController.getClass();
                        authPanelController.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                    case 1:
                        AuthPanelController authPanelController2 = this.f$0;
                        authPanelController2.getClass();
                        authPanelController2.mCornerRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        break;
                    case 2:
                        AuthPanelController authPanelController3 = this.f$0;
                        authPanelController3.getClass();
                        authPanelController3.mContentHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        authPanelController3.mPanelView.invalidateOutline();
                        break;
                    default:
                        AuthPanelController authPanelController4 = this.f$0;
                        authPanelController4.getClass();
                        authPanelController4.mContentWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        break;
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(i3);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(ofFloat, ofInt2, ofInt3, ofInt);
        animatorSet.start();
    }
}
