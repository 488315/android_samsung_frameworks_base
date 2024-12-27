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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BiometricsModule_Companion_ProvidesOverlapDetectorFactory implements Provider {
    public static OverlapDetector providesOverlapDetector() {
        BiometricsModule.Companion.getClass();
        List split$default = StringsKt__StringsKt.split$default(Resources.getSystem().getStringArray(17236342)[Resources.getSystem().getInteger(R.integer.default_data_warning_level_mb)], new String[]{","}, 0, 6);
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
