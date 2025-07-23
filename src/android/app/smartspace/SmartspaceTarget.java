package android.app.smartspace;

import android.annotation.SystemApi;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.widget.RemoteViews;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SmartspaceTarget implements Parcelable {
    public static final Parcelable.Creator<SmartspaceTarget> CREATOR = new Parcelable.Creator<SmartspaceTarget>() { // from class: android.app.smartspace.SmartspaceTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceTarget createFromParcel(Parcel parcel) {
            return new SmartspaceTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceTarget[] newArray(int i) {
            return new SmartspaceTarget[i];
        }
    };
    public static final int FEATURE_ALARM = 7;
    public static final int FEATURE_BEDTIME_ROUTINE = 16;
    public static final int FEATURE_BLAZE_BUILD_PROGRESS = 40;
    public static final int FEATURE_CALENDAR = 2;
    public static final int FEATURE_COMMUTE_TIME = 3;
    public static final int FEATURE_CONSENT = 11;
    public static final int FEATURE_CROSS_DEVICE_TIMER = 32;
    public static final int FEATURE_DOORBELL = 30;
    public static final int FEATURE_DRIVING_MODE = 26;
    public static final int FEATURE_EARTHQUAKE_ALERT = 38;
    public static final int FEATURE_EARTHQUAKE_OCCURRED = 41;
    public static final int FEATURE_ETA_MONITORING = 18;
    public static final int FEATURE_FITNESS_TRACKING = 17;
    public static final int FEATURE_FLASHLIGHT = 28;
    public static final int FEATURE_FLIGHT = 4;
    public static final int FEATURE_GAS_STATION_PAYMENT = 24;
    public static final int FEATURE_HOLIDAY_ALARM = 34;
    public static final int FEATURE_LOYALTY_CARD = 14;
    public static final int FEATURE_MEDIA = 15;
    public static final int FEATURE_MEDIA_HEADS_UP = 36;
    public static final int FEATURE_MEDIA_RESUME = 31;
    public static final int FEATURE_MISSED_CALL = 19;
    public static final int FEATURE_ONBOARDING = 8;
    public static final int FEATURE_PACKAGE_TRACKING = 20;
    public static final int FEATURE_PAIRED_DEVICE_STATE = 25;
    public static final int FEATURE_REMINDER = 6;
    public static final int FEATURE_SAFETY_CHECK = 35;
    public static final int FEATURE_SEVERE_WEATHER_ALERT = 33;
    public static final int FEATURE_SHOPPING_LIST = 13;
    public static final int FEATURE_SLEEP_SUMMARY = 27;
    public static final int FEATURE_SPORTS = 9;
    public static final int FEATURE_STEP_COUNTING = 37;
    public static final int FEATURE_STEP_DATE = 39;
    public static final int FEATURE_STOCK_PRICE_CHANGE = 12;
    public static final int FEATURE_STOPWATCH = 22;
    public static final int FEATURE_TIMER = 21;
    public static final int FEATURE_TIME_TO_LEAVE = 29;
    public static final int FEATURE_TIPS = 5;
    public static final int FEATURE_UNDEFINED = 0;
    public static final int FEATURE_UPCOMING_ALARM = 23;
    public static final int FEATURE_WEATHER = 1;
    public static final int FEATURE_WEATHER_ALERT = 10;
    public static final int UI_TEMPLATE_CAROUSEL = 4;
    public static final int UI_TEMPLATE_COMBINED_CARDS = 6;
    public static final int UI_TEMPLATE_DEFAULT = 1;
    public static final int UI_TEMPLATE_HEAD_TO_HEAD = 5;
    public static final int UI_TEMPLATE_SUB_CARD = 7;
    public static final int UI_TEMPLATE_SUB_IMAGE = 2;
    public static final int UI_TEMPLATE_SUB_LIST = 3;
    public static final int UI_TEMPLATE_UNDEFINED = 0;
    private final List<SmartspaceAction> mActionChips;
    private final String mAssociatedSmartspaceTargetId;
    private final SmartspaceAction mBaseAction;
    private final ComponentName mComponentName;
    private final long mCreationTimeMillis;
    private final long mExpiryTimeMillis;
    private final int mFeatureType;
    private final SmartspaceAction mHeaderAction;
    private final List<SmartspaceAction> mIconGrid;
    private final RemoteViews mRemoteViews;
    private final float mScore;
    private final boolean mSensitive;
    private final boolean mShouldShowExpanded;
    private final Uri mSliceUri;
    private final String mSmartspaceTargetId;
    private final String mSourceNotificationKey;
    private final BaseTemplateData mTemplateData;
    private final UserHandle mUserHandle;
    private final AppWidgetProviderInfo mWidget;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FeatureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UiTemplateType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SmartspaceTarget(Parcel parcel) {
        this.mSmartspaceTargetId = parcel.readString();
        this.mHeaderAction = (SmartspaceAction) parcel.readTypedObject(SmartspaceAction.CREATOR);
        this.mBaseAction = (SmartspaceAction) parcel.readTypedObject(SmartspaceAction.CREATOR);
        this.mCreationTimeMillis = parcel.readLong();
        this.mExpiryTimeMillis = parcel.readLong();
        this.mScore = parcel.readFloat();
        this.mActionChips = parcel.createTypedArrayList(SmartspaceAction.CREATOR);
        this.mIconGrid = parcel.createTypedArrayList(SmartspaceAction.CREATOR);
        this.mFeatureType = parcel.readInt();
        this.mSensitive = parcel.readBoolean();
        this.mShouldShowExpanded = parcel.readBoolean();
        this.mSourceNotificationKey = parcel.readString();
        this.mComponentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.mUserHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        this.mAssociatedSmartspaceTargetId = parcel.readString();
        this.mSliceUri = (Uri) parcel.readTypedObject(Uri.CREATOR);
        this.mWidget = (AppWidgetProviderInfo) parcel.readTypedObject(AppWidgetProviderInfo.CREATOR);
        this.mTemplateData = (BaseTemplateData) parcel.readParcelable(null, BaseTemplateData.class);
        this.mRemoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
    }

    private SmartspaceTarget(String str, SmartspaceAction smartspaceAction, SmartspaceAction smartspaceAction2, long j, long j2, float f, List<SmartspaceAction> list, List<SmartspaceAction> list2, int i, boolean z, boolean z2, String str2, ComponentName componentName, UserHandle userHandle, String str3, Uri uri, AppWidgetProviderInfo appWidgetProviderInfo, BaseTemplateData baseTemplateData, RemoteViews remoteViews) {
        this.mSmartspaceTargetId = str;
        this.mHeaderAction = smartspaceAction;
        this.mBaseAction = smartspaceAction2;
        this.mCreationTimeMillis = j;
        this.mExpiryTimeMillis = j2;
        this.mScore = f;
        this.mActionChips = list;
        this.mIconGrid = list2;
        this.mFeatureType = i;
        this.mSensitive = z;
        this.mShouldShowExpanded = z2;
        this.mSourceNotificationKey = str2;
        this.mComponentName = componentName;
        this.mUserHandle = userHandle;
        this.mAssociatedSmartspaceTargetId = str3;
        this.mSliceUri = uri;
        this.mWidget = appWidgetProviderInfo;
        this.mTemplateData = baseTemplateData;
        this.mRemoteViews = remoteViews;
    }

    public String getSmartspaceTargetId() {
        return this.mSmartspaceTargetId;
    }

    public SmartspaceAction getHeaderAction() {
        return this.mHeaderAction;
    }

    public SmartspaceAction getBaseAction() {
        return this.mBaseAction;
    }

    public long getCreationTimeMillis() {
        return this.mCreationTimeMillis;
    }

    public long getExpiryTimeMillis() {
        return this.mExpiryTimeMillis;
    }

    public float getScore() {
        return this.mScore;
    }

    public List<SmartspaceAction> getActionChips() {
        return this.mActionChips;
    }

    public List<SmartspaceAction> getIconGrid() {
        return this.mIconGrid;
    }

    public int getFeatureType() {
        return this.mFeatureType;
    }

    public boolean isSensitive() {
        return this.mSensitive;
    }

    public boolean shouldShowExpanded() {
        return this.mShouldShowExpanded;
    }

    public String getSourceNotificationKey() {
        return this.mSourceNotificationKey;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public String getAssociatedSmartspaceTargetId() {
        return this.mAssociatedSmartspaceTargetId;
    }

    public Uri getSliceUri() {
        return this.mSliceUri;
    }

    public AppWidgetProviderInfo getWidget() {
        return this.mWidget;
    }

    public BaseTemplateData getTemplateData() {
        return this.mTemplateData;
    }

    public RemoteViews getRemoteViews() {
        return this.mRemoteViews;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSmartspaceTargetId);
        parcel.writeTypedObject(this.mHeaderAction, i);
        parcel.writeTypedObject(this.mBaseAction, i);
        parcel.writeLong(this.mCreationTimeMillis);
        parcel.writeLong(this.mExpiryTimeMillis);
        parcel.writeFloat(this.mScore);
        parcel.writeTypedList(this.mActionChips);
        parcel.writeTypedList(this.mIconGrid);
        parcel.writeInt(this.mFeatureType);
        parcel.writeBoolean(this.mSensitive);
        parcel.writeBoolean(this.mShouldShowExpanded);
        parcel.writeString(this.mSourceNotificationKey);
        parcel.writeTypedObject(this.mComponentName, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeString(this.mAssociatedSmartspaceTargetId);
        parcel.writeTypedObject(this.mSliceUri, i);
        parcel.writeTypedObject(this.mWidget, i);
        parcel.writeParcelable(this.mTemplateData, i);
        parcel.writeTypedObject(this.mRemoteViews, i);
    }

    public String toString() {
        return "SmartspaceTarget{mSmartspaceTargetId='" + this.mSmartspaceTargetId + "', mHeaderAction=" + this.mHeaderAction + ", mBaseAction=" + this.mBaseAction + ", mCreationTimeMillis=" + this.mCreationTimeMillis + ", mExpiryTimeMillis=" + this.mExpiryTimeMillis + ", mScore=" + this.mScore + ", mActionChips=" + this.mActionChips + ", mIconGrid=" + this.mIconGrid + ", mFeatureType=" + this.mFeatureType + ", mSensitive=" + this.mSensitive + ", mShouldShowExpanded=" + this.mShouldShowExpanded + ", mSourceNotificationKey='" + this.mSourceNotificationKey + "', mComponentName=" + this.mComponentName + ", mUserHandle=" + this.mUserHandle + ", mAssociatedSmartspaceTargetId='" + this.mAssociatedSmartspaceTargetId + "', mSliceUri=" + this.mSliceUri + ", mWidget=" + this.mWidget + ", mTemplateData=" + this.mTemplateData + ", mRemoteViews=" + this.mRemoteViews + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SmartspaceTarget smartspaceTarget = (SmartspaceTarget) obj;
            if (this.mCreationTimeMillis == smartspaceTarget.mCreationTimeMillis && this.mExpiryTimeMillis == smartspaceTarget.mExpiryTimeMillis && Float.compare(smartspaceTarget.mScore, this.mScore) == 0 && this.mFeatureType == smartspaceTarget.mFeatureType && this.mSensitive == smartspaceTarget.mSensitive && this.mShouldShowExpanded == smartspaceTarget.mShouldShowExpanded && this.mSmartspaceTargetId.equals(smartspaceTarget.mSmartspaceTargetId) && Objects.equals(this.mHeaderAction, smartspaceTarget.mHeaderAction) && Objects.equals(this.mBaseAction, smartspaceTarget.mBaseAction) && Objects.equals(this.mActionChips, smartspaceTarget.mActionChips) && Objects.equals(this.mIconGrid, smartspaceTarget.mIconGrid) && Objects.equals(this.mSourceNotificationKey, smartspaceTarget.mSourceNotificationKey) && this.mComponentName.equals(smartspaceTarget.mComponentName) && this.mUserHandle.equals(smartspaceTarget.mUserHandle) && Objects.equals(this.mAssociatedSmartspaceTargetId, smartspaceTarget.mAssociatedSmartspaceTargetId) && Objects.equals(this.mSliceUri, smartspaceTarget.mSliceUri) && Objects.equals(this.mWidget, smartspaceTarget.mWidget) && Objects.equals(this.mTemplateData, smartspaceTarget.mTemplateData) && Objects.equals(this.mRemoteViews, smartspaceTarget.mRemoteViews)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mSmartspaceTargetId, this.mHeaderAction, this.mBaseAction, Long.valueOf(this.mCreationTimeMillis), Long.valueOf(this.mExpiryTimeMillis), Float.valueOf(this.mScore), this.mActionChips, this.mIconGrid, Integer.valueOf(this.mFeatureType), Boolean.valueOf(this.mSensitive), Boolean.valueOf(this.mShouldShowExpanded), this.mSourceNotificationKey, this.mComponentName, this.mUserHandle, this.mAssociatedSmartspaceTargetId, this.mSliceUri, this.mWidget, this.mTemplateData, this.mRemoteViews);
    }

    @SystemApi
    public static final class Builder {
        private String mAssociatedSmartspaceTargetId;
        private SmartspaceAction mBaseAction;
        private final ComponentName mComponentName;
        private long mCreationTimeMillis;
        private long mExpiryTimeMillis;
        private int mFeatureType;
        private SmartspaceAction mHeaderAction;
        private RemoteViews mRemoteViews;
        private float mScore;
        private boolean mSensitive;
        private boolean mShouldShowExpanded;
        private Uri mSliceUri;
        private final String mSmartspaceTargetId;
        private String mSourceNotificationKey;
        private BaseTemplateData mTemplateData;
        private final UserHandle mUserHandle;
        private AppWidgetProviderInfo mWidget;
        private List<SmartspaceAction> mActionChips = new ArrayList();
        private List<SmartspaceAction> mIconGrid = new ArrayList();

        public Builder(String str, ComponentName componentName, UserHandle userHandle) {
            this.mSmartspaceTargetId = str;
            this.mComponentName = componentName;
            this.mUserHandle = userHandle;
        }

        public Builder setHeaderAction(SmartspaceAction smartspaceAction) {
            this.mHeaderAction = smartspaceAction;
            return this;
        }

        public Builder setBaseAction(SmartspaceAction smartspaceAction) {
            this.mBaseAction = smartspaceAction;
            return this;
        }

        public Builder setCreationTimeMillis(long j) {
            this.mCreationTimeMillis = j;
            return this;
        }

        public Builder setExpiryTimeMillis(long j) {
            this.mExpiryTimeMillis = j;
            return this;
        }

        public Builder setScore(float f) {
            this.mScore = f;
            return this;
        }

        public Builder setActionChips(List<SmartspaceAction> list) {
            this.mActionChips = list;
            return this;
        }

        public Builder setIconGrid(List<SmartspaceAction> list) {
            this.mIconGrid = list;
            return this;
        }

        public Builder setFeatureType(int i) {
            this.mFeatureType = i;
            return this;
        }

        public Builder setSensitive(boolean z) {
            this.mSensitive = z;
            return this;
        }

        public Builder setShouldShowExpanded(boolean z) {
            this.mShouldShowExpanded = z;
            return this;
        }

        public Builder setSourceNotificationKey(String str) {
            this.mSourceNotificationKey = str;
            return this;
        }

        public Builder setAssociatedSmartspaceTargetId(String str) {
            this.mAssociatedSmartspaceTargetId = str;
            return this;
        }

        public Builder setSliceUri(Uri uri) {
            this.mSliceUri = uri;
            return this;
        }

        public Builder setWidget(AppWidgetProviderInfo appWidgetProviderInfo) {
            if (this.mRemoteViews != null) {
                throw new IllegalStateException("Widget providers and RemoteViews cannot be used at the same time.");
            }
            this.mWidget = appWidgetProviderInfo;
            return this;
        }

        public Builder setTemplateData(BaseTemplateData baseTemplateData) {
            this.mTemplateData = baseTemplateData;
            return this;
        }

        public Builder setRemoteViews(RemoteViews remoteViews) {
            if (this.mWidget != null) {
                throw new IllegalStateException("Widget providers and RemoteViews cannot be used at the same time.");
            }
            this.mRemoteViews = remoteViews;
            return this;
        }

        public SmartspaceTarget build() {
            if (this.mSmartspaceTargetId == null || this.mComponentName == null || this.mUserHandle == null) {
                throw new IllegalStateException("Please assign a value to all @NonNull args.");
            }
            return new SmartspaceTarget(this.mSmartspaceTargetId, this.mHeaderAction, this.mBaseAction, this.mCreationTimeMillis, this.mExpiryTimeMillis, this.mScore, this.mActionChips, this.mIconGrid, this.mFeatureType, this.mSensitive, this.mShouldShowExpanded, this.mSourceNotificationKey, this.mComponentName, this.mUserHandle, this.mAssociatedSmartspaceTargetId, this.mSliceUri, this.mWidget, this.mTemplateData, this.mRemoteViews);
        }
    }
}
