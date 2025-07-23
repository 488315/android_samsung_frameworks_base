package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslDialogTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslDialogTokens darkDialogTokens;
    public static final SeslDialogTokens lightDialogTokens;
    public final Drawable background;

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
            int[] iArr = new int[SeslDialogDrawableSchemeKeyTokens.values().length];
            try {
                iArr[SeslDialogDrawableSchemeKeyTokens.Background.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        lightDialogTokens = new SeslDialogTokens(new ColorDrawable(ColorKt.m467toArgb8_81llA(SeslPaletteTokens.GRAYSCALE_L1)));
        seslPaletteTokens.getClass();
        darkDialogTokens = new SeslDialogTokens(new ColorDrawable(ColorKt.m467toArgb8_81llA(SeslPaletteTokens.GRAYSCALE_D5)));
    }

    public SeslDialogTokens(Drawable drawable) {
        this.background = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SeslDialogTokens) && Intrinsics.areEqual(this.background, ((SeslDialogTokens) obj).background);
    }

    public final int hashCode() {
        return this.background.hashCode();
    }

    public final String toString() {
        return "SeslDialogTokens(background=" + this.background + ")";
    }
}
