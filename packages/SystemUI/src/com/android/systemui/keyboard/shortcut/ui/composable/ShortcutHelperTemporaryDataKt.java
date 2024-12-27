package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class ShortcutHelperTemporaryDataKt {
    public static final void access$command(ShortcutBuilder shortcutBuilder, Function1 function1) {
        List list = shortcutBuilder.commands;
        ShortcutCommandBuilder shortcutCommandBuilder = new ShortcutCommandBuilder();
        function1.invoke(shortcutCommandBuilder);
        ((ArrayList) list).add(new ShortcutCommand(shortcutCommandBuilder.keys));
    }

    public static final void access$shortcut(SubCategoryBuilder subCategoryBuilder, String str, Function1 function1) {
        List list = subCategoryBuilder.shortcuts;
        ShortcutBuilder shortcutBuilder = new ShortcutBuilder(str);
        function1.invoke(shortcutBuilder);
        ArrayList arrayList = (ArrayList) list;
        arrayList.add(new Shortcut(shortcutBuilder.label, shortcutBuilder.commands));
    }

    public static final ShortcutHelperCategory access$shortcutHelperCategory(int i, ImageVector imageVector, Function1 function1) {
        ShortcutHelperCategoryBuilder shortcutHelperCategoryBuilder = new ShortcutHelperCategoryBuilder(i, imageVector);
        function1.invoke(shortcutHelperCategoryBuilder);
        return new ShortcutHelperCategory(shortcutHelperCategoryBuilder.labelResId, shortcutHelperCategoryBuilder.icon, shortcutHelperCategoryBuilder.subCategories);
    }

    public static final void access$subCategory(ShortcutHelperCategoryBuilder shortcutHelperCategoryBuilder, String str, Function1 function1) {
        List list = shortcutHelperCategoryBuilder.subCategories;
        SubCategoryBuilder subCategoryBuilder = new SubCategoryBuilder(str);
        function1.invoke(subCategoryBuilder);
        ArrayList arrayList = (ArrayList) list;
        arrayList.add(new SubCategory(subCategoryBuilder.label, subCategoryBuilder.shortcuts));
    }
}
