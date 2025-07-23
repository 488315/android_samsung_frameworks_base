package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class CellBroadcastIdRange implements Parcelable {
    public static final Parcelable.Creator<CellBroadcastIdRange> CREATOR = new Parcelable.Creator<CellBroadcastIdRange>() { // from class: android.telephony.CellBroadcastIdRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellBroadcastIdRange createFromParcel(Parcel parcel) {
            return new CellBroadcastIdRange(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellBroadcastIdRange[] newArray(int i) {
            return new CellBroadcastIdRange[i];
        }
    };
    private int mEndId;
    private boolean mIsEnabled;
    private int mStartId;
    private int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellBroadcastIdRange(int i, int i2, int i3, boolean z) throws IllegalArgumentException {
        if (i < 0 || i2 < 0 || i > 65535 || i2 > 65535) {
            throw new IllegalArgumentException("invalid id");
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endId must be greater than or equal to startId");
        }
        this.mStartId = i;
        this.mEndId = i2;
        this.mType = i3;
        this.mIsEnabled = z;
    }

    public int getStartId() {
        return this.mStartId;
    }

    public int getEndId() {
        return this.mEndId;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStartId);
        parcel.writeInt(this.mEndId);
        parcel.writeInt(this.mType);
        parcel.writeBoolean(this.mIsEnabled);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStartId), Integer.valueOf(this.mEndId), Integer.valueOf(this.mType), Boolean.valueOf(this.mIsEnabled));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CellBroadcastIdRange)) {
            return false;
        }
        CellBroadcastIdRange cellBroadcastIdRange = (CellBroadcastIdRange) obj;
        return this.mStartId == cellBroadcastIdRange.mStartId && this.mEndId == cellBroadcastIdRange.mEndId && this.mType == cellBroadcastIdRange.mType && this.mIsEnabled == cellBroadcastIdRange.mIsEnabled;
    }

    public String toString() {
        return "CellBroadcastIdRange[" + this.mStartId + ", " + this.mEndId + ", " + this.mType + ", " + this.mIsEnabled + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
