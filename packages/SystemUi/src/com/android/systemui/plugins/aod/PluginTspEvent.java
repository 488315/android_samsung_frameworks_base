package com.android.systemui.plugins.aod;

import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PluginTspEvent {
    public final int action;

    /* renamed from: x */
    public final int f330x;

    /* renamed from: y */
    public final int f331y;

    public PluginTspEvent(int i, int i2, int i3) {
        this.action = i;
        this.f330x = i2;
        this.f331y = i3;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "[PluginTspEvent : action = %s, x = %d, y = %d]", Integer.valueOf(this.action), Integer.valueOf(this.f330x), Integer.valueOf(this.f331y));
    }
}
