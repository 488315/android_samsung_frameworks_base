package com.google.android.msdl.domain;

import com.google.android.msdl.data.model.FeedbackLevel;
import com.google.android.msdl.data.model.MSDLToken;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface MSDLPlayer {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final FeedbackLevel SYSTEM_FEEDBACK_LEVEL = FeedbackLevel.DEFAULT;

        private Companion() {
        }
    }

    List getHistory();

    void playToken(MSDLToken mSDLToken, InteractionProperties interactionProperties);
}
