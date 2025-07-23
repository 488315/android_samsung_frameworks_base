package com.android.internal.pm.pkg.component;

import android.aconfig.DeviceProtos;
import android.aconfig.nano.Aconfig;
import android.content.res.Flags;
import android.media.MediaMetrics;
import android.os.Environment;
import android.os.Process;
import android.os.flagging.AconfigPackage;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class AconfigFlags {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "AconfigFlags";
    private static final String OVERRIDE_PREFIX = "device_config_overrides/";
    private static final String STAGED_PREFIX = "staged/";
    private final Map<String, Boolean> mFlagValues = new ArrayMap();
    private final Map<String, AconfigPackage> mAconfigPackages = new ConcurrentHashMap();

    public AconfigFlags() {
        if (Flags.manifestFlagging()) {
            if (useNewStorage()) {
                Slog.i(LOG_TAG, "Using new flag storage");
                return;
            }
            Slog.i(LOG_TAG, "Using OLD proto flag storage");
            for (String str : Process.myUid() == 1000 ? DeviceProtos.parsedFlagsProtoPaths() : Arrays.asList(DeviceProtos.PATHS)) {
                File file = new File(str);
                if (file.isFile() && file.canRead()) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            loadAconfigDefaultValues(fileInputStream.readAllBytes());
                            fileInputStream.close();
                        } catch (Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        Slog.w(LOG_TAG, "Failed to read Aconfig values from " + str, e);
                    }
                }
            }
            if (Process.myUid() == 1000) {
                loadServerOverrides();
            }
        }
    }

    private static boolean useNewStorage() {
        return com.android.internal.hidden_from_bootclasspath.android.provider.flags.Flags.newStoragePublicApi() && Flags.useNewAconfigStorage();
    }

    private void loadServerOverrides() {
        int i;
        Integer num;
        File file = new File(Environment.getUserSystemDirectory(0), "settings_config.xml");
        if (file.isFile() && file.canRead()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    if (resolvePullParser.next() != 3 && "settings".equals(resolvePullParser.getName())) {
                        ArrayMap arrayMap = new ArrayMap();
                        int depth = resolvePullParser.getDepth();
                        while (true) {
                            int next = resolvePullParser.next();
                            if (next == 1 || (next == 3 && resolvePullParser.getDepth() <= depth)) {
                                break;
                            }
                            if (next != 3 && next != 4 && "setting".equals(resolvePullParser.getName())) {
                                String attributeValue = resolvePullParser.getAttributeValue(null, "name");
                                String attributeValue2 = resolvePullParser.getAttributeValue(null, "value");
                                if (attributeValue != null && attributeValue2 != null && ("false".equalsIgnoreCase(attributeValue2) || "true".equalsIgnoreCase(attributeValue2))) {
                                    String str = "/";
                                    if (attributeValue.startsWith(OVERRIDE_PREFIX)) {
                                        attributeValue = attributeValue.substring(24);
                                        str = ":";
                                        i = 20;
                                    } else if (attributeValue.startsWith(STAGED_PREFIX)) {
                                        attributeValue = attributeValue.substring(7);
                                        str = "*";
                                        i = 10;
                                    } else {
                                        i = 0;
                                    }
                                    String parseFlagPackageAndName = parseFlagPackageAndName(attributeValue, str);
                                    if (parseFlagPackageAndName != null && this.mFlagValues.containsKey(parseFlagPackageAndName) && ((num = (Integer) arrayMap.get(parseFlagPackageAndName)) == null || num.intValue() < i)) {
                                        arrayMap.put(parseFlagPackageAndName, Integer.valueOf(i));
                                        this.mFlagValues.put(parseFlagPackageAndName, Boolean.valueOf(Boolean.parseBoolean(attributeValue2)));
                                    }
                                }
                            }
                        }
                    }
                    fileInputStream.close();
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | XmlPullParserException e) {
                Slog.w(LOG_TAG, "Failed to read Aconfig values from settings_config.xml", e);
            }
        }
    }

    private static String parseFlagPackageAndName(String str, String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return null;
        }
        return str.substring(indexOf + 1);
    }

    private void loadAconfigDefaultValues(byte[] bArr) throws IOException {
        for (Aconfig.parsed_flag parsed_flagVar : Aconfig.parsed_flags.parseFrom(bArr).parsedFlag) {
            String str = parsed_flagVar.package_ + MediaMetrics.SEPARATOR + parsed_flagVar.name;
            boolean z = true;
            if (parsed_flagVar.state != 1) {
                z = false;
            }
            this.mFlagValues.put(str, Boolean.valueOf(z));
        }
    }

    public Boolean getFlagValue(String str) {
        if (useNewStorage()) {
            return getFlagValueFromNewStorage(str);
        }
        return this.mFlagValues.get(str);
    }

    private Boolean getFlagValueFromNewStorage(String str) {
        if (!this.mFlagValues.isEmpty() && this.mFlagValues.containsKey(str)) {
            return this.mFlagValues.get(str);
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf < 0) {
            Slog.e(LOG_TAG, "Unable to parse package name from " + str);
            return null;
        }
        String substring = str.substring(0, lastIndexOf);
        String substring2 = str.substring(lastIndexOf + 1);
        AconfigPackage computeIfAbsent = this.mAconfigPackages.computeIfAbsent(substring, new Function() { // from class: com.android.internal.pm.pkg.component.AconfigFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AconfigFlags.lambda$getFlagValueFromNewStorage$0((String) obj);
            }
        });
        if (computeIfAbsent != null) {
            try {
                return Boolean.valueOf(computeIfAbsent.getBooleanFlagValue(substring2, false));
            } catch (Exception e) {
                Slog.e(LOG_TAG, "Failed to read Aconfig flag value for " + str, e);
            }
        }
        return null;
    }

    static /* synthetic */ AconfigPackage lambda$getFlagValueFromNewStorage$0(String str) {
        try {
            return AconfigPackage.load(str);
        } catch (Exception e) {
            Slog.e(LOG_TAG, "Failed to load aconfig package " + str, e);
            return null;
        }
    }

    public boolean skipCurrentElement(ParsingPackage parsingPackage, XmlPullParser xmlPullParser) {
        return skipCurrentElement(parsingPackage, xmlPullParser, false);
    }

    public boolean skipCurrentElement(ParsingPackage parsingPackage, XmlPullParser xmlPullParser, boolean z) {
        boolean z2;
        boolean z3;
        if (!Flags.manifestFlagging()) {
            return false;
        }
        String attributeValue = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "featureFlag");
        if (attributeValue == null && z) {
            attributeValue = xmlPullParser.getAttributeValue(null, "featureFlag");
        }
        if (attributeValue == null) {
            return false;
        }
        String strip = attributeValue.strip();
        if (strip.startsWith("!")) {
            strip = strip.substring(1).strip();
            z2 = true;
        } else {
            z2 = false;
        }
        Boolean flagValue = getFlagValue(strip);
        if (flagValue == null) {
            flagValue = false;
            z3 = true;
        } else {
            z3 = false;
        }
        boolean z4 = flagValue.booleanValue() == z2;
        if (parsingPackage != null && com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.includeFeatureFlagsInPackageCacher()) {
            if (z3) {
                parsingPackage.addFeatureFlag(strip, null);
                return z4;
            }
            parsingPackage.addFeatureFlag(strip, flagValue);
        }
        return z4;
    }

    public void addFlagValuesForTesting(Map<String, Boolean> map) {
        this.mFlagValues.putAll(map);
    }
}
