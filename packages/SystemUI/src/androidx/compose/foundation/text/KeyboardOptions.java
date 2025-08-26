package androidx.compose.foundation.text;

import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PlatformImeOptions;
import androidx.compose.ui.text.intl.LocaleList;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class KeyboardOptions {
    public static final Companion Companion = new Companion(null);
    public static final KeyboardOptions Default = new KeyboardOptions(0, (Boolean) null, 0, 0, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 127, (DefaultConstructorMarker) null);
    public final Boolean autoCorrectEnabled;
    public final int capitalization;
    public final LocaleList hintLocales;
    public final int imeAction;
    public final int keyboardType;
    public final PlatformImeOptions platformImeOptions;
    public final Boolean showKeyboardOnFocus;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Boolean bool = Boolean.FALSE;
        KeyboardType.Companion.getClass();
        new KeyboardOptions(0, bool, KeyboardType.Password, 0, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 121, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ KeyboardOptions(int i, Boolean bool, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool2, LocaleList localeList, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, bool, i2, i3, platformImeOptions, bool2, localeList);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyboardOptions)) {
            return false;
        }
        KeyboardOptions keyboardOptions = (KeyboardOptions) obj;
        int i = keyboardOptions.capitalization;
        KeyboardCapitalization.Companion companion = KeyboardCapitalization.Companion;
        if (this.capitalization != i || !Intrinsics.areEqual(this.autoCorrectEnabled, keyboardOptions.autoCorrectEnabled)) {
            return false;
        }
        int i2 = keyboardOptions.keyboardType;
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        if (this.keyboardType == i2) {
            int i3 = keyboardOptions.imeAction;
            ImeAction.Companion companion3 = ImeAction.Companion;
            return this.imeAction == i3 && Intrinsics.areEqual(this.platformImeOptions, keyboardOptions.platformImeOptions) && Intrinsics.areEqual(this.showKeyboardOnFocus, keyboardOptions.showKeyboardOnFocus) && Intrinsics.areEqual(this.hintLocales, keyboardOptions.hintLocales);
        }
        return false;
    }

    public final int hashCode() {
        KeyboardCapitalization.Companion companion = KeyboardCapitalization.Companion;
        int iHashCode = Integer.hashCode(this.capitalization) * 31;
        Boolean bool = this.autoCorrectEnabled;
        int iHashCode2 = (iHashCode + (bool != null ? bool.hashCode() : 0)) * 31;
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.keyboardType, iHashCode2, 31);
        ImeAction.Companion companion3 = ImeAction.Companion;
        int iM2 = ReorderTile$$ExternalSyntheticOutline0.m(this.imeAction, iM, 31);
        PlatformImeOptions platformImeOptions = this.platformImeOptions;
        int iHashCode3 = (iM2 + (platformImeOptions != null ? platformImeOptions.hashCode() : 0)) * 31;
        Boolean bool2 = this.showKeyboardOnFocus;
        int iHashCode4 = (iHashCode3 + (bool2 != null ? bool2.hashCode() : 0)) * 31;
        LocaleList localeList = this.hintLocales;
        return iHashCode4 + (localeList != null ? localeList.localeList.hashCode() : 0);
    }

    public final String toString() {
        return "KeyboardOptions(capitalization=" + ((Object) KeyboardCapitalization.m777toStringimpl(this.capitalization)) + ", autoCorrectEnabled=" + this.autoCorrectEnabled + ", keyboardType=" + ((Object) KeyboardType.m779toStringimpl(this.keyboardType)) + ", imeAction=" + ((Object) ImeAction.m775toStringimpl(this.imeAction)) + ", platformImeOptions=" + this.platformImeOptions + "showKeyboardOnFocus=" + this.showKeyboardOnFocus + ", hintLocales=" + this.hintLocales + ')';
    }

    public /* synthetic */ KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool, LocaleList localeList, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, i2, i3, platformImeOptions, bool, localeList);
    }

    public /* synthetic */ KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, i2, i3, platformImeOptions);
    }

    public /* synthetic */ KeyboardOptions(int i, boolean z, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, i2, i3);
    }

    private KeyboardOptions(int i, Boolean bool, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool2, LocaleList localeList) {
        this.capitalization = i;
        this.autoCorrectEnabled = bool;
        this.keyboardType = i2;
        this.imeAction = i3;
        this.platformImeOptions = platformImeOptions;
        this.showKeyboardOnFocus = bool2;
        this.hintLocales = localeList;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KeyboardOptions(int i, Boolean bool, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool2, LocaleList localeList, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        int i6;
        int i7;
        if ((i4 & 1) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i5 = KeyboardCapitalization.Unspecified;
        } else {
            i5 = i;
        }
        Boolean bool3 = (i4 & 2) != 0 ? null : bool;
        if ((i4 & 4) != 0) {
            KeyboardType.Companion.getClass();
            i6 = 0;
        } else {
            i6 = i2;
        }
        if ((i4 & 8) != 0) {
            ImeAction.Companion.getClass();
            i7 = ImeAction.Unspecified;
        } else {
            i7 = i3;
        }
        PlatformImeOptions platformImeOptions2 = (i4 & 16) != 0 ? null : platformImeOptions;
        Boolean bool4 = (i4 & 32) != 0 ? null : bool2;
        this(i5, bool3, i6, i7, platformImeOptions2, bool4, (i4 & 64) == 0 ? localeList : null, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool, LocaleList localeList, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i4 & 1) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i = KeyboardCapitalization.Unspecified;
        }
        int i5 = i;
        if ((i4 & 4) != 0) {
            KeyboardType.Companion.getClass();
            i2 = 0;
        }
        int i6 = i2;
        if ((i4 & 8) != 0) {
            ImeAction.Companion.getClass();
            i3 = ImeAction.Unspecified;
        }
        this(i5, z, i6, i3, (i4 & 16) != 0 ? null : platformImeOptions, (i4 & 32) != 0 ? null : bool, (i4 & 64) != 0 ? null : localeList, (DefaultConstructorMarker) null);
    }

    private KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool, LocaleList localeList) {
        this(i, Boolean.valueOf(z), i2, i3, platformImeOptions, bool, localeList, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KeyboardOptions(int i, boolean z, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i4 & 1) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i = KeyboardCapitalization.Unspecified;
        }
        int i5 = i;
        if ((i4 & 2) != 0) {
            Boolean bool = Default.autoCorrectEnabled;
            z = bool != null ? bool.booleanValue() : true;
        }
        boolean z2 = z;
        if ((i4 & 4) != 0) {
            KeyboardType.Companion.getClass();
            i2 = 0;
        }
        int i6 = i2;
        if ((i4 & 8) != 0) {
            ImeAction.Companion.getClass();
            i3 = ImeAction.Default;
        }
        this(i5, z2, i6, i3, (DefaultConstructorMarker) null);
    }

    private KeyboardOptions(int i, boolean z, int i2, int i3) {
        this(i, Boolean.valueOf(z), i2, i3, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 96, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i4 & 1) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i = 0;
        }
        int i5 = i;
        if ((i4 & 2) != 0) {
            Boolean bool = Default.autoCorrectEnabled;
            z = bool != null ? bool.booleanValue() : true;
        }
        boolean z2 = z;
        if ((i4 & 4) != 0) {
            KeyboardType.Companion.getClass();
            i2 = KeyboardType.Text;
        }
        int i6 = i2;
        if ((i4 & 8) != 0) {
            ImeAction.Companion.getClass();
            i3 = ImeAction.Default;
        }
        this(i5, z2, i6, i3, (i4 & 16) != 0 ? null : platformImeOptions, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions) {
        Boolean boolValueOf = Boolean.valueOf(z);
        Boolean bool = Default.showKeyboardOnFocus;
        this(i, boolValueOf, i2, i3, platformImeOptions, Boolean.valueOf(bool != null ? bool.booleanValue() : true), (LocaleList) null, 64, (DefaultConstructorMarker) null);
    }
}
