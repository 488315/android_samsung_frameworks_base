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
                return NNFilter.this.m9550xf2fa8145();
            }
        });
    }

    /* renamed from: lambda$getDescriptor$0$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ NNDescriptor m9550xf2fa8145() {
        return (NNDescriptor) super.getDescriptor();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        super.prepare();
    }

    /* renamed from: lambda$run$1$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9552lambda$run$1$comsamsungandroidsumecorefilterNNFilter(MediaBuffer mediaBuffer, BufferProcessor bufferProcessor) {
        return bufferProcessor.process(mediaBuffer, this.descriptor.getOption());
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        List list;
        MediaBuffer mediaBuffer2;
        MediaBuffer mediaBuffer3 = (MediaBuffer) ((NNPlugin) this.plugin).getPreExecutor().map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NNFilter.this.m9552lambda$run$1$comsamsungandroidsumecorefilterNNFilter(mediaBuffer, (BufferProcessor) obj);
            }
        }).orElse(mediaBuffer);
        mediaBuffer3.addExtra(mediaBuffer.getExtra());
        Log.d(TAG, "input=" + mediaBuffer3);
        if (this.descriptor.getOption().isBatchIO()) {
            list = new ArrayList();
            super.run(mediaBuffer3, mutableMediaBuffer);
            list.add(mutableMediaBuffer.reset());
        } else {
            list = (List) mediaBuffer3.stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return NNFilter.this.m9553lambda$run$2$comsamsungandroidsumecorefilterNNFilter((MediaBuffer) obj);
                }
            }).collect(Collectors.toList());
        }
        if (mediaBuffer3 instanceof DeriveBufferGroup) {
            list.add(0, ((DeriveBufferGroup) mediaBuffer3).getPrimaryBuffer());
            mediaBuffer2 = MediaBuffer.groupOf(0, (List<MediaBuffer>) list);
        } else if (mediaBuffer3 instanceof MediaBufferGroup) {
            mediaBuffer2 = MediaBuffer.groupOf((List<MediaBuffer>) list);
        } else {
            mediaBuffer2 = (MediaBuffer) list.get(0);
        }
        mediaBuffer2.addExtra(mediaBuffer3.getExtra());
        mediaBuffer3.release();
        if (((NNPlugin) this.plugin).getPostExecutor() != null) {
            mediaBuffer2 = ((NNPlugin) this.plugin).getPostExecutor().process(mediaBuffer2, this.descriptor.getOption());
        }
        if (mediaBuffer2 instanceof MutableMediaBuffer) {
            mutableMediaBuffer.put(((MutableMediaBuffer) mediaBuffer2).reset());
            return mutableMediaBuffer;
        }
        mutableMediaBuffer.put(mediaBuffer2);
        return mutableMediaBuffer;
    }

    /* renamed from: lambda$run$2$com-samsung-android-sume-core-filter-NNFilter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9553lambda$run$2$comsamsungandroidsumecorefilterNNFilter(MediaBuffer mediaBuffer) {
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
                final MutableMediaFormat mutableImageOf = com.samsung.android.sume.core.format.MediaFormat.mutableImageOf(new Object[0]);
                mutableImageOf.setCols(mediaFormat.getInteger("width"));
                mutableImageOf.setRows(mediaFormat.getInteger("height"));
                Optional.ofNullable(this.descriptor.getModelSelector()).map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        ModelSelector.Item select;
                        select = ((ModelSelector) obj).select(MediaBuffer.mutableOf(MutableMediaFormat.this));
                        return select;
                    }
                }).flatMap(new Function() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Optional ofNullable;
                        ofNullable = Optional.ofNullable(((ModelSelector.Item) obj).descriptorUpdater);
                        return ofNullable;
                    }
                }).ifPresent(new Consumer() { // from class: com.samsung.android.sume.core.filter.NNFilter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NNFilter.this.m9551x99249ace((Consumer) obj);
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
    /* synthetic */ void m9551x99249ace(Consumer consumer) {
        consumer.accept(this.descriptor);
    }
}
