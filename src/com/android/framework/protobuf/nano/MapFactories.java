package com.android.framework.protobuf.nano;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public final class MapFactories {
    private static volatile MapFactory mapFactory = new DefaultMapFactory();

    public interface MapFactory {
        <K, V> Map<K, V> forMap(Map<K, V> map);
    }

    static void setMapFactory(MapFactory mapFactory2) {
        mapFactory = mapFactory2;
    }

    public static MapFactory getMapFactory() {
        return mapFactory;
    }

    private static class DefaultMapFactory implements MapFactory {
        private DefaultMapFactory() {
        }

        @Override // com.android.framework.protobuf.nano.MapFactories.MapFactory
        public <K, V> Map<K, V> forMap(Map<K, V> map) {
            return map == null ? new HashMap() : map;
        }
    }

    private MapFactories() {
    }
}
