package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class UiccCardInfo implements Parcelable {
    public static final Parcelable.Creator<UiccCardInfo> CREATOR = new Parcelable.Creator<UiccCardInfo>() { // from class: android.telephony.UiccCardInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccCardInfo createFromParcel(Parcel parcel) {
            return new UiccCardInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccCardInfo[] newArray(int i) {
            return new UiccCardInfo[i];
        }
    };
    private final int mCardId;
    private final String mEid;
    private final String mIccId;
    private boolean mIccIdAccessRestricted;
    private final boolean mIsEuicc;
    private final boolean mIsMultipleEnabledProfilesSupported;
    private final boolean mIsRemovable;
    private final int mPhysicalSlotIndex;
    private final List<UiccPortInfo> mPortList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UiccCardInfo(Parcel parcel) {
        this.mIccIdAccessRestricted = false;
        this.mIsEuicc = parcel.readBoolean();
        this.mCardId = parcel.readInt();
        this.mEid = parcel.readString8();
        this.mIccId = parcel.readString8();
        this.mPhysicalSlotIndex = parcel.readInt();
        this.mIsRemovable = parcel.readBoolean();
        this.mIsMultipleEnabledProfilesSupported = parcel.readBoolean();
        ArrayList arrayList = new ArrayList();
        this.mPortList = arrayList;
        parcel.readTypedList(arrayList, UiccPortInfo.CREATOR);
        this.mIccIdAccessRestricted = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEuicc);
        parcel.writeInt(this.mCardId);
        parcel.writeString8(this.mEid);
        parcel.writeString8(this.mIccId);
        parcel.writeInt(this.mPhysicalSlotIndex);
        parcel.writeBoolean(this.mIsRemovable);
        parcel.writeBoolean(this.mIsMultipleEnabledProfilesSupported);
        parcel.writeTypedList(this.mPortList, i);
        parcel.writeBoolean(this.mIccIdAccessRestricted);
    }

    public UiccCardInfo(boolean z, int i, String str, int i2, boolean z2, boolean z3, List<UiccPortInfo> list) {
        this.mIccIdAccessRestricted = false;
        this.mIsEuicc = z;
        this.mCardId = i;
        this.mEid = str;
        this.mIccId = null;
        this.mPhysicalSlotIndex = i2;
        this.mIsRemovable = z2;
        this.mIsMultipleEnabledProfilesSupported = z3;
        this.mPortList = list;
    }

    public boolean isEuicc() {
        return this.mIsEuicc;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public String getEid() {
        if (this.mIsEuicc) {
            return this.mEid;
        }
        return null;
    }

    @Deprecated
    public String getIccId() {
        if (this.mIccIdAccessRestricted) {
            throw new UnsupportedOperationException("getIccId() is not supported by UiccCardInfo. Please Use UiccPortInfo API instead");
        }
        if (this.mPortList.isEmpty()) {
            return null;
        }
        return this.mPortList.get(0).getIccId();
    }

    @Deprecated
    public int getSlotIndex() {
        return this.mPhysicalSlotIndex;
    }

    public int getPhysicalSlotIndex() {
        return this.mPhysicalSlotIndex;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public boolean isMultipleEnabledProfilesSupported() {
        return this.mIsMultipleEnabledProfilesSupported;
    }

    public Collection<UiccPortInfo> getPorts() {
        return Collections.unmodifiableList(this.mPortList);
    }

    public void setIccIdAccessRestricted(boolean z) {
        this.mIccIdAccessRestricted = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UiccCardInfo uiccCardInfo = (UiccCardInfo) obj;
            if (this.mIsEuicc == uiccCardInfo.mIsEuicc && this.mCardId == uiccCardInfo.mCardId && Objects.equals(this.mEid, uiccCardInfo.mEid) && Objects.equals(this.mIccId, uiccCardInfo.mIccId) && this.mPhysicalSlotIndex == uiccCardInfo.mPhysicalSlotIndex && this.mIsRemovable == uiccCardInfo.mIsRemovable && this.mIsMultipleEnabledProfilesSupported == uiccCardInfo.mIsMultipleEnabledProfilesSupported && Objects.equals(this.mPortList, uiccCardInfo.mPortList)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsEuicc), Integer.valueOf(this.mCardId), this.mEid, this.mIccId, Integer.valueOf(this.mPhysicalSlotIndex), Boolean.valueOf(this.mIsRemovable), Boolean.valueOf(this.mIsMultipleEnabledProfilesSupported), this.mPortList);
    }

    public String toString() {
        return "UiccCardInfo (mIsEuicc=" + this.mIsEuicc + ", mCardId=" + this.mCardId + ", mEid=" + com.android.telephony.Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, this.mEid) + ", mPhysicalSlotIndex=" + this.mPhysicalSlotIndex + ", mIsRemovable=" + this.mIsRemovable + ", mIsMultipleEnabledProfilesSupported=" + this.mIsMultipleEnabledProfilesSupported + ", mPortList=" + this.mPortList + ", mIccIdAccessRestricted=" + this.mIccIdAccessRestricted + NavigationBarInflaterView.KEY_CODE_END;
    }
}
