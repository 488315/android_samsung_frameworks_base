package com.android.internal.pm.pkg.component;

import android.content.IntentFilter;

/* loaded from: classes5.dex */
public interface ParsedIntentInfo {
    int getIcon();

    IntentFilter getIntentFilter();

    int getLabelRes();

    CharSequence getNonLocalizedLabel();

    boolean isHasDefault();
}
