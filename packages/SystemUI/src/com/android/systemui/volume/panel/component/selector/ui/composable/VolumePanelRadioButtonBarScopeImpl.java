package com.android.systemui.volume.panel.component.selector.ui.composable;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumePanelRadioButtonBarScopeImpl implements VolumePanelRadioButtonBarScope {
    public final List items;
    public final List mutableItems;
    public int selectedIndex = -1;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public VolumePanelRadioButtonBarScopeImpl() {
        ArrayList arrayList = new ArrayList();
        this.mutableItems = arrayList;
        this.items = arrayList;
    }
}
