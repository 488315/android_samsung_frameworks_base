package android.app;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.ZenDeviceEffects;
import android.service.notification.ZenPolicy;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AutomaticZenRule implements Parcelable {
    public static final Parcelable.Creator<AutomaticZenRule> CREATOR = new Parcelable.Creator<AutomaticZenRule>() { // from class: android.app.AutomaticZenRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AutomaticZenRule createFromParcel(Parcel parcel) {
            return new AutomaticZenRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AutomaticZenRule[] newArray(int i) {
            return new AutomaticZenRule[i];
        }
    };
    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    public static final int FIELD_ICON = 4;
    public static final int FIELD_INTERRUPTION_FILTER = 2;
    public static final int FIELD_NAME = 1;
    public static final int MAX_DESC_LENGTH = 150;
    public static final int MAX_STRING_LENGTH = 500;
    public static final int TYPE_BEDTIME = 3;
    public static final int TYPE_DRIVING = 4;
    public static final int TYPE_IMMERSIVE = 5;
    public static final int TYPE_MANAGED = 7;
    public static final int TYPE_OTHER = 0;
    public static final int TYPE_SCHEDULE_CALENDAR = 2;
    public static final int TYPE_SCHEDULE_TIME = 1;
    public static final int TYPE_THEATER = 6;
    public static final int TYPE_UNKNOWN = -1;
    private Uri conditionId;
    private ComponentName configurationActivity;
    private long creationTime;
    private boolean enabled;
    private int interruptionFilter;
    private boolean mAllowManualInvocation;
    private ZenDeviceEffects mDeviceEffects;
    private int mIconResId;
    private String mPkg;
    private String mTriggerDescription;
    private int mType;
    private ZenPolicy mZenPolicy;
    private String name;
    private ComponentName owner;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public AutomaticZenRule(String str, ComponentName componentName, Uri uri, int i, boolean z) {
        this(str, componentName, null, uri, null, i, z);
    }

    public AutomaticZenRule(String str, ComponentName componentName, ComponentName componentName2, Uri uri, ZenPolicy zenPolicy, int i, boolean z) {
        this.mType = -1;
        this.name = getTrimmedString(str);
        this.owner = getTrimmedComponentName(componentName);
        this.configurationActivity = getTrimmedComponentName(componentName2);
        this.conditionId = getTrimmedUri(uri);
        this.interruptionFilter = i;
        this.enabled = z;
        this.mZenPolicy = zenPolicy;
    }

    @Deprecated
    public AutomaticZenRule(String str, ComponentName componentName, ComponentName componentName2, Uri uri, ZenPolicy zenPolicy, int i, boolean z, long j) {
        this(str, componentName, componentName2, uri, zenPolicy, i, z);
        this.creationTime = j;
    }

    public AutomaticZenRule(Parcel parcel) {
        this.mType = -1;
        this.enabled = parcel.readInt() == 1;
        if (parcel.readInt() == 1) {
            this.name = getTrimmedString(parcel.readString8());
        }
        this.interruptionFilter = parcel.readInt();
        this.conditionId = getTrimmedUri((Uri) parcel.readParcelable(null, Uri.class));
        this.owner = getTrimmedComponentName((ComponentName) parcel.readParcelable(null, ComponentName.class));
        this.configurationActivity = getTrimmedComponentName((ComponentName) parcel.readParcelable(null, ComponentName.class));
        this.creationTime = parcel.readLong();
        this.mZenPolicy = (ZenPolicy) parcel.readParcelable(null, ZenPolicy.class);
        this.mPkg = parcel.readString8();
        this.mDeviceEffects = (ZenDeviceEffects) parcel.readParcelable(null, ZenDeviceEffects.class);
        this.mAllowManualInvocation = parcel.readBoolean();
        this.mIconResId = parcel.readInt();
        this.mTriggerDescription = getTrimmedString(parcel.readString8(), 150);
        this.mType = parcel.readInt();
    }

    public ComponentName getOwner() {
        return this.owner;
    }

    public ComponentName getConfigurationActivity() {
        return this.configurationActivity;
    }

    public Uri getConditionId() {
        return this.conditionId;
    }

    public int getInterruptionFilter() {
        return this.interruptionFilter;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public ZenPolicy getZenPolicy() {
        ZenPolicy zenPolicy = this.mZenPolicy;
        if (zenPolicy == null) {
            return null;
        }
        return zenPolicy.copy();
    }

    public ZenDeviceEffects getDeviceEffects() {
        return this.mDeviceEffects;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public void setConditionId(Uri uri) {
        this.conditionId = getTrimmedUri(uri);
    }

    public void setInterruptionFilter(int i) {
        this.interruptionFilter = i;
    }

    public void setName(String str) {
        this.name = getTrimmedString(str);
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public void setZenPolicy(ZenPolicy zenPolicy) {
        this.mZenPolicy = zenPolicy == null ? null : zenPolicy.copy();
    }

    public void setDeviceEffects(ZenDeviceEffects zenDeviceEffects) {
        this.mDeviceEffects = zenDeviceEffects;
    }

    public void setOwner(ComponentName componentName) {
        this.owner = componentName;
    }

    public void setConfigurationActivity(ComponentName componentName) {
        this.configurationActivity = getTrimmedComponentName(componentName);
    }

    public void setPackageName(String str) {
        this.mPkg = str;
    }

    public String getPackageName() {
        return this.mPkg;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = checkValidType(i);
    }

    public String getTriggerDescription() {
        return this.mTriggerDescription;
    }

    public void setTriggerDescription(String str) {
        this.mTriggerDescription = str;
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public void setIconResId(int i) {
        this.mIconResId = i;
    }

    public boolean isManualInvocationAllowed() {
        return this.mAllowManualInvocation;
    }

    public void setManualInvocationAllowed(boolean z) {
        this.mAllowManualInvocation = z;
    }

    public void validate() {
        checkValidType(this.mType);
        ZenDeviceEffects zenDeviceEffects = this.mDeviceEffects;
        if (zenDeviceEffects != null) {
            zenDeviceEffects.validate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkValidType(int i) {
        Preconditions.checkArgument(i >= -1 && i <= 7, "Rule type must be one of TYPE_UNKNOWN, TYPE_OTHER, TYPE_SCHEDULE_TIME, TYPE_SCHEDULE_CALENDAR, TYPE_BEDTIME, TYPE_DRIVING, TYPE_IMMERSIVE, TYPE_THEATER, or TYPE_MANAGED");
        return i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.enabled ? 1 : 0);
        if (this.name != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.name);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.interruptionFilter);
        parcel.writeParcelable(this.conditionId, 0);
        parcel.writeParcelable(this.owner, 0);
        parcel.writeParcelable(this.configurationActivity, 0);
        parcel.writeLong(this.creationTime);
        parcel.writeParcelable(this.mZenPolicy, 0);
        parcel.writeString8(this.mPkg);
        parcel.writeParcelable(this.mDeviceEffects, 0);
        parcel.writeBoolean(this.mAllowManualInvocation);
        parcel.writeInt(this.mIconResId);
        parcel.writeString8(this.mTriggerDescription);
        parcel.writeInt(this.mType);
    }

    public String toString() {
        return "AutomaticZenRule[enabled=" + this.enabled + ",name=" + this.name + ",type=" + this.mType + ",interruptionFilter=" + this.interruptionFilter + ",pkg=" + this.mPkg + ",conditionId=" + this.conditionId + ",owner=" + this.owner + ",configActivity=" + this.configurationActivity + ",creationTime=" + this.creationTime + ",mZenPolicy=" + this.mZenPolicy + ",deviceEffects=" + this.mDeviceEffects + ",allowManualInvocation=" + this.mAllowManualInvocation + ",iconResId=" + this.mIconResId + ",triggerDescription=" + this.mTriggerDescription + ']';
    }

    public static String fieldsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("FIELD_NAME");
        }
        if ((i & 2) != 0) {
            arrayList.add("FIELD_INTERRUPTION_FILTER");
        }
        if ((i & 4) != 0) {
            arrayList.add("FIELD_ICON");
        }
        return "{" + String.join(",", arrayList) + "}";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AutomaticZenRule)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        AutomaticZenRule automaticZenRule = (AutomaticZenRule) obj;
        return automaticZenRule.enabled == this.enabled && Objects.equals(automaticZenRule.name, this.name) && automaticZenRule.interruptionFilter == this.interruptionFilter && Objects.equals(automaticZenRule.conditionId, this.conditionId) && Objects.equals(automaticZenRule.owner, this.owner) && Objects.equals(automaticZenRule.mZenPolicy, this.mZenPolicy) && Objects.equals(automaticZenRule.configurationActivity, this.configurationActivity) && Objects.equals(automaticZenRule.mPkg, this.mPkg) && automaticZenRule.creationTime == this.creationTime && Objects.equals(automaticZenRule.mDeviceEffects, this.mDeviceEffects) && automaticZenRule.mAllowManualInvocation == this.mAllowManualInvocation && automaticZenRule.mIconResId == this.mIconResId && Objects.equals(automaticZenRule.mTriggerDescription, this.mTriggerDescription) && automaticZenRule.mType == this.mType;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.enabled), this.name, Integer.valueOf(this.interruptionFilter), this.conditionId, this.owner, this.configurationActivity, this.mZenPolicy, this.mDeviceEffects, Long.valueOf(this.creationTime), this.mPkg, Boolean.valueOf(this.mAllowManualInvocation), Integer.valueOf(this.mIconResId), this.mTriggerDescription, Integer.valueOf(this.mType));
    }

    private static ComponentName getTrimmedComponentName(ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        return new ComponentName(getTrimmedString(componentName.getPackageName()), getTrimmedString(componentName.getClassName()));
    }

    private static String getTrimmedString(String str) {
        return getTrimmedString(str, 500);
    }

    private static String getTrimmedString(String str, int i) {
        return (str == null || str.length() <= i) ? str : str.substring(0, i);
    }

    private static Uri getTrimmedUri(Uri uri) {
        return (uri == null || uri.toString().length() <= 500) ? uri : Uri.parse(getTrimmedString(uri.toString()));
    }

    public static final class Builder {
        private boolean mAllowManualInvocation;
        private Uri mConditionId;
        private ComponentName mConfigurationActivity;
        private long mCreationTime;
        private String mDescription;
        private ZenDeviceEffects mDeviceEffects;
        private boolean mEnabled;
        private int mIconResId;
        private int mInterruptionFilter;
        private String mName;
        private ComponentName mOwner;
        private String mPkg;
        private ZenPolicy mPolicy;
        private int mType;

        public Builder(AutomaticZenRule automaticZenRule) {
            this.mInterruptionFilter = 2;
            this.mEnabled = true;
            this.mConfigurationActivity = null;
            this.mPolicy = null;
            this.mDeviceEffects = null;
            this.mType = -1;
            this.mName = automaticZenRule.getName();
            this.mOwner = automaticZenRule.getOwner();
            this.mConditionId = automaticZenRule.getConditionId();
            this.mInterruptionFilter = automaticZenRule.getInterruptionFilter();
            this.mEnabled = automaticZenRule.isEnabled();
            this.mConfigurationActivity = automaticZenRule.getConfigurationActivity();
            this.mPolicy = automaticZenRule.getZenPolicy();
            this.mDeviceEffects = automaticZenRule.getDeviceEffects();
            this.mType = automaticZenRule.getType();
            this.mDescription = automaticZenRule.getTriggerDescription();
            this.mIconResId = automaticZenRule.getIconResId();
            this.mAllowManualInvocation = automaticZenRule.isManualInvocationAllowed();
            this.mCreationTime = automaticZenRule.getCreationTime();
            this.mPkg = automaticZenRule.getPackageName();
        }

        public Builder(String str, Uri uri) {
            this.mInterruptionFilter = 2;
            this.mEnabled = true;
            this.mConfigurationActivity = null;
            this.mPolicy = null;
            this.mDeviceEffects = null;
            this.mType = -1;
            this.mName = (String) Objects.requireNonNull(str);
            this.mConditionId = (Uri) Objects.requireNonNull(uri);
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setOwner(ComponentName componentName) {
            this.mOwner = componentName;
            return this;
        }

        public Builder setConditionId(Uri uri) {
            this.mConditionId = uri;
            return this;
        }

        public Builder setInterruptionFilter(int i) {
            this.mInterruptionFilter = i;
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.mEnabled = z;
            return this;
        }

        public Builder setConfigurationActivity(ComponentName componentName) {
            this.mConfigurationActivity = componentName;
            return this;
        }

        public Builder setZenPolicy(ZenPolicy zenPolicy) {
            this.mPolicy = zenPolicy;
            return this;
        }

        public Builder setDeviceEffects(ZenDeviceEffects zenDeviceEffects) {
            this.mDeviceEffects = zenDeviceEffects;
            return this;
        }

        public Builder setType(int i) {
            this.mType = AutomaticZenRule.checkValidType(i);
            return this;
        }

        public Builder setTriggerDescription(String str) {
            this.mDescription = str;
            return this;
        }

        public Builder setIconResId(int i) {
            this.mIconResId = i;
            return this;
        }

        public Builder setManualInvocationAllowed(boolean z) {
            this.mAllowManualInvocation = z;
            return this;
        }

        public Builder setCreationTime(long j) {
            this.mCreationTime = j;
            return this;
        }

        public Builder setPackage(String str) {
            this.mPkg = str;
            return this;
        }

        public AutomaticZenRule build() {
            AutomaticZenRule automaticZenRule = new AutomaticZenRule(this.mName, this.mOwner, this.mConfigurationActivity, this.mConditionId, this.mPolicy, this.mInterruptionFilter, this.mEnabled);
            automaticZenRule.mDeviceEffects = this.mDeviceEffects;
            automaticZenRule.creationTime = this.mCreationTime;
            automaticZenRule.mType = this.mType;
            automaticZenRule.mTriggerDescription = this.mDescription;
            automaticZenRule.mIconResId = this.mIconResId;
            automaticZenRule.mAllowManualInvocation = this.mAllowManualInvocation;
            automaticZenRule.setPackageName(this.mPkg);
            return automaticZenRule;
        }
    }

    public static final class AzrWithId implements Parcelable {
        public static final Parcelable.Creator<AzrWithId> CREATOR = new Parcelable.Creator<AzrWithId>() { // from class: android.app.AutomaticZenRule.AzrWithId.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AzrWithId createFromParcel(Parcel parcel) {
                return new AzrWithId(parcel.readString8(), (AutomaticZenRule) parcel.readParcelable(AutomaticZenRule.class.getClassLoader(), AutomaticZenRule.class));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AzrWithId[] newArray(int i) {
                return new AzrWithId[i];
            }
        };
        public final String mId;
        public final AutomaticZenRule mRule;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AzrWithId(String str, AutomaticZenRule automaticZenRule) {
            this.mId = str;
            this.mRule = automaticZenRule;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mId);
            parcel.writeParcelable(this.mRule, i);
        }
    }
}
