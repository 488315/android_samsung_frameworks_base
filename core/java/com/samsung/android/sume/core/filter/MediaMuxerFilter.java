package com.samsung.android.sume.core.filter;

import android.app.blob.XmlTags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.p009os.ConditionVariable;
import android.system.ErrnoException;
import android.system.Int64Ref;
import android.system.Os;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.cache.DiskCache;
import com.samsung.android.sume.core.cache.KeyGenerator;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.MediaMuxerDescriptor;
import com.samsung.android.sume.core.exception.StreamFilterExitException;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.message.MessageProducer;
import com.samsung.android.sume.core.types.MediaType;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class MediaMuxerFilter implements MediaFilter, MediaInputStreamFilter {
    private static final String TAG = Def.tagOf((Class<?>) MediaMuxerFilter.class);
    private String cacheId;
    private int contentId;
    private MutableMediaFormat contentsFormat;
    private ConditionVariable cvPause;
    private final MediaMuxerDescriptor descriptor;
    private DiskCache diskCache;
    private MessageProducer messageProducer;
    private MediaMuxer muxer;
    private FileDescriptor outputFd;
    private int receiveChannelCount;
    private Function<Enum<?>, BufferChannel> receiveChannelQuery;
    private boolean storeCache;
    private final Map<MediaType, Pair<String, Integer>> trackIndexMap = new HashMap();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(2);
    private final Semaphore readyToStart = new Semaphore(0);
    private AtomicReference<ConditionVariable> channelReady = new AtomicReference<>();

    public MediaMuxerFilter(MediaMuxerDescriptor descriptor) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.cvPause = conditionVariable;
        this.descriptor = descriptor;
        conditionVariable.open();
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{4, 3, 6};
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00f5 -> B:43:0x011e). Please report as a decompilation issue!!! */
    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        DiskCache diskCache;
        switch (message.getCode()) {
            case 3:
                MediaType mediaType = (MediaType) message.get(Message.KEY_MEDIA_TYPE);
                MediaFormat mediaFormat = (MediaFormat) message.get("media-format");
                if (mediaFormat.containsKey("rotation-degrees")) {
                    int orientation = mediaFormat.getInteger("rotation-degrees");
                    this.muxer.setOrientationHint(orientation);
                    this.contentsFormat.set("rotation-degrees", Integer.valueOf(orientation));
                }
                if (mediaFormat.containsKey("width")) {
                    this.contentsFormat.setCols(mediaFormat.getInteger("width"));
                }
                if (mediaFormat.containsKey("height")) {
                    this.contentsFormat.setRows(mediaFormat.getInteger("height"));
                }
                int trackIndex = this.muxer.addTrack(mediaFormat);
                this.trackIndexMap.put(mediaType, new Pair<>(mediaFormat.getString(MediaFormat.KEY_MIME), Integer.valueOf(trackIndex)));
                message.reply("track-idx", Integer.valueOf(trackIndex));
                this.readyToStart.release();
                return true;
            case 4:
                FileDescriptor fileDescriptor = (FileDescriptor) message.get(Message.KEY_OUT_FILE);
                this.outputFd = fileDescriptor;
                Def.require(fileDescriptor != null);
                String str = TAG;
                Log.m94d(str, "outputFd size: " + Def.getFileSize(this.outputFd));
                String str2 = (String) Optional.ofNullable((String) message.get(Message.KEY_CACHE_ID)).map(new Function() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return KeyGenerator.getSimpleKey((String) obj);
                    }
                }).orElse(null);
                this.cacheId = str2;
                if (!this.storeCache && (diskCache = this.diskCache) != null && str2 != null) {
                    File cached = diskCache.get(str2);
                    if (cached == null || !cached.exists()) {
                        Log.m94d(str, "no cache exist: " + this.cacheId);
                    } else {
                        Log.m94d(str, "restore from cache: " + this.cacheId);
                        FileInputStream fis = null;
                        try {
                            try {
                                try {
                                    fis = new FileInputStream(cached);
                                    feedExistFramesToBufferChannel(fis.getFD());
                                    fis.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    if (fis != null) {
                                        fis.close();
                                    }
                                }
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        } catch (Throwable th) {
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                }
                try {
                    this.muxer = new MediaMuxer(this.outputFd, 0);
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                this.contentsFormat = com.samsung.android.sume.core.format.MediaFormat.mutableImageOf(new Object[0]);
                this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID)).intValue();
                int numTracks = ((Integer) message.get("track-count", 0)).intValue();
                this.readyToStart.release(this.receiveChannelCount - numTracks);
                return true;
            case 5:
            default:
                return false;
            case 6:
                Pair<DiskCache, Boolean> cacheData = (Pair) message.remove("cache");
                this.diskCache = cacheData.first;
                this.storeCache = cacheData.second.booleanValue();
                Def.require(this.diskCache != null);
                Log.m94d(TAG, "store: " + this.storeCache + ", disk-cache: " + this.diskCache);
                return true;
        }
    }

    private void feedExistFramesToBufferChannel(FileDescriptor cachedFd) {
        Log.m94d(TAG, "feedExistFramesToBufferChannel");
        if (this.receiveChannelQuery == null && this.channelReady.compareAndSet(null, new ConditionVariable())) {
            this.channelReady.get().block();
        }
        final MediaExtractor extractor = new MediaExtractor();
        try {
            try {
                extractor.setDataSource(cachedFd);
                IntStream.range(0, extractor.getTrackCount()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda4
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        MediaMuxerFilter.this.m373x6750d1fc(extractor, i);
                    }
                });
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
            }
        } finally {
            extractor.release();
        }
    }

    /* renamed from: lambda$feedExistFramesToBufferChannel$0$com-samsung-android-sume-core-filter-MediaMuxerFilter */
    /* synthetic */ void m373x6750d1fc(MediaExtractor extractor, int idx) {
        String mimeType = extractor.getTrackFormat(idx).getString(MediaFormat.KEY_MIME);
        MediaType mediaType = mimeType.startsWith("video") ? MediaType.RAW_VIDEO : MediaType.RAW_AUDIO;
        BufferChannel bufferChannel = this.receiveChannelQuery.apply(mediaType);
        if (bufferChannel == null) {
            Log.m102w(TAG, "no given buffer-channel for " + mediaType);
            return;
        }
        extractor.selectTrack(idx);
        while (true) {
            int sampleSize = (int) extractor.getSampleSize();
            if (sampleSize < 0) {
                Log.m94d(TAG, "parser reached EOS");
                extractor.unselectTrack(idx);
                return;
            }
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(sampleSize);
            byteBuffer.order(ByteOrder.nativeOrder());
            boolean z = false;
            int readBytes = extractor.readSampleData(byteBuffer, 0);
            if (sampleSize == readBytes) {
                z = true;
            }
            Def.check(z);
            MediaBuffer mediaBuffer = MediaBuffer.m331of(mediaType, byteBuffer);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.size = sampleSize;
            bufferInfo.presentationTimeUs = extractor.getSampleTime();
            if ((extractor.getSampleFlags() & 1) != 0) {
                bufferInfo.flags = 1 | bufferInfo.flags;
            }
            mediaBuffer.setExtra("buffer-info", bufferInfo);
            extractor.advance();
            Log.m94d(TAG, "push to buffer-channel[" + mimeType + "]: " + bufferInfo.presentationTimeUs + "[us]");
            bufferChannel.send(mediaBuffer);
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        MediaMuxer mediaMuxer;
        File cached;
        try {
            try {
                Log.m94d(TAG, "run: ibuf=" + ibuf + ", obuf=" + obuf);
                Def.require(this.receiveChannelCount != 0);
                this.readyToStart.acquire(this.receiveChannelCount);
                mediaMuxer = this.muxer;
            } catch (InterruptedException e) {
                e.printStackTrace();
                MediaMuxer mediaMuxer2 = this.muxer;
                if (mediaMuxer2 != null) {
                    mediaMuxer2.release();
                    this.muxer = null;
                    Log.m94d(TAG, "muxer released");
                }
            }
            if (mediaMuxer == null) {
                ibuf.release();
                throw new StreamFilterExitException("no muxer is given, might be released");
            }
            mediaMuxer.start();
            final List<Future<Boolean>> results = new ArrayList<>();
            this.trackIndexMap.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MediaMuxerFilter.this.m375x97443e46(results, (MediaType) obj, (Pair) obj2);
                }
            });
            for (Future<Boolean> result : results) {
                try {
                    Log.m94d(TAG, "result: " + result.get());
                } catch (CancellationException | ExecutionException e2) {
                    Log.m94d(TAG, "task canceled: " + e2.getMessage());
                }
            }
            results.clear();
            String str = TAG;
            Log.m94d(str, "total outputFd size: " + Def.getFileSize(this.outputFd));
            this.muxer.stop();
            DiskCache diskCache = this.diskCache;
            if (diskCache != null) {
                if (this.storeCache) {
                    if (this.cacheId == null) {
                        this.cacheId = "";
                    }
                    Log.m94d(str, "cache output file to " + this.cacheId);
                    this.diskCache.put(this.cacheId, new Function() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return MediaMuxerFilter.this.m376xc0989387((File) obj);
                        }
                    });
                } else {
                    String str2 = this.cacheId;
                    if (str2 != null && (cached = diskCache.get(str2)) != null && cached.exists()) {
                        boolean success = cached.delete();
                        Log.m94d(str, "cache is consumed, remove it: " + success);
                    }
                }
            }
            MediaMuxer mediaMuxer3 = this.muxer;
            if (mediaMuxer3 != null) {
                mediaMuxer3.release();
                this.muxer = null;
                Log.m94d(str, "muxer released");
            }
            MediaBuffer mediaBuffer = MediaBuffer.m328of(this.contentsFormat, this.outputFd);
            mediaBuffer.setExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId));
            String str3 = this.cacheId;
            if (str3 != null) {
                mediaBuffer.setExtra(Message.KEY_CACHE_ID, str3);
            }
            obuf.put(mediaBuffer);
            return obuf;
        } catch (Throwable th) {
            MediaMuxer mediaMuxer4 = this.muxer;
            if (mediaMuxer4 != null) {
                mediaMuxer4.release();
                this.muxer = null;
                Log.m94d(TAG, "muxer released");
            }
            throw th;
        }
    }

    /* renamed from: lambda$run$2$com-samsung-android-sume-core-filter-MediaMuxerFilter */
    /* synthetic */ void m375x97443e46(List results, final MediaType mediaType, final Pair data) {
        Future<Boolean> result = this.threadPool.submit(new Callable() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return MediaMuxerFilter.this.m374x6defe905(data, mediaType);
            }
        });
        results.add(result);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$run$1$com-samsung-android-sume-core-filter-MediaMuxerFilter */
    /* synthetic */ Boolean m374x6defe905(Pair data, MediaType mediaType) throws Exception {
        int i;
        String mime;
        String tag;
        BufferChannel bufferChannel;
        MediaType mediaType2 = mediaType;
        String mime2 = (String) data.first;
        int trackIndex = ((Integer) data.second).intValue();
        String tag2 = "[enc: " + mime2 + NavigationBarInflaterView.SIZE_MOD_END;
        BufferChannel bufferChannel2 = this.receiveChannelQuery.apply(mediaType2);
        long lastTimestampUs = 0;
        int numFrames = 0;
        int numFrames2 = 0;
        while (numFrames2 == 0) {
            this.cvPause.block();
            MediaBuffer mediaBuffer = bufferChannel2.receive();
            MediaCodec.BufferInfo bufferInfo = (MediaCodec.BufferInfo) mediaBuffer.getExtra("buffer-info");
            if ((bufferInfo.flags & 2) != 0) {
                bufferInfo.size = 0;
            }
            if ((bufferInfo.flags & 4) == 0) {
                i = numFrames2;
            } else {
                Log.m98i(TAG, tag2 + "muxer reached EOS");
                i = 1;
            }
            if (bufferInfo.size == 0) {
                mime = mime2;
                tag = tag2;
                bufferChannel = bufferChannel2;
            } else {
                ByteBuffer outputBuffer = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                tag = tag2;
                bufferChannel = bufferChannel2;
                Log.m94d(TAG, "write data[#" + trackIndex + "] from " + tag2 + ": " + bufferInfo.presentationTimeUs + XmlTags.ATTR_USER_ID);
                try {
                    this.muxer.writeSampleData(trackIndex, outputBuffer, bufferInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.m94d(TAG, "outputFd size: " + Def.getFileSize(this.outputFd));
                long lastTimestampUs2 = bufferInfo.presentationTimeUs;
                if (!this.descriptor.isMediaTypeToNotifyEvent(mediaType2)) {
                    mime = mime2;
                    lastTimestampUs = lastTimestampUs2;
                } else {
                    numFrames++;
                    mime = mime2;
                    this.messageProducer.newMessage(508, new Pair(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId)), new Pair(Message.KEY_MEDIA_TYPE, mediaType2), new Pair(Message.KEY_PROCESSED_FRAMES, Integer.valueOf(numFrames))).post();
                    lastTimestampUs = lastTimestampUs2;
                }
            }
            mediaBuffer.release();
            mediaType2 = mediaType;
            numFrames2 = i;
            tag2 = tag;
            bufferChannel2 = bufferChannel;
            mime2 = mime;
        }
        this.contentsFormat.set("last-" + (mediaType.isVideo() ? "video" : "audio") + "-timestamp-us", Long.valueOf(lastTimestampUs));
        return true;
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-filter-MediaMuxerFilter */
    /* synthetic */ Boolean m376xc0989387(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Os.sendfile(fos.getFD(), this.outputFd, new Int64Ref(0L), Def.getFileSize(this.outputFd));
            return true;
        } catch (ErrnoException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        String str = TAG;
        Log.m94d(str, "release...E");
        this.readyToStart.release(this.receiveChannelCount);
        Log.m94d(str, "release...X");
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

    @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
    public void setReceiveChannelQuery(Function<Enum<?>, BufferChannel> receiveChannelQuery, int numChannels) {
        this.receiveChannelQuery = receiveChannelQuery;
        this.receiveChannelCount = numChannels;
        if (this.channelReady.get() != null) {
            this.channelReady.get().open();
        }
    }

    @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
    public Function<Enum<?>, BufferChannel> getReceiveChannelQuery() {
        return this.receiveChannelQuery;
    }

    @Override // com.samsung.android.sume.core.filter.MediaInputStreamFilter
    public int getReceiveChannelCount() {
        return this.receiveChannelCount;
    }
}
