package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import com.android.systemui.bouncer.shared.constants.PinBouncerConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.random.XorWowRandom;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PinShapeAdapter {
    public final List shapes = new ArrayList();

    public PinShapeAdapter(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        new XorWowRandom((int) currentTimeMillis, (int) (currentTimeMillis >> 32));
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(PinBouncerConstants.pinShapes);
        int length = obtainTypedArray.length();
        for (int i = 0; i < length; i++) {
            ((ArrayList) this.shapes).add(Integer.valueOf(obtainTypedArray.getResourceId(i, 0)));
        }
        Collections.shuffle(this.shapes);
        obtainTypedArray.recycle();
    }
}
