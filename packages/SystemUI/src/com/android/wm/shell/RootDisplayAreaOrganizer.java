package com.android.wm.shell;

import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import com.android.wm.shell.sysui.ShellInit;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RootDisplayAreaOrganizer extends DisplayAreaOrganizer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SparseArray mDisplayAreasInfo;
    public final SparseArray mLeashes;

    public RootDisplayAreaOrganizer(Executor executor, ShellInit shellInit) {
        super(executor);
        this.mDisplayAreasInfo = new SparseArray();
        this.mLeashes = new SparseArray();
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.RootDisplayAreaOrganizer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RootDisplayAreaOrganizer rootDisplayAreaOrganizer = RootDisplayAreaOrganizer.this;
                int i = RootDisplayAreaOrganizer.$r8$clinit;
                List registerOrganizer = rootDisplayAreaOrganizer.registerOrganizer(0);
                for (int size = registerOrganizer.size() - 1; size >= 0; size--) {
                    rootDisplayAreaOrganizer.onDisplayAreaAppeared(((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getDisplayAreaInfo(), ((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getLeash());
                }
            }
        }, this);
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        if (displayAreaInfo.featureId != 0) {
            throw new IllegalArgumentException("Unknown feature: " + displayAreaInfo.featureId + "displayAreaInfo:" + displayAreaInfo);
        }
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) == null) {
            surfaceControl.setUnreleasedWarningCallSite("RootDisplayAreaOrganizer.onDisplayAreaAppeared");
            this.mDisplayAreasInfo.put(i, displayAreaInfo);
            this.mLeashes.put(i, surfaceControl);
        } else {
            throw new IllegalArgumentException("Duplicate DA for displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
        }
    }

    public final void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) != null) {
            this.mDisplayAreasInfo.put(i, displayAreaInfo);
            return;
        }
        throw new IllegalArgumentException("onDisplayAreaInfoChanged() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
    }

    public final void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) != null) {
            this.mDisplayAreasInfo.remove(i);
            ((SurfaceControl) this.mLeashes.get(i)).release();
            this.mLeashes.remove(i);
        } else {
            throw new IllegalArgumentException("onDisplayAreaVanished() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
        }
    }

    public final String toString() {
        return "RootDisplayAreaOrganizer#" + this.mDisplayAreasInfo.size();
    }
}
