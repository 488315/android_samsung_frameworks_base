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

public class SequentialPickerFilter extends SequentialFilter {
    private final List<Pair<Evaluator, MediaFilter>> evaluateFilters;

    public SequentialPickerFilter(SequentialDescriptor descriptor) {
        super(descriptor);
        this.evaluateFilters = new ArrayList();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        Def.require(
                this.descriptor.getEvaluators().size() == this.filters.size(),
                "# of evaluator & filter are not matched",
                new Object[0]);
        IntStream.range(0, this.filters.size())
                .forEach(
                        new IntConsumer() { // from class:
                                            // com.samsung.android.sume.core.filter.collection.SequentialPickerFilter$$ExternalSyntheticLambda1
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i) {
                                SequentialPickerFilter.this.m9158x33bd5385(i);
                            }
                        });
    }

    /* renamed from: lambda$prepare$0$com-samsung-android-sume-core-filter-collection-SequentialPickerFilter, reason: not valid java name */
    /* synthetic */ void m9158x33bd5385(int idx) {
        MediaFilter filter = this.filters.get(idx);
        filter.prepare();
        this.evaluateFilters.add(new Pair<>(this.descriptor.getEvaluators().get(idx), filter));
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        for (Pair<Evaluator, MediaFilter> evaluateFilter : this.evaluateFilters) {
            MediaBufferReader<?> reader =
                    MediaBufferReader.of(ibuf, evaluateFilter.first.getValueType());
            if (evaluateFilter.first.evaluate(reader.get())) {
                if (ibuf instanceof MediaBufferGroup) {
                    Stream<MediaBuffer> stream = ibuf.stream();
                    final MediaFilter mediaFilter = evaluateFilter.second;
                    Objects.requireNonNull(mediaFilter);
                    List<MediaBuffer> buffers =
                            (List)
                                    stream.map(
                                                    new Function() { // from class:
                                                                     // com.samsung.android.sume.core.filter.collection.SequentialPickerFilter$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            return MediaFilter.this.run(
                                                                    (MediaBuffer) obj);
                                                        }
                                                    })
                                            .collect(Collectors.toList());
                    if (buffers.size() == 1) {
                        obuf.put(buffers.get(0));
                    } else {
                        obuf.put(MediaBuffer.groupOf(buffers));
                    }
                } else {
                    return evaluateFilter.second.run(ibuf, obuf);
                }
            }
        }
        return obuf;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        super.release();
        this.evaluateFilters.clear();
    }
}
