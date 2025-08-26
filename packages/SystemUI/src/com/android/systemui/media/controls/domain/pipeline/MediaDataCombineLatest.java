package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes2.dex */
public final class MediaDataCombineLatest implements MediaDataManager.Listener {
    public final Set listeners = new LinkedHashSet();
    public final Map entries = new LinkedHashMap();

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        if (str2 == null || str2.equals(str) || !this.entries.containsKey(str2)) {
            Map map = this.entries;
            Pair pair = (Pair) ((LinkedHashMap) map).get(str);
            map.put(str, new Pair(mediaData, pair != null ? (MediaDeviceData) pair.getSecond() : null));
            update(str, str);
            return;
        }
        Map map2 = this.entries;
        Pair pair2 = (Pair) map2.remove(str2);
        map2.put(str, new Pair(mediaData, pair2 != null ? (MediaDeviceData) pair2.getSecond() : null));
        update(str, str2);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        remove(str, z);
    }

    public final void remove(String str, boolean z) {
        if (((Pair) this.entries.remove(str)) != null) {
            Iterator it = CollectionsKt___CollectionsKt.toSet(this.listeners).iterator();
            while (it.hasNext()) {
                ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
            }
        }
    }

    public final void update(String str, String str2) {
        String str3 = str;
        Pair pair = (Pair) ((LinkedHashMap) this.entries).get(str3);
        if (pair == null) {
            pair = new Pair(null, null);
        }
        MediaData mediaData = (MediaData) pair.component1();
        MediaDeviceData mediaDeviceData = (MediaDeviceData) pair.component2();
        if (mediaData == null || mediaDeviceData == null) {
            return;
        }
        MediaData mediaDataCopy$default = MediaData.copy$default(mediaData, null, null, null, null, null, mediaDeviceData, false, null, false, null, 0L, 0L, null, 0, 268427263);
        Iterator it = CollectionsKt___CollectionsKt.toSet(this.listeners).iterator();
        while (it.hasNext()) {
            MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str3, str2, mediaDataCopy$default, false, 56);
            str3 = str;
        }
    }
}
