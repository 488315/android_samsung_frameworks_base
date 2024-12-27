package com.android.systemui.volume.panel.ui.layout;

import java.util.Collection;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DefaultComponentsLayoutManager implements ComponentsLayoutManager {
    public final String bottomBar;
    public final Collection footerComponents;
    public final Collection headerComponents;

    public DefaultComponentsLayoutManager(String str, Collection<String> collection, Collection<String> collection2) {
        this.bottomBar = str;
        this.headerComponents = collection;
        this.footerComponents = collection2;
    }

    public DefaultComponentsLayoutManager(String str, Collection collection, Collection collection2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? EmptyList.INSTANCE : collection, (i & 4) != 0 ? EmptyList.INSTANCE : collection2);
    }
}
