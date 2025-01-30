package com.android.settingslib.bluetooth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BluetoothLeBroadcastMetadataExt {
    public static final BluetoothLeBroadcastMetadataExt INSTANCE = new BluetoothLeBroadcastMetadataExt();

    private BluetoothLeBroadcastMetadataExt() {
    }

    public static String toQrCodeString(String str, List list) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(pair.getFirst() + ":" + pair.getSecond());
        }
        return CollectionsKt___CollectionsKt.joinToString$default(arrayList, str, null, null, null, 62);
    }
}
