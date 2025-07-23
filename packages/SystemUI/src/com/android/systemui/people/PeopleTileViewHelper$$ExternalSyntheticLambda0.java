package com.android.systemui.people;

import android.app.people.ConversationStatus;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ConversationStatus conversationStatus = (ConversationStatus) obj;
        switch (this.$r8$classId) {
            case 0:
                Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
                if (conversationStatus.getAvailability() == 0) {
                    break;
                }
                break;
            case 1:
                Pattern pattern2 = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
                if (conversationStatus.getActivity() == 1) {
                    break;
                }
                break;
            default:
                Pattern pattern3 = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
                if (conversationStatus.getActivity() == 3) {
                    break;
                }
                break;
        }
        return true;
    }
}
