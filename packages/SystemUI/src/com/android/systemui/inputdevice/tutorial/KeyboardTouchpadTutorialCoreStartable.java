package com.android.systemui.inputdevice.tutorial;

import android.content.Context;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardTouchpadTutorialCoreStartable implements CoreStartable {
    public final Context applicationContext;

    public KeyboardTouchpadTutorialCoreStartable(Lazy lazy, BroadcastDispatcher broadcastDispatcher, Context context) {
        this.applicationContext = context;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
