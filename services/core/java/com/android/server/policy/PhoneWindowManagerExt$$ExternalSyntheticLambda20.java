package com.android.server.policy;

import android.util.Log;

import java.util.function.Consumer;

public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda20
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        if (((Boolean) obj).booleanValue()) {
            Log.d(
                    "PhoneWindowManagerExt",
                    "role success := com.google.android.googlequicksearchbox");
        } else {
            Log.d("PhoneWindowManagerExt", "role fail := com.google.android.googlequicksearchbox");
        }
    }
}
