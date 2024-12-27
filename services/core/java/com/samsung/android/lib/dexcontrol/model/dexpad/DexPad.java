package com.samsung.android.lib.dexcontrol.model.dexpad;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DockManager;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.model.common.FanControlModel;
import com.samsung.android.lib.dexcontrol.utils.ErrorData;
import com.samsung.android.lib.dexcontrol.utils.GsimcLogger;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.utils.Util;
import com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener;
import com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmLongTypeSender;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

public final class DexPad extends FanControlModel {
    public String DexUNNumber;
    public boolean isAlreadySentDSVersionInfo;
    public int mCharger_Power_Value;
    public int mCharger_Type_Value;
    public DockManager.AnonymousClass3 mControlResponseListener;
    public int mCurrentFanLevel;
    public ErrorData mError;
    public int mFanLevel4Gsim;
    public int mFanRetryCnt;
    public int mFastChargingRetryCnt;
    public boolean mFastChargingStatus;
    public HandlerThread mHandlerThread;
    public final AnonymousClass1 mIResponseListener;
    public AnonymousClass2 mInternalHandler;
    public boolean mIsFastChargingEnabled;
    public int reTryPowerChargerInfoRequestCount;

    /* JADX WARN: Type inference failed for: r6v4, types: [com.samsung.android.lib.dexcontrol.model.dexpad.DexPad$2] */
    public DexPad(Context context, DockManager.AnonymousClass3 anonymousClass3) {
        super(context, 41001, false);
        this.mCharger_Power_Value = 0;
        this.mCharger_Type_Value = 0;
        this.mIsFastChargingEnabled = false;
        this.isAlreadySentDSVersionInfo = false;
        this.DexUNNumber = null;
        this.mFanLevel4Gsim = -999;
        this.mCurrentFanLevel = 0;
        this.mFanRetryCnt = 0;
        this.reTryPowerChargerInfoRequestCount = 1;
        IResponseListener iResponseListener =
                new IResponseListener() { // from class:
                                          // com.samsung.android.lib.dexcontrol.model.dexpad.DexPad.1
                    @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
                    public final void onFail(int i, ResponseResult responseResult) {
                        SLog.e("DexPad", "onFail : " + i);
                        DexPad dexPad = DexPad.this;
                        AnonymousClass2 anonymousClass2 = dexPad.mInternalHandler;
                        if (anonymousClass2 != null) {
                            Message obtainMessage = anonymousClass2.obtainMessage(1);
                            obtainMessage.obj = responseResult.mData.clone();
                            obtainMessage.arg1 = i;
                            dexPad.mInternalHandler.sendMessage(obtainMessage);
                            responseResult.mData = null;
                            if (dexPad.DexUNNumber == null) {
                                dexPad.mError.count(i);
                                return;
                            }
                            if (i == -3) {
                                String.valueOf(true);
                            } else if (i == -2) {
                                String.valueOf(true);
                            } else {
                                if (i != -1) {
                                    return;
                                }
                                String.valueOf(true);
                            }
                        }
                    }

                    @Override // com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener
                    public final void onSuccess(ResponseResult responseResult) {
                        DexPad dexPad = DexPad.this;
                        AnonymousClass2 anonymousClass2 = dexPad.mInternalHandler;
                        if (anonymousClass2 != null) {
                            Message obtainMessage = anonymousClass2.obtainMessage(0);
                            obtainMessage.obj = responseResult.mData.clone();
                            dexPad.mInternalHandler.sendMessage(obtainMessage);
                            responseResult.mData = null;
                        }
                    }
                };
        this.mFastChargingStatus = false;
        this.mFastChargingRetryCnt = 0;
        this.mControlResponseListener = anonymousClass3;
        ErrorData errorData = new ErrorData();
        errorData.mUvdmOpenErrorCntr = 0;
        errorData.mUvdmWriteErrorCntr = 0;
        errorData.mUvdmReadErrorCntr = 0;
        errorData.mDexFanErrorCntr = 0;
        this.mError = errorData;
        HandlerThread handlerThread = new HandlerThread("DexPad");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        AnonymousClass2 anonymousClass2 = this.mInternalHandler;
        if (anonymousClass2 != null) {
            anonymousClass2.removeCallbacksAndMessages(null);
            this.mInternalHandler = null;
        }
        this.mInternalHandler =
                new Handler(this.mHandlerThread.getLooper()) { // from class:
                    // com.samsung.android.lib.dexcontrol.model.dexpad.DexPad.2
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message != null) {
                            SLog.d("DexPad", "handleMessage : " + message.what);
                            int i = message.what;
                            DexPad dexPad = DexPad.this;
                            switch (i) {
                                case 0:
                                    byte[] bArr = (byte[]) message.obj;
                                    SLog.i("DexPad", Util.byteArrayToHex(bArr));
                                    try {
                                        DexPad.access$700(dexPad, bArr);
                                        break;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                case 1:
                                    byte[] bArr2 = (byte[]) message.obj;
                                    dexPad.getClass();
                                    SLog.e(
                                            "DexPad",
                                            "handleOnFailResult : " + Util.byteArrayToHex(bArr2));
                                    if (bArr2.length > 1) {
                                        byte b = bArr2[0];
                                        if (b == -80) {
                                            if (bArr2[1] == 16) {
                                                SLog.e(
                                                        "DexPad",
                                                        "TYPE_CHARGING_CONTROL -"
                                                            + " COMMAND_CHARGER_TYPE_REQUEST");
                                                dexPad.notifyFailedError(-3);
                                                break;
                                            } else {
                                                SLog.e(
                                                        "DexPad",
                                                        "TYPE_CHARGING_CONTROL - Not in Case");
                                                break;
                                            }
                                        } else if (b == 2) {
                                            SLog.e("DexPad", "TYPE_FAN_CONTROL");
                                            break;
                                        } else if (b == 3) {
                                            byte b2 = bArr2[1];
                                            if (b2 == 1) {
                                                SLog.e(
                                                        "DexPad",
                                                        "TYPE_CHARGING_CONTROL -"
                                                            + " COMMAND_CHARGING_CONTROL_REQUEST");
                                                dexPad.notifyFailedError(-1);
                                                break;
                                            } else if (b2 == 16) {
                                                SLog.e(
                                                        "DexPad",
                                                        "TYPE_CHARGING_CONTROL -"
                                                            + " COMMAND_CURRENT_CHARGING_MODE_REQUEST");
                                                dexPad.notifyFailedError(-2);
                                                break;
                                            } else {
                                                SLog.e(
                                                        "DexPad",
                                                        "TYPE_CHARGING_CONTROL - Not in Case");
                                                break;
                                            }
                                        } else {
                                            SLog.e("DexPad", "Not in Case");
                                            break;
                                        }
                                    } else {
                                        SLog.e(
                                                "DexPad",
                                                "message is wrong format : "
                                                        + Util.byteArrayToHex(bArr2));
                                        break;
                                    }
                                case 2:
                                    dexPad.requestConnectedPowerChargerInfoUpdate();
                                    break;
                                case 3:
                                    boolean z = dexPad.mFastChargingStatus;
                                    SLog.d("DexPad", "setFastChargingEnable - " + z);
                                    dexPad.mFastChargingStatus = z;
                                    UvdmSendExecutor uvdmSendExecutor = dexPad.mMessageSender;
                                    if (uvdmSendExecutor == null) {
                                        SLog.d("DexPad", "mMessageSender null");
                                        dexPad.notifyFailedError(-7);
                                        break;
                                    } else {
                                        uvdmSendExecutor.send(
                                                700, new byte[] {3, 1, z ? (byte) 1 : (byte) 0});
                                        break;
                                    }
                                case 4:
                                    int i2 = dexPad.mCurrentFanLevel;
                                    SLog.d("DexPad", "controlDexFanLevel " + i2);
                                    if (i2 >= 0 && dexPad.mFanLevel4Gsim != i2) {
                                        GsimcLogger.insertLog(
                                                dexPad.mContext, "2FAN", String.valueOf(i2));
                                        dexPad.mFanLevel4Gsim = i2;
                                    }
                                    UvdmSendExecutor uvdmSendExecutor2 = dexPad.mMessageSender;
                                    if (uvdmSendExecutor2 == null) {
                                        SLog.d("DexPad", "mMessageSender null");
                                        dexPad.notifyFailedError(-7);
                                        break;
                                    } else {
                                        uvdmSendExecutor2.send(700, new byte[] {2, 1, (byte) i2});
                                        break;
                                    }
                                    break;
                                case 5:
                                    dexPad.getClass();
                                    SLog.d("DexPad", "requestCurrentFanLevelUpdate");
                                    UvdmSendExecutor uvdmSendExecutor3 = dexPad.mMessageSender;
                                    if (uvdmSendExecutor3 == null) {
                                        SLog.d("DexPad", "mMessageSender null");
                                        dexPad.notifyFailedError(-7);
                                        break;
                                    } else {
                                        uvdmSendExecutor3.send(
                                                500,
                                                new byte[] {
                                                    2, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED
                                                });
                                        break;
                                    }
                                case 6:
                                    dexPad.getClass();
                                    SLog.d("DexPad", "requestUniqueNumber");
                                    UvdmSendExecutor uvdmSendExecutor4 = dexPad.mMessageSender;
                                    if (uvdmSendExecutor4 == null) {
                                        SLog.d("DexPad", "mMessageSender null");
                                        dexPad.notifyFailedError(-7);
                                        break;
                                    } else {
                                        uvdmSendExecutor4.send(500, new byte[] {-80, 0});
                                        break;
                                    }
                            }
                        }
                    }
                };
        SLog.d("DexPad", "destroyMessageSender : " + this.mMessageSender);
        UvdmSendExecutor uvdmSendExecutor = this.mMessageSender;
        if (uvdmSendExecutor != null) {
            uvdmSendExecutor.destroy();
            this.mMessageSender = null;
        }
        SLog.d("DexPad", "createMessageSender : " + this.mMessageSender);
        if (this.mMessageSender == null) {
            UvdmLongTypeSender uvdmLongTypeSender = new UvdmLongTypeSender(41001);
            this.mMessageSender = uvdmLongTypeSender;
            uvdmLongTypeSender.mInMsgMinLength = 3;
            uvdmLongTypeSender.mListener = iResponseListener;
        }
        creatFanControl();
    }

    public static void access$700(DexPad dexPad, byte[] bArr) {
        dexPad.getClass();
        int i = 0;
        byte b = bArr[0];
        if (b != -80) {
            if (b != 2) {
                if (b != 3) {
                    SLog.e("DexPad", "handleResponseResult - Not in Case");
                    return;
                }
                byte b2 = bArr[1];
                if (b2 != 1) {
                    if (b2 != 16) {
                        SLog.e("DexPad", "Not in Case");
                        return;
                    }
                    if (bArr[2] == 0) {
                        SLog.d("DexPad", "COMMAND_CURRENT_CHARGING_MODE_REPONSE ACK");
                        dexPad.mIsFastChargingEnabled = bArr[3] != 0;
                        return;
                    } else {
                        SLog.e(
                                "DexPad",
                                "COMMAND_CURRENT_CHARGING_MODE_REPONSE NAK : " + ((int) bArr[3]));
                        return;
                    }
                }
                if (bArr[2] != 0) {
                    SLog.e("DexPad", "COMMAND_CHARGING_CONTROL_RESPONSE NAK : " + ((int) bArr[3]));
                    if (bArr[3] == -16) {
                        int i2 = dexPad.mFastChargingRetryCnt;
                        dexPad.mFastChargingRetryCnt = 1 + i2;
                        if (i2 < 3) {
                            dexPad.sendRequestToHandler(3, 300);
                            return;
                        } else {
                            dexPad.mFastChargingRetryCnt = 0;
                            dexPad.notifyFailedError(-4);
                            return;
                        }
                    }
                    return;
                }
                if (bArr.length < 4) {
                    SLog.d("DexPad", "COMMAND_CHARGING_CONTROL_RESPONSE ACK");
                    SLog.d("DexPad", "requestChargingModeUpdate");
                    UvdmSendExecutor uvdmSendExecutor = dexPad.mMessageSender;
                    if (uvdmSendExecutor != null) {
                        uvdmSendExecutor.send(
                                500, new byte[] {3, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED});
                    } else {
                        SLog.d("DexPad", "mMessageSender null");
                        dexPad.notifyFailedError(-7);
                    }
                } else {
                    dexPad.mIsFastChargingEnabled = bArr[3] != 0;
                    SLog.d(
                            "DexPad",
                            "COMMAND_CHARGING_CONTROL_RESPONSE ACK - enable = "
                                    + dexPad.mIsFastChargingEnabled);
                }
                dexPad.mFastChargingRetryCnt = 0;
                return;
            }
            byte b3 = bArr[1];
            if (b3 != 1) {
                if (b3 != 16) {
                    SLog.e("DexPad", "handleFanControlResponse - Not in Case");
                    return;
                }
                if (bArr[2] == 0) {
                    SLog.d("DexPad", "COMMAND_CURRENT_FAN_STATUS_RESPONSE ACK");
                    dexPad.updateResponsedFanLevel(bArr[3]);
                    return;
                } else {
                    SLog.e(
                            "DexPad",
                            "COMMAND_CURRENT_FAN_STATUS_RESPONSE NAK : " + ((int) bArr[3]));
                    return;
                }
            }
            if (bArr[2] == 0) {
                dexPad.mFanRetryCnt = 0;
                if (bArr.length < 4) {
                    SLog.d("DexPad", "COMMAND_FAN_CONTROL_RESPONSE ACK");
                    dexPad.sendRequestToHandler(5, 0);
                    return;
                }
                byte b4 = bArr[3];
                SLog.d("DexPad", "COMMAND_FAN_CONTROL_RESPONSE ACK - level = " + ((int) b4));
                dexPad.updateResponsedFanLevel(b4);
                return;
            }
            SLog.e("DexPad", "COMMAND_FAN_CONTROL_RESPONSE NAK : " + ((int) bArr[3]));
            byte b5 = bArr[3];
            if (b5 == -15 || b5 == -16) {
                SLog.e("DexPad", "ERROR_SYSTEM_FAULT : " + dexPad.mFanRetryCnt);
                int i3 = dexPad.mFanRetryCnt;
                int i4 = i3 + 1;
                dexPad.mFanRetryCnt = i4;
                if (i3 < 3) {
                    dexPad.sendRequestToHandler(4, 300);
                    return;
                }
                if (i4 == 3) {
                    GsimcLogger.insertLog(dexPad.mContext, "2ERR");
                    if (dexPad.DexUNNumber != null) {
                        String.valueOf(true);
                    } else {
                        dexPad.mError.count(-5);
                    }
                    dexPad.notifyFailedError(-6);
                    dexPad.mFanRetryCnt++;
                    return;
                }
                return;
            }
            return;
        }
        byte b6 = bArr[1];
        if (b6 == 0) {
            if (bArr[2] != 0) {
                SLog.e(
                        "DexPad",
                        "handleBigDataResponse - COMMAND_UN_NUMBER_RESPONSE NAK : "
                                + ((int) bArr[3]));
                return;
            }
            try {
                dexPad.DexUNNumber = new String(Arrays.copyOfRange(bArr, 3, bArr.length), "UTF-8");
                SLog.d(
                        "DexPad",
                        "handleBigDataResponse - COMMAND_UN_NUMBER_RESPONSE ACK : "
                                + dexPad.DexUNNumber);
                if (dexPad.mCharger_Power_Value > 0) {}
                ErrorData errorData = dexPad.mError;
                if (errorData != null) {
                    if (errorData.mDexFanErrorCntr > 0) {
                        String.valueOf(true);
                    }
                    if (dexPad.mError.mUvdmOpenErrorCntr > 0) {
                        String.valueOf(true);
                    }
                    if (dexPad.mError.mUvdmWriteErrorCntr > 0) {
                        String.valueOf(true);
                    }
                    if (dexPad.mError.mUvdmReadErrorCntr > 0) {
                        String.valueOf(true);
                    }
                    ErrorData errorData2 = dexPad.mError;
                    errorData2.mUvdmOpenErrorCntr = 0;
                    errorData2.mUvdmWriteErrorCntr = 0;
                    errorData2.mUvdmReadErrorCntr = 0;
                    errorData2.mDexFanErrorCntr = 0;
                    return;
                }
                return;
            } catch (UnsupportedEncodingException e) {
                SLog.e("DexPad", "un convert error");
                e.printStackTrace();
                return;
            }
        }
        if (b6 != 16) {
            SLog.e("DexPad", "handleBigDataResponse - Not in Case");
            return;
        }
        if (bArr[2] == 0) {
            SLog.d("DexPad", "handleBigDataResponse - COMMAND_CHARGER_TYPE_RESPONSE ACK");
            dexPad.mCharger_Power_Value = 0;
            dexPad.mCharger_Type_Value = 0;
            if (bArr.length <= 4 || bArr.length == 10) {
                SLog.e("DexPad", "handleChargerTypeResponse - TA Not Exist");
            } else {
                byte b7 = bArr[4];
                dexPad.mCharger_Power_Value = b7;
                byte b8 = bArr[3];
                dexPad.mCharger_Type_Value = b8;
                String str =
                        b7 <= 10 ? "1" : b7 <= 15 ? "2" : b7 <= 24 ? "3" : b7 <= 100 ? "4" : null;
                if (b8 == 0) {
                    dexPad.mCharger_Power_Value = 0;
                } else {
                    Context context = dexPad.mContext;
                    StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                    m.append(
                            (String)
                                    dexPad.mTATypeMap.get(
                                            Integer.valueOf(dexPad.mCharger_Type_Value)));
                    GsimcLogger.insertLog(context, "2TAT", m.toString());
                    if (dexPad.DexUNNumber != null) {}
                    int i5 = dexPad.mCharger_Type_Value;
                    i = (i5 == 1 ? dexPad.mCharger_Power_Value < 15 : i5 != 5) ? 1 : 2;
                    dexPad.sendFanControlCheckChargerFastCharging();
                }
            }
            SLog.d(
                    "DexPad",
                    "handleChargerType -  Power : "
                            + dexPad.mCharger_Power_Value
                            + "  Type : "
                            + dexPad.mCharger_Type_Value
                            + "  FastCharging Support : "
                            + i);
            DockManager.AnonymousClass3 anonymousClass3 = dexPad.mControlResponseListener;
            if (anonymousClass3 != null) {
                int i6 = dexPad.mCharger_Power_Value;
                DockManager dockManager = (DockManager) anonymousClass3.this$0;
                dockManager.mHandler.removeMessages(211);
                dockManager.mHandler.obtainMessage(211, i6, i).sendToTarget();
            }
            dexPad.reTryPowerChargerInfoRequestCount = 1;
        } else {
            SLog.e(
                    "DexPad",
                    "handleBigDataResponse - COMMAND_CHARGER_TYPE_RESPONSE NAK : "
                            + ((int) bArr[3]));
            if (bArr[3] == -16) {
                int i7 = dexPad.reTryPowerChargerInfoRequestCount;
                dexPad.reTryPowerChargerInfoRequestCount = i7 + 1;
                if (i7 < 50) {
                    dexPad.sendRequestToHandler(2, 300);
                    return;
                } else {
                    dexPad.reTryPowerChargerInfoRequestCount = 1;
                    dexPad.notifyFailedError(-5);
                }
            }
        }
        if (dexPad.isAlreadySentDSVersionInfo) {
            return;
        }
        if ((bArr.length == 10 || bArr.length == 11) && dexPad.mControlResponseListener != null) {
            StringBuilder sb =
                    new StringBuilder(
                            Util.byteArrayToHex(
                                    Arrays.copyOfRange(bArr, bArr.length - 3, bArr.length)));
            sb.deleteCharAt(2);
            sb.deleteCharAt(3);
            String sb2 = sb.toString();
            String upperCase = "0000".equalsIgnoreCase(sb2) ? null : sb2.toUpperCase(Locale.US);
            SLog.d("DexPad", "update ds verison - " + upperCase);
            DockManager dockManager2 = (DockManager) dexPad.mControlResponseListener.this$0;
            dockManager2.mHandler.removeMessages(231);
            dockManager2.mHandler.obtainMessage(231, upperCase).sendToTarget();
            dexPad.isAlreadySentDSVersionInfo = true;
            dexPad.sendRequestToHandler(6, 15000);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public final void destroy() {
        super.destroy();
        SLog.d("DexPad", "destroy");
        AnonymousClass2 anonymousClass2 = this.mInternalHandler;
        if (anonymousClass2 != null) {
            anonymousClass2.removeCallbacksAndMessages(null);
        }
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
        this.DexUNNumber = null;
        this.mError = null;
    }

    public final void notifyFailedError(int i) {
        SLog.i("DexPad", "notifyFailedError : " + i);
        DockManager.AnonymousClass3 anonymousClass3 = this.mControlResponseListener;
        if (anonymousClass3 != null) {
            DockManager dockManager = (DockManager) anonymousClass3.this$0;
            dockManager.mHandler.removeMessages(221);
            dockManager.mHandler.obtainMessage(221, Integer.valueOf(i)).sendToTarget();
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public final void onFanControlResult(DexFanControlManager.FAN_LEVEL fan_level) {
        this.mCurrentFanLevel = fan_level.getFanLevel();
        sendFanControlCheckChargerFastCharging();
    }

    public final void requestConnectedPowerChargerInfoUpdate() {
        SLog.d("DexPad", "requestConnectedPowerChargerInfoUpdate");
        UvdmSendExecutor uvdmSendExecutor = this.mMessageSender;
        if (uvdmSendExecutor != null) {
            uvdmSendExecutor.send(500, new byte[] {-80, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED});
        } else {
            SLog.d("DexPad", "mMessageSender null");
            notifyFailedError(-7);
        }
    }

    public final void sendFanControlCheckChargerFastCharging() {
        SLog.d(
                "DexPad",
                "sendFanControlCheckChargerFastCharging - type : "
                        + this.mCharger_Type_Value
                        + "  power : "
                        + this.mCharger_Power_Value
                        + "  fastcharging : "
                        + this.mIsFastChargingEnabled);
        if (this.mCharger_Type_Value == 0
                || this.mCharger_Power_Value == 0
                || this.mIsFastChargingEnabled) {
            return;
        }
        sendRequestToHandler(4, 0);
    }

    public final void sendRequestToHandler(int i, int i2) {
        SLog.d("DexPad", "sendRequestToHandler " + i + " " + i2);
        AnonymousClass2 anonymousClass2 = this.mInternalHandler;
        if (anonymousClass2 == null) {
            SLog.e("DexPad", "Handler is null");
            return;
        }
        if (anonymousClass2.hasMessages(i)) {
            removeMessages(i);
        }
        sendEmptyMessageDelayed(i, i2);
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.FanControlModel
    public final void unregisterReceiver() {
        this.mContext.unregisterReceiver(this.mUsbDeviceReceiver);
        this.mContext.unregisterReceiver(this.mDexMonitorIntentReceiver);
        SLog.d("DexPad", "unregisterReceiver");
    }

    public final void updateResponsedFanLevel(int i) {
        SLog.d("FanControlModel", "updateResponsedFanLevel " + i);
        DexFanControlManager dexFanControlManager = this.mIDexFanControl;
        dexFanControlManager.getClass();
        SLog.d("DexFanControlManager", "onDexFanLevelUpdated : " + i);
        DexFanControlManager.FAN_LEVEL fan_level = dexFanControlManager.mCurrentFanLevel;
        if (fan_level == null || fan_level.getFanLevel() == i) {
            return;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "Received Fan Level(", ") is not matched with ");
        m.append(dexFanControlManager.mCurrentFanLevel.getFanLevel());
        m.append(" level.");
        SLog.e("DexFanControlManager", m.toString());
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public final void usbDeviceChanged(int i, String str) {
        if (i == 41001 || i == 63056 || i == 63045) {
            return;
        }
        GsimcLogger.insertLog(this.mContext, "2USB", str);
    }
}
