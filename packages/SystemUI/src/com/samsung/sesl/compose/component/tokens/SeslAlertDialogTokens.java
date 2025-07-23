package com.samsung.sesl.compose.component.tokens;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslAlertDialogTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslAlertDialogTokens darkAlertDialogTokens;
    public static final SeslAlertDialogTokens lightAlertDialogTokens;
    public final long buttonTextColor;
    public final long messageTextColor;
    public final long titleTextColor;

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
        lightAlertDialogTokens = new SeslAlertDialogTokens(j, j2, j, null);
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAY_TEXT_D1;
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.GRAY_TEXT_D2;
        seslPaletteTokens.getClass();
        darkAlertDialogTokens = new SeslAlertDialogTokens(j3, j4, j3, null);
    }

    public /* synthetic */ SeslAlertDialogTokens(long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3);
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
        return ULong.m3427equalsimpl0(this.titleTextColor, j) && ULong.m3427equalsimpl0(this.messageTextColor, seslAlertDialogTokens.messageTextColor) && ULong.m3427equalsimpl0(this.buttonTextColor, seslAlertDialogTokens.buttonTextColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.buttonTextColor) + MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.titleTextColor) * 31, 31, this.messageTextColor);
    }

    public final String toString() {
        String m462toStringimpl = Color.m462toStringimpl(this.titleTextColor);
        String m462toStringimpl2 = Color.m462toStringimpl(this.messageTextColor);
        return TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslAlertDialogTokens(titleTextColor=", m462toStringimpl, ", messageTextColor=", m462toStringimpl2, ", buttonTextColor="), Color.m462toStringimpl(this.buttonTextColor), ")");
    }

    private SeslAlertDialogTokens(long j, long j2, long j3) {
        this.titleTextColor = j;
        this.messageTextColor = j2;
        this.buttonTextColor = j3;
    }
}
