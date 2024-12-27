package com.android.keyguard;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardClockFrame extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public int drawAlpha;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static void saveCanvasAlpha(View view, Canvas canvas, int i, Function1 function1) {
            if (i <= 0) {
                return;
            }
            if (i >= 255) {
                function1.invoke(canvas);
                return;
            }
            view.getLocationOnScreen(new int[2]);
            Pair pair = new Pair(Float.valueOf(r0[0]), Float.valueOf(r0[1]));
            float floatValue = ((Number) pair.component1()).floatValue();
            float floatValue2 = ((Number) pair.component2()).floatValue();
            int saveLayerAlpha = canvas.saveLayerAlpha(floatValue * (-1.0f), floatValue2 * (-1.0f), floatValue + view.getWidth(), floatValue2 + view.getHeight(), i);
            function1.invoke(canvas);
            canvas.restoreToCount(saveLayerAlpha);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardClockFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.drawAlpha = 255;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Companion companion = Companion;
        int i = this.drawAlpha;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.KeyguardClockFrame$dispatchDraw$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.FrameLayout*/.dispatchDraw((Canvas) obj);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        Companion.saveCanvasAlpha(this, canvas, i, function1);
    }

    @Override // android.view.View
    public final boolean onSetAlpha(int i) {
        this.drawAlpha = (int) (getTransitionAlpha() * getAlpha() * 255);
        return true;
    }
}
