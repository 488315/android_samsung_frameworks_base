package com.android.systemui.keyboard.shortcut.data.source;

import android.content.res.Resources;
import android.hardware.input.InputSettings;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.model.KeyboardShortcutInfoBuilder;
import java.util.ArrayList;
import java.util.Collections;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class AccessibilityShortcutsSource implements KeyboardShortcutGroupsSource {
    public final Resources resources;

    public AccessibilityShortcutsSource(Resources resources) {
        this.resources = resources;
    }

    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    public final Object shortcutGroups(int i, Continuation continuation) throws Resources.NotFoundException {
        String string = this.resources.getString(R.string.shortcutHelper_category_accessibility);
        ArrayList arrayList = new ArrayList();
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_accessibility_toggle_bounce_keys));
        keyboardShortcutInfoBuilder.modifiers = 65538;
        keyboardShortcutInfoBuilder.keyCode = 10;
        Unit unit = Unit.INSTANCE;
        arrayList.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder.label, keyboardShortcutInfoBuilder.keyCode, keyboardShortcutInfoBuilder.modifiers));
        if (InputSettings.isAccessibilityMouseKeysFeatureFlagEnabled()) {
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder2 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_accessibility_toggle_mouse_keys));
            keyboardShortcutInfoBuilder2.modifiers = 65538;
            keyboardShortcutInfoBuilder2.keyCode = 11;
            arrayList.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder2.label, keyboardShortcutInfoBuilder2.keyCode, keyboardShortcutInfoBuilder2.modifiers));
        }
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder3 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_accessibility_toggle_sticky_keys));
        keyboardShortcutInfoBuilder3.modifiers = 65538;
        keyboardShortcutInfoBuilder3.keyCode = 12;
        arrayList.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder3.label, keyboardShortcutInfoBuilder3.keyCode, keyboardShortcutInfoBuilder3.modifiers));
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder4 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.group_accessibility_toggle_slow_keys));
        keyboardShortcutInfoBuilder4.modifiers = 65538;
        keyboardShortcutInfoBuilder4.keyCode = 13;
        arrayList.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder4.label, keyboardShortcutInfoBuilder4.keyCode, keyboardShortcutInfoBuilder4.modifiers));
        return Collections.singletonList(new KeyboardShortcutGroup(string, arrayList));
    }
}
