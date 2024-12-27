package com.android.systemui.qs.tiles.dialog;

import android.util.Log;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.tiles.dialog.InternetDialogDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.sec.ims.settings.ImsProfile;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.internal.ContextScope;

public final class InternetDialogManager {
    public static SystemUIDialog dialog;
    public final CoroutineDispatcher bgDispatcher;
    public ContextScope coroutineScope;
    public final InternetDialogDelegate.Factory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;

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

    public InternetDialogManager(DialogTransitionAnimator dialogTransitionAnimator, InternetDialogDelegate.Factory factory, CoroutineDispatcher coroutineDispatcher) {
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.dialogFactory = factory;
        this.bgDispatcher = coroutineDispatcher;
    }

    public final void create(boolean z, boolean z2, Expandable expandable) {
        if (dialog != null) {
            if (InternetDialogManagerKt.DEBUG) {
                Log.d("InternetDialogFactory", "InternetDialog is showing, do not create it twice.");
                return;
            }
            return;
        }
        ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(this.bgDispatcher);
        this.coroutineScope = CoroutineScope;
        dialog = this.dialogFactory.create(true, z, z2, CoroutineScope).createDialog();
        DialogTransitionAnimator.Controller dialogTransitionController = expandable != null ? expandable.dialogTransitionController(new DialogCuj(58, ImsProfile.PDN_INTERNET)) : null;
        if (dialogTransitionController != null) {
            SystemUIDialog systemUIDialog = dialog;
            Intrinsics.checkNotNull(systemUIDialog);
            this.dialogTransitionAnimator.show(systemUIDialog, dialogTransitionController, true);
        } else {
            SystemUIDialog systemUIDialog2 = dialog;
            if (systemUIDialog2 != null) {
                systemUIDialog2.show();
            }
        }
    }

    public final void destroyDialog() {
        if (InternetDialogManagerKt.DEBUG) {
            Log.d("InternetDialogFactory", "destroyDialog");
        }
        if (dialog != null) {
            ContextScope contextScope = this.coroutineScope;
            if (contextScope == null) {
                contextScope = null;
            }
            CoroutineScopeKt.cancel(contextScope, null);
        }
        dialog = null;
    }
}
