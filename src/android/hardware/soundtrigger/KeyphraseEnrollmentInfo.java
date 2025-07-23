package android.hardware.soundtrigger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class KeyphraseEnrollmentInfo {
    public static final String ACTION_MANAGE_VOICE_KEYPHRASES = "com.android.intent.action.MANAGE_VOICE_KEYPHRASES";
    public static final String EXTRA_VOICE_KEYPHRASE_ACTION = "com.android.intent.extra.VOICE_KEYPHRASE_ACTION";
    public static final String EXTRA_VOICE_KEYPHRASE_HINT_TEXT = "com.android.intent.extra.VOICE_KEYPHRASE_HINT_TEXT";
    public static final String EXTRA_VOICE_KEYPHRASE_LOCALE = "com.android.intent.extra.VOICE_KEYPHRASE_LOCALE";
    public static final int MANAGE_ACTION_ENROLL = 0;
    public static final int MANAGE_ACTION_RE_ENROLL = 1;
    public static final int MANAGE_ACTION_UN_ENROLL = 2;
    private static final String TAG = "KeyphraseEnrollmentInfo";
    private static final String VOICE_KEYPHRASE_META_DATA = "android.voice_enrollment";
    private final Map<KeyphraseMetadata, String> mKeyphrasePackageMap;
    private final KeyphraseMetadata[] mKeyphrases;
    private String mParseError;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ManageActions {
    }

    public KeyphraseEnrollmentInfo(PackageManager packageManager) {
        Objects.requireNonNull(packageManager);
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new Intent(ACTION_MANAGE_VOICE_KEYPHRASES), 65536);
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            this.mParseError = "No enrollment applications found";
            this.mKeyphrasePackageMap = Collections.EMPTY_MAP;
            this.mKeyphrases = null;
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.mKeyphrasePackageMap = new HashMap();
        for (ResolveInfo resolveInfo : queryIntentServices) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resolveInfo.serviceInfo.packageName, 128);
                if ((applicationInfo.privateFlags & 8) == 0) {
                    Slog.w(TAG, applicationInfo.packageName + " is not a privileged system app");
                } else if (Manifest.permission.MANAGE_VOICE_KEYPHRASES.equals(applicationInfo.permission)) {
                    KeyphraseMetadata keyphraseMetadataFromApplicationInfo = getKeyphraseMetadataFromApplicationInfo(packageManager, applicationInfo, arrayList);
                    if (keyphraseMetadataFromApplicationInfo != null) {
                        this.mKeyphrasePackageMap.put(keyphraseMetadataFromApplicationInfo, applicationInfo.packageName);
                    }
                } else {
                    Slog.w(TAG, applicationInfo.packageName + " does not require MANAGE_VOICE_KEYPHRASES");
                }
            } catch (PackageManager.NameNotFoundException e) {
                String str = "error parsing voice enrollment meta-data for " + resolveInfo.serviceInfo.packageName;
                arrayList.add(str + ": " + e);
                Slog.w(TAG, str, e);
            }
        }
        if (this.mKeyphrasePackageMap.isEmpty()) {
            arrayList.add("No suitable enrollment application found");
            Slog.w(TAG, "No suitable enrollment application found");
            this.mKeyphrases = null;
        } else {
            this.mKeyphrases = (KeyphraseMetadata[]) this.mKeyphrasePackageMap.keySet().toArray(new KeyphraseMetadata[0]);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mParseError = TextUtils.join(ShaderAssembler.NEWLINE, arrayList);
    }

    private KeyphraseMetadata getKeyphraseMetadataFromApplicationInfo(PackageManager packageManager, ApplicationInfo applicationInfo, List<String> list) {
        KeyphraseMetadata keyphraseMetadata;
        XmlResourceParser loadXmlMetaData;
        int next;
        String str = applicationInfo.packageName;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                loadXmlMetaData = applicationInfo.loadXmlMetaData(packageManager, VOICE_KEYPHRASE_META_DATA);
            } catch (Throwable th) {
                th = th;
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            e = e;
            keyphraseMetadata = null;
        }
        try {
            try {
                if (loadXmlMetaData == null) {
                    String str2 = "No android.voice_enrollment meta-data for " + str;
                    list.add(str2);
                    Slog.w(TAG, str2);
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return null;
                }
                Resources resourcesForApplication = packageManager.getResourcesForApplication(applicationInfo);
                AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if ("voice-enrollment-application".equals(loadXmlMetaData.getName())) {
                    TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.VoiceEnrollmentApplication);
                    KeyphraseMetadata keyphraseFromTypedArray = getKeyphraseFromTypedArray(obtainAttributes, str, list);
                    obtainAttributes.recycle();
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return keyphraseFromTypedArray;
                }
                String str3 = "Meta-data does not start with voice-enrollment-application tag for " + str;
                list.add(str3);
                Slog.w(TAG, str3);
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                xmlResourceParser = loadXmlMetaData;
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                throw th;
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e2) {
            e = e2;
            keyphraseMetadata = null;
            xmlResourceParser = loadXmlMetaData;
            String str4 = "Error parsing keyphrase enrollment meta-data for " + str;
            list.add(str4 + ": " + e);
            Slog.w(TAG, str4, e);
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            return keyphraseMetadata;
        }
    }

    private KeyphraseMetadata getKeyphraseFromTypedArray(TypedArray typedArray, String str, List<String> list) {
        int i = typedArray.getInt(0, -1);
        if (i <= 0) {
            String str2 = "No valid searchKeyphraseId specified in meta-data for " + str;
            list.add(str2);
            Slog.w(TAG, str2);
            return null;
        }
        String string = typedArray.getString(1);
        if (string == null) {
            String str3 = "No valid searchKeyphrase specified in meta-data for " + str;
            list.add(str3);
            Slog.w(TAG, str3);
            return null;
        }
        String string2 = typedArray.getString(2);
        if (string2 == null) {
            String str4 = "No valid searchKeyphraseSupportedLocales specified in meta-data for " + str;
            list.add(str4);
            Slog.w(TAG, str4);
            return null;
        }
        ArraySet arraySet = new ArraySet();
        if (!TextUtils.isEmpty(string2)) {
            try {
                for (String str5 : string2.split(",")) {
                    arraySet.add(Locale.forLanguageTag(str5));
                }
            } catch (Exception unused) {
                String str6 = "Error reading searchKeyphraseSupportedLocales from meta-data for " + str;
                list.add(str6);
                Slog.w(TAG, str6);
                return null;
            }
        }
        int i2 = typedArray.getInt(3, -1);
        if (i2 < 0) {
            String str7 = "No valid searchKeyphraseRecognitionFlags specified in meta-data for " + str;
            list.add(str7);
            Slog.w(TAG, str7);
            return null;
        }
        return new KeyphraseMetadata(i, string, arraySet, i2);
    }

    public String getParseError() {
        return this.mParseError;
    }

    public Collection<KeyphraseMetadata> listKeyphraseMetadata() {
        return Arrays.asList(this.mKeyphrases);
    }

    public Intent getManageKeyphraseIntent(int i, String str, Locale locale) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(locale);
        Map<KeyphraseMetadata, String> map = this.mKeyphrasePackageMap;
        if (map == null || map.isEmpty()) {
            Slog.w(TAG, "No enrollment application exists");
            return null;
        }
        KeyphraseMetadata keyphraseMetadata = getKeyphraseMetadata(str, locale);
        if (keyphraseMetadata != null) {
            return new Intent(ACTION_MANAGE_VOICE_KEYPHRASES).setPackage(this.mKeyphrasePackageMap.get(keyphraseMetadata)).putExtra(EXTRA_VOICE_KEYPHRASE_HINT_TEXT, str).putExtra(EXTRA_VOICE_KEYPHRASE_LOCALE, locale.toLanguageTag()).putExtra(EXTRA_VOICE_KEYPHRASE_ACTION, i);
        }
        return null;
    }

    public KeyphraseMetadata getKeyphraseMetadata(String str, Locale locale) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(locale);
        KeyphraseMetadata[] keyphraseMetadataArr = this.mKeyphrases;
        if (keyphraseMetadataArr != null && keyphraseMetadataArr.length > 0) {
            for (KeyphraseMetadata keyphraseMetadata : keyphraseMetadataArr) {
                if (keyphraseMetadata.supportsPhrase(str) && keyphraseMetadata.supportsLocale(locale)) {
                    return keyphraseMetadata;
                }
            }
        }
        Slog.w(TAG, "No enrollment application supports the given keyphrase/locale: '" + str + "'/" + locale);
        return null;
    }

    public String toString() {
        return "KeyphraseEnrollmentInfo [KeyphrasePackageMap=" + this.mKeyphrasePackageMap.toString() + ", ParseError=" + this.mParseError + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
