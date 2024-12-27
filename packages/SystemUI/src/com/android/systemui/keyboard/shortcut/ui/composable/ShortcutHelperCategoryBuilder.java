package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShortcutHelperCategoryBuilder {
    public final ImageVector icon;
    public final int labelResId;
    public final List subCategories = new ArrayList();

    public ShortcutHelperCategoryBuilder(int i, ImageVector imageVector) {
        this.labelResId = i;
        this.icon = imageVector;
    }
}
