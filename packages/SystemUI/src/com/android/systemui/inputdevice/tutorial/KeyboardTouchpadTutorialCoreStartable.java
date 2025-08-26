package com.android.systemui.inputdevice.tutorial;

import android.content.Context;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.Lazy;

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
