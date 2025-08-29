package com.android.systemui.screenshot;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.exifinterface.media.ExifInterface;
import com.android.systemui.screenshot.sep.SmartClipDataExtractor;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.media.SemExtendedFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class ImageExporter {
    public static String mCapturedAppInfo;
    public static int mHeight;
    public static String mImageDisplayName;
    public static String mImageFileName;
    public static String mImageFilePath;
    public static String mImageFileRelativePath;
    public static long mImageTime;
    public static String mMimeType;
    public static SmartClipDataExtractor.WebData mScreenshotsWebData;
    public static long mSecDate;
    public static long mSize;
    public static String mVolumeName;
    public static int mWidth;
    public Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    public final int mQuality = 100;
    public final ContentResolver mResolver;

    /* renamed from: com.android.systemui.screenshot.ImageExporter$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;

        static {
            int[] iArr = new int[Bitmap.CompressFormat.values().length];
            $SwitchMap$android$graphics$Bitmap$CompressFormat = iArr;
            try {
                iArr[Bitmap.CompressFormat.JPEG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP_LOSSLESS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP_LOSSY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    final class ImageExportException extends IOException {
        public ImageExportException(String str) {
            super(str);
        }

        public ImageExportException(String str, Throwable th) {
            super(str, th);
        }
    }

    public class Result {
        public String fileName;
        public Bitmap.CompressFormat format;
        public UUID requestId;
        public long timestamp;
        public Uri uri;

        public final String toString() {
            return "Result{uri=" + this.uri + ", requestId=" + this.requestId + ", fileName='" + this.fileName + "', timestamp=" + this.timestamp + ", format=" + this.format + '}';
        }
    }

    public class Task {
        public final Bitmap mBitmap;
        public final ZonedDateTime mCaptureTime;
        public final String mFileName;
        public final Bitmap.CompressFormat mFormat;
        public final UserHandle mOwner;
        public final int mQuality;
        public final UUID mRequestId;
        public final ContentResolver mResolver;

        public Task(ContentResolver contentResolver, UUID uuid, Bitmap bitmap, ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, int i, UserHandle userHandle, String str) {
            this(contentResolver, uuid, bitmap, zonedDateTime, compressFormat, i, userHandle, str, false);
        }

        public final Result execute() {
            Uri uriM2938$$Nest$smsemCreateEntry;
            String str = this.mFileName;
            Trace.beginSection("ImageExporter_execute");
            Result result = new Result();
            try {
                try {
                    uriM2938$$Nest$smsemCreateEntry = ImageExporter.m2938$$Nest$smsemCreateEntry(this.mResolver, this.mOwner);
                    try {
                        if (Thread.currentThread().isInterrupted()) {
                            throw new InterruptedException();
                        }
                        ImageExporter.m2940$$Nest$smwriteImage(this.mResolver, this.mBitmap, this.mFormat, this.mQuality, uriM2938$$Nest$smsemCreateEntry);
                        if (Thread.currentThread().isInterrupted()) {
                            throw new InterruptedException();
                        }
                        ImageExporter.m2939$$Nest$smwriteExif(this.mResolver, uriM2938$$Nest$smsemCreateEntry, this.mRequestId, this.mBitmap.getWidth(), this.mBitmap.getHeight(), this.mCaptureTime);
                        if (Thread.currentThread().isInterrupted()) {
                            throw new InterruptedException();
                        }
                        ImageExporter.m2937$$Nest$smpublishEntry(this.mResolver, uriM2938$$Nest$smsemCreateEntry, this.mOwner);
                        result.timestamp = this.mCaptureTime.toInstant().toEpochMilli();
                        result.requestId = this.mRequestId;
                        result.uri = uriM2938$$Nest$smsemCreateEntry;
                        result.fileName = str;
                        result.format = this.mFormat;
                        return result;
                    } catch (ImageExportException e) {
                        e = e;
                        if (uriM2938$$Nest$smsemCreateEntry != null) {
                            this.mResolver.delete(uriM2938$$Nest$smsemCreateEntry, null);
                        }
                        throw e;
                    }
                } finally {
                    Trace.endSection();
                }
            } catch (ImageExportException e2) {
                e = e2;
                uriM2938$$Nest$smsemCreateEntry = null;
            }
        }

        public final String toString() {
            return "export [" + this.mBitmap + "] to [" + this.mFormat + "] at quality " + this.mQuality;
        }

        public Task(ContentResolver contentResolver, UUID uuid, Bitmap bitmap, ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, int i, UserHandle userHandle, String str, boolean z) {
            this.mResolver = contentResolver;
            this.mRequestId = uuid;
            this.mBitmap = bitmap;
            this.mCaptureTime = zonedDateTime;
            this.mFormat = compressFormat;
            this.mQuality = i;
            this.mOwner = userHandle;
            this.mFileName = str;
        }
    }

    /* renamed from: -$$Nest$smpublishEntry, reason: not valid java name */
    public static void m2937$$Nest$smpublishEntry(ContentResolver contentResolver, Uri uri, UserHandle userHandle) {
        Trace.beginSection("ImageExporter_publishEntry");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_pending", (Integer) 0);
            contentValues.putNull("date_expires");
            if (contentResolver.update(uri, contentValues, null) < 1) {
                throw new ImageExportException("Failed to publish entry. ContentResolver#update reported no rows updated.");
            }
            if (userHandle.getIdentifier() == UserHandle.myUserId()) {
                semCreateSecmpEntry(contentResolver);
            }
        } finally {
            Trace.endSection();
        }
    }

    /* renamed from: -$$Nest$smsemCreateEntry, reason: not valid java name */
    public static Uri m2938$$Nest$smsemCreateEntry(ContentResolver contentResolver, UserHandle userHandle) {
        Trace.beginSection("ImageExporter_semCreateEntry");
        try {
            Uri uriInsert = contentResolver.insert(ContentProvider.maybeAddUserId(MediaStore.Images.Media.getContentUri(mVolumeName), userHandle.getIdentifier()), semCreateMetadata());
            if (uriInsert == null) {
                throw new ImageExportException("ContentResolver#insert returned null.");
            }
            Log.d("Screenshot", "Inserted new URI: " + uriInsert);
            return uriInsert;
        } finally {
            Trace.endSection();
        }
    }

    /* renamed from: -$$Nest$smwriteExif, reason: not valid java name */
    public static void m2939$$Nest$smwriteExif(ContentResolver contentResolver, Uri uri, UUID uuid, int i, int i2, ZonedDateTime zonedDateTime) {
        Trace.beginSection("ImageExporter_writeExif");
        try {
            try {
                ParcelFileDescriptor parcelFileDescriptorOpenFile = contentResolver.openFile(uri, "rw", null);
                if (parcelFileDescriptorOpenFile == null) {
                    throw new ImageExportException("ContentResolver#openFile returned null.");
                }
                try {
                    ExifInterface exifInterface = new ExifInterface(parcelFileDescriptorOpenFile.getFileDescriptor());
                    updateExifAttributes(exifInterface, uuid, i, i2, zonedDateTime);
                    try {
                        exifInterface.saveAttributes();
                        Charset charset = StandardCharsets.UTF_8;
                        SemExtendedFormat.addData(parcelFileDescriptorOpenFile, "Samsung_Capture_Info", "Screenshot".getBytes(charset), 3153, 1);
                        SemExtendedFormat.addData(parcelFileDescriptorOpenFile, "Captured_App_Info", mCapturedAppInfo.getBytes(charset), 3489, 1);
                        FileUtils.closeQuietly(parcelFileDescriptorOpenFile);
                        Trace.endSection();
                    } catch (IOException e) {
                        throw new ImageExportException("ExifInterface threw an exception writing to the file descriptor.", e);
                    }
                } catch (IOException e2) {
                    throw new ImageExportException("ExifInterface threw an exception reading from the file descriptor.", e2);
                }
            } catch (Throwable th) {
                FileUtils.closeQuietly((AutoCloseable) null);
                Trace.endSection();
                throw th;
            }
        } catch (FileNotFoundException e3) {
            throw new ImageExportException("ContentResolver#openFile threw an exception.", e3);
        }
    }

    /* renamed from: -$$Nest$smwriteImage, reason: not valid java name */
    public static void m2940$$Nest$smwriteImage(ContentResolver contentResolver, Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i, Uri uri) {
        Trace.beginSection("ImageExporter_writeImage");
        try {
            try {
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uri);
                try {
                    SystemClock.elapsedRealtime();
                    if (!bitmap.compress(compressFormat, i, outputStreamOpenOutputStream)) {
                        throw new ImageExportException("Bitmap.compress returned false. (Failure unknown)");
                    }
                    if (outputStreamOpenOutputStream != null) {
                        outputStreamOpenOutputStream.close();
                    }
                } catch (Throwable th) {
                    if (outputStreamOpenOutputStream != null) {
                        try {
                            outputStreamOpenOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } finally {
                Trace.endSection();
            }
        } catch (IOException e) {
            throw new ImageExportException("ContentResolver#openOutputStream threw an exception.", e);
        }
    }

    static {
        Duration.ofHours(24L);
        String str = Environment.DIRECTORY_DCIM;
        String str2 = File.separator;
        String str3 = Environment.DIRECTORY_SCREENSHOTS;
    }

    public ImageExporter(ContentResolver contentResolver) {
        this.mResolver = contentResolver;
    }

    public static String createFilename(ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, int i) {
        return i == 0 ? String.format("Screenshot_%1$tY%<tm%<td-%<tH%<tM%<tS.%2$s", zonedDateTime, fileExtension(compressFormat)) : String.format("Screenshot_%1$tY%<tm%<td-%<tH%<tM%<tS-display-%2$d.%3$s", zonedDateTime, Integer.valueOf(i), fileExtension(compressFormat));
    }

    public static String createSystemFileDisplayName(String str, Bitmap.CompressFormat compressFormat) {
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ".");
        sbM.append(fileExtension(compressFormat));
        return sbM.toString();
    }

    public static String fileExtension(Bitmap.CompressFormat compressFormat) {
        int i = AnonymousClass1.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i == 1) {
            return "jpg";
        }
        if (i == 2) {
            return "png";
        }
        if (i == 3 || i == 4 || i == 5) {
            return "webp";
        }
        throw new IllegalArgumentException("Unknown CompressFormat!");
    }

    public static ContentValues semCreateMetadata() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("relative_path", mImageFileRelativePath);
        contentValues.put(UniversalCredentialUtil.AGENT_TITLE, mImageDisplayName);
        contentValues.put("_display_name", mImageFileName);
        contentValues.put("datetaken", Long.valueOf(mImageTime));
        contentValues.put("date_added", Long.valueOf(mSecDate));
        contentValues.put("date_modified", Long.valueOf(mSecDate));
        contentValues.put("mime_type", mMimeType);
        contentValues.put("width", Integer.valueOf(mWidth));
        contentValues.put("height", Integer.valueOf(mHeight));
        contentValues.put("_size", Long.valueOf(mSize));
        contentValues.put("media_type", (Integer) 1);
        contentValues.put("is_pending", (Integer) 1);
        return contentValues;
    }

    public static void semCreateSecmpEntry(ContentResolver contentResolver) {
        Trace.beginSection("ImageExporter_semCreateSecmpEntry");
        try {
            Uri uriInsert = contentResolver.insert(Uri.parse("content://secmedia/media"), semCreateSecmpMetadata());
            if (uriInsert == null) {
                throw new ImageExportException("ContentResolver#insert returned null.");
            }
            Log.d("Screenshot", "Inserted new URI: " + uriInsert);
        } finally {
            Trace.endSection();
        }
    }

    public static ContentValues semCreateSecmpMetadata() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("relative_path", mImageFileRelativePath);
        contentValues.put(UniversalCredentialUtil.AGENT_TITLE, mImageDisplayName);
        contentValues.put("_display_name", mImageFileName);
        contentValues.put("datetaken", Long.valueOf(mImageTime));
        contentValues.put("date_added", Long.valueOf(mSecDate));
        contentValues.put("date_modified", Long.valueOf(mSecDate));
        contentValues.put("mime_type", mMimeType);
        contentValues.put("width", Integer.valueOf(mWidth));
        contentValues.put("height", Integer.valueOf(mHeight));
        contentValues.put("_size", Long.valueOf(mSize));
        contentValues.put("_data", mImageFilePath);
        contentValues.put("media_type", (Integer) 1);
        SmartClipDataExtractor.WebData webData = mScreenshotsWebData;
        if (webData != null) {
            contentValues.put("captured_url", webData.mUrl);
            contentValues.put("captured_app", mScreenshotsWebData.mAppPkgName);
        }
        return contentValues;
    }

    public static void updateExifAttributes(ExifInterface exifInterface, UUID uuid, int i, int i2, ZonedDateTime zonedDateTime) throws NumberFormatException {
        exifInterface.setAttribute("ImageUniqueID", uuid.toString());
        exifInterface.setAttribute("Software", "Android " + Build.DISPLAY);
        exifInterface.setAttribute("ImageWidth", Integer.toString(i));
        exifInterface.setAttribute("ImageLength", Integer.toString(i2));
        String str = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(zonedDateTime);
        String str2 = DateTimeFormatter.ofPattern("SSS").format(zonedDateTime);
        String str3 = DateTimeFormatter.ofPattern("xxx").format(zonedDateTime);
        exifInterface.setAttribute("DateTimeOriginal", str);
        exifInterface.setAttribute("SubSecTimeOriginal", str2);
        exifInterface.setAttribute("OffsetTimeOriginal", str3);
    }

    public final CallbackToFutureAdapter.SafeFuture export(Executor executor, UUID uuid, Bitmap bitmap, UserHandle userHandle, int i) {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.systemDefault());
        ContentResolver contentResolver = this.mResolver;
        Bitmap.CompressFormat compressFormat = this.mCompressFormat;
        return CallbackToFutureAdapter.getFuture(new ImageExporter$$ExternalSyntheticLambda0(executor, new Task(contentResolver, uuid, bitmap, zonedDateTimeNow, compressFormat, this.mQuality, userHandle, createFilename(zonedDateTimeNow, compressFormat, i))));
    }
}
