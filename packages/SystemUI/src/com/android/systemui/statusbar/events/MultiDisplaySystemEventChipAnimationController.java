package com.android.systemui.statusbar.events;

import android.view.Display;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.PerDisplayStoreImpl;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.data.repository.SystemEventChipAnimationControllerStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplaySystemEventChipAnimationController implements SystemEventChipAnimationController {
    public final SystemEventChipAnimationControllerStore controllerStore;
    public final DisplayRepository displayRepository;

    public MultiDisplaySystemEventChipAnimationController(DisplayRepository displayRepository, SystemEventChipAnimationControllerStore systemEventChipAnimationControllerStore) {
        this.displayRepository = displayRepository;
        this.controllerStore = systemEventChipAnimationControllerStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    public final List controllersForAllDisplays() {
        Iterable iterable = (Iterable) ((DisplayRepositoryImpl) this.displayRepository).displayRepositoryFromLib.getDisplays().getValue();
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            SystemEventChipAnimationController systemEventChipAnimationController = (SystemEventChipAnimationController) ((PerDisplayStoreImpl) this.controllerStore).forDisplay(((Display) it.next()).getDisplayId());
            if (systemEventChipAnimationController != null) {
                arrayList.add(systemEventChipAnimationController);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController, com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) {
        List controllersForAllDisplays = controllersForAllDisplays();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controllersForAllDisplays, 10));
        ArrayList arrayList2 = (ArrayList) controllersForAllDisplays;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((SystemEventChipAnimationController) obj).onSystemEventAnimationBegin(z, z2));
        }
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(arrayList);
        return springAnimatorSet;
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController, com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        List controllersForAllDisplays = controllersForAllDisplays();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controllersForAllDisplays, 10));
        ArrayList arrayList2 = (ArrayList) controllersForAllDisplays;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((SystemEventChipAnimationController) obj).onSystemEventAnimationFinish(z, z2, z3));
        }
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(arrayList);
        return springAnimatorSet;
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
    public final void prepareChipAnimation(Function1 function1, boolean z) {
        ArrayList arrayList = (ArrayList) controllersForAllDisplays();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SystemEventChipAnimationController) obj).prepareChipAnimation(function1, z);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
    public final void stop() {
        MultiDisplaySystemEventChipAnimationController$$ExternalSyntheticLambda0 multiDisplaySystemEventChipAnimationController$$ExternalSyntheticLambda0 = new MultiDisplaySystemEventChipAnimationController$$ExternalSyntheticLambda0();
        ArrayList arrayList = (ArrayList) controllersForAllDisplays();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            multiDisplaySystemEventChipAnimationController$$ExternalSyntheticLambda0.mo779invoke((SystemEventChipAnimationController) obj);
        }
    }
}
