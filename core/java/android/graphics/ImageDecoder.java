package android.graphics;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
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

  @Deprecated public static final int ERROR_SOURCE_ERROR = 3;

  @Deprecated public static final int ERROR_SOURCE_EXCEPTION = 1;

  @Deprecated public static final int ERROR_SOURCE_INCOMPLETE = 2;
  public static final int MEMORY_POLICY_DEFAULT = 1;
  public static final int MEMORY_POLICY_LOW_RAM = 0;
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
  private static boolean sIsHevcDecoderSupported = false;
  private static boolean sIsHevcDecoderSupportedInitialized = false;
  private static final Object sIsHevcDecoderSupportedLock = new Object();
  private static boolean sIsP010SupportedForAV1 = false;
  private static boolean sIsP010SupportedForHEVC = false;
  private static boolean sIsP010SupportedFlagsInitialized = false;
  private static final Object sIsP010SupportedLock = new Object();
  private int mAllocator = 0;
  private boolean mUnpremultipliedRequired = false;
  private boolean mMutable = false;
  private boolean mConserveMemory = false;
  private boolean mDecodeAsAlphaMask = false;
  private ColorSpace mDesiredColorSpace = null;
  private final AtomicBoolean mClosed = new AtomicBoolean();

  @Retention(RetentionPolicy.SOURCE)
  public @interface Allocator {}

  @Deprecated
  public static class IncompleteException extends IOException {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface MemoryPolicy {}

  public interface OnHeaderDecodedListener {
    void onHeaderDecoded(ImageDecoder imageDecoder, ImageInfo imageInfo, Source source);
  }

  public interface OnPartialImageListener {
    boolean onPartialImage(DecodeException decodeException);
  }

  private static native void nClose(long j);

  private static native ImageDecoder nCreate(long j, boolean z, Source source) throws IOException;

  private static native ImageDecoder nCreate(
      FileDescriptor fileDescriptor, long j, boolean z, Source source) throws IOException;

  private static native ImageDecoder nCreate(
      InputStream inputStream, byte[] bArr, boolean z, Source source) throws IOException;

  /* JADX INFO: Access modifiers changed from: private */
  public static native ImageDecoder nCreate(
      ByteBuffer byteBuffer, int i, int i2, boolean z, Source source) throws IOException;

  /* JADX INFO: Access modifiers changed from: private */
  public static native ImageDecoder nCreate(byte[] bArr, int i, int i2, boolean z, Source source)
      throws IOException;

  private static native ImageDecoder nCreateQMG(long j, boolean z, Source source)
      throws IOException;

  private static native ImageDecoder nCreateQMG(
      InputStream inputStream, byte[] bArr, boolean z, Source source) throws IOException;

  private static native Bitmap nDecodeBitmap(
      long j,
      ImageDecoder imageDecoder,
      boolean z,
      int i,
      int i2,
      Rect rect,
      boolean z2,
      int i3,
      boolean z3,
      boolean z4,
      boolean z5,
      long j2,
      boolean z6)
      throws IOException;

  private static native ColorSpace nGetColorSpace(long j);

  private static native String nGetMimeType(long j);

  private static native void nGetPadding(long j, Rect rect);

  private static native Size nGetSampledSize(long j, int i);

  public abstract static class Source {
    abstract ImageDecoder createImageDecoder(boolean z) throws IOException;

    private Source() {}

    Resources getResources() {
      return null;
    }

    int getDensity() {
      return 0;
    }

    final int computeDstDensity() {
      Resources res = getResources();
      if (res == null) {
        return Bitmap.getDefaultDensity();
      }
      return res.getDisplayMetrics().densityDpi;
    }
  }

  private static class ByteArraySource extends Source {
    private final byte[] mData;
    private final int mLength;
    private final int mOffset;

    ByteArraySource(byte[] data, int offset, int length) {
      super();
      this.mData = data;
      this.mOffset = offset;
      this.mLength = length;
    }

    @Override // android.graphics.ImageDecoder.Source
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      return ImageDecoder.nCreate(this.mData, this.mOffset, this.mLength, preferAnimation, this);
    }

    public String toString() {
      return "ByteArraySource{len=" + this.mLength + "}";
    }
  }

  private static class ByteBufferSource extends Source {
    private final ByteBuffer mBuffer;
    private final int mLength;

    ByteBufferSource(ByteBuffer buffer) {
      super();
      this.mBuffer = buffer;
      this.mLength = buffer.limit() - buffer.position();
    }

    @Override // android.graphics.ImageDecoder.Source
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      if (!this.mBuffer.isDirect() && this.mBuffer.hasArray()) {
        int offset = this.mBuffer.arrayOffset() + this.mBuffer.position();
        int length = this.mBuffer.limit() - this.mBuffer.position();
        return ImageDecoder.nCreate(this.mBuffer.array(), offset, length, preferAnimation, this);
      }
      ByteBuffer buffer = this.mBuffer.slice();
      return ImageDecoder.nCreate(buffer, buffer.position(), buffer.limit(), preferAnimation, this);
    }

    public String toString() {
      return "ByteBufferSource{len=" + this.mLength + "}";
    }
  }

  private static class ContentResolverSource extends Source {
    private final ContentResolver mResolver;
    private final Resources mResources;
    private final Uri mUri;

    ContentResolverSource(ContentResolver resolver, Uri uri, Resources res) {
      super();
      this.mResolver = resolver;
      this.mUri = uri;
      this.mResources = res;
    }

    @Override // android.graphics.ImageDecoder.Source
    Resources getResources() {
      return this.mResources;
    }

    @Override // android.graphics.ImageDecoder.Source
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      AssetFileDescriptor assetFd = null;
      try {
        if ("content".equals(this.mUri.getScheme())) {
          assetFd =
              this.mResolver.openTypedAssetFileDescriptor(
                  this.mUri, ContentType.IMAGE_UNSPECIFIED, null);
        } else {
          assetFd = this.mResolver.openAssetFileDescriptor(this.mUri, "r");
        }
      } catch (FileNotFoundException e) {
      }
      if (assetFd == null) {
        InputStream is = this.mResolver.openInputStream(this.mUri);
        if (is == null) {
          throw new FileNotFoundException(this.mUri.toString());
        }
        return ImageDecoder.createFromStream(is, true, preferAnimation, this);
      }
      return ImageDecoder.createFromAssetFileDescriptor(assetFd, preferAnimation, this);
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
  public static ImageDecoder createFromFile(File file, boolean preferAnimation, Source source)
      throws IOException {
    FileInputStream stream = new FileInputStream(file);
    FileDescriptor fd = stream.getFD();
    try {
      Os.lseek(fd, 0L, OsConstants.SEEK_CUR);
      ImageDecoder decoder = null;
      try {
        decoder = nCreate(fd, -1L, preferAnimation, source);
        return decoder;
      } finally {
        if (decoder == null) {
          IoUtils.closeQuietly(stream);
        } else {
          decoder.mInputStream = stream;
          decoder.mOwnsInputStream = true;
        }
      }
    } catch (ErrnoException e) {
      return createFromStream(stream, true, preferAnimation, source);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static ImageDecoder createFromStream(
      InputStream is, boolean closeInputStream, boolean preferAnimation, Source source)
      throws IOException {
    byte[] storage = new byte[16384];
    ImageDecoder decoder = null;
    try {
      decoder = nCreate(is, storage, preferAnimation, source);
      return decoder;
    } finally {
      if (decoder == null) {
        if (closeInputStream) {
          IoUtils.closeQuietly(is);
        }
      } else {
        decoder.mInputStream = is;
        decoder.mOwnsInputStream = closeInputStream;
        decoder.mTempStorage = storage;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static ImageDecoder createFromStreamQMG(
      InputStream is, boolean closeInputStream, boolean preferAnimation, Source source)
      throws IOException {
    byte[] storage = new byte[16384];
    ImageDecoder decoder = null;
    try {
      decoder = nCreateQMG(is, storage, preferAnimation, source);
      return decoder;
    } finally {
      if (decoder == null) {
        if (closeInputStream) {
          IoUtils.closeQuietly(is);
        }
      } else {
        decoder.mInputStream = is;
        decoder.mOwnsInputStream = closeInputStream;
        decoder.mTempStorage = storage;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static ImageDecoder createFromAssetFileDescriptor(
      AssetFileDescriptor assetFd, boolean preferAnimation, Source source) throws IOException {
    if (assetFd == null) {
      throw new FileNotFoundException();
    }
    FileDescriptor fd = assetFd.getFileDescriptor();
    long offset = assetFd.getStartOffset();
    ImageDecoder decoder = null;
    try {
      try {
        Os.lseek(fd, offset, OsConstants.SEEK_SET);
        decoder = nCreate(fd, assetFd.getDeclaredLength(), preferAnimation, source);
      } catch (ErrnoException e) {
        decoder = createFromStream(new FileInputStream(fd), true, preferAnimation, source);
      }
      if (decoder == null) {
        IoUtils.closeQuietly(assetFd);
      } else {
        decoder.mAssetFd = assetFd;
      }
      return decoder;
    } catch (Throwable th) {
      if (decoder == null) {
        IoUtils.closeQuietly(assetFd);
      } else {
        decoder.mAssetFd = assetFd;
      }
      throw th;
    }
  }

  private static class InputStreamSource extends Source {
    final int mInputDensity;
    InputStream mInputStream;
    final Resources mResources;

    InputStreamSource(Resources res, InputStream is, int inputDensity) {
      super();
      if (is == null) {
        throw new IllegalArgumentException("The InputStream cannot be null");
      }
      this.mResources = res;
      this.mInputStream = is;
      this.mInputDensity = inputDensity;
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      ImageDecoder createFromStream;
      synchronized (this) {
        InputStream is = this.mInputStream;
        if (is == null) {
          throw new IOException("Cannot reuse InputStreamSource");
        }
        this.mInputStream = null;
        createFromStream = ImageDecoder.createFromStream(is, false, preferAnimation, this);
      }
      return createFromStream;
    }

    public String toString() {
      return "InputStream{s=" + this.mInputStream + "}";
    }
  }

  private static class InputStreamSourceQMG extends Source {
    final int mInputDensity;
    InputStream mInputStream;
    final Resources mResources;

    InputStreamSourceQMG(Resources res, InputStream is, int inputDensity) {
      super();
      if (is == null) {
        throw new IllegalArgumentException("The InputStream cannot be null");
      }
      this.mResources = res;
      this.mInputStream = is;
      this.mInputDensity = inputDensity;
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      ImageDecoder createFromStreamQMG;
      synchronized (this) {
        InputStream is = this.mInputStream;
        if (is == null) {
          throw new IOException("Cannot reuse InputStreamSource");
        }
        this.mInputStream = null;
        createFromStreamQMG = ImageDecoder.createFromStreamQMG(is, false, preferAnimation, this);
      }
      return createFromStreamQMG;
    }
  }

  public static class AssetInputStreamSource extends Source {
    private AssetManager.AssetInputStream mAssetInputStream;
    private final int mDensity;
    private final Resources mResources;

    public AssetInputStreamSource(
        AssetManager.AssetInputStream ais, Resources res, TypedValue value) {
      super();
      this.mAssetInputStream = ais;
      this.mResources = res;
      if (value.density == 0) {
        this.mDensity = 160;
      } else if (value.density != 65535) {
        this.mDensity = value.density;
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      ImageDecoder createFromAsset;
      synchronized (this) {
        AssetManager.AssetInputStream ais = this.mAssetInputStream;
        if (ais == null) {
          throw new IOException("Cannot reuse AssetInputStreamSource");
        }
        this.mAssetInputStream = null;
        createFromAsset = ImageDecoder.createFromAsset(ais, preferAnimation, this);
      }
      return createFromAsset;
    }

    public String toString() {
      return "AssetInputStream{s=" + this.mAssetInputStream + "}";
    }
  }

  public static class AssetInputStreamSourceQMG extends Source {
    private AssetManager.AssetInputStream mAssetInputStream;
    private final int mDensity;
    private final Resources mResources;

    public AssetInputStreamSourceQMG(
        AssetManager.AssetInputStream ais, Resources res, TypedValue value) {
      super();
      this.mAssetInputStream = ais;
      this.mResources = res;
      if (value.density == 0) {
        this.mDensity = 160;
      } else if (value.density != 65535) {
        this.mDensity = value.density;
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      ImageDecoder createFromAssetQMG;
      synchronized (this) {
        AssetManager.AssetInputStream ais = this.mAssetInputStream;
        if (ais == null) {
          throw new IOException("Cannot reuse AssetInputStreamSource");
        }
        this.mAssetInputStream = null;
        createFromAssetQMG = ImageDecoder.createFromAssetQMG(ais, preferAnimation, this);
      }
      return createFromAssetQMG;
    }
  }

  private static class ResourceSource extends Source {
    private Object mLock;
    int mResDensity;
    final int mResId;
    final Resources mResources;

    ResourceSource(Resources res, int resId) {
      super();
      this.mLock = new Object();
      this.mResources = res;
      this.mResId = resId;
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      TypedValue value = new TypedValue();
      InputStream is = this.mResources.openRawResource(this.mResId, value);
      synchronized (this.mLock) {
        if (value.density == 0) {
          this.mResDensity = 160;
        } else if (value.density != 65535) {
          this.mResDensity = value.density;
        }
      }
      return ImageDecoder.createFromAsset(
          (AssetManager.AssetInputStream) is, preferAnimation, this);
    }

    public String toString() {
      try {
        return "Resource{name=" + this.mResources.getResourceName(this.mResId) + "}";
      } catch (Resources.NotFoundException e) {
        return "Resource{id=" + this.mResId + "}";
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static ImageDecoder createFromAsset(
      AssetManager.AssetInputStream ais, boolean preferAnimation, Source source)
      throws IOException {
    ImageDecoder decoder = null;
    try {
      long asset = ais.getNativeAsset();
      decoder = nCreate(asset, preferAnimation, source);
      return decoder;
    } finally {
      if (decoder == null) {
        IoUtils.closeQuietly(ais);
      } else {
        decoder.mInputStream = ais;
        decoder.mOwnsInputStream = true;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static ImageDecoder createFromAssetQMG(
      AssetManager.AssetInputStream ais, boolean preferAnimation, Source source)
      throws IOException {
    ImageDecoder decoder = null;
    try {
      long asset = ais.getNativeAsset();
      decoder = nCreateQMG(asset, preferAnimation, source);
      return decoder;
    } finally {
      if (decoder == null) {
        IoUtils.closeQuietly(ais);
      } else {
        decoder.mInputStream = ais;
        decoder.mOwnsInputStream = true;
      }
    }
  }

  private static class AssetSource extends Source {
    private final AssetManager mAssets;
    private final String mFileName;

    AssetSource(AssetManager assets, String fileName) {
      super();
      this.mAssets = assets;
      this.mFileName = fileName;
    }

    @Override // android.graphics.ImageDecoder.Source
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      InputStream is = this.mAssets.open(this.mFileName);
      return ImageDecoder.createFromAsset(
          (AssetManager.AssetInputStream) is, preferAnimation, this);
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      return ImageDecoder.createFromFile(this.mFile, preferAnimation, this);
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
    public ImageDecoder createImageDecoder(boolean preferAnimation) throws IOException {
      try {
        AssetFileDescriptor assetFd = this.mCallable.call();
        return ImageDecoder.createFromAssetFileDescriptor(assetFd, preferAnimation, this);
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

    private ImageInfo(Size size, boolean isAnimated, String mimeType, ColorSpace colorSpace) {
      this.mSize = size;
      this.mIsAnimated = isAnimated;
      this.mMimeType = mimeType;
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
    public @interface Error {}

    DecodeException(int error, Throwable cause, Source source) {
      super(errorMessage(error, cause), cause);
      this.mError = error;
      this.mSource = source;
    }

    DecodeException(int error, String msg, Throwable cause, Source source) {
      super(msg + errorMessage(error, cause), cause);
      this.mError = error;
      this.mSource = source;
    }

    public int getError() {
      return this.mError;
    }

    public Source getSource() {
      return this.mSource;
    }

    private static String errorMessage(int error, Throwable cause) {
      switch (error) {
        case 1:
          return "Exception in input: " + cause;
        case 2:
          return "Input was incomplete.";
        case 3:
          return "Input contained an error.";
        default:
          return "";
      }
    }
  }

  private ImageDecoder(
      long nativePtr, int width, int height, boolean animated, boolean isNinePatch) {
    CloseGuard closeGuard = CloseGuard.get();
    this.mCloseGuard = closeGuard;
    this.mNativePtr = nativePtr;
    this.mWidth = width;
    this.mHeight = height;
    this.mDesiredWidth = width;
    this.mDesiredHeight = height;
    this.mAnimated = animated;
    this.mIsNinePatch = isNinePatch;
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

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public static boolean isMimeTypeSupported(String mimeType) {
    char c;
    Objects.requireNonNull(mimeType);
    String lowerCase = mimeType.toLowerCase(Locale.US);
    switch (lowerCase.hashCode()) {
      case -1875291391:
        if (lowerCase.equals("image/x-fuji-raf")) {
          c = '\r';
          break;
        }
        c = 65535;
        break;
      case -1635437028:
        if (lowerCase.equals("image/x-samsung-srw")) {
          c = 16;
          break;
        }
        c = 65535;
        break;
      case -1594371159:
        if (lowerCase.equals("image/x-sony-arw")) {
          c = 7;
          break;
        }
        c = 65535;
        break;
      case -1487656890:
        if (lowerCase.equals(MediaFormat.MIMETYPE_IMAGE_AVIF)) {
          c = 19;
          break;
        }
        c = 65535;
        break;
      case -1487464693:
        if (lowerCase.equals("image/heic")) {
          c = 18;
          break;
        }
        c = 65535;
        break;
      case -1487464690:
        if (lowerCase.equals("image/heif")) {
          c = 17;
          break;
        }
        c = 65535;
        break;
      case -1487394660:
        if (lowerCase.equals(ContentType.IMAGE_JPEG)) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case -1487018032:
        if (lowerCase.equals("image/webp")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case -1423313290:
        if (lowerCase.equals("image/x-adobe-dng")) {
          c = '\t';
          break;
        }
        c = 65535;
        break;
      case -985160897:
        if (lowerCase.equals("image/x-panasonic-rw2")) {
          c = 14;
          break;
        }
        c = 65535;
        break;
      case -879272239:
        if (lowerCase.equals(ContentType.IMAGE_BMP)) {
          c = 4;
          break;
        }
        c = 65535;
        break;
      case -879267568:
        if (lowerCase.equals(ContentType.IMAGE_GIF)) {
          c = 3;
          break;
        }
        c = 65535;
        break;
      case -879258763:
        if (lowerCase.equals(ContentType.IMAGE_PNG)) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      case -332763809:
        if (lowerCase.equals("image/x-pentax-pef")) {
          c = 15;
          break;
        }
        c = 65535;
        break;
      case 741270252:
        if (lowerCase.equals(ContentType.IMAGE_WBMP)) {
          c = 6;
          break;
        }
        c = 65535;
        break;
      case 1146342924:
        if (lowerCase.equals("image/x-ico")) {
          c = 5;
          break;
        }
        c = 65535;
        break;
      case 1378106698:
        if (lowerCase.equals("image/x-olympus-orf")) {
          c = '\f';
          break;
        }
        c = 65535;
        break;
      case 2099152104:
        if (lowerCase.equals("image/x-nikon-nef")) {
          c = '\n';
          break;
        }
        c = 65535;
        break;
      case 2099152524:
        if (lowerCase.equals("image/x-nikon-nrw")) {
          c = 11;
          break;
        }
        c = 65535;
        break;
      case 2111234748:
        if (lowerCase.equals("image/x-canon-cr2")) {
          c = '\b';
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case '\b':
      case '\t':
      case '\n':
      case 11:
      case '\f':
      case '\r':
      case 14:
      case 15:
      case 16:
        return true;
      case 17:
      case 18:
        return isHevcDecoderSupported();
      case 19:
        return isP010SupportedForAV1();
      default:
        return false;
    }
  }

  public static Source createSource(Resources res, int resId) {
    return new ResourceSource(res, resId);
  }

  public static Source createSource(ContentResolver cr, Uri uri) {
    return new ContentResolverSource(cr, uri, null);
  }

  public static Source createSource(ContentResolver cr, Uri uri, Resources res) {
    return new ContentResolverSource(cr, uri, res);
  }

  public static Source createSource(AssetManager assets, String fileName) {
    return new AssetSource(assets, fileName);
  }

  public static Source createSource(byte[] data, int offset, int length)
      throws ArrayIndexOutOfBoundsException {
    if (data == null) {
      throw new NullPointerException("null byte[] in createSource!");
    }
    if (offset < 0 || length < 0 || offset >= data.length || offset + length > data.length) {
      throw new ArrayIndexOutOfBoundsException("invalid offset/length!");
    }
    return new ByteArraySource(data, offset, length);
  }

  public static Source createSource(byte[] data) {
    return createSource(data, 0, data.length);
  }

  public static Source createSource(ByteBuffer buffer) {
    return new ByteBufferSource(buffer);
  }

  public static Source createSource(Resources res, InputStream is) {
    return new InputStreamSource(res, is, Bitmap.getDefaultDensity());
  }

  public static Source createSource(Resources res, InputStream is, int density) {
    return new InputStreamSource(res, is, density);
  }

  public static Source createSourceQMG(Resources res, InputStream is, int density) {
    return new InputStreamSourceQMG(res, is, density);
  }

  public static Source createSource(File file) {
    return new FileSource(file);
  }

  public static Source createSource(Callable<AssetFileDescriptor> callable) {
    return new CallableSource(callable);
  }

  private Size getSampledSize(int sampleSize) {
    if (sampleSize <= 0) {
      throw new IllegalArgumentException("sampleSize must be positive! provided " + sampleSize);
    }
    long j = this.mNativePtr;
    if (j == 0) {
      throw new IllegalStateException("ImageDecoder is closed!");
    }
    return nGetSampledSize(j, sampleSize);
  }

  @Deprecated
  public ImageDecoder setResize(int width, int height) {
    setTargetSize(width, height);
    return this;
  }

  public void setTargetSize(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException(
          "Dimensions must be positive! provided ("
              + width
              + ", "
              + height
              + NavigationBarInflaterView.KEY_CODE_END);
    }
    this.mDesiredWidth = width;
    this.mDesiredHeight = height;
  }

  @Deprecated
  public ImageDecoder setResize(int sampleSize) {
    setTargetSampleSize(sampleSize);
    return this;
  }

  private int getTargetDimension(int original, int sampleSize, int computed) {
    if (sampleSize >= original) {
      return 1;
    }
    int target = original / sampleSize;
    if (computed == target) {
      return computed;
    }
    int reverse = computed * sampleSize;
    if (Math.abs(reverse - original) < sampleSize) {
      return computed;
    }
    return target;
  }

  public void setTargetSampleSize(int sampleSize) {
    Size size = getSampledSize(sampleSize);
    int targetWidth = getTargetDimension(this.mWidth, sampleSize, size.getWidth());
    int targetHeight = getTargetDimension(this.mHeight, sampleSize, size.getHeight());
    setTargetSize(targetWidth, targetHeight);
  }

  private boolean requestedResize() {
    return (this.mWidth == this.mDesiredWidth && this.mHeight == this.mDesiredHeight)
        ? false
        : true;
  }

  public void setAllocator(int allocator) {
    if (allocator < 0 || allocator > 3) {
      throw new IllegalArgumentException("invalid allocator " + allocator);
    }
    this.mAllocator = allocator;
  }

  public int getAllocator() {
    return this.mAllocator;
  }

  public void setUnpremultipliedRequired(boolean unpremultipliedRequired) {
    this.mUnpremultipliedRequired = unpremultipliedRequired;
  }

  @Deprecated
  public ImageDecoder setRequireUnpremultiplied(boolean unpremultipliedRequired) {
    setUnpremultipliedRequired(unpremultipliedRequired);
    return this;
  }

  public boolean isUnpremultipliedRequired() {
    return this.mUnpremultipliedRequired;
  }

  @Deprecated
  public boolean getRequireUnpremultiplied() {
    return isUnpremultipliedRequired();
  }

  public void setPostProcessor(PostProcessor postProcessor) {
    this.mPostProcessor = postProcessor;
  }

  public PostProcessor getPostProcessor() {
    return this.mPostProcessor;
  }

  public void setOnPartialImageListener(OnPartialImageListener listener) {
    this.mOnPartialImageListener = listener;
  }

  public OnPartialImageListener getOnPartialImageListener() {
    return this.mOnPartialImageListener;
  }

  public void setCrop(Rect subset) {
    this.mCropRect = subset;
  }

  public Rect getCrop() {
    return this.mCropRect;
  }

  public void setOutPaddingRect(Rect outPadding) {
    this.mOutPaddingRect = outPadding;
  }

  public void setMutableRequired(boolean mutable) {
    this.mMutable = mutable;
  }

  @Deprecated
  public ImageDecoder setMutable(boolean mutable) {
    setMutableRequired(mutable);
    return this;
  }

  public boolean isMutableRequired() {
    return this.mMutable;
  }

  @Deprecated
  public boolean getMutable() {
    return isMutableRequired();
  }

  public void setMemorySizePolicy(int policy) {
    this.mConserveMemory = policy == 0;
  }

  public int getMemorySizePolicy() {
    return !this.mConserveMemory ? 1 : 0;
  }

  @Deprecated
  public void setConserveMemory(boolean conserveMemory) {
    this.mConserveMemory = conserveMemory;
  }

  @Deprecated
  public boolean getConserveMemory() {
    return this.mConserveMemory;
  }

  public void setDecodeAsAlphaMaskEnabled(boolean enabled) {
    this.mDecodeAsAlphaMask = enabled;
  }

  @Deprecated
  public ImageDecoder setDecodeAsAlphaMask(boolean enabled) {
    setDecodeAsAlphaMaskEnabled(enabled);
    return this;
  }

  @Deprecated
  public ImageDecoder setAsAlphaMask(boolean asAlphaMask) {
    setDecodeAsAlphaMask(asAlphaMask);
    return this;
  }

  public boolean isDecodeAsAlphaMaskEnabled() {
    return this.mDecodeAsAlphaMask;
  }

  @Deprecated
  public boolean getDecodeAsAlphaMask() {
    return this.mDecodeAsAlphaMask;
  }

  @Deprecated
  public boolean getAsAlphaMask() {
    return getDecodeAsAlphaMask();
  }

  public void setTargetColorSpace(ColorSpace colorSpace) {
    this.mDesiredColorSpace = colorSpace;
  }

  @Override // java.lang.AutoCloseable
  public void close() {
    this.mCloseGuard.close();
    if (!this.mClosed.compareAndSet(false, true)) {
      return;
    }
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

  private void checkState(boolean animated) {
    if (this.mNativePtr == 0) {
      throw new IllegalStateException("Cannot use closed ImageDecoder!");
    }
    checkSubset(this.mDesiredWidth, this.mDesiredHeight, this.mCropRect);
    if (!animated && this.mAllocator == 3) {
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

  private static void checkSubset(int width, int height, Rect r) {
    if (r == null) {
      return;
    }
    if (r.width() <= 0 || r.height() <= 0) {
      throw new IllegalStateException("Subset " + r + " is empty/unsorted");
    }
    if (r.left < 0 || r.top < 0 || r.right > width || r.bottom > height) {
      throw new IllegalStateException(
          "Subset "
              + r
              + " not contained by scaled image bounds: ("
              + width
              + " x "
              + height
              + NavigationBarInflaterView.KEY_CODE_END);
    }
  }

  private boolean checkForExtended() {
    ColorSpace colorSpace = this.mDesiredColorSpace;
    if (colorSpace == null) {
      return false;
    }
    return colorSpace == ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB)
        || this.mDesiredColorSpace == ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
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
    return nDecodeBitmap(
        this.mNativePtr,
        this,
        this.mPostProcessor != null,
        this.mDesiredWidth,
        this.mDesiredHeight,
        this.mCropRect,
        this.mMutable,
        this.mAllocator,
        this.mUnpremultipliedRequired,
        this.mConserveMemory,
        this.mDecodeAsAlphaMask,
        getColorSpacePtr(),
        checkForExtended());
  }

  private void callHeaderDecoded(OnHeaderDecodedListener listener, Source src) {
    if (listener != null) {
      ImageInfo info =
          new ImageInfo(
              new Size(this.mWidth, this.mHeight), this.mAnimated, getMimeType(), getColorSpace());
      listener.onHeaderDecoded(this, info, src);
    }
  }

  public static ImageInfo decodeHeader(Source src) throws IOException {
    Trace.traceBegin(8192L, "ImageDecoder#decodeHeader");
    try {
      ImageDecoder decoder = src.createImageDecoder(true);
      try {
        ImageInfo imageInfo =
            new ImageInfo(
                new Size(decoder.mWidth, decoder.mHeight),
                decoder.mAnimated,
                decoder.getMimeType(),
                decoder.getColorSpace());
        if (decoder != null) {
          decoder.close();
        }
        return imageInfo;
      } finally {
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public static Drawable decodeDrawable(Source src, OnHeaderDecodedListener listener)
      throws IOException {
    if (listener == null) {
      throw new IllegalArgumentException(
          "listener cannot be null! Use decodeDrawable(Source) to not have a listener");
    }
    return decodeDrawableImpl(src, listener);
  }

  private static Drawable decodeDrawableImpl(Source src, OnHeaderDecodedListener listener)
      throws IOException {
    Trace.traceBegin(8192L, "ImageDecoder#decodeDrawable");
    try {
      try {
        ImageDecoder decoder = src.createImageDecoder(true);
        try {
          decoder.mSource = src;
          try {
            decoder.callHeaderDecoded(listener, src);
            ImageDecoderSourceTrace unused = new ImageDecoderSourceTrace(decoder);
            try {
              if (decoder.mUnpremultipliedRequired) {
                throw new IllegalStateException(
                    "Cannot decode a Drawable with unpremultiplied pixels!");
              }
              if (decoder.mMutable) {
                throw new IllegalStateException("Cannot decode a mutable Drawable!");
              }
              int srcDensity = decoder.computeDensity(src);
              if (decoder.mAnimated) {
                ImageDecoder postProcessPtr = decoder.mPostProcessor == null ? null : decoder;
                decoder.checkState(true);
                Drawable d =
                    new AnimatedImageDrawable(
                        decoder.mNativePtr,
                        postProcessPtr,
                        decoder.mDesiredWidth,
                        decoder.mDesiredHeight,
                        decoder.getColorSpacePtr(),
                        decoder.checkForExtended(),
                        srcDensity,
                        src.computeDstDensity(),
                        decoder.mCropRect,
                        decoder.mInputStream,
                        decoder.mAssetFd);
                decoder.mInputStream = null;
                decoder.mAssetFd = null;
                unused.close();
                if (decoder != null) {
                  decoder.close();
                }
                Trace.traceEnd(8192L);
                return d;
              }
              Bitmap bm = decoder.decodeBitmapInternal();
              bm.setDensity(srcDensity);
              Resources res = src.getResources();
              byte[] np = bm.getNinePatchChunk();
              if (np == null || !NinePatch.isNinePatchChunk(np)) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(res, bm);
                unused.close();
                if (decoder != null) {
                  decoder.close();
                }
                Trace.traceEnd(8192L);
                return bitmapDrawable;
              }
              Rect opticalInsets = new Rect();
              bm.getOpticalInsets(opticalInsets);
              Rect padding = decoder.mOutPaddingRect;
              Rect padding2 = padding == null ? new Rect() : padding;
              nGetPadding(decoder.mNativePtr, padding2);
              NinePatchDrawable ninePatchDrawable =
                  new NinePatchDrawable(res, bm, np, padding2, opticalInsets, null);
              unused.close();
              if (decoder != null) {
                decoder.close();
              }
              Trace.traceEnd(8192L);
              return ninePatchDrawable;
            } finally {
            }
          } catch (Throwable th) {
            th = th;
            Throwable th2 = th;
            if (decoder == null) {
              throw th2;
            }
            try {
              decoder.close();
              throw th2;
            } catch (Throwable th3) {
              th2.addSuppressed(th3);
              throw th2;
            }
          }
        } catch (Throwable th4) {
          th = th4;
        }
      } catch (Throwable th5) {
        th = th5;
        Trace.traceEnd(8192L);
        throw th;
      }
    } catch (Throwable th6) {
      th = th6;
      Trace.traceEnd(8192L);
      throw th;
    }
  }

  public static Drawable decodeDrawable(Source src) throws IOException {
    return decodeDrawableImpl(src, null);
  }

  public static Bitmap decodeBitmap(Source src, OnHeaderDecodedListener listener)
      throws IOException {
    if (listener == null) {
      throw new IllegalArgumentException(
          "listener cannot be null! Use decodeBitmap(Source) to not have a listener");
    }
    return decodeBitmapImpl(src, listener);
  }

  private static Bitmap decodeBitmapImpl(Source src, OnHeaderDecodedListener listener)
      throws IOException {
    byte[] np;
    Trace.traceBegin(8192L, "ImageDecoder#decodeBitmap");
    try {
      ImageDecoder decoder = src.createImageDecoder(false);
      try {
        decoder.mSource = src;
        decoder.callHeaderDecoded(listener, src);
        ImageDecoderSourceTrace unused = new ImageDecoderSourceTrace(decoder);
        try {
          int srcDensity = decoder.computeDensity(src);
          Bitmap bm = decoder.decodeBitmapInternal();
          bm.setDensity(srcDensity);
          Rect padding = decoder.mOutPaddingRect;
          if (padding != null
              && (np = bm.getNinePatchChunk()) != null
              && NinePatch.isNinePatchChunk(np)) {
            nGetPadding(decoder.mNativePtr, padding);
          }
          unused.close();
          if (decoder != null) {
            decoder.close();
          }
          return bm;
        } finally {
        }
      } finally {
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  private static AutoCloseable traceDecoderSource(ImageDecoder decoder) {
    final boolean resourceTracingEnabled = Trace.isTagEnabled(8192L);
    if (resourceTracingEnabled) {
      Trace.traceBegin(8192L, describeDecoderForTrace(decoder));
    }
    return new AutoCloseable() { // from class: android.graphics.ImageDecoder.1
      @Override // java.lang.AutoCloseable
      public void close() throws Exception {
        if (resourceTracingEnabled) {
          Trace.traceEnd(8192L);
        }
      }
    };
  }

  private int computeDensity(Source src) {
    if (requestedResize()) {
      return 0;
    }
    int srcDensity = src.getDensity();
    if (srcDensity == 0) {
      return srcDensity;
    }
    if (this.mIsNinePatch && this.mPostProcessor == null) {
      return srcDensity;
    }
    Resources res = src.getResources();
    if (res != null && res.getDisplayMetrics().noncompatDensityDpi == srcDensity) {
      return srcDensity;
    }
    int dstDensity = src.computeDstDensity();
    if (srcDensity == dstDensity) {
      return srcDensity;
    }
    if (srcDensity < dstDensity && Compatibility.getTargetSdkVersion() >= 28) {
      return srcDensity;
    }
    float scale = dstDensity / srcDensity;
    int scaledWidth = Math.max((int) ((this.mWidth * scale) + 0.5f), 1);
    int scaledHeight = Math.max((int) ((this.mHeight * scale) + 0.5f), 1);
    setTargetSize(scaledWidth, scaledHeight);
    return dstDensity;
  }

  private String getMimeType() {
    return nGetMimeType(this.mNativePtr);
  }

  private ColorSpace getColorSpace() {
    return nGetColorSpace(this.mNativePtr);
  }

  public static Bitmap decodeBitmap(Source src) throws IOException {
    return decodeBitmapImpl(src, null);
  }

  private static boolean isHevcDecoderSupported() {
    synchronized (sIsHevcDecoderSupportedLock) {
      if (sIsHevcDecoderSupportedInitialized) {
        return sIsHevcDecoderSupported;
      }
      MediaFormat format = new MediaFormat();
      format.setString(MediaFormat.KEY_MIME, "video/hevc");
      MediaCodecList mcl = new MediaCodecList(0);
      boolean z = mcl.findDecoderForFormat(format) != null;
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
    MediaCodecList codecList = new MediaCodecList(0);
    for (MediaCodecInfo mediaCodecInfo : codecList.getCodecInfos()) {
      if (!mediaCodecInfo.isEncoder()) {
        for (String mediaType : mediaCodecInfo.getSupportedTypes()) {
          if (mediaType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AV1)
              || mediaType.equalsIgnoreCase("video/hevc")) {
            MediaCodecInfo.CodecCapabilities codecCapabilities =
                mediaCodecInfo.getCapabilitiesForType(mediaType);
            for (int i = 0; i < codecCapabilities.colorFormats.length; i++) {
              if (codecCapabilities.colorFormats[i] == 54) {
                if (mediaType.equalsIgnoreCase(MediaFormat.MIMETYPE_VIDEO_AV1)) {
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

  private void onPartialImage(int error, Throwable cause) throws DecodeException {
    DecodeException exception = new DecodeException(error, cause, this.mSource);
    OnPartialImageListener onPartialImageListener = this.mOnPartialImageListener;
    if (onPartialImageListener != null) {
      if (!onPartialImageListener.onPartialImage(exception)) {
        throw exception;
      }
      return;
    }
    throw exception;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static String describeDecoderForTrace(ImageDecoder decoder) {
    StringBuilder builder = new StringBuilder();
    builder.append("ID#w=");
    builder.append(decoder.mWidth);
    builder.append(";h=");
    builder.append(decoder.mHeight);
    if (decoder.mDesiredWidth != decoder.mWidth || decoder.mDesiredHeight != decoder.mHeight) {
      builder.append(";dw=");
      builder.append(decoder.mDesiredWidth);
      builder.append(";dh=");
      builder.append(decoder.mDesiredHeight);
    }
    builder.append(";src=");
    builder.append(decoder.mSource);
    return builder.toString();
  }

  private static final class ImageDecoderSourceTrace implements AutoCloseable {
    private final boolean mResourceTracingEnabled;

    ImageDecoderSourceTrace(ImageDecoder decoder) {
      boolean isTagEnabled = Trace.isTagEnabled(8192L);
      this.mResourceTracingEnabled = isTagEnabled;
      if (isTagEnabled) {
        Trace.traceBegin(8192L, ImageDecoder.describeDecoderForTrace(decoder));
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
