package com.samsung.android.sume.core.message;

import android.util.Pair;

import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes6.dex */
public class MessagePublisher {
    private final Function<Integer, List<MessageChannel>> messageChannelQuery;
    private final MessageProducer messageProducer = new MessageProducerImpl(this);
    private String name;

    public MessagePublisher(Function<Integer, List<MessageChannel>> messageChannelQuery) {
        this.messageChannelQuery = messageChannelQuery;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public MessageProducer getMessageProducer() {
        return this.messageProducer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Message getMessage(int code) {
        return new Message(code).setPublisher(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Message getMessage(int code, Map<String, Object> data) {
        return new Message(code).put(data).setPublisher(this);
    }

    List<MessageChannel> getChannels(int code) {
        return this.messageChannelQuery.apply(Integer.valueOf(code));
    }

    public void sendMessage(Message message) {
        message.setPublisher(this).post();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MessageProducerImpl implements MessageProducer {
        private final WeakReference<MessagePublisher> weakProducer;

        MessageProducerImpl(MessagePublisher messagePublisher) {
            this.weakProducer = new WeakReference<>(messagePublisher);
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int code) {
            return (Message)
                    Optional.ofNullable(this.weakProducer.get())
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            Message message;
                                            message = ((MessagePublisher) obj).getMessage(code);
                                            return message;
                                        }
                                    })
                            .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int code, final Map<String, Object> data) {
            return (Message)
                    Optional.ofNullable(this.weakProducer.get())
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            Message message;
                                            message =
                                                    ((MessagePublisher) obj).getMessage(code, data);
                                            return message;
                                        }
                                    })
                            .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int code, final Object data) {
            return (Message)
                    Optional.ofNullable(this.weakProducer.get())
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return MessagePublisher.MessageProducerImpl.this
                                                    .m9191x1dd59b1a(
                                                            code, data, (MessagePublisher) obj);
                                        }
                                    })
                            .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        /* renamed from: lambda$newMessage$2$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl, reason: not valid java name */
        /* synthetic */ Message m9191x1dd59b1a(int code, final Object data, MessagePublisher e) {
            return e.getMessage(
                    code,
                    new HashMap<
                            String,
                            Object>() { // from class:
                                        // com.samsung.android.sume.core.message.MessagePublisher.MessageProducerImpl.1
                        {
                            put("data", data);
                        }
                    });
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int code, final String key, final Object data) {
            return (Message)
                    Optional.ofNullable(this.weakProducer.get())
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda4
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return MessagePublisher.MessageProducerImpl.this
                                                    .m9192x5fecc879(
                                                            code,
                                                            key,
                                                            data,
                                                            (MessagePublisher) obj);
                                        }
                                    })
                            .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        /* renamed from: lambda$newMessage$3$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl, reason: not valid java name */
        /* synthetic */ Message m9192x5fecc879(
                int code, final String key, final Object data, MessagePublisher e) {
            return e.getMessage(
                    code,
                    new HashMap<
                            String,
                            Object>() { // from class:
                                        // com.samsung.android.sume.core.message.MessagePublisher.MessageProducerImpl.2
                        {
                            put(key, data);
                        }
                    });
        }

        /* renamed from: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$3, reason: invalid class name */
        class AnonymousClass3 extends HashMap<String, Object> {
            final /* synthetic */ Pair[] val$keyValues;

            AnonymousClass3(Pair[] pairArr) {
                this.val$keyValues = pairArr;
                Arrays.asList(this.val$keyValues)
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$3$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        MessagePublisher.MessageProducerImpl.AnonymousClass3.this
                                                .m9194x9f89700c((Pair) obj);
                                    }
                                });
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* renamed from: lambda$new$0$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl$3, reason: not valid java name */
            /* synthetic */ void m9194x9f89700c(Pair it) {
                put((String) it.first, it.second);
            }
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int code, final Pair<String, Object>... keyValues) {
            return (Message)
                    Optional.ofNullable(this.weakProducer.get())
                            .map(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return MessagePublisher.MessageProducerImpl.this
                                                    .m9193xa203f5d8(
                                                            code,
                                                            keyValues,
                                                            (MessagePublisher) obj);
                                        }
                                    })
                            .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        /* renamed from: lambda$newMessage$4$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl, reason: not valid java name */
        /* synthetic */ Message m9193xa203f5d8(int code, Pair[] keyValues, MessagePublisher e) {
            return e.getMessage(code, new AnonymousClass3(keyValues));
        }
    }
}
