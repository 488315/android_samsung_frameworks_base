package com.android.systemui.communal.widgets;

import androidx.lifecycle.LifecycleKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.BuildersKt;

final /* synthetic */ class EditWidgetsActivity$onCreate$1$1$1$2 extends FunctionReferenceImpl implements Function0 {
    public EditWidgetsActivity$onCreate$1$1$1$2(Object obj) {
        super(0, obj, EditWidgetsActivity.class, "onEditDone", "onEditDone()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final /* bridge */ /* synthetic */ Object invoke() {
        m945invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m945invoke() {
        EditWidgetsActivity editWidgetsActivity = (EditWidgetsActivity) this.receiver;
        int i = EditWidgetsActivity.$r8$clinit;
        editWidgetsActivity.getClass();
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(editWidgetsActivity.getLifecycle()), null, null, new EditWidgetsActivity$onEditDone$1(editWidgetsActivity, null), 3);
    }
}
