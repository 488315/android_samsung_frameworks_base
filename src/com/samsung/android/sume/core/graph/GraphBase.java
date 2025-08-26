package com.samsung.android.sume.core.graph;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.message.MessageChannelRouter;
import com.samsung.android.sume.core.message.MessagePublisher;
import com.samsung.android.sume.core.message.MessageSubscriber;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.solution.filter.UniImgp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class GraphBase<T> implements Graph<T> {
    private static final String TAG = Def.tagOf((Class<?>) GraphBase.class);
    protected BufferChannel inputChannel;
    protected MessagePublisher messagePublisher;
    protected final List<GraphNode<T>> nodes;
    protected final Graph.Option option;
    protected BufferChannel outputChannel;
    protected final ConcurrentHashMap<Integer, MediaBuffer> outBufferMap = new ConcurrentHashMap<>();
    protected final MessageChannelRouter messageChannelRouter = new MessageChannelRouter(32);

    GraphBase(List<GraphNode<T>> list, Graph.Option option) {
        this.nodes = list;
        this.option = option;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private MediaBuffer onReceiveOutputBuffer(MediaBuffer mediaBuffer) throws Throwable {
        FileOutputStream fileOutputStream;
        Exception e;
        MediaType mediaType = mediaBuffer.getFormat().getMediaType();
        MediaBuffer mediaBufferRemove = this.outBufferMap.remove(mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
        if (mediaType == MediaType.SCALA || mediaType == MediaType.META || mediaBufferRemove == null) {
            return mediaBuffer;
        }
        Log.d(TAG, "onReceiveOutputBuffer: " + mediaBuffer + " => " + mediaBufferRemove);
        UniImgp.ofUnified().run(mediaBuffer, MediaBuffer.mutableOf(mediaBufferRemove));
        if (mediaBufferRemove.getFormat().getMediaType() == MediaType.COMPRESSED_IMAGE) {
            mediaBufferRemove.setExtra(mediaBuffer.getExtra());
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(((ParcelFileDescriptor) mediaBufferRemove.getExtra(Message.KEY_FILE_DESCRIPTOR)).getFileDescriptor());
                    try {
                        try {
                            ((Bitmap) mediaBufferRemove.getTypedData(Bitmap.class)).compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                            fileOutputStream.close();
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            mediaBufferRemove.setExtra("freezed", true);
                            return mediaBufferRemove;
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            } catch (Exception e5) {
                fileOutputStream = null;
                e = e5;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream2 != null) {
                }
                throw th;
            }
        }
        mediaBufferRemove.setExtra("freezed", true);
        return mediaBufferRemove;
    }

    protected void runBatch(List<MediaBuffer> list, List<MediaBuffer> list2) throws Throwable {
        Log.d(TAG, "runBatch: # of inputs " + list.size());
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9589lambda$runBatch$0$comsamsungandroidsumecoregraphGraphBase((MediaBuffer) obj);
            }
        });
        try {
            int size = list.size();
            list.clear();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                Log.d(TAG, "wait to receive output...");
                MediaBuffer mediaBufferOnReceiveOutputBuffer = onReceiveOutputBuffer(this.outputChannel.receive());
                if (!this.option.isOutputOnEventCallback()) {
                    list2.add(mediaBufferOnReceiveOutputBuffer);
                }
                publishEvent(510, mediaBufferOnReceiveOutputBuffer);
                size = i;
            }
        } catch (CancellationException unused) {
            onCanceled();
        }
    }

    /* renamed from: lambda$runBatch$0$com-samsung-android-sume-core-graph-GraphBase, reason: not valid java name */
    /* synthetic */ void m9589lambda$runBatch$0$comsamsungandroidsumecoregraphGraphBase(MediaBuffer mediaBuffer) {
        this.inputChannel.send(mediaBuffer);
        publishEvent(509, mediaBuffer);
    }

    protected void runOneByOne(List<MediaBuffer> list, final List<MediaBuffer> list2) {
        Log.d(TAG, "runOneByOne: # of inputs " + list.size());
        try {
            list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws Throwable {
                    this.f$0.m9590x8ab719a8(list2, (MediaBuffer) obj);
                }
            });
        } catch (CancellationException unused) {
            onCanceled();
        }
    }

    /* renamed from: lambda$runOneByOne$1$com-samsung-android-sume-core-graph-GraphBase, reason: not valid java name */
    /* synthetic */ void m9590x8ab719a8(List list, MediaBuffer mediaBuffer) throws Throwable {
        this.inputChannel.send(mediaBuffer);
        publishEvent(509, mediaBuffer);
        MediaBuffer mediaBufferOnReceiveOutputBuffer = onReceiveOutputBuffer(this.outputChannel.receive());
        if (!this.option.isOutputOnEventCallback()) {
            list.add(mediaBufferOnReceiveOutputBuffer);
        }
        publishEvent(510, mediaBufferOnReceiveOutputBuffer);
    }

    private void publishEvent(int i, final MediaBuffer mediaBuffer) {
        long j;
        String str = TAG;
        Log.d(str, "publishEvent E: code=" + i + ", buffer=" + mediaBuffer);
        if (this.messagePublisher != null) {
            final Event eventOf = Event.of(i);
            eventOf.setPublisher(this.messagePublisher);
            if (i == 509) {
                eventOf.put(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
                if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                    eventOf.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                }
                eventOf.put(Message.KEY_START_TIME_MS, Long.valueOf(System.currentTimeMillis()));
            } else if (i == 510) {
                eventOf.put(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
                if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                    eventOf.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                }
                eventOf.put("width", Integer.valueOf(mediaBuffer.getCols()));
                eventOf.put("height", Integer.valueOf(mediaBuffer.getRows()));
                eventOf.put(Message.KEY_END_TIME_MS, Long.valueOf(System.currentTimeMillis()));
                Stream.of((Object[]) new String[]{"rotation-degrees", "last-video-timestamp-us", "last-audio-timestamp-us"}).filter(new Predicate() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return mediaBuffer.getFormat().contains((String) obj);
                    }
                }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str2 = (String) obj;
                        eventOf.put(str2, mediaBuffer.getFormat().get(str2));
                    }
                });
                if (mediaBuffer.getFormat().getMediaType().isVideo()) {
                    long jLongValue = ((Long) Optional.ofNullable(eventOf.get("last-video-timestamp-us")).orElse(-1L)).longValue();
                    long jLongValue2 = ((Long) Optional.ofNullable(eventOf.get("last-audio-timestamp-us")).orElse(-1L)).longValue();
                    if (jLongValue > jLongValue2) {
                        j = (jLongValue / 1000) + 1;
                    } else {
                        j = (jLongValue2 / 1000) + 1;
                    }
                    eventOf.put("duration", Long.valueOf(j));
                }
                if (this.option.isOutputOnEventCallback()) {
                    Log.d(str, "set output buffer to event cb");
                    eventOf.setBundledDataHandler(new Message.BundledDataHandler() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda2
                        @Override // com.samsung.android.sume.core.message.Message.BundledDataHandler
                        public final void accept(Bundle bundle) {
                            bundle.putParcelableArray("buffer-list", new MediaBuffer[]{mediaBuffer});
                        }
                    });
                }
            }
            eventOf.post();
            Log.d(str, "publishEvent X: code=" + i);
        }
    }

    private void onCanceled() {
        Log.i(TAG, "onCanceled");
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void setMessageSubscriber(MessageSubscriber messageSubscriber) {
        Log.d(TAG, "setMessageSubscriber");
        this.messageChannelRouter.addMessageSubscriber(messageSubscriber);
        this.messagePublisher = this.messageChannelRouter.newMessagePublisher();
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void release() {
        String str = TAG;
        Log.d(str, "release...E");
        this.inputChannel.cancel();
        this.outputChannel.cancel();
        this.nodes.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((GraphNode) obj).release();
            }
        });
        this.option.clear();
        Log.d(str, "release...X");
    }
}
