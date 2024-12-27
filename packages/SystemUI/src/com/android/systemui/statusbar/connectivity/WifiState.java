package com.android.systemui.statusbar.connectivity;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WifiState extends ConnectivityState {
    public boolean isCarrierMerged;
    public boolean isDefault;
    public boolean isDefaultConnectionValidated;
    public boolean isTransient;
    public String ssid;
    public String statusLabel;
    public int subId;

    public WifiState() {
        this(null, false, false, null, false, false, 0, 127, null);
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final void copyFrom(ConnectivityState connectivityState) {
        super.copyFrom(connectivityState);
        WifiState wifiState = (WifiState) connectivityState;
        this.ssid = wifiState.ssid;
        this.isTransient = wifiState.isTransient;
        this.isDefault = wifiState.isDefault;
        this.statusLabel = wifiState.statusLabel;
        this.isCarrierMerged = wifiState.isCarrierMerged;
        this.isDefaultConnectionValidated = wifiState.isDefaultConnectionValidated;
        this.subId = wifiState.subId;
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!WifiState.class.equals(obj != null ? obj.getClass() : null) || !super.equals(obj)) {
            return false;
        }
        WifiState wifiState = (WifiState) obj;
        return Intrinsics.areEqual(this.ssid, wifiState.ssid) && this.isTransient == wifiState.isTransient && this.isDefault == wifiState.isDefault && Intrinsics.areEqual(this.statusLabel, wifiState.statusLabel) && this.isCarrierMerged == wifiState.isCarrierMerged && this.isDefaultConnectionValidated == wifiState.isDefaultConnectionValidated && this.subId == wifiState.subId;
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final int hashCode() {
        int hashCode = super.hashCode() * 31;
        String str = this.ssid;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode + (str != null ? str.hashCode() : 0)) * 31, 31, this.isTransient), 31, this.isDefault);
        String str2 = this.statusLabel;
        return TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((m + (str2 != null ? str2.hashCode() : 0)) * 31, 31, this.isCarrierMerged), 31, this.isDefaultConnectionValidated) + this.subId;
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final List tableColumns() {
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf("ssid", "isTransient", Account.IS_DEFAULT, "statusLabel", "isCarrierMerged", "isDefaultConnectionValidated", "subId"), (Collection) super.tableColumns());
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final List tableData() {
        List listOf = CollectionsKt__CollectionsKt.listOf(this.ssid, Boolean.valueOf(this.isTransient), Boolean.valueOf(this.isDefault), this.statusLabel, Boolean.valueOf(this.isCarrierMerged), Boolean.valueOf(this.isDefaultConnectionValidated), Integer.valueOf(this.subId));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it = listOf.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(it.next()));
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) super.tableData());
    }

    @Override // com.android.systemui.statusbar.connectivity.ConnectivityState
    public final void toString(StringBuilder sb) {
        super.toString(sb);
        sb.append(",ssid=");
        sb.append(this.ssid);
        sb.append(",isTransient=");
        sb.append(this.isTransient);
        sb.append(",isDefault=");
        sb.append(this.isDefault);
        sb.append(",statusLabel=");
        sb.append(this.statusLabel);
        sb.append(",isCarrierMerged=");
        sb.append(this.isCarrierMerged);
        sb.append(",isDefaultConnectionValidated=");
        sb.append(this.isDefaultConnectionValidated);
        sb.append(",subId=");
        sb.append(this.subId);
    }

    public /* synthetic */ WifiState(String str, boolean z, boolean z2, String str2, boolean z3, boolean z4, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : str, (i2 & 2) != 0 ? false : z, (i2 & 4) != 0 ? false : z2, (i2 & 8) != 0 ? null : str2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? 0 : i);
    }

    public WifiState(String str, boolean z, boolean z2, String str2, boolean z3, boolean z4, int i) {
        this.ssid = str;
        this.isTransient = z;
        this.isDefault = z2;
        this.statusLabel = str2;
        this.isCarrierMerged = z3;
        this.isDefaultConnectionValidated = z4;
        this.subId = i;
    }
}
