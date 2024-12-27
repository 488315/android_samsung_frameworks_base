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

    public MediaFilterTracer(MediaFilter successor, MessageProducer messageProducer) {
        super(successor);
        this.instantRun = false;
        this.contentId = -1;
        this.numBlocks = -1;
        this.messageHandlers = new ArrayList();
        this.messageProducer = messageProducer;
    }

    public MediaFilterTracer(MediaFilter successor, MessageProducer messageProducer, MediaFilter parent) {
        this(successor, messageProducer);
        if (parent instanceof InstantFilter) {
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
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        Log.d(TAG, "run: successor=" + this.successor);
        makeReport(513, ibuf);
        MutableMediaBuffer obuf2 = super.run(ibuf, obuf);
        makeReport(514, obuf2);
        return obuf2;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        Log.d(TAG, "release: successor=" + this.successor);
        makeReport(515);
        super.release();
        makeReport(516);
    }

    private void makeReport(int code) {
        makeReport(code, null);
    }

    private void makeReport(int code, MediaBuffer mediaBuffer) {
        Log.d(TAG, "makeReport: code=" + code + ", buffer=" + mediaBuffer);
        long currentInMillis = System.currentTimeMillis();
        final Message message = this.messageProducer.newMessage(code);
        message.put(Message.KEY_UNIT_ID, Integer.valueOf(this.successor.hashCode()));
        if (mediaBuffer != null) {
            int contentId = ((Integer) mediaBuffer.getExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId))).intValue();
            message.put(Message.KEY_CONTENTS_ID, Integer.valueOf(contentId));
            int blockId = ((Integer) mediaBuffer.getExtra(Message.KEY_BLOCK_ID, -1)).intValue();
            if (blockId != -1) {
                message.put(Message.KEY_BLOCK_ID, Integer.valueOf(blockId));
                message.put(Message.KEY_NUM_BLOCKS, mediaBuffer.getExtra(Message.KEY_NUM_BLOCKS, Integer.valueOf(this.numBlocks)));
            }
            if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                message.put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
            }
        }
        switch (code) {
            case 511:
                message.put(Message.KEY_START_TIME_MS, Long.valueOf(currentInMillis));
                Map<String, Object> shortDescription = getShortDescription(getDescriptor());
                if (!shortDescription.isEmpty()) {
                    message.put(Message.KEY_UNIT_DESCRIPTION, shortDescription);
                    break;
                }
                break;
            case 512:
                message.put(Message.KEY_END_TIME_MS, Long.valueOf(currentInMillis));
                break;
            case 513:
                message.put(Message.KEY_START_TIME_MS, Long.valueOf(currentInMillis));
                break;
            case 514:
                message.put(Message.KEY_END_TIME_MS, Long.valueOf(currentInMillis));
                if (this.instantRun) {
                    this.messageHandlers.add(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterTracer$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            MediaFilterTracer.this.m9140x3bc8565(message, (Message) obj);
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
                            ((Consumer) obj).accept(Message.this);
                        }
                    });
                }
                message.put(Message.KEY_START_TIME_MS, Long.valueOf(currentInMillis));
                break;
            case 516:
                if (this.instantRun) {
                    this.messageHandlers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterTracer$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((Consumer) obj).accept(Message.this);
                        }
                    });
                }
                message.put(Message.KEY_END_TIME_MS, Long.valueOf(currentInMillis));
                break;
        }
        message.post();
    }

    /* renamed from: lambda$makeReport$0$com-samsung-android-sume-core-filter-MediaFilterTracer, reason: not valid java name */
    /* synthetic */ void m9140x3bc8565(Message message, Message msg) {
        msg.put(Message.KEY_CONTENTS_ID, message.get(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId)));
    }

    private Map<String, Object> getShortDescription(MFDescriptor descriptor) {
        Log.d(TAG, "getShortDescription: descriptor=" + descriptor);
        Map<String, Object> shortDescription = new HashMap<>();
        if (descriptor instanceof NNFWDescriptor) {
            NNFWDescriptor nnfwDescriptor = (NNFWDescriptor) descriptor;
            shortDescription.put("type", "NNFWDescriptor");
            shortDescription.put("model", nnfwDescriptor.getNNDescriptor().getModelId());
            shortDescription.put("fw", nnfwDescriptor.getFw());
            shortDescription.put("hw", nnfwDescriptor.getHw());
            shortDescription.put("input-data-type", nnfwDescriptor.getInputFormat().getDataType());
            shortDescription.put("input-color-format", nnfwDescriptor.getInputFormat().getColorFormat());
            shortDescription.put("input-shape", nnfwDescriptor.getInputFormat().getShape());
            shortDescription.put("output-data-type", nnfwDescriptor.getOutputFormat().getDataType());
            shortDescription.put("output-color-format", nnfwDescriptor.getOutputFormat().getColorFormat());
            shortDescription.put("output-shape", nnfwDescriptor.getOutputFormat().getShape());
        }
        return shortDescription;
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{7};
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        Log.d(TAG, "onMessageReceived: " + message);
        switch (message.getCode()) {
            case 7:
                this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID, -1)).intValue();
                this.numBlocks = ((Integer) message.get(Message.KEY_WHOLE_FRAMES, -1)).intValue();
                break;
        }
        return false;
    }
}
