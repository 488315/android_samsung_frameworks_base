package com.android.wm.shell.desktopmode;

import android.app.Activity;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.os.Bundle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DesktopWallpaperActivity extends Activity {
    public static final Companion Companion = new Companion(null);
    public static final ComponentName wallpaperActivityComponent = new ComponentName("com.android.systemui", DesktopWallpaperActivity.class.getName());

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isWallpaperTask(TaskInfo taskInfo) {
            ComponentName component = taskInfo.baseIntent.getComponent();
            if (component != null) {
                return component.equals(DesktopWallpaperActivity.wallpaperActivityComponent);
            }
            return false;
        }

        private Companion() {
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(8);
    }
}
