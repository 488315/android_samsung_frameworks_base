package com.samsung.android.core.pm.allowlist;

import android.content.IntentFilter;
import android.os.Environment;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
        final Map<String, Set<String>> packageMap = new ArrayMap<>();
        this.mIntentMap.forEach(
                new BiConsumer() { // from class:
                                   // com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        BroadcastReceiverListParser.lambda$getPackageMap$0(
                                packageMap, (String) obj, (Set) obj2);
                    }
                });
        return packageMap;
    }

    static /* synthetic */ void lambda$getPackageMap$0(
            Map packageMap, String actionName, Set packages) {
        if (packages != null && !packages.isEmpty()) {
            Iterator it = packages.iterator();
            while (it.hasNext()) {
                String pkgName = (String) it.next();
                Set<String> actions = (Set) packageMap.get(pkgName);
                if (actions == null) {
                    actions = new HashSet<>();
                }
                if (!actions.contains(actionName)) {
                    actions.add(actionName);
                }
                packageMap.put(pkgName, actions);
            }
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

    public boolean isInAllowList(String action, String pkgName, IntentFilter filter) {
        if (isAllowedPackage(pkgName) || isAllowedIntentOfPackage(action, pkgName)) {
            return true;
        }
        if (isPackageXXXIntent(action) && hasPackageSSP(filter)) {
            return true;
        }
        Log.e(
                TAG,
                "isInAllowList() Intent="
                        + action
                        + " Package="
                        + pkgName
                        + " is not in allowlist!");
        return false;
    }

    public boolean isInRestrictedPackageList(String pkgName) {
        if (this.mRestrictedPkgNames.contains(pkgName)) {
            return true;
        }
        for (String prefix : this.mRestrictedPkgPrefixNames) {
            if (pkgName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedPackage(String pkgName) {
        if (this.mAllowedPkgNames.contains(pkgName)) {
            return true;
        }
        for (String prefix : this.mAllowedPkgPrefixNames) {
            if (pkgName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedIntentOfPackage(String intent, String pkgName) {
        return this.mIntentMap.containsKey(intent) && this.mIntentMap.get(intent).contains(pkgName);
    }

    public void parseAllowList() {
        parseAllowList(null);
    }

    public void parseAllowList(String path) {
        parseAllowListInternal(path);
    }

    private void parseAllowListInternal(String path) {
        if (TextUtils.isEmpty(path)) {
            path = Environment.getRootDirectory() + "/etc/broadcast_allowlist.xml";
        }
        File xmlFile = new File(path);
        if (!xmlFile.exists()) {
            Log.d(TAG, "No xml file exists.");
        }
        XmlPullParser parser = Xml.newPullParser();
        try {
            FileInputStream fin = new FileInputStream(xmlFile);
            try {
                parser.setInput(fin, null);
                parseAllowListElement(parser);
                fin.close();
            } catch (Throwable th) {
                try {
                    fin.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
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
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0062, code lost:

       if (r5.equals("intent") != false) goto L35;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseAllowListElement(org.xmlpull.v1.XmlPullParser r11)
            throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser.parseAllowListElement(org.xmlpull.v1.XmlPullParser):void");
    }

    List<String> parsePackages(XmlPullParser parser) throws IOException, XmlPullParserException {
        int outerDepth = parser.getDepth();
        List<String> packages = new ArrayList<>();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            }
            if (type != 3 && type != 4) {
                String tagName = parser.getName();
                if (tagName.equals("package")) {
                    String packageName = parser.getAttributeValue(null, "name");
                    if (!TextUtils.isEmpty(packageName) && !packages.contains(packageName)) {
                        packages.add(packageName);
                    }
                }
            }
        }
        return packages;
    }

    private List<String> parseIntents(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        int outerDepth = parser.getDepth();
        List<String> packages = new ArrayList<>();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            }
            if (type != 3 && type != 4) {
                String tagName = parser.getName();
                if (tagName.equals("intent")) {
                    String packageName = parser.getAttributeValue(null, "action");
                    if (!TextUtils.isEmpty(packageName) && !packages.contains(packageName)) {
                        packages.add(packageName);
                    }
                }
            }
        }
        return packages;
    }

    public static boolean isPackageXXXIntent(String action) {
        return action != null && action.startsWith("android.intent.action.PACKAGE_");
    }

    public static boolean hasPackageSSP(IntentFilter filter) {
        return filter != null
                && filter.hasDataScheme("package")
                && filter.countDataSchemeSpecificParts() > 0;
    }
}
