package com.android.systemui.communal.widgets;

import android.content.Intent;
import android.content.res.Resources;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.communal.widgets.EditWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            EditWidgetsActivity editWidgetsActivity = this.this$0;
            CommunalEditModeViewModel communalEditModeViewModel = editWidgetsActivity.communalViewModel;
            Resources resources = editWidgetsActivity.getResources();
            final EditWidgetsActivity editWidgetsActivity2 = this.this$0;
            ?? r4 = new Function1() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    EditWidgetsActivity editWidgetsActivity3 = EditWidgetsActivity.this;
                    editWidgetsActivity3.startActivityForResultAsUser((Intent) obj2, 200, ((UserTrackerImpl) editWidgetsActivity3.userTracker).getUserHandle());
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (communalEditModeViewModel.onOpenWidgetPicker(resources, r4, this) == coroutineSingletons) {
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
