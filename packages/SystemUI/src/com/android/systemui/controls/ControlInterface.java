package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
