package com.android.internal.app;

import android.app.LocaleManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.LocaleList;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.app.AppLocaleStore;
import com.android.internal.app.LocaleStore;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class AppLocaleCollector implements LocaleCollectorBase {
    private static final boolean ENABLED = true;
    private static final String PROP_APP_LANGUAGE_SUGGESTION = "android.app.language.suggestion.enhanced";
    private static final String TAG = "AppLocaleCollector";
    private Set<LocaleStore.LocaleInfo> mAllAppActiveLocales;
    private LocaleStore.LocaleInfo mAppCurrentLocale;
    private final String mAppPackageName;
    private final Context mContext;
    private Set<LocaleStore.LocaleInfo> mImeLocales;

    @Override // com.android.internal.app.LocaleCollectorBase
    public boolean hasSpecificPackageName() {
        return true;
    }

    public AppLocaleCollector(Context context, String str) {
        this.mContext = context;
        this.mAppPackageName = str;
    }

    public LocaleStore.LocaleInfo getAppCurrentLocale() {
        return LocaleStore.getAppActivatedLocaleInfo(this.mContext, this.mAppPackageName, true);
    }

    public Set<LocaleStore.LocaleInfo> getAllAppActiveLocales() {
        PackageManager packageManager = this.mContext.getPackageManager();
        LocaleManager localeManager = (LocaleManager) this.mContext.getSystemService(LocaleManager.class);
        final HashSet hashSet = new HashSet();
        if (packageManager != null && localeManager != null) {
            HashMap map = new HashMap();
            Iterator<ApplicationInfo> it = packageManager.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(0L)).iterator();
            while (it.hasNext()) {
                LocaleStore.LocaleInfo appActivatedLocaleInfo = LocaleStore.getAppActivatedLocaleInfo(this.mContext, it.next().packageName, false);
                if (appActivatedLocaleInfo != null && appActivatedLocaleInfo.getLocale().getCountry().length() > 0) {
                    map.put(appActivatedLocaleInfo.getId(), appActivatedLocaleInfo);
                }
            }
            map.forEach(new BiConsumer() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    hashSet.add((LocaleStore.LocaleInfo) obj2);
                }
            });
        }
        return hashSet;
    }

    public Set<LocaleStore.LocaleInfo> getActiveImeLocales() {
        InputMethodInfo activeIme;
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        Set<LocaleStore.LocaleInfo> setTransformImeLanguageTagToLocaleInfo = (inputMethodManager == null || (activeIme = getActiveIme(inputMethodManager)) == null) ? null : LocaleStore.transformImeLanguageTagToLocaleInfo(inputMethodManager.getEnabledInputMethodSubtypeList(activeIme, true));
        if (setTransformImeLanguageTagToLocaleInfo == null) {
            return Collections.EMPTY_SET;
        }
        return (Set) setTransformImeLanguageTagToLocaleInfo.stream().filter(new Predicate() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AppLocaleCollector.lambda$getActiveImeLocales$1((LocaleStore.LocaleInfo) obj);
            }
        }).collect(Collectors.toSet());
    }

    static /* synthetic */ boolean lambda$getActiveImeLocales$1(LocaleStore.LocaleInfo localeInfo) {
        return localeInfo.getLocale().getCountry().length() > 0;
    }

    private InputMethodInfo getActiveIme(InputMethodManager inputMethodManager) {
        List<InputMethodInfo> enabledInputMethodList = inputMethodManager.getEnabledInputMethodList();
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, this.mContext.getUserId());
        InputMethodInfo inputMethodInfo = null;
        if (enabledInputMethodList != null && stringForUser != null) {
            for (InputMethodInfo inputMethodInfo2 : enabledInputMethodList) {
                if (inputMethodInfo2.getId().equals(stringForUser)) {
                    inputMethodInfo = inputMethodInfo2;
                }
            }
        }
        return inputMethodInfo;
    }

    public AppLocaleStore.AppLocaleResult getAppSupportedLocales() {
        return AppLocaleStore.getAppSupportedLocales(this.mContext, this.mAppPackageName);
    }

    public Set<LocaleStore.LocaleInfo> getSystemSupportedLocale(Set<String> set, LocaleStore.LocaleInfo localeInfo, boolean z) {
        return LocaleStore.getLevelLocales(this.mContext, set, localeInfo, z);
    }

    public Set<LocaleStore.LocaleInfo> getSystemCurrentLocales() {
        return (Set) LocaleStore.getSystemCurrentLocales().stream().filter(new Predicate() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AppLocaleCollector.lambda$getSystemCurrentLocales$2((LocaleStore.LocaleInfo) obj);
            }
        }).collect(Collectors.toSet());
    }

    static /* synthetic */ boolean lambda$getSystemCurrentLocales$2(LocaleStore.LocaleInfo localeInfo) {
        return localeInfo.getLocale().getCountry().length() > 0;
    }

    @Override // com.android.internal.app.LocaleCollectorBase
    public Set<String> getIgnoredLocaleList(boolean z) {
        final HashSet hashSet = new HashSet();
        LocaleStore.LocaleInfo localeInfo = this.mAppCurrentLocale;
        if (localeInfo != null) {
            hashSet.add(localeInfo.getLocale().toLanguageTag());
        }
        if (SystemProperties.getBoolean(PROP_APP_LANGUAGE_SUGGESTION, true)) {
            this.mAllAppActiveLocales.forEach(new Consumer() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    hashSet.add(((LocaleStore.LocaleInfo) obj).getLocale().toLanguageTag());
                }
            });
            this.mImeLocales.forEach(new Consumer() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    hashSet.add(((LocaleStore.LocaleInfo) obj).getLocale().toLanguageTag());
                }
            });
        }
        LocaleList localeList = LocaleList.getDefault();
        for (int i = 0; i < localeList.size(); i++) {
            hashSet.add(localeList.get(i).toLanguageTag());
        }
        return hashSet;
    }

    @Override // com.android.internal.app.LocaleCollectorBase
    public Set<LocaleStore.LocaleInfo> getSupportedLocaleList(LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2) {
        Set<LocaleStore.LocaleInfo> systemSupportedLocale;
        if (this.mAppCurrentLocale == null) {
            this.mAppCurrentLocale = getAppCurrentLocale();
        }
        if (this.mAllAppActiveLocales == null) {
            this.mAllAppActiveLocales = getAllAppActiveLocales();
        }
        if (this.mImeLocales == null) {
            this.mImeLocales = getActiveImeLocales();
        }
        AppLocaleStore.AppLocaleResult appSupportedLocales = getAppSupportedLocales();
        Set<String> ignoredLocaleList = getIgnoredLocaleList(z);
        HashSet hashSet = new HashSet();
        boolean z3 = appSupportedLocales.mLocaleStatus == AppLocaleStore.AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_LOCAL_CONFIG || appSupportedLocales.mLocaleStatus == AppLocaleStore.AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_ASSET;
        Set<LocaleStore.LocaleInfo> suggestedLocales = null;
        if (z2) {
            systemSupportedLocale = getSystemSupportedLocale(ignoredLocaleList, localeInfo, z);
        } else {
            systemSupportedLocale = getSystemSupportedLocale(ignoredLocaleList, null, z);
        }
        LocaleStore.LocaleInfo localeInfo2 = this.mAppCurrentLocale;
        if (localeInfo2 != null && !z2) {
            hashSet.add(localeInfo2);
        }
        if (!z2) {
            for (LocaleStore.LocaleInfo localeInfo3 : filterSupportedLocales(getSystemCurrentLocales(), appSupportedLocales.mAppSupportedLocales)) {
                boolean z4 = this.mAppCurrentLocale != null && localeInfo3.getLocale().equals(this.mAppCurrentLocale.getLocale());
                boolean zAddSystemSuggestionFlag = addSystemSuggestionFlag(localeInfo3, this.mAllAppActiveLocales);
                boolean zAddSystemSuggestionFlag2 = addSystemSuggestionFlag(localeInfo3, this.mImeLocales);
                if (!z4 && !zAddSystemSuggestionFlag && !zAddSystemSuggestionFlag2) {
                    hashSet.add(localeInfo3);
                }
            }
        }
        if (z3) {
            hashSet.addAll(filterSupportedLocales(systemSupportedLocale, appSupportedLocales.mAppSupportedLocales));
            suggestedLocales = getSuggestedLocales(hashSet);
        }
        if (!z2 && SystemProperties.getBoolean(PROP_APP_LANGUAGE_SUGGESTION, true)) {
            Set<LocaleStore.LocaleInfo> setFilterSupportedLocales = filterSupportedLocales(this.mAllAppActiveLocales, appSupportedLocales.mAppSupportedLocales);
            if (suggestedLocales != null) {
                setFilterSupportedLocales = addImeSuggestionFlag(filterSameLanguageAndCountry(setFilterSupportedLocales, suggestedLocales));
            }
            hashSet.addAll(setFilterSupportedLocales);
            suggestedLocales.addAll(setFilterSupportedLocales);
            Set<LocaleStore.LocaleInfo> setFilterSupportedLocales2 = filterSupportedLocales(this.mImeLocales, appSupportedLocales.mAppSupportedLocales);
            if (suggestedLocales != null) {
                setFilterSupportedLocales2 = filterSameLanguageAndCountry(setFilterSupportedLocales2, suggestedLocales);
            }
            hashSet.addAll(setFilterSupportedLocales2);
            suggestedLocales.addAll(setFilterSupportedLocales2);
        }
        if (!z2 && z3) {
            hashSet.add(LocaleStore.getSystemDefaultLocaleInfo(this.mAppCurrentLocale == null));
        }
        if (Build.isDebuggable()) {
            Log.d(TAG, "App locale list: " + hashSet);
        }
        return hashSet;
    }

    private Set<LocaleStore.LocaleInfo> getSuggestedLocales(Set<LocaleStore.LocaleInfo> set) {
        return (Set) set.stream().filter(new Predicate() { // from class: com.android.internal.app.AppLocaleCollector$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((LocaleStore.LocaleInfo) obj).isSuggested();
            }
        }).collect(Collectors.toSet());
    }

    private boolean addSystemSuggestionFlag(LocaleStore.LocaleInfo localeInfo, Set<LocaleStore.LocaleInfo> set) {
        for (LocaleStore.LocaleInfo localeInfo2 : set) {
            if (localeInfo2.getLocale().equals(localeInfo.getLocale())) {
                localeInfo2.extendSuggestionOfType(64);
                return true;
            }
        }
        return false;
    }

    private Set<LocaleStore.LocaleInfo> addImeSuggestionFlag(Set<LocaleStore.LocaleInfo> set) {
        for (LocaleStore.LocaleInfo localeInfo : set) {
            Iterator<LocaleStore.LocaleInfo> it = this.mImeLocales.iterator();
            while (it.hasNext()) {
                if (it.next().getLocale().equals(localeInfo.getLocale())) {
                    localeInfo.extendSuggestionOfType(32);
                }
            }
        }
        return set;
    }

    private Set<LocaleStore.LocaleInfo> filterSameLanguageAndCountry(Set<LocaleStore.LocaleInfo> set, Set<LocaleStore.LocaleInfo> set2) {
        HashSet hashSet = new HashSet(set.size());
        for (LocaleStore.LocaleInfo localeInfo : set) {
            Locale locale = localeInfo.getLocale();
            Iterator<LocaleStore.LocaleInfo> it = set2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Locale locale2 = it.next().getLocale();
                    if (!locale.getLanguage().equals(locale2.getLanguage()) || !locale.getCountry().equals(locale2.getCountry())) {
                    }
                } else {
                    hashSet.add(localeInfo);
                    break;
                }
            }
        }
        return hashSet;
    }

    private Set<LocaleStore.LocaleInfo> filterSupportedLocales(Set<LocaleStore.LocaleInfo> set, HashSet<Locale> hashSet) {
        HashSet hashSet2 = new HashSet();
        for (LocaleStore.LocaleInfo localeInfo : set) {
            if (hashSet.contains(localeInfo.getLocale())) {
                hashSet2.add(localeInfo);
            } else {
                Iterator<Locale> it = hashSet.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (LocaleList.matchesLanguageAndScript(localeInfo.getLocale(), it.next())) {
                            hashSet2.add(localeInfo);
                            break;
                        }
                    }
                }
            }
        }
        return hashSet2;
    }
}
