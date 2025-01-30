package com.android.systemui.navigationbar.gestural;

import android.graphics.Region;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.pip.Pip;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda0(EdgeBackGestureHandler edgeBackGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = edgeBackGestureHandler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
                edgeBackGestureHandler.getClass();
                edgeBackGestureHandler.mIsInPip = ((Boolean) obj).booleanValue();
                break;
            case 1:
                this.f$0.mDesktopModeExcludeRegion.set((Region) obj);
                break;
            case 2:
                ((Pip) obj).setOnIsInPipStateChangedListener(this.f$0.mOnIsInPipStateChangedListener);
                break;
            default:
                EdgeBackGestureHandler edgeBackGestureHandler2 = this.f$0;
                ((DesktopMode) obj).addDesktopGestureExclusionRegionListener(edgeBackGestureHandler2.mMainExecutor, edgeBackGestureHandler2.mDesktopCornersChangedListener);
                break;
        }
    }
}
