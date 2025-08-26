package com.samsung.android.sume.core.filter.collection;

import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MediaBufferReader;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.SequentialDescriptor;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.filter.MediaFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class SequentialPickerFilter extends SequentialFilter {
    private final List<Pair<Evaluator, MediaFilter>> evaluateFilters;

    public SequentialPickerFilter(SequentialDescriptor sequentialDescriptor) {
        super(sequentialDescriptor);
        this.evaluateFilters = new ArrayList();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        Def.require(this.descriptor.getEvaluators().size() == this.filters.size(), "# of evaluator & filter are not matched", new Object[0]);
        IntStream.range(0, this.filters.size()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.filter.collection.SequentialPickerFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                this.f$0.m9572x33bd5385(i);
            }
        });
    }

    /* renamed from: lambda$prepare$0$com-samsung-android-sume-core-filter-collection-SequentialPickerFilter, reason: not valid java name */
    /* synthetic */ void m9572x33bd5385(int i) {
        MediaFilter mediaFilter = this.filters.get(i);
        mediaFilter.prepare();
        this.evaluateFilters.add(new Pair<>(this.descriptor.getEvaluators().get(i), mediaFilter));
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        for (Pair<Evaluator, MediaFilter> pair : this.evaluateFilters) {
            if (pair.first.evaluate(MediaBufferReader.of(mediaBuffer, pair.first.getValueType()).get())) {
                if (mediaBuffer instanceof MediaBufferGroup) {
                    Stream<MediaBuffer> stream = mediaBuffer.stream();
                    final MediaFilter mediaFilter = pair.second;
                    Objects.requireNonNull(mediaFilter);
                    List list = (List) stream.map(new Function() { // from class: com.samsung.android.sume.core.filter.collection.SequentialPickerFilter$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return mediaFilter.run((MediaBuffer) obj);
                        }
                    }).collect(Collectors.toList());
                    if (list.size() == 1) {
                        mutableMediaBuffer.put((MediaBuffer) list.get(0));
                    } else {
                        mutableMediaBuffer.put(MediaBuffer.groupOf((List<MediaBuffer>) list));
                    }
                } else {
                    return pair.second.run(mediaBuffer, mutableMediaBuffer);
                }
            }
        }
        return mutableMediaBuffer;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        super.release();
        this.evaluateFilters.clear();
    }
}
