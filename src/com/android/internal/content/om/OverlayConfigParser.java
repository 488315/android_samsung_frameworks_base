package com.android.internal.content.om;

import android.content.pm.PackagePartitions;
import android.os.Build;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.content.om.OverlayScanner;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0060 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void readConfigFile(java.io.File r8, com.android.internal.content.om.OverlayScanner r9, java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> r10, com.android.internal.content.om.OverlayConfigParser.ParsingContext r11) {
        /*
            java.lang.String r0 = "OverlayConfig"
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.io.FileNotFoundException -> L78
            r1.<init>(r8)     // Catch: java.io.FileNotFoundException -> L78
            org.xmlpull.v1.XmlPullParser r2 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            r2.setInput(r1)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            java.lang.String r3 = "config"
            com.android.internal.util.XmlUtils.beginDocument(r2, r3)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            int r3 = r2.getDepth()     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
        L17:
            boolean r4 = com.android.internal.util.XmlUtils.nextElementWithin(r2, r3)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            if (r4 == 0) goto L64
            java.lang.String r4 = r2.getName()     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            int r5 = r4.hashCode()     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            r6 = -1091287984(0xffffffffbef44450, float:-0.47708368)
            r7 = 1
            if (r5 == r6) goto L3b
            r6 = 103785528(0x62fa438, float:3.303449E-35)
            if (r5 == r6) goto L31
            goto L45
        L31:
            java.lang.String r5 = "merge"
            boolean r5 = r4.equals(r5)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            if (r5 == 0) goto L45
            r5 = 0
            goto L46
        L3b:
            java.lang.String r5 = "overlay"
            boolean r5 = r4.equals(r5)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            if (r5 == 0) goto L45
            r5 = r7
            goto L46
        L45:
            r5 = -1
        L46:
            if (r5 == 0) goto L60
            if (r5 == r7) goto L5c
            java.lang.String r5 = "Tag %s is unknown in %s at %s"
            java.lang.String r6 = r2.getPositionDescription()     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            java.lang.Object[] r4 = new java.lang.Object[]{r4, r8, r6}     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            java.lang.String r4 = java.lang.String.format(r5, r4)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            android.util.Log.w(r0, r4)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            goto L17
        L5c:
            parseOverlay(r8, r2, r9, r10, r11)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            goto L17
        L60:
            parseMerge(r8, r2, r9, r10, r11)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6a
            goto L17
        L64:
            libcore.io.IoUtils.closeQuietly(r1)
            return
        L68:
            r8 = move-exception
            goto L74
        L6a:
            r8 = move-exception
            java.lang.String r9 = "Got exception parsing overlay configuration."
            android.util.Log.w(r0, r9, r8)     // Catch: java.lang.Throwable -> L68
            libcore.io.IoUtils.closeQuietly(r1)
            return
        L74:
            libcore.io.IoUtils.closeQuietly(r1)
            throw r8
        L78:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Couldn't find or open overlay configuration file "
            r9.<init>(r10)
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            android.util.Log.w(r0, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.content.om.OverlayConfigParser.readConfigFile(java.io.File, com.android.internal.content.om.OverlayScanner, java.util.Map, com.android.internal.content.om.OverlayConfigParser$ParsingContext):void");
    }

    public static String expandProperty(String str, SysPropWrapper sysPropWrapper) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf("${");
        if (indexOf == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, indexOf));
        int indexOf2 = str.indexOf("}", indexOf);
        if (indexOf2 == -1) {
            throw new IllegalStateException("Malformed property, unmatched braces, in: " + str);
        }
        int i = indexOf + 2;
        if (str.indexOf("${", i) != -1) {
            throw new IllegalStateException("Only a single property supported in path: " + str);
        }
        String substring = str.substring(i, indexOf2);
        if (!substring.startsWith("ro.")) {
            throw new IllegalStateException("Only read only properties can be used when merging RRO config files: " + substring);
        }
        String str2 = sysPropWrapper.get(substring);
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalStateException("Property is empty or doesn't exist: " + substring);
        }
        Log.d("OverlayConfig", String.format("Using property in overlay config path: \"%s\"", substring));
        sb.append(str2);
        int i2 = indexOf2 + 1;
        if (i2 < str.length()) {
            sb.append(str.substring(i2));
        }
        return sb.toString();
    }

    private static void parseMerge(File file, XmlPullParser xmlPullParser, OverlayScanner overlayScanner, Map<String, OverlayScanner.ParsedOverlayInfo> map, ParsingContext parsingContext) {
        try {
            String expandProperty = expandProperty(xmlPullParser.getAttributeValue(null, "path"), new SysPropWrapper() { // from class: com.android.internal.content.om.OverlayConfigParser$$ExternalSyntheticLambda0
                @Override // com.android.internal.content.om.OverlayConfigParser.SysPropWrapper
                public final String get(String str) {
                    String str2;
                    str2 = SystemProperties.get(str, "");
                    return str2;
                }
            });
            if (expandProperty == null) {
                throw new IllegalStateException(String.format("<merge> without path in %s at %s", file, xmlPullParser.getPositionDescription()));
            }
            if (expandProperty.startsWith("/")) {
                throw new IllegalStateException(String.format("Path %s must be relative to the directory containing overlay configurations  files in %s at %s ", expandProperty, file, xmlPullParser.getPositionDescription()));
            }
            int i = parsingContext.mMergeDepth;
            parsingContext.mMergeDepth = i + 1;
            if (i == 5) {
                throw new IllegalStateException(String.format("Maximum <merge> depth exceeded in %s at %s", file, xmlPullParser.getPositionDescription()));
            }
            try {
                File canonicalFile = new File(parsingContext.mPartition.getOverlayFolder(), CONFIG_DIRECTORY).getCanonicalFile();
                File canonicalFile2 = new File(canonicalFile, expandProperty).getCanonicalFile();
                if (!canonicalFile2.exists()) {
                    throw new IllegalStateException(String.format("Merged configuration file %s does not exist in %s at %s", expandProperty, file, xmlPullParser.getPositionDescription()));
                }
                if (!FileUtils.contains(canonicalFile, canonicalFile2)) {
                    throw new IllegalStateException(String.format("Merged file %s outside of configuration directory in %s at %s", canonicalFile2.getAbsolutePath(), canonicalFile2, xmlPullParser.getPositionDescription()));
                }
                readConfigFile(canonicalFile2, overlayScanner, map, parsingContext);
                parsingContext.mMergeDepth--;
            } catch (IOException e) {
                throw new IllegalStateException(String.format("Couldn't find or open merged configuration file %s in %s at %s", expandProperty, file, xmlPullParser.getPositionDescription()), e);
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
            boolean equals = "false".equals(attributeValue2);
            z = !equals;
            if (equals && parsingContext.mFoundMutableOverlay) {
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
