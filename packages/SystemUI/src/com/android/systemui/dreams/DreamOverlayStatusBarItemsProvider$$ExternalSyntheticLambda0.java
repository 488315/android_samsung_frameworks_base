package com.android.systemui.dreams;

import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        ambientStatusBarViewController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                AmbientStatusBarViewController.$r8$lambda$K0wXz652B8Jyh9bMy8eqfTnVhUo(AmbientStatusBarViewController.this, list);
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
