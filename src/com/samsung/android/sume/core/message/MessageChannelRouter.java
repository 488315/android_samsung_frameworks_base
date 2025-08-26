package com.samsung.android.sume.core.message;

import android.app.PendingIntent$$ExternalSyntheticLambda2;
import android.util.Log;
import android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda8;
import com.samsung.android.sume.core.Def;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

/* loaded from: classes6.dex */
public class MessageChannelRouter {
    private static final String TAG = Def.tagOf((Class<?>) MessageChannelRouter.class);
    private final List<MessageChannel> errorListener = new LinkedList();
    private final List<MessageChannel> eventListener = new LinkedList();
    private final Map<Integer, List<MessageChannel>> messageSubscribers = new HashMap();
    private ReplayMessageChannel replayChannel;

    private static class ReplayMessageChannel extends BlockingMessageChannel {
        public ReplayMessageChannel(int i) {
            super("", i);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.samsung.android.sume.core.message.BlockingMessageChannel, com.samsung.android.sume.core.channel.Channel
        public void send(Message message) {
            Log.d(MessageChannelRouter.TAG, "send replay message: " + message.getCode());
            if (this.queue.offer(message)) {
                return;
            }
            this.queue.poll();
            Def.check(this.queue.offer(message), "fail to send message as replay", new Object[0]);
        }

        public int drainTo(List<Message> list) {
            return this.queue.drainTo(list);
        }
    }

    public MessageChannelRouter() {
    }

    public MessageChannelRouter(int i) {
        this.replayChannel = new ReplayMessageChannel(i);
    }

    public MessagePublisher newMessagePublisher() {
        return new MessagePublisher(new Function() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.queryMessageChannel(((Integer) obj).intValue());
            }
        });
    }

    List<MessageChannel> queryMessageChannel(int i) {
        ReplayMessageChannel replayMessageChannel;
        String str = TAG;
        Log.d(str, "code=" + i);
        if (Message.isError(i)) {
            return this.errorListener;
        }
        List<MessageChannel> list = (List) Stream.concat((Stream) Optional.ofNullable(this.messageSubscribers.get(Integer.valueOf(i))).map(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Stream.of((Object[]) new MessageChannel[0]);
            }
        }), this.eventListener.stream()).collect(Collectors.toList());
        Log.d(str, "messageChannels: " + Arrays.toString(list.toArray()));
        if (list.isEmpty() && (replayMessageChannel = this.replayChannel) != null) {
            list.add(replayMessageChannel);
        }
        return list;
    }

    public void addMessageSubscriber(MessageSubscriber messageSubscriber) {
        Log.d(TAG, "addMessageSubscriber");
        for (Integer num : messageSubscriber.getSubscribeMessages()) {
            if (Message.isError(num.intValue()) || num.intValue() == 993) {
                this.errorListener.add(messageSubscriber.getMessageChannel());
            } else if (num.intValue() == 990) {
                this.eventListener.add(messageSubscriber.getMessageChannel());
            } else {
                if (!this.messageSubscribers.containsKey(num)) {
                    this.messageSubscribers.put(num, new LinkedList());
                }
                this.messageSubscribers.get(num).add(messageSubscriber.getMessageChannel());
            }
        }
        if (this.replayChannel != null) {
            ArrayList arrayList = new ArrayList();
            this.replayChannel.drainTo(arrayList);
            Map map = (Map) arrayList.stream().collect(Collectors.partitioningBy(new Predicate() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.m9604xbafa26ef((Message) obj);
                }
            }));
            if (map.containsKey(false)) {
                this.replayChannel.queue.addAll((Collection) map.get(false));
            }
            for (final Message message : (List) Optional.ofNullable((List) map.get(true)).orElseGet(new PendingIntent$$ExternalSyntheticLambda2())) {
                List<MessageChannel> list = this.messageSubscribers.get(Integer.valueOf(message.getCode()));
                if (list != null) {
                    list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((MessageChannel) obj).send(message);
                        }
                    });
                }
            }
        }
    }

    /* renamed from: lambda$addMessageSubscriber$1$com-samsung-android-sume-core-message-MessageChannelRouter, reason: not valid java name */
    /* synthetic */ boolean m9604xbafa26ef(Message message) {
        return this.messageSubscribers.containsKey(Integer.valueOf(message.getCode()));
    }

    public void removeMessageSubscriber(final MessageSubscriber messageSubscriber) {
        this.messageSubscribers.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.message.MessageChannelRouter$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((List) obj2).remove(messageSubscriber);
            }
        });
    }
}
