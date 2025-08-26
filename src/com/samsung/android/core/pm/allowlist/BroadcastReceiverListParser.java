package com.samsung.android.core.pm.allowlist;

import android.content.IntentFilter;
import android.os.Environment;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class BroadcastReceiverListParser {
    public static boolean FW_BR_ALLOW_LIST_WITH_SCPM = true;
    static final String TAG = "BRListParser";
    private static final String TAG_ACTION = "action";
    private static final String TAG_ALLOWED_PACKAGE = "allowed-packages";
    private static final String TAG_FEATURE = "feature";
    private static final String TAG_INTENT = "intent";
    static final String TAG_NAME = "name";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_RESTRICTED_INTENTS = "restricted-intents";
    private static final String TAG_RESTRICTED_PACKAGE = "restricted-packages";
    private static final String TAG_VALUE = "value";
    private static final String TAG_VERSION = "version";
    private static final String WORK_COMP_CHANGED = "work_comp_changed";
    private final List<String> mRestrictedIntents = new ArrayList();
    private final Set<String> mAllowedPkgNames = new HashSet();
    private final List<String> mAllowedPkgPrefixNames = new ArrayList();
    private final Set<String> mRestrictedPkgNames = new HashSet();
    private final List<String> mRestrictedPkgPrefixNames = new ArrayList();
    private final Map<String, Set<String>> mIntentMap = new ArrayMap();
    private boolean mIsWorkCompChangedEnabled = true;

    public Map<String, Set<String>> getIntentMap() {
        return this.mIntentMap;
    }

    public Map<String, Set<String>> getPackageMap() {
        final ArrayMap arrayMap = new ArrayMap();
        this.mIntentMap.forEach(new BiConsumer() { // from class: com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                BroadcastReceiverListParser.lambda$getPackageMap$0(arrayMap, (String) obj, (Set) obj2);
            }
        });
        return arrayMap;
    }

    static /* synthetic */ void lambda$getPackageMap$0(Map map, String str, Set set) {
        if (set == null || set.isEmpty()) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Set hashSet = (Set) map.get(str2);
            if (hashSet == null) {
                hashSet = new HashSet();
            }
            if (!hashSet.contains(str)) {
                hashSet.add(str);
            }
            map.put(str2, hashSet);
        }
    }

    public List<String> getRestricedIntent() {
        return this.mRestrictedIntents;
    }

    public Set<String> getAllowedPackageNames() {
        return this.mAllowedPkgNames;
    }

    public List<String> getAllowedPackagePrefixNames() {
        return this.mAllowedPkgPrefixNames;
    }

    public Set<String> getRestrictedPackageNames() {
        return this.mRestrictedPkgNames;
    }

    public List<String> getRestrictedPackagePrefixNames() {
        return this.mRestrictedPkgPrefixNames;
    }

    public boolean isWorkCompChangedEnabled() {
        return this.mIsWorkCompChangedEnabled;
    }

    public boolean isInAllowList(String str, String str2, IntentFilter intentFilter) {
        if (isAllowedPackage(str2) || isAllowedIntentOfPackage(str, str2)) {
            return true;
        }
        if (isPackageXXXIntent(str) && hasPackageSSP(intentFilter)) {
            return true;
        }
        Log.e(TAG, "isInAllowList() Intent=" + str + " Package=" + str2 + " is not in allowlist!");
        return false;
    }

    public boolean isInRestrictedPackageList(String str) {
        if (this.mRestrictedPkgNames.contains(str)) {
            return true;
        }
        Iterator<String> it = this.mRestrictedPkgPrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedPackage(String str) {
        if (this.mAllowedPkgNames.contains(str)) {
            return true;
        }
        Iterator<String> it = this.mAllowedPkgPrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedIntentOfPackage(String str, String str2) {
        return this.mIntentMap.containsKey(str) && this.mIntentMap.get(str).contains(str2);
    }

    public void parseAllowList() throws XmlPullParserException, IOException {
        parseAllowList(null);
    }

    public void parseAllowList(String str) throws XmlPullParserException, IOException {
        parseAllowListInternal(str);
    }

    private void parseAllowListInternal(String str) throws XmlPullParserException, IOException {
        if (TextUtils.isEmpty(str)) {
            str = Environment.getRootDirectory() + "/etc/broadcast_allowlist.xml";
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.d(TAG, "No xml file exists.");
        }
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                xmlPullParserNewPullParser.setInput(fileInputStream, null);
                parseAllowListElement(xmlPullParserNewPullParser);
                fileInputStream.close();
            } finally {
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Failed to parse allowlist. FileNotFoundException " + e);
        } catch (IOException e2) {
            Log.d(TAG, "Failed to parse allowlist. IOException " + e2);
        } catch (XmlPullParserException e3) {
            Log.e(TAG, "Failed to parse allowlist. XmlPullParserException " + e3);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseAllowListElement(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.next();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            char c = 1;
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                name.hashCode();
                switch (name.hashCode()) {
                    case -1183762788:
                        if (name.equals("intent")) {
                            c = 0;
                            break;
                        } else {
                            c = 65535;
                            break;
                        }
                    case -979207434:
                        if (!name.equals("feature")) {
                        }
                        break;
                    case 276706162:
                        if (name.equals(TAG_ALLOWED_PACKAGE)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 351608024:
                        if (name.equals("version")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 1153629669:
                        if (name.equals(TAG_RESTRICTED_INTENTS)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1583351327:
                        if (name.equals(TAG_RESTRICTED_PACKAGE)) {
                            c = 5;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        String attributeValue = xmlPullParser.getAttributeValue(null, "action");
                        if (TextUtils.isEmpty(attributeValue)) {
                            break;
                        } else {
                            this.mIntentMap.put(attributeValue, new HashSet(parsePackages(xmlPullParser)));
                            break;
                        }
                    case 1:
                        if (WORK_COMP_CHANGED.equals(xmlPullParser.getAttributeValue(null, "name"))) {
                            this.mIsWorkCompChangedEnabled = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "value"));
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        for (String str : parsePackages(xmlPullParser)) {
                            if (str.contains("*")) {
                                this.mAllowedPkgPrefixNames.add(str.replace("*", ""));
                            } else {
                                this.mAllowedPkgNames.add(str);
                            }
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        this.mRestrictedIntents.addAll(parseIntents(xmlPullParser));
                        break;
                    case 5:
                        for (String str2 : parsePackages(xmlPullParser)) {
                            if (str2.contains("*")) {
                                this.mRestrictedPkgPrefixNames.add(str2.replace("*", ""));
                            } else {
                                this.mRestrictedPkgNames.add(str2);
                            }
                        }
                        break;
                    default:
                        Log.d(TAG, "Invalid element name: " + name);
                        break;
                }
            }
        }
    }

    List<String> parsePackages(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals("package")) {
                String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                if (!TextUtils.isEmpty(attributeValue) && !arrayList.contains(attributeValue)) {
                    arrayList.add(attributeValue);
                }
            }
        }
        return arrayList;
    }

    private List<String> parseIntents(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals("intent")) {
                String attributeValue = xmlPullParser.getAttributeValue(null, "action");
                if (!TextUtils.isEmpty(attributeValue) && !arrayList.contains(attributeValue)) {
                    arrayList.add(attributeValue);
                }
            }
        }
        return arrayList;
    }

    public static boolean isPackageXXXIntent(String str) {
        return str != null && str.startsWith("android.intent.action.PACKAGE_");
    }

    public static boolean hasPackageSSP(IntentFilter intentFilter) {
        return intentFilter != null && intentFilter.hasDataScheme("package") && intentFilter.countDataSchemeSpecificParts() > 0;
    }
}
