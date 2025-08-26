package androidx.compose.ui.text.input;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.intl.LocaleList;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ImeOptions {
    public static final Companion Companion = new Companion(null);
    public static final ImeOptions Default = new ImeOptions(false, 0, false, 0, 0, null, null, 127, null);
    public final boolean autoCorrect;
    public final int capitalization;
    public final LocaleList hintLocales;
    public final int imeAction;
    public final int keyboardType;
    public final PlatformImeOptions platformImeOptions;
    public final boolean singleLine;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions, LocaleList localeList, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, i, z2, i2, i3, platformImeOptions, localeList);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImeOptions)) {
            return false;
        }
        ImeOptions imeOptions = (ImeOptions) obj;
        if (this.singleLine != imeOptions.singleLine) {
            return false;
        }
        int i = imeOptions.capitalization;
        KeyboardCapitalization.Companion companion = KeyboardCapitalization.Companion;
        if (this.capitalization != i || this.autoCorrect != imeOptions.autoCorrect) {
            return false;
        }
        int i2 = imeOptions.keyboardType;
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        if (this.keyboardType == i2) {
            int i3 = imeOptions.imeAction;
            ImeAction.Companion companion3 = ImeAction.Companion;
            return this.imeAction == i3 && Intrinsics.areEqual(this.platformImeOptions, imeOptions.platformImeOptions) && Intrinsics.areEqual(this.hintLocales, imeOptions.hintLocales);
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = Boolean.hashCode(this.singleLine) * 31;
        KeyboardCapitalization.Companion companion = KeyboardCapitalization.Companion;
        int iM = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.capitalization, iHashCode, 31), 31, this.autoCorrect);
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        int iM2 = ReorderTile$$ExternalSyntheticOutline0.m(this.keyboardType, iM, 31);
        ImeAction.Companion companion3 = ImeAction.Companion;
        int iM3 = ReorderTile$$ExternalSyntheticOutline0.m(this.imeAction, iM2, 31);
        PlatformImeOptions platformImeOptions = this.platformImeOptions;
        return this.hintLocales.localeList.hashCode() + ((iM3 + (platformImeOptions != null ? platformImeOptions.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "ImeOptions(singleLine=" + this.singleLine + ", capitalization=" + ((Object) KeyboardCapitalization.m777toStringimpl(this.capitalization)) + ", autoCorrect=" + this.autoCorrect + ", keyboardType=" + ((Object) KeyboardType.m779toStringimpl(this.keyboardType)) + ", imeAction=" + ((Object) ImeAction.m775toStringimpl(this.imeAction)) + ", platformImeOptions=" + this.platformImeOptions + ", hintLocales=" + this.hintLocales + ')';
    }

    public /* synthetic */ ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, i, z2, i2, i3, platformImeOptions);
    }

    public /* synthetic */ ImeOptions(boolean z, int i, boolean z2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, i, z2, i2, i3);
    }

    private ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions, LocaleList localeList) {
        this.singleLine = z;
        this.capitalization = i;
        this.autoCorrect = z2;
        this.keyboardType = i2;
        this.imeAction = i3;
        this.platformImeOptions = platformImeOptions;
        this.hintLocales = localeList;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions, LocaleList localeList, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        z = (i4 & 1) != 0 ? false : z;
        if ((i4 & 2) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i = 0;
        }
        z2 = (i4 & 4) != 0 ? true : z2;
        if ((i4 & 8) != 0) {
            KeyboardType.Companion.getClass();
            i2 = KeyboardType.Text;
        }
        if ((i4 & 16) != 0) {
            ImeAction.Companion.getClass();
            i3 = ImeAction.Default;
        }
        platformImeOptions = (i4 & 32) != 0 ? null : platformImeOptions;
        if ((i4 & 64) != 0) {
            LocaleList.Companion.getClass();
            localeList = LocaleList.Empty;
        }
        this(z, i, z2, i2, i3, platformImeOptions, localeList, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        z = (i4 & 1) != 0 ? false : z;
        if ((i4 & 2) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i = 0;
        }
        z2 = (i4 & 4) != 0 ? true : z2;
        if ((i4 & 8) != 0) {
            KeyboardType.Companion.getClass();
            i2 = KeyboardType.Text;
        }
        if ((i4 & 16) != 0) {
            ImeAction.Companion.getClass();
            i3 = ImeAction.Default;
        }
        this(z, i, z2, i2, i3, (i4 & 32) != 0 ? null : platformImeOptions, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions) {
        this(z, i, z2, i2, i3, platformImeOptions, LocaleList.Empty, (DefaultConstructorMarker) null);
        LocaleList.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ImeOptions(boolean z, int i, boolean z2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        z = (i4 & 1) != 0 ? false : z;
        if ((i4 & 2) != 0) {
            KeyboardCapitalization.Companion.getClass();
            i = 0;
        }
        z2 = (i4 & 4) != 0 ? true : z2;
        if ((i4 & 8) != 0) {
            KeyboardType.Companion.getClass();
            i2 = KeyboardType.Text;
        }
        if ((i4 & 16) != 0) {
            ImeAction.Companion.getClass();
            i3 = ImeAction.Default;
        }
        this(z, i, z2, i2, i3, (DefaultConstructorMarker) null);
    }

    private ImeOptions(boolean z, int i, boolean z2, int i2, int i3) {
        this(z, i, z2, i2, i3, null, null, 64, null);
    }
}
