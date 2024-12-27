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

public class AppWidgetProviderInfo implements Parcelable {
    public static final Parcelable.Creator<AppWidgetProviderInfo> CREATOR =
            new Parcelable.Creator<AppWidgetProviderInfo>() { // from class:
                // android.appwidget.AppWidgetProviderInfo.1
                @Override // android.os.Parcelable.Creator
                public AppWidgetProviderInfo createFromParcel(Parcel parcel) {
                    return new AppWidgetProviderInfo(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public AppWidgetProviderInfo[] newArray(int size) {
                    return new AppWidgetProviderInfo[size];
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
    public static final int WIDGET_CATEGORY_SEARCHBOX = 4;
    public static final int WIDGET_CATEGORY_UNKNOWN = -1;
    public static final int WIDGET_FEATURE_CONFIGURATION_OPTIONAL = 4;
    public static final int WIDGET_FEATURE_HIDE_FROM_PICKER = 2;
    public static final int WIDGET_FEATURE_RECONFIGURABLE = 1;
    public int autoAdvanceViewId;
    public ComponentName configure;
    public int descriptionRes;
    public int generatedPreviewCategories;
    public int hidden_semGeneratedColorfulPreviewStates;
    public int hidden_semGeneratedMonotonePreviewStates;
    public int hidden_semPreviewRecordResetStates;
    public int icon;
    public int initialKeyguardLayout;
    public int initialLayout;
    public boolean isExtendedFromAppWidgetProvider;

    @Deprecated public String label;
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
    public @interface CategoryFlags {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FeatureFlags {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResizeModeFlags {}

    public AppWidgetProviderInfo() {}

    public AppWidgetProviderInfo(Parcel in) {
        this.provider = (ComponentName) in.readTypedObject(ComponentName.CREATOR);
        this.minWidth = in.readInt();
        this.minHeight = in.readInt();
        this.minResizeWidth = in.readInt();
        this.minResizeHeight = in.readInt();
        this.maxResizeWidth = in.readInt();
        this.maxResizeHeight = in.readInt();
        this.targetCellWidth = in.readInt();
        this.targetCellHeight = in.readInt();
        this.updatePeriodMillis = in.readInt();
        this.initialLayout = in.readInt();
        this.initialKeyguardLayout = in.readInt();
        this.configure = (ComponentName) in.readTypedObject(ComponentName.CREATOR);
        this.semConfigure = (ComponentName) in.readTypedObject(ComponentName.CREATOR);
        this.label = in.readString();
        this.icon = in.readInt();
        this.previewImage = in.readInt();
        this.previewLayout = in.readInt();
        this.autoAdvanceViewId = in.readInt();
        this.resizeMode = in.readInt();
        this.widgetCategory = in.readInt();
        this.providerInfo = (ActivityInfo) in.readTypedObject(ActivityInfo.CREATOR);
        this.widgetFeatures = in.readInt();
        this.descriptionRes = in.readInt();
        this.isExtendedFromAppWidgetProvider = in.readBoolean();
        if (Flags.generatedPreviews()) {
            this.generatedPreviewCategories = in.readInt();
        }
        this.hidden_semGeneratedColorfulPreviewStates = in.readInt();
        this.hidden_semGeneratedMonotonePreviewStates = in.readInt();
        this.hidden_semPreviewRecordResetStates = in.readInt();
    }

    public final String loadLabel(PackageManager packageManager) {
        CharSequence label = this.providerInfo.loadLabel(packageManager);
        if (label != null) {
            return label.toString().trim();
        }
        return null;
    }

    public final Drawable loadIcon(Context context, int density) {
        return loadDrawable(context, density, this.providerInfo.getIconResource(), true);
    }

    public final Drawable loadPreviewImage(Context context, int density) {
        return loadDrawable(context, density, this.previewImage, false);
    }

    public final CharSequence loadDescription(Context context) {
        CharSequence description;
        if (ResourceId.isValid(this.descriptionRes)
                && (description =
                                context.getPackageManager()
                                        .getText(
                                                this.providerInfo.packageName,
                                                this.descriptionRes,
                                                this.providerInfo.applicationInfo))
                        != null) {
            return description.toString().trim();
        }
        return null;
    }

    public final UserHandle getProfile() {
        return new UserHandle(UserHandle.getUserId(this.providerInfo.applicationInfo.uid));
    }

    public ActivityInfo getActivityInfo() {
        return this.providerInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedObject(this.provider, flags);
        out.writeInt(this.minWidth);
        out.writeInt(this.minHeight);
        out.writeInt(this.minResizeWidth);
        out.writeInt(this.minResizeHeight);
        out.writeInt(this.maxResizeWidth);
        out.writeInt(this.maxResizeHeight);
        out.writeInt(this.targetCellWidth);
        out.writeInt(this.targetCellHeight);
        out.writeInt(this.updatePeriodMillis);
        out.writeInt(this.initialLayout);
        out.writeInt(this.initialKeyguardLayout);
        out.writeTypedObject(this.configure, flags);
        out.writeTypedObject(this.semConfigure, flags);
        out.writeString(this.label);
        out.writeInt(this.icon);
        out.writeInt(this.previewImage);
        out.writeInt(this.previewLayout);
        out.writeInt(this.autoAdvanceViewId);
        out.writeInt(this.resizeMode);
        out.writeInt(this.widgetCategory);
        out.writeTypedObject(this.providerInfo, flags);
        out.writeInt(this.widgetFeatures);
        out.writeInt(this.descriptionRes);
        out.writeBoolean(this.isExtendedFromAppWidgetProvider);
        if (Flags.generatedPreviews()) {
            out.writeInt(this.generatedPreviewCategories);
        }
        out.writeInt(this.hidden_semGeneratedColorfulPreviewStates);
        out.writeInt(this.hidden_semGeneratedMonotonePreviewStates);
        out.writeInt(this.hidden_semPreviewRecordResetStates);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AppWidgetProviderInfo m783clone() {
        AppWidgetProviderInfo that = new AppWidgetProviderInfo();
        that.provider = this.provider == null ? null : this.provider.m840clone();
        that.minWidth = this.minWidth;
        that.minHeight = this.minHeight;
        that.minResizeWidth = this.minResizeWidth;
        that.minResizeHeight = this.minResizeHeight;
        that.maxResizeWidth = this.maxResizeWidth;
        that.maxResizeHeight = this.maxResizeHeight;
        that.targetCellWidth = this.targetCellWidth;
        that.targetCellHeight = this.targetCellHeight;
        that.updatePeriodMillis = this.updatePeriodMillis;
        that.initialLayout = this.initialLayout;
        that.initialKeyguardLayout = this.initialKeyguardLayout;
        that.configure = this.configure == null ? null : this.configure.m840clone();
        that.semConfigure = this.semConfigure != null ? this.semConfigure.m840clone() : null;
        that.label = this.label;
        that.icon = this.icon;
        that.previewImage = this.previewImage;
        that.previewLayout = this.previewLayout;
        that.autoAdvanceViewId = this.autoAdvanceViewId;
        that.resizeMode = this.resizeMode;
        that.widgetCategory = this.widgetCategory;
        that.providerInfo = this.providerInfo;
        that.widgetFeatures = this.widgetFeatures;
        that.descriptionRes = this.descriptionRes;
        that.isExtendedFromAppWidgetProvider = this.isExtendedFromAppWidgetProvider;
        if (Flags.generatedPreviews()) {
            that.generatedPreviewCategories = this.generatedPreviewCategories;
        }
        that.hidden_semGeneratedColorfulPreviewStates =
                this.hidden_semGeneratedColorfulPreviewStates;
        that.hidden_semGeneratedMonotonePreviewStates =
                this.hidden_semGeneratedMonotonePreviewStates;
        that.hidden_semPreviewRecordResetStates = this.hidden_semPreviewRecordResetStates;
        return that;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Drawable loadDrawable(
            Context context, int density, int resourceId, boolean loadDefaultIcon) {
        try {
            Resources resources =
                    context.getPackageManager()
                            .getResourcesForApplication(this.providerInfo.applicationInfo);
            if (ResourceId.isValid(resourceId)) {
                if (density < 0) {
                    density = 0;
                }
                return resources.getDrawableForDensity(resourceId, density, null);
            }
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
        }
        if (loadDefaultIcon) {
            return this.providerInfo.loadIcon(context.getPackageManager());
        }
        return null;
    }

    public void updateDimensions(DisplayMetrics displayMetrics) {
        this.minWidth = TypedValue.complexToDimensionPixelSize(this.minWidth, displayMetrics);
        this.minHeight = TypedValue.complexToDimensionPixelSize(this.minHeight, displayMetrics);
        this.minResizeWidth =
                TypedValue.complexToDimensionPixelSize(this.minResizeWidth, displayMetrics);
        this.minResizeHeight =
                TypedValue.complexToDimensionPixelSize(this.minResizeHeight, displayMetrics);
        this.maxResizeWidth =
                TypedValue.complexToDimensionPixelSize(this.maxResizeWidth, displayMetrics);
        this.maxResizeHeight =
                TypedValue.complexToDimensionPixelSize(this.maxResizeHeight, displayMetrics);
    }

    public String toString() {
        return "AppWidgetProviderInfo(" + getProfile() + '/' + this.provider + ')';
    }
}
