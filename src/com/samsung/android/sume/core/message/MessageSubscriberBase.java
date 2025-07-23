package com.samsung.android.sume.core.message;

import android.util.Log;
import android.view.contentprotection.ContentProtectionEventProcessor$$ExternalSyntheticLambda8;
import com.samsung.android.sume.core.Def;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class MessageSubscriberBase implements MessageSubscriber {
    private static final String TAG = Def.tagOf((Class<?>) MessageSubscriberBase.class);
    protected MessageChannel messageChannel;
    protected Map<Integer, List<MessageConsumer>> messageConsumers = new HashMap();
    protected List<MessageConsumer> errorListener = new LinkedList();
    protected List<MessageConsumer> eventListener = new LinkedList();

    public MessageSubscriberBase(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public MessageChannel getMessageChannel() {
        return this.messageChannel;
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public void bindToMessageChannelRouter(MessageChannelRouter messageChannelRouter) {
        messageChannelRouter.addMessageSubscriber(this);
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public Integer[] getSubscribeMessages() {
        ArrayList arrayList = new ArrayList(this.messageConsumers.keySet());
        if (!this.eventListener.isEmpty()) {
            arrayList.add(990);
        }
        if (!this.errorListener.isEmpty()) {
            arrayList.add(993);
        }
        return (Integer[]) arrayList.toArray(new Integer[0]);
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public void onMessageReceived(final Message message) {
        int code = message.getCode();
        if (Message.isError(code)) {
            this.errorListener.forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((MessageConsumer) obj).onMessageReceived(Message.this);
                }
            });
        } else {
            Stream.concat((Stream) Optional.ofNullable(this.messageConsumers.get(Integer.valueOf(code))).map(new ContentProtectionEventProcessor$$ExternalSyntheticLambda8()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    Stream of;
                    of = Stream.of((Object[]) new MessageConsumer[0]);
                    return of;
                }
            }), this.eventListener.stream()).forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((MessageConsumer) obj).onMessageReceived(Message.this);
                }
            });
        }
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public void addMessageConsumer(MessageConsumer messageConsumer) {
        String str = TAG;
        Log.d(str, "addMessageConsumer: " + messageConsumer);
        int[] consumeMessage = messageConsumer.getConsumeMessage();
        if (consumeMessage == null || consumeMessage.length == 0) {
            Log.d(str, "no consume code given");
            return;
        }
        for (int i : consumeMessage) {
            if (i == 993 || Message.isError(i)) {
                this.errorListener.add(messageConsumer);
            } else if (i == 990) {
                this.eventListener.add(messageConsumer);
            } else {
                if (!this.messageConsumers.containsKey(Integer.valueOf(i))) {
                    this.messageConsumers.put(Integer.valueOf(i), new LinkedList());
                }
                this.messageConsumers.get(Integer.valueOf(i)).add(messageConsumer);
            }
        }
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public void removeMessageConsumer(final MessageConsumer messageConsumer) {
        this.messageConsumers.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((List) obj2).remove(MessageConsumer.this);
            }
        });
    }

    protected void release() {
        Log.d(TAG, "close message channel: " + this.messageChannel);
        this.messageChannel.cancel();
    }
}
