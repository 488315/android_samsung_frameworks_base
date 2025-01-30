package com.samsung.android.desktopsystemui.sharedlib.keyguard;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.wallpaper.legibilitycolors.LegibilityLogic;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SemWallpaperColorsWrapper implements Parcelable {
    public static final int COMPARE_ADAPTIVE_CONTRAST = 2;
    public static final int COMPARE_COLOR = 0;
    public static final int COMPARE_SHADOW = 1;
    public static final Parcelable.Creator<SemWallpaperColorsWrapper> CREATOR = new Parcelable.Creator<SemWallpaperColorsWrapper>() { // from class: com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWallpaperColorsWrapper createFromParcel(Parcel parcel) {
            return new SemWallpaperColorsWrapper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWallpaperColorsWrapper[] newArray(int i) {
            return new SemWallpaperColorsWrapper[i];
        }
    };
    public static final int FONT_COLOR_BLACK = 1;
    public static final int FONT_COLOR_GRAY = 2;
    public static final int FONT_COLOR_WHITE = 0;
    public static final int HOMESCREEN_BODY = 64;
    public static final int HOMESCREEN_NAVIBAR = 128;
    public static final int HOMESCREEN_STATUSBAR = 32;
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
    public static final long LOCKSCREEN_SECURE_TEXT = 2199023255552L;
    public static final long LOCKSCREEN_SHORTCUT = 549755813888L;
    public static final int LOCKSCREEN_STATUSBAR = 16;
    public static final long LOCKSCREEN_STATUS_BAR = 4294967296L;
    private final SemWallpaperColors mWallpaperColors;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Item {
        private final SemWallpaperColors.Item mItem;

        public boolean equals(Object obj) {
            return this.mItem.equals(obj);
        }

        public int getFontColor() {
            return this.mItem.getFontColor();
        }

        public int getFontColorRgb() {
            return this.mItem.getFontColorRgb();
        }

        public float getShadowOpacity() {
            return this.mItem.getShadowOpacity();
        }

        public float getShadowSize() {
            return this.mItem.getShadowSize();
        }

        public int hashCode() {
            return this.mItem.hashCode();
        }

        public String toString() {
            return this.mItem.toString();
        }

        private Item(SemWallpaperColors.Item item) {
            this.mItem = item;
        }

        public boolean equals(Object obj, int i) {
            return this.mItem.equals(obj, i);
        }

        public int getFontColorRgb(int i) {
            return this.mItem.getFontColorRgb(i);
        }

        public Item(int i, float f, float f2) {
            this.mItem = new SemWallpaperColors.Item(i, f, f2);
        }

        public Item(int i, int i2, LegibilityLogic.LegibilityResult legibilityResult, LegibilityLogic.LegibilityResult legibilityResult2) {
            this.mItem = new SemWallpaperColors.Item(i, i2, legibilityResult, legibilityResult2);
        }

        public Item(int i, int i2, float f, float f2, float[] fArr, LegibilityLogic.LegibilityResult legibilityResult) {
            this.mItem = new SemWallpaperColors.Item(i, i2, f, f2, fArr, legibilityResult);
        }
    }

    public static SemWallpaperColorsWrapper fromBitmap(Context context, Bitmap bitmap, Rect[] rectArr, boolean z) {
        return new SemWallpaperColorsWrapper(SemWallpaperColors.fromBitmap(context, bitmap, rectArr, z));
    }

    public static SemWallpaperColorsWrapper fromXml(String str) {
        return new SemWallpaperColorsWrapper(SemWallpaperColors.fromXml(str));
    }

    public static SemWallpaperColorsWrapper getBlankWallpaperColors() {
        return new SemWallpaperColorsWrapper(SemWallpaperColors.getBlankWallpaperColors());
    }

    public static int getDeviceVersion() {
        return SemWallpaperColors.getDeviceVersion();
    }

    public static String getLegibilityVersion() {
        return SemWallpaperColors.getLegibilityVersion();
    }

    public static int getXmlVersion(String str) {
        return SemWallpaperColors.getXmlVersion(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mWallpaperColors.describeContents();
    }

    public Item get(int i) {
        return new Item(this.mWallpaperColors.get(i));
    }

    public int getAdaptiveDimColor() {
        return this.mWallpaperColors.getAdaptiveDimColor();
    }

    public float getAdaptiveDimOpacity() {
        return this.mWallpaperColors.getAdaptiveDimOpacity();
    }

    public int getColorThemeColor(int i) {
        return this.mWallpaperColors.getColorThemeColor(i);
    }

    public int getDarkModeDimColor() {
        return this.mWallpaperColors.getDarkModeDimColor();
    }

    public float getDarkModeDimOpacity() {
        return this.mWallpaperColors.getDarkModeDimOpacity();
    }

    public ArrayList<Rect> getKey() {
        return this.mWallpaperColors.getKey();
    }

    public String getXml() {
        return this.mWallpaperColors.getXml();
    }

    public void save(String str) {
        this.mWallpaperColors.save(str);
    }

    public String toSimpleString() {
        return this.mWallpaperColors.toSimpleString();
    }

    public String toString() {
        return this.mWallpaperColors.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mWallpaperColors.writeToParcel(parcel, i);
    }

    private SemWallpaperColorsWrapper(SemWallpaperColors semWallpaperColors) {
        this.mWallpaperColors = semWallpaperColors;
    }

    public Item get(Rect rect) {
        return new Item(this.mWallpaperColors.get(rect));
    }

    public static SemWallpaperColorsWrapper fromBitmap(Context context, Bitmap bitmap, int i, boolean z, Rect[] rectArr) {
        return new SemWallpaperColorsWrapper(SemWallpaperColors.fromBitmap(context, bitmap, i, z, rectArr));
    }

    private SemWallpaperColorsWrapper(Parcel parcel) {
        this.mWallpaperColors = new SemWallpaperColors(parcel);
    }

    public static SemWallpaperColorsWrapper fromBitmap(Context context, Bitmap bitmap, int i, int i2, Rect[] rectArr) {
        return new SemWallpaperColorsWrapper(SemWallpaperColors.fromBitmap(context, bitmap, i, i2, rectArr));
    }
}
