package com.android.wm.shell.displayareahelper;

import com.android.wm.shell.RootDisplayAreaOrganizer;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class DisplayAreaHelperController {
    public final Executor mExecutor;

    public DisplayAreaHelperController(Executor executor, RootDisplayAreaOrganizer rootDisplayAreaOrganizer) {
        this.mExecutor = executor;
    }
}
