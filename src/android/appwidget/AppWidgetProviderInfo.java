package android.appwidget;

import android.appwidget.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class AppWidgetProviderInfo implements Parcelable {
    public static final Parcelable.Creator<AppWidgetProviderInfo> CREATOR = new Parcelable.Creator<AppWidgetProviderInfo>() { // from class: android.appwidget.AppWidgetProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppWidgetProviderInfo createFromParcel(Parcel parcel) {
            return new AppWidgetProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppWidgetProviderInfo[] newArray(int i) {
            return new AppWidgetProviderInfo[i];
        }
    };
    public static final int RESIZE_BOTH = 3;
    public static final int RESIZE_HORIZONTAL = 1;
    public static final int RESIZE_NONE = 0;
    public static final int RESIZE_VERTICAL = 2;
    public static final int SEM_WIDGET_CATEGORY_COVER_SCREEN = 2048;
    public static final int SEM_WIDGET_CATEGORY_EASY_HOME_SCREEN = 256;
    public static final int SEM_WIDGET_CATEGORY_HIDDEN_FROM_3P = 8192;
    public static final int SEM_WIDGET_CATEGORY_SAMSUNG_HOME_SCREEN = 512;
    public static final int SEM_WIDGET_CATEGORY_SMART_WIDGET = 4096;
    public static final int SEM_WIDGET_CATEGORY_SUB_DISPLAY_HOME_SCREEN = 1024;
    public static final int WIDGET_CATEGORY_HOME_SCREEN = 1;
    public static final int WIDGET_CATEGORY_KEYGUARD = 2;
    public static final int WIDGET_CATEGORY_NOT_KEYGUARD = 8;
    public static final int WIDGET_CATEGORY_SEARCHBOX = 4;
    public static final int WIDGET_CATEGORY_UNKNOWN = -1;
    public static final int WIDGET_FEATURE_CONFIGURATION_OPTIONAL = 4;
    public static final int WIDGET_FEATURE_HIDE_FROM_PICKER = 2;
    public static final int WIDGET_FEATURE_RECONFIGURABLE = 1;
    public int autoAdvanceViewId;
    public ComponentName configure;
    public int descriptionRes;
    public int generatedPreviewCategories;
    public int hidden_semAppWidgeAdditionOptions;
    public int hidden_semGeneratedColorfulPreviewStates;
    public int hidden_semGeneratedMonotonePreviewStates;
    public int hidden_semPreviewRecordResetStates;
    public int icon;
    public int initialKeyguardLayout;
    public int initialLayout;
    public boolean isExtendedFromAppWidgetProvider;

    @Deprecated
    public String label;
    public int maxResizeHeight;
    public int maxResizeWidth;
    public int minHeight;
    public int minResizeHeight;
    public int minResizeWidth;
    public int minWidth;
    public int previewImage;
    public int previewLayout;
    public ComponentName provider;
    public ActivityInfo providerInfo;
    public int resizeMode;
    public ComponentName semConfigure;
    public int targetCellHeight;
    public int targetCellWidth;
    public int updatePeriodMillis;
    public int widgetCategory;
    public int widgetFeatures;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CategoryFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FeatureFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResizeModeFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppWidgetProviderInfo() {
    }

    public AppWidgetProviderInfo(Parcel parcel) {
        this.provider = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.minWidth = parcel.readInt();
        this.minHeight = parcel.readInt();
        this.minResizeWidth = parcel.readInt();
        this.minResizeHeight = parcel.readInt();
        this.maxResizeWidth = parcel.readInt();
        this.maxResizeHeight = parcel.readInt();
        this.targetCellWidth = parcel.readInt();
        this.targetCellHeight = parcel.readInt();
        this.updatePeriodMillis = parcel.readInt();
        this.initialLayout = parcel.readInt();
        this.initialKeyguardLayout = parcel.readInt();
        this.configure = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.semConfigure = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.label = parcel.readString();
        this.icon = parcel.readInt();
        this.previewImage = parcel.readInt();
        this.previewLayout = parcel.readInt();
        this.autoAdvanceViewId = parcel.readInt();
        this.resizeMode = parcel.readInt();
        this.widgetCategory = parcel.readInt();
        this.providerInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
        this.widgetFeatures = parcel.readInt();
        this.descriptionRes = parcel.readInt();
        this.isExtendedFromAppWidgetProvider = parcel.readBoolean();
        if (Flags.generatedPreviews()) {
            this.generatedPreviewCategories = parcel.readInt();
        }
        this.hidden_semGeneratedColorfulPreviewStates = parcel.readInt();
        this.hidden_semGeneratedMonotonePreviewStates = parcel.readInt();
        this.hidden_semPreviewRecordResetStates = parcel.readInt();
        this.hidden_semAppWidgeAdditionOptions = parcel.readInt();
    }

    public final String loadLabel(PackageManager packageManager) {
        CharSequence loadLabel = this.providerInfo.loadLabel(packageManager);
        if (loadLabel != null) {
            return loadLabel.toString().trim();
        }
        return null;
    }

    public final Drawable loadIcon(Context context, int i) {
        return loadDrawable(context, i, this.providerInfo.getIconResource(), true);
    }

    public final Drawable loadPreviewImage(Context context, int i) {
        return loadDrawable(context, i, this.previewImage, false);
    }

    public final CharSequence loadDescription(Context context) {
        CharSequence text;
        if (!ResourceId.isValid(this.descriptionRes) || (text = context.getPackageManager().getText(this.providerInfo.packageName, this.descriptionRes, this.providerInfo.applicationInfo)) == null) {
            return null;
        }
        return text.toString().trim();
    }

    public final UserHandle getProfile() {
        return new UserHandle(UserHandle.getUserId(this.providerInfo.applicationInfo.uid));
    }

    public ActivityInfo getActivityInfo() {
        return this.providerInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.provider, i);
        parcel.writeInt(this.minWidth);
        parcel.writeInt(this.minHeight);
        parcel.writeInt(this.minResizeWidth);
        parcel.writeInt(this.minResizeHeight);
        parcel.writeInt(this.maxResizeWidth);
        parcel.writeInt(this.maxResizeHeight);
        parcel.writeInt(this.targetCellWidth);
        parcel.writeInt(this.targetCellHeight);
        parcel.writeInt(this.updatePeriodMillis);
        parcel.writeInt(this.initialLayout);
        parcel.writeInt(this.initialKeyguardLayout);
        parcel.writeTypedObject(this.configure, i);
        parcel.writeTypedObject(this.semConfigure, i);
        parcel.writeString(this.label);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.previewImage);
        parcel.writeInt(this.previewLayout);
        parcel.writeInt(this.autoAdvanceViewId);
        parcel.writeInt(this.resizeMode);
        parcel.writeInt(this.widgetCategory);
        parcel.writeTypedObject(this.providerInfo, i);
        parcel.writeInt(this.widgetFeatures);
        parcel.writeInt(this.descriptionRes);
        parcel.writeBoolean(this.isExtendedFromAppWidgetProvider);
        if (Flags.generatedPreviews()) {
            parcel.writeInt(this.generatedPreviewCategories);
        }
        parcel.writeInt(this.hidden_semGeneratedColorfulPreviewStates);
        parcel.writeInt(this.hidden_semGeneratedMonotonePreviewStates);
        parcel.writeInt(this.hidden_semPreviewRecordResetStates);
        parcel.writeInt(this.hidden_semAppWidgeAdditionOptions);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AppWidgetProviderInfo m865clone() {
        AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
        ComponentName componentName = this.provider;
        appWidgetProviderInfo.provider = componentName == null ? null : componentName.m926clone();
        appWidgetProviderInfo.minWidth = this.minWidth;
        appWidgetProviderInfo.minHeight = this.minHeight;
        appWidgetProviderInfo.minResizeWidth = this.minResizeWidth;
        appWidgetProviderInfo.minResizeHeight = this.minResizeHeight;
        appWidgetProviderInfo.maxResizeWidth = this.maxResizeWidth;
        appWidgetProviderInfo.maxResizeHeight = this.maxResizeHeight;
        appWidgetProviderInfo.targetCellWidth = this.targetCellWidth;
        appWidgetProviderInfo.targetCellHeight = this.targetCellHeight;
        appWidgetProviderInfo.updatePeriodMillis = this.updatePeriodMillis;
        appWidgetProviderInfo.initialLayout = this.initialLayout;
        appWidgetProviderInfo.initialKeyguardLayout = this.initialKeyguardLayout;
        ComponentName componentName2 = this.configure;
        appWidgetProviderInfo.configure = componentName2 == null ? null : componentName2.m926clone();
        ComponentName componentName3 = this.semConfigure;
        appWidgetProviderInfo.semConfigure = componentName3 != null ? componentName3.m926clone() : null;
        appWidgetProviderInfo.label = this.label;
        appWidgetProviderInfo.icon = this.icon;
        appWidgetProviderInfo.previewImage = this.previewImage;
        appWidgetProviderInfo.previewLayout = this.previewLayout;
        appWidgetProviderInfo.autoAdvanceViewId = this.autoAdvanceViewId;
        appWidgetProviderInfo.resizeMode = this.resizeMode;
        appWidgetProviderInfo.widgetCategory = this.widgetCategory;
        appWidgetProviderInfo.providerInfo = this.providerInfo;
        appWidgetProviderInfo.widgetFeatures = this.widgetFeatures;
        appWidgetProviderInfo.descriptionRes = this.descriptionRes;
        appWidgetProviderInfo.isExtendedFromAppWidgetProvider = this.isExtendedFromAppWidgetProvider;
        if (Flags.generatedPreviews()) {
            appWidgetProviderInfo.generatedPreviewCategories = this.generatedPreviewCategories;
        }
        appWidgetProviderInfo.hidden_semGeneratedColorfulPreviewStates = this.hidden_semGeneratedColorfulPreviewStates;
        appWidgetProviderInfo.hidden_semGeneratedMonotonePreviewStates = this.hidden_semGeneratedMonotonePreviewStates;
        appWidgetProviderInfo.hidden_semPreviewRecordResetStates = this.hidden_semPreviewRecordResetStates;
        appWidgetProviderInfo.hidden_semAppWidgeAdditionOptions = this.hidden_semAppWidgeAdditionOptions;
        return appWidgetProviderInfo;
    }

    private Drawable loadDrawable(Context context, int i, int i2, boolean z) {
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(this.providerInfo.applicationInfo);
            if (ResourceId.isValid(i2)) {
                if (i < 0) {
                    i = 0;
                }
                return resourcesForApplication.getDrawableForDensity(i2, i, null);
            }
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
        }
        if (z) {
            return this.providerInfo.loadIcon(context.getPackageManager());
        }
        return null;
    }

    public void updateDimensions(DisplayMetrics displayMetrics) {
        this.minWidth = TypedValue.complexToDimensionPixelSize(this.minWidth, displayMetrics);
        this.minHeight = TypedValue.complexToDimensionPixelSize(this.minHeight, displayMetrics);
        this.minResizeWidth = TypedValue.complexToDimensionPixelSize(this.minResizeWidth, displayMetrics);
        this.minResizeHeight = TypedValue.complexToDimensionPixelSize(this.minResizeHeight, displayMetrics);
        this.maxResizeWidth = TypedValue.complexToDimensionPixelSize(this.maxResizeWidth, displayMetrics);
        this.maxResizeHeight = TypedValue.complexToDimensionPixelSize(this.maxResizeHeight, displayMetrics);
    }

    public String toString() {
        return "AppWidgetProviderInfo(" + getProfile() + '/' + this.provider + ')';
    }
}
