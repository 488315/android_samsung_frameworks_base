package android.service.euicc;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class GetEuiccProfileInfoListResult implements Parcelable {
    public static final Parcelable.Creator<GetEuiccProfileInfoListResult> CREATOR = new Parcelable.Creator<GetEuiccProfileInfoListResult>() { // from class: android.service.euicc.GetEuiccProfileInfoListResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetEuiccProfileInfoListResult createFromParcel(Parcel parcel) {
            return new GetEuiccProfileInfoListResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetEuiccProfileInfoListResult[] newArray(int i) {
            return new GetEuiccProfileInfoListResult[i];
        }
    };
    private final boolean mIsRemovable;
    private final EuiccProfileInfo[] mProfiles;

    @Deprecated
    public final int result;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getResult() {
        return this.result;
    }

    public List<EuiccProfileInfo> getProfiles() {
        EuiccProfileInfo[] euiccProfileInfoArr = this.mProfiles;
        if (euiccProfileInfoArr == null) {
            return null;
        }
        return Arrays.asList(euiccProfileInfoArr);
    }

    public boolean getIsRemovable() {
        return this.mIsRemovable;
    }

    public GetEuiccProfileInfoListResult(int i, EuiccProfileInfo[] euiccProfileInfoArr, boolean z) {
        this.result = i;
        this.mIsRemovable = z;
        if (i == 0) {
            this.mProfiles = euiccProfileInfoArr;
        } else {
            if (euiccProfileInfoArr != null && euiccProfileInfoArr.length > 0) {
                throw new IllegalArgumentException("Error result with non-empty profiles: " + i);
            }
            this.mProfiles = null;
        }
    }

    private GetEuiccProfileInfoListResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.mProfiles = (EuiccProfileInfo[]) parcel.createTypedArray(EuiccProfileInfo.CREATOR);
        this.mIsRemovable = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeTypedArray(this.mProfiles, i);
        parcel.writeBoolean(this.mIsRemovable);
    }

    public String toString() {
        return "[GetEuiccProfileInfoListResult: result=" + EuiccService.resultToString(this.result) + ", isRemovable=" + this.mIsRemovable + ", mProfiles=" + Arrays.toString(this.mProfiles) + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
