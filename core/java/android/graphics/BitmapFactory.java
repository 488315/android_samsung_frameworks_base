package android.graphics;

import android.content.p001om.SamsungThemeConstants;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.drm.DrmInfo;
import android.drm.DrmInfoRequest;
import android.drm.DrmManagerClient;
import android.p009os.ParcelFileDescriptor;
import android.p009os.Trace;
import android.util.Log;
import android.util.TypedValue;
import com.android.internal.widget.MessagingMessage;
import com.google.android.mms.ContentType;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class BitmapFactory {
  private static final int DECODE_BUFFER_SIZE = 16384;
  private static final String TAG = "BitmapFactory";

  private static native Bitmap nativeDecodeAsset(
      long j, Rect rect, Options options, long j2, long j3);

  private static native Bitmap nativeDecodeAssetQMG(
      long j, Rect rect, Options options, long j2, long j3);

  private static native Bitmap nativeDecodeByteArray(
      byte[] bArr, int i, int i2, Options options, long j, long j2);

  private static native Bitmap nativeDecodeFileDescriptor(
      FileDescriptor fileDescriptor, Rect rect, Options options, long j, long j2);

  private static native Bitmap nativeDecodeStream(
      InputStream inputStream, byte[] bArr, Rect rect, Options options, long j, long j2);

  private static native Bitmap nativeDecodeStreamQMG(
      InputStream inputStream, byte[] bArr, Rect rect, Options options, long j, long j2);

  private static native boolean nativeIsQMG(InputStream inputStream);

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
    public boolean inCalledByResource = false;
    public boolean contentRedirectionToKumiho = false;
    public Bitmap.Config inPreferredConfig = Bitmap.Config.ARGB_8888;
    public ColorSpace inPreferredColorSpace = null;
    public boolean semIsPreview = false;
    public boolean semInApplyPhotoHdr = false;
    public boolean semInCreateGainmap = false;
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
      Bitmap bitmap = opts.inBitmap;
      if (bitmap != null) {
        if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
          throw new IllegalArgumentException("Bitmaps with Config.HARDWARE are always immutable");
        }
        if (opts.inBitmap.isRecycled()) {
          throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
        }
      }
      if (opts.inMutable && opts.inPreferredConfig == Bitmap.Config.HARDWARE) {
        throw new IllegalArgumentException(
            "Bitmaps with Config.HARDWARE cannot be decoded into - they are immutable");
      }
      ColorSpace colorSpace = opts.inPreferredColorSpace;
      if (colorSpace != null) {
        if (!(colorSpace instanceof ColorSpace.Rgb)) {
          throw new IllegalArgumentException(
              "The destination color space must use the RGB color model");
        }
        if (!colorSpace.equals(ColorSpace.get(ColorSpace.Named.BT2020_HLG))
            && !opts.inPreferredColorSpace.equals(ColorSpace.get(ColorSpace.Named.BT2020_PQ))
            && ((ColorSpace.Rgb) opts.inPreferredColorSpace).getTransferParameters() == null) {
          throw new IllegalArgumentException(
              "The destination color space must use an ICC parametric transfer function");
        }
      }
    }

    static long nativeInBitmap(Options opts) {
      Bitmap bitmap;
      if (opts == null || (bitmap = opts.inBitmap) == null) {
        return 0L;
      }
      bitmap.setGainmap(null);
      return opts.inBitmap.getNativeInstance();
    }

    static long nativeColorSpace(Options opts) {
      ColorSpace colorSpace;
      if (opts == null || (colorSpace = opts.inPreferredColorSpace) == null) {
        return 0L;
      }
      return colorSpace.getNativeInstance();
    }
  }

  public static Bitmap decodeFile(String pathName, Options opts) {
    Options.validate(opts);
    Bitmap bm = null;
    InputStream stream = null;
    try {
      try {
        if (pathName != null) {
          try {
            if (pathName.endsWith(".dcf")) {
              stream = null;
              DrmManagerClient drmClient = new DrmManagerClient(null);
              String drmMimetype = drmClient.getOriginalMimeType(pathName);
              Log.m98i(TAG, "decodeFile drmMimetype = " + drmMimetype);
              if (drmMimetype != null
                  && drmMimetype.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                int rightStatus = drmClient.checkRightsStatus(pathName, 7);
                if (rightStatus == 0) {
                  long fileLength = new File(pathName).length();
                  DrmInfoRequest drmInfoRequest =
                      new DrmInfoRequest(10, ContentType.APP_DRM_CONTENT);
                  drmInfoRequest.put(DrmInfoRequest.SEM_DRM_PATH, pathName);
                  drmInfoRequest.put("LENGTH", Long.valueOf(fileLength).toString());
                  DrmInfo resultInfo = drmClient.acquireDrmInfo(drmInfoRequest);
                  String status = resultInfo.get("status").toString();
                  Log.m98i(TAG, "decodeFile acquireDrmInfo status is " + status);
                  if (!status.equals("success")) {
                    Log.m96e(TAG, "decodeFile FAIL status = " + resultInfo.get("INFO"));
                  } else if (resultInfo.getData() != null) {
                    stream = new ByteArrayInputStream(resultInfo.getData());
                  } else {
                    Log.m96e(TAG, "decodeFile acquireDrmInfo resultInfo is null");
                  }
                } else {
                  Log.m98i(TAG, "decodeFile Rights not present. rightStatus = " + rightStatus);
                }
              }
              drmClient.release();
            } else {
              stream = new FileInputStream(pathName);
            }
          } catch (Exception e) {
            Log.m96e(TAG, "Unable to decode stream: " + e);
            if (stream != null) {
              stream.close();
            }
          }
        }
        if (stream != null) {
          if (pathName.startsWith("/data/overlays")
              || pathName.startsWith(SamsungThemeConstants.DATA_APP_DIR)) {
            InputStream checkStream = new FileInputStream(pathName);
            if (isQMGImage(checkStream)) {
              bm = decodeStreamQMG(stream, null, opts);
            }
          }
          if (bm == null) {
            bm = decodeStream(stream, null, opts);
          }
          stream.close();
        }
        if (stream != null) {
          stream.close();
        }
      } finally {
      }
    } catch (IOException e2) {
    }
    return bm;
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
    if (value != null && value.string != null && res != null && res.getAssets() != null) {
      String file = value.string.toString();
      String apkPath = res.getAssets().getApkPaths()[value.assetCookie - 1];
      try {
        if (apkPath.startsWith("/data/overlays")
            || apkPath.startsWith(SamsungThemeConstants.DATA_APP_DIR)) {
          InputStream checkStream = res.getAssets().openNonAsset(value.assetCookie, file, 2);
          if (isQMGImage(checkStream)) {
            Bitmap bm = decodeStreamQMG(is, pad, opts);
            if (bm != null) {
              return bm;
            }
          }
        }
      } catch (IOException ioe) {
        Log.m96e(TAG, "[QMG] Failed to read input stream: " + ioe);
      }
    }
    return decodeStream(is, pad, opts);
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x001b -> B:7:0x0030). Please report as a decompilation issue!!! */
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

  public static Bitmap decodeStreamQMG(InputStream is, Rect outPadding, Options opts) {
    Bitmap bm;
    if (is == null) {
      return null;
    }
    Options.validate(opts);
    Trace.traceBegin(2L, "decodeBitmapQMG");
    try {
      if (is instanceof AssetManager.AssetInputStream) {
        long asset = ((AssetManager.AssetInputStream) is).getNativeAsset();
        bm =
            nativeDecodeAssetQMG(
                asset,
                outPadding,
                opts,
                Options.nativeInBitmap(opts),
                Options.nativeColorSpace(opts));
      } else {
        bm = decodeStreamInternalQMG(is, outPadding, opts);
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

  private static boolean isQMGImage(InputStream is) {
    byte[] buffer = new byte[2];
    try {
      is.read(buffer);
      char h0 = (char) buffer[0];
      char h1 = (char) buffer[1];
      is.close();
      if (h0 != 'Q' || h1 != 'G') {
        return false;
      }
      return true;
    } catch (IOException ioe) {
      Log.m96e(TAG, "Failed to read input stream: " + ioe);
      return false;
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

  private static Bitmap decodeStreamInternalQMG(InputStream is, Rect outPadding, Options opts) {
    byte[] tempStorage = opts != null ? opts.inTempStorage : null;
    if (tempStorage == null) {
      tempStorage = new byte[16384];
    }
    return nativeDecodeStreamQMG(
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
    Options.validate(opts);
    Trace.traceBegin(2L, "decodeFileDescriptor");
    try {
      if (nativeIsSeekable(fd)) {
        bm =
            nativeDecodeFileDescriptor(
                fd, outPadding, opts, Options.nativeInBitmap(opts), Options.nativeColorSpace(opts));
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
