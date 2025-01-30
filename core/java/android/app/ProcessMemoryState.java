package android.app;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public final class ProcessMemoryState implements Parcelable {
  public static final Parcelable.Creator<ProcessMemoryState> CREATOR =
      new Parcelable.Creator<ProcessMemoryState>() { // from class: android.app.ProcessMemoryState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProcessMemoryState createFromParcel(Parcel in) {
          return new ProcessMemoryState(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProcessMemoryState[] newArray(int size) {
          return new ProcessMemoryState[size];
        }
      };
  public static final int HOSTING_COMPONENT_TYPE_ACTIVITY = 16;
  public static final int HOSTING_COMPONENT_TYPE_BACKUP = 4;
  public static final int HOSTING_COMPONENT_TYPE_BOUND_SERVICE = 512;
  public static final int HOSTING_COMPONENT_TYPE_BROADCAST_RECEIVER = 32;
  public static final int HOSTING_COMPONENT_TYPE_EMPTY = 0;
  public static final int HOSTING_COMPONENT_TYPE_FOREGROUND_SERVICE = 256;
  public static final int HOSTING_COMPONENT_TYPE_INSTRUMENTATION = 8;
  public static final int HOSTING_COMPONENT_TYPE_PERSISTENT = 2;
  public static final int HOSTING_COMPONENT_TYPE_PROVIDER = 64;
  public static final int HOSTING_COMPONENT_TYPE_STARTED_SERVICE = 128;
  public static final int HOSTING_COMPONENT_TYPE_SYSTEM = 1;
  public final boolean hasForegroundServices;
  public final int mHistoricalHostingComponentTypes;
  public final int mHostingComponentTypes;
  public final int oomScore;
  public final int pid;
  public final String processName;
  public final int uid;

  public @interface HostingComponentType {}

  public ProcessMemoryState(
      int uid,
      int pid,
      String processName,
      int oomScore,
      boolean hasForegroundServices,
      int hostingComponentTypes,
      int historicalHostingComponentTypes) {
    this.uid = uid;
    this.pid = pid;
    this.processName = processName;
    this.oomScore = oomScore;
    this.hasForegroundServices = hasForegroundServices;
    this.mHostingComponentTypes = hostingComponentTypes;
    this.mHistoricalHostingComponentTypes = historicalHostingComponentTypes;
  }

  private ProcessMemoryState(Parcel in) {
    this.uid = in.readInt();
    this.pid = in.readInt();
    this.processName = in.readString();
    this.oomScore = in.readInt();
    this.hasForegroundServices = in.readInt() == 1;
    this.mHostingComponentTypes = in.readInt();
    this.mHistoricalHostingComponentTypes = in.readInt();
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(this.uid);
    parcel.writeInt(this.pid);
    parcel.writeString(this.processName);
    parcel.writeInt(this.oomScore);
    parcel.writeInt(this.hasForegroundServices ? 1 : 0);
    parcel.writeInt(this.mHostingComponentTypes);
    parcel.writeInt(this.mHistoricalHostingComponentTypes);
  }
}
