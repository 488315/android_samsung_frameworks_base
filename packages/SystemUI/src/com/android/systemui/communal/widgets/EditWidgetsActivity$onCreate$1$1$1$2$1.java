package com.android.systemui.communal.widgets;

import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class EditWidgetsActivity$onCreate$1$1$1$2$1 extends FunctionReferenceImpl implements Function0 {
    public EditWidgetsActivity$onCreate$1$1$1$2$1(Object obj) {
        super(0, obj, EditWidgetsActivity.class, "onEditDone", "onEditDone()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final /* bridge */ /* synthetic */ Object invoke() {
        m1091invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m1091invoke() {
        EditWidgetsActivity editWidgetsActivity = (EditWidgetsActivity) this.receiver;
        int i = EditWidgetsActivity.$r8$clinit;
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(editWidgetsActivity.lifecycleRegistry), null, null, new EditWidgetsActivity$onEditDone$1(editWidgetsActivity, null), 7);
    }
}
