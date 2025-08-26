package com.samsung.android.sume.core.filter;

import android.app.job.JobInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.SurfaceChannel;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.exception.StreamFilterExitException;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.types.MediaType;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class EncoderFilter extends MediaCodecFilter {
    private static final String TAG = Def.tagOf((Class<?>) EncoderFilter.class);
    private int orientation;

    public EncoderFilter(CodecDescriptor codecDescriptor) {
        super(codecDescriptor);
        this.orientation = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0079 A[PHI: r10
      0x0079: PHI (r10v2 int) = (r10v1 int), (r10v6 int) binds: [B:6:0x0044, B:8:0x0056] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.samsung.android.sume.core.filter.MediaCodecFilter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void configCodec(final Message message) {
        float f;
        String str;
        MediaFormat mediaFormatCreateAudioFormat;
        String str2 = TAG;
        Log.d(str2, "configCodec: " + message);
        CodecDescriptor codecDescriptor = (CodecDescriptor) getDescriptor();
        String mimeType = (String) message.get("mime");
        if (codecDescriptor.getMimeType() != null) {
            mimeType = codecDescriptor.getMimeType();
        }
        int bitrate = codecDescriptor.getBitrate();
        if (bitrate == 0) {
            bitrate = ((Integer) message.get(MediaFormat.KEY_BIT_RATE)).intValue();
            if (codecDescriptor.getScale() != 0.0f) {
                double d = bitrate;
                float scale = codecDescriptor.getScale();
                f = 0.0f;
                str = MediaFormat.KEY_BIT_RATE;
                bitrate = (int) (d * Math.pow(10.0d, (int) Math.log10(Math.pow(scale, 2.0d))));
            } else {
                f = 0.0f;
                str = MediaFormat.KEY_BIT_RATE;
            }
        }
        MediaType mediaType = codecDescriptor.getMediaType();
        try {
            if (mediaType.isVideo()) {
                Pair pair = (Pair) Optional.ofNullable(codecDescriptor.getRectSize()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.filter.EncoderFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return EncoderFilter.lambda$configCodec$0(message);
                    }
                });
                int iIntValue = ((Integer) pair.first).intValue();
                int iIntValue2 = ((Integer) pair.second).intValue();
                if (codecDescriptor.getScale() != f) {
                    iIntValue = (int) (iIntValue * codecDescriptor.getScale());
                    iIntValue2 = (int) (iIntValue2 * codecDescriptor.getScale());
                }
                mediaFormatCreateAudioFormat = MediaFormat.createVideoFormat(mimeType, iIntValue, iIntValue2);
                mediaFormatCreateAudioFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
                mediaFormatCreateAudioFormat.setInteger(MediaFormat.KEY_FRAME_RATE, ((Integer) message.get(MediaFormat.KEY_FRAME_RATE)).intValue());
                mediaFormatCreateAudioFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, ((Integer) message.get(MediaFormat.KEY_I_FRAME_INTERVAL)).intValue());
                mediaFormatCreateAudioFormat.setInteger("vendor.qti-ext-enc-linear-color-format.value", 1);
                if (message.contains("rotation-degrees")) {
                    int iIntValue3 = ((Integer) message.get("rotation-degrees")).intValue();
                    this.orientation = iIntValue3;
                    mediaFormatCreateAudioFormat.setInteger("rotation-degrees", iIntValue3);
                }
            } else {
                if (!mediaType.isAudio()) {
                    throw new UnsupportedOperationException("not supported type" + mediaType);
                }
                mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(mimeType, ((Integer) message.get(MediaFormat.KEY_SAMPLE_RATE)).intValue(), ((Integer) message.get(MediaFormat.KEY_CHANNEL_COUNT)).intValue());
            }
            mediaFormatCreateAudioFormat.setInteger(str, bitrate);
            Log.d(str2, "media-format=" + mediaFormatCreateAudioFormat);
            this.mediaCodec = MediaCodec.createEncoderByType(mimeType);
            this.mediaCodec.configure(mediaFormatCreateAudioFormat, (Surface) null, (MediaCrypto) null, 1);
            BufferChannel bufferChannelApply = this.receiveChannelQuery.apply(mediaType);
            if (bufferChannelApply instanceof SurfaceChannel) {
                ((SurfaceChannel) bufferChannelApply).configure(this.mediaCodec.createInputSurface());
            }
            this.mediaCodec.start();
            signalCodecFromReady();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static /* synthetic */ Pair lambda$configCodec$0(Message message) {
        return new Pair((Integer) message.get("width"), (Integer) message.get("height"));
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws MediaCodec.CryptoException, InterruptedException {
        CodecDescriptor codecDescriptor;
        MediaType mediaType;
        BufferChannel bufferChannel;
        boolean z;
        Log.d(TAG, "run");
        awaitCodecToReady();
        if (this.mediaCodec == null) {
            mediaBuffer.release();
            throw new StreamFilterExitException("no media-codec given, might be released");
        }
        CodecDescriptor codecDescriptor2 = (CodecDescriptor) getDescriptor();
        MediaType mediaType2 = codecDescriptor2.getMediaType();
        BufferChannel bufferChannelApply = this.receiveChannelQuery.apply(mediaType2);
        BufferChannel bufferChannelApply2 = this.sendChannelQuery.apply(mediaType2);
        AtomicInteger atomicInteger = new AtomicInteger();
        boolean z2 = bufferChannelApply instanceof SurfaceChannel;
        this.reachedInputEos = z2;
        this.reachedOutputEos = false;
        this.processedFrames = 0;
        String str = "[enc: " + this.mediaCodec.getCodecInfo().getCanonicalName() + NavigationBarInflaterView.SIZE_MOD_END;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        boolean z3 = false;
        while (true) {
            if (!this.reachedInputEos || !this.reachedOutputEos) {
                this.cvPause.block();
                if (!this.reachedInputEos && z3) {
                    MediaBuffer mediaBufferReceive = bufferChannelApply.receive();
                    String str2 = TAG;
                    Log.d(str2, "[bhko] buffer=" + mediaBufferReceive);
                    int iDequeueInputBuffer = this.mediaCodec.dequeueInputBuffer(JobInfo.MIN_BACKOFF_MILLIS);
                    Log.d(str2, str + "dequeue input buffer: " + iDequeueInputBuffer);
                    if (iDequeueInputBuffer >= 0) {
                        if (mediaBufferReceive.containsExtra("reached-eos")) {
                            this.mediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
                            this.reachedInputEos = true;
                        } else {
                            try {
                                Thread.sleep(30L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (bufferChannelApply.isClosedForReceive()) {
                                throw new CancellationException("input channel is already closed");
                            }
                            ByteBuffer inputBuffer = this.mediaCodec.getInputBuffer(iDequeueInputBuffer);
                            ByteBuffer byteBuffer = (ByteBuffer) mediaBufferReceive.getTypedData(ByteBuffer.class);
                            byteBuffer.rewind();
                            inputBuffer.put(byteBuffer);
                            this.mediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, inputBuffer.limit(), ((Long) mediaBufferReceive.getExtra("timestampUs", 0L)).longValue(), 0);
                        }
                    }
                    mediaBufferReceive.release();
                }
                String str3 = TAG;
                Log.d(str3, str + "dequeue output buffer");
                int iDequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, JobInfo.MIN_BACKOFF_MILLIS);
                Log.d(str3, str + "buffer st=" + iDequeueOutputBuffer + ", info=" + bufferInfo);
                if (iDequeueOutputBuffer == -1) {
                    Log.d(str3, str + "retry dequeue output buffer");
                } else if (iDequeueOutputBuffer == -2) {
                    Log.d(str3, str + "track format = " + this.mediaCodec.getOutputFormat());
                    HashMap map = new HashMap();
                    map.put(Message.KEY_MEDIA_TYPE, mediaType2);
                    MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
                    int i = this.orientation;
                    if (i != 0) {
                        outputFormat.setInteger("rotation-degrees", i);
                    }
                    map.put("media-format", outputFormat);
                    this.messageProducer.newMessage(3, (Map<String, Object>) map).post();
                    Log.d(str3, str + "now ready to start encode");
                } else {
                    if (iDequeueOutputBuffer >= 0) {
                        ByteBuffer outputBuffer = this.mediaCodec.getOutputBuffer(iDequeueOutputBuffer);
                        MediaBuffer mediaBufferOf = MediaBuffer.of(mediaType2, outputBuffer);
                        mediaBufferOf.setExtra("track-idx", Integer.valueOf(atomicInteger.get()));
                        mediaBufferOf.setExtra("buffer-info", bufferInfo);
                        Log.d(str3, "flag=" + Integer.toHexString(bufferInfo.flags));
                        if ((bufferInfo.flags & 2) != 0) {
                            bufferInfo.size = 0;
                            mediaBufferOf.release();
                            z3 = true;
                        }
                        Log.d(str3, "size=" + bufferInfo.size);
                        if (bufferInfo.size != 0) {
                            this.processedFrames++;
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("# of encoded frames: ");
                            sb.append(this.processedFrames);
                            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                            codecDescriptor = codecDescriptor2;
                            mediaType = mediaType2;
                            sb.append(bufferInfo.presentationTimeUs);
                            sb.append("](");
                            sb.append(Integer.toHexString(bufferInfo.flags));
                            sb.append(NavigationBarInflaterView.KEY_CODE_END);
                            Log.d(str3, sb.toString());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str);
                            sb2.append("total # :");
                            sb2.append(this.numWholeFrames.get());
                            sb2.append(", last ts: ");
                            bufferChannel = bufferChannelApply;
                            sb2.append(this.lastTimestampUs.get());
                            Log.d(str3, sb2.toString());
                            if (z2 && (isReachedLastFrame(this.processedFrames) || isReachedLastTimestamp(bufferInfo.presentationTimeUs))) {
                                bufferInfo.flags |= 4;
                                this.lastTimestampUs.set(Long.MAX_VALUE);
                            }
                            outputBuffer.position(bufferInfo.offset);
                            outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                            bufferChannelApply2.send(mediaBufferOf);
                        } else {
                            codecDescriptor = codecDescriptor2;
                            mediaType = mediaType2;
                            bufferChannel = bufferChannelApply;
                        }
                        if ((bufferInfo.flags & 4) != 0) {
                            Log.i(str3, str + "encoder reached eos");
                            this.reachedOutputEos = true;
                            if (!z2) {
                                bufferChannelApply2.send(mediaBufferOf);
                            }
                        }
                        z = false;
                        this.mediaCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                    }
                    bufferChannelApply = bufferChannel;
                    codecDescriptor2 = codecDescriptor;
                    mediaType2 = mediaType;
                }
                codecDescriptor = codecDescriptor2;
                mediaType = mediaType2;
                bufferChannel = bufferChannelApply;
                z = false;
                bufferChannelApply = bufferChannel;
                codecDescriptor2 = codecDescriptor;
                mediaType2 = mediaType;
            } else {
                if (codecDescriptor2.isRunInstant()) {
                    release();
                }
                return mutableMediaBuffer;
            }
        }
    }

    private boolean isReachedLastFrame(int i) {
        return this.numWholeFrames.get() == i;
    }

    private boolean isReachedLastTimestamp(long j) {
        return this.lastTimestampUs.get() <= j;
    }
}
