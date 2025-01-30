package com.samsung.android.sume.core.message;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.filter.factory.MediaFilterFactory$$ExternalSyntheticLambda3;
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

/* loaded from: classes4.dex */
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
        List<Integer> keys = new ArrayList<>(this.messageConsumers.keySet());
        if (!this.eventListener.isEmpty()) {
            keys.add(990);
        }
        if (!this.errorListener.isEmpty()) {
            keys.add(993);
        }
        return (Integer[]) keys.toArray(new Integer[0]);
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public void onMessageReceived(final Message message) {
        int code = message.getCode();
        if (Message.isError(code)) {
            this.errorListener.forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((MessageConsumer) obj).onMessageReceived(Message.this);
                }
            });
        } else {
            Stream.concat((Stream) Optional.ofNullable(this.messageConsumers.get(Integer.valueOf(code))).map(new MediaFilterFactory$$ExternalSyntheticLambda3()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    Stream of;
                    of = Stream.of((Object[]) new MessageConsumer[0]);
                    return of;
                }
            }), this.eventListener.stream()).forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda3
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
        Log.m94d(str, "addMessageConsumer: " + messageConsumer);
        int[] codes = messageConsumer.getConsumeMessage();
        if (codes == null || codes.length == 0) {
            Log.m94d(str, "no consume code given");
            return;
        }
        for (int code : codes) {
            if (code == 993 || Message.isError(code)) {
                this.errorListener.add(messageConsumer);
            } else if (code == 990) {
                this.eventListener.add(messageConsumer);
            } else {
                if (!this.messageConsumers.containsKey(Integer.valueOf(code))) {
                    this.messageConsumers.put(Integer.valueOf(code), new LinkedList());
                }
                this.messageConsumers.get(Integer.valueOf(code)).add(messageConsumer);
            }
        }
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriber
    public void removeMessageConsumer(final MessageConsumer messageConsumer) {
        this.messageConsumers.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.message.MessageSubscriberBase$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((List) obj2).remove(MessageConsumer.this);
            }
        });
    }

    protected void release() {
        Log.m94d(TAG, "close message channel: " + this.messageChannel);
        this.messageChannel.cancel();
    }
}
