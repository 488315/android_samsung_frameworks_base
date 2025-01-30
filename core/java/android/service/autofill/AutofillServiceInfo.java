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
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.C4337R;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class AutofillServiceInfo {
  private static final String TAG = "AutofillServiceInfo";
  private static final String TAG_AUTOFILL_SERVICE = "autofill-service";
  private static final String TAG_COMPATIBILITY_PACKAGE = "compatibility-package";
  private final ArrayMap<String, Long> mCompatibilityPackages;
  private final boolean mInlineSuggestionsEnabled;
  private final String mPasswordsActivity;
  private final ServiceInfo mServiceInfo;
  private final String mSettingsActivity;

  private static ServiceInfo getServiceInfoOrThrow(ComponentName comp, int userHandle)
      throws PackageManager.NameNotFoundException {
    try {
      ServiceInfo si = AppGlobals.getPackageManager().getServiceInfo(comp, 128L, userHandle);
      if (si != null) {
        return si;
      }
    } catch (RemoteException e) {
    }
    throw new PackageManager.NameNotFoundException(comp.toString());
  }

  public AutofillServiceInfo(Context context, ComponentName comp, int userHandle)
      throws PackageManager.NameNotFoundException {
    this(context, getServiceInfoOrThrow(comp, userHandle));
  }

  public AutofillServiceInfo(Context context, ServiceInfo si) {
    if (!Manifest.permission.BIND_AUTOFILL_SERVICE.equals(si.permission)) {
      if (Manifest.permission.BIND_AUTOFILL.equals(si.permission)) {
        Log.m102w(
            TAG,
            "AutofillService from '"
                + si.packageName
                + "' uses unsupported permission "
                + Manifest.permission.BIND_AUTOFILL
                + ". It works for now, but might not be supported on future releases");
        new MetricsLogger()
            .write(
                new LogMaker(MetricsProto.MetricsEvent.AUTOFILL_INVALID_PERMISSION)
                    .setPackageName(si.packageName));
      } else {
        Log.m102w(
            TAG,
            "AutofillService from '"
                + si.packageName
                + "' does not require permission "
                + Manifest.permission.BIND_AUTOFILL_SERVICE);
        throw new SecurityException(
            "Service does not require permission android.permission.BIND_AUTOFILL_SERVICE");
      }
    }
    this.mServiceInfo = si;
    XmlResourceParser parser =
        si.loadXmlMetaData(context.getPackageManager(), AutofillService.SERVICE_META_DATA);
    if (parser == null) {
      this.mSettingsActivity = null;
      this.mPasswordsActivity = null;
      this.mCompatibilityPackages = null;
      this.mInlineSuggestionsEnabled = false;
      return;
    }
    String settingsActivity = null;
    String passwordsActivity = null;
    ArrayMap<String, Long> compatibilityPackages = null;
    boolean inlineSuggestionsEnabled = false;
    try {
      Resources resources =
          context.getPackageManager().getResourcesForApplication(si.applicationInfo);
      for (int type = 0; type != 1 && type != 2; type = parser.next()) {}
      if (TAG_AUTOFILL_SERVICE.equals(parser.getName())) {
        AttributeSet allAttributes = Xml.asAttributeSet(parser);
        TypedArray afsAttributes = null;
        try {
          afsAttributes =
              resources.obtainAttributes(allAttributes, C4337R.styleable.AutofillService);
          settingsActivity = afsAttributes.getString(0);
          passwordsActivity = afsAttributes.getString(2);
          inlineSuggestionsEnabled = afsAttributes.getBoolean(1, false);
          compatibilityPackages = parseCompatibilityPackages(parser, resources);
        } finally {
          if (afsAttributes != null) {
            afsAttributes.recycle();
          }
        }
      } else {
        Log.m96e(TAG, "Meta-data does not start with autofill-service tag");
      }
    } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
      Log.m97e(TAG, "Error parsing auto fill service meta-data", e);
    }
    this.mSettingsActivity = settingsActivity;
    this.mPasswordsActivity = passwordsActivity;
    this.mCompatibilityPackages = compatibilityPackages;
    this.mInlineSuggestionsEnabled = inlineSuggestionsEnabled;
  }

  private ArrayMap<String, Long> parseCompatibilityPackages(
      XmlPullParser parser, Resources resources) throws IOException, XmlPullParserException {
    Long maxVersionCode;
    int outerDepth = parser.getDepth();
    ArrayMap<String, Long> compatibilityPackages = null;
    while (true) {
      int type = parser.next();
      if (type != 1 && (type != 3 || parser.getDepth() > outerDepth)) {
        if (type != 3 && type != 4 && TAG_COMPATIBILITY_PACKAGE.equals(parser.getName())) {
          TypedArray cpAttributes = null;
          try {
            AttributeSet allAttributes = Xml.asAttributeSet(parser);
            try {
              cpAttributes =
                  resources.obtainAttributes(
                      allAttributes, C4337R.styleable.AutofillService_CompatibilityPackage);
              String name = cpAttributes.getString(0);
              if (TextUtils.isEmpty(name)) {
                Log.m96e(TAG, "Invalid compatibility package:" + name);
                XmlUtils.skipCurrentTag(parser);
                if (cpAttributes != null) {
                  cpAttributes.recycle();
                }
              } else {
                String maxVersionCodeStr = cpAttributes.getString(1);
                if (maxVersionCodeStr != null) {
                  try {
                    maxVersionCode = Long.valueOf(Long.parseLong(maxVersionCodeStr));
                    if (maxVersionCode.longValue() < 0) {
                      Log.m96e(TAG, "Invalid compatibility max version code:" + maxVersionCode);
                      XmlUtils.skipCurrentTag(parser);
                      if (cpAttributes != null) {
                        cpAttributes.recycle();
                      }
                    }
                  } catch (NumberFormatException e) {
                    Log.m96e(TAG, "Invalid compatibility max version code:" + maxVersionCodeStr);
                    XmlUtils.skipCurrentTag(parser);
                    if (cpAttributes != null) {
                      cpAttributes.recycle();
                    }
                  }
                } else {
                  maxVersionCode = Long.MAX_VALUE;
                }
                if (compatibilityPackages == null) {
                  compatibilityPackages = new ArrayMap<>();
                }
                compatibilityPackages.put(name, maxVersionCode);
                XmlUtils.skipCurrentTag(parser);
                if (cpAttributes != null) {
                  cpAttributes.recycle();
                }
              }
            } catch (Throwable th) {
              th = th;
              XmlUtils.skipCurrentTag(parser);
              if (cpAttributes != null) {
                cpAttributes.recycle();
              }
              throw th;
            }
          } catch (Throwable th2) {
            th = th2;
          }
        }
      }
    }
    return compatibilityPackages;
  }

  private AutofillServiceInfo(String passwordsActivity) {
    ServiceInfo serviceInfo = new ServiceInfo();
    this.mServiceInfo = serviceInfo;
    serviceInfo.applicationInfo = new ApplicationInfo();
    serviceInfo.packageName = "com.android.test";
    this.mSettingsActivity = null;
    this.mPasswordsActivity = passwordsActivity;
    this.mCompatibilityPackages = null;
    this.mInlineSuggestionsEnabled = false;
  }

  public static final class TestDataBuilder {
    private String mPasswordsActivity;

    public TestDataBuilder setPasswordsActivity(String passwordsActivity) {
      this.mPasswordsActivity = passwordsActivity;
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

  public static List<AutofillServiceInfo> getAvailableServices(Context context, int user) {
    List<AutofillServiceInfo> services = new ArrayList<>();
    List<ResolveInfo> resolveInfos =
        context
            .getPackageManager()
            .queryIntentServicesAsUser(new Intent(AutofillService.SERVICE_INTERFACE), 128, user);
    for (ResolveInfo resolveInfo : resolveInfos) {
      ServiceInfo serviceInfo = resolveInfo.serviceInfo;
      try {
        services.add(new AutofillServiceInfo(context, serviceInfo));
      } catch (SecurityException e) {
        Log.m102w(TAG, "Error getting info for " + serviceInfo + ": " + e);
      }
    }
    return services;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append(NavigationBarInflaterView.SIZE_MOD_START).append(this.mServiceInfo);
    builder.append(", settings:").append(this.mSettingsActivity);
    builder.append(", passwords activity:").append(this.mPasswordsActivity);
    StringBuilder append = builder.append(", hasCompatPckgs:");
    ArrayMap<String, Long> arrayMap = this.mCompatibilityPackages;
    append
        .append((arrayMap == null || arrayMap.isEmpty()) ? false : true)
        .append(NavigationBarInflaterView.SIZE_MOD_END);
    builder.append(", inline suggestions enabled:").append(this.mInlineSuggestionsEnabled);
    return builder.toString();
  }

  public void dump(String prefix, PrintWriter pw) {
    pw.print(prefix);
    pw.print("Component: ");
    pw.println(getServiceInfo().getComponentName());
    pw.print(prefix);
    pw.print("Settings: ");
    pw.println(this.mSettingsActivity);
    pw.print(prefix);
    pw.print("Passwords activity: ");
    pw.println(this.mPasswordsActivity);
    pw.print(prefix);
    pw.print("Compat packages: ");
    pw.println(this.mCompatibilityPackages);
    pw.print(prefix);
    pw.print("Inline Suggestions Enabled: ");
    pw.println(this.mInlineSuggestionsEnabled);
  }
}
