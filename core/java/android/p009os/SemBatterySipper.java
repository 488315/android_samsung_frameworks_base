package android.p009os;

import android.hardware.scontext.SContextConstants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SemBatterySipper implements Parcelable {
  public static final Parcelable.Creator<SemBatterySipper> CREATOR =
      new Parcelable.Creator<SemBatterySipper>() { // from class: android.os.SemBatterySipper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatterySipper createFromParcel(Parcel in) {
          return new SemBatterySipper(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatterySipper[] newArray(int size) {
          return new SemBatterySipper[size];
        }
      };
  public SemDevicePowerInfo mDevPowerInfo;
  public List<SemKernelWakelockInfo> mKernelWakelockInfoList;
  public List<SemScreenWakeInfo> mScreenWakeInfoList;
  public List<SemUidPowerInfo> mUidPowerInfoList;
  public List<SemWakeupReasonInfo> mWakeupReasonInfoList;

  public SemBatterySipper() {
    this.mDevPowerInfo = new SemDevicePowerInfo(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    this.mUidPowerInfoList = new ArrayList();
    this.mWakeupReasonInfoList = new ArrayList();
    this.mKernelWakelockInfoList = new ArrayList();
    this.mScreenWakeInfoList = new ArrayList();
  }

  public SemBatterySipper(
      SemDevicePowerInfo devPowerInfo,
      List<SemUidPowerInfo> uidPowerInfoList,
      List<SemWakeupReasonInfo> wakeupReasonInfoList,
      List<SemKernelWakelockInfo> kernelWakelockInfoList,
      List<SemScreenWakeInfo> screenWakeInfoList) {
    this.mDevPowerInfo = devPowerInfo;
    this.mUidPowerInfoList = uidPowerInfoList;
    this.mWakeupReasonInfoList = wakeupReasonInfoList;
    this.mKernelWakelockInfoList = kernelWakelockInfoList;
    this.mScreenWakeInfoList = screenWakeInfoList;
  }

  protected SemBatterySipper(Parcel in) {
    this.mDevPowerInfo = new SemDevicePowerInfo(in);
    this.mUidPowerInfoList = in.createTypedArrayList(SemUidPowerInfo.CREATOR);
    this.mWakeupReasonInfoList = in.createTypedArrayList(SemWakeupReasonInfo.CREATOR);
    this.mKernelWakelockInfoList = in.createTypedArrayList(SemKernelWakelockInfo.CREATOR);
    this.mScreenWakeInfoList = in.createTypedArrayList(SemScreenWakeInfo.CREATOR);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    this.mDevPowerInfo.writeToParcel(dest, flags);
    dest.writeTypedList(this.mUidPowerInfoList);
    dest.writeTypedList(this.mWakeupReasonInfoList);
    dest.writeTypedList(this.mKernelWakelockInfoList);
    dest.writeTypedList(this.mScreenWakeInfoList);
  }
}
