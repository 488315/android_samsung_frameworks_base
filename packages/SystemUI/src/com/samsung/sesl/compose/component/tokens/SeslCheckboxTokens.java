package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.Drawable;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslCheckboxTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslCheckboxTokens darkCheckboxTokens;
    public static final SeslCheckboxTokens lightCheckboxTokens;
    public final Drawable disabledOffDrawable;
    public final Drawable disabledOnDrawable;
    public final Drawable selectedDrawable;
    public final Drawable unselectedDrawable;

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
            int[] iArr = new int[SeslCheckboxDrawableSchemeKeyTokens.values().length];
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.Selected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.Unselected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.DisabledOn.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslCheckboxDrawableSchemeKeyTokens.DisabledOff.ordinal()] = 4;
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
        this.selectedDrawable = drawable;
        this.unselectedDrawable = drawable2;
        this.disabledOnDrawable = drawable3;
        this.disabledOffDrawable = drawable4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslCheckboxTokens)) {
            return false;
        }
        SeslCheckboxTokens seslCheckboxTokens = (SeslCheckboxTokens) obj;
        return Intrinsics.areEqual(this.selectedDrawable, seslCheckboxTokens.selectedDrawable) && Intrinsics.areEqual(this.unselectedDrawable, seslCheckboxTokens.unselectedDrawable) && Intrinsics.areEqual(this.disabledOnDrawable, seslCheckboxTokens.disabledOnDrawable) && Intrinsics.areEqual(this.disabledOffDrawable, seslCheckboxTokens.disabledOffDrawable);
    }

    public final int hashCode() {
        return this.disabledOffDrawable.hashCode() + ((this.disabledOnDrawable.hashCode() + ((this.unselectedDrawable.hashCode() + (this.selectedDrawable.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SeslCheckboxTokens(selectedDrawable=" + this.selectedDrawable + ", unselectedDrawable=" + this.unselectedDrawable + ", disabledOnDrawable=" + this.disabledOnDrawable + ", disabledOffDrawable=" + this.disabledOffDrawable + ")";
    }
}
