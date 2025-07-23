package com.android.internal.app;

import android.app.LocaleManager;
import android.content.Context;
import android.os.LocaleList;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telecom.Logging.Session;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.R;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.internal.content.NativeLibraryHelper;
import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IllformedLocaleException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes5.dex */
public class LocaleStore {
    private static final String COUNTRY_NAME_CHINESE = "CN";
    private static final int DEFAULT_SUPPORTED_LOCALE = 0;
    private static final String DID_LOCALE = "en-DI";
    private static final int DID_SUPPORTED_LOCALE = 2;
    private static final String FULLNAME_CHINESE = "简体中文";
    private static final String FULLNAME_REGION_CHINESE = "中国大陆";
    private static final String FULLNAME_SERBIAN = "Srpski";
    private static final String LANGUAGE_NAME_CHINESE = "zh_CN_#Hans";
    private static final String LANGUAGE_NAME_SERBIAN = "sr";
    private static final String LANGUAGE_XML = "/system/csc/language.xml";
    private static final String LANGUAGE_XML_OMC_V1_DIR = "/data/omc/etc";
    private static final int MODIFY_CHINA_DISPLAY_NAME = 0;
    private static final String SHOW_DESIGN_ID_LOCALE = "show_text_id";
    private static final int SHOW_DESIGN_ID_LOCALE_OFF = 0;
    private static final int SHOW_DESIGN_ID_LOCALE_ON = 1;
    private static final int SPECIFIC_SUPPORTED_LOCALE = 1;
    private static final String TAG = "LocaleStore";
    private static final String TAG_DISPLAY = "Display";
    private static final String TAG_LANGUAGE = "LanguageSet";
    private static final String TAG_NONSUGGESTED = "NonSuggested";
    private static final String TAG_NOT_DISPLAY = "NonDisplay";
    private static final String TAG_SUGGESTED = "Suggested";
    private static final int TIER_LANGUAGE = 1;
    private static final int TIER_NUMBERING = 3;
    private static final int TIER_REGION = 2;
    private static final int XML_LOCALES_INDEX_NO_SUGGESTED = 3;
    private static final int XML_LOCALES_INDEX_SUGGESTED = 2;
    private static final int XML_LOCALES_INDEX_SUPPORTED = 0;
    private static final int XML_LOCALES_INDEX_UNSUPPORTED = 1;
    private static final int XML_LOCALES_SIZE = 4;
    private static boolean sCountryMode = false;
    private static boolean sFullyInitialized = false;
    private static final ConcurrentHashMap<String, LocaleInfo> sLocaleCache = new ConcurrentHashMap<>();
    private static volatile int sPreIsDIDLocaleOn = 0;
    private static volatile LocaleList sPrevDefaultLocaleList;
    private static final String sPriorityLocale;

    static {
        sPriorityLocale = isChina() ? "zh" : "";
        sPrevDefaultLocaleList = null;
        sCountryMode = false;
    }

    public static class LocaleInfo implements Serializable {
        public static final int SUGGESTION_TYPE_CFG = 2;
        public static final int SUGGESTION_TYPE_CURRENT = 4;
        public static final int SUGGESTION_TYPE_IME_LANGUAGE = 32;
        public static final int SUGGESTION_TYPE_NONE = 0;
        public static final int SUGGESTION_TYPE_OTHER_APP_LANGUAGE = 16;
        public static final int SUGGESTION_TYPE_SEC = 16;
        public static final int SUGGESTION_TYPE_SIM = 1;
        public static final int SUGGESTION_TYPE_SYSTEM_AVAILABLE_LANGUAGE = 64;
        public static final int SUGGESTION_TYPE_SYSTEM_LANGUAGE = 8;
        public static final int SUGGESTION_TYPE_XML = 32;
        private String mFullCountryNameNative;
        private String mFullNameNative;
        private boolean mHasNumberingSystems;
        private final String mId;
        private boolean mIsChecked;
        private boolean mIsPriorityLocale;
        private boolean mIsPseudo;
        private boolean mIsSelected;
        private boolean mIsTranslated;
        private String mLangScriptKey;
        public final Locale mLocale;
        private final Locale mParent;
        public int mSuggestionFlags;

        @Retention(RetentionPolicy.SOURCE)
        public @interface SuggestionType {
        }

        private LocaleInfo(Locale locale) {
            this.mLocale = locale;
            String languageTag = locale.toLanguageTag();
            this.mId = languageTag;
            this.mParent = getParent(locale);
            this.mHasNumberingSystems = false;
            this.mIsChecked = false;
            this.mSuggestionFlags = 0;
            this.mIsTranslated = false;
            this.mIsPseudo = false;
            this.mIsSelected = false;
            if (languageTag != null) {
                this.mIsPriorityLocale = languageTag.startsWith(LocaleStore.sPriorityLocale);
            }
        }

        private LocaleInfo(String str) {
            this(Locale.forLanguageTag(str));
        }

        private LocaleInfo(LocaleInfo localeInfo) {
            this.mLocale = localeInfo.getLocale();
            this.mId = localeInfo.getId();
            this.mParent = localeInfo.getParent();
            this.mHasNumberingSystems = localeInfo.mHasNumberingSystems;
            this.mIsChecked = localeInfo.getChecked();
            this.mSuggestionFlags = localeInfo.mSuggestionFlags;
            this.mIsTranslated = localeInfo.isTranslated();
            this.mIsPseudo = localeInfo.mIsPseudo;
        }

        private static Locale getParent(Locale locale) {
            if (locale.getCountry().isEmpty()) {
                return null;
            }
            return new Locale.Builder().setLocale(locale).setRegion("").setExtension('u', "").build();
        }

        public boolean hasNumberingSystems() {
            return this.mHasNumberingSystems;
        }

        public String toString() {
            return this.mId;
        }

        public Locale getLocale() {
            return this.mLocale;
        }

        public Locale getParent() {
            return this.mParent;
        }

        public String getId() {
            return this.mId;
        }

        public boolean isTranslated() {
            return this.mIsTranslated;
        }

        public void setTranslated(boolean z) {
            this.mIsTranslated = z;
        }

        public boolean isSuggested() {
            if (!this.mIsTranslated) {
                return false;
            }
            if (LocaleStore.sCountryMode) {
                return this.mSuggestionFlags != 0;
            }
            int i = this.mSuggestionFlags;
            return (i == 0 || i == 16 || i == 18) ? false : true;
        }

        boolean isSecSuggested() {
            return ((this.mSuggestionFlags & 16) == 0 || isSuggested()) ? false : true;
        }

        boolean isSecXmlSuggested() {
            return (this.mSuggestionFlags & 32) != 0;
        }

        boolean isPriorityLocale() {
            return this.mIsPriorityLocale;
        }

        public boolean isSuggestionOfType(int i) {
            return this.mIsTranslated && (this.mSuggestionFlags & i) == i;
        }

        public void extendSuggestionOfType(int i) {
            if (this.mIsTranslated) {
                this.mSuggestionFlags = i | this.mSuggestionFlags;
            }
        }

        public String getSecFullNameNative() {
            String locale = this.mLocale.toString();
            String country = this.mLocale.getCountry();
            if (!LocaleStore.isChina() && LocaleStore.LANGUAGE_NAME_CHINESE.equals(locale) && LocaleStore.COUNTRY_NAME_CHINESE.equals(country)) {
                return "简体中文(中国大陆)";
            }
            return getFullNameNative();
        }

        public String getFullNameNative() {
            if (this.mFullNameNative == null) {
                if (LocaleStore.LANGUAGE_NAME_SERBIAN.equals(this.mLocale.toString())) {
                    this.mFullNameNative = LocaleStore.FULLNAME_SERBIAN;
                } else {
                    Locale localeWithOnlyNumberingSystem = LocaleStore.getLocaleWithOnlyNumberingSystem(this.mLocale);
                    this.mFullNameNative = LocaleHelper.getDisplayName(localeWithOnlyNumberingSystem, localeWithOnlyNumberingSystem, true);
                }
            }
            return this.mFullNameNative;
        }

        public String getFullNameNative(Context context) {
            String fullNameFromSpecialLocale = getFullNameFromSpecialLocale(context);
            return !fullNameFromSpecialLocale.isEmpty() ? fullNameFromSpecialLocale : getFullNameNative();
        }

        public String getFullCountryNameNative() {
            if (this.mFullCountryNameNative == null) {
                Locale locale = this.mLocale;
                this.mFullCountryNameNative = LocaleHelper.getDisplayCountry(locale, locale);
            }
            return this.mFullCountryNameNative;
        }

        String getFullCountryNameInUiLanguage() {
            return LocaleHelper.getDisplayCountry(this.mLocale);
        }

        public String getFullNameInUiLanguage() {
            return LocaleHelper.getDisplayName(this.mLocale.stripExtensions(), true);
        }

        public String getFullNameInUiLanguage(Context context) {
            String fullNameFromSpecialLocale = getFullNameFromSpecialLocale(context);
            return !fullNameFromSpecialLocale.isEmpty() ? fullNameFromSpecialLocale : getFullNameInUiLanguage();
        }

        private String getFullNameFromSpecialLocale(Context context) {
            String[] stringArray = context.getResources().getStringArray(R.array.special_locale_codes);
            String[] stringArray2 = context.getResources().getStringArray(R.array.special_locale_names);
            String locale = this.mLocale.toString();
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i].equals(locale)) {
                    return stringArray2[i];
                }
            }
            return "";
        }

        public boolean getSelected() {
            return this.mIsSelected;
        }

        public void setSelected(boolean z) {
            this.mIsSelected = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getLangScriptKey() {
            String languageTag;
            if (this.mLangScriptKey == null) {
                new Locale.Builder().setLocale(this.mLocale).setExtension('u', "").build();
                Locale parent = getParent(LocaleHelper.addLikelySubtags(this.mLocale));
                if (parent == null) {
                    languageTag = this.mLocale.toLanguageTag();
                } else {
                    languageTag = parent.toLanguageTag();
                }
                this.mLangScriptKey = languageTag;
            }
            return this.mLangScriptKey;
        }

        String getLabel(boolean z) {
            return getLabel(z, 0);
        }

        String getLabel(boolean z, int i) {
            if ((i & 1) != 0 && !LocaleStore.isChina() && z && LocaleStore.LANGUAGE_NAME_CHINESE.equals(this.mLocale.toString()) && LocaleStore.COUNTRY_NAME_CHINESE.equals(this.mLocale.getCountry())) {
                return LocaleStore.FULLNAME_REGION_CHINESE;
            }
            return z ? getFullCountryNameNative() : getFullNameNative();
        }

        String getNumberingSystem() {
            Locale locale = this.mLocale;
            return LocaleHelper.getDisplayNumberingSystemKeyValue(locale, locale);
        }

        String getContentDescription(boolean z) {
            if (z) {
                return getFullCountryNameInUiLanguage();
            }
            return getFullNameInUiLanguage();
        }

        public boolean getChecked() {
            return this.mIsChecked;
        }

        public void setChecked(boolean z) {
            this.mIsChecked = z;
        }

        public boolean isAppCurrentLocale() {
            return (this.mSuggestionFlags & 4) > 0;
        }

        public boolean isSystemLocale() {
            return (this.mSuggestionFlags & 8) > 0;
        }

        public boolean isInCurrentSystemLocales() {
            return (this.mSuggestionFlags & 64) > 0;
        }
    }

    private static Set<String> getSimCountries(Context context) {
        HashSet hashSet = new HashSet();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (telephonyManager != null) {
            String upperCase = telephonyManager.getSimCountryIso().toUpperCase(Locale.US);
            if (!upperCase.isEmpty()) {
                hashSet.add(upperCase);
            }
            String upperCase2 = telephonyManager.getNetworkCountryIso().toUpperCase(Locale.US);
            if (!upperCase2.isEmpty()) {
                hashSet.add(upperCase2);
            }
        }
        return hashSet;
    }

    public static void updateSimCountries(Context context) {
        Set<String> simCountries = getSimCountries(context);
        for (LocaleInfo localeInfo : sLocaleCache.values()) {
            if (simCountries.contains(localeInfo.getLocale().getCountry())) {
                localeInfo.mSuggestionFlags |= 1;
            }
        }
    }

    public static LocaleInfo getAppActivatedLocaleInfo(Context context, String str, boolean z) {
        LocaleList applicationLocales;
        if (str == null) {
            return null;
        }
        LocaleManager localeManager = (LocaleManager) context.getSystemService(LocaleManager.class);
        if (localeManager == null) {
            applicationLocales = null;
        } else {
            try {
                applicationLocales = localeManager.getApplicationLocales(str);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, "IllegalArgumentException ", e);
            }
        }
        Locale locale = applicationLocales == null ? null : applicationLocales.get(0);
        if (locale != null) {
            LocaleInfo localeInfo = new LocaleInfo(getLocaleInfo(locale, sLocaleCache));
            if (z) {
                localeInfo.mSuggestionFlags |= 4;
                return localeInfo;
            }
            localeInfo.mSuggestionFlags |= 16;
            return localeInfo;
        }
        return null;
    }

    public static Set<LocaleInfo> transformImeLanguageTagToLocaleInfo(List<InputMethodSubtype> list) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Iterator<InputMethodSubtype> it = list.iterator();
        while (it.hasNext()) {
            String languageTag = it.next().getLanguageTag();
            if (!hashSet2.contains(languageTag)) {
                hashSet2.add(languageTag);
                LocaleInfo localeInfo = new LocaleInfo(getLocaleInfo(Locale.forLanguageTag(languageTag), sLocaleCache));
                localeInfo.mSuggestionFlags |= 32;
                hashSet.add(localeInfo);
            }
        }
        return hashSet;
    }

    public static Set<LocaleInfo> getSystemCurrentLocales() {
        HashSet hashSet = new HashSet();
        LocaleList localeList = LocaleList.getDefault();
        for (int i = 0; i < localeList.size(); i++) {
            LocaleInfo localeInfo = new LocaleInfo(getLocaleInfo(getLocaleWithOnlyNumberingSystem(localeList.get(i)), sLocaleCache));
            localeInfo.mSuggestionFlags |= 64;
            hashSet.add(localeInfo);
        }
        return hashSet;
    }

    public static LocaleInfo getSystemDefaultLocaleInfo(boolean z) {
        LocaleInfo localeInfo = new LocaleInfo("");
        localeInfo.mSuggestionFlags |= 8;
        if (z) {
            localeInfo.mSuggestionFlags |= 4;
        }
        localeInfo.mIsTranslated = true;
        return localeInfo;
    }

    private static void addSuggestedLocalesForRegion(Locale locale) {
        if (locale == null) {
            return;
        }
        String country = locale.getCountry();
        if (country.isEmpty()) {
            return;
        }
        for (LocaleInfo localeInfo : sLocaleCache.values()) {
            if (country.equals(localeInfo.getLocale().getCountry())) {
                localeInfo.mSuggestionFlags |= 1;
            }
        }
    }

    public static void fillCache(Context context) {
        fillCacheManaged(context, true);
    }

    private static /* synthetic */ void lambda$fillCache$0(LocaleInfo localeInfo, Locale locale) {
        if (localeInfo.getLocale().stripExtensions().equals(locale.stripExtensions())) {
            localeInfo.mHasNumberingSystems = true;
        }
    }

    public static void fillCacheManaged(Context context, boolean z) {
        String str;
        LocaleInfo localeInfo;
        int i = Settings.System.getInt(context.getContentResolver(), SHOW_DESIGN_ID_LOCALE, 0);
        if (sPreIsDIDLocaleOn == i && sPrevDefaultLocaleList != null && sPrevDefaultLocaleList.equals(LocaleList.getDefault())) {
            return;
        }
        sPreIsDIDLocaleOn = i;
        String str2 = SystemProperties.get("persist.sys.omc_path", LANGUAGE_XML_OMC_V1_DIR) + "/language.xml";
        String str3 = SystemProperties.get("persist.sys.omc_etcpath", LANGUAGE_XML_OMC_V1_DIR) + "/language.xml";
        if (!new File(str2).exists()) {
            str2 = new File(str3).exists() ? str3 : LANGUAGE_XML;
        }
        String[] localeListFromXML = getLocaleListFromXML(str2);
        if (localeListFromXML == null) {
            str = "";
        } else {
            str = localeListFromXML[3];
        }
        buildLocaleCache(context, localeListFromXML, 0);
        buildLocaleCache(context, localeListFromXML, 1);
        buildLocaleCache(context, localeListFromXML, 2);
        HashSet hashSet = new HashSet();
        for (String str4 : LocalePicker.getSystemAssetLocales()) {
            LocaleInfo localeInfo2 = null;
            LocaleInfo localeInfo3 = new LocaleInfo(str4);
            LocaleInfo localeInfo4 = new LocaleInfo(str4 + "-u-nu-latn");
            String country = localeInfo3.getLocale().getCountry();
            if (!country.isEmpty()) {
                ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
                if (concurrentHashMap.containsKey(localeInfo3.getId())) {
                    localeInfo = concurrentHashMap.get(localeInfo3.getId());
                } else {
                    String str5 = localeInfo3.getLangScriptKey() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + country;
                    localeInfo = concurrentHashMap.containsKey(str5) ? concurrentHashMap.get(str5) : null;
                }
                if (concurrentHashMap.containsKey(localeInfo4.getId())) {
                    localeInfo2 = concurrentHashMap.get(localeInfo4.getId());
                    localeInfo3.mHasNumberingSystems = true;
                }
                if (localeInfo != null) {
                    if (str.contains(localeInfo3.toString())) {
                        localeInfo.mSuggestionFlags = 0;
                    } else {
                        localeInfo.mSuggestionFlags |= 2;
                        localeInfo.mSuggestionFlags |= 16;
                    }
                    if (DID_LOCALE.equals(localeInfo3.getId())) {
                        if (sPreIsDIDLocaleOn == 0) {
                            concurrentHashMap.remove(localeInfo3.getId());
                        } else {
                            localeInfo.mSuggestionFlags = 0;
                        }
                    }
                }
                if (localeInfo2 != null) {
                    if (str.contains(localeInfo3.toString())) {
                        localeInfo2.mSuggestionFlags = 0;
                    } else {
                        localeInfo2.mSuggestionFlags |= 2;
                        localeInfo2.mSuggestionFlags |= 16;
                    }
                }
            }
            if (z) {
                hashSet.add(localeInfo3.getLangScriptKey());
            }
        }
        if (z) {
            for (LocaleInfo localeInfo5 : sLocaleCache.values()) {
                localeInfo5.setTranslated(hashSet.contains(localeInfo5.getLangScriptKey()));
            }
            addSuggestedLocalesForRegion(Locale.getDefault());
            sPrevDefaultLocaleList = LocaleList.getDefault();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ac A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void buildLocaleCache(android.content.Context r12, java.lang.String[] r13, int r14) {
        /*
            r0 = 0
            r1 = 2
            r2 = 1
            java.lang.String r3 = ""
            if (r14 == 0) goto L1a
            if (r14 == r2) goto L15
            if (r14 == r1) goto L10
            java.lang.String[] r4 = new java.lang.String[r2]
            r4[r0] = r3
            goto L1e
        L10:
            java.lang.String[] r4 = com.android.internal.app.LocalePicker.getDIDLocale(r12)
            goto L1e
        L15:
            java.lang.String[] r4 = com.android.internal.app.LocalePicker.getSpecificCustomerSupportedLocales(r12)
            goto L1e
        L1a:
            java.lang.String[] r4 = com.android.internal.app.LocalePicker.getSupportedLocales(r12)
        L1e:
            if (r14 == r1) goto L29
            if (r13 == 0) goto L29
            r3 = r13[r0]
            r5 = r13[r2]
            r13 = r13[r1]
            goto L2b
        L29:
            r13 = r3
            r5 = r13
        L2b:
            java.util.Set r12 = getSimCountries(r12)
            int r6 = r4.length
        L30:
            if (r0 >= r6) goto Lb7
            r7 = r4[r0]
            boolean r8 = r7.isEmpty()
            if (r8 != 0) goto Laf
            com.android.internal.app.LocaleStore$LocaleInfo r8 = new com.android.internal.app.LocaleStore$LocaleInfo
            r9 = 0
            r8.<init>(r7)
            java.lang.String r7 = r8.toString()
            if (r7 != 0) goto L47
            goto Lac
        L47:
            if (r14 != r2) goto L50
            boolean r10 = r3.contains(r7)
            if (r10 != 0) goto L71
            goto Lac
        L50:
            if (r14 != 0) goto L6a
            boolean r10 = r13.contains(r7)
            if (r10 == 0) goto L63
            int r10 = r8.mSuggestionFlags
            r10 = r10 | r2
            r8.mSuggestionFlags = r10
            int r10 = r8.mSuggestionFlags
            r10 = r10 | 32
            r8.mSuggestionFlags = r10
        L63:
            boolean r10 = r5.contains(r7)
            if (r10 == 0) goto L71
            goto Lac
        L6a:
            if (r14 != r1) goto L71
            int r10 = com.android.internal.app.LocaleStore.sPreIsDIDLocaleOn
            if (r10 != 0) goto L71
            goto Lac
        L71:
            java.util.Locale r10 = r8.getLocale()
            java.lang.String r10 = r10.getCountry()
            boolean r10 = r12.contains(r10)
            if (r10 == 0) goto L84
            int r10 = r8.mSuggestionFlags
            r10 = r10 | r2
            r8.mSuggestionFlags = r10
        L84:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.android.internal.app.LocaleStore$LocaleInfo> r10 = com.android.internal.app.LocaleStore.sLocaleCache
            r10.put(r7, r8)
            java.util.Locale r7 = r8.getParent()
            if (r7 == 0) goto Lac
            java.lang.String r8 = r7.toLanguageTag()
            if (r8 == 0) goto La4
            boolean r11 = r10.containsKey(r8)
            if (r11 != 0) goto Lac
            com.android.internal.app.LocaleStore$LocaleInfo r11 = new com.android.internal.app.LocaleStore$LocaleInfo
            r11.<init>(r7)
            r10.put(r8, r11)
            goto Lac
        La4:
            java.lang.String r7 = "LocaleStore"
            java.lang.String r8 = "put null key to sLocaleCache #2"
            android.util.Log.d(r7, r8)
        Lac:
            int r0 = r0 + 1
            goto L30
        Laf:
            java.util.IllformedLocaleException r12 = new java.util.IllformedLocaleException
            java.lang.String r13 = "Bad locale entry in locale_config.xml"
            r12.<init>(r13)
            throw r12
        Lb7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.LocaleStore.buildLocaleCache(android.content.Context, java.lang.String[], int):void");
    }

    private static boolean isShallIgnore(Set<String> set, final LocaleInfo localeInfo, boolean z) {
        if (set.stream().anyMatch(new Predicate() { // from class: com.android.internal.app.LocaleStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = Locale.forLanguageTag((String) obj).stripExtensions().equals(LocaleStore.LocaleInfo.this.getLocale().stripExtensions());
                return equals;
            }
        })) {
            return true;
        }
        if (localeInfo.mIsPseudo) {
            return false;
        }
        return (z && !localeInfo.isTranslated()) || localeInfo.getParent() == null;
    }

    private static int getLocaleTier(LocaleInfo localeInfo) {
        if (localeInfo == null) {
            return 1;
        }
        return localeInfo.getLocale().getCountry().isEmpty() ? 2 : 3;
    }

    public static Set<LocaleInfo> getLevelLocales(Context context, Set<String> set, LocaleInfo localeInfo, boolean z) {
        return getLevelLocales(context, set, localeInfo, z, null);
    }

    public static Set<LocaleInfo> getLevelLocales(Context context, Set<String> set, LocaleInfo localeInfo, boolean z, LocaleList localeList) {
        ConcurrentHashMap<String, LocaleInfo> convertExplicitLocales;
        sCountryMode = localeInfo != null;
        if (context != null) {
            fillCache(context);
        }
        if (localeInfo != null) {
            localeInfo.getId();
        }
        new HashSet();
        if (localeList == null) {
            convertExplicitLocales = sLocaleCache;
        } else {
            convertExplicitLocales = convertExplicitLocales(localeList, sLocaleCache.values());
        }
        return getTierLocales(set, localeInfo, z, convertExplicitLocales);
    }

    private static Set<LocaleInfo> getTierLocales(Set<String> set, LocaleInfo localeInfo, boolean z, ConcurrentHashMap<String, LocaleInfo> concurrentHashMap) {
        String id = localeInfo != null ? localeInfo.getId() : null;
        HashSet hashSet = new HashSet();
        for (LocaleInfo localeInfo2 : concurrentHashMap.values()) {
            if (!isShallIgnore(set, localeInfo2, z)) {
                int localeTier = getLocaleTier(localeInfo);
                if (localeTier != 1) {
                    if (localeTier == 2) {
                        if (id.equals(localeInfo2.getParent().toLanguageTag())) {
                            hashSet.add(localeInfo2);
                        }
                    } else if (localeTier == 3 && localeInfo.getLocale().stripExtensions().equals(localeInfo2.getLocale().stripExtensions())) {
                        hashSet.add(localeInfo2);
                    }
                } else if (localeInfo2.isSuggestionOfType(1)) {
                    hashSet.add(localeInfo2);
                } else {
                    LocaleInfo localeInfo3 = getLocaleInfo(localeInfo2.getParent(), concurrentHashMap);
                    if (localeInfo3 != null) {
                        if (localeInfo2.isSuggestionOfType(16)) {
                            localeInfo3.mSuggestionFlags |= 16;
                        }
                        hashSet.add(localeInfo3);
                    }
                }
            }
        }
        return hashSet;
    }

    public static ConcurrentHashMap<String, LocaleInfo> convertExplicitLocales(LocaleList localeList, Collection<LocaleInfo> collection) {
        LocaleList matchLocaleFromSupportedLocaleList = matchLocaleFromSupportedLocaleList(localeList, collection);
        ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < matchLocaleFromSupportedLocaleList.size(); i++) {
            Locale locale = matchLocaleFromSupportedLocaleList.get(i);
            if (locale.toString().isEmpty()) {
                throw new IllformedLocaleException("Bad locale entry");
            }
            LocaleInfo localeInfo = new LocaleInfo(locale);
            if (!concurrentHashMap.containsKey(localeInfo.getId())) {
                concurrentHashMap.put(localeInfo.getId(), localeInfo);
                Locale parent = localeInfo.getParent();
                if (parent != null) {
                    String languageTag = parent.toLanguageTag();
                    if (!concurrentHashMap.containsKey(languageTag)) {
                        concurrentHashMap.put(languageTag, new LocaleInfo(parent));
                    }
                }
            }
        }
        return concurrentHashMap;
    }

    private static LocaleList matchLocaleFromSupportedLocaleList(LocaleList localeList, Collection<LocaleInfo> collection) {
        if (collection == null) {
            return localeList;
        }
        Locale[] localeArr = new Locale[localeList.size()];
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            if (!TextUtils.isEmpty(locale.getCountry())) {
                Iterator<LocaleInfo> it = collection.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LocaleInfo next = it.next();
                    if (LocaleList.matchesLanguageAndScript(locale, next.getLocale()) && TextUtils.equals(locale.getCountry(), next.getLocale().getCountry())) {
                        localeArr[i] = next.getLocale();
                        break;
                    }
                }
            }
            if (localeArr[i] == null) {
                localeArr[i] = locale;
            }
        }
        return new LocaleList(localeArr);
    }

    public static LocaleInfo getLocaleInfo(Locale locale) {
        ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
        LocaleInfo localeInfo = getLocaleInfo(locale, concurrentHashMap);
        addLocaleInfoToMap(locale, localeInfo, concurrentHashMap);
        return localeInfo;
    }

    private static LocaleInfo getLocaleInfo(Locale locale, ConcurrentHashMap<String, LocaleInfo> concurrentHashMap) {
        String languageTag = locale.toLanguageTag();
        if (!concurrentHashMap.containsKey(languageTag)) {
            Locale localeWithOnlyNumberingSystem = getLocaleWithOnlyNumberingSystem(locale);
            if (concurrentHashMap.containsKey(localeWithOnlyNumberingSystem.toLanguageTag())) {
                LocaleInfo localeInfo = new LocaleInfo(locale);
                LocaleInfo localeInfo2 = concurrentHashMap.get(localeWithOnlyNumberingSystem.toLanguageTag());
                localeInfo.mIsPseudo = localeInfo2.mIsPseudo;
                localeInfo.mIsTranslated = localeInfo2.mIsTranslated;
                localeInfo.mHasNumberingSystems = localeInfo2.mHasNumberingSystems;
                localeInfo.mSuggestionFlags = localeInfo2.mSuggestionFlags;
                return localeInfo;
            }
            return new LocaleInfo(locale);
        }
        return concurrentHashMap.get(languageTag);
    }

    public static List<LocalePicker.LocaleInfo> getAllLocaleInfos(Context context) {
        Locale forLanguageTag;
        fillCacheManaged(context, false);
        ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
        ArrayList arrayList = new ArrayList(concurrentHashMap.size());
        for (LocaleInfo localeInfo : concurrentHashMap.values()) {
            if ((localeInfo.mSuggestionFlags & 16) != 0 && localeInfo.getParent() != null && (forLanguageTag = Locale.forLanguageTag(localeInfo.toString())) != null) {
                arrayList.add(new LocalePicker.LocaleInfo(toTitleCase(forLanguageTag.getDisplayName(forLanguageTag)), forLanguageTag));
            }
        }
        arrayList.trimToSize();
        if (!sPriorityLocale.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                LocalePicker.LocaleInfo localeInfo2 = (LocalePicker.LocaleInfo) it.next();
                if (isConfigLocale(sPriorityLocale, localeInfo2.getLocale())) {
                    arrayList2.add(localeInfo2);
                } else {
                    arrayList3.add(localeInfo2);
                }
            }
            Collections.sort(arrayList2);
            Collections.sort(arrayList3);
            arrayList2.addAll(arrayList3);
            return arrayList2;
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static boolean isConfigLocale(String str, Locale locale) {
        return str.equals(locale.getLanguage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isChina() {
        return "CHINA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
    }

    private static String toTitleCase(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0038 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String[] getLocaleListFromXML(java.lang.String r5) {
        /*
            java.lang.String r0 = "LocaleStore"
            r1 = 0
            javax.xml.parsers.DocumentBuilderFactory r2 = javax.xml.parsers.DocumentBuilderFactory.newInstance()     // Catch: java.io.IOException -> L1b org.xml.sax.SAXException -> L24 javax.xml.parsers.ParserConfigurationException -> L2d
            javax.xml.parsers.DocumentBuilder r2 = r2.newDocumentBuilder()     // Catch: java.io.IOException -> L1b org.xml.sax.SAXException -> L24 javax.xml.parsers.ParserConfigurationException -> L2d
            java.io.File r3 = new java.io.File     // Catch: java.io.IOException -> L1b org.xml.sax.SAXException -> L24 javax.xml.parsers.ParserConfigurationException -> L2d
            r3.<init>(r5)     // Catch: java.io.IOException -> L1b org.xml.sax.SAXException -> L24 javax.xml.parsers.ParserConfigurationException -> L2d
            org.w3c.dom.Document r5 = r2.parse(r3)     // Catch: java.io.IOException -> L1b org.xml.sax.SAXException -> L24 javax.xml.parsers.ParserConfigurationException -> L2d
            if (r5 == 0) goto L35
            org.w3c.dom.Element r5 = r5.getDocumentElement()     // Catch: java.io.IOException -> L1b org.xml.sax.SAXException -> L24 javax.xml.parsers.ParserConfigurationException -> L2d
            goto L36
        L1b:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r0, r5)
            goto L35
        L24:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r0, r5)
            goto L35
        L2d:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r0, r5)
        L35:
            r5 = r1
        L36:
            if (r5 != 0) goto L39
            return r1
        L39:
            java.lang.String r0 = "Display"
            java.lang.String r1 = "LanguageSet"
            java.lang.String[] r0 = new java.lang.String[]{r1, r0}
            java.lang.String r2 = "NonDisplay"
            java.lang.String[] r2 = new java.lang.String[]{r1, r2}
            java.lang.String r3 = "Suggested"
            java.lang.String[] r3 = new java.lang.String[]{r1, r3}
            java.lang.String r4 = "NonSuggested"
            java.lang.String[] r1 = new java.lang.String[]{r1, r4}
            java.lang.String r0 = findTagValue(r0, r5)
            java.lang.String r2 = findTagValue(r2, r5)
            java.lang.String r3 = findTagValue(r3, r5)
            java.lang.String r5 = findTagValue(r1, r5)
            java.lang.String[] r5 = new java.lang.String[]{r0, r2, r3, r5}
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.LocaleStore.getLocaleListFromXML(java.lang.String):java.lang.String[]");
    }

    private static String findTagValue(String[] strArr, Node node) {
        String str;
        NodeList childNodes;
        for (String str2 : strArr) {
            if (node != null && (childNodes = node.getChildNodes()) != null) {
                int length = childNodes.getLength();
                for (int i = 0; i < length; i++) {
                    Node item = childNodes.item(i);
                    if (item != null && str2.equals(item.getNodeName())) {
                        node = item;
                    }
                }
            }
        }
        if (node == null || !strArr[strArr.length - 1].equals(node.getNodeName())) {
            return "";
        }
        Node firstChild = node.getFirstChild();
        if (firstChild == null) {
            str = "";
        } else {
            str = firstChild.getNodeValue();
        }
        return str.replaceAll("\\s", "").replaceAll(Session.SESSION_SEPARATION_CHAR_CHILD, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Locale getLocaleWithOnlyNumberingSystem(Locale locale) {
        return new Locale.Builder().setLocale(locale.stripExtensions()).setUnicodeLocaleKeyword("nu", locale.getUnicodeLocaleType("nu")).build();
    }

    private static void addLocaleInfoToMap(Locale locale, LocaleInfo localeInfo, ConcurrentHashMap<String, LocaleInfo> concurrentHashMap) {
        if (concurrentHashMap.containsKey(locale.toLanguageTag()) || concurrentHashMap.containsKey(getLocaleWithOnlyNumberingSystem(locale).toLanguageTag())) {
            return;
        }
        concurrentHashMap.put(locale.toLanguageTag(), localeInfo);
    }

    public static LocaleInfo fromLocale(Locale locale) {
        return new LocaleInfo(locale);
    }
}
