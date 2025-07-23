package com.android.systemui.keyboard.shortcut.data.source;

import android.content.Context;
import android.content.res.Resources;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.window.DesktopModeFlags;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.model.KeyboardShortcutInfoBuilder;
import com.android.wm.shell.shared.desktopmode.DesktopModeStatus;
import java.util.Collections;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MultitaskingShortcutsSource implements KeyboardShortcutGroupsSource {
    public final Context context;
    public final Resources resources;

    public MultitaskingShortcutsSource(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
    }

    @Override // com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource
    public final Object shortcutGroups(int i, Continuation continuation) {
        String string = this.resources.getString(R.string.shortcutHelper_category_split_screen);
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_multitasking_rhs));
        keyboardShortcutInfoBuilder.modifiers = 69632;
        keyboardShortcutInfoBuilder.keyCode = 22;
        Unit unit = Unit.INSTANCE;
        createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder.label, keyboardShortcutInfoBuilder.keyCode, keyboardShortcutInfoBuilder.modifiers));
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder2 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_multitasking_lhs));
        keyboardShortcutInfoBuilder2.modifiers = 69632;
        keyboardShortcutInfoBuilder2.keyCode = 21;
        createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder2.label, keyboardShortcutInfoBuilder2.keyCode, keyboardShortcutInfoBuilder2.modifiers));
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder3 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_multitasking_full_screen));
        keyboardShortcutInfoBuilder3.modifiers = 69632;
        keyboardShortcutInfoBuilder3.keyCode = 19;
        createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder3.label, keyboardShortcutInfoBuilder3.keyCode, keyboardShortcutInfoBuilder3.modifiers));
        if (DesktopModeStatus.canEnterDesktopMode(this.context)) {
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder4 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_multitasking_desktop_view));
            keyboardShortcutInfoBuilder4.modifiers = 69632;
            keyboardShortcutInfoBuilder4.keyCode = 20;
            createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder4.label, keyboardShortcutInfoBuilder4.keyCode, keyboardShortcutInfoBuilder4.modifiers));
        }
        KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder5 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_multitasking_move_to_next_display));
        keyboardShortcutInfoBuilder5.modifiers = 69632;
        keyboardShortcutInfoBuilder5.keyCode = 32;
        createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder5.label, keyboardShortcutInfoBuilder5.keyCode, keyboardShortcutInfoBuilder5.modifiers));
        if (DesktopModeStatus.canEnterDesktopMode(this.context) && DesktopModeFlags.ENABLE_TASK_RESIZING_KEYBOARD_SHORTCUTS.isTrue()) {
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder6 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_desktop_mode_snap_left_window));
            keyboardShortcutInfoBuilder6.modifiers = 65536;
            keyboardShortcutInfoBuilder6.keyCode = 71;
            createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder6.label, keyboardShortcutInfoBuilder6.keyCode, keyboardShortcutInfoBuilder6.modifiers));
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder7 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_desktop_mode_snap_right_window));
            keyboardShortcutInfoBuilder7.modifiers = 65536;
            keyboardShortcutInfoBuilder7.keyCode = 72;
            createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder7.label, keyboardShortcutInfoBuilder7.keyCode, keyboardShortcutInfoBuilder7.modifiers));
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder8 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_desktop_mode_toggle_maximize_window));
            keyboardShortcutInfoBuilder8.modifiers = 65536;
            keyboardShortcutInfoBuilder8.keyCode = 70;
            createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder8.label, keyboardShortcutInfoBuilder8.keyCode, keyboardShortcutInfoBuilder8.modifiers));
            KeyboardShortcutInfoBuilder keyboardShortcutInfoBuilder9 = new KeyboardShortcutInfoBuilder(this.resources.getString(R.string.system_desktop_mode_minimize_window));
            keyboardShortcutInfoBuilder9.modifiers = 65536;
            keyboardShortcutInfoBuilder9.keyCode = 69;
            createListBuilder.add(new KeyboardShortcutInfo(keyboardShortcutInfoBuilder9.label, keyboardShortcutInfoBuilder9.keyCode, keyboardShortcutInfoBuilder9.modifiers));
        }
        return Collections.singletonList(new KeyboardShortcutGroup(string, createListBuilder.build()));
    }
}
