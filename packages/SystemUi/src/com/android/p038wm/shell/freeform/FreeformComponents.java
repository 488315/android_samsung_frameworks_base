package com.android.p038wm.shell.freeform;

import android.content.Context;
import android.provider.Settings;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.transition.Transitions;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformComponents {
    public final ShellTaskOrganizer.TaskListener mTaskListener;

    public FreeformComponents(ShellTaskOrganizer.TaskListener taskListener, Optional<Transitions.TransitionHandler> optional, Optional<Transitions.TransitionObserver> optional2) {
        this.mTaskListener = taskListener;
    }

    public static boolean isFreeformEnabled(Context context) {
        return context.getPackageManager().hasSystemFeature("android.software.freeform_window_management") || Settings.Global.getInt(context.getContentResolver(), "enable_freeform_support", 0) != 0;
    }
}
