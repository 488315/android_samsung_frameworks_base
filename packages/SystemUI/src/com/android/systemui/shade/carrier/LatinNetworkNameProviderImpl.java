package com.android.systemui.shade.carrier;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.SystemProperties;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.CellLocation;
import android.telephony.ICellBroadcastService;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class LatinNetworkNameProviderImpl implements LatinNetworkNameProvider, Dumpable {
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final CarrierInfraMediator carrierInfraMediator;
    public final HashMap cbMsgBody;
    public ICellBroadcastService cellBroadcastService;
    public final Context context;
    public final DumpManager dumpManager;
    public final ImsRegStateUtil imsRegStateUtil;
    public boolean isAirplaneMode;
    public ShadeCarrierGroupController$$ExternalSyntheticLambda5 latinNetworkNameCallback;
    public final LocationController locationController;
    public final String mNetworkNameSeparator;
    public String networkManuallySelected;
    public boolean showCBMsg;
    public final SubscriptionManager subscriptionManager;
    public final SubscriptionsOrder subscriptionsOrder;
    public final TelephonyManager telephonyManager;
    public final HashMap serviceStateHash = new HashMap();
    public final HashMap networkNameHash = new HashMap();
    public final HashMap simState = new HashMap();
    public final LatinNetworkNameProviderImpl$broadcastReceiver$1 broadcastReceiver = new LatinNetworkNameProviderImpl$broadcastReceiver$1(this);
    public final CellLocationChangedCallback cellLocationCallback0 = new CellLocationChangedCallback(0, new LatinNetworkNameProviderImpl$cellLocationCallback0$1(this));
    public final CellLocationChangedCallback cellLocationCallback1 = new CellLocationChangedCallback(1, new LatinNetworkNameProviderImpl$cellLocationCallback1$1(this));
    public final LatinNetworkNameProviderImpl$cellBroadcastServiceConnection$1 cellBroadcastServiceConnection = new ServiceConnection() { // from class: com.android.systemui.shade.carrier.LatinNetworkNameProviderImpl$cellBroadcastServiceConnection$1
        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            Log.d("LatinNetworkNameProvider", "Binding died");
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Log.d("LatinNetworkNameProvider", "Null binding");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("LatinNetworkNameProvider", "connected to CellBroadcastService");
            this.this$0.cellBroadcastService = ICellBroadcastService.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("LatinNetworkNameProvider", "CellBroadcastService is disconnected unexpectedly");
            this.this$0.cellBroadcastService = null;
        }
    };

    public final class CellLocationChangedCallback extends TelephonyCallback implements TelephonyCallback.CellLocationListener {
        public final Function1 callback;
        public final int slotId;

        public CellLocationChangedCallback(int i, Function1 function1) {
            this.slotId = i;
            this.callback = function1;
        }

        @Override // android.telephony.TelephonyCallback.CellLocationListener
        public final void onCellLocationChanged(CellLocation cellLocation) {
            this.callback.mo781invoke(Integer.valueOf(this.slotId));
        }
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.shade.carrier.LatinNetworkNameProviderImpl$cellBroadcastServiceConnection$1] */
    public LatinNetworkNameProviderImpl(Context context, BroadcastDispatcher broadcastDispatcher, CarrierInfraMediator carrierInfraMediator, TelephonyManager telephonyManager, LocationController locationController, SubscriptionManager subscriptionManager, Executor executor, DumpManager dumpManager, ImsRegStateUtil imsRegStateUtil, SubscriptionsOrder subscriptionsOrder) throws Resources.NotFoundException {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.carrierInfraMediator = carrierInfraMediator;
        this.telephonyManager = telephonyManager;
        this.locationController = locationController;
        this.subscriptionManager = subscriptionManager;
        this.backgroundExecutor = executor;
        this.dumpManager = dumpManager;
        this.imsRegStateUtil = imsRegStateUtil;
        this.subscriptionsOrder = subscriptionsOrder;
        this.mNetworkNameSeparator = context.getResources().getString(R.string.shade_carrier_divider);
        context.getResources().getString(android.R.string.permlab_accessLastKnownCellId);
        this.cbMsgBody = new HashMap();
    }

    public static final void access$handleCellLocationChanged(LatinNetworkNameProviderImpl latinNetworkNameProviderImpl, int i) {
        latinNetworkNameProviderImpl.getClass();
        Log.d("LatinNetworkNameProvider", "onCellLocationChanged [" + i + "]");
        ServiceStateInfo serviceStateInfo = (ServiceStateInfo) latinNetworkNameProviderImpl.serviceStateHash.get(Integer.valueOf(i));
        if (serviceStateInfo != null) {
            if (isLatinGSM(serviceStateInfo.networkType, serviceStateInfo.voiceNetworkType)) {
                if (latinNetworkNameProviderImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.DISPLAY_CBCH50, 0, new Object[0])) {
                    latinNetworkNameProviderImpl.broadcastCBClear(i);
                    return;
                }
                return;
            }
            ShadeCarrierGroupController$$ExternalSyntheticLambda5 shadeCarrierGroupController$$ExternalSyntheticLambda5 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
            if (shadeCarrierGroupController$$ExternalSyntheticLambda5 != null) {
                shadeCarrierGroupController$$ExternalSyntheticLambda5.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
            }
        }
    }

    public static boolean isLatinGSM(int i, int i2) {
        if (i == 0 || i == 18) {
            i = i2;
        }
        return i == 1 || i == 2 || i == 16;
    }

    public final void broadcastCBClear(int i) {
        Intent intent = new Intent("com.sec.android.app.mms.CB_CLEAR");
        intent.putExtra("phone", i);
        this.context.sendBroadcast(intent);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        for (Map.Entry entry : this.networkNameHash.entrySet()) {
            printWriter.println("network name[" + entry.getKey() + "] " + entry.getValue());
        }
        for (Map.Entry entry2 : this.serviceStateHash.entrySet()) {
            printWriter.println("service state[" + entry2.getKey() + "] " + entry2.getValue());
        }
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isAirplaneMode=", this.isAirplaneMode);
    }

    public final String getCombinedNetworkName() {
        NetworkNameInfo networkNameInfo;
        NetworkNameInfo networkNameInfo2;
        boolean z = ((this.networkNameHash.get(0) == null || (networkNameInfo2 = (NetworkNameInfo) this.networkNameHash.get(0)) == null || !networkNameInfo2.hasVoWifiPLMN) && (this.networkNameHash.get(1) == null || (networkNameInfo = (NetworkNameInfo) this.networkNameHash.get(1)) == null || !networkNameInfo.hasVoWifiPLMN)) ? false : true;
        if (this.isAirplaneMode && !z) {
            String string = this.context.getString(R.string.kg_flight_mode);
            string.getClass();
            return string;
        }
        String latinNetworkName = "";
        if (((NetworkNameInfo) this.networkNameHash.get(0)) != null && !StringsKt__StringsJVMKt.equals((String) this.simState.get(0), "UNKNOWN", false) && SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(0))) {
            latinNetworkName = getLatinNetworkName(0);
        }
        if (((NetworkNameInfo) this.networkNameHash.get(1)) != null && !StringsKt__StringsJVMKt.equals((String) this.simState.get(1), "UNKNOWN", false) && SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(1))) {
            String latinNetworkName2 = getLatinNetworkName(1);
            if (latinNetworkName.length() <= 0 || latinNetworkName2.length() <= 0) {
                latinNetworkName = ((Object) latinNetworkName) + latinNetworkName2;
            } else {
                SubscriptionsOrder subscriptionsOrder = this.subscriptionsOrder;
                subscriptionsOrder.getClass();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < 2; i++) {
                    SubscriptionInfo activeSubscriptionInfo = subscriptionsOrder.subscriptionManager.getActiveSubscriptionInfo(SubscriptionManager.getSubscriptionId(i));
                    if (activeSubscriptionInfo != null) {
                        arrayList.add(activeSubscriptionInfo);
                    }
                }
                int simOrder = subscriptionsOrder.getSimOrder(SubscriptionManager.getSubscriptionId(0), arrayList);
                String str = this.mNetworkNameSeparator;
                if (simOrder != 0) {
                    Log.d("LatinNetworkNameProvider", "subscriptionsOrder should be REVERSED");
                    latinNetworkName = (latinNetworkName2 + str) + ((Object) latinNetworkName);
                } else {
                    latinNetworkName = ((Object) (((Object) latinNetworkName) + str)) + latinNetworkName2;
                }
            }
        }
        Log.d("LatinNetworkNameProvider", "getCombinedNetworkName : carrierText - " + ((Object) latinNetworkName));
        return latinNetworkName;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0212  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getLatinNetworkName(int i) {
        String strValueOf;
        String str;
        String str2;
        String strM;
        String str3;
        String string;
        NetworkNameInfo networkNameInfo;
        NetworkNameInfo networkNameInfo2;
        NetworkNameInfo networkNameInfo3;
        ServiceStateInfo serviceStateInfo = (ServiceStateInfo) this.serviceStateHash.get(Integer.valueOf(i));
        if (serviceStateInfo == null) {
            return "";
        }
        if (isLatinGSM(serviceStateInfo.networkType, serviceStateInfo.voiceNetworkType)) {
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = this.subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
            if (activeSubscriptionInfoForSimSlotIndex != null) {
                string = activeSubscriptionInfoForSimSlotIndex.getCarrierName().toString();
                if (this.showCBMsg && this.cbMsgBody.containsKey(Integer.valueOf(i)) && !StringsKt__StringsJVMKt.equals((String) this.cbMsgBody.get(Integer.valueOf(i)), "", false)) {
                    if (string.length() > 0) {
                        string = string.concat(" / ");
                    }
                    string = string + this.cbMsgBody.get(Integer.valueOf(i));
                }
                if (string == null) {
                    string = "";
                }
            }
        } else {
            NetworkNameInfo networkNameInfo4 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
            Boolean boolValueOf = networkNameInfo4 != null ? Boolean.valueOf(networkNameInfo4.showSpn) : null;
            NetworkNameInfo networkNameInfo5 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
            String str4 = networkNameInfo5 != null ? networkNameInfo5.spn : null;
            NetworkNameInfo networkNameInfo6 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
            String str5 = networkNameInfo6 != null ? networkNameInfo6.dataSpn : null;
            NetworkNameInfo networkNameInfo7 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
            Boolean boolValueOf2 = networkNameInfo7 != null ? Boolean.valueOf(networkNameInfo7.showPlmn) : null;
            NetworkNameInfo networkNameInfo8 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
            String str6 = networkNameInfo8 != null ? networkNameInfo8.plmn : null;
            ImsRegStateUtil imsRegStateUtil = this.imsRegStateUtil;
            boolean zIsVoWifiConnected = imsRegStateUtil.isVoWifiConnected(i);
            String str7 = this.networkManuallySelected;
            StringBuilder sb = new StringBuilder("updateNetworkNameLatin: showSpn=");
            sb.append(boolValueOf);
            sb.append(", spn=");
            sb.append(str4);
            sb.append(", dataSpn=");
            sb.append(str5);
            sb.append(", showPlmn=");
            sb.append(boolValueOf2);
            sb.append(", plmn=");
            sb.append(str6);
            sb.append(", voWifiConnected=");
            sb.append(zIsVoWifiConnected);
            sb.append(", networkManuallySelected=");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str7, "LatinNetworkNameProvider");
            CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CLARO_PLMN, i, new Object[0]) && imsRegStateUtil.isVoWifiConnected(i)) {
                NetworkNameInfo networkNameInfo9 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                if (networkNameInfo9 == null || (string = networkNameInfo9.plmn) == null) {
                    string = "";
                }
                NetworkNameInfo networkNameInfo10 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                if ((networkNameInfo10 != null ? networkNameInfo10.spn : null) != null) {
                    NetworkNameInfo networkNameInfo11 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                    if (!"".equals(networkNameInfo11 != null ? networkNameInfo11.dataSpn : null)) {
                        NetworkNameInfo networkNameInfo12 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                        str = networkNameInfo12 != null ? networkNameInfo12.dataSpn : null;
                        str.getClass();
                        string = str;
                    }
                }
            } else if (this.isAirplaneMode) {
                if (this.networkNameHash.get(Integer.valueOf(i)) != null && (networkNameInfo = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i))) != null && networkNameInfo.hasVoWifiPLMN) {
                    NetworkNameInfo networkNameInfo13 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                    if (networkNameInfo13 == null || !networkNameInfo13.showPlmn) {
                        string = "";
                        networkNameInfo2 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                        if (networkNameInfo2 != null && networkNameInfo2.showSpn) {
                            networkNameInfo3 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                            if ((networkNameInfo3 == null ? networkNameInfo3.spn : null) != null) {
                                if (string.length() > 0) {
                                    StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                                    sbM.append(this.mNetworkNameSeparator);
                                    string = sbM.toString();
                                }
                                NetworkNameInfo networkNameInfo14 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                                strValueOf = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, networkNameInfo14 != null ? networkNameInfo14.spn : null);
                                string = strValueOf;
                            }
                        }
                    } else {
                        NetworkNameInfo networkNameInfo15 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                        if ((networkNameInfo15 != null ? networkNameInfo15.plmn : null) != null) {
                            NetworkNameInfo networkNameInfo16 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                            string = String.valueOf(networkNameInfo16 != null ? networkNameInfo16.plmn : null);
                        }
                        networkNameInfo2 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                        if (networkNameInfo2 != null) {
                            networkNameInfo3 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                            if ((networkNameInfo3 == null ? networkNameInfo3.spn : null) != null) {
                            }
                        }
                    }
                }
            } else if (this.serviceStateHash.get(Integer.valueOf(i)) != null) {
                Object obj = this.serviceStateHash.get(Integer.valueOf(i));
                obj.getClass();
                if (!((ServiceStateInfo) obj).isEmergency) {
                    if (this.networkNameHash.get(Integer.valueOf(i)) != null) {
                        Object obj2 = this.networkNameHash.get(Integer.valueOf(i));
                        obj2.getClass();
                        if (((NetworkNameInfo) obj2).plmn != null) {
                            String string2 = this.context.getString(android.R.string.keyguard_accessibility_pin_unlock);
                            Object obj3 = this.networkNameHash.get(Integer.valueOf(i));
                            obj3.getClass();
                            if (Intrinsics.areEqual(string2, ((NetworkNameInfo) obj3).plmn)) {
                                if (i < 0 || (str3 = this.networkManuallySelected) == null || str3.length() <= 0) {
                                    Object obj4 = this.networkNameHash.get(Integer.valueOf(i));
                                    obj4.getClass();
                                    strValueOf = ((NetworkNameInfo) obj4).plmn;
                                    if (strValueOf == null) {
                                    }
                                } else {
                                    String str8 = this.networkManuallySelected;
                                    Object obj5 = this.networkNameHash.get(Integer.valueOf(i));
                                    obj5.getClass();
                                    StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str8, " ");
                                    sbM2.append(((NetworkNameInfo) obj5).plmn);
                                    strValueOf = sbM2.toString();
                                }
                            }
                            string = strValueOf;
                        }
                    }
                    if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.MULTI_LINE_CARRIER_LABEL, 0, new Object[0])) {
                        String str9 = SystemProperties.get("gsm.sim.operator.numeric");
                        int length = str9.length() - 1;
                        int i2 = 0;
                        boolean z = false;
                        while (i2 <= length) {
                            boolean z2 = Intrinsics.compare(str9.charAt(!z ? i2 : length), 32) <= 0;
                            if (z) {
                                if (!z2) {
                                    break;
                                }
                                length--;
                            } else if (z2) {
                                i2++;
                            } else {
                                z = true;
                            }
                        }
                        String[] strArr = (String[]) StringsKt__StringsKt.split$default(str9.subSequence(i2, length + 1).toString(), new String[]{","}, 0, 6).toArray(new String[0]);
                        String str10 = SystemProperties.get("gsm.operator.numeric");
                        int length2 = str10.length() - 1;
                        int i3 = 0;
                        boolean z3 = false;
                        while (i3 <= length2) {
                            boolean z4 = Intrinsics.compare(str10.charAt(!z3 ? i3 : length2), 32) <= 0;
                            if (z3) {
                                if (!z4) {
                                    break;
                                }
                                length2--;
                            } else if (z4) {
                                i3++;
                            } else {
                                z3 = true;
                            }
                        }
                        String[] strArr2 = (String[]) StringsKt__StringsKt.split$default(str10.subSequence(i3, length2 + 1).toString(), new String[]{","}, 0, 6).toArray(new String[0]);
                        if (CollectionsKt__CollectionsKt.arrayListOf("72406", "72410", "72411", "72423").contains(strArr[i])) {
                            if (CollectionsKt__CollectionsKt.arrayListOf("72406", "72410", "72411", "72423").contains(strArr2[i])) {
                                NetworkNameInfo networkNameInfo17 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                                if (networkNameInfo17 == null || (str = networkNameInfo17.plmn) == null) {
                                    str = "";
                                }
                                NetworkNameInfo networkNameInfo18 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                                if (networkNameInfo18 == null || (str2 = networkNameInfo18.spn) == null) {
                                    str2 = "";
                                }
                                NetworkNameInfo networkNameInfo19 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                                boolean z5 = networkNameInfo19 != null ? networkNameInfo19.showSpn : false;
                                NetworkNameInfo networkNameInfo20 = (NetworkNameInfo) this.networkNameHash.get(Integer.valueOf(i));
                                boolean z6 = networkNameInfo20 != null ? networkNameInfo20.showPlmn : false;
                                ServiceStateInfo serviceStateInfo2 = (ServiceStateInfo) this.serviceStateHash.get(Integer.valueOf(i));
                                if (serviceStateInfo2 != null) {
                                    if (!serviceStateInfo2.isRoaming) {
                                        String[] strArr3 = (String[]) StringsKt__StringsKt.split$default(SystemProperties.get("gsm.operator.isroaming", "false, false"), new String[]{","}, 0, 6).toArray(new String[0]);
                                        if (strArr3.length <= 1 ? !Intrinsics.areEqual(strArr3[0], "true") : !Intrinsics.areEqual(strArr3[i], "true")) {
                                            if (serviceStateInfo2.connected && !serviceStateInfo2.isEmergency && !TextUtils.isEmpty(str2)) {
                                                StringBuilder sb2 = new StringBuilder();
                                                try {
                                                    String networkOperator = this.telephonyManager.getNetworkOperator();
                                                    networkOperator.getClass();
                                                    if (networkOperator.length() > 0 && !networkOperator.equals("") && Integer.parseInt(networkOperator) > 0) {
                                                        sb2 = setAreaCode();
                                                    }
                                                } catch (Exception e) {
                                                    EmergencyButton$$ExternalSyntheticOutline0.m("getAreaInfo ", e, "LatinNetworkNameProvider");
                                                }
                                                String string3 = sb2.toString();
                                                if (!z6) {
                                                    strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, string3);
                                                } else if (str.startsWith(str2) && str.equals(str2)) {
                                                    strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, string3);
                                                } else {
                                                    strM = str + " " + str2 + string3;
                                                }
                                                str = strM;
                                            }
                                            Log.d("LatinNetworkNameProvider", "updateNetworkNameLatinLAC=" + ((Object) str));
                                        } else {
                                            if (!TextUtils.isEmpty(str)) {
                                                if (z6 && z5 && !TextUtils.isEmpty(str2)) {
                                                    str = ((Object) str) + " " + str2;
                                                }
                                            }
                                            Log.d("LatinNetworkNameProvider", "updateNetworkNameLatinLAC=" + ((Object) str));
                                        }
                                    }
                                }
                                string = str;
                            }
                        }
                    }
                    SubscriptionInfo activeSubscriptionInfoForSimSlotIndex2 = this.subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
                    strValueOf = String.valueOf(activeSubscriptionInfoForSimSlotIndex2 != null ? activeSubscriptionInfoForSimSlotIndex2.getCarrierName() : null);
                    string = strValueOf;
                }
            }
        }
        return string == null ? "" : string;
    }

    public final void registerLocationListener() {
        ListPopupWindow$$ExternalSyntheticOutline0.m(this.serviceStateHash.size(), "registerLocationListener subscriptions=", "LatinNetworkNameProvider");
        Iterator it = this.serviceStateHash.entrySet().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) ((Map.Entry) it.next()).getKey()).intValue();
            TelephonyManager telephonyManagerCreateForSubscriptionId = this.telephonyManager.createForSubscriptionId(SubscriptionManager.getSubscriptionId(iIntValue));
            CellLocationChangedCallback cellLocationChangedCallback = iIntValue == 0 ? this.cellLocationCallback0 : this.cellLocationCallback1;
            if (((LocationControllerImpl) this.locationController).isLocationEnabled$1()) {
                telephonyManagerCreateForSubscriptionId.registerTelephonyCallback(this.backgroundExecutor, cellLocationChangedCallback);
                StringBuilder sb = new StringBuilder("Location is enabled, start listening cell location [");
                sb.append(iIntValue);
                ExifInterface$$ExternalSyntheticOutline0.m(sb, "]", "LatinNetworkNameProvider");
            } else {
                telephonyManagerCreateForSubscriptionId.unregisterTelephonyCallback(cellLocationChangedCallback);
                Log.d("LatinNetworkNameProvider", "Location is turned off, stop listening cell location [" + iIntValue + "]");
            }
        }
    }

    public final StringBuilder setAreaCode() {
        String str;
        int phoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        ListPopupWindow$$ExternalSyntheticOutline0.m(phoneId, "getDefaultDataPhoneId ", "LatinNetworkNameProvider");
        if (phoneId < 0) {
            phoneId = 0;
        } else if (phoneId > 1) {
            phoneId = 1;
        }
        StringBuilder sb = new StringBuilder();
        CellLocation cellLocationForPhone = this.telephonyManager.getCellLocationForPhone(phoneId);
        if (cellLocationForPhone == null) {
            return sb;
        }
        int lac = ((GsmCellLocation) cellLocationForPhone).getLac();
        if (lac != -1 && lac != 255 && lac != 0 && lac != 65535) {
            int i = lac % 100;
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    str = "SP";
                    break;
                case 20:
                case 23:
                case 25:
                case 26:
                case 29:
                case 30:
                case 36:
                case 39:
                case 40:
                case 50:
                case 52:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 70:
                case 72:
                case 76:
                case 78:
                case 80:
                case 90:
                default:
                    str = "";
                    break;
                case 21:
                case 22:
                case 24:
                    str = "RJ";
                    break;
                case 27:
                case 28:
                    str = "ES";
                    break;
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 37:
                case 38:
                    str = "MG";
                    break;
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                    str = "PR";
                    break;
                case 47:
                case 48:
                case 49:
                    str = "SC";
                    break;
                case 51:
                case 53:
                case 54:
                case 55:
                    str = "RS";
                    break;
                case 61:
                    str = "DF";
                    break;
                case 62:
                case 64:
                    str = "GO";
                    break;
                case 63:
                    str = "TO";
                    break;
                case 65:
                case 66:
                    str = "MT";
                    break;
                case 67:
                    str = "MS";
                    break;
                case 68:
                    str = "AC";
                    break;
                case 69:
                    str = "RO";
                    break;
                case 71:
                case 73:
                case 74:
                case 75:
                case 77:
                    str = "BA";
                    break;
                case 79:
                    str = "SE";
                    break;
                case 81:
                case 87:
                    str = "PE";
                    break;
                case 82:
                    str = "AL";
                    break;
                case 83:
                    str = "PB";
                    break;
                case 84:
                    str = "RN";
                    break;
                case 85:
                case 88:
                    str = "CE";
                    break;
                case 86:
                case 89:
                    str = "PI";
                    break;
                case 91:
                case 93:
                case 94:
                    str = "PA";
                    break;
                case 92:
                case 97:
                    str = "AM";
                    break;
                case 95:
                    str = "RR";
                    break;
                case 96:
                    str = "AP";
                    break;
                case 98:
                case 99:
                    str = "MA";
                    break;
            }
            sb.append(" " + str + " " + i);
        }
        Log.d("LatinNetworkNameProvider", "setAreaCode areaInfo=" + ((Object) sb));
        return sb;
    }

    public final void unregisterLocationListener() {
        Iterator it = this.serviceStateHash.entrySet().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) ((Map.Entry) it.next()).getKey()).intValue();
            this.telephonyManager.createForSubscriptionId(SubscriptionManager.getSubscriptionId(iIntValue)).unregisterTelephonyCallback(iIntValue == 0 ? this.cellLocationCallback0 : this.cellLocationCallback1);
        }
    }
}
