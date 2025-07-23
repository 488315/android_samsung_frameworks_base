package com.android.systemui.volume.panel.ui.layout;

import java.util.Collection;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
