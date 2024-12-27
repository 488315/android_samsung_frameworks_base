package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaDataCombineLatest implements MediaDataManager.Listener {
    public final Set listeners = new LinkedHashSet();
    public final Map entries = new LinkedHashMap();

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
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

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        Iterator it = CollectionsKt___CollectionsKt.toSet(this.listeners).iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onSmartspaceMediaDataLoaded(str, smartspaceMediaData);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        Iterator it = CollectionsKt___CollectionsKt.toSet(this.listeners).iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onSmartspaceMediaDataRemoved(str, z);
        }
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
        Pair pair = (Pair) ((LinkedHashMap) this.entries).get(str);
        if (pair == null) {
            pair = new Pair(null, null);
        }
        MediaData mediaData = (MediaData) pair.component1();
        MediaDeviceData mediaDeviceData = (MediaDeviceData) pair.component2();
        if (mediaData == null || mediaDeviceData == null) {
            return;
        }
        MediaData copy$default = MediaData.copy$default(mediaData, null, null, null, null, null, mediaDeviceData, false, null, false, false, null, false, 0L, 0L, null, 0, 268427263);
        Iterator it = CollectionsKt___CollectionsKt.toSet(this.listeners).iterator();
        while (it.hasNext()) {
            MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, copy$default, false, 0, false, 56);
        }
    }
}
