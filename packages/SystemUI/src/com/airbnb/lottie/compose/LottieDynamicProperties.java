package com.airbnb.lottie.compose;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.Typeface;
import com.airbnb.lottie.value.ScaleXY;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class LottieDynamicProperties {
    public final List bitmapProperties;
    public final List charSequenceProperties;
    public final List colorFilterProperties;
    public final List floatProperties;
    public final List intArrayProperties;
    public final List intProperties;
    public final List pointFProperties;
    public final List scaleProperties;
    public final List typefaceProperties;

    public LottieDynamicProperties(List<LottieDynamicProperty> list, List<LottieDynamicProperty> list2, List<LottieDynamicProperty> list3, List<LottieDynamicProperty> list4, List<LottieDynamicProperty> list5, List<LottieDynamicProperty> list6, List<LottieDynamicProperty> list7, List<LottieDynamicProperty> list8, List<LottieDynamicProperty> list9) {
        this.intProperties = list;
        this.pointFProperties = list2;
        this.floatProperties = list3;
        this.scaleProperties = list4;
        this.colorFilterProperties = list5;
        this.intArrayProperties = list6;
        this.typefaceProperties = list7;
        this.bitmapProperties = list8;
        this.charSequenceProperties = list9;
    }

    public LottieDynamicProperties(List<? extends LottieDynamicProperty> list) {
        List<? extends LottieDynamicProperty> list2 = list;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (((LottieDynamicProperty) obj).property instanceof Integer) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            if (((LottieDynamicProperty) obj2).property instanceof PointF) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : list2) {
            if (((LottieDynamicProperty) obj3).property instanceof Float) {
                arrayList3.add(obj3);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj4 : list2) {
            if (((LottieDynamicProperty) obj4).property instanceof ScaleXY) {
                arrayList4.add(obj4);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj5 : list2) {
            if (((LottieDynamicProperty) obj5).property instanceof ColorFilter) {
                arrayList5.add(obj5);
            }
        }
        ArrayList arrayList6 = new ArrayList();
        for (Object obj6 : list2) {
            if (((LottieDynamicProperty) obj6).property instanceof Object[]) {
                arrayList6.add(obj6);
            }
        }
        ArrayList arrayList7 = new ArrayList();
        for (Object obj7 : list2) {
            if (((LottieDynamicProperty) obj7).property instanceof Typeface) {
                arrayList7.add(obj7);
            }
        }
        ArrayList arrayList8 = new ArrayList();
        for (Object obj8 : list2) {
            if (((LottieDynamicProperty) obj8).property instanceof Bitmap) {
                arrayList8.add(obj8);
            }
        }
        ArrayList arrayList9 = new ArrayList();
        for (Object obj9 : list2) {
            if (((LottieDynamicProperty) obj9).property instanceof CharSequence) {
                arrayList9.add(obj9);
            }
        }
        this(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7, arrayList8, arrayList9);
    }
}
