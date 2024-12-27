package com.android.internal.pm.pkg.component;

import android.aconfig.nano.Aconfig;
import android.content.res.Flags;
import android.content.res.XmlResourceParser;
import android.media.MediaMetrics;
import android.os.Environment;
import android.os.Process;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class AconfigFlags {
    private static final String LOG_TAG = "AconfigFlags";
    private static final List<String> sTextProtoFilesOnDevice = List.of("/system/etc/aconfig_flags.pb", "/system_ext/etc/aconfig_flags.pb", "/product/etc/aconfig_flags.pb", "/vendor/etc/aconfig_flags.pb");
    private final ArrayMap<String, Boolean> mFlagValues = new ArrayMap<>();

    public AconfigFlags() {
        if (!Flags.manifestFlagging()) {
            Slog.v(LOG_TAG, "Feature disabled, skipped all loading");
            return;
        }
        for (String fileName : sTextProtoFilesOnDevice) {
            try {
                FileInputStream inputStream = new FileInputStream(fileName);
                try {
                    loadAconfigDefaultValues(inputStream.readAllBytes());
                    inputStream.close();
                } catch (Throwable th) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                Slog.e(LOG_TAG, "Failed to read Aconfig values from " + fileName, e);
            }
        }
        if (Process.myUid() == 1000) {
            loadServerOverrides();
        }
    }

    private void loadServerOverrides() {
        Throwable th;
        String prefix;
        String prefix2;
        int priority;
        String str = "staged/";
        String flagPackageAndName = "device_config_overrides/";
        File settingsFile = new File(Environment.getUserSystemDirectory(0), "settings_config.xml");
        try {
            try {
                FileInputStream inputStream = new FileInputStream(settingsFile);
                try {
                    TypedXmlPullParser parser = Xml.resolvePullParser(inputStream);
                    int i = 3;
                    if (parser.next() != 3 && "settings".equals(parser.getName())) {
                        ArrayMap<String, Integer> flagPriority = new ArrayMap<>();
                        int outerDepth = parser.getDepth();
                        while (true) {
                            int type = parser.next();
                            if (type == 1) {
                                break;
                            }
                            if (type == i) {
                                try {
                                    if (parser.getDepth() <= outerDepth) {
                                        break;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    try {
                                        inputStream.close();
                                        throw th;
                                    } catch (Throwable th3) {
                                        th.addSuppressed(th3);
                                        throw th;
                                    }
                                }
                            }
                            if (type == i) {
                                i = 3;
                            } else if (type != 4 && "setting".equals(parser.getName())) {
                                String name = parser.getAttributeValue(null, "name");
                                String value = parser.getAttributeValue(null, "value");
                                if (name == null) {
                                    i = 3;
                                } else if (value != null && ("false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value))) {
                                    String separator = "/";
                                    if (name.startsWith(flagPackageAndName)) {
                                        String prefix3 = flagPackageAndName;
                                        name = name.substring(flagPackageAndName.length());
                                        separator = ":";
                                        prefix = prefix3;
                                        prefix2 = str;
                                        priority = 20;
                                    } else if (name.startsWith(str)) {
                                        String prefix4 = str;
                                        name = name.substring(str.length());
                                        separator = "*";
                                        prefix = prefix4;
                                        prefix2 = str;
                                        priority = 10;
                                    } else {
                                        prefix = "default";
                                        prefix2 = str;
                                        priority = 0;
                                    }
                                    String flagPackageAndName2 = parseFlagPackageAndName(name, separator);
                                    String str2 = flagPackageAndName;
                                    if (flagPackageAndName2 == null) {
                                        str = prefix2;
                                        flagPackageAndName = str2;
                                        i = 3;
                                    } else {
                                        File settingsFile2 = settingsFile;
                                        try {
                                            if (this.mFlagValues.containsKey(flagPackageAndName2)) {
                                                TypedXmlPullParser parser2 = parser;
                                                Slog.d(LOG_TAG, "Found " + prefix + " Aconfig flag value for " + flagPackageAndName2 + " = " + value);
                                                Integer currentPriority = flagPriority.get(flagPackageAndName2);
                                                if (currentPriority == null || currentPriority.intValue() < priority) {
                                                    int outerDepth2 = outerDepth;
                                                    flagPriority.put(flagPackageAndName2, Integer.valueOf(priority));
                                                    this.mFlagValues.put(flagPackageAndName2, Boolean.valueOf(Boolean.parseBoolean(value)));
                                                    str = prefix2;
                                                    flagPackageAndName = str2;
                                                    settingsFile = settingsFile2;
                                                    parser = parser2;
                                                    outerDepth = outerDepth2;
                                                    i = 3;
                                                } else {
                                                    Slog.i(LOG_TAG, "Skipping " + prefix + " flag " + flagPackageAndName2 + " because of the existing one with priority " + currentPriority);
                                                    str = prefix2;
                                                    flagPackageAndName = str2;
                                                    settingsFile = settingsFile2;
                                                    parser = parser2;
                                                    outerDepth = outerDepth;
                                                    i = 3;
                                                }
                                            } else {
                                                str = prefix2;
                                                flagPackageAndName = str2;
                                                settingsFile = settingsFile2;
                                                i = 3;
                                            }
                                        } catch (Throwable th4) {
                                            th = th4;
                                            inputStream.close();
                                            throw th;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    inputStream.close();
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (IOException | XmlPullParserException e) {
                e = e;
                Slog.e(LOG_TAG, "Failed to read Aconfig values from settings_config.xml", e);
            }
        } catch (IOException | XmlPullParserException e2) {
            e = e2;
            Slog.e(LOG_TAG, "Failed to read Aconfig values from settings_config.xml", e);
        }
    }

    private static String parseFlagPackageAndName(String fullName, String separator) {
        int index = fullName.indexOf(separator);
        if (index < 0) {
            return null;
        }
        return fullName.substring(index + 1);
    }

    private void loadAconfigDefaultValues(byte[] fileContents) throws IOException {
        Aconfig.parsed_flags parsedFlags = Aconfig.parsed_flags.parseFrom(fileContents);
        for (Aconfig.parsed_flag flag : parsedFlags.parsedFlag) {
            String flagPackageAndName = flag.package_ + MediaMetrics.SEPARATOR + flag.name;
            boolean z = true;
            if (flag.state != 1) {
                z = false;
            }
            boolean flagValue = z;
            Slog.v(LOG_TAG, "Read Aconfig default flag value " + flagPackageAndName + " = " + flagValue);
            this.mFlagValues.put(flagPackageAndName, Boolean.valueOf(flagValue));
        }
    }

    public Boolean getFlagValue(String flagPackageAndName) {
        Boolean value = this.mFlagValues.get(flagPackageAndName);
        Slog.d(LOG_TAG, "Aconfig flag value for " + flagPackageAndName + " = " + value);
        return value;
    }

    public boolean skipCurrentElement(XmlResourceParser parser) {
        String featureFlag;
        if (!Flags.manifestFlagging() || (featureFlag = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "featureFlag")) == null) {
            return false;
        }
        String featureFlag2 = featureFlag.strip();
        boolean negated = false;
        if (featureFlag2.startsWith("!")) {
            negated = true;
            featureFlag2 = featureFlag2.substring(1).strip();
        }
        Boolean flagValue = getFlagValue(featureFlag2);
        if (flagValue == null) {
            Slog.w(LOG_TAG, "Skipping element " + parser.getName() + " due to unknown feature flag " + featureFlag2);
            return true;
        }
        if (flagValue.booleanValue() != negated) {
            return false;
        }
        Slog.v(LOG_TAG, "Skipping element " + parser.getName() + " behind feature flag " + featureFlag2 + " = " + flagValue);
        return true;
    }

    public void addFlagValuesForTesting(Map<String, Boolean> flagValues) {
        this.mFlagValues.putAll(flagValues);
    }
}
