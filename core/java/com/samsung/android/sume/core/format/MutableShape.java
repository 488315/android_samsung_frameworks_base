package com.samsung.android.sume.core.format;

import android.util.Pair;

public interface MutableShape extends Shape {
    MutableShape scale(Pair<Float, Float> pair);

    MutableShape scale(Float f);

    MutableShape setBatch(int i);

    MutableShape setChannels(int i);

    MutableShape setCols(int i);

    MutableShape setRows(int i);

    <V extends Shape> V toShape();
}
