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

/* loaded from: classes6.dex */
public class CocktailProviderInfo implements Parcelable {
    private static final String COCKTAIL_AUTO_SCALE = "autoScale";
    private static final String COCKTAIL_CATEGORY = "category";
    public static final int COCKTAIL_CATEGORY_CONTEXTUAL = 65536;

    @Deprecated
    public static final int COCKTAIL_CATEGORY_EXPRESS_ME = 64;
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
    public static final Parcelable.Creator<CocktailProviderInfo> CREATOR = new Parcelable.Creator<CocktailProviderInfo>() { // from class: com.samsung.android.cocktailbar.CocktailProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CocktailProviderInfo createFromParcel(Parcel parcel) {
            return new CocktailProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CocktailProviderInfo[] newArray(int i) {
            return new CocktailProviderInfo[i];
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CocktailProviderInfo() {
        this.permitVisibilityChanged = false;
    }

    public static CocktailProviderInfo create(Context context, ResolveInfo resolveInfo, ComponentName componentName, XmlResourceParser xmlResourceParser, int i, int i2) {
        PackageManager packageManager = context.getPackageManager();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Resources resourcesForApplicationAsUser = packageManager.getResourcesForApplicationAsUser(componentName.getPackageName(), UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid));
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                try {
                    CocktailProviderInfo cocktailProviderInfo = new CocktailProviderInfo(context, packageManager, resourcesForApplicationAsUser, componentName, xmlResourceParser, resolveInfo, i2);
                    if (enforceValidCategory(i, cocktailProviderInfo)) {
                        if (cocktailProviderInfo.category != -1) {
                            return cocktailProviderInfo;
                        }
                    }
                    return null;
                } catch (Resources.NotFoundException unused) {
                    Log.e(TAG, "XML resources failed");
                    return null;
                } catch (IllegalArgumentException unused2) {
                    Log.e(TAG, "IllegalArgumentException");
                    return null;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "failed to load find package", e);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return null;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    private CocktailProviderInfo(Context context, PackageManager packageManager, Resources resources, ComponentName componentName, XmlResourceParser xmlResourceParser, ResolveInfo resolveInfo, int i) throws Resources.NotFoundException, IllegalArgumentException {
        this.permitVisibilityChanged = false;
        this.provider = componentName;
        this.icon = xmlResourceParser.getAttributeResourceValue(null, "icon", 0);
        this.label = xmlResourceParser.getAttributeResourceValue(null, "label", 0);
        this.description = xmlResourceParser.getAttributeResourceValue(null, "description", 0);
        String strLoadXmlString = loadXmlString(xmlResourceParser, resources, COCKTAIL_CATEGORY, "normal");
        if (TextUtils.isEmpty(strLoadXmlString)) {
            this.category = 1;
        } else {
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter('|');
            simpleStringSplitter.setString(strLoadXmlString);
            while (simpleStringSplitter.hasNext()) {
                String strTrim = simpleStringSplitter.next().trim();
                int categoryId = getCategoryId(strTrim);
                if (categoryId != -1) {
                    if (categoryId != 4) {
                        if (categoryId != 8 && categoryId != 16) {
                            if (categoryId != 32 && categoryId != 128) {
                                if (categoryId != 256) {
                                    this.category |= categoryId;
                                }
                            }
                        }
                        this.category = categoryId;
                    }
                    this.category = categoryId;
                    break;
                }
                Log.e(TAG, "Provider: " + componentName + " specified an invalid catetory of " + strTrim);
                this.category = -1;
                return;
            }
        }
        if (i > 1) {
            this.cocktailWidth = loadXmlDimension(xmlResourceParser, resources, COCKTAIL_COCKTAIL_WIDTH, 160);
            this.launchOnClick = loadXmlString(xmlResourceParser, resources, COCKTAIL_LAUNCH_ON_CLICK, null);
            this.autoScale = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_AUTO_SCALE, true);
            this.logoResourceId = xmlResourceParser.getAttributeResourceValue(null, COCKTAIL_LOGO_ID, 0);
            this.isDateTimeEnabled = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_DATETIME_ENABLED, false);
            this.labelHide = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_LABEL_HIDE, false);
            this.landLayout = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_LAND_LAYOUT, false);
        } else {
            this.cocktailWidth = 160;
        }
        this.privateMode = loadXmlString(xmlResourceParser, resources, COCKTAIL_PRIVATE_MODE, null);
        this.previewImage = xmlResourceParser.getAttributeResourceValue(null, COCKTAIL_PREVIEW_IMAGE, 0);
        this.updatePeriodMillis = loadXmlInt(xmlResourceParser, resources, COCKTAIL_UPDATE_TIME, 0);
        this.permitVisibilityChanged = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_PERMIT_VISIBILITY_CHANGED, false);
        this.pullToRefresh = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_PULL_TO_REFRESH, false);
        String strLoadXmlString2 = loadXmlString(xmlResourceParser, resources, COCKTAIL_CONFIGURE, null);
        if (strLoadXmlString2 != null) {
            this.configure = new ComponentName(componentName.getPackageName(), strLoadXmlString2);
        }
        this.cscPreviewImage = loadXmlBoolean(xmlResourceParser, resources, COCKTAIL_CSC_PREVIEW_IMAGE, false);
        if (this.category == 512) {
            this.whisper = loadXmlString(xmlResourceParser, resources, "whisper", null);
        }
    }

    private static boolean enforceValidCategory(int i, CocktailProviderInfo cocktailProviderInfo) {
        if (i != 0) {
            return cocktailProviderInfo.privateMode == null && (i & cocktailProviderInfo.category) != 0;
        }
        Log.i(TAG, "enforceValidCategory: there is no category filters");
        return true;
    }

    public static int getCategoryIds(ArrayList<String> arrayList) {
        int categoryId = 0;
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                categoryId |= getCategoryId(it.next());
            }
        }
        return categoryId;
    }

    private static int getCategoryId(String str) {
        if ("normal".equals(str)) {
            return 1;
        }
        if ("contextual".equals(str)) {
            return 65536;
        }
        if (XMLVAL_HOME_SCREEN.equals(str)) {
            return 8;
        }
        if (XMLVAL_FEEDS.equals(str)) {
            return 256;
        }
        if ("whisper".equals(str)) {
            return 512;
        }
        if (XMLVAL_QUICK_TOOL.equals(str)) {
            return 4;
        }
        if (XMLVAL_TABLE_MODE.equals(str)) {
            return 32;
        }
        if (XMLVAL_NIGHT_MODE.equals(str)) {
            return 128;
        }
        return XMLVAL_LOCK_SCREEN.equals(str) ? 16 : -1;
    }

    private CocktailProviderInfo(Parcel parcel) {
        this.permitVisibilityChanged = false;
        this.provider = parcel.readInt() != 0 ? new ComponentName(parcel) : null;
        this.updatePeriodMillis = parcel.readInt();
        this.label = parcel.readInt();
        this.description = parcel.readInt();
        this.icon = parcel.readInt();
        this.previewImage = parcel.readInt();
        this.category = parcel.readInt();
        this.cocktailWidth = parcel.readInt();
        this.privateMode = parcel.readInt() != 0 ? parcel.readString() : null;
        this.permitVisibilityChanged = parcel.readByte() == 1;
        this.pullToRefresh = parcel.readByte() == 1;
        this.configure = parcel.readInt() != 0 ? new ComponentName(parcel) : null;
        this.launchOnClick = parcel.readInt() != 0 ? parcel.readString() : null;
        this.cscPreviewImage = parcel.readByte() == 1;
        this.autoScale = parcel.readByte() == 1;
        this.logoResourceId = parcel.readInt();
        this.isDateTimeEnabled = parcel.readByte() == 1;
        this.labelHide = parcel.readByte() == 1;
        this.landLayout = parcel.readByte() == 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.provider != null) {
            parcel.writeInt(1);
            this.provider.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.updatePeriodMillis);
        parcel.writeInt(this.label);
        parcel.writeInt(this.description);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.previewImage);
        parcel.writeInt(this.category);
        parcel.writeInt(this.cocktailWidth);
        if (this.privateMode != null) {
            parcel.writeInt(1);
            parcel.writeString(this.privateMode);
        } else {
            parcel.writeInt(0);
        }
        if (this.permitVisibilityChanged) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.pullToRefresh) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.configure != null) {
            parcel.writeInt(1);
            this.configure.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.launchOnClick != null) {
            parcel.writeInt(1);
            parcel.writeString(this.launchOnClick);
        } else {
            parcel.writeInt(0);
        }
        if (this.cscPreviewImage) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.autoScale) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.logoResourceId);
        if (this.isDateTimeEnabled) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.labelHide) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.landLayout) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
    }

    private int loadXmlInt(XmlResourceParser xmlResourceParser, Resources resources, String str, int i) {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, str, 0);
        if (attributeResourceValue != 0) {
            try {
                return resources.getInteger(attributeResourceValue);
            } catch (Resources.NotFoundException unused) {
                return i;
            }
        }
        return xmlResourceParser.getAttributeIntValue(null, str, i);
    }

    private String loadXmlString(XmlResourceParser xmlResourceParser, Resources resources, String str, String str2) {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, str, 0);
        if (attributeResourceValue != 0) {
            try {
                return resources.getString(attributeResourceValue);
            } catch (Resources.NotFoundException unused) {
            }
        } else {
            String attributeValue = xmlResourceParser.getAttributeValue(null, str);
            if (attributeValue != null) {
                return attributeValue;
            }
        }
        return str2;
    }

    private boolean loadXmlBoolean(XmlResourceParser xmlResourceParser, Resources resources, String str, boolean z) {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, str, 0);
        if (attributeResourceValue != 0) {
            try {
                return resources.getBoolean(attributeResourceValue);
            } catch (Resources.NotFoundException unused) {
                return z;
            }
        }
        return xmlResourceParser.getAttributeBooleanValue(null, str, z);
    }

    private int loadXmlDimension(XmlResourceParser xmlResourceParser, Resources resources, String str, int i) {
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, str, 0);
        if (attributeResourceValue != 0) {
            try {
                return resources.getDimensionPixelSize(attributeResourceValue);
            } catch (Resources.NotFoundException unused) {
                return i;
            }
        }
        return xmlResourceParser.getAttributeIntValue(null, str, i);
    }
}
