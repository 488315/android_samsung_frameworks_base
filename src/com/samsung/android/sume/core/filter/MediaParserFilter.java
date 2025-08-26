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
    private final ConditionVariable cvPause;
    private final MediaParserDescriptor descriptor;
    private MessageProducer messageProducer;
    private int sendChannelCount;
    private Function<Enum<?>, BufferChannel> sendChannelQuery;

    public MediaParserFilter(MediaParserDescriptor mediaParserDescriptor) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.cvPause = conditionVariable;
        this.descriptor = mediaParserDescriptor;
        conditionVariable.open();
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        MutableMediaBuffer mutableMediaBuffer2;
        int i;
        MessageProducer messageProducer;
        Log.d(TAG, "run: " + mediaBuffer);
        final MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            try {
                final int iIntValue = ((Integer) mediaBuffer.getExtra(Message.KEY_CONTENTS_ID)).intValue();
                final FileDescriptor fileDescriptor = (FileDescriptor) Optional.ofNullable(mediaBuffer.getExtra(Message.KEY_FILE_DESCRIPTOR)).map(new Function() { // from class: com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaParserFilter.lambda$run$0(obj);
                    }
                }).orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda13());
                FileDescriptor fileDescriptor2 = (FileDescriptor) Optional.ofNullable(mutableMediaBuffer).map(new Function() { // from class: com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((MutableMediaBuffer) obj).getExtra(Message.KEY_FILE_DESCRIPTOR);
                    }
                }).map(new Function() { // from class: com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaParserFilter.lambda$run$2(obj);
                    }
                }).orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda13());
                Def.require(fileDescriptor.valid());
                Def.require(fileDescriptor2.valid());
                long jLongValue = ((Long) mediaBuffer.getExtra(Message.KEY_START_TIME_US, -1L)).longValue();
                final long jLongValue2 = ((Long) mediaBuffer.getExtra(Message.KEY_END_TIME_US, Long.MAX_VALUE)).longValue();
                MessageProducer messageProducer2 = this.messageProducer;
                try {
                    HashMap<String, Object> map = new HashMap<String, Object>(iIntValue, fileDescriptor2, mutableMediaBuffer, mediaBuffer) { // from class: com.samsung.android.sume.core.filter.MediaParserFilter.1
                        final /* synthetic */ int val$contentId;
                        final /* synthetic */ MediaBuffer val$ibuf;
                        final /* synthetic */ MutableMediaBuffer val$obuf;
                        final /* synthetic */ FileDescriptor val$outputFd;

                        {
                            this.val$contentId = iIntValue;
                            this.val$outputFd = fileDescriptor2;
                            this.val$obuf = mutableMediaBuffer;
                            this.val$ibuf = mediaBuffer;
                            put(Message.KEY_CONTENTS_ID, Integer.valueOf(iIntValue));
                            put("track-count", Integer.valueOf(MediaParserFilter.this.descriptor.countToParse()));
                            put(Message.KEY_OUT_FILE, fileDescriptor2);
                            if (mutableMediaBuffer.containsExtra(Message.KEY_CACHE_ID) || !mediaBuffer.containsExtra(Message.KEY_CACHE_ID)) {
                                return;
                            }
                            put(Message.KEY_CACHE_ID, mediaBuffer.getExtra(Message.KEY_CACHE_ID));
                        }
                    };
                    mutableMediaBuffer2 = mutableMediaBuffer;
                    try {
                        messageProducer2.newMessage(4, (Map<String, Object>) map).post();
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        mediaMetadataRetriever.setDataSource(fileDescriptor);
                        i = Integer.parseInt(mediaMetadataRetriever.extractMetadata(32));
                        this.bitrate = Integer.parseInt(mediaMetadataRetriever.extractMetadata(20));
                        mediaMetadataRetriever.release();
                        this.messageProducer.newMessage(2, (Map<String, Object>) new HashMap<String, Object>(iIntValue, i, jLongValue, jLongValue2) { // from class: com.samsung.android.sume.core.filter.MediaParserFilter.2
                            final /* synthetic */ int val$contentId;
                            final /* synthetic */ long val$endTimeUs;
                            final /* synthetic */ int val$frameCount;
                            final /* synthetic */ long val$startTimeUs;

                            {
                                this.val$contentId = iIntValue;
                                this.val$frameCount = i;
                                this.val$startTimeUs = jLongValue;
                                this.val$endTimeUs = jLongValue2;
                                put(Message.KEY_CONTENTS_ID, Integer.valueOf(iIntValue));
                                put(Message.KEY_WHOLE_FRAMES, Integer.valueOf(i));
                                put(Message.KEY_START_TIME_US, Long.valueOf(jLongValue));
                                put(Message.KEY_END_TIME_US, Long.valueOf(jLongValue2));
                            }
                        }).post();
                        messageProducer = this.messageProducer;
                    } catch (IOException e) {
                        e = e;
                    }
                } catch (IOException e2) {
                    e = e2;
                    mutableMediaBuffer2 = mutableMediaBuffer;
                }
                try {
                    HashMap<String, Object> map2 = new HashMap<String, Object>(iIntValue, i, mediaBuffer, mutableMediaBuffer2) { // from class: com.samsung.android.sume.core.filter.MediaParserFilter.3
                        final /* synthetic */ int val$contentId;
                        final /* synthetic */ int val$frameCount;
                        final /* synthetic */ MediaBuffer val$ibuf;
                        final /* synthetic */ MutableMediaBuffer val$obuf;

                        {
                            this.val$contentId = iIntValue;
                            this.val$frameCount = i;
                            this.val$ibuf = mediaBuffer;
                            this.val$obuf = mutableMediaBuffer2;
                            put(Message.KEY_CONTENTS_ID, Integer.valueOf(iIntValue));
                            put(Message.KEY_WHOLE_FRAMES, Integer.valueOf(i));
                            if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
                                put(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
                            }
                            if (mutableMediaBuffer2.containsExtra(Message.KEY_OUT_FILE)) {
                                put(Message.KEY_OUT_FILE, mutableMediaBuffer2.getExtra(Message.KEY_OUT_FILE));
                            }
                        }
                    };
                    mutableMediaBuffer2 = mutableMediaBuffer2;
                    messageProducer.newMessage(7, (Map<String, Object>) map2).post();
                    mediaExtractor.setDataSource(fileDescriptor);
                    final ArrayList arrayList = new ArrayList();
                    IntStream.range(0, mediaExtractor.getTrackCount()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda3
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            this.f$0.m9559xbef1d985(mediaExtractor, fileDescriptor, iIntValue, arrayList, i2);
                        }
                    });
                    IntStream.range(0, mediaExtractor.getTrackCount()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.filter.MediaParserFilter$$ExternalSyntheticLambda4
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            this.f$0.m9560xc0282c64(mediaExtractor, jLongValue2, i2);
                        }
                    });
                    mediaExtractor.release();
                    return mutableMediaBuffer2;
                } catch (IOException e3) {
                    e = e3;
                    mutableMediaBuffer2 = mutableMediaBuffer2;
                    e.printStackTrace();
                    mediaExtractor.release();
                    return mutableMediaBuffer2;
                }
            } catch (Throwable th) {
                mediaExtractor.release();
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            mutableMediaBuffer2 = mutableMediaBuffer;
        }
    }

    static /* synthetic */ Object lambda$run$0(Object obj) {
        if (obj instanceof ParcelFileDescriptor) {
            return ((ParcelFileDescriptor) obj).getFileDescriptor();
        }
        if (obj instanceof FileDescriptor) {
            return obj;
        }
        return null;
    }

    static /* synthetic */ Object lambda$run$2(Object obj) {
        if (obj instanceof ParcelFileDescriptor) {
            return ((ParcelFileDescriptor) obj).getFileDescriptor();
        }
        if (obj instanceof FileDescriptor) {
            return obj;
        }
        return null;
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-filter-MediaParserFilter, reason: not valid java name */
    /* synthetic */ void m9559xbef1d985(MediaExtractor mediaExtractor, FileDescriptor fileDescriptor, int i, List list, int i2) {
        String string = mediaExtractor.getTrackFormat(i2).getString("mime");
        MediaType mediaType = string.startsWith("video") ? MediaType.COMPRESSED_VIDEO : MediaType.COMPRESSED_AUDIO;
        if (!this.descriptor.needToParse(mediaType)) {
            Log.d(TAG, "descriptor has type: " + mediaType);
            return;
        }
        HashMap map = new HashMap();
        map.put("mime", string);
        MediaFormat trackFormat = mediaExtractor.getTrackFormat(i2);
        String str = TAG;
        Log.d(str, "media-format = " + trackFormat);
        map.put("media-format", trackFormat);
        map.put(Message.KEY_MEDIA_TYPE, mediaType);
        map.put(mediaType == MediaType.COMPRESSED_VIDEO ? "video-format" : "audio-format", trackFormat);
        if (trackFormat.containsKey("width")) {
            map.put("width", Integer.valueOf(trackFormat.getInteger("width")));
        }
        if (trackFormat.containsKey("height")) {
            map.put("height", Integer.valueOf(trackFormat.getInteger("height")));
        }
        if (trackFormat.containsKey("rotation-degrees")) {
            map.put("rotation-degrees", Integer.valueOf(trackFormat.getInteger("rotation-degrees")));
        }
        if (trackFormat.containsKey(MediaFormat.KEY_BIT_RATE)) {
            map.put(MediaFormat.KEY_BIT_RATE, Integer.valueOf(trackFormat.getInteger(MediaFormat.KEY_BIT_RATE)));
        } else {
            int i3 = this.bitrate;
            if (i3 != 0) {
                map.put(MediaFormat.KEY_BIT_RATE, Integer.valueOf(i3));
            } else if (trackFormat.containsKey(MediaFormat.KEY_DURATION)) {
                int fileSize = (int) (((Def.getFileSize(fileDescriptor) << 3) * 1000000) / trackFormat.getLong(MediaFormat.KEY_DURATION));
                this.bitrate = fileSize;
                map.put(MediaFormat.KEY_BIT_RATE, Integer.valueOf(fileSize));
            }
        }
        if (trackFormat.containsKey(MediaFormat.KEY_FRAME_RATE)) {
            map.put(MediaFormat.KEY_FRAME_RATE, Integer.valueOf(trackFormat.getInteger(MediaFormat.KEY_FRAME_RATE)));
        }
        map.put(MediaFormat.KEY_I_FRAME_INTERVAL, Integer.valueOf(trackFormat.getInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)));
        if (trackFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE)) {
            map.put(MediaFormat.KEY_SAMPLE_RATE, Integer.valueOf(trackFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)));
        }
        if (trackFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT)) {
            map.put(MediaFormat.KEY_CHANNEL_COUNT, Integer.valueOf(trackFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)));
        }
        Log.d(str, "send TRACK_FORMAT message to decoder");
        map.put(Message.KEY_CONTENTS_ID, Integer.valueOf(i));
        this.messageProducer.newMessage(1, (Map<String, Object>) map).post();
        list.add(new Pair(Integer.valueOf(i2), mediaType));
    }

    /* renamed from: lambda$run$4$com-samsung-android-sume-core-filter-MediaParserFilter, reason: not valid java name */
    /* synthetic */ void m9560xc0282c64(MediaExtractor mediaExtractor, long j, int i) {
        String string = mediaExtractor.getTrackFormat(i).getString("mime");
        MediaType mediaType = string.startsWith("video") ? MediaType.COMPRESSED_VIDEO : MediaType.COMPRESSED_AUDIO;
        if (!this.descriptor.needToParse(mediaType)) {
            Log.d(TAG, "descriptor has type: " + mediaType);
            return;
        }
        try {
            BufferChannel bufferChannelApply = this.sendChannelQuery.apply(mediaType);
            if (bufferChannelApply == null) {
                Log.w(TAG, "no buffer-channel given for " + string + ", skip decoding this track");
                return;
            }
            mediaExtractor.selectTrack(i);
            String str = "[track: " + string + NavigationBarInflaterView.SIZE_MOD_END;
            boolean z = false;
            while (!z) {
                this.cvPause.block();
                MediaBuffer mediaBufferGroupOf = bufferChannelApply instanceof BufferSupplyChannel ? ((BufferSupplyChannel) bufferChannelApply).get() : MediaBuffer.groupOf(new MediaBuffer[0]);
                int sampleData = mediaExtractor.readSampleData((ByteBuffer) mediaBufferGroupOf.getTypedData(ByteBuffer.class), 0);
                if (sampleData < 0 || j < mediaExtractor.getSampleTime()) {
                    Log.d(TAG, str + "parser reached EOS");
                    mediaBufferGroupOf.setExtra("chunk-size", -1);
                    z = true;
                } else {
                    mediaBufferGroupOf.setExtra("chunk-size", Integer.valueOf(sampleData));
                    mediaBufferGroupOf.setExtra("timestampUs", Long.valueOf(mediaExtractor.getSampleTime()));
                    mediaExtractor.advance();
                }
                bufferChannelApply.send(mediaBufferGroupOf);
            }
            mediaExtractor.unselectTrack(i);
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
}
