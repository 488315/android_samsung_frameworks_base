package com.samsung.android.sume.core.filter;

import android.app.job.JobInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.SurfaceChannel;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.exception.StreamFilterExitException;
import com.samsung.android.sume.core.functional.BufferSupplier;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.types.MediaType;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CancellationException;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class DecoderFilter extends MediaCodecFilter implements BufferSupplier {
    private static final String TAG = Def.tagOf((Class<?>) DecoderFilter.class);

    public DecoderFilter(CodecDescriptor codecDescriptor) {
        super(codecDescriptor);
    }

    @Override // com.samsung.android.sume.core.filter.MediaCodecFilter
    protected void configCodec(Message message) {
        Surface surface;
        String str = TAG;
        Log.d(str, "configCodec: " + message);
        CodecDescriptor codecDescriptor = (CodecDescriptor) getDescriptor();
        String str2 = (String) message.get("mime");
        MediaType mediaType = codecDescriptor.getMediaType();
        try {
            MediaFormat mediaFormat = (MediaFormat) message.get("media-format");
            BufferChannel apply = this.sendChannelQuery.apply(mediaType);
            Log.d(str, "outputChannel: " + apply);
            if (apply instanceof SurfaceChannel) {
                ((SurfaceChannel) apply).configure(mediaFormat.getInteger("width"), mediaFormat.getInteger("height"), 34);
                surface = ((SurfaceChannel) apply).getSurface();
            } else {
                surface = null;
            }
            mediaFormat.setInteger("vendor.qti-ext-dec-forceNonUBWC.value", 1);
            mediaFormat.setLong("vendor.sec-dec-output.buffers.usage.value", 1L);
            this.mediaCodec = MediaCodec.createDecoderByType(str2);
            this.mediaCodec.configure(mediaFormat, surface, (MediaCrypto) null, 0);
            this.mediaCodec.start();
            signalCodecFromReady();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        Log.d(TAG, "run");
        awaitCodecToReady();
        if (this.mediaCodec == null) {
            mediaBuffer.release();
            throw new StreamFilterExitException("no media-codec given, might be released");
        }
        CodecDescriptor codecDescriptor = (CodecDescriptor) getDescriptor();
        MediaType mediaType = codecDescriptor.getMediaType();
        BufferChannel apply = this.receiveChannelQuery.apply(mediaType);
        BufferChannel apply2 = this.sendChannelQuery.apply(mediaType);
        this.reachedInputEos = false;
        this.reachedOutputEos = false;
        this.codecTag = "[dec: " + this.mediaCodec.getCodecInfo().getCanonicalName() + NavigationBarInflaterView.SIZE_MOD_END;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        while (true) {
            if (!this.reachedInputEos || !this.reachedOutputEos) {
                this.cvPause.block();
                if (!this.reachedInputEos) {
                    MediaBuffer receive = apply.receive();
                    int intValue = ((Integer) receive.getExtra("chunk-size")).intValue();
                    int intValue2 = ((Integer) receive.getExtra("buffer-idx")).intValue();
                    if (intValue < 0) {
                        this.mediaCodec.queueInputBuffer(intValue2, 0, 0, 0L, 4);
                        this.reachedInputEos = true;
                    } else {
                        this.mediaCodec.queueInputBuffer(intValue2, 0, intValue, ((Long) receive.getExtra("timestampUs")).longValue(), 0);
                    }
                    receive.release();
                }
                int dequeueOutputBuffer = this.mediaCodec.dequeueOutputBuffer(bufferInfo, JobInfo.MIN_BACKOFF_MILLIS);
                if (dequeueOutputBuffer == -1) {
                    Log.d(TAG, tagged("retry dequeue output buffer", new Object[0]));
                } else if (dequeueOutputBuffer == -2) {
                    Log.d(TAG, tagged("output format changed: " + this.mediaCodec.getOutputFormat(), new Object[0]));
                } else if (dequeueOutputBuffer < 0) {
                    continue;
                } else {
                    if ((bufferInfo.flags & 4) != 0) {
                        Log.d(TAG, tagged("reached EOS", new Object[0]));
                        this.reachedOutputEos = true;
                        if (apply2 instanceof SurfaceChannel) {
                            this.messageProducer.newMessage(5, "last-timestampUs", Long.valueOf(this.lastTimestampUs.get())).post();
                        } else {
                            MutableMediaBuffer mutableOf = MediaBuffer.mutableOf(com.samsung.android.sume.core.format.MediaFormat.mutableImageOf(new Object[0]));
                            mutableOf.setExtra("reached-eos", true);
                            apply2.send(mutableOf);
                        }
                    }
                    if (bufferInfo.size == 0) {
                        continue;
                    } else if (this.startTimeUs.get() > 0 && bufferInfo.presentationTimeUs < this.startTimeUs.get()) {
                        Log.d(TAG, "drop sample of " + bufferInfo.presentationTimeUs + " before " + this.startTimeUs.get());
                        this.mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                    } else {
                        if (apply2 instanceof SurfaceChannel) {
                            try {
                                Thread.sleep(40L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (apply2.isClosedForSend()) {
                                throw new CancellationException("output channel is already closed");
                            }
                            this.mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, bufferInfo.presentationTimeUs * 1000);
                        } else {
                            ByteBuffer outputBuffer = this.mediaCodec.getOutputBuffer(dequeueOutputBuffer);
                            outputBuffer.rewind();
                            MediaBuffer of = MediaBuffer.of(com.samsung.android.sume.core.format.MediaFormat.mutableAudioOf(Integer.valueOf(outputBuffer.limit())));
                            ((ByteBuffer) of.getTypedData(ByteBuffer.class)).put(outputBuffer);
                            of.setExtra("timestampUs", Long.valueOf(bufferInfo.presentationTimeUs));
                            apply2.send(of);
                            this.mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                        }
                        this.lastTimestampUs.set(bufferInfo.presentationTimeUs);
                        String str = TAG;
                        StringBuilder sb = new StringBuilder("# of decoded frames: ");
                        int i = this.processedFrames + 1;
                        this.processedFrames = i;
                        sb.append(i);
                        Log.d(str, tagged(sb.toString(), new Object[0]));
                    }
                }
            } else {
                if (codecDescriptor.isRunInstant()) {
                    release();
                }
                return mutableMediaBuffer;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaBuffer supplyMediaBuffer() {
        Log.d(TAG, "supplyMediaBuffer");
        if (this.mediaCodec == null) {
            awaitCodecToReady();
        }
        while (true) {
            int dequeueInputBuffer = this.mediaCodec.dequeueInputBuffer(JobInfo.MIN_BACKOFF_MILLIS);
            String str = TAG;
            Log.d(str, tagged("dequeue input buffer: " + dequeueInputBuffer, new Object[0]));
            if (dequeueInputBuffer < 0) {
                try {
                    Log.d(str, tagged("fail to dequeue input buffer, wait 50ms", new Object[0]));
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG, tagged("retry to dequeue input buffer: " + dequeueInputBuffer, new Object[0]));
                }
            } else {
                Log.d(str, tagged("success to dequeue input buffer: " + dequeueInputBuffer, new Object[0]));
                MediaBuffer of = MediaBuffer.of(((CodecDescriptor) getDescriptor()).getMediaType(), this.mediaCodec.getInputBuffer(dequeueInputBuffer));
                of.setExtra("buffer-idx", Integer.valueOf(dequeueInputBuffer));
                return of;
            }
        }
    }

    @Override // com.samsung.android.sume.core.functional.BufferSupplier
    public Supplier<MediaBuffer> getBufferSupplier() {
        return new Supplier() { // from class: com.samsung.android.sume.core.filter.DecoderFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                MediaBuffer supplyMediaBuffer;
                supplyMediaBuffer = DecoderFilter.this.supplyMediaBuffer();
                return supplyMediaBuffer;
            }
        };
    }
}
