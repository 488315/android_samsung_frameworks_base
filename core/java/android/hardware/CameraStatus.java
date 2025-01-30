package android.hardware;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class CameraStatus implements Parcelable {
  public static final Parcelable.Creator<CameraStatus> CREATOR =
      new Parcelable.Creator<CameraStatus>() { // from class: android.hardware.CameraStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraStatus createFromParcel(Parcel in) {
          CameraStatus status = new CameraStatus();
          status.readFromParcel(in);
          return status;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraStatus[] newArray(int size) {
          return new CameraStatus[size];
        }
      };
  public String cameraId;
  public String clientPackage;
  public int status;
  public String[] unavailablePhysicalCameras;

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(this.cameraId);
    out.writeInt(this.status);
    out.writeStringArray(this.unavailablePhysicalCameras);
    out.writeString(this.clientPackage);
  }

  public void readFromParcel(Parcel in) {
    this.cameraId = in.readString();
    this.status = in.readInt();
    this.unavailablePhysicalCameras = in.readStringArray();
    this.clientPackage = in.readString();
  }
}
