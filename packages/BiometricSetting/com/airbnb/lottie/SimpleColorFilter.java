package com.airbnb.lottie;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

/* loaded from: classes.dex */
public final class SimpleColorFilter extends PorterDuffColorFilter {
    public SimpleColorFilter(int i) {
        super(i, PorterDuff.Mode.SRC_ATOP);
    }
}
