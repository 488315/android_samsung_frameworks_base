package com.android.systemui.communal.widgets;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import androidx.activity.result.ActivityResultRegistry;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class EditWidgetsActivity$onOpenWidgetPicker$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ EditWidgetsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditWidgetsActivity$onOpenWidgetPicker$1(EditWidgetsActivity editWidgetsActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = editWidgetsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditWidgetsActivity$onOpenWidgetPicker$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditWidgetsActivity$onOpenWidgetPicker$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            EditWidgetsActivity editWidgetsActivity = this.this$0;
            CommunalEditModeViewModel communalEditModeViewModel = editWidgetsActivity.communalViewModel;
            Resources resources = editWidgetsActivity.getResources();
            PackageManager packageManager = this.this$0.getPackageManager();
            ActivityResultRegistry.AnonymousClass2 anonymousClass2 = this.this$0.addWidgetActivityLauncher;
            this.label = 1;
            if (communalEditModeViewModel.onOpenWidgetPicker(resources, packageManager, anonymousClass2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
