package com.android.systemui.biometrics.dagger;

import android.R;
import android.content.res.Resources;
import com.android.systemui.biometrics.EllipseOverlapDetectorParams;
import com.android.systemui.biometrics.udfps.BoundingBoxOverlapDetector;
import com.android.systemui.biometrics.udfps.EllipseOverlapDetector;
import com.android.systemui.biometrics.udfps.OverlapDetector;
import dagger.internal.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BiometricsModule_Companion_ProvidesOverlapDetectorFactory implements Provider {
    public static OverlapDetector providesOverlapDetector() {
        BiometricsModule.Companion.getClass();
        List split$default = StringsKt__StringsKt.split$default(Resources.getSystem().getStringArray(17236356)[Resources.getSystem().getInteger(R.integer.device_idle_sensing_to_ms)], new String[]{","}, 0, 6);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
        Iterator it = split$default.iterator();
        while (it.hasNext()) {
            arrayList.add(Float.valueOf(Float.parseFloat((String) it.next())));
        }
        return ((Number) arrayList.get(0)).floatValue() == 1.0f ? new EllipseOverlapDetector(new EllipseOverlapDetectorParams(((Number) arrayList.get(3)).floatValue(), ((Number) arrayList.get(2)).floatValue(), (int) ((Number) arrayList.get(4)).floatValue())) : new BoundingBoxOverlapDetector(((Number) arrayList.get(2)).floatValue());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesOverlapDetector();
    }
}
