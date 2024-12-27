package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

public final class MediaSessionBasedFilter$dispatchMediaDataRemoved$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ boolean $userInitiated;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$dispatchMediaDataRemoved$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str, boolean z) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
        this.$userInitiated = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Set set = CollectionsKt___CollectionsKt.toSet(this.this$0.listeners);
        String str = this.$key;
        boolean z = this.$userInitiated;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
        }
    }
}
