package com.android.systemui.statusbar.pipeline.shared.data.model;

import android.content.Context;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* loaded from: classes3.dex */
public final class ConnectivitySlots {
    public final Map slotByName;

    public ConnectivitySlots(Context context) {
        this.slotByName = MapsKt__MapsKt.mapOf(new Pair(context.getString(17043261), ConnectivitySlot.AIRPLANE), new Pair(context.getString(17043287), ConnectivitySlot.MOBILE), new Pair(context.getString(17043309), ConnectivitySlot.WIFI), new Pair(context.getString(17043276), ConnectivitySlot.ETHERNET));
    }
}
