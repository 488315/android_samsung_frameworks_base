package com.android.systemui.dreams;

import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStatusBarItemsProvider f$0;
    public final /* synthetic */ DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4 f$1;

    public /* synthetic */ DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider, DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4 dreamOverlayStatusBarViewController$$ExternalSyntheticLambda4, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStatusBarItemsProvider;
        this.f$1 = dreamOverlayStatusBarViewController$$ExternalSyntheticLambda4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider = this.f$0;
                DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4 dreamOverlayStatusBarViewController$$ExternalSyntheticLambda4 = this.f$1;
                dreamOverlayStatusBarItemsProvider.getClass();
                Objects.requireNonNull(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda4, "Callback must not be null.");
                ((ArrayList) dreamOverlayStatusBarItemsProvider.mCallbacks).remove(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda4);
                break;
            default:
                DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider2 = this.f$0;
                DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4 dreamOverlayStatusBarViewController$$ExternalSyntheticLambda42 = this.f$1;
                dreamOverlayStatusBarItemsProvider2.getClass();
                Objects.requireNonNull(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda42, "Callback must not be null.");
                ArrayList arrayList = (ArrayList) dreamOverlayStatusBarItemsProvider2.mCallbacks;
                if (!arrayList.contains(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda42)) {
                    arrayList.add(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda42);
                    final List list = dreamOverlayStatusBarItemsProvider2.mItems;
                    if (!((ArrayList) list).isEmpty()) {
                        final DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = dreamOverlayStatusBarViewController$$ExternalSyntheticLambda42.f$0;
                        dreamOverlayStatusBarViewController.getClass();
                        dreamOverlayStatusBarViewController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController2 = DreamOverlayStatusBarViewController.this;
                                List list2 = list;
                                ArrayList arrayList2 = (ArrayList) dreamOverlayStatusBarViewController2.mExtraStatusBarItems;
                                arrayList2.clear();
                                arrayList2.addAll(list2);
                                final DreamOverlayStatusBarView dreamOverlayStatusBarView = (DreamOverlayStatusBarView) dreamOverlayStatusBarViewController2.mView;
                                List list3 = (List) list2.stream().map(new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda9()).collect(Collectors.toList());
                                dreamOverlayStatusBarView.mExtraSystemStatusViewGroup.removeAllViews();
                                list3.forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarView$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        DreamOverlayStatusBarView.this.mExtraSystemStatusViewGroup.addView((View) obj);
                                    }
                                });
                            }
                        });
                        break;
                    }
                }
                break;
        }
    }
}
