package androidx.compose.ui.unit;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.fontscaling.FontScaleConverter;
import androidx.compose.ui.unit.fontscaling.FontScaleConverterFactory;

/* loaded from: classes.dex */
public interface FontScaling {
    float getFontScale();

    /* renamed from: toDp-GaN1DYA */
    default float mo53toDpGaN1DYA(long j) {
        long jM869getTypeUIouoOA = TextUnit.m869getTypeUIouoOA(j);
        TextUnitType.Companion.getClass();
        if (!TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp)) {
            InlineClassHelperKt.throwIllegalStateException("Only Sp can convert to Px");
        }
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        float fontScale = getFontScale();
        fontScaleConverterFactory.getClass();
        if (fontScale < 1.03f) {
            float fontScale2 = getFontScale() * TextUnit.m870getValueimpl(j);
            Dp.Companion companion = Dp.Companion;
            return fontScale2;
        }
        FontScaleConverter fontScaleConverterForScale = FontScaleConverterFactory.forScale(getFontScale());
        if (fontScaleConverterForScale != null) {
            float fConvertSpToDp = fontScaleConverterForScale.convertSpToDp(TextUnit.m870getValueimpl(j));
            Dp.Companion companion2 = Dp.Companion;
            return fConvertSpToDp;
        }
        float fontScale3 = getFontScale() * TextUnit.m870getValueimpl(j);
        Dp.Companion companion3 = Dp.Companion;
        return fontScale3;
    }

    /* renamed from: toSp-0xMU5do */
    default long mo60toSp0xMU5do(float f) {
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        float fontScale = getFontScale();
        fontScaleConverterFactory.getClass();
        if (!(fontScale >= 1.03f)) {
            return TextUnitKt.pack(f / getFontScale(), 4294967296L);
        }
        FontScaleConverter fontScaleConverterForScale = FontScaleConverterFactory.forScale(getFontScale());
        return TextUnitKt.pack(fontScaleConverterForScale != null ? fontScaleConverterForScale.convertDpToSp(f) : f / getFontScale(), 4294967296L);
    }
}
