package com.google.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StructuralMessageInfo implements MessageInfo {
    public final int[] checkInitialized;
    public final MessageLite defaultInstance;
    public final FieldInfo[] fields;
    public final boolean messageSetWireFormat;
    public final ProtoSyntax syntax;

    public StructuralMessageInfo(ProtoSyntax protoSyntax, boolean z, int[] iArr, FieldInfo[] fieldInfoArr, Object obj) {
        this.syntax = protoSyntax;
        this.messageSetWireFormat = z;
        this.checkInitialized = iArr;
        this.fields = fieldInfoArr;
        Internal.checkNotNull(obj, "defaultInstance");
        this.defaultInstance = (MessageLite) obj;
    }

    @Override // com.google.protobuf.MessageInfo
    public final MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    @Override // com.google.protobuf.MessageInfo
    public final ProtoSyntax getSyntax() {
        return this.syntax;
    }

    @Override // com.google.protobuf.MessageInfo
    public final boolean isMessageSetWireFormat() {
        return this.messageSetWireFormat;
    }
}
