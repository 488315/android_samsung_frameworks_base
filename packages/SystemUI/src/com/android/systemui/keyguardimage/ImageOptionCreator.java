package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class ImageOptionCreator {

    public class ImageOption {
        public int height;
        public boolean isRtl;
        public int realHeight;
        public int realWidth;
        public int width;
        public int type = -1;
        public int rotation = -1;
        public int which = -1;
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
            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(msg);
            int[] iArr = this.color;
            sbM.append(LogUtil.getMsg(", main=0x%x, 2nd=0x%x, bg_main=0x%x, bg_2nd=0x%x", Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]), Integer.valueOf(iArr[3])));
            return sbM.toString();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ImageOption createImageOption(Context context, Uri uri, boolean z) throws NumberFormatException {
        int unsignedInt;
        int unsignedInt2;
        float f;
        int i;
        int i2;
        boolean z2;
        char c;
        char c2;
        int i3 = 5;
        int i4 = 2;
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
            String[] strArrSplit = ((String) it.next()).split("=");
            if (strArrSplit.length == i4) {
                String str = strArrSplit[0];
                String str2 = strArrSplit[1];
                LogUtil.d("ImageOptionCreator", AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("createImageOption() key: ", str, ", value: ", str2), new Object[0]);
                str.getClass();
                switch (str.hashCode()) {
                    case -1892315952:
                        if (str.equals("color_second")) {
                            i = 0;
                            break;
                        } else {
                            i = -1;
                            break;
                        }
                    case -1834919982:
                        if (str.equals("color_bg_second")) {
                            i = 1;
                            break;
                        }
                        break;
                    case -1830213043:
                        if (str.equals("useScreenshot")) {
                            i = 2;
                            break;
                        }
                        break;
                    case -1565881260:
                        if (str.equals("fontColor")) {
                            i = 3;
                            break;
                        }
                        break;
                    case -1399524596:
                        if (str.equals("coverClockColor")) {
                            i = 4;
                            break;
                        }
                        break;
                    case -681420649:
                        if (str.equals("color_bg_main")) {
                            i = i3;
                            break;
                        }
                        break;
                    case -574348749:
                        if (str.equals("white_theme")) {
                            i = 6;
                            break;
                        }
                        break;
                    case -40300674:
                        if (str.equals("rotation")) {
                            i = 7;
                            break;
                        }
                        break;
                    case 3575610:
                        if (str.equals("type")) {
                            i = 8;
                            break;
                        }
                        break;
                    case 113101341:
                        if (str.equals("which")) {
                            i = 9;
                            break;
                        }
                        break;
                    case 289961751:
                        if (str.equals("paletteIndex")) {
                            i = 10;
                            break;
                        }
                        break;
                    case 1211980895:
                        if (str.equals("legibilityColor")) {
                            i = 11;
                            break;
                        }
                        break;
                    case 1263592043:
                        if (str.equals("colorClock")) {
                            i = 12;
                            break;
                        }
                        break;
                    case 1289467157:
                        if (str.equals("color_main")) {
                            i = 13;
                            break;
                        }
                        break;
                    case 1671764162:
                        if (str.equals("display")) {
                            i = 14;
                            break;
                        }
                        break;
                }
                switch (i) {
                    case 0:
                        i2 = 2;
                        if (!z3) {
                            z2 = z3;
                            c = 1;
                            c2 = 65535;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 1:
                        i2 = 2;
                        if (!z3) {
                            z2 = z3;
                            c = 3;
                            c2 = 65535;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 2:
                        i2 = 2;
                        imageOption.useScreenshot = "true".equals(str2);
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 3:
                        i2 = 2;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            if (imageOption.useClockColor) {
                                imageOption.clockColor = Integer.parseInt(str2);
                            } else {
                                imageOption.coverClockColorType = Integer.parseInt(str2);
                            }
                        }
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 4:
                        i2 = 2;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.coverClockColor = Integer.parseInt(str2);
                        }
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 5:
                        i2 = 2;
                        if (!z3) {
                            z2 = z3;
                            c = 2;
                            c2 = 65535;
                            break;
                        }
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 6:
                        i2 = 2;
                        if ("true".equals(str2) || "on".equals(str2)) {
                            for (int i5 = 0; i5 < 4; i5++) {
                                iArr[i5] = -12237499;
                            }
                            z2 = true;
                        } else {
                            z2 = z3;
                        }
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 7:
                        i2 = 2;
                        imageOption.rotation = Integer.parseInt(str2);
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 8:
                        if ("wallpaper".equals(str2)) {
                            imageOption.type = 1;
                        } else if ("cover_wallpaper".equals(str2)) {
                            imageOption.type = i3;
                            imageOption.displayType = 17;
                        } else if (SystemUIAnalytics.QPNE_VID_COVER_ALL.equals(str2)) {
                            imageOption.type = 3;
                        } else if (str2.startsWith("clockColor")) {
                            String strSubstring = str2.substring(10);
                            imageOption.type = 2;
                            imageOption.useClockColor = true;
                            if ((strSubstring == null || !strSubstring.isEmpty()) && isNumeric(strSubstring)) {
                                imageOption.clockType = strSubstring;
                            }
                            i2 = 2;
                            z2 = z3;
                            c2 = 65535;
                            c = 65535;
                            break;
                        } else {
                            if (str2.startsWith(SubRoom.EXTRA_VALUE_CLOCK)) {
                                String strSubstring2 = str2.substring(i3);
                                i2 = 2;
                                imageOption.type = 2;
                                if ((strSubstring2 == null || !strSubstring2.isEmpty()) && isNumeric(strSubstring2)) {
                                    imageOption.clockType = strSubstring2;
                                }
                            } else {
                                i2 = 2;
                                if (str2.startsWith("cover")) {
                                    String strSubstring3 = str2.substring(i3);
                                    imageOption.type = 4;
                                    if ((strSubstring3 == null || !strSubstring3.isEmpty()) && isNumeric(strSubstring3)) {
                                        imageOption.clockType = strSubstring3;
                                    }
                                }
                            }
                            z2 = z3;
                            c2 = 65535;
                            c = 65535;
                        }
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 9:
                        imageOption.which = Integer.parseInt(str2);
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 10:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.coverClockColorIndex = Integer.parseInt(str2);
                        }
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 11:
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.legibilityColor = Integer.parseInt(str2);
                        }
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 12:
                        imageOption.useClockColor = true;
                        if ((str2 == null || !str2.isEmpty()) && isNumeric(str2)) {
                            imageOption.clockType = str2;
                        }
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 13:
                        if (!z3) {
                            z2 = z3;
                            c = 0;
                            c2 = 65535;
                            i2 = 2;
                            break;
                        }
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    case 14:
                        if ((str2 == null || !str2.isEmpty()) && str2 != null && str2.equals("virtual")) {
                            imageOption.displayType = 33;
                        }
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                    default:
                        i2 = 2;
                        z2 = z3;
                        c2 = 65535;
                        c = 65535;
                        break;
                }
                if (c > c2) {
                    try {
                        iArr[c] = Integer.parseUnsignedInt(str2, 16);
                        zArr[c] = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                z3 = z2;
                i4 = i2;
                i3 = 5;
            }
        }
        if (!z3) {
            int i6 = 0;
            while (true) {
                if (i6 < 4) {
                    if (zArr[i6]) {
                        i6++;
                    } else {
                        imageOption.useDefaultColor = true;
                    }
                }
            }
        }
        if (!z) {
            boolean z4 = imageOption.type == 4;
            Point realScreenSize = WallpaperUtils.getRealScreenSize(context, z4);
            int iMin = Math.min(realScreenSize.x, realScreenSize.y);
            int iMax = Math.max(realScreenSize.x, realScreenSize.y);
            if (z4) {
                iMin = realScreenSize.x;
            }
            if (z4) {
                iMax = realScreenSize.y;
            }
            imageOption.realWidth = realScreenSize.x;
            imageOption.realHeight = realScreenSize.y;
            imageOption.isRtl = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
            String path = uri.getPath();
            if (TextUtils.isEmpty(path) || "/portrait".equals(path)) {
                imageOption.width = iMin;
                imageOption.height = iMax;
                if ("/portrait".equals(path)) {
                    imageOption.rotation = 0;
                }
            } else if ("/landscape".equals(path)) {
                imageOption.width = iMax;
                imageOption.height = iMin;
            } else if ("/custom".equals(path)) {
                try {
                    unsignedInt = Integer.parseUnsignedInt(uri.getQueryParameter("width"));
                } catch (NumberFormatException e2) {
                    e = e2;
                    unsignedInt = 0;
                }
                try {
                    unsignedInt2 = Integer.parseUnsignedInt(uri.getQueryParameter("height"));
                } catch (NumberFormatException e3) {
                    e = e3;
                    e.printStackTrace();
                    unsignedInt2 = 0;
                    return unsignedInt == 0 ? null : null;
                }
                if (unsignedInt == 0 && unsignedInt2 != 0) {
                    if (unsignedInt >= unsignedInt2 || unsignedInt > iMin || unsignedInt2 > iMax) {
                        f = (unsignedInt <= unsignedInt2 || unsignedInt > iMax || unsignedInt2 > iMin) ? 0.0f : unsignedInt / iMax;
                    } else {
                        f = unsignedInt / iMin;
                        if (imageOption.rotation < 0) {
                            imageOption.rotation = 0;
                        }
                    }
                    if (z4) {
                        LogUtil.d("ImageOptionCreator", "createImageOption(), scale for cover", new Object[0]);
                        f = unsignedInt / iMin;
                    }
                    if (f == 0.0f) {
                        return null;
                    }
                    imageOption.scale = f;
                    imageOption.width = unsignedInt;
                    imageOption.height = unsignedInt2;
                }
            }
        }
        return imageOption;
    }

    public static boolean isNumeric(String str) throws NumberFormatException {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            LogUtil.w("ImageOptionCreator", AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("isNumeric() return false - ", str), new Object[0]);
            return false;
        }
    }
}
