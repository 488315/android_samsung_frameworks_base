package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAutoBrightnessAttribute extends SContextAttribute {
  private static final String TAG = "SContextAutoBrightnessAttribute";
  private int mDeviceMode;
  private byte[] mLuminanceTable;
  private int mMode;
  private static int MODE_DEVICE_MODE = 0;
  private static int MODE_CONFIGURATION = 1;

  SContextAutoBrightnessAttribute() {
    this.mLuminanceTable = null;
    this.mDeviceMode = 0;
    this.mMode = -1;
    setAttribute();
  }

  public SContextAutoBrightnessAttribute(int deviceMode) {
    this.mLuminanceTable = null;
    this.mDeviceMode = 0;
    this.mMode = -1;
    this.mDeviceMode = deviceMode;
    this.mMode = MODE_DEVICE_MODE;
    setAttribute();
  }

  public SContextAutoBrightnessAttribute(byte[] luminanceTable) {
    this.mLuminanceTable = null;
    this.mDeviceMode = 0;
    this.mMode = -1;
    this.mLuminanceTable = luminanceTable;
    this.mMode = MODE_CONFIGURATION;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mMode;
    if (i == MODE_DEVICE_MODE) {
      int i2 = this.mDeviceMode;
      if (i2 < 0 || i2 > 2) {
        Log.m96e(TAG, "The device mode is wrong.");
        return false;
      }
      return true;
    }
    if (i == MODE_CONFIGURATION && this.mLuminanceTable == null) {
      Log.m96e(TAG, "The luminance configration data is null.");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("mode", this.mMode);
    int i = this.mMode;
    if (i == MODE_CONFIGURATION) {
      attribute.putByteArray("luminance_config_data", this.mLuminanceTable);
    } else if (i == MODE_DEVICE_MODE) {
      attribute.putInt("device_mode", this.mDeviceMode);
    }
    super.setAttribute(39, attribute);
  }
}
