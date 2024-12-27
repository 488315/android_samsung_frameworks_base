package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.Collection;

public interface SysuiDarkIconDispatcher extends DarkIconDispatcher, Dumpable {

    public final class DarkChange {
        public static final DarkChange EMPTY = new DarkChange(new ArrayList(), 0.0f, -301989889);
        public final Collection areas;
        public final int tint;

        public DarkChange(Collection<Rect> collection, float f, int i) {
            this.areas = collection;
            this.tint = i;
        }
    }
}
