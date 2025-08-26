package com.samsung.android.sume.core.filter;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.os.ConditionVariable;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.message.MessageProducer;
import com.samsung.android.sume.core.types.MediaType;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class MediaCodecFilter implements MediaInputStreamFilter, MediaOutputStreamFilter {
    private static final String TAG = Def.tagOf((Class<?>) MediaCodecFilter.class);
    protected final CodecDescriptor codecDescriptor;
    private final Condition condition;
    protected int contentId;
    protected final ConditionVariable cvPause;
    private final ReentrantLock lock;
    protected MediaCodec mediaCodec;
    protected MessageProducer messageProducer;
    protected boolean reachedInputEos;
    protected boolean reachedOutputEos;
    protected int receiveChannelCount;
    protected Function<Enum<?>, BufferChannel> receiveChannelQuery;
    protected int sendChannelCount;
    protected Function<Enum<?>, BufferChannel> sendChannelQuery;
    protected String codecTag = "";
    protected int processedFrames = 0;
    protected AtomicInteger numWholeFrames = new AtomicInteger(0);
    protected AtomicLong startTimeUs = new AtomicLong(-1);
    protected AtomicLong endTimeUs = new AtomicLong(Long.MAX_VALUE);
    protected AtomicLong lastTimestampUs = new AtomicLong(Long.MAX_VALUE);

    protected abstract void configCodec(Message message);

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return null;
    }

    protected MediaCodecFilter(CodecDescriptor codecDescriptor) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
        ConditionVariable conditionVariable = new ConditionVariable();
        this.cvPause = conditionVariable;
        this.codecDescriptor = codecDescriptor;
        conditionVariable.open();
    }

    protected void awaitCodecToReady() {
        String str = TAG;
        Log.d(str, "awaitCodecToReady...E: " + this);
        this.lock.lock();
        try {
            try {
                this.condition.await();
                this.lock.unlock();
                Log.d(str, "awaitCodecToReady...X: " + this);
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.lock.unlock();
                Log.d(TAG, "awaitCodecToReady...X: " + this);
            }
        } catch (Throwable th) {
            this.lock.unlock();
            Log.d(TAG, "awaitCodecToReady...X: " + this);
            throw th;
        }
    }

    protected void signalCodecFromReady() {
        String str = TAG;
        Log.d(str, "signalCodecFromReady...E: " + this);
        this.lock.lock();
        try {
            this.condition.signalAll();
            this.lock.unlock();
            Log.d(str, "signalCodecFromReady...X: " + this);
        } catch (Throwable th) {
            this.lock.unlock();
            Log.d(TAG, "signalCodecFromReady...X: " + this);
            throw th;
        }
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{1, 2, 5};
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00ec  */
    @Override // com.samsung.android.sume.core.message.MessageConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        String str = TAG;
        Log.d(str, "onMessageReceived: " + message.getCode());
        HashMap map = new HashMap();
        int code = message.getCode();
        boolean z = false;
        if (code == 1) {
            synchronized (message) {
                if (message.contains(Message.KEY_CONTENTS_ID)) {
                    this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID)).intValue();
                }
                CodecDescriptor codecDescriptor = (CodecDescriptor) getDescriptor();
                MediaType mediaType = (MediaType) message.get(Message.KEY_MEDIA_TYPE);
                if ((mediaType.isAudio() && codecDescriptor.getMediaType().isAudio()) || (mediaType.isVideo() && codecDescriptor.getMediaType().isVideo())) {
                    configCodec(message);
                }
                Log.d(str, "config-data of " + mediaType + " is not match this codec type " + codecDescriptor.getMediaType());
                return false;
            }
        }
        if (code == 2) {
            this.numWholeFrames.set(((Integer) message.get(Message.KEY_WHOLE_FRAMES)).intValue());
            if (message.contains(Message.KEY_START_TIME_US)) {
                this.startTimeUs.set(((Long) message.get(Message.KEY_START_TIME_US)).longValue());
            }
            if (message.contains(Message.KEY_END_TIME_US)) {
                this.endTimeUs.set(((Long) message.get(Message.KEY_END_TIME_US)).longValue());
            }
        } else {
            if (code == 5) {
                long jLongValue = ((Long) message.get("last-timestampUs")).longValue();
                Log.d(str, "last timestampUs set as " + jLongValue);
                this.lastTimestampUs.set(jLongValue);
            }
            if (message.isRequestToReply()) {
                message.reply(map);
            }
            return z;
        }
        z = true;
        if (message.isRequestToReply()) {
        }
        return z;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        Log.d(TAG, "release...E");
        MediaCodec mediaCodec = this.mediaCodec;
        if (mediaCodec != null) {
            try {
                try {
                    mediaCodec.stop();
                } catch (IllegalStateException unused) {
                    Log.w(TAG, "codec stop called but not started yet");
                }
            } finally {
                this.mediaCodec.release();
                this.mediaCodec = null;
            }
        }
        signalCodecFromReady();
        Log.d(TAG, "release...X");
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void pause() {
        this.cvPause.close();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void resume() {
        this.cvPause.open();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.codecDescriptor;
    }

    public MediaCodec getMediaCodec() {
        return this.mediaCodec;
    }

    @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
    public void setReceiveChannelQuery(Function<Enum<?>, BufferChannel> function, int i) {
        this.receiveChannelQuery = function;
        this.receiveChannelCount = i;
    }

    @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
    public Function<Enum<?>, BufferChannel> getReceiveChannelQuery() {
        return this.receiveChannelQuery;
    }

    @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
    public int getReceiveChannelCount() {
        return this.receiveChannelCount;
    }

    @Override // com.samsung.android.sume.core.filter.MediaOutputStreamFilter
    public void setSendChannelQuery(Function<Enum<?>, BufferChannel> function, int i) {
        this.sendChannelQuery = function;
        this.sendChannelCount = i;
    }

    @Override // com.samsung.android.sume.core.filter.MediaOutputStreamFilter
    public Function<Enum<?>, BufferChannel> getSendChannelQuery() {
        return this.sendChannelQuery;
    }

    @Override // com.samsung.android.sume.core.filter.MediaOutputStreamFilter
    public int getSendChannelCount() {
        return this.sendChannelCount;
    }

    protected String tagged(String str, Object... objArr) {
        return String.format(NavigationBarInflaterView.SIZE_MOD_START + this.codecTag + NavigationBarInflaterView.SIZE_MOD_END + str, objArr);
    }
}
