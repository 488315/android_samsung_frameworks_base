package com.android.wm.shell.recents;

import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ RecentTasksController$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        DesktopUserRepositories desktopUserRepositories = (DesktopUserRepositories) obj;
        switch (i) {
            case 0:
                desktopUserRepositories.getCurrent().removeTask(-1, i2);
                break;
            default:
                desktopUserRepositories.getCurrent().removeTask(-1, i2);
                break;
        }
    }
}
