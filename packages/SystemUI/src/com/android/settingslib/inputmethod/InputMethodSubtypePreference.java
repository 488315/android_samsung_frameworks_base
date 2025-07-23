package com.android.settingslib.inputmethod;

import android.content.Context;
import android.text.TextUtils;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class InputMethodSubtypePreference extends SwitchWithNoTextPreference {
    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public InputMethodSubtypePreference(android.content.Context r9, android.view.inputmethod.InputMethodSubtype r10, android.view.inputmethod.InputMethodInfo r11) {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r11.getId()
            r0.append(r1)
            int r1 = r10.hashCode()
            r0.append(r1)
            java.lang.String r4 = r0.toString()
            int r0 = com.android.settingslib.inputmethod.InputMethodAndSubtypeUtil.$r8$clinit
            if (r9 != 0) goto L20
            java.util.Locale r0 = java.util.Locale.getDefault()
            goto L49
        L20:
            android.content.res.Resources r0 = r9.getResources()
            if (r0 != 0) goto L2b
            java.util.Locale r0 = java.util.Locale.getDefault()
            goto L49
        L2b:
            android.content.res.Resources r0 = r9.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            if (r0 != 0) goto L3a
            java.util.Locale r0 = java.util.Locale.getDefault()
            goto L49
        L3a:
            android.os.LocaleList r0 = r0.getLocales()
            r1 = 0
            java.util.Locale r0 = r0.get(r1)
            if (r0 != 0) goto L49
            java.util.Locale r0 = java.util.Locale.getDefault()
        L49:
            java.lang.String r1 = r11.getPackageName()
            android.content.pm.ServiceInfo r11 = r11.getServiceInfo()
            android.content.pm.ApplicationInfo r11 = r11.applicationInfo
            java.lang.CharSequence r11 = r10.getDisplayName(r9, r1, r11)
            java.lang.String r11 = r11.toString()
            java.lang.String r5 = com.android.internal.app.LocaleHelper.toSentenceCase(r11, r0)
            java.util.Locale r6 = r10.getLocaleObject()
            android.content.res.Resources r10 = r9.getResources()
            android.content.res.Configuration r10 = r10.getConfiguration()
            java.util.Locale r7 = r10.locale
            r2 = r8
            r3 = r9
            r2.<init>(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.inputmethod.InputMethodSubtypePreference.<init>(android.content.Context, android.view.inputmethod.InputMethodSubtype, android.view.inputmethod.InputMethodInfo):void");
    }

    public InputMethodSubtypePreference(Context context, String str, CharSequence charSequence, Locale locale, Locale locale2) {
        super(context);
        this.mPersistent = false;
        setKey(str);
        setTitle(charSequence);
        if (locale == null || locale.equals(locale2)) {
            return;
        }
        TextUtils.equals(locale.getLanguage(), locale2.getLanguage());
    }
}
