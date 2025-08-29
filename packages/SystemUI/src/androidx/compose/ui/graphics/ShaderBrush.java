package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.ui.geometry.Size;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;

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
    public final void mo451applyToPq9zytI(float f, long j, Paint paint) {
        Shader shaderMo453createShaderuvyYCjk = this.internalShader;
        if (shaderMo453createShaderuvyYCjk == null || !Size.m416equalsimpl0(this.createdSize, j)) {
            if (Size.m420isEmptyimpl(j)) {
                shaderMo453createShaderuvyYCjk = null;
                this.internalShader = null;
                Size.Companion.getClass();
                this.createdSize = Size.Unspecified;
            } else {
                shaderMo453createShaderuvyYCjk = mo453createShaderuvyYCjk(j);
                this.internalShader = shaderMo453createShaderuvyYCjk;
                this.createdSize = j;
            }
        }
        AndroidPaint androidPaint = (AndroidPaint) paint;
        long jColor = ColorKt.Color(androidPaint.internalPaint.getColor());
        Color.Companion.getClass();
        long j2 = Color.Black;
        if (!ULong.m3446equalsimpl0(jColor, j2)) {
            androidPaint.m440setColor8_81llA(j2);
        }
        if (!Intrinsics.areEqual(androidPaint.internalShader, shaderMo453createShaderuvyYCjk)) {
            androidPaint.setShader(shaderMo453createShaderuvyYCjk);
        }
        if (androidPaint.internalPaint.getAlpha() / 255.0f == f) {
            return;
        }
        androidPaint.setAlpha(f);
    }

    /* renamed from: createShader-uvyYCjk */
    public abstract Shader mo453createShaderuvyYCjk(long j);
}
