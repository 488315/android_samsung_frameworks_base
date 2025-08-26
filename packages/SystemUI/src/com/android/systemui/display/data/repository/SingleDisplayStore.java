package com.android.systemui.display.data.repository;

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
