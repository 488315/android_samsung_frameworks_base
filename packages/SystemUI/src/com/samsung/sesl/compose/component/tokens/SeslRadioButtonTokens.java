package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.Drawable;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslRadioButtonTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslRadioButtonTokens darkRadioButtonTokens;
    public static final SeslRadioButtonTokens lightRadioButtonTokens;
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
            int[] iArr = new int[SeslRadioButtonDrawableSchemeKeyTokens.values().length];
            try {
                iArr[SeslRadioButtonDrawableSchemeKeyTokens.Selected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslRadioButtonDrawableSchemeKeyTokens.Unselected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SeslRadioButtonDrawableSchemeKeyTokens.DisabledOn.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SeslRadioButtonDrawableSchemeKeyTokens.DisabledOff.ordinal()] = 4;
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
        lightRadioButtonTokens = new SeslRadioButtonTokens(drawable, drawable, drawable, drawable);
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        darkRadioButtonTokens = new SeslRadioButtonTokens(drawable, drawable, drawable, drawable);
    }

    public SeslRadioButtonTokens(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        this.selectedDrawable = drawable;
        this.unselectedDrawable = drawable2;
        this.disabledOnDrawable = drawable3;
        this.disabledOffDrawable = drawable4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslRadioButtonTokens)) {
            return false;
        }
        SeslRadioButtonTokens seslRadioButtonTokens = (SeslRadioButtonTokens) obj;
        return Intrinsics.areEqual(this.selectedDrawable, seslRadioButtonTokens.selectedDrawable) && Intrinsics.areEqual(this.unselectedDrawable, seslRadioButtonTokens.unselectedDrawable) && Intrinsics.areEqual(this.disabledOnDrawable, seslRadioButtonTokens.disabledOnDrawable) && Intrinsics.areEqual(this.disabledOffDrawable, seslRadioButtonTokens.disabledOffDrawable);
    }

    public final int hashCode() {
        return this.disabledOffDrawable.hashCode() + ((this.disabledOnDrawable.hashCode() + ((this.unselectedDrawable.hashCode() + (this.selectedDrawable.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SeslRadioButtonTokens(selectedDrawable=" + this.selectedDrawable + ", unselectedDrawable=" + this.unselectedDrawable + ", disabledOnDrawable=" + this.disabledOnDrawable + ", disabledOffDrawable=" + this.disabledOffDrawable + ")";
    }
}
