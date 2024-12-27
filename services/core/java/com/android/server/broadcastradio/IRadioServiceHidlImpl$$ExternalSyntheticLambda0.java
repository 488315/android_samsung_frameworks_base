package com.android.server.broadcastradio;

import android.hardware.radio.RadioManager;

import java.util.function.ToIntFunction;

public final /* synthetic */ class IRadioServiceHidlImpl$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((RadioManager.ModuleProperties) obj).getId();
    }
}
