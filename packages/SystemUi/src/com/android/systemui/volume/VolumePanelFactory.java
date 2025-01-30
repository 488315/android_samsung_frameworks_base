package com.android.systemui.volume;

import android.content.Context;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumePanelFactory {
    public static VolumePanelDialog volumePanelDialog;
    public final ActivityStarter activityStarter;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;

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

    public VolumePanelFactory(Context context, ActivityStarter activityStarter, DialogLaunchAnimator dialogLaunchAnimator) {
        this.context = context;
        this.activityStarter = activityStarter;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
    }

    public final void create() {
        VolumePanelDialog volumePanelDialog2 = volumePanelDialog;
        if (volumePanelDialog2 != null && volumePanelDialog2.isShowing()) {
            return;
        }
        VolumePanelDialog volumePanelDialog3 = new VolumePanelDialog(this.context, this.activityStarter, true);
        volumePanelDialog = volumePanelDialog3;
        volumePanelDialog3.show();
    }
}
