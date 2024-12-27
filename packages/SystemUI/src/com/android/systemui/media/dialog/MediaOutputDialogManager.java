package com.android.systemui.media.dialog;

import android.content.Context;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.animation.ViewDialogTransitionAnimatorController;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaOutputDialogManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final MediaOutputController.Factory mediaOutputControllerFactory;
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public MediaOutputDialogManager(Context context, BroadcastSender broadcastSender, UiEventLogger uiEventLogger, DialogTransitionAnimator dialogTransitionAnimator, MediaOutputController.Factory factory) {
        this.context = context;
        this.broadcastSender = broadcastSender;
        this.uiEventLogger = uiEventLogger;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.mediaOutputControllerFactory = factory;
    }

    public static void createAndShowWithController$default(MediaOutputDialogManager mediaOutputDialogManager, String str, boolean z, DialogTransitionAnimator.Controller controller, MediaSession.Token token, int i) {
        if ((i & 16) != 0) {
            token = null;
        }
        mediaOutputDialogManager.createAndShow(str, z, controller, true, null, token);
    }

    public final void createAndShow(String str, boolean z, View view, UserHandle userHandle, MediaSession.Token token) {
        ViewDialogTransitionAnimatorController viewDialogTransitionAnimatorController;
        if (view != null) {
            DialogTransitionAnimator.Controller.Companion companion = DialogTransitionAnimator.Controller.Companion;
            DialogCuj dialogCuj = new DialogCuj(58, "media_output");
            companion.getClass();
            viewDialogTransitionAnimatorController = DialogTransitionAnimator.Controller.Companion.fromView(view, dialogCuj);
        } else {
            viewDialogTransitionAnimatorController = null;
        }
        createAndShow(str, z, viewDialogTransitionAnimatorController, true, userHandle, token);
    }

    public final void createAndShow(String str, boolean z, DialogTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle, MediaSession.Token token) {
        MediaOutputController create = this.mediaOutputControllerFactory.create(str, userHandle, token);
        MediaOutputDialog mediaOutputDialog = new MediaOutputDialog(this.context, z, this.broadcastSender, create, this.dialogTransitionAnimator, this.uiEventLogger, z2);
        if (controller != null) {
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            this.dialogTransitionAnimator.show(mediaOutputDialog, controller, false);
        } else {
            mediaOutputDialog.show();
        }
    }
}
