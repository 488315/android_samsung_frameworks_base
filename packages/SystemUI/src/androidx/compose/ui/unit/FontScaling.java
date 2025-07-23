package androidx.compose.ui.unit;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.fontscaling.FontScaleConverter;
import androidx.compose.ui.unit.fontscaling.FontScaleConverterFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface FontScaling {
    float getFontScale();

    /* renamed from: toDp-GaN1DYA */
    default float mo52toDpGaN1DYA(long j) {
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion.getClass();
        if (!TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            InlineClassHelperKt.throwIllegalStateException("Only Sp can convert to Px");
        }
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        float fontScale = getFontScale();
        fontScaleConverterFactory.getClass();
        if (fontScale < 1.03f) {
            float fontScale2 = getFontScale() * TextUnit.m868getValueimpl(j);
            Dp.Companion companion = Dp.Companion;
            return fontScale2;
        }
        FontScaleConverter forScale = FontScaleConverterFactory.forScale(getFontScale());
        if (forScale != null) {
            float convertSpToDp = forScale.convertSpToDp(TextUnit.m868getValueimpl(j));
            Dp.Companion companion2 = Dp.Companion;
            return convertSpToDp;
        }
        float fontScale3 = getFontScale() * TextUnit.m868getValueimpl(j);
        Dp.Companion companion3 = Dp.Companion;
        return fontScale3;
    }

    /* renamed from: toSp-0xMU5do */
    default long mo59toSp0xMU5do(float f) {
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        float fontScale = getFontScale();
        fontScaleConverterFactory.getClass();
        if (!(fontScale >= 1.03f)) {
            return TextUnitKt.pack(f / getFontScale(), 4294967296L);
        }
        FontScaleConverter forScale = FontScaleConverterFactory.forScale(getFontScale());
        return TextUnitKt.pack(forScale != null ? forScale.convertDpToSp(f) : f / getFontScale(), 4294967296L);
    }
}
