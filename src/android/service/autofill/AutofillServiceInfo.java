package android.service.autofill;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.R;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    /* JADX WARN: Removed duplicated region for block: B:44:0x00ce A[Catch: NameNotFoundException | IOException | XmlPullParserException -> 0x00d2, TryCatch #2 {NameNotFoundException | IOException | XmlPullParserException -> 0x00d2, blocks: (B:37:0x00b8, B:38:0x00bb, B:44:0x00ce, B:45:0x00d1), top: B:26:0x00a0 }] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AutofillServiceInfo(android.content.Context r8, android.content.pm.ServiceInfo r9) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.service.autofill.AutofillServiceInfo.<init>(android.content.Context, android.content.pm.ServiceInfo):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x00d5, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.ArrayMap<java.lang.String, java.lang.Long> parseCompatibilityPackages(org.xmlpull.v1.XmlPullParser r13, android.content.res.Resources r14) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            r12 = this;
            java.lang.String r12 = "Invalid compatibility max version code:"
            int r0 = r13.getDepth()
            r1 = 0
            r2 = r1
        L8:
            int r3 = r13.next()
            r4 = 1
            if (r3 == r4) goto Ld5
            r5 = 3
            if (r3 != r5) goto L18
            int r6 = r13.getDepth()
            if (r6 <= r0) goto Ld5
        L18:
            if (r3 == r5) goto L8
            r5 = 4
            if (r3 != r5) goto L1e
            goto L8
        L1e:
            java.lang.String r3 = "compatibility-package"
            java.lang.String r5 = r13.getName()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L8
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r13)     // Catch: java.lang.Throwable -> Lcb
            int[] r5 = com.android.internal.R.styleable.AutofillService_CompatibilityPackage     // Catch: java.lang.Throwable -> Lcb
            android.content.res.TypedArray r3 = r14.obtainAttributes(r3, r5)     // Catch: java.lang.Throwable -> Lcb
            r5 = 0
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> Lc8
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r7 = "AutofillServiceInfo"
            if (r6 == 0) goto L5e
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc8
            r12.<init>()     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r14 = "Invalid compatibility package:"
            r12.append(r14)     // Catch: java.lang.Throwable -> Lc8
            r12.append(r5)     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r12 = r12.toString()     // Catch: java.lang.Throwable -> Lc8
            android.util.Log.e(r7, r12)     // Catch: java.lang.Throwable -> Lc8
            com.android.internal.util.XmlUtils.skipCurrentTag(r13)
            if (r3 == 0) goto Ld5
            r3.recycle()
            return r2
        L5e:
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> Lc8
            if (r4 == 0) goto Lab
            long r8 = java.lang.Long.parseLong(r4)     // Catch: java.lang.NumberFormatException -> L90 java.lang.Throwable -> Lc8
            java.lang.Long r4 = java.lang.Long.valueOf(r8)     // Catch: java.lang.NumberFormatException -> L90 java.lang.Throwable -> Lc8
            r4.getClass()     // Catch: java.lang.Throwable -> Lc8
            r10 = 0
            int r6 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r6 >= 0) goto Lb4
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc8
            r14.<init>()     // Catch: java.lang.Throwable -> Lc8
            r14.append(r12)     // Catch: java.lang.Throwable -> Lc8
            r14.append(r4)     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r12 = r14.toString()     // Catch: java.lang.Throwable -> Lc8
            android.util.Log.e(r7, r12)     // Catch: java.lang.Throwable -> Lc8
            com.android.internal.util.XmlUtils.skipCurrentTag(r13)
            if (r3 == 0) goto Ld5
            r3.recycle()
            return r2
        L90:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc8
            r14.<init>()     // Catch: java.lang.Throwable -> Lc8
            r14.append(r12)     // Catch: java.lang.Throwable -> Lc8
            r14.append(r4)     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r12 = r14.toString()     // Catch: java.lang.Throwable -> Lc8
            android.util.Log.e(r7, r12)     // Catch: java.lang.Throwable -> Lc8
            com.android.internal.util.XmlUtils.skipCurrentTag(r13)
            if (r3 == 0) goto Ld5
            r3.recycle()
            goto Ld5
        Lab:
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            java.lang.Long r4 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> Lc8
        Lb4:
            if (r2 != 0) goto Lbb
            android.util.ArrayMap r2 = new android.util.ArrayMap     // Catch: java.lang.Throwable -> Lc8
            r2.<init>()     // Catch: java.lang.Throwable -> Lc8
        Lbb:
            r2.put(r5, r4)     // Catch: java.lang.Throwable -> Lc8
            com.android.internal.util.XmlUtils.skipCurrentTag(r13)
            if (r3 == 0) goto L8
            r3.recycle()
            goto L8
        Lc8:
            r12 = move-exception
            r1 = r3
            goto Lcc
        Lcb:
            r12 = move-exception
        Lcc:
            com.android.internal.util.XmlUtils.skipCurrentTag(r13)
            if (r1 == 0) goto Ld4
            r1.recycle()
        Ld4:
            throw r12
        Ld5:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.service.autofill.AutofillServiceInfo.parseCompatibilityPackages(org.xmlpull.v1.XmlPullParser, android.content.res.Resources):android.util.ArrayMap");
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

    private static boolean isCredentialManagerAutofillService(Context context, ComponentName componentName) {
        ComponentName componentName2;
        if (componentName == null) {
            return false;
        }
        String string = context.getResources().getString(R.string.config_defaultCredentialManagerAutofillService);
        if (string != null && !string.isEmpty()) {
            componentName2 = ComponentName.unflattenFromString(string);
        } else {
            Log.w(TAG, "Invalid CredentialAutofillService");
            componentName2 = null;
        }
        return componentName.equals(componentName2);
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
