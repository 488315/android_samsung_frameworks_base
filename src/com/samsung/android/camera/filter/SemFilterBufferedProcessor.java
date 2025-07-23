package com.samsung.android.camera.filter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.samsung.android.camera.filter.SemFilterManager;
import java.io.File;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
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

    protected void setInitialized(boolean z) {
        this.isInitialized = z;
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
        SemFilterManager.SemFilterImpl semFilterImpl2 = this.mSemFilterImpl;
        if (semFilterImpl2 == null) {
            this.mSemFilterImpl = semFilterImpl;
        } else if (semFilterImpl2.getFilterIdentifier().equals(semFilterImpl.getFilterIdentifier())) {
            return;
        } else {
            this.mSemFilterImpl = semFilterImpl;
        }
        if (semFilterImpl.getFilterIdentifierIdx() != -1) {
            native_setEffect_internal(semFilterImpl.getFilterIdentifierIdx());
        } else {
            native_setEffect(this.mSemFilterImpl.getFilterIdentifier());
        }
    }

    public void setFilterParameter(String str) {
        checkInitialized();
        if (str == null) {
            throw new IllegalArgumentException("parameter must not null");
        }
        native_setEffect_parameter(str);
    }

    public Bitmap processImage(Bitmap bitmap, boolean z) {
        checkInitialized();
        if (bitmap == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (bitmap.getWidth() < 1 || bitmap.getHeight() < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight())));
        }
        if (bitmap.getWidth() > 8192 || bitmap.getHeight() > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()), 8192, 8192));
        }
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            if (copy != null) {
                native_process_bitmap_overwrite(copy, z);
                copy.recycle();
            }
            return null;
        }
        Object native_process_bitmap_overwrite = native_process_bitmap_overwrite(bitmap, z);
        if (native_process_bitmap_overwrite != null) {
            return (Bitmap) native_process_bitmap_overwrite;
        }
        return null;
    }

    @Deprecated(forRemoval = true, since = "15.5")
    public int[] processImage(int[] iArr, int i, int i2) {
        checkInitialized();
        if (iArr == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(i), Integer.valueOf(i2)));
        }
        if (i > 8192 || i2 > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(i), Integer.valueOf(i2), 8192, 8192));
        }
        if (iArr.length < i * i2) {
            throw new IllegalArgumentException("Image Buffer Size is not valid.");
        }
        return native_process_buffer(iArr, i, i2);
    }

    @Deprecated(forRemoval = true, since = "15.5")
    public void processImage(String str, String str2) {
        checkInitialized();
        if (str == null) {
            throw new IllegalArgumentException("inputFileName must not null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("outputFileName must not null");
        }
        if (!checkInputFilePermission(str)) {
            throw new IllegalArgumentException("input file does not exist.");
        }
        if (!checkOutputFilePermission(str2)) {
            throw new IllegalArgumentException("output file is invalid.");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        if (options.outWidth < 1 || options.outHeight < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight)));
        }
        if (options.outWidth > 8192 || options.outHeight > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight), 8192, 8192));
        }
        native_process_file(str, str2);
    }

    public byte[] processImage(byte[] bArr, int i, int i2, int i3) {
        checkInitialized();
        if (bArr == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(i), Integer.valueOf(i2)));
        }
        if (i > 8192 || i2 > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(i), Integer.valueOf(i2), 8192, 8192));
        }
        if (i3 == 0) {
            if (bArr.length < i * i2 * 4) {
                throw new IllegalArgumentException("Image Buffer Size is not valid.");
            }
        } else if (i3 == 1) {
            if (bArr.length < (i * i2) + (((i + 1) / 2) * ((i2 + 1) / 2) * 2)) {
                throw new IllegalArgumentException("Image Buffer Size is not valid.");
            }
        } else {
            throw new IllegalArgumentException("Image Format is not valid.");
        }
        return native_process_array(bArr, i, i2, i3);
    }

    public byte[] processImage(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        checkInitialized();
        if (bArr == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(i), Integer.valueOf(i2)));
        }
        if (i > 8192 || i2 > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(i), Integer.valueOf(i2), 8192, 8192));
        }
        if (i4 < i) {
            throw new IllegalArgumentException(String.format("Image having stride (stride=%d) lesser than width (width=%d) is not valid.", Integer.valueOf(i4), Integer.valueOf(i)));
        }
        if (i5 < i2) {
            throw new IllegalArgumentException(String.format("Image having sliceHeight (sliceHeight=%d) lesser than height (height=%d) is not valid.", Integer.valueOf(i5), Integer.valueOf(i2)));
        }
        if (i4 < 1 || i5 < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(i4), Integer.valueOf(i5)));
        }
        if (i4 > 8192 || i5 > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is greater thant the %dx%d", Integer.valueOf(i4), Integer.valueOf(i5), 8192, 8192));
        }
        if (i3 == 0) {
            if (bArr.length < i * i2 * 4) {
                throw new IllegalArgumentException("Image Buffer Size is not valid.");
            }
        } else if (i3 == 1) {
            if (bArr.length < (i4 * i5) + (((i4 + 1) / 2) * ((i2 + 1) / 2) * 2)) {
                throw new IllegalArgumentException("Image Buffer Size is not valid.");
            }
        } else {
            throw new IllegalArgumentException("Image Format is not valid.");
        }
        return native_process_array_stride(bArr, i, i2, i3, i4, i5);
    }

    public byte[] processImage(byte[] bArr, int i, int i2, int i3, int i4, int i5, boolean z) {
        checkInitialized();
        if (bArr == null) {
            throw new IllegalArgumentException("data must not null");
        }
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(i), Integer.valueOf(i2)));
        }
        if (i > 8192 || i2 > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is is greater than the %dx%d", Integer.valueOf(i), Integer.valueOf(i2), 8192, 8192));
        }
        if (i4 < i) {
            throw new IllegalArgumentException(String.format("Image having stride (stride=%d) lesser than width (width=%d) is not valid.", Integer.valueOf(i4), Integer.valueOf(i)));
        }
        if (i5 < i2) {
            throw new IllegalArgumentException(String.format("Image having sliceHeight (sliceHeight=%d) lesser than height (height=%d) is not valid.", Integer.valueOf(i5), Integer.valueOf(i2)));
        }
        if (i4 < 1 || i5 < 1) {
            throw new IllegalArgumentException(String.format("Image with size (w=%d, h=%d) is not valid.", Integer.valueOf(i4), Integer.valueOf(i5)));
        }
        if (i4 > 8192 || i5 > 8192) {
            throw new IllegalArgumentException(String.format("Image resolution(w=%d, h=%d) is greater thant the %dx%d", Integer.valueOf(i4), Integer.valueOf(i5), 8192, 8192));
        }
        if (i3 == 0) {
            if (bArr.length < i * i2 * 4) {
                throw new IllegalArgumentException("Image Buffer Size is not valid.");
            }
        } else if (i3 == 1) {
            if (bArr.length < (i4 * i5) + (((i4 + 1) / 2) * ((i2 + 1) / 2) * 2)) {
                throw new IllegalArgumentException("Image Buffer Size is not valid.");
            }
        } else {
            throw new IllegalArgumentException("Image Format is not valid.");
        }
        return native_process_array_stride_overwrite(bArr, i, i2, i3, i4, i5, z);
    }

    private boolean checkInputFilePermission(String str) {
        return str != null && str.length() >= 1 && new File(str).exists();
    }

    private boolean checkOutputFilePermission(String str) {
        int lastIndexOf;
        if (str == null || str.length() < 1 || (lastIndexOf = str.lastIndexOf("/")) < 0) {
            return false;
        }
        if (!str.toLowerCase().endsWith(".jpeg") && !str.toLowerCase().endsWith(".jpg")) {
            return false;
        }
        File file = new File(str.substring(0, lastIndexOf));
        return file.exists() && file.isDirectory() && file.canWrite();
    }
}
