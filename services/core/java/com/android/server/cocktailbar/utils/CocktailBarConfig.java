package com.android.server.cocktailbar.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class CocktailBarConfig {
    public static CocktailBarConfig sInstance;
    public ArrayList mCategoryFilter;
    public String mCategoryFilterStr;
    public String mMetaDataHideEdgeService;
    public final PackageManager mPackageManager;
    public int mVersion = 2;
    public int mPreferredWidth = 0;
    public final HashSet mPackageHideEdgeServiceList = new HashSet();
    public boolean mIsServiceFounded = false;
    public final HashMap mReplacedComponent = new HashMap();

    public CocktailBarConfig(Context context) {
        this.mPackageManager = context.getPackageManager();
        parseEdgeConfig();
    }

    public static CocktailBarConfig getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CocktailBarConfig(context);
        }
        return sInstance;
    }

    public final String dump() {
        StringBuffer stringBuffer = new StringBuffer("[CocktailBarConfig]  version=");
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
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public final ArrayList getCategoryFilter() {
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
                Slog.d("CocktailBarConfig", "getCategoryFilter: CategoryFilterStr is null");
            }
        }
        return this.mCategoryFilter;
    }

    public final void parseEdgeConfig() {
        XmlResourceParser xmlResourceParser;
        List<ResolveInfo> queryIntentServicesAsUser =
                this.mPackageManager.queryIntentServicesAsUser(
                        new Intent("com.samsung.app.honeyspace.edge"), 128, 0);
        if (queryIntentServicesAsUser == null || queryIntentServicesAsUser.size() <= 0) {
            this.mIsServiceFounded = false;
            Slog.d("CocktailBarConfig", "getEdgeConfigParser: no enabled cocktailbarservice");
        } else {
            this.mIsServiceFounded = true;
            for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
                if (!resolveInfo.serviceInfo.isEnabled()) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            new StringBuilder("getEdgeConfigParser: not enabled: "),
                            resolveInfo.resolvePackageName,
                            "CocktailBarConfig");
                } else {
                    if (CocktailBarUtils$CocktailBarWhiteList.isSystemApplication(
                            0, resolveInfo.serviceInfo.packageName)) {
                        xmlResourceParser =
                                resolveInfo.serviceInfo.loadXmlMetaData(
                                        this.mPackageManager, "com.samsung.android.edge.config");
                        break;
                    }
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            new StringBuilder("getEdgeConfigParser: not system app: "),
                            resolveInfo.resolvePackageName,
                            "CocktailBarConfig");
                }
            }
        }
        xmlResourceParser = null;
        String str = SystemProperties.get("ro.product.name");
        try {
            if (xmlResourceParser != null) {
                try {
                    try {
                        parseXml(xmlResourceParser, str);
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } finally {
            xmlResourceParser.close();
        }
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
                            && ((this.mCategoryFilterStr == null
                                            && attributeValue.equals("default"))
                                    || (str != null && str.contains(attributeValue)))) {
                        this.mCategoryFilterStr =
                                xmlResourceParser.getAttributeValue(null, "value");
                    }
                } else if ("preferred_width".equals(name)) {
                    this.mPreferredWidth =
                            xmlResourceParser.getAttributeIntValue(null, "value", 160);
                } else if ("package_hide_edge_service".equals(name)) {
                    String attributeValue2 = xmlResourceParser.getAttributeValue(null, "value");
                    if (attributeValue2 != null && attributeValue2.length() > 0) {
                        this.mPackageHideEdgeServiceList.add(attributeValue2);
                    }
                } else if ("meta_data_hide_edge_service".equals(name)) {
                    this.mMetaDataHideEdgeService =
                            xmlResourceParser.getAttributeValue(null, "value");
                } else if ("replaced_component".equals(name)) {
                    String attributeValue3 = xmlResourceParser.getAttributeValue(null, "value");
                    if (TextUtils.isEmpty(attributeValue3)) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                "addReplcaedComponent: value is empty. -",
                                attributeValue3,
                                "CocktailBarConfig");
                    } else {
                        String[] split = attributeValue3.split(",");
                        if (split.length < 2
                                || TextUtils.isEmpty(split[0])
                                || TextUtils.isEmpty(split[1])) {
                            Slog.e(
                                    "CocktailBarConfig",
                                    "addReplcaedComponent: value is wrong. - "
                                            .concat(attributeValue3));
                        } else {
                            this.mReplacedComponent.put(split[0], split[1]);
                        }
                    }
                }
            }
            eventType = xmlResourceParser.next();
        }
    }
}
