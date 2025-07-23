package com.samsung.android.sume.core.message;

import android.util.Pair;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.message.MessagePublisher;
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

    public MessagePublisher(Function<Integer, List<MessageChannel>> function) {
        this.messageChannelQuery = function;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public MessageProducer getMessageProducer() {
        return this.messageProducer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Message getMessage(int i) {
        return new Message(i).setPublisher(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Message getMessage(int i, Map<String, Object> map) {
        return new Message(i).put(map).setPublisher(this);
    }

    List<MessageChannel> getChannels(int i) {
        return this.messageChannelQuery.apply(Integer.valueOf(i));
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
        public Message newMessage(final int i) {
            return (Message) Optional.ofNullable(this.weakProducer.get()).map(new Function() { // from class: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Message message;
                    message = ((MessagePublisher) obj).getMessage(i);
                    return message;
                }
            }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int i, final Map<String, Object> map) {
            return (Message) Optional.ofNullable(this.weakProducer.get()).map(new Function() { // from class: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Message message;
                    message = ((MessagePublisher) obj).getMessage(i, map);
                    return message;
                }
            }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int i, final Object obj) {
            return (Message) Optional.ofNullable(this.weakProducer.get()).map(new Function() { // from class: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return MessagePublisher.MessageProducerImpl.this.m9592x1dd59b1a(i, obj, (MessagePublisher) obj2);
                }
            }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        /* renamed from: lambda$newMessage$2$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl, reason: not valid java name */
        /* synthetic */ Message m9592x1dd59b1a(int i, Object obj, MessagePublisher messagePublisher) {
            return messagePublisher.getMessage(i, new HashMap<String, Object>(obj) { // from class: com.samsung.android.sume.core.message.MessagePublisher.MessageProducerImpl.1
                final /* synthetic */ Object val$data;

                {
                    this.val$data = obj;
                    put("data", obj);
                }
            });
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int i, final String str, final Object obj) {
            return (Message) Optional.ofNullable(this.weakProducer.get()).map(new Function() { // from class: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return MessagePublisher.MessageProducerImpl.this.m9593x5fecc879(i, str, obj, (MessagePublisher) obj2);
                }
            }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        /* renamed from: lambda$newMessage$3$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl, reason: not valid java name */
        /* synthetic */ Message m9593x5fecc879(int i, String str, Object obj, MessagePublisher messagePublisher) {
            return messagePublisher.getMessage(i, new HashMap<String, Object>(str, obj) { // from class: com.samsung.android.sume.core.message.MessagePublisher.MessageProducerImpl.2
                final /* synthetic */ Object val$data;
                final /* synthetic */ String val$key;

                {
                    this.val$key = str;
                    this.val$data = obj;
                    put(str, obj);
                }
            });
        }

        /* renamed from: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$3, reason: invalid class name */
        class AnonymousClass3 extends HashMap<String, Object> {
            final /* synthetic */ Pair[] val$keyValues;

            AnonymousClass3(Pair[] pairArr) {
                this.val$keyValues = pairArr;
                Arrays.asList(pairArr).forEach(new Consumer() { // from class: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MessagePublisher.MessageProducerImpl.AnonymousClass3.this.m9595x9f89700c((Pair) obj);
                    }
                });
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* renamed from: lambda$new$0$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl$3, reason: not valid java name */
            /* synthetic */ void m9595x9f89700c(Pair pair) {
                put((String) pair.first, pair.second);
            }
        }

        @Override // com.samsung.android.sume.core.message.MessageProducer
        public Message newMessage(final int i, final Pair<String, Object>... pairArr) {
            return (Message) Optional.ofNullable(this.weakProducer.get()).map(new Function() { // from class: com.samsung.android.sume.core.message.MessagePublisher$MessageProducerImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MessagePublisher.MessageProducerImpl.this.m9594xa203f5d8(i, pairArr, (MessagePublisher) obj);
                }
            }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        }

        /* renamed from: lambda$newMessage$4$com-samsung-android-sume-core-message-MessagePublisher$MessageProducerImpl, reason: not valid java name */
        /* synthetic */ Message m9594xa203f5d8(int i, Pair[] pairArr, MessagePublisher messagePublisher) {
            return messagePublisher.getMessage(i, new AnonymousClass3(pairArr));
        }
    }
}
