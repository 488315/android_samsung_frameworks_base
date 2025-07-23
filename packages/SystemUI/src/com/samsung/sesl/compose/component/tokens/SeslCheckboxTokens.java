package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.Drawable;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslCheckboxTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslCheckboxTokens darkCheckboxTokens;
    public static final SeslCheckboxTokens lightCheckboxTokens;
    public final Drawable checkboxDisabledOff;
    public final Drawable checkboxDisabledOn;
    public final Drawable checkboxSelected;
    public final Drawable checkboxUnselected;

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
            int[] iArr = new int[SeslCheckboxDrawableSchemeKeyTokens.values().length];
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.CheckboxSelected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.CheckboxUnselected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.CheckboxDisabledOn.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.CheckboxDisabledOff.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslDrawableTokens.Companion companion = SeslDrawableTokens.Companion;
        companion.getClass();
        Drawable drawable = SeslDrawableTokens.emptyDrawable;
        companion.getClass();
        companion.getClass();
        companion.getClass();
        lightCheckboxTokens = new SeslCheckboxTokens(drawable, drawable, drawable, drawable);
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        darkCheckboxTokens = new SeslCheckboxTokens(drawable, drawable, drawable, drawable);
    }

    public SeslCheckboxTokens(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        this.checkboxSelected = drawable;
        this.checkboxUnselected = drawable2;
        this.checkboxDisabledOn = drawable3;
        this.checkboxDisabledOff = drawable4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslCheckboxTokens)) {
            return false;
        }
        SeslCheckboxTokens seslCheckboxTokens = (SeslCheckboxTokens) obj;
        return Intrinsics.areEqual(this.checkboxSelected, seslCheckboxTokens.checkboxSelected) && Intrinsics.areEqual(this.checkboxUnselected, seslCheckboxTokens.checkboxUnselected) && Intrinsics.areEqual(this.checkboxDisabledOn, seslCheckboxTokens.checkboxDisabledOn) && Intrinsics.areEqual(this.checkboxDisabledOff, seslCheckboxTokens.checkboxDisabledOff);
    }

    public final int hashCode() {
        return this.checkboxDisabledOff.hashCode() + ((this.checkboxDisabledOn.hashCode() + ((this.checkboxUnselected.hashCode() + (this.checkboxSelected.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SeslCheckboxTokens(checkboxSelected=" + this.checkboxSelected + ", checkboxUnselected=" + this.checkboxUnselected + ", checkboxDisabledOn=" + this.checkboxDisabledOn + ", checkboxDisabledOff=" + this.checkboxDisabledOff + ")";
    }
}
