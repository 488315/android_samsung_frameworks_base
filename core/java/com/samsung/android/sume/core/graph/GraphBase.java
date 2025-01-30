package com.samsung.android.sume.core.graph;

import android.graphics.Bitmap;
import android.p009os.Bundle;
import android.p009os.ParcelFileDescriptor;
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

/* loaded from: classes4.dex */
public abstract class GraphBase<T> implements Graph<T> {
    private static final String TAG = Def.tagOf((Class<?>) GraphBase.class);
    protected BufferChannel inputChannel;
    protected MessagePublisher messagePublisher;
    protected final List<GraphNode<T>> nodes;
    protected final Graph.Option option;
    protected BufferChannel outputChannel;
    protected final ConcurrentHashMap<Integer, MediaBuffer> outBufferMap = new ConcurrentHashMap<>();
    protected final MessageChannelRouter messageChannelRouter = new MessageChannelRouter(32);

    GraphBase(List<GraphNode<T>> nodes, Graph.Option option) {
        this.nodes = nodes;
        this.option = option;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0095 -> B:12:0x00a4). Please report as a decompilation issue!!! */
    private MediaBuffer onReceiveOutputBuffer(MediaBuffer mediaBuffer) {
        MediaType outMediaType = mediaBuffer.getFormat().getMediaType();
        MediaBuffer storedOutput = this.outBufferMap.remove(mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
        if (outMediaType == MediaType.SCALA || outMediaType == MediaType.META || storedOutput == null) {
            return mediaBuffer;
        }
        Log.m94d(TAG, "onReceiveOutputBuffer: " + mediaBuffer + " => " + storedOutput);
        UniImgp.ofUnified().run(mediaBuffer, MediaBuffer.mutableOf(storedOutput));
        if (storedOutput.getFormat().getMediaType() == MediaType.COMPRESSED_IMAGE) {
            storedOutput.setExtra(mediaBuffer.getExtra());
            FileOutputStream os = null;
            try {
                try {
                    try {
                        ParcelFileDescriptor pfd = (ParcelFileDescriptor) storedOutput.getExtra(Message.KEY_FILE_DESCRIPTOR);
                        os = new FileOutputStream(pfd.getFileDescriptor());
                        ((Bitmap) storedOutput.getTypedData(Bitmap.class)).compress(Bitmap.CompressFormat.JPEG, 95, os);
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (os != null) {
                            os.close();
                        }
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th) {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        }
        storedOutput.setExtra("freezed", true);
        return storedOutput;
    }

    protected void runBatch(List<MediaBuffer> inBuffers, List<MediaBuffer> outBuffers) {
        Log.m94d(TAG, "runBatch: # of inputs " + inBuffers.size());
        inBuffers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GraphBase.this.m9212lambda$runBatch$0$comsamsungandroidsumecoregraphGraphBase((MediaBuffer) obj);
            }
        });
        try {
            int remains = inBuffers.size();
            inBuffers.clear();
            while (true) {
                int remains2 = remains - 1;
                if (remains != 0) {
                    Log.m94d(TAG, "wait to receive output...");
                    MediaBuffer obuf = onReceiveOutputBuffer(this.outputChannel.receive());
                    if (!this.option.isOutputOnEventCallback()) {
                        outBuffers.add(obuf);
                    }
                    publishEvent(510, obuf);
                    remains = remains2;
                } else {
                    return;
                }
            }
        } catch (CancellationException e) {
            onCanceled();
        }
    }

    /* renamed from: lambda$runBatch$0$com-samsung-android-sume-core-graph-GraphBase, reason: not valid java name */
    /* synthetic */ void m9212lambda$runBatch$0$comsamsungandroidsumecoregraphGraphBase(MediaBuffer it) {
        this.inputChannel.send(it);
        publishEvent(509, it);
    }

    protected void runOneByOne(List<MediaBuffer> inBuffers, final List<MediaBuffer> outBuffers) {
        Log.m94d(TAG, "runOneByOne: # of inputs " + inBuffers.size());
        try {
            inBuffers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    GraphBase.this.m412x8ab719a8(outBuffers, (MediaBuffer) obj);
                }
            });
        } catch (CancellationException e) {
            onCanceled();
        }
    }

    /* renamed from: lambda$runOneByOne$1$com-samsung-android-sume-core-graph-GraphBase */
    /* synthetic */ void m412x8ab719a8(List outBuffers, MediaBuffer it) {
        this.inputChannel.send(it);
        publishEvent(509, it);
        MediaBuffer obuf = onReceiveOutputBuffer(this.outputChannel.receive());
        if (!this.option.isOutputOnEventCallback()) {
            outBuffers.add(obuf);
        }
        publishEvent(510, obuf);
    }

    private void publishEvent(int code, final MediaBuffer mediaBuffer) {
        long durationMs;
        String str = TAG;
        Log.m94d(str, "publishEvent E: code=" + code + ", buffer=" + mediaBuffer);
        if (this.messagePublisher != null) {
            final Event event = Event.m419of(code);
            event.setPublisher(this.messagePublisher);
            switch (code) {
                case 509:
                    event.put(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
                    if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                        event.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                    }
                    event.put(Message.KEY_START_TIME_MS, Long.valueOf(System.currentTimeMillis()));
                    break;
                case 510:
                    event.put(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
                    if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                        event.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                    }
                    event.put("width", Integer.valueOf(mediaBuffer.getCols()));
                    event.put("height", Integer.valueOf(mediaBuffer.getRows()));
                    event.put(Message.KEY_END_TIME_MS, Long.valueOf(System.currentTimeMillis()));
                    Stream.of((Object[]) new String[]{"rotation-degrees", "last-video-timestamp-us", "last-audio-timestamp-us"}).filter(new Predicate() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean contains;
                            contains = MediaBuffer.this.getFormat().contains((String) obj);
                            return contains;
                        }
                    }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Event.this.put(r3, mediaBuffer.getFormat().get((String) obj));
                        }
                    });
                    if (mediaBuffer.getFormat().getMediaType().isVideo()) {
                        long videoDurationUs = ((Long) Optional.ofNullable(event.get("last-video-timestamp-us")).orElse(-1L)).longValue();
                        long audioDurationUs = ((Long) Optional.ofNullable(event.get("last-audio-timestamp-us")).orElse(-1L)).longValue();
                        if (videoDurationUs > audioDurationUs) {
                            durationMs = (videoDurationUs / 1000) + 1;
                        } else {
                            long durationMs2 = audioDurationUs / 1000;
                            durationMs = durationMs2 + 1;
                        }
                        event.put("duration", Long.valueOf(durationMs));
                    }
                    if (this.option.isOutputOnEventCallback()) {
                        Log.m94d(str, "set output buffer to event cb");
                        event.setBundledDataHandler(new Message.BundledDataHandler() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda4
                            @Override // com.samsung.android.sume.core.message.Message.BundledDataHandler
                            public final void accept(Bundle bundle) {
                                bundle.putParcelableArray("buffer-list", new MediaBuffer[]{MediaBuffer.this});
                            }
                        });
                        break;
                    }
                    break;
            }
            event.post();
            Log.m94d(str, "publishEvent X: code=" + code);
        }
    }

    private void onCanceled() {
        Log.m98i(TAG, "onCanceled");
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void setMessageSubscriber(MessageSubscriber messageSubscriber) {
        Log.m94d(TAG, "setMessageSubscriber");
        this.messageChannelRouter.addMessageSubscriber(messageSubscriber);
        this.messagePublisher = this.messageChannelRouter.newMessagePublisher();
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void release() {
        String str = TAG;
        Log.m94d(str, "release...E");
        this.inputChannel.cancel();
        this.outputChannel.cancel();
        this.nodes.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((GraphNode) obj).release();
            }
        });
        this.option.clear();
        Log.m94d(str, "release...X");
    }
}
