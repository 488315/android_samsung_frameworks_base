package com.android.systemui.shade.domain.interactor;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class SecQSExpansionStateInteractor$repository$2$1 extends FunctionReferenceImpl implements Function1 {
    public SecQSExpansionStateInteractor$repository$2$1(Object obj) {
        super(1, obj, SecQSExpansionStateInteractor.class, "notify", "notify(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) this.receiver;
        secQSExpansionStateInteractor.getClass();
        SecQSExpansionStateChangeEvent secQSExpansionStateChangeEvent = new SecQSExpansionStateChangeEvent(booleanValue);
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("notify: "), secQSExpansionStateChangeEvent.expanded, "SecQSExpansionStateInteractor");
        Iterator it = ((List) secQSExpansionStateInteractor.expansionStateListeners$delegate.getValue()).iterator();
        while (it.hasNext()) {
            ((SecQSExpansionStateListener) it.next()).onQSExpansionStateChanged(secQSExpansionStateChangeEvent);
        }
        return Unit.INSTANCE;
    }
}
