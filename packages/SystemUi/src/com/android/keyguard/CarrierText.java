package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Debug;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R$styleable;
import com.android.systemui.slimindicator.SlimIndicatorKeyguardCarrierTextHelper;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class CarrierText extends TextView {
    public final String mDebugLocation;
    public float mFontSize;
    public String mSetTextSizeCaller;
    public final boolean mShowAirplaneMode;
    public final boolean mShowMissingSim;
    public SlimIndicatorKeyguardCarrierTextHelper mSlimIndicatorKeyguardCarrierTextInterface;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CarrierTextTransformationMethod extends SingleLineTransformationMethod {
        public final boolean mAllCaps;
        public final Locale mLocale;

        public CarrierTextTransformationMethod(Context context, boolean z) {
            this.mLocale = context.getResources().getConfiguration().locale;
            this.mAllCaps = z;
        }

        @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
        public final CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view);
            return (!this.mAllCaps || transformation == null) ? transformation : transformation.toString().toUpperCase(this.mLocale);
        }
    }

    public CarrierText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        this.mFontSize = f;
        this.mSetTextSizeCaller = Debug.getCallers(6);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper = this.mSlimIndicatorKeyguardCarrierTextInterface;
        if (slimIndicatorKeyguardCarrierTextHelper == null) {
            super.setVisibility(i);
            return;
        }
        slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility = i;
        if (i == 0) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator;
            boolean z = false;
            if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
                if (slimIndicatorViewMediatorImpl.mCarrierCrew.mIsLockCarrierDisabled == 1) {
                    z = true;
                }
            }
            if (z) {
                i = 8;
            }
        }
        super.setVisibility(i);
    }

    public CarrierText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.CarrierText, 0, 0);
        try {
            boolean z = obtainStyledAttributes.getBoolean(0, false);
            this.mShowAirplaneMode = obtainStyledAttributes.getBoolean(2, false);
            this.mShowMissingSim = obtainStyledAttributes.getBoolean(3, false);
            this.mDebugLocation = obtainStyledAttributes.getString(1);
            obtainStyledAttributes.recycle();
            setTransformationMethod(new CarrierTextTransformationMethod(((TextView) this).mContext, z));
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
