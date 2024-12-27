package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ModernStatusBarViewVisibilityHelper {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static void setVisibilityState(View view, View view2, int i) {
            if (i == 0) {
                view.setVisibility(0);
                view2.setVisibility(8);
            } else if (i == 1) {
                view.setVisibility(4);
                view2.setVisibility(0);
            } else {
                if (i != 2) {
                    return;
                }
                view.setVisibility(4);
                view2.setVisibility(4);
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
