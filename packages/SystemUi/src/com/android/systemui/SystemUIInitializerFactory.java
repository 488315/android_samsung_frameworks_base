package com.android.systemui;

import android.content.Context;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIInitializerFactory {
    public static SystemUIInitializer initializer;

    static {
        new SystemUIInitializerFactory();
    }

    private SystemUIInitializerFactory() {
    }

    public static final SystemUIInitializer createFromConfigNoAssert(Context context) {
        SystemUIInitializer systemUIInitializer = initializer;
        if (systemUIInitializer != null) {
            return systemUIInitializer;
        }
        String string = context.getString(R.string.config_systemUIFactoryComponent);
        if (string.length() == 0) {
            throw new RuntimeException("No SystemUIFactory component configured");
        }
        try {
            SystemUIInitializer systemUIInitializer2 = (SystemUIInitializer) context.getClassLoader().loadClass(string).getConstructor(Context.class).newInstance(context);
            initializer = systemUIInitializer2;
            return systemUIInitializer2;
        } catch (Throwable th) {
            Log.w("SysUIInitializerFactory", "Error creating SystemUIInitializer component: ".concat(string), th);
            throw th;
        }
    }
}
