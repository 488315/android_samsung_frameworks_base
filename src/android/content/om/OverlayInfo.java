package android.content.om;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class OverlayInfo implements CriticalOverlayInfo, Parcelable {
    public static final String CATEGORY_THEME = "android.theme";
    public static final Parcelable.Creator<OverlayInfo> CREATOR = new Parcelable.Creator<OverlayInfo>() { // from class: android.content.om.OverlayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayInfo createFromParcel(Parcel parcel) {
            return new OverlayInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayInfo[] newArray(int i) {
            return new OverlayInfo[i];
        }
    };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 3;

    @Deprecated
    public static final int STATE_ENABLED_IMMUTABLE = 6;
    public static final int STATE_MISSING_TARGET = 0;
    public static final int STATE_NO_IDMAP = 1;
    public static final int STATE_OVERLAY_IS_BEING_REPLACED = 5;
    public static final int STATE_SYSTEM_UPDATE_UNINSTALL = 7;

    @Deprecated
    public static final int STATE_TARGET_IS_BEING_REPLACED = 4;
    public static final int STATE_UNKNOWN = -1;
    public final String baseCodePath;
    public final String category;
    public final List<OverlayConstraint> constraints;
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
    public @interface State {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OverlayInfo(OverlayInfo overlayInfo, int i) {
        this(overlayInfo.packageName, overlayInfo.overlayName, overlayInfo.targetPackageName, overlayInfo.targetOverlayableName, overlayInfo.category, overlayInfo.baseCodePath, i, overlayInfo.userId, overlayInfo.priority, overlayInfo.isMutable, overlayInfo.isFabricated, overlayInfo.constraints);
    }

    public OverlayInfo(String str, String str2, String str3, String str4, String str5, int i, int i2, int i3, boolean z) {
        this(str, null, str2, str3, str4, str5, i, i2, i3, z, false);
    }

    public OverlayInfo(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, int i3, boolean z, boolean z2) {
        this(str, str2, str3, str4, str5, str6, i, i2, i3, z, z2, Collections.EMPTY_LIST);
    }

    public OverlayInfo(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, int i3, boolean z, boolean z2, List<OverlayConstraint> list) {
        this.packageName = str;
        this.overlayName = str2;
        this.targetPackageName = str3;
        this.targetOverlayableName = str4;
        this.category = str5;
        this.baseCodePath = str6;
        this.state = i;
        this.userId = i2;
        this.priority = i3;
        this.isMutable = z;
        this.isFabricated = z2;
        this.constraints = list;
        ensureValidState();
    }

    public OverlayInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.overlayName = parcel.readString();
        this.targetPackageName = parcel.readString();
        this.targetOverlayableName = parcel.readString();
        this.category = parcel.readString();
        this.baseCodePath = parcel.readString();
        this.state = parcel.readInt();
        this.userId = parcel.readInt();
        this.priority = parcel.readInt();
        this.isMutable = parcel.readBoolean();
        this.isFabricated = parcel.readBoolean();
        this.constraints = Arrays.asList((OverlayConstraint[]) parcel.createTypedArray(OverlayConstraint.CREATOR));
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

    public List<OverlayConstraint> getConstraints() {
        return this.constraints;
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
        if (this.constraints == null) {
            throw new IllegalArgumentException("constraints must not be null");
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.overlayName);
        parcel.writeString(this.targetPackageName);
        parcel.writeString(this.targetOverlayableName);
        parcel.writeString(this.category);
        parcel.writeString(this.baseCodePath);
        parcel.writeInt(this.state);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.priority);
        parcel.writeBoolean(this.isMutable);
        parcel.writeBoolean(this.isFabricated);
        parcel.writeTypedArray((OverlayConstraint[]) this.constraints.toArray(new OverlayConstraint[0]), i);
    }

    @SystemApi
    public boolean isEnabled() {
        int i = this.state;
        return i == 3 || i == 6;
    }

    public static String stateToString(int i) {
        switch (i) {
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
        int i = (((this.userId + 31) * 31) + this.state) * 31;
        String str = this.packageName;
        int hashCode = (i + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.overlayName;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.targetPackageName;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.targetOverlayableName;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.category;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.baseCodePath;
        return ((hashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31) + (this.constraints.isEmpty() ? 0 : this.constraints.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OverlayInfo overlayInfo = (OverlayInfo) obj;
        if (this.userId == overlayInfo.userId && this.state == overlayInfo.state && this.packageName.equals(overlayInfo.packageName) && Objects.equals(this.overlayName, overlayInfo.overlayName) && this.targetPackageName.equals(overlayInfo.targetPackageName) && Objects.equals(this.targetOverlayableName, overlayInfo.targetOverlayableName) && Objects.equals(this.category, overlayInfo.category) && this.baseCodePath.equals(overlayInfo.baseCodePath)) {
            return Objects.equals(this.constraints, overlayInfo.constraints);
        }
        return false;
    }

    public String toString() {
        return "OverlayInfo {packageName=" + this.packageName + ", overlayName=" + this.overlayName + ", targetPackage=" + this.targetPackageName + ", targetOverlayable=" + this.targetOverlayableName + ", category=" + this.category + ", state=" + this.state + " (" + stateToString(this.state) + "),, userId=" + this.userId + ", constraints=" + OverlayConstraint.constraintsToString(this.constraints) + " }";
    }
}
