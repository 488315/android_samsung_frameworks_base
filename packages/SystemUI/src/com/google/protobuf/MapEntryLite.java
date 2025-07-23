package com.google.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MapEntryLite {
    public final Object key;
    public final Metadata metadata;
    public final Object value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Metadata {
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

    public static MapEntryLite newDefaultInstance(WireFormat$FieldType wireFormat$FieldType, Object obj, WireFormat$FieldType wireFormat$FieldType2, Object obj2) {
        return new MapEntryLite(wireFormat$FieldType, obj, wireFormat$FieldType2, obj2);
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
