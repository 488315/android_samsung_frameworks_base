package com.android.systemui.shared.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.shared.R$styleable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public class DoubleShadowTextView extends TextView {
    private DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    private DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;

    public static Unit $r8$lambda$yC14j5wcj7kwjbyrqQmbREx4iTg(DoubleShadowTextView doubleShadowTextView, Canvas canvas) {
        super.onDraw(canvas);
        return Unit.INSTANCE;
    }

    public DoubleShadowTextView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(final Canvas canvas) {
        DoubleShadowTextHelper doubleShadowTextHelper = DoubleShadowTextHelper.INSTANCE;
        DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
        if (shadowInfo == null) {
            shadowInfo = null;
        }
        DoubleShadowTextHelper.ShadowInfo shadowInfo2 = this.mAmbientShadowInfo;
        DoubleShadowTextHelper.ShadowInfo shadowInfo3 = shadowInfo2 != null ? shadowInfo2 : null;
        Function0 function0 = new Function0() { // from class: com.android.systemui.shared.shadow.DoubleShadowTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DoubleShadowTextView.$r8$lambda$yC14j5wcj7kwjbyrqQmbREx4iTg(this.f$0, canvas);
            }
        };
        doubleShadowTextHelper.getClass();
        DoubleShadowTextHelper.applyShadows(shadowInfo, shadowInfo3, this, canvas, function0);
    }

    @Override // android.widget.TextView
    public void setTextAppearance(int i) {
        super.setTextAppearance(i);
        updateShadowDrawables(getContext().obtainStyledAttributes(i, R$styleable.DoubleShadowTextView));
    }

    public final void updateShadowDrawables(TypedArray typedArray) {
        try {
            this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArray.getDimension(7, 0.0f), typedArray.getDimension(8, 0.0f), typedArray.getDimension(9, 0.0f), typedArray.getFloat(6, 0.0f));
            this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArray.getDimension(1, 0.0f), typedArray.getDimension(2, 0.0f), typedArray.getDimension(3, 0.0f), typedArray.getFloat(0, 0.0f));
            int dimensionPixelSize = typedArray.getDimensionPixelSize(5, 0);
            int dimensionPixelSize2 = typedArray.getDimensionPixelSize(4, 0);
            typedArray.recycle();
            Drawable[] drawableArr = new Drawable[4];
            drawableArr[0] = null;
            drawableArr[1] = null;
            drawableArr[2] = null;
            drawableArr[3] = null;
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            int length = compoundDrawablesRelative.length;
            for (int i = 0; i < length; i++) {
                Drawable drawable = compoundDrawablesRelative[i];
                if (drawable != null) {
                    DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
                    if (shadowInfo == null) {
                        shadowInfo = null;
                    }
                    DoubleShadowTextHelper.ShadowInfo shadowInfo2 = this.mAmbientShadowInfo;
                    if (shadowInfo2 == null) {
                        shadowInfo2 = null;
                    }
                    drawableArr[i] = new DoubleShadowIconDrawable(shadowInfo, shadowInfo2, drawable, dimensionPixelSize, dimensionPixelSize2);
                }
            }
            setCompoundDrawablesRelative(drawableArr[0], drawableArr[1], drawableArr[2], drawableArr[3]);
        } catch (Throwable th) {
            typedArray.recycle();
            throw th;
        }
    }

    public DoubleShadowTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public DoubleShadowTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ DoubleShadowTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public DoubleShadowTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        updateShadowDrawables(context.obtainStyledAttributes(attributeSet, R$styleable.DoubleShadowTextView, i, i2));
    }
}
