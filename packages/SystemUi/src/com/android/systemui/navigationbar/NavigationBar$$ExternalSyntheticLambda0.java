package com.android.systemui.navigationbar;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).awakenDreams();
                break;
            case 1:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).awakenDreams();
                break;
            case 2:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).checkBarModes();
                break;
            default:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).awakenDreams();
                break;
        }
    }
}
