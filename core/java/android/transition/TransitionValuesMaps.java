package android.transition;

import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

class TransitionValuesMaps {
    ArrayMap<View, TransitionValues> viewValues = new ArrayMap<>();
    SparseArray<View> idValues = new SparseArray<>();
    LongSparseArray<View> itemIdValues = new LongSparseArray<>();
    ArrayMap<String, View> nameValues = new ArrayMap<>();

    TransitionValuesMaps() {}
}
