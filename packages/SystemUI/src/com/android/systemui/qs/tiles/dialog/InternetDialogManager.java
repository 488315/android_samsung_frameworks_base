package com.android.systemui.qs.tiles.dialog;

import android.util.Log;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.qs.flags.QsDetailedView;
import com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.sec.ims.settings.ImsProfile;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InternetDialogManager {
    public static SystemUIDialog dialog;
    public final CoroutineDispatcher bgDispatcher;
    public ContextScope coroutineScope;
    public final InternetDialogDelegateLegacy.Factory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final ShadeModeInteractor shadeModeInteractor;

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

    public InternetDialogManager(DialogTransitionAnimator dialogTransitionAnimator, InternetDialogDelegateLegacy.Factory factory, CoroutineDispatcher coroutineDispatcher, ShadeModeInteractor shadeModeInteractor) {
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.dialogFactory = factory;
        this.bgDispatcher = coroutineDispatcher;
        this.shadeModeInteractor = shadeModeInteractor;
    }

    public final void create(boolean z, boolean z2, Expandable expandable) {
        if (this.shadeModeInteractor.isDualShade()) {
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = QsDetailedView.$r8$clinit;
        }
        if (dialog != null) {
            if (InternetDialogManagerKt.DEBUG) {
                Log.d("InternetDialogFactory", "InternetDialog is showing, do not create it twice.");
                return;
            }
            return;
        }
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        CoroutineDispatcher coroutineDispatcher = this.bgDispatcher;
        coroutineDispatcher.getClass();
        ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(coroutineDispatcher, emptyCoroutineContext));
        this.coroutineScope = CoroutineScope;
        dialog = this.dialogFactory.create(true, z, z2, CoroutineScope).createDialog();
        DialogTransitionAnimator.Controller dialogTransitionController = expandable != null ? expandable.dialogTransitionController(new DialogCuj(58, ImsProfile.PDN_INTERNET)) : null;
        if (dialogTransitionController != null) {
            SystemUIDialog systemUIDialog = dialog;
            systemUIDialog.getClass();
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
