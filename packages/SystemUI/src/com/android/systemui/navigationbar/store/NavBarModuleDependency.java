package com.android.systemui.navigationbar.store;

import java.util.HashMap;

public final class NavBarModuleDependency {
    public final HashMap modules;

    public NavBarModuleDependency() {
        HashMap hashMap = new HashMap();
        this.modules = hashMap;
        hashMap.clear();
    }
}
