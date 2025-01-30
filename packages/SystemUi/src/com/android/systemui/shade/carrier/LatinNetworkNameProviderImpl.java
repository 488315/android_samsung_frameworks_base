package com.android.systemui.shade.carrier;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemProperties;
import android.telephony.CellLocation;
import android.telephony.ICellBroadcastService;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
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
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public ShadeCarrierGroupController$$ExternalSyntheticLambda2 latinNetworkNameCallback;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        context.getResources().getString(android.R.string.permdesc_accessWimaxState);
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
            ShadeCarrierGroupController$$ExternalSyntheticLambda2 shadeCarrierGroupController$$ExternalSyntheticLambda2 = latinNetworkNameProviderImpl.latinNetworkNameCallback;
            if (shadeCarrierGroupController$$ExternalSyntheticLambda2 != null) {
                shadeCarrierGroupController$$ExternalSyntheticLambda2.updateCarrierInfo(latinNetworkNameProviderImpl.getCombinedNetworkName());
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
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("isAirplaneMode=", this.isAirplaneMode, printWriter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x0038, code lost:
    
        if ((r3 != null && r3.hasVoWifiPLMN) != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0021, code lost:
    
        if ((r3 != null && r3.hasVoWifiPLMN) == false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x003a, code lost:
    
        r3 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getCombinedNetworkName() {
        String str;
        HashMap hashMap = this.networkNameHash;
        if (hashMap.get(0) != null) {
            NetworkNameInfo networkNameInfo = (NetworkNameInfo) hashMap.get(0);
        }
        if (hashMap.get(1) != null) {
            NetworkNameInfo networkNameInfo2 = (NetworkNameInfo) hashMap.get(1);
        }
        boolean z = false;
        if (this.isAirplaneMode && !z) {
            return this.context.getString(R.string.kg_flight_mode);
        }
        NetworkNameInfo networkNameInfo3 = (NetworkNameInfo) hashMap.get(0);
        HashMap hashMap2 = this.simState;
        String latinNetworkName = (networkNameInfo3 == null || StringsKt__StringsJVMKt.equals((String) hashMap2.get(0), "UNKNOWN", false) || !SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(0))) ? "" : getLatinNetworkName(0);
        if (((NetworkNameInfo) hashMap.get(1)) != null && !StringsKt__StringsJVMKt.equals((String) hashMap2.get(1), "UNKNOWN", false) && SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getSubscriptionId(1))) {
            String latinNetworkName2 = getLatinNetworkName(1);
            if (latinNetworkName.length() > 0) {
                if (latinNetworkName2.length() > 0) {
                    SubscriptionsOrder subscriptionsOrder = this.subscriptionsOrder;
                    subscriptionsOrder.getClass();
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < 2; i++) {
                        SubscriptionInfo activeSubscriptionInfo = subscriptionsOrder.subscriptionManager.getActiveSubscriptionInfo(SubscriptionManager.getSubscriptionId(i));
                        if (activeSubscriptionInfo != null) {
                            arrayList.add(activeSubscriptionInfo);
                        }
                    }
                    boolean z2 = subscriptionsOrder.getSimOrder(SubscriptionManager.getSubscriptionId(0), arrayList) != 0;
                    String str2 = this.mNetworkNameSeparator;
                    if (z2) {
                        Log.d("LatinNetworkNameProvider", "subscriptionsOrder should be REVERSED");
                        str = (latinNetworkName2 + str2) + ((Object) latinNetworkName);
                    } else {
                        str = ((Object) (((Object) latinNetworkName) + str2)) + latinNetworkName2;
                    }
                    latinNetworkName = str;
                }
            }
            str = ((Object) latinNetworkName) + latinNetworkName2;
            latinNetworkName = str;
        }
        Log.d("LatinNetworkNameProvider", "getCombinedNetworkName : carrierText - " + ((Object) latinNetworkName));
        return latinNetworkName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x0252, code lost:
    
        if (((com.android.systemui.shade.carrier.ServiceStateInfo) r7).isEmergency == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x028d, code lost:
    
        if (r18 < 0) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x028f, code lost:
    
        r2 = r17.networkManuallySelected;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0291, code lost:
    
        if (r2 == null) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0297, code lost:
    
        if (r2.length() <= 0) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0299, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x029c, code lost:
    
        if (r2 == false) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x029e, code lost:
    
        r0 = r17.networkManuallySelected;
        r1 = r3.get(java.lang.Integer.valueOf(r18));
        kotlin.jvm.internal.Intrinsics.checkNotNull(r1);
        r0 = android.support.v4.media.AbstractC0000x2c234b15.m2m(r0, " ");
        r0.append(((com.android.systemui.shade.carrier.NetworkNameInfo) r1).plmn);
        r0 = r0.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x029b, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x02bb, code lost:
    
        r0 = r3.get(java.lang.Integer.valueOf(r18));
        kotlin.jvm.internal.Intrinsics.checkNotNull(r0);
        r0 = ((com.android.systemui.shade.carrier.NetworkNameInfo) r0).plmn;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x02ca, code lost:
    
        if (r0 != null) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x028b, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r7, ((com.android.systemui.shade.carrier.NetworkNameInfo) r10).plmn) != false) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0075, code lost:
    
        if (r2 == null) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x042e, code lost:
    
        if ((r9.length <= 1 ? kotlin.jvm.internal.Intrinsics.areEqual(r9[0], "true") : kotlin.jvm.internal.Intrinsics.areEqual(r9[r18], "true")) != false) goto L219;
     */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getLatinNetworkName(int i) {
        String valueOf;
        boolean z;
        String str;
        String str2;
        String str3;
        boolean z2;
        boolean z3;
        String str4;
        String str5;
        boolean z4;
        boolean z5;
        NetworkNameInfo networkNameInfo;
        HashMap hashMap = this.serviceStateHash;
        ServiceStateInfo serviceStateInfo = (ServiceStateInfo) hashMap.get(Integer.valueOf(i));
        if (serviceStateInfo == null) {
            return "";
        }
        boolean isLatinGSM = isLatinGSM(serviceStateInfo.networkType, serviceStateInfo.voiceNetworkType);
        SubscriptionManager subscriptionManager = this.subscriptionManager;
        if (isLatinGSM) {
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
            if (activeSubscriptionInfoForSimSlotIndex != null) {
                str3 = activeSubscriptionInfoForSimSlotIndex.getCarrierName().toString();
                if (this.showCBMsg) {
                    HashMap hashMap2 = this.cbMsgBody;
                    if (hashMap2.containsKey(Integer.valueOf(i)) && !StringsKt__StringsJVMKt.equals((String) hashMap2.get(Integer.valueOf(i)), "", false)) {
                        if (str3.length() > 0) {
                            str3 = str3.concat(" / ");
                        }
                        str3 = str3 + hashMap2.get(Integer.valueOf(i));
                    }
                }
            }
            str3 = "";
        } else {
            HashMap hashMap3 = this.networkNameHash;
            NetworkNameInfo networkNameInfo2 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
            Boolean valueOf2 = networkNameInfo2 != null ? Boolean.valueOf(networkNameInfo2.showSpn) : null;
            NetworkNameInfo networkNameInfo3 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
            String str6 = networkNameInfo3 != null ? networkNameInfo3.spn : null;
            NetworkNameInfo networkNameInfo4 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
            String str7 = networkNameInfo4 != null ? networkNameInfo4.dataSpn : null;
            NetworkNameInfo networkNameInfo5 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
            Boolean valueOf3 = networkNameInfo5 != null ? Boolean.valueOf(networkNameInfo5.showPlmn) : null;
            NetworkNameInfo networkNameInfo6 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
            String str8 = networkNameInfo6 != null ? networkNameInfo6.plmn : null;
            ImsRegStateUtil imsRegStateUtil = this.imsRegStateUtil;
            boolean isVoWifiConnected = imsRegStateUtil.isVoWifiConnected(i);
            String str9 = this.networkManuallySelected;
            StringBuilder sb = new StringBuilder("updateNetworkNameLatin: showSpn=");
            sb.append(valueOf2);
            sb.append(", spn=");
            sb.append(str6);
            sb.append(", dataSpn=");
            sb.append(str7);
            sb.append(", showPlmn=");
            sb.append(valueOf3);
            sb.append(", plmn=");
            sb.append(str8);
            sb.append(", voWifiConnected=");
            sb.append(isVoWifiConnected);
            sb.append(", networkManuallySelected=");
            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str9, "LatinNetworkNameProvider");
            CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CLARO_PLMN, i, new Object[0]) && imsRegStateUtil.isVoWifiConnected(i)) {
                NetworkNameInfo networkNameInfo7 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                if (networkNameInfo7 == null || (valueOf = networkNameInfo7.plmn) == null) {
                    valueOf = "";
                }
                NetworkNameInfo networkNameInfo8 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                if ((networkNameInfo8 != null ? networkNameInfo8.spn : null) != null) {
                    NetworkNameInfo networkNameInfo9 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                    if (!Intrinsics.areEqual("", networkNameInfo9 != null ? networkNameInfo9.dataSpn : null)) {
                        NetworkNameInfo networkNameInfo10 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                        str3 = networkNameInfo10 != null ? networkNameInfo10.dataSpn : null;
                        Intrinsics.checkNotNull(str3);
                    }
                }
                str3 = valueOf;
            } else if (this.isAirplaneMode) {
                if (hashMap3.get(Integer.valueOf(i)) != null) {
                    NetworkNameInfo networkNameInfo11 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                    if (networkNameInfo11 != null) {
                        z4 = true;
                        if (networkNameInfo11.hasVoWifiPLMN) {
                            z5 = true;
                            if (z4) {
                                NetworkNameInfo networkNameInfo12 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                if (networkNameInfo12 != null && networkNameInfo12.showPlmn == z5) {
                                    NetworkNameInfo networkNameInfo13 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                    if ((networkNameInfo13 != null ? networkNameInfo13.plmn : null) != null) {
                                        NetworkNameInfo networkNameInfo14 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                        str3 = String.valueOf(networkNameInfo14 != null ? networkNameInfo14.plmn : null);
                                        networkNameInfo = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                        if (networkNameInfo == null && networkNameInfo.showSpn) {
                                            NetworkNameInfo networkNameInfo15 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                            if ((networkNameInfo15 != null ? networkNameInfo15.spn : null) != null) {
                                                if (str3.length() > 0) {
                                                    StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str3);
                                                    m18m.append(this.mNetworkNameSeparator);
                                                    str3 = m18m.toString();
                                                }
                                                NetworkNameInfo networkNameInfo16 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                                valueOf = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str3, networkNameInfo16 != null ? networkNameInfo16.spn : null);
                                                str3 = valueOf;
                                            }
                                        }
                                    }
                                }
                                str3 = "";
                                networkNameInfo = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                if (networkNameInfo == null && networkNameInfo.showSpn) {
                                }
                            }
                        }
                    } else {
                        z4 = true;
                    }
                    boolean z6 = z4;
                    z4 = false;
                    z5 = z6;
                    if (z4) {
                    }
                }
                str3 = "";
            } else {
                if (hashMap.get(Integer.valueOf(i)) != null) {
                    Object obj = hashMap.get(Integer.valueOf(i));
                    Intrinsics.checkNotNull(obj);
                }
                if (hashMap3.get(Integer.valueOf(i)) != null) {
                    Object obj2 = hashMap3.get(Integer.valueOf(i));
                    Intrinsics.checkNotNull(obj2);
                    if (((NetworkNameInfo) obj2).plmn != null) {
                        String string = this.context.getString(android.R.string.indeterminate_progress_08);
                        Object obj3 = hashMap3.get(Integer.valueOf(i));
                        Intrinsics.checkNotNull(obj3);
                    }
                }
                if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.MULTI_LINE_CARRIER_LABEL, 0, new Object[0])) {
                    String str10 = SystemProperties.get("gsm.sim.operator.numeric");
                    int length = str10.length() - 1;
                    boolean z7 = false;
                    int i2 = 0;
                    while (i2 <= length) {
                        boolean z8 = Intrinsics.compare(str10.charAt(!z7 ? i2 : length), 32) <= 0;
                        if (z7) {
                            if (!z8) {
                                break;
                            }
                            length--;
                        } else if (z8) {
                            i2++;
                        } else {
                            z7 = true;
                        }
                    }
                    boolean z9 = false;
                    String[] strArr = (String[]) StringsKt__StringsKt.split$default(str10.subSequence(i2, length + 1).toString(), new String[]{","}, 0, 6).toArray(new String[0]);
                    String str11 = SystemProperties.get("gsm.operator.numeric");
                    int length2 = str11.length() - 1;
                    int i3 = 0;
                    while (i3 <= length2) {
                        boolean z10 = Intrinsics.compare(str11.charAt(!z9 ? i3 : length2), 32) <= 0;
                        if (z9) {
                            if (!z10) {
                                break;
                            }
                            length2--;
                        } else if (z10) {
                            i3++;
                        } else {
                            z9 = true;
                        }
                    }
                    String[] strArr2 = (String[]) StringsKt__StringsKt.split$default(str11.subSequence(i3, length2 + 1).toString(), new String[]{","}, 0, 6).toArray(new String[0]);
                    if (CollectionsKt__CollectionsKt.arrayListOf("72406", "72410", "72411", "72423").contains(strArr[i])) {
                        if (CollectionsKt__CollectionsKt.arrayListOf("72406", "72410", "72411", "72423").contains(strArr2[i])) {
                            z = true;
                            if (z) {
                                NetworkNameInfo networkNameInfo17 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                if (networkNameInfo17 == null || (str = networkNameInfo17.plmn) == null) {
                                    str = "";
                                }
                                NetworkNameInfo networkNameInfo18 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                if (networkNameInfo18 == null || (str2 = networkNameInfo18.spn) == null) {
                                    str2 = "";
                                }
                                NetworkNameInfo networkNameInfo19 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                boolean z11 = networkNameInfo19 != null ? networkNameInfo19.showSpn : false;
                                NetworkNameInfo networkNameInfo20 = (NetworkNameInfo) hashMap3.get(Integer.valueOf(i));
                                boolean z12 = networkNameInfo20 != null ? networkNameInfo20.showPlmn : false;
                                ServiceStateInfo serviceStateInfo2 = (ServiceStateInfo) hashMap.get(Integer.valueOf(i));
                                if (serviceStateInfo2 != null) {
                                    if (serviceStateInfo2.isRoaming) {
                                        z2 = false;
                                        z3 = true;
                                    } else {
                                        z2 = false;
                                        String[] strArr3 = (String[]) StringsKt__StringsKt.split$default(SystemProperties.get("gsm.operator.isroaming", "false, false"), new String[]{","}, 0, 6).toArray(new String[0]);
                                        z3 = true;
                                    }
                                    if (!TextUtils.isEmpty(str)) {
                                        if (z12 && z11 && !TextUtils.isEmpty(str2)) {
                                            str4 = ((Object) str) + " " + str2;
                                            str = str4;
                                        }
                                        Log.d("LatinNetworkNameProvider", "updateNetworkNameLatinLAC=" + ((Object) str));
                                    }
                                    if (serviceStateInfo2.connected && !serviceStateInfo2.isEmergency && !TextUtils.isEmpty(str2)) {
                                        StringBuilder sb2 = new StringBuilder();
                                        TelephonyManager telephonyManager = this.telephonyManager;
                                        String networkOperator = telephonyManager.getNetworkOperator();
                                        if (networkOperator.length() > 0) {
                                            z2 = z3;
                                        }
                                        if (z2 && !networkOperator.equals("") && Integer.parseInt(networkOperator) > 0) {
                                            int subscriptionId = SubscriptionManager.getSubscriptionId(i);
                                            sb2 = new StringBuilder();
                                            CellLocation cellLocationBySubId = telephonyManager.getCellLocationBySubId(subscriptionId);
                                            if (cellLocationBySubId != null) {
                                                int lac = ((GsmCellLocation) cellLocationBySubId).getLac();
                                                if (lac != -1 && lac != 255 && lac != 0 && lac != 65535) {
                                                    int i4 = lac % 100;
                                                    switch (i4) {
                                                        case 11:
                                                        case 12:
                                                        case 13:
                                                        case 14:
                                                        case 15:
                                                        case 16:
                                                        case 17:
                                                        case 18:
                                                        case 19:
                                                            str5 = "SP";
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
                                                            str5 = "";
                                                            break;
                                                        case 21:
                                                        case 22:
                                                        case 24:
                                                            str5 = "RJ";
                                                            break;
                                                        case 27:
                                                        case 28:
                                                            str5 = "ES";
                                                            break;
                                                        case 31:
                                                        case 32:
                                                        case 33:
                                                        case 34:
                                                        case 35:
                                                        case 37:
                                                        case 38:
                                                            str5 = "MG";
                                                            break;
                                                        case 41:
                                                        case 42:
                                                        case 43:
                                                        case 44:
                                                        case 45:
                                                        case 46:
                                                            str5 = "PR";
                                                            break;
                                                        case 47:
                                                        case 48:
                                                        case 49:
                                                            str5 = "SC";
                                                            break;
                                                        case 51:
                                                        case 53:
                                                        case 54:
                                                        case 55:
                                                            str5 = "RS";
                                                            break;
                                                        case 61:
                                                            str5 = "DF";
                                                            break;
                                                        case 62:
                                                        case 64:
                                                            str5 = "GO";
                                                            break;
                                                        case 63:
                                                            str5 = "TO";
                                                            break;
                                                        case 65:
                                                        case 66:
                                                            str5 = "MT";
                                                            break;
                                                        case 67:
                                                            str5 = "MS";
                                                            break;
                                                        case 68:
                                                            str5 = "AC";
                                                            break;
                                                        case 69:
                                                            str5 = "RO";
                                                            break;
                                                        case 71:
                                                        case 73:
                                                        case 74:
                                                        case 75:
                                                        case 77:
                                                            str5 = "BA";
                                                            break;
                                                        case 79:
                                                            str5 = "SE";
                                                            break;
                                                        case 81:
                                                        case 87:
                                                            str5 = "PE";
                                                            break;
                                                        case 82:
                                                            str5 = "AL";
                                                            break;
                                                        case 83:
                                                            str5 = "PB";
                                                            break;
                                                        case 84:
                                                            str5 = "RN";
                                                            break;
                                                        case 85:
                                                        case 88:
                                                            str5 = "CE";
                                                            break;
                                                        case 86:
                                                        case 89:
                                                            str5 = "PI";
                                                            break;
                                                        case 91:
                                                        case 93:
                                                        case 94:
                                                            str5 = "PA";
                                                            break;
                                                        case 92:
                                                        case 97:
                                                            str5 = "AM";
                                                            break;
                                                        case 95:
                                                            str5 = "RR";
                                                            break;
                                                        case 96:
                                                            str5 = "AP";
                                                            break;
                                                        case 98:
                                                        case 99:
                                                            str5 = "MA";
                                                            break;
                                                    }
                                                    sb2.append(" " + str5 + " " + i4);
                                                }
                                                Log.d("LatinNetworkNameProvider", "setAreaCode areaInfo=" + ((Object) sb2));
                                            }
                                        }
                                        String sb3 = sb2.toString();
                                        if (!z12) {
                                            str4 = str2.concat(sb3);
                                        } else if (str.startsWith(str2) && Intrinsics.areEqual(str, str2)) {
                                            str4 = str2.concat(sb3);
                                        } else {
                                            str4 = str + " " + str2 + sb3;
                                        }
                                        str = str4;
                                    }
                                    Log.d("LatinNetworkNameProvider", "updateNetworkNameLatinLAC=" + ((Object) str));
                                }
                                str3 = str;
                            }
                        }
                    }
                    z = false;
                    if (z) {
                    }
                }
                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex2 = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
                valueOf = String.valueOf(activeSubscriptionInfoForSimSlotIndex2 != null ? activeSubscriptionInfoForSimSlotIndex2.getCarrierName() : null);
                str3 = valueOf;
            }
        }
        return str3 == null ? "" : str3;
    }

    public final boolean isUseLatinNetworkName() {
        return this.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.DISPLAY_CBCH50, 0, new Object[0]) && (this.subscriptionManager.getCompleteActiveSubscriptionInfoList().isEmpty() ^ true);
    }

    public final void registerLocationListener() {
        HashMap hashMap = this.serviceStateHash;
        Log.d("LatinNetworkNameProvider", "registerLocationListener subscriptions=" + hashMap.size());
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Number) ((Map.Entry) it.next()).getKey()).intValue();
            TelephonyManager createForSubscriptionId = this.telephonyManager.createForSubscriptionId(SubscriptionManager.getSubscriptionId(intValue));
            CellLocationChangedCallback cellLocationChangedCallback = intValue == 0 ? this.cellLocationCallback0 : this.cellLocationCallback1;
            if (((LocationControllerImpl) this.locationController).isLocationEnabled()) {
                createForSubscriptionId.registerTelephonyCallback(this.backgroundExecutor, cellLocationChangedCallback);
                StringBuilder sb = new StringBuilder("Location is enabled, start listening cell location [");
                sb.append(intValue);
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, "]", "LatinNetworkNameProvider");
            } else {
                createForSubscriptionId.unregisterTelephonyCallback(cellLocationChangedCallback);
                Log.d("LatinNetworkNameProvider", "Location is turned off, stop listening cell location [" + intValue + "]");
            }
        }
    }

    public final void unregisterLocationListener() {
        Iterator it = this.serviceStateHash.entrySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Number) ((Map.Entry) it.next()).getKey()).intValue();
            this.telephonyManager.createForSubscriptionId(SubscriptionManager.getSubscriptionId(intValue)).unregisterTelephonyCallback(intValue == 0 ? this.cellLocationCallback0 : this.cellLocationCallback1);
        }
    }
}
