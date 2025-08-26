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

/* loaded from: classes6.dex */
public class NNFilter extends PluginDecorateFilter<NNPlugin> {
    private static final String TAG = Def.tagOf((Class<?>) NNFilter.class);
    private NNDescriptor descriptor;

    public NNFilter(NNPlugin nNPlugin, MediaFilter mediaFilter) {
        super(nNPlugin, mediaFilter);
    }

    public NNFilter(NNDescriptor nNDescriptor, NNPlugin nNPlugin, MediaFilter mediaFilter) {
        super(nNPlugin, mediaFilter);
        this.descriptor = nNDescriptor;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return (MFDescriptor) Optional.ofNullable(this.descriptor).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m9563xf2fa8145();
            }
        });
    }

    /* renamed from: lambda$getDescriptor$0$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ NNDescriptor m9563xf2fa8145() {
        return (NNDescriptor) super.getDescriptor();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        super.prepare();
    }

    /* renamed from: lambda$run$1$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9565lambda$run$1$comsamsungandroidsumecorefilterNNFilter(MediaBuffer mediaBuffer, BufferProcessor bufferProcessor) {
        return bufferProcessor.process(mediaBuffer, this.descriptor.getOption());
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        List arrayList;
        MediaBuffer mediaBufferGroupOf;
        MediaBuffer mediaBuffer2 = (MediaBuffer) ((NNPlugin) this.plugin).getPreExecutor().map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9565lambda$run$1$comsamsungandroidsumecorefilterNNFilter(mediaBuffer, (BufferProcessor) obj);
            }
        }).orElse(mediaBuffer);
        mediaBuffer2.addExtra(mediaBuffer.getExtra());
        Log.d(TAG, "input=" + mediaBuffer2);
        if (this.descriptor.getOption().isBatchIO()) {
            arrayList = new ArrayList();
            super.run(mediaBuffer2, mutableMediaBuffer);
            arrayList.add(mutableMediaBuffer.reset());
        } else {
            arrayList = (List) mediaBuffer2.stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m9566lambda$run$2$comsamsungandroidsumecorefilterNNFilter((MediaBuffer) obj);
                }
            }).collect(Collectors.toList());
        }
        if (mediaBuffer2 instanceof DeriveBufferGroup) {
            arrayList.add(0, ((DeriveBufferGroup) mediaBuffer2).getPrimaryBuffer());
            mediaBufferGroupOf = MediaBuffer.groupOf(0, (List<MediaBuffer>) arrayList);
        } else if (mediaBuffer2 instanceof MediaBufferGroup) {
            mediaBufferGroupOf = MediaBuffer.groupOf((List<MediaBuffer>) arrayList);
        } else {
            mediaBufferGroupOf = (MediaBuffer) arrayList.get(0);
        }
        mediaBufferGroupOf.addExtra(mediaBuffer2.getExtra());
        mediaBuffer2.release();
        if (((NNPlugin) this.plugin).getPostExecutor() != null) {
            mediaBufferGroupOf = ((NNPlugin) this.plugin).getPostExecutor().process(mediaBufferGroupOf, this.descriptor.getOption());
        }
        if (mediaBufferGroupOf instanceof MutableMediaBuffer) {
            mutableMediaBuffer.put(((MutableMediaBuffer) mediaBufferGroupOf).reset());
            return mutableMediaBuffer;
        }
        mutableMediaBuffer.put(mediaBufferGroupOf);
        return mutableMediaBuffer;
    }

    /* renamed from: lambda$run$2$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9566lambda$run$2$comsamsungandroidsumecorefilterNNFilter(MediaBuffer mediaBuffer) {
        return super.run(mediaBuffer, MediaBuffer.mutableOf()).reset();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        super.release();
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{1};
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        if (message.getCode() == 1) {
            MediaType mediaType = (MediaType) message.get(Message.KEY_MEDIA_TYPE);
            MediaFormat mediaFormat = (MediaFormat) message.get("media-format");
            if (this.descriptor.getMediaType().isVideo() && mediaType.isVideo()) {
                final MutableMediaFormat mutableMediaFormatMutableImageOf = com.samsung.android.sume.core.format.MediaFormat.mutableImageOf(new Object[0]);
                mutableMediaFormatMutableImageOf.setCols(mediaFormat.getInteger("width"));
                mutableMediaFormatMutableImageOf.setRows(mediaFormat.getInteger("height"));
                Optional.ofNullable(this.descriptor.getModelSelector()).map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((ModelSelector) obj).select(MediaBuffer.mutableOf(mutableMediaFormatMutableImageOf));
                    }
                }).flatMap(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Optional.ofNullable(((ModelSelector.Item) obj).descriptorUpdater);
                    }
                }).ifPresent(new Consumer() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.m9564x99249ace((Consumer) obj);
                    }
                });
            } else if (this.descriptor.getMediaType().isAudio() && mediaType.isAudio()) {
                throw new UnsupportedOperationException("not implemented yet for MutableMediaFormat");
            }
            return true;
        }
        return super.onMessageReceived(message);
    }

    /* renamed from: lambda$onMessageReceived$5$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ void m9564x99249ace(Consumer consumer) {
        consumer.accept(this.descriptor);
    }
}
