package com.google.protobuf;

import java.util.Map;

/* loaded from: classes4.dex */
public class MapFieldSchemaLite implements MapFieldSchema {
    public final int getSerializedSize(int i, Object obj, Object obj2) {
        MapFieldLite mapFieldLite = (MapFieldLite) obj;
        MapEntryLite mapEntryLite = (MapEntryLite) obj2;
        int iM = 0;
        if (mapFieldLite.isEmpty()) {
            return 0;
        }
        for (Map.Entry entry : mapFieldLite.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            mapEntryLite.getClass();
            int iComputeTagSize = CodedOutputStream.computeTagSize(i);
            int iComputeSerializedSize = MapEntryLite.computeSerializedSize(mapEntryLite.metadata, key, value);
            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSerializedSize, iComputeSerializedSize, iComputeTagSize, iM);
        }
        return iM;
    }

    public final MapFieldLite mergeFrom(Object obj, Object obj2) {
        MapFieldLite mapFieldLiteMutableCopy = (MapFieldLite) obj;
        MapFieldLite mapFieldLite = (MapFieldLite) obj2;
        if (!mapFieldLite.isEmpty()) {
            if (!mapFieldLiteMutableCopy.isMutable()) {
                mapFieldLiteMutableCopy = mapFieldLiteMutableCopy.mutableCopy();
            }
            mapFieldLiteMutableCopy.ensureMutable();
            if (!mapFieldLite.isEmpty()) {
                mapFieldLiteMutableCopy.putAll(mapFieldLite);
            }
        }
        return mapFieldLiteMutableCopy;
    }
}
