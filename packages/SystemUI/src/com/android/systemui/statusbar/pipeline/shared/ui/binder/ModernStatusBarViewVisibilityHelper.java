package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ModernStatusBarViewVisibilityHelper {
    public static final Companion Companion = new Companion(null);

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
