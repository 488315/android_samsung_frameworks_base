package com.att.iqi.libs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Pair;
import com.att.iqi.lib.IQIManager;
import com.att.iqi.libs.PreferenceStore;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IQIConcierge {
    private static final String ACTION_MCC_MNC_VALIDATION_STATE_CHANGED = "com.att.iqi.action.ACTION_MCC_MNC_VALIDATION_STATE_CHANGED";
    private static final String IQI_MCCMNC_RESOURCE = "/mccmnc.xml";
    private static final String PERMISSION_MODIFY_MNC_MCC_VALIDATION_STATE = "com.att.iqi.permission.MODIFY_MNC_MCC_VALIDATION_STATE";
    private static final String VERSION = "14.1-skye";
    private static IQIConcierge sInstance = null;
    private static boolean sMccMncValidationDisabled = false;
    private static boolean sSimDisabled = true;
    private static final String sXmlAttributeMccName = "mcc";
    private static final String sXmlAttributeMncName = "mnc";
    private static final String sXmlTagName = "carrier";
    private final BroadcastReceiver mMncMccValidationStateChangedReceiver;
    private static final String BRIDGE_LIBRARY_NAME = System.mapLibraryName("iqi_bridge");
    private static final ArrayList sMCCMNCs = createNetIdPairList();

    private IQIConcierge(Context context) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.att.iqi.libs.IQIConcierge.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (TextUtils.equals(intent.getAction(), IQIConcierge.ACTION_MCC_MNC_VALIDATION_STATE_CHANGED)) {
                    PreferenceStore.getInstance().setBoolean(PreferenceStore.PREF_DISABLE_MCC_MNC_VALIDATION, intent.getBooleanExtra("disabled", false));
                }
            }
        };
        this.mMncMccValidationStateChangedReceiver = broadcastReceiver;
        LogUtil.loge("IQI Concierge version: 14.1-skye");
        IQIManager.getInstance();
        context.registerReceiver(broadcastReceiver, new IntentFilter(ACTION_MCC_MNC_VALIDATION_STATE_CHANGED), PERMISSION_MODIFY_MNC_MCC_VALIDATION_STATE, WorkerThread.getHandler(), 2);
        PreferenceStore.getInstance().registerPreferenceChangeListener(new PreferenceStore.PreferenceChangeListener() { // from class: com.att.iqi.libs.IQIConcierge$$ExternalSyntheticLambda0
            @Override // com.att.iqi.libs.PreferenceStore.PreferenceChangeListener
            public final void onPreferenceChanged(String str) {
                IQIConcierge.this.lambda$new$0(str);
            }
        });
        updateMccMncValidationStateFromPrefStore();
    }

    private static void copyBridgeLibrary(String str) {
        Path path = Paths.get(str, BRIDGE_LIBRARY_NAME);
        String string = PreferenceStore.getInstance().getString(PreferenceStore.PREF_BRIDGE_LIBRARY_PATH, "");
        if (string.isEmpty()) {
            if (!Files.exists(path, new LinkOption[0])) {
                LogUtil.logd("File not found in: " + path.toString());
                return;
            } else {
                LogUtil.logd("storing packageLibrary [" + path + "]");
                PreferenceStore.getInstance().setString(PreferenceStore.PREF_BRIDGE_LIBRARY_PATH, path.toString());
                return;
            }
        }
        Path path2 = Paths.get(string, new String[0]);
        LogUtil.logd("packageLibrary [" + path + "] systemLibrary [" + path2 + "]");
        if (!shouldCopy(path, path2)) {
            LogUtil.logd("No copy needed");
            return;
        }
        LogUtil.logd("Performing copy...");
        try {
            Files.copy(path2, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            Files.setPosixFilePermissions(path, new HashSet(Arrays.asList(PosixFilePermission.OTHERS_EXECUTE, PosixFilePermission.OTHERS_READ, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.GROUP_READ, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE)));
            LogUtil.logd("Copy completed");
        } catch (Exception e) {
            LogUtil.loge(e.toString(), e);
        }
    }

    private static ArrayList createNetIdPairList() {
        HashSet hashSet = new HashSet();
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            InputStream resourceAsStream = IQIConcierge.class.getResourceAsStream(IQI_MCCMNC_RESOURCE);
            newPullParser.setInput(resourceAsStream, null);
            parseXmlConfig(newPullParser, hashSet);
            resourceAsStream.close();
        } catch (FileNotFoundException unused) {
            LogUtil.loge("IQI XML resource not found");
        } catch (IOException unused2) {
            LogUtil.loge("XML resource reader failure");
        } catch (XmlPullParserException unused3) {
            LogUtil.loge("failed to start XML parser");
        }
        return new ArrayList(hashSet);
    }

    private static String getNativeLibraryDirPath(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo(str, 1024).applicationInfo;
            if (applicationInfo != null) {
                return applicationInfo.nativeLibraryDir;
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new IQIConcierge(context);
        }
    }

    public static boolean isServiceBindingAllowed() {
        int integer = PreferenceStore.getInstance().getInteger(PreferenceStore.PREF_SERVICE_STATE, 1);
        boolean z = (sMccMncValidationDisabled || !sSimDisabled) && integer == 1;
        StringBuilder sb = new StringBuilder("isServiceBindingAllowed? ");
        sb.append(z ? "YES" : "NO");
        sb.append(" [sMccMncValidationDisabled = ");
        sb.append(sMccMncValidationDisabled);
        sb.append(" sSimDisabled = ");
        sb.append(sSimDisabled);
        sb.append(" serviceState = ");
        sb.append(integer);
        sb.append("]");
        LogUtil.logd(sb.toString());
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        if (TextUtils.equals(str, PreferenceStore.PREF_DISABLE_MCC_MNC_VALIDATION)) {
            updateMccMncValidationStateFromPrefStore();
        }
    }

    public static void loadBridgeLibrary(Context context, String str) {
        String nativeLibraryDirPath = getNativeLibraryDirPath(context, str);
        LogUtil.logd("Native library dir path: " + nativeLibraryDirPath);
        if (nativeLibraryDirPath != null) {
            copyBridgeLibrary(nativeLibraryDirPath);
        }
    }

    private static boolean matchesNetId(SubscriptionInfo subscriptionInfo) {
        String mccString = subscriptionInfo.getMccString();
        String mncString = subscriptionInfo.getMncString();
        LogUtil.logd("sub netId " + mccString + " " + mncString);
        Iterator it = sMCCMNCs.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            LogUtil.logd("config netId " + ((String) pair.first) + " " + ((String) pair.second));
            if (((String) pair.second).equals(mncString) && ((String) pair.first).equals(mccString)) {
                return true;
            }
        }
        return sMCCMNCs.isEmpty();
    }

    private static void parseXmlConfig(XmlPullParser xmlPullParser, HashSet hashSet) throws XmlPullParserException, IOException {
        String name;
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 3 && (name = xmlPullParser.getName()) != null && name.equalsIgnoreCase(sXmlTagName)) {
                Pair pair = new Pair(xmlPullParser.getAttributeValue(null, sXmlAttributeMccName), xmlPullParser.getAttributeValue(null, sXmlAttributeMncName));
                LogUtil.logd("read tag in XML: " + ((String) pair.first) + " " + ((String) pair.second));
                hashSet.add(pair);
            }
            eventType = xmlPullParser.next();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean shouldCopy(java.nio.file.Path r8, java.nio.file.Path r9) {
        /*
            r0 = 0
            java.nio.file.LinkOption[] r1 = new java.nio.file.LinkOption[r0]
            boolean r1 = java.nio.file.Files.exists(r8, r1)
            java.nio.file.LinkOption[] r2 = new java.nio.file.LinkOption[r0]
            boolean r2 = java.nio.file.Files.exists(r9, r2)
            r3 = -1
            if (r1 == 0) goto L2a
            java.lang.Class<java.nio.file.attribute.BasicFileAttributes> r5 = java.nio.file.attribute.BasicFileAttributes.class
            java.nio.file.LinkOption[] r6 = new java.nio.file.LinkOption[r0]     // Catch: java.io.IOException -> L22
            java.nio.file.attribute.BasicFileAttributes r8 = java.nio.file.Files.readAttributes(r8, r5, r6)     // Catch: java.io.IOException -> L22
            java.nio.file.attribute.FileTime r8 = r8.creationTime()     // Catch: java.io.IOException -> L22
            long r5 = r8.toMillis()     // Catch: java.io.IOException -> L22
            goto L2b
        L22:
            r8 = move-exception
            java.lang.String r5 = r8.toString()
            com.att.iqi.libs.LogUtil.loge(r5, r8)
        L2a:
            r5 = r3
        L2b:
            if (r2 == 0) goto L46
            java.lang.Class<java.nio.file.attribute.BasicFileAttributes> r8 = java.nio.file.attribute.BasicFileAttributes.class
            java.nio.file.LinkOption[] r7 = new java.nio.file.LinkOption[r0]     // Catch: java.io.IOException -> L3e
            java.nio.file.attribute.BasicFileAttributes r8 = java.nio.file.Files.readAttributes(r9, r8, r7)     // Catch: java.io.IOException -> L3e
            java.nio.file.attribute.FileTime r8 = r8.creationTime()     // Catch: java.io.IOException -> L3e
            long r3 = r8.toMillis()     // Catch: java.io.IOException -> L3e
            goto L46
        L3e:
            r8 = move-exception
            java.lang.String r9 = r8.toString()
            com.att.iqi.libs.LogUtil.loge(r9, r8)
        L46:
            java.lang.String r8 = "appLibraryExists ["
            java.lang.String r9 = "] systemLibraryExists ["
            java.lang.String r7 = "] appLibraryCreationDate ["
            java.lang.StringBuilder r8 = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(r8, r1, r9, r2, r7)
            r8.append(r5)
            java.lang.String r9 = "] systemLibraryCreationDate ["
            r8.append(r9)
            r8.append(r3)
            java.lang.String r9 = "]"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.att.iqi.libs.LogUtil.logd(r8)
            if (r2 == 0) goto L70
            if (r1 == 0) goto L6f
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 <= 0) goto L70
        L6f:
            r0 = 1
        L70:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.att.iqi.libs.IQIConcierge.shouldCopy(java.nio.file.Path, java.nio.file.Path):boolean");
    }

    private void updateMccMncValidationStateFromPrefStore() {
        sMccMncValidationDisabled = PreferenceStore.getInstance().getBoolean(PreferenceStore.PREF_DISABLE_MCC_MNC_VALIDATION, false);
    }

    public static boolean updateSubscriptions(SubscriptionManager subscriptionManager) {
        if (subscriptionManager != null) {
            List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            boolean z = true;
            if (activeSubscriptionInfoList != null && !activeSubscriptionInfoList.isEmpty()) {
                Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (!matchesNetId(it.next())) {
                        LogUtil.logd("Non-AT&T SIM found");
                        break;
                    }
                }
            } else {
                LogUtil.logd("updateSubscriptions - no active subscriptions");
            }
            sSimDisabled = z;
        }
        return isServiceBindingAllowed();
    }
}
