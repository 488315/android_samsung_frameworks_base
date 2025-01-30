package com.android.systemui.statusbar.connectivity;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.controls.p005ui.util.ControlExtension$Companion$$ExternalSyntheticOutline0;
import com.samsung.android.knox.accounts.Account;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileState extends ConnectivityState {
    public boolean airplaneMode;
    public int carrierId;
    public boolean carrierNetworkChangeMode;
    public boolean dataConnected;
    public boolean dataSim;
    public int dataState;
    public boolean defaultDataOff;
    public boolean isDefault;
    public boolean isEmergency;
    public String networkName;
    public String networkNameData;
    public final NetworkTypeResIdCache networkTypeResIdCache;
    public boolean roaming;
    public ServiceState serviceState;
    public SignalStrength signalStrength;
    public TelephonyDisplayInfo telephonyDisplayInfo;
    public boolean userSetup;

    public MobileState() {
        this(null, null, false, false, false, false, false, false, false, false, 0, false, 4095, null);
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final void copyFrom(ConnectivityState connectivityState) {
        MobileState mobileState = connectivityState instanceof MobileState ? (MobileState) connectivityState : null;
        if (mobileState == null) {
            throw new IllegalArgumentException("MobileState can only update from another MobileState");
        }
        super.copyFrom(mobileState);
        this.networkName = mobileState.networkName;
        this.networkNameData = mobileState.networkNameData;
        this.dataSim = mobileState.dataSim;
        this.dataConnected = mobileState.dataConnected;
        this.isEmergency = mobileState.isEmergency;
        this.airplaneMode = mobileState.airplaneMode;
        this.carrierNetworkChangeMode = mobileState.carrierNetworkChangeMode;
        this.isDefault = mobileState.isDefault;
        this.userSetup = mobileState.userSetup;
        this.roaming = mobileState.roaming;
        this.dataState = mobileState.dataState;
        this.defaultDataOff = mobileState.defaultDataOff;
        this.telephonyDisplayInfo = mobileState.telephonyDisplayInfo;
        this.serviceState = mobileState.serviceState;
        this.signalStrength = mobileState.signalStrength;
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(MobileState.class, obj != null ? obj.getClass() : null) || !super.equals(obj)) {
            return false;
        }
        MobileState mobileState = (MobileState) obj;
        return Intrinsics.areEqual(this.networkName, mobileState.networkName) && Intrinsics.areEqual(this.networkNameData, mobileState.networkNameData) && this.carrierId == mobileState.carrierId && this.dataSim == mobileState.dataSim && this.dataConnected == mobileState.dataConnected && this.isEmergency == mobileState.isEmergency && this.airplaneMode == mobileState.airplaneMode && this.carrierNetworkChangeMode == mobileState.carrierNetworkChangeMode && this.isDefault == mobileState.isDefault && this.userSetup == mobileState.userSetup && this.roaming == mobileState.roaming && this.dataState == mobileState.dataState && this.defaultDataOff == mobileState.defaultDataOff && Intrinsics.areEqual(this.telephonyDisplayInfo, mobileState.telephonyDisplayInfo) && Intrinsics.areEqual(this.serviceState, mobileState.serviceState) && Intrinsics.areEqual(this.signalStrength, mobileState.signalStrength);
    }

    public final String getOperatorAlphaShort() {
        ServiceState serviceState = this.serviceState;
        String operatorAlphaShort = serviceState != null ? serviceState.getOperatorAlphaShort() : null;
        return operatorAlphaShort == null ? "" : operatorAlphaShort;
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final int hashCode() {
        int hashCode = super.hashCode() * 31;
        String str = this.networkName;
        int hashCode2 = (hashCode + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.networkNameData;
        int hashCode3 = (this.telephonyDisplayInfo.hashCode() + ((Boolean.hashCode(this.defaultDataOff) + ((((Boolean.hashCode(this.roaming) + ((Boolean.hashCode(this.userSetup) + ((Boolean.hashCode(this.isDefault) + ((Boolean.hashCode(this.carrierNetworkChangeMode) + ((Boolean.hashCode(this.airplaneMode) + ((Boolean.hashCode(this.isEmergency) + ((Boolean.hashCode(this.dataConnected) + ((Boolean.hashCode(this.dataSim) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.carrierId, (hashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31, 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + this.dataState) * 31)) * 31)) * 31;
        ServiceState serviceState = this.serviceState;
        int hashCode4 = (hashCode3 + (serviceState != null ? serviceState.hashCode() : 0)) * 31;
        SignalStrength signalStrength = this.signalStrength;
        return hashCode4 + (signalStrength != null ? signalStrength.hashCode() : 0);
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final List tableColumns() {
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf("dataSim", "carrierId", "networkName", "networkNameData", "dataConnected", "roaming", Account.IS_DEFAULT, "isEmergency", "airplaneMode", "carrierNetworkChangeMode", "userSetup", "dataState", "defaultDataOff", "showQuickSettingsRatIcon", "voiceServiceState", "isInService", "networkTypeIconCache", "serviceState", "signalStrength", "displayInfo"), (Collection) super.tableColumns());
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x008b, code lost:
    
        if (((r1 == com.android.settingslib.mobile.TelephonyIcons.DATA_DISABLED || r1 == com.android.settingslib.mobile.TelephonyIcons.NOT_DEFAULT_DATA) && r6.userSetup) != false) goto L13;
     */
    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List tableData() {
        String str;
        String access$minLog;
        Object[] objArr = new Object[20];
        boolean z = false;
        objArr[0] = Boolean.valueOf(this.dataSim);
        objArr[1] = Integer.valueOf(this.carrierId);
        objArr[2] = this.networkName;
        objArr[3] = this.networkNameData;
        objArr[4] = Boolean.valueOf(this.dataConnected);
        objArr[5] = Boolean.valueOf(this.roaming);
        objArr[6] = Boolean.valueOf(this.isDefault);
        objArr[7] = Boolean.valueOf(this.isEmergency);
        objArr[8] = Boolean.valueOf(this.airplaneMode);
        objArr[9] = Boolean.valueOf(this.carrierNetworkChangeMode);
        objArr[10] = Boolean.valueOf(this.userSetup);
        objArr[11] = Integer.valueOf(this.dataState);
        objArr[12] = Boolean.valueOf(this.defaultDataOff);
        if (!this.dataConnected) {
            SignalIcon$IconGroup signalIcon$IconGroup = this.iconGroup;
        }
        z = true;
        objArr[13] = Boolean.valueOf(z);
        ServiceState serviceState = this.serviceState;
        objArr[14] = Integer.valueOf(serviceState != null ? serviceState.getState() : -1);
        objArr[15] = Boolean.valueOf(Utils.isInService(this.serviceState));
        objArr[16] = this.networkTypeResIdCache;
        ServiceState serviceState2 = this.serviceState;
        String str2 = "(null)";
        if (serviceState2 == null || (str = MobileStateKt.access$minLog(serviceState2)) == null) {
            str = "(null)";
        }
        objArr[17] = str;
        SignalStrength signalStrength = this.signalStrength;
        if (signalStrength != null && (access$minLog = MobileStateKt.access$minLog(signalStrength)) != null) {
            str2 = access$minLog;
        }
        objArr[18] = str2;
        objArr[19] = this.telephonyDisplayInfo;
        List listOf = CollectionsKt__CollectionsKt.listOf(objArr);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it = listOf.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(it.next()));
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) super.tableData());
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final void toString(StringBuilder sb) {
        String str;
        String access$minLog;
        super.toString(sb);
        sb.append(',');
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("dataSim=", this.dataSim, ",", sb);
        sb.append("carrierId=" + this.carrierId);
        sb.append("networkName=" + this.networkName + ",");
        sb.append("networkNameData=" + this.networkNameData + ",");
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("dataConnected=", this.dataConnected, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("roaming=", this.roaming, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("isDefault=", this.isDefault, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("isEmergency=", this.isEmergency, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("airplaneMode=", this.airplaneMode, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("carrierNetworkChangeMode=", this.carrierNetworkChangeMode, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("userSetup=", this.userSetup, ",", sb);
        sb.append("dataState=" + this.dataState + ",");
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("defaultDataOff=", this.defaultDataOff, ",", sb);
        boolean z = true;
        if (!this.dataConnected) {
            SignalIcon$IconGroup signalIcon$IconGroup = this.iconGroup;
            if (!((signalIcon$IconGroup == TelephonyIcons.DATA_DISABLED || signalIcon$IconGroup == TelephonyIcons.NOT_DEFAULT_DATA) && this.userSetup)) {
                z = false;
            }
        }
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("showQuickSettingsRatIcon=", z, ",", sb);
        ServiceState serviceState = this.serviceState;
        sb.append("voiceServiceState=" + (serviceState != null ? serviceState.getState() : -1) + ",");
        sb.append("isInService=" + Utils.isInService(this.serviceState) + ",");
        StringBuilder sb2 = new StringBuilder("networkTypeIconCache=");
        sb2.append(this.networkTypeResIdCache);
        sb.append(sb2.toString());
        ServiceState serviceState2 = this.serviceState;
        String str2 = "(null)";
        if (serviceState2 == null || (str = MobileStateKt.access$minLog(serviceState2)) == null) {
            str = "(null)";
        }
        sb.append("serviceState=" + str + ",");
        SignalStrength signalStrength = this.signalStrength;
        if (signalStrength != null && (access$minLog = MobileStateKt.access$minLog(signalStrength)) != null) {
            str2 = access$minLog;
        }
        sb.append("signalStrength=" + str2 + ",");
        sb.append("displayInfo=" + this.telephonyDisplayInfo);
    }

    public /* synthetic */ MobileState(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, boolean z9, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : str, (i2 & 2) != 0 ? null : str2, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & 128) != 0 ? false : z6, (i2 & 256) != 0 ? false : z7, (i2 & 512) != 0 ? false : z8, (i2 & 1024) != 0 ? 0 : i, (i2 & 2048) != 0 ? false : z9);
    }

    public MobileState(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, boolean z9) {
        this.networkName = str;
        this.networkNameData = str2;
        this.dataSim = z;
        this.dataConnected = z2;
        this.isEmergency = z3;
        this.airplaneMode = z4;
        this.carrierNetworkChangeMode = z5;
        this.isDefault = z6;
        this.userSetup = z7;
        this.roaming = z8;
        this.dataState = i;
        this.defaultDataOff = z9;
        this.telephonyDisplayInfo = new TelephonyDisplayInfo(0, 0, false);
        this.carrierId = -1;
        this.networkTypeResIdCache = new NetworkTypeResIdCache(null, 1, null);
    }

    public static /* synthetic */ void getNetworkTypeResIdCache$annotations() {
    }
}
