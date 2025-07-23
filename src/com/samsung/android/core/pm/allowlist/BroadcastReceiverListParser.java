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

    public void parseAllowList() {
        parseAllowList(null);
    }

    public void parseAllowList(String str) {
        parseAllowListInternal(str);
    }

    private void parseAllowListInternal(String str) {
        if (TextUtils.isEmpty(str)) {
            str = Environment.getRootDirectory() + "/etc/broadcast_allowlist.xml";
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.d(TAG, "No xml file exists.");
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                parseAllowListElement(newPullParser);
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
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0063, code lost:
    
        if (r1.equals("feature") == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseAllowListElement(org.xmlpull.v1.XmlPullParser r8) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser.parseAllowListElement(org.xmlpull.v1.XmlPullParser):void");
    }

    List<String> parsePackages(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
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

    private List<String> parseIntents(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
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
