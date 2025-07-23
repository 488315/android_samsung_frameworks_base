package com.android.systemui.statusbar.pipeline.shared.data.model;

import android.content.Context;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConnectivitySlots {
    public final Map slotByName;

    public ConnectivitySlots(Context context) {
        this.slotByName = MapsKt__MapsKt.mapOf(new Pair(context.getString(17043257), ConnectivitySlot.AIRPLANE), new Pair(context.getString(17043283), ConnectivitySlot.MOBILE), new Pair(context.getString(17043305), ConnectivitySlot.WIFI), new Pair(context.getString(17043272), ConnectivitySlot.ETHERNET));
    }
}
