package android.speech.tts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.accessibility.common.ShortcutConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class TtsEngines {
    private static final boolean DBG = false;
    private static final String LOCALE_DELIMITER_NEW = "_";
    private static final String LOCALE_DELIMITER_OLD = "-";
    private static final String TAG = "TtsEngines";
    private static final String XML_TAG_NAME = "tts-engine";
    private static final Map<String, String> sNormalizeCountry;
    private static final Map<String, String> sNormalizeLanguage;
    private final Context mContext;

    static {
        HashMap map = new HashMap();
        for (String str : Locale.getISOLanguages()) {
            try {
                map.put(new Locale(str).getISO3Language(), str);
            } catch (MissingResourceException unused) {
            }
        }
        sNormalizeLanguage = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        for (String str2 : Locale.getISOCountries()) {
            try {
                map2.put(new Locale("", str2).getISO3Country(), str2);
            } catch (MissingResourceException unused2) {
            }
        }
        sNormalizeCountry = Collections.unmodifiableMap(map2);
    }

    public TtsEngines(Context context) {
        this.mContext = context;
    }

    public String getDefaultEngine() {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.TTS_DEFAULT_SYNTH);
        return isEngineInstalled(string) ? string : getHighestRankedEngineName();
    }

    public String getHighestRankedEngineName() {
        List<TextToSpeech.EngineInfo> engines = getEngines();
        if (engines.size() <= 0 || !engines.get(0).system) {
            return null;
        }
        return engines.get(0).name;
    }

    public TextToSpeech.EngineInfo getEngineInfo(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
        intent.setPackage(str);
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 65536);
        if (listQueryIntentServices == null || listQueryIntentServices.size() != 1) {
            return null;
        }
        return getEngineInfo(listQueryIntentServices.get(0), packageManager);
    }

    public List<TextToSpeech.EngineInfo> getEngines() {
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(new Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE), 65536);
        if (listQueryIntentServices == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(listQueryIntentServices.size());
        Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
        while (it.hasNext()) {
            TextToSpeech.EngineInfo engineInfo = getEngineInfo(it.next(), packageManager);
            if (engineInfo != null) {
                arrayList.add(engineInfo);
            }
        }
        Collections.sort(arrayList, EngineInfoComparator.INSTANCE);
        return arrayList;
    }

    private boolean isSystemEngine(ServiceInfo serviceInfo) {
        ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        return (applicationInfo == null || (applicationInfo.flags & 1) == 0) ? false : true;
    }

    public boolean isEngineInstalled(String str) {
        return (str == null || getEngineInfo(str) == null) ? false : true;
    }

    public Intent getSettingsIntent(String str) {
        ServiceInfo serviceInfo;
        String str2;
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
        intent.setPackage(str);
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 65664);
        if (listQueryIntentServices == null || listQueryIntentServices.size() != 1 || (serviceInfo = listQueryIntentServices.get(0).serviceInfo) == null || (str2 = settingsActivityFromServiceInfo(serviceInfo, packageManager)) == null) {
            return null;
        }
        Intent intent2 = new Intent();
        intent2.setClassName(str, str2);
        return intent2;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x00e9: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:51:0x00e9 */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String settingsActivityFromServiceInfo(ServiceInfo serviceInfo, PackageManager packageManager) throws Throwable {
        XmlResourceParser xmlResourceParserLoadXmlMetaData;
        XmlResourceParser xmlResourceParser;
        int next;
        XmlResourceParser xmlResourceParser2 = null;
        try {
            try {
                xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, TextToSpeech.Engine.SERVICE_META_DATA);
                try {
                    if (xmlResourceParserLoadXmlMetaData == null) {
                        Log.w(TAG, "No meta-data found for :" + serviceInfo);
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                        return null;
                    }
                    Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                    do {
                        next = xmlResourceParserLoadXmlMetaData.next();
                        if (next == 1) {
                            if (xmlResourceParserLoadXmlMetaData != null) {
                                xmlResourceParserLoadXmlMetaData.close();
                            }
                            return null;
                        }
                    } while (next != 2);
                    if (XML_TAG_NAME.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                        TypedArray typedArrayObtainAttributes = resourcesForApplication.obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.TextToSpeechEngine);
                        String string = typedArrayObtainAttributes.getString(0);
                        typedArrayObtainAttributes.recycle();
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                        return string;
                    }
                    Log.w(TAG, "Package " + serviceInfo + " uses unknown tag :" + xmlResourceParserLoadXmlMetaData.getName());
                    if (xmlResourceParserLoadXmlMetaData != null) {
                        xmlResourceParserLoadXmlMetaData.close();
                    }
                    return null;
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.w(TAG, "Could not load resources for : " + serviceInfo);
                    if (xmlResourceParserLoadXmlMetaData != null) {
                        xmlResourceParserLoadXmlMetaData.close();
                    }
                    return null;
                } catch (IOException e) {
                    e = e;
                    Log.w(TAG, "Error parsing metadata for " + serviceInfo + ":" + e);
                    if (xmlResourceParserLoadXmlMetaData != null) {
                        xmlResourceParserLoadXmlMetaData.close();
                    }
                    return null;
                } catch (XmlPullParserException e2) {
                    e = e2;
                    Log.w(TAG, "Error parsing metadata for " + serviceInfo + ":" + e);
                    if (xmlResourceParserLoadXmlMetaData != null) {
                        xmlResourceParserLoadXmlMetaData.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                xmlResourceParser2 = xmlResourceParser;
                if (xmlResourceParser2 != null) {
                    xmlResourceParser2.close();
                }
                throw th;
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            xmlResourceParserLoadXmlMetaData = null;
        } catch (IOException e3) {
            e = e3;
            xmlResourceParserLoadXmlMetaData = null;
        } catch (XmlPullParserException e4) {
            e = e4;
            xmlResourceParserLoadXmlMetaData = null;
        } catch (Throwable th2) {
            th = th2;
            if (xmlResourceParser2 != null) {
            }
            throw th;
        }
    }

    private TextToSpeech.EngineInfo getEngineInfo(ResolveInfo resolveInfo, PackageManager packageManager) {
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        if (serviceInfo == null) {
            return null;
        }
        TextToSpeech.EngineInfo engineInfo = new TextToSpeech.EngineInfo();
        engineInfo.name = serviceInfo.packageName;
        CharSequence charSequenceLoadLabel = serviceInfo.loadLabel(packageManager);
        engineInfo.label = TextUtils.isEmpty(charSequenceLoadLabel) ? engineInfo.name : charSequenceLoadLabel.toString();
        engineInfo.icon = serviceInfo.getIconResource();
        engineInfo.priority = resolveInfo.priority;
        engineInfo.system = isSystemEngine(serviceInfo);
        return engineInfo;
    }

    private static class EngineInfoComparator implements Comparator<TextToSpeech.EngineInfo> {
        static EngineInfoComparator INSTANCE = new EngineInfoComparator();

        private EngineInfoComparator() {
        }

        @Override // java.util.Comparator
        public int compare(TextToSpeech.EngineInfo engineInfo, TextToSpeech.EngineInfo engineInfo2) {
            if (engineInfo.system && !engineInfo2.system) {
                return -1;
            }
            if (!engineInfo2.system || engineInfo.system) {
                return engineInfo2.priority - engineInfo.priority;
            }
            return 1;
        }
    }

    public Locale getLocalePrefForEngine(String str) {
        return getLocalePrefForEngine(str, Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.TTS_DEFAULT_LOCALE));
    }

    public Locale getLocalePrefForEngine(String str, String str2) throws MissingResourceException {
        String enginePrefFromList = parseEnginePrefFromList(str2, str);
        if (TextUtils.isEmpty(enginePrefFromList)) {
            return Locale.getDefault();
        }
        Locale localeString = parseLocaleString(enginePrefFromList);
        if (localeString != null) {
            return localeString;
        }
        Log.w(TAG, "Failed to parse locale " + enginePrefFromList + ", returning en_US instead");
        return Locale.US;
    }

    public boolean isLocaleSetToDefaultForEngine(String str) {
        return TextUtils.isEmpty(parseEnginePrefFromList(Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.TTS_DEFAULT_LOCALE), str));
    }

    public Locale parseLocaleString(String str) throws MissingResourceException {
        String str2;
        String str3;
        String upperCase;
        str2 = "";
        if (TextUtils.isEmpty(str)) {
            str3 = "";
            upperCase = str3;
        } else {
            String[] strArrSplit = str.split("[-_]");
            String lowerCase = strArrSplit[0].toLowerCase();
            if (strArrSplit.length == 0) {
                Log.w(TAG, "Failed to convert " + str + " to a valid Locale object. Only separators");
                return null;
            }
            if (strArrSplit.length > 3) {
                Log.w(TAG, "Failed to convert " + str + " to a valid Locale object. Too many separators");
                return null;
            }
            upperCase = strArrSplit.length >= 2 ? strArrSplit[1].toUpperCase() : "";
            str3 = strArrSplit.length >= 3 ? strArrSplit[2] : "";
            str2 = lowerCase;
        }
        String str4 = sNormalizeLanguage.get(str2);
        if (str4 != null) {
            str2 = str4;
        }
        String str5 = sNormalizeCountry.get(upperCase);
        if (str5 != null) {
            upperCase = str5;
        }
        Locale locale = new Locale(str2, upperCase, str3);
        try {
            locale.getISO3Language();
            locale.getISO3Country();
            return locale;
        } catch (MissingResourceException unused) {
            Log.w(TAG, "Failed to convert " + str + " to a valid Locale object.");
            return null;
        }
    }

    public static Locale normalizeTTSLocale(Locale locale) {
        String str;
        String str2;
        String language = locale.getLanguage();
        if (!TextUtils.isEmpty(language) && (str2 = sNormalizeLanguage.get(language)) != null) {
            language = str2;
        }
        String country = locale.getCountry();
        if (!TextUtils.isEmpty(country) && (str = sNormalizeCountry.get(country)) != null) {
            country = str;
        }
        return new Locale(language, country, locale.getVariant());
    }

    public static String[] toOldLocaleStringFormat(Locale locale) {
        String[] strArr = {"", "", ""};
        try {
            strArr[0] = locale.getISO3Language();
            strArr[1] = locale.getISO3Country();
            strArr[2] = locale.getVariant();
            return strArr;
        } catch (MissingResourceException unused) {
            return new String[]{"eng", "USA", ""};
        }
    }

    private static String parseEnginePrefFromList(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (String str3 : str.split(",")) {
            int iIndexOf = str3.indexOf(58);
            if (iIndexOf > 0 && str2.equals(str3.substring(0, iIndexOf))) {
                return str3.substring(iIndexOf + 1);
            }
        }
        return null;
    }

    public synchronized void updateLocalePrefForEngine(String str, Locale locale) {
        Settings.Secure.putString(this.mContext.getContentResolver(), Settings.Secure.TTS_DEFAULT_LOCALE, updateValueInCommaSeparatedList(Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.TTS_DEFAULT_LOCALE), str, locale != null ? locale.toString() : "").toString());
    }

    private String updateValueInCommaSeparatedList(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            sb.append(str2);
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(str3);
        } else {
            boolean z = true;
            boolean z2 = false;
            for (String str4 : str.split(",")) {
                int iIndexOf = str4.indexOf(58);
                if (iIndexOf > 0) {
                    if (str2.equals(str4.substring(0, iIndexOf))) {
                        if (z) {
                            z = false;
                        } else {
                            sb.append(',');
                        }
                        sb.append(str2);
                        sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                        sb.append(str3);
                        z2 = true;
                    } else {
                        if (z) {
                            z = false;
                        } else {
                            sb.append(',');
                        }
                        sb.append(str4);
                    }
                }
            }
            if (!z2) {
                sb.append(',');
                sb.append(str2);
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                sb.append(str3);
            }
        }
        return sb.toString();
    }
}
