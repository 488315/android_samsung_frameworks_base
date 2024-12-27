package com.android.systemui.people;

import android.app.people.ConversationStatus;
import java.util.function.Function;

public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda3 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Long.valueOf(((ConversationStatus) obj).getStartTimeMillis());
    }
}
