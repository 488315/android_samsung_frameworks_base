package android.media;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;
import libcore.io.IoUtils;

/* loaded from: classes2.dex */
public class ThumbnailUtils {
    private static final int OPTIONS_NONE = 0;
    public static final int OPTIONS_RECYCLE_INPUT = 2;
    private static final int OPTIONS_SCALE_UP = 1;
    private static final String TAG = "ThumbnailUtils";

    @Deprecated
    public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;

    @Deprecated
    private static int computeInitialSampleSize(BitmapFactory.Options options, int i, int i2) {
        return 1;
    }

    @Deprecated
    private static int computeSampleSize(BitmapFactory.Options options, int i, int i2) {
        return 1;
    }

    @Deprecated
    private static void createThumbnailFromEXIF(String str, int i, int i2, SizedThumbnailBitmap sizedThumbnailBitmap) {
    }

    private static Size convertKind(int i) {
        return MediaStore.Images.Thumbnails.getKindSize(i);
    }

    private static class Resizer implements ImageDecoder.OnHeaderDecodedListener {
        private final CancellationSignal signal;
        private final Size size;

        public Resizer(Size size, CancellationSignal cancellationSignal) {
            this.size = size;
            this.signal = cancellationSignal;
        }

        @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
        public void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
            CancellationSignal cancellationSignal = this.signal;
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            imageDecoder.setAllocator(1);
            int iMax = Math.max(imageInfo.getSize().getWidth() / this.size.getWidth(), imageInfo.getSize().getHeight() / this.size.getHeight());
            if (iMax > 1) {
                imageDecoder.setTargetSampleSize(iMax);
            }
        }
    }

    @Deprecated
    public static Bitmap createAudioThumbnail(String str, int i) {
        try {
            return createAudioThumbnail(new File(str), convertKind(i), null);
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }
    }

    public static Bitmap createAudioThumbnail(File file, Size size, CancellationSignal cancellationSignal) throws IOException {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Resizer resizer = new Resizer(size, cancellationSignal);
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
                if (embeddedPicture != null) {
                    Bitmap bitmapDecodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(embeddedPicture), resizer);
                    mediaMetadataRetriever.close();
                    return bitmapDecodeBitmap;
                }
                mediaMetadataRetriever.close();
                if ("unknown".equals(Environment.getExternalStorageState(file))) {
                    throw new IOException("No embedded album art found");
                }
                File parentFile = file.getParentFile();
                File parentFile2 = parentFile != null ? parentFile.getParentFile() : null;
                if (parentFile != null && parentFile.getName().equals(Environment.DIRECTORY_DOWNLOADS)) {
                    throw new IOException("No thumbnails in Downloads directories");
                }
                if (parentFile2 != null && "unknown".equals(Environment.getExternalStorageState(parentFile2))) {
                    throw new IOException("No thumbnails in top-level directories");
                }
                File[] fileArrDefeatNullable = ArrayUtils.defeatNullable(file.getParentFile().listFiles(new FilenameFilter() { // from class: android.media.ThumbnailUtils$$ExternalSyntheticLambda0
                    @Override // java.io.FilenameFilter
                    public final boolean accept(File file2, String str) {
                        return ThumbnailUtils.lambda$createAudioThumbnail$0(file2, str);
                    }
                }));
                final ToIntFunction toIntFunction = new ToIntFunction() { // from class: android.media.ThumbnailUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        return ThumbnailUtils.lambda$createAudioThumbnail$1((File) obj);
                    }
                };
                File file2 = (File) Arrays.asList(fileArrDefeatNullable).stream().max(new Comparator() { // from class: android.media.ThumbnailUtils$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ThumbnailUtils.lambda$createAudioThumbnail$2(toIntFunction, (File) obj, (File) obj2);
                    }
                }).orElse(null);
                if (file2 == null) {
                    throw new IOException("No album art found");
                }
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                return ImageDecoder.decodeBitmap(ImageDecoder.createSource(file2), resizer);
            } finally {
            }
        } catch (RuntimeException e) {
            throw new IOException("Failed to create thumbnail", e);
        }
    }

    static /* synthetic */ boolean lambda$createAudioThumbnail$0(File file, String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.contains("albumart")) {
            return lowerCase.endsWith(".jpg") || lowerCase.endsWith(".png");
        }
        return false;
    }

    static /* synthetic */ int lambda$createAudioThumbnail$1(File file) {
        String lowerCase = file.getName().toLowerCase();
        if (lowerCase.equals("albumart.jpg")) {
            return 4;
        }
        if (lowerCase.startsWith("albumart") && lowerCase.endsWith(".jpg")) {
            return 3;
        }
        return (lowerCase.contains("albumart") && lowerCase.endsWith(".jpg")) ? 2 : 0;
    }

    static /* synthetic */ int lambda$createAudioThumbnail$2(ToIntFunction toIntFunction, File file, File file2) {
        return toIntFunction.applyAsInt(file) - toIntFunction.applyAsInt(file2);
    }

    @Deprecated
    public static Bitmap createImageThumbnail(String str, int i) {
        try {
            return createImageThumbnail(new File(str), convertKind(i), null);
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }
    }

    public static Bitmap createImageThumbnail(File file, Size size, CancellationSignal cancellationSignal) throws IOException {
        ExifInterface exifInterface;
        byte[] thumbnailBytes;
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Resizer resizer = new Resizer(size, cancellationSignal);
        String mimeTypeForFile = MediaFile.getMimeTypeForFile(file.getName());
        int i = 0;
        Bitmap bitmapDecodeBitmap = null;
        if (MediaFile.isExifMimeType(mimeTypeForFile)) {
            exifInterface = new ExifInterface(file);
            int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            if (attributeInt == 3) {
                i = 180;
            } else if (attributeInt == 6) {
                i = 90;
            } else if (attributeInt == 8) {
                i = 270;
            }
        } else {
            exifInterface = null;
        }
        if (mimeTypeForFile.equals("image/heif") || mimeTypeForFile.equals("image/heif-sequence") || mimeTypeForFile.equals("image/heic") || mimeTypeForFile.equals("image/heic-sequence") || mimeTypeForFile.equals(MediaFormat.MIMETYPE_IMAGE_AVIF)) {
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                    Bitmap thumbnailImageAtIndex = mediaMetadataRetriever.getThumbnailImageAtIndex(-1, new MediaMetadataRetriever.BitmapParams(), size.getWidth(), size.getWidth() * size.getHeight());
                    mediaMetadataRetriever.close();
                    bitmapDecodeBitmap = thumbnailImageAtIndex;
                } finally {
                }
            } catch (RuntimeException e) {
                throw new IOException("Failed to create thumbnail", e);
            }
        }
        if (bitmapDecodeBitmap == null && exifInterface != null && (thumbnailBytes = exifInterface.getThumbnailBytes()) != null) {
            try {
                bitmapDecodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(thumbnailBytes), resizer);
            } catch (ImageDecoder.DecodeException e2) {
                Log.w(TAG, e2);
            }
        }
        Bitmap bitmap = bitmapDecodeBitmap;
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (bitmap == null) {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(file), resizer);
        }
        if (i == 0 || bitmap == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(i, width / 2, height / 2);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

    @Deprecated
    public static Bitmap createVideoThumbnail(String str, int i) {
        try {
            return createVideoThumbnail(new File(str), convertKind(i), null);
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }
    }

    public static Bitmap createVideoThumbnail(File file, Size size, CancellationSignal cancellationSignal) throws IOException {
        Bitmap bitmapDecodeBitmap;
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Resizer resizer = new Resizer(size, cancellationSignal);
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
                if (embeddedPicture != null) {
                    bitmapDecodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(embeddedPicture), resizer);
                } else {
                    MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
                    bitmapParams.setPreferredConfig(Bitmap.Config.ARGB_8888);
                    int i = Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
                    int i2 = Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
                    long j = (Long.parseLong(mediaMetadataRetriever.extractMetadata(9)) * 1000) / 2;
                    if (size.getWidth() > i && size.getHeight() > i2) {
                        bitmapDecodeBitmap = (Bitmap) Objects.requireNonNull(mediaMetadataRetriever.getFrameAtTime(j, 2, bitmapParams));
                    } else {
                        bitmapDecodeBitmap = (Bitmap) Objects.requireNonNull(mediaMetadataRetriever.getScaledFrameAtTime(j, 2, size.getWidth(), size.getHeight(), bitmapParams));
                    }
                }
                mediaMetadataRetriever.close();
                return bitmapDecodeBitmap;
            } finally {
            }
        } catch (RuntimeException e) {
            throw new IOException("Failed to create thumbnail", e);
        }
    }

    public static Bitmap extractThumbnail(Bitmap bitmap, int i, int i2) {
        return extractThumbnail(bitmap, i, i2, 0);
    }

    public static Bitmap extractThumbnail(Bitmap bitmap, int i, int i2, int i3) {
        float f;
        int height;
        if (bitmap == null) {
            return null;
        }
        if (bitmap.getWidth() < bitmap.getHeight()) {
            f = i;
            height = bitmap.getWidth();
        } else {
            f = i2;
            height = bitmap.getHeight();
        }
        float f2 = f / height;
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f2);
        return transform(matrix, bitmap, i, i2, i3 | 1);
    }

    @Deprecated
    private static void closeSilently(ParcelFileDescriptor parcelFileDescriptor) {
        IoUtils.closeQuietly(parcelFileDescriptor);
    }

    @Deprecated
    private static ParcelFileDescriptor makeInputStream(Uri uri, ContentResolver contentResolver) {
        try {
            return contentResolver.openFileDescriptor(uri, "r");
        } catch (IOException unused) {
            return null;
        }
    }

    @Deprecated
    private static Bitmap transform(Matrix matrix, Bitmap bitmap, int i, int i2, int i3) {
        Matrix matrix2;
        boolean z = (i3 & 1) != 0;
        boolean z2 = (i3 & 2) != 0;
        int width = bitmap.getWidth() - i;
        int height = bitmap.getHeight() - i2;
        Matrix matrix3 = null;
        if (!z && (width < 0 || height < 0)) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            int iMax = Math.max(0, width / 2);
            int iMax2 = Math.max(0, height / 2);
            Rect rect = new Rect(iMax, iMax2, Math.min(i, bitmap.getWidth()) + iMax, Math.min(i2, bitmap.getHeight()) + iMax2);
            int iWidth = (i - rect.width()) / 2;
            int iHeight = (i2 - rect.height()) / 2;
            canvas.drawBitmap(bitmap, rect, new Rect(iWidth, iHeight, i - iWidth, i2 - iHeight), (Paint) null);
            if (z2) {
                bitmap.recycle();
            }
            canvas.setBitmap(null);
            return bitmapCreateBitmap;
        }
        float width2 = bitmap.getWidth();
        float height2 = bitmap.getHeight();
        float f = i;
        float f2 = i2;
        if (width2 / height2 > f / f2) {
            float f3 = f2 / height2;
            if (f3 < 0.9f || f3 > 1.0f) {
                matrix.setScale(f3, f3);
                matrix2 = matrix;
            } else {
                matrix2 = null;
            }
            matrix3 = matrix2;
        } else {
            float f4 = f / width2;
            if (f4 < 0.9f || f4 > 1.0f) {
                matrix.setScale(f4, f4);
                matrix3 = matrix;
            }
        }
        Bitmap bitmapCreateBitmap2 = matrix3 != null ? Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix3, true) : bitmap;
        if (z2 && bitmapCreateBitmap2 != bitmap) {
            bitmap.recycle();
        }
        Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(bitmapCreateBitmap2, Math.max(0, bitmapCreateBitmap2.getWidth() - i) / 2, Math.max(0, bitmapCreateBitmap2.getHeight() - i2) / 2, i, i2);
        if (bitmapCreateBitmap3 != bitmapCreateBitmap2 && (z2 || bitmapCreateBitmap2 != bitmap)) {
            bitmapCreateBitmap2.recycle();
        }
        return bitmapCreateBitmap3;
    }

    @Deprecated
    private static class SizedThumbnailBitmap {
        public Bitmap mBitmap;
        public byte[] mThumbnailData;
        public int mThumbnailHeight;
        public int mThumbnailWidth;

        private SizedThumbnailBitmap() {
        }
    }
}
