package com.android.systemui.statusbar.model;

import android.view.KeyboardShortcutInfo;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KshData$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Optional.ofNullable(((KeyboardShortcutInfo) obj).getIcon()).isPresent();
            default:
                return ((Optional) obj).isPresent();
        }
    }
}
