package com.android.server.wm;

import java.io.PrintWriter;

public final class MultiTaskingAppCompatSizeCompatModePolicy
        implements MultiTaskingAppCompatController.OverridesObserver {
    public boolean mAvoidAppCompatDisplayInsets;

    /* renamed from: com.android.server.wm.MultiTaskingAppCompatSizeCompatModePolicy$1, reason: invalid class name */
    public final class AnonymousClass1
            implements MultiTaskingAppCompatController.OverridesObserver {
        @Override // com.android.server.wm.MultiTaskingAppCompatController.OverridesObserver
        public final void onDumpInTask(Task task, PrintWriter printWriter, String str) {}
    }
}
