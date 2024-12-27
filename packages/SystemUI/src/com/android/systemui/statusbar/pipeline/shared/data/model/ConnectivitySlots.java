package com.android.systemui.statusbar.pipeline.shared.data.model;

import android.content.Context;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConnectivitySlots {
    public final Map slotByName;

    public ConnectivitySlots(Context context) {
        this.slotByName = MapsKt__MapsKt.mapOf(new Pair(context.getString(17043119), ConnectivitySlot.AIRPLANE), new Pair(context.getString(17043145), ConnectivitySlot.MOBILE), new Pair(context.getString(17043166), ConnectivitySlot.WIFI), new Pair(context.getString(17043134), ConnectivitySlot.ETHERNET));
    }
}
