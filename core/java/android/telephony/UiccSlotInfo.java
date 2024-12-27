package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public class UiccSlotInfo implements Parcelable {
    public static final int CARD_STATE_INFO_ABSENT = 1;
    public static final int CARD_STATE_INFO_ERROR = 3;
    public static final int CARD_STATE_INFO_PRESENT = 2;
    public static final int CARD_STATE_INFO_RESTRICTED = 4;
    public static final Parcelable.Creator<UiccSlotInfo> CREATOR = new Parcelable.Creator<UiccSlotInfo>() { // from class: android.telephony.UiccSlotInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccSlotInfo createFromParcel(Parcel in) {
            return new UiccSlotInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccSlotInfo[] newArray(int size) {
            return new UiccSlotInfo[size];
        }
    };
    private final String mCardId;
    private final int mCardStateInfo;
    private final boolean mIsActive;
    private final boolean mIsEuicc;
    private final boolean mIsExtendedApduSupported;
    private final boolean mIsRemovable;
    private boolean mLogicalSlotAccessRestricted;
    private final int mLogicalSlotIdx;
    private final List<UiccPortInfo> mPortList;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CardStateInfo {
    }

    private UiccSlotInfo(Parcel in) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsActive = in.readBoolean();
        this.mIsEuicc = in.readBoolean();
        this.mCardId = in.readString8();
        this.mCardStateInfo = in.readInt();
        this.mLogicalSlotIdx = in.readInt();
        this.mIsExtendedApduSupported = in.readBoolean();
        this.mIsRemovable = in.readBoolean();
        this.mPortList = new ArrayList();
        in.readTypedList(this.mPortList, UiccPortInfo.CREATOR);
        this.mLogicalSlotAccessRestricted = in.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mIsActive);
        dest.writeBoolean(this.mIsEuicc);
        dest.writeString8(this.mCardId);
        dest.writeInt(this.mCardStateInfo);
        dest.writeInt(this.mLogicalSlotIdx);
        dest.writeBoolean(this.mIsExtendedApduSupported);
        dest.writeBoolean(this.mIsRemovable);
        dest.writeTypedList(this.mPortList, flags);
        dest.writeBoolean(this.mLogicalSlotAccessRestricted);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public UiccSlotInfo(boolean isActive, boolean isEuicc, String cardId, int cardStateInfo, int logicalSlotIdx, boolean isExtendedApduSupported) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsActive = isActive;
        this.mIsEuicc = isEuicc;
        this.mCardId = cardId;
        this.mCardStateInfo = cardStateInfo;
        this.mLogicalSlotIdx = logicalSlotIdx;
        this.mIsExtendedApduSupported = isExtendedApduSupported;
        this.mIsRemovable = false;
        this.mPortList = new ArrayList();
    }

    public UiccSlotInfo(boolean isEuicc, String cardId, int cardStateInfo, boolean isExtendedApduSupported, boolean isRemovable, List<UiccPortInfo> portList) {
        int logicalSlotIndex;
        this.mLogicalSlotAccessRestricted = false;
        this.mIsEuicc = isEuicc;
        this.mCardId = cardId;
        this.mCardStateInfo = cardStateInfo;
        this.mIsExtendedApduSupported = isExtendedApduSupported;
        this.mIsRemovable = isRemovable;
        this.mPortList = portList;
        this.mIsActive = !portList.isEmpty() && portList.get(0).isActive();
        if (portList.isEmpty()) {
            logicalSlotIndex = -1;
        } else {
            logicalSlotIndex = portList.get(0).getLogicalSlotIndex();
        }
        this.mLogicalSlotIdx = logicalSlotIndex;
    }

    @Deprecated
    public boolean getIsActive() {
        if (this.mLogicalSlotAccessRestricted) {
            throw new UnsupportedOperationException("getIsActive() is not supported by UiccSlotInfo. Please Use UiccPortInfo API instead");
        }
        return this.mIsActive;
    }

    public boolean getIsEuicc() {
        return this.mIsEuicc;
    }

    public String getCardId() {
        return this.mCardId;
    }

    public int getCardStateInfo() {
        return this.mCardStateInfo;
    }

    @Deprecated
    public int getLogicalSlotIdx() {
        if (this.mLogicalSlotAccessRestricted) {
            throw new UnsupportedOperationException("getLogicalSlotIdx() is not supported by UiccSlotInfo. Please use UiccPortInfo API instead");
        }
        return this.mLogicalSlotIdx;
    }

    public boolean getIsExtendedApduSupported() {
        return this.mIsExtendedApduSupported;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public Collection<UiccPortInfo> getPorts() {
        return Collections.unmodifiableList(this.mPortList);
    }

    public void setLogicalSlotAccessRestricted(boolean logicalSlotAccessRestricted) {
        this.mLogicalSlotAccessRestricted = logicalSlotAccessRestricted;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UiccSlotInfo that = (UiccSlotInfo) obj;
        if (this.mIsActive == that.mIsActive && this.mIsEuicc == that.mIsEuicc && Objects.equals(this.mCardId, that.mCardId) && this.mCardStateInfo == that.mCardStateInfo && this.mLogicalSlotIdx == that.mLogicalSlotIdx && this.mIsExtendedApduSupported == that.mIsExtendedApduSupported && this.mIsRemovable == that.mIsRemovable && Objects.equals(this.mPortList, that.mPortList)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsActive), Boolean.valueOf(this.mIsEuicc), this.mCardId, Integer.valueOf(this.mCardStateInfo), Integer.valueOf(this.mLogicalSlotIdx), Boolean.valueOf(this.mIsExtendedApduSupported), Boolean.valueOf(this.mIsRemovable), this.mPortList);
    }

    public String toString() {
        return "UiccSlotInfo (, mIsEuicc=" + this.mIsEuicc + ", mCardId=" + SubscriptionInfo.getPrintableId(this.mCardId) + ", cardState=" + this.mCardStateInfo + ", mIsExtendedApduSupported=" + this.mIsExtendedApduSupported + ", mIsRemovable=" + this.mIsRemovable + ", mPortList=" + this.mPortList + ", mLogicalSlotAccessRestricted=" + this.mLogicalSlotAccessRestricted + NavigationBarInflaterView.KEY_CODE_END;
    }
}
