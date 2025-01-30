package com.android.systemui.statusbar.connectivity;

import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.controls.ui.util.ControlExtension$Companion$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ConnectivityState {
    public boolean activityIn;
    public boolean activityOut;
    public boolean connected;
    public boolean enabled;
    public SignalIcon$IconGroup iconGroup;
    public int inetCondition;
    public int level;
    public int rssi;
    public long time;

    public void copyFrom(ConnectivityState connectivityState) {
        this.connected = connectivityState.connected;
        this.enabled = connectivityState.enabled;
        this.activityIn = connectivityState.activityIn;
        this.activityOut = connectivityState.activityOut;
        this.level = connectivityState.level;
        this.iconGroup = connectivityState.iconGroup;
        this.inetCondition = connectivityState.inetCondition;
        this.rssi = connectivityState.rssi;
        this.time = connectivityState.time;
    }

    public boolean equals(Object obj) {
        if (obj == null || !Intrinsics.areEqual(obj.getClass(), getClass())) {
            return false;
        }
        ConnectivityState connectivityState = (ConnectivityState) obj;
        return connectivityState.connected == this.connected && connectivityState.enabled == this.enabled && connectivityState.level == this.level && connectivityState.inetCondition == this.inetCondition && connectivityState.iconGroup == this.iconGroup && connectivityState.activityIn == this.activityIn && connectivityState.activityOut == this.activityOut && connectivityState.rssi == this.rssi;
    }

    public int hashCode() {
        int hashCode = (((Boolean.hashCode(this.activityOut) + ((Boolean.hashCode(this.activityIn) + ((Boolean.hashCode(this.enabled) + (Boolean.hashCode(this.connected) * 31)) * 31)) * 31)) * 31) + this.level) * 31;
        SignalIcon$IconGroup signalIcon$IconGroup = this.iconGroup;
        return Long.hashCode(this.time) + ((((((hashCode + (signalIcon$IconGroup != null ? signalIcon$IconGroup.hashCode() : 0)) * 31) + this.inetCondition) * 31) + this.rssi) * 31);
    }

    public List tableColumns() {
        return CollectionsKt__CollectionsKt.listOf("connected", "enabled", "activityIn", "activityOut", ActionResults.RESULT_SET_VOLUME_SUCCESS, "iconGroup", "inetCondition", "rssi", "time");
    }

    public List tableData() {
        List listOf = CollectionsKt__CollectionsKt.listOf(Boolean.valueOf(this.connected), Boolean.valueOf(this.enabled), Boolean.valueOf(this.activityIn), Boolean.valueOf(this.activityOut), Integer.valueOf(this.level), this.iconGroup, Integer.valueOf(this.inetCondition), Integer.valueOf(this.rssi), ConnectivityStateKt.sSDF.format(Long.valueOf(this.time)));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it = listOf.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(it.next()));
        }
        return arrayList;
    }

    public final String toString() {
        if (this.time == 0) {
            return "Empty ".concat(getClass().getSimpleName());
        }
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    public void toString(StringBuilder sb) {
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("connected=", this.connected, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("enabled=", this.enabled, ",", sb);
        sb.append("level=" + this.level + ",");
        sb.append("inetCondition=" + this.inetCondition + ",");
        sb.append("iconGroup=" + this.iconGroup + ",");
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("activityIn=", this.activityIn, ",", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m122m("activityOut=", this.activityOut, ",", sb);
        sb.append("rssi=" + this.rssi + ",");
        sb.append("lastModified=" + ConnectivityStateKt.sSDF.format(Long.valueOf(this.time)));
    }
}
