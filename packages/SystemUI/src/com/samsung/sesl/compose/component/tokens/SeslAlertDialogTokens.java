package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslAlertDialogTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslAlertDialogTokens darkAlertDialogTokens;
    public static final SeslAlertDialogTokens lightAlertDialogTokens;
    public final long buttonTextColor;
    public final long listTextColor;
    public final long messageTextColor;
    public final long titleTextColor;

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
            int[] iArr = new int[SeslAlertDialogColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslAlertDialogColorSchemeKeyTokens.TitleTextColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslAlertDialogColorSchemeKeyTokens.MessageTextColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslAlertDialogColorSchemeKeyTokens.ButtonTextColor.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslAlertDialogColorSchemeKeyTokens.ListTextColor.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAY_TEXT_L1;
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAY_TEXT_L2;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        lightAlertDialogTokens = new SeslAlertDialogTokens(j, j2, j, j, null);
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAY_TEXT_D1;
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.GRAY_TEXT_D2;
        seslPaletteTokens.getClass();
        seslPaletteTokens.getClass();
        darkAlertDialogTokens = new SeslAlertDialogTokens(j3, j4, j3, j3, null);
    }

    public /* synthetic */ SeslAlertDialogTokens(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslAlertDialogTokens)) {
            return false;
        }
        SeslAlertDialogTokens seslAlertDialogTokens = (SeslAlertDialogTokens) obj;
        long j = seslAlertDialogTokens.titleTextColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.titleTextColor, j) && ULong.m3446equalsimpl0(this.messageTextColor, seslAlertDialogTokens.messageTextColor) && ULong.m3446equalsimpl0(this.buttonTextColor, seslAlertDialogTokens.buttonTextColor) && ULong.m3446equalsimpl0(this.listTextColor, seslAlertDialogTokens.listTextColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.listTextColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.titleTextColor) * 31, 31, this.messageTextColor), 31, this.buttonTextColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.titleTextColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.messageTextColor);
        return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslAlertDialogTokens(titleTextColor=", strM464toStringimpl, ", messageTextColor=", strM464toStringimpl2, ", buttonTextColor="), Color.m464toStringimpl(this.buttonTextColor), ", listTextColor=", Color.m464toStringimpl(this.listTextColor), ")");
    }

    private SeslAlertDialogTokens(long j, long j2, long j3, long j4) {
        this.titleTextColor = j;
        this.messageTextColor = j2;
        this.buttonTextColor = j3;
        this.listTextColor = j4;
    }
}
