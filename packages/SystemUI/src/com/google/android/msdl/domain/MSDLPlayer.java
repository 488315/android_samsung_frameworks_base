package com.google.android.msdl.domain;

import com.google.android.msdl.data.model.FeedbackLevel;
import com.google.android.msdl.data.model.MSDLToken;
import java.util.List;

/* loaded from: classes4.dex */
public interface MSDLPlayer {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final FeedbackLevel SYSTEM_FEEDBACK_LEVEL = FeedbackLevel.DEFAULT;

        private Companion() {
        }
    }

    List getHistory();

    void playToken(MSDLToken mSDLToken, InteractionProperties interactionProperties);
}
