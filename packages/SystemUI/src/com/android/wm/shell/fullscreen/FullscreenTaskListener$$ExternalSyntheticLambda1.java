package com.android.wm.shell.fullscreen;

import android.app.ActivityManager;
import android.window.WindowContainerToken;
import androidx.core.util.SparseArrayKt$keyIterator$1;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.desktopmode.desktopwallpaperactivity.DesktopWallpaperActivityTokenProvider;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;

/* loaded from: classes3.dex */
public final /* synthetic */ class FullscreenTaskListener$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FullscreenTaskListener$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Object next;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((WindowDecorViewModel) obj).onTaskVanished((ActivityManager.RunningTaskInfo) obj2);
                break;
            case 1:
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj2;
                DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = (DesktopWallpaperActivityTokenProvider) obj;
                DesktopWallpaperActivity.Companion.getClass();
                if (DesktopWallpaperActivity.Companion.isWallpaperTask(runningTaskInfo)) {
                    WindowContainerToken token = runningTaskInfo.getToken();
                    Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$keyIterator$1(desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId)).iterator();
                    while (true) {
                        if (it.hasNext()) {
                            next = it.next();
                            if (Intrinsics.areEqual(desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId.get(((Number) next).intValue()), token)) {
                            }
                        } else {
                            next = null;
                        }
                    }
                    Integer num = (Integer) next;
                    if (num != null) {
                        DesktopWallpaperActivityTokenProvider.logV("Remove desktop wallpaper activity token for display %s", num);
                        desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId.delete(num.intValue());
                        break;
                    }
                }
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj2;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                if (runningTaskInfo2.isVisible) {
                    recentTasksController.removeSplitPair(runningTaskInfo2.taskId);
                    break;
                }
                break;
            default:
                FullscreenTaskListener fullscreenTaskListener = (FullscreenTaskListener) obj2;
                fullscreenTaskListener.getClass();
                ((SplitScreenController) obj).mFullscreenTaskListener = fullscreenTaskListener;
                break;
        }
    }
}
