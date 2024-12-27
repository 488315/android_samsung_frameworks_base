package com.android.server.policy;

import android.util.Log;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
