package com.android.systemui.dreams;

import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStatusBarItemsProvider f$0;
    public final /* synthetic */ AmbientStatusBarViewController$$ExternalSyntheticLambda3 f$1;

    public /* synthetic */ DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider, AmbientStatusBarViewController$$ExternalSyntheticLambda3 ambientStatusBarViewController$$ExternalSyntheticLambda3, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStatusBarItemsProvider;
        this.f$1 = ambientStatusBarViewController$$ExternalSyntheticLambda3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider = this.f$0;
                AmbientStatusBarViewController$$ExternalSyntheticLambda3 ambientStatusBarViewController$$ExternalSyntheticLambda3 = this.f$1;
                dreamOverlayStatusBarItemsProvider.getClass();
                Objects.requireNonNull(ambientStatusBarViewController$$ExternalSyntheticLambda3, "Callback must not be null.");
                if (!((ArrayList) dreamOverlayStatusBarItemsProvider.mCallbacks).contains(ambientStatusBarViewController$$ExternalSyntheticLambda3)) {
                    ((ArrayList) dreamOverlayStatusBarItemsProvider.mCallbacks).add(ambientStatusBarViewController$$ExternalSyntheticLambda3);
                    if (!((ArrayList) dreamOverlayStatusBarItemsProvider.mItems).isEmpty()) {
                        final List list = dreamOverlayStatusBarItemsProvider.mItems;
                        final AmbientStatusBarViewController ambientStatusBarViewController = ambientStatusBarViewController$$ExternalSyntheticLambda3.f$0;
                        ambientStatusBarViewController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda12
                            @Override // java.lang.Runnable
                            public final void run() {
                                AmbientStatusBarViewController.m1011$r8$lambda$58bl0PfQY4gnoAw786XPvbTz9k(ambientStatusBarViewController, list);
                            }
                        });
                        break;
                    }
                }
                break;
            default:
                DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider2 = this.f$0;
                AmbientStatusBarViewController$$ExternalSyntheticLambda3 ambientStatusBarViewController$$ExternalSyntheticLambda32 = this.f$1;
                dreamOverlayStatusBarItemsProvider2.getClass();
                Objects.requireNonNull(ambientStatusBarViewController$$ExternalSyntheticLambda32, "Callback must not be null.");
                ((ArrayList) dreamOverlayStatusBarItemsProvider2.mCallbacks).remove(ambientStatusBarViewController$$ExternalSyntheticLambda32);
                break;
        }
    }
}
