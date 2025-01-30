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
import android.content.res.XmlResourceParser;
import android.credentials.CredentialProviderInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.C4337R;
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
  private static final String TAG = "CredentialProviderInfoFactory";
  private static final String TAG_CAPABILITIES = "capabilities";
  private static final String TAG_CAPABILITY = "capability";
  private static final String TAG_CREDENTIAL_PROVIDER = "credential-provider";

  public static CredentialProviderInfo create(
      Context context, ComponentName serviceComponent, int userId, boolean isSystemProvider)
      throws PackageManager.NameNotFoundException {
    return create(
        context,
        getServiceInfoOrThrow(serviceComponent, userId),
        isSystemProvider,
        false,
        false,
        false);
  }

  public static CredentialProviderInfo create(
      Context context,
      ServiceInfo serviceInfo,
      boolean isSystemProvider,
      boolean disableSystemAppVerificationForTests,
      boolean isEnabled,
      boolean isPrimary)
      throws SecurityException {
    verifyProviderPermission(serviceInfo);
    if (isSystemProvider
        && !isValidSystemProvider(context, serviceInfo, disableSystemAppVerificationForTests)) {
      Slog.m115e(TAG, "Provider is not a valid system provider: " + serviceInfo);
      throw new SecurityException("Provider is not a valid system provider: " + serviceInfo);
    }
    return populateMetadata(context, serviceInfo)
        .setSystemProvider(isSystemProvider)
        .setEnabled(isEnabled)
        .setPrimary(isPrimary)
        .build();
  }

  public static CredentialProviderInfo createForTests(
      ServiceInfo serviceInfo,
      CharSequence overrideLabel,
      boolean isSystemProvider,
      boolean isEnabled,
      List<String> capabilities) {
    return new CredentialProviderInfo.Builder(serviceInfo)
        .setEnabled(isEnabled)
        .setOverrideLabel(overrideLabel)
        .setSystemProvider(isSystemProvider)
        .addCapabilities(capabilities)
        .build();
  }

  private static void verifyProviderPermission(ServiceInfo serviceInfo) throws SecurityException {
    if (Manifest.permission.BIND_CREDENTIAL_PROVIDER_SERVICE.equals(serviceInfo.permission)) {
    } else {
      throw new SecurityException(
          "Service does not require the expected permission :"
              + " android.permission.BIND_CREDENTIAL_PROVIDER_SERVICE");
    }
  }

  private static boolean isSystemProviderWithValidPermission(
      ServiceInfo serviceInfo, Context context) {
    if (context == null) {
      Slog.m121w(TAG, "Context is null in isSystemProviderWithValidPermission");
      return false;
    }
    return PermissionUtils.hasPermission(
        context,
        serviceInfo.packageName,
        Manifest.permission.PROVIDE_DEFAULT_ENABLED_CREDENTIAL_SERVICE);
  }

  private static boolean isValidSystemProvider(
      Context context, ServiceInfo serviceInfo, boolean disableSystemAppVerificationForTests) {
    Objects.requireNonNull(context, "context must not be null");
    if (disableSystemAppVerificationForTests) {
      Bundle metadata = serviceInfo.metaData;
      if (metadata == null) {
        Slog.m121w(
            TAG,
            "metadata is null while reading TEST_SYSTEM_PROVIDER_META_DATA_KEY: " + serviceInfo);
        return false;
      }
      return metadata.getBoolean(CredentialProviderService.TEST_SYSTEM_PROVIDER_META_DATA_KEY);
    }
    return isSystemProviderWithValidPermission(serviceInfo, context);
  }

  private static CredentialProviderInfo.Builder populateMetadata(
      Context context, ServiceInfo serviceInfo) {
    Objects.requireNonNull(context, "context must not be null");
    PackageManager pm = context.getPackageManager();
    CredentialProviderInfo.Builder builder = new CredentialProviderInfo.Builder(serviceInfo);
    Bundle metadata = serviceInfo.metaData;
    if (metadata == null) {
      Slog.m121w(TAG, "Metadata is null for provider: " + serviceInfo.getComponentName());
      return builder;
    }
    Resources resources = null;
    try {
      resources = pm.getResourcesForApplication(serviceInfo.applicationInfo);
    } catch (PackageManager.NameNotFoundException e) {
      Slog.m116e(TAG, "Failed to get app resources", e);
    }
    if (resources == null) {
      Slog.m121w(
          TAG,
          "Resources are null for the serviceInfo being processed: "
              + serviceInfo.getComponentName());
      return builder;
    }
    try {
      return extractXmlMetadata(context, builder, serviceInfo, pm, resources);
    } catch (Exception e2) {
      Slog.m116e(TAG, "Failed to get XML metadata", e2);
      return builder;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:

     r8.addCapabilities(parseXmlProviderOuterCapabilities(r0, r11));
  */
  /* JADX WARN: Code restructure failed: missing block: B:32:0x0048, code lost:

     if (r4 == null) goto L25;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private static CredentialProviderInfo.Builder extractXmlMetadata(
      Context context,
      CredentialProviderInfo.Builder builder,
      ServiceInfo serviceInfo,
      PackageManager pm,
      Resources resources) {
    XmlResourceParser parser =
        serviceInfo.loadXmlMetaData(pm, CredentialProviderService.SERVICE_META_DATA);
    if (parser == null) {
      return builder;
    }
    for (int type = 0; type != 1 && type != 2; type = parser.next()) {
      try {
      } catch (IOException | XmlPullParserException e) {
        Slog.m116e(TAG, "Error parsing credential provider service meta-data", e);
      }
    }
    if (TAG_CREDENTIAL_PROVIDER.equals(parser.getName())) {
      AttributeSet allAttributes = Xml.asAttributeSet(parser);
      TypedArray afsAttributes = null;
      try {
        try {
          afsAttributes =
              resources.obtainAttributes(allAttributes, C4337R.styleable.CredentialProvider);
          builder.setSettingsSubtitle(afsAttributes.getString(0));
        } finally {
          if (afsAttributes != null) {
            afsAttributes.recycle();
          }
        }
      } catch (Exception e2) {
        Slog.m116e(TAG, "Failed to get XML attr", e2);
      }
    } else {
      Slog.m121w(TAG, "Meta-data does not start with credential-provider-service tag");
    }
    return builder;
  }

  private static Set<String> parseXmlProviderOuterCapabilities(
      XmlPullParser parser, Resources resources) throws IOException, XmlPullParserException {
    Set<String> capabilities = new HashSet<>();
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

  private static List<String> parseXmlProviderInnerCapabilities(
      XmlPullParser parser, Resources resources) throws IOException, XmlPullParserException {
    String name;
    List<String> capabilities = new ArrayList<>();
    int outerDepth = parser.getDepth();
    while (true) {
      int type = parser.next();
      if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
        break;
      }
      if (type != 3
          && type != 4
          && "capability".equals(parser.getName())
          && (name = parser.getAttributeValue(null, "name")) != null
          && !TextUtils.isEmpty(name)) {
        capabilities.add(name);
      }
    }
    return capabilities;
  }

  private static ServiceInfo getServiceInfoOrThrow(ComponentName serviceComponent, int userId)
      throws PackageManager.NameNotFoundException {
    try {
      ServiceInfo si =
          AppGlobals.getPackageManager().getServiceInfo(serviceComponent, 128L, userId);
      if (si != null) {
        return si;
      }
    } catch (RemoteException e) {
      Slog.m116e(TAG, "Unable to get serviceInfo", e);
    }
    throw new PackageManager.NameNotFoundException(serviceComponent.toString());
  }

  private static List<ServiceInfo> getAvailableSystemServiceInfos(
      Context context, int userId, boolean disableSystemAppVerificationForTests) {
    Objects.requireNonNull(context, "context must not be null");
    List<ServiceInfo> services = new ArrayList<>();
    List<ResolveInfo> resolveInfos = new ArrayList<>();
    resolveInfos.addAll(
        context
            .getPackageManager()
            .queryIntentServicesAsUser(
                new Intent(CredentialProviderService.SYSTEM_SERVICE_INTERFACE),
                PackageManager.ResolveInfoFlags.m12of(128L),
                userId));
    for (ResolveInfo resolveInfo : resolveInfos) {
      ServiceInfo serviceInfo = resolveInfo.serviceInfo;
      if (disableSystemAppVerificationForTests) {
        if (serviceInfo != null) {
          services.add(serviceInfo);
        }
      } else {
        try {
          ApplicationInfo appInfo =
              context
                  .getPackageManager()
                  .getApplicationInfo(
                      serviceInfo.packageName, PackageManager.ApplicationInfoFlags.m9of(1048576L));
          if (appInfo != null && serviceInfo != null) {
            services.add(serviceInfo);
          }
        } catch (PackageManager.NameNotFoundException | SecurityException e) {
          Slog.m116e(TAG, "Error getting info for " + serviceInfo, e);
        }
      }
    }
    return services;
  }

  public static List<CredentialProviderInfo> getAvailableSystemServices(
      Context context,
      int userId,
      boolean disableSystemAppVerificationForTests,
      Set<ComponentName> enabledServices) {
    Objects.requireNonNull(context, "context must not be null");
    List<CredentialProviderInfo> providerInfos = new ArrayList<>();
    for (ServiceInfo si :
        getAvailableSystemServiceInfos(context, userId, disableSystemAppVerificationForTests)) {
      try {
        CredentialProviderInfo cpi =
            create(
                context,
                si,
                true,
                disableSystemAppVerificationForTests,
                enabledServices.contains(si.getComponentName()),
                false);
        if (!cpi.isSystemProvider()) {
          Slog.m115e(TAG, "Non system provider was in system provider list.");
        } else {
          providerInfos.add(cpi);
        }
      } catch (SecurityException e) {
        Slog.m115e(TAG, "Failed to create CredentialProviderInfo: " + e);
      }
    }
    return providerInfos;
  }

  private static PackagePolicy getDeviceManagerPolicy(Context context, int userId) {
    Context newContext = context.createContextAsUser(UserHandle.m55of(userId), 0);
    try {
      DevicePolicyManager dpm =
          (DevicePolicyManager) newContext.getSystemService(DevicePolicyManager.class);
      PackagePolicy pp = dpm.getCredentialManagerPolicy();
      return pp;
    } catch (SecurityException e) {
      Slog.m115e(TAG, "Failed to get device policy: " + e);
      return null;
    }
  }

  public static List<CredentialProviderInfo> getCredentialProviderServices(
      Context context,
      int userId,
      int providerFilter,
      Set<ComponentName> enabledServices,
      Set<ComponentName> primaryServices) {
    Objects.requireNonNull(context, "context must not be null");
    PackagePolicy pp = getDeviceManagerPolicy(context, userId);
    ProviderGenerator generator = new ProviderGenerator(context, pp, false, providerFilter);
    generator.addUserProviders(
        getUserProviders(context, userId, false, enabledServices, primaryServices));
    generator.addSystemProviders(
        getAvailableSystemServices(context, userId, false, enabledServices));
    return generator.getProviders();
  }

  public static List<CredentialProviderInfo> getCredentialProviderServicesForTesting(
      Context context,
      int userId,
      int providerFilter,
      Set<ComponentName> enabledServices,
      Set<ComponentName> primaryServices) {
    Objects.requireNonNull(context, "context must not be null");
    PackagePolicy pp = getDeviceManagerPolicy(context, userId);
    ProviderGenerator generator = new ProviderGenerator(context, pp, true, providerFilter);
    generator.addUserProviders(
        getUserProviders(context, userId, true, enabledServices, primaryServices));
    generator.addSystemProviders(
        getAvailableSystemServices(context, userId, true, enabledServices));
    return generator.getProviders();
  }

  private static class ProviderGenerator {
    private final Context mContext;
    private final boolean mDisableSystemAppVerificationForTests;
    private final PackagePolicy mPp;
    private final int mProviderFilter;
    private final Map<String, CredentialProviderInfo> mServices = new HashMap();

    ProviderGenerator(
        Context context,
        PackagePolicy pp,
        boolean disableSystemAppVerificationForTests,
        int providerFilter) {
      this.mContext = context;
      this.mPp = pp;
      this.mDisableSystemAppVerificationForTests = disableSystemAppVerificationForTests;
      this.mProviderFilter = providerFilter;
    }

    private boolean isPackageAllowed(boolean isSystemProvider, String packageName) {
      PackagePolicy packagePolicy = this.mPp;
      if (packagePolicy == null) {
        return true;
      }
      if (isSystemProvider) {
        return packagePolicy.getPolicyType() == 2;
      }
      return packagePolicy.isPackageAllowed(packageName, new HashSet());
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
      return cpi.isSystemProvider() ? this.mProviderFilter == 1 : this.mProviderFilter == 2;
    }

    private void addProvider(CredentialProviderInfo cpi) {
      String componentNameString = cpi.getServiceInfo().getComponentName().flattenToString();
      if (!isProviderAllowedWithFilter(cpi)
          || !isPackageAllowed(cpi.isSystemProvider(), cpi.getServiceInfo().packageName)) {
        return;
      }
      this.mServices.put(componentNameString, cpi);
    }
  }

  private static List<CredentialProviderInfo> getUserProviders(
      Context context,
      int userId,
      boolean disableSystemAppVerificationForTests,
      Set<ComponentName> enabledServices,
      Set<ComponentName> primaryServices) {
    List<CredentialProviderInfo> services = new ArrayList<>();
    List<ResolveInfo> resolveInfos =
        context
            .getPackageManager()
            .queryIntentServicesAsUser(
                new Intent(CredentialProviderService.SERVICE_INTERFACE),
                PackageManager.ResolveInfoFlags.m12of(128L),
                userId);
    for (ResolveInfo resolveInfo : resolveInfos) {
      ServiceInfo serviceInfo = resolveInfo.serviceInfo;
      if (serviceInfo == null) {
        Slog.m113d(TAG, "No serviceInfo found for resolveInfo, so skipping provider");
      } else {
        try {
        } catch (Exception e) {
          e = e;
        }
        try {
        } catch (Exception e2) {
          e = e2;
          Slog.m116e(TAG, "Error getting info for " + serviceInfo, e);
        }
        try {
          CredentialProviderInfo cpi =
              create(
                  context,
                  serviceInfo,
                  false,
                  disableSystemAppVerificationForTests,
                  enabledServices.contains(serviceInfo.getComponentName()),
                  primaryServices.contains(serviceInfo.getComponentName()));
          if (!cpi.isSystemProvider()) {
            services.add(cpi);
          }
        } catch (Exception e3) {
          e = e3;
          Slog.m116e(TAG, "Error getting info for " + serviceInfo, e);
        }
      }
    }
    return services;
  }
}
