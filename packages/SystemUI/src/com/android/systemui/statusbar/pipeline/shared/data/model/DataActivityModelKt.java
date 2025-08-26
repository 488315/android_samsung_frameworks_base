package com.android.systemui.statusbar.pipeline.shared.data.model;

/* loaded from: classes3.dex */
public abstract class DataActivityModelKt {
    public static final DataActivityModel toMobileDataActivityModel(int i) {
        return i != 1 ? i != 2 ? i != 3 ? new DataActivityModel(false, false) : new DataActivityModel(true, true) : new DataActivityModel(false, true) : new DataActivityModel(true, false);
    }
}
