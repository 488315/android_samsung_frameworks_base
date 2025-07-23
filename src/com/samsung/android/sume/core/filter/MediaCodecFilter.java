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

    /* JADX WARN: Removed duplicated region for block: B:11:0x00ec  */
    @Override // com.samsung.android.sume.core.message.MessageConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onMessageReceived(com.samsung.android.sume.core.message.Message r9) throws java.lang.UnsupportedOperationException {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.filter.MediaCodecFilter.onMessageReceived(com.samsung.android.sume.core.message.Message):boolean");
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
