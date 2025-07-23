package com.android.wm.shell.desktopmode;

import android.util.SparseArray;
import com.android.wm.shell.desktopmode.DesktopRepository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
