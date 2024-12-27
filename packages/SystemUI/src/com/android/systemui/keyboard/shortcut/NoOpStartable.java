package com.android.systemui.keyboard.shortcut;

import com.android.systemui.CoreStartable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NoOpStartable implements CoreStartable {
    public static final NoOpStartable INSTANCE = new NoOpStartable();

    private NoOpStartable() {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
