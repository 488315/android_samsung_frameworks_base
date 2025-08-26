package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;

/* loaded from: classes2.dex */
public interface ControlInterface {
    ComponentName getComponent();

    String getControlId();

    Icon getCustomIcon();

    int getDeviceType();

    boolean getFavorite();

    boolean getRemoved();

    CharSequence getSubtitle();

    CharSequence getTitle();
}
