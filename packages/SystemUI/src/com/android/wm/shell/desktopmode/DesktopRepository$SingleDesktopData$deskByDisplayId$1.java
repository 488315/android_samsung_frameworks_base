package com.android.wm.shell.desktopmode;

import android.util.SparseArray;
import com.android.wm.shell.desktopmode.DesktopRepository;

/* loaded from: classes3.dex */
public final class DesktopRepository$SingleDesktopData$deskByDisplayId$1 extends SparseArray {
    public final DesktopRepository.Desk getOrCreate(int i) {
        DesktopRepository.Desk desk = (DesktopRepository.Desk) get(i);
        if (desk != null) {
            return desk;
        }
        DesktopRepository.Desk desk2 = new DesktopRepository.Desk(i, i, null, null, null, null, null, null, null, null, null, 0, 0, 8188, null);
        set(i, desk2);
        return desk2;
    }
}
