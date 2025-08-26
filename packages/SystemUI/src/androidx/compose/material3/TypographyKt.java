package androidx.compose.material3;

import androidx.compose.material3.tokens.TypographyKeyTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.text.TextStyle;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class TypographyKt {
    public static final StaticProvidableCompositionLocal LocalTypography = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.TypographyKt$LocalTypography$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Typography(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 32767, null);
        }
    });

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypographyKeyTokens.values().length];
            try {
                iArr[TypographyKeyTokens.DisplayLarge.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypographyKeyTokens.DisplayMedium.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypographyKeyTokens.DisplaySmall.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TypographyKeyTokens.HeadlineLarge.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TypographyKeyTokens.HeadlineMedium.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TypographyKeyTokens.HeadlineSmall.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TypographyKeyTokens.TitleLarge.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[TypographyKeyTokens.TitleMedium.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[TypographyKeyTokens.TitleSmall.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[TypographyKeyTokens.BodyLarge.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[TypographyKeyTokens.BodyMedium.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[TypographyKeyTokens.BodySmall.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[TypographyKeyTokens.LabelLarge.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[TypographyKeyTokens.LabelMedium.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[TypographyKeyTokens.LabelSmall.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[TypographyKeyTokens.DisplayLargeEmphasized.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[TypographyKeyTokens.DisplayMediumEmphasized.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[TypographyKeyTokens.DisplaySmallEmphasized.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[TypographyKeyTokens.HeadlineLargeEmphasized.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[TypographyKeyTokens.HeadlineMediumEmphasized.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[TypographyKeyTokens.HeadlineSmallEmphasized.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[TypographyKeyTokens.TitleLargeEmphasized.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[TypographyKeyTokens.TitleMediumEmphasized.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[TypographyKeyTokens.TitleSmallEmphasized.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[TypographyKeyTokens.BodyLargeEmphasized.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[TypographyKeyTokens.BodyMediumEmphasized.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[TypographyKeyTokens.BodySmallEmphasized.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[TypographyKeyTokens.LabelLargeEmphasized.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[TypographyKeyTokens.LabelMediumEmphasized.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[TypographyKeyTokens.LabelSmallEmphasized.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final TextStyle getValue(TypographyKeyTokens typographyKeyTokens, Composer composer) {
        TextStyle textStyle;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.<get-value> (Typography.kt:586)");
        }
        MaterialTheme.INSTANCE.getClass();
        Typography typography = MaterialTheme.getTypography(composer);
        switch (WhenMappings.$EnumSwitchMapping$0[typographyKeyTokens.ordinal()]) {
            case 1:
                textStyle = typography.displayLarge;
                break;
            case 2:
                textStyle = typography.displayMedium;
                break;
            case 3:
                textStyle = typography.displaySmall;
                break;
            case 4:
                textStyle = typography.headlineLarge;
                break;
            case 5:
                textStyle = typography.headlineMedium;
                break;
            case 6:
                textStyle = typography.headlineSmall;
                break;
            case 7:
                textStyle = typography.titleLarge;
                break;
            case 8:
                textStyle = typography.titleMedium;
                break;
            case 9:
                textStyle = typography.titleSmall;
                break;
            case 10:
                textStyle = typography.bodyLarge;
                break;
            case 11:
                textStyle = typography.bodyMedium;
                break;
            case 12:
                textStyle = typography.bodySmall;
                break;
            case 13:
                textStyle = typography.labelLarge;
                break;
            case 14:
                textStyle = typography.labelMedium;
                break;
            case 15:
                textStyle = typography.labelSmall;
                break;
            case 16:
                textStyle = typography.displayLargeEmphasized;
                break;
            case 17:
                textStyle = typography.displayMediumEmphasized;
                break;
            case 18:
                textStyle = typography.displaySmallEmphasized;
                break;
            case 19:
                textStyle = typography.headlineLargeEmphasized;
                break;
            case 20:
                textStyle = typography.headlineMediumEmphasized;
                break;
            case 21:
                textStyle = typography.headlineSmallEmphasized;
                break;
            case 22:
                textStyle = typography.titleLargeEmphasized;
                break;
            case 23:
                textStyle = typography.titleMediumEmphasized;
                break;
            case 24:
                textStyle = typography.titleSmallEmphasized;
                break;
            case 25:
                textStyle = typography.bodyLargeEmphasized;
                break;
            case 26:
                textStyle = typography.bodyMediumEmphasized;
                break;
            case 27:
                textStyle = typography.bodySmallEmphasized;
                break;
            case 28:
                textStyle = typography.labelLargeEmphasized;
                break;
            case 29:
                textStyle = typography.labelMediumEmphasized;
                break;
            case 30:
                textStyle = typography.labelSmallEmphasized;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return textStyle;
    }
}
