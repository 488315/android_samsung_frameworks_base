package com.android.systemui.navigationbar;

import android.util.Log;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda7(int i) {
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
