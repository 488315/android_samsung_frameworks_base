package com.android.server.autofill;

import android.util.Slog;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Session$$ExternalSyntheticLambda9 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AutofillManagerServiceImpl autofillManagerServiceImpl = (AutofillManagerServiceImpl) obj;
        Session session = (Session) obj2;
        synchronized (autofillManagerServiceImpl.mLock) {
            try {
                if (autofillManagerServiceImpl.mSessions.get(session.id) != null) {
                    session.callSaveLocked();
                    return;
                }
                Slog.w(
                        "AutofillManagerServiceImpl",
                        "handleSessionSave(): already gone: " + session.id);
            } finally {
            }
        }
    }
}
