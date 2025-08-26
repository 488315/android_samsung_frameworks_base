package com.android.wm.shell.desktopmode.desktopwallpaperactivity;

import android.util.SparseArray;
import android.window.WindowContainerToken;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopWallpaperActivityTokenProvider {
    public final SparseArray wallpaperActivityTokenByDisplayId = new SparseArray();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public static void logV(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopWallpaperActivityTokenProvider", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final WindowContainerToken getToken(int i) {
        return (WindowContainerToken) this.wallpaperActivityTokenByDisplayId.get(i);
    }
}
