package com.android.systemui.wallpaper.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IntelligentCropHelper {
    public static Rect getNearestCropHint(Point point, ArrayList arrayList) {
        if (point == null) {
            return null;
        }
        int i = point.x;
        int i2 = point.y;
        float f = i2 / i;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        float f2 = 10000.0f;
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            Rect rect = (Rect) arrayList.get(i4);
            float abs = Math.abs(f - (rect.height() / rect.width()));
            if (f2 > abs) {
                i3 = i4;
                f2 = abs;
            }
        }
        Rect rect2 = (Rect) arrayList.get(i3);
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "selectProperCropHint: baseW = ", ", baseH = ", ", ");
        m.append(rect2);
        Log.i("IntelligentCropHelper", m.toString());
        return rect2;
    }

    public static ArrayList parseCropHints(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = (ArrayList) new Gson().fromJson(str, TypeToken.getParameterized(ArrayList.class, Rect.class).getType());
        Log.i("IntelligentCropHelper", "getIntelligentCropHints : cropHints = " + arrayList);
        return arrayList;
    }
}
