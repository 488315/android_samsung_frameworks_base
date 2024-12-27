package com.android.systemui.keyboard.shortcut;

import com.android.systemui.CoreStartable;

public final class NoOpStartable implements CoreStartable {
    public static final NoOpStartable INSTANCE = new NoOpStartable();

    private NoOpStartable() {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
