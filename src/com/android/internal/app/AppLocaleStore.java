package com.android.internal.app;

import android.app.LocaleConfig;
import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.LocaleList;
import android.util.Log;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class AppLocaleStore {
    private static final String TAG = "AppLocaleStore";

    public static AppLocaleResult getAppSupportedLocales(Context context, String str) {
        LocaleConfig localeConfig;
        AppLocaleResult.LocaleStatus localeStatus;
        AppLocaleResult.LocaleStatus localeStatus2 = AppLocaleResult.LocaleStatus.UNKNOWN_FAILURE;
        HashSet<Locale> hashSet = new HashSet<>();
        HashSet<Locale> assetLocales = getAssetLocales(context, str);
        try {
            localeConfig = new LocaleConfig(context.createPackageContext(str, 0));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Can not found the package name : " + str + " / " + e);
            localeConfig = null;
        }
        if (localeConfig != null) {
            if (localeConfig.getStatus() == 0) {
                LocaleList supportedLocales = localeConfig.getSupportedLocales();
                boolean z = !hasInstallerInfo(context, str) && isSystemApp(context, str);
                Log.d(TAG, "filterNonMatchingLocale. , shouldFilterNotMatchingLocale: " + z + ", assetLocale size: " + assetLocales.size() + ", packageLocaleList size: " + supportedLocales.size());
                for (int i = 0; i < supportedLocales.size(); i++) {
                    hashSet.add(supportedLocales.get(i));
                }
                if (z) {
                    hashSet = filterNotMatchingLocale(hashSet, assetLocales);
                }
                if (hashSet.size() > 0) {
                    localeStatus = AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_LOCAL_CONFIG;
                } else {
                    localeStatus = AppLocaleResult.LocaleStatus.NO_SUPPORTED_LANGUAGE_IN_APP;
                }
                localeStatus2 = localeStatus;
            } else if (localeConfig.getStatus() == 1) {
                if (assetLocales.size() > 0) {
                    localeStatus2 = AppLocaleResult.LocaleStatus.GET_SUPPORTED_LANGUAGE_FROM_ASSET;
                    hashSet = assetLocales;
                } else {
                    localeStatus2 = AppLocaleResult.LocaleStatus.ASSET_LOCALE_IS_EMPTY;
                }
            }
        }
        Log.d(TAG, "getAppSupportedLocales(). package: " + str + ", status: " + localeStatus2 + ", appSupportedLocales:" + hashSet.size());
        return new AppLocaleResult(localeStatus2, hashSet);
    }

    private static HashSet<Locale> getAssetLocales(Context context, String str) {
        String[] nonSystemLocales;
        HashSet<Locale> hashSet = new HashSet<>();
        try {
            PackageManager packageManager = context.getPackageManager();
            nonSystemLocales = packageManager.getResourcesForApplication(packageManager.getPackageInfo(str, 131072).applicationInfo).getAssets().getNonSystemLocales();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Can not found the package name : " + str + " / " + e);
        }
        if (nonSystemLocales == null) {
            Log.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + str + "] locales are null.");
            return hashSet;
        }
        if (nonSystemLocales.length <= 0) {
            Log.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + str + "] locales length is 0.");
            return hashSet;
        }
        for (String str2 : nonSystemLocales) {
            hashSet.add(Locale.forLanguageTag(str2));
        }
        return hashSet;
    }

    private static HashSet<Locale> filterNotMatchingLocale(HashSet<Locale> hashSet, final HashSet<Locale> hashSet2) {
        return (HashSet) hashSet.stream().filter(new Predicate() { // from class: com.android.internal.app.AppLocaleStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean matchLanguageInSet;
                matchLanguageInSet = AppLocaleStore.matchLanguageInSet((Locale) obj, hashSet2);
                return matchLanguageInSet;
            }
        }).collect(Collectors.toCollection(new Supplier() { // from class: com.android.internal.app.AppLocaleStore$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new HashSet();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean matchLanguageInSet(Locale locale, HashSet<Locale> hashSet) {
        if (hashSet.contains(locale)) {
            return true;
        }
        Iterator<Locale> it = hashSet.iterator();
        while (it.hasNext()) {
            if (LocaleList.matchesLanguageAndScript(it.next(), locale)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasInstallerInfo(Context context, String str) {
        try {
            return context.getPackageManager().getInstallSourceInfo(str) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "Installer info not found for: " + str);
            return false;
        }
    }

    private static boolean isSystemApp(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfoAsUser(str, 0, context.getUserId()).isSystemApp();
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "Application info not found for: " + str);
            return false;
        }
    }

    public static class AppLocaleResult {
        public HashSet<Locale> mAppSupportedLocales;
        LocaleStatus mLocaleStatus;

        public enum LocaleStatus {
            UNKNOWN_FAILURE,
            NO_SUPPORTED_LANGUAGE_IN_APP,
            ASSET_LOCALE_IS_EMPTY,
            GET_SUPPORTED_LANGUAGE_FROM_LOCAL_CONFIG,
            GET_SUPPORTED_LANGUAGE_FROM_ASSET
        }

        public AppLocaleResult(LocaleStatus localeStatus, HashSet<Locale> hashSet) {
            this.mLocaleStatus = localeStatus;
            this.mAppSupportedLocales = hashSet;
        }
    }
}
