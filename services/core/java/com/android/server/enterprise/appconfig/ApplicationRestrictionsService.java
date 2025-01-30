package com.android.server.enterprise.appconfig;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.android.server.LocalServices;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.localservice.ApplicationRestrictionsInternal;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public class ApplicationRestrictionsService extends ApplicationRestrictionsInternal {
    public ActivityManager mActivityManager;
    public final Object mAppRestrictionsLock = new Object();
    public Context mContext;
    public Injector mInjector;
    public UserManager mUserManager;

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public UserManager getUserManager() {
            return UserManager.get(this.mContext);
        }

        public ActivityManager getActivityManager() {
            return (ActivityManager) this.mContext.getSystemService("activity");
        }
    }

    public ApplicationRestrictionsService(Context context) {
        Injector injector = new Injector(context);
        this.mInjector = injector;
        this.mContext = injector.mContext;
        this.mUserManager = (UserManager) Preconditions.checkNotNull(injector.getUserManager());
        this.mActivityManager = (ActivityManager) Preconditions.checkNotNull(this.mInjector.getActivityManager());
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.enterprise.appconfig.ApplicationRestrictionsService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Bundle applicationRestrictions = ApplicationRestrictionsService.this.mUserManager.getApplicationRestrictions("com.android.settings");
                if (applicationRestrictions == null || applicationRestrictions.isEmpty()) {
                    return;
                }
                ApplicationRestrictionsService.this.sendBroadcastAsUserInternal("com.android.settings", 0);
            }
        }, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
    }

    public static void addService(Context context) {
        LocalServices.addService(ApplicationRestrictionsInternal.class, new ApplicationRestrictionsService(context));
    }

    public void sendBroadcastAsUserInternal(String str, int i) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        arrayList.add(str);
        str.hashCode();
        switch (str) {
            case "com.sec.android.desktopcommunity":
            case "com.sec.android.desktopmode.uiservice":
                arrayList.add("com.sec.android.app.desktoplauncher");
                this.mActivityManager.clearApplicationUserData("com.android.settings.intelligence", null);
                break;
            case "com.samsung.android.SettingsReceiver":
                Intent intent = new Intent("com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL");
                intent.addFlags(1073741824);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.of(i));
                return;
            case "com.samsung.android.app.telephonyui":
                arrayList.add("com.android.systemui");
                this.mActivityManager.clearApplicationUserData("com.android.settings.intelligence", null);
                break;
            case "com.android.settings":
                for (String str2 : EdmConstants.APP_RESTRICTIONS_PACKAGES.keySet()) {
                    if ("basic".equals(EdmConstants.APP_RESTRICTIONS_PACKAGES.get(str2))) {
                        arrayList.add(str2);
                    }
                }
                this.mActivityManager.clearApplicationUserData("com.android.settings.intelligence", null);
                break;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL");
            intent2.setPackage(str3);
            intent2.addFlags(32);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.of(i));
        }
    }

    public void setApplicationRestrictionsInternal(String str, Bundle bundle, int i, boolean z) {
        try {
            this.mContext.enforceCallingOrSelfPermission("com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION", null);
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
                    if (!bundle.isEmpty()) {
                        writeApplicationRestrictionsLAr(str, bundle, i);
                    }
                }
                cleanAppRestrictionsForPackageLAr(str, i);
            }
            if (z) {
                sendBroadcastAsUserInternal(str, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Bundle getApplicationRestrictionsInternal(String str, int i) {
        Bundle readApplicationRestrictionsLAr;
        synchronized (this.mAppRestrictionsLock) {
            readApplicationRestrictionsLAr = readApplicationRestrictionsLAr(str, i);
        }
        return readApplicationRestrictionsLAr;
    }

    public void setKeyedAppStatesReport(String str, Bundle bundle, int i) {
        try {
            this.mContext.enforceCallingOrSelfPermission("com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION", null);
        } catch (SecurityException unused) {
            checkSystemOrRoot("setKeyedAppStatesReport" + str);
        }
        if (".feedback".endsWith(str)) {
            synchronized (this.mAppRestrictionsLock) {
                Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal(str, i);
                for (String str2 : bundle.keySet()) {
                    applicationRestrictionsInternal.putString(str2, bundle.getString(str2));
                }
                setApplicationRestrictionsInternal(str, applicationRestrictionsInternal, i, false);
            }
        }
    }

    public static String packageToRestrictionsFileName(String str) {
        return "knox_" + str + ".xml";
    }

    public static void cleanAppRestrictionsForPackageLAr(String str, int i) {
        File file = new File(Environment.getUserSystemDirectory(i), packageToRestrictionsFileName(str));
        if (file.exists()) {
            file.delete();
        }
    }

    public static Bundle readApplicationRestrictionsLAr(String str, int i) {
        return readApplicationRestrictionsLAr(new AtomicFile(new File(Environment.getUserSystemDirectory(i), packageToRestrictionsFileName(str))));
    }

    public static Bundle readApplicationRestrictionsLAr(AtomicFile atomicFile) {
        XmlPullParser newPullParser;
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        if (!atomicFile.getBaseFile().exists()) {
            return bundle;
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                newPullParser = Xml.newPullParser();
                newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                XmlUtils.nextElement(newPullParser);
            } catch (IOException | XmlPullParserException e) {
                Log.w("ApplicationRestrictionsService", "Error parsing " + atomicFile.getBaseFile(), e);
            }
            if (newPullParser.getEventType() != 2) {
                Slog.e("ApplicationRestrictionsService", "Unable to read restrictions file " + atomicFile.getBaseFile());
                return bundle;
            }
            while (newPullParser.next() != 1) {
                readEntry(bundle, arrayList, newPullParser);
            }
            return bundle;
        } finally {
            IoUtils.closeQuietly((AutoCloseable) null);
        }
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
                bundle.putBundle(attributeValue, readBundleEntry(xmlPullParser, arrayList));
                return;
            }
            if ("BA".equals(attributeValue2)) {
                int depth = xmlPullParser.getDepth();
                ArrayList arrayList2 = new ArrayList();
                while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                    arrayList2.add(readBundleEntry(xmlPullParser, arrayList));
                }
                bundle.putParcelableArray(attributeValue, (Parcelable[]) arrayList2.toArray(new Bundle[arrayList2.size()]));
                return;
            }
            String trim = xmlPullParser.nextText().trim();
            if ("b".equals(attributeValue2)) {
                bundle.putBoolean(attributeValue, Boolean.parseBoolean(trim));
            } else if ("i".equals(attributeValue2)) {
                bundle.putInt(attributeValue, Integer.parseInt(trim));
            } else {
                bundle.putString(attributeValue, trim);
            }
        }
    }

    public static Bundle readBundleEntry(XmlPullParser xmlPullParser, ArrayList arrayList) {
        Bundle bundle = new Bundle();
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            readEntry(bundle, arrayList, xmlPullParser);
        }
        return bundle;
    }

    public static void writeApplicationRestrictionsLAr(String str, Bundle bundle, int i) {
        writeApplicationRestrictionsLAr(bundle, new AtomicFile(new File(Environment.getUserSystemDirectory(i), packageToRestrictionsFileName(str))));
    }

    public static void writeApplicationRestrictionsLAr(Bundle bundle, AtomicFile atomicFile) {
        FileOutputStream startWrite;
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = atomicFile.startWrite();
        } catch (Exception e) {
            e = e;
        }
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(startWrite);
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(bufferedOutputStream, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument(null, Boolean.TRUE);
            fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            fastXmlSerializer.startTag(null, "restrictions");
            writeBundle(bundle, fastXmlSerializer);
            fastXmlSerializer.endTag(null, "restrictions");
            fastXmlSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = startWrite;
            atomicFile.failWrite(fileOutputStream);
            Slog.e("ApplicationRestrictionsService", "Error writing application restrictions list", e);
        }
    }

    public static void writeBundle(Bundle bundle, XmlSerializer xmlSerializer) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            xmlSerializer.startTag(null, "entry");
            xmlSerializer.attribute(null, "key", str);
            if (obj instanceof Boolean) {
                xmlSerializer.attribute(null, "type", "b");
                xmlSerializer.text(obj.toString());
            } else if (obj instanceof Integer) {
                xmlSerializer.attribute(null, "type", "i");
                xmlSerializer.text(obj.toString());
            } else if (obj == null || (obj instanceof String)) {
                xmlSerializer.attribute(null, "type", "s");
                xmlSerializer.text(obj != null ? (String) obj : "");
            } else if (obj instanceof Bundle) {
                xmlSerializer.attribute(null, "type", "B");
                writeBundle((Bundle) obj, xmlSerializer);
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    xmlSerializer.attribute(null, "type", "BA");
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    int length = parcelableArr.length;
                    while (i < length) {
                        Parcelable parcelable = parcelableArr[i];
                        if (!(parcelable instanceof Bundle)) {
                            throw new IllegalArgumentException("bundle-array can only hold Bundles");
                        }
                        xmlSerializer.startTag(null, "entry");
                        xmlSerializer.attribute(null, "type", "B");
                        writeBundle((Bundle) parcelable, xmlSerializer);
                        xmlSerializer.endTag(null, "entry");
                        i++;
                    }
                } else {
                    xmlSerializer.attribute(null, "type", "sa");
                    String[] strArr = (String[]) obj;
                    xmlSerializer.attribute(null, "m", Integer.toString(strArr.length));
                    int length2 = strArr.length;
                    while (i < length2) {
                        String str2 = strArr[i];
                        xmlSerializer.startTag(null, "value");
                        if (str2 == null) {
                            str2 = "";
                        }
                        xmlSerializer.text(str2);
                        xmlSerializer.endTag(null, "value");
                        i++;
                    }
                }
            }
            xmlSerializer.endTag(null, "entry");
        }
    }

    public static void checkSystemOrRoot(String str) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.isSameApp(callingUid, 1000) || callingUid == 0) {
            return;
        }
        throw new SecurityException("Only system may: " + str);
    }
}
