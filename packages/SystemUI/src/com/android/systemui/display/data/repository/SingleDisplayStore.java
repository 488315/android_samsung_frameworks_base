package com.android.systemui.display.data.repository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SingleDisplayStore implements PerDisplayStore {
    public final Object defaultDisplay;

    public SingleDisplayStore(Object obj) {
        this.defaultDisplay = obj;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        throw null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        throw null;
    }
}
