package com.google.android.collect;

import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes6.dex */
public class Lists {
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> ArrayList<E> newArrayList(E... eArr) {
        ArrayList<E> arrayList = new ArrayList<>(((eArr.length * 110) / 100) + 5);
        Collections.addAll(arrayList, eArr);
        return arrayList;
    }
}
