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
import android.content.res.XmlResourceParser;
import android.credentials.CredentialProviderInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
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
        Resources resourcesForApplication;
        Objects.requireNonNull(context, "context must not be null");
        PackageManager packageManager = context.getPackageManager();
        CredentialProviderInfo.Builder builder = new CredentialProviderInfo.Builder(serviceInfo);
        if (serviceInfo.metaData == null) {
            Slog.w("CredentialManager", "Metadata is null for provider: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CredentialManager", "Failed to get app resources", e);
            resourcesForApplication = null;
        }
        if (resourcesForApplication == null) {
            Slog.w("CredentialManager", "Resources are null for the serviceInfo being processed: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            return extractXmlMetadata(context, serviceInfo, packageManager, resourcesForApplication);
        } catch (Exception e2) {
            Slog.e("CredentialManager", "Failed to get XML metadata", e2);
            return builder;
        }
    }

    private static CredentialProviderInfo.Builder extractXmlMetadata(Context context, ServiceInfo serviceInfo, PackageManager packageManager, Resources resources) {
        CredentialProviderInfo.Builder builder = new CredentialProviderInfo.Builder(serviceInfo);
        XmlResourceParser xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, CredentialProviderService.SERVICE_META_DATA);
        if (xmlResourceParserLoadXmlMetaData != null) {
            for (int next = 0; next != 1 && next != 2; next = xmlResourceParserLoadXmlMetaData.next()) {
                try {
                } catch (IOException | XmlPullParserException e) {
                    Slog.e("CredentialManager", "Error parsing credential provider service meta-data", e);
                }
            }
            if (!TAG_CREDENTIAL_PROVIDER.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                Slog.w("CredentialManager", "Meta-data does not start with credential-provider-service tag");
            } else {
                TypedArray typedArrayObtainAttributes = null;
                try {
                    try {
                        typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.CredentialProvider);
                        builder.setSettingsSubtitle(getAfsAttributeSafe(typedArrayObtainAttributes, 1));
                        builder.setSettingsActivity(getAfsAttributeSafe(typedArrayObtainAttributes, 0));
                    } catch (Exception e2) {
                        Slog.w("CredentialManager", "Failed to get XML attr for metadata", e2);
                        if (typedArrayObtainAttributes != null) {
                        }
                    }
                    builder.addCapabilities(parseXmlProviderOuterCapabilities(xmlResourceParserLoadXmlMetaData, resources));
                } finally {
                    if (typedArrayObtainAttributes != null) {
                        typedArrayObtainAttributes.recycle();
                    }
                }
            }
        }
        return builder;
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

    private static List<String> parseXmlProviderOuterCapabilities(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
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

    private static List<String> parseXmlProviderInnerCapabilities(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
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

    public static List<CredentialProviderInfo> getAvailableSystemServices(Context context, int i, boolean z, Set<ComponentName> set) throws NullPointerException {
        Context context2;
        boolean z2;
        Objects.requireNonNull(context, "context must not be null");
        ArrayList arrayList = new ArrayList();
        for (ServiceInfo serviceInfo : getAvailableSystemServiceInfos(context, i, z)) {
            try {
                context2 = context;
                z2 = z;
                try {
                    CredentialProviderInfo credentialProviderInfoCreate = create(context2, serviceInfo, true, z2, set.contains(serviceInfo.getComponentName()), false);
                    if (!credentialProviderInfoCreate.isSystemProvider()) {
                        Slog.e("CredentialManager", "Non system provider was in system provider list.");
                    } else {
                        arrayList.add(credentialProviderInfoCreate);
                    }
                } catch (SecurityException e) {
                    e = e;
                    Slog.e("CredentialManager", "Failed to create CredentialProviderInfo: " + e);
                    context = context2;
                    z = z2;
                }
            } catch (SecurityException e2) {
                e = e2;
                context2 = context;
                z2 = z;
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
            String strFlattenToString = credentialProviderInfo.getServiceInfo().getComponentName().flattenToString();
            if (isProviderAllowedWithFilter(credentialProviderInfo) && isPackageAllowed(credentialProviderInfo.isSystemProvider(), credentialProviderInfo.getServiceInfo().packageName)) {
                this.mServices.put(strFlattenToString, credentialProviderInfo);
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
                } catch (Exception e) {
                    e = e;
                    context2 = context;
                    z2 = z;
                }
                try {
                    CredentialProviderInfo credentialProviderInfoCreate = create(context2, serviceInfo, false, z2, set.contains(serviceInfo.getComponentName()), set2.contains(serviceInfo.getComponentName()));
                    if (!credentialProviderInfoCreate.isSystemProvider()) {
                        arrayList.add(credentialProviderInfoCreate);
                    }
                } catch (Exception e2) {
                    e = e2;
                    Slog.e("CredentialManager", "Error getting info for " + serviceInfo, e);
                    context = context2;
                    z = z2;
                }
                context = context2;
                z = z2;
            }
        }
        return arrayList;
    }
}
