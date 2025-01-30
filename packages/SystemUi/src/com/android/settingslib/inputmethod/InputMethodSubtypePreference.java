package com.android.settingslib.inputmethod;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.app.LocaleHelper;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class InputMethodSubtypePreference extends SwitchWithNoTextPreference {
    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public InputMethodSubtypePreference(Context context, InputMethodSubtype inputMethodSubtype, InputMethodInfo inputMethodInfo) {
        this(context, r4, LocaleHelper.toSentenceCase(inputMethodSubtype.getDisplayName(context, inputMethodInfo.getPackageName(), inputMethodInfo.getServiceInfo().applicationInfo).toString(), r0), inputMethodSubtype.getLocaleObject(), context.getResources().getConfiguration().locale);
        Locale locale;
        String str = inputMethodInfo.getId() + inputMethodSubtype.hashCode();
        int i = InputMethodAndSubtypeUtil.$r8$clinit;
        if (context == null) {
            locale = Locale.getDefault();
        } else if (context.getResources() == null) {
            locale = Locale.getDefault();
        } else {
            Configuration configuration = context.getResources().getConfiguration();
            if (configuration == null) {
                locale = Locale.getDefault();
            } else {
                locale = configuration.getLocales().get(0);
                if (locale == null) {
                    locale = Locale.getDefault();
                }
            }
        }
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
