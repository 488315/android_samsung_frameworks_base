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
import com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmShortTypeSender;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class DexStation extends FanControlModel {
    public static final String TAG = "DexStation";
    public static final HashMap mFanLevelMap = new HashMap() { // from class: com.samsung.android.lib.dexcontrol.model.dexstation.DexStation.1
        {
            put(0, Byte.valueOf(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED));
            put(1, (byte) 17);
            put(2, (byte) 18);
            put(3, (byte) 19);
        }
    };
    public final BroadcastReceiver mEth0Receiver;
    public final IResponseListener mIResponseListener;
    public int preFanLevel;

    public void setMessageSender(Context context) {
        IUvdmSender iUvdmSender = this.mMessageSender;
        if (iUvdmSender != null) {
            iUvdmSender.destroy();
            this.mMessageSender = null;
        }
        UvdmShortTypeSender uvdmShortTypeSender = new UvdmShortTypeSender(40992);
        this.mMessageSender = uvdmShortTypeSender;
        uvdmShortTypeSender.setResponseListener(this.mIResponseListener);
    }

    public DexStation(Context context) {
        super(context, true, 40992);
        this.preFanLevel = -999;
        this.mIResponseListener = new IResponseListener() { // from class: com.samsung.android.lib.dexcontrol.model.dexstation.DexStation.2
            @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
            public void onSuccess(ResponseResult responseResult) {
                SLog.m126d(DexStation.TAG, "onSuccess");
            }

            @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
            public void onFail(int i, ResponseResult responseResult) {
                SLog.m127e(DexStation.TAG, "error : " + i);
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.lib.dexcontrol.model.dexstation.DexStation.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("samsung.net.ethernet.ETH_STATE_CHANGED") && "Connected".equals(intent.getStringExtra("eth_state"))) {
                    GsimcLogger.insertLog(DexStation.this.getContext(), "1ETH");
                }
            }
        };
        this.mEth0Receiver = broadcastReceiver;
        setMessageSender(context);
        creatFanControl();
        getContext().registerReceiver(broadcastReceiver, new IntentFilter("samsung.net.ethernet.ETH_STATE_CHANGED"));
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void usbDeviceChanged(int i, String str) {
        if (i == 40992 || i == 21008) {
            return;
        }
        GsimcLogger.insertLog(getContext(), "1USB", str);
    }

    public final byte[] getFanControlBytes(int i) {
        SLog.m126d(TAG, "getFanControlBytes : " + i);
        return new byte[]{((Byte) mFanLevelMap.get(Integer.valueOf(i))).byteValue()};
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public void onFanControlResult(DexFanControlManager.FAN_LEVEL fan_level) {
        if (this.mMessageSender != null) {
            SLog.m126d(TAG, "onFanControlResult");
            this.mMessageSender.send(getFanControlBytes(fan_level.getFanLevel()));
            if (fan_level.getFanLevel() < 0 || fan_level.getFanLevel() == this.preFanLevel) {
                return;
            }
            GsimcLogger.insertLog(getContext(), "1FAN", String.valueOf(fan_level.getFanLevel()));
            this.preFanLevel = fan_level.getFanLevel();
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel, com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void unregisterReceiver() {
        super.unregisterReceiver();
        SLog.m126d(TAG, "unregisterReceiver");
        getContext().unregisterReceiver(this.mEth0Receiver);
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel, com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void destroy() {
        super.destroy();
        SLog.m126d(TAG, "destroy");
        this.preFanLevel = -999;
    }
}
