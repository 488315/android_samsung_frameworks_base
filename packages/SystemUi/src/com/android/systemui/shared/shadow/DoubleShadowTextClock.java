package com.android.systemui.shared.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextClock;
import com.android.systemui.shared.R$styleable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DoubleShadowTextClock extends TextClock {
    public final DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    public final DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;

    public DoubleShadowTextClock(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(final Canvas canvas) {
        DoubleShadowTextHelper doubleShadowTextHelper = DoubleShadowTextHelper.INSTANCE;
        DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
        DoubleShadowTextHelper.ShadowInfo shadowInfo2 = this.mAmbientShadowInfo;
        Function0 function0 = new Function0() { // from class: com.android.systemui.shared.shadow.DoubleShadowTextClock$onDraw$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                super/*android.widget.TextClock*/.onDraw(canvas);
                return Unit.INSTANCE;
            }
        };
        doubleShadowTextHelper.getClass();
        DoubleShadowTextHelper.applyShadows(shadowInfo, shadowInfo2, this, canvas, function0);
    }

    public DoubleShadowTextClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DoubleShadowTextClock, i, i2);
        try {
            this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(obtainStyledAttributes.getDimensionPixelSize(5, 0), obtainStyledAttributes.getDimensionPixelSize(6, 0), obtainStyledAttributes.getDimensionPixelSize(7, 0), obtainStyledAttributes.getFloat(4, 0.0f));
            this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(obtainStyledAttributes.getDimensionPixelSize(1, 0), obtainStyledAttributes.getDimensionPixelSize(2, 0), obtainStyledAttributes.getDimensionPixelSize(3, 0), obtainStyledAttributes.getFloat(0, 0.0f));
            boolean z = obtainStyledAttributes.getBoolean(8, false);
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(9, 0);
            if (z) {
                setPaddingRelative(0, 0, 0, dimensionPixelSize - ((int) Math.floor(getPaint().getFontMetrics().descent)));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
