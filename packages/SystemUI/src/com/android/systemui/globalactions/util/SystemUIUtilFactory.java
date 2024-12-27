package com.android.systemui.globalactions.util;

import android.content.Context;
import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.plugins.GlobalActions;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.UtilFactory;

public final class SystemUIUtilFactory implements UtilFactory {
    public final UtilFactory mDefaultUtilFactory;
    public final ArrayMap mProvider;

    public SystemUIUtilFactory(Context context, GlobalActions.GlobalActionsManager globalActionsManager, UtilFactory utilFactory) {
        ArrayMap arrayMap = new ArrayMap();
        this.mProvider = arrayMap;
        this.mDefaultUtilFactory = utilFactory;
        arrayMap.put(GlobalActions.GlobalActionsManager.class, globalActionsManager);
        arrayMap.put(KeyguardUpdateMonitorWrapper.class, new KeyguardUpdateMonitorWrapper(context));
        arrayMap.put(Context.class, context);
        arrayMap.put(CoverUtilWrapper.class, Dependency.sDependency.getDependencyInner(CoverUtilWrapper.class));
        arrayMap.put(ActivityStarterWrapper.class, new ActivityStarterWrapper(context));
        arrayMap.put(ScreenCapturePopupController.class, new ScreenCapturePopupController(context, (LogWrapper) utilFactory.get(LogWrapper.class)));
        arrayMap.put(KnoxCustomManagerWrapper.class, new KnoxCustomManagerWrapper(context));
        arrayMap.put(KnoxEDMWrapper.class, new KnoxEDMWrapper(context));
        arrayMap.put(ProKioskManagerWrapper.class, new ProKioskManagerWrapper(context, (LogWrapper) utilFactory.get(LogWrapper.class)));
        arrayMap.put(KioskModeWrapper.class, new KioskModeWrapper(context));
        arrayMap.put(SemEnterpriseDeviceManagerWrapper.class, new SemEnterpriseDeviceManagerWrapper(context));
    }

    public final Object get(Class cls) {
        return this.mProvider.containsKey(cls) ? this.mProvider.get(cls) : this.mDefaultUtilFactory.get(cls);
    }
}
