package com.android.server.wm.utils;

import android.content.Context;

import java.util.function.IntSupplier;

public final class DimenPxIntSupplier implements IntSupplier {
    public final Context mContext;
    public final int mResourceId;
    public float mLastDensity = Float.MIN_VALUE;
    public int mValue = 0;

    public DimenPxIntSupplier(Context context, int i) {
        this.mContext = context;
        this.mResourceId = i;
    }

    @Override // java.util.function.IntSupplier
    public final int getAsInt() {
        float f = this.mContext.getResources().getDisplayMetrics().density;
        if (f != this.mLastDensity) {
            this.mLastDensity = f;
            this.mValue = this.mContext.getResources().getDimensionPixelSize(this.mResourceId);
        }
        return this.mValue;
    }
}
