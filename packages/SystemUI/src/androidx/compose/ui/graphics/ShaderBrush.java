package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.ui.geometry.Size;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ShaderBrush extends Brush {
    public long createdSize;
    public Shader internalShader;

    public ShaderBrush() {
        super(null);
        Size.Companion.getClass();
        this.createdSize = Size.Unspecified;
    }

    @Override // androidx.compose.ui.graphics.Brush
    /* renamed from: applyTo-Pq9zytI */
    public final void mo449applyToPq9zytI(float f, long j, Paint paint) {
        Shader shader = this.internalShader;
        if (shader == null || !Size.m414equalsimpl0(this.createdSize, j)) {
            if (Size.m418isEmptyimpl(j)) {
                shader = null;
                this.internalShader = null;
                Size.Companion.getClass();
                this.createdSize = Size.Unspecified;
            } else {
                shader = mo451createShaderuvyYCjk(j);
                this.internalShader = shader;
                this.createdSize = j;
            }
        }
        AndroidPaint androidPaint = (AndroidPaint) paint;
        long Color = ColorKt.Color(androidPaint.internalPaint.getColor());
        Color.Companion.getClass();
        long j2 = Color.Black;
        if (!ULong.m3427equalsimpl0(Color, j2)) {
            androidPaint.m438setColor8_81llA(j2);
        }
        if (!Intrinsics.areEqual(androidPaint.internalShader, shader)) {
            androidPaint.setShader(shader);
        }
        if (androidPaint.internalPaint.getAlpha() / 255.0f == f) {
            return;
        }
        androidPaint.setAlpha(f);
    }

    /* renamed from: createShader-uvyYCjk */
    public abstract Shader mo451createShaderuvyYCjk(long j);
}
