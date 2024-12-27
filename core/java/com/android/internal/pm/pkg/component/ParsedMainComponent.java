package com.android.internal.pm.pkg.component;

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
