package com.android.systemui.shade.carrier;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.CellLocation;
import android.telephony.ICellBroadcastService;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

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
            LatinNetworkNameProviderImpl.this.cellBroadcastService = ICellBroadcastService.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("LatinNetworkNameProvider", "CellBroadcastService is disconnected unexpectedly");
            LatinNetworkNameProviderImpl.this.cellBroadcastService = null;
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
            this.callback.invoke(Integer.valueOf(this.slotId));
        }
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.shade.carrier.LatinNetworkNameProviderImpl$cellBroadcastServiceConnection$1] */
    public LatinNetworkNameProviderImpl(Context context, BroadcastDispatcher broadcastDispatcher, CarrierInfraMediator carrierInfraMediator, TelephonyManager telephonyManager, LocationController locationController, SubscriptionManager subscriptionManager, Executor executor, DumpManager dumpManager, ImsRegStateUtil imsRegStateUtil, SubscriptionsOrder subscriptionsOrder) {
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
        context.getResources().getString(android.R.string.permdesc_runInBackground);
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
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isAirplaneMode=", this.isAirplaneMode, printWriter);
    }

    public final String getCombinedNetworkName() {
        NetworkNameInfo networkNameInfo;
        NetworkNameInfo networkNameInfo2;
        boolean z = ((this.networkNameHash.get(0) == null || (networkNameInfo2 = (NetworkNameInfo) this.networkNameHash.get(0)) == null || !networkNameInfo2.hasVoWifiPLMN) && (this.networkNameHash.get(1) == null || (networkNameInfo = (NetworkNameInfo) this.networkNameHash.get(1)) == null || !networkNameInfo.hasVoWifiPLMN)) ? false : true;
        if (this.isAirplaneMode && !z) {
            String string = this.context.getString(R.string.kg_flight_mode);
            Intrinsics.checkNotNull(string);
            return string;
        }
        String str = "";
        if (((NetworkNameInfo) this.networkNameHash.get(0)) != null && !StringsKt__StringsJVMKt.equals((String) this.simState.get(0), "UNKNOWN", false) && SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(0))) {
            str = getLatinNetworkName(0);
        }
        if (((NetworkNameInfo) this.networkNameHash.get(1)) != null && !StringsKt__StringsJVMKt.equals((String) this.simState.get(1), "UNKNOWN", false) && SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(1))) {
            String latinNetworkName = getLatinNetworkName(1);
            if (str.length() <= 0 || latinNetworkName.length() <= 0) {
                str = ((Object) str) + latinNetworkName;
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
                String str2 = this.mNetworkNameSeparator;
                if (simOrder != 0) {
                    Log.d("LatinNetworkNameProvider", "subscriptionsOrder should be REVERSED");
                    str = (latinNetworkName + str2) + ((Object) str);
                } else {
                    str = ((Object) (((Object) str) + str2)) + latinNetworkName;
                }
            }
        }
        Log.d("LatinNetworkNameProvider", "getCombinedNetworkName : carrierText - " + ((Object) str));
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x025c, code lost:
    
        if (((com.android.systemui.shade.carrier.ServiceStateInfo) r6).isEmergency == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x029d, code lost:
    
        if (r17 < 0) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x029f, code lost:
    
        r2 = r16.networkManuallySelected;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x02a1, code lost:
    
        if (r2 == null) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x02a7, code lost:
    
        if (r2.length() <= 0) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x02a9, code lost:
    
        r2 = r16.networkManuallySelected;
        r0 = r16.networkNameHash.get(java.lang.Integer.valueOf(r17));
        kotlin.jvm.internal.Intrinsics.checkNotNull(r0);
        r1 = android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r2, " ");
        r1.append(((com.android.systemui.shade.carrier.NetworkNameInfo) r0).plmn);
        r0 = r1.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x02c8, code lost:
    
        r0 = r16.networkNameHash.get(java.lang.Integer.valueOf(r17));
        kotlin.jvm.internal.Intrinsics.checkNotNull(r0);
        r0 = ((com.android.systemui.shade.carrier.NetworkNameInfo) r0).plmn;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x02d9, code lost:
    
        if (r0 != null) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x02db, code lost:
    
        r0 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x029b, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r6, ((com.android.systemui.shade.carrier.NetworkNameInfo) r10).plmn) != false) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0076, code lost:
    
        if (r2 == null) goto L19;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x049d  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x04a3  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0212  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getLatinNetworkName(int r17) {
        /*
            Method dump skipped, instructions count: 1272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.carrier.LatinNetworkNameProviderImpl.getLatinNetworkName(int):java.lang.String");
    }

    public final boolean isUseLatinNetworkName() {
        return this.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.DISPLAY_CBCH50, 0, new Object[0]) && (this.subscriptionManager.getCompleteActiveSubscriptionInfoList().isEmpty() ^ true);
    }

    public final void registerLocationListener() {
        ListPopupWindow$$ExternalSyntheticOutline0.m(this.serviceStateHash.size(), "registerLocationListener subscriptions=", "LatinNetworkNameProvider");
        Iterator it = this.serviceStateHash.entrySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Number) ((Map.Entry) it.next()).getKey()).intValue();
            TelephonyManager createForSubscriptionId = this.telephonyManager.createForSubscriptionId(SubscriptionManager.getSubscriptionId(intValue));
            CellLocationChangedCallback cellLocationChangedCallback = intValue == 0 ? this.cellLocationCallback0 : this.cellLocationCallback1;
            if (((LocationControllerImpl) this.locationController).isLocationEnabled$1()) {
                createForSubscriptionId.registerTelephonyCallback(this.backgroundExecutor, cellLocationChangedCallback);
                StringBuilder sb = new StringBuilder("Location is enabled, start listening cell location [");
                sb.append(intValue);
                ExifInterface$$ExternalSyntheticOutline0.m(sb, "]", "LatinNetworkNameProvider");
            } else {
                createForSubscriptionId.unregisterTelephonyCallback(cellLocationChangedCallback);
                Log.d("LatinNetworkNameProvider", "Location is turned off, stop listening cell location [" + intValue + "]");
            }
        }
    }

    public final StringBuilder setAreaCode(int i) {
        String str;
        int subscriptionId = SubscriptionManager.getSubscriptionId(i);
        StringBuilder sb = new StringBuilder();
        CellLocation cellLocationBySubId = this.telephonyManager.getCellLocationBySubId(subscriptionId);
        if (cellLocationBySubId == null) {
            return sb;
        }
        int lac = ((GsmCellLocation) cellLocationBySubId).getLac();
        if (lac != -1 && lac != 255 && lac != 0 && lac != 65535) {
            int i2 = lac % 100;
            switch (i2) {
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
            sb.append(" " + str + " " + i2);
        }
        Log.d("LatinNetworkNameProvider", "setAreaCode areaInfo=" + ((Object) sb));
        return sb;
    }

    public final void unregisterLocationListener() {
        Iterator it = this.serviceStateHash.entrySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Number) ((Map.Entry) it.next()).getKey()).intValue();
            this.telephonyManager.createForSubscriptionId(SubscriptionManager.getSubscriptionId(intValue)).unregisterTelephonyCallback(intValue == 0 ? this.cellLocationCallback0 : this.cellLocationCallback1);
        }
    }
}
