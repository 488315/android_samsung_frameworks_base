package com.android.systemui.volume.panel.component.selector.ui.composable;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumePanelRadioButtonBarScopeImpl implements VolumePanelRadioButtonBarScope {
    public final List items;
    public final List mutableItems;
    public int selectedIndex = -1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
