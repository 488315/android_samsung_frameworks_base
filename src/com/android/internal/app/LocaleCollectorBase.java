package com.android.internal.app;

import com.android.internal.app.LocaleStore;
import java.util.Set;

/* loaded from: classes5.dex */
public interface LocaleCollectorBase {
    Set<String> getIgnoredLocaleList(boolean z);

    Set<LocaleStore.LocaleInfo> getSupportedLocaleList(LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2);

    boolean hasSpecificPackageName();
}
