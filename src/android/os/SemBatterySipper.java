package android.os;

import android.hardware.scontext.SContextConstants;
import android.os.Parcelable;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SemBatterySipper implements Parcelable {
    public static final Parcelable.Creator<SemBatterySipper> CREATOR = new Parcelable.Creator<SemBatterySipper>() { // from class: android.os.SemBatterySipper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatterySipper createFromParcel(Parcel parcel) {
            return new SemBatterySipper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatterySipper[] newArray(int i) {
            return new SemBatterySipper[i];
        }
    };
    public SemDevicePowerInfo mDevPowerInfo;
    public List<SemKernelWakelockInfo> mKernelWakelockInfoList;
    public List<SemScreenWakeInfo> mScreenWakeInfoList;
    public SparseArray<SemUidPowerInfo> mUidPowerInfoList;
    public List<SemWakeupReasonInfo> mWakeupReasonInfoList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemBatterySipper() {
        this.mDevPowerInfo = new SemDevicePowerInfo(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        this.mUidPowerInfoList = new SparseArray<>();
        this.mWakeupReasonInfoList = new ArrayList();
        this.mKernelWakelockInfoList = new ArrayList();
        this.mScreenWakeInfoList = new ArrayList();
    }

    public SemBatterySipper(int[][] iArr) {
        this.mDevPowerInfo = new SemDevicePowerInfo(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, iArr);
        this.mUidPowerInfoList = new SparseArray<>();
        this.mWakeupReasonInfoList = new ArrayList();
        this.mKernelWakelockInfoList = new ArrayList();
        this.mScreenWakeInfoList = new ArrayList();
    }

    public SemBatterySipper(SemDevicePowerInfo semDevicePowerInfo, SparseArray<SemUidPowerInfo> sparseArray, List<SemWakeupReasonInfo> list, List<SemKernelWakelockInfo> list2, List<SemScreenWakeInfo> list3) {
        this.mDevPowerInfo = semDevicePowerInfo;
        this.mUidPowerInfoList = sparseArray;
        this.mWakeupReasonInfoList = list;
        this.mKernelWakelockInfoList = list2;
        this.mScreenWakeInfoList = list3;
    }

    protected SemBatterySipper(Parcel parcel) {
        this.mDevPowerInfo = new SemDevicePowerInfo(parcel);
        this.mUidPowerInfoList = parcel.createTypedSparseArray(SemUidPowerInfo.CREATOR);
        this.mWakeupReasonInfoList = parcel.createTypedArrayList(SemWakeupReasonInfo.CREATOR);
        this.mKernelWakelockInfoList = parcel.createTypedArrayList(SemKernelWakelockInfo.CREATOR);
        this.mScreenWakeInfoList = parcel.createTypedArrayList(SemScreenWakeInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mDevPowerInfo.writeToParcel(parcel, i);
        parcel.writeTypedSparseArray(this.mUidPowerInfoList, i);
        parcel.writeTypedList(this.mWakeupReasonInfoList);
        parcel.writeTypedList(this.mKernelWakelockInfoList);
        parcel.writeTypedList(this.mScreenWakeInfoList);
    }
}
