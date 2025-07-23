package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.data.model.StatusBarAppearance;
import com.android.systemui.statusbar.layout.BoundsPair;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class LightBarControllerImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LightBarControllerImpl$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) obj2;
                LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) obj;
                if (lightBarTransitionsController == null) {
                    int i2 = LightBarControllerImpl.$r8$clinit;
                    lightBarControllerImpl.getClass();
                    break;
                } else {
                    lightBarTransitionsController.setIconsDark(lightBarControllerImpl.mNavigationLight, lightBarControllerImpl.animateChange());
                    break;
                }
            case 1:
                LightBarControllerImpl lightBarControllerImpl2 = (LightBarControllerImpl) obj2;
                StatusBarAppearance statusBarAppearance = (StatusBarAppearance) obj;
                int i3 = LightBarControllerImpl.$r8$clinit;
                lightBarControllerImpl2.getClass();
                if (statusBarAppearance != null) {
                    int transitionModeInt = statusBarAppearance.mode.toTransitionModeInt();
                    boolean z = lightBarControllerImpl2.mStatusBarMode != transitionModeInt;
                    lightBarControllerImpl2.mStatusBarMode = transitionModeInt;
                    BoundsPair boundsPair = lightBarControllerImpl2.mStatusBarBounds;
                    BoundsPair boundsPair2 = statusBarAppearance.bounds;
                    boolean z2 = !boundsPair.equals(boundsPair2);
                    lightBarControllerImpl2.mStatusBarBounds = boundsPair2;
                    lightBarControllerImpl2.onStatusBarAppearanceChanged((AppearanceRegion[]) statusBarAppearance.appearanceRegions.toArray(new AppearanceRegion[0]), z, z2, statusBarAppearance.navbarColorManagedByIme);
                    break;
                } else {
                    Log.d("LightBarController", "SKIP onStatusBarAppearanceChanged - null params");
                    break;
                }
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
