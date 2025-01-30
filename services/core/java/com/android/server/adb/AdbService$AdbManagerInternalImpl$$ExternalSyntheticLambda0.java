package com.android.server.adb;

import com.android.internal.util.function.TriConsumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AdbService$AdbManagerInternalImpl$$ExternalSyntheticLambda0 implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        ((AdbService) obj).setAdbdEnabledForTransport(((Boolean) obj2).booleanValue(), ((Byte) obj3).byteValue());
    }
}
