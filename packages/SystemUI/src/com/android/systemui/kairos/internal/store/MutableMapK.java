package com.android.systemui.kairos.internal.store;

import java.util.Map;
import kotlin.jvm.internal.markers.KMutableMap;

/* loaded from: classes2.dex */
public interface MutableMapK extends Map, KMutableMap {

    public interface Factory {
        MutableMapK create(Integer num);
    }

    MapK readOnlyCopy();
}
