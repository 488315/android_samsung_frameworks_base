package com.android.systemui.shared.shadow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.widget.TextView;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DoubleShadowTextHelper {
    public static final DoubleShadowTextHelper INSTANCE = new DoubleShadowTextHelper();

    private DoubleShadowTextHelper() {
    }

    public static void applyShadows(ShadowInfo shadowInfo, ShadowInfo shadowInfo2, TextView textView, Canvas canvas, Function0 function0) {
        TextPaint paint = textView.getPaint();
        int argb = Color.argb(shadowInfo2.alpha, 0.0f, 0.0f, 0.0f);
        paint.setShadowLayer(shadowInfo2.blur, shadowInfo2.offsetX, shadowInfo2.offsetY, argb);
        function0.invoke();
        canvas.save();
        canvas.clipRect(textView.getScrollX(), textView.getExtendedPaddingTop() + textView.getScrollY(), textView.getWidth() + textView.getScrollX(), textView.getHeight() + textView.getScrollY());
        TextPaint paint2 = textView.getPaint();
        int argb2 = Color.argb(shadowInfo.alpha, 0.0f, 0.0f, 0.0f);
        paint2.setShadowLayer(shadowInfo.blur, shadowInfo.offsetX, shadowInfo.offsetY, argb2);
        function0.invoke();
        canvas.restore();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShadowInfo {
        public final float alpha;
        public final float blur;
        public final float offsetX;
        public final float offsetY;

        public ShadowInfo(float f, float f2, float f3, float f4) {
            this.blur = f;
            this.offsetX = f2;
            this.offsetY = f3;
            this.alpha = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShadowInfo)) {
                return false;
            }
            ShadowInfo shadowInfo = (ShadowInfo) obj;
            return Float.compare(this.blur, shadowInfo.blur) == 0 && Float.compare(this.offsetX, shadowInfo.offsetX) == 0 && Float.compare(this.offsetY, shadowInfo.offsetY) == 0 && Float.compare(this.alpha, shadowInfo.alpha) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.alpha) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.offsetY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.offsetX, Float.hashCode(this.blur) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ShadowInfo(blur=");
            sb.append(this.blur);
            sb.append(", offsetX=");
            sb.append(this.offsetX);
            sb.append(", offsetY=");
            sb.append(this.offsetY);
            sb.append(", alpha=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.alpha, ")");
        }

        public /* synthetic */ ShadowInfo(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, f4);
        }
    }
}
