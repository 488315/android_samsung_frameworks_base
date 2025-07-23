package androidx.compose.ui.text.input;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.intl.LocaleList;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = Boolean.hashCode(this.singleLine) * 31;
        KeyboardCapitalization.Companion companion = KeyboardCapitalization.Companion;
        int m = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.capitalization, hashCode, 31), 31, this.autoCorrect);
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.keyboardType, m, 31);
        ImeAction.Companion companion3 = ImeAction.Companion;
        int m3 = ReorderTile$$ExternalSyntheticOutline0.m(this.imeAction, m2, 31);
        PlatformImeOptions platformImeOptions = this.platformImeOptions;
        return this.hintLocales.localeList.hashCode() + ((m3 + (platformImeOptions != null ? platformImeOptions.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "ImeOptions(singleLine=" + this.singleLine + ", capitalization=" + ((Object) KeyboardCapitalization.m775toStringimpl(this.capitalization)) + ", autoCorrect=" + this.autoCorrect + ", keyboardType=" + ((Object) KeyboardType.m777toStringimpl(this.keyboardType)) + ", imeAction=" + ((Object) ImeAction.m773toStringimpl(this.imeAction)) + ", platformImeOptions=" + this.platformImeOptions + ", hintLocales=" + this.hintLocales + ')';
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ImeOptions(boolean r2, int r3, boolean r4, int r5, int r6, androidx.compose.ui.text.input.PlatformImeOptions r7, androidx.compose.ui.text.intl.LocaleList r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r1 = this;
            r10 = r9 & 1
            r0 = 0
            if (r10 == 0) goto L6
            r2 = r0
        L6:
            r10 = r9 & 2
            if (r10 == 0) goto L10
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r3 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r3.getClass()
            r3 = r0
        L10:
            r10 = r9 & 4
            if (r10 == 0) goto L15
            r4 = 1
        L15:
            r10 = r9 & 8
            if (r10 == 0) goto L20
            androidx.compose.ui.text.input.KeyboardType$Companion r5 = androidx.compose.ui.text.input.KeyboardType.Companion
            r5.getClass()
            int r5 = androidx.compose.ui.text.input.KeyboardType.Text
        L20:
            r10 = r9 & 16
            if (r10 == 0) goto L2b
            androidx.compose.ui.text.input.ImeAction$Companion r6 = androidx.compose.ui.text.input.ImeAction.Companion
            r6.getClass()
            int r6 = androidx.compose.ui.text.input.ImeAction.Default
        L2b:
            r10 = r9 & 32
            if (r10 == 0) goto L30
            r7 = 0
        L30:
            r9 = r9 & 64
            if (r9 == 0) goto L3b
            androidx.compose.ui.text.intl.LocaleList$Companion r8 = androidx.compose.ui.text.intl.LocaleList.Companion
            r8.getClass()
            androidx.compose.ui.text.intl.LocaleList r8 = androidx.compose.ui.text.intl.LocaleList.Empty
        L3b:
            r9 = 0
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.ImeOptions.<init>(boolean, int, boolean, int, int, androidx.compose.ui.text.input.PlatformImeOptions, androidx.compose.ui.text.intl.LocaleList, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ImeOptions(boolean r2, int r3, boolean r4, int r5, int r6, androidx.compose.ui.text.input.PlatformImeOptions r7, int r8, kotlin.jvm.internal.DefaultConstructorMarker r9) {
        /*
            r1 = this;
            r9 = r8 & 1
            r0 = 0
            if (r9 == 0) goto L6
            r2 = r0
        L6:
            r9 = r8 & 2
            if (r9 == 0) goto L10
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r3 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r3.getClass()
            r3 = r0
        L10:
            r9 = r8 & 4
            if (r9 == 0) goto L15
            r4 = 1
        L15:
            r9 = r8 & 8
            if (r9 == 0) goto L20
            androidx.compose.ui.text.input.KeyboardType$Companion r5 = androidx.compose.ui.text.input.KeyboardType.Companion
            r5.getClass()
            int r5 = androidx.compose.ui.text.input.KeyboardType.Text
        L20:
            r9 = r8 & 16
            if (r9 == 0) goto L2b
            androidx.compose.ui.text.input.ImeAction$Companion r6 = androidx.compose.ui.text.input.ImeAction.Companion
            r6.getClass()
            int r6 = androidx.compose.ui.text.input.ImeAction.Default
        L2b:
            r8 = r8 & 32
            if (r8 == 0) goto L30
            r7 = 0
        L30:
            r8 = 0
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.ImeOptions.<init>(boolean, int, boolean, int, int, androidx.compose.ui.text.input.PlatformImeOptions, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private ImeOptions(boolean z, int i, boolean z2, int i2, int i3, PlatformImeOptions platformImeOptions) {
        this(z, i, z2, i2, i3, platformImeOptions, LocaleList.Empty, (DefaultConstructorMarker) null);
        LocaleList.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ImeOptions(boolean r2, int r3, boolean r4, int r5, int r6, int r7, kotlin.jvm.internal.DefaultConstructorMarker r8) {
        /*
            r1 = this;
            r8 = r7 & 1
            r0 = 0
            if (r8 == 0) goto L6
            r2 = r0
        L6:
            r8 = r7 & 2
            if (r8 == 0) goto L10
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r3 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r3.getClass()
            r3 = r0
        L10:
            r8 = r7 & 4
            if (r8 == 0) goto L15
            r4 = 1
        L15:
            r8 = r7 & 8
            if (r8 == 0) goto L20
            androidx.compose.ui.text.input.KeyboardType$Companion r5 = androidx.compose.ui.text.input.KeyboardType.Companion
            r5.getClass()
            int r5 = androidx.compose.ui.text.input.KeyboardType.Text
        L20:
            r7 = r7 & 16
            if (r7 == 0) goto L2b
            androidx.compose.ui.text.input.ImeAction$Companion r6 = androidx.compose.ui.text.input.ImeAction.Companion
            r6.getClass()
            int r6 = androidx.compose.ui.text.input.ImeAction.Default
        L2b:
            r7 = 0
            r1.<init>(r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.ImeOptions.<init>(boolean, int, boolean, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private ImeOptions(boolean z, int i, boolean z2, int i2, int i3) {
        this(z, i, z2, i2, i3, null, null, 64, null);
    }
}
