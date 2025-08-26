package com.android.systemui.shade.display;

import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public interface ShadeDisplayPolicy {
    StateFlow getDisplayId();

    String getName();
}
