package com.android.systemui.keyboard.shortcut.data.source;

import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.hardware.input.KeyGlyphMap;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.model.KeyboardShortcutInfoBuilder;
import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SystemShortcutsSource implements KeyboardShortcutGroupsSource {
    public final InputManager inputManager;
    public final Resources resources;

    public SystemShortcutsSource(Resources resources, InputManager inputManager) {
        this.resources = resources;
        this.inputManager = inputManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v2 */
    /* JADX WARN: Type inference failed for: r19v3 */
    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    public final Object shortcutGroups(int i, Continuation continuation) {
        ?? r19;
        int i2;
        Iterable iterable;
        int i3;
        boolean z = true;
        KeyboardShortcutGroup[] keyboardShortcutGroupArr = new KeyboardShortcutGroup[2];
        String string = this.resources.getString(R.string.shortcut_helper_category_system_controls);
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_access_all_apps_search));
        keyboardShortcutInfoBuilder.modifiers = 65536;
        int i4 = 0;
        keyboardShortcutInfoBuilder.keyCode = 0;
        Unit unit = Unit.INSTANCE;
        int i5 = keyboardShortcutInfoBuilder.keyCode;
        int i6 = keyboardShortcutInfoBuilder.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder2 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_access_home_screen));
        keyboardShortcutInfoBuilder2.modifiers = 65536;
        keyboardShortcutInfoBuilder2.keyCode = 36;
        int i7 = keyboardShortcutInfoBuilder2.keyCode;
        int i8 = keyboardShortcutInfoBuilder2.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder3 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_overview_open_apps));
        keyboardShortcutInfoBuilder3.modifiers = 65536;
        keyboardShortcutInfoBuilder3.keyCode = 61;
        int i9 = keyboardShortcutInfoBuilder3.keyCode;
        int i10 = keyboardShortcutInfoBuilder3.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder4 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_cycle_forward));
        keyboardShortcutInfoBuilder4.modifiers = 2;
        keyboardShortcutInfoBuilder4.keyCode = 61;
        int i11 = keyboardShortcutInfoBuilder4.keyCode;
        int i12 = keyboardShortcutInfoBuilder4.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder5 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_cycle_back));
        keyboardShortcutInfoBuilder5.modifiers = 3;
        keyboardShortcutInfoBuilder5.keyCode = 61;
        int i13 = keyboardShortcutInfoBuilder5.keyCode;
        int i14 = keyboardShortcutInfoBuilder5.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder6 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_go_back));
        keyboardShortcutInfoBuilder6.modifiers = 65536;
        keyboardShortcutInfoBuilder6.keyCode = 111;
        int i15 = keyboardShortcutInfoBuilder6.keyCode;
        int i16 = keyboardShortcutInfoBuilder6.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder7 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_go_back));
        keyboardShortcutInfoBuilder7.modifiers = 65536;
        keyboardShortcutInfoBuilder7.keyCode = 21;
        int i17 = keyboardShortcutInfoBuilder7.keyCode;
        int i18 = keyboardShortcutInfoBuilder7.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder8 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_full_screenshot));
        keyboardShortcutInfoBuilder8.modifiers = 65536;
        keyboardShortcutInfoBuilder8.keyCode = 47;
        int i19 = keyboardShortcutInfoBuilder8.keyCode;
        int i20 = keyboardShortcutInfoBuilder8.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder9 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_access_system_app_shortcuts));
        keyboardShortcutInfoBuilder9.modifiers = 65536;
        keyboardShortcutInfoBuilder9.keyCode = 76;
        int i21 = keyboardShortcutInfoBuilder9.keyCode;
        int i22 = keyboardShortcutInfoBuilder9.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder10 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_access_notification_shade));
        keyboardShortcutInfoBuilder10.modifiers = 65536;
        keyboardShortcutInfoBuilder10.keyCode = 42;
        int i23 = keyboardShortcutInfoBuilder10.keyCode;
        int i24 = keyboardShortcutInfoBuilder10.modifiers;
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder11 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_lock_screen));
        keyboardShortcutInfoBuilder11.modifiers = 65536;
        keyboardShortcutInfoBuilder11.keyCode = 40;
        List asList = Arrays.asList(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder.label, i5, i6), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder2.label, i7, i8), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder3.label, i9, i10), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder4.label, i11, i12), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder5.label, i13, i14), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder6.label, i15, i16), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder7.label, i17, i18), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder8.label, i19, i20), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder9.label, i21, i22), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder10.label, i23, i24), new KeyboardShortcutInfo(keyboardShortcutInfoBuilder11.label, keyboardShortcutInfoBuilder11.keyCode, keyboardShortcutInfoBuilder11.modifiers));
        KeyGlyphMap keyGlyphMap = this.inputManager.getKeyGlyphMap(i);
        if (keyGlyphMap != null) {
            ArrayList arrayList = new ArrayList();
            int[] functionRowKeys = keyGlyphMap.getFunctionRowKeys();
            int length = functionRowKeys.length;
            int i25 = 0;
            while (i25 < length) {
                int i26 = functionRowKeys[i25];
                ShortcutHelperKeys.INSTANCE.getClass();
                boolean z2 = z;
                Integer num = (Integer) ShortcutHelperKeys.keyLabelResIds.get(Integer.valueOf(i26));
                if (num != null) {
                    KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder12 = new KeyboardShortcutInfoBuilder(this.resources.getString(num.intValue()));
                    keyboardShortcutInfoBuilder12.modifiers = i4;
                    keyboardShortcutInfoBuilder12.keyCode = i26;
                    Unit unit2 = Unit.INSTANCE;
                    i3 = i4;
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder12.label, keyboardShortcutInfoBuilder12.keyCode, keyboardShortcutInfoBuilder12.modifiers));
                } else {
                    i3 = i4;
                }
                i25++;
                z = z2;
                i4 = i3;
            }
            r19 = z;
            i2 = i4;
            ArrayList arrayList2 = new ArrayList();
            for (Map.Entry entry : keyGlyphMap.getHardwareShortcuts().entrySet()) {
                KeyGlyphMap.KeyCombination keyCombination = (KeyGlyphMap.KeyCombination) entry.getKey();
                Integer num2 = (Integer) entry.getValue();
                ShortcutHelperKeys.INSTANCE.getClass();
                Integer num3 = (Integer) ShortcutHelperKeys.keyLabelResIds.get(num2);
                if (num3 != null) {
                    KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder13 = new KeyboardShortcutInfoBuilder(this.resources.getString(num3.intValue()));
                    int modifierState = keyCombination.getModifierState();
                    int keycode = keyCombination.getKeycode();
                    keyboardShortcutInfoBuilder13.modifiers = modifierState;
                    keyboardShortcutInfoBuilder13.keyCode = keycode;
                    Unit unit3 = Unit.INSTANCE;
                    arrayList2.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder13.label, keyboardShortcutInfoBuilder13.keyCode, keyboardShortcutInfoBuilder13.modifiers));
                }
            }
            iterable = CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList);
        } else {
            r19 = 1;
            i2 = 0;
            iterable = EmptyList.INSTANCE;
        }
        keyboardShortcutGroupArr[i2] = new KeyboardShortcutGroup(string, CollectionsKt___CollectionsKt.plus(iterable, asList));
        String string2 = this.resources.getString(R.string.shortcut_helper_category_system_apps);
        KeyboardShortcutInfo[] keyboardShortcutInfoArr = new KeyboardShortcutInfo[2];
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder14 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_access_system_settings));
        keyboardShortcutInfoBuilder14.modifiers = 65536;
        keyboardShortcutInfoBuilder14.keyCode = 37;
        Unit unit4 = Unit.INSTANCE;
        keyboardShortcutInfoArr[i2] = new KeyboardShortcutInfo(keyboardShortcutInfoBuilder14.label, keyboardShortcutInfoBuilder14.keyCode, keyboardShortcutInfoBuilder14.modifiers);
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder15 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_system_access_google_assistant));
        keyboardShortcutInfoBuilder15.modifiers = 65536;
        keyboardShortcutInfoBuilder15.keyCode = 29;
        keyboardShortcutInfoArr[r19] = new KeyboardShortcutInfo(keyboardShortcutInfoBuilder15.label, keyboardShortcutInfoBuilder15.keyCode, keyboardShortcutInfoBuilder15.modifiers);
        keyboardShortcutGroupArr[r19] = new KeyboardShortcutGroup(string2, Arrays.asList(keyboardShortcutInfoArr));
        return Arrays.asList(keyboardShortcutGroupArr);
    }
}
