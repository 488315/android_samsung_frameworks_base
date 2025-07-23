package android.graphics;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Size;
import android.util.TypedValue;
import com.google.android.mms.ContentType;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class ImageDecoder implements AutoCloseable {
    public static final int ALLOCATOR_DEFAULT = 0;
    public static final int ALLOCATOR_HARDWARE = 3;
    public static final int ALLOCATOR_SHARED_MEMORY = 2;
    public static final int ALLOCATOR_SOFTWARE = 1;
    public static final int MEMORY_POLICY_DEFAULT = 1;
    public static final int MEMORY_POLICY_LOW_RAM = 0;
    private static boolean sIsHevcDecoderSupported = false;
    private static boolean sIsHevcDecoderSupportedInitialized = false;
    private static boolean sIsP010SupportedFlagsInitialized = false;
    private static boolean sIsP010SupportedForAV1 = false;
    private static boolean sIsP010SupportedForHEVC = false;
    private final boolean mAnimated;
    private AssetFileDescriptor mAssetFd;
    private final CloseGuard mCloseGuard;
    private Rect mCropRect;
    private int mDesiredHeight;
    private int mDesiredWidth;
    private final int mHeight;
    private InputStream mInputStream;
    private final boolean mIsNinePatch;
    private long mNativePtr;
    private OnPartialImageListener mOnPartialImageListener;
    private Rect mOutPaddingRect;
    private boolean mOwnsInputStream;
    private PostProcessor mPostProcessor;
    private Source mSource;
    private byte[] mTempStorage;
    private final int mWidth;
    private static final Object sIsHevcDecoderSupportedLock = new Object();
    private static final Object sIsP010SupportedLock = new Object();
    private int mAllocator = 0;
    private boolean mUnpremultipliedRequired = false;
    private boolean mMutable = false;
    private boolean mConserveMemory = false;
    private boolean mDecodeAsAlphaMask = false;
    private ColorSpace mDesiredColorSpace = null;
    private final AtomicBoolean mClosed = new AtomicBoolean();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Allocator {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MemoryPolicy {
    }

    public interface OnHeaderDecodedListener {
        void onHeaderDecoded(ImageDecoder imageDecoder, ImageInfo imageInfo, Source source);
    }

    public interface OnPartialImageListener {
        boolean onPartialImage(DecodeException decodeException);
    }

    private static native void nClose(long j);

    private static native ImageDecoder nCreate(long j, boolean z, Source source) throws IOException;

    private static native ImageDecoder nCreate(FileDescriptor fileDescriptor, long j, boolean z, Source source) throws IOException;

    private static native ImageDecoder nCreate(InputStream inputStream, byte[] bArr, boolean z, Source source) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native ImageDecoder nCreate(ByteBuffer byteBuffer, int i, int i2, boolean z, Source source) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native ImageDecoder nCreate(byte[] bArr, int i, int i2, boolean z, Source source) throws IOException;

    private static native Bitmap nDecodeBitmap(long j, ImageDecoder imageDecoder, boolean z, int i, int i2, Rect rect, boolean z2, int i3, boolean z3, boolean z4, boolean z5, long j2, boolean z6) throws IOException;

    private static native ColorSpace nGetColorSpace(long j);

    private static native String nGetMimeType(long j);

    private static native void nGetPadding(long j, Rect rect);

    private static native Size nGetSampledSize(long j, int i);

    public static abstract class Source {
        abstract ImageDecoder createImageDecoder(boolean z) throws IOException;

        int getDensity() {
            return 0;
        }

        Resources getResources() {
            return null;
        }

        private Source() {
        }

        final int computeDstDensity() {
            Resources resources = getResources();
            if (resources == null) {
                return Bitmap.getDefaultDensity();
            }
            return resources.getDisplayMetrics().densityDpi;
        }
    }

    private static class ByteArraySource extends Source {
        private final byte[] mData;
        private final int mLength;
        private final int mOffset;

        ByteArraySource(byte[] bArr, int i, int i2) {
            super();
            this.mData = bArr;
            this.mOffset = i;
            this.mLength = i2;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            return ImageDecoder.nCreate(this.mData, this.mOffset, this.mLength, z, this);
        }

        public String toString() {
            return "ByteArraySource{len=" + this.mLength + "}";
        }
    }

    private static class ByteBufferSource extends Source {
        private final ByteBuffer mBuffer;
        private final int mLength;

        ByteBufferSource(ByteBuffer byteBuffer) {
            super();
            this.mBuffer = byteBuffer;
            this.mLength = byteBuffer.limit() - byteBuffer.position();
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            if (!this.mBuffer.isDirect() && this.mBuffer.hasArray()) {
                return ImageDecoder.nCreate(this.mBuffer.array(), this.mBuffer.arrayOffset() + this.mBuffer.position(), this.mBuffer.limit() - this.mBuffer.position(), z, this);
            }
            ByteBuffer slice = this.mBuffer.slice();
            return ImageDecoder.nCreate(slice, slice.position(), slice.limit(), z, this);
        }

        public String toString() {
            return "ByteBufferSource{len=" + this.mLength + "}";
        }
    }

    private static class ContentResolverSource extends Source {
        private final ContentResolver mResolver;
        private final Resources mResources;
        private final Uri mUri;

        ContentResolverSource(ContentResolver contentResolver, Uri uri, Resources resources) {
            super();
            this.mResolver = contentResolver;
            this.mUri = uri;
            this.mResources = resources;
        }

        @Override // android.graphics.ImageDecoder.Source
        Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            AssetFileDescriptor assetFileDescriptor = null;
            try {
                if ("content".equals(this.mUri.getScheme())) {
                    assetFileDescriptor = this.mResolver.openTypedAssetFileDescriptor(this.mUri, ContentType.IMAGE_UNSPECIFIED, null);
                } else {
                    assetFileDescriptor = this.mResolver.openAssetFileDescriptor(this.mUri, "r");
                }
            } catch (FileNotFoundException unused) {
            }
            if (assetFileDescriptor == null) {
                InputStream openInputStream = this.mResolver.openInputStream(this.mUri);
                if (openInputStream == null) {
                    throw new FileNotFoundException(this.mUri.toString());
                }
                return ImageDecoder.createFromStream(openInputStream, true, z, this);
            }
            return ImageDecoder.createFromAssetFileDescriptor(assetFileDescriptor, z, this);
        }

        public String toString() {
            String uri = this.mUri.toString();
            if (uri.length() > 90) {
                uri = uri.substring(0, 80) + ".." + uri.substring(uri.length() - 10);
            }
            return "ContentResolverSource{uri=" + uri + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImageDecoder createFromFile(File file, boolean z, Source source) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileDescriptor fd = fileInputStream.getFD();
        try {
            Os.lseek(fd, 0L, OsConstants.SEEK_CUR);
            try {
                ImageDecoder nCreate = nCreate(fd, -1L, z, source);
                if (nCreate == null) {
                    return nCreate;
                }
                nCreate.mInputStream = fileInputStream;
                nCreate.mOwnsInputStream = true;
                return nCreate;
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        } catch (ErrnoException unused) {
            return createFromStream(fileInputStream, true, z, source);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImageDecoder createFromStream(InputStream inputStream, boolean z, boolean z2, Source source) throws IOException {
        byte[] bArr = new byte[16384];
        try {
            ImageDecoder nCreate = nCreate(inputStream, bArr, z2, source);
            if (nCreate == null) {
                return nCreate;
            }
            nCreate.mInputStream = inputStream;
            nCreate.mOwnsInputStream = z;
            nCreate.mTempStorage = bArr;
            return nCreate;
        } finally {
            if (z) {
                IoUtils.closeQuietly(inputStream);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImageDecoder createFromAssetFileDescriptor(AssetFileDescriptor assetFileDescriptor, boolean z, Source source) throws IOException {
        ImageDecoder createFromStream;
        if (assetFileDescriptor == null) {
            throw new FileNotFoundException();
        }
        FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
        try {
            try {
                Os.lseek(fileDescriptor, assetFileDescriptor.getStartOffset(), OsConstants.SEEK_SET);
                createFromStream = nCreate(fileDescriptor, assetFileDescriptor.getDeclaredLength(), z, source);
            } catch (ErrnoException unused) {
                createFromStream = createFromStream(new FileInputStream(fileDescriptor), true, z, source);
            }
            if (createFromStream != null) {
                createFromStream.mAssetFd = assetFileDescriptor;
            }
            return createFromStream;
        } finally {
            IoUtils.closeQuietly(assetFileDescriptor);
        }
    }

    private static class InputStreamSource extends Source {
        final int mInputDensity;
        InputStream mInputStream;
        final Resources mResources;

        InputStreamSource(Resources resources, InputStream inputStream, int i) {
            super();
            if (inputStream == null) {
                throw new IllegalArgumentException("The InputStream cannot be null");
            }
            this.mResources = resources;
            this.mInputStream = inputStream;
            this.mInputDensity = i;
        }

        @Override // android.graphics.ImageDecoder.Source
        public Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public int getDensity() {
            return this.mInputDensity;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            ImageDecoder createFromStream;
            synchronized (this) {
                InputStream inputStream = this.mInputStream;
                if (inputStream == null) {
                    throw new IOException("Cannot reuse InputStreamSource");
                }
                this.mInputStream = null;
                createFromStream = ImageDecoder.createFromStream(inputStream, false, z, this);
            }
            return createFromStream;
        }

        public String toString() {
            return "InputStream{s=" + this.mInputStream + "}";
        }
    }

    public static class AssetInputStreamSource extends Source {
        private AssetManager.AssetInputStream mAssetInputStream;
        private final int mDensity;
        private final Resources mResources;

        public AssetInputStreamSource(AssetManager.AssetInputStream assetInputStream, Resources resources, TypedValue typedValue) {
            super();
            this.mAssetInputStream = assetInputStream;
            this.mResources = resources;
            if (typedValue.density == 0) {
                this.mDensity = 160;
            } else if (typedValue.density != 65535) {
                this.mDensity = typedValue.density;
            } else {
                this.mDensity = 0;
            }
        }

        @Override // android.graphics.ImageDecoder.Source
        public Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public int getDensity() {
            return this.mDensity;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            ImageDecoder createFromAsset;
            synchronized (this) {
                AssetManager.AssetInputStream assetInputStream = this.mAssetInputStream;
                if (assetInputStream == null) {
                    throw new IOException("Cannot reuse AssetInputStreamSource");
                }
                this.mAssetInputStream = null;
                createFromAsset = ImageDecoder.createFromAsset(assetInputStream, z, this);
            }
            return createFromAsset;
        }

        public String toString() {
            return "AssetInputStream{s=" + this.mAssetInputStream + "}";
        }
    }

    private static class ResourceSource extends Source {
        private Object mLock;
        int mResDensity;
        final int mResId;
        final Resources mResources;

        ResourceSource(Resources resources, int i) {
            super();
            this.mLock = new Object();
            this.mResources = resources;
            this.mResId = i;
            this.mResDensity = 0;
        }

        @Override // android.graphics.ImageDecoder.Source
        public Resources getResources() {
            return this.mResources;
        }

        @Override // android.graphics.ImageDecoder.Source
        public int getDensity() {
            int i;
            synchronized (this.mLock) {
                i = this.mResDensity;
            }
            return i;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            TypedValue typedValue = new TypedValue();
            InputStream openRawResource = this.mResources.openRawResource(this.mResId, typedValue);
            synchronized (this.mLock) {
                if (typedValue.density == 0) {
                    this.mResDensity = 160;
                } else if (typedValue.density != 65535) {
                    this.mResDensity = typedValue.density;
                }
            }
            return ImageDecoder.createFromAsset((AssetManager.AssetInputStream) openRawResource, z, this);
        }

        public String toString() {
            try {
                return "Resource{name=" + this.mResources.getResourceName(this.mResId) + "}";
            } catch (Resources.NotFoundException unused) {
                return "Resource{id=" + this.mResId + "}";
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImageDecoder createFromAsset(AssetManager.AssetInputStream assetInputStream, boolean z, Source source) throws IOException {
        try {
            ImageDecoder nCreate = nCreate(assetInputStream.getNativeAsset(), z, source);
            if (nCreate == null) {
                return nCreate;
            }
            nCreate.mInputStream = assetInputStream;
            nCreate.mOwnsInputStream = true;
            return nCreate;
        } finally {
            IoUtils.closeQuietly(assetInputStream);
        }
    }

    private static class AssetSource extends Source {
        private final AssetManager mAssets;
        private final String mFileName;

        AssetSource(AssetManager assetManager, String str) {
            super();
            this.mAssets = assetManager;
            this.mFileName = str;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            return ImageDecoder.createFromAsset((AssetManager.AssetInputStream) this.mAssets.open(this.mFileName), z, this);
        }

        public String toString() {
            return "AssetSource{file=" + this.mFileName + "}";
        }
    }

    private static class FileSource extends Source {
        private final File mFile;

        FileSource(File file) {
            super();
            this.mFile = file;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            return ImageDecoder.createFromFile(this.mFile, z, this);
        }

        public String toString() {
            return "FileSource{file=" + this.mFile + "}";
        }
    }

    private static class CallableSource extends Source {
        private final Callable<AssetFileDescriptor> mCallable;

        CallableSource(Callable<AssetFileDescriptor> callable) {
            super();
            this.mCallable = callable;
        }

        @Override // android.graphics.ImageDecoder.Source
        public ImageDecoder createImageDecoder(boolean z) throws IOException {
            try {
                return ImageDecoder.createFromAssetFileDescriptor(this.mCallable.call(), z, this);
            } catch (Exception e) {
                if (e instanceof IOException) {
                    throw ((IOException) e);
                }
                throw new IOException(e);
            }
        }

        public String toString() {
            return "CallableSource{obj=" + this.mCallable.toString() + "}";
        }
    }

    public static class ImageInfo {
        private final ColorSpace mColorSpace;
        private final boolean mIsAnimated;
        private final String mMimeType;
        private final Size mSize;

        private ImageInfo(Size size, boolean z, String str, ColorSpace colorSpace) {
            this.mSize = size;
            this.mIsAnimated = z;
            this.mMimeType = str;
            this.mColorSpace = colorSpace;
        }

        public Size getSize() {
            return this.mSize;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public boolean isAnimated() {
            return this.mIsAnimated;
        }

        public ColorSpace getColorSpace() {
            return this.mColorSpace;
        }
    }

    public static final class DecodeException extends IOException {
        public static final int SOURCE_EXCEPTION = 1;
        public static final int SOURCE_INCOMPLETE = 2;
        public static final int SOURCE_MALFORMED_DATA = 3;
        final int mError;
        final Source mSource;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Error {
        }

        DecodeException(int i, Throwable th, Source source) {
            super(errorMessage(i, th), th);
            this.mError = i;
            this.mSource = source;
        }

        DecodeException(int i, String str, Throwable th, Source source) {
            super(str + errorMessage(i, th), th);
            this.mError = i;
            this.mSource = source;
        }

        public int getError() {
            return this.mError;
        }

        public Source getSource() {
            return this.mSource;
        }

        private static String errorMessage(int i, Throwable th) {
            if (i == 1) {
                return "Exception in input: " + th;
            }
            if (i == 2) {
                return "Input was incomplete.";
            }
            if (i == 3) {
                return "Input contained an error.";
            }
            return "";
        }
    }

    private ImageDecoder(long j, int i, int i2, boolean z, boolean z2) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mNativePtr = j;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDesiredWidth = i;
        this.mDesiredHeight = i2;
        this.mAnimated = z;
        this.mIsNinePatch = z2;
        closeGuard.open("close");
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            this.mInputStream = null;
            this.mAssetFd = null;
            close();
        } finally {
            super.finalize();
        }
    }

    public static boolean isMimeTypeSupported(String str) {
        Objects.requireNonNull(str);
        String lowerCase = str.toLowerCase(Locale.US);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "image/x-fuji-raf":
            case "image/x-samsung-srw":
            case "image/x-sony-arw":
            case "image/jpeg":
            case "image/webp":
            case "image/x-adobe-dng":
            case "image/x-panasonic-rw2":
            case "image/bmp":
            case "image/gif":
            case "image/png":
            case "image/x-pentax-pef":
            case "image/vnd.wap.wbmp":
            case "image/x-ico":
            case "image/x-olympus-orf":
            case "image/x-nikon-nef":
            case "image/x-nikon-nrw":
            case "image/x-canon-cr2":
                return true;
            case "image/avif":
                return isP010SupportedForAV1();
            case "image/heic":
            case "image/heif":
                return isHevcDecoderSupported();
            default:
                return false;
        }
    }

    public static Source createSource(Resources resources, int i) {
        return new ResourceSource(resources, i);
    }

    public static Source createSource(ContentResolver contentResolver, Uri uri) {
        return new ContentResolverSource(contentResolver, uri, null);
    }

    public static Source createSource(ContentResolver contentResolver, Uri uri, Resources resources) {
        return new ContentResolverSource(contentResolver, uri, resources);
    }

    public static Source createSource(AssetManager assetManager, String str) {
        return new AssetSource(assetManager, str);
    }

    public static Source createSource(byte[] bArr, int i, int i2) throws ArrayIndexOutOfBoundsException {
        if (bArr == null) {
            throw new NullPointerException("null byte[] in createSource!");
        }
        if (i < 0 || i2 < 0 || i >= bArr.length || i + i2 > bArr.length) {
            throw new ArrayIndexOutOfBoundsException("invalid offset/length!");
        }
        return new ByteArraySource(bArr, i, i2);
    }

    public static Source createSource(byte[] bArr) {
        return createSource(bArr, 0, bArr.length);
    }

    public static Source createSource(ByteBuffer byteBuffer) {
        return new ByteBufferSource(byteBuffer);
    }

    public static Source createSource(Resources resources, InputStream inputStream) {
        return new InputStreamSource(resources, inputStream, Bitmap.getDefaultDensity());
    }

    public static Source createSource(Resources resources, InputStream inputStream, int i) {
        return new InputStreamSource(resources, inputStream, i);
    }

    public static Source createSource(File file) {
        return new FileSource(file);
    }

    public static Source createSource(Callable<AssetFileDescriptor> callable) {
        return new CallableSource(callable);
    }

    private Size getSampledSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("sampleSize must be positive! provided " + i);
        }
        long j = this.mNativePtr;
        if (j == 0) {
            throw new IllegalStateException("ImageDecoder is closed!");
        }
        return nGetSampledSize(j, i);
    }

    public void setTargetSize(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive! provided (" + i + ", " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mDesiredWidth = i;
        this.mDesiredHeight = i2;
    }

    private int getTargetDimension(int i, int i2, int i3) {
        if (i2 >= i) {
            return 1;
        }
        int i4 = i / i2;
        return (i3 != i4 && Math.abs((i3 * i2) - i) >= i2) ? i4 : i3;
    }

    public void setTargetSampleSize(int i) {
        Size sampledSize = getSampledSize(i);
        setTargetSize(getTargetDimension(this.mWidth, i, sampledSize.getWidth()), getTargetDimension(this.mHeight, i, sampledSize.getHeight()));
    }

    private boolean requestedResize() {
        return (this.mWidth == this.mDesiredWidth && this.mHeight == this.mDesiredHeight) ? false : true;
    }

    public void setAllocator(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("invalid allocator " + i);
        }
        this.mAllocator = i;
    }

    public int getAllocator() {
        return this.mAllocator;
    }

    public void setUnpremultipliedRequired(boolean z) {
        this.mUnpremultipliedRequired = z;
    }

    public boolean isUnpremultipliedRequired() {
        return this.mUnpremultipliedRequired;
    }

    public void setPostProcessor(PostProcessor postProcessor) {
        this.mPostProcessor = postProcessor;
    }

    public PostProcessor getPostProcessor() {
        return this.mPostProcessor;
    }

    public void setOnPartialImageListener(OnPartialImageListener onPartialImageListener) {
        this.mOnPartialImageListener = onPartialImageListener;
    }

    public OnPartialImageListener getOnPartialImageListener() {
        return this.mOnPartialImageListener;
    }

    public void setCrop(Rect rect) {
        this.mCropRect = rect;
    }

    public Rect getCrop() {
        return this.mCropRect;
    }

    public void setOutPaddingRect(Rect rect) {
        this.mOutPaddingRect = rect;
    }

    public void setMutableRequired(boolean z) {
        this.mMutable = z;
    }

    public boolean isMutableRequired() {
        return this.mMutable;
    }

    public void setMemorySizePolicy(int i) {
        this.mConserveMemory = i == 0;
    }

    public int getMemorySizePolicy() {
        return !this.mConserveMemory ? 1 : 0;
    }

    public void setDecodeAsAlphaMaskEnabled(boolean z) {
        this.mDecodeAsAlphaMask = z;
    }

    public boolean isDecodeAsAlphaMaskEnabled() {
        return this.mDecodeAsAlphaMask;
    }

    public void setTargetColorSpace(ColorSpace colorSpace) {
        this.mDesiredColorSpace = colorSpace;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            nClose(this.mNativePtr);
            this.mNativePtr = 0L;
            if (this.mOwnsInputStream) {
                IoUtils.closeQuietly(this.mInputStream);
            }
            IoUtils.closeQuietly(this.mAssetFd);
            this.mInputStream = null;
            this.mAssetFd = null;
            this.mTempStorage = null;
        }
    }

    private void checkState(boolean z) {
        if (this.mNativePtr == 0) {
            throw new IllegalStateException("Cannot use closed ImageDecoder!");
        }
        checkSubset(this.mDesiredWidth, this.mDesiredHeight, this.mCropRect);
        if (!z && this.mAllocator == 3) {
            if (this.mMutable) {
                throw new IllegalStateException("Cannot make mutable HARDWARE Bitmap!");
            }
            if (this.mDecodeAsAlphaMask) {
                throw new IllegalStateException("Cannot make HARDWARE Alpha mask Bitmap!");
            }
        }
        if (this.mPostProcessor != null && this.mUnpremultipliedRequired) {
            throw new IllegalStateException("Cannot draw to unpremultiplied pixels!");
        }
    }

    private static void checkSubset(int i, int i2, Rect rect) {
        if (rect == null) {
            return;
        }
        if (rect.width() <= 0 || rect.height() <= 0) {
            throw new IllegalStateException("Subset " + rect + " is empty/unsorted");
        }
        if (rect.left < 0 || rect.top < 0 || rect.right > i || rect.bottom > i2) {
            throw new IllegalStateException("Subset " + rect + " not contained by scaled image bounds: (" + i + " x " + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private boolean checkForExtended() {
        ColorSpace colorSpace = this.mDesiredColorSpace;
        if (colorSpace == null) {
            return false;
        }
        return colorSpace == ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB) || this.mDesiredColorSpace == ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
    }

    private long getColorSpacePtr() {
        ColorSpace colorSpace = this.mDesiredColorSpace;
        if (colorSpace == null) {
            return 0L;
        }
        return colorSpace.getNativeInstance();
    }

    private Bitmap decodeBitmapInternal() throws IOException {
        checkState(false);
        boolean z = false;
        long j = this.mNativePtr;
        if (this.mPostProcessor != null) {
            z = true;
        }
        return nDecodeBitmap(j, this, z, this.mDesiredWidth, this.mDesiredHeight, this.mCropRect, this.mMutable, this.mAllocator, this.mUnpremultipliedRequired, this.mConserveMemory, this.mDecodeAsAlphaMask, getColorSpacePtr(), checkForExtended());
    }

    private void callHeaderDecoded(OnHeaderDecodedListener onHeaderDecodedListener, Source source) {
        if (onHeaderDecodedListener != null) {
            onHeaderDecodedListener.onHeaderDecoded(this, new ImageInfo(new Size(this.mWidth, this.mHeight), this.mAnimated, getMimeType(), getColorSpace()), source);
        }
    }

    public static ImageInfo decodeHeader(Source source) throws IOException {
        Trace.traceBegin(8192L, "ImageDecoder#decodeHeader");
        try {
            ImageDecoder createImageDecoder = source.createImageDecoder(true);
            try {
                ImageInfo imageInfo = new ImageInfo(new Size(createImageDecoder.mWidth, createImageDecoder.mHeight), createImageDecoder.mAnimated, createImageDecoder.getMimeType(), createImageDecoder.getColorSpace());
                if (createImageDecoder != null) {
                    createImageDecoder.close();
                }
                return imageInfo;
            } finally {
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable decodeDrawable(Source source, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        if (onHeaderDecodedListener == null) {
            throw new IllegalArgumentException("listener cannot be null! Use decodeDrawable(Source) to not have a listener");
        }
        return decodeDrawableImpl(source, onHeaderDecodedListener);
    }

    private static Drawable decodeDrawableImpl(Source source, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        Trace.traceBegin(8192L, "ImageDecoder#decodeDrawable");
        try {
            ImageDecoder createImageDecoder = source.createImageDecoder(true);
            try {
                createImageDecoder.mSource = source;
                createImageDecoder.callHeaderDecoded(onHeaderDecodedListener, source);
                ImageDecoderSourceTrace imageDecoderSourceTrace = new ImageDecoderSourceTrace(createImageDecoder);
                try {
                    if (createImageDecoder.mUnpremultipliedRequired) {
                        throw new IllegalStateException("Cannot decode a Drawable with unpremultiplied pixels!");
                    }
                    if (createImageDecoder.mMutable) {
                        throw new IllegalStateException("Cannot decode a mutable Drawable!");
                    }
                    int computeDensity = createImageDecoder.computeDensity(source);
                    if (createImageDecoder.mAnimated) {
                        ImageDecoder imageDecoder = createImageDecoder.mPostProcessor == null ? null : createImageDecoder;
                        createImageDecoder.checkState(true);
                        AnimatedImageDrawable animatedImageDrawable = new AnimatedImageDrawable(createImageDecoder.mNativePtr, imageDecoder, createImageDecoder.mDesiredWidth, createImageDecoder.mDesiredHeight, createImageDecoder.getColorSpacePtr(), createImageDecoder.checkForExtended(), computeDensity, source.computeDstDensity(), createImageDecoder.mCropRect, createImageDecoder.mInputStream, createImageDecoder.mAssetFd);
                        createImageDecoder.mInputStream = null;
                        createImageDecoder.mAssetFd = null;
                        imageDecoderSourceTrace.close();
                        if (createImageDecoder != null) {
                            createImageDecoder.close();
                        }
                        return animatedImageDrawable;
                    }
                    Bitmap decodeBitmapInternal = createImageDecoder.decodeBitmapInternal();
                    decodeBitmapInternal.setDensity(computeDensity);
                    Resources resources = source.getResources();
                    byte[] ninePatchChunk = decodeBitmapInternal.getNinePatchChunk();
                    if (ninePatchChunk == null || !NinePatch.isNinePatchChunk(ninePatchChunk)) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, decodeBitmapInternal);
                        imageDecoderSourceTrace.close();
                        if (createImageDecoder != null) {
                            createImageDecoder.close();
                        }
                        return bitmapDrawable;
                    }
                    Rect rect = new Rect();
                    decodeBitmapInternal.getOpticalInsets(rect);
                    Rect rect2 = createImageDecoder.mOutPaddingRect;
                    if (rect2 == null) {
                        rect2 = new Rect();
                    }
                    Rect rect3 = rect2;
                    nGetPadding(createImageDecoder.mNativePtr, rect3);
                    NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(resources, decodeBitmapInternal, ninePatchChunk, rect3, rect, null);
                    imageDecoderSourceTrace.close();
                    if (createImageDecoder != null) {
                        createImageDecoder.close();
                    }
                    return ninePatchDrawable;
                } finally {
                }
            } finally {
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable decodeDrawable(Source source) throws IOException {
        return decodeDrawableImpl(source, null);
    }

    public static Bitmap decodeBitmap(Source source, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        if (onHeaderDecodedListener == null) {
            throw new IllegalArgumentException("listener cannot be null! Use decodeBitmap(Source) to not have a listener");
        }
        return decodeBitmapImpl(source, onHeaderDecodedListener);
    }

    private static Bitmap decodeBitmapImpl(Source source, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        byte[] ninePatchChunk;
        Trace.traceBegin(8192L, "ImageDecoder#decodeBitmap");
        try {
            ImageDecoder createImageDecoder = source.createImageDecoder(false);
            try {
                createImageDecoder.mSource = source;
                createImageDecoder.callHeaderDecoded(onHeaderDecodedListener, source);
                ImageDecoderSourceTrace imageDecoderSourceTrace = new ImageDecoderSourceTrace(createImageDecoder);
                try {
                    int computeDensity = createImageDecoder.computeDensity(source);
                    Bitmap decodeBitmapInternal = createImageDecoder.decodeBitmapInternal();
                    decodeBitmapInternal.setDensity(computeDensity);
                    Rect rect = createImageDecoder.mOutPaddingRect;
                    if (rect != null && (ninePatchChunk = decodeBitmapInternal.getNinePatchChunk()) != null && NinePatch.isNinePatchChunk(ninePatchChunk)) {
                        nGetPadding(createImageDecoder.mNativePtr, rect);
                    }
                    imageDecoderSourceTrace.close();
                    if (createImageDecoder != null) {
                        createImageDecoder.close();
                    }
                    return decodeBitmapInternal;
                } finally {
                }
            } finally {
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private static AutoCloseable traceDecoderSource(ImageDecoder imageDecoder) {
        final boolean isTagEnabled = Trace.isTagEnabled(8192L);
        if (isTagEnabled) {
            Trace.traceBegin(8192L, describeDecoderForTrace(imageDecoder));
        }
        return new AutoCloseable() { // from class: android.graphics.ImageDecoder.1
            @Override // java.lang.AutoCloseable
            public void close() throws Exception {
                if (isTagEnabled) {
                    Trace.traceEnd(8192L);
                }
            }
        };
    }

    private int computeDensity(Source source) {
        Resources resources;
        int computeDstDensity;
        if (requestedResize()) {
            return 0;
        }
        int density = source.getDensity();
        if (density == 0 || ((this.mIsNinePatch && this.mPostProcessor == null) || (((resources = source.getResources()) != null && resources.getDisplayMetrics().noncompatDensityDpi == density) || density == (computeDstDensity = source.computeDstDensity()) || (density < computeDstDensity && Compatibility.getTargetSdkVersion() >= 28)))) {
            return density;
        }
        float f = computeDstDensity / density;
        setTargetSize(Math.max((int) ((this.mWidth * f) + 0.5f), 1), Math.max((int) ((this.mHeight * f) + 0.5f), 1));
        return computeDstDensity;
    }

    private String getMimeType() {
        return nGetMimeType(this.mNativePtr);
    }

    private ColorSpace getColorSpace() {
        return nGetColorSpace(this.mNativePtr);
    }

    public static Bitmap decodeBitmap(Source source) throws IOException {
        return decodeBitmapImpl(source, null);
    }

    private static boolean isHevcDecoderSupported() {
        synchronized (sIsHevcDecoderSupportedLock) {
            if (sIsHevcDecoderSupportedInitialized) {
                return sIsHevcDecoderSupported;
            }
            MediaFormat mediaFormat = new MediaFormat();
            mediaFormat.setString("mime", "video/hevc");
            boolean z = new MediaCodecList(0).findDecoderForFormat(mediaFormat) != null;
            sIsHevcDecoderSupported = z;
            sIsHevcDecoderSupportedInitialized = true;
            return z;
        }
    }

    private static boolean isP010SupportedForAV1() {
        synchronized (sIsP010SupportedLock) {
            if (sIsP010SupportedFlagsInitialized) {
                return sIsP010SupportedForAV1;
            }
            checkP010SupportforAV1HEVC();
            return sIsP010SupportedForAV1;
        }
    }

    private static boolean isP010SupportedForHEVC() {
        synchronized (sIsP010SupportedLock) {
            if (sIsP010SupportedFlagsInitialized) {
                return sIsP010SupportedForHEVC;
            }
            checkP010SupportforAV1HEVC();
            return sIsP010SupportedForHEVC;
        }
    }

    private static void checkP010SupportforAV1HEVC() {
        for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(0).getCodecInfos()) {
            if (!mediaCodecInfo.isEncoder()) {
                for (String str : mediaCodecInfo.getSupportedTypes()) {
                    if (str.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AV1) || str.equalsIgnoreCase("video/hevc")) {
                        MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
                        for (int i = 0; i < capabilitiesForType.colorFormats.length; i++) {
                            if (capabilitiesForType.colorFormats[i] == 54) {
                                if (str.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AV1)) {
                                    sIsP010SupportedForAV1 = true;
                                } else {
                                    sIsP010SupportedForHEVC = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        sIsP010SupportedFlagsInitialized = true;
    }

    private int postProcessAndRelease(Canvas canvas) {
        try {
            return this.mPostProcessor.onPostProcess(canvas);
        } finally {
            canvas.release();
        }
    }

    private void onPartialImage(int i, Throwable th) throws DecodeException {
        DecodeException decodeException = new DecodeException(i, th, this.mSource);
        OnPartialImageListener onPartialImageListener = this.mOnPartialImageListener;
        if (onPartialImageListener != null) {
            if (!onPartialImageListener.onPartialImage(decodeException)) {
                throw decodeException;
            }
            return;
        }
        throw decodeException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String describeDecoderForTrace(ImageDecoder imageDecoder) {
        StringBuilder sb = new StringBuilder("ID#w=");
        sb.append(imageDecoder.mWidth);
        sb.append(";h=");
        sb.append(imageDecoder.mHeight);
        if (imageDecoder.mDesiredWidth != imageDecoder.mWidth || imageDecoder.mDesiredHeight != imageDecoder.mHeight) {
            sb.append(";dw=");
            sb.append(imageDecoder.mDesiredWidth);
            sb.append(";dh=");
            sb.append(imageDecoder.mDesiredHeight);
        }
        sb.append(";src=");
        sb.append(imageDecoder.mSource);
        return sb.toString();
    }

    private static final class ImageDecoderSourceTrace implements AutoCloseable {
        private final boolean mResourceTracingEnabled;

        ImageDecoderSourceTrace(ImageDecoder imageDecoder) {
            boolean isTagEnabled = Trace.isTagEnabled(8192L);
            this.mResourceTracingEnabled = isTagEnabled;
            if (isTagEnabled) {
                Trace.traceBegin(8192L, ImageDecoder.describeDecoderForTrace(imageDecoder));
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.mResourceTracingEnabled) {
                Trace.traceEnd(8192L);
            }
        }
    }
}
