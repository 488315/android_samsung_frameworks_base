package com.samsung.android.sume.core.filter;

import android.app.job.JobInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.SurfaceChannel;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.exception.StreamFilterExitException;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.types.MediaType;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class EncoderFilter extends MediaCodecFilter {
    private static final String TAG = Def.tagOf((Class<?>) EncoderFilter.class);
    private int orientation;

    public EncoderFilter(CodecDescriptor codecDescriptor) {
        super(codecDescriptor);
        this.orientation = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0087 A[Catch: IOException -> 0x0177, TryCatch #0 {IOException -> 0x0177, blocks: (B:11:0x0080, B:13:0x0087, B:15:0x00b2, B:16:0x00c0, B:18:0x00f2, B:19:0x0125, B:21:0x0151, B:22:0x015c, B:25:0x0102, B:27:0x0108, B:28:0x0165, B:29:0x0176), top: B:10:0x0080 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0151 A[Catch: IOException -> 0x0177, TryCatch #0 {IOException -> 0x0177, blocks: (B:11:0x0080, B:13:0x0087, B:15:0x00b2, B:16:0x00c0, B:18:0x00f2, B:19:0x0125, B:21:0x0151, B:22:0x015c, B:25:0x0102, B:27:0x0108, B:28:0x0165, B:29:0x0176), top: B:10:0x0080 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0102 A[Catch: IOException -> 0x0177, TryCatch #0 {IOException -> 0x0177, blocks: (B:11:0x0080, B:13:0x0087, B:15:0x00b2, B:16:0x00c0, B:18:0x00f2, B:19:0x0125, B:21:0x0151, B:22:0x015c, B:25:0x0102, B:27:0x0108, B:28:0x0165, B:29:0x0176), top: B:10:0x0080 }] */
    @Override // com.samsung.android.sume.core.filter.MediaCodecFilter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void configCodec(final com.samsung.android.sume.core.message.Message r20) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.filter.EncoderFilter.configCodec(com.samsung.android.sume.core.message.Message):void");
    }

    static /* synthetic */ Pair lambda$configCodec$0(Message message) {
        return new Pair((Integer) message.get("width"), (Integer) message.get("height"));
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
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
        BufferChannel apply = this.receiveChannelQuery.apply(mediaType2);
        BufferChannel apply2 = this.sendChannelQuery.apply(mediaType2);
        AtomicInteger atomicInteger = new AtomicInteger();
        boolean z2 = apply instanceof SurfaceChannel;
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
                    MediaBuffer receive = apply.receive();
                    String str2 = TAG;
                    Log.d(str2, "[bhko] buffer=" + receive);
                    int dequeueInputBuffer = this.mediaCodec.dequeueInputBuffer(JobInfo.MIN_BACKOFF_MILLIS);
                    Log.d(str2, str + "dequeue input buffer: " + dequeueInputBuffer);
                    if (dequeueInputBuffer >= 0) {
                        if (receive.containsExtra("reached-eos")) {
                            this.mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                            this.reachedInputEos = true;
                        } else {
                            try {
                                Thread.sleep(30L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (apply.isClosedForReceive()) {
                                throw new CancellationException("input channel is already closed");
                            }
                            ByteBuffer inputBuffer = this.mediaCodec.getInputBuffer(dequeueInputBuffer);
                            ByteBuffer byteBuffer = (ByteBuffer) receive.getTypedData(ByteBuffer.class);
                            byteBuffer.rewind();
                            inputBuffer.put(byteBuffer);
                            this.mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, inputBuffer.limit(), ((Long) receive.getExtra("timestampUs", 0L)).longValue(), 0);
                        }
                    }
                    receive.release();
                }
                String str3 = TAG;
                Log.d(str3, str + "dequeue output buffer");
                int dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, JobInfo.MIN_BACKOFF_MILLIS);
                Log.d(str3, str + "buffer st=" + dequeueOutputBuffer + ", info=" + bufferInfo);
                if (dequeueOutputBuffer == -1) {
                    Log.d(str3, str + "retry dequeue output buffer");
                } else if (dequeueOutputBuffer == -2) {
                    Log.d(str3, str + "track format = " + this.mediaCodec.getOutputFormat());
                    HashMap hashMap = new HashMap();
                    hashMap.put(Message.KEY_MEDIA_TYPE, mediaType2);
                    MediaFormat outputFormat = this.mediaCodec.getOutputFormat();
                    int i = this.orientation;
                    if (i != 0) {
                        outputFormat.setInteger("rotation-degrees", i);
                    }
                    hashMap.put("media-format", outputFormat);
                    this.messageProducer.newMessage(3, (Map<String, Object>) hashMap).post();
                    Log.d(str3, str + "now ready to start encode");
                } else if (dequeueOutputBuffer >= 0) {
                    ByteBuffer outputBuffer = this.mediaCodec.getOutputBuffer(dequeueOutputBuffer);
                    MediaBuffer of = MediaBuffer.of(mediaType2, outputBuffer);
                    of.setExtra("track-idx", Integer.valueOf(atomicInteger.get()));
                    of.setExtra("buffer-info", bufferInfo);
                    Log.d(str3, "flag=" + Integer.toHexString(bufferInfo.flags));
                    if ((bufferInfo.flags & 2) != 0) {
                        bufferInfo.size = 0;
                        of.release();
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
                        bufferChannel = apply;
                        sb2.append(this.lastTimestampUs.get());
                        Log.d(str3, sb2.toString());
                        if (z2 && (isReachedLastFrame(this.processedFrames) || isReachedLastTimestamp(bufferInfo.presentationTimeUs))) {
                            bufferInfo.flags |= 4;
                            this.lastTimestampUs.set(Long.MAX_VALUE);
                        }
                        outputBuffer.position(bufferInfo.offset);
                        outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                        apply2.send(of);
                    } else {
                        codecDescriptor = codecDescriptor2;
                        mediaType = mediaType2;
                        bufferChannel = apply;
                    }
                    if ((bufferInfo.flags & 4) != 0) {
                        Log.i(str3, str + "encoder reached eos");
                        this.reachedOutputEos = true;
                        if (!z2) {
                            apply2.send(of);
                        }
                    }
                    z = false;
                    this.mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                    apply = bufferChannel;
                    codecDescriptor2 = codecDescriptor;
                    mediaType2 = mediaType;
                }
                codecDescriptor = codecDescriptor2;
                mediaType = mediaType2;
                bufferChannel = apply;
                z = false;
                apply = bufferChannel;
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
