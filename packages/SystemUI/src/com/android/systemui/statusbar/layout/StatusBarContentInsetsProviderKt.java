package com.android.systemui.statusbar.layout;

import android.graphics.Rect;
import android.util.Pair;
import android.view.DisplayCutout;
import com.android.systemui.CameraProtectionInfo;
import com.android.systemui.SysUICutoutInformation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class StatusBarContentInsetsProviderKt {
    /* JADX WARN: Removed duplicated region for block: B:94:0x0194 A[PHI: r2
      0x0194: PHI (r2v13 boolean) = (r2v10 boolean), (r2v14 boolean), (r2v14 boolean), (r2v14 boolean) binds: [B:103:0x01a5, B:100:0x019f, B:97:0x019a, B:93:0x0192] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0196 A[PHI: r2
      0x0196: PHI (r2v11 boolean) = (r2v10 boolean), (r2v14 boolean), (r2v14 boolean), (r2v14 boolean) binds: [B:103:0x01a5, B:100:0x019f, B:97:0x019a, B:93:0x0192] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Rect calculateInsetsForRotationWithRotatedResources(int i, int i2, SysUICutoutInformation sysUICutoutInformation, Rect rect, int i3, int i4, int i5, boolean z, int i6, int i7, int i8) {
        ArrayList arrayList;
        Rect rect2;
        int i9;
        int i10;
        boolean z2;
        boolean zIntersects;
        boolean z3;
        char c;
        boolean z4;
        DisplayCutout displayCutout;
        SysUICutoutInformation sysUICutoutInformation2 = sysUICutoutInformation;
        Rect rect3 = (i == 0 || i == 2) ? rect : new Rect(0, 0, rect.bottom, rect.right);
        int i11 = rect3.right;
        int i12 = rect3.bottom;
        int iWidth = rect.width();
        int iHeight = rect.height();
        int i13 = i7 >= 0 ? i3 - ((i7 * 2) + i8) : 0;
        if (i2 == 1 || i2 == 3) {
            i11 = i12;
        }
        if (sysUICutoutInformation2 == null || (displayCutout = sysUICutoutInformation2.cutout) == null) {
            arrayList = null;
        } else {
            List listAsList = Arrays.asList(displayCutout.getBoundingRectLeft(), displayCutout.getBoundingRectRight(), displayCutout.getBoundingRectTop());
            arrayList = new ArrayList();
            for (Object obj : listAsList) {
                if (!((Rect) obj).isEmpty()) {
                    arrayList.add(obj);
                }
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return new Rect(i4, i13, i11 - i5, i3);
        }
        int i14 = i - i2;
        if (i14 < 0) {
            i14 += 4;
        }
        Pair pair = new Pair(Integer.valueOf(iWidth), Integer.valueOf(iHeight));
        Integer num = (Integer) pair.first;
        Integer num2 = (Integer) pair.second;
        if (i14 == 0) {
            num.getClass();
            rect2 = new Rect(0, 0, num.intValue(), i3);
        } else if (i14 == 1) {
            num2.getClass();
            rect2 = new Rect(0, 0, i3, num2.intValue());
        } else if (i14 != 2) {
            int iIntValue = num.intValue() - i3;
            int iIntValue2 = num.intValue();
            num2.getClass();
            rect2 = new Rect(iIntValue, 0, iIntValue2, num2.intValue());
        } else {
            int iIntValue3 = num2.intValue() - i3;
            num.getClass();
            rect2 = new Rect(0, iIntValue3, num.intValue(), num2.intValue());
        }
        int size = arrayList.size();
        int iMax = i4;
        int iMax2 = i5;
        int i15 = 0;
        while (i15 < size) {
            Object obj2 = arrayList.get(i15);
            int i16 = i15 + 1;
            Rect rect4 = (Rect) obj2;
            CameraProtectionInfo cameraProtectionInfo = sysUICutoutInformation2.cameraProtection;
            Rect rect5 = cameraProtectionInfo != null ? cameraProtectionInfo.bounds : null;
            if (rect5 != null) {
                i9 = size;
                i10 = i16;
                if (rect5.intersects(rect4.left, rect4.top, rect4.right, rect4.bottom)) {
                    Rect rect6 = new Rect(rect4);
                    rect6.union(rect5);
                    rect4 = rect6;
                }
            } else {
                i9 = size;
                i10 = i16;
            }
            if (iWidth < iHeight) {
                z2 = false;
                zIntersects = rect2.intersects(0, rect4.top, iWidth, rect4.bottom);
            } else {
                z2 = false;
                zIntersects = iWidth > iHeight ? rect2.intersects(rect4.left, 0, rect4.right, iHeight) : false;
            }
            if (!zIntersects) {
                z3 = true;
            } else if ((i14 == 0 ? rect4.left > 0 : i14 == 1 ? rect4.bottom < iHeight : i14 == 2 ? rect4.right < iWidth : rect4.top > 0) ? z2 : true) {
                int iWidth2 = (i14 == 0 || i14 == 2) ? rect4.width() : rect4.height();
                if (z) {
                    iWidth2 += i6;
                }
                iMax = Math.max(iWidth2, iMax);
                size = i9;
                i15 = i10;
                sysUICutoutInformation2 = sysUICutoutInformation;
            } else {
                if (i14 != 0) {
                    z3 = true;
                    z4 = (i14 == 1 ? rect4.top > 0 : i14 == 2 ? rect4.left > 0 : rect4.bottom < iHeight) ? z2 : z3;
                } else {
                    z3 = true;
                    if (rect4.right >= iWidth) {
                    }
                }
                if (z4) {
                    c = 2;
                    int iWidth3 = (i14 == 0 || i14 == 2) ? rect4.width() : rect4.height();
                    if (!z) {
                        iWidth3 += i6;
                    }
                    iMax2 = Math.max(iMax2, iWidth3);
                }
                i15 = i10;
                size = i9;
                sysUICutoutInformation2 = sysUICutoutInformation;
            }
            c = 2;
            i15 = i10;
            size = i9;
            sysUICutoutInformation2 = sysUICutoutInformation;
        }
        return new Rect(iMax, i13, i11 - iMax2, i3);
    }

    public static final Rect getPrivacyChipBoundingRectForInsets(Rect rect, int i, int i2, boolean z) {
        if (z) {
            int i3 = rect.left;
            return new Rect(i3 - i, rect.top, i3 + i2, rect.bottom);
        }
        int i4 = rect.right;
        return new Rect(i4 - i2, rect.top, i4 + i, rect.bottom);
    }
}
