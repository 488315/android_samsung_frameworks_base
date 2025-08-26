package com.android.keyguard;

import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class SecFaceAuthCallback$$ExternalSyntheticLambda3 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = SecFaceAuthCallback.$r8$clinit;
        ((Consumer) obj).accept(SecFaceMsg.obtain(3, -1, null, null));
    }
}
