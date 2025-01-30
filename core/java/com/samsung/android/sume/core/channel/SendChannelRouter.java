package com.samsung.android.sume.core.channel;

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

/* loaded from: classes4.dex */
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

  public SendChannelRouter(List<BufferChannel> channels) {
    super(channels);
    this.sendType = Type.BROADCAST_ONLY;
    init();
  }

  public SendChannelRouter(Map<Evaluator, BufferChannel> evChannelMap, Type sendType) {
    super(evChannelMap);
    this.sendType = sendType;
    init();
  }

  public SendChannelRouter(Map<Evaluator, BufferChannel> evChannelMap) {
    this(evChannelMap, Type.ALL);
  }

  public SendChannelRouter(GraphEdge[] edges, Type type) {
    this(
        (Map<Evaluator, BufferChannel>)
            Arrays.stream(edges)
                .collect(
                    Collectors.toMap(
                        new SendChannelRouter$$ExternalSyntheticLambda1(),
                        new SendChannelRouter$$ExternalSyntheticLambda2(),
                        new BinaryOperator() { // from class:
                                               // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda3
                          @Override // java.util.function.BiFunction
                          public final Object apply(Object obj, Object obj2) {
                            return SendChannelRouter.lambda$new$0(
                                (BufferChannel) obj, (BufferChannel) obj2);
                          }
                        },
                        new ChannelRouterBase$$ExternalSyntheticLambda3())),
        type);
  }

  static /* synthetic */ BufferChannel lambda$new$0(
      BufferChannel oldValue, BufferChannel newValue) {
    return oldValue;
  }

  public SendChannelRouter(GraphEdge[] edges) {
    this(edges, Type.ALL);
  }

  void init() {
    if (this.sendType == Type.EVALUATE_ONLY) {
      this.sendOp =
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SendChannelRouter.this.evaluate((MediaBuffer) obj);
            }
          };
      return;
    }
    if (this.sendType == Type.BROADCAST_ONLY) {
      this.sendOp =
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SendChannelRouter.this.broadcast((MediaBuffer) obj);
            }
          };
    } else if (this.sendType == Type.ANY) {
      this.sendOp =
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SendChannelRouter.this.sendAny((MediaBuffer) obj);
            }
          };
    } else {
      this.sendOp =
          new Consumer() { // from class:
                           // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SendChannelRouter.this.sendAll((MediaBuffer) obj);
            }
          };
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean broadcast(final MediaBuffer mediaBuffer) {
    this.channels.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda10
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((BufferChannel) obj).send(MediaBuffer.this.asRef());
          }
        });
    return !this.channels.isEmpty();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean evaluate(final MediaBuffer mediaBuffer) {
    return this.evChannelMap.entrySet().stream()
        .anyMatch(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return SendChannelRouter.lambda$evaluate$4(MediaBuffer.this, (Map.Entry) obj);
              }
            });
  }

  static /* synthetic */ boolean lambda$evaluate$4(final MediaBuffer mediaBuffer, Map.Entry e) {
    Evaluator evaluator = (Evaluator) e.getKey();
    BufferChannel bufferChannel = (BufferChannel) e.getValue();
    MediaBuffer evaluationBuffer = mediaBuffer;
    if (mediaBuffer instanceof MediaBufferGroup) {
      MediaBuffer evaluationBuffer2 =
          mediaBuffer.stream()
              .filter(
                  new Predicate() { // from class:
                                    // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      return SendChannelRouter.lambda$evaluate$2((MediaBuffer) obj);
                    }
                  })
              .findFirst()
              .orElseGet(
                  new Supplier() { // from class:
                                   // com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda5
                    @Override // java.util.function.Supplier
                    public final Object get() {
                      MediaBuffer mediaBuffer2;
                      mediaBuffer2 = MediaBuffer.this.asList().get(0);
                      return mediaBuffer2;
                    }
                  });
      evaluationBuffer = evaluationBuffer2;
    }
    MediaBufferReader<?> reader =
        MediaBufferReader.m343of(evaluationBuffer, evaluator.getValueType());
    if (evaluator.evaluate(reader.get())) {
      MediaBuffer emitBuffer = mediaBuffer;
      if ((mediaBuffer instanceof MediaBufferGroup) && mediaBuffer.containFlags(2)) {
        MediaBufferGroup bufferGroup = (MediaBufferGroup) mediaBuffer;
        emitBuffer = bufferGroup.getPrimaryBuffer();
        emitBuffer.setExtra("evaluate-value", reader.get());
        emitBuffer.addExtra(mediaBuffer.getExtra());
      }
      bufferChannel.send(emitBuffer.asRef());
      return true;
    }
    return false;
  }

  static /* synthetic */ boolean lambda$evaluate$2(MediaBuffer it) {
    return it.getFormat().getMediaType() == MediaType.SCALA;
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
  public void send(MediaBuffer data) {
    this.sendOp.accept(data);
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.channel.Channel
  public MediaBuffer receive() {
    throw new UnsupportedOperationException();
  }

  @Override // com.samsung.android.sume.core.channel.Channel
  public void close() {}

  @Override // com.samsung.android.sume.core.channel.Channel
  public void cancel() {}

  @Override // com.samsung.android.sume.core.channel.Channel
  public boolean isClosedForSend() {
    return false;
  }

  @Override // com.samsung.android.sume.core.channel.Channel
  public boolean isClosedForReceive() {
    return false;
  }
}
