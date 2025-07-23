package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes5.dex */
interface MessageInfoFactory {
    boolean isSupported(Class<?> cls);

    MessageInfo messageInfoFor(Class<?> cls);
}
