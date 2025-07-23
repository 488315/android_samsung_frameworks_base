package com.android.systemui.keyboard.shortcut;

import com.android.systemui.CoreStartable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NoOpStartable implements CoreStartable {
    public static final NoOpStartable INSTANCE = new NoOpStartable();

    private NoOpStartable() {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
