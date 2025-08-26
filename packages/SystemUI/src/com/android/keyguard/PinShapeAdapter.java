package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.android.systemui.bouncer.shared.constants.PinBouncerConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.random.XorWowRandom;

/* loaded from: classes.dex */
public final class PinShapeAdapter {
    public final List shapes = new ArrayList();

    public PinShapeAdapter(Context context) throws Resources.NotFoundException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        new XorWowRandom((int) jCurrentTimeMillis, (int) (jCurrentTimeMillis >> 32));
        TypedArray typedArrayObtainTypedArray = context.getResources().obtainTypedArray(PinBouncerConstants.pinShapes);
        int length = typedArrayObtainTypedArray.length();
        for (int i = 0; i < length; i++) {
            ((ArrayList) this.shapes).add(Integer.valueOf(typedArrayObtainTypedArray.getResourceId(i, 0)));
        }
        Collections.shuffle(this.shapes);
        typedArrayObtainTypedArray.recycle();
    }
}
