package com.android.systemui.monet;

import android.util.Pair;
import com.google.ux.material.libmonet.dynamiccolor.DynamicColor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class DynamicColors {
    public static List generateSysUINames(Supplier[] supplierArr) {
        ArrayList arrayList = new ArrayList();
        for (Supplier supplier : supplierArr) {
            DynamicColor dynamicColor = (DynamicColor) supplier.get();
            String str = dynamicColor.name;
            if (str.contains("_palette_key_color")) {
                str = "palette_key_color_" + str.replace("_palette_key_color", "");
            }
            arrayList.add(new Pair(str, dynamicColor));
        }
        arrayList.sort(Comparator.comparing(new DynamicColors$$ExternalSyntheticLambda0()));
        return arrayList;
    }
}
