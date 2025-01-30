package com.android.server.cocktailbar.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class CocktailBarConfig {
  public static String INTENT_FILTER = "com.samsung.app.honeyspace.edge";
  public static final String TAG = "CocktailBarConfig";
  public static CocktailBarConfig sInstance;
  public ArrayList mCategoryFilter;
  public String mCategoryFilterStr;
  public Context mContext;
  public String mMetaDataHideEdgeService;
  public PackageManager mPackageManager;
  public int mVersion = 2;
  public int mPreferredWidth = 0;
  public HashSet mPackageHideEdgeServiceList = new HashSet();
  public boolean mIsServiceFounded = false;
  public final HashMap mReplacedComponent = new HashMap();

  public int getDefaultVersion() {
    return 1;
  }

  public static CocktailBarConfig getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new CocktailBarConfig(context);
    }
    return sInstance;
  }

  public CocktailBarConfig(Context context) {
    this.mContext = context;
    this.mPackageManager = context.getPackageManager();
    parseEdgeConfig();
  }

  public int getVersion() {
    return this.mVersion;
  }

  public int getPreferredWidth() {
    return this.mPreferredWidth;
  }

  public String getCategoryFilterStr() {
    return this.mCategoryFilterStr;
  }

  public HashSet getPackageHideEdgeServiceList() {
    return this.mPackageHideEdgeServiceList;
  }

  public String getMetaDataHideEdgeService() {
    return this.mMetaDataHideEdgeService;
  }

  public ArrayList getCategoryFilter() {
    if (this.mCategoryFilter == null) {
      if (this.mCategoryFilterStr != null) {
        this.mCategoryFilter = new ArrayList();
        String[] split = this.mCategoryFilterStr.split(",");
        if (split != null) {
          for (String str : split) {
            this.mCategoryFilter.add(str);
          }
        }
      } else {
        Slog.d(TAG, "getCategoryFilter: CategoryFilterStr is null");
      }
    }
    return this.mCategoryFilter;
  }

  public boolean reload() {
    if (this.mIsServiceFounded) {
      return true;
    }
    parseEdgeConfig();
    return this.mIsServiceFounded;
  }

  public final void parseEdgeConfig() {
    XmlResourceParser edgeConfigParser = getEdgeConfigParser();
    String str = SystemProperties.get("ro.product.name");
    try {
      if (edgeConfigParser != null) {
        try {
          parseXml(edgeConfigParser, str);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (XmlPullParserException e2) {
          e2.printStackTrace();
        }
      }
    } finally {
      edgeConfigParser.close();
    }
  }

  public final XmlResourceParser getEdgeConfigParser() {
    List<ResolveInfo> queryIntentServicesAsUser =
        this.mPackageManager.queryIntentServicesAsUser(new Intent(INTENT_FILTER), 128, 0);
    if (queryIntentServicesAsUser != null && queryIntentServicesAsUser.size() > 0) {
      this.mIsServiceFounded = true;
      for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
        if (!resolveInfo.serviceInfo.isEnabled()) {
          Slog.d(TAG, "getEdgeConfigParser: not enabled: " + resolveInfo.resolvePackageName);
        } else {
          if (CocktailBarUtils.CocktailBarWhiteList.isSystemApplication(
              resolveInfo.serviceInfo.packageName, 0)) {
            return resolveInfo.serviceInfo.loadXmlMetaData(
                this.mPackageManager, "com.samsung.android.edge.config");
          }
          Slog.d(TAG, "getEdgeConfigParser: not system app: " + resolveInfo.resolvePackageName);
        }
      }
      return null;
    }
    this.mIsServiceFounded = false;
    Slog.d(TAG, "getEdgeConfigParser: no enabled cocktailbarservice");
    return null;
  }

  public final void parseXml(XmlResourceParser xmlResourceParser, String str) {
    int eventType = xmlResourceParser.getEventType();
    while (eventType != 1) {
      String name = xmlResourceParser.getName();
      if (eventType == 2) {
        if ("version".equals(name)) {
          this.mVersion = xmlResourceParser.getAttributeIntValue(null, "name", 2);
        } else if ("category_filter".equals(name)) {
          String attributeValue = xmlResourceParser.getAttributeValue(null, "product");
          if (attributeValue != null
              && ((this.mCategoryFilterStr == null && attributeValue.equals("default"))
                  || (str != null && str.contains(attributeValue)))) {
            this.mCategoryFilterStr = xmlResourceParser.getAttributeValue(null, "value");
          }
        } else if ("preferred_width".equals(name)) {
          this.mPreferredWidth = xmlResourceParser.getAttributeIntValue(null, "value", 160);
        } else if ("package_hide_edge_service".equals(name)) {
          String attributeValue2 = xmlResourceParser.getAttributeValue(null, "value");
          if (attributeValue2 != null && attributeValue2.length() > 0) {
            this.mPackageHideEdgeServiceList.add(attributeValue2);
          }
        } else if ("meta_data_hide_edge_service".equals(name)) {
          this.mMetaDataHideEdgeService = xmlResourceParser.getAttributeValue(null, "value");
        } else if ("replaced_component".equals(name)) {
          addReplcaedComponent(xmlResourceParser.getAttributeValue(null, "value"));
        }
      }
      eventType = xmlResourceParser.next();
    }
  }

  public String getConvertedComponent(String str) {
    return (String) this.mReplacedComponent.get(str);
  }

  public final void addReplcaedComponent(String str) {
    if (TextUtils.isEmpty(str)) {
      Slog.e(TAG, "addReplcaedComponent: value is empty. -" + str);
      return;
    }
    String[] split = str.split(",");
    if (split.length < 2 || TextUtils.isEmpty(split[0]) || TextUtils.isEmpty(split[1])) {
      Slog.e(TAG, "addReplcaedComponent: value is wrong. - " + str);
      return;
    }
    this.mReplacedComponent.put(split[0], split[1]);
  }

  public String dump() {
    StringBuffer stringBuffer = new StringBuffer("[CocktailBarConfig] ");
    stringBuffer.append(" version=");
    stringBuffer.append(this.mVersion);
    stringBuffer.append(" categoryStr=");
    stringBuffer.append(this.mCategoryFilterStr);
    stringBuffer.append(" width=");
    stringBuffer.append(this.mPreferredWidth);
    HashSet hashSet = this.mPackageHideEdgeServiceList;
    if (hashSet != null && !hashSet.isEmpty()) {
      stringBuffer.append(" hideAppList=");
      stringBuffer.append(this.mPackageHideEdgeServiceList.toString());
    }
    stringBuffer.append("replaced cn size=");
    stringBuffer.append(this.mReplacedComponent.size());
    stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    return stringBuffer.toString();
  }
}
