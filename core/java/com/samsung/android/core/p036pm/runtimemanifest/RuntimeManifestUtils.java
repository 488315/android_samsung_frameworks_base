package com.samsung.android.core.p036pm.runtimemanifest;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.p009os.Bundle;
import android.p009os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TypedValue;
import com.android.internal.C4337R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class RuntimeManifestUtils {
  public static final String ATTR_MAX_VALUE = "maxValue";
  public static final String ATTR_MIN_VALUE = "minValue";
  public static final String ATTR_PROPERTY_NAME = "propertyName";
  public static final String ATTR_PROPERTY_VALUE = "propertyValue";
  public static final String ATTR_TYPE = "type";
  public static final String ATTR_VALUE = "value";
  public static final boolean DEBUG = true;
  public static final String META_RUNTIME_MANIFEST = "runtime.manifest.overlay";
  public static final String TAG = "RuntimeManifestUtils";
  public static final String TAG_ACTIVITY = "activity";
  public static final String TAG_APPLICATION = "application";
  public static final String TAG_POLICY = "policy";
  public static final String TAG_PROVIDER = "provider";
  public static final String TAG_RECEIVER = "receiver";
  public static final String TAG_RUNTIME_MANIFEST = "runtime-manifest";
  public static final String TAG_SERVICE = "service";
  public static final String SALESCODE = SystemProperties.get("ro.csc.sales_code");
  public static final String COUNTRYCODE = SystemProperties.get("ro.csc.countryiso_code");
  public static final String ONEUI_VERSION = SystemProperties.get("ro.build.version.oneui");
  private static boolean sIsTest = false;
  private static String sSalesCodeForTest = "";
  private static String sCountryCodeForTest = "";
  private static String sOneuiVersionForTest = "";

  public static String getSalesCode() {
    return sIsTest ? sSalesCodeForTest : SALESCODE;
  }

  public static String getCountryCode() {
    return sIsTest ? sCountryCodeForTest : COUNTRYCODE;
  }

  public static long getOneUiVersion() {
    String versionStr = sIsTest ? sOneuiVersionForTest : ONEUI_VERSION;
    try {
      return Long.parseLong(versionStr);
    } catch (NumberFormatException e) {
      return -1L;
    }
  }

  public static void setSalesCodeForTest(String salesCode) {
    sSalesCodeForTest = salesCode;
  }

  public static void setCountryCode(String countryCode) {
    sCountryCodeForTest = countryCode;
  }

  public static void setOneUiVersionForTest(String oneUiVersion) {
    sOneuiVersionForTest = oneUiVersion;
  }

  public static void setTestMode(boolean enable) {
    sIsTest = enable;
  }

  static List<RuntimeManifestPolicies.PolicyInfo> parseOverlayPolicies(
      XmlResourceParser parser, Resources res) throws IOException, XmlPullParserException {
    int outerDepth;
    List<RuntimeManifestPolicies.PolicyInfo> policies = new ArrayList<>();
    int outerDepth2 = parser.getDepth();
    while (true) {
      int parserType = parser.next();
      if (parserType != 1 && (parserType != 3 || parser.getDepth() > outerDepth2)) {
        if (parserType == 3) {
          outerDepth = outerDepth2;
        } else if (parserType == 4) {
          outerDepth = outerDepth2;
        } else {
          RuntimeManifestPolicies.PolicyInfo policy = new RuntimeManifestPolicies.PolicyInfo();
          String elementName = parser.getName();
          if (elementName.equals(TAG_POLICY)) {
            String type = parser.getAttributeValue(null, "type");
            if (!TextUtils.isEmpty(type)) {
              policy.setType(type);
            }
            String value = parser.getAttributeValue(null, "value");
            if (!TextUtils.isEmpty(value)) {
              policy.setValue(value);
            }
            String minValue = parser.getAttributeValue(null, ATTR_MIN_VALUE);
            if (!TextUtils.isEmpty(minValue)) {
              policy.setMinValue(minValue);
            }
            String maxValue = parser.getAttributeValue(null, ATTR_MAX_VALUE);
            if (!TextUtils.isEmpty(maxValue)) {
              policy.setMaxValue(maxValue);
            }
            String propertyName = parser.getAttributeValue(null, ATTR_PROPERTY_NAME);
            if (!TextUtils.isEmpty(propertyName)) {
              policy.setPropertyName(propertyName);
            }
            String propertyValue = parser.getAttributeValue(null, ATTR_PROPERTY_VALUE);
            if (!TextUtils.isEmpty(propertyValue)) {
              policy.setPropertyValue(propertyValue);
            }
            TypedArray ta = res.obtainAttributes(parser, C4337R.styleable.AndroidManifestActivity);
            try {
              TypedValue v = ta.peekValue(1);
              if (v == null) {
                outerDepth = outerDepth2;
              } else {
                outerDepth = outerDepth2;
                try {
                  int outerDepth3 = v.resourceId;
                  if (outerDepth3 == 0) {
                    policy.setLabelRes(0);
                    policy.setCoercedLabel(v.coerceToString());
                  } else {
                    policy.setLabelRes(v.resourceId);
                    policy.setCoercedLabel(null);
                  }
                } catch (Throwable th) {
                  th = th;
                  throw th;
                }
              }
              int iconValue = ta.getResourceId(2, 0);
              if (iconValue != 0) {
                policy.setIconRes(iconValue);
              }
              if (ta.hasValueOrEmpty(5)) {
                boolean enabled = ta.getBoolean(5, true);
                policy.setEnabled(enabled);
              }
              ta.recycle();
              ta = res.obtainAttributes(parser, C4337R.styleable.AndroidManifestIntentFilter);
              try {
                int priority = ta.getInt(2, 0);
                policy.setPriority(priority);
                policies.add(policy);
                ta.recycle();
                Slog.m113d("RuntimeManifestUtils", "Parsed " + policy.toString());
              } finally {
                ta.recycle();
              }
            } catch (Throwable th2) {
              th = th2;
            }
          } else {
            outerDepth = outerDepth2;
            Slog.m113d(
                "RuntimeManifestUtils", "Unknown element under <runtime-manifest>: " + elementName);
            XmlUtils.skipCurrentTag(parser);
          }
        }
        outerDepth2 = outerDepth;
      }
    }
    return policies;
  }

  public static RuntimeManifestPolicies parseRuntimeManifestPolicies(
      XmlResourceParser parser, Resources res) throws IOException, XmlPullParserException {
    RuntimeManifestPolicies overlay = new RuntimeManifestPolicies();
    List<RuntimeManifestPolicies.PolicyInfo> application = new ArrayList<>();
    Map<String, List<RuntimeManifestPolicies.PolicyInfo>> activities = new HashMap<>();
    Map<String, List<RuntimeManifestPolicies.PolicyInfo>> receivers = new HashMap<>();
    Map<String, List<RuntimeManifestPolicies.PolicyInfo>> services = new HashMap<>();
    Map<String, List<RuntimeManifestPolicies.PolicyInfo>> providers = new HashMap<>();
    XmlUtils.beginDocument(parser, TAG_RUNTIME_MANIFEST);
    while (true) {
      XmlUtils.nextElement(parser);
      String elementName = parser.getName();
      if (elementName != null) {
        if (elementName.equals("application")) {
          application.addAll(parseOverlayPolicies(parser, res));
        } else {
          TypedArray ta = res.obtainAttributes(parser, C4337R.styleable.AndroidManifestActivity);
          try {
            String name = ta.getString(3);
            if (name == null) {
              continue;
            } else if (elementName.equals("activity")) {
              List<RuntimeManifestPolicies.PolicyInfo> policies = parseOverlayPolicies(parser, res);
              if (!activities.containsKey(name) && policies.size() > 0) {
                activities.put(name, policies);
              }
            } else if (elementName.equals("receiver")) {
              List<RuntimeManifestPolicies.PolicyInfo> policies2 =
                  parseOverlayPolicies(parser, res);
              if (!receivers.containsKey(name) && policies2.size() > 0) {
                receivers.put(name, policies2);
              }
            } else if (elementName.equals("service")) {
              List<RuntimeManifestPolicies.PolicyInfo> policies3 =
                  parseOverlayPolicies(parser, res);
              if (!services.containsKey(name) && policies3.size() > 0) {
                services.put(name, policies3);
              }
            } else if (elementName.equals(TAG_PROVIDER)) {
              List<RuntimeManifestPolicies.PolicyInfo> policies4 =
                  parseOverlayPolicies(parser, res);
              if (!providers.containsKey(name) && policies4.size() > 0) {
                providers.put(name, policies4);
              }
            } else {
              throw new XmlPullParserException(
                  "Unknown element under <runtime-manifest>: " + elementName);
            }
          } finally {
            ta.recycle();
          }
        }
      } else {
        overlay.addApplicationPolicies(application);
        overlay.addActivityPolicies(activities);
        overlay.addReceiverPolicies(receivers);
        overlay.addServicePolicies(services);
        overlay.addProviderPolicies(providers);
        return overlay;
      }
    }
  }

  public static RuntimeManifestPolicies.PolicyInfo getMatchingPolicy(
      List<RuntimeManifestPolicies.PolicyInfo> policies) {
    for (RuntimeManifestPolicies.PolicyInfo policy : policies) {
      String type = policy.getType();
      if (!TextUtils.isEmpty(type)) {
        if (type.equalsIgnoreCase("SALESCODE")) {
          String value = policy.getValue();
          if (!TextUtils.isEmpty(value) && value.equalsIgnoreCase(getSalesCode())) {
            Slog.m113d("RuntimeManifestUtils", "Matched policy(salescode): " + policy);
            return policy;
          }
        } else if (type.equalsIgnoreCase("COUNTRYCODE")) {
          String value2 = policy.getValue();
          if (!TextUtils.isEmpty(value2) && value2.equalsIgnoreCase(getCountryCode())) {
            Slog.m113d("RuntimeManifestUtils", "Matched policy(countrycode): " + policy);
            return policy;
          }
        } else if (type.equalsIgnoreCase("ONEUI")
            && matchOneUiPolicy(policy.getMinValue(), policy.getMaxValue(), getOneUiVersion())) {
          Slog.m113d("RuntimeManifestUtils", "Matched policy(oneui): " + policy);
          return policy;
        }
      }
    }
    return null;
  }

  private static boolean matchOneUiPolicy(long minValue, long maxValue, long curVersion) {
    if (curVersion < 0) {
      Slog.m121w("RuntimeManifestUtils", "Invalid current OneUi version " + curVersion);
      return false;
    }
    if (minValue < 0 && maxValue < 0) {
      Slog.m121w(
          "RuntimeManifestUtils", "Invalid value set, min: " + minValue + ", max: " + maxValue);
      return false;
    }
    if (minValue >= 0 && curVersion < minValue) {
      Slog.m121w("RuntimeManifestUtils", "It's lower than minValue " + minValue);
      return false;
    }
    if (maxValue >= 0 && curVersion > maxValue) {
      Slog.m121w("RuntimeManifestUtils", "It's higher than maxValue " + maxValue);
      return false;
    }
    return true;
  }

  public static boolean useLegacyRuntimeManifest(Bundle metaData) {
    return metaData == null || !metaData.containsKey(META_RUNTIME_MANIFEST);
  }

  public static String buildClassName(String pkg, CharSequence clsSeq) {
    if (clsSeq == null || clsSeq.length() <= 0) {
      return null;
    }
    String cls = clsSeq.toString();
    char c = cls.charAt(0);
    if (c == '.') {
      return pkg + cls;
    }
    if (cls.indexOf(46) < 0) {
      return pkg + '.' + cls;
    }
    return cls;
  }
}
