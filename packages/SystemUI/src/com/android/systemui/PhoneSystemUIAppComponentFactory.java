package com.android.systemui;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PhoneSystemUIAppComponentFactory extends SystemUIAppComponentFactoryBase {
    @Override // com.android.systemui.SystemUIAppComponentFactoryBase
    public final SystemUIInitializerImpl createSystemUIInitializer(Context context) {
        return new SystemUIInitializerImpl(context);
    }
}
