package com.samsung.android.sume.core.filter;

import android.app.blob.XmlTags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import android.os.ConditionVariable;
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
import com.samsung.android.sume.core.format.MediaFormat;
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

/* loaded from: classes6.dex */
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

    public MediaMuxerFilter(MediaMuxerDescriptor mediaMuxerDescriptor) {
        ConditionVariable conditionVariable = new ConditionVariable();
        this.cvPause = conditionVariable;
        this.descriptor = mediaMuxerDescriptor;
        conditionVariable.open();
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{4, 3, 6};
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        DiskCache diskCache;
        int code = message.getCode();
        if (code != 3) {
            if (code == 4) {
                FileDescriptor fileDescriptor = (FileDescriptor) message.get(Message.KEY_OUT_FILE);
                this.outputFd = fileDescriptor;
                Def.require(fileDescriptor != null);
                String str = TAG;
                Log.d(str, "outputFd size: " + Def.getFileSize(this.outputFd));
                FileInputStream fileInputStream = null;
                String str2 = (String) Optional.ofNullable((String) message.get(Message.KEY_CACHE_ID)).map(new Function() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return KeyGenerator.getSimpleKey((String) obj);
                    }
                }).orElse(null);
                this.cacheId = str2;
                if (!this.storeCache && (diskCache = this.diskCache) != null && str2 != null) {
                    File file = diskCache.get(str2);
                    if (file != null && file.exists()) {
                        Log.d(str, "restore from cache: " + this.cacheId);
                        try {
                            try {
                                try {
                                    FileInputStream fileInputStream2 = new FileInputStream(file);
                                    try {
                                        feedExistFramesToBufferChannel(fileInputStream2.getFD());
                                        fileInputStream2.close();
                                    } catch (IOException e) {
                                        e = e;
                                        fileInputStream = fileInputStream2;
                                        e.printStackTrace();
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        this.muxer = new MediaMuxer(this.outputFd, 0);
                                        this.contentsFormat = MediaFormat.mutableImageOf(new Object[0]);
                                        this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID)).intValue();
                                        this.readyToStart.release(this.receiveChannelCount - ((Integer) message.get("track-count", 0)).intValue());
                                        return true;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileInputStream = fileInputStream2;
                                        if (fileInputStream != null) {
                                            try {
                                                fileInputStream.close();
                                            } catch (IOException e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            } catch (IOException e3) {
                                e = e3;
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    } else {
                        Log.d(str, "no cache exist: " + this.cacheId);
                    }
                }
                try {
                    this.muxer = new MediaMuxer(this.outputFd, 0);
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                this.contentsFormat = MediaFormat.mutableImageOf(new Object[0]);
                this.contentId = ((Integer) message.get(Message.KEY_CONTENTS_ID)).intValue();
                this.readyToStart.release(this.receiveChannelCount - ((Integer) message.get("track-count", 0)).intValue());
            } else {
                if (code != 6) {
                    return false;
                }
                Pair pair = (Pair) message.remove("cache");
                this.diskCache = (DiskCache) pair.first;
                this.storeCache = ((Boolean) pair.second).booleanValue();
                Def.require(this.diskCache != null);
                Log.d(TAG, "store: " + this.storeCache + ", disk-cache: " + this.diskCache);
            }
        } else {
            MediaType mediaType = (MediaType) message.get(Message.KEY_MEDIA_TYPE);
            android.media.MediaFormat mediaFormat = (android.media.MediaFormat) message.get("media-format");
            if (mediaFormat.containsKey("rotation-degrees")) {
                int integer = mediaFormat.getInteger("rotation-degrees");
                this.muxer.setOrientationHint(integer);
                this.contentsFormat.set("rotation-degrees", Integer.valueOf(integer));
            }
            if (mediaFormat.containsKey("width")) {
                this.contentsFormat.setCols(mediaFormat.getInteger("width"));
            }
            if (mediaFormat.containsKey("height")) {
                this.contentsFormat.setRows(mediaFormat.getInteger("height"));
            }
            int addTrack = this.muxer.addTrack(mediaFormat);
            this.trackIndexMap.put(mediaType, new Pair<>(mediaFormat.getString("mime"), Integer.valueOf(addTrack)));
            message.reply("track-idx", Integer.valueOf(addTrack));
            this.readyToStart.release();
        }
        return true;
    }

    private void feedExistFramesToBufferChannel(FileDescriptor fileDescriptor) {
        Log.d(TAG, "feedExistFramesToBufferChannel");
        if (this.receiveChannelQuery == null && this.channelReady.compareAndSet(null, new ConditionVariable())) {
            this.channelReady.get().block();
        }
        final MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(fileDescriptor);
            IntStream.range(0, mediaExtractor.getTrackCount()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    MediaMuxerFilter.this.m9542x6750d1fc(mediaExtractor, i);
                }
            });
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        } finally {
            mediaExtractor.release();
        }
    }

    /* renamed from: lambda$feedExistFramesToBufferChannel$0$com-samsung-android-sume-core-filter-MediaMuxerFilter, reason: not valid java name */
    /* synthetic */ void m9542x6750d1fc(MediaExtractor mediaExtractor, int i) {
        String string = mediaExtractor.getTrackFormat(i).getString("mime");
        MediaType mediaType = string.startsWith("video") ? MediaType.RAW_VIDEO : MediaType.RAW_AUDIO;
        BufferChannel apply = this.receiveChannelQuery.apply(mediaType);
        if (apply == null) {
            Log.w(TAG, "no given buffer-channel for " + mediaType);
            return;
        }
        mediaExtractor.selectTrack(i);
        while (true) {
            int sampleSize = (int) mediaExtractor.getSampleSize();
            if (sampleSize < 0) {
                Log.d(TAG, "parser reached EOS");
                mediaExtractor.unselectTrack(i);
                return;
            }
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(sampleSize);
            allocateDirect.order(ByteOrder.nativeOrder());
            Def.check(sampleSize == mediaExtractor.readSampleData(allocateDirect, 0));
            MediaBuffer of = MediaBuffer.of(mediaType, allocateDirect);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.size = sampleSize;
            bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
            if ((mediaExtractor.getSampleFlags() & 1) != 0) {
                bufferInfo.flags |= 1;
            }
            of.setExtra("buffer-info", bufferInfo);
            mediaExtractor.advance();
            Log.d(TAG, "push to buffer-channel[" + string + "]: " + bufferInfo.presentationTimeUs + "[us]");
            apply.send(of);
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        String str;
        MediaMuxer mediaMuxer;
        File file;
        try {
            try {
                Log.d(TAG, "run: ibuf=" + mediaBuffer + ", obuf=" + mutableMediaBuffer);
                Def.require(this.receiveChannelCount != 0);
                this.readyToStart.acquire(this.receiveChannelCount);
                mediaMuxer = this.muxer;
            } catch (InterruptedException e) {
                e.printStackTrace();
                MediaMuxer mediaMuxer2 = this.muxer;
                if (mediaMuxer2 != null) {
                    mediaMuxer2.release();
                    this.muxer = null;
                    str = TAG;
                }
            }
            if (mediaMuxer == null) {
                mediaBuffer.release();
                throw new StreamFilterExitException("no muxer is given, might be released");
            }
            mediaMuxer.start();
            final ArrayList<Future> arrayList = new ArrayList();
            this.trackIndexMap.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MediaMuxerFilter.this.m9544x97443e46(arrayList, (MediaType) obj, (Pair) obj2);
                }
            });
            for (Future future : arrayList) {
                try {
                    Log.d(TAG, "result: " + future.get());
                } catch (CancellationException | ExecutionException e2) {
                    Log.d(TAG, "task canceled: " + e2.getMessage());
                }
            }
            arrayList.clear();
            str = TAG;
            Log.d(str, "total outputFd size: " + Def.getFileSize(this.outputFd));
            this.muxer.stop();
            DiskCache diskCache = this.diskCache;
            if (diskCache != null) {
                if (this.storeCache) {
                    if (this.cacheId == null) {
                        this.cacheId = "";
                    }
                    Log.d(str, "cache output file to " + this.cacheId);
                    this.diskCache.put(this.cacheId, new Function() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return MediaMuxerFilter.this.m9545xc0989387((File) obj);
                        }
                    });
                } else {
                    String str2 = this.cacheId;
                    if (str2 != null && (file = diskCache.get(str2)) != null && file.exists()) {
                        Log.d(str, "cache is consumed, remove it: " + file.delete());
                    }
                }
            }
            MediaMuxer mediaMuxer3 = this.muxer;
            if (mediaMuxer3 != null) {
                mediaMuxer3.release();
                this.muxer = null;
                Log.d(str, "muxer released");
            }
            MediaBuffer of = MediaBuffer.of(this.contentsFormat, this.outputFd);
            of.setExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId));
            String str3 = this.cacheId;
            if (str3 != null) {
                of.setExtra(Message.KEY_CACHE_ID, str3);
            }
            mutableMediaBuffer.put(of);
            return mutableMediaBuffer;
        } catch (Throwable th) {
            MediaMuxer mediaMuxer4 = this.muxer;
            if (mediaMuxer4 != null) {
                mediaMuxer4.release();
                this.muxer = null;
                Log.d(TAG, "muxer released");
            }
            throw th;
        }
    }

    /* renamed from: lambda$run$2$com-samsung-android-sume-core-filter-MediaMuxerFilter, reason: not valid java name */
    /* synthetic */ void m9544x97443e46(List list, final MediaType mediaType, final Pair pair) {
        list.add(this.threadPool.submit(new Callable() { // from class: com.samsung.android.sume.core.filter.MediaMuxerFilter$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return MediaMuxerFilter.this.m9543x6defe905(pair, mediaType);
            }
        }));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$run$1$com-samsung-android-sume-core-filter-MediaMuxerFilter, reason: not valid java name */
    /* synthetic */ Boolean m9543x6defe905(Pair pair, MediaType mediaType) throws Exception {
        boolean z;
        int i;
        MediaBuffer mediaBuffer;
        String str = (String) pair.first;
        int intValue = ((Integer) pair.second).intValue();
        String str2 = "[enc: " + str + NavigationBarInflaterView.SIZE_MOD_END;
        BufferChannel apply = this.receiveChannelQuery.apply(mediaType);
        int i2 = 0;
        long j = 0;
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            this.cvPause.block();
            MediaBuffer receive = apply.receive();
            MediaCodec.BufferInfo bufferInfo = (MediaCodec.BufferInfo) receive.getExtra("buffer-info");
            if ((bufferInfo.flags & 2) != 0) {
                bufferInfo.size = i2;
            }
            if ((bufferInfo.flags & 4) != 0) {
                Log.i(TAG, str2 + "muxer reached EOS");
                z = true;
            } else {
                z = z2;
            }
            if (bufferInfo.size != 0) {
                ByteBuffer byteBuffer = (ByteBuffer) receive.getTypedData(ByteBuffer.class);
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                String str3 = TAG;
                StringBuilder sb = new StringBuilder("write data[#");
                sb.append(intValue);
                sb.append("] from ");
                sb.append(str2);
                sb.append(": ");
                mediaBuffer = receive;
                sb.append(bufferInfo.presentationTimeUs);
                sb.append(XmlTags.ATTR_USER_ID);
                Log.d(str3, sb.toString());
                try {
                    this.muxer.writeSampleData(intValue, byteBuffer, bufferInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "outputFd size: " + Def.getFileSize(this.outputFd));
                j = bufferInfo.presentationTimeUs;
                if (this.descriptor.isMediaTypeToNotifyEvent(mediaType)) {
                    MessageProducer messageProducer = this.messageProducer;
                    Pair<String, Object>[] pairArr = new Pair[3];
                    i = i2;
                    pairArr[i] = new Pair<>(Message.KEY_CONTENTS_ID, Integer.valueOf(this.contentId));
                    pairArr[1] = new Pair<>(Message.KEY_MEDIA_TYPE, mediaType);
                    i3++;
                    pairArr[2] = new Pair<>(Message.KEY_PROCESSED_FRAMES, Integer.valueOf(i3));
                    messageProducer.newMessage(508, pairArr).post();
                } else {
                    i = i2;
                }
            } else {
                i = i2;
                mediaBuffer = receive;
            }
            mediaBuffer.release();
            z2 = z;
            i2 = i;
        }
        MutableMediaFormat mutableMediaFormat = this.contentsFormat;
        StringBuilder sb2 = new StringBuilder("last-");
        sb2.append(mediaType.isVideo() ? "video" : "audio");
        sb2.append("-timestamp-us");
        mutableMediaFormat.set(sb2.toString(), Long.valueOf(j));
        return true;
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-filter-MediaMuxerFilter, reason: not valid java name */
    /* synthetic */ Boolean m9545xc0989387(File file) {
        try {
            Os.sendfile(new FileOutputStream(file).getFD(), this.outputFd, new Int64Ref(0L), Def.getFileSize(this.outputFd));
            return true;
        } catch (ErrnoException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        String str = TAG;
        Log.d(str, "release...E");
        this.readyToStart.release(this.receiveChannelCount);
        Log.d(str, "release...X");
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
    public void setReceiveChannelQuery(Function<Enum<?>, BufferChannel> function, int i) {
        this.receiveChannelQuery = function;
        this.receiveChannelCount = i;
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
