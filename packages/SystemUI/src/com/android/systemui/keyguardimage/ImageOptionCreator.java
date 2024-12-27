package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Arrays;
import java.util.Iterator;

public final class ImageOptionCreator {

    public final class ImageOption {
        public int height;
        public boolean isRtl;
        public int realHeight;
        public int realWidth;
        public int width;
        public int type = -1;
        public int rotation = -1;
        public boolean useScreenshot = false;
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
            StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(msg);
            int[] iArr = this.color;
            m.append(LogUtil.getMsg(", main=0x%x, 2nd=0x%x, bg_main=0x%x, bg_2nd=0x%x", Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]), Integer.valueOf(iArr[3])));
            return m.toString();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static ImageOption createImageOption(Context context, Uri uri, boolean z) {
        int i;
        int i2;
        float f;
        int i3;
        int i4;
        boolean z2;
        char c;
        int i5 = 5;
        int i6 = 2;
        if (context == null || uri == null) {
            return null;
        }
        ImageOption imageOption = new ImageOption();
        int[] iArr = imageOption.color;
        String query = uri.getQuery();
        boolean[] zArr = new boolean[4];
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
        zArr[3] = false;
        Iterator it = Arrays.asList(query.split("&")).iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            String[] split = ((String) it.next()).split("=");
            if (split.length == i6) {
                String str = split[0];
                String str2 = split[1];
                LogUtil.d("ImageOptionCreator", FontProvider$$ExternalSyntheticOutline0.m("createImageOption() key: ", str, ", value: ", str2), new Object[0]);
                str.getClass();
                switch (str.hashCode()) {
                    case -1892315952:
                        if (str.equals("color_second")) {
                            i3 = 0;
                            break;
                        }
                        i3 = -1;
                        break;
                    case -1834919982:
                        if (str.equals("color_bg_second")) {
                            i3 = 1;
                            break;
                        }
                        i3 = -1;
                        break;
                    case -1830213043:
                        if (str.equals("useScreenshot")) {
                            i3 = 2;
                            break;
                        }
                        i3 = -1;
                        break;
                    case -1565881260:
                        if (str.equals("fontColor")) {
                            i3 = 3;
                            break;
                        }
                        i3 = -1;
                        break;
                    case -1399524596:
                        if (str.equals("coverClockColor")) {
                            i3 = 4;
                            break;
                        }
                        i3 = -1;
                        break;
                    case -681420649:
                        if (str.equals("color_bg_main")) {
                            i3 = i5;
                            break;
                        }
                        i3 = -1;
                        break;
                    case -574348749:
                        if (str.equals("white_theme")) {
                            i3 = 6;
                            break;
                        }
                        i3 = -1;
                        break;
                    case 3575610:
                        if (str.equals("type")) {
                            i3 = 7;
                            break;
                        }
                        i3 = -1;
                        break;
                    case 289961751:
                        if (str.equals("paletteIndex")) {
                            i3 = 8;
                            break;
                        }
                        i3 = -1;
                        break;
                    case 1211980895:
                        if (str.equals("legibilityColor")) {
                            i3 = 9;
                            break;
                        }
                        i3 = -1;
                        break;
                    case 1263592043:
                        if (str.equals("colorClock")) {
                            i3 = 10;
                            break;
                        }
                        i3 = -1;
                        break;
                    case 1289467157:
                        if (str.equals("color_main")) {
                            i3 = 11;
                            break;
                        }
                        i3 = -1;
                        break;
                    case 1671764162:
                        if (str.equals("display")) {
                            i3 = 12;
                            break;
                        }
                        i3 = -1;
                        break;
                    default:
                        i3 = -1;
                        break;
                }
                switch (i3) {
                    case 0:
                        i4 = 2;
                        if (!z3) {
                            z2 = z3;
                            c = 1;
                            break;
                        }
                        z2 = z3;
                        c = 65535;
                        break;
                    case 1:
                        i4 = 2;
                        if (!z3) {
                            z2 = z3;
                            c = 3;
                            break;
                        }
                        z2 = z3;
                        c = 65535;
                        break;
                    case 2:
                        i4 = 2;
                        imageOption.useScreenshot = "true".equals(str2);
                        z2 = z3;
                        c = 65535;
                        break;
                    case 3:
                        i4 = 2;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            if (imageOption.useClockColor) {
                                imageOption.clockColor = Integer.parseInt(str2);
                            } else {
                                imageOption.coverClockColorType = Integer.parseInt(str2);
                            }
                        }
                        z2 = z3;
                        c = 65535;
                        break;
                    case 4:
                        i4 = 2;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.coverClockColor = Integer.parseInt(str2);
                        }
                        z2 = z3;
                        c = 65535;
                        break;
                    case 5:
                        i4 = 2;
                        if (!z3) {
                            z2 = z3;
                            c = 2;
                            break;
                        }
                        z2 = z3;
                        c = 65535;
                        break;
                    case 6:
                        i4 = 2;
                        if ("true".equals(str2) || "on".equals(str2)) {
                            for (int i7 = 0; i7 < 4; i7++) {
                                iArr[i7] = -12237499;
                            }
                            z2 = true;
                            c = 65535;
                            break;
                        }
                        z2 = z3;
                        c = 65535;
                        break;
                    case 7:
                        if ("wallpaper".equals(str2)) {
                            imageOption.type = 1;
                        } else if ("cover_wallpaper".equals(str2)) {
                            imageOption.type = i5;
                            imageOption.displayType = 17;
                        } else if (SystemUIAnalytics.QPNE_VID_COVER_ALL.equals(str2)) {
                            imageOption.type = 3;
                        } else if (str2.startsWith("clockColor")) {
                            String substring = str2.substring(10);
                            imageOption.type = 2;
                            imageOption.useClockColor = true;
                            if ((substring == null || !substring.isEmpty()) && isNumeric(substring)) {
                                imageOption.clockType = substring;
                            }
                            i4 = 2;
                            z2 = z3;
                            c = 65535;
                            break;
                        } else {
                            if (str2.startsWith(SubRoom.EXTRA_VALUE_CLOCK)) {
                                String substring2 = str2.substring(i5);
                                i4 = 2;
                                imageOption.type = 2;
                                if ((substring2 == null || !substring2.isEmpty()) && isNumeric(substring2)) {
                                    imageOption.clockType = substring2;
                                }
                            } else {
                                i4 = 2;
                                if (str2.startsWith("cover")) {
                                    String substring3 = str2.substring(i5);
                                    imageOption.type = 4;
                                    if ((substring3 == null || !substring3.isEmpty()) && isNumeric(substring3)) {
                                        imageOption.clockType = substring3;
                                    }
                                }
                            }
                            z2 = z3;
                            c = 65535;
                        }
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                    case 8:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.coverClockColorIndex = Integer.parseInt(str2);
                        }
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                    case 9:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.legibilityColor = Integer.parseInt(str2);
                        }
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                    case 10:
                        imageOption.useClockColor = true;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.clockType = str2;
                        }
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                    case 11:
                        if (!z3) {
                            z2 = z3;
                            c = 0;
                            i4 = 2;
                            break;
                        }
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                    case 12:
                        if ((str2 == null || !str2.isEmpty()) && str2 != null && str2.equals("virtual")) {
                            imageOption.displayType = 33;
                        }
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                    default:
                        i4 = 2;
                        z2 = z3;
                        c = 65535;
                        break;
                }
                if (c > 65535) {
                    try {
                        iArr[c] = Integer.parseUnsignedInt(str2, 16);
                        zArr[c] = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                z3 = z2;
                i6 = i4;
                i5 = 5;
            }
        }
        if (!z3) {
            int i8 = 0;
            while (true) {
                if (i8 < 4) {
                    if (zArr[i8]) {
                        i8++;
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
        imageOption.isRtl = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
        String path = uri.getPath();
        if (TextUtils.isEmpty(path) || "/portrait".equals(path)) {
            imageOption.width = min;
            imageOption.height = max;
            if ("/portrait".equals(path)) {
                imageOption.rotation = 0;
            }
        } else if ("/landscape".equals(path)) {
            imageOption.width = max;
            imageOption.height = min;
        } else if ("/custom".equals(path)) {
            try {
                i = Integer.parseUnsignedInt(uri.getQueryParameter("width"));
            } catch (NumberFormatException e2) {
                e = e2;
                i = 0;
            }
            try {
                i2 = Integer.parseUnsignedInt(uri.getQueryParameter("height"));
            } catch (NumberFormatException e3) {
                e = e3;
                e.printStackTrace();
                i2 = 0;
                return i == 0 ? null : null;
            }
            if (i == 0 && i2 != 0) {
                if (i >= i2 || i > min || i2 > max) {
                    f = (i <= i2 || i > max || i2 > min) ? 0.0f : i / max;
                } else {
                    f = i / min;
                    imageOption.rotation = 0;
                }
                if (z4) {
                    LogUtil.d("ImageOptionCreator", "createImageOption(), scale for cover", new Object[0]);
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

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            LogUtil.w("ImageOptionCreator", AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("isNumeric() return false - ", str), new Object[0]);
            return false;
        }
    }
}
