package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.random.XorWowRandom;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
