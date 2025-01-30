package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedState;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedState {
    public static int sCurrentState;
    public final List mStateChangeListeners = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnStateChangedListener {
    }

    public OneHandedState() {
        sCurrentState = 0;
    }

    public final void setState(final int i) {
        sCurrentState = i;
        List list = this.mStateChangeListeners;
        if (((ArrayList) list).isEmpty()) {
            return;
        }
        ((ArrayList) list).forEach(new Consumer() { // from class: com.android.wm.shell.onehanded.OneHandedState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                OneHandedTutorialHandler oneHandedTutorialHandler = (OneHandedTutorialHandler) ((OneHandedState.OnStateChangedListener) obj);
                oneHandedTutorialHandler.mCurrentState = i2;
                oneHandedTutorialHandler.mBackgroundWindowManager.mCurrentState = i2;
                if (i2 != 0) {
                    if (i2 == 1) {
                        oneHandedTutorialHandler.createViewAndAttachToWindow(oneHandedTutorialHandler.mContext);
                        oneHandedTutorialHandler.updateThemeColor();
                        oneHandedTutorialHandler.setupAlphaTransition(true);
                        return;
                    } else if (i2 == 2) {
                        oneHandedTutorialHandler.checkTransitionEnd();
                        oneHandedTutorialHandler.setupAlphaTransition(false);
                        return;
                    } else if (i2 != 3) {
                        return;
                    }
                }
                oneHandedTutorialHandler.checkTransitionEnd();
                oneHandedTutorialHandler.removeTutorialFromWindowManager();
            }
        });
    }
}
