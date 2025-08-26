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

/* loaded from: classes3.dex */
final /* synthetic */ class SecPanelExpansionStateInteractor$repository$2$1 extends FunctionReferenceImpl implements Function1 {
    public SecPanelExpansionStateInteractor$repository$2$1(Object obj) {
        super(1, obj, SecPanelExpansionStateInteractor.class, "notify", "notify(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        final int iIntValue = ((Number) obj).intValue();
        final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) this.receiver;
        secPanelExpansionStateInteractor.getClass();
        SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent = new SecPanelExpansionStateChangeEvent(iIntValue);
        Iterator it = ((CopyOnWriteArrayList) secPanelExpansionStateInteractor.expansionStateListeners$delegate.getValue()).iterator();
        while (it.hasNext()) {
            ((SecPanelExpansionStateListener) it.next()).onPanelExpansionStateChanged(secPanelExpansionStateChangeEvent);
        }
        ((UiOffloadThread) secPanelExpansionStateInteractor.uiOffloadThread$delegate.getValue()).execute(new Runnable() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$notify$2
            @Override // java.lang.Runnable
            public final void run() {
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor2 = secPanelExpansionStateInteractor;
                int i = iIntValue;
                int i2 = SecPanelExpansionStateInteractor.$r8$clinit;
                secPanelExpansionStateInteractor2.getClass();
                Intent intent = i != 0 ? i != 1 ? i != 2 ? null : new Intent("com.samsung.systemui.statusbar.EXPANDED") : new Intent("com.samsung.systemui.statusbar.ANIMATING") : new Intent("com.samsung.systemui.statusbar.COLLAPSED");
                if (intent != null) {
                    secPanelExpansionStateInteractor.context.sendBroadcastAsUser(intent, UserHandle.ALL);
                }
                Integer numValueOf = Integer.valueOf(iIntValue);
                Integer num = numValueOf.intValue() != 1 ? numValueOf : null;
                if (num != null) {
                    SecPanelExpansionStateInteractor secPanelExpansionStateInteractor3 = secPanelExpansionStateInteractor;
                    int iIntValue2 = num.intValue();
                    SemStatusBarManager semStatusBarManager = (SemStatusBarManager) secPanelExpansionStateInteractor3.statusBarManager$delegate.getValue();
                    if (semStatusBarManager != null) {
                        semStatusBarManager.setPanelExpandState(iIntValue2 == 2);
                    }
                }
            }
        });
        return Unit.INSTANCE;
    }
}
