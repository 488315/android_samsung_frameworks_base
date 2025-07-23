package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.saver.domain.model.DataSaverTileModel;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DataSaverTileUserActionInteractor implements QSTileUserActionInteractor {
    public final CoroutineContext backgroundContext;
    public final ShadeDialogContextInteractor contextInteractor;
    public final CoroutineContext coroutineContext;
    public final DataSaverController dataSaverController;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final SharedPreferences sharedPreferences;
    public final SystemUIDialog.Factory systemUIDialogFactory;

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

    public DataSaverTileUserActionInteractor(Context context, ShadeDialogContextInteractor shadeDialogContextInteractor, CoroutineContext coroutineContext, CoroutineContext coroutineContext2, DataSaverController dataSaverController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, DialogTransitionAnimator dialogTransitionAnimator, SystemUIDialog.Factory factory, UserFileManager userFileManager) {
        this.contextInteractor = shadeDialogContextInteractor;
        this.coroutineContext = coroutineContext;
        this.backgroundContext = coroutineContext2;
        this.dataSaverController = dataSaverController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.systemUIDialogFactory = factory;
        this.sharedPreferences = ((UserFileManagerImpl) userFileManager).getSharedPreferences$1(context.getUserId(), "data_saver");
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    public final Object handleInput(QSTileInput qSTileInput, Continuation continuation) {
        QSTileUserAction qSTileUserAction = qSTileInput.action;
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            boolean z = ((DataSaverTileModel) qSTileInput.data).isEnabled;
            if (z || this.sharedPreferences.getBoolean("data_saver_dialog_shown", false)) {
                Object withContext = BuildersKt.withContext(this.backgroundContext, new DataSaverTileUserActionInteractor$handleInput$2$1(this, z, null), continuation);
                if (withContext == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return withContext;
                }
            } else {
                Object withContext2 = BuildersKt.withContext(this.coroutineContext, new DataSaverTileUserActionInteractor$handleInput$2$2(this, qSTileInput, null), continuation);
                if (withContext2 == CoroutineSingletons.COROUTINE_SUSPENDED) {
                    return withContext2;
                }
            }
        } else if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            QSTileIntentUserInputHandler.handle$default(this.qsTileIntentUserActionHandler, ((QSTileUserAction.LongClick) qSTileUserAction).expandable, new Intent("android.settings.DATA_SAVER_SETTINGS"));
        } else if (!(qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
            throw new NoWhenBranchMatchedException();
        }
        return Unit.INSTANCE;
    }
}
