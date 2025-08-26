package com.android.systemui.shade.carrier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ICellBroadcastService;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes3.dex */
public final class LatinNetworkNameProviderImpl$broadcastReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ LatinNetworkNameProviderImpl this$0;

    public LatinNetworkNameProviderImpl$broadcastReceiver$1(LatinNetworkNameProviderImpl latinNetworkNameProviderImpl) {
        this.this$0 = latinNetworkNameProviderImpl;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01d8  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        String str;
        HashMap map;
        if (intent != null) {
            LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = this.this$0;
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -2104353374:
                        if (action.equals("android.intent.action.SERVICE_STATE")) {
                            int intExtra = intent.getIntExtra("slot", 0);
                            latinNetworkNameProviderImpl.getClass();
                            if (SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(intExtra))) {
                                Bundle extras = intent.getExtras();
                                ServiceState serviceStateNewFromBundle = extras != null ? ServiceState.newFromBundle(extras) : null;
                                if (serviceStateNewFromBundle != null) {
                                    ServiceStateInfo serviceStateInfo = new ServiceStateInfo(serviceStateNewFromBundle.getRilDataRadioTechnology(), serviceStateNewFromBundle.getRilVoiceRadioTechnology(), serviceStateNewFromBundle.isEmergencyOnly(), serviceStateNewFromBundle.getRoaming(), serviceStateNewFromBundle.getState() == 0);
                                    ServiceStateInfo serviceStateInfo2 = (ServiceStateInfo) latinNetworkNameProviderImpl.serviceStateHash.get(Integer.valueOf(intExtra));
                                    if (serviceStateInfo2 != null && LatinNetworkNameProviderImpl.isLatinGSM(serviceStateInfo2.networkType, serviceStateInfo2.voiceNetworkType) && !LatinNetworkNameProviderImpl.isLatinGSM(serviceStateInfo.networkType, serviceStateInfo.voiceNetworkType)) {
                                        latinNetworkNameProviderImpl.broadcastCBClear(intExtra);
                                    }
                                    int size = latinNetworkNameProviderImpl.serviceStateHash.size();
                                    latinNetworkNameProviderImpl.serviceStateHash.put(Integer.valueOf(intExtra), serviceStateInfo);
                                    if (size != latinNetworkNameProviderImpl.serviceStateHash.size()) {
                                        SuggestionsAdapter$$ExternalSyntheticOutline0.m(size, latinNetworkNameProviderImpl.serviceStateHash.size(), "There's changes in Subscriptions ", " -> ", "LatinNetworkNameProvider");
                                        latinNetworkNameProviderImpl.unregisterLocationListener();
                                        latinNetworkNameProviderImpl.registerLocationListener();
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case -1162466375:
                        if (action.equals("android.telephony.action.AREA_INFO_UPDATED")) {
                            int intExtra2 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            boolean booleanExtra = intent.getBooleanExtra("enable", true);
                            if (intExtra2 == -1) {
                                NetworkNameInfo networkNameInfo = (NetworkNameInfo) latinNetworkNameProviderImpl.networkNameHash.get(Integer.valueOf(intExtra2));
                                KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(intExtra2, "[INVALID_SIM_SLOT_INDEX] slotId=", " plmn=", networkNameInfo != null ? networkNameInfo.plmn : null, "LatinNetworkNameProvider");
                                latinNetworkNameProviderImpl.showCBMsg = false;
                            } else if (booleanExtra) {
                                NetworkNameInfo networkNameInfo2 = (NetworkNameInfo) latinNetworkNameProviderImpl.networkNameHash.get(Integer.valueOf(intExtra2));
                                if (networkNameInfo2 != null && networkNameInfo2.plmn != null && latinNetworkNameProviderImpl.serviceStateHash.get(Integer.valueOf(intExtra2)) != null) {
                                    Object obj = latinNetworkNameProviderImpl.serviceStateHash.get(Integer.valueOf(intExtra2));
                                    obj.getClass();
                                    if (((ServiceStateInfo) obj).connected) {
                                        ICellBroadcastService iCellBroadcastService = latinNetworkNameProviderImpl.cellBroadcastService;
                                        if (iCellBroadcastService != null) {
                                            try {
                                                str = (String) iCellBroadcastService.getCellBroadcastAreaInfo(intExtra2);
                                            } catch (RemoteException e) {
                                                Log.d("LatinNetworkNameProvider", "Can't get cell broadcast msg on channel 50", e);
                                            }
                                            latinNetworkNameProviderImpl.cbMsgBody.put(Integer.valueOf(intExtra2), str);
                                            map = latinNetworkNameProviderImpl.cbMsgBody;
                                            if (map != null && !map.isEmpty()) {
                                                NetworkNameInfo networkNameInfo3 = (NetworkNameInfo) latinNetworkNameProviderImpl.networkNameHash.get(Integer.valueOf(intExtra2));
                                                String str2 = networkNameInfo3 != null ? networkNameInfo3.plmn : null;
                                                Object obj2 = latinNetworkNameProviderImpl.cbMsgBody.get(Integer.valueOf(intExtra2));
                                                StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(intExtra2, "CellBroadcast Message arrived. Slot=", " PLMN=", str2, " CbMsg=");
                                                sbM.append(obj2);
                                                Log.d("LatinNetworkNameProvider", sbM.toString());
                                                latinNetworkNameProviderImpl.showCBMsg = true;
                                            }
                                        } else {
                                            str = "";
                                            latinNetworkNameProviderImpl.cbMsgBody.put(Integer.valueOf(intExtra2), str);
                                            map = latinNetworkNameProviderImpl.cbMsgBody;
                                            if (map != null) {
                                                NetworkNameInfo networkNameInfo32 = (NetworkNameInfo) latinNetworkNameProviderImpl.networkNameHash.get(Integer.valueOf(intExtra2));
                                                if (networkNameInfo32 != null) {
                                                }
                                                Object obj22 = latinNetworkNameProviderImpl.cbMsgBody.get(Integer.valueOf(intExtra2));
                                                StringBuilder sbM2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(intExtra2, "CellBroadcast Message arrived. Slot=", " PLMN=", str2, " CbMsg=");
                                                sbM2.append(obj22);
                                                Log.d("LatinNetworkNameProvider", sbM2.toString());
                                                latinNetworkNameProviderImpl.showCBMsg = true;
                                            }
                                        }
                                    }
                                }
                            } else {
                                latinNetworkNameProviderImpl.broadcastCBClear(intExtra2);
                            }
                            ShadeCarrierGroupController$$ExternalSyntheticLambda5 shadeCarrierGroupController$$ExternalSyntheticLambda5 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
                            if (shadeCarrierGroupController$$ExternalSyntheticLambda5 != null) {
                                shadeCarrierGroupController$$ExternalSyntheticLambda5.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
                                break;
                            }
                        }
                        break;
                    case -576984847:
                        if (action.equals("android.telephony.action.SERVICE_PROVIDERS_UPDATED")) {
                            int intExtra3 = intent.getIntExtra("phone", 0);
                            boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
                            String stringExtra = intent.getStringExtra("android.telephony.extra.SPN");
                            String stringExtra2 = intent.getStringExtra("android.telephony.extra.DATA_SPN");
                            boolean booleanExtra3 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
                            String stringExtra3 = intent.getStringExtra("android.telephony.extra.PLMN");
                            boolean booleanExtra4 = intent.getBooleanExtra("showEpdg", false);
                            latinNetworkNameProviderImpl.getClass();
                            NetworkNameInfo networkNameInfo4 = new NetworkNameInfo(booleanExtra2, stringExtra, stringExtra2, booleanExtra3, stringExtra3, booleanExtra4);
                            latinNetworkNameProviderImpl.networkNameHash.put(Integer.valueOf(intExtra3), networkNameInfo4);
                            Log.d("LatinNetworkNameProvider", "updateNetworkName [" + intExtra3 + "] " + networkNameInfo4);
                            break;
                        }
                        break;
                    case -511271086:
                        if (action.equals("android.location.MODE_CHANGED")) {
                            latinNetworkNameProviderImpl.registerLocationListener();
                            break;
                        }
                        break;
                    case -229777127:
                        if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                            int intExtra4 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                            String stringExtra4 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                            if (intExtra4 != -1) {
                                latinNetworkNameProviderImpl.simState.put(Integer.valueOf(intExtra4), stringExtra4);
                            } else if (StringsKt__StringsJVMKt.equals(stringExtra4, "ABSENT", false)) {
                                latinNetworkNameProviderImpl.serviceStateHash.remove(Integer.valueOf(intExtra4));
                                latinNetworkNameProviderImpl.networkNameHash.remove(Integer.valueOf(intExtra4));
                                latinNetworkNameProviderImpl.simState.remove(Integer.valueOf(intExtra4));
                            }
                            ShadeCarrierGroupController$$ExternalSyntheticLambda5 shadeCarrierGroupController$$ExternalSyntheticLambda52 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
                            if (shadeCarrierGroupController$$ExternalSyntheticLambda52 != null) {
                                shadeCarrierGroupController$$ExternalSyntheticLambda52.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
                                break;
                            }
                        }
                        break;
                    case -182744469:
                        if (action.equals("com.sec.android.app.UPDATE_NETWORK_EMERGENCY_ONLY")) {
                            String stringExtra5 = intent.getStringExtra("network_manually_selected");
                            int intExtra5 = intent.getIntExtra("network_manually_selected_phone_id", 0);
                            if (stringExtra5 != null && intExtra5 >= 0) {
                                latinNetworkNameProviderImpl.networkManuallySelected = stringExtra5;
                                break;
                            }
                        }
                        break;
                    case 1195534238:
                        if (action.equals("com.sec.android.app.mms.CB_CLEAR")) {
                            int intExtra6 = intent.getIntExtra("phone", 0);
                            ListPopupWindow$$ExternalSyntheticOutline0.m(intExtra6, "CB is being cleared on slot=", "LatinNetworkNameProvider");
                            HashMap map2 = latinNetworkNameProviderImpl.cbMsgBody;
                            if (map2 != null && !map2.isEmpty() && latinNetworkNameProviderImpl.cbMsgBody.containsKey(Integer.valueOf(intExtra6))) {
                                latinNetworkNameProviderImpl.cbMsgBody.remove(Integer.valueOf(intExtra6));
                            }
                            ShadeCarrierGroupController$$ExternalSyntheticLambda5 shadeCarrierGroupController$$ExternalSyntheticLambda53 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
                            if (shadeCarrierGroupController$$ExternalSyntheticLambda53 != null) {
                                shadeCarrierGroupController$$ExternalSyntheticLambda53.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
                                break;
                            }
                        }
                        break;
                }
            }
        }
    }
}
