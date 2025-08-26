package com.android.wm.shell.dagger;

import com.android.wm.shell.appzoomout.AppZoomOutController;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((RecentTasksController) obj).mImpl;
            case 1:
                return ((BubbleController) obj).asBubbles();
            case 2:
                return ((AppZoomOutController) obj).mImpl;
            case 3:
                return ((SplitScreenController) obj).mImpl;
            case 4:
                return ((BackAnimationController) obj).mBackAnimation;
            case 5:
                return ((OneHandedController) obj).mImpl;
            default:
                return ((DesktopTasksController) obj).desktopMode;
        }
    }
}
