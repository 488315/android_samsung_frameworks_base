package com.samsung.android.server.pm.allowlist;

import android.content.IntentFilter;
import android.os.Environment;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Xml;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
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

/* loaded from: classes2.dex */
public class BroadcastReceiverListParser {
    public static boolean FW_BR_ALLOW_LIST_WITH_SCPM = true;
    public final List mRestrictedIntents = new ArrayList();
    public final Set mAllowedPkgNames = new HashSet();
    public final List mAllowedPkgPrefixNames = new ArrayList();
    public final Set mRestrictedPkgNames = new HashSet();
    public final List mRestrictedPkgPrefixNames = new ArrayList();
    public final Map mIntentMap = new ArrayMap();
    public boolean mIsWorkCompChangedEnabled = true;

    public Map getPackageMap() {
        final ArrayMap arrayMap = new ArrayMap();
        this.mIntentMap.forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.allowlist.BroadcastReceiverListParser$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                BroadcastReceiverListParser.lambda$getPackageMap$0(arrayMap, (String) obj, (Set) obj2);
            }
        });
        return arrayMap;
    }

    public static /* synthetic */ void lambda$getPackageMap$0(Map map, String str, Set set) {
        if (set == null || set.isEmpty()) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Set set2 = (Set) map.get(str2);
            if (set2 == null) {
                set2 = new HashSet();
            }
            if (!set2.contains(str)) {
                set2.add(str);
            }
            map.put(str2, set2);
        }
    }

    public List getRestricedIntent() {
        return this.mRestrictedIntents;
    }

    public Set getAllowedPackageNames() {
        return this.mAllowedPkgNames;
    }

    public List getAllowedPackagePrefixNames() {
        return this.mAllowedPkgPrefixNames;
    }

    public Set getRestrictedPackageNames() {
        return this.mRestrictedPkgNames;
    }

    public List getRestrictedPackagePrefixNames() {
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
        Log.e("BRListParser", "isInAllowList() Intent=" + str + " Package=" + str2 + " is not in allowlist!");
        return false;
    }

    public boolean isInRestrictedPackageList(String str) {
        if (this.mRestrictedPkgNames.contains(str)) {
            return true;
        }
        Iterator it = this.mRestrictedPkgPrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final boolean isAllowedPackage(String str) {
        if (this.mAllowedPkgNames.contains(str)) {
            return true;
        }
        Iterator it = this.mAllowedPkgPrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final boolean isAllowedIntentOfPackage(String str, String str2) {
        return this.mIntentMap.containsKey(str) && ((Set) this.mIntentMap.get(str)).contains(str2);
    }

    public void parseAllowList() {
        parseAllowList(null);
    }

    public void parseAllowList(String str) {
        parseAllowListInternal(str);
    }

    public final void parseAllowListInternal(String str) {
        if (TextUtils.isEmpty(str)) {
            str = Environment.getRootDirectory() + "/etc/broadcast_allowlist.xml";
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.d("BRListParser", "No xml file exists.");
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                parseAllowListElement(newPullParser);
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.e("BRListParser", "Failed to parse allowlist. FileNotFoundException " + e);
        } catch (IOException e2) {
            Log.d("BRListParser", "Failed to parse allowlist. IOException " + e2);
        } catch (XmlPullParserException e3) {
            Log.e("BRListParser", "Failed to parse allowlist. XmlPullParserException " + e3);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0063, code lost:
    
        if (r1.equals(com.samsung.android.knox.custom.LauncherConfigurationInternal.KEY_FEATURE_INT) == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parseAllowListElement(XmlPullParser xmlPullParser) {
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
                        if (name.equals(KnoxCustomManagerService.INTENT)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -979207434:
                        break;
                    case 276706162:
                        if (name.equals("allowed-packages")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 351608024:
                        if (name.equals("version")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1153629669:
                        if (name.equals("restricted-intents")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1583351327:
                        if (name.equals("restricted-packages")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
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
                        if ("work_comp_changed".equals(xmlPullParser.getAttributeValue(null, "name"))) {
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
                        Log.d("BRListParser", "Invalid element name: " + name);
                        break;
                }
            }
        }
    }

    public List parsePackages(XmlPullParser xmlPullParser) {
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

    public final List parseIntents(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals(KnoxCustomManagerService.INTENT)) {
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
