package com.android.systemui.statusbar.phone;

import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.data.model.StatusBarAppearance;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class LightBarController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LightBarController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                LightBarController lightBarController = (LightBarController) obj2;
                LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) obj;
                if (lightBarTransitionsController == null) {
                    lightBarController.getClass();
                    break;
                } else {
                    lightBarTransitionsController.setIconsDark(lightBarController.mNavigationLight, lightBarController.animateChange());
                    break;
                }
            case 1:
                LightBarController lightBarController2 = (LightBarController) obj2;
                StatusBarAppearance statusBarAppearance = (StatusBarAppearance) obj;
                lightBarController2.getClass();
                if (statusBarAppearance != null) {
                    int transitionModeInt = statusBarAppearance.mode.toTransitionModeInt();
                    boolean z = lightBarController2.mStatusBarMode != transitionModeInt;
                    lightBarController2.mStatusBarMode = transitionModeInt;
                    BoundsPair boundsPair = lightBarController2.mStatusBarBounds;
                    BoundsPair boundsPair2 = statusBarAppearance.bounds;
                    boolean z2 = !boundsPair.equals(boundsPair2);
                    lightBarController2.mStatusBarBounds = boundsPair2;
                    lightBarController2.onStatusBarAppearanceChanged((AppearanceRegion[]) statusBarAppearance.appearanceRegions.toArray(new AppearanceRegion[0]), z, z2, statusBarAppearance.navbarColorManagedByIme);
                    break;
                }
                break;
            default:
                Consumer consumer = (Consumer) obj2;
                LightBarTransitionsController lightBarTransitionsController2 = (LightBarTransitionsController) obj;
                if (lightBarTransitionsController2 != null) {
                    consumer.accept(lightBarTransitionsController2);
                    break;
                }
                break;
        }
    }
}
