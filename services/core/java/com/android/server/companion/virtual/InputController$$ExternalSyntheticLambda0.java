package com.android.server.companion.virtual;

import android.os.IBinder;

import java.util.concurrent.atomic.AtomicLong;

public final /* synthetic */ class InputController$$ExternalSyntheticLambda0
        implements IBinder.DeathRecipient {
    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        AtomicLong atomicLong = InputController.sNextPhysId;
    }
}
