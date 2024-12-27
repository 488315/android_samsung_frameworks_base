package com.samsung.android.sume.core.channel;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.evaluate.Evaluator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class ReceiveChannelRouter extends ChannelRouterBase {
    private static final String TAG = Def.tagOf((Class<?>) ReceiveChannelRouter.class);
    private Supplier<MediaBuffer> receiveOp;
    private final Type receiveType;

    public enum Type {
        ANY,
        ALL
    }

    public ReceiveChannelRouter(List<BufferChannel> channels, Type type) {
        super(channels);
        this.receiveType = type;
        init();
    }

    public ReceiveChannelRouter(Map<Evaluator, BufferChannel> evaluateChannelMap, Type type) {
        super(evaluateChannelMap);
        this.receiveType = type;
        init();
    }

    private void init() {
        if (this.receiveType == Type.ANY) {
            if (!this.evChannelMap.isEmpty()) {
                this.channels.addAll(new ArrayList(this.evChannelMap.values()));
            }
            this.receiveOp = new Supplier() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    MediaBuffer receiveAny;
                    receiveAny = ReceiveChannelRouter.this.receiveAny();
                    return receiveAny;
                }
            };
        } else {
            this.evChannelMap.clear();
            this.receiveOp = new Supplier() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    MediaBuffer receiveAll;
                    receiveAll = ReceiveChannelRouter.this.receiveAll();
                    return receiveAll;
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaBuffer receiveAll() {
        final List<MediaBuffer> buffers = (List) ((Stream) this.channels.stream().parallel()).map(new ReceiveChannelRouter$$ExternalSyntheticLambda3()).collect(Collectors.toList());
        int primaryId = IntStream.range(0, buffers.size()).filter(new IntPredicate() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda4
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                boolean containsExtra;
                containsExtra = ((MediaBuffer) buffers.get(i)).containsExtra("primary");
                return containsExtra;
            }
        }).findFirst().orElse(0);
        return MediaBuffer.groupOf(primaryId, buffers);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaBuffer receiveAny() {
        Log.d(TAG, "anyReceived: # of channel=" + this.channels.size());
        Def.require(!this.channels.isEmpty());
        if (this.channels.size() == 1) {
            Log.d(TAG, "channel: " + this.channels.get(0));
            return (MediaBuffer) this.channels.stream().findFirst().map(new ReceiveChannelRouter$$ExternalSyntheticLambda3()).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }
        final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(this.channels.size());
        final ExecutorService threadPool = Executors.newFixedThreadPool(this.channels.size());
        Map<Integer, Future<MediaBuffer>> results = (Map) IntStream.range(0, this.channels.size()).boxed().collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReceiveChannelRouter.this.m9119x12a5943b(threadPool, queue, (Integer) obj);
            }
        }));
        while (!results.isEmpty()) {
            try {
                try {
                    int id = queue.take().intValue();
                    MediaBuffer buffer = (MediaBuffer) ((Future) Objects.requireNonNull(results.remove(Integer.valueOf(id)))).get();
                    if (buffer != null) {
                        return buffer;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new CancellationException("buffer-channels receive thread are interrupted");
                }
            } finally {
                results.values().forEach(new Consumer() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Future) obj).cancel(true);
                    }
                });
            }
        }
        throw new CancellationException("all buffer-channels are canceled");
    }

    /* renamed from: lambda$receiveAny$2$com-samsung-android-sume-core-channel-ReceiveChannelRouter, reason: not valid java name */
    /* synthetic */ Future m9119x12a5943b(ExecutorService threadPool, final BlockingQueue queue, final Integer it) {
        return threadPool.submit(new Callable() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReceiveChannelRouter.this.m9118xca1c8dc(it, queue);
            }
        });
    }

    /* renamed from: lambda$receiveAny$1$com-samsung-android-sume-core-channel-ReceiveChannelRouter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9118xca1c8dc(Integer it, BlockingQueue queue) throws Exception {
        try {
            MediaBuffer buffer = this.channels.get(it.intValue()).receive();
            queue.put(it);
            return buffer;
        } catch (Exception e) {
            Log.d(TAG, "buffer-channel receive thread is interrupted: " + e.getMessage());
            queue.put(it);
            return null;
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer data) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        return this.receiveOp.get();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        return false;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        return false;
    }
}
