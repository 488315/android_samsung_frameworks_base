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
import com.android.keyguard.AbstractC0662xaf167275;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LatinNetworkNameProviderImpl$broadcastReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ LatinNetworkNameProviderImpl this$0;

    public LatinNetworkNameProviderImpl$broadcastReceiver$1(LatinNetworkNameProviderImpl latinNetworkNameProviderImpl) {
        this.this$0 = latinNetworkNameProviderImpl;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01dc  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        String str;
        HashMap hashMap;
        if (intent != null) {
            LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = this.this$0;
            String action = intent.getAction();
            if (action != null) {
                boolean z = true;
                switch (action.hashCode()) {
                    case -2104353374:
                        if (action.equals("android.intent.action.SERVICE_STATE")) {
                            int intExtra = intent.getIntExtra("slot", 0);
                            latinNetworkNameProviderImpl.getClass();
                            if (SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(intExtra))) {
                                Bundle extras = intent.getExtras();
                                ServiceState newFromBundle = extras != null ? ServiceState.newFromBundle(extras) : null;
                                if (newFromBundle != null) {
                                    ServiceStateInfo serviceStateInfo = new ServiceStateInfo(newFromBundle.getRilDataRadioTechnology(), newFromBundle.getRilVoiceRadioTechnology(), newFromBundle.isEmergencyOnly(), newFromBundle.getRoaming(), newFromBundle.getState() == 0);
                                    HashMap hashMap2 = latinNetworkNameProviderImpl.serviceStateHash;
                                    ServiceStateInfo serviceStateInfo2 = (ServiceStateInfo) hashMap2.get(Integer.valueOf(intExtra));
                                    if (serviceStateInfo2 != null && LatinNetworkNameProviderImpl.isLatinGSM(serviceStateInfo2.networkType, serviceStateInfo2.voiceNetworkType) && !LatinNetworkNameProviderImpl.isLatinGSM(serviceStateInfo.networkType, serviceStateInfo.voiceNetworkType)) {
                                        latinNetworkNameProviderImpl.broadcastCBClear(intExtra);
                                    }
                                    int size = hashMap2.size();
                                    hashMap2.put(Integer.valueOf(intExtra), serviceStateInfo);
                                    if (size != hashMap2.size()) {
                                        Log.d("LatinNetworkNameProvider", "There's changes in Subscriptions " + size + " -> " + hashMap2.size());
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
                            HashMap hashMap3 = latinNetworkNameProviderImpl.networkNameHash;
                            if (intExtra2 == -1) {
                                NetworkNameInfo networkNameInfo = (NetworkNameInfo) hashMap3.get(Integer.valueOf(intExtra2));
                                Log.d("LatinNetworkNameProvider", "[INVALID_SIM_SLOT_INDEX] slotId=" + intExtra2 + " plmn=" + (networkNameInfo != null ? networkNameInfo.plmn : null));
                                latinNetworkNameProviderImpl.showCBMsg = false;
                            } else if (booleanExtra) {
                                NetworkNameInfo networkNameInfo2 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(intExtra2));
                                if (networkNameInfo2 != null && networkNameInfo2.plmn != null) {
                                    HashMap hashMap4 = latinNetworkNameProviderImpl.serviceStateHash;
                                    if (hashMap4.get(Integer.valueOf(intExtra2)) != null) {
                                        Object obj = hashMap4.get(Integer.valueOf(intExtra2));
                                        Intrinsics.checkNotNull(obj);
                                        if (((ServiceStateInfo) obj).connected) {
                                            ICellBroadcastService iCellBroadcastService = latinNetworkNameProviderImpl.cellBroadcastService;
                                            if (iCellBroadcastService != null) {
                                                try {
                                                    str = (String) iCellBroadcastService.getCellBroadcastAreaInfo(intExtra2);
                                                } catch (RemoteException e) {
                                                    Log.d("LatinNetworkNameProvider", "Can't get cell broadcast msg on channel 50", e);
                                                }
                                                hashMap = latinNetworkNameProviderImpl.cbMsgBody;
                                                hashMap.put(Integer.valueOf(intExtra2), str);
                                                if (!hashMap.isEmpty()) {
                                                    NetworkNameInfo networkNameInfo3 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(intExtra2));
                                                    String str2 = networkNameInfo3 != null ? networkNameInfo3.plmn : null;
                                                    Object obj2 = hashMap.get(Integer.valueOf(intExtra2));
                                                    StringBuilder m61m = AbstractC0662xaf167275.m61m("CellBroadcast Message arrived. Slot=", intExtra2, " PLMN=", str2, " CbMsg=");
                                                    m61m.append(obj2);
                                                    Log.d("LatinNetworkNameProvider", m61m.toString());
                                                    latinNetworkNameProviderImpl.showCBMsg = true;
                                                }
                                            }
                                            str = "";
                                            hashMap = latinNetworkNameProviderImpl.cbMsgBody;
                                            hashMap.put(Integer.valueOf(intExtra2), str);
                                            if (!hashMap.isEmpty()) {
                                            }
                                        }
                                    }
                                }
                            } else {
                                latinNetworkNameProviderImpl.broadcastCBClear(intExtra2);
                            }
                            ShadeCarrierGroupController$$ExternalSyntheticLambda2 shadeCarrierGroupController$$ExternalSyntheticLambda2 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
                            if (shadeCarrierGroupController$$ExternalSyntheticLambda2 != null) {
                                shadeCarrierGroupController$$ExternalSyntheticLambda2.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
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
                            } else if (stringExtra4.equals("ABSENT")) {
                                latinNetworkNameProviderImpl.serviceStateHash.remove(Integer.valueOf(intExtra4));
                                latinNetworkNameProviderImpl.networkNameHash.remove(Integer.valueOf(intExtra4));
                                latinNetworkNameProviderImpl.simState.remove(Integer.valueOf(intExtra4));
                            }
                            ShadeCarrierGroupController$$ExternalSyntheticLambda2 shadeCarrierGroupController$$ExternalSyntheticLambda22 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
                            if (shadeCarrierGroupController$$ExternalSyntheticLambda22 != null) {
                                shadeCarrierGroupController$$ExternalSyntheticLambda22.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
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
                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("CB is being cleared on slot=", intExtra6, "LatinNetworkNameProvider");
                            HashMap hashMap5 = latinNetworkNameProviderImpl.cbMsgBody;
                            if (hashMap5 != null && !hashMap5.isEmpty()) {
                                z = false;
                            }
                            if (!z) {
                                HashMap hashMap6 = latinNetworkNameProviderImpl.cbMsgBody;
                                if (hashMap6.containsKey(Integer.valueOf(intExtra6))) {
                                    hashMap6.remove(Integer.valueOf(intExtra6));
                                }
                            }
                            ShadeCarrierGroupController$$ExternalSyntheticLambda2 shadeCarrierGroupController$$ExternalSyntheticLambda23 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
                            if (shadeCarrierGroupController$$ExternalSyntheticLambda23 != null) {
                                shadeCarrierGroupController$$ExternalSyntheticLambda23.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
                                break;
                            }
                        }
                        break;
                }
            }
        }
    }
}
