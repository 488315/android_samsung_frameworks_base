package com.android.systemui.statusbar.pipeline.shared.data.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class DataActivityModelKt {
    public static final DataActivityModel toMobileDataActivityModel(int i) {
        return i != 1 ? i != 2 ? i != 3 ? new DataActivityModel(false, false) : new DataActivityModel(true, true) : new DataActivityModel(false, true) : new DataActivityModel(true, false);
    }
}
