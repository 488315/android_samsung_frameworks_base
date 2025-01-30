package com.android.p038wm.shell.pip;

import android.content.ComponentName;
import android.view.IPinnedTaskListener;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.pip.PinnedStackListenerForwarder;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PinnedStackListenerForwarder {
    public final PinnedTaskListenerImpl mListenerImpl = new PinnedTaskListenerImpl(this, 0);
    public final ArrayList mListeners = new ArrayList();
    public final ShellExecutor mMainExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PinnedTaskListenerImpl extends IPinnedTaskListener.Stub {
        public /* synthetic */ PinnedTaskListenerImpl(PinnedStackListenerForwarder pinnedStackListenerForwarder, int i) {
            this();
        }

        public final void onActivityHidden(final ComponentName componentName) {
            ((HandlerExecutor) PinnedStackListenerForwarder.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = PinnedStackListenerForwarder.PinnedTaskListenerImpl.this;
                    ComponentName componentName2 = componentName;
                    Iterator it = PinnedStackListenerForwarder.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((PinnedStackListenerForwarder.PinnedTaskListener) it.next()).onActivityHidden(componentName2);
                    }
                }
            });
        }

        public final void onImeVisibilityChanged(final boolean z, final int i) {
            ((HandlerExecutor) PinnedStackListenerForwarder.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = PinnedStackListenerForwarder.PinnedTaskListenerImpl.this;
                    boolean z2 = z;
                    int i2 = i;
                    Iterator it = PinnedStackListenerForwarder.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((PinnedStackListenerForwarder.PinnedTaskListener) it.next()).onImeVisibilityChanged(z2, i2);
                    }
                }
            });
        }

        public final void onMovementBoundsChanged(final boolean z) {
            ((HandlerExecutor) PinnedStackListenerForwarder.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = PinnedStackListenerForwarder.PinnedTaskListenerImpl.this;
                    boolean z2 = z;
                    Iterator it = PinnedStackListenerForwarder.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((PinnedStackListenerForwarder.PinnedTaskListener) it.next()).onMovementBoundsChanged(z2);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PinnedTaskListener {
        public void onActivityHidden(ComponentName componentName) {
        }

        public void onMovementBoundsChanged(boolean z) {
        }

        public void onImeVisibilityChanged(boolean z, int i) {
        }
    }
}
