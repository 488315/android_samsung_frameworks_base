package com.android.systemui.wallpaper.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IntelligentCropHelper {
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
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "selectProperCropHint: baseW = ", ", baseH = ", ", ");
        m.append(rect2);
        Log.i("IntelligentCropHelper", m.toString());
        return rect2;
    }

    public static ArrayList parseCropHints(String str) {
        if (str == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList) new Gson().fromJson(str, TypeToken.getParameterized(ArrayList.class, Rect.class).getType());
            Log.i("IntelligentCropHelper", "getIntelligentCropHints : cropHints = " + arrayList);
            return arrayList;
        } catch (Exception e) {
            Log.e("IntelligentCropHelper", "getIntelligentCropHints : " + e.getMessage());
            Log.e("IntelligentCropHelper", "getIntelligentCropHints : json = ".concat(str));
            return null;
        }
    }
}
