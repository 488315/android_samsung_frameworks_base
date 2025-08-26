package com.android.app.displaylib;

/* loaded from: classes.dex */
public final class SingleInstanceRepositoryImpl implements PerDisplayRepository {
    public final Object instance;

    public SingleInstanceRepositoryImpl(String str, Object obj) {
        this.instance = obj;
    }

    @Override // com.android.app.displaylib.PerDisplayRepository
    public final Object get(int i) {
        return this.instance;
    }
}
