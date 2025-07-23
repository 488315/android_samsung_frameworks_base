package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.ParallelDescriptor;
import com.samsung.android.sume.core.filter.AsyncFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.MediaFilterGroup;
import com.samsung.android.sume.core.filter.collection.ParallelDNCFilter;
import com.samsung.android.sume.core.filter.collection.ParallelFilter;
import com.samsung.android.sume.core.filter.collection.ParallelSharedFilter;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class ParallelFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(final MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        MediaFilterGroup parallelSharedFilter;
        ParallelDescriptor parallelDescriptor = (ParallelDescriptor) mFDescriptor;
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$filter$collection$ParallelFilter$Type[parallelDescriptor.getParallelType().ordinal()];
        if (i == 1) {
            parallelSharedFilter = new ParallelSharedFilter(parallelDescriptor, mediaFilterFactory.getBufferChannelSupplier());
        } else {
            parallelSharedFilter = i != 2 ? null : new ParallelDNCFilter(parallelDescriptor, mediaFilterFactory.getBufferChannelSupplier());
        }
        Def.require(parallelSharedFilter != null);
        Stream<MFDescriptor> stream = parallelDescriptor.getDescriptors().stream();
        Objects.requireNonNull(mediaFilterFactory);
        final List<MediaFilter> list = (List) stream.map(new ParallelFilterCreator$$ExternalSyntheticLambda0(mediaFilterFactory)).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.ParallelFilterCreator$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MediaFilter newFilter;
                newFilter = MediaFilterFactory.this.newFilter(AsyncFilter.class, r1.getDescriptor(), (MediaFilter) obj);
                return newFilter;
            }
        }).collect(Collectors.toList());
        IntStream.range(0, list.size()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.filter.factory.ParallelFilterCreator$$ExternalSyntheticLambda2
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                ((AsyncFilter) list.get(i2)).setId(i2);
            }
        });
        parallelSharedFilter.addFilter(list);
        return parallelSharedFilter;
    }

    /* renamed from: com.samsung.android.sume.core.filter.factory.ParallelFilterCreator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$filter$collection$ParallelFilter$Type;

        static {
            int[] iArr = new int[ParallelFilter.Type.values().length];
            $SwitchMap$com$samsung$android$sume$core$filter$collection$ParallelFilter$Type = iArr;
            try {
                iArr[ParallelFilter.Type.SHARED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$filter$collection$ParallelFilter$Type[ParallelFilter.Type.DNC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
