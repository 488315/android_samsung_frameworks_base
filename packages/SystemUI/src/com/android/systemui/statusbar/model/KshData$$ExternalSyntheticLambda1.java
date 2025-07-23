package com.android.systemui.statusbar.model;

import android.view.KeyboardShortcutInfo;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
