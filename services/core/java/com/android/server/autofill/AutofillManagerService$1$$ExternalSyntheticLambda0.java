package com.android.server.autofill;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractPerUserSystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AutofillManagerService$1$$ExternalSyntheticLambda0 {
    public final void visit(AbstractPerUserSystemService abstractPerUserSystemService) {
        AutofillManagerServiceImpl autofillManagerServiceImpl =
                (AutofillManagerServiceImpl) abstractPerUserSystemService;
        for (int size = autofillManagerServiceImpl.mSessions.size() - 1; size >= 0; size--) {
            Session session = (Session) autofillManagerServiceImpl.mSessions.valueAt(size);
            if (session.mSessionFlags.mShowingSaveUi) {
                if (Helper.sDebug) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            new StringBuilder("destroyFinishedSessionsLocked(): "),
                            session.id,
                            "AutofillManagerServiceImpl");
                }
                session.forceRemoveFromServiceLocked(0);
            } else {
                Runnable runnable = session.mAugmentedAutofillDestroyer;
                if (runnable != null) {
                    runnable.run();
                    session.mAugmentedAutofillDestroyer = null;
                }
            }
        }
    }
}
