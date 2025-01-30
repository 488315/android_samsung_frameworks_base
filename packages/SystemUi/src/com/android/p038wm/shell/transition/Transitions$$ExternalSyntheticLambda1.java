package com.android.p038wm.shell.transition;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;
import android.window.TransitionMetrics;
import android.window.WindowOrganizer;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.transition.Transitions;
import com.android.wm.shell.transition.Transitions.SettingsObserver;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class Transitions$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Transitions$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final Transitions transitions = (Transitions) this.f$0;
                WindowOrganizer windowOrganizer = transitions.mOrganizer;
                boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
                if (z) {
                    windowOrganizer.shareTransactionQueue();
                }
                transitions.mShellController.addExternalInterface(QuickStepContract.KEY_EXTRA_SHELL_SHELL_TRANSITIONS, new Supplier() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda5
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Transitions transitions2 = Transitions.this;
                        transitions2.getClass();
                        return new Transitions.IShellTransitionsImpl(transitions2);
                    }
                }, transitions);
                Context context = transitions.mContext;
                ContentResolver contentResolver = context.getContentResolver();
                float fixScale = WindowManager.fixScale(Settings.Global.getFloat(context.getContentResolver(), "transition_animation_scale", context.getResources().getFloat(R.dimen.chooser_preview_width)));
                transitions.mTransitionAnimationScaleSetting = fixScale;
                ArrayList arrayList = transitions.mHandlers;
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        contentResolver.registerContentObserver(Settings.Global.getUriFor("transition_animation_scale"), false, transitions.new SettingsObserver());
                        if (z) {
                            transitions.mIsRegistered = true;
                            try {
                                windowOrganizer.registerTransitionPlayer(transitions.mPlayerImpl);
                                TransitionMetrics.getInstance();
                            } catch (RuntimeException e) {
                                transitions.mIsRegistered = false;
                                throw e;
                            }
                        }
                        ShellCommandHandler shellCommandHandler = transitions.mShellCommandHandler;
                        if (shellCommandHandler != null) {
                            shellCommandHandler.addCommandCallback("transitions", transitions, transitions);
                            return;
                        }
                        return;
                    }
                    ((Transitions.TransitionHandler) arrayList.get(size)).setAnimScaleSetting(fixScale);
                }
            default:
                Transitions transitions2 = Transitions.this;
                float f = transitions2.mTransitionAnimationScaleSetting;
                ArrayList arrayList2 = transitions2.mHandlers;
                int size2 = arrayList2.size();
                while (true) {
                    size2--;
                    if (size2 < 0) {
                        return;
                    } else {
                        ((Transitions.TransitionHandler) arrayList2.get(size2)).setAnimScaleSetting(f);
                    }
                }
        }
    }
}
