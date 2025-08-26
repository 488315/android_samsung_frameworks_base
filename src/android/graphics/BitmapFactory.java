package android.graphics;

import android.content.pm.IPackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Trace;
import android.system.OsConstants;
import android.util.Log;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import libcore.io.IoBridge;

/* loaded from: classes.dex */
public class BitmapFactory {
    private static final int DECODE_BUFFER_SIZE = 16384;
    private static final String TAG = "BitmapFactory";
    private static final int WECHAT_SAMPLE_SIZE_2 = 2;
    private static final int WECHAT_SAMPLE_SIZE_3 = 3;

    private static native Bitmap nativeDecodeAsset(long j, Rect rect, Options options, long j2, long j3);

    private static native Bitmap nativeDecodeByteArray(byte[] bArr, int i, int i2, Options options, long j, long j2);

    private static native Bitmap nativeDecodeFileDescriptor(FileDescriptor fileDescriptor, Rect rect, Options options, long j, long j2);

    private static native Bitmap nativeDecodeStream(InputStream inputStream, byte[] bArr, Rect rect, Options options, long j, long j2);

    private static native boolean nativeIsSeekable(FileDescriptor fileDescriptor);

    public static class Options {
        public Bitmap inBitmap;
        public int inDensity;
        public boolean inDither;

        @Deprecated
        public boolean inInputShareable;
        public boolean inJustDecodeBounds;
        public boolean inMutable;

        @Deprecated
        public boolean inPreferQualityOverSpeed;

        @Deprecated
        public boolean inPurgeable;
        public int inSampleSize;
        public int inScreenDensity;
        public int inTargetDensity;
        public byte[] inTempStorage;

        @Deprecated
        public boolean mCancel;
        public ColorSpace outColorSpace;
        public Bitmap.Config outConfig;
        public int outHeight;
        public String outMimeType;
        public int outWidth;
        public String messageOnCrash = null;
        public boolean inCalledByResource = false;
        public boolean contentRedirectionToKumiho = false;
        public Bitmap.Config inPreferredConfig = Bitmap.Config.ARGB_8888;
        public ColorSpace inPreferredColorSpace = null;
        public boolean semIsPreview = false;
        public boolean semInApplyPhotoHdr = false;
        public boolean semInCreateGainmap = false;
        public int semBitmapDecodeMode = 0;
        public boolean inScaled = true;
        public boolean inPremultiplied = true;

        @Deprecated
        public void requestCancelDecode() {
            this.mCancel = true;
        }

        static void validate(Options options) {
            if (options == null) {
                return;
            }
            Bitmap bitmap = options.inBitmap;
            if (bitmap != null) {
                if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
                    throw new IllegalArgumentException("Bitmaps with Config.HARDWARE are always immutable");
                }
                if (options.inBitmap.isRecycled()) {
                    throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
            }
            if (options.inMutable && options.inPreferredConfig == Bitmap.Config.HARDWARE) {
                throw new IllegalArgumentException("Bitmaps with Config.HARDWARE cannot be decoded into - they are immutable");
            }
            ColorSpace colorSpace = options.inPreferredColorSpace;
            if (colorSpace != null) {
                if (!(colorSpace instanceof ColorSpace.Rgb)) {
                    throw new IllegalArgumentException("The destination color space must use the RGB color model");
                }
                if (!colorSpace.equals(ColorSpace.get(ColorSpace.Named.BT2020_HLG)) && !options.inPreferredColorSpace.equals(ColorSpace.get(ColorSpace.Named.BT2020_PQ)) && ((ColorSpace.Rgb) options.inPreferredColorSpace).getTransferParameters() == null) {
                    throw new IllegalArgumentException("The destination color space must use an ICC parametric transfer function");
                }
            }
        }

        static long nativeInBitmap(Options options) {
            Bitmap bitmap;
            if (options == null || (bitmap = options.inBitmap) == null) {
                return 0L;
            }
            bitmap.setGainmap(null);
            return options.inBitmap.getNativeInstance();
        }

        static long nativeColorSpace(Options options) {
            ColorSpace colorSpace;
            if (options == null || (colorSpace = options.inPreferredColorSpace) == null) {
                return 0L;
            }
            return colorSpace.getNativeInstance();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0060 A[PHI: r1 r4
      0x0060: PHI (r1v8 android.graphics.Bitmap) = (r1v14 android.graphics.Bitmap), (r1v6 android.graphics.Bitmap), (r1v10 android.graphics.Bitmap) binds: [B:28:0x005d, B:19:0x0042, B:18:0x0040] A[DONT_GENERATE, DONT_INLINE]
      0x0060: PHI (r4v5 java.lang.String) = (r4v7 java.lang.String), (r4v8 java.lang.String), (r4v9 java.lang.String) binds: [B:28:0x005d, B:19:0x0042, B:18:0x0040] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0042 A[EXC_TOP_SPLITTER, PHI: r1 r4
      0x0042: PHI (r1v6 android.graphics.Bitmap) = (r1v15 android.graphics.Bitmap), (r1v10 android.graphics.Bitmap) binds: [B:28:0x005d, B:18:0x0040] A[DONT_GENERATE, DONT_INLINE]
      0x0042: PHI (r4v4 ??) = (r4v10 ??), (r4v11 ??) binds: [B:28:0x005d, B:18:0x0040] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.FileDescriptor] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.FileDescriptor] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.FileDescriptor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap decodeFile(String str, Options options) throws Throwable {
        ?? Open;
        ?? r4;
        int iMyUid = Process.myUid();
        IPackageManager iPackageManagerAsInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        String nameForUid = "";
        if (iPackageManagerAsInterface != null) {
            try {
                nameForUid = iPackageManagerAsInterface.getNameForUid(iMyUid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (options != null && nameForUid != null && nameForUid.contains("com.tencent.mm") && options.inSampleSize == 3) {
            options.inSampleSize = 2;
        }
        Options.validate(options);
        ?? r1 = null;
        bitmapDecodeFileDescriptor = null;
        Bitmap bitmapDecodeFileDescriptor = null;
        try {
            try {
                Open = IoBridge.open(str, OsConstants.O_RDONLY);
                try {
                    bitmapDecodeFileDescriptor = decodeFileDescriptor(Open, null, options);
                    str = Open;
                    r4 = Open;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Unable to decode file: " + e);
                    str = Open;
                    r4 = Open;
                    if (Open != 0) {
                    }
                    return bitmapDecodeFileDescriptor;
                }
            } catch (Throwable th) {
                th = th;
                r1 = str;
                if (r1 != null) {
                    try {
                        IoBridge.closeAndSignalBlockedThreads((FileDescriptor) r1);
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Open = 0;
        } catch (Throwable th2) {
            th = th2;
            if (r1 != null) {
            }
            throw th;
        }
        if (Open != 0) {
            try {
                IoBridge.closeAndSignalBlockedThreads((FileDescriptor) r4);
                str = r4;
            } catch (IOException unused2) {
            }
        }
        return bitmapDecodeFileDescriptor;
    }

    public static Bitmap decodeFile(String str) {
        return decodeFile(str, null);
    }

    public static Bitmap decodeResourceStream(Resources resources, TypedValue typedValue, InputStream inputStream, Rect rect, Options options) {
        int iMyUid = Process.myUid();
        IPackageManager iPackageManagerAsInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        String nameForUid = "";
        if (iPackageManagerAsInterface != null) {
            try {
                nameForUid = iPackageManagerAsInterface.getNameForUid(iMyUid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (options != null && nameForUid != null && nameForUid.contains("com.tencent.mm") && options.inSampleSize == 3) {
            options.inSampleSize = 2;
        }
        Options.validate(options);
        if (options == null) {
            options = new Options();
        }
        if (resources != null && !options.inCalledByResource) {
            options.inCalledByResource = true;
        }
        if (options.inDensity == 0 && typedValue != null) {
            int i = typedValue.density;
            if (i == 0) {
                options.inDensity = 160;
            } else if (i != 65535) {
                options.inDensity = i;
            }
        }
        if (options.inTargetDensity == 0 && resources != null) {
            options.inTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        return decodeStream(inputStream, rect, options);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0013 A[EXC_TOP_SPLITTER, PHI: r0 r3
      0x0013: PHI (r0v3 android.graphics.Bitmap) = (r0v9 android.graphics.Bitmap), (r0v7 android.graphics.Bitmap) binds: [B:15:0x0022, B:5:0x0011] A[DONT_GENERATE, DONT_INLINE]
      0x0013: PHI (r3v3 java.io.InputStream) = (r3v2 java.io.InputStream), (r3v5 java.io.InputStream) binds: [B:15:0x0022, B:5:0x0011] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap decodeResource(Resources resources, int i, Options options) throws Throwable {
        InputStream inputStreamOpenRawResource;
        Options.validate(options);
        InputStream inputStream = null;
        bitmapDecodeResourceStream = null;
        Bitmap bitmapDecodeResourceStream = null;
        try {
            TypedValue typedValue = new TypedValue();
            inputStreamOpenRawResource = resources.openRawResource(i, typedValue);
            try {
                bitmapDecodeResourceStream = decodeResourceStream(resources, typedValue, inputStreamOpenRawResource, null, options);
            } catch (Exception unused) {
                if (inputStreamOpenRawResource != null) {
                }
                if (bitmapDecodeResourceStream == null) {
                }
                return bitmapDecodeResourceStream;
            } catch (Throwable th) {
                th = th;
                inputStream = inputStreamOpenRawResource;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception unused3) {
            inputStreamOpenRawResource = null;
        } catch (Throwable th2) {
            th = th2;
        }
        if (inputStreamOpenRawResource != null) {
            try {
                inputStreamOpenRawResource.close();
            } catch (IOException unused4) {
            }
        }
        if (bitmapDecodeResourceStream == null || options == null || options.inBitmap == null) {
            return bitmapDecodeResourceStream;
        }
        throw new IllegalArgumentException("Problem decoding into existing bitmap");
    }

    public static Bitmap decodeResource(Resources resources, int i) {
        return decodeResource(resources, i, null);
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2, Options options) {
        if (options != null) {
            options.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
        }
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Options.validate(options);
        Trace.traceBegin(2L, "decodeBitmap");
        try {
            Bitmap bitmapNativeDecodeByteArray = nativeDecodeByteArray(bArr, i, i2, options, Options.nativeInBitmap(options), Options.nativeColorSpace(options));
            if (bitmapNativeDecodeByteArray == null && options != null && options.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bitmapNativeDecodeByteArray, options);
            return bitmapNativeDecodeByteArray;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeByteArray(byte[] bArr, int i, int i2) {
        return decodeByteArray(bArr, i, i2, null);
    }

    private static void setDensityFromOptions(Bitmap bitmap, Options options) {
        if (bitmap == null || options == null) {
            return;
        }
        int i = options.inDensity;
        if (i != 0) {
            bitmap.setDensity(i);
            int i2 = options.inTargetDensity;
            if (i2 == 0 || i == i2 || i == options.inScreenDensity) {
                return;
            }
            byte[] ninePatchChunk = bitmap.getNinePatchChunk();
            boolean z = ninePatchChunk != null && NinePatch.isNinePatchChunk(ninePatchChunk);
            if (options.inScaled || z) {
                bitmap.setDensity(i2);
                return;
            }
            return;
        }
        if (options.inBitmap != null) {
            bitmap.setDensity(Bitmap.getDefaultDensity());
        }
    }

    public static Bitmap decodeStream(InputStream inputStream, Rect rect, Options options) {
        Options options2;
        Bitmap bitmapDecodeStreamInternal;
        if (options != null) {
            options.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
            options.contentRedirectionToKumiho = inputStream instanceof ParcelFileDescriptor.KumihoInputStream;
        }
        int iMyUid = Process.myUid();
        IPackageManager iPackageManagerAsInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        String nameForUid = "";
        if (iPackageManagerAsInterface != null) {
            try {
                nameForUid = iPackageManagerAsInterface.getNameForUid(iMyUid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (options != null && nameForUid != null && nameForUid.contains("com.tencent.mm") && options.inSampleSize == 3) {
            options.inSampleSize = 2;
        }
        if (inputStream == null) {
            return null;
        }
        Options.validate(options);
        Trace.traceBegin(2L, "decodeBitmap");
        try {
            if (inputStream instanceof AssetManager.AssetInputStream) {
                options2 = options;
                bitmapDecodeStreamInternal = nativeDecodeAsset(((AssetManager.AssetInputStream) inputStream).getNativeAsset(), rect, options2, Options.nativeInBitmap(options), Options.nativeColorSpace(options));
            } else {
                options2 = options;
                bitmapDecodeStreamInternal = decodeStreamInternal(inputStream, rect, options2);
            }
            if (bitmapDecodeStreamInternal == null && options2 != null && options2.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bitmapDecodeStreamInternal, options2);
            return bitmapDecodeStreamInternal;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    private static Bitmap decodeStreamInternal(InputStream inputStream, Rect rect, Options options) {
        byte[] bArr = options != null ? options.inTempStorage : null;
        if (bArr == null) {
            bArr = new byte[16384];
        }
        return nativeDecodeStream(inputStream, bArr, rect, options, Options.nativeInBitmap(options), Options.nativeColorSpace(options));
    }

    public static Bitmap decodeStream(InputStream inputStream) {
        return decodeStream(inputStream, null, null);
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor, Rect rect, Options options) {
        Bitmap bitmapNativeDecodeFileDescriptor;
        if (options == null) {
            Log.w(TAG, "Null Options, creating a new one.");
            options = new Options();
        }
        Options options2 = options;
        options2.contentRedirectionToKumiho = true;
        options2.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
        Options.validate(options2);
        Trace.traceBegin(2L, "decodeFileDescriptor");
        try {
            if (nativeIsSeekable(fileDescriptor)) {
                bitmapNativeDecodeFileDescriptor = nativeDecodeFileDescriptor(fileDescriptor, rect, options2, Options.nativeInBitmap(options2), Options.nativeColorSpace(options2));
            } else {
                FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
                try {
                    Bitmap bitmapDecodeStreamInternal = decodeStreamInternal(fileInputStream, rect, options2);
                    try {
                        fileInputStream.close();
                    } catch (Throwable unused) {
                    }
                    bitmapNativeDecodeFileDescriptor = bitmapDecodeStreamInternal;
                } finally {
                }
            }
            if (bitmapNativeDecodeFileDescriptor == null && options2 != null && options2.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bitmapNativeDecodeFileDescriptor, options2);
            return bitmapNativeDecodeFileDescriptor;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        return decodeFileDescriptor(fileDescriptor, null, null);
    }
}
