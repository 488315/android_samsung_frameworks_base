package com.android.systemui.shade.domain.interactor;

import android.app.SemStatusBarManager;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.UiOffloadThread;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class SecPanelExpansionStateInteractor$repository$2$1 extends FunctionReferenceImpl implements Function1 {
    public SecPanelExpansionStateInteractor$repository$2$1(Object obj) {
        super(1, obj, SecPanelExpansionStateInteractor.class, "notify", "notify(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        final int intValue = ((Number) obj).intValue();
        final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) this.receiver;
        secPanelExpansionStateInteractor.getClass();
        SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent = new SecPanelExpansionStateChangeEvent(intValue);
        Iterator it = ((CopyOnWriteArrayList) secPanelExpansionStateInteractor.expansionStateListeners$delegate.getValue()).iterator();
        while (it.hasNext()) {
            ((SecPanelExpansionStateListener) it.next()).onPanelExpansionStateChanged(secPanelExpansionStateChangeEvent);
        }
        ((UiOffloadThread) secPanelExpansionStateInteractor.uiOffloadThread$delegate.getValue()).execute(new Runnable() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$notify$2
            @Override // java.lang.Runnable
            public final void run() {
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor2 = SecPanelExpansionStateInteractor.this;
                int i = intValue;
                int i2 = SecPanelExpansionStateInteractor.$r8$clinit;
                secPanelExpansionStateInteractor2.getClass();
                Intent intent = i != 0 ? i != 1 ? i != 2 ? null : new Intent("com.samsung.systemui.statusbar.EXPANDED") : new Intent("com.samsung.systemui.statusbar.ANIMATING") : new Intent("com.samsung.systemui.statusbar.COLLAPSED");
                if (intent != null) {
                    SecPanelExpansionStateInteractor.this.context.sendBroadcastAsUser(intent, UserHandle.ALL);
                }
                Integer valueOf = Integer.valueOf(intValue);
                Integer num = valueOf.intValue() != 1 ? valueOf : null;
                if (num != null) {
                    SecPanelExpansionStateInteractor secPanelExpansionStateInteractor3 = SecPanelExpansionStateInteractor.this;
                    int intValue2 = num.intValue();
                    SemStatusBarManager semStatusBarManager = (SemStatusBarManager) secPanelExpansionStateInteractor3.statusBarManager$delegate.getValue();
                    if (semStatusBarManager != null) {
                        semStatusBarManager.setPanelExpandState(intValue2 == 2);
                    }
                }
            }
        });
        return Unit.INSTANCE;
    }
}
