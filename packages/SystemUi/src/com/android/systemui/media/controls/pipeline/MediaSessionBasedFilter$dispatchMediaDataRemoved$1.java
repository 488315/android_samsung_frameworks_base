package com.android.systemui.media.controls.pipeline;

import com.android.systemui.media.controls.pipeline.MediaDataManager;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaSessionBasedFilter$dispatchMediaDataRemoved$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$dispatchMediaDataRemoved$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Set set = CollectionsKt___CollectionsKt.toSet(this.this$0.listeners);
        String str = this.$key;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str);
        }
    }
}
