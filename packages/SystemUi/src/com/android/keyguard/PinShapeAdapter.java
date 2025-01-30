package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.random.XorWowRandom;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PinShapeAdapter {
    public final List shapes = new ArrayList();

    public PinShapeAdapter(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        new XorWowRandom((int) currentTimeMillis, (int) (currentTimeMillis >> 32));
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(R.array.bouncer_pin_shapes);
        int length = obtainTypedArray.length();
        for (int i = 0; i < length; i++) {
            this.shapes.add(Integer.valueOf(obtainTypedArray.getResourceId(i, 0)));
        }
        Collections.shuffle(this.shapes);
        obtainTypedArray.recycle();
    }
}
