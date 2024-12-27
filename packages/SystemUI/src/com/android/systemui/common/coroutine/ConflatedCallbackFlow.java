package com.android.systemui.common.coroutine;

public final class ConflatedCallbackFlow {
    public static final ConflatedCallbackFlow INSTANCE = new ConflatedCallbackFlow();

    private ConflatedCallbackFlow() {
    }
}
