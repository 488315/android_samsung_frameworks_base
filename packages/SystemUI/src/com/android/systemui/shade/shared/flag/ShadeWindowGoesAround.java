package com.android.systemui.shade.shared.flag;

import android.window.DesktopExperienceFlags;
import java.util.function.BooleanSupplier;

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
