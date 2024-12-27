package com.android.systemui.volume.panel.ui.composable;

import java.util.Map;
import javax.inject.Provider;

public final class ComponentsFactory {
    public final Map componentByKey;

    public ComponentsFactory(Map<String, Provider> map) {
        this.componentByKey = map;
    }
}
