package android.service.credentials;

import android.Manifest;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PackagePolicy;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
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

    public static CredentialProviderInfo create(Context context, ComponentName serviceComponent, int userId, boolean isSystemProvider, boolean isPrimary) throws PackageManager.NameNotFoundException {
        return create(context, getServiceInfoOrThrow(serviceComponent, userId), isSystemProvider, false, false, isPrimary);
    }

    public static CredentialProviderInfo create(Context context, ServiceInfo serviceInfo, boolean isSystemProvider, boolean disableSystemAppVerificationForTests, boolean isEnabled, boolean isPrimary) throws SecurityException {
        verifyProviderPermission(serviceInfo);
        if (isSystemProvider && !isValidSystemProvider(context, serviceInfo, disableSystemAppVerificationForTests)) {
            Slog.e("CredentialManager", "Provider is not a valid system provider: " + serviceInfo);
            throw new SecurityException("Provider is not a valid system provider: " + serviceInfo);
        }
        return populateMetadata(context, serviceInfo).setSystemProvider(isSystemProvider).setEnabled(isEnabled).setPrimary(isPrimary).build();
    }

    public static CredentialProviderInfo createForTests(ServiceInfo serviceInfo, CharSequence overrideLabel, boolean isSystemProvider, boolean isEnabled, List<String> capabilities) {
        return new CredentialProviderInfo.Builder(serviceInfo).setEnabled(isEnabled).setOverrideLabel(overrideLabel).setSystemProvider(isSystemProvider).addCapabilities(capabilities).build();
    }

    private static void verifyProviderPermission(ServiceInfo serviceInfo) throws SecurityException {
        if (Manifest.permission.BIND_CREDENTIAL_PROVIDER_SERVICE.equals(serviceInfo.permission)) {
        } else {
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

    private static boolean isValidSystemProvider(Context context, ServiceInfo serviceInfo, boolean disableSystemAppVerificationForTests) {
        Objects.requireNonNull(context, "context must not be null");
        if (disableSystemAppVerificationForTests) {
            Bundle metadata = serviceInfo.metaData;
            if (metadata == null) {
                Slog.w("CredentialManager", "metadata is null while reading TEST_SYSTEM_PROVIDER_META_DATA_KEY: " + serviceInfo);
                return false;
            }
            return metadata.getBoolean(CredentialProviderService.TEST_SYSTEM_PROVIDER_META_DATA_KEY);
        }
        return isSystemProviderWithValidPermission(serviceInfo, context);
    }

    private static CredentialProviderInfo.Builder populateMetadata(Context context, ServiceInfo serviceInfo) {
        Objects.requireNonNull(context, "context must not be null");
        PackageManager pm = context.getPackageManager();
        CredentialProviderInfo.Builder builder = new CredentialProviderInfo.Builder(serviceInfo);
        Bundle metadata = serviceInfo.metaData;
        if (metadata == null) {
            Slog.w("CredentialManager", "Metadata is null for provider: " + serviceInfo.getComponentName());
            return builder;
        }
        Resources resources = null;
        try {
            resources = pm.getResourcesForApplication(serviceInfo.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CredentialManager", "Failed to get app resources", e);
        }
        if (resources == null) {
            Slog.w("CredentialManager", "Resources are null for the serviceInfo being processed: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            return extractXmlMetadata(context, serviceInfo, pm, resources);
        } catch (Exception e2) {
            Slog.e("CredentialManager", "Failed to get XML metadata", e2);
            return builder;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0058, code lost:
    
        r0.addCapabilities(parseXmlProviderOuterCapabilities(r1, r11));
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
    
        if (r6 == null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.credentials.CredentialProviderInfo.Builder extractXmlMetadata(android.content.Context r8, android.content.pm.ServiceInfo r9, android.content.pm.PackageManager r10, android.content.res.Resources r11) {
        /*
            android.credentials.CredentialProviderInfo$Builder r0 = new android.credentials.CredentialProviderInfo$Builder
            r0.<init>(r9)
            java.lang.String r1 = "android.credentials.provider"
            android.content.res.XmlResourceParser r1 = r9.loadXmlMetaData(r10, r1)
            if (r1 != 0) goto Lf
            return r0
        Lf:
            r2 = 0
        L10:
            r3 = 1
            java.lang.String r4 = "CredentialManager"
            if (r2 == r3) goto L1e
            r5 = 2
            if (r2 == r5) goto L1e
            int r3 = r1.next()     // Catch: java.lang.Throwable -> L6e
            r2 = r3
            goto L10
        L1e:
            java.lang.String r5 = "credential-provider"
            java.lang.String r6 = r1.getName()     // Catch: java.lang.Throwable -> L6e
            boolean r5 = r5.equals(r6)     // Catch: java.lang.Throwable -> L6e
            if (r5 == 0) goto L68
            android.util.AttributeSet r5 = android.util.Xml.asAttributeSet(r1)     // Catch: java.lang.Throwable -> L6e
            r6 = 0
            int[] r7 = com.android.internal.R.styleable.CredentialProvider     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            android.content.res.TypedArray r7 = r11.obtainAttributes(r5, r7)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r6 = r7
            java.lang.String r3 = getAfsAttributeSafe(r6, r3)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r0.setSettingsSubtitle(r3)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r3 = 0
            java.lang.String r3 = getAfsAttributeSafe(r6, r3)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r0.setSettingsActivity(r3)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            if (r6 == 0) goto L58
        L49:
            r6.recycle()     // Catch: java.lang.Throwable -> L6e java.lang.Throwable -> L6e
            goto L58
        L4d:
            r3 = move-exception
            goto L61
        L4f:
            r3 = move-exception
            java.lang.String r7 = "Failed to get XML attr for metadata"
            android.util.Slog.w(r4, r7, r3)     // Catch: java.lang.Throwable -> L4d
            if (r6 == 0) goto L58
            goto L49
        L58:
            java.util.List r3 = parseXmlProviderOuterCapabilities(r1, r11)     // Catch: java.lang.Throwable -> L6e java.lang.Throwable -> L6e
            r0.addCapabilities(r3)     // Catch: java.lang.Throwable -> L6e java.lang.Throwable -> L6e
            goto L6d
        L61:
            if (r6 == 0) goto L66
            r6.recycle()     // Catch: java.lang.Throwable -> L6e java.lang.Throwable -> L6e
        L66:
            throw r3     // Catch: java.lang.Throwable -> L6e java.lang.Throwable -> L6e
        L68:
            java.lang.String r3 = "Meta-data does not start with credential-provider-service tag"
            android.util.Slog.w(r4, r3)     // Catch: java.lang.Throwable -> L6e java.lang.Throwable -> L6e
        L6d:
            goto L74
        L6e:
            r2 = move-exception
            java.lang.String r3 = "Error parsing credential provider service meta-data"
            android.util.Slog.e(r4, r3, r2)
        L74:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.service.credentials.CredentialProviderInfoFactory.extractXmlMetadata(android.content.Context, android.content.pm.ServiceInfo, android.content.pm.PackageManager, android.content.res.Resources):android.credentials.CredentialProviderInfo$Builder");
    }

    private static String getAfsAttributeSafe(TypedArray afsAttributes, int resId) {
        if (afsAttributes == null) {
            return null;
        }
        try {
            return afsAttributes.getString(resId);
        } catch (Exception e) {
            Slog.w("CredentialManager", "Failed to get XML attr from afs attributes", e);
            return null;
        }
    }

    private static List<String> parseXmlProviderOuterCapabilities(XmlPullParser parser, Resources resources) throws IOException, XmlPullParserException {
        List<String> capabilities = new ArrayList<>();
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            }
            if (type != 3 && type != 4 && TAG_CAPABILITIES.equals(parser.getName())) {
                capabilities.addAll(parseXmlProviderInnerCapabilities(parser, resources));
            }
        }
        return capabilities;
    }

    private static List<String> parseXmlProviderInnerCapabilities(XmlPullParser parser, Resources resources) throws IOException, XmlPullParserException {
        String name;
        List<String> capabilities = new ArrayList<>();
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            }
            if (type != 3 && type != 4 && "capability".equals(parser.getName()) && (name = parser.getAttributeValue(null, "name")) != null && !TextUtils.isEmpty(name)) {
                capabilities.add(name);
            }
        }
        return capabilities;
    }

    private static ServiceInfo getServiceInfoOrThrow(ComponentName serviceComponent, int userId) throws PackageManager.NameNotFoundException {
        try {
            ServiceInfo si = AppGlobals.getPackageManager().getServiceInfo(serviceComponent, 128L, userId);
            if (si != null) {
                return si;
            }
        } catch (RemoteException e) {
            Slog.e("CredentialManager", "Unable to get serviceInfo", e);
        }
        throw new PackageManager.NameNotFoundException(serviceComponent.toString());
    }

    private static List<ServiceInfo> getAvailableSystemServiceInfos(Context context, int userId, boolean disableSystemAppVerificationForTests) {
        Objects.requireNonNull(context, "context must not be null");
        List<ServiceInfo> services = new ArrayList<>();
        List<ResolveInfo> resolveInfos = new ArrayList<>();
        resolveInfos.addAll(context.getPackageManager().queryIntentServicesAsUser(new Intent(CredentialProviderService.SYSTEM_SERVICE_INTERFACE), PackageManager.ResolveInfoFlags.of(128L), userId));
        for (ResolveInfo resolveInfo : resolveInfos) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (disableSystemAppVerificationForTests) {
                if (serviceInfo != null) {
                    services.add(serviceInfo);
                }
            } else {
                try {
                    ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(serviceInfo.packageName, PackageManager.ApplicationInfoFlags.of(1048576L));
                    if (appInfo != null && serviceInfo != null) {
                        services.add(serviceInfo);
                    }
                } catch (PackageManager.NameNotFoundException | SecurityException e) {
                    Slog.e("CredentialManager", "Error getting info for " + serviceInfo, e);
                }
            }
        }
        return services;
    }

    public static List<CredentialProviderInfo> getAvailableSystemServices(Context context, int userId, boolean disableSystemAppVerificationForTests, Set<ComponentName> enabledServices) {
        Objects.requireNonNull(context, "context must not be null");
        List<CredentialProviderInfo> providerInfos = new ArrayList<>();
        for (ServiceInfo si : getAvailableSystemServiceInfos(context, userId, disableSystemAppVerificationForTests)) {
            try {
                CredentialProviderInfo cpi = create(context, si, true, disableSystemAppVerificationForTests, enabledServices.contains(si.getComponentName()), false);
                if (!cpi.isSystemProvider()) {
                    Slog.e("CredentialManager", "Non system provider was in system provider list.");
                } else {
                    providerInfos.add(cpi);
                }
            } catch (SecurityException e) {
                Slog.e("CredentialManager", "Failed to create CredentialProviderInfo: " + e);
            }
        }
        return providerInfos;
    }

    private static PackagePolicy getDeviceManagerPolicy(Context context, int userId) {
        Context newContext = context.createContextAsUser(UserHandle.of(userId), 0);
        try {
            DevicePolicyManager dpm = (DevicePolicyManager) newContext.getSystemService(DevicePolicyManager.class);
            PackagePolicy pp = dpm.getCredentialManagerPolicy();
            return pp;
        } catch (SecurityException e) {
            Slog.e("CredentialManager", "Failed to get device policy: " + e);
            return null;
        }
    }

    public static List<CredentialProviderInfo> getCredentialProviderServices(Context context, int userId, int providerFilter, Set<ComponentName> enabledServices, Set<ComponentName> primaryServices) {
        PackagePolicy pp;
        Objects.requireNonNull(context, "context must not be null");
        if (providerFilter != 3) {
            pp = getDeviceManagerPolicy(context, userId);
        } else {
            pp = null;
        }
        ProviderGenerator generator = new ProviderGenerator(context, pp, false, providerFilter);
        generator.addUserProviders(getUserProviders(context, userId, false, enabledServices, primaryServices));
        generator.addSystemProviders(getAvailableSystemServices(context, userId, false, enabledServices));
        return generator.getProviders();
    }

    public static List<CredentialProviderInfo> getCredentialProviderServicesForTesting(Context context, int userId, int providerFilter, Set<ComponentName> enabledServices, Set<ComponentName> primaryServices) {
        PackagePolicy pp;
        Objects.requireNonNull(context, "context must not be null");
        if (providerFilter != 3) {
            pp = getDeviceManagerPolicy(context, userId);
        } else {
            pp = null;
        }
        ProviderGenerator generator = new ProviderGenerator(context, pp, true, providerFilter);
        generator.addUserProviders(getUserProviders(context, userId, true, enabledServices, primaryServices));
        generator.addSystemProviders(getAvailableSystemServices(context, userId, true, enabledServices));
        return generator.getProviders();
    }

    private static class ProviderGenerator {
        private final Context mContext;
        private final boolean mDisableSystemAppVerificationForTests;
        private final PackagePolicy mPp;
        private final int mProviderFilter;
        private final Map<String, CredentialProviderInfo> mServices = new HashMap();

        ProviderGenerator(Context context, PackagePolicy pp, boolean disableSystemAppVerificationForTests, int providerFilter) {
            this.mContext = context;
            this.mPp = pp;
            this.mDisableSystemAppVerificationForTests = disableSystemAppVerificationForTests;
            this.mProviderFilter = providerFilter;
        }

        private boolean isPackageAllowed(boolean isSystemProvider, String packageName) {
            if (this.mPp == null) {
                return true;
            }
            if (isSystemProvider) {
                return this.mPp.getPolicyType() == 2;
            }
            return this.mPp.isPackageAllowed(packageName, new HashSet());
        }

        public List<CredentialProviderInfo> getProviders() {
            return new ArrayList(this.mServices.values());
        }

        public void addUserProviders(List<CredentialProviderInfo> providers) {
            for (CredentialProviderInfo cpi : providers) {
                if (!cpi.isSystemProvider()) {
                    addProvider(cpi);
                }
            }
        }

        public void addSystemProviders(List<CredentialProviderInfo> providers) {
            for (CredentialProviderInfo cpi : providers) {
                if (cpi.isSystemProvider()) {
                    addProvider(cpi);
                }
            }
        }

        private boolean isProviderAllowedWithFilter(CredentialProviderInfo cpi) {
            if (this.mProviderFilter == 0) {
                return true;
            }
            return cpi.isSystemProvider() ? this.mProviderFilter == 1 : this.mProviderFilter == 2 || this.mProviderFilter == 3;
        }

        private void addProvider(CredentialProviderInfo cpi) {
            String componentNameString = cpi.getServiceInfo().getComponentName().flattenToString();
            if (!isProviderAllowedWithFilter(cpi) || !isPackageAllowed(cpi.isSystemProvider(), cpi.getServiceInfo().packageName)) {
                return;
            }
            this.mServices.put(componentNameString, cpi);
        }
    }

    private static List<CredentialProviderInfo> getUserProviders(Context context, int userId, boolean disableSystemAppVerificationForTests, Set<ComponentName> enabledServices, Set<ComponentName> primaryServices) {
        List<CredentialProviderInfo> services = new ArrayList<>();
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentServicesAsUser(new Intent(CredentialProviderService.SERVICE_INTERFACE), PackageManager.ResolveInfoFlags.of(128L), userId);
        for (ResolveInfo resolveInfo : resolveInfos) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo == null) {
                Slog.d("CredentialManager", "No serviceInfo found for resolveInfo, so skipping provider");
            } else {
                try {
                    try {
                        try {
                            CredentialProviderInfo cpi = create(context, serviceInfo, false, disableSystemAppVerificationForTests, enabledServices.contains(serviceInfo.getComponentName()), primaryServices.contains(serviceInfo.getComponentName()));
                            if (!cpi.isSystemProvider()) {
                                services.add(cpi);
                            }
                        } catch (Exception e) {
                            e = e;
                            Slog.e("CredentialManager", "Error getting info for " + serviceInfo, e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Slog.e("CredentialManager", "Error getting info for " + serviceInfo, e);
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }
        return services;
    }
}
