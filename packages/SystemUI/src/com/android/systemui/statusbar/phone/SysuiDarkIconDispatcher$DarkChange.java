package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes3.dex */
public class SysuiDarkIconDispatcher$DarkChange {
    public static final SysuiDarkIconDispatcher$DarkChange EMPTY = new SysuiDarkIconDispatcher$DarkChange(new ArrayList(), 0.0f, -301989889);
    public final Collection areas;
    public final float darkIntensity;
    public final int tint;

    public SysuiDarkIconDispatcher$DarkChange(Collection<Rect> collection, float f, int i) {
        this.areas = collection;
        this.darkIntensity = f;
        this.tint = i;
    }
}
