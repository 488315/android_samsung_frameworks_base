package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileSimpleLogger {
    public boolean connected;
    public boolean dataConnected;
    public NetworkTypeIconModel dataType;
    public boolean fivegavailable;
    public int networkType;
    public int overrideNetworkType;
    public String simCard;
    public final int slotId;
    public final String slotName;
    public final int subId;

    public MobileSimpleLogger(int i, int i2, String str, boolean z, boolean z2, NetworkTypeIconModel networkTypeIconModel, int i3, int i4, boolean z3, String str2) {
        this.slotId = i;
        this.subId = i2;
        this.slotName = str;
        this.connected = z;
        this.dataConnected = z2;
        this.dataType = networkTypeIconModel;
        this.networkType = i3;
        this.overrideNetworkType = i4;
        this.fivegavailable = z3;
        this.simCard = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileSimpleLogger)) {
            return false;
        }
        MobileSimpleLogger mobileSimpleLogger = (MobileSimpleLogger) obj;
        return this.slotId == mobileSimpleLogger.slotId && this.subId == mobileSimpleLogger.subId && Intrinsics.areEqual(this.slotName, mobileSimpleLogger.slotName) && this.connected == mobileSimpleLogger.connected && this.dataConnected == mobileSimpleLogger.dataConnected && Intrinsics.areEqual(this.dataType, mobileSimpleLogger.dataType) && this.networkType == mobileSimpleLogger.networkType && this.overrideNetworkType == mobileSimpleLogger.overrideNetworkType && this.fivegavailable == mobileSimpleLogger.fivegavailable && Intrinsics.areEqual(this.simCard, mobileSimpleLogger.simCard);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.slotName, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.subId, Integer.hashCode(this.slotId) * 31, 31), 31);
        boolean z = this.connected;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m41m + i) * 31;
        boolean z2 = this.dataConnected;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.overrideNetworkType, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.networkType, (this.dataType.hashCode() + ((i2 + i3) * 31)) * 31, 31), 31);
        boolean z3 = this.fivegavailable;
        return this.simCard.hashCode() + ((m42m + (z3 ? 1 : z3 ? 1 : 0)) * 31);
    }

    public final String toString() {
        boolean z = this.connected;
        boolean z2 = this.dataConnected;
        NetworkTypeIconModel networkTypeIconModel = this.dataType;
        int i = this.networkType;
        int i2 = this.overrideNetworkType;
        StringBuilder sb = new StringBuilder(TelephonyManager.getNetworkTypeName(i));
        if (!PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE.equals(TelephonyDisplayInfo.overrideNetworkTypeToString(i2)) && !"UNKNOWN".equals(TelephonyDisplayInfo.overrideNetworkTypeToString(i2))) {
            sb.append("/");
            sb.append(TelephonyDisplayInfo.overrideNetworkTypeToString(i2));
            Integer num = 3;
            if (num.equals(TelephonyDisplayInfo.overrideNetworkTypeToString(i2))) {
                sb.append("(5gavailable:");
                sb.append(this.fivegavailable);
                sb.append(")");
            }
        }
        String sb2 = sb.toString();
        String str = this.simCard;
        StringBuilder sb3 = new StringBuilder("[");
        sb3.append(this.slotName);
        sb3.append("] (");
        sb3.append(this.slotId);
        sb3.append("/");
        sb3.append(this.subId);
        sb3.append(") connected=");
        sb3.append(z);
        sb3.append(" dataConnected=");
        sb3.append(z2);
        sb3.append(" dataType=");
        sb3.append(networkTypeIconModel);
        sb3.append(" networkType=");
        return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb3, sb2, " simCard=", str);
    }
}
