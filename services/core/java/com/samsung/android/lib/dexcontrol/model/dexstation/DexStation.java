package com.samsung.android.lib.dexcontrol.model.dexstation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.model.common.FanControlModel;
import com.samsung.android.lib.dexcontrol.utils.GsimcLogger;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener;
import com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmShortTypeSender;
import java.util.HashMap;

public final class DexStation extends FanControlModel {
    public static final HashMap mFanLevelMap = new HashMap() { // from class: com.samsung.android.lib.dexcontrol.model.dexstation.DexStation.1
        {
            put(0, Byte.valueOf(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED));
            put(1, (byte) 17);
            put(2, (byte) 18);
            put(3, (byte) 19);
        }
    };
    public final AnonymousClass3 mEth0Receiver;
    public final AnonymousClass2 mIResponseListener;
    public int preFanLevel;

    /* renamed from: com.samsung.android.lib.dexcontrol.model.dexstation.DexStation$2, reason: invalid class name */
    public final class AnonymousClass2 implements IResponseListener {
        @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
        public final void onFail(int i, ResponseResult responseResult) {
            HashMap hashMap = DexStation.mFanLevelMap;
            SLog.e("DexStation", "error : " + i);
        }

        @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
        public final void onSuccess(ResponseResult responseResult) {
            HashMap hashMap = DexStation.mFanLevelMap;
            SLog.d("DexStation", "onSuccess");
        }
    }

    public DexStation(Context context) {
        super(context, 40992, true);
        this.preFanLevel = -999;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        ?? r0 = new BroadcastReceiver() { // from class: com.samsung.android.lib.dexcontrol.model.dexstation.DexStation.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("samsung.net.ethernet.ETH_STATE_CHANGED") && "Connected".equals(intent.getStringExtra("eth_state"))) {
                    GsimcLogger.insertLog(DexStation.this.mContext, "1ETH");
                }
            }
        };
        this.mEth0Receiver = r0;
        UvdmSendExecutor uvdmSendExecutor = this.mMessageSender;
        if (uvdmSendExecutor != null) {
            uvdmSendExecutor.destroy();
            this.mMessageSender = null;
        }
        UvdmShortTypeSender uvdmShortTypeSender = new UvdmShortTypeSender(40992);
        this.mMessageSender = uvdmShortTypeSender;
        uvdmShortTypeSender.mListener = anonymousClass2;
        creatFanControl();
        this.mContext.registerReceiver(r0, new IntentFilter("samsung.net.ethernet.ETH_STATE_CHANGED"), 2);
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public final void destroy() {
        super.destroy();
        SLog.d("DexStation", "destroy");
        this.preFanLevel = -999;
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public final void onFanControlResult(DexFanControlManager.FAN_LEVEL fan_level) {
        if (this.mMessageSender != null) {
            SLog.d("DexStation", "onFanControlResult");
            int fanLevel = fan_level.getFanLevel();
            SLog.d("DexStation", "getFanControlBytes : " + fanLevel);
            this.mMessageSender.send(500, new byte[]{((Byte) mFanLevelMap.get(Integer.valueOf(fanLevel))).byteValue()});
            if (fan_level.getFanLevel() < 0 || fan_level.getFanLevel() == this.preFanLevel) {
                return;
            }
            GsimcLogger.insertLog(this.mContext, "1FAN", String.valueOf(fan_level.getFanLevel()));
            this.preFanLevel = fan_level.getFanLevel();
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public final void unregisterReceiver() {
        this.mContext.unregisterReceiver(this.mUsbDeviceReceiver);
        this.mContext.unregisterReceiver(this.mDexMonitorIntentReceiver);
        SLog.d("DexStation", "unregisterReceiver");
        this.mContext.unregisterReceiver(this.mEth0Receiver);
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public final void usbDeviceChanged(int i, String str) {
        if (i == 40992 || i == 21008) {
            return;
        }
        GsimcLogger.insertLog(this.mContext, "1USB", str);
    }
}
