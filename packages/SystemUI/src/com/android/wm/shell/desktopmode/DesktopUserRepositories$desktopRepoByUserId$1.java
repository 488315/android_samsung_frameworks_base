package com.android.wm.shell.desktopmode;

import android.util.SparseArray;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopUserRepositories$desktopRepoByUserId$1 extends SparseArray {
    public final /* synthetic */ DesktopConfig $desktopConfig;
    public final /* synthetic */ DesktopUserRepositories this$0;

    public DesktopUserRepositories$desktopRepoByUserId$1(DesktopUserRepositories desktopUserRepositories, DesktopConfig desktopConfig) {
        this.this$0 = desktopUserRepositories;
        this.$desktopConfig = desktopConfig;
    }

    public final DesktopRepository getOrCreate(int i) {
        DesktopRepository desktopRepository = (DesktopRepository) get(i);
        if (desktopRepository != null) {
            return desktopRepository;
        }
        DesktopUserRepositories desktopUserRepositories = this.this$0;
        DesktopRepository desktopRepository2 = new DesktopRepository(desktopUserRepositories.persistentRepository, desktopUserRepositories.mainCoroutineScope, i, this.$desktopConfig);
        set(i, desktopRepository2);
        return desktopRepository2;
    }
}
