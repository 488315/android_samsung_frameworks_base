package com.samsung.android.lib.dexcontrol.model.dexpad;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.lib.dexcontrol.ISDCDeviceController;
import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.model.common.FanControlModel;
import com.samsung.android.lib.dexcontrol.utils.ContextUtil;
import com.samsung.android.lib.dexcontrol.utils.ErrorData;
import com.samsung.android.lib.dexcontrol.utils.GsimcLogger;
import com.samsung.android.lib.dexcontrol.utils.HwLogger;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.utils.Util;
import com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener;
import com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult;
import com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmLongTypeSender;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DexPad extends FanControlModel {
    public static final String TAG = "DexPad";
    public String DexUNNumber;
    public boolean isAlreadySentDSVersionInfo;
    public int mCharger_Power_Value;
    public int mCharger_Type_Value;
    public ISDCDeviceController.ControlResponseListener mControlResponseListener;
    public int mCurrentFanLevel;
    public boolean mDexBinaryDownloading;
    public ErrorData mError;
    public int mFanLevel4Gsim;
    public int mFanRetryCnt;
    public int mFastChargingRetryCnt;
    public boolean mFastChargingStatus;
    public HandlerThread mHandlerThread;
    public final IResponseListener mIResponseListener;
    public Handler mInternalHandler;
    public boolean mIsFastChargingEnabled;
    public int reTryPowerChargerInfoRequestCount;

    public DexPad(Context context, ISDCDeviceController.ControlResponseListener controlResponseListener) {
        super(context, false, 41001);
        this.mCharger_Power_Value = 0;
        this.mCharger_Type_Value = 0;
        this.mIsFastChargingEnabled = false;
        this.isAlreadySentDSVersionInfo = false;
        this.mDexBinaryDownloading = false;
        this.DexUNNumber = null;
        this.mControlResponseListener = null;
        this.mFanLevel4Gsim = -999;
        this.mCurrentFanLevel = 0;
        this.mFanRetryCnt = 0;
        this.mError = null;
        this.reTryPowerChargerInfoRequestCount = 1;
        this.mIResponseListener = new IResponseListener() { // from class: com.samsung.android.lib.dexcontrol.model.dexpad.DexPad.1
            @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
            public void onSuccess(ResponseResult responseResult) {
                if (responseResult == null || DexPad.this.mInternalHandler == null) {
                    return;
                }
                Message obtainMessage = DexPad.this.mInternalHandler.obtainMessage(0);
                obtainMessage.obj = responseResult.getData().clone();
                DexPad.this.mInternalHandler.sendMessage(obtainMessage);
                responseResult.setData(null);
            }

            @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
            public void onFail(int i, ResponseResult responseResult) {
                SLog.m127e(DexPad.TAG, "onFail : " + i);
                if (responseResult == null || DexPad.this.mInternalHandler == null) {
                    return;
                }
                Message obtainMessage = DexPad.this.mInternalHandler.obtainMessage(1);
                obtainMessage.obj = responseResult.getData().clone();
                obtainMessage.arg1 = i;
                DexPad.this.mInternalHandler.sendMessage(obtainMessage);
                responseResult.setData(null);
                if (DexPad.this.DexUNNumber == null) {
                    DexPad.this.mError.count(i);
                    return;
                }
                if (i == -3) {
                    HwLogger.insertHQM(DexPad.this.getContext(), DexPad.this.DexUNNumber, "DXUR", String.valueOf(true));
                } else if (i == -2) {
                    HwLogger.insertHQM(DexPad.this.getContext(), DexPad.this.DexUNNumber, "DXUW", String.valueOf(true));
                } else {
                    if (i != -1) {
                        return;
                    }
                    HwLogger.insertHQM(DexPad.this.getContext(), DexPad.this.DexUNNumber, "DXUO", String.valueOf(true));
                }
            }
        };
        this.mFastChargingStatus = false;
        this.mFastChargingRetryCnt = 0;
        this.mControlResponseListener = controlResponseListener;
        this.mError = new ErrorData();
        initHandler();
        setMessageSender(context);
        creatFanControl();
    }

    public final void initHandler() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Handler handler = this.mInternalHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mInternalHandler = null;
        }
        this.mInternalHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.samsung.android.lib.dexcontrol.model.dexpad.DexPad.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message != null) {
                    SLog.m126d(DexPad.TAG, "handleMessage : " + message.what);
                    switch (message.what) {
                        case 0:
                            byte[] bArr = (byte[]) message.obj;
                            SLog.m128i(DexPad.TAG, Util.byteArrayToHex(bArr));
                            try {
                                DexPad.this.handleResponseResult(bArr);
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        case 1:
                            DexPad.this.handleOnFailResult((byte[]) message.obj);
                            break;
                        case 2:
                            DexPad.this.requestConnectedPowerChargerInfoUpdate();
                            break;
                        case 3:
                            DexPad dexPad = DexPad.this;
                            dexPad.setFastChargingEnable(dexPad.mFastChargingStatus);
                            break;
                        case 4:
                            DexPad dexPad2 = DexPad.this;
                            dexPad2.controlDexFanLevel(dexPad2.mCurrentFanLevel);
                            break;
                        case 5:
                            DexPad.this.requestCurrentFanLevelUpdate();
                            break;
                        case 6:
                            DexPad.this.requestUniqueNumber();
                            break;
                    }
                }
            }
        };
    }

    public final void destroyMessageSender() {
        SLog.m126d(TAG, "destroyMessageSender : " + this.mMessageSender);
        IUvdmSender iUvdmSender = this.mMessageSender;
        if (iUvdmSender != null) {
            iUvdmSender.destroy();
            this.mMessageSender = null;
        }
    }

    public final void createMessageSender() {
        SLog.m126d(TAG, "createMessageSender : " + this.mMessageSender);
        if (this.mMessageSender == null) {
            UvdmLongTypeSender uvdmLongTypeSender = new UvdmLongTypeSender(41001);
            this.mMessageSender = uvdmLongTypeSender;
            uvdmLongTypeSender.setInMsgMinLength(3);
            this.mMessageSender.setResponseListener(this.mIResponseListener);
        }
    }

    public void setMessageSender(Context context) {
        destroyMessageSender();
        createMessageSender();
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public void onFanControlResult(DexFanControlManager.FAN_LEVEL fan_level) {
        this.mCurrentFanLevel = fan_level.getFanLevel();
        sendFanControlCheckChargerFastCharging();
    }

    public final void sendFanControlCheckChargerFastCharging() {
        SLog.m126d(TAG, "sendFanControlCheckChargerFastCharging - type : " + this.mCharger_Type_Value + "  power : " + this.mCharger_Power_Value + "  fastcharging : " + this.mIsFastChargingEnabled);
        if (this.mCharger_Type_Value == 0 || this.mCharger_Power_Value == 0 || this.mIsFastChargingEnabled) {
            return;
        }
        sendRequestToHandler(4, 0);
    }

    public final void sendRequestToHandler(int i, int i2) {
        String str = TAG;
        SLog.m126d(str, "sendRequestToHandler " + i + " " + i2);
        Handler handler = this.mInternalHandler;
        if (handler != null) {
            if (handler.hasMessages(i)) {
                this.mInternalHandler.removeMessages(i);
            }
            this.mInternalHandler.sendEmptyMessageDelayed(i, i2);
            return;
        }
        SLog.m127e(str, "Handler is null");
    }

    public final void controlDexFanLevel(int i) {
        String str = TAG;
        SLog.m126d(str, "controlDexFanLevel " + i);
        if (i >= 0 && this.mFanLevel4Gsim != i) {
            GsimcLogger.insertLog(getContext(), "2FAN", String.valueOf(i));
            this.mFanLevel4Gsim = i;
        }
        if (this.mMessageSender != null) {
            this.mMessageSender.send(DexPadMessage.getBytes((byte) 2, (byte) 1, i), 700);
        } else {
            SLog.m126d(str, "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    public void setFastChargingEnable(boolean z) {
        String str = TAG;
        SLog.m126d(str, "setFastChargingEnable - " + z);
        this.mFastChargingStatus = z;
        if (this.mMessageSender != null) {
            this.mMessageSender.send(DexPadMessage.getBytes((byte) 3, (byte) 1, z ? 1 : 0), 700);
        } else {
            SLog.m126d(str, "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    public void requestChargingModeUpdate() {
        String str = TAG;
        SLog.m126d(str, "requestChargingModeUpdate");
        if (this.mMessageSender != null) {
            this.mMessageSender.send(DexPadMessage.getBytes((byte) 3, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED));
        } else {
            SLog.m126d(str, "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    public void requestConnectedPowerChargerInfoUpdate() {
        String str = TAG;
        SLog.m126d(str, "requestConnectedPowerChargerInfoUpdate");
        if (this.mMessageSender != null) {
            this.mMessageSender.send(DexPadMessage.getBytes((byte) -80, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED));
        } else {
            SLog.m126d(str, "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    public void requestUniqueNumber() {
        String str = TAG;
        SLog.m126d(str, "requestUniqueNumber");
        if (this.mMessageSender != null) {
            this.mMessageSender.send(DexPadMessage.getBytes((byte) -80, (byte) 0));
        } else {
            SLog.m126d(str, "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    public final void requestCurrentFanLevelUpdate() {
        String str = TAG;
        SLog.m126d(str, "requestCurrentFanLevelUpdate");
        if (this.mMessageSender != null) {
            this.mMessageSender.send(DexPadMessage.getBytes((byte) 2, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED));
        } else {
            SLog.m126d(str, "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel, com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void unregisterReceiver() {
        super.unregisterReceiver();
        SLog.m126d(TAG, "unregisterReceiver");
    }

    public final void removeMessagesInHandler() {
        Handler handler = this.mInternalHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel, com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void destroy() {
        super.destroy();
        SLog.m126d(TAG, "destroy");
        removeMessagesInHandler();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mHandlerThread.join();
                this.mHandlerThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.mInternalHandler = null;
        this.mControlResponseListener = null;
        this.mFanLevel4Gsim = -999;
        ContextUtil.destory();
        this.DexUNNumber = null;
        this.mError = null;
    }

    public final void handleResponseResult(byte[] bArr) {
        byte b = bArr[0];
        if (b == -80) {
            handleBigDataResponse(bArr);
            return;
        }
        if (b == 2) {
            handleFanControlResponse(bArr);
        } else if (b == 3) {
            handleChargingControlResponse(bArr);
        } else {
            SLog.m127e(TAG, "handleResponseResult - Not in Case");
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public void updateResponsedFanLevel(int i) {
        super.updateResponsedFanLevel(i);
    }

    public final void handleFanControlResponse(byte[] bArr) {
        byte b = bArr[1];
        if (b != 1) {
            if (b == 16) {
                if (bArr[2] == 0) {
                    SLog.m126d(TAG, "COMMAND_CURRENT_FAN_STATUS_RESPONSE ACK");
                    updateResponsedFanLevel(bArr[3]);
                    return;
                }
                SLog.m127e(TAG, "COMMAND_CURRENT_FAN_STATUS_RESPONSE NAK : " + ((int) bArr[3]));
                return;
            }
            SLog.m127e(TAG, "handleFanControlResponse - Not in Case");
            return;
        }
        if (bArr[2] == 0) {
            this.mFanRetryCnt = 0;
            if (bArr.length < 4) {
                SLog.m126d(TAG, "COMMAND_FAN_CONTROL_RESPONSE ACK");
                sendRequestToHandler(5, 0);
                return;
            }
            byte b2 = bArr[3];
            SLog.m126d(TAG, "COMMAND_FAN_CONTROL_RESPONSE ACK - level = " + ((int) b2));
            updateResponsedFanLevel(b2);
            return;
        }
        String str = TAG;
        SLog.m127e(str, "COMMAND_FAN_CONTROL_RESPONSE NAK : " + ((int) bArr[3]));
        byte b3 = bArr[3];
        if (b3 == -15 || b3 == -16) {
            SLog.m127e(str, "ERROR_SYSTEM_FAULT : " + this.mFanRetryCnt);
            int i = this.mFanRetryCnt;
            int i2 = i + 1;
            this.mFanRetryCnt = i2;
            if (i < 3) {
                sendRequestToHandler(4, 300);
                return;
            }
            if (i2 == 3) {
                GsimcLogger.insertLog(getContext(), "2ERR");
                if (this.DexUNNumber != null) {
                    HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXER", String.valueOf(true));
                } else {
                    this.mError.count(-5);
                }
                notifyFailedError(-6);
                this.mFanRetryCnt++;
            }
        }
    }

    public final void handleChargingControlResponse(byte[] bArr) {
        byte b = bArr[1];
        if (b != 1) {
            if (b == 16) {
                if (bArr[2] == 0) {
                    SLog.m126d(TAG, "COMMAND_CURRENT_CHARGING_MODE_REPONSE ACK");
                    boolean z = bArr[3] != 0;
                    this.mIsFastChargingEnabled = z;
                    ISDCDeviceController.ControlResponseListener controlResponseListener = this.mControlResponseListener;
                    if (controlResponseListener != null) {
                        controlResponseListener.onChargingModeUpdated(z);
                        return;
                    }
                    return;
                }
                SLog.m127e(TAG, "COMMAND_CURRENT_CHARGING_MODE_REPONSE NAK : " + ((int) bArr[3]));
                return;
            }
            SLog.m127e(TAG, "Not in Case");
            return;
        }
        if (bArr[2] == 0) {
            if (bArr.length < 4) {
                SLog.m126d(TAG, "COMMAND_CHARGING_CONTROL_RESPONSE ACK");
                requestChargingModeUpdate();
            } else {
                this.mIsFastChargingEnabled = bArr[3] != 0;
                SLog.m126d(TAG, "COMMAND_CHARGING_CONTROL_RESPONSE ACK - enable = " + this.mIsFastChargingEnabled);
                ISDCDeviceController.ControlResponseListener controlResponseListener2 = this.mControlResponseListener;
                if (controlResponseListener2 != null) {
                    controlResponseListener2.onChargingModeUpdated(this.mIsFastChargingEnabled);
                }
            }
            this.mFastChargingRetryCnt = 0;
            return;
        }
        SLog.m127e(TAG, "COMMAND_CHARGING_CONTROL_RESPONSE NAK : " + ((int) bArr[3]));
        if (bArr[3] == -16) {
            int i = this.mFastChargingRetryCnt;
            this.mFastChargingRetryCnt = i + 1;
            if (i < 3) {
                sendRequestToHandler(3, 300);
            } else {
                this.mFastChargingRetryCnt = 0;
                notifyFailedError(-4);
            }
        }
    }

    public final void handleChargerTypeResponse(byte[] bArr) {
        int i = 0;
        this.mCharger_Power_Value = 0;
        this.mCharger_Type_Value = 0;
        if (bArr.length <= 4 || bArr.length == 10) {
            SLog.m127e(TAG, "handleChargerTypeResponse - TA Not Exist");
        } else {
            byte b = bArr[4];
            this.mCharger_Power_Value = b;
            byte b2 = bArr[3];
            this.mCharger_Type_Value = b2;
            String str = b <= 10 ? "1" : b <= 15 ? "2" : b <= 24 ? "3" : b <= 100 ? "4" : null;
            if (b2 == 0) {
                this.mCharger_Power_Value = 0;
            } else {
                GsimcLogger.insertLog(getContext(), "2TAT", str + ((String) this.mTATypeMap.get(Integer.valueOf(this.mCharger_Type_Value))));
                if (this.DexUNNumber != null) {
                    HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXTA", ((String) this.mTATypeMap.get(Integer.valueOf(this.mCharger_Type_Value))) + this.mCharger_Power_Value);
                }
                int i2 = this.mCharger_Type_Value;
                i = 2;
                if (i2 == 1 ? this.mCharger_Power_Value < 15 : i2 != 5) {
                    i = 1;
                }
                sendFanControlCheckChargerFastCharging();
            }
        }
        SLog.m126d(TAG, "handleChargerType -  Power : " + this.mCharger_Power_Value + "  Type : " + this.mCharger_Type_Value + "  FastCharging Support : " + i);
        ISDCDeviceController.ControlResponseListener controlResponseListener = this.mControlResponseListener;
        if (controlResponseListener != null) {
            controlResponseListener.onConnectedPowerChargerInfoUpdated(this.mCharger_Type_Value, this.mCharger_Power_Value, i);
        }
    }

    public final void handleBigDataResponse(byte[] bArr) {
        byte b = bArr[1];
        if (b == 0) {
            if (bArr[2] == 0) {
                try {
                    this.DexUNNumber = new String(Arrays.copyOfRange(bArr, 3, bArr.length), "UTF-8");
                    SLog.m126d(TAG, "handleBigDataResponse - COMMAND_UN_NUMBER_RESPONSE ACK : " + this.DexUNNumber);
                    if (this.mCharger_Power_Value > 0) {
                        HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXTA", ((String) this.mTATypeMap.get(Integer.valueOf(this.mCharger_Type_Value))) + this.mCharger_Power_Value);
                    }
                    ErrorData errorData = this.mError;
                    if (errorData != null) {
                        if (errorData.getCountNumber(-5) > 0) {
                            HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXER", String.valueOf(true));
                        }
                        if (this.mError.getCountNumber(-1) > 0) {
                            HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXUO", String.valueOf(true));
                        }
                        if (this.mError.getCountNumber(-2) > 0) {
                            HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXUW", String.valueOf(true));
                        }
                        if (this.mError.getCountNumber(-3) > 0) {
                            HwLogger.insertHQM(getContext(), this.DexUNNumber, "DXUR", String.valueOf(true));
                        }
                        this.mError.reset();
                        return;
                    }
                    return;
                } catch (UnsupportedEncodingException e) {
                    SLog.m127e(TAG, "un convert error");
                    e.printStackTrace();
                    return;
                }
            }
            SLog.m127e(TAG, "handleBigDataResponse - COMMAND_UN_NUMBER_RESPONSE NAK : " + ((int) bArr[3]));
            return;
        }
        if (b == 16) {
            if (bArr[2] == 0) {
                SLog.m126d(TAG, "handleBigDataResponse - COMMAND_CHARGER_TYPE_RESPONSE ACK");
                handleChargerTypeResponse(bArr);
                this.reTryPowerChargerInfoRequestCount = 1;
            } else {
                SLog.m127e(TAG, "handleBigDataResponse - COMMAND_CHARGER_TYPE_RESPONSE NAK : " + ((int) bArr[3]));
                if (bArr[3] == -16) {
                    int i = this.reTryPowerChargerInfoRequestCount;
                    this.reTryPowerChargerInfoRequestCount = i + 1;
                    if (i < 50) {
                        sendRequestToHandler(2, 300);
                        return;
                    } else {
                        this.reTryPowerChargerInfoRequestCount = 1;
                        notifyFailedError(-5);
                    }
                }
            }
            if (this.isAlreadySentDSVersionInfo) {
                return;
            }
            if ((bArr.length == 10 || bArr.length == 11) && this.mControlResponseListener != null) {
                StringBuilder sb = new StringBuilder(Util.byteArrayToHex(Arrays.copyOfRange(bArr, bArr.length - 3, bArr.length)));
                sb.deleteCharAt(2);
                sb.deleteCharAt(3);
                String sb2 = sb.toString();
                String upperCase = "0000".equalsIgnoreCase(sb2) ? null : sb2.toUpperCase(Locale.US);
                SLog.m126d(TAG, "update ds verison - " + upperCase);
                this.mControlResponseListener.onDSVersionUpdated(upperCase);
                this.isAlreadySentDSVersionInfo = true;
                sendRequestToHandler(6, 15000);
                return;
            }
            return;
        }
        SLog.m127e(TAG, "handleBigDataResponse - Not in Case");
    }

    public final void notifyFailedError(int i) {
        SLog.m128i(TAG, "notifyFailedError : " + i);
        ISDCDeviceController.ControlResponseListener controlResponseListener = this.mControlResponseListener;
        if (controlResponseListener != null) {
            controlResponseListener.onError(i);
        }
    }

    public final void handleOnFailResult(byte[] bArr) {
        String str = TAG;
        SLog.m127e(str, "handleOnFailResult : " + Util.byteArrayToHex(bArr));
        if (bArr.length <= 1) {
            SLog.m127e(str, "message is wrong format : " + Util.byteArrayToHex(bArr));
            return;
        }
        byte b = bArr[0];
        if (b == -80) {
            if (bArr[1] == 16) {
                SLog.m127e(str, "TYPE_CHARGING_CONTROL - COMMAND_CHARGER_TYPE_REQUEST");
                notifyFailedError(-3);
                return;
            } else {
                SLog.m127e(str, "TYPE_CHARGING_CONTROL - Not in Case");
                return;
            }
        }
        if (b == 2) {
            SLog.m127e(str, "TYPE_FAN_CONTROL");
            return;
        }
        if (b == 3) {
            byte b2 = bArr[1];
            if (b2 == 1) {
                SLog.m127e(str, "TYPE_CHARGING_CONTROL - COMMAND_CHARGING_CONTROL_REQUEST");
                notifyFailedError(-1);
                return;
            } else if (b2 == 16) {
                SLog.m127e(str, "TYPE_CHARGING_CONTROL - COMMAND_CURRENT_CHARGING_MODE_REQUEST");
                notifyFailedError(-2);
                return;
            } else {
                SLog.m127e(str, "TYPE_CHARGING_CONTROL - Not in Case");
                return;
            }
        }
        SLog.m127e(str, "Not in Case");
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void usbDeviceChanged(int i, String str) {
        if (i == 41001 || i == 63056 || i == 63045) {
            return;
        }
        GsimcLogger.insertLog(getContext(), "2USB", str);
    }
}
