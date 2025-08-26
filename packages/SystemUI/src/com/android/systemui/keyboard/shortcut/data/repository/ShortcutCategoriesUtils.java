package com.android.systemui.keyboard.shortcut.data.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.hardware.input.KeyGlyphMap;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutGroup;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo;
import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperExclusions;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutIcon;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ShortcutCategoriesUtils {
    public static final List SUPPORTED_MODIFIERS;
    public final CoroutineContext backgroundCoroutineContext;
    public final Context context;
    public final InputManager inputManager;
    public final ShortcutHelperExclusions shortcutHelperExclusions;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        SUPPORTED_MODIFIERS = Arrays.asList(65536, 4096, 2, 1, 4, 8);
    }

    public ShortcutCategoriesUtils(Context context, CoroutineContext coroutineContext, InputManager inputManager, ShortcutHelperExclusions shortcutHelperExclusions) {
        this.context = context;
        this.backgroundCoroutineContext = coroutineContext;
        this.inputManager = inputManager;
        this.shortcutHelperExclusions = shortcutHelperExclusions;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r21v0, types: [com.android.systemui.keyboard.shortcut.shared.model.Shortcut] */
    /* JADX WARN: Type inference failed for: r24v0 */
    /* JADX WARN: Type inference failed for: r24v1, types: [com.android.systemui.keyboard.shortcut.shared.model.ShortcutIcon] */
    /* JADX WARN: Type inference failed for: r24v2 */
    public final ShortcutCategory fetchShortcutCategory(ShortcutCategoryType shortcutCategoryType, List list, InputDevice inputDevice, Set set) {
        KeyGlyphMap keyGlyphMap;
        KeyCharacterMap keyCharacterMap;
        String resPackage;
        boolean z;
        ?? shortcutIcon;
        ShortcutCategory shortcut;
        int[] functionRowKeys;
        ShortcutCategory shortcutCategory = null;
        if (shortcutCategoryType == null) {
            return null;
        }
        KeyGlyphMap keyGlyphMap2 = this.inputManager.getKeyGlyphMap(inputDevice.getId());
        KeyCharacterMap keyCharacterMap2 = inputDevice.getKeyCharacterMap();
        boolean zIsTrusted = shortcutCategoryType.isTrusted();
        List<InternalKeyboardShortcutGroup> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (InternalKeyboardShortcutGroup internalKeyboardShortcutGroup : list2) {
            String str = internalKeyboardShortcutGroup.label;
            List list3 = internalKeyboardShortcutGroup.items;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : list3) {
                InternalKeyboardShortcutInfo internalKeyboardShortcutInfo = (InternalKeyboardShortcutInfo) obj;
                ShortcutCategory shortcutCategory2 = shortcutCategory;
                int i = internalKeyboardShortcutInfo.keycode;
                if (i == 0 || set.contains(Integer.valueOf(i)) || (keyGlyphMap2 != null && (functionRowKeys = keyGlyphMap2.getFunctionRowKeys()) != null && ArraysKt___ArraysKt.indexOf(internalKeyboardShortcutInfo.keycode, functionRowKeys) >= 0)) {
                    arrayList2.add(obj);
                }
                shortcutCategory = shortcutCategory2;
            }
            ShortcutCategory shortcutCategory3 = shortcutCategory;
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                InternalKeyboardShortcutInfo internalKeyboardShortcutInfo2 = (InternalKeyboardShortcutInfo) obj2;
                ShortcutCommand shortcutCommand = toShortcutCommand(keyGlyphMap2, keyCharacterMap2, internalKeyboardShortcutInfo2);
                if (shortcutCommand == null) {
                    keyGlyphMap = keyGlyphMap2;
                    keyCharacterMap = keyCharacterMap2;
                    z = zIsTrusted;
                    shortcut = shortcutCategory3;
                } else {
                    if (zIsTrusted) {
                        keyGlyphMap = keyGlyphMap2;
                        Icon icon = internalKeyboardShortcutInfo2.icon;
                        if (icon != null) {
                            keyCharacterMap = keyCharacterMap2;
                            if (icon.getType() == 2 && (resPackage = icon.getResPackage()) != null && resPackage.length() != 0 && icon.getResId() > 0) {
                                z = zIsTrusted;
                                shortcutIcon = new ShortcutIcon(icon.getResPackage(), icon.getResId());
                            }
                            List listSingletonList = Collections.singletonList(shortcutCommand);
                            ShortcutHelperExclusions shortcutHelperExclusions = this.shortcutHelperExclusions;
                            shortcut = new Shortcut(internalKeyboardShortcutInfo2.label, listSingletonList, shortcutIcon, null, !Arrays.asList(shortcutHelperExclusions.context.getString(R.string.group_system_cycle_forward), shortcutHelperExclusions.context.getString(R.string.group_system_cycle_back)).contains(internalKeyboardShortcutInfo2.label), 8, null);
                        }
                        z = zIsTrusted;
                        shortcutIcon = shortcutCategory3;
                        List listSingletonList2 = Collections.singletonList(shortcutCommand);
                        ShortcutHelperExclusions shortcutHelperExclusions2 = this.shortcutHelperExclusions;
                        shortcut = new Shortcut(internalKeyboardShortcutInfo2.label, listSingletonList2, shortcutIcon, null, !Arrays.asList(shortcutHelperExclusions2.context.getString(R.string.group_system_cycle_forward), shortcutHelperExclusions2.context.getString(R.string.group_system_cycle_back)).contains(internalKeyboardShortcutInfo2.label), 8, null);
                    } else {
                        keyGlyphMap = keyGlyphMap2;
                    }
                    keyCharacterMap = keyCharacterMap2;
                    z = zIsTrusted;
                    shortcutIcon = shortcutCategory3;
                    List listSingletonList22 = Collections.singletonList(shortcutCommand);
                    ShortcutHelperExclusions shortcutHelperExclusions22 = this.shortcutHelperExclusions;
                    shortcut = new Shortcut(internalKeyboardShortcutInfo2.label, listSingletonList22, shortcutIcon, null, !Arrays.asList(shortcutHelperExclusions22.context.getString(R.string.group_system_cycle_forward), shortcutHelperExclusions22.context.getString(R.string.group_system_cycle_back)).contains(internalKeyboardShortcutInfo2.label), 8, null);
                }
                if (shortcut != null) {
                    arrayList3.add(shortcut);
                }
                keyGlyphMap2 = keyGlyphMap;
                keyCharacterMap2 = keyCharacterMap;
                zIsTrusted = z;
            }
            arrayList.add(new ShortcutSubCategory(str, arrayList3));
            shortcutCategory = shortcutCategory3;
        }
        ShortcutCategory shortcutCategory4 = shortcutCategory;
        ArrayList arrayList4 = new ArrayList();
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj3 = arrayList.get(i3);
            i3++;
            if (!((ShortcutSubCategory) obj3).shortcuts.isEmpty()) {
                arrayList4.add(obj3);
            }
        }
        if (!arrayList4.isEmpty()) {
            return new ShortcutCategory(shortcutCategoryType, arrayList4);
        }
        Log.w("ShortcutCategoriesUtils", "Empty sub categories after converting " + list);
        return shortcutCategory4;
    }

    public final ShortcutCommand toShortcutCommand(KeyGlyphMap keyGlyphMap, KeyCharacterMap keyCharacterMap, InternalKeyboardShortcutInfo internalKeyboardShortcutInfo) {
        ArrayList arrayList = new ArrayList();
        int i = internalKeyboardShortcutInfo.modifiers;
        Iterator it = SUPPORTED_MODIFIERS.iterator();
        while (true) {
            if (!it.hasNext()) {
                if (i != 0) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Unsupported modifiers remaining: ", "ShortcutCategoriesUtils");
                    return null;
                }
                char c = internalKeyboardShortcutInfo.baseCharacter;
                int i2 = internalKeyboardShortcutInfo.keycode;
                if (i2 != 0 || Intrinsics.compare(c, 0) > 0) {
                    ShortcutKey shortcutKey = toShortcutKey(keyGlyphMap, keyCharacterMap, i2, c);
                    if (shortcutKey != null) {
                        arrayList.add(shortcutKey);
                    }
                }
                if (!arrayList.isEmpty()) {
                    return new ShortcutCommand(arrayList, internalKeyboardShortcutInfo.isCustomShortcut);
                }
                Log.w("ShortcutCategoriesUtils", "No keys for " + internalKeyboardShortcutInfo);
                return null;
            }
            int iIntValue = ((Number) it.next()).intValue();
            if ((iIntValue & i) != 0) {
                ShortcutKey shortcutModifierKey = toShortcutModifierKey(iIntValue, keyGlyphMap);
                if (shortcutModifierKey == null) {
                    break;
                }
                arrayList.add(shortcutModifierKey);
                i &= ~iIntValue;
            }
        }
        return null;
    }

    public final ShortcutKey toShortcutKey(KeyGlyphMap keyGlyphMap, KeyCharacterMap keyCharacterMap, int i, char c) {
        Drawable drawableForKeycode = keyGlyphMap != null ? keyGlyphMap.getDrawableForKeycode(this.context, i) : null;
        if (drawableForKeycode != null) {
            return new ShortcutKey.Icon.DrawableIcon(drawableForKeycode);
        }
        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
        shortcutHelperKeys.getClass();
        Integer num = (Integer) ShortcutHelperKeys.keyIcons.get(Integer.valueOf(i));
        if (num != null) {
            return new ShortcutKey.Icon.ResIdIcon(num.intValue());
        }
        if (Intrinsics.compare(c, 0) > 0) {
            return new ShortcutKey.Text(String.valueOf(c).toUpperCase(Locale.ROOT));
        }
        shortcutHelperKeys.getClass();
        Function1 function1 = (Function1) ShortcutHelperKeys.specialKeyLabels.get(Integer.valueOf(i));
        if (function1 != null) {
            return new ShortcutKey.Text((String) function1.mo781invoke(this.context));
        }
        char displayLabel = keyCharacterMap.getDisplayLabel(i);
        if (displayLabel != 0) {
            return new ShortcutKey.Text(String.valueOf(displayLabel));
        }
        RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Couldn't find label or icon for key: ", "ShortcutCategoriesUtils");
        return null;
    }

    public final ShortcutKey toShortcutModifierKey(int i, KeyGlyphMap keyGlyphMap) {
        Drawable drawableForModifierState = keyGlyphMap != null ? keyGlyphMap.getDrawableForModifierState(this.context, i) : null;
        if (drawableForModifierState != null) {
            return new ShortcutKey.Icon.DrawableIcon(drawableForModifierState);
        }
        if (i == 65536) {
            ShortcutHelperKeys.INSTANCE.getClass();
            return new ShortcutKey.Icon.ResIdIcon(ShortcutHelperKeys.metaModifierIconResId);
        }
        ShortcutHelperKeys.INSTANCE.getClass();
        Function1 function1 = (Function1) ShortcutHelperKeys.modifierLabels.get(Integer.valueOf(i));
        if (function1 != null) {
            return new ShortcutKey.Text((String) function1.mo781invoke(this.context));
        }
        Log.wtf("TAG", "Couldn't find label or icon for modifier " + i);
        return null;
    }

    public final List toShortcutModifierKeys(int i, KeyGlyphMap keyGlyphMap) {
        ArrayList arrayList = new ArrayList();
        Iterator it = SUPPORTED_MODIFIERS.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            if ((iIntValue & i) != 0) {
                ShortcutKey shortcutModifierKey = toShortcutModifierKey(iIntValue, keyGlyphMap);
                if (shortcutModifierKey == null) {
                    return null;
                }
                arrayList.add(shortcutModifierKey);
                i &= ~iIntValue;
            }
        }
        return arrayList;
    }
}
