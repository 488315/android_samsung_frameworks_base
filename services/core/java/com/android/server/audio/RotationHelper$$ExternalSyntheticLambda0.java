package com.android.server.audio;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RotationHelper$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        synchronized (RotationHelper.sFoldStateLock) {
            try {
                Boolean bool2 = RotationHelper.sFoldState;
                if (bool2 != null) {
                    if (bool2.booleanValue() != booleanValue) {}
                }
                RotationHelper.sFoldState = bool;
                RotationHelper.sFoldStateCallback.accept(bool);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
