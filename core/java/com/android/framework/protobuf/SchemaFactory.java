package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes4.dex */
interface SchemaFactory {
    <T> Schema<T> createSchema(Class<T> cls);
}
