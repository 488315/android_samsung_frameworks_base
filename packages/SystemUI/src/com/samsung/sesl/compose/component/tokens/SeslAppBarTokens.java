package com.samsung.sesl.compose.component.tokens;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslAppBarTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslAppBarTokens darkAppBarTokens;
    public static final SeslAppBarTokens lightAppBarTokens;
    public final Drawable backIcon;
    public final long topAppBarBackgroundColor;
    public final long topAppBarMenuTextColor;
    public final long topAppBarSubTitleTextColor;
    public final long topAppBarTitleTextColor;
    public final SeslDpProducer topAppBarTopPaddingDp;
    public final long topExtendedAppBarSubTitleColor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[SeslAppBarColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslAppBarColorSchemeKeyTokens.TopAppBarBackgroundColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslAppBarColorSchemeKeyTokens.TopAppBarTitleTextColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslAppBarColorSchemeKeyTokens.TopAppBarSubTitleTextColor.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslAppBarColorSchemeKeyTokens.TopAppBarMenuTextColor.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SeslAppBarColorSchemeKeyTokens.TopExtendedAppBarSubTitleTextColor.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[SeslAppBarDimensionSchemeKeyTokens.values().length];
            try {
                iArr2[SeslAppBarDimensionSchemeKeyTokens.TopAppBarTopPadding.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[SeslAppBarDrawableSchemeKeyTokens.values().length];
            try {
                iArr3[SeslAppBarDrawableSchemeKeyTokens.BackIcon.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAY_TEXT_L3;
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAY_TEXT_L1;
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAY_TEXT_L4;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.GRAY_TEXT_D5;
        SeslAppBarTokens$Companion$lightAppBarTokens$1 seslAppBarTokens$Companion$lightAppBarTokens$1 = new SeslDpProducer() { // from class: com.samsung.sesl.compose.component.tokens.SeslAppBarTokens$Companion$lightAppBarTokens$1
            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
            /* renamed from: produce-u2uoSUM */
            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) {
                Configuration configuration = params.configuration;
                if (configuration.orientation == 1) {
                    float f = 8;
                    Dp.Companion companion = Dp.Companion;
                    return f;
                }
                float f2 = configuration.screenHeightDp;
                Dp.Companion companion2 = Dp.Companion;
                return Float.compare(f2, (float) 579) <= 0 ? 0 : 16;
            }
        };
        SeslDrawableTokens.Companion companion = SeslDrawableTokens.Companion;
        companion.getClass();
        Drawable drawable = SeslDrawableTokens.emptyDrawable;
        lightAppBarTokens = new SeslAppBarTokens(j, j2, j3, j2, j4, seslAppBarTokens$Companion$lightAppBarTokens$1, drawable, null);
        seslPaletteTokens.getClass();
        long j5 = SeslPaletteTokens.GRAYSCALE_D1;
        seslPaletteTokens.getClass();
        long j6 = SeslPaletteTokens.GRAY_TEXT_D1;
        seslPaletteTokens.getClass();
        long j7 = SeslPaletteTokens.GRAYSCALE_D5;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        SeslAppBarTokens$Companion$darkAppBarTokens$1 seslAppBarTokens$Companion$darkAppBarTokens$1 = new SeslDpProducer() { // from class: com.samsung.sesl.compose.component.tokens.SeslAppBarTokens$Companion$darkAppBarTokens$1
            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
            /* renamed from: produce-u2uoSUM */
            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) {
                Configuration configuration = params.configuration;
                if (configuration.orientation == 1) {
                    float f = 8;
                    Dp.Companion companion2 = Dp.Companion;
                    return f;
                }
                float f2 = configuration.screenHeightDp;
                Dp.Companion companion3 = Dp.Companion;
                return Float.compare(f2, (float) 579) <= 0 ? 0 : 16;
            }
        };
        companion.getClass();
        darkAppBarTokens = new SeslAppBarTokens(j5, j6, j7, j6, j3, seslAppBarTokens$Companion$darkAppBarTokens$1, drawable, null);
    }

    public /* synthetic */ SeslAppBarTokens(long j, long j2, long j3, long j4, long j5, SeslDpProducer seslDpProducer, Drawable drawable, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, seslDpProducer, drawable);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslAppBarTokens)) {
            return false;
        }
        SeslAppBarTokens seslAppBarTokens = (SeslAppBarTokens) obj;
        long j = seslAppBarTokens.topAppBarBackgroundColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.topAppBarBackgroundColor, j) && ULong.m3447equalsimpl0(this.topAppBarTitleTextColor, seslAppBarTokens.topAppBarTitleTextColor) && ULong.m3447equalsimpl0(this.topAppBarSubTitleTextColor, seslAppBarTokens.topAppBarSubTitleTextColor) && ULong.m3447equalsimpl0(this.topAppBarMenuTextColor, seslAppBarTokens.topAppBarMenuTextColor) && ULong.m3447equalsimpl0(this.topExtendedAppBarSubTitleColor, seslAppBarTokens.topExtendedAppBarSubTitleColor) && Intrinsics.areEqual(this.topAppBarTopPaddingDp, seslAppBarTokens.topAppBarTopPaddingDp) && Intrinsics.areEqual(this.backIcon, seslAppBarTokens.backIcon);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.backIcon.hashCode() + ((this.topAppBarTopPaddingDp.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.topAppBarBackgroundColor) * 31, 31, this.topAppBarTitleTextColor), 31, this.topAppBarSubTitleTextColor), 31, this.topAppBarMenuTextColor), 31, this.topExtendedAppBarSubTitleColor)) * 31);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.topAppBarBackgroundColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.topAppBarTitleTextColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.topAppBarSubTitleTextColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.topAppBarMenuTextColor);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.topExtendedAppBarSubTitleColor);
        Drawable drawable = this.backIcon;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslAppBarTokens(topAppBarBackgroundColor=", strM464toStringimpl, ", topAppBarTitleTextColor=", strM464toStringimpl2, ", topAppBarSubTitleTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", topAppBarMenuTextColor=", strM464toStringimpl4, ", topExtendedAppBarSubTitleColor=");
        sbM.append(strM464toStringimpl5);
        sbM.append(", topAppBarTopPaddingDp=");
        sbM.append(this.topAppBarTopPaddingDp);
        sbM.append(", backIcon=");
        sbM.append(drawable);
        sbM.append(")");
        return sbM.toString();
    }

    private SeslAppBarTokens(long j, long j2, long j3, long j4, long j5, SeslDpProducer seslDpProducer, Drawable drawable) {
        this.topAppBarBackgroundColor = j;
        this.topAppBarTitleTextColor = j2;
        this.topAppBarSubTitleTextColor = j3;
        this.topAppBarMenuTextColor = j4;
        this.topExtendedAppBarSubTitleColor = j5;
        this.topAppBarTopPaddingDp = seslDpProducer;
        this.backIcon = drawable;
    }
}
