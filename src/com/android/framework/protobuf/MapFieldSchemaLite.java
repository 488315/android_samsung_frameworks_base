package com.android.framework.protobuf;

import com.android.framework.protobuf.MapEntryLite;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes5.dex */
class MapFieldSchemaLite implements MapFieldSchema {
    MapFieldSchemaLite() {
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public Map<?, ?> forMutableMapData(Object obj) {
        return (MapFieldLite) obj;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public MapEntryLite.Metadata<?, ?> forMapMetadata(Object obj) {
        return ((MapEntryLite) obj).getMetadata();
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public Map<?, ?> forMapData(Object obj) {
        return (MapFieldLite) obj;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public boolean isImmutable(Object obj) {
        return !((MapFieldLite) obj).isMutable();
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public Object toImmutable(Object obj) {
        ((MapFieldLite) obj).makeImmutable();
        return obj;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public Object newMapField(Object obj) {
        return MapFieldLite.emptyMapField().mutableCopy();
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public Object mergeFrom(Object obj, Object obj2) {
        return mergeFromLite(obj, obj2);
    }

    private static <K, V> MapFieldLite<K, V> mergeFromLite(Object obj, Object obj2) {
        MapFieldLite<K, V> mapFieldLite = (MapFieldLite) obj;
        MapFieldLite<K, V> mapFieldLite2 = (MapFieldLite) obj2;
        if (!mapFieldLite2.isEmpty()) {
            if (!mapFieldLite.isMutable()) {
                mapFieldLite = mapFieldLite.mutableCopy();
            }
            mapFieldLite.mergeFrom(mapFieldLite2);
        }
        return mapFieldLite;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public int getSerializedSize(int i, Object obj, Object obj2) {
        return getSerializedSizeLite(i, obj, obj2);
    }

    private static <K, V> int getSerializedSizeLite(int i, Object obj, Object obj2) {
        MapFieldLite mapFieldLite = (MapFieldLite) obj;
        MapEntryLite mapEntryLite = (MapEntryLite) obj2;
        int i2 = 0;
        if (mapFieldLite.isEmpty()) {
            return 0;
        }
        for (Map.Entry<K, V> entry : mapFieldLite.entrySet()) {
            i2 += mapEntryLite.computeMessageSize(i, entry.getKey(), entry.getValue());
        }
        return i2;
    }
}
