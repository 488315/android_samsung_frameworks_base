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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GestureHintGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList hintGroup;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) it.next();
            int size = buttonDispatcher.mViews.size();
            for (int i2 = 0; i2 < size; i2++) {
                KeyEvent.Callback callback = (View) buttonDispatcher.mViews.get(i2);
                if (callback instanceof ButtonInterface) {
                    ((ButtonInterface) callback).setCurrentRotation(i, z);
                }
            }
        }
    }
}
