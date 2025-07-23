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
import android.util.Log;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;

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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
    
        if (r4 != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0042, code lost:
    
        libcore.io.IoBridge.closeAndSignalBlockedThreads(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005d, code lost:
    
        if (r4 == null) goto L30;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap decodeFile(java.lang.String r4, android.graphics.BitmapFactory.Options r5) {
        /*
            java.lang.String r0 = "Unable to decode file: "
            int r1 = android.os.Process.myUid()
            java.lang.String r2 = "package"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)
            android.content.pm.IPackageManager r2 = android.content.pm.IPackageManager.Stub.asInterface(r2)
            java.lang.String r3 = ""
            if (r2 == 0) goto L1e
            java.lang.String r3 = r2.getNameForUid(r1)     // Catch: android.os.RemoteException -> L1a
            goto L1e
        L1a:
            r1 = move-exception
            r1.printStackTrace()
        L1e:
            if (r5 == 0) goto L32
            if (r3 == 0) goto L32
            java.lang.String r1 = "com.tencent.mm"
            boolean r1 = r3.contains(r1)
            if (r1 == 0) goto L32
            int r1 = r5.inSampleSize
            r2 = 3
            if (r1 != r2) goto L32
            r1 = 2
            r5.inSampleSize = r1
        L32:
            android.graphics.BitmapFactory.Options.validate(r5)
            r1 = 0
            int r2 = android.system.OsConstants.O_RDONLY     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.io.FileDescriptor r4 = libcore.io.IoBridge.open(r4, r2)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            android.graphics.Bitmap r1 = decodeFileDescriptor(r4, r1, r5)     // Catch: java.lang.Exception -> L46 java.lang.Throwable -> L61
            if (r4 == 0) goto L60
        L42:
            libcore.io.IoBridge.closeAndSignalBlockedThreads(r4)     // Catch: java.io.IOException -> L60
            goto L60
        L46:
            r5 = move-exception
            goto L4c
        L48:
            r5 = move-exception
            goto L63
        L4a:
            r5 = move-exception
            r4 = r1
        L4c:
            java.lang.String r2 = "BitmapFactory"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L61
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L61
            r3.append(r5)     // Catch: java.lang.Throwable -> L61
            java.lang.String r5 = r3.toString()     // Catch: java.lang.Throwable -> L61
            android.util.Log.e(r2, r5)     // Catch: java.lang.Throwable -> L61
            if (r4 == 0) goto L60
            goto L42
        L60:
            return r1
        L61:
            r5 = move-exception
            r1 = r4
        L63:
            if (r1 == 0) goto L68
            libcore.io.IoBridge.closeAndSignalBlockedThreads(r1)     // Catch: java.io.IOException -> L68
        L68:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.BitmapFactory.decodeFile(java.lang.String, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    public static Bitmap decodeFile(String str) {
        return decodeFile(str, null);
    }

    public static Bitmap decodeResourceStream(Resources resources, TypedValue typedValue, InputStream inputStream, Rect rect, Options options) {
        int myUid = Process.myUid();
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        String str = "";
        if (asInterface != null) {
            try {
                str = asInterface.getNameForUid(myUid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (options != null && str != null && str.contains("com.tencent.mm") && options.inSampleSize == 3) {
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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0013, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0022, code lost:
    
        if (r3 == null) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0011, code lost:
    
        if (r3 != null) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap decodeResource(android.content.res.Resources r2, int r3, android.graphics.BitmapFactory.Options r4) {
        /*
            android.graphics.BitmapFactory.Options.validate(r4)
            r0 = 0
            android.util.TypedValue r1 = new android.util.TypedValue     // Catch: java.lang.Throwable -> L1a java.lang.Exception -> L21
            r1.<init>()     // Catch: java.lang.Throwable -> L1a java.lang.Exception -> L21
            java.io.InputStream r3 = r2.openRawResource(r3, r1)     // Catch: java.lang.Throwable -> L1a java.lang.Exception -> L21
            android.graphics.Bitmap r0 = decodeResourceStream(r2, r1, r3, r0, r4)     // Catch: java.lang.Throwable -> L17 java.lang.Exception -> L22
            if (r3 == 0) goto L25
        L13:
            r3.close()     // Catch: java.io.IOException -> L25
            goto L25
        L17:
            r2 = move-exception
            r0 = r3
            goto L1b
        L1a:
            r2 = move-exception
        L1b:
            if (r0 == 0) goto L20
            r0.close()     // Catch: java.io.IOException -> L20
        L20:
            throw r2
        L21:
            r3 = r0
        L22:
            if (r3 == 0) goto L25
            goto L13
        L25:
            if (r0 != 0) goto L36
            if (r4 == 0) goto L36
            android.graphics.Bitmap r2 = r4.inBitmap
            if (r2 != 0) goto L2e
            goto L36
        L2e:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Problem decoding into existing bitmap"
            r2.<init>(r3)
            throw r2
        L36:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.BitmapFactory.decodeResource(android.content.res.Resources, int, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
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
            Bitmap nativeDecodeByteArray = nativeDecodeByteArray(bArr, i, i2, options, Options.nativeInBitmap(options), Options.nativeColorSpace(options));
            if (nativeDecodeByteArray == null && options != null && options.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(nativeDecodeByteArray, options);
            return nativeDecodeByteArray;
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
        Bitmap decodeStreamInternal;
        if (options != null) {
            options.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
            options.contentRedirectionToKumiho = inputStream instanceof ParcelFileDescriptor.KumihoInputStream;
        }
        int myUid = Process.myUid();
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        String str = "";
        if (asInterface != null) {
            try {
                str = asInterface.getNameForUid(myUid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (options != null && str != null && str.contains("com.tencent.mm") && options.inSampleSize == 3) {
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
                decodeStreamInternal = nativeDecodeAsset(((AssetManager.AssetInputStream) inputStream).getNativeAsset(), rect, options2, Options.nativeInBitmap(options), Options.nativeColorSpace(options));
            } else {
                options2 = options;
                decodeStreamInternal = decodeStreamInternal(inputStream, rect, options2);
            }
            if (decodeStreamInternal == null && options2 != null && options2.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(decodeStreamInternal, options2);
            return decodeStreamInternal;
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
        Bitmap bitmap;
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
                bitmap = nativeDecodeFileDescriptor(fileDescriptor, rect, options2, Options.nativeInBitmap(options2), Options.nativeColorSpace(options2));
            } else {
                FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
                try {
                    Bitmap decodeStreamInternal = decodeStreamInternal(fileInputStream, rect, options2);
                    try {
                        fileInputStream.close();
                    } catch (Throwable unused) {
                    }
                    bitmap = decodeStreamInternal;
                } finally {
                }
            }
            if (bitmap == null && options2 != null && options2.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bitmap, options2);
            return bitmap;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        return decodeFileDescriptor(fileDescriptor, null, null);
    }
}
