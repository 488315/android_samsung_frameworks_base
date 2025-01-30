package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public interface DozeParameters {
    public static final int VERSION = 1;

    boolean shouldControlScreenOff();
}
