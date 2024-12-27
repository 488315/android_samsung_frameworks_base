package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes3.dex */
interface MessageInfo {
    MessageLite getDefaultInstance();

    ProtoSyntax getSyntax();

    boolean isMessageSetWireFormat();
}
