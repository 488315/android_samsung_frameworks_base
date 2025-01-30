package com.android.systemui.statusbar.pipeline.shared.data.model;

import android.content.Context;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConnectivitySlots {
    public final Map slotByName;

    public ConnectivitySlots(Context context) {
        this.slotByName = MapsKt__MapsKt.mapOf(new Pair(context.getString(17042905), ConnectivitySlot.AIRPLANE), new Pair(context.getString(17042930), ConnectivitySlot.MOBILE), new Pair(context.getString(17042951), ConnectivitySlot.WIFI), new Pair(context.getString(17042919), ConnectivitySlot.ETHERNET));
    }
}
