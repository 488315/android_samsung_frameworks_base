package com.samsung.android.sume.core.filter;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.ConditionVariable;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.BufferSupplyChannel;
import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda13;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.MediaParserDescriptor;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.message.MessageProducer;
import com.samsung.android.sume.core.types.MediaType;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class MediaParserFilter implements MediaFilter, MediaOutputStreamFilter {
    private static final String TAG = Def.tagOf((Class<?>) MediaParserFilter.class);
    private int bitrate;
    private final ConditionVariable cvPause = new ConditionVariable();
    private final MediaParserDescriptor descriptor;
    private MessageProducer messageProducer;
    private int sendChannelCount;
    private Function<Enum<?>, BufferChannel> sendChannelQuery;

    public MediaParserFilter(MediaParserDescriptor descriptor) {
        this.descriptor = descriptor;
        this.cvPause.open();
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer ibuf, final MutableMediaBuffer obuf) {
        final MediaExtractor extractor;
        final int contentId;
        final FileDescriptor inputFd;
        final long startTimeUs;
        final long endTimeUs;
        final int frameCount;
        Log.d(TAG, "run: " + ibuf);
        MediaExtractor extractor2 = new MediaExtractor();
        try {
            try {
                contentId = ((Integer) ibuf.getExtra(Message.KEY_CONTENTS_ID)).intValue();
                inputFd =
                        (FileDescriptor)
                                Optional.ofNullable(ibuf.getExtra(Message.KEY_FILE_DESCRIPTOR))
                                        .map(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return MediaParserFilter.lambda$run$0(obj);
                                                    }
                                                })
                                        .orElseThrow(
                                                new SurfaceChannelImpl$$ExternalSyntheticLambda13());
                final FileDescriptor outputFd =
                        (FileDescriptor)
                                Optional.ofNullable(obuf)
                                        .map(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        Object extra;
                                                        extra =
                                                                ((MutableMediaBuffer) obj)
                                                                        .getExtra(
                                                                                Message
                                                                                        .KEY_FILE_DESCRIPTOR);
                                                        return extra;
                                                    }
                                                })
                                        .map(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return MediaParserFilter.lambda$run$2(obj);
                                                    }
                                                })
                                        .orElseThrow(
                                                new SurfaceChannelImpl$$ExternalSyntheticLambda13());
                Def.require(inputFd.valid());
                Def.require(outputFd.valid());
                startTimeUs = ((Long) ibuf.getExtra(Message.KEY_START_TIME_US, -1L)).longValue();
                endTimeUs =
                        ((Long) ibuf.getExtra(Message.KEY_END_TIME_US, Long.MAX_VALUE)).longValue();
                this.messageProducer
                        .newMessage(
                                4,
                                (Map<String, Object>)
                                        new HashMap<
                                                String,
                                                Object>() { // from class:
                                                            // com.samsung.android.sume.core.filter.MediaParserFilter.1
                                            {
                                                put(
                                                        Message.KEY_CONTENTS_ID,
                                                        Integer.valueOf(contentId));
                                                put(
                                                        "track-count",
                                                        Integer.valueOf(
                                                                MediaParserFilter.this.descriptor
                                                                        .countToParse()));
                                                put(Message.KEY_OUT_FILE, outputFd);
                                                if (!obuf.containsExtra(Message.KEY_CACHE_ID)
                                                        && ibuf.containsExtra(
                                                                Message.KEY_CACHE_ID)) {
                                                    put(
                                                            Message.KEY_CACHE_ID,
                                                            ibuf.getExtra(Message.KEY_CACHE_ID));
                                                }
                                            }
                                        })
                        .post();
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(inputFd);
                frameCount = Integer.parseInt(retriever.extractMetadata(32));
                this.bitrate = Integer.parseInt(retriever.extractMetadata(20));
                retriever.release();
                extractor = extractor2;
            } catch (IOException e) {
                e = e;
                extractor = extractor2;
            } catch (Throwable th) {
                th = th;
                extractor = extractor2;
            }
            try {
                this.messageProducer
                        .newMessage(
                                2,
                                (Map<String, Object>)
                                        new HashMap<
                                                String,
                                                Object>() { // from class:
                                                            // com.samsung.android.sume.core.filter.MediaParserFilter.2
                                            {
                                                put(
                                                        Message.KEY_CONTENTS_ID,
                                                        Integer.valueOf(contentId));
                                                put(
                                                        Message.KEY_WHOLE_FRAMES,
                                                        Integer.valueOf(frameCount));
                                                put(
                                                        Message.KEY_START_TIME_US,
                                                        Long.valueOf(startTimeUs));
                                                put(
                                                        Message.KEY_END_TIME_US,
                                                        Long.valueOf(endTimeUs));
                                            }
                                        })
                        .post();
            } catch (IOException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                extractor.release();
                throw th;
            }
            try {
                this.messageProducer
                        .newMessage(
                                7,
                                (Map<String, Object>)
                                        new HashMap<
                                                String,
                                                Object>() { // from class:
                                                            // com.samsung.android.sume.core.filter.MediaParserFilter.3
                                            {
                                                put(
                                                        Message.KEY_CONTENTS_ID,
                                                        Integer.valueOf(contentId));
                                                put(
                                                        Message.KEY_WHOLE_FRAMES,
                                                        Integer.valueOf(frameCount));
                                                if (ibuf.containsExtra(Message.KEY_IN_FILE)) {
                                                    put(
                                                            Message.KEY_IN_FILE,
                                                            ibuf.getExtra(Message.KEY_IN_FILE));
                                                }
                                                if (obuf.containsExtra(Message.KEY_OUT_FILE)) {
                                                    put(
                                                            Message.KEY_OUT_FILE,
                                                            obuf.getExtra(Message.KEY_OUT_FILE));
                                                }
                                            }
                                        })
                        .post();
                extractor.setDataSource(inputFd);
                final List<Pair<Integer, MediaType>> tracks = new ArrayList<>();
                IntStream.range(0, extractor.getTrackCount())
                        .forEach(
                                new IntConsumer() { // from class:
                                                    // com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda3
                                    @Override // java.util.function.IntConsumer
                                    public final void accept(int i) {
                                        MediaParserFilter.this.m9145xbef1d985(
                                                extractor, inputFd, contentId, tracks, i);
                                    }
                                });
                IntStream.range(0, extractor.getTrackCount())
                        .forEach(
                                new IntConsumer() { // from class:
                                                    // com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda4
                                    @Override // java.util.function.IntConsumer
                                    public final void accept(int i) {
                                        MediaParserFilter.this.m9146xc0282c64(
                                                extractor, endTimeUs, i);
                                    }
                                });
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                extractor.release();
                return obuf;
            }
            extractor.release();
            return obuf;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    static /* synthetic */ Object lambda$run$0(Object it) {
        if (it instanceof ParcelFileDescriptor) {
            return ((ParcelFileDescriptor) it).getFileDescriptor();
        }
        if (it instanceof FileDescriptor) {
            return it;
        }
        return null;
    }

    static /* synthetic */ Object lambda$run$2(Object it) {
        if (it instanceof ParcelFileDescriptor) {
            return ((ParcelFileDescriptor) it).getFileDescriptor();
        }
        if (it instanceof FileDescriptor) {
            return it;
        }
        return null;
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-filter-MediaParserFilter, reason: not valid java name */
    /* synthetic */ void m9145xbef1d985(
            MediaExtractor extractor, FileDescriptor inputFd, int contentId, List tracks, int idx) {
        String mimeType = extractor.getTrackFormat(idx).getString("mime");
        MediaType mediaType =
                mimeType.startsWith("video")
                        ? MediaType.COMPRESSED_VIDEO
                        : MediaType.COMPRESSED_AUDIO;
        if (!this.descriptor.needToParse(mediaType)) {
            Log.d(TAG, "descriptor has type: " + mediaType);
            return;
        }
        Map<String, Object> configData = new HashMap<>();
        configData.put("mime", mimeType);
        MediaFormat mediaFormat = extractor.getTrackFormat(idx);
        Log.d(TAG, "media-format = " + mediaFormat);
        configData.put("media-format", mediaFormat);
        configData.put(Message.KEY_MEDIA_TYPE, mediaType);
        configData.put(
                mediaType == MediaType.COMPRESSED_VIDEO ? "video-format" : "audio-format",
                mediaFormat);
        if (mediaFormat.containsKey("width")) {
            configData.put("width", Integer.valueOf(mediaFormat.getInteger("width")));
        }
        if (mediaFormat.containsKey("height")) {
            configData.put("height", Integer.valueOf(mediaFormat.getInteger("height")));
        }
        if (mediaFormat.containsKey("rotation-degrees")) {
            configData.put(
                    "rotation-degrees",
                    Integer.valueOf(mediaFormat.getInteger("rotation-degrees")));
        }
        if (mediaFormat.containsKey(MediaFormat.KEY_BIT_RATE)) {
            configData.put(
                    MediaFormat.KEY_BIT_RATE,
                    Integer.valueOf(mediaFormat.getInteger(MediaFormat.KEY_BIT_RATE)));
        } else if (this.bitrate != 0) {
            configData.put(MediaFormat.KEY_BIT_RATE, Integer.valueOf(this.bitrate));
        } else if (mediaFormat.containsKey(MediaFormat.KEY_DURATION)) {
            this.bitrate =
                    (int)
                            (((Def.getFileSize(inputFd) << 3) * 1000000)
                                    / mediaFormat.getLong(MediaFormat.KEY_DURATION));
            configData.put(MediaFormat.KEY_BIT_RATE, Integer.valueOf(this.bitrate));
        }
        if (mediaFormat.containsKey(MediaFormat.KEY_FRAME_RATE)) {
            configData.put(
                    MediaFormat.KEY_FRAME_RATE,
                    Integer.valueOf(mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE)));
        }
        configData.put(
                MediaFormat.KEY_I_FRAME_INTERVAL,
                Integer.valueOf(mediaFormat.getInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)));
        if (mediaFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE)) {
            configData.put(
                    MediaFormat.KEY_SAMPLE_RATE,
                    Integer.valueOf(mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)));
        }
        if (mediaFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT)) {
            configData.put(
                    MediaFormat.KEY_CHANNEL_COUNT,
                    Integer.valueOf(mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)));
        }
        Log.d(TAG, "send TRACK_FORMAT message to decoder");
        configData.put(Message.KEY_CONTENTS_ID, Integer.valueOf(contentId));
        this.messageProducer.newMessage(1, configData).post();
        tracks.add(new Pair(Integer.valueOf(idx), mediaType));
    }

    /* renamed from: lambda$run$4$com-samsung-android-sume-core-filter-MediaParserFilter, reason: not valid java name */
    /* synthetic */ void m9146xc0282c64(MediaExtractor extractor, long endTimeUs, int idx) {
        MediaBuffer mediaBuffer;
        String mimeType = extractor.getTrackFormat(idx).getString("mime");
        MediaType mediaType =
                mimeType.startsWith("video")
                        ? MediaType.COMPRESSED_VIDEO
                        : MediaType.COMPRESSED_AUDIO;
        if (!this.descriptor.needToParse(mediaType)) {
            Log.d(TAG, "descriptor has type: " + mediaType);
            return;
        }
        try {
            BufferChannel bufferChannel = this.sendChannelQuery.apply(mediaType);
            if (bufferChannel == null) {
                Log.w(
                        TAG,
                        "no buffer-channel given for " + mimeType + ", skip decoding this track");
                return;
            }
            extractor.selectTrack(idx);
            String tag = "[track: " + mimeType + NavigationBarInflaterView.SIZE_MOD_END;
            boolean reachedEos = false;
            while (!reachedEos) {
                this.cvPause.block();
                if (bufferChannel instanceof BufferSupplyChannel) {
                    mediaBuffer = ((BufferSupplyChannel) bufferChannel).get();
                } else {
                    mediaBuffer = MediaBuffer.groupOf(new MediaBuffer[0]);
                }
                ByteBuffer byteBuffer = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                int chunkSize = extractor.readSampleData(byteBuffer, 0);
                if (chunkSize >= 0 && endTimeUs >= extractor.getSampleTime()) {
                    mediaBuffer.setExtra("chunk-size", Integer.valueOf(chunkSize));
                    mediaBuffer.setExtra("timestampUs", Long.valueOf(extractor.getSampleTime()));
                    extractor.advance();
                    bufferChannel.send(mediaBuffer);
                }
                Log.d(TAG, tag + "parser reached EOS");
                reachedEos = true;
                mediaBuffer.setExtra("chunk-size", -1);
                bufferChannel.send(mediaBuffer);
            }
            extractor.unselectTrack(idx);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
        return this.descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return Stream.of(this);
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
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
}
