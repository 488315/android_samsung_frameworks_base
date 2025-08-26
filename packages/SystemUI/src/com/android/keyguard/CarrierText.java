package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Debug;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.android.systemui.res.R$styleable;
import com.android.systemui.slimindicator.SlimIndicatorKeyguardCarrierTextHelper;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.util.DeviceState;
import java.util.Locale;

/* loaded from: classes.dex */
public class CarrierText extends TextView {
    public final String mDebugLocation;
    public float mFontSize;
    public String mSetTextSizeCaller;
    public final boolean mShowAirplaneMode;
    public final boolean mShowMissingSim;
    public SlimIndicatorKeyguardCarrierTextHelper mSlimIndicatorKeyguardCarrierTextInterface;

    public class CarrierTextTransformationMethod extends SingleLineTransformationMethod {
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

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
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
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        if (charSequence != null) {
            setLetterSpacing(charSequence.length() > 30 ? -0.05f : 0.0f);
        }
        if (DeviceState.isTestModeIndicatorGarden()) {
            super.setText("A23456789 B23456789 C23456789 D23456789 E23456789", bufferType);
        } else {
            super.setText(charSequence, bufferType);
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
        if (i != 8) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator;
            if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsLockCarrierDisabled == 1) {
                i = 8;
            }
        }
        super.setVisibility(i);
    }

    public CarrierText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.CarrierText, 0, 0);
        try {
            boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
            this.mShowAirplaneMode = typedArrayObtainStyledAttributes.getBoolean(2, false);
            this.mShowMissingSim = typedArrayObtainStyledAttributes.getBoolean(3, false);
            this.mDebugLocation = typedArrayObtainStyledAttributes.getString(1);
            typedArrayObtainStyledAttributes.recycle();
            setTransformationMethod(new CarrierTextTransformationMethod(((TextView) this).mContext, z));
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
