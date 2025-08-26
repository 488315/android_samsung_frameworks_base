package com.samsung.android.sume.core.channel;

import android.util.NtpTrustedTime$$ExternalSyntheticLambda5;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MediaBufferReader;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.graph.GraphEdge;
import com.samsung.android.sume.core.types.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class SendChannelRouter extends ChannelRouterBase {
    private static final String TAG = Def.tagOf((Class<?>) SendChannelRouter.class);
    private Consumer<MediaBuffer> sendOp;
    private final Type sendType;

    public enum Type {
        ANY,
        ALL,
        EVALUATE_ONLY,
        BROADCAST_ONLY
    }

    static /* synthetic */ BufferChannel lambda$new$0(BufferChannel bufferChannel, BufferChannel bufferChannel2) {
        return bufferChannel;
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

    public SendChannelRouter(List<BufferChannel> list) {
        super(list);
        this.sendType = Type.BROADCAST_ONLY;
        init();
    }

    public SendChannelRouter(Map<Evaluator, BufferChannel> map, Type type) {
        super(map);
        this.sendType = type;
        init();
    }

    public SendChannelRouter(Map<Evaluator, BufferChannel> map) {
        this(map, Type.ALL);
    }

    public SendChannelRouter(GraphEdge[] graphEdgeArr, Type type) {
        this((Map<Evaluator, BufferChannel>) Arrays.stream(graphEdgeArr).collect(Collectors.toMap(new SendChannelRouter$$ExternalSyntheticLambda8(), new SendChannelRouter$$ExternalSyntheticLambda9(), new BinaryOperator() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda10
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return SendChannelRouter.lambda$new$0((BufferChannel) obj, (BufferChannel) obj2);
            }
        }, new NtpTrustedTime$$ExternalSyntheticLambda5())), type);
    }

    public SendChannelRouter(GraphEdge[] graphEdgeArr) {
        this(graphEdgeArr, Type.ALL);
    }

    void init() {
        if (this.sendType == Type.EVALUATE_ONLY) {
            this.sendOp = new Consumer() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.evaluate((MediaBuffer) obj);
                }
            };
            return;
        }
        if (this.sendType == Type.BROADCAST_ONLY) {
            this.sendOp = new Consumer() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.broadcast((MediaBuffer) obj);
                }
            };
        } else if (this.sendType == Type.ANY) {
            this.sendOp = new Consumer() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.sendAny((MediaBuffer) obj);
                }
            };
        } else {
            this.sendOp = new Consumer() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.sendAll((MediaBuffer) obj);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean broadcast(final MediaBuffer mediaBuffer) {
        this.channels.forEach(new Consumer() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((BufferChannel) obj).send(mediaBuffer.asRef());
            }
        });
        return !this.channels.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean evaluate(final MediaBuffer mediaBuffer) {
        return this.evChannelMap.entrySet().stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SendChannelRouter.lambda$evaluate$4(mediaBuffer, (Map.Entry) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$evaluate$4(final MediaBuffer mediaBuffer, Map.Entry entry) {
        Evaluator evaluator = (Evaluator) entry.getKey();
        BufferChannel bufferChannel = (BufferChannel) entry.getValue();
        boolean z = mediaBuffer instanceof MediaBufferGroup;
        MediaBufferReader mediaBufferReaderOf = MediaBufferReader.of(z ? mediaBuffer.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SendChannelRouter.lambda$evaluate$2((MediaBuffer) obj);
            }
        }).findFirst().orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return mediaBuffer.asList().get(0);
            }
        }) : mediaBuffer, evaluator.getValueType());
        if (!evaluator.evaluate(mediaBufferReaderOf.get())) {
            return false;
        }
        if (z && mediaBuffer.containFlags(2)) {
            MediaBuffer primaryBuffer = ((MediaBufferGroup) mediaBuffer).getPrimaryBuffer();
            primaryBuffer.setExtra("evaluate-value", mediaBufferReaderOf.get());
            primaryBuffer.addExtra(mediaBuffer.getExtra());
            mediaBuffer = primaryBuffer;
        }
        bufferChannel.send(mediaBuffer.asRef());
        return true;
    }

    static /* synthetic */ boolean lambda$evaluate$2(MediaBuffer mediaBuffer) {
        return mediaBuffer.getFormat().getMediaType() == MediaType.SCALA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendAny(MediaBuffer mediaBuffer) {
        if (evaluate(mediaBuffer)) {
            return true;
        }
        return broadcast(mediaBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendAll(MediaBuffer mediaBuffer) {
        return evaluate(mediaBuffer) || broadcast(mediaBuffer);
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer mediaBuffer) {
        this.sendOp.accept(mediaBuffer);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        throw new UnsupportedOperationException();
    }
}
