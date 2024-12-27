package com.android.internal.pm.pkg.component;

import java.util.List;

public interface ParsedAttribution {
    public static final int MAX_ATTRIBUTION_TAG_LEN = 50;

    List<String> getInheritFrom();

    int getLabel();

    String getTag();
}
