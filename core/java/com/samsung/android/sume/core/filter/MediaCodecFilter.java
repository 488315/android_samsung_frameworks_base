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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
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
    StringBuilder sb;
    String str = TAG;
    Log.m94d(str, "awaitCodecToReady...E: " + this);
    this.lock.lock();
    try {
      try {
        this.condition.await();
        this.lock.unlock();
        sb = new StringBuilder();
      } catch (InterruptedException e) {
        e.printStackTrace();
        this.lock.unlock();
        str = TAG;
        sb = new StringBuilder();
      }
      Log.m94d(str, sb.append("awaitCodecToReady...X: ").append(this).toString());
    } catch (Throwable th) {
      this.lock.unlock();
      Log.m94d(TAG, "awaitCodecToReady...X: " + this);
      throw th;
    }
  }

  protected void signalCodecFromReady() {
    String str = TAG;
    Log.m94d(str, "signalCodecFromReady...E: " + this);
    this.lock.lock();
    try {
      this.condition.signalAll();
      this.lock.unlock();
      Log.m94d(str, "signalCodecFromReady...X: " + this);
    } catch (Throwable th) {
      this.lock.unlock();
      Log.m94d(TAG, "signalCodecFromReady...X: " + this);
      throw th;
    }
  }

  @Override // com.samsung.android.sume.core.message.MessageConsumer
  public int[] getConsumeMessage() {
    return new int[] {1, 2, 5};
  }

  @Override // com.samsung.android.sume.core.message.MessageConsumer
  public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
    boolean consumed = true;
    String str = TAG;
    Log.m94d(str, "onMessageReceived: " + message.getCode());
    Map<String, Object> replyData = new HashMap<>();
    switch (message.getCode()) {
      case 1:
        synchronized (message) {
          if (message.contains(Message.KEY_CONTENTS_ID)) {
            this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID)).intValue();
          }
          CodecDescriptor descriptor = (CodecDescriptor) getDescriptor();
          MediaType mediaType = (MediaType) message.get(Message.KEY_MEDIA_TYPE);
          if ((mediaType.isAudio() && descriptor.getMediaType().isAudio())
              || (mediaType.isVideo() && descriptor.getMediaType().isVideo())) {
            configCodec(message);
            break;
          }
          Log.m94d(
              str,
              "config-data of "
                  + mediaType
                  + " is not match this codec type "
                  + descriptor.getMediaType());
          return false;
        }
      case 2:
        int numFrames = ((Integer) message.get(Message.KEY_WHOLE_FRAMES)).intValue();
        this.numWholeFrames.set(numFrames);
        if (message.contains(Message.KEY_START_TIME_US)) {
          this.startTimeUs.set(((Long) message.get(Message.KEY_START_TIME_US)).longValue());
        }
        if (message.contains(Message.KEY_END_TIME_US)) {
          this.endTimeUs.set(((Long) message.get(Message.KEY_END_TIME_US)).longValue());
          break;
        }
        break;
      case 3:
      case 4:
      default:
        consumed = false;
        break;
      case 5:
        long timestampUs = ((Long) message.get("last-timestampUs")).longValue();
        Log.m94d(str, "last timestampUs set as " + timestampUs);
        this.lastTimestampUs.set(timestampUs);
        break;
    }
    if (message.isRequestToReply()) {
      message.reply(replyData);
    }
    return consumed;
  }

  @Override // com.samsung.android.sume.core.filter.MediaFilter
  public void setMessageProducer(MessageProducer messageProducer) {
    this.messageProducer = messageProducer;
  }

  @Override // com.samsung.android.sume.core.filter.MediaFilter
  public void release() {
    Log.m94d(TAG, "release...E");
    MediaCodec mediaCodec = this.mediaCodec;
    if (mediaCodec != null) {
      try {
        try {
          mediaCodec.stop();
        } catch (IllegalStateException e) {
          Log.m102w(TAG, "codec stop called but not started yet");
        }
      } finally {
        this.mediaCodec.release();
        this.mediaCodec = null;
      }
    }
    signalCodecFromReady();
    Log.m94d(TAG, "release...X");
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

  @Override // com.samsung.android.sume.core.filter.MediaFilter
  public Stream<MediaFilter> stream() {
    return null;
  }

  public MediaCodec getMediaCodec() {
    return this.mediaCodec;
  }

  @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
  public void setReceiveChannelQuery(
      Function<Enum<?>, BufferChannel> receiveChannelQuery, int numChannels) {
    this.receiveChannelQuery = receiveChannelQuery;
    this.receiveChannelCount = numChannels;
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
  public void setSendChannelQuery(
      Function<Enum<?>, BufferChannel> sendChannelQuery, int numChannels) {
    this.sendChannelQuery = sendChannelQuery;
    this.sendChannelCount = numChannels;
  }

  @Override // com.samsung.android.sume.core.filter.MediaOutputStreamFilter
  public Function<Enum<?>, BufferChannel> getSendChannelQuery() {
    return this.sendChannelQuery;
  }

  @Override // com.samsung.android.sume.core.filter.MediaOutputStreamFilter
  public int getSendChannelCount() {
    return this.sendChannelCount;
  }

  protected String tagged(String fmt, Object... args) {
    return String.format(
        NavigationBarInflaterView.SIZE_MOD_START
            + this.codecTag
            + NavigationBarInflaterView.SIZE_MOD_END
            + fmt,
        args);
  }
}
