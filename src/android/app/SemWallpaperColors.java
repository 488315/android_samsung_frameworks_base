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
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.colortheme.ColorPaletteCreator5;
import com.samsung.android.wallpaper.colortheme.ColorThemeExtractor;
import com.samsung.android.wallpaper.colortheme.StandardColorPaletteCreator;
import com.samsung.android.wallpaper.legibilitycolors.ColorHSV;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityAutoDim;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityDefinition;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityLogic;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
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
    public static final Parcelable.Creator<SemWallpaperColors> CREATOR = new Parcelable.Creator<SemWallpaperColors>() { // from class: android.app.SemWallpaperColors.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWallpaperColors createFromParcel(Parcel parcel) {
            return new SemWallpaperColors(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWallpaperColors[] newArray(int i) {
            return new SemWallpaperColors[i];
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
    private int[] mSeedColors;
    private int mWhich;

    private static boolean isHome(int i) {
        return (i & 1) == 1;
    }

    private static boolean isLock(int i) {
        return (i & 2) == 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getDarkModeDimColor() {
        return -16777216;
    }

    public static int getDeviceVersion() {
        Log.d(TAG, "version 22");
        return 22;
    }

    public static int getXmlVersion(String str) {
        if (str == null || !str.contains("<Version>")) {
            return 0;
        }
        try {
            return Integer.parseInt(str.substring(str.indexOf("<Version>") + 9, str.indexOf("</Version>")));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getLegibilityVersion() {
        return LegibilityDefinition.VERSION;
    }

    public static SemWallpaperColors fromBitmap(Context context, Bitmap bitmap, Rect[] rectArr, boolean z) {
        return fromBitmap(context, bitmap, z ? 2 : 0, false, rectArr);
    }

    public static SemWallpaperColors fromBitmap(Context context, Bitmap bitmap, int i, boolean z, Rect[] rectArr) {
        return fromBitmap(context, bitmap, i, z ? 90 : 0, rectArr);
    }

    public static SemWallpaperColors fromBitmap(Context context, Bitmap bitmap, int i, int i2, Rect[] rectArr) {
        return new SemWallpaperColors(context, bitmap, i, i2, rectArr, null);
    }

    public static SemWallpaperColors fromBitmap(Context context, Bitmap bitmap, int i, int i2, Rect[] rectArr, WallpaperColorOverrideAreas wallpaperColorOverrideAreas) {
        Log.d(TAG, "fromBitmap " + i);
        if (rectArr != null) {
            for (Rect rect : rectArr) {
                if (rect.left < 0 || rect.top < 0 || rect.right > bitmap.getWidth() || rect.bottom > bitmap.getHeight()) {
                    throw new IllegalArgumentException("illegal argument " + rect);
                }
            }
        }
        return new SemWallpaperColors(context, bitmap, i, i2, rectArr, wallpaperColorOverrideAreas);
    }

    public static SemWallpaperColors fromXml(String str) {
        if (str == null || str.equals("")) {
            Log.e(TAG, "fromXml invalid xml " + str);
            return null;
        }
        return new SemWallpaperColors(str);
    }

    public static SemWallpaperColors getBlankWallpaperColors() {
        return new SemWallpaperColors(2, new Item(0, 1.0f, 0.1f), null);
    }

    public void save(String str) {
        Log.d(TAG, "save " + str);
        if (str == null) {
            Log.e(TAG, "save, path == null");
            return;
        }
        File file = new File(str);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(xmlGenerator().getBytes(StandardCharsets.UTF_8));
                        Log.d(TAG, "save done");
                        fileOutputStream2.close();
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    public Item get(long j) {
        Item item;
        if (!isHome(this.mWhich) && !isLock(this.mWhich)) {
            Log.e(TAG, "SemWallpaperColors is not support default area");
            return new Item(0, 1.0f, 0.5f);
        }
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (true) {
            if (!it.hasNext()) {
                item = null;
                break;
            }
            WallpaperColorsData next = it.next();
            if (next != null && j == next.getExternalKey()) {
                item = next.getItem();
                break;
            }
        }
        if (item != null) {
            return item;
        }
        Log.d(TAG, "returning default dummy Item " + j);
        return new Item(0, 1.0f, 0.5f);
    }

    public Item get(Rect rect) {
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
            WallpaperColorsData next = it.next();
            if (next != null && next.getRect() != null && rect.equals(next.getRect())) {
                return next.getItem();
            }
        }
        return null;
    }

    public ArrayList<Rect> getKey() {
        ArrayList<Rect> arrayList = new ArrayList<>();
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getRect());
        }
        return arrayList;
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

    public float getDarkModeDimOpacity() {
        return this.mDarkModeDimOpacity;
    }

    public SemWallpaperColors(Parcel parcel) {
        this.mWhich = 0;
        this.mCurrentResolution = null;
        this.mAdaptiveDimOpacity = 0.0f;
        this.mAdaptiveDimColor = 0;
        this.mDarkModeDimOpacity = 0.0f;
        this.mDataList = new ArrayList<>();
        this.mColorTableList = new ArrayList();
        this.mColorTableListGoogle = new ArrayList();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        init(null, readInt, 0);
        for (int i = 0; i < readInt2; i++) {
            Rect rect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
            Item item = new Item();
            item.setFontColor(parcel.readInt());
            item.setFontColorRgb(parcel.readInt());
            item.setShadowSize(parcel.readFloat());
            item.setShadowOpacity(parcel.readFloat());
            item.setHSV(parcel.createFloatArray());
            WallpaperColorsData wallpaperColorsData = this.mDataList.get(i);
            if (wallpaperColorsData != null) {
                wallpaperColorsData.setRect(rect);
                wallpaperColorsData.setItem(item);
            } else {
                this.mDataList.add(new WallpaperColorsData(this, rect, item));
            }
        }
        workaround();
        this.mAdaptiveDimOpacity = parcel.readFloat();
        this.mAdaptiveDimColor = parcel.readInt();
        this.mDarkModeDimOpacity = parcel.readFloat();
        this.mSeedColors = parcel.createIntArray();
    }

    private SemWallpaperColors(String str) {
        this.mWhich = 0;
        this.mCurrentResolution = null;
        this.mAdaptiveDimOpacity = 0.0f;
        this.mAdaptiveDimColor = 0;
        this.mDarkModeDimOpacity = 0.0f;
        this.mDataList = new ArrayList<>();
        this.mColorTableList = new ArrayList();
        this.mColorTableListGoogle = new ArrayList();
        xmlParser(str);
        workaround();
    }

    private SemWallpaperColors(int i, Item item, Bitmap bitmap) {
        this.mWhich = 0;
        this.mCurrentResolution = null;
        this.mAdaptiveDimOpacity = 0.0f;
        this.mAdaptiveDimColor = 0;
        this.mDarkModeDimOpacity = 0.0f;
        this.mDataList = new ArrayList<>();
        this.mColorTableList = new ArrayList();
        this.mColorTableListGoogle = new ArrayList();
        init(null, i, 0);
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
            it.next().setItem(item);
        }
        workaround();
        if (bitmap != null) {
            setSeedColors(bitmap);
        }
    }

    private SemWallpaperColors(Context context, Bitmap bitmap, int i, int i2, Rect[] rectArr) {
        this(context, bitmap, i, i2, rectArr, null);
    }

    private SemWallpaperColors(Context context, Bitmap bitmap, int i, int i2, Rect[] rectArr, WallpaperColorOverrideAreas wallpaperColorOverrideAreas) {
        int[][] iArr;
        this.mWhich = 0;
        this.mCurrentResolution = null;
        this.mAdaptiveDimOpacity = 0.0f;
        this.mAdaptiveDimColor = 0;
        this.mDarkModeDimOpacity = 0.0f;
        this.mDataList = new ArrayList<>();
        this.mColorTableList = new ArrayList();
        this.mColorTableListGoogle = new ArrayList();
        init(context, i, i2, wallpaperColorOverrideAreas);
        try {
            if (isHome(i) || isLock(i)) {
                if (isLock(i)) {
                    iArr = new int[][]{new int[]{1, 2}};
                } else {
                    iArr = new int[0][];
                }
                calc(bitmap, 0, true, iArr);
                calc(bitmap, 1, true, iArr);
                calc(bitmap, 2, true, null);
            }
            if (rectArr != null) {
                for (Rect rect : rectArr) {
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
            WallpaperColorsData next = it.next();
            if (next.getItem() == null) {
                Log.e(TAG, "data.getItem() == null, " + next.getInternalKey());
                next.setItem(new Item(0, 1.0f, 0.1f));
            }
        }
    }

    private void init(Context context, int i, int i2) {
        init(context, i, i2, null);
    }

    private void init(Context context, int i, int i2, WallpaperColorOverrideAreas wallpaperColorOverrideAreas) {
        this.mContext = context;
        this.mWhich = i;
        this.mArea = new SemWallpaperColorsArea(context, i, i2, wallpaperColorOverrideAreas);
        if (isLock(this.mWhich)) {
            this.mDataList.add(new WallpaperColorsData(this, 16L, 0));
            this.mDataList.add(new WallpaperColorsData(this, 32L, 2));
            this.mDataList.add(new WallpaperColorsData(this, 64L, 3));
            this.mDataList.add(new WallpaperColorsData(this, 128L, 4));
            this.mDataList.add(new WallpaperColorsData(this, 256L, 5));
            this.mDataList.add(new WallpaperColorsData(this, 512L, 7));
        } else if (isHome(this.mWhich)) {
            if (isWatchFaceLargeDisplay(i)) {
                this.mDataList.add(new WallpaperColorsData(this, 16L, 0));
                this.mDataList.add(new WallpaperColorsData(this, 32L, 2));
                this.mDataList.add(new WallpaperColorsData(this, 64L, 3));
                this.mDataList.add(new WallpaperColorsData(this, 128L, 4));
                this.mDataList.add(new WallpaperColorsData(this, 256L, 5));
                this.mDataList.add(new WallpaperColorsData(this, 512L, 7));
            } else if (isWatchFaceDisplay(i)) {
                this.mDataList.add(new WallpaperColorsData(this, 16L, 2));
                this.mDataList.add(new WallpaperColorsData(this, 32L, 9));
                this.mDataList.add(new WallpaperColorsData(this, 64L, 8));
                this.mDataList.add(new WallpaperColorsData(this, 128L, 10));
                this.mDataList.add(new WallpaperColorsData(this, 256L, 4));
                this.mDataList.add(new WallpaperColorsData(this, 512L, 7));
            } else if (isVirtualDisplay(i)) {
                this.mDataList.add(new WallpaperColorsData(this, 16L, 0));
                this.mDataList.add(new WallpaperColorsData(this, 32L, 2));
            } else {
                this.mDataList.add(new WallpaperColorsData(this, 32L, 0));
                this.mDataList.add(new WallpaperColorsData(this, 64L, 1));
                this.mDataList.add(new WallpaperColorsData(this, 128L, 6));
            }
        } else {
            Log.d(TAG, "init custom");
        }
        this.mArea.buildKeyMap(this.mDataList);
    }

    private void calc(Bitmap bitmap, int i, boolean z, int[][] iArr) {
        if (i == 0 || i == 1) {
            for (int[] iArr2 : iArr) {
                calcInternal(bitmap, z, this.mDataList.get(iArr2[i]), i == 1 ? this.mDataList.get(iArr2[0]).getItem() : null);
            }
            return;
        }
        if (i == 2) {
            Iterator<WallpaperColorsData> it = this.mDataList.iterator();
            while (it.hasNext()) {
                calcInternal(bitmap, z, it.next(), null);
            }
        } else {
            Log.e(TAG, "calc, invalid type " + i);
        }
    }

    private void calcInternal(Bitmap bitmap, boolean z, WallpaperColorsData wallpaperColorsData, Item item) {
        if (wallpaperColorsData.getItem() != null) {
            return;
        }
        Rect rect = wallpaperColorsData.getRect();
        if (z) {
            rect = this.mArea.get(wallpaperColorsData.getInternalKey(), bitmap.getWidth(), bitmap.getHeight());
        }
        if (rect.left < 0 || rect.top < 0 || rect.right - rect.left <= 0 || rect.bottom - rect.top <= 0) {
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
        boolean z2 = false;
        if (!isWatchFaceLargeDisplay(this.mWhich) && (isWatchFaceDisplay(this.mWhich) || isVirtualDisplay(this.mWhich))) {
            wallpaperColorsData.setItem(fromBitmapInternal(createBitmap, item, false));
            return;
        }
        if (z && this.mDataList.indexOf(wallpaperColorsData) == 0) {
            z2 = true;
        }
        wallpaperColorsData.setItem(fromBitmapInternal(createBitmap, item, z2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x014e, code lost:
    
        if (r0.equals("HD") != false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.app.SemWallpaperColors.Item fromBitmapInternal(android.graphics.Bitmap r17, android.app.SemWallpaperColors.Item r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 491
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SemWallpaperColors.fromBitmapInternal(android.graphics.Bitmap, android.app.SemWallpaperColors$Item, boolean):android.app.SemWallpaperColors$Item");
    }

    private int[] getIndicatorPixels(Bitmap bitmap, Bitmap bitmap2) {
        int[] iArr = new int[(bitmap.getWidth() * bitmap.getHeight()) + (bitmap2.getWidth() * bitmap2.getHeight())];
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr2 = new int[width];
        int width2 = bitmap2.getWidth() * bitmap2.getHeight();
        int[] iArr3 = new int[width2];
        bitmap.getPixels(iArr2, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        bitmap2.getPixels(iArr3, 0, bitmap2.getWidth(), 0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        for (int i = 0; i < width; i++) {
            iArr[i] = iArr2[i];
        }
        for (int i2 = 0; i2 < width2; i2++) {
            iArr[width + i2] = iArr3[i2];
        }
        return iArr;
    }

    private Bitmap getLeftIndicator(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = this.mArea.get(0);
        return Bitmap.createBitmap(bitmap, 0, 0, Math.max(1, Math.min(width, (int) (Resources.getSystem().getDisplayMetrics().density * 110.0f * (width / (rect.right - rect.left))))), height);
    }

    private Bitmap getRightIndicator(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = this.mArea.get(0);
        int max = Math.max(1, Math.min(width, (int) (Resources.getSystem().getDisplayMetrics().density * 76.0f * (width / (rect.right - rect.left)))));
        return Bitmap.createBitmap(bitmap, Math.max(0, width - max), 0, max, height);
    }

    private void calcAdaptiveDim() {
        Item item;
        if (isHome(this.mWhich) && (isWatchFaceDisplay(this.mWhich) || isVirtualDisplay(this.mWhich))) {
            Log.d(TAG, "calcAdaptiveDim: Cover wallpaper, return");
            return;
        }
        if (!isLock(this.mWhich) && !isHome(this.mWhich)) {
            Log.d(TAG, "calcAdaptiveDim: Custom area, return");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
            WallpaperColorsData next = it.next();
            if (next != null && next.getInternalKey() != 7 && next.getInternalKey() != 5 && (item = next.getItem()) != null) {
                if (next.getInternalKey() == 0) {
                    arrayList.add(item.getLeftLegibilityResult());
                    arrayList.add(item.getRightLegibilityResult());
                } else {
                    arrayList.add(item.getLegibilityResult());
                }
            }
        }
        if (arrayList.size() > 0) {
            LegibilityAutoDim.AutoDimResult calculateAdaptiveDim = LegibilityAutoDim.calculateAdaptiveDim((LegibilityLogic.LegibilityResult[]) arrayList.toArray(new LegibilityLogic.LegibilityResult[arrayList.size()]));
            this.mAdaptiveDimOpacity = calculateAdaptiveDim.opacity;
            this.mAdaptiveDimColor = calculateAdaptiveDim.color;
            Log.d(TAG, "calcAdaptiveDim, " + this.mAdaptiveDimOpacity + ", " + Integer.toHexString(this.mAdaptiveDimColor));
        }
    }

    private void setSeedColors(Bitmap bitmap) {
        if (bitmap == null) {
            Log.d(TAG, "setSeedColors: bitmap is null.");
            return;
        }
        this.mColorTableList.clear();
        this.mColorTableListGoogle.clear();
        this.mSeedColors = ColorThemeExtractor.getSeedColors(bitmap);
    }

    private void calcDarkModeDim() {
        ColorExtractor.DominantColorResult[] dominantColorResultArr;
        int i = 7;
        if (!isLock(this.mWhich)) {
            if (isHome(this.mWhich)) {
                if (!isWatchFaceDisplay(this.mWhich)) {
                    i = isVirtualDisplay(this.mWhich) ? 2 : 1;
                }
            } else {
                Log.d(TAG, "custom area, return");
                return;
            }
        }
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (true) {
            if (!it.hasNext()) {
                dominantColorResultArr = null;
                break;
            }
            WallpaperColorsData next = it.next();
            if (next != null && next.getInternalKey() == i) {
                dominantColorResultArr = next.getItem().getLegibilityResult().dominantColorResult;
                break;
            }
        }
        if (dominantColorResultArr == null) {
            Log.e(TAG, "dominantColorResult == null");
            return;
        }
        float[][] fArr = new float[dominantColorResultArr.length][];
        float f = 0.0f;
        for (int i2 = 0; i2 < dominantColorResultArr.length; i2++) {
            if (dominantColorResultArr[i2].percentage == 0.0f) {
                fArr[i2] = null;
            } else {
                fArr[i2] = new float[3];
                Color.colorToHSV(dominantColorResultArr[i2].color, fArr[i2]);
                f += fArr[i2][2] * dominantColorResultArr[i2].percentage;
            }
        }
        if (f <= 0.6f) {
            this.mDarkModeDimOpacity = 0.15f;
        } else if (f >= 0.8f) {
            this.mDarkModeDimOpacity = 0.25f;
        } else {
            this.mDarkModeDimOpacity = (f * 0.5f) - 0.15f;
        }
        Log.d(TAG, "calcDarkModeDim, " + this.mDarkModeDimOpacity);
    }

    private String xmlGenerator() {
        XmlSerializer newSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try {
            newSerializer.setOutput(stringWriter);
            newSerializer.startDocument(null, true);
            newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            newSerializer.startTag(null, "Version");
            newSerializer.text("22");
            newSerializer.endTag(null, "Version");
            newSerializer.startTag(null, "Which");
            newSerializer.text("" + this.mWhich);
            newSerializer.endTag(null, "Which");
            newSerializer.startTag(null, "AdaptiveDimOpacity");
            newSerializer.text("" + this.mAdaptiveDimOpacity);
            newSerializer.endTag(null, "AdaptiveDimOpacity");
            newSerializer.startTag(null, "AdaptiveDimColor");
            newSerializer.text("" + this.mAdaptiveDimColor);
            newSerializer.endTag(null, "AdaptiveDimColor");
            newSerializer.startTag(null, "DarkModeDimOpacity");
            newSerializer.text("" + this.mDarkModeDimOpacity);
            newSerializer.endTag(null, "DarkModeDimOpacity");
            int[] iArr = this.mSeedColors;
            if (iArr != null && iArr.length > 0) {
                newSerializer.startTag(null, "SeedColors");
                newSerializer.text(Arrays.toString(this.mSeedColors));
                newSerializer.endTag(null, "SeedColors");
            }
            Iterator<WallpaperColorsData> it = this.mDataList.iterator();
            while (it.hasNext()) {
                WallpaperColorsData next = it.next();
                xmlWrite(newSerializer, next.getRect(), next.getItem());
            }
            newSerializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    private void xmlWrite(XmlSerializer xmlSerializer, Rect rect, Item item) {
        if (rect == null || item == null) {
            Log.e(TAG, "xmlWrite check null");
            return;
        }
        try {
            xmlSerializer.startTag(null, "Rect");
            xmlSerializer.startTag(null, "Left");
            xmlSerializer.text("" + rect.left);
            xmlSerializer.endTag(null, "Left");
            xmlSerializer.startTag(null, "Top");
            xmlSerializer.text("" + rect.top);
            xmlSerializer.endTag(null, "Top");
            xmlSerializer.startTag(null, "Right");
            xmlSerializer.text("" + rect.right);
            xmlSerializer.endTag(null, "Right");
            xmlSerializer.startTag(null, "Bottom");
            xmlSerializer.text("" + rect.bottom);
            String str = null;
            xmlSerializer.endTag(null, "Bottom");
            xmlSerializer.endTag(null, "Rect");
            xmlSerializer.startTag(null, "Legibility");
            if (item.mHSV != null) {
                xmlSerializer.startTag(null, "avgHSV");
                xmlSerializer.text("" + IUXColorUtils.HSVToColor(item.getHSV()));
                xmlSerializer.endTag(null, "avgHSV");
                str = null;
            }
            xmlSerializer.startTag(str, "FontColor");
            xmlSerializer.text("" + item.getFontColor());
            xmlSerializer.endTag(null, "FontColor");
            xmlSerializer.startTag(null, "FontColorRgb");
            xmlSerializer.text("" + item.getFontColorRgb());
            xmlSerializer.endTag(null, "FontColorRgb");
            xmlSerializer.startTag(null, "ShadowSize");
            xmlSerializer.text("" + item.getShadowSize());
            xmlSerializer.endTag(null, "ShadowSize");
            xmlSerializer.startTag(null, "ShadowOpacity");
            xmlSerializer.text("" + item.getShadowOpacity());
            xmlSerializer.endTag(null, "ShadowOpacity");
            xmlSerializer.endTag(null, "Legibility");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[] stringToIntArray(String str) {
        String[] split = str.replace(NavigationBarInflaterView.SIZE_MOD_START, "").replace(NavigationBarInflaterView.SIZE_MOD_END, "").split(", ");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = Integer.parseInt(split[i]);
        }
        return iArr;
    }

    private void xmlParser(String str) {
        ByteArrayInputStream byteArrayInputStream;
        Log.d(TAG, "xmlParser");
        try {
            byteArrayInputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            byteArrayInputStream = null;
        }
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new InputStreamReader(byteArrayInputStream, "UTF-8"));
            Rect rect = new Rect();
            Item item = new Item();
            int i = 0;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 2) {
                    String name = newPullParser.getName();
                    if (name.equals("Which")) {
                        init(null, Integer.parseInt(newPullParser.nextText()), 0);
                    }
                    if (name.equals(SoundTheme.Default)) {
                        init(null, 2, 0);
                    }
                    if (name.equals("AdaptiveDimOpacity")) {
                        this.mAdaptiveDimOpacity = Float.parseFloat(newPullParser.nextText());
                    }
                    if (name.equals("AdaptieDimColor")) {
                        this.mAdaptiveDimColor = Integer.parseInt(newPullParser.nextText());
                    }
                    if (name.equals("DarkModeDimOpacity")) {
                        this.mDarkModeDimOpacity = Float.parseFloat(newPullParser.nextText());
                    }
                    if (name.equals("Rect")) {
                        rect = new Rect();
                    }
                    if (name.equals("Left")) {
                        rect.left = Integer.parseInt(newPullParser.nextText());
                    }
                    if (name.equals("Top")) {
                        rect.top = Integer.parseInt(newPullParser.nextText());
                    }
                    if (name.equals("Right")) {
                        rect.right = Integer.parseInt(newPullParser.nextText());
                    }
                    if (name.equals("Bottom")) {
                        rect.bottom = Integer.parseInt(newPullParser.nextText());
                    }
                    if (name.equals("Legibility")) {
                        item = new Item();
                    }
                    if (name.equals("avgHSV")) {
                        float[] fArr = new float[3];
                        ColorHSV.colorToHSV(Integer.parseInt(newPullParser.nextText()), fArr);
                        item.setHSV(fArr);
                    }
                    if (name.equals("FontColor")) {
                        item.setFontColor(Integer.parseInt(newPullParser.nextText()));
                    }
                    if (name.equals("FontColorRgb")) {
                        item.setFontColorRgb(Integer.parseInt(newPullParser.nextText()));
                    }
                    if (name.equals("ShadowSize")) {
                        item.setShadowSize(Float.parseFloat(newPullParser.nextText()));
                    }
                    if (name.equals("ShadowOpacity")) {
                        item.setShadowOpacity(Float.parseFloat(newPullParser.nextText()));
                    }
                    if (name.equals("SeedColors")) {
                        String nextText = newPullParser.nextText();
                        if (!TextUtils.isEmpty(nextText)) {
                            this.mSeedColors = stringToIntArray(nextText);
                        }
                    }
                } else if (eventType != 3) {
                    continue;
                } else if (newPullParser.getName().equals("Legibility") && i < this.mDataList.size()) {
                    int i2 = i + 1;
                    WallpaperColorsData wallpaperColorsData = this.mDataList.get(i);
                    if (wallpaperColorsData != null) {
                        if (item.getFontColorRgb() == 0) {
                            if (item.getFontColor() == 0) {
                                item.setFontColorRgb(-1);
                            } else {
                                item.setFontColorRgb(-16777216);
                            }
                        }
                        wallpaperColorsData.setRect(rect);
                        wallpaperColorsData.setItem(item);
                    } else {
                        this.mDataList.add(new WallpaperColorsData(this, rect, item));
                    }
                    i = i2;
                }
            }
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (XmlPullParserException e4) {
            e4.printStackTrace();
        }
    }

    private static void saveBitmaptoJpeg(Bitmap bitmap, String str) {
        Log.d(TAG, "saveBitmaptoJpeg " + str);
        if (str == null) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    private String getCurrentResolution() {
        String str;
        String str2 = this.mCurrentResolution;
        if (str2 != null) {
            return str2;
        }
        int i = Resources.getSystem().getDisplayMetrics().widthPixels;
        if (i >= 1440) {
            str = "WQHD";
        } else if (i > 720 && i <= 1080) {
            str = "FHD";
        } else {
            str = "HD";
        }
        this.mCurrentResolution = str;
        return str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mWhich);
        parcel.writeInt(this.mDataList.size());
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
            WallpaperColorsData next = it.next();
            parcel.writeParcelable(next.getRect(), 0);
            Item item = next.getItem();
            parcel.writeInt(item.getFontColor());
            parcel.writeInt(item.getFontColorRgb());
            parcel.writeFloat(item.getShadowSize());
            parcel.writeFloat(item.getShadowOpacity());
            parcel.writeFloatArray(item.getHSV());
        }
        parcel.writeFloat(this.mAdaptiveDimOpacity);
        parcel.writeInt(this.mAdaptiveDimColor);
        parcel.writeFloat(this.mDarkModeDimOpacity);
        parcel.writeIntArray(this.mSeedColors);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[version:22");
        sb.append(", which:" + this.mWhich);
        sb.append(", adaptive dim:" + this.mAdaptiveDimOpacity + "/" + Integer.toHexString(this.mAdaptiveDimColor));
        StringBuilder sb2 = new StringBuilder(", darkmode dim:");
        sb2.append(this.mDarkModeDimOpacity);
        sb2.append(NavigationBarInflaterView.SIZE_MOD_END);
        sb.append(sb2.toString());
        int[] iArr = this.mSeedColors;
        if (iArr != null && iArr.length > 0) {
            sb.append("\n\t[SeedColors, " + Arrays.toString(this.mSeedColors) + NavigationBarInflaterView.SIZE_MOD_END);
        }
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (it.hasNext()) {
            WallpaperColorsData next = it.next();
            Rect rect = next.getRect();
            Item item = next.getItem();
            sb.append("\n\t[" + SemWallpaperColorsArea.name(next.getInternalKey()) + ", " + rect + ":" + item + NavigationBarInflaterView.SIZE_MOD_END);
        }
        return sb.toString();
    }

    public String toSimpleString() {
        StringBuilder sb = new StringBuilder();
        if (!isLock(this.mWhich)) {
            ArrayList<WallpaperColorsData> arrayList = this.mDataList;
            if (arrayList != null) {
                Iterator<WallpaperColorsData> it = arrayList.iterator();
                while (it.hasNext()) {
                    WallpaperColorsData next = it.next();
                    Rect rect = next.getRect();
                    Item item = next.getItem();
                    if (item != null) {
                        sb.append(rect + NavigationBarInflaterView.SIZE_MOD_START + item.getFontColor() + "] ");
                    }
                }
            }
        } else {
            ArrayList<WallpaperColorsData> arrayList2 = this.mDataList;
            if (arrayList2 != null) {
                int size = arrayList2.size();
                Iterator<WallpaperColorsData> it2 = this.mDataList.iterator();
                while (it2.hasNext()) {
                    WallpaperColorsData next2 = it2.next();
                    next2.getRect();
                    Item item2 = next2.getItem();
                    int indexOf = this.mDataList.indexOf(next2);
                    if (item2 != null) {
                        if (indexOf > 0 && indexOf < size) {
                            sb.append(", ");
                        }
                        sb.append(SemWallpaperColorsArea.name(next2.getInternalKey()) + NavigationBarInflaterView.SIZE_MOD_START + item2.getFontColor() + NavigationBarInflaterView.SIZE_MOD_END);
                    }
                }
            }
        }
        return sb.toString();
    }

    public int[] getSeedColors() {
        return this.mSeedColors;
    }

    public List<int[][]> getColorTableList() {
        return getColorTableList(false);
    }

    public List<int[][]> getColorTableList(boolean z) {
        if (z) {
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
            ColorPaletteCreator5 colorPaletteCreator5 = new ColorPaletteCreator5();
            colorPaletteCreator5.setColors(this.mSeedColors);
            colorPaletteCreator5.generateColorPalette(z);
            List<int[][]> colorPalettes = colorPaletteCreator5.getColorPalettes();
            if (colorPalettes == null || colorPalettes.size() <= 0) {
                Log.e(TAG, "getColorTableList: Error while generating color palettes.");
                return null;
            }
            int i = 0;
            if (z) {
                this.mColorTableListGoogle.clear();
                if (colorPalettes.size() > 0) {
                    while (i < colorPalettes.size()) {
                        this.mColorTableListGoogle.add(colorPalettes.get(i));
                        i++;
                    }
                }
            } else {
                this.mColorTableList.clear();
                if (colorPalettes.size() > 0) {
                    while (i < colorPalettes.size()) {
                        this.mColorTableList.add(colorPalettes.get(i));
                        i++;
                    }
                }
            }
            return colorPalettes;
        }
        Log.e(TAG, "getColorTableList: No seed colors.");
        return null;
    }

    public int[] getStandardSeedColors() {
        StandardColorPaletteCreator standardColorPaletteCreator = new StandardColorPaletteCreator();
        standardColorPaletteCreator.initSeedColors();
        return standardColorPaletteCreator.getSeedColors();
    }

    public List<int[][]> getStandardPaletteList(boolean z) {
        StandardColorPaletteCreator standardColorPaletteCreator = new StandardColorPaletteCreator();
        standardColorPaletteCreator.initSeedColors();
        standardColorPaletteCreator.setColors(standardColorPaletteCreator.getSeedColors());
        standardColorPaletteCreator.generateColorPalette(z);
        return standardColorPaletteCreator.getColorPalettes();
    }

    private int getItemFontColor(long j) {
        if (get(j) == null) {
            return -1;
        }
        int fontColor = get(j).getFontColor();
        Log.d(TAG, "getItemColor: fontColor = " + fontColor);
        return fontColor == 0 ? -1 : -16777216;
    }

    public int getColorThemeColor(long j) {
        int i;
        WallpaperColorsData wallpaperColorsData;
        int i2;
        int[] iArr = this.mSeedColors;
        if (iArr == null || iArr.length <= 0) {
            Log.e(TAG, "getColorThemeColor: We don't have seed colors.");
            return getItemFontColor(j);
        }
        ArrayList arrayList = new ArrayList(this.mColorTableList);
        if (arrayList.isEmpty()) {
            ColorPaletteCreator5 colorPaletteCreator5 = new ColorPaletteCreator5();
            colorPaletteCreator5.setColors(this.mSeedColors);
            colorPaletteCreator5.generateColorPalette();
            List<int[][]> colorPalettes = colorPaletteCreator5.getColorPalettes();
            if (colorPalettes != null && !colorPalettes.isEmpty()) {
                arrayList.addAll(colorPalettes);
            }
            if (arrayList.isEmpty()) {
                Log.e(TAG, "getColorThemeColor: Error while generating color palette.");
                return getItemFontColor(j);
            }
            this.mColorTableList.clear();
            this.mColorTableList.addAll(arrayList);
            Log.d(TAG, "getColorThemeColor mColorTableList size : " + this.mColorTableList.size());
        }
        int i3 = 0;
        int[][] iArr2 = (int[][]) arrayList.get(0);
        if (j == 281474976710656L || j == 562949953421312L) {
            Item item = get(j != 281474976710656L ? 64L : 32L);
            if (item == null) {
                Log.d(TAG, "getColorThemeColor: item is null. return WHITE");
                return -1;
            }
            if (item.getFontColor() == 0) {
                i = iArr2[0][10];
            } else {
                i = iArr2[0][3];
            }
            Log.d(TAG, "getColorThemeColor nio text retColor: " + i);
            return i;
        }
        Iterator<WallpaperColorsData> it = this.mDataList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            wallpaperColorsData = it.next();
            if (wallpaperColorsData == null) {
                Log.d(TAG, "getColorThemeColor: data is null. return FONT_COLOR_WHITE");
            } else if (wallpaperColorsData.getExternalKey() == j) {
                Item item2 = wallpaperColorsData.getItem();
                if (item2 == null) {
                    Log.d(TAG, "getColorThemeColor: item is null. return WHITE");
                    return -1;
                }
                int fontColor = item2.getFontColor();
                if (iArr2 == null) {
                    Log.d(TAG, "getColorThemeColor: colorPalette is null. return fontColor");
                    return fontColor == 0 ? -1 : -16777216;
                }
                if (j == 32 || j == 64) {
                    i2 = fontColor == 0 ? iArr2[0][3] : iArr2[0][10];
                } else if (j == 128) {
                    i2 = fontColor == 0 ? iArr2[0][10] : iArr2[0][3];
                } else if (j == 256 || j == 512) {
                    i2 = fontColor == 0 ? iArr2[0][4] : iArr2[0][8];
                } else {
                    Log.d(TAG, "getColorThemeColor not matched.");
                }
                i3 = i2;
            }
        }
        wallpaperColorsData = null;
        if (wallpaperColorsData != null && wallpaperColorsData.getItem() != null) {
            Log.d(TAG, "getColorThemeColor :" + wallpaperColorsData.getExternalKey() + ", " + wallpaperColorsData.getItem().mFontColor + ", " + i3);
            return i3;
        }
        Log.d(TAG, "getColorThemeColor retColor:" + i3);
        return i3;
    }

    private boolean isWatchFaceLargeDisplay(int i) {
        return isWatchFaceDisplay(i) && Rune.SUPPORT_LARGE_FRONT_SUB_DISPLAY;
    }

    private boolean isWatchFaceDisplay(int i) {
        return Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (i & 16) == 16;
    }

    private boolean isVirtualDisplay(int i) {
        return Rune.VIRTUAL_DISPLAY_WALLPAPER && (i & 32) == 32;
    }

    public int getColorDataSize() {
        return this.mDataList.size();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemWallpaperColors m585clone() {
        try {
            SemWallpaperColors semWallpaperColors = (SemWallpaperColors) super.clone();
            semWallpaperColors.mArea = this.mArea.m598clone();
            semWallpaperColors.mCurrentResolution = this.mCurrentResolution;
            if (this.mDataList != null) {
                semWallpaperColors.mDataList = new ArrayList<>();
                Iterator<WallpaperColorsData> it = this.mDataList.iterator();
                while (it.hasNext()) {
                    semWallpaperColors.mDataList.add(it.next().m597clone());
                }
            }
            int[] iArr = this.mSeedColors;
            if (iArr != null) {
                semWallpaperColors.mSeedColors = (int[]) iArr.clone();
            }
            if (this.mColorTableList != null) {
                ArrayList arrayList = new ArrayList();
                semWallpaperColors.mColorTableList = arrayList;
                arrayList.addAll(this.mColorTableList);
            }
            if (this.mColorTableListGoogle != null) {
                ArrayList arrayList2 = new ArrayList();
                semWallpaperColors.mColorTableListGoogle = arrayList2;
                arrayList2.addAll(this.mColorTableListGoogle);
            }
            return semWallpaperColors;
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, "clone: " + e.getMessage());
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

        public Item(int i, float f, float f2) {
            this.mHSV = new float[3];
            this.mLegibilityResult = null;
            this.mLeftLegibilityResult = null;
            this.mRightLegibilityResult = null;
            this.mFontColor = i;
            if (i == 0) {
                this.mFontColorRgb = -1;
            } else {
                this.mFontColorRgb = -16777216;
            }
            this.mShadowSize = f;
            this.mShadowOpacity = f2;
        }

        public Item(int i, int i2, LegibilityLogic.LegibilityResult legibilityResult, LegibilityLogic.LegibilityResult legibilityResult2) {
            this.mHSV = new float[3];
            this.mLegibilityResult = null;
            this.mFontColor = i;
            this.mFontColorRgb = i2;
            this.mShadowSize = 1.0f;
            this.mShadowOpacity = 0.1f;
            this.mLeftLegibilityResult = legibilityResult;
            this.mRightLegibilityResult = legibilityResult2;
        }

        public Item(int i, int i2, float f, float f2, float[] fArr, LegibilityLogic.LegibilityResult legibilityResult) {
            this.mLeftLegibilityResult = null;
            this.mRightLegibilityResult = null;
            this.mFontColor = i;
            this.mFontColorRgb = i2;
            this.mShadowSize = f;
            this.mShadowOpacity = f2;
            this.mHSV = fArr;
            this.mLegibilityResult = legibilityResult;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFontColor(int i) {
            this.mFontColor = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFontColorRgb(int i) {
            this.mFontColorRgb = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setShadowSize(float f) {
            this.mShadowSize = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setShadowOpacity(float f) {
            this.mShadowOpacity = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHSV(float[] fArr) {
            this.mHSV = fArr;
        }

        public int getFontColor() {
            return this.mFontColor;
        }

        public int getFontColorRgb() {
            return this.mFontColorRgb;
        }

        public int getFontColorRgb(int i) {
            return LegibilityLogic.getUnequivalanttColor(this.mFontColorRgb, i);
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
            return new float[]{fArr[0], fArr[1], fArr[2]};
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
            return item.mFontColor == this.mFontColor && item.mFontColorRgb == this.mFontColorRgb && Math.abs(item.mShadowSize - this.mShadowSize) < 1.0f && Math.abs(item.mShadowOpacity - this.mShadowOpacity) < 0.01f;
        }

        public boolean equals(Object obj, int i) {
            Item item = (Item) obj;
            if (item == null) {
                return false;
            }
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        if (item.mFontColorRgb != this.mFontColorRgb) {
                            return false;
                        }
                    } else if (item.mFontColor != this.mFontColor) {
                        return false;
                    }
                } else if (Math.abs(item.mShadowSize - this.mShadowSize) >= 1.0f || Math.abs(item.mShadowOpacity - this.mShadowOpacity) >= 0.01f) {
                    return false;
                }
            } else if (item.mFontColor != this.mFontColor) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return super.hashCode();
        }

        public String toString() {
            String str = "" + this.mFontColor + "/" + Integer.toHexString(this.mFontColorRgb) + "/" + this.mShadowSize + "/" + this.mShadowOpacity;
            if (this.mHSV != null) {
                str = str + ", " + this.mHSV[0] + "/" + this.mHSV[1] + "/" + this.mHSV[2];
            }
            if (this.mLeftLegibilityResult != null) {
                str = str + ", " + this.mLeftLegibilityResult.contentsColorType + "/" + Integer.toHexString(this.mLeftLegibilityResult.contentsColor);
            }
            if (this.mRightLegibilityResult == null) {
                return str;
            }
            return str + ", " + this.mRightLegibilityResult.contentsColorType + "/" + Integer.toHexString(this.mRightLegibilityResult.contentsColor);
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Item m596clone() {
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
                    item.mLegibilityResult = legibilityResult.m9627clone();
                }
                LegibilityLogic.LegibilityResult legibilityResult2 = this.mLeftLegibilityResult;
                if (legibilityResult2 != null) {
                    item.mLeftLegibilityResult = legibilityResult2.m9627clone();
                }
                LegibilityLogic.LegibilityResult legibilityResult3 = this.mRightLegibilityResult;
                if (legibilityResult3 != null) {
                    item.mRightLegibilityResult = legibilityResult3.m9627clone();
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

        public Builder setWhich(int i) {
            this.mWhich = i;
            return this;
        }

        public Builder setColorType(int i) {
            this.mColorType = i;
            return this;
        }

        public Builder setThumbnailBitmap(Bitmap bitmap) {
            this.mBitmap = bitmap;
            return this;
        }

        public SemWallpaperColors build() {
            Item item = new Item(this.mColorType, this.mShadowSize, this.mShadowOpacity);
            Log.d(SemWallpaperColors.TAG, "build() mColorType: " + this.mColorType + ", mWhich: " + this.mWhich + ", item: " + item.toString() + "mBitmap:" + this.mBitmap);
            return new SemWallpaperColors(this.mWhich, item, this.mBitmap);
        }
    }

    class WallpaperColorsData implements Cloneable {
        private long mExternalKey;
        private int mInternalKey;
        private Item mItem;
        private Rect mRect;

        public WallpaperColorsData(SemWallpaperColors semWallpaperColors, long j, int i) {
            this(semWallpaperColors, j, i, semWallpaperColors.mArea.get(i), null);
        }

        public WallpaperColorsData(SemWallpaperColors semWallpaperColors, Rect rect, Item item) {
            this(semWallpaperColors, -1L, -1, rect, item);
        }

        public WallpaperColorsData(SemWallpaperColors semWallpaperColors, long j, int i, Rect rect, Item item) {
            this.mExternalKey = j;
            this.mInternalKey = i;
            this.mRect = rect;
            this.mItem = item;
        }

        public void setExternalKey(long j) {
            this.mExternalKey = j;
        }

        public long getExternalKey() {
            return this.mExternalKey;
        }

        public void setInternalKey(int i) {
            this.mInternalKey = i;
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
        public WallpaperColorsData m597clone() {
            try {
                WallpaperColorsData wallpaperColorsData = (WallpaperColorsData) super.clone();
                wallpaperColorsData.mRect = new Rect(this.mRect);
                wallpaperColorsData.mItem = this.mItem.m596clone();
                return wallpaperColorsData;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
