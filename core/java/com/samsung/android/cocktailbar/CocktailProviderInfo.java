package com.samsung.android.cocktailbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class CocktailProviderInfo implements Parcelable {
    private static final String COCKTAIL_AUTO_SCALE = "autoScale";
    private static final String COCKTAIL_CATEGORY = "category";
    public static final int COCKTAIL_CATEGORY_CONTEXTUAL = 65536;

    @Deprecated public static final int COCKTAIL_CATEGORY_EXPRESS_ME = 64;
    public static final int COCKTAIL_CATEGORY_FEEDS = 256;
    public static final int COCKTAIL_CATEGORY_HOME_SCREEN = 8;
    public static final int COCKTAIL_CATEGORY_INVALID = -1;
    public static final int COCKTAIL_CATEGORY_LOCK_SCREEN = 16;
    public static final int COCKTAIL_CATEGORY_NIGHT_MODE = 128;
    public static final int COCKTAIL_CATEGORY_NORMAL = 1;
    public static final int COCKTAIL_CATEGORY_QUICK_TOOL = 4;
    public static final int COCKTAIL_CATEGORY_TABLE_MODE = 32;
    public static final int COCKTAIL_CATEGORY_WHISPER = 512;
    private static final String COCKTAIL_COCKTAIL_WIDTH = "cocktailWidth";
    private static final String COCKTAIL_CONFIGURE = "configure";
    private static final String COCKTAIL_CSC_PREVIEW_IMAGE = "cscPreviewImage";
    private static final String COCKTAIL_DATETIME_ENABLED = "dateTimeEnabled";
    private static final String COCKTAIL_DESCRIPTION = "description";
    private static final String COCKTAIL_ICON = "icon";
    private static final String COCKTAIL_LABEL = "label";
    private static final String COCKTAIL_LABEL_HIDE = "labelhide";
    private static final String COCKTAIL_LAND_LAYOUT = "landlayout";
    private static final String COCKTAIL_LAUNCH_ON_CLICK = "launchOnClick";
    private static final String COCKTAIL_LOGO_ID = "logoResourceId";
    private static final String COCKTAIL_PERMIT_VISIBILITY_CHANGED = "permitVisibilityChanged";
    private static final String COCKTAIL_PREVIEW_IMAGE = "previewImage";
    private static final String COCKTAIL_PRIVATE_MODE = "privateMode";
    private static final String COCKTAIL_PULL_TO_REFRESH = "pullToRefresh";
    private static final String COCKTAIL_UPDATE_TIME = "updatePeriodMillis";
    private static final String COCKTAIL_WHISPER = "whisper";
    public static final Parcelable.Creator<CocktailProviderInfo> CREATOR =
            new Parcelable.Creator<
                    CocktailProviderInfo>() { // from class:
                                              // com.samsung.android.cocktailbar.CocktailProviderInfo.1
                @Override // android.os.Parcelable.Creator
                public CocktailProviderInfo createFromParcel(Parcel in) {
                    return new CocktailProviderInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public CocktailProviderInfo[] newArray(int size) {
                    return new CocktailProviderInfo[size];
                }
            };
    private static final String TAG = "CocktailProviderInfo";
    private static final int VAL_DEFAULT_COCKTAIL_WIDTH = 160;
    private static final String XMLVAL_CONTEXTUAL = "contextual";
    private static final String XMLVAL_FEEDS = "feeds";
    private static final String XMLVAL_HOME_SCREEN = "homescreen";
    private static final String XMLVAL_LOCK_SCREEN = "lockscreen";
    private static final String XMLVAL_NIGHT_MODE = "nightmode";
    private static final String XMLVAL_NORMAL = "normal";
    private static final String XMLVAL_QUICK_TOOL = "quicktool";
    private static final String XMLVAL_TABLE_MODE = "tablemode";
    private static final String XMLVAL_WHISPER = "whisper";
    public boolean autoScale;
    public int category;
    public int cocktailWidth;
    public ComponentName configure;
    public boolean cscPreviewImage;
    public int description;
    public int icon;
    public boolean isDateTimeEnabled;
    public int label;
    public boolean labelHide;
    public boolean landLayout;
    public String launchOnClick;
    public int logoResourceId;
    public boolean permitVisibilityChanged;
    public int previewImage;
    public String privateMode;
    public ComponentName provider;
    public boolean pullToRefresh;
    public int updatePeriodMillis;
    public String whisper;

    public CocktailProviderInfo() {
        this.permitVisibilityChanged = false;
    }

    public static CocktailProviderInfo create(
            Context context,
            ResolveInfo ri,
            ComponentName cn,
            XmlResourceParser xml,
            int configuredCategoryFilter,
            int version) {
        CocktailProviderInfo pInfo;
        PackageManager pkgMgr = context.getPackageManager();
        long identity = Binder.clearCallingIdentity();
        try {
            try {
                Resources resources =
                        pkgMgr.getResourcesForApplicationAsUser(
                                cn.getPackageName(),
                                UserHandle.getUserId(ri.activityInfo.applicationInfo.uid));
                Binder.restoreCallingIdentity(identity);
                try {
                    pInfo =
                            new CocktailProviderInfo(
                                    context, pkgMgr, resources, cn, xml, ri, version);
                } catch (Resources.NotFoundException e) {
                } catch (IllegalArgumentException e2) {
                }
                try {
                    if (enforceValidCategory(configuredCategoryFilter, pInfo)) {
                        if (pInfo.category != -1) {
                            return pInfo;
                        }
                    }
                    return null;
                } catch (Resources.NotFoundException e3) {
                    Log.e(TAG, "XML resources failed");
                    return null;
                } catch (IllegalArgumentException e4) {
                    Log.e(TAG, "IllegalArgumentException");
                    return null;
                }
            } catch (PackageManager.NameNotFoundException e5) {
                e = e5;
                try {
                    Log.e(TAG, "failed to load find package", e);
                    Binder.restoreCallingIdentity(identity);
                    return null;
                } catch (Throwable th) {
                    e = th;
                    Binder.restoreCallingIdentity(identity);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                Binder.restoreCallingIdentity(identity);
                throw e;
            }
        } catch (PackageManager.NameNotFoundException e6) {
            e = e6;
        } catch (Throwable th3) {
            e = th3;
        }
    }

    private CocktailProviderInfo(
            Context context,
            PackageManager pkgMgr,
            Resources res,
            ComponentName provider,
            XmlResourceParser xml,
            ResolveInfo info,
            int version)
            throws Resources.NotFoundException, IllegalArgumentException {
        this.permitVisibilityChanged = false;
        this.provider = provider;
        this.icon = xml.getAttributeResourceValue(null, "icon", 0);
        this.label = xml.getAttributeResourceValue(null, "label", 0);
        this.description = xml.getAttributeResourceValue(null, "description", 0);
        String category = loadXmlString(xml, res, COCKTAIL_CATEGORY, "normal");
        if (TextUtils.isEmpty(category)) {
            this.category = 1;
        } else {
            TextUtils.SimpleStringSplitter categorySplitter =
                    new TextUtils.SimpleStringSplitter('|');
            categorySplitter.setString(category);
            while (categorySplitter.hasNext()) {
                String c = categorySplitter.next().trim();
                int categoryId = getCategoryId(c);
                boolean isBreak = false;
                switch (categoryId) {
                    case -1:
                        Log.e(
                                TAG,
                                "Provider: " + provider + " specified an invalid catetory of " + c);
                        this.category = -1;
                        return;
                    case 4:
                    case 32:
                    case 128:
                        this.category = categoryId;
                        isBreak = true;
                        break;
                    case 8:
                    case 16:
                    case 256:
                        this.category = categoryId;
                        break;
                    default:
                        this.category |= categoryId;
                        break;
                }
                if (isBreak) {}
            }
        }
        if (version > 1) {
            this.cocktailWidth = loadXmlDimension(xml, res, COCKTAIL_COCKTAIL_WIDTH, 160);
            this.launchOnClick = loadXmlString(xml, res, COCKTAIL_LAUNCH_ON_CLICK, null);
            this.autoScale = loadXmlBoolean(xml, res, COCKTAIL_AUTO_SCALE, true);
            this.logoResourceId = xml.getAttributeResourceValue(null, COCKTAIL_LOGO_ID, 0);
            this.isDateTimeEnabled = loadXmlBoolean(xml, res, COCKTAIL_DATETIME_ENABLED, false);
            this.labelHide = loadXmlBoolean(xml, res, COCKTAIL_LABEL_HIDE, false);
            this.landLayout = loadXmlBoolean(xml, res, COCKTAIL_LAND_LAYOUT, false);
        } else {
            this.cocktailWidth = 160;
        }
        this.privateMode = loadXmlString(xml, res, COCKTAIL_PRIVATE_MODE, null);
        this.previewImage = xml.getAttributeResourceValue(null, COCKTAIL_PREVIEW_IMAGE, 0);
        this.updatePeriodMillis = loadXmlInt(xml, res, COCKTAIL_UPDATE_TIME, 0);
        this.permitVisibilityChanged =
                loadXmlBoolean(xml, res, COCKTAIL_PERMIT_VISIBILITY_CHANGED, false);
        this.pullToRefresh = loadXmlBoolean(xml, res, COCKTAIL_PULL_TO_REFRESH, false);
        String configureClassName = loadXmlString(xml, res, COCKTAIL_CONFIGURE, null);
        if (configureClassName != null) {
            this.configure = new ComponentName(provider.getPackageName(), configureClassName);
        }
        this.cscPreviewImage = loadXmlBoolean(xml, res, COCKTAIL_CSC_PREVIEW_IMAGE, false);
        if (this.category == 512) {
            this.whisper = loadXmlString(xml, res, "whisper", null);
        }
    }

    private static boolean enforceValidCategory(
            int configuredCategoryFilter, CocktailProviderInfo pInfo) {
        if (configuredCategoryFilter != 0) {
            return pInfo.privateMode == null && (pInfo.category & configuredCategoryFilter) != 0;
        }
        Log.i(TAG, "enforceValidCategory: there is no category filters");
        return true;
    }

    public static int getCategoryIds(ArrayList<String> list) {
        int ids = 0;
        if (list == null || list.size() == 0) {
            return 0;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String category = it.next();
            ids |= getCategoryId(category);
        }
        return ids;
    }

    private static int getCategoryId(String category) {
        if ("normal".equals(category)) {
            return 1;
        }
        if ("contextual".equals(category)) {
            return 65536;
        }
        if (XMLVAL_HOME_SCREEN.equals(category)) {
            return 8;
        }
        if (XMLVAL_FEEDS.equals(category)) {
            return 256;
        }
        if ("whisper".equals(category)) {
            return 512;
        }
        if (XMLVAL_QUICK_TOOL.equals(category)) {
            return 4;
        }
        if (XMLVAL_TABLE_MODE.equals(category)) {
            return 32;
        }
        if (XMLVAL_NIGHT_MODE.equals(category)) {
            return 128;
        }
        if (XMLVAL_LOCK_SCREEN.equals(category)) {
            return 16;
        }
        return -1;
    }

    private CocktailProviderInfo(Parcel in) {
        this.permitVisibilityChanged = false;
        this.provider = in.readInt() != 0 ? new ComponentName(in) : null;
        this.updatePeriodMillis = in.readInt();
        this.label = in.readInt();
        this.description = in.readInt();
        this.icon = in.readInt();
        this.previewImage = in.readInt();
        this.category = in.readInt();
        this.cocktailWidth = in.readInt();
        this.privateMode = in.readInt() != 0 ? in.readString() : null;
        this.permitVisibilityChanged = in.readByte() == 1;
        this.pullToRefresh = in.readByte() == 1;
        this.configure = in.readInt() != 0 ? new ComponentName(in) : null;
        this.launchOnClick = in.readInt() != 0 ? in.readString() : null;
        this.cscPreviewImage = in.readByte() == 1;
        this.autoScale = in.readByte() == 1;
        this.logoResourceId = in.readInt();
        this.isDateTimeEnabled = in.readByte() == 1;
        this.labelHide = in.readByte() == 1;
        this.landLayout = in.readByte() == 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (this.provider != null) {
            out.writeInt(1);
            this.provider.writeToParcel(out, flags);
        } else {
            out.writeInt(0);
        }
        out.writeInt(this.updatePeriodMillis);
        out.writeInt(this.label);
        out.writeInt(this.description);
        out.writeInt(this.icon);
        out.writeInt(this.previewImage);
        out.writeInt(this.category);
        out.writeInt(this.cocktailWidth);
        if (this.privateMode != null) {
            out.writeInt(1);
            out.writeString(this.privateMode);
        } else {
            out.writeInt(0);
        }
        if (this.permitVisibilityChanged) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
        if (this.pullToRefresh) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
        if (this.configure != null) {
            out.writeInt(1);
            this.configure.writeToParcel(out, flags);
        } else {
            out.writeInt(0);
        }
        if (this.launchOnClick != null) {
            out.writeInt(1);
            out.writeString(this.launchOnClick);
        } else {
            out.writeInt(0);
        }
        if (this.cscPreviewImage) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
        if (this.autoScale) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
        out.writeInt(this.logoResourceId);
        if (this.isDateTimeEnabled) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
        if (this.labelHide) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
        if (this.landLayout) {
            out.writeByte((byte) 1);
        } else {
            out.writeByte((byte) 0);
        }
    }

    private int loadXmlInt(
            XmlResourceParser parser, Resources pkgRes, String attrName, int defaultValue) {
        int refId = parser.getAttributeResourceValue(null, attrName, 0);
        if (refId != 0) {
            try {
                int value = pkgRes.getInteger(refId);
                return value;
            } catch (Resources.NotFoundException e) {
                return defaultValue;
            }
        }
        int value2 = parser.getAttributeIntValue(null, attrName, defaultValue);
        return value2;
    }

    private String loadXmlString(
            XmlResourceParser parser, Resources pkgRes, String attrName, String defaultValue) {
        int refId = parser.getAttributeResourceValue(null, attrName, 0);
        if (refId != 0) {
            try {
                return pkgRes.getString(refId);
            } catch (Resources.NotFoundException e) {
                return defaultValue;
            }
        }
        String value = parser.getAttributeValue(null, attrName);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    private boolean loadXmlBoolean(
            XmlResourceParser parser, Resources pkgRes, String attrName, boolean defaultValue) {
        int refId = parser.getAttributeResourceValue(null, attrName, 0);
        if (refId != 0) {
            try {
                boolean value = pkgRes.getBoolean(refId);
                return value;
            } catch (Resources.NotFoundException e) {
                return defaultValue;
            }
        }
        boolean value2 = parser.getAttributeBooleanValue(null, attrName, defaultValue);
        return value2;
    }

    private int loadXmlDimension(
            XmlResourceParser parser, Resources pkgRes, String attrName, int defaultValue) {
        int refId = parser.getAttributeResourceValue(null, attrName, 0);
        if (refId != 0) {
            try {
                int value = pkgRes.getDimensionPixelSize(refId);
                return value;
            } catch (Resources.NotFoundException e) {
                return defaultValue;
            }
        }
        int value2 = parser.getAttributeIntValue(null, attrName, defaultValue);
        return value2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
