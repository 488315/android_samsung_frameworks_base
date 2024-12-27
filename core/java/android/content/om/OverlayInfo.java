package android.content.om;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class OverlayInfo implements CriticalOverlayInfo, Parcelable {
    public static final String CATEGORY_THEME = "android.theme";
    public static final Parcelable.Creator<OverlayInfo> CREATOR =
            new Parcelable.Creator<OverlayInfo>() { // from class: android.content.om.OverlayInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OverlayInfo createFromParcel(Parcel source) {
                    return new OverlayInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OverlayInfo[] newArray(int size) {
                    return new OverlayInfo[size];
                }
            };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 3;

    @Deprecated public static final int STATE_ENABLED_IMMUTABLE = 6;
    public static final int STATE_MISSING_TARGET = 0;
    public static final int STATE_NO_IDMAP = 1;
    public static final int STATE_OVERLAY_IS_BEING_REPLACED = 5;
    public static final int STATE_SYSTEM_UPDATE_UNINSTALL = 7;

    @Deprecated public static final int STATE_TARGET_IS_BEING_REPLACED = 4;
    public static final int STATE_UNKNOWN = -1;
    public final String baseCodePath;
    public final String category;
    public final boolean isFabricated;
    public final boolean isMutable;
    private OverlayIdentifier mIdentifierCached;
    public final String overlayName;
    public final String packageName;
    public final int priority;
    public final int state;
    public final String targetOverlayableName;
    public final String targetPackageName;
    public final int userId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    public OverlayInfo(OverlayInfo source, int state) {
        this(
                source.packageName,
                source.overlayName,
                source.targetPackageName,
                source.targetOverlayableName,
                source.category,
                source.baseCodePath,
                state,
                source.userId,
                source.priority,
                source.isMutable,
                source.isFabricated);
    }

    public OverlayInfo(
            String packageName,
            String targetPackageName,
            String targetOverlayableName,
            String category,
            String baseCodePath,
            int state,
            int userId,
            int priority,
            boolean isMutable) {
        this(
                packageName,
                null,
                targetPackageName,
                targetOverlayableName,
                category,
                baseCodePath,
                state,
                userId,
                priority,
                isMutable,
                false);
    }

    public OverlayInfo(
            String packageName,
            String overlayName,
            String targetPackageName,
            String targetOverlayableName,
            String category,
            String baseCodePath,
            int state,
            int userId,
            int priority,
            boolean isMutable,
            boolean isFabricated) {
        this.packageName = packageName;
        this.overlayName = overlayName;
        this.targetPackageName = targetPackageName;
        this.targetOverlayableName = targetOverlayableName;
        this.category = category;
        this.baseCodePath = baseCodePath;
        this.state = state;
        this.userId = userId;
        this.priority = priority;
        this.isMutable = isMutable;
        this.isFabricated = isFabricated;
        ensureValidState();
    }

    public OverlayInfo(Parcel source) {
        this.packageName = source.readString();
        this.overlayName = source.readString();
        this.targetPackageName = source.readString();
        this.targetOverlayableName = source.readString();
        this.category = source.readString();
        this.baseCodePath = source.readString();
        this.state = source.readInt();
        this.userId = source.readInt();
        this.priority = source.readInt();
        this.isMutable = source.readBoolean();
        this.isFabricated = source.readBoolean();
        ensureValidState();
    }

    @Override // android.content.om.CriticalOverlayInfo
    @SystemApi
    public String getPackageName() {
        return this.packageName;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public String getOverlayName() {
        return this.overlayName;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public String getTargetPackageName() {
        return this.targetPackageName;
    }

    @SystemApi
    public String getCategory() {
        return this.category;
    }

    @SystemApi
    public int getUserId() {
        return this.userId;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public String getTargetOverlayableName() {
        return this.targetOverlayableName;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public boolean isFabricated() {
        return this.isFabricated;
    }

    public String getBaseCodePath() {
        return this.baseCodePath;
    }

    @Override // android.content.om.CriticalOverlayInfo
    public OverlayIdentifier getOverlayIdentifier() {
        if (this.mIdentifierCached == null) {
            this.mIdentifierCached = new OverlayIdentifier(this.packageName, this.overlayName);
        }
        return this.mIdentifierCached;
    }

    private void ensureValidState() {
        if (this.packageName == null) {
            throw new IllegalArgumentException("packageName must not be null");
        }
        if (this.targetPackageName == null) {
            throw new IllegalArgumentException("targetPackageName must not be null");
        }
        if (this.baseCodePath == null) {
            throw new IllegalArgumentException("baseCodePath must not be null");
        }
        switch (this.state) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return;
            default:
                throw new IllegalArgumentException("State " + this.state + " is not a valid state");
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.overlayName);
        dest.writeString(this.targetPackageName);
        dest.writeString(this.targetOverlayableName);
        dest.writeString(this.category);
        dest.writeString(this.baseCodePath);
        dest.writeInt(this.state);
        dest.writeInt(this.userId);
        dest.writeInt(this.priority);
        dest.writeBoolean(this.isMutable);
        dest.writeBoolean(this.isFabricated);
    }

    @SystemApi
    public boolean isEnabled() {
        switch (this.state) {
            case 3:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public static String stateToString(int state) {
        switch (state) {
            case -1:
                return "STATE_UNKNOWN";
            case 0:
                return "STATE_MISSING_TARGET";
            case 1:
                return "STATE_NO_IDMAP";
            case 2:
                return "STATE_DISABLED";
            case 3:
                return "STATE_ENABLED";
            case 4:
                return "STATE_TARGET_IS_BEING_REPLACED";
            case 5:
                return "STATE_OVERLAY_IS_BEING_REPLACED";
            case 6:
                return "STATE_ENABLED_IMMUTABLE";
            case 7:
                return "STATE_SYSTEM_UPDATE_UNINSTALL";
            default:
                return "<unknown state>";
        }
    }

    public int hashCode() {
        int result = (1 * 31) + this.userId;
        return (((((((((((((result * 31) + this.state) * 31)
                                                                                                + (this
                                                                                                                        .packageName
                                                                                                                == null
                                                                                                        ? 0
                                                                                                        : this
                                                                                                                .packageName
                                                                                                                .hashCode()))
                                                                                        * 31)
                                                                                + (this.overlayName
                                                                                                == null
                                                                                        ? 0
                                                                                        : this
                                                                                                .overlayName
                                                                                                .hashCode()))
                                                                        * 31)
                                                                + (this.targetPackageName == null
                                                                        ? 0
                                                                        : this.targetPackageName
                                                                                .hashCode()))
                                                        * 31)
                                                + (this.targetOverlayableName == null
                                                        ? 0
                                                        : this.targetOverlayableName.hashCode()))
                                        * 31)
                                + (this.category == null ? 0 : this.category.hashCode()))
                        * 31)
                + (this.baseCodePath != null ? this.baseCodePath.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OverlayInfo other = (OverlayInfo) obj;
        if (this.userId == other.userId
                && this.state == other.state
                && this.packageName.equals(other.packageName)
                && Objects.equals(this.overlayName, other.overlayName)
                && this.targetPackageName.equals(other.targetPackageName)
                && Objects.equals(this.targetOverlayableName, other.targetOverlayableName)
                && Objects.equals(this.category, other.category)
                && this.baseCodePath.equals(other.baseCodePath)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "OverlayInfo {packageName="
                + this.packageName
                + ", overlayName="
                + this.overlayName
                + ", targetPackage="
                + this.targetPackageName
                + ", targetOverlayable="
                + this.targetOverlayableName
                + ", category="
                + this.category
                + ", state="
                + this.state
                + " ("
                + stateToString(this.state)
                + "),, userId="
                + this.userId
                + " }";
    }
}
