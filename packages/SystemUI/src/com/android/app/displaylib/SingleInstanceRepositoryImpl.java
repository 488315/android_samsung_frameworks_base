package com.android.app.displaylib;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
