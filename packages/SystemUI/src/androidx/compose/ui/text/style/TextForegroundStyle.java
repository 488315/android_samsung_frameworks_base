package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.SolidColor;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public interface TextForegroundStyle {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static TextForegroundStyle from(float f, Brush brush) {
            if (brush == null) {
                return Unspecified.INSTANCE;
            }
            if (brush instanceof SolidColor) {
                return m812from8_81llA(TextDrawStyleKt.m811modulateDxMtmZc(f, ((SolidColor) brush).value));
            }
            if (brush instanceof ShaderBrush) {
                return new BrushStyle((ShaderBrush) brush, f);
            }
            throw new NoWhenBranchMatchedException();
        }

        /* renamed from: from-8_81llA, reason: not valid java name */
        public static TextForegroundStyle m812from8_81llA(long j) {
            return j != 16 ? new ColorStyle(j, null) : Unspecified.INSTANCE;
        }
    }

    public final class Unspecified implements TextForegroundStyle {
        public static final Unspecified INSTANCE = new Unspecified();

        private Unspecified() {
        }

        @Override // androidx.compose.ui.text.style.TextForegroundStyle
        public final float getAlpha() {
            return Float.NaN;
        }

        @Override // androidx.compose.ui.text.style.TextForegroundStyle
        public final Brush getBrush() {
            return null;
        }

        @Override // androidx.compose.ui.text.style.TextForegroundStyle
        /* renamed from: getColor-0d7_KjU */
        public final long mo794getColor0d7_KjU() {
            Color.Companion.getClass();
            return Color.Unspecified;
        }
    }

    float getAlpha();

    Brush getBrush();

    /* renamed from: getColor-0d7_KjU */
    long mo794getColor0d7_KjU();

    default TextForegroundStyle merge(TextForegroundStyle textForegroundStyle) {
        boolean z = textForegroundStyle instanceof BrushStyle;
        if (z && (this instanceof BrushStyle)) {
            ShaderBrush shaderBrush = ((BrushStyle) textForegroundStyle).value;
            float fFloatValue = ((BrushStyle) textForegroundStyle).alpha;
            Function0 function0 = new Function0() { // from class: androidx.compose.ui.text.style.TextForegroundStyle.merge.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Float.valueOf(TextForegroundStyle.this.getAlpha());
                }
            };
            if (Float.isNaN(fFloatValue)) {
                fFloatValue = ((Number) function0.invoke()).floatValue();
            }
            return new BrushStyle(shaderBrush, fFloatValue);
        }
        if (z && !(this instanceof BrushStyle)) {
            return textForegroundStyle;
        }
        if (!z && (this instanceof BrushStyle)) {
            return this;
        }
        Function0 function02 = new Function0() { // from class: androidx.compose.ui.text.style.TextForegroundStyle.merge.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TextForegroundStyle.this;
            }
        };
        textForegroundStyle.getClass();
        return !textForegroundStyle.equals(Unspecified.INSTANCE) ? textForegroundStyle : (TextForegroundStyle) function02.invoke();
    }
}
