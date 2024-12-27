package com.android.server.enterprise.appconfig;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import libcore.io.IoUtils;

import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.localservice.ApplicationRestrictionsInternal;
import com.samsung.android.knoxguard.service.utils.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ApplicationRestrictionsService extends ApplicationRestrictionsInternal {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityManager mActivityManager;
    public final Object mAppRestrictionsLock = new Object();
    public final Context mContext;
    public final UserManager mUserManager;

    public ApplicationRestrictionsService(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) Preconditions.checkNotNull(UserManager.get(context));
        this.mActivityManager =
                (ActivityManager)
                        Preconditions.checkNotNull(
                                (ActivityManager) context.getSystemService("activity"));
        context.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.enterprise.appconfig.ApplicationRestrictionsService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Bundle applicationRestrictions =
                                ApplicationRestrictionsService.this.mUserManager
                                        .getApplicationRestrictions(
                                                KnoxCustomManagerService.SETTING_PKG_NAME);
                        if (applicationRestrictions == null || applicationRestrictions.isEmpty()) {
                            return;
                        }
                        ApplicationRestrictionsService.this.sendBroadcastAsUserInternal(
                                KnoxCustomManagerService.SETTING_PKG_NAME, 0);
                    }
                },
                new IntentFilter("android.intent.action.BOOT_COMPLETED"));
    }

    public static void checkSystemOrRoot(String str) {
        int callingUid = Binder.getCallingUid();
        if (!UserHandle.isSameApp(callingUid, 1000) && callingUid != 0) {
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Only system may: ", str));
        }
    }

    public static String packageToRestrictionsFileName(String str) {
        return XmlUtils$$ExternalSyntheticOutline0.m("knox_", str, ".xml");
    }

    public static Bundle readApplicationRestrictionsLAr(int i, String str) {
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(
                                Environment.getUserSystemDirectory(i),
                                packageToRestrictionsFileName(str)));
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        if (atomicFile.getBaseFile().exists()) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = atomicFile.openRead();
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                    XmlUtils.nextElement(newPullParser);
                    if (newPullParser.getEventType() != 2) {
                        Slog.e(
                                "ApplicationRestrictionsService",
                                "Unable to read restrictions file " + atomicFile.getBaseFile());
                    } else {
                        while (newPullParser.next() != 1) {
                            readEntry(bundle, arrayList, newPullParser);
                        }
                    }
                } catch (IOException | XmlPullParserException e) {
                    Log.w(
                            "ApplicationRestrictionsService",
                            "Error parsing " + atomicFile.getBaseFile(),
                            e);
                }
            } finally {
                IoUtils.closeQuietly((AutoCloseable) null);
            }
        }
        return bundle;
    }

    public static void readEntry(Bundle bundle, ArrayList arrayList, XmlPullParser xmlPullParser) {
        if (xmlPullParser.getEventType() == 2 && xmlPullParser.getName().equals("entry")) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "key");
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "type");
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "m");
            if (attributeValue3 != null) {
                arrayList.clear();
                int parseInt = Integer.parseInt(attributeValue3);
                while (parseInt > 0) {
                    int next = xmlPullParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2 && xmlPullParser.getName().equals("value")) {
                        arrayList.add(xmlPullParser.nextText().trim());
                        parseInt--;
                    }
                }
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray(attributeValue, strArr);
                return;
            }
            if ("B".equals(attributeValue2)) {
                Bundle bundle2 = new Bundle();
                int depth = xmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                    readEntry(bundle2, arrayList, xmlPullParser);
                }
                bundle.putBundle(attributeValue, bundle2);
                return;
            }
            if (!"BA".equals(attributeValue2)) {
                String trim = xmlPullParser.nextText().trim();
                if ("b".equals(attributeValue2)) {
                    bundle.putBoolean(attributeValue, Boolean.parseBoolean(trim));
                    return;
                } else if ("i".equals(attributeValue2)) {
                    bundle.putInt(attributeValue, Integer.parseInt(trim));
                    return;
                } else {
                    bundle.putString(attributeValue, trim);
                    return;
                }
            }
            int depth2 = xmlPullParser.getDepth();
            ArrayList arrayList2 = new ArrayList();
            while (XmlUtils.nextElementWithin(xmlPullParser, depth2)) {
                Bundle bundle3 = new Bundle();
                int depth3 = xmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(xmlPullParser, depth3)) {
                    readEntry(bundle3, arrayList, xmlPullParser);
                }
                arrayList2.add(bundle3);
            }
            bundle.putParcelableArray(
                    attributeValue,
                    (Parcelable[]) arrayList2.toArray(new Bundle[arrayList2.size()]));
        }
    }

    public static void writeApplicationRestrictionsLAr(int i, String str, Bundle bundle) {
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(
                                Environment.getUserSystemDirectory(i),
                                packageToRestrictionsFileName(str)));
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(startWrite);
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(bufferedOutputStream, StandardCharsets.UTF_8.name());
                fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
                fastXmlSerializer.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer.startTag((String) null, "restrictions");
                writeBundle(bundle, fastXmlSerializer);
                fastXmlSerializer.endTag((String) null, "restrictions");
                fastXmlSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
            } catch (Exception e) {
                e = e;
                fileOutputStream = startWrite;
                atomicFile.failWrite(fileOutputStream);
                Slog.e(
                        "ApplicationRestrictionsService",
                        "Error writing application restrictions list",
                        e);
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static void writeBundle(Bundle bundle, XmlSerializer xmlSerializer) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
            fastXmlSerializer.startTag((String) null, "entry");
            fastXmlSerializer.attribute((String) null, "key", str);
            if (obj instanceof Boolean) {
                fastXmlSerializer.attribute((String) null, "type", "b");
                fastXmlSerializer.text(obj.toString());
            } else if (obj instanceof Integer) {
                fastXmlSerializer.attribute((String) null, "type", "i");
                fastXmlSerializer.text(obj.toString());
            } else if (obj == null || (obj instanceof String)) {
                fastXmlSerializer.attribute((String) null, "type", "s");
                fastXmlSerializer.text(obj != null ? (String) obj : "");
            } else if (obj instanceof Bundle) {
                fastXmlSerializer.attribute((String) null, "type", "B");
                writeBundle((Bundle) obj, xmlSerializer);
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    fastXmlSerializer.attribute((String) null, "type", "BA");
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    int length = parcelableArr.length;
                    while (i < length) {
                        Parcelable parcelable = parcelableArr[i];
                        if (!(parcelable instanceof Bundle)) {
                            throw new IllegalArgumentException(
                                    "bundle-array can only hold Bundles");
                        }
                        fastXmlSerializer.startTag((String) null, "entry");
                        fastXmlSerializer.attribute((String) null, "type", "B");
                        writeBundle((Bundle) parcelable, xmlSerializer);
                        fastXmlSerializer.endTag((String) null, "entry");
                        i++;
                    }
                } else {
                    fastXmlSerializer.attribute((String) null, "type", "sa");
                    String[] strArr = (String[]) obj;
                    fastXmlSerializer.attribute(
                            (String) null, "m", Integer.toString(strArr.length));
                    int length2 = strArr.length;
                    while (i < length2) {
                        String str2 = strArr[i];
                        fastXmlSerializer.startTag((String) null, "value");
                        if (str2 == null) {
                            str2 = "";
                        }
                        fastXmlSerializer.text(str2);
                        fastXmlSerializer.endTag((String) null, "value");
                        i++;
                    }
                }
            }
            fastXmlSerializer.endTag((String) null, "entry");
        }
    }

    public final Bundle getApplicationRestrictionsInternal(String str, int i) {
        Bundle readApplicationRestrictionsLAr;
        synchronized (this.mAppRestrictionsLock) {
            readApplicationRestrictionsLAr = readApplicationRestrictionsLAr(i, str);
        }
        return readApplicationRestrictionsLAr;
    }

    public final void sendBroadcastAsUserInternal(String str, int i) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        arrayList.add(str);
        str.getClass();
        switch (str) {
            case "com.sec.android.desktopcommunity":
            case "com.sec.android.desktopmode.uiservice":
                arrayList.add("com.sec.android.app.desktoplauncher");
                this.mActivityManager.clearApplicationUserData(
                        "com.android.settings.intelligence", null);
                break;
            case "com.samsung.android.SettingsReceiver":
                this.mContext.sendBroadcastAsUser(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                1073741824,
                                "com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL"),
                        UserHandle.of(i));
                return;
            case "com.samsung.android.app.telephonyui":
                arrayList.add(Constants.SYSTEMUI_PACKAGE_NAME);
                this.mActivityManager.clearApplicationUserData(
                        "com.android.settings.intelligence", null);
                break;
            case "com.samsung.android.knox.galaxyai":
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL");
                intent.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
                intent.putExtra("galaxyai", true);
                intent.addFlags(32);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.of(0));
                Iterator it = EdmConstants.AI_APP_RESTRICTIONS_PACKAGES.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) it.next());
                }
                break;
            case "com.android.settings":
                for (String str2 : EdmConstants.APP_RESTRICTIONS_PACKAGES.keySet()) {
                    if ("basic".equals(EdmConstants.APP_RESTRICTIONS_PACKAGES.get(str2))) {
                        arrayList.add(str2);
                    }
                }
                break;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            Intent intent2 =
                    new Intent(
                            "com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL");
            intent2.setPackage(str3);
            intent2.addFlags(32);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.of(i));
        }
    }

    public final void setApplicationRestrictionsInternal(
            String str, Bundle bundle, int i, boolean z) {
        try {
            this.mContext.enforceCallingOrSelfPermission(
                    "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION", null);
        } catch (SecurityException unused) {
            checkSystemOrRoot("setApplicationRestrictionsInternal" + str);
        }
        if (bundle != null) {
            bundle.setDefusable(true);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAppRestrictionsLock) {
                if (bundle != null) {
                    try {
                        if (!bundle.isEmpty()) {
                            writeApplicationRestrictionsLAr(i, str, bundle);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                File file =
                        new File(
                                Environment.getUserSystemDirectory(i),
                                packageToRestrictionsFileName(str));
                if (file.exists()) {
                    file.delete();
                }
            }
            if (z) {
                sendBroadcastAsUserInternal(str, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setKeyedAppStatesReport(String str, Bundle bundle, int i) {
        try {
            this.mContext.enforceCallingOrSelfPermission(
                    "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION", null);
        } catch (SecurityException unused) {
            checkSystemOrRoot("setKeyedAppStatesReport" + str);
        }
        if (".feedback".endsWith(str)) {
            synchronized (this.mAppRestrictionsLock) {
                try {
                    Bundle applicationRestrictionsInternal =
                            getApplicationRestrictionsInternal(str, i);
                    for (String str2 : bundle.keySet()) {
                        applicationRestrictionsInternal.putString(str2, bundle.getString(str2));
                    }
                    setApplicationRestrictionsInternal(
                            str, applicationRestrictionsInternal, i, false);
                } finally {
                }
            }
        }
    }
}
