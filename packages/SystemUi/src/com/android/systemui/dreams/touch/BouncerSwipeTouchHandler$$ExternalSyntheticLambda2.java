package com.android.systemui.dreams.touch;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class BouncerSwipeTouchHandler$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BouncerSwipeTouchHandler$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
        }
        return Boolean.valueOf(((CentralSurfacesImpl) ((CentralSurfaces) obj)).mBouncerShowing);
    }
}
