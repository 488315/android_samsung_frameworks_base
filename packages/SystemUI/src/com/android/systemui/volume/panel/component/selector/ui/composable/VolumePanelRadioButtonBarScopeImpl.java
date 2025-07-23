package com.android.systemui.volume.panel.component.selector.ui.composable;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelRadioButtonBarScopeImpl implements VolumePanelRadioButtonBarScope {
    public final List items;
    public final List mutableItems;
    public int selectedIndex = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
