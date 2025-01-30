package com.android.systemui;

import android.content.Context;
import com.android.systemui.util.Assert;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes.dex */
public class SystemUIAppComponentFactory extends SystemUIAppComponentFactoryBase {
    @Override // com.android.systemui.SystemUIAppComponentFactoryBase
    public final SystemUIInitializer createSystemUIInitializer(Context context) {
        SystemUIInitializer systemUIInitializer = SystemUIInitializerFactory.initializer;
        Assert.isMainThread();
        return SystemUIInitializerFactory.createFromConfigNoAssert(context);
    }
}
