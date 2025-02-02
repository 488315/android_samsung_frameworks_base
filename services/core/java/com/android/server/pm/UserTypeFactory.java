package com.android.server.pm;

import android.R;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public abstract class UserTypeFactory {
  public static ArrayMap getUserTypes() {
    ArrayMap defaultBuilders = getDefaultBuilders();
    XmlResourceParser xml = Resources.getSystem().getXml(R.xml.global_keys);
    try {
      customizeBuilders(defaultBuilders, xml);
      if (xml != null) {
        xml.close();
      }
      ArrayMap arrayMap = new ArrayMap(defaultBuilders.size());
      for (int i = 0; i < defaultBuilders.size(); i++) {
        arrayMap.put(
            (String) defaultBuilders.keyAt(i),
            ((UserTypeDetails.Builder) defaultBuilders.valueAt(i)).createUserTypeDetails());
      }
      return arrayMap;
    } catch (Throwable th) {
      if (xml != null) {
        try {
          xml.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public static ArrayMap getDefaultBuilders() {
    ArrayMap arrayMap = new ArrayMap();
    arrayMap.put("android.os.usertype.profile.MANAGED", getDefaultTypeProfileManaged());
    arrayMap.put("android.os.usertype.full.SYSTEM", getDefaultTypeFullSystem());
    arrayMap.put("android.os.usertype.full.SECONDARY", getDefaultTypeFullSecondary());
    arrayMap.put("android.os.usertype.full.GUEST", getDefaultTypeFullGuest());
    arrayMap.put("android.os.usertype.full.DEMO", getDefaultTypeFullDemo());
    arrayMap.put("android.os.usertype.full.RESTRICTED", getDefaultTypeFullRestricted());
    arrayMap.put("android.os.usertype.system.HEADLESS", getDefaultTypeSystemHeadless());
    arrayMap.put("android.os.usertype.profile.CLONE", getDefaultTypeProfileClone());
    if (Build.IS_DEBUGGABLE) {
      arrayMap.put("android.os.usertype.profile.TEST", getDefaultTypeProfileTest());
    }
    return arrayMap;
  }

  public static UserTypeDetails.Builder getDefaultTypeProfileClone() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.profile.CLONE")
        .setBaseType(IInstalld.FLAG_USE_QUOTA)
        .setMaxAllowedPerParent(1)
        .setLabel(0)
        .setIconBadge(R.drawable.ic_call_decline)
        .setBadgePlain(R.drawable.ic_call_answer_video)
        .setBadgeNoBackground(R.drawable.ic_call_answer_video)
        .setBadgeLabels(R.string.config_signalAttributionPath)
        .setBadgeColors(R.color.system_neutral2_800)
        .setDarkThemeBadgeColors(R.color.system_neutral2_900)
        .setIconBadge(R.drawable.ic_dialog_usb)
        .setBadgePlain(R.drawable.ic_dialog_usb)
        .setBadgeNoBackground(R.drawable.$progress_indeterminate_horizontal_material__1)
        .setBadgeLabels(R.string.permdesc_route_media_output)
        .setBadgeColors(R.color.control_highlight_material)
        .setDarkThemeBadgeColors(R.color.surface_variant_light)
        .setDefaultRestrictions(null)
        .setDefaultCrossProfileIntentFilters(getDefaultCloneCrossProfileIntentFilter())
        .setDefaultSecureSettings(getDefaultNonManagedProfileSecureSettings())
        .setDefaultUserProperties(
            new UserProperties.Builder()
                .setStartWithParent(true)
                .setShowInLauncher(0)
                .setShowInSettings(0)
                .setInheritDevicePolicy(1)
                .setUseParentsContacts(true)
                .setUpdateCrossProfileIntentFiltersOnOTA(true)
                .setCrossProfileIntentFilterAccessControl(10)
                .setCrossProfileIntentResolutionStrategy(1)
                .setMediaSharedWithParent(true)
                .setCredentialShareableWithParent(true)
                .setDeleteAppWithParent(true));
  }

  public static UserTypeDetails.Builder getDefaultTypeProfileManaged() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.profile.MANAGED")
        .setBaseType(IInstalld.FLAG_USE_QUOTA)
        .setDefaultUserInfoPropertyFlags(32)
        .setMaxAllowedPerParent(1)
        .setLabel(0)
        .setIconBadge(R.drawable.ic_clone_icon_badge)
        .setBadgePlain(R.drawable.ic_clear_search_api_disabled_holo_dark)
        .setBadgeNoBackground(R.drawable.ic_clear_search_api_holo_dark)
        .setBadgeLabels(
            R.string.permdesc_route_media_output,
            R.string.permdesc_runInBackground,
            R.string.permdesc_schedule_exact_alarm)
        .setBadgeColors(
            R.color.surface_variant_dark,
            R.color.switch_thumb_disabled_material_dark,
            R.color.switch_thumb_material_dark)
        .setDarkThemeBadgeColors(
            R.color.surface_variant_light,
            R.color.switch_thumb_disabled_material_light,
            R.color.switch_thumb_material_light)
        .setDefaultRestrictions(getDefaultManagedProfileRestrictions())
        .setDefaultSecureSettings(getDefaultManagedProfileSecureSettings())
        .setDefaultCrossProfileIntentFilters(getDefaultManagedCrossProfileIntentFilter())
        .setDefaultUserProperties(
            new UserProperties.Builder()
                .setStartWithParent(true)
                .setShowInLauncher(1)
                .setShowInSettings(1)
                .setCredentialShareableWithParent(true));
  }

  public static UserTypeDetails.Builder getDefaultTypeProfileTest() {
    Bundle bundle = new Bundle();
    bundle.putBoolean("no_fun", true);
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.profile.TEST")
        .setBaseType(IInstalld.FLAG_USE_QUOTA)
        .setMaxAllowedPerParent(2)
        .setLabel(0)
        .setIconBadge(R.drawable.ic_star_half_black_48dp)
        .setBadgePlain(R.drawable.ic_star_half_black_16dp)
        .setBadgeNoBackground(R.drawable.ic_star_half_black_36dp)
        .setBadgeLabels(
            R.string.permdesc_route_media_output,
            R.string.permdesc_runInBackground,
            R.string.permdesc_schedule_exact_alarm)
        .setBadgeColors(
            R.color.surface_variant_dark,
            R.color.switch_thumb_disabled_material_dark,
            R.color.switch_thumb_material_dark)
        .setDarkThemeBadgeColors(
            R.color.surface_variant_light,
            R.color.switch_thumb_disabled_material_light,
            R.color.switch_thumb_material_light)
        .setDefaultRestrictions(bundle)
        .setDefaultSecureSettings(getDefaultNonManagedProfileSecureSettings());
  }

  public static UserTypeDetails.Builder getDefaultTypeFullSecondary() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.full.SECONDARY")
        .setBaseType(1024)
        .setMaxAllowed(-1)
        .setDefaultRestrictions(getDefaultSecondaryUserRestrictions());
  }

  public static UserTypeDetails.Builder getDefaultTypeFullGuest() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.full.GUEST")
        .setBaseType(1024)
        .setDefaultUserInfoPropertyFlags(
            (Resources.getSystem().getBoolean(R.bool.config_enableWifiDisplay) ? 256 : 0) | 4)
        .setMaxAllowed(1)
        .setDefaultRestrictions(getDefaultGuestUserRestrictions());
  }

  public static UserTypeDetails.Builder getDefaultTypeFullDemo() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.full.DEMO")
        .setBaseType(1024)
        .setDefaultUserInfoPropertyFlags(512)
        .setMaxAllowed(-1)
        .setDefaultRestrictions(null);
  }

  public static UserTypeDetails.Builder getDefaultTypeFullRestricted() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.full.RESTRICTED")
        .setBaseType(1024)
        .setDefaultUserInfoPropertyFlags(8)
        .setMaxAllowed(-1)
        .setDefaultRestrictions(null);
  }

  public static UserTypeDetails.Builder getDefaultTypeFullSystem() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.full.SYSTEM")
        .setBaseType(3072)
        .setDefaultUserInfoPropertyFlags(16387)
        .setMaxAllowed(1);
  }

  public static UserTypeDetails.Builder getDefaultTypeSystemHeadless() {
    return new UserTypeDetails.Builder()
        .setName("android.os.usertype.system.HEADLESS")
        .setBaseType(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES)
        .setDefaultUserInfoPropertyFlags(3)
        .setMaxAllowed(1);
  }

  public static Bundle getDefaultSecondaryUserRestrictions() {
    Bundle bundle = new Bundle();
    bundle.putBoolean("no_outgoing_calls", true);
    bundle.putBoolean("no_sms", true);
    return bundle;
  }

  public static Bundle getDefaultGuestUserRestrictions() {
    Bundle defaultSecondaryUserRestrictions = getDefaultSecondaryUserRestrictions();
    defaultSecondaryUserRestrictions.putBoolean("no_config_wifi", true);
    defaultSecondaryUserRestrictions.putBoolean("no_install_unknown_sources", true);
    defaultSecondaryUserRestrictions.putBoolean("no_config_credentials", true);
    return defaultSecondaryUserRestrictions;
  }

  public static Bundle getDefaultManagedProfileRestrictions() {
    Bundle bundle = new Bundle();
    bundle.putBoolean("no_wallpaper", true);
    return bundle;
  }

  public static Bundle getDefaultManagedProfileSecureSettings() {
    Bundle bundle = new Bundle();
    bundle.putString("managed_profile_contact_remote_search", "1");
    bundle.putString("cross_profile_calendar_enabled", "1");
    return bundle;
  }

  public static List getDefaultManagedCrossProfileIntentFilter() {
    return DefaultCrossProfileIntentFiltersUtils.getDefaultManagedProfileFilters();
  }

  public static List getDefaultCloneCrossProfileIntentFilter() {
    return DefaultCrossProfileIntentFiltersUtils.getDefaultCloneProfileFilters();
  }

  public static Bundle getDefaultNonManagedProfileSecureSettings() {
    Bundle bundle = new Bundle();
    bundle.putString("user_setup_complete", "1");
    return bundle;
  }

  public static void customizeBuilders(ArrayMap arrayMap, XmlResourceParser xmlResourceParser) {
    boolean z;
    final UserTypeDetails.Builder builder;
    try {
      XmlUtils.beginDocument(xmlResourceParser, "user-types");
      XmlUtils.nextElement(xmlResourceParser);
      while (true) {
        boolean z2 = true;
        if (xmlResourceParser.getEventType() == 1) {
          return;
        }
        String name = xmlResourceParser.getName();
        if ("profile-type".equals(name)) {
          z = true;
        } else if ("full-type".equals(name)) {
          z = false;
        } else {
          if ("change-user-type".equals(name)) {
            XmlUtils.skipCurrentTag(xmlResourceParser);
          } else {
            Slog.w(
                "UserTypeFactory",
                "Skipping unknown element "
                    + name
                    + " in "
                    + xmlResourceParser.getPositionDescription());
            XmlUtils.skipCurrentTag(xmlResourceParser);
          }
          XmlUtils.nextElement(xmlResourceParser);
        }
        String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
        if (attributeValue != null && !attributeValue.equals("")) {
          String intern = attributeValue.intern();
          if (intern.startsWith("android.")) {
            Slog.i("UserTypeFactory", "Customizing user type " + intern);
            builder = (UserTypeDetails.Builder) arrayMap.get(intern);
            if (builder == null) {
              throw new IllegalArgumentException(
                  "Illegal custom user type name "
                      + intern
                      + ": Non-AOSP user types cannot start with 'android.'");
            }
            if ((!z || builder.getBaseType() != 4096) && (z || builder.getBaseType() != 1024)) {
              z2 = false;
            }
            throw new IllegalArgumentException(
                "Wrong base type to customize user type ("
                    + intern
                    + "), which is type "
                    + UserInfo.flagsToString(builder.getBaseType()));
          }
          if (z) {
            Slog.i("UserTypeFactory", "Creating custom user type " + intern);
            builder = new UserTypeDetails.Builder();
            builder.setName(intern);
            builder.setBaseType(IInstalld.FLAG_USE_QUOTA);
            arrayMap.put(intern, builder);
          } else {
            throw new IllegalArgumentException(
                "Creation of non-profile user type (" + intern + ") is not currently supported.");
          }
          if (z) {
            setIntAttribute(
                xmlResourceParser,
                "max-allowed-per-parent",
                new Consumer() { // from class:
                  // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    UserTypeDetails.Builder.this.setMaxAllowedPerParent(((Integer) obj).intValue());
                  }
                });
            setResAttribute(
                xmlResourceParser,
                "icon-badge",
                new Consumer() { // from class:
                  // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda1
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    UserTypeDetails.Builder.this.setIconBadge(((Integer) obj).intValue());
                  }
                });
            setResAttribute(
                xmlResourceParser,
                "badge-plain",
                new Consumer() { // from class:
                  // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda2
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    UserTypeDetails.Builder.this.setBadgePlain(((Integer) obj).intValue());
                  }
                });
            setResAttribute(
                xmlResourceParser,
                "badge-no-background",
                new Consumer() { // from class:
                  // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda3
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    UserTypeDetails.Builder.this.setBadgeNoBackground(((Integer) obj).intValue());
                  }
                });
          }
          setIntAttribute(
              xmlResourceParser,
              "enabled",
              new Consumer() { // from class:
                // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  UserTypeDetails.Builder.this.setEnabled(((Integer) obj).intValue());
                }
              });
          setIntAttribute(
              xmlResourceParser,
              "max-allowed",
              new Consumer() { // from class:
                // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  UserTypeDetails.Builder.this.setMaxAllowed(((Integer) obj).intValue());
                }
              });
          int depth = xmlResourceParser.getDepth();
          while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            String name2 = xmlResourceParser.getName();
            if ("default-restrictions".equals(name2)) {
              builder.setDefaultRestrictions(
                  UserRestrictionsUtils.readRestrictions(XmlUtils.makeTyped(xmlResourceParser)));
            } else if (z && "badge-labels".equals(name2)) {
              setResAttributeArray(
                  xmlResourceParser,
                  new Consumer() { // from class:
                    // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      UserTypeDetails.Builder.this.setBadgeLabels((int[]) obj);
                    }
                  });
            } else if (z && "badge-colors".equals(name2)) {
              setResAttributeArray(
                  xmlResourceParser,
                  new Consumer() { // from class:
                    // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      UserTypeDetails.Builder.this.setBadgeColors((int[]) obj);
                    }
                  });
            } else if (z && "badge-colors-dark".equals(name2)) {
              setResAttributeArray(
                  xmlResourceParser,
                  new Consumer() { // from class:
                    // com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      UserTypeDetails.Builder.this.setDarkThemeBadgeColors((int[]) obj);
                    }
                  });
            } else if ("user-properties".equals(name2)) {
              builder
                  .getDefaultUserProperties()
                  .updateFromXml(XmlUtils.makeTyped(xmlResourceParser));
            } else {
              Slog.w(
                  "UserTypeFactory",
                  "Unrecognized tag "
                      + name2
                      + " in "
                      + xmlResourceParser.getPositionDescription());
            }
          }
          XmlUtils.nextElement(xmlResourceParser);
        }
        Slog.w(
            "UserTypeFactory",
            "Skipping user type with no name in " + xmlResourceParser.getPositionDescription());
        XmlUtils.skipCurrentTag(xmlResourceParser);
        XmlUtils.nextElement(xmlResourceParser);
      }
    } catch (IOException | XmlPullParserException e) {
      Slog.w("UserTypeFactory", "Cannot read user type configuration file.", e);
    }
  }

  public static void setIntAttribute(
      XmlResourceParser xmlResourceParser, String str, Consumer consumer) {
    String attributeValue = xmlResourceParser.getAttributeValue(null, str);
    if (attributeValue == null) {
      return;
    }
    try {
      consumer.accept(Integer.valueOf(Integer.parseInt(attributeValue)));
    } catch (NumberFormatException e) {
      Slog.e(
          "UserTypeFactory",
          "Cannot parse value of '"
              + attributeValue
              + "' for "
              + str
              + " in "
              + xmlResourceParser.getPositionDescription(),
          e);
      throw e;
    }
  }

  public static void setResAttribute(
      XmlResourceParser xmlResourceParser, String str, Consumer consumer) {
    if (xmlResourceParser.getAttributeValue(null, str) == null) {
      return;
    }
    consumer.accept(Integer.valueOf(xmlResourceParser.getAttributeResourceValue(null, str, 0)));
  }

  public static void setResAttributeArray(XmlResourceParser xmlResourceParser, Consumer consumer) {
    ArrayList arrayList = new ArrayList();
    int depth = xmlResourceParser.getDepth();
    while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
      String name = xmlResourceParser.getName();
      if (!"item".equals(name)) {
        Slog.w(
            "UserTypeFactory",
            "Skipping unknown child element "
                + name
                + " in "
                + xmlResourceParser.getPositionDescription());
        XmlUtils.skipCurrentTag(xmlResourceParser);
      } else {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, "res", -1);
        if (attributeResourceValue != -1) {
          arrayList.add(Integer.valueOf(attributeResourceValue));
        }
      }
    }
    int[] iArr = new int[arrayList.size()];
    for (int i = 0; i < arrayList.size(); i++) {
      iArr[i] = ((Integer) arrayList.get(i)).intValue();
    }
    consumer.accept(iArr);
  }

  public static int getUserTypeVersion() {
    XmlResourceParser xml = Resources.getSystem().getXml(R.xml.global_keys);
    try {
      int userTypeVersion = getUserTypeVersion(xml);
      if (xml != null) {
        xml.close();
      }
      return userTypeVersion;
    } catch (Throwable th) {
      if (xml != null) {
        try {
          xml.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public static int getUserTypeVersion(XmlResourceParser xmlResourceParser) {
    try {
      XmlUtils.beginDocument(xmlResourceParser, "user-types");
      String attributeValue = xmlResourceParser.getAttributeValue(null, "version");
      if (attributeValue == null) {
        return 0;
      }
      try {
        return Integer.parseInt(attributeValue);
      } catch (NumberFormatException e) {
        Slog.e(
            "UserTypeFactory",
            "Cannot parse value of '"
                + attributeValue
                + "' for version in "
                + xmlResourceParser.getPositionDescription(),
            e);
        throw e;
      }
    } catch (IOException | XmlPullParserException e2) {
      Slog.w("UserTypeFactory", "Cannot read user type configuration file.", e2);
      return 0;
    }
  }

  public static List getUserTypeUpgrades() {
    XmlResourceParser xml = Resources.getSystem().getXml(R.xml.global_keys);
    try {
      List parseUserUpgrades = parseUserUpgrades(getDefaultBuilders(), xml);
      if (xml != null) {
        xml.close();
      }
      return parseUserUpgrades;
    } catch (Throwable th) {
      if (xml != null) {
        try {
          xml.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public static List parseUserUpgrades(ArrayMap arrayMap, XmlResourceParser xmlResourceParser) {
    ArrayList arrayList = new ArrayList();
    try {
      XmlUtils.beginDocument(xmlResourceParser, "user-types");
      XmlUtils.nextElement(xmlResourceParser);
      while (xmlResourceParser.getEventType() != 1) {
        if ("change-user-type".equals(xmlResourceParser.getName())) {
          String attributeValue = xmlResourceParser.getAttributeValue(null, "from");
          String attributeValue2 = xmlResourceParser.getAttributeValue(null, "to");
          validateUserTypeIsProfile(attributeValue, arrayMap);
          validateUserTypeIsProfile(attributeValue2, arrayMap);
          try {
            arrayList.add(
                new UserTypeUpgrade(
                    attributeValue,
                    attributeValue2,
                    Integer.parseInt(xmlResourceParser.getAttributeValue(null, "whenVersionLeq"))));
          } catch (NumberFormatException e) {
            Slog.e(
                "UserTypeFactory",
                "Cannot parse value of whenVersionLeq in "
                    + xmlResourceParser.getPositionDescription(),
                e);
            throw e;
          }
        } else {
          XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        XmlUtils.nextElement(xmlResourceParser);
      }
    } catch (IOException | XmlPullParserException e2) {
      Slog.w("UserTypeFactory", "Cannot read user type configuration file.", e2);
    }
    return arrayList;
  }

  public static void validateUserTypeIsProfile(String str, ArrayMap arrayMap) {
    UserTypeDetails.Builder builder = (UserTypeDetails.Builder) arrayMap.get(str);
    if (builder == null || builder.getBaseType() == 4096) {
      return;
    }
    throw new IllegalArgumentException(
        "Illegal upgrade of user type " + str + " : Can only upgrade profiles user types");
  }

  public class UserTypeUpgrade {
    public final String mFromType;
    public final String mToType;
    public final int mUpToVersion;

    public UserTypeUpgrade(String str, String str2, int i) {
      this.mFromType = str;
      this.mToType = str2;
      this.mUpToVersion = i;
    }

    public String getFromType() {
      return this.mFromType;
    }

    public String getToType() {
      return this.mToType;
    }

    public int getUpToVersion() {
      return this.mUpToVersion;
    }
  }
}
