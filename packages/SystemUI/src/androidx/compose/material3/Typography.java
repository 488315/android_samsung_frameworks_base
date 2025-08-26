package androidx.compose.material3;

import androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0;
import androidx.compose.material3.tokens.TypographyTokens;
import androidx.compose.ui.text.TextStyle;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Typography {
    public final TextStyle bodyLarge;
    public final TextStyle bodyLargeEmphasized;
    public final TextStyle bodyMedium;
    public final TextStyle bodyMediumEmphasized;
    public final TextStyle bodySmall;
    public final TextStyle bodySmallEmphasized;
    public final TextStyle displayLarge;
    public final TextStyle displayLargeEmphasized;
    public final TextStyle displayMedium;
    public final TextStyle displayMediumEmphasized;
    public final TextStyle displaySmall;
    public final TextStyle displaySmallEmphasized;
    public final TextStyle headlineLarge;
    public final TextStyle headlineLargeEmphasized;
    public final TextStyle headlineMedium;
    public final TextStyle headlineMediumEmphasized;
    public final TextStyle headlineSmall;
    public final TextStyle headlineSmallEmphasized;
    public final TextStyle labelLarge;
    public final TextStyle labelLargeEmphasized;
    public final TextStyle labelMedium;
    public final TextStyle labelMediumEmphasized;
    public final TextStyle labelSmall;
    public final TextStyle labelSmallEmphasized;
    public final TextStyle titleLarge;
    public final TextStyle titleLargeEmphasized;
    public final TextStyle titleMedium;
    public final TextStyle titleMediumEmphasized;
    public final TextStyle titleSmall;
    public final TextStyle titleSmallEmphasized;

    public Typography() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 1073741823, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Typography)) {
            return false;
        }
        Typography typography = (Typography) obj;
        return Intrinsics.areEqual(this.displayLarge, typography.displayLarge) && Intrinsics.areEqual(this.displayMedium, typography.displayMedium) && Intrinsics.areEqual(this.displaySmall, typography.displaySmall) && Intrinsics.areEqual(this.headlineLarge, typography.headlineLarge) && Intrinsics.areEqual(this.headlineMedium, typography.headlineMedium) && Intrinsics.areEqual(this.headlineSmall, typography.headlineSmall) && Intrinsics.areEqual(this.titleLarge, typography.titleLarge) && Intrinsics.areEqual(this.titleMedium, typography.titleMedium) && Intrinsics.areEqual(this.titleSmall, typography.titleSmall) && Intrinsics.areEqual(this.bodyLarge, typography.bodyLarge) && Intrinsics.areEqual(this.bodyMedium, typography.bodyMedium) && Intrinsics.areEqual(this.bodySmall, typography.bodySmall) && Intrinsics.areEqual(this.labelLarge, typography.labelLarge) && Intrinsics.areEqual(this.labelMedium, typography.labelMedium) && Intrinsics.areEqual(this.labelSmall, typography.labelSmall) && Intrinsics.areEqual(this.displayLargeEmphasized, typography.displayLargeEmphasized) && Intrinsics.areEqual(this.displayMediumEmphasized, typography.displayMediumEmphasized) && Intrinsics.areEqual(this.displaySmallEmphasized, typography.displaySmallEmphasized) && Intrinsics.areEqual(this.headlineLargeEmphasized, typography.headlineLargeEmphasized) && Intrinsics.areEqual(this.headlineMediumEmphasized, typography.headlineMediumEmphasized) && Intrinsics.areEqual(this.headlineSmallEmphasized, typography.headlineSmallEmphasized) && Intrinsics.areEqual(this.titleLargeEmphasized, typography.titleLargeEmphasized) && Intrinsics.areEqual(this.titleMediumEmphasized, typography.titleMediumEmphasized) && Intrinsics.areEqual(this.titleSmallEmphasized, typography.titleSmallEmphasized) && Intrinsics.areEqual(this.bodyLargeEmphasized, typography.bodyLargeEmphasized) && Intrinsics.areEqual(this.bodyMediumEmphasized, typography.bodyMediumEmphasized) && Intrinsics.areEqual(this.bodySmallEmphasized, typography.bodySmallEmphasized) && Intrinsics.areEqual(this.labelLargeEmphasized, typography.labelLargeEmphasized) && Intrinsics.areEqual(this.labelMediumEmphasized, typography.labelMediumEmphasized) && Intrinsics.areEqual(this.labelSmallEmphasized, typography.labelSmallEmphasized);
    }

    public final int hashCode() {
        return this.labelSmallEmphasized.hashCode() + SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(SelectableTextAnnotatedStringElement$$ExternalSyntheticOutline0.m(this.displayLarge.hashCode() * 31, 31, this.displayMedium), 31, this.displaySmall), 31, this.headlineLarge), 31, this.headlineMedium), 31, this.headlineSmall), 31, this.titleLarge), 31, this.titleMedium), 31, this.titleSmall), 31, this.bodyLarge), 31, this.bodyMedium), 31, this.bodySmall), 31, this.labelLarge), 31, this.labelMedium), 31, this.labelSmall), 31, this.displayLargeEmphasized), 31, this.displayMediumEmphasized), 31, this.displaySmallEmphasized), 31, this.headlineLargeEmphasized), 31, this.headlineMediumEmphasized), 31, this.headlineSmallEmphasized), 31, this.titleLargeEmphasized), 31, this.titleMediumEmphasized), 31, this.titleSmallEmphasized), 31, this.bodyLargeEmphasized), 31, this.bodyMediumEmphasized), 31, this.bodySmallEmphasized), 31, this.labelLargeEmphasized), 31, this.labelMediumEmphasized);
    }

    public final String toString() {
        return "Typography(displayLarge=" + this.displayLarge + ", displayMedium=" + this.displayMedium + ",displaySmall=" + this.displaySmall + ", headlineLarge=" + this.headlineLarge + ", headlineMedium=" + this.headlineMedium + ", headlineSmall=" + this.headlineSmall + ", titleLarge=" + this.titleLarge + ", titleMedium=" + this.titleMedium + ", titleSmall=" + this.titleSmall + ", bodyLarge=" + this.bodyLarge + ", bodyMedium=" + this.bodyMedium + ", bodySmall=" + this.bodySmall + ", labelLarge=" + this.labelLarge + ", labelMedium=" + this.labelMedium + ", labelSmall=" + this.labelSmall + ", displayLargeEmphasized=" + this.displayLargeEmphasized + ", displayMediumEmphasized=" + this.displayMediumEmphasized + ", displaySmallEmphasized=" + this.displaySmallEmphasized + ", headlineLargeEmphasized=" + this.headlineLargeEmphasized + ", headlineMediumEmphasized=" + this.headlineMediumEmphasized + ", headlineSmallEmphasized=" + this.headlineSmallEmphasized + ", titleLargeEmphasized=" + this.titleLargeEmphasized + ", titleMediumEmphasized=" + this.titleMediumEmphasized + ", titleSmallEmphasized=" + this.titleSmallEmphasized + ", bodyLargeEmphasized=" + this.bodyLargeEmphasized + ", bodyMediumEmphasized=" + this.bodyMediumEmphasized + ", bodySmallEmphasized=" + this.bodySmallEmphasized + ", labelLargeEmphasized=" + this.labelLargeEmphasized + ", labelMediumEmphasized=" + this.labelMediumEmphasized + ", labelSmallEmphasized=" + this.labelSmallEmphasized + ')';
    }

    public Typography(TextStyle textStyle, TextStyle textStyle2, TextStyle textStyle3, TextStyle textStyle4, TextStyle textStyle5, TextStyle textStyle6, TextStyle textStyle7, TextStyle textStyle8, TextStyle textStyle9, TextStyle textStyle10, TextStyle textStyle11, TextStyle textStyle12, TextStyle textStyle13, TextStyle textStyle14, TextStyle textStyle15, TextStyle textStyle16, TextStyle textStyle17, TextStyle textStyle18, TextStyle textStyle19, TextStyle textStyle20, TextStyle textStyle21, TextStyle textStyle22, TextStyle textStyle23, TextStyle textStyle24, TextStyle textStyle25, TextStyle textStyle26, TextStyle textStyle27, TextStyle textStyle28, TextStyle textStyle29, TextStyle textStyle30) {
        this.displayLarge = textStyle;
        this.displayMedium = textStyle2;
        this.displaySmall = textStyle3;
        this.headlineLarge = textStyle4;
        this.headlineMedium = textStyle5;
        this.headlineSmall = textStyle6;
        this.titleLarge = textStyle7;
        this.titleMedium = textStyle8;
        this.titleSmall = textStyle9;
        this.bodyLarge = textStyle10;
        this.bodyMedium = textStyle11;
        this.bodySmall = textStyle12;
        this.labelLarge = textStyle13;
        this.labelMedium = textStyle14;
        this.labelSmall = textStyle15;
        this.displayLargeEmphasized = textStyle16;
        this.displayMediumEmphasized = textStyle17;
        this.displaySmallEmphasized = textStyle18;
        this.headlineLargeEmphasized = textStyle19;
        this.headlineMediumEmphasized = textStyle20;
        this.headlineSmallEmphasized = textStyle21;
        this.titleLargeEmphasized = textStyle22;
        this.titleMediumEmphasized = textStyle23;
        this.titleSmallEmphasized = textStyle24;
        this.bodyLargeEmphasized = textStyle25;
        this.bodyMediumEmphasized = textStyle26;
        this.bodySmallEmphasized = textStyle27;
        this.labelLargeEmphasized = textStyle28;
        this.labelMediumEmphasized = textStyle29;
        this.labelSmallEmphasized = textStyle30;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Typography(TextStyle textStyle, TextStyle textStyle2, TextStyle textStyle3, TextStyle textStyle4, TextStyle textStyle5, TextStyle textStyle6, TextStyle textStyle7, TextStyle textStyle8, TextStyle textStyle9, TextStyle textStyle10, TextStyle textStyle11, TextStyle textStyle12, TextStyle textStyle13, TextStyle textStyle14, TextStyle textStyle15, TextStyle textStyle16, TextStyle textStyle17, TextStyle textStyle18, TextStyle textStyle19, TextStyle textStyle20, TextStyle textStyle21, TextStyle textStyle22, TextStyle textStyle23, TextStyle textStyle24, TextStyle textStyle25, TextStyle textStyle26, TextStyle textStyle27, TextStyle textStyle28, TextStyle textStyle29, TextStyle textStyle30, int i, DefaultConstructorMarker defaultConstructorMarker) {
        TextStyle textStyle31;
        TextStyle textStyle32;
        TextStyle textStyle33;
        TextStyle textStyle34;
        TextStyle textStyle35;
        TextStyle textStyle36;
        TextStyle textStyle37;
        TextStyle textStyle38;
        TextStyle textStyle39;
        TextStyle textStyle40;
        TextStyle textStyle41;
        TextStyle textStyle42;
        TextStyle textStyle43;
        TextStyle textStyle44;
        TextStyle textStyle45;
        TextStyle textStyle46;
        TextStyle textStyle47;
        TextStyle textStyle48;
        TextStyle textStyle49;
        TextStyle textStyle50;
        TextStyle textStyle51;
        TextStyle textStyle52;
        TextStyle textStyle53;
        TextStyle textStyle54;
        TextStyle textStyle55;
        TextStyle textStyle56;
        TextStyle textStyle57;
        TextStyle textStyle58;
        TextStyle textStyle59;
        TextStyle textStyle60;
        if ((i & 1) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle31 = TypographyTokens.DisplayLarge;
        } else {
            textStyle31 = textStyle;
        }
        if ((i & 2) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle32 = TypographyTokens.DisplayMedium;
        } else {
            textStyle32 = textStyle2;
        }
        if ((i & 4) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle33 = TypographyTokens.DisplaySmall;
        } else {
            textStyle33 = textStyle3;
        }
        if ((i & 8) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle34 = TypographyTokens.HeadlineLarge;
        } else {
            textStyle34 = textStyle4;
        }
        if ((i & 16) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle35 = TypographyTokens.HeadlineMedium;
        } else {
            textStyle35 = textStyle5;
        }
        if ((i & 32) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle36 = TypographyTokens.HeadlineSmall;
        } else {
            textStyle36 = textStyle6;
        }
        if ((i & 64) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle37 = TypographyTokens.TitleLarge;
        } else {
            textStyle37 = textStyle7;
        }
        if ((i & 128) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle38 = TypographyTokens.TitleMedium;
        } else {
            textStyle38 = textStyle8;
        }
        if ((i & 256) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle39 = TypographyTokens.TitleSmall;
        } else {
            textStyle39 = textStyle9;
        }
        if ((i & 512) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle40 = TypographyTokens.BodyLarge;
        } else {
            textStyle40 = textStyle10;
        }
        if ((i & 1024) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle41 = TypographyTokens.BodyMedium;
        } else {
            textStyle41 = textStyle11;
        }
        if ((i & 2048) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle42 = TypographyTokens.BodySmall;
        } else {
            textStyle42 = textStyle12;
        }
        if ((i & 4096) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle43 = TypographyTokens.LabelLarge;
        } else {
            textStyle43 = textStyle13;
        }
        if ((i & 8192) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle44 = TypographyTokens.LabelMedium;
        } else {
            textStyle44 = textStyle14;
        }
        if ((i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle45 = TypographyTokens.LabelSmall;
        } else {
            textStyle45 = textStyle15;
        }
        if ((i & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle46 = TypographyTokens.DisplayLargeEmphasized;
        } else {
            textStyle46 = textStyle16;
        }
        if ((i & 65536) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle47 = TypographyTokens.DisplayMediumEmphasized;
        } else {
            textStyle47 = textStyle17;
        }
        if ((i & 131072) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle48 = TypographyTokens.DisplaySmallEmphasized;
        } else {
            textStyle48 = textStyle18;
        }
        if ((i & 262144) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle49 = TypographyTokens.HeadlineLargeEmphasized;
        } else {
            textStyle49 = textStyle19;
        }
        if ((i & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle50 = TypographyTokens.HeadlineMediumEmphasized;
        } else {
            textStyle50 = textStyle20;
        }
        if ((i & 1048576) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle51 = TypographyTokens.HeadlineSmallEmphasized;
        } else {
            textStyle51 = textStyle21;
        }
        if ((i & 2097152) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle52 = TypographyTokens.TitleLargeEmphasized;
        } else {
            textStyle52 = textStyle22;
        }
        if ((i & 4194304) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle53 = TypographyTokens.TitleMediumEmphasized;
        } else {
            textStyle53 = textStyle23;
        }
        if ((i & 8388608) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle54 = TypographyTokens.TitleSmallEmphasized;
        } else {
            textStyle54 = textStyle24;
        }
        if ((i & 16777216) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle55 = TypographyTokens.BodyLargeEmphasized;
        } else {
            textStyle55 = textStyle25;
        }
        if ((i & 33554432) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle56 = TypographyTokens.BodyMediumEmphasized;
        } else {
            textStyle56 = textStyle26;
        }
        if ((i & 67108864) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle57 = TypographyTokens.BodySmallEmphasized;
        } else {
            textStyle57 = textStyle27;
        }
        if ((i & 134217728) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle58 = TypographyTokens.LabelLargeEmphasized;
        } else {
            textStyle58 = textStyle28;
        }
        if ((i & 268435456) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle59 = TypographyTokens.LabelMediumEmphasized;
        } else {
            textStyle59 = textStyle29;
        }
        if ((i & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle60 = TypographyTokens.LabelSmallEmphasized;
        } else {
            textStyle60 = textStyle30;
        }
        this(textStyle31, textStyle32, textStyle33, textStyle34, textStyle35, textStyle36, textStyle37, textStyle38, textStyle39, textStyle40, textStyle41, textStyle42, textStyle43, textStyle44, textStyle45, textStyle46, textStyle47, textStyle48, textStyle49, textStyle50, textStyle51, textStyle52, textStyle53, textStyle54, textStyle55, textStyle56, textStyle57, textStyle58, textStyle59, textStyle60);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Typography(TextStyle textStyle, TextStyle textStyle2, TextStyle textStyle3, TextStyle textStyle4, TextStyle textStyle5, TextStyle textStyle6, TextStyle textStyle7, TextStyle textStyle8, TextStyle textStyle9, TextStyle textStyle10, TextStyle textStyle11, TextStyle textStyle12, TextStyle textStyle13, TextStyle textStyle14, TextStyle textStyle15, int i, DefaultConstructorMarker defaultConstructorMarker) {
        TextStyle textStyle16;
        TextStyle textStyle17;
        TextStyle textStyle18;
        TextStyle textStyle19;
        TextStyle textStyle20;
        TextStyle textStyle21;
        TextStyle textStyle22;
        TextStyle textStyle23;
        TextStyle textStyle24;
        TextStyle textStyle25;
        TextStyle textStyle26;
        TextStyle textStyle27;
        TextStyle textStyle28;
        TextStyle textStyle29;
        TextStyle textStyle30;
        if ((i & 1) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle16 = TypographyTokens.DisplayLarge;
        } else {
            textStyle16 = textStyle;
        }
        if ((i & 2) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle17 = TypographyTokens.DisplayMedium;
        } else {
            textStyle17 = textStyle2;
        }
        if ((i & 4) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle18 = TypographyTokens.DisplaySmall;
        } else {
            textStyle18 = textStyle3;
        }
        if ((i & 8) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle19 = TypographyTokens.HeadlineLarge;
        } else {
            textStyle19 = textStyle4;
        }
        if ((i & 16) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle20 = TypographyTokens.HeadlineMedium;
        } else {
            textStyle20 = textStyle5;
        }
        if ((i & 32) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle21 = TypographyTokens.HeadlineSmall;
        } else {
            textStyle21 = textStyle6;
        }
        if ((i & 64) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle22 = TypographyTokens.TitleLarge;
        } else {
            textStyle22 = textStyle7;
        }
        if ((i & 128) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle23 = TypographyTokens.TitleMedium;
        } else {
            textStyle23 = textStyle8;
        }
        if ((i & 256) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle24 = TypographyTokens.TitleSmall;
        } else {
            textStyle24 = textStyle9;
        }
        if ((i & 512) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle25 = TypographyTokens.BodyLarge;
        } else {
            textStyle25 = textStyle10;
        }
        if ((i & 1024) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle26 = TypographyTokens.BodyMedium;
        } else {
            textStyle26 = textStyle11;
        }
        if ((i & 2048) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle27 = TypographyTokens.BodySmall;
        } else {
            textStyle27 = textStyle12;
        }
        if ((i & 4096) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle28 = TypographyTokens.LabelLarge;
        } else {
            textStyle28 = textStyle13;
        }
        if ((i & 8192) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle29 = TypographyTokens.LabelMedium;
        } else {
            textStyle29 = textStyle14;
        }
        if ((i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
            TypographyTokens.INSTANCE.getClass();
            textStyle30 = TypographyTokens.LabelSmall;
        } else {
            textStyle30 = textStyle15;
        }
        this(textStyle16, textStyle17, textStyle18, textStyle19, textStyle20, textStyle21, textStyle22, textStyle23, textStyle24, textStyle25, textStyle26, textStyle27, textStyle28, textStyle29, textStyle30);
    }

    public Typography(TextStyle textStyle, TextStyle textStyle2, TextStyle textStyle3, TextStyle textStyle4, TextStyle textStyle5, TextStyle textStyle6, TextStyle textStyle7, TextStyle textStyle8, TextStyle textStyle9, TextStyle textStyle10, TextStyle textStyle11, TextStyle textStyle12, TextStyle textStyle13, TextStyle textStyle14, TextStyle textStyle15) {
        this(textStyle, textStyle2, textStyle3, textStyle4, textStyle5, textStyle6, textStyle7, textStyle8, textStyle9, textStyle10, textStyle11, textStyle12, textStyle13, textStyle14, textStyle15, textStyle, textStyle2, textStyle3, textStyle4, textStyle5, textStyle6, textStyle7, textStyle8, textStyle9, textStyle10, textStyle11, textStyle12, textStyle13, textStyle14, textStyle15);
    }
}
