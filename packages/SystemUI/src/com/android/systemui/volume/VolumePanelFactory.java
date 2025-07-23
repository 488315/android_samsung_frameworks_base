package com.android.systemui.volume;

import android.content.Context;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelFactory {
    public static VolumePanelDialog volumePanelDialog;
    public final ActivityStarter activityStarter;
    public final Context context;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public VolumePanelFactory(Context context, ActivityStarter activityStarter, DialogTransitionAnimator dialogTransitionAnimator) {
        this.context = context;
        this.activityStarter = activityStarter;
    }
}
