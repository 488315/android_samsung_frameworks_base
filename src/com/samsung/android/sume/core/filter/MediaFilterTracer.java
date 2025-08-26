package com.samsung.android.sume.core.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.message.MessageProducer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public class MediaFilterTracer extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) MediaFilterTracer.class);
    private int contentId;
    private boolean instantRun;
    private final List<Consumer<Message>> messageHandlers;
    private final MessageProducer messageProducer;
    private int numBlocks;

    public MediaFilterTracer(MediaFilter mediaFilter, MessageProducer messageProducer) {
        super(mediaFilter);
        this.instantRun = false;
        this.contentId = -1;
        this.numBlocks = -1;
        this.messageHandlers = new ArrayList();
        this.messageProducer = messageProducer;
    }

    public MediaFilterTracer(MediaFilter mediaFilter, MessageProducer messageProducer, MediaFilter mediaFilter2) {
        this(mediaFilter, messageProducer);
        if (mediaFilter2 instanceof InstantFilter) {
            this.instantRun = true;
        }
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        Log.d(TAG, "prepare: successor=" + this.successor);
        makeReport(511);
        super.prepare();
        makeReport(512);
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        Log.d(TAG, "run: successor=" + this.successor);
        makeReport(513, mediaBuffer);
        MutableMediaBuffer mutableMediaBufferRun = super.run(mediaBuffer, mutableMediaBuffer);
        makeReport(514, mutableMediaBufferRun);
        return mutableMediaBufferRun;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        Log.d(TAG, "release: successor=" + this.successor);
        makeReport(515);
        super.release();
        makeReport(516);
    }

    private void makeReport(int i) {
        makeReport(i, null);
    }

    private void makeReport(int i, MediaBuffer mediaBuffer) {
        Log.d(TAG, "makeReport: code=" + i + ", buffer=" + mediaBuffer);
        long jCurrentTimeMillis = System.currentTimeMillis();
        final Message messageNewMessage = this.messageProducer.newMessage(i);
        messageNewMessage.put(Message.KEY_UNIT_ID, Integer.valueOf(this.successor.hashCode()));
        if (mediaBuffer != null) {
            Integer num = (Integer) mediaBuffer.getExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId));
            num.intValue();
            messageNewMessage.put(Message.KEY_CONTENTS_ID, num);
            Integer num2 = (Integer) mediaBuffer.getExtra(Message.KEY_BLOCK_ID, -1);
            if (num2.intValue() != -1) {
                messageNewMessage.put(Message.KEY_BLOCK_ID, num2);
                messageNewMessage.put(Message.KEY_NUM_BLOCKS, mediaBuffer.getExtra(Message.KEY_NUM_BLOCKS, Integer.valueOf(this.numBlocks)));
            }
            if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                messageNewMessage.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
            }
        }
        switch (i) {
            case 511:
                messageNewMessage.put(Message.KEY_START_TIME_MS, Long.valueOf(jCurrentTimeMillis));
                Map<String, Object> shortDescription = getShortDescription(getDescriptor());
                if (!shortDescription.isEmpty()) {
                    messageNewMessage.put(Message.KEY_UNIT_DESCRIPTION, shortDescription);
                    break;
                }
                break;
            case 512:
                messageNewMessage.put(Message.KEY_END_TIME_MS, Long.valueOf(jCurrentTimeMillis));
                break;
            case 513:
                messageNewMessage.put(Message.KEY_START_TIME_MS, Long.valueOf(jCurrentTimeMillis));
                break;
            case 514:
                messageNewMessage.put(Message.KEY_END_TIME_MS, Long.valueOf(jCurrentTimeMillis));
                if (this.instantRun) {
                    this.messageHandlers.add(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterTracer$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.m9554x3bc8565(messageNewMessage, (Message) obj);
                        }
                    });
                    break;
                }
                break;
            case 515:
                if (this.instantRun) {
                    this.messageHandlers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterTracer$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((Consumer) obj).accept(messageNewMessage);
                        }
                    });
                }
                messageNewMessage.put(Message.KEY_START_TIME_MS, Long.valueOf(jCurrentTimeMillis));
                break;
            case 516:
                if (this.instantRun) {
                    this.messageHandlers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterTracer$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((Consumer) obj).accept(messageNewMessage);
                        }
                    });
                }
                messageNewMessage.put(Message.KEY_END_TIME_MS, Long.valueOf(jCurrentTimeMillis));
                break;
        }
        messageNewMessage.post();
    }

    /* renamed from: lambda$makeReport$0$com-samsung-android-sume-core-filter-MediaFilterTracer, reason: not valid java name */
    /* synthetic */ void m9554x3bc8565(Message message, Message message2) {
        message2.put(Message.KEY_CONTENTS_ID, message.get(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId)));
    }

    private Map<String, Object> getShortDescription(MFDescriptor mFDescriptor) {
        Log.d(TAG, "getShortDescription: descriptor=" + mFDescriptor);
        HashMap map = new HashMap();
        if (mFDescriptor instanceof NNFWDescriptor) {
            NNFWDescriptor nNFWDescriptor = (NNFWDescriptor) mFDescriptor;
            map.put("type", "NNFWDescriptor");
            map.put("model", nNFWDescriptor.getNNDescriptor().getModelId());
            map.put("fw", nNFWDescriptor.getFw());
            map.put("hw", nNFWDescriptor.getHw());
            map.put("input-data-type", nNFWDescriptor.getInputFormat().getDataType());
            map.put("input-color-format", nNFWDescriptor.getInputFormat().getColorFormat());
            map.put("input-shape", nNFWDescriptor.getInputFormat().getShape());
            map.put("output-data-type", nNFWDescriptor.getOutputFormat().getDataType());
            map.put("output-color-format", nNFWDescriptor.getOutputFormat().getColorFormat());
            map.put("output-shape", nNFWDescriptor.getOutputFormat().getShape());
        }
        return map;
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{7};
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        Log.d(TAG, "onMessageReceived: " + message);
        if (message.getCode() != 7) {
            return false;
        }
        this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID, -1)).intValue();
        this.numBlocks = ((Integer) message.get(Message.KEY_WHOLE_FRAMES, -1)).intValue();
        return false;
    }
}
