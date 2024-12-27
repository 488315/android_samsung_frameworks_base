package com.samsung.android.sume.core.channel;

import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.SharedBufferManager;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SurfaceChannelImpl implements BufferChannel, SurfaceChannel {
    private static final int HAL_PIXEL_FORMAT_EXYNOS_YCbCr_420_SPN = 291;
    private static final int HAL_PIXEL_FORMAT_EXYNOS_YCbCr_420_SP_M = 261;
    private static final int HAL_PIXEL_FORMAT_YCbCr_420_SP_VENUS = 2141391876;
    private static final String TAG = Def.tagOf((Class<?>) SurfaceChannelImpl.class);
    private static final Map<ColorFormat, int[]> vendorSpecificColorFormat =
            new HashMap<
                    ColorFormat,
                    int
                            []>() { // from class:
                                    // com.samsung.android.sume.core.channel.SurfaceChannelImpl.1
                {
                    put(
                            ColorFormat.NV12,
                            new int[] {SurfaceChannelImpl.HAL_PIXEL_FORMAT_YCbCr_420_SP_VENUS});
                    put(ColorFormat.NV21, new int[] {261, 291});
                }
            };
    private BufferChannel bufferChannel;
    private final int channelType;
    private final ImageReader.OnImageAvailableListener onImageAvailableListener;
    private ImageReader reader;
    private final Supplier<MediaBuffer> receiveHandler;
    private HandlerThread receiveThread;
    private final Consumer<MediaBuffer> sendHandler;
    private ImageWriter writer;
    private ColorFormat pixelFormat = ColorFormat.NONE;
    private int processedFrames = 0;
    private final AtomicInteger numberOfFrames = new AtomicInteger(0);
    private int capacity = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = this.lock.newCondition();

    SurfaceChannelImpl(int channelType, final BufferChannel bufferChannel) {
        this.channelType = channelType;
        switch (channelType) {
            case 2:
                Def.require(bufferChannel != null);
                this.bufferChannel = bufferChannel;
                Objects.requireNonNull(bufferChannel);
                this.sendHandler = new SurfaceChannelImpl$$ExternalSyntheticLambda5(bufferChannel);
                Objects.requireNonNull(bufferChannel);
                this.receiveHandler =
                        new Supplier() { // from class:
                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda6
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return BufferChannel.this.receive();
                            }
                        };
                this.onImageAvailableListener =
                        new ImageReader
                                .OnImageAvailableListener() { // from class:
                                                              // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda7
                            @Override // android.media.ImageReader.OnImageAvailableListener
                            public final void onImageAvailable(ImageReader imageReader) {
                                SurfaceChannelImpl.this.onImageReceive(imageReader);
                            }
                        };
                return;
            case 3:
                this.sendHandler =
                        new Consumer() { // from class:
                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda8
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SurfaceChannelImpl.this.writeToSurface((MediaBuffer) obj);
                            }
                        };
                this.receiveHandler =
                        new Supplier() { // from class:
                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda9
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return SurfaceChannelImpl.lambda$new$2();
                            }
                        };
                this.onImageAvailableListener =
                        new ImageReader
                                .OnImageAvailableListener() { // from class:
                                                              // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda10
                            @Override // android.media.ImageReader.OnImageAvailableListener
                            public final void onImageAvailable(ImageReader imageReader) {
                                SurfaceChannelImpl.lambda$new$3(imageReader);
                            }
                        };
                return;
            case 4:
                this.sendHandler =
                        new Consumer() { // from class:
                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SurfaceChannelImpl.this.m9123xaa413ced((MediaBuffer) obj);
                            }
                        };
                this.receiveHandler =
                        new Supplier() { // from class:
                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda3
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return SurfaceChannelImpl.this.m9124x372e540c();
                            }
                        };
                this.onImageAvailableListener =
                        new ImageReader
                                .OnImageAvailableListener() { // from class:
                                                              // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda4
                            @Override // android.media.ImageReader.OnImageAvailableListener
                            public final void onImageAvailable(ImageReader imageReader) {
                                SurfaceChannelImpl.this.onImageTransit(imageReader);
                            }
                        };
                return;
            default:
                throw new IllegalStateException("not supported type");
        }
    }

    /* renamed from: lambda$new$0$com-samsung-android-sume-core-channel-SurfaceChannelImpl, reason: not valid java name */
    /* synthetic */ void m9123xaa413ced(MediaBuffer mediaBuffer) {
        signal();
    }

    /* renamed from: lambda$new$1$com-samsung-android-sume-core-channel-SurfaceChannelImpl, reason: not valid java name */
    /* synthetic */ MediaBuffer m9124x372e540c() {
        waitUntilSignaled("receive buffer");
        return MediaBuffer.mutableOf(MediaFormat.mutableImageOf(new Object[0]));
    }

    static /* synthetic */ MediaBuffer lambda$new$2() {
        throw new UnsupportedOperationException("");
    }

    static /* synthetic */ void lambda$new$3(ImageReader imageReader) {
        throw new UnsupportedOperationException("");
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public void configure(int width, int height, int format) {
        Def.require(this.channelType != 3);
        this.receiveThread = new HandlerThread("surface-receive-thread");
        this.receiveThread.start();
        int maxImages = this.channelType == 4 ? 1 + this.capacity : 1;
        this.reader = ImageReader.newInstance(width, height, format, maxImages);
        this.reader.setOnImageAvailableListener(
                this.onImageAvailableListener, new Handler(this.receiveThread.getLooper()));
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public void configure(Surface surface) {
        Def.require(this.channelType != 2);
        int maxImages = this.channelType == 4 ? 1 + this.capacity : 1;
        this.writer = ImageWriter.newInstance(surface, maxImages);
        signal();
    }

    private void waitUntilSignaled(String waitMessage) {
        this.lock.lock();
        try {
            try {
                Log.w(TAG, "wait until " + waitMessage);
                this.condition.await();
                Log.d(TAG, "now " + waitMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            this.lock.unlock();
        }
    }

    private void signal() {
        this.lock.lock();
        try {
            this.condition.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public Surface getSurface() {
        Def.require(this.channelType != 3);
        return this.reader.getSurface();
    }

    public void onImageTransit(ImageReader reader) {
        if (this.writer == null) {
            waitUntilSignaled("writer is given");
        }
        Image image = reader.acquireNextImage();
        String str = TAG;
        StringBuilder append =
                new StringBuilder()
                        .append("received image=")
                        .append(image)
                        .append(", # of processed frames: ");
        int i = this.processedFrames + 1;
        this.processedFrames = i;
        Log.d(str, append.append(i).toString());
        this.writer.queueInputImage(image);
        signal();
    }

    public void onImageReceive(ImageReader reader) {
        Image image = reader.acquireLatestImage();
        final HardwareBuffer hwBuffer = image.getHardwareBuffer();
        if (hwBuffer != null) {
            if (this.pixelFormat == ColorFormat.NONE) {
                this.pixelFormat =
                        (ColorFormat)
                                vendorSpecificColorFormat.entrySet().stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                                  // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda11
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        boolean anyMatch;
                                                        anyMatch =
                                                                Arrays.stream(
                                                                                (int[])
                                                                                        ((Map.Entry)
                                                                                                        obj)
                                                                                                .getValue())
                                                                        .anyMatch(
                                                                                new IntPredicate() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda0
                                                                                    @Override // java.util.function.IntPredicate
                                                                                    public final
                                                                                    boolean test(
                                                                                            int i) {
                                                                                        return SurfaceChannelImpl
                                                                                                .lambda$onImageReceive$4(
                                                                                                        HardwareBuffer
                                                                                                                .this,
                                                                                                        i);
                                                                                    }
                                                                                });
                                                        return anyMatch;
                                                    }
                                                })
                                        .map(
                                                new Function() { // from class:
                                                                 // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda12
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return (ColorFormat)
                                                                ((Map.Entry) obj).getKey();
                                                    }
                                                })
                                        .findFirst()
                                        .orElseThrow(
                                                new SurfaceChannelImpl$$ExternalSyntheticLambda13());
            }
            Log.d(
                    TAG,
                    "fmt="
                            + Integer.toHexString(hwBuffer.getFormat())
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + this.pixelFormat
                            + "], usage="
                            + Long.toHexString(hwBuffer.getUsage()));
            MediaBuffer mediaBuffer =
                    MediaBuffer.of(
                                    MediaFormat.mutableImageOf(
                                            DataType.U8,
                                            Shape.rectOf(image.getWidth(), image.getHeight()),
                                            this.pixelFormat),
                                    hwBuffer)
                            .convertTo(ByteBuffer.class);
            mediaBuffer.setExtra("timestampNs", Long.valueOf(image.getTimestamp()));
            mediaBuffer.setExtra(Message.KEY_BLOCK_ID, Integer.valueOf(this.processedFrames));
            send(mediaBuffer);
        }
        String str = TAG;
        StringBuilder append =
                new StringBuilder()
                        .append("received image=")
                        .append(image)
                        .append(", # of processed frames: ");
        int i = this.processedFrames + 1;
        this.processedFrames = i;
        Log.d(str, append.append(i).toString());
        image.close();
    }

    static /* synthetic */ boolean lambda$onImageReceive$4(HardwareBuffer hwBuffer, int e) {
        return e == hwBuffer.getFormat();
    }

    public void writeToSurface(MediaBuffer mediaBuffer) {
        Log.d(TAG, "writeToSurface: " + mediaBuffer);
        if (this.writer == null) {
            waitUntilSignaled("writer given");
        }
        long timestampNs = ((Long) mediaBuffer.getExtra("timestampNs")).longValue();
        Image image = this.writer.dequeueInputImage();
        image.setTimestamp(timestampNs);
        SharedBufferManager.copyFromBuffer(mediaBuffer, image.getHardwareBuffer());
        mediaBuffer.release();
        this.writer.queueInputImage(image);
        String str = TAG;
        StringBuilder append =
                new StringBuilder()
                        .append("send image=")
                        .append(image)
                        .append(", # of processed frames: ");
        int i = this.processedFrames + 1;
        this.processedFrames = i;
        Log.d(
                str,
                append.append(i)
                        .append(NavigationBarInflaterView.SIZE_MOD_START)
                        .append(timestampNs / 1000)
                        .append(NavigationBarInflaterView.SIZE_MOD_END)
                        .toString());
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer data) {
        this.sendHandler.accept(data);
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        return this.receiveHandler.get();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
        if (this.bufferChannel != null) {
            this.bufferChannel.close();
        }
        if (this.receiveThread != null) {
            this.receiveThread.quitSafely();
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
        if (this.bufferChannel != null) {
            this.bufferChannel.cancel();
        }
        if (this.receiveThread != null) {
            this.receiveThread.quitSafely();
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        return ((Boolean)
                        Optional.ofNullable(this.bufferChannel)
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda14
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return Boolean.valueOf(
                                                        ((BufferChannel) obj).isClosedForSend());
                                            }
                                        })
                                .orElse(false))
                .booleanValue();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        return ((Boolean)
                        Optional.ofNullable(this.bufferChannel)
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return Boolean.valueOf(
                                                        ((BufferChannel) obj).isClosedForReceive());
                                            }
                                        })
                                .orElse(false))
                .booleanValue();
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public int getNumberOfFrames() {
        return this.numberOfFrames.get();
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public void setNumberOfFrames(int numberOfFrames) {
        this.numberOfFrames.set(numberOfFrames);
    }

    @Override // com.samsung.android.sume.core.channel.BufferChannel
    public void setCapacity(int capacity) {
        Def.require(capacity > 0);
        this.capacity = capacity;
    }
}
