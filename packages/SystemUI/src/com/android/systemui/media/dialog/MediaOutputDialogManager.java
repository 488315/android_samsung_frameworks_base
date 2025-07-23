package com.android.systemui.media.dialog;

import android.content.Context;
import android.media.session.MediaSession;
import android.os.UserHandle;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaSwitchingController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MediaOutputDialogManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Executor backgroundExecutor;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public Executor mainExecutor;
    public final MediaSwitchingController.Factory mediaSwitchingControllerFactory;
    public final UiEventLogger uiEventLogger;

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

    public MediaOutputDialogManager(Context context, BroadcastSender broadcastSender, UiEventLogger uiEventLogger, DialogTransitionAnimator dialogTransitionAnimator, MediaSwitchingController.Factory factory) {
        this.context = context;
        this.broadcastSender = broadcastSender;
        this.uiEventLogger = uiEventLogger;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.mediaSwitchingControllerFactory = factory;
    }

    public static void createAndShowWithController$default(MediaOutputDialogManager mediaOutputDialogManager, String str, boolean z, DialogTransitionAnimator.Controller controller, MediaSession.Token token, int i) {
        if ((i & 16) != 0) {
            token = null;
        }
        mediaOutputDialogManager.createAndShow(str, z, controller, true, null, token);
    }

    public final void createAndShow(String str, boolean z, DialogTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle, MediaSession.Token token) {
        MediaSwitchingController create = this.mediaSwitchingControllerFactory.create(str, userHandle, token);
        Context context = this.context;
        UiEventLogger uiEventLogger = this.uiEventLogger;
        Executor executor = this.mainExecutor;
        Executor executor2 = executor != null ? executor : null;
        Executor executor3 = this.backgroundExecutor;
        MediaOutputDialog mediaOutputDialog = new MediaOutputDialog(context, z, this.broadcastSender, create, this.dialogTransitionAnimator, uiEventLogger, executor2, executor3 != null ? executor3 : null, z2);
        if (controller == null) {
            mediaOutputDialog.show();
        } else {
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            this.dialogTransitionAnimator.show(mediaOutputDialog, controller, false);
        }
    }
}
