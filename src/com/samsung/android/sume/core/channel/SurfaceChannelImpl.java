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
import org.json.JSONException;

/* loaded from: classes6.dex */
public final class SurfaceChannelImpl implements BufferChannel, SurfaceChannel {
    private static final int HAL_PIXEL_FORMAT_EXYNOS_YCbCr_420_SPN = 291;
    private static final int HAL_PIXEL_FORMAT_EXYNOS_YCbCr_420_SP_M = 261;
    private static final int HAL_PIXEL_FORMAT_YCbCr_420_SP_VENUS = 2141391876;
    private static final String TAG = Def.tagOf((Class<?>) SurfaceChannelImpl.class);
    private static final Map<ColorFormat, int[]> vendorSpecificColorFormat = new HashMap<ColorFormat, int[]>() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl.1
        {
            put(ColorFormat.NV12, new int[]{SurfaceChannelImpl.HAL_PIXEL_FORMAT_YCbCr_420_SP_VENUS});
            put(ColorFormat.NV21, new int[]{261, 291});
        }
    };
    private BufferChannel bufferChannel;
    private final int channelType;
    private final Condition condition;
    private final ReentrantLock lock;
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

    SurfaceChannelImpl(int i, final BufferChannel bufferChannel) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
        this.channelType = i;
        if (i == 2) {
            Def.require(bufferChannel != null);
            this.bufferChannel = bufferChannel;
            Objects.requireNonNull(bufferChannel);
            this.sendHandler = new SurfaceChannelImpl$$ExternalSyntheticLambda5(bufferChannel);
            Objects.requireNonNull(bufferChannel);
            this.receiveHandler = new Supplier() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return bufferChannel.receive();
                }
            };
            this.onImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda7
                @Override // android.media.ImageReader.OnImageAvailableListener
                public final void onImageAvailable(ImageReader imageReader) {
                    this.f$0.onImageReceive(imageReader);
                }
            };
            return;
        }
        if (i == 3) {
            this.sendHandler = new Consumer() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws JSONException {
                    this.f$0.writeToSurface((MediaBuffer) obj);
                }
            };
            this.receiveHandler = new Supplier() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final Object get() {
                    return SurfaceChannelImpl.lambda$new$2();
                }
            };
            this.onImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda10
                @Override // android.media.ImageReader.OnImageAvailableListener
                public final void onImageAvailable(ImageReader imageReader) {
                    SurfaceChannelImpl.lambda$new$3(imageReader);
                }
            };
        } else {
            if (i == 4) {
                this.sendHandler = new Consumer() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.m9537xaa413ced((MediaBuffer) obj);
                    }
                };
                this.receiveHandler = new Supplier() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.m9538x372e540c();
                    }
                };
                this.onImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda4
                    @Override // android.media.ImageReader.OnImageAvailableListener
                    public final void onImageAvailable(ImageReader imageReader) {
                        this.f$0.onImageTransit(imageReader);
                    }
                };
                return;
            }
            throw new IllegalStateException("not supported type");
        }
    }

    /* renamed from: lambda$new$0$com-samsung-android-sume-core-channel-SurfaceChannelImpl, reason: not valid java name */
    /* synthetic */ void m9537xaa413ced(MediaBuffer mediaBuffer) {
        signal();
    }

    /* renamed from: lambda$new$1$com-samsung-android-sume-core-channel-SurfaceChannelImpl, reason: not valid java name */
    /* synthetic */ MediaBuffer m9538x372e540c() {
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
    public void configure(int i, int i2, int i3) {
        Def.require(this.channelType != 3);
        HandlerThread handlerThread = new HandlerThread("surface-receive-thread");
        this.receiveThread = handlerThread;
        handlerThread.start();
        ImageReader imageReaderNewInstance = ImageReader.newInstance(i, i2, i3, this.channelType == 4 ? 1 + this.capacity : 1);
        this.reader = imageReaderNewInstance;
        imageReaderNewInstance.setOnImageAvailableListener(this.onImageAvailableListener, new Handler(this.receiveThread.getLooper()));
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public void configure(Surface surface) {
        Def.require(this.channelType != 2);
        this.writer = ImageWriter.newInstance(surface, this.channelType == 4 ? 1 + this.capacity : 1);
        signal();
    }

    private void waitUntilSignaled(String str) {
        this.lock.lock();
        try {
            String str2 = TAG;
            Log.w(str2, "wait until " + str);
            this.condition.await();
            Log.d(str2, "now " + str);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void onImageTransit(ImageReader imageReader) {
        if (this.writer == null) {
            waitUntilSignaled("writer is given");
        }
        Image imageAcquireNextImage = imageReader.acquireNextImage();
        String str = TAG;
        StringBuilder sb = new StringBuilder("received image=");
        sb.append(imageAcquireNextImage);
        sb.append(", # of processed frames: ");
        int i = this.processedFrames + 1;
        this.processedFrames = i;
        sb.append(i);
        Log.d(str, sb.toString());
        this.writer.queueInputImage(imageAcquireNextImage);
        signal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onImageReceive(ImageReader imageReader) {
        Image imageAcquireLatestImage = imageReader.acquireLatestImage();
        final HardwareBuffer hardwareBuffer = imageAcquireLatestImage.getHardwareBuffer();
        if (hardwareBuffer != null) {
            if (this.pixelFormat == ColorFormat.NONE) {
                this.pixelFormat = (ColorFormat) vendorSpecificColorFormat.entrySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Arrays.stream((int[]) ((Map.Entry) obj).getValue()).anyMatch(new IntPredicate() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntPredicate
                            public final boolean test(int i) {
                                return SurfaceChannelImpl.lambda$onImageReceive$4(hardwareBuffer, i);
                            }
                        });
                    }
                }).map(new Function() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda12
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return (ColorFormat) ((Map.Entry) obj).getKey();
                    }
                }).findFirst().orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda13());
            }
            Log.d(TAG, "fmt=" + Integer.toHexString(hardwareBuffer.getFormat()) + NavigationBarInflaterView.SIZE_MOD_START + this.pixelFormat + "], usage=" + Long.toHexString(hardwareBuffer.getUsage()));
            MediaBuffer mediaBufferConvertTo = MediaBuffer.of(MediaFormat.mutableImageOf(DataType.U8, Shape.rectOf(imageAcquireLatestImage.getWidth(), imageAcquireLatestImage.getHeight()), this.pixelFormat), hardwareBuffer).convertTo(ByteBuffer.class);
            mediaBufferConvertTo.setExtra("timestampNs", Long.valueOf(imageAcquireLatestImage.getTimestamp()));
            mediaBufferConvertTo.setExtra(Message.KEY_BLOCK_ID, Integer.valueOf(this.processedFrames));
            send(mediaBufferConvertTo);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("received image=");
        sb.append(imageAcquireLatestImage);
        sb.append(", # of processed frames: ");
        int i = this.processedFrames + 1;
        this.processedFrames = i;
        sb.append(i);
        Log.d(str, sb.toString());
        imageAcquireLatestImage.close();
    }

    static /* synthetic */ boolean lambda$onImageReceive$4(HardwareBuffer hardwareBuffer, int i) {
        return i == hardwareBuffer.getFormat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToSurface(MediaBuffer mediaBuffer) throws JSONException {
        String str = TAG;
        Log.d(str, "writeToSurface: " + mediaBuffer);
        if (this.writer == null) {
            waitUntilSignaled("writer given");
        }
        long jLongValue = ((Long) mediaBuffer.getExtra("timestampNs")).longValue();
        Image imageDequeueInputImage = this.writer.dequeueInputImage();
        imageDequeueInputImage.setTimestamp(jLongValue);
        SharedBufferManager.copyFromBuffer(mediaBuffer, imageDequeueInputImage.getHardwareBuffer());
        mediaBuffer.release();
        this.writer.queueInputImage(imageDequeueInputImage);
        StringBuilder sb = new StringBuilder("send image=");
        sb.append(imageDequeueInputImage);
        sb.append(", # of processed frames: ");
        int i = this.processedFrames + 1;
        this.processedFrames = i;
        sb.append(i);
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(jLongValue / 1000);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        Log.d(str, sb.toString());
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(MediaBuffer mediaBuffer) {
        this.sendHandler.accept(mediaBuffer);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public MediaBuffer receive() {
        return this.receiveHandler.get();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
        BufferChannel bufferChannel = this.bufferChannel;
        if (bufferChannel != null) {
            bufferChannel.close();
        }
        HandlerThread handlerThread = this.receiveThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
        BufferChannel bufferChannel = this.bufferChannel;
        if (bufferChannel != null) {
            bufferChannel.cancel();
        }
        HandlerThread handlerThread = this.receiveThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        return ((Boolean) Optional.ofNullable(this.bufferChannel).map(new Function() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((BufferChannel) obj).isClosedForSend());
            }
        }).orElse(false)).booleanValue();
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        return ((Boolean) Optional.ofNullable(this.bufferChannel).map(new Function() { // from class: com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((BufferChannel) obj).isClosedForReceive());
            }
        }).orElse(false)).booleanValue();
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public int getNumberOfFrames() {
        return this.numberOfFrames.get();
    }

    @Override // com.samsung.android.sume.core.channel.SurfaceChannel
    public void setNumberOfFrames(int i) {
        this.numberOfFrames.set(i);
    }

    @Override // com.samsung.android.sume.core.channel.BufferChannel
    public void setCapacity(int i) {
        Def.require(i > 0);
        this.capacity = i;
    }
}
