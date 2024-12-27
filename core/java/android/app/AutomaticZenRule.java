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
    public static final Parcelable.Creator<AutomaticZenRule> CREATOR =
            new Parcelable.Creator<
                    AutomaticZenRule>() { // from class: android.app.AutomaticZenRule.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AutomaticZenRule createFromParcel(Parcel source) {
                    return new AutomaticZenRule(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AutomaticZenRule[] newArray(int size) {
                    return new AutomaticZenRule[size];
                }
            };
    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    public static final int FIELD_ICON = 4;
    public static final int FIELD_INTERRUPTION_FILTER = 2;
    public static final int FIELD_NAME = 1;
    public static final int MAX_DESC_LENGTH = 150;
    public static final int MAX_STRING_LENGTH = 1000;
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
    private boolean mModified;
    private String mPkg;
    private String mTriggerDescription;
    private int mType;
    private ZenPolicy mZenPolicy;
    private String name;
    private ComponentName owner;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModifiableField {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

    @Deprecated
    public AutomaticZenRule(
            String name,
            ComponentName owner,
            Uri conditionId,
            int interruptionFilter,
            boolean enabled) {
        this(name, owner, null, conditionId, null, interruptionFilter, enabled);
    }

    public AutomaticZenRule(
            String name,
            ComponentName owner,
            ComponentName configurationActivity,
            Uri conditionId,
            ZenPolicy policy,
            int interruptionFilter,
            boolean enabled) {
        this.mModified = false;
        this.mType = Flags.modesApi() ? -1 : 0;
        this.name = getTrimmedString(name);
        this.owner = getTrimmedComponentName(owner);
        this.configurationActivity = getTrimmedComponentName(configurationActivity);
        this.conditionId = getTrimmedUri(conditionId);
        this.interruptionFilter = interruptionFilter;
        this.enabled = enabled;
        this.mZenPolicy = policy;
    }

    public AutomaticZenRule(
            String name,
            ComponentName owner,
            ComponentName configurationActivity,
            Uri conditionId,
            ZenPolicy policy,
            int interruptionFilter,
            boolean enabled,
            long creationTime) {
        this(name, owner, configurationActivity, conditionId, policy, interruptionFilter, enabled);
        this.creationTime = creationTime;
    }

    public AutomaticZenRule(Parcel source) {
        this.mModified = false;
        this.mType = Flags.modesApi() ? -1 : 0;
        this.enabled = source.readInt() == 1;
        if (source.readInt() == 1) {
            this.name = getTrimmedString(source.readString());
        }
        this.interruptionFilter = source.readInt();
        this.conditionId = getTrimmedUri((Uri) source.readParcelable(null, Uri.class));
        this.owner =
                getTrimmedComponentName(
                        (ComponentName) source.readParcelable(null, ComponentName.class));
        this.configurationActivity =
                getTrimmedComponentName(
                        (ComponentName) source.readParcelable(null, ComponentName.class));
        this.creationTime = source.readLong();
        this.mZenPolicy = (ZenPolicy) source.readParcelable(null, ZenPolicy.class);
        this.mModified = source.readInt() == 1;
        this.mPkg = source.readString();
        if (Flags.modesApi()) {
            this.mDeviceEffects =
                    (ZenDeviceEffects) source.readParcelable(null, ZenDeviceEffects.class);
            this.mAllowManualInvocation = source.readBoolean();
            this.mIconResId = source.readInt();
            this.mTriggerDescription = getTrimmedString(source.readString(), 150);
            this.mType = source.readInt();
        }
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

    public boolean isModified() {
        return this.mModified;
    }

    public ZenPolicy getZenPolicy() {
        if (this.mZenPolicy == null) {
            return null;
        }
        return this.mZenPolicy.copy();
    }

    public ZenDeviceEffects getDeviceEffects() {
        return this.mDeviceEffects;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public void setConditionId(Uri conditionId) {
        this.conditionId = getTrimmedUri(conditionId);
    }

    public void setInterruptionFilter(int interruptionFilter) {
        this.interruptionFilter = interruptionFilter;
    }

    public void setName(String name) {
        this.name = getTrimmedString(name);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setModified(boolean modified) {
        this.mModified = modified;
    }

    public void setZenPolicy(ZenPolicy zenPolicy) {
        this.mZenPolicy = zenPolicy == null ? null : zenPolicy.copy();
    }

    public void setDeviceEffects(ZenDeviceEffects deviceEffects) {
        this.mDeviceEffects = deviceEffects;
    }

    public void setConfigurationActivity(ComponentName componentName) {
        this.configurationActivity = getTrimmedComponentName(componentName);
    }

    public void setPackageName(String pkgName) {
        this.mPkg = pkgName;
    }

    public String getPackageName() {
        return this.mPkg;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = checkValidType(type);
    }

    public String getTriggerDescription() {
        return this.mTriggerDescription;
    }

    public void setTriggerDescription(String triggerDescription) {
        this.mTriggerDescription = triggerDescription;
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public void setIconResId(int iconResId) {
        this.mIconResId = iconResId;
    }

    public boolean isManualInvocationAllowed() {
        return this.mAllowManualInvocation;
    }

    public void setManualInvocationAllowed(boolean allowManualInvocation) {
        this.mAllowManualInvocation = allowManualInvocation;
    }

    public void validate() {
        if (Flags.modesApi()) {
            checkValidType(this.mType);
            if (this.mDeviceEffects != null) {
                this.mDeviceEffects.validate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkValidType(int type) {
        Preconditions.checkArgument(
                type >= -1 && type <= 7,
                "Rule type must be one of TYPE_UNKNOWN, TYPE_OTHER, TYPE_SCHEDULE_TIME,"
                        + " TYPE_SCHEDULE_CALENDAR, TYPE_BEDTIME, TYPE_DRIVING, TYPE_IMMERSIVE,"
                        + " TYPE_THEATER, or TYPE_MANAGED");
        return type;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.enabled ? 1 : 0);
        if (this.name != null) {
            parcel.writeInt(1);
            parcel.writeString(this.name);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.interruptionFilter);
        parcel.writeParcelable(this.conditionId, 0);
        parcel.writeParcelable(this.owner, 0);
        parcel.writeParcelable(this.configurationActivity, 0);
        parcel.writeLong(this.creationTime);
        parcel.writeParcelable(this.mZenPolicy, 0);
        parcel.writeInt(this.mModified ? 1 : 0);
        parcel.writeString(this.mPkg);
        if (Flags.modesApi()) {
            parcel.writeParcelable(this.mDeviceEffects, 0);
            parcel.writeBoolean(this.mAllowManualInvocation);
            parcel.writeInt(this.mIconResId);
            parcel.writeString(this.mTriggerDescription);
            parcel.writeInt(this.mType);
        }
    }

    public String toString() {
        StringBuilder sb =
                new StringBuilder(AutomaticZenRule.class.getSimpleName())
                        .append('[')
                        .append("enabled=")
                        .append(this.enabled)
                        .append(",name=")
                        .append(this.name)
                        .append(",interruptionFilter=")
                        .append(this.interruptionFilter)
                        .append(",pkg=")
                        .append(this.mPkg)
                        .append(",conditionId=")
                        .append(this.conditionId)
                        .append(",owner=")
                        .append(this.owner)
                        .append(",configActivity=")
                        .append(this.configurationActivity)
                        .append(",creationTime=")
                        .append(this.creationTime)
                        .append(",mZenPolicy=")
                        .append(this.mZenPolicy);
        if (Flags.modesApi()) {
            sb.append(",deviceEffects=")
                    .append(this.mDeviceEffects)
                    .append(",allowManualInvocation=")
                    .append(this.mAllowManualInvocation)
                    .append(",iconResId=")
                    .append(this.mIconResId)
                    .append(",triggerDescription=")
                    .append(this.mTriggerDescription)
                    .append(",type=")
                    .append(this.mType);
        }
        return sb.append(']').toString();
    }

    public static String fieldsToString(int bitmask) {
        ArrayList<String> modified = new ArrayList<>();
        if ((bitmask & 1) != 0) {
            modified.add("FIELD_NAME");
        }
        if ((bitmask & 2) != 0) {
            modified.add("FIELD_INTERRUPTION_FILTER");
        }
        if ((bitmask & 4) != 0) {
            modified.add("FIELD_ICON");
        }
        return "{" + String.join(",", modified) + "}";
    }

    public boolean equals(Object o) {
        if (!(o instanceof AutomaticZenRule)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        AutomaticZenRule other = (AutomaticZenRule) o;
        boolean finalEquals =
                other.enabled == this.enabled
                        && other.mModified == this.mModified
                        && Objects.equals(other.name, this.name)
                        && other.interruptionFilter == this.interruptionFilter
                        && Objects.equals(other.conditionId, this.conditionId)
                        && Objects.equals(other.owner, this.owner)
                        && Objects.equals(other.mZenPolicy, this.mZenPolicy)
                        && Objects.equals(other.configurationActivity, this.configurationActivity)
                        && Objects.equals(other.mPkg, this.mPkg)
                        && other.creationTime == this.creationTime;
        if (Flags.modesApi()) {
            return finalEquals
                    && Objects.equals(other.mDeviceEffects, this.mDeviceEffects)
                    && other.mAllowManualInvocation == this.mAllowManualInvocation
                    && other.mIconResId == this.mIconResId
                    && Objects.equals(other.mTriggerDescription, this.mTriggerDescription)
                    && other.mType == this.mType;
        }
        return finalEquals;
    }

    public int hashCode() {
        if (Flags.modesApi()) {
            return Objects.hash(
                    Boolean.valueOf(this.enabled),
                    this.name,
                    Integer.valueOf(this.interruptionFilter),
                    this.conditionId,
                    this.owner,
                    this.configurationActivity,
                    this.mZenPolicy,
                    this.mDeviceEffects,
                    Boolean.valueOf(this.mModified),
                    Long.valueOf(this.creationTime),
                    this.mPkg,
                    Boolean.valueOf(this.mAllowManualInvocation),
                    Integer.valueOf(this.mIconResId),
                    this.mTriggerDescription,
                    Integer.valueOf(this.mType));
        }
        return Objects.hash(
                Boolean.valueOf(this.enabled),
                this.name,
                Integer.valueOf(this.interruptionFilter),
                this.conditionId,
                this.owner,
                this.configurationActivity,
                this.mZenPolicy,
                Boolean.valueOf(this.mModified),
                Long.valueOf(this.creationTime),
                this.mPkg);
    }

    private static ComponentName getTrimmedComponentName(ComponentName cn) {
        if (cn == null) {
            return null;
        }
        return new ComponentName(
                getTrimmedString(cn.getPackageName()), getTrimmedString(cn.getClassName()));
    }

    private static String getTrimmedString(String input) {
        return getTrimmedString(input, 1000);
    }

    private static String getTrimmedString(String input, int length) {
        if (input != null && input.length() > length) {
            return input.substring(0, length);
        }
        return input;
    }

    private static Uri getTrimmedUri(Uri input) {
        if (input != null && input.toString().length() > 1000) {
            return Uri.parse(getTrimmedString(input.toString()));
        }
        return input;
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

        public Builder(AutomaticZenRule rule) {
            this.mInterruptionFilter = 2;
            this.mEnabled = true;
            this.mConfigurationActivity = null;
            this.mPolicy = null;
            this.mDeviceEffects = null;
            this.mType = -1;
            this.mName = rule.getName();
            this.mOwner = rule.getOwner();
            this.mConditionId = rule.getConditionId();
            this.mInterruptionFilter = rule.getInterruptionFilter();
            this.mEnabled = rule.isEnabled();
            this.mConfigurationActivity = rule.getConfigurationActivity();
            this.mPolicy = rule.getZenPolicy();
            this.mDeviceEffects = rule.getDeviceEffects();
            this.mType = rule.getType();
            this.mDescription = rule.getTriggerDescription();
            this.mIconResId = rule.getIconResId();
            this.mAllowManualInvocation = rule.isManualInvocationAllowed();
            this.mCreationTime = rule.getCreationTime();
            this.mPkg = rule.getPackageName();
        }

        public Builder(String name, Uri conditionId) {
            this.mInterruptionFilter = 2;
            this.mEnabled = true;
            this.mConfigurationActivity = null;
            this.mPolicy = null;
            this.mDeviceEffects = null;
            this.mType = -1;
            this.mName = (String) Objects.requireNonNull(name);
            this.mConditionId = (Uri) Objects.requireNonNull(conditionId);
        }

        public Builder setName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setOwner(ComponentName owner) {
            this.mOwner = owner;
            return this;
        }

        public Builder setConditionId(Uri conditionId) {
            this.mConditionId = conditionId;
            return this;
        }

        public Builder setInterruptionFilter(int interruptionFilter) {
            this.mInterruptionFilter = interruptionFilter;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.mEnabled = enabled;
            return this;
        }

        public Builder setConfigurationActivity(ComponentName configurationActivity) {
            this.mConfigurationActivity = configurationActivity;
            return this;
        }

        public Builder setZenPolicy(ZenPolicy policy) {
            this.mPolicy = policy;
            return this;
        }

        public Builder setDeviceEffects(ZenDeviceEffects deviceEffects) {
            this.mDeviceEffects = deviceEffects;
            return this;
        }

        public Builder setType(int type) {
            this.mType = AutomaticZenRule.checkValidType(type);
            return this;
        }

        public Builder setTriggerDescription(String description) {
            this.mDescription = description;
            return this;
        }

        public Builder setIconResId(int iconResId) {
            this.mIconResId = iconResId;
            return this;
        }

        public Builder setManualInvocationAllowed(boolean allowManualInvocation) {
            this.mAllowManualInvocation = allowManualInvocation;
            return this;
        }

        public Builder setCreationTime(long creationTime) {
            this.mCreationTime = creationTime;
            return this;
        }

        public Builder setPackage(String pkg) {
            this.mPkg = pkg;
            return this;
        }

        public AutomaticZenRule build() {
            AutomaticZenRule rule =
                    new AutomaticZenRule(
                            this.mName,
                            this.mOwner,
                            this.mConfigurationActivity,
                            this.mConditionId,
                            this.mPolicy,
                            this.mInterruptionFilter,
                            this.mEnabled);
            rule.mDeviceEffects = this.mDeviceEffects;
            rule.creationTime = this.mCreationTime;
            rule.mType = this.mType;
            rule.mTriggerDescription = this.mDescription;
            rule.mIconResId = this.mIconResId;
            rule.mAllowManualInvocation = this.mAllowManualInvocation;
            rule.setPackageName(this.mPkg);
            return rule;
        }
    }
}
