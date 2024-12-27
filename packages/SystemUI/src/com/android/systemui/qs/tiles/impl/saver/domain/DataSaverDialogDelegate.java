package com.android.systemui.qs.tiles.impl.saver.domain;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DataSaverDialogDelegate implements SystemUIDialog.Delegate {
    public final CoroutineContext backgroundContext;
    public final Context context;
    public final DataSaverController dataSaverController;
    public final SharedPreferences sharedPreferences;
    public final SystemUIDialog.Factory sysuiDialogFactory;

    public DataSaverDialogDelegate(SystemUIDialog.Factory factory, Context context, CoroutineContext coroutineContext, DataSaverController dataSaverController, SharedPreferences sharedPreferences) {
        this.sysuiDialogFactory = factory;
        this.context = context;
        this.backgroundContext = coroutineContext;
        this.dataSaverController = dataSaverController;
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setTitle(R.string.fingerprint_error_hw_not_present);
        systemUIDialog.setMessage(R.string.fingerprint_error_canceled);
        systemUIDialog.setPositiveButton(R.string.fingerprint_error_hw_not_available, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.impl.saver.domain.DataSaverDialogDelegate$beforeCreate$1$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.tiles.impl.saver.domain.DataSaverDialogDelegate$beforeCreate$1$1$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ DataSaverDialogDelegate this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(DataSaverDialogDelegate dataSaverDialogDelegate, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = dataSaverDialogDelegate;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    ((DataSaverControllerImpl) this.this$0.dataSaverController).setDataSaverEnabled(true);
                    return Unit.INSTANCE;
                }
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(DataSaverDialogDelegate.this.backgroundContext), null, null, new AnonymousClass1(DataSaverDialogDelegate.this, null), 3);
                DataSaverDialogDelegate.this.sharedPreferences.edit().putBoolean("data_saver_dialog_shown", true).apply();
            }
        });
        systemUIDialog.setNeutralButton(R.string.cancel, null, true);
        SystemUIDialog.setShowForAllUsers(systemUIDialog);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        return this.sysuiDialogFactory.create(this, this.context);
    }
}
