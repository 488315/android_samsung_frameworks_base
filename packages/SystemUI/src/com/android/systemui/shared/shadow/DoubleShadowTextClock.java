package com.android.systemui.shared.shadow;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextClock;
import com.android.systemui.R;
import com.android.systemui.shared.R$styleable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DoubleShadowTextClock extends TextClock {
    public static final int paddingDividedOffset;
    public final TypedArray attributesInput;
    public DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    public DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;
    public final Resources resources;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public static Unit $r8$lambda$y586VqXeueHNllCFpB83wsDCduE(DoubleShadowTextClock doubleShadowTextClock, Canvas canvas) {
        super.onDraw(canvas);
        return Unit.INSTANCE;
    }

    static {
        new Companion(null);
        paddingDividedOffset = 2;
    }

    public DoubleShadowTextClock(Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
    }

    public final void initializeAttributes(AttributeSet attributeSet, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = this.attributesInput;
        if (typedArrayObtainStyledAttributes == null) {
            typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.DoubleShadowTextClock, i, i2);
        }
        Resources resources = this.resources;
        if (resources == null) {
            resources = getContext().getResources();
        }
        try {
            this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArrayObtainStyledAttributes.getDimensionPixelSize(5, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0), typedArrayObtainStyledAttributes.getFloat(4, 0.0f));
            this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(3, 0), typedArrayObtainStyledAttributes.getFloat(0, 0.0f));
            boolean z = typedArrayObtainStyledAttributes.getBoolean(8, false);
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, 0);
            if (z) {
                boolean z2 = resources.getBoolean(R.bool.dream_overlay_complication_clock_bottom_padding);
                Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
                setPaddingRelative(0, 0, 0, z2 ? dimensionPixelSize + (((int) Math.floor(fontMetrics.descent)) / paddingDividedOffset) : dimensionPixelSize - ((int) Math.floor(fontMetrics.descent)));
            }
            typedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(final Canvas canvas) {
        DoubleShadowTextHelper doubleShadowTextHelper = DoubleShadowTextHelper.INSTANCE;
        DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
        if (shadowInfo == null) {
            shadowInfo = null;
        }
        DoubleShadowTextHelper.ShadowInfo shadowInfo2 = this.mAmbientShadowInfo;
        DoubleShadowTextHelper.ShadowInfo shadowInfo3 = shadowInfo2 != null ? shadowInfo2 : null;
        Function0 function0 = new Function0() { // from class: com.android.systemui.shared.shadow.DoubleShadowTextClock$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return DoubleShadowTextClock.$r8$lambda$y586VqXeueHNllCFpB83wsDCduE(this.f$0, canvas);
            }
        };
        doubleShadowTextHelper.getClass();
        DoubleShadowTextHelper.applyShadows(shadowInfo, shadowInfo3, this, canvas, function0);
    }

    public DoubleShadowTextClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
    }

    public DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initializeAttributes(attributeSet, i, i2);
    }

    public /* synthetic */ DoubleShadowTextClock(Resources resources, Context context, AttributeSet attributeSet, int i, int i2, TypedArray typedArray, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(resources, context, (i3 & 4) != 0 ? null : attributeSet, (i3 & 8) != 0 ? 0 : i, (i3 & 16) != 0 ? 0 : i2, (i3 & 32) != 0 ? null : typedArray);
    }

    public DoubleShadowTextClock(Resources resources, Context context, AttributeSet attributeSet, int i, int i2, TypedArray typedArray) {
        this(context, attributeSet, i, i2);
        this.attributesInput = typedArray;
        this.resources = resources;
        initializeAttributes(attributeSet, i, i2);
    }
}
