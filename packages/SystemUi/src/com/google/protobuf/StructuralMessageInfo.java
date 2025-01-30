package com.google.protobuf;

import java.nio.charset.Charset;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        Charset charset = Internal.UTF_8;
        if (obj == null) {
            throw new NullPointerException("defaultInstance");
        }
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
