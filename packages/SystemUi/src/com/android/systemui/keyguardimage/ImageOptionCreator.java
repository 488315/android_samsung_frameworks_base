package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.util.LogUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ImageOptionCreator {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ImageOption {
        public int height;
        public boolean isRtl;
        public int realHeight;
        public int realWidth;
        public int width;
        public int type = -1;
        public int rotation = -1;
        public boolean useThumbnail = false;
        public boolean useDefaultColor = false;
        public float scale = 1.0f;
        public int displayType = 0;
        public final int[] color = new int[4];
        public String clockType = null;
        public int coverClockColorIndex = -1;
        public int coverClockColor = 0;
        public int coverClockColorType = -1;
        public int clockColor = 0;
        public boolean useClockColor = false;
        public int legibilityColor = -1;

        public final String toString() {
            String msg = LogUtil.getMsg("type=%d, width=%d, height=%d, scale=%f, displayType=%d, useDefaultColor=%s, useClockColor=%s, legibilityColor=%d, clockType=%s, coverClockColorIndex=%d, coverClockColor=%d,  coverClockColorType=%d, clockColor=%d", Integer.valueOf(this.type), Integer.valueOf(this.width), Integer.valueOf(this.height), Float.valueOf(this.scale), Integer.valueOf(this.displayType), Boolean.valueOf(this.useDefaultColor), Boolean.valueOf(this.useClockColor), Integer.valueOf(this.legibilityColor), this.clockType, Integer.valueOf(this.coverClockColorIndex), Integer.valueOf(this.coverClockColor), Integer.valueOf(this.coverClockColorType), Integer.valueOf(this.clockColor));
            if (this.useDefaultColor) {
                return msg;
            }
            int[] iArr = this.color;
            return msg.concat(LogUtil.getMsg(", main=0x%x, 2nd=0x%x, bg_main=0x%x, bg_2nd=0x%x", Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]), Integer.valueOf(iArr[3])));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0209, code lost:
    
        if (r0 == false) goto L140;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ImageOption createImageOption(Context context, Uri uri, boolean z) {
        int i;
        int i2;
        char c;
        boolean z2;
        if (context == null || uri == null) {
            return null;
        }
        ImageOption imageOption = new ImageOption();
        boolean[] zArr = {false, false, false, false};
        Iterator it = Arrays.asList(uri.getQuery().split("&")).iterator();
        boolean z3 = false;
        int i3 = 0;
        while (true) {
            if (!it.hasNext()) {
                if (!z3) {
                    int i4 = 0;
                    while (true) {
                        if (i4 < 4) {
                            if (zArr[i4]) {
                                i4++;
                            } else {
                                imageOption.useDefaultColor = true;
                            }
                        }
                    }
                }
                if (z) {
                    return imageOption;
                }
                boolean z4 = imageOption.type == 4;
                Point realScreenSize = WallpaperUtils.getRealScreenSize(context, z4);
                int min = Math.min(realScreenSize.x, realScreenSize.y);
                int max = Math.max(realScreenSize.x, realScreenSize.y);
                if (z4) {
                    min = realScreenSize.x;
                }
                if (z4) {
                    max = realScreenSize.y;
                }
                imageOption.realWidth = realScreenSize.x;
                imageOption.realHeight = realScreenSize.y;
                imageOption.isRtl = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(context) == 1;
                String path = uri.getPath();
                if (TextUtils.isEmpty(path) || "/portrait".equals(path)) {
                    imageOption.width = min;
                    imageOption.height = max;
                } else if ("/landscape".equals(path)) {
                    imageOption.width = max;
                    imageOption.height = min;
                } else if ("/custom".equals(path)) {
                    try {
                        i = Integer.parseUnsignedInt(uri.getQueryParameter("width"));
                        try {
                            i2 = Integer.parseUnsignedInt(uri.getQueryParameter("height"));
                        } catch (NumberFormatException e) {
                            e = e;
                            e.printStackTrace();
                            i2 = 0;
                            return i == 0 ? null : null;
                        }
                    } catch (NumberFormatException e2) {
                        e = e2;
                        i = 0;
                    }
                    if (i == 0 && i2 != 0) {
                        float f = (i >= i2 || i > min || i2 > max) ? (i <= i2 || i > max || i2 > min) ? 0.0f : i / max : i / min;
                        if (z4) {
                            LogUtil.m223d("ImageOptionCreator", "createImageOption(), scale for cover", new Object[0]);
                            f = i / min;
                        }
                        if (f == 0.0f) {
                            return null;
                        }
                        imageOption.scale = f;
                        imageOption.width = i;
                        imageOption.height = i2;
                    }
                }
                return imageOption;
            }
            String[] split = ((String) it.next()).split("=");
            char c2 = 2;
            if (split.length == 2) {
                String str = split[i3];
                String str2 = split[1];
                LogUtil.m223d("ImageOptionCreator", FontProvider$$ExternalSyntheticOutline0.m32m("createImageOption() key: ", str, ", value: ", str2), new Object[i3]);
                str.getClass();
                switch (str.hashCode()) {
                    case -1892315952:
                        if (str.equals("color_second")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1834919982:
                        if (str.equals("color_bg_second")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1565881260:
                        if (str.equals("fontColor")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1399524596:
                        if (str.equals("coverClockColor")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -681420649:
                        if (str.equals("color_bg_main")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -574348749:
                        if (str.equals("white_theme")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3575610:
                        if (str.equals("type")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 289961751:
                        if (str.equals("paletteIndex")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1211980895:
                        if (str.equals("legibilityColor")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1263592043:
                        if (str.equals("colorClock")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1289467157:
                        if (str.equals("color_main")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1671764162:
                        if (str.equals("display")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                int[] iArr = imageOption.color;
                switch (c) {
                    case 0:
                        if (!z3) {
                            z2 = z3;
                            c2 = 1;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 1:
                        if (!z3) {
                            z2 = z3;
                            c2 = 3;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 2:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            if (imageOption.useClockColor) {
                                imageOption.clockColor = Integer.parseInt(str2);
                            } else {
                                imageOption.coverClockColorType = Integer.parseInt(str2);
                            }
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 3:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.coverClockColor = Integer.parseInt(str2);
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 4:
                        break;
                    case 5:
                        if ("true".equals(str2) || "on".equals(str2)) {
                            for (int i5 = 0; i5 < 4; i5++) {
                                iArr[i5] = -12237499;
                            }
                            z2 = true;
                            c2 = 65535;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 6:
                        if ("wallpaper".equals(str2)) {
                            imageOption.type = 1;
                        } else if ("cover_wallpaper".equals(str2)) {
                            imageOption.type = 5;
                            imageOption.displayType = 17;
                        } else if ("all".equals(str2)) {
                            imageOption.type = 3;
                        } else if (str2.startsWith("clockColor")) {
                            String substring = str2.substring(10);
                            imageOption.type = 2;
                            imageOption.useClockColor = true;
                            if ((substring == null || !substring.isEmpty()) && isNumeric(substring)) {
                                imageOption.clockType = substring;
                            }
                        } else if (str2.startsWith(SubRoom.EXTRA_VALUE_CLOCK)) {
                            String substring2 = str2.substring(5);
                            imageOption.type = 2;
                            if ((substring2 == null || !substring2.isEmpty()) && isNumeric(substring2)) {
                                imageOption.clockType = substring2;
                            }
                        } else if (str2.startsWith("cover")) {
                            String substring3 = str2.substring(5);
                            imageOption.type = 4;
                            if ((substring3 == null || !substring3.isEmpty()) && isNumeric(substring3)) {
                                imageOption.clockType = substring3;
                            }
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 7:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.coverClockColorIndex = Integer.parseInt(str2);
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case '\b':
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.legibilityColor = Integer.parseInt(str2);
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case '\t':
                        imageOption.useClockColor = true;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.clockType = str2;
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case '\n':
                        if (!z3) {
                            c2 = 0;
                            z2 = z3;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    case 11:
                        if ((str2 == null || !str2.isEmpty()) && str2 != null && str2.equals("virtual")) {
                            imageOption.displayType = 33;
                        }
                        z2 = z3;
                        c2 = 65535;
                        break;
                    default:
                        z2 = z3;
                        c2 = 65535;
                        break;
                }
                if (c2 > 65535) {
                    try {
                        iArr[c2] = Integer.parseUnsignedInt(str2, 16);
                        zArr[c2] = true;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                boolean z5 = z2;
                i3 = 0;
                z3 = z5;
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            LogUtil.m226w("ImageOptionCreator", KeyAttributes$$ExternalSyntheticOutline0.m21m("isNumeric() return false - ", str), new Object[0]);
            return false;
        }
    }
}
