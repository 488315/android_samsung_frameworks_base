package com.android.keyguard;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardClockFrame;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public final class KeyguardStatusContainer extends LinearLayout {
    public int drawAlpha;

    public KeyguardStatusContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.drawAlpha = 255;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(final Canvas canvas) {
        KeyguardClockFrame.Companion companion = KeyguardClockFrame.Companion;
        int i = this.drawAlpha;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.KeyguardStatusContainer$dispatchDraw$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.dispatchDraw(canvas);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        KeyguardClockFrame.Companion.saveCanvasAlpha(this, canvas, i, function1);
    }

    @Override // android.view.View
    public final boolean onSetAlpha(int i) {
        this.drawAlpha = i;
        return true;
    }
}
