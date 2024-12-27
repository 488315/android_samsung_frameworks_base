package com.android.systemui.keyboard.shortcut.data.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.CommandQueue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class ShortcutHelperRepository implements CoreStartable {
    public final BroadcastDispatcher broadcastDispatcher;
    public final CommandQueue commandQueue;
    public final StateFlowImpl state = StateFlowKt.MutableStateFlow(ShortcutHelperState.Inactive.INSTANCE);

    public ShortcutHelperRepository(CommandQueue commandQueue, BroadcastDispatcher broadcastDispatcher) {
        this.commandQueue = commandQueue;
        this.broadcastDispatcher = broadcastDispatcher;
    }

    public final void registerBroadcastReceiver(String str, final Function0 function0) {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperRepository$registerBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Function0.this.invoke();
            }
        }, new IntentFilter(str), null, UserHandle.ALL, 3, null, 36);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        registerBroadcastReceiver("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS", new Function0() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperRepository$start$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ShortcutHelperRepository.this.state.updateState(null, new ShortcutHelperState.Active(null, 1, null));
                return Unit.INSTANCE;
            }
        });
        registerBroadcastReceiver("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS", new Function0() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperRepository$start$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ShortcutHelperRepository.this.state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                return Unit.INSTANCE;
            }
        });
        registerBroadcastReceiver(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS, new Function0() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperRepository$start$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ShortcutHelperRepository.this.state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                return Unit.INSTANCE;
            }
        });
        this.commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperRepository$start$4
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void dismissKeyboardShortcutsMenu() {
                ShortcutHelperRepository.this.state.setValue(ShortcutHelperState.Inactive.INSTANCE);
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void toggleKeyboardShortcutsMenu(int i) {
                StateFlowImpl stateFlowImpl = ShortcutHelperRepository.this.state;
                stateFlowImpl.setValue(stateFlowImpl.getValue() instanceof ShortcutHelperState.Inactive ? new ShortcutHelperState.Active(Integer.valueOf(i)) : ShortcutHelperState.Inactive.INSTANCE);
            }
        });
    }
}
