package com.android.systemui.navigationbar.gestural;

import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GestureHintGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList hintGroup;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public GestureHintGroup(SparseArray<ButtonDispatcher> sparseArray) {
        ArrayList arrayList = new ArrayList();
        this.hintGroup = arrayList;
        arrayList.clear();
        arrayList.add(sparseArray.get(R.id.hint_left));
        arrayList.add(sparseArray.get(R.id.hint_center));
        arrayList.add(sparseArray.get(R.id.hint_right));
    }

    public final void setCurrentRotation(int i, boolean z) {
        Iterator it = this.hintGroup.iterator();
        while (it.hasNext()) {
            ArrayList arrayList = ((ButtonDispatcher) it.next()).mViews;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                KeyEvent.Callback callback = (View) arrayList.get(i2);
                if (callback instanceof ButtonInterface) {
                    ((ButtonInterface) callback).setCurrentRotation(i, z);
                }
            }
        }
    }
}
