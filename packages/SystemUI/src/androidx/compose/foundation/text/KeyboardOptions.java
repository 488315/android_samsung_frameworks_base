package androidx.compose.foundation.text;

import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PlatformImeOptions;
import androidx.compose.ui.text.intl.LocaleList;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = Integer.hashCode(this.capitalization) * 31;
        Boolean bool = this.autoCorrectEnabled;
        int hashCode2 = (hashCode + (bool != null ? bool.hashCode() : 0)) * 31;
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.keyboardType, hashCode2, 31);
        ImeAction.Companion companion3 = ImeAction.Companion;
        int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.imeAction, m, 31);
        PlatformImeOptions platformImeOptions = this.platformImeOptions;
        int hashCode3 = (m2 + (platformImeOptions != null ? platformImeOptions.hashCode() : 0)) * 31;
        Boolean bool2 = this.showKeyboardOnFocus;
        int hashCode4 = (hashCode3 + (bool2 != null ? bool2.hashCode() : 0)) * 31;
        LocaleList localeList = this.hintLocales;
        return hashCode4 + (localeList != null ? localeList.localeList.hashCode() : 0);
    }

    public final String toString() {
        return "KeyboardOptions(capitalization=" + ((Object) KeyboardCapitalization.m775toStringimpl(this.capitalization)) + ", autoCorrectEnabled=" + this.autoCorrectEnabled + ", keyboardType=" + ((Object) KeyboardType.m777toStringimpl(this.keyboardType)) + ", imeAction=" + ((Object) ImeAction.m773toStringimpl(this.imeAction)) + ", platformImeOptions=" + this.platformImeOptions + "showKeyboardOnFocus=" + this.showKeyboardOnFocus + ", hintLocales=" + this.hintLocales + ')';
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KeyboardOptions(int r9, java.lang.Boolean r10, int r11, int r12, androidx.compose.ui.text.input.PlatformImeOptions r13, java.lang.Boolean r14, androidx.compose.ui.text.intl.LocaleList r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
        /*
            r8 = this;
            r0 = r16 & 1
            if (r0 == 0) goto Lc
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r0 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.input.KeyboardCapitalization.Unspecified
            goto Ld
        Lc:
            r0 = r9
        Ld:
            r1 = r16 & 2
            r2 = 0
            if (r1 == 0) goto L14
            r1 = r2
            goto L15
        L14:
            r1 = r10
        L15:
            r3 = r16 & 4
            if (r3 == 0) goto L20
            androidx.compose.ui.text.input.KeyboardType$Companion r3 = androidx.compose.ui.text.input.KeyboardType.Companion
            r3.getClass()
            r3 = 0
            goto L21
        L20:
            r3 = r11
        L21:
            r4 = r16 & 8
            if (r4 == 0) goto L2d
            androidx.compose.ui.text.input.ImeAction$Companion r4 = androidx.compose.ui.text.input.ImeAction.Companion
            r4.getClass()
            int r4 = androidx.compose.ui.text.input.ImeAction.Unspecified
            goto L2e
        L2d:
            r4 = r12
        L2e:
            r5 = r16 & 16
            if (r5 == 0) goto L34
            r5 = r2
            goto L35
        L34:
            r5 = r13
        L35:
            r6 = r16 & 32
            if (r6 == 0) goto L3b
            r6 = r2
            goto L3c
        L3b:
            r6 = r14
        L3c:
            r7 = r16 & 64
            if (r7 == 0) goto L41
            goto L42
        L41:
            r2 = r15
        L42:
            r7 = 0
            r9 = r8
            r10 = r0
            r11 = r1
            r16 = r2
            r12 = r3
            r13 = r4
            r14 = r5
            r15 = r6
            r17 = r7
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.KeyboardOptions.<init>(int, java.lang.Boolean, int, int, androidx.compose.ui.text.input.PlatformImeOptions, java.lang.Boolean, androidx.compose.ui.text.intl.LocaleList, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KeyboardOptions(int r10, boolean r11, int r12, int r13, androidx.compose.ui.text.input.PlatformImeOptions r14, java.lang.Boolean r15, androidx.compose.ui.text.intl.LocaleList r16, int r17, kotlin.jvm.internal.DefaultConstructorMarker r18) {
        /*
            r9 = this;
            r0 = r17 & 1
            if (r0 == 0) goto Lb
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r10 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r10.getClass()
            int r10 = androidx.compose.ui.text.input.KeyboardCapitalization.Unspecified
        Lb:
            r1 = r10
            r10 = r17 & 4
            if (r10 == 0) goto L16
            androidx.compose.ui.text.input.KeyboardType$Companion r10 = androidx.compose.ui.text.input.KeyboardType.Companion
            r10.getClass()
            r12 = 0
        L16:
            r3 = r12
            r10 = r17 & 8
            if (r10 == 0) goto L22
            androidx.compose.ui.text.input.ImeAction$Companion r10 = androidx.compose.ui.text.input.ImeAction.Companion
            r10.getClass()
            int r13 = androidx.compose.ui.text.input.ImeAction.Unspecified
        L22:
            r4 = r13
            r10 = r17 & 16
            r12 = 0
            if (r10 == 0) goto L2a
            r5 = r12
            goto L2b
        L2a:
            r5 = r14
        L2b:
            r10 = r17 & 32
            if (r10 == 0) goto L31
            r6 = r12
            goto L32
        L31:
            r6 = r15
        L32:
            r10 = r17 & 64
            if (r10 == 0) goto L38
            r7 = r12
            goto L3a
        L38:
            r7 = r16
        L3a:
            r8 = 0
            r0 = r9
            r2 = r11
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.KeyboardOptions.<init>(int, boolean, int, int, androidx.compose.ui.text.input.PlatformImeOptions, java.lang.Boolean, androidx.compose.ui.text.intl.LocaleList, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private KeyboardOptions(int i, boolean z, int i2, int i3, PlatformImeOptions platformImeOptions, Boolean bool, LocaleList localeList) {
        this(i, Boolean.valueOf(z), i2, i3, platformImeOptions, bool, localeList, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KeyboardOptions(int r7, boolean r8, int r9, int r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r12 = r11 & 1
            if (r12 == 0) goto Lb
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r7 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r7.getClass()
            int r7 = androidx.compose.ui.text.input.KeyboardCapitalization.Unspecified
        Lb:
            r1 = r7
            r7 = r11 & 2
            if (r7 == 0) goto L1e
            androidx.compose.foundation.text.KeyboardOptions r7 = androidx.compose.foundation.text.KeyboardOptions.Default
            java.lang.Boolean r7 = r7.autoCorrectEnabled
            if (r7 == 0) goto L1c
            boolean r7 = r7.booleanValue()
        L1a:
            r8 = r7
            goto L1e
        L1c:
            r7 = 1
            goto L1a
        L1e:
            r2 = r8
            r7 = r11 & 4
            if (r7 == 0) goto L29
            androidx.compose.ui.text.input.KeyboardType$Companion r7 = androidx.compose.ui.text.input.KeyboardType.Companion
            r7.getClass()
            r9 = 0
        L29:
            r3 = r9
            r7 = r11 & 8
            if (r7 == 0) goto L35
            androidx.compose.ui.text.input.ImeAction$Companion r7 = androidx.compose.ui.text.input.ImeAction.Companion
            r7.getClass()
            int r10 = androidx.compose.ui.text.input.ImeAction.Default
        L35:
            r4 = r10
            r5 = 0
            r0 = r6
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.KeyboardOptions.<init>(int, boolean, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private KeyboardOptions(int i, boolean z, int i2, int i3) {
        this(i, Boolean.valueOf(z), i2, i3, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 96, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KeyboardOptions(int r8, boolean r9, int r10, int r11, androidx.compose.ui.text.input.PlatformImeOptions r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 1
            if (r14 == 0) goto La
            androidx.compose.ui.text.input.KeyboardCapitalization$Companion r8 = androidx.compose.ui.text.input.KeyboardCapitalization.Companion
            r8.getClass()
            r8 = 0
        La:
            r1 = r8
            r8 = r13 & 2
            if (r8 == 0) goto L1d
            androidx.compose.foundation.text.KeyboardOptions r8 = androidx.compose.foundation.text.KeyboardOptions.Default
            java.lang.Boolean r8 = r8.autoCorrectEnabled
            if (r8 == 0) goto L1b
            boolean r8 = r8.booleanValue()
        L19:
            r9 = r8
            goto L1d
        L1b:
            r8 = 1
            goto L19
        L1d:
            r2 = r9
            r8 = r13 & 4
            if (r8 == 0) goto L29
            androidx.compose.ui.text.input.KeyboardType$Companion r8 = androidx.compose.ui.text.input.KeyboardType.Companion
            r8.getClass()
            int r10 = androidx.compose.ui.text.input.KeyboardType.Text
        L29:
            r3 = r10
            r8 = r13 & 8
            if (r8 == 0) goto L35
            androidx.compose.ui.text.input.ImeAction$Companion r8 = androidx.compose.ui.text.input.ImeAction.Companion
            r8.getClass()
            int r11 = androidx.compose.ui.text.input.ImeAction.Default
        L35:
            r4 = r11
            r8 = r13 & 16
            if (r8 == 0) goto L3b
            r12 = 0
        L3b:
            r5 = r12
            r6 = 0
            r0 = r7
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.KeyboardOptions.<init>(int, boolean, int, int, androidx.compose.ui.text.input.PlatformImeOptions, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private KeyboardOptions(int r11, boolean r12, int r13, int r14, androidx.compose.ui.text.input.PlatformImeOptions r15) {
        /*
            r10 = this;
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r12)
            androidx.compose.foundation.text.KeyboardOptions r12 = androidx.compose.foundation.text.KeyboardOptions.Default
            java.lang.Boolean r12 = r12.showKeyboardOnFocus
            if (r12 == 0) goto Lf
            boolean r12 = r12.booleanValue()
            goto L10
        Lf:
            r12 = 1
        L10:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r12)
            r8 = 64
            r9 = 0
            r7 = 0
            r0 = r10
            r1 = r11
            r3 = r13
            r4 = r14
            r5 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.KeyboardOptions.<init>(int, boolean, int, int, androidx.compose.ui.text.input.PlatformImeOptions):void");
    }
}
