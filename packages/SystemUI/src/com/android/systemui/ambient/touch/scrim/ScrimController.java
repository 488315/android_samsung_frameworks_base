package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ScrimController {
    void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent);

    default void reset$1() {
    }

    default void show() {
    }
}
