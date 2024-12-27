package com.android.systemui;

import android.content.Context;

public final class PhoneSystemUIAppComponentFactory extends SystemUIAppComponentFactoryBase {
    @Override // com.android.systemui.SystemUIAppComponentFactoryBase
    public final SystemUIInitializerImpl createSystemUIInitializer(Context context) {
        return new SystemUIInitializerImpl(context);
    }
}
