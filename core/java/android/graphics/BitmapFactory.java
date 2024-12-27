package android.graphics;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.ParcelFileDescriptor;
import android.os.Trace;
import android.util.Log;
import android.util.TypedValue;

import com.android.internal.R;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapFactory {
    private static final int DECODE_BUFFER_SIZE = 16384;
    private static final String TAG = "BitmapFactory";

    private static native Bitmap nativeDecodeAsset(
            long j, Rect rect, Options options, long j2, long j3);

    private static native Bitmap nativeDecodeByteArray(
            byte[] bArr, int i, int i2, Options options, long j, long j2);

    private static native Bitmap nativeDecodeFileDescriptor(
            FileDescriptor fileDescriptor, Rect rect, Options options, long j, long j2);

    private static native Bitmap nativeDecodeStream(
            InputStream inputStream, byte[] bArr, Rect rect, Options options, long j, long j2);

    private static native boolean nativeIsSeekable(FileDescriptor fileDescriptor);

    public static class Options {
        public Bitmap inBitmap;
        public int inDensity;
        public boolean inDither;

        @Deprecated public boolean inInputShareable;
        public boolean inJustDecodeBounds;
        public boolean inMutable;

        @Deprecated public boolean inPreferQualityOverSpeed;

        @Deprecated public boolean inPurgeable;
        public int inSampleSize;
        public int inScreenDensity;
        public int inTargetDensity;
        public byte[] inTempStorage;

        @Deprecated public boolean mCancel;
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

        static void validate(Options opts) {
            if (opts == null) {
                return;
            }
            if (opts.inBitmap != null) {
                if (opts.inBitmap.getConfig() == Bitmap.Config.HARDWARE) {
                    throw new IllegalArgumentException(
                            "Bitmaps with Config.HARDWARE are always immutable");
                }
                if (opts.inBitmap.isRecycled()) {
                    throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
            }
            if (opts.inMutable && opts.inPreferredConfig == Bitmap.Config.HARDWARE) {
                throw new IllegalArgumentException(
                        "Bitmaps with Config.HARDWARE cannot be decoded into - they are immutable");
            }
            if (opts.inPreferredColorSpace != null) {
                if (!(opts.inPreferredColorSpace instanceof ColorSpace.Rgb)) {
                    throw new IllegalArgumentException(
                            "The destination color space must use the RGB color model");
                }
                if (!opts.inPreferredColorSpace.equals(ColorSpace.get(ColorSpace.Named.BT2020_HLG))
                        && !opts.inPreferredColorSpace.equals(
                                ColorSpace.get(ColorSpace.Named.BT2020_PQ))
                        && ((ColorSpace.Rgb) opts.inPreferredColorSpace).getTransferParameters()
                                == null) {
                    throw new IllegalArgumentException(
                            "The destination color space must use an ICC parametric transfer"
                                    + " function");
                }
            }
        }

        static long nativeInBitmap(Options opts) {
            if (opts == null || opts.inBitmap == null) {
                return 0L;
            }
            opts.inBitmap.setGainmap(null);
            return opts.inBitmap.getNativeInstance();
        }

        static long nativeColorSpace(Options opts) {
            if (opts == null || opts.inPreferredColorSpace == null) {
                return 0L;
            }
            return opts.inPreferredColorSpace.getNativeInstance();
        }
    }

    /*  JADX ERROR: Types fix failed
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
    */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    public static android.graphics.Bitmap decodeFile(
            java.lang.String r17, android.graphics.BitmapFactory.Options r18) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.graphics.BitmapFactory.decodeFile(java.lang.String,"
                        + " android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    public static Bitmap decodeFile(String pathName) {
        return decodeFile(pathName, null);
    }

    public static Bitmap decodeResourceStream(
            Resources res, TypedValue value, InputStream is, Rect pad, Options opts) {
        Options.validate(opts);
        if (opts == null) {
            opts = new Options();
        }
        if (res != null && !opts.inCalledByResource) {
            opts.inCalledByResource = true;
        }
        if (opts.inDensity == 0 && value != null) {
            int density = value.density;
            if (density == 0) {
                opts.inDensity = 160;
            } else if (density != 65535) {
                opts.inDensity = density;
            }
        }
        if (opts.inTargetDensity == 0 && res != null) {
            opts.inTargetDensity = res.getDisplayMetrics().densityDpi;
        }
        return decodeStream(is, pad, opts);
    }

    public static Bitmap decodeResource(Resources res, int id, Options opts) {
        Options.validate(opts);
        Bitmap bm = null;
        InputStream is = null;
        try {
            try {
                TypedValue value = new TypedValue();
                is = res.openRawResource(id, value);
                bm = decodeResourceStream(res, value, is, null, opts);
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                if (is != null) {
                    is.close();
                }
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
        }
        if (bm == null && opts != null && opts.inBitmap != null) {
            throw new IllegalArgumentException("Problem decoding into existing bitmap");
        }
        return bm;
    }

    public static Bitmap decodeResource(Resources res, int id) {
        return decodeResource(res, id, null);
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length, Options opts) {
        if (opts != null) {
            opts.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
        }
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Options.validate(opts);
        Trace.traceBegin(2L, "decodeBitmap");
        try {
            Bitmap bm =
                    nativeDecodeByteArray(
                            data,
                            offset,
                            length,
                            opts,
                            Options.nativeInBitmap(opts),
                            Options.nativeColorSpace(opts));
            if (bm == null && opts != null && opts.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bm, opts);
            return bm;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length) {
        return decodeByteArray(data, offset, length, null);
    }

    private static void setDensityFromOptions(Bitmap outputBitmap, Options opts) {
        if (outputBitmap == null || opts == null) {
            return;
        }
        int density = opts.inDensity;
        if (density != 0) {
            outputBitmap.setDensity(density);
            int targetDensity = opts.inTargetDensity;
            if (targetDensity == 0 || density == targetDensity || density == opts.inScreenDensity) {
                return;
            }
            byte[] np = outputBitmap.getNinePatchChunk();
            boolean isNinePatch = np != null && NinePatch.isNinePatchChunk(np);
            if (opts.inScaled || isNinePatch) {
                outputBitmap.setDensity(targetDensity);
                return;
            }
            return;
        }
        if (opts.inBitmap != null) {
            outputBitmap.setDensity(Bitmap.getDefaultDensity());
        }
    }

    public static Bitmap decodeStream(InputStream is, Rect outPadding, Options opts) {
        Bitmap bm;
        if (opts != null) {
            opts.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
            opts.contentRedirectionToKumiho = is instanceof ParcelFileDescriptor.KumihoInputStream;
        }
        if (is == null) {
            return null;
        }
        Options.validate(opts);
        Trace.traceBegin(2L, "decodeBitmap");
        try {
            if (is instanceof AssetManager.AssetInputStream) {
                long asset = ((AssetManager.AssetInputStream) is).getNativeAsset();
                bm =
                        nativeDecodeAsset(
                                asset,
                                outPadding,
                                opts,
                                Options.nativeInBitmap(opts),
                                Options.nativeColorSpace(opts));
            } else {
                bm = decodeStreamInternal(is, outPadding, opts);
            }
            if (bm == null && opts != null && opts.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bm, opts);
            return bm;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    private static Bitmap decodeStreamInternal(InputStream is, Rect outPadding, Options opts) {
        byte[] tempStorage = opts != null ? opts.inTempStorage : null;
        if (tempStorage == null) {
            tempStorage = new byte[16384];
        }
        return nativeDecodeStream(
                is,
                tempStorage,
                outPadding,
                opts,
                Options.nativeInBitmap(opts),
                Options.nativeColorSpace(opts));
    }

    public static Bitmap decodeStream(InputStream is) {
        return decodeStream(is, null, null);
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options opts) {
        Bitmap bm;
        if (opts == null) {
            Log.w(TAG, "Null Options, creating a new one.");
            opts = new Options();
        }
        opts.contentRedirectionToKumiho = true;
        opts.messageOnCrash = Resources.getSystem().getString(R.string.message_on_crash);
        Options.validate(opts);
        Trace.traceBegin(2L, "decodeFileDescriptor");
        try {
            if (nativeIsSeekable(fd)) {
                bm =
                        nativeDecodeFileDescriptor(
                                fd,
                                outPadding,
                                opts,
                                Options.nativeInBitmap(opts),
                                Options.nativeColorSpace(opts));
            } else {
                FileInputStream fis = new FileInputStream(fd);
                try {
                    Bitmap bm2 = decodeStreamInternal(fis, outPadding, opts);
                    bm = bm2;
                } finally {
                    try {
                        fis.close();
                    } catch (Throwable th) {
                    }
                }
            }
            if (bm == null && opts != null && opts.inBitmap != null) {
                throw new IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(bm, opts);
            return bm;
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fd) {
        return decodeFileDescriptor(fd, null, null);
    }
}
