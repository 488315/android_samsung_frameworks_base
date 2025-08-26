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

    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        return false;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        return false;
    }

    public ReceiveChannelRouter(List<BufferChannel> list, Type type) {
        super(list);
        this.receiveType = type;
        init();
    }

    public ReceiveChannelRouter(Map<Evaluator, BufferChannel> map, Type type) {
        super(map);
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
                    return this.f$0.receiveAny();
                }
            };
        } else {
            this.evChannelMap.clear();
            this.receiveOp = new Supplier() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.receiveAll();
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaBuffer receiveAll() {
        final List list = (List) ((Stream) this.channels.stream().parallel()).map(new ReceiveChannelRouter$$ExternalSyntheticLambda3()).collect(Collectors.toList());
        return MediaBuffer.groupOf(IntStream.range(0, list.size()).filter(new IntPredicate() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda4
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return ((MediaBuffer) list.get(i)).containsExtra("primary");
            }
        }).findFirst().orElse(0), (List<MediaBuffer>) list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaBuffer receiveAny() {
        String str = TAG;
        Log.d(str, "anyReceived: # of channel=" + this.channels.size());
        Def.require(this.channels.isEmpty() ^ true);
        if (this.channels.size() == 1) {
            Log.d(str, "channel: " + this.channels.get(0));
            return (MediaBuffer) this.channels.stream().findFirst().map(new ReceiveChannelRouter$$ExternalSyntheticLambda3()).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(this.channels.size());
        final ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(this.channels.size());
        Map map = (Map) IntStream.range(0, this.channels.size()).boxed().collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9533x12a5943b(executorServiceNewFixedThreadPool, linkedBlockingQueue, (Integer) obj);
            }
        }));
        while (!map.isEmpty()) {
            try {
                try {
                    Integer num = (Integer) linkedBlockingQueue.take();
                    num.intValue();
                    MediaBuffer mediaBuffer = (MediaBuffer) ((Future) Objects.requireNonNull((Future) map.remove(num))).get();
                    if (mediaBuffer != null) {
                        return mediaBuffer;
                    }
                } catch (InterruptedException | ExecutionException unused) {
                    throw new CancellationException("buffer-channels receive thread are interrupted");
                }
            } finally {
                map.values().forEach(new Consumer() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda6
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
    /* synthetic */ Future m9533x12a5943b(ExecutorService executorService, final BlockingQueue blockingQueue, final Integer num) {
        return executorService.submit(new Callable() { // from class: com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m9532xca1c8dc(num, blockingQueue);
            }
        });
    }

    /* renamed from: lambda$receiveAny$1$com-samsung-android-sume-core-channel-ReceiveChannelRouter, reason: not valid java name */
    /* synthetic */ MediaBuffer m9532xca1c8dc(Integer num, BlockingQueue blockingQueue) throws Exception {
        try {
            MediaBuffer mediaBufferReceive = this.channels.get(num.intValue()).receive();
            blockingQueue.put(num);
            return mediaBufferReceive;
        } catch (Exception e) {
            Log.d(TAG, "buffer-channel receive thread is interrupted: " + e.getMessage());
            blockingQueue.put(num);
            return null;
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer mediaBuffer) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        return this.receiveOp.get();
    }
}
