package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class SyncFilter extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) SyncFilter.class);

    public SyncFilter(MediaFilter mediaFilter) {
        super(mediaFilter);
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        if (mediaBuffer instanceof MediaBufferGroup) {
            MediaBuffer primaryBuffer = ((MediaBufferGroup) mediaBuffer).getPrimaryBuffer();
            if (primaryBuffer instanceof MediaBufferGroup) {
                primaryBuffer.addExtra(mediaBuffer.getExtra());
            }
        }
        List list = (List) mediaBuffer.stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.SyncFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SyncFilter.this.m9554lambda$run$0$comsamsungandroidsumecorefilterSyncFilter((MediaBuffer) obj);
            }
        }).collect(Collectors.toList());
        if (list.size() == 1) {
            mutableMediaBuffer.put((MediaBuffer) list.get(0));
        } else {
            mutableMediaBuffer.put(MediaBuffer.groupOf((List<MediaBuffer>) list));
        }
        mutableMediaBuffer.addExtra(mediaBuffer.getExtra());
        return mutableMediaBuffer;
    }

    /* renamed from: lambda$run$0$com-samsung-android-sume-core-filter-SyncFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9554lambda$run$0$comsamsungandroidsumecorefilterSyncFilter(MediaBuffer mediaBuffer) {
        return super.run(mediaBuffer, MediaBuffer.mutableOf()).reset();
    }
}
