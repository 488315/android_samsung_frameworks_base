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

    public MultiResolutionImageReader(Collection<MultiResolutionStreamInfo> streams, int format, int maxImages) {
        this.mFormat = format;
        this.mMaxImages = maxImages;
        if (streams == null || streams.size() <= 1) {
            throw new IllegalArgumentException("The streams info collection must contain at least 2 entries");
        }
        if (this.mMaxImages < 1) {
            throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (format == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        int numImageReaders = streams.size();
        this.mReaders = new ImageReader[numImageReaders];
        this.mStreamInfo = new MultiResolutionStreamInfo[numImageReaders];
        int index = 0;
        for (MultiResolutionStreamInfo streamInfo : streams) {
            this.mReaders[index] = ImageReader.newInstance(streamInfo.getWidth(), streamInfo.getHeight(), format, maxImages);
            this.mStreamInfo[index] = streamInfo;
            index++;
        }
    }

    public MultiResolutionImageReader(Collection<MultiResolutionStreamInfo> streams, int format, int maxImages, long usage) {
        this.mFormat = format;
        this.mMaxImages = maxImages;
        if (streams == null || streams.size() <= 1) {
            throw new IllegalArgumentException("The streams info collection must contain at least 2 entries");
        }
        if (this.mMaxImages < 1) {
            throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (format == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        int numImageReaders = streams.size();
        this.mReaders = new ImageReader[numImageReaders];
        this.mStreamInfo = new MultiResolutionStreamInfo[numImageReaders];
        int index = 0;
        for (MultiResolutionStreamInfo streamInfo : streams) {
            this.mReaders[index] = ImageReader.newInstance(streamInfo.getWidth(), streamInfo.getHeight(), format, maxImages, usage);
            this.mStreamInfo[index] = streamInfo;
            index++;
        }
    }

    public void setOnImageAvailableListener(ImageReader.OnImageAvailableListener listener, Executor executor) {
        for (int i = 0; i < this.mReaders.length; i++) {
            this.mReaders[i].setOnImageAvailableListenerWithExecutor(listener, executor);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        flush();
        for (int i = 0; i < this.mReaders.length; i++) {
            this.mReaders[i].close();
        }
    }

    protected void finalize() {
        close();
    }

    public void flush() {
        flushOther(null);
    }

    public void flushOther(ImageReader reader) {
        for (int i = 0; i < this.mReaders.length; i++) {
            if (reader == null || reader != this.mReaders[i]) {
                while (true) {
                    Image image = this.mReaders[i].acquireNextImageNoThrowISE();
                    if (image == null) {
                        break;
                    } else {
                        image.close();
                    }
                }
            }
        }
    }

    public ImageReader[] getReaders() {
        return this.mReaders;
    }

    public Surface getSurface(Size configuredSize, String physicalCameraId) {
        Preconditions.checkNotNull(configuredSize, "configuredSize must not be null");
        Preconditions.checkNotNull(physicalCameraId, "physicalCameraId must not be null");
        for (int i = 0; i < this.mStreamInfo.length; i++) {
            if (this.mStreamInfo[i].getWidth() == configuredSize.getWidth() && this.mStreamInfo[i].getHeight() == configuredSize.getHeight() && physicalCameraId.equals(this.mStreamInfo[i].getPhysicalCameraId())) {
                return this.mReaders[i].getSurface();
            }
        }
        throw new IllegalArgumentException("configuredSize and physicalCameraId don't match with this MultiResolutionImageReader");
    }

    public Surface getSurface() {
        int minReaderSize = this.mReaders[0].getWidth() * this.mReaders[0].getHeight();
        Surface candidateSurface = this.mReaders[0].getSurface();
        for (int i = 1; i < this.mReaders.length; i++) {
            int readerSize = this.mReaders[i].getWidth() * this.mReaders[i].getHeight();
            if (readerSize < minReaderSize) {
                minReaderSize = readerSize;
                candidateSurface = this.mReaders[i].getSurface();
            }
        }
        return candidateSurface;
    }

    public MultiResolutionStreamInfo getStreamInfoForImageReader(ImageReader reader) {
        for (int i = 0; i < this.mReaders.length; i++) {
            if (reader == this.mReaders[i]) {
                return this.mStreamInfo[i];
            }
        }
        throw new IllegalArgumentException("ImageReader doesn't belong to this multi-resolution imagereader");
    }
}
