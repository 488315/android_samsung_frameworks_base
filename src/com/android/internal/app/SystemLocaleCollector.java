package com.android.internal.app;

import android.content.Context;
import android.os.LocaleList;
import com.android.internal.app.LocaleStore;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes5.dex */
public class SystemLocaleCollector implements LocaleCollectorBase {
    private final Context mContext;
    private LocaleList mExplicitLocales;

    @Override // com.android.internal.app.LocaleCollectorBase
    public boolean hasSpecificPackageName() {
        return false;
    }

    SystemLocaleCollector(Context context) {
        this(context, null);
    }

    public SystemLocaleCollector(Context context, LocaleList localeList) {
        this.mContext = context;
        this.mExplicitLocales = localeList;
    }

    @Override // com.android.internal.app.LocaleCollectorBase
    public Set<String> getIgnoredLocaleList(boolean z) {
        HashSet hashSet = new HashSet();
        if (!z) {
            Collections.addAll(hashSet, LocalePicker.getLocales().toLanguageTags().split(","));
        }
        return hashSet;
    }

    @Override // com.android.internal.app.LocaleCollectorBase
    public Set<LocaleStore.LocaleInfo> getSupportedLocaleList(LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2) {
        Set<String> ignoredLocaleList = getIgnoredLocaleList(z);
        if (z2) {
            return LocaleStore.getLevelLocales(this.mContext, ignoredLocaleList, localeInfo, z, this.mExplicitLocales);
        }
        return LocaleStore.getLevelLocales(this.mContext, ignoredLocaleList, null, z, this.mExplicitLocales);
    }
}
