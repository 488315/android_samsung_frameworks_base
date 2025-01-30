package com.android.systemui.p016qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InternetDialogFactory {
    public static InternetDialog internetDialog;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final Executor executor;
    public final Handler handler;
    public final InternetDialogController internetDialogController;
    public final KeyguardStateController keyguardStateController;
    public final UiEventLogger uiEventLogger;

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

    public InternetDialogFactory(Handler handler, Executor executor, InternetDialogController internetDialogController, Context context, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, KeyguardStateController keyguardStateController) {
        this.handler = handler;
        this.executor = executor;
        this.internetDialogController = internetDialogController;
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.keyguardStateController = keyguardStateController;
    }
}
