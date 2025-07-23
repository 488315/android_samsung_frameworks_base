package android.service.credentials;

import android.Manifest;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PackagePolicy;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.credentials.CredentialProviderInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class CredentialProviderInfoFactory {
    private static final String ATTR_NAME = "name";
    private static final String TAG = "CredentialManager";
    private static final String TAG_CAPABILITIES = "capabilities";
    private static final String TAG_CAPABILITY = "capability";
    private static final String TAG_CREDENTIAL_PROVIDER = "credential-provider";

    public static CredentialProviderInfo create(Context context, ComponentName componentName, int i, boolean z, boolean z2) throws PackageManager.NameNotFoundException, SecurityException, NullPointerException {
        return create(context, getServiceInfoOrThrow(componentName, i), z, false, false, z2);
    }

    public static CredentialProviderInfo create(Context context, ServiceInfo serviceInfo, boolean z, boolean z2, boolean z3, boolean z4) throws SecurityException, NullPointerException {
        verifyProviderPermission(serviceInfo);
        if (z && !isValidSystemProvider(context, serviceInfo, z2)) {
            Slog.e("CredentialManager", "Provider is not a valid system provider: " + serviceInfo);
            throw new SecurityException("Provider is not a valid system provider: " + serviceInfo);
        }
        return populateMetadata(context, serviceInfo).setSystemProvider(z).setEnabled(z3).setPrimary(z4).build();
    }

    public static CredentialProviderInfo createForTests(ServiceInfo serviceInfo, CharSequence charSequence, boolean z, boolean z2, List<String> list) {
        return new CredentialProviderInfo.Builder(serviceInfo).setEnabled(z2).setOverrideLabel(charSequence).setSystemProvider(z).addCapabilities(list).build();
    }

    private static void verifyProviderPermission(ServiceInfo serviceInfo) throws SecurityException {
        if (!Manifest.permission.BIND_CREDENTIAL_PROVIDER_SERVICE.equals(serviceInfo.permission)) {
            throw new SecurityException("Service does not require the expected permission : android.permission.BIND_CREDENTIAL_PROVIDER_SERVICE");
        }
    }

    private static boolean isSystemProviderWithValidPermission(ServiceInfo serviceInfo, Context context) {
        if (context == null) {
            Slog.w("CredentialManager", "Context is null in isSystemProviderWithValidPermission");
            return false;
        }
        return PermissionUtils.hasPermission(context, serviceInfo.packageName, Manifest.permission.PROVIDE_DEFAULT_ENABLED_CREDENTIAL_SERVICE);
    }

    private static boolean isValidSystemProvider(Context context, ServiceInfo serviceInfo, boolean z) {
        Objects.requireNonNull(context, "context must not be null");
        if (z) {
            Bundle bundle = serviceInfo.metaData;
            if (bundle == null) {
                Slog.w("CredentialManager", "metadata is null while reading TEST_SYSTEM_PROVIDER_META_DATA_KEY: " + serviceInfo);
                return false;
            }
            return bundle.getBoolean(CredentialProviderService.TEST_SYSTEM_PROVIDER_META_DATA_KEY);
        }
        return isSystemProviderWithValidPermission(serviceInfo, context);
    }

    private static CredentialProviderInfo.Builder populateMetadata(Context context, ServiceInfo serviceInfo) throws NullPointerException {
        Resources resources;
        Objects.requireNonNull(context, "context must not be null");
        PackageManager packageManager = context.getPackageManager();
        CredentialProviderInfo.Builder builder = new CredentialProviderInfo.Builder(serviceInfo);
        if (serviceInfo.metaData == null) {
            Slog.w("CredentialManager", "Metadata is null for provider: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            resources = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CredentialManager", "Failed to get app resources", e);
            resources = null;
        }
        if (resources == null) {
            Slog.w("CredentialManager", "Resources are null for the serviceInfo being processed: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            return extractXmlMetadata(context, serviceInfo, packageManager, resources);
        } catch (Exception e2) {
            Slog.e("CredentialManager", "Failed to get XML metadata", e2);
            return builder;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        r5.addCapabilities(parseXmlProviderOuterCapabilities(r6, r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0050, code lost:
    
        if (r3 == null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.credentials.CredentialProviderInfo.Builder extractXmlMetadata(android.content.Context r5, android.content.pm.ServiceInfo r6, android.content.pm.PackageManager r7, android.content.res.Resources r8) {
        /*
            android.credentials.CredentialProviderInfo$Builder r5 = new android.credentials.CredentialProviderInfo$Builder
            r5.<init>(r6)
            java.lang.String r0 = "android.credentials.provider"
            android.content.res.XmlResourceParser r6 = r6.loadXmlMetaData(r7, r0)
            if (r6 != 0) goto Le
            goto L6d
        Le:
            r7 = 0
            r0 = r7
        L10:
            r1 = 1
            java.lang.String r2 = "CredentialManager"
            if (r0 == r1) goto L1d
            r3 = 2
            if (r0 == r3) goto L1d
            int r0 = r6.next()     // Catch: java.lang.Throwable -> L67
            goto L10
        L1d:
            java.lang.String r0 = "credential-provider"
            java.lang.String r3 = r6.getName()     // Catch: java.lang.Throwable -> L67
            boolean r0 = r0.equals(r3)     // Catch: java.lang.Throwable -> L67
            if (r0 == 0) goto L61
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r6)     // Catch: java.lang.Throwable -> L67
            r3 = 0
            int[] r4 = com.android.internal.R.styleable.CredentialProvider     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            android.content.res.TypedArray r3 = r8.obtainAttributes(r0, r4)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r0 = getAfsAttributeSafe(r3, r1)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r5.setSettingsSubtitle(r0)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r7 = getAfsAttributeSafe(r3, r7)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r5.setSettingsActivity(r7)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            if (r3 == 0) goto L53
        L44:
            r3.recycle()     // Catch: java.lang.Throwable -> L67 java.lang.Throwable -> L67
            goto L53
        L48:
            r6 = move-exception
            goto L5b
        L4a:
            r7 = move-exception
            java.lang.String r0 = "Failed to get XML attr for metadata"
            android.util.Slog.w(r2, r0, r7)     // Catch: java.lang.Throwable -> L48
            if (r3 == 0) goto L53
            goto L44
        L53:
            java.util.List r6 = parseXmlProviderOuterCapabilities(r6, r8)     // Catch: java.lang.Throwable -> L67 java.lang.Throwable -> L67
            r5.addCapabilities(r6)     // Catch: java.lang.Throwable -> L67 java.lang.Throwable -> L67
            goto L6d
        L5b:
            if (r3 == 0) goto L60
            r3.recycle()     // Catch: java.lang.Throwable -> L67 java.lang.Throwable -> L67
        L60:
            throw r6     // Catch: java.lang.Throwable -> L67 java.lang.Throwable -> L67
        L61:
            java.lang.String r6 = "Meta-data does not start with credential-provider-service tag"
            android.util.Slog.w(r2, r6)     // Catch: java.lang.Throwable -> L67 java.lang.Throwable -> L67
            goto L6d
        L67:
            r6 = move-exception
            java.lang.String r7 = "Error parsing credential provider service meta-data"
            android.util.Slog.e(r2, r7, r6)
        L6d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.service.credentials.CredentialProviderInfoFactory.extractXmlMetadata(android.content.Context, android.content.pm.ServiceInfo, android.content.pm.PackageManager, android.content.res.Resources):android.credentials.CredentialProviderInfo$Builder");
    }

    private static String getAfsAttributeSafe(TypedArray typedArray, int i) {
        if (typedArray == null) {
            return null;
        }
        try {
            return typedArray.getString(i);
        } catch (Exception e) {
            Slog.w("CredentialManager", "Failed to get XML attr from afs attributes", e);
            return null;
        }
    }

    private static List<String> parseXmlProviderOuterCapabilities(XmlPullParser xmlPullParser, Resources resources) throws IOException, XmlPullParserException {
        ArrayList arrayList = new ArrayList();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && TAG_CAPABILITIES.equals(xmlPullParser.getName())) {
                arrayList.addAll(parseXmlProviderInnerCapabilities(xmlPullParser, resources));
            }
        }
        return arrayList;
    }

    private static List<String> parseXmlProviderInnerCapabilities(XmlPullParser xmlPullParser, Resources resources) throws IOException, XmlPullParserException {
        String attributeValue;
        ArrayList arrayList = new ArrayList();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && "capability".equals(xmlPullParser.getName()) && (attributeValue = xmlPullParser.getAttributeValue(null, "name")) != null && !TextUtils.isEmpty(attributeValue)) {
                arrayList.add(attributeValue);
            }
        }
        return arrayList;
    }

    private static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        try {
            ServiceInfo serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, i);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        } catch (RemoteException e) {
            Slog.e("CredentialManager", "Unable to get serviceInfo", e);
        }
        throw new PackageManager.NameNotFoundException(componentName.toString());
    }

    private static List<ServiceInfo> getAvailableSystemServiceInfos(Context context, int i, boolean z) {
        Objects.requireNonNull(context, "context must not be null");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(context.getPackageManager().queryIntentServicesAsUser(new Intent(CredentialProviderService.SYSTEM_SERVICE_INTERFACE), PackageManager.ResolveInfoFlags.of(128L), i));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            if (!z) {
                try {
                    if (context.getPackageManager().getApplicationInfo(serviceInfo.packageName, PackageManager.ApplicationInfoFlags.of(1048576L)) != null && serviceInfo != null) {
                        arrayList.add(serviceInfo);
                    }
                } catch (PackageManager.NameNotFoundException | SecurityException e) {
                    Slog.e("CredentialManager", "Error getting info for " + serviceInfo, e);
                }
            } else if (serviceInfo != null) {
                arrayList.add(serviceInfo);
            }
        }
        return arrayList;
    }

    public static List<CredentialProviderInfo> getAvailableSystemServices(Context context, int i, boolean z, Set<ComponentName> set) {
        Context context2;
        boolean z2;
        Objects.requireNonNull(context, "context must not be null");
        ArrayList arrayList = new ArrayList();
        for (ServiceInfo serviceInfo : getAvailableSystemServiceInfos(context, i, z)) {
            try {
                context2 = context;
                z2 = z;
            } catch (SecurityException e) {
                e = e;
                context2 = context;
                z2 = z;
            }
            try {
                CredentialProviderInfo create = create(context2, serviceInfo, true, z2, set.contains(serviceInfo.getComponentName()), false);
                if (!create.isSystemProvider()) {
                    Slog.e("CredentialManager", "Non system provider was in system provider list.");
                } else {
                    arrayList.add(create);
                }
            } catch (SecurityException e2) {
                e = e2;
                Slog.e("CredentialManager", "Failed to create CredentialProviderInfo: " + e);
                context = context2;
                z = z2;
            }
            context = context2;
            z = z2;
        }
        return arrayList;
    }

    private static PackagePolicy getDeviceManagerPolicy(Context context, int i) {
        try {
            return ((DevicePolicyManager) context.createContextAsUser(UserHandle.of(i), 0).getSystemService(DevicePolicyManager.class)).getCredentialManagerPolicy();
        } catch (SecurityException e) {
            Slog.e("CredentialManager", "Failed to get device policy: " + e);
            return null;
        }
    }

    public static List<CredentialProviderInfo> getCredentialProviderServices(Context context, int i, int i2, Set<ComponentName> set, Set<ComponentName> set2) {
        Objects.requireNonNull(context, "context must not be null");
        ProviderGenerator providerGenerator = new ProviderGenerator(context, i2 != 3 ? getDeviceManagerPolicy(context, i) : null, false, i2);
        providerGenerator.addUserProviders(getUserProviders(context, i, false, set, set2));
        providerGenerator.addSystemProviders(getAvailableSystemServices(context, i, false, set));
        return providerGenerator.getProviders();
    }

    public static List<CredentialProviderInfo> getCredentialProviderServicesForTesting(Context context, int i, int i2, Set<ComponentName> set, Set<ComponentName> set2) {
        Objects.requireNonNull(context, "context must not be null");
        ProviderGenerator providerGenerator = new ProviderGenerator(context, i2 != 3 ? getDeviceManagerPolicy(context, i) : null, true, i2);
        providerGenerator.addUserProviders(getUserProviders(context, i, true, set, set2));
        providerGenerator.addSystemProviders(getAvailableSystemServices(context, i, true, set));
        return providerGenerator.getProviders();
    }

    private static class ProviderGenerator {
        private final Context mContext;
        private final boolean mDisableSystemAppVerificationForTests;
        private final PackagePolicy mPp;
        private final int mProviderFilter;
        private final Map<String, CredentialProviderInfo> mServices = new HashMap();

        ProviderGenerator(Context context, PackagePolicy packagePolicy, boolean z, int i) {
            this.mContext = context;
            this.mPp = packagePolicy;
            this.mDisableSystemAppVerificationForTests = z;
            this.mProviderFilter = i;
        }

        private boolean isPackageAllowed(boolean z, String str) {
            PackagePolicy packagePolicy = this.mPp;
            if (packagePolicy == null) {
                return true;
            }
            if (z) {
                return packagePolicy.getPolicyType() == 2;
            }
            return packagePolicy.isPackageAllowed(str, new HashSet());
        }

        public List<CredentialProviderInfo> getProviders() {
            return new ArrayList(this.mServices.values());
        }

        public void addUserProviders(List<CredentialProviderInfo> list) {
            for (CredentialProviderInfo credentialProviderInfo : list) {
                if (!credentialProviderInfo.isSystemProvider()) {
                    addProvider(credentialProviderInfo);
                }
            }
        }

        public void addSystemProviders(List<CredentialProviderInfo> list) {
            for (CredentialProviderInfo credentialProviderInfo : list) {
                if (credentialProviderInfo.isSystemProvider()) {
                    addProvider(credentialProviderInfo);
                }
            }
        }

        private boolean isProviderAllowedWithFilter(CredentialProviderInfo credentialProviderInfo) {
            if (this.mProviderFilter == 0) {
                return true;
            }
            if (credentialProviderInfo.isSystemProvider()) {
                return this.mProviderFilter == 1;
            }
            int i = this.mProviderFilter;
            return i == 2 || i == 3;
        }

        private void addProvider(CredentialProviderInfo credentialProviderInfo) {
            String flattenToString = credentialProviderInfo.getServiceInfo().getComponentName().flattenToString();
            if (isProviderAllowedWithFilter(credentialProviderInfo) && isPackageAllowed(credentialProviderInfo.isSystemProvider(), credentialProviderInfo.getServiceInfo().packageName)) {
                this.mServices.put(flattenToString, credentialProviderInfo);
            }
        }
    }

    private static List<CredentialProviderInfo> getUserProviders(Context context, int i, boolean z, Set<ComponentName> set, Set<ComponentName> set2) {
        Context context2;
        boolean z2;
        ArrayList arrayList = new ArrayList();
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentServicesAsUser(new Intent(CredentialProviderService.SERVICE_INTERFACE), PackageManager.ResolveInfoFlags.of(128L), i).iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            if (serviceInfo == null) {
                Slog.d("CredentialManager", "No serviceInfo found for resolveInfo, so skipping provider");
            } else {
                try {
                    context2 = context;
                    z2 = z;
                    try {
                        CredentialProviderInfo create = create(context2, serviceInfo, false, z2, set.contains(serviceInfo.getComponentName()), set2.contains(serviceInfo.getComponentName()));
                        if (!create.isSystemProvider()) {
                            arrayList.add(create);
                        }
                    } catch (Exception e) {
                        e = e;
                        Slog.e("CredentialManager", "Error getting info for " + serviceInfo, e);
                        context = context2;
                        z = z2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    context2 = context;
                    z2 = z;
                }
                context = context2;
                z = z2;
            }
        }
        return arrayList;
    }
}
