package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslSwitchTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslSwitchTokens darkSwitchTokens;
    public static final SeslSwitchTokens lightSwitchTokens;
    public final long thumbOffColor;
    public final long thumbOnColor;
    public final long trackOffColor;
    public final long trackOnColor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SeslSwitchColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslSwitchColorSchemeKeyTokens.TrackOnColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslSwitchColorSchemeKeyTokens.TrackOffColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslSwitchColorSchemeKeyTokens.ThumbOnColor.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslSwitchColorSchemeKeyTokens.ThumbOffColor.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAY_TEXT_D6;
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAYSCALE_L1;
        seslPaletteTokens.getClass();
        lightSwitchTokens = new SeslSwitchTokens(j, j2, j3, j3, null);
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.GRAY_TEXT_L4;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        darkSwitchTokens = new SeslSwitchTokens(j, j4, j3, j3, null);
    }

    public /* synthetic */ SeslSwitchTokens(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslSwitchTokens)) {
            return false;
        }
        SeslSwitchTokens seslSwitchTokens = (SeslSwitchTokens) obj;
        long j = seslSwitchTokens.trackOnColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.trackOnColor, j) && ULong.m3446equalsimpl0(this.trackOffColor, seslSwitchTokens.trackOffColor) && ULong.m3446equalsimpl0(this.thumbOnColor, seslSwitchTokens.thumbOnColor) && ULong.m3446equalsimpl0(this.thumbOffColor, seslSwitchTokens.thumbOffColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.thumbOffColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.trackOnColor) * 31, 31, this.trackOffColor), 31, this.thumbOnColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.trackOnColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.trackOffColor);
        return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslSwitchTokens(trackOnColor=", strM464toStringimpl, ", trackOffColor=", strM464toStringimpl2, ", thumbOnColor="), Color.m464toStringimpl(this.thumbOnColor), ", thumbOffColor=", Color.m464toStringimpl(this.thumbOffColor), ")");
    }

    private SeslSwitchTokens(long j, long j2, long j3, long j4) {
        this.trackOnColor = j;
        this.trackOffColor = j2;
        this.thumbOnColor = j3;
        this.thumbOffColor = j4;
    }
}
