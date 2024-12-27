package com.android.systemui.statusbar.model;

import android.view.KeyboardShortcutInfo;
import java.util.Optional;
import java.util.function.Predicate;

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
