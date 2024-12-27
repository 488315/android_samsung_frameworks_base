package com.samsung.android.camera.filter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.samsung.android.camera.filter.SemFilterManager;
import java.io.File;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public final class SemFilterBufferedProcessor {
    public static final int IMAGE_FORMAT_RGBA_8888 = 0;
    public static final int IMAGE_FORMAT_YUV_420_888 = 1;
    private static final int MAX_IMAGE_SIZE = 8192;
    private static final String TAG = "SemFilterBufferedProcessor";
    private long mNativeContext;
    private boolean isInitialized = false;
    private SemFilterManager.SemFilterImpl mSemFilterImpl = null;

    private static final native void native_init();

    private native void native_initialize();

    private native byte[] native_process_array(byte[] bArr, int i, int i2, int i3);

    private native byte[] native_process_array_stride(byte[] bArr, int i, int i2, int i3, int i4, int i5);

    private native byte[] native_process_array_stride_overwrite(byte[] bArr, int i, int i2, int i3, int i4, int i5, boolean z);

    private native Object native_process_bitmap(Object obj);

    private native Object native_process_bitmap_overwrite(Object obj, boolean z);

    private native int[] native_process_buffer(int[] iArr, int i, int i2);

    private native void native_process_file(String str, String str2);

    private native void native_release();

    private native void native_setEffect(String str);

    private native void native_setEffect_internal(int i);

    private native void native_setEffect_parameter(String str);

    private final native void native_setup(Object obj) throws IllegalStateException;

    static {
        System.loadLibrary("secimaging.camera.samsung");
        native_init();
    }

    public SemFilterBufferedProcessor() {
        native_setup(new WeakReference(this));
    }

    protected void setInitialized(boolean value) {
        this.isInitialized = value;
    }

    protected void checkInitialized() {
        if (!this.isInitialized) {
            throw new IllegalStateException("SCameraFilterContext is not initialized.");
        }
    }

    protected void checkNotInitialized() {
        if (this.isInitialized) {
            throw new IllegalStateException("SCameraFilterContext is already initialized.");
        }
    }

    protected boolean isInitialized() {
        return this.isInitialized;
    }

    public void initialize() {
        checkNotInitialized();
        native_initialize();
        setInitialized(true);
    }

    public void release() {
        checkInitialized();
        native_release();
        this.mSemFilterImpl = null;
        setInitialized(false);
    }

    public void setFilter(SemFilter semFilter) {
        checkInitialized();
        if (semFilter == null) {
            throw new IllegalArgumentException("semFilter must not null");
        }
        SemFilterManager.SemFilterImpl semFilterImpl = (SemFilterManager.SemFilterImpl) semFilter;
        boolean isEffectChanged = false;
        if (this.mSemFilterImpl == null) {
            this.mSemFilterImpl = semFilterImpl;
            isEffectChanged = true;
        } else if (!this.mSemFilterImpl.getFilterIdentifier().equals(semFilterImpl.getFilterIdentifier())) {
            this.mSemFilterImpl = semFilterImpl;
            isEffectChanged = true;
        }
        if (isEffectChanged) {
            if (semFilterImpl.getFilterIdentifierIdx() != -1) {
                native_setEffect_internal(semFilterImpl.getFilterIdentifierIdx());
            } else {
                native_setEffect(this.mSemFilterImpl.getFilterIdentifier());
            }
        }
    }

    public void setFilterParameter(String parameter) {
        checkInitialized();
        if (parameter == null) {
            throw new IllegalArgumentException("parameter must not null");
        }
        native_setEffect_parameter(parameter);
    }

    public Bitmap processImage(Bitmap data) {
        checkInitialized();
        if (data == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (data.getWidth() < 1 || data.getHeight() < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(data.getWidth()), Integer.valueOf(data.getHeight())));
        }
        if (data.getWidth() > 8192 || data.getHeight() > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(data.getWidth()), Integer.valueOf(data.getHeight()), 8192, 8192));
        }
        if (data.getConfig() != Bitmap.Config.ARGB_8888) {
            Bitmap data_ARGB888 = data.copy(Bitmap.Config.ARGB_8888, true);
            if (data_ARGB888 != null) {
                Bitmap data_result = (Bitmap) native_process_bitmap(data_ARGB888);
                data_ARGB888.recycle();
                return data_result;
            }
            return null;
        }
        return (Bitmap) native_process_bitmap(data);
    }

    public Bitmap processImage(Bitmap data, boolean overwrite) {
        checkInitialized();
        if (data == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (data.getWidth() < 1 || data.getHeight() < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(data.getWidth()), Integer.valueOf(data.getHeight())));
        }
        if (data.getWidth() > 8192 || data.getHeight() > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(data.getWidth()), Integer.valueOf(data.getHeight()), 8192, 8192));
        }
        if (data.getConfig() != Bitmap.Config.ARGB_8888) {
            Bitmap data_ARGB888 = data.copy(Bitmap.Config.ARGB_8888, true);
            if (data_ARGB888 == null) {
                return null;
            }
            native_process_bitmap_overwrite(data_ARGB888, overwrite);
            data_ARGB888.recycle();
            return null;
        }
        Object bitmap = native_process_bitmap_overwrite(data, overwrite);
        if (bitmap != null) {
            return (Bitmap) bitmap;
        }
        return null;
    }

    @Deprecated(forRemoval = true, since = "15.5")
    public int[] processImage(int[] data, int width, int height) {
        checkInitialized();
        if (data == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(width), Integer.valueOf(height)));
        }
        if (width > 8192 || height > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(width), Integer.valueOf(height), 8192, 8192));
        }
        int expectedRGBABufferSize = width * height;
        if (data.length < expectedRGBABufferSize) {
            throw new IllegalArgumentException("Image Buffer Size is not valid.");
        }
        return native_process_buffer(data, width, height);
    }

    @Deprecated(forRemoval = true, since = "15.5")
    public void processImage(String inputFileName, String outputFileName) {
        checkInitialized();
        if (inputFileName == null) {
            throw new IllegalArgumentException("inputFileName must not null");
        }
        if (outputFileName == null) {
            throw new IllegalArgumentException("outputFileName must not null");
        }
        if (!checkInputFilePermission(inputFileName)) {
            throw new IllegalArgumentException("input file does not exist.");
        }
        if (!checkOutputFilePermission(outputFileName)) {
            throw new IllegalArgumentException("output file is invalid.");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(inputFileName, options);
        if (options.outWidth < 1 || options.outHeight < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight)));
        }
        if (options.outWidth > 8192 || options.outHeight > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight), 8192, 8192));
        }
        native_process_file(inputFileName, outputFileName);
    }

    public byte[] processImage(byte[] data, int width, int height, int imageFormat) {
        checkInitialized();
        if (data == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(width), Integer.valueOf(height)));
        }
        if (width > 8192 || height > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(width), Integer.valueOf(height), 8192, 8192));
        }
        switch (imageFormat) {
            case 0:
                int ySize = width * height;
                int expectedRGBABufferSize = ySize * 4;
                if (data.length < expectedRGBABufferSize) {
                    throw new IllegalArgumentException("Image Buffer Size is not valid.");
                }
                break;
            case 1:
                int ySize2 = width * height;
                int uvSize = (width / 2) * (height / 2) * 2;
                int expectedYUVBufferSize = ySize2 + uvSize;
                if (data.length < expectedYUVBufferSize) {
                    throw new IllegalArgumentException("Image Buffer Size is not valid.");
                }
                break;
            default:
                throw new IllegalArgumentException("Image Format is not valid.");
        }
        return native_process_array(data, width, height, imageFormat);
    }

    public byte[] processImage(byte[] data, int width, int height, int imageFormat, int stride, int sliceHeight) {
        checkInitialized();
        if (data == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(width), Integer.valueOf(height)));
        }
        if (width > 8192 || height > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(width), Integer.valueOf(height), 8192, 8192));
        }
        if (stride < width) {
            throw new IllegalArgumentException(String.format("Image having stride (stride=%d) lesser than width (width=%d) is not valid.", Integer.valueOf(stride), Integer.valueOf(width)));
        }
        if (sliceHeight < height) {
            throw new IllegalArgumentException(String.format("Image having sliceHeight (sliceHeight=%d) lesser than height (height=%d) is not valid.", Integer.valueOf(sliceHeight), Integer.valueOf(height)));
        }
        switch (imageFormat) {
            case 0:
                int ySize = width * height;
                int expectedRGBABufferSize = ySize * 4;
                if (data.length < expectedRGBABufferSize) {
                    throw new IllegalArgumentException("Image Buffer Size is not valid.");
                }
                break;
            case 1:
                int ySize2 = stride * sliceHeight;
                int uvStride = stride / 2;
                int uvSliceHeight = height / 2;
                int uvSize = uvStride * uvSliceHeight * 2;
                int expectedYUVBufferSize = ySize2 + uvSize;
                if (data.length < expectedYUVBufferSize) {
                    throw new IllegalArgumentException("Image Buffer Size is not valid.");
                }
                break;
            default:
                throw new IllegalArgumentException("Image Format is not valid.");
        }
        return native_process_array_stride(data, width, height, imageFormat, stride, sliceHeight);
    }

    public byte[] processImage(byte[] data, int width, int height, int imageFormat, int stride, int sliceHeight, boolean overwrite) {
        checkInitialized();
        if (data == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(width), Integer.valueOf(height)));
        }
        if (width > 8192 || height > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(width), Integer.valueOf(height), 8192, 8192));
        }
        if (stride < width) {
            throw new IllegalArgumentException(String.format("Image having stride (stride=%d) lesser than width (width=%d) is not valid.", Integer.valueOf(stride), Integer.valueOf(width)));
        }
        if (sliceHeight < height) {
            throw new IllegalArgumentException(String.format("Image having sliceHeight (sliceHeight=%d) lesser than height (height=%d) is not valid.", Integer.valueOf(sliceHeight), Integer.valueOf(height)));
        }
        switch (imageFormat) {
            case 0:
                int ySize = width * height;
                int expectedRGBABufferSize = ySize * 4;
                if (data.length < expectedRGBABufferSize) {
                    throw new IllegalArgumentException("Image Buffer Size is not valid.");
                }
                break;
            case 1:
                int ySize2 = stride * sliceHeight;
                int uvStride = stride / 2;
                int uvSliceHeight = height / 2;
                int uvSize = uvStride * uvSliceHeight * 2;
                int expectedYUVBufferSize = ySize2 + uvSize;
                if (data.length < expectedYUVBufferSize) {
                    throw new IllegalArgumentException("Image Buffer Size is not valid.");
                }
                break;
            default:
                throw new IllegalArgumentException("Image Format is not valid.");
        }
        return native_process_array_stride_overwrite(data, width, height, imageFormat, stride, sliceHeight, overwrite);
    }

    private boolean checkInputFilePermission(String filePath) {
        if (filePath == null || filePath.length() < 1) {
            return false;
        }
        File currentFile = new File(filePath);
        return currentFile.exists();
    }

    private boolean checkOutputFilePermission(String filePath) {
        int separatorPosition;
        if (filePath == null || filePath.length() < 1 || (separatorPosition = filePath.lastIndexOf("/")) < 0) {
            return false;
        }
        if (!filePath.toLowerCase().endsWith(".jpeg") && !filePath.toLowerCase().endsWith(".jpg")) {
            return false;
        }
        String parentPath = filePath.substring(0, separatorPosition);
        File parentPathFile = new File(parentPath);
        return parentPathFile.exists() && parentPathFile.isDirectory() && parentPathFile.canWrite();
    }
}
