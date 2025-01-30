package android.hardware.tv.cec.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.Objects;

/* loaded from: classes.dex */
public final class HotplugEvent {
  public boolean connected = false;
  public int portId = 0;

  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || obj.getClass() != HotplugEvent.class) {
      return false;
    }
    HotplugEvent hotplugEvent = (HotplugEvent) obj;
    return this.connected == hotplugEvent.connected && this.portId == hotplugEvent.portId;
  }

  public final int hashCode() {
    return Objects.hash(
        Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.connected))),
        Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.portId))));
  }

  public final String toString() {
    return "{.connected = " + this.connected + ", .portId = " + this.portId + "}";
  }

  public final void readFromParcel(HwParcel hwParcel) {
    readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
  }

  public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
    this.connected = hwBlob.getBool(0 + j);
    this.portId = hwBlob.getInt32(j + 4);
  }
}
