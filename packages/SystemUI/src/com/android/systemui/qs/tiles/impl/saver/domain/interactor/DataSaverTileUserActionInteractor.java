package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.saver.domain.model.DataSaverTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DataSaverTileUserActionInteractor implements QSTileUserActionInteractor {
    public final CoroutineContext backgroundContext;
    public final Context context;
    public final CoroutineContext coroutineContext;
    public final DataSaverController dataSaverController;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;
    public final SharedPreferences sharedPreferences;
    public final SystemUIDialog.Factory systemUIDialogFactory;

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

    public DataSaverTileUserActionInteractor(Context context, CoroutineContext coroutineContext, CoroutineContext coroutineContext2, DataSaverController dataSaverController, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, DialogTransitionAnimator dialogTransitionAnimator, SystemUIDialog.Factory factory, UserFileManager userFileManager) {
        this.context = context;
        this.coroutineContext = coroutineContext;
        this.backgroundContext = coroutineContext2;
        this.dataSaverController = dataSaverController;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.systemUIDialogFactory = factory;
        this.sharedPreferences = ((UserFileManagerImpl) userFileManager).getSharedPreferences$1(context.getUserId(), "data_saver");
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
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
        }
        return Unit.INSTANCE;
    }
}
