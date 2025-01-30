package com.android.internal.app;

import android.app.LocaleManager;
import android.content.Context;
import android.p009os.LocaleList;
import android.p009os.SystemProperties;
import android.provider.Settings;
import android.telecom.Logging.Session;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.C4337R;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.internal.content.NativeLibraryHelper;
import java.io.File;
import java.io.IOException;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
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
    private static boolean sCountryMode;
    private static volatile LocaleList sPrevDefaultLocaleList;
    private static final String sPriorityLocale;
    private static boolean sFullyInitialized = false;
    private static final String TAG = LocaleStore.class.getSimpleName();
    private static final ConcurrentHashMap<String, LocaleInfo> sLocaleCache = new ConcurrentHashMap<>();
    private static volatile int sPreIsDIDLocaleOn = 0;

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
        private static final int SUGGESTION_TYPE_OTHER_APP_LANGUAGE = 16;
        private static final int SUGGESTION_TYPE_SEC = 16;
        public static final int SUGGESTION_TYPE_SIM = 1;
        public static final int SUGGESTION_TYPE_SYSTEM_AVAILABLE_LANGUAGE = 64;
        public static final int SUGGESTION_TYPE_SYSTEM_LANGUAGE = 8;
        private static final int SUGGESTION_TYPE_XML = 32;
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
        private final Locale mLocale;
        private final Locale mParent;
        private int mSuggestionFlags;

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

        private LocaleInfo(String localeId) {
            this(Locale.forLanguageTag(localeId));
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

        public void setTranslated(boolean isTranslated) {
            this.mIsTranslated = isTranslated;
        }

        boolean isSuggested() {
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

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isSuggestionOfType(int suggestionMask) {
            return this.mIsTranslated && (this.mSuggestionFlags & suggestionMask) == suggestionMask;
        }

        public void extendSuggestionOfType(int suggestionMask) {
            if (!this.mIsTranslated) {
                return;
            }
            this.mSuggestionFlags |= suggestionMask;
        }

        public String getSecFullNameNative() {
            String id = this.mLocale.toString();
            String country = this.mLocale.getCountry();
            if (!LocaleStore.isChina() && LocaleStore.LANGUAGE_NAME_CHINESE.equals(id) && LocaleStore.COUNTRY_NAME_CHINESE.equals(country)) {
                return "简体中文(中国大陆)";
            }
            return getFullNameNative();
        }

        public String getFullNameNative() {
            if (this.mFullNameNative == null) {
                String id = this.mLocale.toString();
                if (LocaleStore.LANGUAGE_NAME_SERBIAN.equals(id)) {
                    this.mFullNameNative = LocaleStore.FULLNAME_SERBIAN;
                } else {
                    Locale locale = LocaleStore.getLocaleWithOnlyNumberingSystem(this.mLocale);
                    this.mFullNameNative = LocaleHelper.getDisplayName(locale, locale, true);
                }
            }
            String id2 = this.mFullNameNative;
            return id2;
        }

        public String getFullNameNative(Context context) {
            String specialName = getFullNameFromSpecialLocale(context);
            return !specialName.isEmpty() ? specialName : getFullNameNative();
        }

        String getFullCountryNameNative() {
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
            Locale locale = this.mLocale.stripExtensions();
            return LocaleHelper.getDisplayName(locale, true);
        }

        public String getFullNameInUiLanguage(Context context) {
            String specialName = getFullNameFromSpecialLocale(context);
            return !specialName.isEmpty() ? specialName : getFullNameInUiLanguage();
        }

        private String getFullNameFromSpecialLocale(Context context) {
            String[] spLocaleCodes = context.getResources().getStringArray(C4337R.array.special_locale_codes);
            String[] spLocaleNames = context.getResources().getStringArray(C4337R.array.special_locale_names);
            String id = this.mLocale.toString();
            for (int i = 0; i < spLocaleCodes.length; i++) {
                if (spLocaleCodes[i].equals(id)) {
                    return spLocaleNames[i];
                }
            }
            return "";
        }

        public boolean getSelected() {
            return this.mIsSelected;
        }

        public void setSelected(boolean selected) {
            this.mIsSelected = selected;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getLangScriptKey() {
            String languageTag;
            if (this.mLangScriptKey == null) {
                new Locale.Builder().setLocale(this.mLocale).setExtension('u', "").build();
                Locale parentWithScript = getParent(LocaleHelper.addLikelySubtags(this.mLocale));
                if (parentWithScript == null) {
                    languageTag = this.mLocale.toLanguageTag();
                } else {
                    languageTag = parentWithScript.toLanguageTag();
                }
                this.mLangScriptKey = languageTag;
            }
            return this.mLangScriptKey;
        }

        String getLabel(boolean countryMode) {
            return getLabel(countryMode, 0);
        }

        String getLabel(boolean countryMode, int changeDisplayName) {
            if ((changeDisplayName & 1) != 0 && !LocaleStore.isChina() && countryMode && LocaleStore.LANGUAGE_NAME_CHINESE.equals(this.mLocale.toString()) && LocaleStore.COUNTRY_NAME_CHINESE.equals(this.mLocale.getCountry())) {
                return LocaleStore.FULLNAME_REGION_CHINESE;
            }
            return countryMode ? getFullCountryNameNative() : getFullNameNative();
        }

        String getNumberingSystem() {
            Locale locale = this.mLocale;
            return LocaleHelper.getDisplayNumberingSystemKeyValue(locale, locale);
        }

        String getContentDescription(boolean countryMode) {
            if (countryMode) {
                return getFullCountryNameInUiLanguage();
            }
            return getFullNameInUiLanguage();
        }

        public boolean getChecked() {
            return this.mIsChecked;
        }

        public void setChecked(boolean checked) {
            this.mIsChecked = checked;
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
        Set<String> result = new HashSet<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (tm != null) {
            String iso = tm.getSimCountryIso().toUpperCase(Locale.US);
            if (!iso.isEmpty()) {
                result.add(iso);
            }
            String iso2 = tm.getNetworkCountryIso().toUpperCase(Locale.US);
            if (!iso2.isEmpty()) {
                result.add(iso2);
            }
        }
        return result;
    }

    public static void updateSimCountries(Context context) {
        Set<String> simCountries = getSimCountries(context);
        for (LocaleInfo li : sLocaleCache.values()) {
            if (simCountries.contains(li.getLocale().getCountry())) {
                li.mSuggestionFlags |= 1;
            }
        }
    }

    public static LocaleInfo getAppActivatedLocaleInfo(Context context, String appPackageName, boolean isAppSelected) {
        LocaleList localeList;
        if (appPackageName == null) {
            return null;
        }
        LocaleManager localeManager = (LocaleManager) context.getSystemService(LocaleManager.class);
        if (localeManager == null) {
            localeList = null;
        } else {
            try {
                localeList = localeManager.getApplicationLocales(appPackageName);
            } catch (IllegalArgumentException e) {
                Log.m95d(TAG, "IllegalArgumentException ", e);
            }
        }
        Locale locale = localeList == null ? null : localeList.get(0);
        if (locale != null) {
            LocaleInfo cacheInfo = getLocaleInfo(locale, sLocaleCache);
            LocaleInfo localeInfo = new LocaleInfo(cacheInfo);
            if (isAppSelected) {
                localeInfo.mSuggestionFlags |= 4;
            } else {
                localeInfo.mSuggestionFlags |= 16;
            }
            return localeInfo;
        }
        return null;
    }

    public static Set<LocaleInfo> transformImeLanguageTagToLocaleInfo(List<InputMethodSubtype> list) {
        Set<LocaleInfo> imeLocales = new HashSet<>();
        for (InputMethodSubtype subtype : list) {
            Locale locale = Locale.forLanguageTag(subtype.getLanguageTag());
            LocaleInfo cacheInfo = getLocaleInfo(locale, sLocaleCache);
            LocaleInfo localeInfo = new LocaleInfo(cacheInfo);
            localeInfo.mSuggestionFlags |= 32;
            imeLocales.add(localeInfo);
        }
        return imeLocales;
    }

    public static Set<LocaleInfo> getSystemCurrentLocales() {
        Set<LocaleInfo> localeList = new HashSet<>();
        LocaleList systemLangList = LocaleList.getDefault();
        for (int i = 0; i < systemLangList.size(); i++) {
            Locale sysLocale = getLocaleWithOnlyNumberingSystem(systemLangList.get(i));
            LocaleInfo cacheInfo = getLocaleInfo(sysLocale, sLocaleCache);
            LocaleInfo localeInfo = new LocaleInfo(cacheInfo);
            localeInfo.mSuggestionFlags |= 64;
            localeList.add(localeInfo);
        }
        return localeList;
    }

    public static LocaleInfo getSystemDefaultLocaleInfo(boolean hasAppLanguage) {
        LocaleInfo systemDefaultInfo = new LocaleInfo("");
        systemDefaultInfo.mSuggestionFlags |= 8;
        if (hasAppLanguage) {
            systemDefaultInfo.mSuggestionFlags |= 4;
        }
        systemDefaultInfo.mIsTranslated = true;
        return systemDefaultInfo;
    }

    private static void addSuggestedLocalesForRegion(Locale locale) {
        if (locale == null) {
            return;
        }
        String country = locale.getCountry();
        if (country.isEmpty()) {
            return;
        }
        for (LocaleInfo li : sLocaleCache.values()) {
            if (country.equals(li.getLocale().getCountry())) {
                li.mSuggestionFlags |= 1;
            }
        }
    }

    public static void fillCache(Context context) {
        fillCacheManaged(context, true);
    }

    private static /* synthetic */ void lambda$fillCache$0(LocaleInfo li, Locale l) {
        if (li.getLocale().stripExtensions().equals(l.stripExtensions())) {
            li.mHasNumberingSystems = true;
        }
    }

    private static boolean isShallIgnore(Set<String> ignorables, final LocaleInfo li, boolean translatedOnly) {
        if (ignorables.stream().anyMatch(new Predicate() { // from class: com.android.internal.app.LocaleStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = Locale.forLanguageTag((String) obj).stripExtensions().equals(LocaleStore.LocaleInfo.this.getLocale().stripExtensions());
                return equals;
            }
        })) {
            return true;
        }
        if (li.mIsPseudo) {
            return false;
        }
        return (translatedOnly && !li.isTranslated()) || li.getParent() == null;
    }

    private static int getLocaleTier(LocaleInfo parent) {
        if (parent == null) {
            return 1;
        }
        if (parent.getLocale().getCountry().isEmpty()) {
            return 2;
        }
        return 3;
    }

    public static void fillCacheManaged(Context context, boolean isInternalCalled) {
        int curDIDLocaleOn;
        String languageXmlPath;
        boolean z;
        char c;
        LocaleInfo cachedLocaleWithLatnExt;
        int curDIDLocaleOn2 = Settings.System.getInt(context.getContentResolver(), SHOW_DESIGN_ID_LOCALE, 0);
        if (sPreIsDIDLocaleOn == curDIDLocaleOn2 && sPrevDefaultLocaleList != null && sPrevDefaultLocaleList.equals(LocaleList.getDefault())) {
            return;
        }
        sPreIsDIDLocaleOn = curDIDLocaleOn2;
        String languageXmlPath2 = LANGUAGE_XML;
        String nosuggestedLocales = "";
        String omcV2Path = SystemProperties.get("persist.sys.omc_path", LANGUAGE_XML_OMC_V1_DIR) + "/language.xml";
        String omcV5Path = SystemProperties.get("persist.sys.omc_etcpath", LANGUAGE_XML_OMC_V1_DIR) + "/language.xml";
        if (new File(omcV2Path).exists()) {
            languageXmlPath2 = omcV2Path;
        } else if (new File(omcV5Path).exists()) {
            languageXmlPath2 = omcV5Path;
        }
        String[] xmlLocales = getLocaleListFromXML(languageXmlPath2);
        if (xmlLocales != null) {
            nosuggestedLocales = xmlLocales[3];
        }
        buildLocaleCache(context, xmlLocales, 0);
        buildLocaleCache(context, xmlLocales, 1);
        buildLocaleCache(context, xmlLocales, 2);
        HashSet<String> localizedLocales = new HashSet<>();
        String[] systemAssetLocales = LocalePicker.getSystemAssetLocales();
        int length = systemAssetLocales.length;
        int i = 0;
        while (i < length) {
            String localeId = systemAssetLocales[i];
            LocaleInfo li = new LocaleInfo(localeId);
            LocaleInfo liWithLatnExt = new LocaleInfo(localeId + "-u-nu-latn");
            String country = li.getLocale().getCountry();
            if (country.isEmpty()) {
                curDIDLocaleOn = curDIDLocaleOn2;
                languageXmlPath = languageXmlPath2;
                z = false;
                c = 2;
            } else {
                LocaleInfo cachedLocale = null;
                ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
                curDIDLocaleOn = curDIDLocaleOn2;
                if (concurrentHashMap.containsKey(li.getId())) {
                    LocaleInfo cachedLocale2 = concurrentHashMap.get(li.getId());
                    cachedLocale = cachedLocale2;
                    languageXmlPath = languageXmlPath2;
                } else {
                    StringBuilder sb = new StringBuilder();
                    languageXmlPath = languageXmlPath2;
                    String languageXmlPath3 = li.getLangScriptKey();
                    String langScriptCtry = sb.append(languageXmlPath3).append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE).append(country).toString();
                    if (concurrentHashMap.containsKey(langScriptCtry)) {
                        LocaleInfo cachedLocale3 = concurrentHashMap.get(langScriptCtry);
                        cachedLocale = cachedLocale3;
                    }
                }
                if (!concurrentHashMap.containsKey(liWithLatnExt.getId())) {
                    cachedLocaleWithLatnExt = null;
                } else {
                    LocaleInfo cachedLocaleWithLatnExt2 = concurrentHashMap.get(liWithLatnExt.getId());
                    li.mHasNumberingSystems = true;
                    cachedLocaleWithLatnExt = cachedLocaleWithLatnExt2;
                }
                if (cachedLocale != null) {
                    if (nosuggestedLocales.contains(li.toString())) {
                        cachedLocale.mSuggestionFlags = 0;
                    } else {
                        cachedLocale.mSuggestionFlags |= 2;
                        cachedLocale.mSuggestionFlags |= 16;
                    }
                    if (DID_LOCALE.equals(li.getId())) {
                        if (sPreIsDIDLocaleOn == 0) {
                            concurrentHashMap.remove(li.getId());
                            z = false;
                            c = 2;
                            i++;
                            curDIDLocaleOn2 = curDIDLocaleOn;
                            languageXmlPath2 = languageXmlPath;
                        } else {
                            cachedLocale.mSuggestionFlags = 0;
                        }
                    }
                }
                if (cachedLocaleWithLatnExt == null) {
                    z = false;
                    c = 2;
                } else if (nosuggestedLocales.contains(li.toString())) {
                    z = false;
                    cachedLocaleWithLatnExt.mSuggestionFlags = 0;
                    c = 2;
                } else {
                    z = false;
                    c = 2;
                    cachedLocaleWithLatnExt.mSuggestionFlags |= 2;
                    cachedLocaleWithLatnExt.mSuggestionFlags |= 16;
                }
            }
            if (isInternalCalled) {
                localizedLocales.add(li.getLangScriptKey());
            }
            i++;
            curDIDLocaleOn2 = curDIDLocaleOn;
            languageXmlPath2 = languageXmlPath;
        }
        if (isInternalCalled) {
            for (LocaleInfo li2 : sLocaleCache.values()) {
                li2.setTranslated(localizedLocales.contains(li2.getLangScriptKey()));
            }
            addSuggestedLocalesForRegion(Locale.getDefault());
            sPrevDefaultLocaleList = LocaleList.getDefault();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b9 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void buildLocaleCache(Context context, String[] xmlLocales, int whichSupport) {
        String[] baseSupportedLocales;
        Locale parent;
        String supportedLocales = "";
        String unsupportedLocales = "";
        String suggestedLocales = "";
        switch (whichSupport) {
            case 0:
                baseSupportedLocales = LocalePicker.getSupportedLocales(context);
                break;
            case 1:
                baseSupportedLocales = LocalePicker.getSpecificCustomerSupportedLocales(context);
                break;
            case 2:
                baseSupportedLocales = LocalePicker.getDIDLocale(context);
                break;
            default:
                baseSupportedLocales = new String[]{""};
                break;
        }
        int i = 0;
        int i2 = 2;
        int i3 = 1;
        if (whichSupport != 2 && xmlLocales != null) {
            supportedLocales = xmlLocales[0];
            unsupportedLocales = xmlLocales[1];
            suggestedLocales = xmlLocales[2];
        }
        Set<String> simCountries = getSimCountries(context);
        int length = baseSupportedLocales.length;
        while (i < length) {
            String localeId = baseSupportedLocales[i];
            if (localeId.isEmpty()) {
                throw new IllformedLocaleException("Bad locale entry in locale_config.xml");
            }
            LocaleInfo li = new LocaleInfo(localeId);
            String lId = li.toString();
            if (lId != null) {
                if (whichSupport == i3) {
                    if (!supportedLocales.contains(lId)) {
                    }
                    if (simCountries.contains(li.getLocale().getCountry())) {
                        li.mSuggestionFlags |= i3;
                    }
                    ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
                    concurrentHashMap.put(lId, li);
                    parent = li.getParent();
                    if (parent != null) {
                        String parentId = parent.toLanguageTag();
                        if (parentId != null) {
                            if (!concurrentHashMap.containsKey(parentId)) {
                                concurrentHashMap.put(parentId, new LocaleInfo(parent));
                            }
                        } else {
                            Log.m94d(TAG, "put null key to sLocaleCache #2");
                        }
                    }
                } else if (whichSupport == 0) {
                    if (suggestedLocales.contains(lId)) {
                        li.mSuggestionFlags |= i3;
                        li.mSuggestionFlags |= 32;
                    }
                    if (unsupportedLocales.contains(lId)) {
                    }
                    if (simCountries.contains(li.getLocale().getCountry())) {
                    }
                    ConcurrentHashMap<String, LocaleInfo> concurrentHashMap2 = sLocaleCache;
                    concurrentHashMap2.put(lId, li);
                    parent = li.getParent();
                    if (parent != null) {
                    }
                } else {
                    if (whichSupport == i2 && sPreIsDIDLocaleOn == 0) {
                    }
                    if (simCountries.contains(li.getLocale().getCountry())) {
                    }
                    ConcurrentHashMap<String, LocaleInfo> concurrentHashMap22 = sLocaleCache;
                    concurrentHashMap22.put(lId, li);
                    parent = li.getParent();
                    if (parent != null) {
                    }
                }
            }
            i++;
            i2 = 2;
            i3 = 1;
        }
    }

    public static Set<LocaleInfo> getLevelLocales(Context context, Set<String> ignorables, LocaleInfo parent, boolean translatedOnly) {
        return getLevelLocales(context, ignorables, parent, translatedOnly, null);
    }

    public static Set<LocaleInfo> getLevelLocales(Context context, Set<String> ignorables, LocaleInfo parent, boolean translatedOnly, LocaleList explicitLocales) {
        ConcurrentHashMap<String, LocaleInfo> supportedLocaleInfos;
        sCountryMode = parent != null;
        fillCache(context);
        if (parent != null) {
            parent.getId();
        }
        new HashSet();
        if (explicitLocales == null) {
            supportedLocaleInfos = sLocaleCache;
        } else {
            supportedLocaleInfos = convertExplicitLocales(explicitLocales, sLocaleCache.values());
        }
        return getTierLocales(ignorables, parent, translatedOnly, supportedLocaleInfos);
    }

    private static Set<LocaleInfo> getTierLocales(Set<String> ignorables, LocaleInfo parent, boolean translatedOnly, ConcurrentHashMap<String, LocaleInfo> supportedLocaleInfos) {
        boolean hasTargetParent = parent != null;
        String parentId = hasTargetParent ? parent.getId() : null;
        HashSet<LocaleInfo> result = new HashSet<>();
        for (LocaleInfo li : supportedLocaleInfos.values()) {
            if (!isShallIgnore(ignorables, li, translatedOnly)) {
                switch (getLocaleTier(parent)) {
                    case 1:
                        if (li.isSuggestionOfType(1)) {
                            result.add(li);
                            break;
                        } else {
                            LocaleInfo parentLi = getLocaleInfo(li.getParent(), supportedLocaleInfos);
                            if (parentLi != null) {
                                if (li.isSuggestionOfType(16)) {
                                    parentLi.mSuggestionFlags = 16 | parentLi.mSuggestionFlags;
                                }
                                result.add(parentLi);
                                break;
                            } else {
                                break;
                            }
                        }
                    case 2:
                        if (parentId.equals(li.getParent().toLanguageTag())) {
                            result.add(li);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (parent.getLocale().stripExtensions().equals(li.getLocale().stripExtensions())) {
                            result.add(li);
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        return result;
    }

    public static ConcurrentHashMap<String, LocaleInfo> convertExplicitLocales(LocaleList explicitLocales, Collection<LocaleInfo> localeinfo) {
        LocaleList localeList = matchLocaleFromSupportedLocaleList(explicitLocales, localeinfo);
        ConcurrentHashMap<String, LocaleInfo> localeInfos = new ConcurrentHashMap<>();
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            if (locale.toString().isEmpty()) {
                throw new IllformedLocaleException("Bad locale entry");
            }
            LocaleInfo li = new LocaleInfo(locale);
            if (!localeInfos.containsKey(li.getId())) {
                localeInfos.put(li.getId(), li);
                Locale parent = li.getParent();
                if (parent != null) {
                    String parentId = parent.toLanguageTag();
                    if (!localeInfos.containsKey(parentId)) {
                        localeInfos.put(parentId, new LocaleInfo(parent));
                    }
                }
            }
        }
        return localeInfos;
    }

    private static LocaleList matchLocaleFromSupportedLocaleList(LocaleList explicitLocales, Collection<LocaleInfo> localeInfos) {
        if (localeInfos == null) {
            return explicitLocales;
        }
        Locale[] resultLocales = new Locale[explicitLocales.size()];
        for (int i = 0; i < explicitLocales.size(); i++) {
            Locale locale = explicitLocales.get(i);
            if (!TextUtils.isEmpty(locale.getCountry())) {
                Iterator<LocaleInfo> it = localeInfos.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LocaleInfo localeInfo = it.next();
                    if (LocaleList.matchesLanguageAndScript(locale, localeInfo.getLocale()) && TextUtils.equals(locale.getCountry(), localeInfo.getLocale().getCountry())) {
                        resultLocales[i] = localeInfo.getLocale();
                        break;
                    }
                }
            }
            if (resultLocales[i] == null) {
                resultLocales[i] = locale;
            }
        }
        return new LocaleList(resultLocales);
    }

    public static LocaleInfo getLocaleInfo(Locale locale) {
        ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
        LocaleInfo localeInfo = getLocaleInfo(locale, concurrentHashMap);
        addLocaleInfoToMap(locale, localeInfo, concurrentHashMap);
        return localeInfo;
    }

    private static LocaleInfo getLocaleInfo(Locale locale, ConcurrentHashMap<String, LocaleInfo> localeInfos) {
        String id = locale.toLanguageTag();
        if (!localeInfos.containsKey(id)) {
            Locale filteredLocale = getLocaleWithOnlyNumberingSystem(locale);
            if (localeInfos.containsKey(filteredLocale.toLanguageTag())) {
                LocaleInfo result = new LocaleInfo(locale);
                LocaleInfo localeInfo = localeInfos.get(filteredLocale.toLanguageTag());
                result.mIsPseudo = localeInfo.mIsPseudo;
                result.mIsTranslated = localeInfo.mIsTranslated;
                result.mHasNumberingSystems = localeInfo.mHasNumberingSystems;
                result.mSuggestionFlags = localeInfo.mSuggestionFlags;
                return result;
            }
            LocaleInfo result2 = new LocaleInfo(locale);
            return result2;
        }
        LocaleInfo result3 = localeInfos.get(id);
        return result3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Locale getLocaleWithOnlyNumberingSystem(Locale locale) {
        return new Locale.Builder().setLocale(locale.stripExtensions()).setUnicodeLocaleKeyword("nu", locale.getUnicodeLocaleType("nu")).build();
    }

    private static void addLocaleInfoToMap(Locale locale, LocaleInfo localeInfo, ConcurrentHashMap<String, LocaleInfo> map) {
        if (!map.containsKey(locale.toLanguageTag())) {
            Locale localeWithNumberingSystem = getLocaleWithOnlyNumberingSystem(locale);
            if (!map.containsKey(localeWithNumberingSystem.toLanguageTag())) {
                map.put(locale.toLanguageTag(), localeInfo);
            }
        }
    }

    public static List<LocalePicker.LocaleInfo> getAllLocaleInfos(Context context) {
        Locale l;
        fillCacheManaged(context, false);
        ConcurrentHashMap<String, LocaleInfo> concurrentHashMap = sLocaleCache;
        ArrayList<LocalePicker.LocaleInfo> localeInfos = new ArrayList<>(concurrentHashMap.size());
        for (LocaleInfo li : concurrentHashMap.values()) {
            if ((li.mSuggestionFlags & 16) != 0 && li.getParent() != null && (l = Locale.forLanguageTag(li.toString())) != null) {
                localeInfos.add(new LocalePicker.LocaleInfo(toTitleCase(l.getDisplayName(l)), l));
            }
        }
        localeInfos.trimToSize();
        if (!sPriorityLocale.isEmpty()) {
            ArrayList<LocalePicker.LocaleInfo> configLocaleInfos = new ArrayList<>();
            ArrayList<LocalePicker.LocaleInfo> tmpLocaleInfos = new ArrayList<>();
            Iterator<LocalePicker.LocaleInfo> it = localeInfos.iterator();
            while (it.hasNext()) {
                LocalePicker.LocaleInfo localeInfo = it.next();
                if (isConfigLocale(sPriorityLocale, localeInfo.getLocale())) {
                    configLocaleInfos.add(localeInfo);
                } else {
                    tmpLocaleInfos.add(localeInfo);
                }
            }
            Collections.sort(configLocaleInfos);
            Collections.sort(tmpLocaleInfos);
            configLocaleInfos.addAll(tmpLocaleInfos);
            return configLocaleInfos;
        }
        Collections.sort(localeInfos);
        return localeInfos;
    }

    private static boolean isConfigLocale(String config, Locale locale) {
        return config.equals(locale.getLanguage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isChina() {
        return "CHINA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
    }

    private static String toTitleCase(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static String[] getLocaleListFromXML(String filePath) {
        String[] result = new String[4];
        Node rootNode = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            if (doc != null) {
                rootNode = doc.getDocumentElement();
            }
        } catch (IOException ex) {
            Log.m94d(TAG, ex.toString());
        } catch (ParserConfigurationException ex2) {
            Log.m94d(TAG, ex2.toString());
        } catch (SAXException ex3) {
            Log.m94d(TAG, ex3.toString());
        }
        if (rootNode == null) {
            return null;
        }
        String[] displayTagPath = {TAG_LANGUAGE, TAG_DISPLAY};
        String[] notDisplayTagPath = {TAG_LANGUAGE, TAG_NOT_DISPLAY};
        String[] suggestedTagPath = {TAG_LANGUAGE, TAG_SUGGESTED};
        String[] notSuggestedTagPath = {TAG_LANGUAGE, TAG_NONSUGGESTED};
        result[0] = findTagValue(displayTagPath, rootNode);
        result[1] = findTagValue(notDisplayTagPath, rootNode);
        result[2] = findTagValue(suggestedTagPath, rootNode);
        result[3] = findTagValue(notSuggestedTagPath, rootNode);
        return result;
    }

    private static String findTagValue(String[] tagList, Node node) {
        NodeList children;
        String result = "";
        for (String tagName : tagList) {
            if (node != null && (children = node.getChildNodes()) != null) {
                int n = children.getLength();
                for (int i = 0; i < n; i++) {
                    Node child = children.item(i);
                    if (child != null && tagName.equals(child.getNodeName())) {
                        node = child;
                    }
                }
            }
        }
        if (node == null || !tagList[tagList.length - 1].equals(node.getNodeName())) {
            return "";
        }
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            result = firstChild.getNodeValue();
        }
        return result.replaceAll("\\s", "").replaceAll(Session.SESSION_SEPARATION_CHAR_CHILD, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
    }

    public static LocaleInfo fromLocale(Locale locale) {
        return new LocaleInfo(locale);
    }
}
