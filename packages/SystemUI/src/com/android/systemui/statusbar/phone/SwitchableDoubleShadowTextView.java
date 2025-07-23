package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.shared.R$styleable;
import com.android.systemui.shared.shadow.DoubleShadowIconDrawable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SwitchableDoubleShadowTextView extends TextView {
    public boolean aggregatedVisible;
    public final DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    public final DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;
    public boolean shadowEnabled;

    /* renamed from: $r8$lambda$beoBxlXxQAhP91V-cdfHl-HB8rI, reason: not valid java name */
    public static Unit m3077$r8$lambda$beoBxlXxQAhP91VcdfHlHB8rI(SwitchableDoubleShadowTextView switchableDoubleShadowTextView, Canvas canvas) {
        super.onDraw(canvas);
        return Unit.INSTANCE;
    }

    public SwitchableDoubleShadowTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(final Canvas canvas) {
        if (!this.shadowEnabled) {
            super.onDraw(canvas);
            return;
        }
        DoubleShadowTextHelper doubleShadowTextHelper = DoubleShadowTextHelper.INSTANCE;
        DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
        DoubleShadowTextHelper.ShadowInfo shadowInfo2 = this.mAmbientShadowInfo;
        Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SwitchableDoubleShadowTextView.m3077$r8$lambda$beoBxlXxQAhP91VcdfHlHB8rI(SwitchableDoubleShadowTextView.this, canvas);
            }
        };
        doubleShadowTextHelper.getClass();
        DoubleShadowTextHelper.applyShadows(shadowInfo, shadowInfo2, this, canvas, function0);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z == this.aggregatedVisible) {
            return;
        }
        this.aggregatedVisible = z;
        if (z) {
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
            setSelected(true);
        } else {
            setEllipsize(TextUtils.TruncateAt.END);
            setSelected(false);
        }
    }

    public SwitchableDoubleShadowTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public SwitchableDoubleShadowTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ SwitchableDoubleShadowTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public SwitchableDoubleShadowTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DoubleShadowTextView, i, i2);
        try {
            this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(obtainStyledAttributes.getDimension(7, 0.0f), obtainStyledAttributes.getDimension(8, 0.0f), obtainStyledAttributes.getDimension(9, 0.0f), obtainStyledAttributes.getFloat(6, 0.0f));
            this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(obtainStyledAttributes.getDimension(1, 0.0f), obtainStyledAttributes.getDimension(2, 0.0f), obtainStyledAttributes.getDimension(3, 0.0f), obtainStyledAttributes.getFloat(0, 0.0f));
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(4, 0);
            obtainStyledAttributes.recycle();
            Drawable[] drawableArr = new Drawable[4];
            drawableArr[0] = null;
            drawableArr[1] = null;
            drawableArr[2] = null;
            drawableArr[3] = null;
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            int length = compoundDrawablesRelative.length;
            for (int i3 = 0; i3 < length; i3++) {
                Drawable drawable = compoundDrawablesRelative[i3];
                if (drawable != null) {
                    drawableArr[i3] = new DoubleShadowIconDrawable(this.mKeyShadowInfo, this.mAmbientShadowInfo, drawable, dimensionPixelSize, dimensionPixelSize2);
                }
            }
            setCompoundDrawablesRelative(drawableArr[0], drawableArr[1], drawableArr[2], drawableArr[3]);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
