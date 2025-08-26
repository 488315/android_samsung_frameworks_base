package androidx.compose.ui.text.platform.style;

import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import androidx.compose.ui.graphics.AndroidPathEffect;
import androidx.compose.ui.graphics.PathEffect;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class DrawStyleSpan extends CharacterStyle implements UpdateAppearance {
    public final DrawStyle drawStyle;

    public DrawStyleSpan(DrawStyle drawStyle) {
        this.drawStyle = drawStyle;
    }

    @Override // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        if (textPaint != null) {
            DrawStyle drawStyle = this.drawStyle;
            if (Intrinsics.areEqual(drawStyle, Fill.INSTANCE)) {
                textPaint.setStyle(Paint.Style.FILL);
                return;
            }
            if (drawStyle instanceof Stroke) {
                textPaint.setStyle(Paint.Style.STROKE);
                textPaint.setStrokeWidth(((Stroke) this.drawStyle).width);
                textPaint.setStrokeMiter(((Stroke) this.drawStyle).miter);
                textPaint.setStrokeJoin(DrawStyleSpan_androidKt.m792toAndroidJoinWw9F2mQ(((Stroke) this.drawStyle).join));
                textPaint.setStrokeCap(DrawStyleSpan_androidKt.m791toAndroidCapBeK7IIE(((Stroke) this.drawStyle).cap));
                PathEffect pathEffect = ((Stroke) this.drawStyle).pathEffect;
                textPaint.setPathEffect(pathEffect != null ? ((AndroidPathEffect) pathEffect).nativePathEffect : null);
            }
        }
    }
}
