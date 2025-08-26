package com.android.systemui.communal.widgets;

import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class EditWidgetsActivity$onCreate$1$1$1$2$1 extends FunctionReferenceImpl implements Function0 {
    public EditWidgetsActivity$onCreate$1$1$1$2$1(Object obj) {
        super(0, obj, EditWidgetsActivity.class, "onEditDone", "onEditDone()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final /* bridge */ /* synthetic */ Object invoke() {
        m1093invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m1093invoke() {
        EditWidgetsActivity editWidgetsActivity = (EditWidgetsActivity) this.receiver;
        int i = EditWidgetsActivity.$r8$clinit;
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(editWidgetsActivity.lifecycleRegistry), null, null, new EditWidgetsActivity$onEditDone$1(editWidgetsActivity, null), 7);
    }
}
