package com.samsung.android.sume.core.message;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda6;
import com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class MessageChannelRouter {
    private static final String TAG = Def.tagOf((Class<?>) MessageChannelRouter.class);
    private final List<MessageChannel> errorListener = new LinkedList();
    private final List<MessageChannel> eventListener = new LinkedList();
    private final Map<Integer, List<MessageChannel>> messageSubscribers = new HashMap();
    private ReplayMessageChannel replayChannel;

    private static class ReplayMessageChannel extends BlockingMessageChannel {
        public ReplayMessageChannel(int replay) {
            super("", replay);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.samsung.android.sume.core.message.BlockingMessageChannel, com.samsung.android.sume.core.channel.Channel
        public void send(Message data) {
            Log.m94d(MessageChannelRouter.TAG, "send replay message: " + data.getCode());
            if (!this.queue.offer(data)) {
                this.queue.poll();
                Def.check(this.queue.offer(data), "fail to send message as replay", new Object[0]);
            }
        }

        public int drainTo(List<Message> messages) {
            return this.queue.drainTo(messages);
        }
    }

    public MessageChannelRouter() {
    }

    public MessageChannelRouter(int replay) {
        this.replayChannel = new ReplayMessageChannel(replay);
    }

    public MessagePublisher newMessagePublisher() {
        return new MessagePublisher(new Function() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MessageChannelRouter.this.queryMessageChannel(((Integer) obj).intValue());
            }
        });
    }

    List<MessageChannel> queryMessageChannel(int code) {
        ReplayMessageChannel replayMessageChannel;
        String str = TAG;
        Log.m94d(str, "code=" + code);
        if (Message.isError(code)) {
            return this.errorListener;
        }
        List<MessageChannel> messageChannels = (List) Stream.concat((Stream) Optional.ofNullable(this.messageSubscribers.get(Integer.valueOf(code))).map(new MediaFilterFactory$$ExternalSyntheticLambda3()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Stream of;
                of = Stream.of((Object[]) new MessageChannel[0]);
                return of;
            }
        }), this.eventListener.stream()).collect(Collectors.toList());
        Log.m94d(str, "messageChannels: " + Arrays.toString(messageChannels.toArray()));
        if (messageChannels.isEmpty() && (replayMessageChannel = this.replayChannel) != null) {
            messageChannels.add(replayMessageChannel);
        }
        return messageChannels;
    }

    public void addMessageSubscriber(MessageSubscriber messageSubscriber) {
        Log.m94d(TAG, "addMessageSubscriber");
        Integer[] subscribeCodes = messageSubscriber.getSubscribeMessages();
        for (Integer code : subscribeCodes) {
            if (Message.isError(code.intValue()) || code.intValue() == 993) {
                this.errorListener.add(messageSubscriber.getMessageChannel());
            } else if (code.intValue() == 990) {
                this.eventListener.add(messageSubscriber.getMessageChannel());
            } else {
                if (!this.messageSubscribers.containsKey(code)) {
                    this.messageSubscribers.put(code, new LinkedList());
                }
                this.messageSubscribers.get(code).add(messageSubscriber.getMessageChannel());
            }
        }
        if (this.replayChannel != null) {
            List<Message> replayMessages = new ArrayList<>();
            this.replayChannel.drainTo(replayMessages);
            Map<Boolean, List<Message>> partitions = (Map) replayMessages.stream().collect(Collectors.partitioningBy(new Predicate() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return MessageChannelRouter.this.m428xbafa26ef((Message) obj);
                }
            }));
            if (partitions.containsKey(false)) {
                this.replayChannel.queue.addAll(partitions.get(false));
            }
            for (final Message message : (List) Optional.ofNullable(partitions.get(true)).orElseGet(new ChannelRouterBase$$ExternalSyntheticLambda6())) {
                List<MessageChannel> channels = this.messageSubscribers.get(Integer.valueOf(message.getCode()));
                if (channels != null) {
                    channels.forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((MessageChannel) obj).send(Message.this);
                        }
                    });
                }
            }
        }
    }

    /* renamed from: lambda$addMessageSubscriber$1$com-samsung-android-sume-core-message-MessageChannelRouter */
    /* synthetic */ boolean m428xbafa26ef(Message it) {
        return this.messageSubscribers.containsKey(Integer.valueOf(it.getCode()));
    }

    public void removeMessageSubscriber(final MessageSubscriber messageSubscriber) {
        this.messageSubscribers.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((List) obj2).remove(MessageSubscriber.this);
            }
        });
    }
}
