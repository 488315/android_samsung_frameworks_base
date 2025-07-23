package com.samsung.sesl.compose.component.tokens;

import android.content.res.Configuration;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslAppBarTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslAppBarTokens darkAppBarTokens;
    public static final SeslAppBarTokens lightAppBarTokens;
    public final long topAppBarBackgroundColor;
    public final long topAppBarMenuTextColor;
    public final long topAppBarSubTitleTextColor;
    public final long topAppBarTitleTextColor;
    public final SeslDpProducer topAppBarTopPaddingDp;
    public final long topExtendedAppBarSubTitleColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

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
        lightAppBarTokens = new SeslAppBarTokens(j, j2, j3, j2, SeslPaletteTokens.GRAY_TEXT_D5, new SeslDpProducer() { // from class: com.samsung.sesl.compose.component.tokens.SeslAppBarTokens$Companion$lightAppBarTokens$1
            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
            /* renamed from: produce-u2uoSUM */
            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
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
        }, null);
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.GRAYSCALE_D1;
        seslPaletteTokens.getClass();
        long j5 = SeslPaletteTokens.GRAY_TEXT_D1;
        seslPaletteTokens.getClass();
        long j6 = SeslPaletteTokens.GRAYSCALE_D5;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        darkAppBarTokens = new SeslAppBarTokens(j4, j5, j6, j5, j3, new SeslDpProducer() { // from class: com.samsung.sesl.compose.component.tokens.SeslAppBarTokens$Companion$darkAppBarTokens$1
            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
            /* renamed from: produce-u2uoSUM */
            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
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
        }, null);
    }

    public /* synthetic */ SeslAppBarTokens(long j, long j2, long j3, long j4, long j5, SeslDpProducer seslDpProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, seslDpProducer);
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
        return ULong.m3427equalsimpl0(this.topAppBarBackgroundColor, j) && ULong.m3427equalsimpl0(this.topAppBarTitleTextColor, seslAppBarTokens.topAppBarTitleTextColor) && ULong.m3427equalsimpl0(this.topAppBarSubTitleTextColor, seslAppBarTokens.topAppBarSubTitleTextColor) && ULong.m3427equalsimpl0(this.topAppBarMenuTextColor, seslAppBarTokens.topAppBarMenuTextColor) && ULong.m3427equalsimpl0(this.topExtendedAppBarSubTitleColor, seslAppBarTokens.topExtendedAppBarSubTitleColor) && Intrinsics.areEqual(this.topAppBarTopPaddingDp, seslAppBarTokens.topAppBarTopPaddingDp);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.topAppBarTopPaddingDp.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.topAppBarBackgroundColor) * 31, 31, this.topAppBarTitleTextColor), 31, this.topAppBarSubTitleTextColor), 31, this.topAppBarMenuTextColor), 31, this.topExtendedAppBarSubTitleColor);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.topAppBarBackgroundColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.topAppBarTitleTextColor);
        String m462toStringimpl3 = Color.m462toStringimpl(this.topAppBarSubTitleTextColor);
        String m462toStringimpl4 = Color.m462toStringimpl(this.topAppBarMenuTextColor);
        String m462toStringimpl5 = Color.m462toStringimpl(this.topExtendedAppBarSubTitleColor);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslAppBarTokens(topAppBarBackgroundColor=", m462toStringimpl, ", topAppBarTitleTextColor=", m462toStringimpl2, ", topAppBarSubTitleTextColor=");
        MoveResult$$ExternalSyntheticOutline0.m(m, m462toStringimpl3, ", topAppBarMenuTextColor=", m462toStringimpl4, ", topExtendedAppBarSubTitleColor=");
        m.append(m462toStringimpl5);
        m.append(", topAppBarTopPaddingDp=");
        m.append(this.topAppBarTopPaddingDp);
        m.append(")");
        return m.toString();
    }

    private SeslAppBarTokens(long j, long j2, long j3, long j4, long j5, SeslDpProducer seslDpProducer) {
        this.topAppBarBackgroundColor = j;
        this.topAppBarTitleTextColor = j2;
        this.topAppBarSubTitleTextColor = j3;
        this.topAppBarMenuTextColor = j4;
        this.topExtendedAppBarSubTitleColor = j5;
        this.topAppBarTopPaddingDp = seslDpProducer;
    }
}
