package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
public final class MapFieldSchemaLite implements MapFieldSchema {
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
