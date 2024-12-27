package com.android.server.companion.virtual;

import android.os.IBinder;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InputController$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        AtomicLong atomicLong = InputController.sNextPhysId;
    }
}
