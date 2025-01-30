package com.android.systemui.people;

import android.app.people.ConversationStatus;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((ConversationStatus) obj).getAvailability() != 0) {
                    break;
                }
                break;
            case 1:
                if (((ConversationStatus) obj).getActivity() != 3) {
                    break;
                }
                break;
            default:
                if (((ConversationStatus) obj).getActivity() != 1) {
                    break;
                }
                break;
        }
        return false;
    }
}
