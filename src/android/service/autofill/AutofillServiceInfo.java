package android.service.autofill;

import android.Manifest;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.metrics.LogMaker;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class AutofillServiceInfo {
    private static final ComponentName CREDMAN_SERVICE_COMPONENT_NAME = new ComponentName("com.android.credentialmanager", "com.android.credentialmanager.autofill.CredentialAutofillService");
    private static final String TAG = "AutofillServiceInfo";
    private static final String TAG_AUTOFILL_SERVICE = "autofill-service";
    private static final String TAG_COMPATIBILITY_PACKAGE = "compatibility-package";
    private final ArrayMap<String, Long> mCompatibilityPackages;
    private final boolean mInlineSuggestionsEnabled;
    private final String mPasswordsActivity;
    private final ServiceInfo mServiceInfo;
    private final String mSettingsActivity;

    private static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        try {
            ServiceInfo serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, i);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        } catch (RemoteException unused) {
        }
        throw new PackageManager.NameNotFoundException(componentName.toString());
    }

    public AutofillServiceInfo(Context context, ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        this(context, getServiceInfoOrThrow(componentName, i));
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00ce A[Catch: NameNotFoundException | IOException | XmlPullParserException -> 0x00d2, TryCatch #2 {NameNotFoundException | IOException | XmlPullParserException -> 0x00d2, blocks: (B:26:0x00b8, B:27:0x00bb, B:38:0x00ce, B:39:0x00d1), top: B:54:0x00a0 }] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AutofillServiceInfo(Context context, ServiceInfo serviceInfo) throws Throwable {
        ?? r4;
        String name;
        String str;
        ArrayMap<String, Long> arrayMap;
        String str2;
        Resources resourcesForApplication;
        TypedArray typedArrayObtainAttributes;
        Object obj;
        if (!Manifest.permission.BIND_AUTOFILL_SERVICE.equals(serviceInfo.permission)) {
            if (Manifest.permission.BIND_AUTOFILL.equals(serviceInfo.permission)) {
                Log.w(TAG, "AutofillService from '" + serviceInfo.packageName + "' uses unsupported permission android.permission.BIND_AUTOFILL. It works for now, but might not be supported on future releases");
                new MetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.AUTOFILL_INVALID_PERMISSION).setPackageName(serviceInfo.packageName));
            } else {
                Log.w(TAG, "AutofillService from '" + serviceInfo.packageName + "' does not require permission android.permission.BIND_AUTOFILL_SERVICE");
                throw new SecurityException("Service does not require permission android.permission.BIND_AUTOFILL_SERVICE");
            }
        }
        this.mServiceInfo = serviceInfo;
        XmlResourceParser xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(context.getPackageManager(), AutofillService.SERVICE_META_DATA);
        boolean z = false;
        ArrayMap<String, Long> compatibilityPackages = null;
        String str3 = null;
        if (xmlResourceParserLoadXmlMetaData == null) {
            this.mSettingsActivity = null;
            this.mPasswordsActivity = null;
            this.mCompatibilityPackages = null;
            this.mInlineSuggestionsEnabled = false;
            return;
        }
        try {
            resourcesForApplication = context.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo);
            int next = 0;
            while (true) {
                r4 = 2;
                if (next == 1 || next == 2) {
                    break;
                } else {
                    next = xmlResourceParserLoadXmlMetaData.next();
                }
            }
            name = xmlResourceParserLoadXmlMetaData.getName();
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            e = e;
            r4 = 0;
            name = null;
        }
        if (TAG_AUTOFILL_SERVICE.equals(name)) {
            try {
            } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e2) {
                e = e2;
                Log.e(TAG, "Error parsing auto fill service meta-data", e);
                str = r4;
                arrayMap = compatibilityPackages;
                str3 = name;
                str2 = str;
                this.mSettingsActivity = str3;
                this.mPasswordsActivity = str2;
                this.mCompatibilityPackages = arrayMap;
                this.mInlineSuggestionsEnabled = z;
            }
            try {
                typedArrayObtainAttributes = resourcesForApplication.obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.AutofillService);
                try {
                    name = typedArrayObtainAttributes.getString(0);
                    try {
                        String string = typedArrayObtainAttributes.getString(2);
                        try {
                            z = typedArrayObtainAttributes.getBoolean(1, false);
                            if (typedArrayObtainAttributes != null) {
                                typedArrayObtainAttributes.recycle();
                            }
                            compatibilityPackages = parseCompatibilityPackages(xmlResourceParserLoadXmlMetaData, resourcesForApplication);
                            str = string;
                            arrayMap = compatibilityPackages;
                            str3 = name;
                            str2 = str;
                            this.mSettingsActivity = str3;
                            this.mPasswordsActivity = str2;
                            this.mCompatibilityPackages = arrayMap;
                            this.mInlineSuggestionsEnabled = z;
                        } catch (Throwable th) {
                            th = th;
                            if (typedArrayObtainAttributes != null) {
                                typedArrayObtainAttributes.recycle();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    obj = null;
                    if (typedArrayObtainAttributes != null) {
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                typedArrayObtainAttributes = null;
                obj = null;
            }
        } else {
            Log.e(TAG, "Meta-data does not start with autofill-service tag");
            arrayMap = null;
            str2 = null;
            this.mSettingsActivity = str3;
            this.mPasswordsActivity = str2;
            this.mCompatibilityPackages = arrayMap;
            this.mInlineSuggestionsEnabled = z;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00d5, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ArrayMap<String, Long> parseCompatibilityPackages(XmlPullParser xmlPullParser, Resources resources) throws Throwable {
        Long lValueOf;
        int depth = xmlPullParser.getDepth();
        TypedArray typedArray = null;
        ArrayMap<String, Long> arrayMap = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && TAG_COMPATIBILITY_PACKAGE.equals(xmlPullParser.getName())) {
                try {
                    TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.AutofillService_CompatibilityPackage);
                    try {
                        String string = typedArrayObtainAttributes.getString(0);
                        if (TextUtils.isEmpty(string)) {
                            Log.e(TAG, "Invalid compatibility package:" + string);
                            XmlUtils.skipCurrentTag(xmlPullParser);
                            if (typedArrayObtainAttributes != null) {
                                typedArrayObtainAttributes.recycle();
                                return arrayMap;
                            }
                        } else {
                            String string2 = typedArrayObtainAttributes.getString(1);
                            if (string2 != null) {
                                try {
                                    long j = Long.parseLong(string2);
                                    lValueOf = Long.valueOf(j);
                                    lValueOf.getClass();
                                    if (j < 0) {
                                        Log.e(TAG, "Invalid compatibility max version code:" + lValueOf);
                                        XmlUtils.skipCurrentTag(xmlPullParser);
                                        if (typedArrayObtainAttributes != null) {
                                            typedArrayObtainAttributes.recycle();
                                            return arrayMap;
                                        }
                                    }
                                } catch (NumberFormatException unused) {
                                    Log.e(TAG, "Invalid compatibility max version code:" + string2);
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    if (typedArrayObtainAttributes != null) {
                                        typedArrayObtainAttributes.recycle();
                                    }
                                }
                            } else {
                                lValueOf = Long.MAX_VALUE;
                            }
                            if (arrayMap == null) {
                                arrayMap = new ArrayMap<>();
                            }
                            arrayMap.put(string, lValueOf);
                            XmlUtils.skipCurrentTag(xmlPullParser);
                            if (typedArrayObtainAttributes != null) {
                                typedArrayObtainAttributes.recycle();
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        typedArray = typedArrayObtainAttributes;
                        XmlUtils.skipCurrentTag(xmlPullParser);
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
    }

    private AutofillServiceInfo(String str) {
        ServiceInfo serviceInfo = new ServiceInfo();
        this.mServiceInfo = serviceInfo;
        serviceInfo.applicationInfo = new ApplicationInfo();
        serviceInfo.packageName = "com.android.test";
        this.mSettingsActivity = null;
        this.mPasswordsActivity = str;
        this.mCompatibilityPackages = null;
        this.mInlineSuggestionsEnabled = false;
    }

    public static final class TestDataBuilder {
        private String mPasswordsActivity;

        public TestDataBuilder setPasswordsActivity(String str) {
            this.mPasswordsActivity = str;
            return this;
        }

        public AutofillServiceInfo build() {
            return new AutofillServiceInfo(this.mPasswordsActivity);
        }
    }

    public ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public String getPasswordsActivity() {
        return this.mPasswordsActivity;
    }

    public ArrayMap<String, Long> getCompatibilityPackages() {
        return this.mCompatibilityPackages;
    }

    public boolean isInlineSuggestionsEnabled() {
        return this.mInlineSuggestionsEnabled;
    }

    public static List<AutofillServiceInfo> getAvailableServices(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentServicesAsUser(new Intent(AutofillService.SERVICE_INTERFACE), 128, i).iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            if (serviceInfo != null) {
                try {
                } catch (SecurityException e) {
                    Log.w(TAG, "Error getting info for " + serviceInfo + ": " + e);
                }
                if (isCredentialManagerAutofillService(context, serviceInfo.getComponentName())) {
                }
            }
            arrayList.add(new AutofillServiceInfo(context, serviceInfo));
        }
        return arrayList;
    }

    private static boolean isCredentialManagerAutofillService(Context context, ComponentName componentName) throws Resources.NotFoundException {
        ComponentName componentNameUnflattenFromString;
        if (componentName == null) {
            return false;
        }
        String string = context.getResources().getString(R.string.config_defaultCredentialManagerAutofillService);
        if (string != null && !string.isEmpty()) {
            componentNameUnflattenFromString = ComponentName.unflattenFromString(string);
        } else {
            Log.w(TAG, "Invalid CredentialAutofillService");
            componentNameUnflattenFromString = null;
        }
        return componentName.equals(componentNameUnflattenFromString);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mServiceInfo);
        sb.append(", settings:");
        sb.append(this.mSettingsActivity);
        sb.append(", passwords activity:");
        sb.append(this.mPasswordsActivity);
        sb.append(", hasCompatPckgs:");
        ArrayMap<String, Long> arrayMap = this.mCompatibilityPackages;
        sb.append((arrayMap == null || arrayMap.isEmpty()) ? false : true);
        sb.append("], inline suggestions enabled:");
        sb.append(this.mInlineSuggestionsEnabled);
        return sb.toString();
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("Component: ");
        printWriter.println(getServiceInfo().getComponentName());
        printWriter.print(str);
        printWriter.print("Settings: ");
        printWriter.println(this.mSettingsActivity);
        printWriter.print(str);
        printWriter.print("Passwords activity: ");
        printWriter.println(this.mPasswordsActivity);
        printWriter.print(str);
        printWriter.print("Compat packages: ");
        printWriter.println(this.mCompatibilityPackages);
        printWriter.print(str);
        printWriter.print("Inline Suggestions Enabled: ");
        printWriter.println(this.mInlineSuggestionsEnabled);
    }
}
