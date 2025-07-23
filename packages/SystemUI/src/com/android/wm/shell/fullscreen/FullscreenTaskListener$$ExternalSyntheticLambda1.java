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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Object obj2;
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                ((WindowDecorViewModel) obj).onTaskVanished((ActivityManager.RunningTaskInfo) obj3);
                break;
            case 1:
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj3;
                DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = (DesktopWallpaperActivityTokenProvider) obj;
                DesktopWallpaperActivity.Companion.getClass();
                if (DesktopWallpaperActivity.Companion.isWallpaperTask(runningTaskInfo)) {
                    WindowContainerToken token = runningTaskInfo.getToken();
                    Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$keyIterator$1(desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId)).iterator();
                    while (true) {
                        if (it.hasNext()) {
                            obj2 = it.next();
                            if (Intrinsics.areEqual(desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId.get(((Number) obj2).intValue()), token)) {
                            }
                        } else {
                            obj2 = null;
                        }
                    }
                    Integer num = (Integer) obj2;
                    if (num != null) {
                        DesktopWallpaperActivityTokenProvider.logV("Remove desktop wallpaper activity token for display %s", num);
                        desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId.delete(num.intValue());
                        break;
                    }
                }
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj3;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                if (runningTaskInfo2.isVisible) {
                    recentTasksController.removeSplitPair(runningTaskInfo2.taskId);
                    break;
                }
                break;
            default:
                FullscreenTaskListener fullscreenTaskListener = (FullscreenTaskListener) obj3;
                fullscreenTaskListener.getClass();
                ((SplitScreenController) obj).mFullscreenTaskListener = fullscreenTaskListener;
                break;
        }
    }
}
