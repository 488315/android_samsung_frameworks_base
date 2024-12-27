package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface SysuiDarkIconDispatcher extends DarkIconDispatcher, Dumpable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
