package com.android.systemui.shade.shared.flag;

import android.window.DesktopExperienceFlags;
import java.util.function.BooleanSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeWindowGoesAround {
    public static final ShadeWindowGoesAround INSTANCE = new ShadeWindowGoesAround();
    public static final DesktopExperienceFlags.DesktopExperienceFlag FLAG = new DesktopExperienceFlags.DesktopExperienceFlag(new BooleanSupplier() { // from class: com.android.systemui.shade.shared.flag.ShadeWindowGoesAround$FLAG$1
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return false;
        }
    }, true);

    private ShadeWindowGoesAround() {
    }

    public static final boolean isEnabled() {
        INSTANCE.getClass();
        return FLAG.isTrue();
    }
}
