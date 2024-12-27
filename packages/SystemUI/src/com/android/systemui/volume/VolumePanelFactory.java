package com.android.systemui.volume;

import android.content.Context;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumePanelFactory {
    public static VolumePanelDialog volumePanelDialog;
    public final ActivityStarter activityStarter;
    public final Context context;

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

    public VolumePanelFactory(Context context, ActivityStarter activityStarter, DialogTransitionAnimator dialogTransitionAnimator) {
        this.context = context;
        this.activityStarter = activityStarter;
    }
}
