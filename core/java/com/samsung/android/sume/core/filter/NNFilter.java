package com.samsung.android.sume.core.filter;

import android.media.MediaFormat;
import android.util.Log;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.DeriveBufferGroup;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.functional.BufferProcessor;
import com.samsung.android.sume.core.functional.ModelSelector;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.plugin.NNPlugin;
import com.samsung.android.sume.core.types.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class NNFilter extends PluginDecorateFilter<NNPlugin> {
    private static final String TAG = Def.tagOf((Class<?>) NNFilter.class);
    private NNDescriptor descriptor;

    public NNFilter(NNPlugin plugin, MediaFilter filter) {
        super(plugin, filter);
    }

    public NNFilter(NNDescriptor descriptor, NNPlugin plugin, MediaFilter filter) {
        super(plugin, filter);
        this.descriptor = descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return (MFDescriptor)
                Optional.ofNullable(this.descriptor)
                        .orElseGet(
                                new Supplier() { // from class:
                                                 // com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return NNFilter.this.m9149xf2fa8145();
                                    }
                                });
    }

    /* renamed from: lambda$getDescriptor$0$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ NNDescriptor m9149xf2fa8145() {
        return (NNDescriptor) super.getDescriptor();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        super.prepare();
    }

    /* renamed from: lambda$run$1$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9151lambda$run$1$comsamsungandroidsumecorefilterNNFilter(
            MediaBuffer ibuf, BufferProcessor e) {
        return e.process(ibuf, this.descriptor.getOption());
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer ibuf, MutableMediaBuffer obuf) {
        List<MediaBuffer> outputs;
        MediaBuffer output;
        MediaBuffer input =
                (MediaBuffer)
                        ((NNPlugin) this.plugin)
                                .getPreExecutor()
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return NNFilter.this
                                                        .m9151lambda$run$1$comsamsungandroidsumecorefilterNNFilter(
                                                                ibuf, (BufferProcessor) obj);
                                            }
                                        })
                                .orElse(ibuf);
        input.addExtra(ibuf.getExtra());
        Log.d(TAG, "input=" + input);
        if (this.descriptor.getOption().isBatchIO()) {
            outputs = new ArrayList<>();
            super.run(input, obuf);
            outputs.add(obuf.reset());
        } else {
            outputs =
                    (List)
                            input.stream()
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return NNFilter.this
                                                            .m9152lambda$run$2$comsamsungandroidsumecorefilterNNFilter(
                                                                    (MediaBuffer) obj);
                                                }
                                            })
                                    .collect(Collectors.toList());
        }
        if (input instanceof DeriveBufferGroup) {
            outputs.add(0, ((DeriveBufferGroup) input).getPrimaryBuffer());
            output = MediaBuffer.groupOf(0, outputs);
        } else if (input instanceof MediaBufferGroup) {
            output = MediaBuffer.groupOf(outputs);
        } else {
            MediaBuffer output2 = outputs.get(0);
            output = output2;
        }
        output.addExtra(input.getExtra());
        input.release();
        if (((NNPlugin) this.plugin).getPostExecutor() != null) {
            output =
                    ((NNPlugin) this.plugin)
                            .getPostExecutor()
                            .process(output, this.descriptor.getOption());
        }
        if (output instanceof MutableMediaBuffer) {
            obuf.put(((MutableMediaBuffer) output).reset());
        } else {
            obuf.put(output);
        }
        return obuf;
    }

    /* renamed from: lambda$run$2$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9152lambda$run$2$comsamsungandroidsumecorefilterNNFilter(
            MediaBuffer it) {
        MutableMediaBuffer buf = super.run(it, MediaBuffer.mutableOf());
        return buf.reset();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        super.release();
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[] {1};
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        switch (message.getCode()) {
            case 1:
                MediaType mediaType = (MediaType) message.get(Message.KEY_MEDIA_TYPE);
                MediaFormat mediaFormat = (MediaFormat) message.get("media-format");
                if (this.descriptor.getMediaType().isVideo() && mediaType.isVideo()) {
                    final MutableMediaFormat imgFormat =
                            com.samsung.android.sume.core.format.MediaFormat.mutableImageOf(
                                    new Object[0]);
                    imgFormat.setCols(mediaFormat.getInteger("width"));
                    imgFormat.setRows(mediaFormat.getInteger("height"));
                    Optional.ofNullable(this.descriptor.getModelSelector())
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            ModelSelector.Item select;
                                            select =
                                                    ((ModelSelector) obj)
                                                            .select(
                                                                    MediaBuffer.mutableOf(
                                                                            MutableMediaFormat
                                                                                    .this));
                                            return select;
                                        }
                                    })
                            .flatMap(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            Optional ofNullable;
                                            ofNullable =
                                                    Optional.ofNullable(
                                                            ((ModelSelector.Item) obj)
                                                                    .descriptorUpdater);
                                            return ofNullable;
                                        }
                                    })
                            .ifPresent(
                                    new Consumer() { // from class:
                                                     // com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            NNFilter.this.m9150x99249ace((Consumer) obj);
                                        }
                                    });
                    return true;
                }
                if (this.descriptor.getMediaType().isAudio() && mediaType.isAudio()) {
                    throw new UnsupportedOperationException(
                            "not implemented yet for MutableMediaFormat");
                }
                return true;
            default:
                return super.onMessageReceived(message);
        }
    }

    /* renamed from: lambda$onMessageReceived$5$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ void m9150x99249ace(Consumer it) {
        it.accept(this.descriptor);
    }
}
