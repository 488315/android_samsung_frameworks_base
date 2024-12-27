package com.android.systemui.navigationbar;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Function;

public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda18 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((CentralSurfacesImpl) ((CentralSurfaces) obj)).mDeviceInteractive);
    }
}
