package com.android.systemui.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;

/* loaded from: classes.dex */
public final class GSFAxes {
    public static final Map AXIS_MAP;
    public static final GSFAxes INSTANCE = new GSFAxes();
    public static final AxisDefinition OPTICAL_SIZE = null;
    public static final AxisDefinition ROUND;
    public static final AxisDefinition SLANT;
    public static final AxisDefinition WEIGHT;
    public static final AxisDefinition WIDTH;

    static {
        AxisDefinition axisDefinition = new AxisDefinition("wght", 1.0f, 400.0f, 1000.0f, 10.0f);
        WEIGHT = axisDefinition;
        AxisDefinition axisDefinition2 = new AxisDefinition("wdth", 25.0f, 100.0f, 151.0f, 1.0f);
        WIDTH = axisDefinition2;
        AxisDefinition axisDefinition3 = new AxisDefinition("slnt", 0.0f, 0.0f, -10.0f, 0.1f);
        SLANT = axisDefinition3;
        AxisDefinition axisDefinition4 = new AxisDefinition("ROND", 0.0f, 0.0f, 100.0f, 1.0f);
        ROUND = axisDefinition4;
        List<AxisDefinition> listAsList = Arrays.asList(axisDefinition, axisDefinition2, axisDefinition3, axisDefinition4, new AxisDefinition("GRAD", 0.0f, 0.0f, 100.0f, 1.0f), new AxisDefinition("opsz", 6.0f, 18.0f, 144.0f, 1.0f), new AxisDefinition("ITAL", 0.0f, 0.0f, 1.0f, 0.1f));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAsList, 10));
        for (AxisDefinition axisDefinition5 : listAsList) {
            arrayList.add(new Pair(axisDefinition5.tag.toLowerCase(Locale.ROOT), axisDefinition5));
        }
        AXIS_MAP = MapsKt__MapsKt.toMap(arrayList);
    }

    private GSFAxes() {
    }
}
