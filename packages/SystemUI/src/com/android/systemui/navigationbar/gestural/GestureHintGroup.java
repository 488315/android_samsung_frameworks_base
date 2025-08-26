package com.android.systemui.navigationbar.gestural;

import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.navigationbar.views.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.views.buttons.ButtonInterface;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class GestureHintGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList hintGroup;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        ArrayList arrayList = this.hintGroup;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) obj;
            int size2 = buttonDispatcher.mViews.size();
            for (int i3 = 0; i3 < size2; i3++) {
                KeyEvent.Callback callback = (View) buttonDispatcher.mViews.get(i3);
                if (callback instanceof ButtonInterface) {
                    ((ButtonInterface) callback).setCurrentRotation(i, z);
                }
            }
        }
    }
}
