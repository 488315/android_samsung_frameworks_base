package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes5.dex */
interface MessageInfo {
    MessageLite getDefaultInstance();

    ProtoSyntax getSyntax();

    boolean isMessageSetWireFormat();
}
