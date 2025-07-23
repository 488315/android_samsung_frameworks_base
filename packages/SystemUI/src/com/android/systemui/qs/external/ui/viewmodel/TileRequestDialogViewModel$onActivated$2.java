package com.android.systemui.qs.external.ui.viewmodel;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.systemui.qs.external.TileData;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileRequestDialogViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ TileRequestDialogViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileRequestDialogViewModel$onActivated$2(TileRequestDialogViewModel tileRequestDialogViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tileRequestDialogViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TileRequestDialogViewModel$onActivated$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileRequestDialogViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Drawable loadDrawableCheckingUriGrant;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TileRequestDialogViewModel tileRequestDialogViewModel = this.this$0;
        TileData tileData = tileRequestDialogViewModel.tileData;
        Icon icon = tileData.icon;
        if (icon == null || (loadDrawableCheckingUriGrant = icon.loadDrawableCheckingUriGrant(tileRequestDialogViewModel.dialogContext, tileRequestDialogViewModel.iUriGrantsManager, tileData.callingUid, tileData.packageName)) == null) {
            return null;
        }
        ((SnapshotMutableStateImpl) this.this$0._icon$delegate).setValue(new QSTileImpl.DrawableIcon(loadDrawableCheckingUriGrant));
        return Unit.INSTANCE;
    }
}
