package com.android.wm.shell.onehanded;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OneHandedState {
    public static int sCurrentState;
    public final List mStateChangeListeners = new ArrayList();

    public OneHandedState() {
        sCurrentState = 0;
    }

    public final void setState(final int i) {
        sCurrentState = i;
        if (((ArrayList) this.mStateChangeListeners).isEmpty()) {
            return;
        }
        ((ArrayList) this.mStateChangeListeners).forEach(new Consumer() { // from class: com.android.wm.shell.onehanded.OneHandedState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                OneHandedTutorialHandler oneHandedTutorialHandler = (OneHandedTutorialHandler) obj;
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
