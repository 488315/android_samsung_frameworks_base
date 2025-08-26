package com.android.settingslib.inputmethod;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.app.LocaleHelper;
import java.util.Locale;

/* loaded from: classes.dex */
public class InputMethodSubtypePreference extends SwitchWithNoTextPreference {
    /* JADX WARN: Illegal instructions before constructor call */
    public InputMethodSubtypePreference(Context context, InputMethodSubtype inputMethodSubtype, InputMethodInfo inputMethodInfo) {
        Configuration configuration;
        Locale locale;
        String str = inputMethodInfo.getId() + inputMethodSubtype.hashCode();
        int i = InputMethodAndSubtypeUtil.$r8$clinit;
        if (context == null || context.getResources() == null || (configuration = context.getResources().getConfiguration()) == null || (locale = configuration.getLocales().get(0)) == null) {
            locale = Locale.getDefault();
        }
        this(context, str, LocaleHelper.toSentenceCase(inputMethodSubtype.getDisplayName(context, inputMethodInfo.getPackageName(), inputMethodInfo.getServiceInfo().applicationInfo).toString(), locale), inputMethodSubtype.getLocaleObject(), context.getResources().getConfiguration().locale);
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
