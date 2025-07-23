package com.android.systemui.navigationbar.views;

import android.util.Log;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).awakenDreams();
                break;
            case 1:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).checkBarModes$1();
                break;
            case 2:
                Log.d("NavigationBar", "Use duration override: " + ((Long) obj));
                break;
            default:
                Log.d("NavigationBar", "Use slop multiplier override: " + ((Float) obj));
                break;
        }
    }
}
