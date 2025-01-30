package com.android.systemui.statusbar.pipeline.shared.data.model;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class DataActivityModelKt {
    public static final DataActivityModel toMobileDataActivityModel(int i) {
        return i != 1 ? i != 2 ? i != 3 ? new DataActivityModel(false, false) : new DataActivityModel(true, true) : new DataActivityModel(false, true) : new DataActivityModel(true, false);
    }

    public static final DataActivityModel toWifiDataActivityModel(int i) {
        return i != 1 ? i != 2 ? i != 3 ? new DataActivityModel(false, false) : new DataActivityModel(true, true) : new DataActivityModel(false, true) : new DataActivityModel(true, false);
    }
}
