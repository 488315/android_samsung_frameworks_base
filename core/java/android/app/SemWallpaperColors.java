package android.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.samsung.android.audio.SoundTheme;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.colortheme.ColorPaletteCreator5;
import com.samsung.android.wallpaper.colortheme.ColorThemeExtractor;
import com.samsung.android.wallpaper.colortheme.StandardColorPaletteCreator;
import com.samsung.android.wallpaper.legibilitycolors.ColorHSV;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityAutoDim;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityColorByHSV;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityDefinition;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityLogic;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.image.BitmapHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class SemWallpaperColors implements Parcelable, Cloneable {
  public static final int COMPARE_ADAPTIVE_CONTRAST = 2;
  public static final int COMPARE_COLOR = 0;
  public static final int COMPARE_SHADOW = 1;
  public static final Parcelable.Creator<SemWallpaperColors> CREATOR =
      new Parcelable.Creator<SemWallpaperColors>() { // from class: android.app.SemWallpaperColors.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWallpaperColors createFromParcel(Parcel in) {
          return new SemWallpaperColors(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWallpaperColors[] newArray(int size) {
          return new SemWallpaperColors[size];
        }
      };
  private static final boolean DEBUG = false;
  private static final int DEVICE_VERSION = 22;
  public static final int FONT_COLOR_BLACK = 1;
  public static final int FONT_COLOR_GRAY = 2;
  public static final int FONT_COLOR_WHITE = 0;
  public static final int HOMESCREEN_BODY = 64;
  public static final int HOMESCREEN_NAVIBAR = 128;
  public static final int HOMESCREEN_STATUSBAR = 32;
  public static final int LOCKSCREEN_AREA_SIZE = 6;
  public static final int LOCKSCREEN_BACKGROUND = 512;
  public static final int LOCKSCREEN_BODY_BOTTOM = 128;
  public static final int LOCKSCREEN_BODY_MID = 64;
  public static final int LOCKSCREEN_BODY_TOP = 32;
  public static final long LOCKSCREEN_BOUNCER = 8796093022208L;
  public static final long LOCKSCREEN_CLOCK = 17179869184L;
  public static final long LOCKSCREEN_FINGERPRINT = 4398046511104L;
  public static final long LOCKSCREEN_HELP_TEXT = 274877906944L;
  public static final long LOCKSCREEN_LOCK_ICON = 8589934592L;
  public static final long LOCKSCREEN_MUSIC = 137438953472L;
  public static final int LOCKSCREEN_NAVIBAR = 256;
  public static final long LOCKSCREEN_NAVI_BAR = 1099511627776L;
  public static final long LOCKSCREEN_NIO = 34359738368L;
  public static final long LOCKSCREEN_NIO_TEXT = 68719476736L;
  public static final long LOCKSCREEN_NIO_TEXT_MID = 562949953421312L;
  public static final long LOCKSCREEN_NIO_TEXT_TOP = 281474976710656L;
  public static final long LOCKSCREEN_SECURE_TEXT = 2199023255552L;
  public static final long LOCKSCREEN_SHORTCUT = 549755813888L;
  public static final int LOCKSCREEN_STATUSBAR = 16;
  public static final long LOCKSCREEN_STATUS_BAR = 4294967296L;
  private static final String TAG = "SemWallpaperColors";
  private static final int TYPE_MAJOR = 0;
  private static final int TYPE_MINOR = 1;
  private static final int TYPE_OTHER = 2;
  private int mAdaptiveDimColor;
  private float mAdaptiveDimOpacity;
  private SemWallpaperColorsArea mArea;
  private List<int[][]> mColorTableList;
  private List<int[][]> mColorTableListGoogle;
  private Context mContext;
  private String mCurrentResolution;
  private float mDarkModeDimOpacity;
  private ArrayList<WallpaperColorsData> mDataList;
  private int mRotation;
  private int[] mSeedColors;
  private int mWhich;

  public static int getDeviceVersion() {
    Log.m94d(TAG, "version 22");
    return 22;
  }

  public static int getXmlVersion(String xml) {
    if (xml == null || !xml.contains("<Version>")) {
      return 0;
    }
    try {
      int before = xml.indexOf("<Version>");
      int after = xml.indexOf("</Version>");
      int version = Integer.parseInt(xml.substring(before + 9, after));
      return version;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public static String getLegibilityVersion() {
    return LegibilityDefinition.VERSION;
  }

  public static SemWallpaperColors fromBitmap(
      Context context, Bitmap bitmap, Rect[] rects, boolean includeDefaultArea) {
    int which = 0;
    if (includeDefaultArea) {
      which = 2;
    }
    return fromBitmap(context, bitmap, which, false, rects);
  }

  public static SemWallpaperColors fromBitmap(
      Context context, Bitmap bitmap, int which, boolean landscape, Rect[] rects) {
    return fromBitmap(context, bitmap, which, landscape ? 90 : 0, rects);
  }

  public static SemWallpaperColors fromBitmap(
      Context context, Bitmap bitmap, int which, int rotation, Rect[] rects) {
    return new SemWallpaperColors(context, bitmap, which, rotation, rects, null);
  }

  public static SemWallpaperColors fromBitmap(
      Context context,
      Bitmap bitmap,
      int which,
      int rotation,
      Rect[] rects,
      WallpaperColorOverrideAreas baseOverrideColorArea) {
    Log.m94d(TAG, "fromBitmap " + which);
    if (bitmap == null) {
      throw new IllegalArgumentException("Bitmap can't be null");
    }
    if (rects != null) {
      for (Rect rect : rects) {
        if (rect.left < 0
            || rect.top < 0
            || rect.right > bitmap.getWidth()
            || rect.bottom > bitmap.getHeight()) {
          throw new IllegalArgumentException("illegal argument " + rect);
        }
      }
    }
    return new SemWallpaperColors(context, bitmap, which, rotation, rects, baseOverrideColorArea);
  }

  public static SemWallpaperColors fromXml(String xml) {
    if (xml == null || xml.equals("")) {
      Log.m96e(TAG, "fromXml invalid xml " + xml);
      return null;
    }
    return new SemWallpaperColors(xml);
  }

  public static SemWallpaperColors getBlankWallpaperColors() {
    return new SemWallpaperColors(2, new Item(0, 1.0f, 0.1f), null);
  }

  public void save(String path) {
    Log.m94d(TAG, "save " + path);
    if (path == null) {
      Log.m96e(TAG, "save, path == null");
      return;
    }
    FileOutputStream fos = null;
    File file = new File(path);
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      try {
        try {
          fos = new FileOutputStream(file);
          fos.write(xmlGenerator().getBytes(StandardCharsets.UTF_8));
          Log.m94d(TAG, "save done");
          fos.close();
        } catch (Throwable th) {
          if (fos != null) {
            try {
              fos.close();
            } catch (IOException e2) {
              e2.printStackTrace();
            }
          }
          throw th;
        }
      } catch (Exception e3) {
        e3.printStackTrace();
        if (fos != null) {
          fos.close();
        }
      }
    } catch (IOException e4) {
      e4.printStackTrace();
    }
  }

  public Item get(long area) {
    if (!isHome(this.mWhich) && !isLock(this.mWhich)) {
      Log.m96e(TAG, "SemWallpaperColors is not support default area");
      return new Item(0, 1.0f, 0.5f);
    }
    Item item = null;
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (true) {
      if (!it.hasNext()) {
        break;
      }
      WallpaperColorsData data = it.next();
      if (data != null && area == data.getExternalKey()) {
        item = data.getItem();
        break;
      }
    }
    if (item == null) {
      Log.m94d(TAG, "returning default dummy Item " + area);
      Item item2 = new Item(0, 1.0f, 0.5f);
      return item2;
    }
    return item;
  }

  public Item get(Rect rect) {
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      if (data != null && data.getRect() != null && rect.equals(data.getRect())) {
        return data.getItem();
      }
    }
    return null;
  }

  public ArrayList<Rect> getKey() {
    ArrayList<Rect> keyList = new ArrayList<>();
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      keyList.add(data.getRect());
    }
    return keyList;
  }

  public int getWhich() {
    return this.mWhich;
  }

  public String getXml() {
    return xmlGenerator();
  }

  public int getAdaptiveDimColor() {
    return this.mAdaptiveDimColor;
  }

  public float getAdaptiveDimOpacity() {
    return this.mAdaptiveDimOpacity;
  }

  public int getDarkModeDimColor() {
    return -16777216;
  }

  public float getDarkModeDimOpacity() {
    return this.mDarkModeDimOpacity;
  }

  public SemWallpaperColors(Parcel in) {
    this.mWhich = 0;
    this.mRotation = 0;
    this.mCurrentResolution = null;
    this.mAdaptiveDimOpacity = 0.0f;
    this.mAdaptiveDimColor = 0;
    this.mDarkModeDimOpacity = 0.0f;
    this.mDataList = new ArrayList<>();
    this.mColorTableList = new ArrayList();
    this.mColorTableListGoogle = new ArrayList();
    int which = in.readInt();
    int count = in.readInt();
    int rotation = in.readInt();
    Context context = null;
    try {
      context = AppGlobals.getInitialApplication();
    } catch (Exception e) {
      Log.m96e(TAG, "SemWallpaperColors: from parcel: null context mode: " + e);
    }
    init(context, which, rotation);
    Log.m94d(
        TAG,
        "SemWallpaperColors: mWhich = "
            + this.mWhich
            + ", mRotation = "
            + this.mRotation
            + ", context = "
            + context);
    for (int i = 0; i < count; i++) {
      Rect rect = (Rect) in.readParcelable(Rect.class.getClassLoader());
      Item item = new Item();
      item.setFontColor(in.readInt());
      item.setFontColorRgb(in.readInt());
      item.setShadowSize(in.readFloat());
      item.setShadowOpacity(in.readFloat());
      item.setHSV(in.createFloatArray());
      WallpaperColorsData data = this.mDataList.get(i);
      if (data != null) {
        data.setRect(rect);
        data.setItem(item);
      } else {
        this.mDataList.add(new WallpaperColorsData(this, rect, item));
      }
    }
    workaround();
    this.mAdaptiveDimOpacity = in.readFloat();
    this.mAdaptiveDimColor = in.readInt();
    this.mDarkModeDimOpacity = in.readFloat();
    this.mSeedColors = in.createIntArray();
  }

  private SemWallpaperColors(String xml) {
    this.mWhich = 0;
    this.mRotation = 0;
    this.mCurrentResolution = null;
    this.mAdaptiveDimOpacity = 0.0f;
    this.mAdaptiveDimColor = 0;
    this.mDarkModeDimOpacity = 0.0f;
    this.mDataList = new ArrayList<>();
    this.mColorTableList = new ArrayList();
    this.mColorTableListGoogle = new ArrayList();
    xmlParser(xml);
    workaround();
  }

  private SemWallpaperColors(int which, Item item, Bitmap bitmap) {
    this.mWhich = 0;
    this.mRotation = 0;
    this.mCurrentResolution = null;
    this.mAdaptiveDimOpacity = 0.0f;
    this.mAdaptiveDimColor = 0;
    this.mDarkModeDimOpacity = 0.0f;
    this.mDataList = new ArrayList<>();
    this.mColorTableList = new ArrayList();
    this.mColorTableListGoogle = new ArrayList();
    init(null, which, 0);
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      data.setItem(item);
    }
    workaround();
    if (bitmap != null) {
      setSeedColors(bitmap);
    }
  }

  private SemWallpaperColors(
      Context context, Bitmap bitmap, int which, int rotation, Rect[] rects) {
    this(context, bitmap, which, rotation, rects, null);
  }

  private SemWallpaperColors(
      Context context,
      Bitmap bitmap,
      int which,
      int rotation,
      Rect[] rects,
      WallpaperColorOverrideAreas baseOverrideColorArea) {
    int[][] colorWeightList;
    this.mWhich = 0;
    this.mRotation = 0;
    this.mCurrentResolution = null;
    this.mAdaptiveDimOpacity = 0.0f;
    this.mAdaptiveDimColor = 0;
    this.mDarkModeDimOpacity = 0.0f;
    this.mDataList = new ArrayList<>();
    this.mColorTableList = new ArrayList();
    this.mColorTableListGoogle = new ArrayList();
    init(context, which, rotation, baseOverrideColorArea);
    try {
      if (isHome(which) || isLock(which)) {
        if (isLock(which)) {
          colorWeightList = new int[][] {new int[] {1, 2}};
        } else {
          colorWeightList = new int[0][];
        }
        calc(bitmap, 0, true, colorWeightList);
        calc(bitmap, 1, true, colorWeightList);
        calc(bitmap, 2, true, null);
      }
      if (rects != null) {
        for (Rect rect : rects) {
          this.mDataList.add(new WallpaperColorsData(this, rect, (Item) null));
        }
        calc(bitmap, 2, false, null);
      }
      calcAdaptiveDim();
      calcDarkModeDim();
      setSeedColors(bitmap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    workaround();
  }

  private void workaround() {
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      if (data.getItem() == null) {
        Log.m96e(TAG, "data.getItem() == null, " + data.getInternalKey());
        data.setItem(new Item(0, 1.0f, 0.1f));
      }
    }
  }

  private void init(Context context, int which, int rotation) {
    init(context, which, rotation, null);
  }

  private void init(
      Context context, int which, int rotation, WallpaperColorOverrideAreas baseOverrideColorArea) {
    this.mContext = context;
    this.mWhich = which;
    this.mRotation = rotation;
    this.mArea = new SemWallpaperColorsArea(context, which, rotation, baseOverrideColorArea);
    if (isLock(this.mWhich)) {
      this.mDataList.add(new WallpaperColorsData(this, 16L, 0));
      this.mDataList.add(new WallpaperColorsData(this, 32L, 2));
      this.mDataList.add(new WallpaperColorsData(this, 64L, 3));
      this.mDataList.add(new WallpaperColorsData(this, 128L, 4));
      this.mDataList.add(new WallpaperColorsData(this, 256L, 5));
      this.mDataList.add(new WallpaperColorsData(this, 512L, 7));
    } else if (isHome(this.mWhich)) {
      if (isWatchFaceLargeDisplay(which)) {
        this.mDataList.add(new WallpaperColorsData(this, 16L, 0));
        this.mDataList.add(new WallpaperColorsData(this, 32L, 2));
        this.mDataList.add(new WallpaperColorsData(this, 64L, 3));
        this.mDataList.add(new WallpaperColorsData(this, 128L, 4));
        this.mDataList.add(new WallpaperColorsData(this, 256L, 5));
        this.mDataList.add(new WallpaperColorsData(this, 512L, 7));
      } else if (isWatchFaceDisplay(which)) {
        this.mDataList.add(new WallpaperColorsData(this, 16L, 2));
        this.mDataList.add(new WallpaperColorsData(this, 32L, 9));
        this.mDataList.add(new WallpaperColorsData(this, 64L, 8));
        this.mDataList.add(new WallpaperColorsData(this, 128L, 10));
        this.mDataList.add(new WallpaperColorsData(this, 256L, 4));
        this.mDataList.add(new WallpaperColorsData(this, 512L, 7));
      } else if (isVirtualDisplay(which)) {
        this.mDataList.add(new WallpaperColorsData(this, 16L, 0));
        this.mDataList.add(new WallpaperColorsData(this, 32L, 2));
      } else {
        this.mDataList.add(new WallpaperColorsData(this, 32L, 0));
        this.mDataList.add(new WallpaperColorsData(this, 64L, 1));
        this.mDataList.add(new WallpaperColorsData(this, 128L, 6));
      }
    } else {
      Log.m94d(TAG, "init custom");
    }
    this.mArea.buildKeyMap(this.mDataList);
  }

  private void calc(Bitmap bitmap, int type, boolean defaultArea, int[][] colorWeightList) {
    if (type == 0 || type == 1) {
      for (int[] colorWeight : colorWeightList) {
        int index = colorWeight[type];
        WallpaperColorsData data = this.mDataList.get(index);
        Item major = null;
        if (type == 1) {
          major = this.mDataList.get(colorWeight[0]).getItem();
        }
        calcInternal(bitmap, defaultArea, data, major);
      }
      return;
    }
    if (type == 2) {
      Iterator<WallpaperColorsData> it = this.mDataList.iterator();
      while (it.hasNext()) {
        WallpaperColorsData data2 = it.next();
        calcInternal(bitmap, defaultArea, data2, null);
      }
      return;
    }
    Log.m96e(TAG, "calc, invalid type " + type);
  }

  private void calcInternal(
      Bitmap bitmap, boolean defaultArea, WallpaperColorsData data, Item major) {
    if (data.getItem() != null) {
      return;
    }
    Rect rect = data.getRect();
    if (defaultArea) {
      rect = this.mArea.get(data.getInternalKey(), bitmap.getWidth(), bitmap.getHeight());
    }
    if (rect.left >= 0
        && rect.top >= 0
        && rect.right - rect.left > 0
        && rect.bottom - rect.top > 0) {
      Bitmap cropBitmap =
          Bitmap.createBitmap(
              bitmap, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
      boolean z = false;
      if (!isWatchFaceLargeDisplay(this.mWhich)
          && (isWatchFaceDisplay(this.mWhich) || isVirtualDisplay(this.mWhich))) {
        data.setItem(fromBitmapInternal(cropBitmap, major, false));
        return;
      }
      if (defaultArea && this.mDataList.indexOf(data) == 0) {
        z = true;
      }
      data.setItem(fromBitmapInternal(cropBitmap, major, z));
    }
  }

  private Item fromBitmapInternal(Bitmap bitmap, Item major, boolean indicator) {
    float opacityMin;
    float opacityMax;
    float sizeMin;
    float sizeMin2;
    Log.m94d(
        TAG,
        "fromBitmap "
            + bitmap.getWidth()
            + ", "
            + bitmap.getHeight()
            + ", major = "
            + major
            + ", indicator = "
            + indicator);
    if (!indicator) {
      float descalingValue =
          BitmapHelper.fineScaleValueBySquareRootSize(bitmap.getWidth(), bitmap.getHeight(), 100);
      int scaledWidth = (int) (bitmap.getWidth() * descalingValue);
      int scaledHeight = (int) (bitmap.getHeight() * descalingValue);
      Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, false);
      LegibilityLogic.LegibilityResult majorLegibilityResult = null;
      if (major != null) {
        LegibilityDefinition.ColorType majorColorType = LegibilityDefinition.ColorType.LIGHT;
        if (major.getFontColor() == 1) {
          majorColorType = LegibilityDefinition.ColorType.DARK;
        }
        majorLegibilityResult =
            new LegibilityLogic.LegibilityResult(majorColorType, major.getHSV());
      }
      LegibilityLogic.LegibilityResult legibilityResult =
          LegibilityLogic.calculateTotalLegibilityResult(scaledBitmap, majorLegibilityResult, 0);
      int colorType = 0;
      if (legibilityResult.contentsColorType == LegibilityDefinition.ColorType.DARK) {
        colorType = 1;
      } else if (legibilityResult.contentsColorType == LegibilityDefinition.ColorType.GRAY) {
        colorType = 2;
      }
      int colorRgb = legibilityResult.adjustedContentsColor;
      String currentResolution = getCurrentResolution();
      if (legibilityResult.contentsColorType == LegibilityDefinition.ColorType.LIGHT) {
        opacityMin = 0.4f;
        opacityMax = 0.9f;
        if (currentResolution.equals("HD")) {
          sizeMin = 1.0f;
          sizeMin2 = 3.0f;
        } else {
          sizeMin = 2.0f;
          sizeMin2 = 6.0f;
        }
      } else {
        opacityMin = 0.3f;
        opacityMax = 0.9f;
        if (currentResolution.equals("HD")) {
          sizeMin = 1.0f;
          sizeMin2 = 3.0f;
        } else if (currentResolution.equals("FHD")) {
          sizeMin = 2.0f;
          sizeMin2 = 6.0f;
        } else {
          sizeMin = 3.0f;
          sizeMin2 = 6.0f;
        }
      }
      Log.m94d(
          TAG,
          "resolution = "
              + currentResolution
              + "size min = "
              + sizeMin
              + ", max = "
              + sizeMin2
              + ", opacity min = "
              + opacityMin
              + ", max = "
              + opacityMax);
      float size =
          LegibilityLogic.getInterpolatedShadowSize(
              legibilityResult.adaptiveShadowData, sizeMin, sizeMin2);
      float opacity =
          LegibilityLogic.getInterpolatedShadowOpacity(
              legibilityResult.adaptiveShadowData, opacityMin, opacityMax);
      Log.m94d(
          TAG,
          "colorType="
              + colorType
              + ", rgb="
              + colorRgb
              + ", shadowData="
              + size
              + "/"
              + opacity
              + " avgHSV= "
              + Arrays.toString(legibilityResult.avgHSV));
      Item item =
          new Item(colorType, colorRgb, size, opacity, legibilityResult.avgHSV, legibilityResult);
      scaledBitmap.recycle();
      return item;
    }
    Bitmap leftIndicator = getLeftIndicator(bitmap);
    Bitmap rightIndicator = getRightIndicator(bitmap);
    if (leftIndicator != null && rightIndicator != null) {
      float descalingValue2 =
          BitmapHelper.fineScaleValueBySquareRootSize(
              leftIndicator.getWidth(), leftIndicator.getHeight(), 100);
      int scaledWidth2 = (int) (leftIndicator.getWidth() * descalingValue2);
      int scaledHeight2 = (int) (leftIndicator.getHeight() * descalingValue2);
      Bitmap leftIndicator2 =
          Bitmap.createScaledBitmap(leftIndicator, scaledWidth2, scaledHeight2, false);
      float descalingValue3 =
          BitmapHelper.fineScaleValueBySquareRootSize(
              rightIndicator.getWidth(), rightIndicator.getHeight(), 100);
      int scaledWidth3 = (int) (rightIndicator.getWidth() * descalingValue3);
      int scaledHeight3 = (int) (rightIndicator.getHeight() * descalingValue3);
      Bitmap rightIndicator2 =
          Bitmap.createScaledBitmap(rightIndicator, scaledWidth3, scaledHeight3, false);
      LegibilityColorByHSV.EdgeCaseResultForIndicator indicatorLegibilityResult =
          LegibilityColorByHSV.calcurateIndicatorLegibility(
              getIndicatorPixels(leftIndicator2, rightIndicator2));
      int colorType2 = 0;
      if (indicatorLegibilityResult.colorType == LegibilityDefinition.ColorType.DARK) {
        colorType2 = 1;
      } else if (indicatorLegibilityResult.colorType == LegibilityDefinition.ColorType.GRAY) {
        colorType2 = 2;
      }
      int colorRgb2 = indicatorLegibilityResult.color;
      LegibilityLogic.LegibilityResult leftLegibilityResult =
          LegibilityLogic.calculateTotalLegibilityResult(
              leftIndicator2, null, indicatorLegibilityResult.colorType, 0);
      LegibilityLogic.LegibilityResult rightLegibilityResult =
          LegibilityLogic.calculateTotalLegibilityResult(
              rightIndicator2, null, indicatorLegibilityResult.colorType, 0);
      leftIndicator2.recycle();
      rightIndicator2.recycle();
      Log.m94d(TAG, "edgeCase " + colorType2 + ", " + Integer.toHexString(colorRgb2));
      Item item2 = new Item(colorType2, colorRgb2, leftLegibilityResult, rightLegibilityResult);
      return item2;
    }
    Log.m96e(TAG, "fromBitmap indicator left/right bitmap == null");
    return new Item(0, 1.0f, 0.1f);
  }

  private int[] getIndicatorPixels(Bitmap leftIndicator, Bitmap rightIndicator) {
    int[] indicatorPixels =
        new int
            [(leftIndicator.getWidth() * leftIndicator.getHeight())
                + (rightIndicator.getWidth() * rightIndicator.getHeight())];
    int[] leftIndicatorPixels = new int[leftIndicator.getWidth() * leftIndicator.getHeight()];
    int[] rightIndicatorPixels = new int[rightIndicator.getWidth() * rightIndicator.getHeight()];
    leftIndicator.getPixels(
        leftIndicatorPixels,
        0,
        leftIndicator.getWidth(),
        0,
        0,
        leftIndicator.getWidth(),
        leftIndicator.getHeight());
    rightIndicator.getPixels(
        rightIndicatorPixels,
        0,
        rightIndicator.getWidth(),
        0,
        0,
        rightIndicator.getWidth(),
        rightIndicator.getHeight());
    for (int i = 0; i < leftIndicatorPixels.length; i++) {
      indicatorPixels[i] = leftIndicatorPixels[i];
    }
    for (int i2 = 0; i2 < rightIndicatorPixels.length; i2++) {
      indicatorPixels[leftIndicatorPixels.length + i2] = rightIndicatorPixels[i2];
    }
    return indicatorPixels;
  }

  private Bitmap getLeftIndicator(Bitmap bitmap) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Rect rect = this.mArea.get(0);
    float scale = width / (rect.right - rect.left);
    float density = Resources.getSystem().getDisplayMetrics().density;
    int leftWidth = Math.max(1, Math.min(width, (int) (110.0f * density * scale)));
    return Bitmap.createBitmap(bitmap, 0, 0, leftWidth, height);
  }

  private Bitmap getRightIndicator(Bitmap bitmap) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Rect rect = this.mArea.get(0);
    float scale = width / (rect.right - rect.left);
    float density = Resources.getSystem().getDisplayMetrics().density;
    int rightWidth = Math.max(1, Math.min(width, (int) (76.0f * density * scale)));
    int offsetX = Math.max(0, width - rightWidth);
    return Bitmap.createBitmap(bitmap, offsetX, 0, rightWidth, height);
  }

  private void calcAdaptiveDim() {
    Item item;
    if (isHome(this.mWhich) && (isWatchFaceDisplay(this.mWhich) || isVirtualDisplay(this.mWhich))) {
      Log.m94d(TAG, "calcAdaptiveDim: Cover wallpaper, return");
      return;
    }
    if (!isLock(this.mWhich) && !isHome(this.mWhich)) {
      Log.m94d(TAG, "calcAdaptiveDim: Custom area, return");
      return;
    }
    ArrayList<LegibilityLogic.LegibilityResult> resultList = new ArrayList<>();
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      if (data != null
          && data.getInternalKey() != 7
          && data.getInternalKey() != 5
          && (item = data.getItem()) != null) {
        if (data.getInternalKey() == 0) {
          resultList.add(item.getLeftLegibilityResult());
          resultList.add(item.getRightLegibilityResult());
        } else {
          resultList.add(item.getLegibilityResult());
        }
      }
    }
    if (resultList.size() > 0) {
      LegibilityLogic.LegibilityResult[] resultArray =
          new LegibilityLogic.LegibilityResult[resultList.size()];
      LegibilityAutoDim.AutoDimResult autoDimResult =
          LegibilityAutoDim.calculateAdaptiveDim(
              (LegibilityLogic.LegibilityResult[]) resultList.toArray(resultArray));
      this.mAdaptiveDimOpacity = autoDimResult.opacity;
      this.mAdaptiveDimColor = autoDimResult.color;
      Log.m94d(
          TAG,
          "calcAdaptiveDim, "
              + this.mAdaptiveDimOpacity
              + ", "
              + Integer.toHexString(this.mAdaptiveDimColor));
    }
  }

  private void setSeedColors(Bitmap bitmap) {
    if (bitmap == null) {
      Log.m94d(TAG, "setSeedColors: bitmap is null.");
      return;
    }
    this.mColorTableList.clear();
    this.mColorTableListGoogle.clear();
    this.mSeedColors = ColorThemeExtractor.getSeedColors(bitmap);
  }

  private void calcDarkModeDim() {
    int targetKey;
    if (isLock(this.mWhich)) {
      targetKey = 7;
    } else {
      int targetKey2 = this.mWhich;
      if (isHome(targetKey2)) {
        if (isWatchFaceDisplay(this.mWhich)) {
          targetKey = 7;
        } else {
          int targetKey3 = this.mWhich;
          if (isVirtualDisplay(targetKey3)) {
            targetKey = 2;
          } else {
            targetKey = 1;
          }
        }
      } else {
        Log.m94d(TAG, "custom area, return");
        return;
      }
    }
    ColorExtractor.DominantColorResult[] dominantColorResults = null;
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (true) {
      if (!it.hasNext()) {
        break;
      }
      WallpaperColorsData data = it.next();
      if (data != null && data.getInternalKey() == targetKey) {
        dominantColorResults = data.getItem().getLegibilityResult().dominantColorResult;
        break;
      }
    }
    if (dominantColorResults == null) {
      Log.m96e(TAG, "dominantColorResult == null");
      return;
    }
    float[][] hsvColors = new float[dominantColorResults.length][];
    float avgBrightness = 0.0f;
    for (int i = 0; i < dominantColorResults.length; i++) {
      if (dominantColorResults[i].percentage == 0.0f) {
        hsvColors[i] = null;
      } else {
        hsvColors[i] = new float[3];
        Color.colorToHSV(dominantColorResults[i].color, hsvColors[i]);
        avgBrightness += hsvColors[i][2] * dominantColorResults[i].percentage;
      }
    }
    if (avgBrightness <= 0.6f) {
      this.mDarkModeDimOpacity = 0.15f;
    } else if (avgBrightness >= 0.8f) {
      this.mDarkModeDimOpacity = 0.25f;
    } else {
      this.mDarkModeDimOpacity = (0.5f * avgBrightness) - 0.15f;
    }
    Log.m94d(TAG, "calcDarkModeDim, " + this.mDarkModeDimOpacity);
  }

  private String xmlGenerator() {
    XmlSerializer serial = Xml.newSerializer();
    Writer writer = new StringWriter();
    try {
      serial.setOutput(writer);
      serial.startDocument(null, true);
      serial.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
      serial.startTag(null, "Version");
      serial.text("22");
      serial.endTag(null, "Version");
      serial.startTag(null, "Which");
      serial.text("" + this.mWhich);
      serial.endTag(null, "Which");
      serial.startTag(null, "AdaptiveDimOpacity");
      serial.text("" + this.mAdaptiveDimOpacity);
      serial.endTag(null, "AdaptiveDimOpacity");
      serial.startTag(null, "AdaptiveDimColor");
      serial.text("" + this.mAdaptiveDimColor);
      serial.endTag(null, "AdaptiveDimColor");
      serial.startTag(null, "DarkModeDimOpacity");
      serial.text("" + this.mDarkModeDimOpacity);
      serial.endTag(null, "DarkModeDimOpacity");
      int[] iArr = this.mSeedColors;
      if (iArr != null && iArr.length > 0) {
        serial.startTag(null, "SeedColors");
        serial.text(Arrays.toString(this.mSeedColors));
        serial.endTag(null, "SeedColors");
      }
      Iterator<WallpaperColorsData> it = this.mDataList.iterator();
      while (it.hasNext()) {
        WallpaperColorsData data = it.next();
        xmlWrite(serial, data.getRect(), data.getItem());
      }
      serial.endDocument();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return writer.toString();
  }

  private void xmlWrite(XmlSerializer serial, Rect rect, Item item) {
    if (rect != null && item != null) {
      try {
        serial.startTag(null, "Rect");
        serial.startTag(null, CoreSaConstant.DETAIL_FREEFORM_HIDE_LEFT);
        serial.text("" + rect.left);
        serial.endTag(null, CoreSaConstant.DETAIL_FREEFORM_HIDE_LEFT);
        serial.startTag(null, "Top");
        serial.text("" + rect.top);
        serial.endTag(null, "Top");
        serial.startTag(null, CoreSaConstant.DETAIL_FREEFORM_HIDE_RIGHT);
        serial.text("" + rect.right);
        serial.endTag(null, CoreSaConstant.DETAIL_FREEFORM_HIDE_RIGHT);
        serial.startTag(null, "Bottom");
        serial.text("" + rect.bottom);
        serial.endTag(null, "Bottom");
        serial.endTag(null, "Rect");
        serial.startTag(null, "Legibility");
        if (item.mHSV != null) {
          serial.startTag(null, "avgHSV");
          serial.text("" + IUXColorUtils.HSVToColor(item.getHSV()));
          serial.endTag(null, "avgHSV");
        }
        serial.startTag(null, "FontColor");
        serial.text("" + item.getFontColor());
        serial.endTag(null, "FontColor");
        serial.startTag(null, "FontColorRgb");
        serial.text("" + item.getFontColorRgb());
        serial.endTag(null, "FontColorRgb");
        serial.startTag(null, "ShadowSize");
        serial.text("" + item.getShadowSize());
        serial.endTag(null, "ShadowSize");
        serial.startTag(null, "ShadowOpacity");
        serial.text("" + item.getShadowOpacity());
        serial.endTag(null, "ShadowOpacity");
        serial.endTag(null, "Legibility");
        return;
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
    }
    Log.m96e(TAG, "xmlWrite check null");
  }

  private static int[] stringToIntArray(String string) {
    String[] strings =
        string
            .replace(NavigationBarInflaterView.SIZE_MOD_START, "")
            .replace(NavigationBarInflaterView.SIZE_MOD_END, "")
            .split(", ");
    int[] result = new int[strings.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = Integer.parseInt(strings[i]);
    }
    return result;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  private void xmlParser(String xml) {
    XmlPullParser parser;
    int eventType;
    Rect rect;
    Item item;
    int count;
    Log.m94d(TAG, "xmlParser");
    InputStream is = null;
    try {
      is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
      parser = factory.newPullParser();
      parser.setInput(new InputStreamReader(is, "UTF-8"));
      rect = new Rect();
      item = new Item();
      count = 0;
    } catch (UnsupportedEncodingException e2) {
      e2.printStackTrace();
      return;
    } catch (IOException e3) {
      e3.printStackTrace();
      return;
    } catch (XmlPullParserException e4) {
      e4.printStackTrace();
      return;
    }
    for (eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
      switch (eventType) {
        case 2:
          String startTag = parser.getName();
          if (startTag.equals("Which")) {
            int which = Integer.parseInt(parser.nextText());
            init(null, which, 0);
          }
          if (startTag.equals(SoundTheme.Default)) {
            init(null, 2, 0);
          }
          if (startTag.equals("AdaptiveDimOpacity")) {
            this.mAdaptiveDimOpacity = Float.parseFloat(parser.nextText());
          }
          if (startTag.equals("AdaptieDimColor")) {
            this.mAdaptiveDimColor = Integer.parseInt(parser.nextText());
          }
          if (startTag.equals("DarkModeDimOpacity")) {
            this.mDarkModeDimOpacity = Float.parseFloat(parser.nextText());
          }
          if (startTag.equals("Rect")) {
            rect = new Rect();
          }
          if (startTag.equals(CoreSaConstant.DETAIL_FREEFORM_HIDE_LEFT)) {
            rect.left = Integer.parseInt(parser.nextText());
          }
          if (startTag.equals("Top")) {
            rect.top = Integer.parseInt(parser.nextText());
          }
          if (startTag.equals(CoreSaConstant.DETAIL_FREEFORM_HIDE_RIGHT)) {
            rect.right = Integer.parseInt(parser.nextText());
          }
          if (startTag.equals("Bottom")) {
            rect.bottom = Integer.parseInt(parser.nextText());
          }
          if (startTag.equals("Legibility")) {
            item = new Item();
          }
          if (startTag.equals("avgHSV")) {
            float[] hsv = new float[3];
            int color = Integer.parseInt(parser.nextText());
            ColorHSV.colorToHSV(color, hsv);
            item.setHSV(hsv);
          }
          if (startTag.equals("FontColor")) {
            item.setFontColor(Integer.parseInt(parser.nextText()));
          }
          if (startTag.equals("FontColorRgb")) {
            item.setFontColorRgb(Integer.parseInt(parser.nextText()));
          }
          if (startTag.equals("ShadowSize")) {
            item.setShadowSize(Float.parseFloat(parser.nextText()));
          }
          if (startTag.equals("ShadowOpacity")) {
            item.setShadowOpacity(Float.parseFloat(parser.nextText()));
          }
          if (startTag.equals("SeedColors")) {
            String seeds = parser.nextText();
            if (!TextUtils.isEmpty(seeds)) {
              this.mSeedColors = stringToIntArray(seeds);
            }
          }
        case 3:
          String endTag = parser.getName();
          if (endTag.equals("Legibility") && count < this.mDataList.size()) {
            int count2 = count + 1;
            WallpaperColorsData data = this.mDataList.get(count);
            if (data != null) {
              if (item.getFontColorRgb() == 0) {
                if (item.getFontColor() == 0) {
                  item.setFontColorRgb(-1);
                } else {
                  item.setFontColorRgb(-16777216);
                }
              }
              data.setRect(rect);
              data.setItem(item);
            } else {
              this.mDataList.add(new WallpaperColorsData(this, rect, item));
            }
            count = count2;
          }
          break;
        default:
      }
      return;
    }
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0030 -> B:10:0x0040). Please report as a decompilation issue!!! */
  private static void saveBitmaptoJpeg(Bitmap bitmap, String path) {
    Log.m94d(TAG, "saveBitmaptoJpeg " + path);
    if (path == null) {
      return;
    }
    FileOutputStream fos = null;
    try {
      try {
        try {
          fos = new FileOutputStream(path);
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
          fos.close();
        } catch (Throwable th) {
          if (fos != null) {
            try {
              fos.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          throw th;
        }
      } catch (Exception e2) {
        e2.printStackTrace();
        if (fos != null) {
          fos.close();
        }
      }
    } catch (IOException e3) {
      e3.printStackTrace();
    }
  }

  private String getCurrentResolution() {
    String currentResolution;
    String str = this.mCurrentResolution;
    if (str != null) {
      return str;
    }
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    if (width >= 1440) {
      currentResolution = "WQHD";
    } else if (width > 720 && width <= 1080) {
      currentResolution = "FHD";
    } else {
      currentResolution = "HD";
    }
    this.mCurrentResolution = currentResolution;
    return currentResolution;
  }

  private static boolean isHome(int which) {
    return (which & 1) == 1;
  }

  private static boolean isLock(int which) {
    return (which & 2) == 2;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mWhich);
    dest.writeInt(this.mDataList.size());
    dest.writeInt(this.mRotation);
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      dest.writeParcelable(data.getRect(), 0);
      Item item = data.getItem();
      dest.writeInt(item.getFontColor());
      dest.writeInt(item.getFontColorRgb());
      dest.writeFloat(item.getShadowSize());
      dest.writeFloat(item.getShadowOpacity());
      dest.writeFloatArray(item.getHSV());
    }
    dest.writeFloat(this.mAdaptiveDimOpacity);
    dest.writeInt(this.mAdaptiveDimColor);
    dest.writeFloat(this.mDarkModeDimOpacity);
    dest.writeIntArray(this.mSeedColors);
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("[version:22");
    result.append(", which:" + this.mWhich);
    result.append(", rotation:" + this.mRotation);
    result.append(
        ", adaptive dim:"
            + this.mAdaptiveDimOpacity
            + "/"
            + Integer.toHexString(this.mAdaptiveDimColor));
    result.append(
        ", darkmode dim:" + this.mDarkModeDimOpacity + NavigationBarInflaterView.SIZE_MOD_END);
    int[] iArr = this.mSeedColors;
    if (iArr != null && iArr.length > 0) {
      result.append(
          "\n\t[SeedColors, "
              + Arrays.toString(this.mSeedColors)
              + NavigationBarInflaterView.SIZE_MOD_END);
    }
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (it.hasNext()) {
      WallpaperColorsData data = it.next();
      Rect rect = data.getRect();
      Item item = data.getItem();
      String name = SemWallpaperColorsArea.name(data.getInternalKey());
      result.append(
          "\n\t[" + name + ", " + rect + ":" + item + NavigationBarInflaterView.SIZE_MOD_END);
    }
    return result.toString();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toSimpleString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (!isLock(this.mWhich)) {
      ArrayList<WallpaperColorsData> arrayList = this.mDataList;
      if (arrayList != null) {
        Iterator<WallpaperColorsData> it = arrayList.iterator();
        while (it.hasNext()) {
          WallpaperColorsData data = it.next();
          Rect rect = data.getRect();
          Item item = data.getItem();
          if (item != null) {
            stringBuilder.append(
                rect + NavigationBarInflaterView.SIZE_MOD_START + item.getFontColor() + "] ");
          }
        }
      }
    } else {
      ArrayList<WallpaperColorsData> arrayList2 = this.mDataList;
      if (arrayList2 != null) {
        int size = arrayList2.size();
        Iterator<WallpaperColorsData> it2 = this.mDataList.iterator();
        while (it2.hasNext()) {
          WallpaperColorsData data2 = it2.next();
          data2.getRect();
          Item item2 = data2.getItem();
          int index = this.mDataList.indexOf(data2);
          if (item2 != null) {
            if (index > 0 && index < size) {
              stringBuilder.append(", ");
            }
            String name = SemWallpaperColorsArea.name(data2.getInternalKey());
            stringBuilder.append(
                name
                    + NavigationBarInflaterView.SIZE_MOD_START
                    + item2.getFontColor()
                    + NavigationBarInflaterView.SIZE_MOD_END);
          }
        }
      }
    }
    return stringBuilder.toString();
  }

  public int[] getSeedColors() {
    return this.mSeedColors;
  }

  public List<int[][]> getColorTableList() {
    return getColorTableList(false);
  }

  public List<int[][]> getColorTableList(boolean fromGoogle) {
    if (fromGoogle) {
      List<int[][]> list = this.mColorTableListGoogle;
      if (list != null && list.size() > 0) {
        return this.mColorTableListGoogle;
      }
    } else {
      List<int[][]> list2 = this.mColorTableList;
      if (list2 != null && list2.size() > 0) {
        return this.mColorTableList;
      }
    }
    int[] iArr = this.mSeedColors;
    if (iArr != null && iArr.length > 0) {
      ColorPaletteCreator5 paletteCreator = new ColorPaletteCreator5();
      paletteCreator.setColors(this.mSeedColors);
      paletteCreator.generateColorPalette(fromGoogle);
      List<int[][]> palettes = paletteCreator.getColorPalettes();
      if (palettes != null && palettes.size() > 0) {
        if (fromGoogle) {
          this.mColorTableListGoogle.clear();
          if (palettes.size() > 0) {
            for (int i = 0; i < palettes.size(); i++) {
              this.mColorTableListGoogle.add(palettes.get(i));
            }
          }
        } else {
          this.mColorTableList.clear();
          if (palettes.size() > 0) {
            for (int i2 = 0; i2 < palettes.size(); i2++) {
              this.mColorTableList.add(palettes.get(i2));
            }
          }
        }
        return palettes;
      }
      Log.m96e(TAG, "getColorTableList: Error while generating color palettes.");
      return null;
    }
    Log.m96e(TAG, "getColorTableList: No seed colors.");
    return null;
  }

  public int[] getStandardSeedColors() {
    StandardColorPaletteCreator standardColorPaletteCreator = new StandardColorPaletteCreator();
    standardColorPaletteCreator.initSeedColors();
    return standardColorPaletteCreator.getSeedColors();
  }

  public List<int[][]> getStandardPaletteList(boolean monet) {
    StandardColorPaletteCreator standardColorPaletteCreator = new StandardColorPaletteCreator();
    standardColorPaletteCreator.initSeedColors();
    int[] seeds = standardColorPaletteCreator.getSeedColors();
    standardColorPaletteCreator.setColors(seeds);
    standardColorPaletteCreator.generateColorPalette(monet);
    return standardColorPaletteCreator.getColorPalettes();
  }

  private int getItemFontColor(long key) {
    Item item = get(key);
    if (item == null) {
      return -1;
    }
    int fontColor = get(key).getFontColor();
    Log.m94d(TAG, "getItemColor: fontColor = " + fontColor);
    return fontColor == 0 ? -1 : -16777216;
  }

  public int getColorThemeColor(long key) {
    int[] iArr = this.mSeedColors;
    if (iArr == null || iArr.length <= 0) {
      Log.m96e(TAG, "getColorThemeColor: We don't have seed colors.");
      return getItemFontColor(key);
    }
    int retColor = 0;
    if (this.mColorTableList.size() < 1) {
      ColorPaletteCreator5 paletteCreator = new ColorPaletteCreator5();
      paletteCreator.setColors(this.mSeedColors);
      paletteCreator.generateColorPalette();
      List<int[][]> palettes = paletteCreator.getColorPalettes();
      this.mColorTableList.clear();
      if (palettes != null && palettes.size() > 0) {
        for (int i = 0; i < palettes.size(); i++) {
          this.mColorTableList.add(palettes.get(i));
        }
      }
      if (this.mColorTableList.size() >= 1) {
        Log.m94d(TAG, "getColorThemeColor mColorTableList size : " + this.mColorTableList.size());
      } else {
        Log.m96e(TAG, "getColorThemeColor: Error while generating color palette.");
        return getItemFontColor(key);
      }
    }
    int[][] colorPalette = this.mColorTableList.get(0);
    WallpaperColorsData retData = null;
    if (key == 281474976710656L || key == 562949953421312L) {
      Item item = get(key != 281474976710656L ? 64L : 32L);
      if (item == null) {
        Log.m94d(TAG, "getColorThemeColor: item is null. return WHITE");
        return -1;
      }
      int retColor2 = item.getFontColor() == 0 ? colorPalette[0][10] : colorPalette[0][3];
      Log.m94d(TAG, "getColorThemeColor nio text retColor: " + retColor2);
      return retColor2;
    }
    Iterator<WallpaperColorsData> it = this.mDataList.iterator();
    while (true) {
      if (!it.hasNext()) {
        break;
      }
      WallpaperColorsData data = it.next();
      if (data == null) {
        Log.m94d(TAG, "getColorThemeColor: data is null. return FONT_COLOR_WHITE");
      } else if (data.getExternalKey() == key) {
        Item item2 = data.getItem();
        if (item2 == null) {
          Log.m94d(TAG, "getColorThemeColor: item is null. return WHITE");
          return -1;
        }
        int fontColor = item2.getFontColor();
        if (colorPalette == null) {
          Log.m94d(TAG, "getColorThemeColor: colorPalette is null. return fontColor");
          return fontColor == 0 ? -1 : -16777216;
        }
        if (key == 32 || key == 64) {
          retColor = fontColor == 0 ? colorPalette[0][3] : colorPalette[0][10];
          retData = data;
        } else if (key == 128) {
          retColor = fontColor == 0 ? colorPalette[0][10] : colorPalette[0][3];
          retData = data;
        } else if (key == 256 || key == 512) {
          retColor = fontColor == 0 ? colorPalette[0][4] : colorPalette[0][8];
          retData = data;
        } else {
          Log.m94d(TAG, "getColorThemeColor not matched.");
        }
      }
    }
    if (retData != null && retData.getItem() != null) {
      Log.m94d(
          TAG,
          "getColorThemeColor :"
              + retData.getExternalKey()
              + ", "
              + retData.getItem().mFontColor
              + ", "
              + retColor);
    } else {
      Log.m94d(TAG, "getColorThemeColor retColor:" + retColor);
    }
    return retColor;
  }

  private boolean isWatchFaceLargeDisplay(int which) {
    if (isWatchFaceDisplay(which) && Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
      return true;
    }
    return false;
  }

  private boolean isWatchFaceDisplay(int which) {
    if (Rune.SUPPORT_SUB_DISPLAY_MODE
        && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
        && (which & 16) == 16) {
      return true;
    }
    return false;
  }

  private boolean isVirtualDisplay(int which) {
    if (Rune.VIRTUAL_DISPLAY_WALLPAPER && (which & 32) == 32) {
      return true;
    }
    return false;
  }

  public int getColorDataSize() {
    return this.mDataList.size();
  }

  /* renamed from: clone, reason: merged with bridge method [inline-methods] */
  public SemWallpaperColors m988clone() {
    try {
      SemWallpaperColors clone = (SemWallpaperColors) super.clone();
      clone.mArea = this.mArea.m1001clone();
      clone.mWhich = this.mWhich;
      clone.mRotation = this.mRotation;
      clone.mCurrentResolution = this.mCurrentResolution;
      if (this.mDataList != null) {
        clone.mDataList = new ArrayList<>();
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
          WallpaperColorsData wallpaperColorsData = it.next();
          clone.mDataList.add(wallpaperColorsData.m1000clone());
        }
      }
      int[] iArr = this.mSeedColors;
      if (iArr != null) {
        clone.mSeedColors = (int[]) iArr.clone();
      }
      if (this.mColorTableList != null) {
        ArrayList arrayList = new ArrayList();
        clone.mColorTableList = arrayList;
        arrayList.addAll(this.mColorTableList);
      }
      if (this.mColorTableListGoogle != null) {
        ArrayList arrayList2 = new ArrayList();
        clone.mColorTableListGoogle = arrayList2;
        arrayList2.addAll(this.mColorTableListGoogle);
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      Log.m96e(TAG, "clone: " + e.getMessage());
      return null;
    }
  }

  public static class Item implements Cloneable {
    private int mFontColor;
    private int mFontColorRgb;
    private float[] mHSV;
    private LegibilityLogic.LegibilityResult mLeftLegibilityResult;
    private LegibilityLogic.LegibilityResult mLegibilityResult;
    private LegibilityLogic.LegibilityResult mRightLegibilityResult;
    private float mShadowOpacity;
    private float mShadowSize;

    private Item() {
      this.mHSV = new float[3];
      this.mLegibilityResult = null;
      this.mLeftLegibilityResult = null;
      this.mRightLegibilityResult = null;
    }

    public Item(int fontColor, float shadowSize, float shadowOpacity) {
      this.mHSV = new float[3];
      this.mLegibilityResult = null;
      this.mLeftLegibilityResult = null;
      this.mRightLegibilityResult = null;
      this.mFontColor = fontColor;
      if (fontColor == 0) {
        this.mFontColorRgb = -1;
      } else {
        this.mFontColorRgb = -16777216;
      }
      this.mShadowSize = shadowSize;
      this.mShadowOpacity = shadowOpacity;
    }

    public Item(
        int fontColor,
        int fontColorRgb,
        LegibilityLogic.LegibilityResult leftLegibilityResult,
        LegibilityLogic.LegibilityResult rightLegibilityResult) {
      this.mHSV = new float[3];
      this.mLegibilityResult = null;
      this.mLeftLegibilityResult = null;
      this.mRightLegibilityResult = null;
      this.mFontColor = fontColor;
      this.mFontColorRgb = fontColorRgb;
      this.mShadowSize = 1.0f;
      this.mShadowOpacity = 0.1f;
      this.mLeftLegibilityResult = leftLegibilityResult;
      this.mRightLegibilityResult = rightLegibilityResult;
    }

    public Item(
        int fontColor,
        int fontColorRgb,
        float shadowSize,
        float shadowOpacity,
        float[] hsv,
        LegibilityLogic.LegibilityResult legibilityResult) {
      this.mHSV = new float[3];
      this.mLegibilityResult = null;
      this.mLeftLegibilityResult = null;
      this.mRightLegibilityResult = null;
      this.mFontColor = fontColor;
      this.mFontColorRgb = fontColorRgb;
      this.mShadowSize = shadowSize;
      this.mShadowOpacity = shadowOpacity;
      this.mHSV = hsv;
      this.mLegibilityResult = legibilityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFontColor(int fontColor) {
      this.mFontColor = fontColor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFontColorRgb(int fontColorRgb) {
      this.mFontColorRgb = fontColorRgb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShadowSize(float shadowSize) {
      this.mShadowSize = shadowSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShadowOpacity(float shadowOpacity) {
      this.mShadowOpacity = shadowOpacity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHSV(float[] hsv) {
      this.mHSV = hsv;
    }

    public int getFontColor() {
      return this.mFontColor;
    }

    public int getFontColorRgb() {
      return this.mFontColorRgb;
    }

    public int getFontColorRgb(int prevFontColorRgb) {
      return LegibilityLogic.getUnequivalanttColor(this.mFontColorRgb, prevFontColorRgb);
    }

    public float getShadowSize() {
      return this.mShadowSize;
    }

    public float getShadowOpacity() {
      return this.mShadowOpacity;
    }

    public float[] getHSV() {
      float[] fArr = this.mHSV;
      if (fArr == null || 3 != fArr.length) {
        return null;
      }
      return new float[] {fArr[0], fArr[1], fArr[2]};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegibilityLogic.LegibilityResult getLegibilityResult() {
      return this.mLegibilityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegibilityLogic.LegibilityResult getLeftLegibilityResult() {
      return this.mLeftLegibilityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegibilityLogic.LegibilityResult getRightLegibilityResult() {
      return this.mRightLegibilityResult;
    }

    public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Item)) {
        return false;
      }
      Item item = (Item) obj;
      if (item.mFontColor != this.mFontColor
          || item.mFontColorRgb != this.mFontColorRgb
          || Math.abs(item.mShadowSize - this.mShadowSize) >= 1.0f
          || Math.abs(item.mShadowOpacity - this.mShadowOpacity) >= 0.01f) {
        return false;
      }
      return true;
    }

    public boolean equals(Object obj, int compareType) {
      Item item = (Item) obj;
      if (item == null) {
        return false;
      }
      switch (compareType) {
        case 0:
          if (item.mFontColor != this.mFontColor) {}
          break;
        case 1:
          if (Math.abs(item.mShadowSize - this.mShadowSize) < 1.0f
              && Math.abs(item.mShadowOpacity - this.mShadowOpacity) < 0.01f) {}
          break;
        case 2:
          if (item.mFontColorRgb != this.mFontColorRgb) {}
          break;
        default:
          if (item.mFontColor != this.mFontColor) {}
          break;
      }
      return false;
    }

    public int hashCode() {
      return super.hashCode();
    }

    public String toString() {
      String result =
          ""
              + this.mFontColor
              + "/"
              + Integer.toHexString(this.mFontColorRgb)
              + "/"
              + this.mShadowSize
              + "/"
              + this.mShadowOpacity;
      if (this.mHSV != null) {
        result = result + ", " + this.mHSV[0] + "/" + this.mHSV[1] + "/" + this.mHSV[2];
      }
      if (this.mLeftLegibilityResult != null) {
        result =
            result
                + ", "
                + this.mLeftLegibilityResult.contentsColorType
                + "/"
                + Integer.toHexString(this.mLeftLegibilityResult.contentsColor);
      }
      if (this.mRightLegibilityResult != null) {
        return result
            + ", "
            + this.mRightLegibilityResult.contentsColorType
            + "/"
            + Integer.toHexString(this.mRightLegibilityResult.contentsColor);
      }
      return result;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Item m999clone() {
      try {
        Item item = (Item) super.clone();
        float[] fArr = this.mHSV;
        if (fArr != null) {
          item.mHSV = new float[fArr.length];
          int i = 0;
          while (true) {
            float[] fArr2 = this.mHSV;
            if (i >= fArr2.length) {
              break;
            }
            item.mHSV[i] = fArr2[i];
            i++;
          }
        }
        LegibilityLogic.LegibilityResult legibilityResult = this.mLegibilityResult;
        if (legibilityResult != null) {
          item.mLegibilityResult = legibilityResult.m9231clone();
        }
        LegibilityLogic.LegibilityResult legibilityResult2 = this.mLeftLegibilityResult;
        if (legibilityResult2 != null) {
          item.mLeftLegibilityResult = legibilityResult2.m9231clone();
        }
        LegibilityLogic.LegibilityResult legibilityResult3 = this.mRightLegibilityResult;
        if (legibilityResult3 != null) {
          item.mRightLegibilityResult = legibilityResult3.m9231clone();
        }
        return item;
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  public static class Builder {
    private int mWhich = 2;
    private int mColorType = 0;
    private float mShadowSize = 1.0f;
    private float mShadowOpacity = 0.1f;
    private Bitmap mBitmap = null;

    public Builder setWhich(int which) {
      this.mWhich = which;
      return this;
    }

    public Builder setColorType(int colorType) {
      this.mColorType = colorType;
      return this;
    }

    public Builder setThumbnailBitmap(Bitmap bitmap) {
      this.mBitmap = bitmap;
      return this;
    }

    public SemWallpaperColors build() {
      Item item = new Item(this.mColorType, this.mShadowSize, this.mShadowOpacity);
      Log.m94d(
          SemWallpaperColors.TAG,
          "build() mColorType: "
              + this.mColorType
              + ", mWhich: "
              + this.mWhich
              + ", item: "
              + item.toString()
              + "mBitmap:"
              + this.mBitmap);
      return new SemWallpaperColors(this.mWhich, item, this.mBitmap);
    }
  }

  class WallpaperColorsData implements Cloneable {
    private long mExternalKey;
    private int mInternalKey;
    private Item mItem;
    private Rect mRect;

    public WallpaperColorsData(SemWallpaperColors this$0, long externalKey, int internalKey) {
      this(externalKey, internalKey, this$0.mArea.get(internalKey), null);
    }

    public WallpaperColorsData(SemWallpaperColors this$0, Rect rect, Item item) {
      this(-1L, -1, rect, item);
    }

    public WallpaperColorsData(long externalKey, int internalKey, Rect rect, Item item) {
      this.mExternalKey = externalKey;
      this.mInternalKey = internalKey;
      this.mRect = rect;
      this.mItem = item;
    }

    public void setExternalKey(long externalKey) {
      this.mExternalKey = externalKey;
    }

    public long getExternalKey() {
      return this.mExternalKey;
    }

    public void setInternalKey(int internalKey) {
      this.mInternalKey = internalKey;
    }

    public int getInternalKey() {
      return this.mInternalKey;
    }

    public void setRect(Rect rect) {
      this.mRect = new Rect(rect);
    }

    public Rect getRect() {
      return new Rect(this.mRect);
    }

    public void setItem(Item item) {
      this.mItem = item;
    }

    public Item getItem() {
      return this.mItem;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public WallpaperColorsData m1000clone() {
      try {
        WallpaperColorsData clone = (WallpaperColorsData) super.clone();
        clone.mRect = new Rect(this.mRect);
        clone.mItem = this.mItem.m999clone();
        return clone;
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
        return null;
      }
    }
  }
}
