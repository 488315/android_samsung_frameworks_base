package com.android.systemui.people;

import android.app.people.ConversationStatus;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ConversationStatus conversationStatus = (ConversationStatus) obj;
        switch (this.$r8$classId) {
            case 0:
                return conversationStatus.getAvailability() == 0;
            case 1:
                return conversationStatus.getActivity() == 1;
            default:
                return conversationStatus.getActivity() == 3;
        }
    }
}
