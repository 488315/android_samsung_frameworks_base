package com.samsung.android.lib.dexcontrol;

import android.content.Context;
import com.samsung.android.lib.dexcontrol.ISDCDeviceController;
import com.samsung.android.lib.dexcontrol.model.dexpad.DexPad;
import com.samsung.android.lib.dexcontrol.model.dexstation.DexStation;
import com.samsung.android.lib.dexcontrol.utils.SLog;

/* loaded from: classes2.dex */
public class SDCDeviceController implements ISDCDeviceController {
    public static final String TAG = "SDCDeviceController";
    public int mDexDevicePID;
    public DexPad mDexPad;
    public DexStation mDexStation;

    public SDCDeviceController(Context context, int i, ISDCDeviceController.ControlResponseListener controlResponseListener) {
        this.mDexStation = null;
        this.mDexPad = null;
        this.mDexDevicePID = i;
        String str = TAG;
        SLog.m128i(str, "Model : " + i + ", DexControlLib Version : ");
        int i2 = this.mDexDevicePID;
        if (i2 == 40992) {
            this.mDexStation = new DexStation(context);
            return;
        }
        if (i2 == 41001) {
            this.mDexPad = new DexPad(context, controlResponseListener);
            return;
        }
        SLog.m127e(str, "SDCDeviceController unknown pid = " + i);
    }

    @Override // com.samsung.android.lib.dexcontrol.ISDCDeviceController
    public void requestConnectedPowerChargerInfoUpdate() {
        DexPad dexPad = this.mDexPad;
        if (dexPad != null) {
            dexPad.requestConnectedPowerChargerInfoUpdate();
            return;
        }
        throw new NotSupportDexFeatureException(this.mDexDevicePID);
    }

    @Override // com.samsung.android.lib.dexcontrol.ISDCDeviceController
    public void destroy() {
        SLog.m128i(TAG, "destroy");
        DexStation dexStation = this.mDexStation;
        if (dexStation != null) {
            dexStation.destroy();
        }
        this.mDexStation = null;
        DexPad dexPad = this.mDexPad;
        if (dexPad != null) {
            dexPad.destroy();
        }
        this.mDexPad = null;
        this.mDexDevicePID = 0;
    }
}
