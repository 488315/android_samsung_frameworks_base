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

/* loaded from: classes4.dex */
public class DecoderFilter extends MediaCodecFilter implements BufferSupplier {
    private static final String TAG = Def.tagOf((Class<?>) DecoderFilter.class);

    public DecoderFilter(CodecDescriptor codecDescriptor) {
        super(codecDescriptor);
    }

    @Override // com.samsung.android.sume.core.filter.MediaCodecFilter
    protected void configCodec(Message configData) {
        String str = TAG;
        Log.m94d(str, "configCodec: " + configData);
        CodecDescriptor descriptor = (CodecDescriptor) getDescriptor();
        String mimeType = (String) configData.get(MediaFormat.KEY_MIME);
        MediaType mediaType = descriptor.getMediaType();
        try {
            MediaFormat mediaFormat = (MediaFormat) configData.get("media-format");
            BufferChannel outputChannel = this.sendChannelQuery.apply(mediaType);
            Log.m94d(str, "outputChannel: " + outputChannel);
            Surface surface = null;
            if (outputChannel instanceof SurfaceChannel) {
                int width = mediaFormat.getInteger("width");
                int height = mediaFormat.getInteger("height");
                ((SurfaceChannel) outputChannel).configure(width, height, 34);
                surface = ((SurfaceChannel) outputChannel).getSurface();
            }
            mediaFormat.setInteger("vendor.qti-ext-dec-forceNonUBWC.value", 1);
            mediaFormat.setLong("vendor.sec-dec-output.buffers.usage.value", 1L);
            this.mediaCodec = MediaCodec.createDecoderByType(mimeType);
            this.mediaCodec.configure(mediaFormat, surface, (MediaCrypto) null, 0);
            this.mediaCodec.start();
            signalCodecFromReady();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        Log.m94d(TAG, "run");
        awaitCodecToReady();
        if (this.mediaCodec == null) {
            ibuf.release();
            throw new StreamFilterExitException("no media-codec given, might be released");
        }
        CodecDescriptor descriptor = (CodecDescriptor) getDescriptor();
        MediaType mediaType = descriptor.getMediaType();
        BufferChannel inputChannel = this.receiveChannelQuery.apply(mediaType);
        BufferChannel outputChannel = this.sendChannelQuery.apply(mediaType);
        this.reachedInputEos = false;
        this.reachedOutputEos = false;
        this.codecTag = "[dec: " + this.mediaCodec.getCodecInfo().getCanonicalName() + NavigationBarInflaterView.SIZE_MOD_END;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        while (true) {
            if (!this.reachedInputEos || !this.reachedOutputEos) {
                this.cvPause.block();
                if (!this.reachedInputEos) {
                    MediaBuffer mediaBuffer = inputChannel.receive();
                    int chunkSize = ((Integer) mediaBuffer.getExtra("chunk-size")).intValue();
                    int bufferIdx = ((Integer) mediaBuffer.getExtra("buffer-idx")).intValue();
                    if (chunkSize < 0) {
                        this.mediaCodec.queueInputBuffer(bufferIdx, 0, 0, 0L, 4);
                        this.reachedInputEos = true;
                    } else {
                        long timeUs = ((Long) mediaBuffer.getExtra("timestampUs")).longValue();
                        this.mediaCodec.queueInputBuffer(bufferIdx, 0, chunkSize, timeUs, 0);
                    }
                    mediaBuffer.release();
                }
                int status = this.mediaCodec.dequeueOutputBuffer(bufferInfo, JobInfo.MIN_BACKOFF_MILLIS);
                if (status == -1) {
                    Log.m94d(TAG, tagged("retry dequeue output buffer", new Object[0]));
                } else if (status == -2) {
                    Log.m94d(TAG, tagged("output format changed: " + this.mediaCodec.getOutputFormat(), new Object[0]));
                } else if (status < 0) {
                    continue;
                } else {
                    if ((bufferInfo.flags & 4) != 0) {
                        Log.m94d(TAG, tagged("reached EOS", new Object[0]));
                        this.reachedOutputEos = true;
                        if (outputChannel instanceof SurfaceChannel) {
                            this.messageProducer.newMessage(5, "last-timestampUs", Long.valueOf(this.lastTimestampUs.get())).post();
                        } else {
                            MutableMediaBuffer mutableOf = MediaBuffer.mutableOf(com.samsung.android.sume.core.format.MediaFormat.mutableImageOf(new Object[0]));
                            mutableOf.setExtra("reached-eos", true);
                            outputChannel.send(mutableOf);
                        }
                    }
                    if (bufferInfo.size == 0) {
                        continue;
                    } else if (this.startTimeUs.get() > 0 && bufferInfo.presentationTimeUs < this.startTimeUs.get()) {
                        Log.m94d(TAG, "drop sample of " + bufferInfo.presentationTimeUs + " before " + this.startTimeUs.get());
                        this.mediaCodec.releaseOutputBuffer(status, false);
                    } else {
                        if (outputChannel instanceof SurfaceChannel) {
                            try {
                                Thread.sleep(40L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (outputChannel.isClosedForSend()) {
                                throw new CancellationException("output channel is already closed");
                            }
                            this.mediaCodec.releaseOutputBuffer(status, bufferInfo.presentationTimeUs * 1000);
                        } else {
                            ByteBuffer decodedBuffer = this.mediaCodec.getOutputBuffer(status);
                            decodedBuffer.rewind();
                            MediaBuffer mediaBuffer2 = MediaBuffer.m326of(com.samsung.android.sume.core.format.MediaFormat.mutableAudioOf(Integer.valueOf(decodedBuffer.limit())));
                            ((ByteBuffer) mediaBuffer2.getTypedData(ByteBuffer.class)).put(decodedBuffer);
                            mediaBuffer2.setExtra("timestampUs", Long.valueOf(bufferInfo.presentationTimeUs));
                            outputChannel.send(mediaBuffer2);
                            this.mediaCodec.releaseOutputBuffer(status, false);
                        }
                        this.lastTimestampUs.set(bufferInfo.presentationTimeUs);
                        String str = TAG;
                        StringBuilder append = new StringBuilder().append("# of decoded frames: ");
                        int i = this.processedFrames + 1;
                        this.processedFrames = i;
                        Log.m94d(str, tagged(append.append(i).toString(), new Object[0]));
                    }
                }
            } else {
                if (descriptor.isRunInstant()) {
                    release();
                }
                return obuf;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaBuffer supplyMediaBuffer() {
        Log.m94d(TAG, "supplyMediaBuffer");
        if (this.mediaCodec == null) {
            awaitCodecToReady();
        }
        while (true) {
            int bufferIdx = this.mediaCodec.dequeueInputBuffer(JobInfo.MIN_BACKOFF_MILLIS);
            String str = TAG;
            Log.m94d(str, tagged("dequeue input buffer: " + bufferIdx, new Object[0]));
            if (bufferIdx < 0) {
                try {
                    Log.m94d(str, tagged("fail to dequeue input buffer, wait 50ms", new Object[0]));
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.m94d(TAG, tagged("retry to dequeue input buffer: " + bufferIdx, new Object[0]));
                }
            } else {
                Log.m94d(str, tagged("success to dequeue input buffer: " + bufferIdx, new Object[0]));
                ByteBuffer byteBuffer = this.mediaCodec.getInputBuffer(bufferIdx);
                MediaType mediaType = ((CodecDescriptor) getDescriptor()).getMediaType();
                MediaBuffer mediaBuffer = MediaBuffer.m331of(mediaType, byteBuffer);
                mediaBuffer.setExtra("buffer-idx", Integer.valueOf(bufferIdx));
                return mediaBuffer;
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
