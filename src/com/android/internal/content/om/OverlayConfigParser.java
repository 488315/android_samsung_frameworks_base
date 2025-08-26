package com.android.internal.content.om;

import android.content.pm.PackagePartitions;
import android.os.Build;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.content.om.OverlayScanner;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public final class OverlayConfigParser {
    private static final String CONFIG_DEFAULT_FILENAME = "config/config.xml";
    private static final String CONFIG_DIRECTORY = "config";
    static final boolean DEFAULT_ENABLED_STATE = false;
    static final boolean DEFAULT_MUTABILITY = true;
    private static final int MAXIMUM_MERGE_DEPTH = 5;

    @FunctionalInterface
    public interface SysPropWrapper {
        String get(String str);
    }

    public static class ParsedConfigFile {
        public final int line;
        public final String path;
        public final String xml;

        ParsedConfigFile(String str, int i, String str2) {
            this.path = str;
            this.line = i;
            this.xml = str2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(getClass().getSimpleName());
            sb.append("{path=");
            sb.append(this.path);
            sb.append(", line=");
            sb.append(this.line);
            if (this.xml != null) {
                sb.append(", xml=");
                sb.append(this.xml);
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public static class ParsedConfiguration {
        public final boolean enabled;
        public final boolean mutable;
        public final String packageName;
        public final ParsedConfigFile parsedConfigFile;
        public final OverlayScanner.ParsedOverlayInfo parsedInfo;
        public final String policy;

        ParsedConfiguration(String str, boolean z, boolean z2, String str2, OverlayScanner.ParsedOverlayInfo parsedOverlayInfo, ParsedConfigFile parsedConfigFile) {
            this.packageName = str;
            this.enabled = z;
            this.mutable = z2;
            this.policy = str2;
            this.parsedInfo = parsedOverlayInfo;
            this.parsedConfigFile = parsedConfigFile;
        }

        public String toString() {
            return getClass().getSimpleName() + String.format("{packageName=%s, enabled=%s, mutable=%s, policy=%s, parsedInfo=%s, parsedConfigFile=%s}", this.packageName, Boolean.valueOf(this.enabled), Boolean.valueOf(this.mutable), this.policy, this.parsedInfo, this.parsedConfigFile);
        }
    }

    public static class OverlayPartition extends PackagePartitions.SystemPartition {
        static final String POLICY_ODM = "odm";
        static final String POLICY_OEM = "oem";
        static final String POLICY_PRODUCT = "product";
        static final String POLICY_PUBLIC = "public";
        static final String POLICY_SYSTEM = "system";
        static final String POLICY_VENDOR = "vendor";
        public final String policy;

        public OverlayPartition(PackagePartitions.SystemPartition systemPartition) {
            super(systemPartition);
            this.policy = policyForPartition(systemPartition);
        }

        OverlayPartition(File file, PackagePartitions.SystemPartition systemPartition) {
            super(file, systemPartition);
            this.policy = policyForPartition(systemPartition);
        }

        private static String policyForPartition(PackagePartitions.SystemPartition systemPartition) {
            int i = systemPartition.type;
            if (i == 0) {
                return "system";
            }
            if (i == 1) {
                return "vendor";
            }
            if (i == 2) {
                return "odm";
            }
            if (i == 3) {
                return "oem";
            }
            if (i == 4) {
                return "product";
            }
            if (i == 5) {
                return "system";
            }
            throw new IllegalStateException("Unable to determine policy for " + systemPartition.getFolder());
        }
    }

    private static class ParsingContext {
        private final ArraySet<String> mConfiguredOverlays;
        private boolean mFoundMutableOverlay;
        private int mMergeDepth;
        private final ArrayList<ParsedConfiguration> mOrderedConfigurations;
        private final OverlayPartition mPartition;

        private ParsingContext(OverlayPartition overlayPartition) {
            this.mOrderedConfigurations = new ArrayList<>();
            this.mConfiguredOverlays = new ArraySet<>();
            this.mPartition = overlayPartition;
        }
    }

    static ArrayList<ParsedConfiguration> getConfigurations(OverlayPartition overlayPartition, OverlayScanner overlayScanner, Map<String, OverlayScanner.ParsedOverlayInfo> map, List<String> list) {
        if (overlayScanner != null) {
            if (overlayPartition.getOverlayFolder() != null) {
                overlayScanner.scanDir(overlayPartition.getOverlayFolder());
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                overlayScanner.scanDir(new File("/apex/" + it.next() + "/overlay/"));
            }
        }
        if (overlayPartition.getOverlayFolder() == null) {
            return null;
        }
        File file = new File(overlayPartition.getOverlayFolder(), CONFIG_DEFAULT_FILENAME);
        if (!file.exists()) {
            return null;
        }
        ParsingContext parsingContext = new ParsingContext(overlayPartition);
        readConfigFile(file, overlayScanner, map, parsingContext);
        return parsingContext.mOrderedConfigurations;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void readConfigFile(File file, OverlayScanner overlayScanner, Map<String, OverlayScanner.ParsedOverlayInfo> map, ParsingContext parsingContext) {
        char c;
        try {
            FileReader fileReader = new FileReader(file);
            try {
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                xmlPullParserNewPullParser.setInput(fileReader);
                XmlUtils.beginDocument(xmlPullParserNewPullParser, CONFIG_DIRECTORY);
                int depth = xmlPullParserNewPullParser.getDepth();
                while (XmlUtils.nextElementWithin(xmlPullParserNewPullParser, depth)) {
                    String name = xmlPullParserNewPullParser.getName();
                    int iHashCode = name.hashCode();
                    if (iHashCode != -1091287984) {
                        c = (iHashCode == 103785528 && name.equals("merge")) ? (char) 0 : (char) 65535;
                    } else if (name.equals("overlay")) {
                        c = 1;
                    }
                    if (c == 0) {
                        parseMerge(file, xmlPullParserNewPullParser, overlayScanner, map, parsingContext);
                    } else if (c != 1) {
                        Log.w("OverlayConfig", String.format("Tag %s is unknown in %s at %s", name, file, xmlPullParserNewPullParser.getPositionDescription()));
                    } else {
                        parseOverlay(file, xmlPullParserNewPullParser, overlayScanner, map, parsingContext);
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                Log.w("OverlayConfig", "Got exception parsing overlay configuration.", e);
            } finally {
                IoUtils.closeQuietly(fileReader);
            }
        } catch (FileNotFoundException unused) {
            Log.w("OverlayConfig", "Couldn't find or open overlay configuration file " + file);
        }
    }

    public static String expandProperty(String str, SysPropWrapper sysPropWrapper) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf("${");
        if (iIndexOf == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, iIndexOf));
        int iIndexOf2 = str.indexOf("}", iIndexOf);
        if (iIndexOf2 == -1) {
            throw new IllegalStateException("Malformed property, unmatched braces, in: " + str);
        }
        int i = iIndexOf + 2;
        if (str.indexOf("${", i) != -1) {
            throw new IllegalStateException("Only a single property supported in path: " + str);
        }
        String strSubstring = str.substring(i, iIndexOf2);
        if (!strSubstring.startsWith("ro.")) {
            throw new IllegalStateException("Only read only properties can be used when merging RRO config files: " + strSubstring);
        }
        String str2 = sysPropWrapper.get(strSubstring);
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalStateException("Property is empty or doesn't exist: " + strSubstring);
        }
        Log.d("OverlayConfig", String.format("Using property in overlay config path: \"%s\"", strSubstring));
        sb.append(str2);
        int i2 = iIndexOf2 + 1;
        if (i2 < str.length()) {
            sb.append(str.substring(i2));
        }
        return sb.toString();
    }

    private static void parseMerge(File file, XmlPullParser xmlPullParser, OverlayScanner overlayScanner, Map<String, OverlayScanner.ParsedOverlayInfo> map, ParsingContext parsingContext) throws IOException {
        try {
            String strExpandProperty = expandProperty(xmlPullParser.getAttributeValue(null, "path"), new SysPropWrapper() { // from class: com.android.internal.content.om.OverlayConfigParser$$ExternalSyntheticLambda0
                @Override // com.android.internal.content.om.OverlayConfigParser.SysPropWrapper
                public final String get(String str) {
                    return SystemProperties.get(str, "");
                }
            });
            if (strExpandProperty == null) {
                throw new IllegalStateException(String.format("<merge> without path in %s at %s", file, xmlPullParser.getPositionDescription()));
            }
            if (strExpandProperty.startsWith("/")) {
                throw new IllegalStateException(String.format("Path %s must be relative to the directory containing overlay configurations  files in %s at %s ", strExpandProperty, file, xmlPullParser.getPositionDescription()));
            }
            int i = parsingContext.mMergeDepth;
            parsingContext.mMergeDepth = i + 1;
            if (i == 5) {
                throw new IllegalStateException(String.format("Maximum <merge> depth exceeded in %s at %s", file, xmlPullParser.getPositionDescription()));
            }
            try {
                File canonicalFile = new File(parsingContext.mPartition.getOverlayFolder(), CONFIG_DIRECTORY).getCanonicalFile();
                File canonicalFile2 = new File(canonicalFile, strExpandProperty).getCanonicalFile();
                if (!canonicalFile2.exists()) {
                    throw new IllegalStateException(String.format("Merged configuration file %s does not exist in %s at %s", strExpandProperty, file, xmlPullParser.getPositionDescription()));
                }
                if (!FileUtils.contains(canonicalFile, canonicalFile2)) {
                    throw new IllegalStateException(String.format("Merged file %s outside of configuration directory in %s at %s", canonicalFile2.getAbsolutePath(), canonicalFile2, xmlPullParser.getPositionDescription()));
                }
                readConfigFile(canonicalFile2, overlayScanner, map, parsingContext);
                parsingContext.mMergeDepth--;
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Couldn't find or open merged configuration file %s in %s at %s", strExpandProperty, file, xmlPullParser.getPositionDescription()), e);
            }
        } catch (IllegalStateException e2) {
            throw new IllegalStateException(String.format("<merge> path expand error in %s at %s", file, xmlPullParser.getPositionDescription()), e2);
        }
    }

    private static void parseOverlay(File file, XmlPullParser xmlPullParser, OverlayScanner overlayScanner, Map<String, OverlayScanner.ParsedOverlayInfo> map, ParsingContext parsingContext) {
        OverlayScanner.ParsedOverlayInfo parsedOverlayInfo;
        boolean z;
        Preconditions.checkArgument((overlayScanner == null) != (map == null), "scanner and packageManagerOverlayInfos cannot be both null or both non-null");
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (attributeValue == null) {
            throw new IllegalStateException(String.format("\"<overlay> without package in %s at %s", file, xmlPullParser.getPositionDescription()));
        }
        if (overlayScanner != null) {
            OverlayScanner.ParsedOverlayInfo parsedInfo = overlayScanner.getParsedInfo(attributeValue);
            if (parsedInfo == null && overlayScanner.isExcludedOverlayPackage(attributeValue, parsingContext.mPartition)) {
                Log.d("OverlayConfig", "overlay " + attributeValue + " in partition " + parsingContext.mPartition.getOverlayFolder() + " is ignored.");
                return;
            }
            if (parsedInfo == null || !parsingContext.mPartition.containsOverlay(parsedInfo.path)) {
                throw new IllegalStateException(String.format("overlay %s not present in partition %s in %s at %s", attributeValue, parsingContext.mPartition.getOverlayFolder(), file, xmlPullParser.getPositionDescription()));
            }
            parsedOverlayInfo = parsedInfo;
        } else {
            if (map.get(attributeValue) == null) {
                Log.d("OverlayConfig", "overlay " + attributeValue + " in partition " + parsingContext.mPartition.getOverlayFolder() + " is ignored.");
                return;
            }
            parsedOverlayInfo = null;
        }
        if (parsingContext.mConfiguredOverlays.contains(attributeValue)) {
            throw new IllegalStateException(String.format("overlay %s configured multiple times in a single partition in %s at %s", attributeValue, file, xmlPullParser.getPositionDescription()));
        }
        boolean z2 = xmlPullParser.getAttributeValue(null, "enabled") != null ? !"false".equals(r13) : false;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "mutable");
        if (attributeValue2 != null) {
            boolean zEquals = "false".equals(attributeValue2);
            z = !zEquals;
            if (zEquals && parsingContext.mFoundMutableOverlay) {
                throw new IllegalStateException(String.format("immutable overlays must precede mutable overlays: found in %s at %s", file, xmlPullParser.getPositionDescription()));
            }
        } else {
            z = true;
        }
        if (z) {
            parsingContext.mFoundMutableOverlay = true;
        } else if (!z2) {
            Log.w("OverlayConfig", "found default-disabled immutable overlay " + attributeValue);
        }
        ParsedConfiguration parsedConfiguration = new ParsedConfiguration(attributeValue, z2, z, parsingContext.mPartition.policy, parsedOverlayInfo, new ParsedConfigFile(file.getPath().intern(), xmlPullParser.getLineNumber(), (Build.IS_ENG || Build.IS_USERDEBUG) ? currentParserContextToString(xmlPullParser) : null));
        parsingContext.mConfiguredOverlays.add(attributeValue);
        parsingContext.mOrderedConfigurations.add(parsedConfiguration);
    }

    private static String currentParserContextToString(XmlPullParser xmlPullParser) {
        StringBuilder sb = new StringBuilder("<");
        sb.append(xmlPullParser.getName());
        sb.append(" ");
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            sb.append(xmlPullParser.getAttributeName(i));
            sb.append("=\"");
            sb.append(xmlPullParser.getAttributeValue(i));
            sb.append("\" ");
        }
        sb.append("/>");
        return sb.toString();
    }
}
