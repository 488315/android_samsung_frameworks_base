package com.android.settingslib.notification.modes;

import android.app.AutomaticZenRule;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.util.Log;
import com.google.common.base.Platform;
import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ZenMode implements Parcelable {
    public static final Parcelable.Creator<ZenMode> CREATOR;
    public static final AnonymousClass1 PRIORITIZED_TYPE_COMPARATOR = null;
    public static final Comparator PRIORITIZING_COMPARATOR;
    public final String mId;
    public final Kind mKind;
    public final AutomaticZenRule mRule;
    public final Status mStatus;

    public enum Kind {
        NORMAL,
        MANUAL_DND,
        IMPLICIT
    }

    public final class Owner extends Record {
        public final ComponentName conditionProvider;
        public final ComponentName configurationActivity;
        public final String packageName;

        public Owner(String str, ComponentName componentName, ComponentName componentName2) {
            this.packageName = str;
            this.configurationActivity = componentName;
            this.conditionProvider = componentName2;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof Owner)) {
                return false;
            }
            Owner owner = (Owner) obj;
            return Objects.equals(this.packageName, owner.packageName) && Objects.equals(this.configurationActivity, owner.configurationActivity) && Objects.equals(this.conditionProvider, owner.conditionProvider);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            String str = this.packageName;
            ComponentName componentName = this.configurationActivity;
            ComponentName componentName2 = this.conditionProvider;
            return Objects.hashCode(componentName2) + ((Objects.hashCode(componentName) + (Objects.hashCode(str) * 31)) * 31);
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {this.packageName, this.configurationActivity, this.conditionProvider};
            String[] strArrSplit = "packageName;configurationActivity;conditionProvider".length() == 0 ? new String[0] : "packageName;configurationActivity;conditionProvider".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(Owner.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < strArrSplit.length; i++) {
                sb.append(strArrSplit[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != strArrSplit.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public enum Status {
        ENABLED,
        ENABLED_AND_ACTIVE,
        DISABLED_BY_USER,
        DISABLED_BY_OTHER
    }

    static {
        final int i = 0;
        final int i2 = 1;
        Comparator comparatorThenComparing = Comparator.comparing(new Function() { // from class: com.android.settingslib.notification.modes.ZenMode$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                switch (i) {
                    case 0:
                        return Boolean.valueOf(zenMode.isManualDnd());
                    case 1:
                        return Integer.valueOf(zenMode.mRule.getType());
                    default:
                        String name = zenMode.mRule.getName();
                        int i3 = Platform.$r8$clinit;
                        return name == null ? "" : name;
                }
            }
        }).reversed().thenComparing(new Function() { // from class: com.android.settingslib.notification.modes.ZenMode$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                switch (i2) {
                    case 0:
                        return Boolean.valueOf(zenMode.isManualDnd());
                    case 1:
                        return Integer.valueOf(zenMode.mRule.getType());
                    default:
                        String name = zenMode.mRule.getName();
                        int i3 = Platform.$r8$clinit;
                        return name == null ? "" : name;
                }
            }
        }, new Comparator() { // from class: com.android.settingslib.notification.modes.ZenMode.1
            public static final ImmutableList PRIORITIZED_TYPES = ImmutableList.construct(3, 4);

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                Integer num = (Integer) obj;
                Integer num2 = (Integer) obj2;
                ImmutableList immutableList = PRIORITIZED_TYPES;
                if (immutableList.contains(num) && immutableList.contains(num2)) {
                    return immutableList.indexOf(num) - immutableList.indexOf(num2);
                }
                if (immutableList.contains(num)) {
                    return -1;
                }
                return immutableList.contains(num2) ? 1 : 0;
            }
        });
        final int i3 = 2;
        PRIORITIZING_COMPARATOR = comparatorThenComparing.thenComparing(new Function() { // from class: com.android.settingslib.notification.modes.ZenMode$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                switch (i3) {
                    case 0:
                        return Boolean.valueOf(zenMode.isManualDnd());
                    case 1:
                        return Integer.valueOf(zenMode.mRule.getType());
                    default:
                        String name = zenMode.mRule.getName();
                        int i32 = Platform.$r8$clinit;
                        return name == null ? "" : name;
                }
            }
        });
        CREATOR = new Parcelable.Creator() { // from class: com.android.settingslib.notification.modes.ZenMode.2
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                String string = parcel.readString();
                AutomaticZenRule automaticZenRule = (AutomaticZenRule) parcel.readParcelable(AutomaticZenRule.class.getClassLoader(), AutomaticZenRule.class);
                automaticZenRule.getClass();
                return new ZenMode(string, automaticZenRule, Kind.valueOf(parcel.readString()), Status.valueOf(parcel.readString()), 0);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i4) {
                return new ZenMode[i4];
            }
        };
    }

    public /* synthetic */ ZenMode(String str, AutomaticZenRule automaticZenRule, Kind kind, Status status, int i) {
        this(str, automaticZenRule, kind, status);
    }

    public static ZenMode manualDndMode(AutomaticZenRule automaticZenRule, boolean z) {
        return new ZenMode("MANUAL_RULE", automaticZenRule, Kind.MANUAL_DND, z ? Status.ENABLED_AND_ACTIVE : Status.ENABLED);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ZenMode)) {
            return false;
        }
        ZenMode zenMode = (ZenMode) obj;
        return this.mId.equals(zenMode.mId) && this.mRule.equals(zenMode.mRule) && this.mKind.equals(zenMode.mKind) && this.mStatus.equals(zenMode.mStatus);
    }

    public final String getOwnerPackage() {
        return new Owner(this.mRule.getPackageName(), this.mRule.getConfigurationActivity(), this.mRule.getOwner()).packageName;
    }

    public final ZenPolicy getPolicy() {
        int interruptionFilter = this.mRule.getInterruptionFilter();
        if (interruptionFilter == 1 || interruptionFilter == 2) {
            ZenPolicy zenPolicy = this.mRule.getZenPolicy();
            Objects.requireNonNull(zenPolicy);
            return zenPolicy;
        }
        if (interruptionFilter == 3) {
            return new ZenPolicy.Builder(ZenModeConfig.getDefaultZenPolicy()).build().overwrittenWith(ZenPolicy.getBasePolicyInterruptionFilterNone());
        }
        if (interruptionFilter == 4) {
            return new ZenPolicy.Builder(ZenModeConfig.getDefaultZenPolicy()).build().overwrittenWith(ZenPolicy.getBasePolicyInterruptionFilterAlarms());
        }
        Log.wtf("ZenMode", "Rule " + this.mId + " with unexpected interruptionFilter " + this.mRule.getInterruptionFilter());
        ZenPolicy zenPolicy2 = this.mRule.getZenPolicy();
        Objects.requireNonNull(zenPolicy2);
        return zenPolicy2;
    }

    public final int hashCode() {
        return Objects.hash(this.mId, this.mRule, this.mKind, this.mStatus);
    }

    public final boolean isActive() {
        return this.mStatus == Status.ENABLED_AND_ACTIVE;
    }

    public final boolean isManualDnd() {
        return this.mKind == Kind.MANUAL_DND;
    }

    public final String toString() {
        return this.mId + " (" + this.mKind + ", " + this.mStatus + ") -> " + this.mRule;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeParcelable(this.mRule, 0);
        parcel.writeString(this.mKind.name());
        parcel.writeString(this.mStatus.name());
    }

    public ZenMode(String str, AutomaticZenRule automaticZenRule, ZenModeConfig.ZenRule zenRule) {
        this(str, automaticZenRule, ZenModeConfig.isImplicitRuleId(str) ? Kind.IMPLICIT : Kind.NORMAL, zenRule.enabled ? zenRule.isActive() ? Status.ENABLED_AND_ACTIVE : Status.ENABLED : zenRule.disabledOrigin == 3 ? Status.DISABLED_BY_USER : Status.DISABLED_BY_OTHER);
    }

    private ZenMode(String str, AutomaticZenRule automaticZenRule, Kind kind, Status status) {
        this.mId = str;
        this.mRule = automaticZenRule;
        this.mKind = kind;
        this.mStatus = status;
    }
}
