package com.android.wm.shell.pip;

import android.view.IPinnedTaskListener;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PinnedStackListenerForwarder {
    public final PinnedTaskListenerImpl mListenerImpl = new PinnedTaskListenerImpl(this, 0);
    public final ArrayList mListeners = new ArrayList();
    public final ShellExecutor mMainExecutor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PinnedTaskListenerImpl extends IPinnedTaskListener.Stub {
        public /* synthetic */ PinnedTaskListenerImpl(PinnedStackListenerForwarder pinnedStackListenerForwarder, int i) {
            this();
        }

        public final void onImeVisibilityChanged(final boolean z, final int i) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = PinnedStackListenerForwarder.PinnedTaskListenerImpl.this;
                    boolean z2 = z;
                    int i2 = i;
                    ArrayList arrayList = PinnedStackListenerForwarder.this.mListeners;
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        ((PinnedStackListenerForwarder.PinnedTaskListener) obj).onImeVisibilityChanged(z2, i2);
                    }
                }
            });
        }

        public final void onMovementBoundsChanged(final boolean z) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = PinnedStackListenerForwarder.PinnedTaskListenerImpl.this;
                    boolean z2 = z;
                    ArrayList arrayList = PinnedStackListenerForwarder.this.mListeners;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((PinnedStackListenerForwarder.PinnedTaskListener) obj).onMovementBoundsChanged(z2);
                    }
                }
            });
        }

        private PinnedTaskListenerImpl() {
        }
    }

    public PinnedStackListenerForwarder(ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PinnedTaskListener {
        public void onMovementBoundsChanged(boolean z) {
        }

        public void onImeVisibilityChanged(boolean z, int i) {
        }
    }
}
