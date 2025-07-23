package androidx.compose.material3;

import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.material3.tokens.CircularProgressIndicatorTokens;
import androidx.compose.material3.tokens.LinearProgressIndicatorTokens;
import androidx.compose.material3.tokens.MotionTokens;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ProgressIndicatorKt {
    public static final float CircularIndicatorDiameter;
    public static final CubicBezierEasing CircularProgressEasing;

    static {
        Dp.Companion companion = Dp.Companion;
        LinearProgressIndicatorTokens.INSTANCE.getClass();
        CircularProgressIndicatorTokens.INSTANCE.getClass();
        CircularIndicatorDiameter = CircularProgressIndicatorTokens.Size;
        MotionTokens motionTokens = MotionTokens.INSTANCE;
        motionTokens.getClass();
        motionTokens.getClass();
        CircularProgressEasing = MotionTokens.EasingStandardCubicBezier;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00f3  */
    /* renamed from: CircularProgressIndicator-4lLiAd8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m278CircularProgressIndicator4lLiAd8(androidx.compose.ui.Modifier r31, long r32, float r34, long r35, int r37, float r38, androidx.compose.runtime.Composer r39, final int r40, final int r41) {
        /*
            Method dump skipped, instructions count: 763
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ProgressIndicatorKt.m278CircularProgressIndicator4lLiAd8(androidx.compose.ui.Modifier, long, float, long, int, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: drawCircularIndicator-42QJj7c, reason: not valid java name */
    public static final void m279drawCircularIndicator42QJj7c(DrawScope drawScope, float f, float f2, long j, Stroke stroke) {
        float f3 = 2;
        float f4 = stroke.width / f3;
        float m417getWidthimpl = Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()) - (f3 * f4);
        long Offset = OffsetKt.Offset(f4, f4);
        long Size = SizeKt.Size(m417getWidthimpl, m417getWidthimpl);
        DrawScope.Companion.getClass();
        drawScope.mo516drawArcyD3GUKo(j, f, f2, Offset, Size, stroke, DrawScope.Companion.DefaultBlendMode);
    }
}
