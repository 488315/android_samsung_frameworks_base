package com.android.systemui.people;

import android.app.people.ConversationStatus;
import java.util.function.Function;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda3 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
        return Long.valueOf(((ConversationStatus) obj).getStartTimeMillis());
    }
}
