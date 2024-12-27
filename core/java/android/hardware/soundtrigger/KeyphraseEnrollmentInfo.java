package android.hardware.soundtrigger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;

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

/* loaded from: classes2.dex */
public class KeyphraseEnrollmentInfo {
    public static final String ACTION_MANAGE_VOICE_KEYPHRASES =
            "com.android.intent.action.MANAGE_VOICE_KEYPHRASES";
    public static final String EXTRA_VOICE_KEYPHRASE_ACTION =
            "com.android.intent.extra.VOICE_KEYPHRASE_ACTION";
    public static final String EXTRA_VOICE_KEYPHRASE_HINT_TEXT =
            "com.android.intent.extra.VOICE_KEYPHRASE_HINT_TEXT";
    public static final String EXTRA_VOICE_KEYPHRASE_LOCALE =
            "com.android.intent.extra.VOICE_KEYPHRASE_LOCALE";
    public static final int MANAGE_ACTION_ENROLL = 0;
    public static final int MANAGE_ACTION_RE_ENROLL = 1;
    public static final int MANAGE_ACTION_UN_ENROLL = 2;
    private static final String TAG = "KeyphraseEnrollmentInfo";
    private static final String VOICE_KEYPHRASE_META_DATA = "android.voice_enrollment";
    private final Map<KeyphraseMetadata, String> mKeyphrasePackageMap;
    private final KeyphraseMetadata[] mKeyphrases;
    private String mParseError;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ManageActions {}

    public KeyphraseEnrollmentInfo(PackageManager pm) {
        Objects.requireNonNull(pm);
        List<ResolveInfo> ris =
                pm.queryIntentServices(new Intent(ACTION_MANAGE_VOICE_KEYPHRASES), 65536);
        if (ris == null || ris.isEmpty()) {
            this.mParseError = "No enrollment applications found";
            this.mKeyphrasePackageMap = Collections.emptyMap();
            this.mKeyphrases = null;
            return;
        }
        List<String> parseErrors = new ArrayList<>();
        this.mKeyphrasePackageMap = new HashMap();
        for (ResolveInfo ri : ris) {
            try {
                ApplicationInfo ai = pm.getApplicationInfo(ri.serviceInfo.packageName, 128);
                if ((ai.privateFlags & 8) == 0) {
                    Slog.w(TAG, ai.packageName + " is not a privileged system app");
                } else if (!Manifest.permission.MANAGE_VOICE_KEYPHRASES.equals(ai.permission)) {
                    Slog.w(TAG, ai.packageName + " does not require MANAGE_VOICE_KEYPHRASES");
                } else {
                    KeyphraseMetadata metadata =
                            getKeyphraseMetadataFromApplicationInfo(pm, ai, parseErrors);
                    if (metadata != null) {
                        this.mKeyphrasePackageMap.put(metadata, ai.packageName);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                String error =
                        "error parsing voice enrollment meta-data for "
                                + ri.serviceInfo.packageName;
                parseErrors.add(error + ": " + e);
                Slog.w(TAG, error, e);
            }
        }
        if (this.mKeyphrasePackageMap.isEmpty()) {
            parseErrors.add("No suitable enrollment application found");
            Slog.w(TAG, "No suitable enrollment application found");
            this.mKeyphrases = null;
        } else {
            this.mKeyphrases =
                    (KeyphraseMetadata[])
                            this.mKeyphrasePackageMap.keySet().toArray(new KeyphraseMetadata[0]);
        }
        if (!parseErrors.isEmpty()) {
            this.mParseError = TextUtils.join("\n", parseErrors);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x007e, code lost:

       if (r1 != null) goto L23;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0080, code lost:

       r1.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00bb, code lost:

       return r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b8, code lost:

       if (0 == 0) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.hardware.soundtrigger.KeyphraseMetadata getKeyphraseMetadataFromApplicationInfo(
            android.content.pm.PackageManager r12,
            android.content.pm.ApplicationInfo r13,
            java.util.List<java.lang.String> r14) {
        /*
            r11 = this;
            java.lang.String r0 = "KeyphraseEnrollmentInfo"
            r1 = 0
            java.lang.String r2 = r13.packageName
            r3 = 0
            java.lang.String r4 = "android.voice_enrollment"
            android.content.res.XmlResourceParser r4 = r13.loadXmlMetaData(r12, r4)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            r1 = r4
            r4 = 0
            if (r1 != 0) goto L30
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            r5.<init>()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            java.lang.String r6 = "No android.voice_enrollment meta-data for "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            r14.add(r5)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            android.util.Slog.w(r0, r5)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86
            if (r1 == 0) goto L2f
            r1.close()
        L2f:
            return r4
        L30:
            android.content.res.Resources r5 = r12.getResourcesForApplication(r13)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            android.util.AttributeSet r6 = android.util.Xml.asAttributeSet(r1)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
        L38:
            int r7 = r1.next()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            r8 = r7
            r9 = 1
            if (r7 == r9) goto L44
            r7 = 2
            if (r8 == r7) goto L44
            goto L38
        L44:
            java.lang.String r7 = r1.getName()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            java.lang.String r9 = "voice-enrollment-application"
            boolean r9 = r9.equals(r7)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            if (r9 != 0) goto L70
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            r9.<init>()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            java.lang.String r10 = "Meta-data does not start with voice-enrollment-application tag for "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            java.lang.StringBuilder r9 = r9.append(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            r14.add(r9)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            android.util.Slog.w(r0, r9)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            if (r1 == 0) goto L6f
            r1.close()
        L6f:
            return r4
        L70:
            int[] r4 = com.android.internal.R.styleable.VoiceEnrollmentApplication     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            android.content.res.TypedArray r4 = r5.obtainAttributes(r6, r4)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            android.hardware.soundtrigger.KeyphraseMetadata r9 = r11.getKeyphraseFromTypedArray(r4, r2, r14)     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            r3 = r9
            r4.recycle()     // Catch: java.lang.Throwable -> L84 java.lang.Throwable -> L86 java.lang.Throwable -> L86 java.lang.Throwable -> L86
            if (r1 == 0) goto Lbb
        L80:
            r1.close()
            goto Lbb
        L84:
            r0 = move-exception
            goto Lbc
        L86:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84
            r5.<init>()     // Catch: java.lang.Throwable -> L84
            java.lang.String r6 = "Error parsing keyphrase enrollment meta-data for "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L84
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch: java.lang.Throwable -> L84
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L84
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84
            r6.<init>()     // Catch: java.lang.Throwable -> L84
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch: java.lang.Throwable -> L84
            java.lang.String r7 = ": "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> L84
            java.lang.StringBuilder r6 = r6.append(r4)     // Catch: java.lang.Throwable -> L84
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L84
            r14.add(r6)     // Catch: java.lang.Throwable -> L84
            android.util.Slog.w(r0, r5, r4)     // Catch: java.lang.Throwable -> L84
            if (r1 == 0) goto Lbb
            goto L80
        Lbb:
            return r3
        Lbc:
            if (r1 == 0) goto Lc1
            r1.close()
        Lc1:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.hardware.soundtrigger.KeyphraseEnrollmentInfo.getKeyphraseMetadataFromApplicationInfo(android.content.pm.PackageManager,"
                    + " android.content.pm.ApplicationInfo,"
                    + " java.util.List):android.hardware.soundtrigger.KeyphraseMetadata");
    }

    private KeyphraseMetadata getKeyphraseFromTypedArray(
            TypedArray array, String packageName, List<String> parseErrors) {
        int searchKeyphraseId = array.getInt(0, -1);
        if (searchKeyphraseId <= 0) {
            String error = "No valid searchKeyphraseId specified in meta-data for " + packageName;
            parseErrors.add(error);
            Slog.w(TAG, error);
            return null;
        }
        String searchKeyphrase = array.getString(1);
        if (searchKeyphrase == null) {
            String error2 = "No valid searchKeyphrase specified in meta-data for " + packageName;
            parseErrors.add(error2);
            Slog.w(TAG, error2);
            return null;
        }
        String searchKeyphraseSupportedLocales = array.getString(2);
        if (searchKeyphraseSupportedLocales == null) {
            String error3 =
                    "No valid searchKeyphraseSupportedLocales specified in meta-data for "
                            + packageName;
            parseErrors.add(error3);
            Slog.w(TAG, error3);
            return null;
        }
        ArraySet<Locale> locales = new ArraySet<>();
        if (!TextUtils.isEmpty(searchKeyphraseSupportedLocales)) {
            try {
                String[] supportedLocalesDelimited = searchKeyphraseSupportedLocales.split(",");
                for (String s : supportedLocalesDelimited) {
                    locales.add(Locale.forLanguageTag(s));
                }
            } catch (Exception e) {
                String error4 =
                        "Error reading searchKeyphraseSupportedLocales from meta-data for "
                                + packageName;
                parseErrors.add(error4);
                Slog.w(TAG, error4);
                return null;
            }
        }
        int recognitionModes = array.getInt(3, -1);
        if (recognitionModes < 0) {
            String error5 =
                    "No valid searchKeyphraseRecognitionFlags specified in meta-data for "
                            + packageName;
            parseErrors.add(error5);
            Slog.w(TAG, error5);
            return null;
        }
        return new KeyphraseMetadata(searchKeyphraseId, searchKeyphrase, locales, recognitionModes);
    }

    public String getParseError() {
        return this.mParseError;
    }

    public Collection<KeyphraseMetadata> listKeyphraseMetadata() {
        return Arrays.asList(this.mKeyphrases);
    }

    public Intent getManageKeyphraseIntent(int action, String keyphrase, Locale locale) {
        Objects.requireNonNull(keyphrase);
        Objects.requireNonNull(locale);
        if (this.mKeyphrasePackageMap == null || this.mKeyphrasePackageMap.isEmpty()) {
            Slog.w(TAG, "No enrollment application exists");
            return null;
        }
        KeyphraseMetadata keyphraseMetadata = getKeyphraseMetadata(keyphrase, locale);
        if (keyphraseMetadata != null) {
            return new Intent(ACTION_MANAGE_VOICE_KEYPHRASES)
                    .setPackage(this.mKeyphrasePackageMap.get(keyphraseMetadata))
                    .putExtra(EXTRA_VOICE_KEYPHRASE_HINT_TEXT, keyphrase)
                    .putExtra(EXTRA_VOICE_KEYPHRASE_LOCALE, locale.toLanguageTag())
                    .putExtra(EXTRA_VOICE_KEYPHRASE_ACTION, action);
        }
        return null;
    }

    public KeyphraseMetadata getKeyphraseMetadata(String keyphrase, Locale locale) {
        Objects.requireNonNull(keyphrase);
        Objects.requireNonNull(locale);
        if (this.mKeyphrases != null && this.mKeyphrases.length > 0) {
            for (KeyphraseMetadata keyphraseMetadata : this.mKeyphrases) {
                if (keyphraseMetadata.supportsPhrase(keyphrase)
                        && keyphraseMetadata.supportsLocale(locale)) {
                    return keyphraseMetadata;
                }
            }
        }
        Slog.w(
                TAG,
                "No enrollment application supports the given keyphrase/locale: '"
                        + keyphrase
                        + "'/"
                        + locale);
        return null;
    }

    public String toString() {
        return "KeyphraseEnrollmentInfo [KeyphrasePackageMap="
                + this.mKeyphrasePackageMap.toString()
                + ", ParseError="
                + this.mParseError
                + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
