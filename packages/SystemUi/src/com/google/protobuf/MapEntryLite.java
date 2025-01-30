package com.google.protobuf;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MapEntryLite {
    public final Object key;
    public final Metadata metadata;
    public final Object value;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Metadata {
        public final Object defaultKey;
        public final Object defaultValue;
        public final WireFormat$FieldType keyType;
        public final WireFormat$FieldType valueType;

        public Metadata(WireFormat$FieldType wireFormat$FieldType, Object obj, WireFormat$FieldType wireFormat$FieldType2, Object obj2) {
            this.keyType = wireFormat$FieldType;
            this.defaultKey = obj;
            this.valueType = wireFormat$FieldType2;
            this.defaultValue = obj2;
        }
    }

    private MapEntryLite(WireFormat$FieldType wireFormat$FieldType, Object obj, WireFormat$FieldType wireFormat$FieldType2, Object obj2) {
        this.metadata = new Metadata(wireFormat$FieldType, obj, wireFormat$FieldType2, obj2);
        this.key = obj;
        this.value = obj2;
    }

    public static int computeSerializedSize(Metadata metadata, Object obj, Object obj2) {
        return FieldSet.computeElementSize(metadata.valueType, 2, obj2) + FieldSet.computeElementSize(metadata.keyType, 1, obj);
    }

    public static void writeTo(CodedOutputStream codedOutputStream, Metadata metadata, Object obj, Object obj2) {
        FieldSet.writeElement(codedOutputStream, metadata.keyType, 1, obj);
        FieldSet.writeElement(codedOutputStream, metadata.valueType, 2, obj2);
    }

    private MapEntryLite(Metadata metadata, Object obj, Object obj2) {
        this.metadata = metadata;
        this.key = obj;
        this.value = obj2;
    }
}
