package com.android.systemui.display.dagger;

import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public interface SystemUIDisplaySubcomponent {

    public interface Factory {
        SystemUIDisplaySubcomponent create(int i);
    }

    CoroutineScope getDisplayCoroutineScope();
}
