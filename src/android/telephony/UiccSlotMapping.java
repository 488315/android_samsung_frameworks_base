package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class UiccSlotMapping implements Parcelable {
    public static final Parcelable.Creator<UiccSlotMapping> CREATOR = new Parcelable.Creator<UiccSlotMapping>() { // from class: android.telephony.UiccSlotMapping.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccSlotMapping createFromParcel(Parcel parcel) {
            return new UiccSlotMapping(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccSlotMapping[] newArray(int i) {
            return new UiccSlotMapping[i];
        }
    };
    private final int mLogicalSlotIndex;
    private final int mPhysicalSlotIndex;
    private final int mPortIndex;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UiccSlotMapping(Parcel parcel) {
        this.mPortIndex = parcel.readInt();
        this.mPhysicalSlotIndex = parcel.readInt();
        this.mLogicalSlotIndex = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPortIndex);
        parcel.writeInt(this.mPhysicalSlotIndex);
        parcel.writeInt(this.mLogicalSlotIndex);
    }

    public UiccSlotMapping(int i, int i2, int i3) {
        this.mPortIndex = i;
        this.mPhysicalSlotIndex = i2;
        this.mLogicalSlotIndex = i3;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    public int getPhysicalSlotIndex() {
        return this.mPhysicalSlotIndex;
    }

    public int getLogicalSlotIndex() {
        return this.mLogicalSlotIndex;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UiccSlotMapping uiccSlotMapping = (UiccSlotMapping) obj;
            if (this.mPortIndex == uiccSlotMapping.mPortIndex && this.mPhysicalSlotIndex == uiccSlotMapping.mPhysicalSlotIndex && this.mLogicalSlotIndex == uiccSlotMapping.mLogicalSlotIndex) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPortIndex), Integer.valueOf(this.mPhysicalSlotIndex), Integer.valueOf(this.mLogicalSlotIndex));
    }

    public String toString() {
        return "UiccSlotMapping (mPortIndex=" + this.mPortIndex + ", mPhysicalSlotIndex=" + this.mPhysicalSlotIndex + ", mLogicalSlotIndex=" + this.mLogicalSlotIndex + NavigationBarInflaterView.KEY_CODE_END;
    }
}
