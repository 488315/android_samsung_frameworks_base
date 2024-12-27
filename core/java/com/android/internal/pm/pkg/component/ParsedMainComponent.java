package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedMainComponent extends ParsedComponent {
    String[] getAttributionTags();

    String getClassName();

    int getOrder();

    String getProcessName();

    String getSplitName();

    boolean isDirectBootAware();

    boolean isEnabled();

    boolean isExported();
}
