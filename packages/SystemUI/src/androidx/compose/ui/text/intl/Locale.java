package androidx.compose.ui.text.intl;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Locale {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final java.util.Locale platformLocale;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public Locale(java.util.Locale locale) {
        this.platformLocale = locale;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Locale)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return Intrinsics.areEqual(this.platformLocale.toLanguageTag(), ((Locale) obj).platformLocale.toLanguageTag());
    }

    public final int hashCode() {
        return this.platformLocale.toLanguageTag().hashCode();
    }

    public final String toString() {
        return this.platformLocale.toLanguageTag();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Locale(java.lang.String r4) {
        /*
            r3 = this;
            androidx.compose.ui.text.intl.AndroidLocaleDelegateAPI24 r0 = androidx.compose.ui.text.intl.PlatformLocaleKt.platformLocaleDelegate
            r0.getClass()
            java.util.Locale r0 = java.util.Locale.forLanguageTag(r4)
            java.lang.String r1 = r0.toLanguageTag()
            java.lang.String r2 = "und"
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r1 == 0) goto L2e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "The language tag "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r4 = " is not well-formed. Locale is resolved to Undetermined. Note that underscore '_' is not a valid subtag delimiter and must be replaced with '-'."
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            java.lang.String r1 = "Locale"
            android.util.Log.e(r1, r4)
        L2e:
            r3.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.intl.Locale.<init>(java.lang.String):void");
    }
}
