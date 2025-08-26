package android.hardware.camera2;

import android.hardware.camera2.params.MultiResolutionStreamInfo;
import android.media.Image;
import android.media.ImageReader;
import android.util.Size;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Collection;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class MultiResolutionImageReader implements AutoCloseable {
    private static final String TAG = "MultiResolutionImageReader";
    private final int mFormat;
    private final int mMaxImages;
    private final ImageReader[] mReaders;
    private final MultiResolutionStreamInfo[] mStreamInfo;

    public MultiResolutionImageReader(Collection<MultiResolutionStreamInfo> collection, int i, int i2) {
        this.mFormat = i;
        this.mMaxImages = i2;
        if (collection == null || collection.size() <= 1) {
            throw new IllegalArgumentException("The streams info collection must contain at least 2 entries");
        }
        if (i2 < 1) {
            throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (i == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        int size = collection.size();
        this.mReaders = new ImageReader[size];
        this.mStreamInfo = new MultiResolutionStreamInfo[size];
        int i3 = 0;
        for (MultiResolutionStreamInfo multiResolutionStreamInfo : collection) {
            this.mReaders[i3] = ImageReader.newInstance(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight(), i, i2);
            this.mStreamInfo[i3] = multiResolutionStreamInfo;
            i3++;
        }
    }

    public MultiResolutionImageReader(Collection<MultiResolutionStreamInfo> collection, int i, int i2, long j) {
        this.mFormat = i;
        this.mMaxImages = i2;
        if (collection == null || collection.size() <= 1) {
            throw new IllegalArgumentException("The streams info collection must contain at least 2 entries");
        }
        if (i2 < 1) {
            throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (i == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        int size = collection.size();
        this.mReaders = new ImageReader[size];
        this.mStreamInfo = new MultiResolutionStreamInfo[size];
        int i3 = 0;
        for (MultiResolutionStreamInfo multiResolutionStreamInfo : collection) {
            int i4 = i;
            this.mReaders[i3] = ImageReader.newInstance(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight(), i4, i2, j);
            this.mStreamInfo[i3] = multiResolutionStreamInfo;
            i3++;
            i = i4;
        }
    }

    public void setOnImageAvailableListener(ImageReader.OnImageAvailableListener onImageAvailableListener, Executor executor) {
        int i = 0;
        while (true) {
            ImageReader[] imageReaderArr = this.mReaders;
            if (i >= imageReaderArr.length) {
                return;
            }
            imageReaderArr[i].setOnImageAvailableListenerWithExecutor(onImageAvailableListener, executor);
            i++;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        flush();
        int i = 0;
        while (true) {
            ImageReader[] imageReaderArr = this.mReaders;
            if (i >= imageReaderArr.length) {
                return;
            }
            imageReaderArr[i].close();
            i++;
        }
    }

    protected void finalize() {
        close();
    }

    public void flush() {
        flushOther(null);
    }

    public void flushOther(ImageReader imageReader) {
        int i = 0;
        while (true) {
            ImageReader[] imageReaderArr = this.mReaders;
            if (i >= imageReaderArr.length) {
                return;
            }
            if (imageReader == null || imageReader != imageReaderArr[i]) {
                while (true) {
                    Image imageAcquireNextImageNoThrowISE = this.mReaders[i].acquireNextImageNoThrowISE();
                    if (imageAcquireNextImageNoThrowISE == null) {
                        break;
                    } else {
                        imageAcquireNextImageNoThrowISE.close();
                    }
                }
            }
            i++;
        }
    }

    public ImageReader[] getReaders() {
        return this.mReaders;
    }

    public Surface getSurface(Size size, String str) {
        Preconditions.checkNotNull(size, "configuredSize must not be null");
        Preconditions.checkNotNull(str, "physicalCameraId must not be null");
        int i = 0;
        while (true) {
            MultiResolutionStreamInfo[] multiResolutionStreamInfoArr = this.mStreamInfo;
            if (i < multiResolutionStreamInfoArr.length) {
                if (multiResolutionStreamInfoArr[i].getWidth() == size.getWidth() && this.mStreamInfo[i].getHeight() == size.getHeight() && str.equals(this.mStreamInfo[i].getPhysicalCameraId())) {
                    return this.mReaders[i].getSurface();
                }
                i++;
            } else {
                throw new IllegalArgumentException("configuredSize and physicalCameraId don't match with this MultiResolutionImageReader");
            }
        }
    }

    public Surface getSurface() {
        int width = this.mReaders[0].getWidth() * this.mReaders[0].getHeight();
        Surface surface = this.mReaders[0].getSurface();
        int i = 1;
        while (true) {
            ImageReader[] imageReaderArr = this.mReaders;
            if (i >= imageReaderArr.length) {
                return surface;
            }
            int width2 = imageReaderArr[i].getWidth() * this.mReaders[i].getHeight();
            if (width2 < width) {
                surface = this.mReaders[i].getSurface();
                width = width2;
            }
            i++;
        }
    }

    public MultiResolutionStreamInfo getStreamInfoForImageReader(ImageReader imageReader) {
        int i = 0;
        while (true) {
            ImageReader[] imageReaderArr = this.mReaders;
            if (i < imageReaderArr.length) {
                if (imageReader == imageReaderArr[i]) {
                    return this.mStreamInfo[i];
                }
                i++;
            } else {
                throw new IllegalArgumentException("ImageReader doesn't belong to this multi-resolution imagereader");
            }
        }
    }
}
