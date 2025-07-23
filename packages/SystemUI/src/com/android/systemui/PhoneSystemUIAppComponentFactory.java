package com.android.systemui;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PhoneSystemUIAppComponentFactory extends SystemUIAppComponentFactoryBase {
    @Override // com.android.systemui.SystemUIAppComponentFactoryBase
    public final SystemUIInitializerImpl createSystemUIInitializer(Context context) {
        return new SystemUIInitializerImpl(context);
    }
}
