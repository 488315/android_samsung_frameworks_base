package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SyncFilter extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) SyncFilter.class);

    public SyncFilter(MediaFilter successor) {
        super(successor);
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        if (ibuf instanceof MediaBufferGroup) {
            MediaBuffer primaryBuffer = ((MediaBufferGroup) ibuf).getPrimaryBuffer();
            if (primaryBuffer instanceof MediaBufferGroup) {
                primaryBuffer.addExtra(ibuf.getExtra());
            }
        }
        List<MediaBuffer> output =
                (List)
                        ibuf.stream()
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.filter.SyncFilter$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return SyncFilter.this
                                                        .m9153lambda$run$0$comsamsungandroidsumecorefilterSyncFilter(
                                                                (MediaBuffer) obj);
                                            }
                                        })
                                .collect(Collectors.toList());
        if (output.size() == 1) {
            obuf.put(output.get(0));
        } else {
            obuf.put(MediaBuffer.groupOf(output));
        }
        obuf.addExtra(ibuf.getExtra());
        return obuf;
    }

    /* renamed from: lambda$run$0$com-samsung-android-sume-core-filter-SyncFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9153lambda$run$0$comsamsungandroidsumecorefilterSyncFilter(
            MediaBuffer it) {
        MutableMediaBuffer buf = super.run(it, MediaBuffer.mutableOf());
        return buf.reset();
    }
}
