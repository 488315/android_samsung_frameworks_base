package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.jvm.internal.Intrinsics;

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

    public final int hashCode() {
        return this.simCard.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.overrideNetworkType, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.networkType, (this.dataType.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.subId, Integer.hashCode(this.slotId) * 31, 31), 31, this.slotName), 31, this.connected), 31, this.dataConnected)) * 31, 31), 31), 31, this.fivegavailable);
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
        return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb3, sb2, " simCard=", str);
    }
}
