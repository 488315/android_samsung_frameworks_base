package com.samsung.android.sume.core.graph;

import android.os.Bundle;
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.samsung.android.sume.core.buffer.MediaBuffer onReceiveOutputBuffer(com.samsung.android.sume.core.buffer.MediaBuffer r4) {
        /*
            r3 = this;
            com.samsung.android.sume.core.format.MediaFormat r0 = r4.getFormat()
            com.samsung.android.sume.core.types.MediaType r0 = r0.getMediaType()
            java.util.concurrent.ConcurrentHashMap<java.lang.Integer, com.samsung.android.sume.core.buffer.MediaBuffer> r3 = r3.outBufferMap
            java.lang.String r1 = "contents-id"
            java.lang.Object r1 = r4.getExtra(r1)
            java.lang.Object r3 = r3.remove(r1)
            com.samsung.android.sume.core.buffer.MediaBuffer r3 = (com.samsung.android.sume.core.buffer.MediaBuffer) r3
            com.samsung.android.sume.core.types.MediaType r1 = com.samsung.android.sume.core.types.MediaType.SCALA
            if (r0 == r1) goto Lac
            com.samsung.android.sume.core.types.MediaType r1 = com.samsung.android.sume.core.types.MediaType.META
            if (r0 == r1) goto Lac
            if (r3 == 0) goto Lac
            java.lang.String r0 = com.samsung.android.sume.core.graph.GraphBase.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "onReceiveOutputBuffer: "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r2 = " => "
            r1.append(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            com.samsung.android.sume.core.functional.Operator r0 = com.samsung.android.sume.solution.filter.UniImgp.ofUnified()
            com.samsung.android.sume.core.buffer.MutableMediaBuffer r1 = com.samsung.android.sume.core.buffer.MediaBuffer.mutableOf(r3)
            r0.run(r4, r1)
            com.samsung.android.sume.core.format.MediaFormat r0 = r3.getFormat()
            com.samsung.android.sume.core.types.MediaType r0 = r0.getMediaType()
            com.samsung.android.sume.core.types.MediaType r1 = com.samsung.android.sume.core.types.MediaType.COMPRESSED_IMAGE
            if (r0 != r1) goto La1
            java.util.Map r4 = r4.getExtra()
            r3.setExtra(r4)
            r4 = 0
            java.lang.String r0 = "file-descriptor"
            java.lang.Object r0 = r3.getExtra(r0)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            android.os.ParcelFileDescriptor r0 = (android.os.ParcelFileDescriptor) r0     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            java.io.FileDescriptor r0 = r0.getFileDescriptor()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L83
            java.lang.Class<android.graphics.Bitmap> r4 = android.graphics.Bitmap.class
            java.lang.Object r4 = r3.getTypedData(r4)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> L94
            android.graphics.Bitmap r4 = (android.graphics.Bitmap) r4     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> L94
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> L94
            r2 = 95
            r4.compress(r0, r2, r1)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> L94
            r1.close()     // Catch: java.io.IOException -> L8f
            goto La1
        L7f:
            r4 = move-exception
            goto L86
        L81:
            r3 = move-exception
            goto L96
        L83:
            r0 = move-exception
            r1 = r4
            r4 = r0
        L86:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L94
            if (r1 == 0) goto La1
            r1.close()     // Catch: java.io.IOException -> L8f
            goto La1
        L8f:
            r4 = move-exception
            r4.printStackTrace()
            goto La1
        L94:
            r3 = move-exception
            r4 = r1
        L96:
            if (r4 == 0) goto La0
            r4.close()     // Catch: java.io.IOException -> L9c
            goto La0
        L9c:
            r4 = move-exception
            r4.printStackTrace()
        La0:
            throw r3
        La1:
            r4 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            java.lang.String r0 = "freezed"
            r3.setExtra(r0, r4)
            return r3
        Lac:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.graph.GraphBase.onReceiveOutputBuffer(com.samsung.android.sume.core.buffer.MediaBuffer):com.samsung.android.sume.core.buffer.MediaBuffer");
    }

    protected void runBatch(List<MediaBuffer> list, List<MediaBuffer> list2) {
        Log.d(TAG, "runBatch: # of inputs " + list.size());
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GraphBase.this.m9576lambda$runBatch$0$comsamsungandroidsumecoregraphGraphBase((MediaBuffer) obj);
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
                MediaBuffer onReceiveOutputBuffer = onReceiveOutputBuffer(this.outputChannel.receive());
                if (!this.option.isOutputOnEventCallback()) {
                    list2.add(onReceiveOutputBuffer);
                }
                publishEvent(510, onReceiveOutputBuffer);
                size = i;
            }
        } catch (CancellationException unused) {
            onCanceled();
        }
    }

    /* renamed from: lambda$runBatch$0$com-samsung-android-sume-core-graph-GraphBase, reason: not valid java name */
    /* synthetic */ void m9576lambda$runBatch$0$comsamsungandroidsumecoregraphGraphBase(MediaBuffer mediaBuffer) {
        this.inputChannel.send(mediaBuffer);
        publishEvent(509, mediaBuffer);
    }

    protected void runOneByOne(List<MediaBuffer> list, final List<MediaBuffer> list2) {
        Log.d(TAG, "runOneByOne: # of inputs " + list.size());
        try {
            list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    GraphBase.this.m9577x8ab719a8(list2, (MediaBuffer) obj);
                }
            });
        } catch (CancellationException unused) {
            onCanceled();
        }
    }

    /* renamed from: lambda$runOneByOne$1$com-samsung-android-sume-core-graph-GraphBase, reason: not valid java name */
    /* synthetic */ void m9577x8ab719a8(List list, MediaBuffer mediaBuffer) {
        this.inputChannel.send(mediaBuffer);
        publishEvent(509, mediaBuffer);
        MediaBuffer onReceiveOutputBuffer = onReceiveOutputBuffer(this.outputChannel.receive());
        if (!this.option.isOutputOnEventCallback()) {
            list.add(onReceiveOutputBuffer);
        }
        publishEvent(510, onReceiveOutputBuffer);
    }

    private void publishEvent(int i, final MediaBuffer mediaBuffer) {
        long j;
        String str = TAG;
        Log.d(str, "publishEvent E: code=" + i + ", buffer=" + mediaBuffer);
        if (this.messagePublisher != null) {
            final Event of = Event.of(i);
            of.setPublisher(this.messagePublisher);
            if (i == 509) {
                of.put(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
                if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                    of.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                }
                of.put(Message.KEY_START_TIME_MS, Long.valueOf(System.currentTimeMillis()));
            } else if (i == 510) {
                of.put(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
                if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                    of.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                }
                of.put("width", Integer.valueOf(mediaBuffer.getCols()));
                of.put("height", Integer.valueOf(mediaBuffer.getRows()));
                of.put(Message.KEY_END_TIME_MS, Long.valueOf(System.currentTimeMillis()));
                Stream.of((Object[]) new String[]{"rotation-degrees", "last-video-timestamp-us", "last-audio-timestamp-us"}).filter(new Predicate() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean contains;
                        contains = MediaBuffer.this.getFormat().contains((String) obj);
                        return contains;
                    }
                }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Event.this.put(r2, mediaBuffer.getFormat().get((String) obj));
                    }
                });
                if (mediaBuffer.getFormat().getMediaType().isVideo()) {
                    long longValue = ((Long) Optional.ofNullable(of.get("last-video-timestamp-us")).orElse(-1L)).longValue();
                    long longValue2 = ((Long) Optional.ofNullable(of.get("last-audio-timestamp-us")).orElse(-1L)).longValue();
                    if (longValue > longValue2) {
                        j = (longValue / 1000) + 1;
                    } else {
                        j = (longValue2 / 1000) + 1;
                    }
                    of.put("duration", Long.valueOf(j));
                }
                if (this.option.isOutputOnEventCallback()) {
                    Log.d(str, "set output buffer to event cb");
                    of.setBundledDataHandler(new Message.BundledDataHandler() { // from class: com.samsung.android.sume.core.graph.GraphBase$$ExternalSyntheticLambda2
                        @Override // com.samsung.android.sume.core.message.Message.BundledDataHandler
                        public final void accept(Bundle bundle) {
                            bundle.putParcelableArray("buffer-list", new MediaBuffer[]{MediaBuffer.this});
                        }
                    });
                }
            }
            of.post();
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
