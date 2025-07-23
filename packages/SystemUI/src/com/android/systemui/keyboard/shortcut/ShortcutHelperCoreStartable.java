package com.android.systemui.keyboard.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputGestureData;
import android.hardware.input.InputSettings;
import android.os.UserHandle;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyboard.shortcut.data.repository.CustomInputGesturesRepository;
import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.CommandQueue;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperCoreStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final CoroutineScope backgroundScope;
    public final BroadcastDispatcher broadcastDispatcher;
    public final CommandQueue commandQueue;
    public final CustomInputGesturesRepository customInputGesturesRepository;
    public final ShortcutHelperStateRepository stateRepository;

    public ShortcutHelperCoreStartable(CommandQueue commandQueue, BroadcastDispatcher broadcastDispatcher, ShortcutHelperStateRepository shortcutHelperStateRepository, ActivityStarter activityStarter, CoroutineScope coroutineScope, CustomInputGesturesRepository customInputGesturesRepository) {
        this.commandQueue = commandQueue;
        this.broadcastDispatcher = broadcastDispatcher;
        this.stateRepository = shortcutHelperStateRepository;
        this.activityStarter = activityStarter;
        this.backgroundScope = coroutineScope;
        this.customInputGesturesRepository = customInputGesturesRepository;
    }

    public final void registerBroadcastReceiver(String str, final Function0 function0) {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$registerBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Function0.this.invoke();
            }
        }, new IntentFilter(str), null, UserHandle.ALL, 3, null, 36);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final int i = 0;
        registerBroadcastReceiver("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS", new Function0(this) { // from class: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ ShortcutHelperCoreStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list;
                ShortcutHelperCoreStartable shortcutHelperCoreStartable = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = ShortcutHelperCoreStartable.$r8$clinit;
                        shortcutHelperCoreStartable.getClass();
                        shortcutHelperCoreStartable.activityStarter.dismissKeyguardThenExecute(new ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1(shortcutHelperCoreStartable, new ShortcutHelperCoreStartable$showShortcutHelper$1(shortcutHelperCoreStartable, null)), ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$2.INSTANCE, true);
                        break;
                    case 1:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    case 2:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    default:
                        CustomInputGesturesRepository customInputGesturesRepository = shortcutHelperCoreStartable.customInputGesturesRepository;
                        customInputGesturesRepository.getClass();
                        if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
                            list = customInputGesturesRepository.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
                            list.getClass();
                        } else {
                            list = EmptyList.INSTANCE;
                        }
                        customInputGesturesRepository._customInputGesture.setValue(list);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i2 = 1;
        registerBroadcastReceiver("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS", new Function0(this) { // from class: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ ShortcutHelperCoreStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list;
                ShortcutHelperCoreStartable shortcutHelperCoreStartable = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = ShortcutHelperCoreStartable.$r8$clinit;
                        shortcutHelperCoreStartable.getClass();
                        shortcutHelperCoreStartable.activityStarter.dismissKeyguardThenExecute(new ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1(shortcutHelperCoreStartable, new ShortcutHelperCoreStartable$showShortcutHelper$1(shortcutHelperCoreStartable, null)), ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$2.INSTANCE, true);
                        break;
                    case 1:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    case 2:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    default:
                        CustomInputGesturesRepository customInputGesturesRepository = shortcutHelperCoreStartable.customInputGesturesRepository;
                        customInputGesturesRepository.getClass();
                        if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
                            list = customInputGesturesRepository.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
                            list.getClass();
                        } else {
                            list = EmptyList.INSTANCE;
                        }
                        customInputGesturesRepository._customInputGesture.setValue(list);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i3 = 2;
        registerBroadcastReceiver(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS, new Function0(this) { // from class: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ ShortcutHelperCoreStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list;
                ShortcutHelperCoreStartable shortcutHelperCoreStartable = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = ShortcutHelperCoreStartable.$r8$clinit;
                        shortcutHelperCoreStartable.getClass();
                        shortcutHelperCoreStartable.activityStarter.dismissKeyguardThenExecute(new ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1(shortcutHelperCoreStartable, new ShortcutHelperCoreStartable$showShortcutHelper$1(shortcutHelperCoreStartable, null)), ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$2.INSTANCE, true);
                        break;
                    case 1:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    case 2:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    default:
                        CustomInputGesturesRepository customInputGesturesRepository = shortcutHelperCoreStartable.customInputGesturesRepository;
                        customInputGesturesRepository.getClass();
                        if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
                            list = customInputGesturesRepository.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
                            list.getClass();
                        } else {
                            list = EmptyList.INSTANCE;
                        }
                        customInputGesturesRepository._customInputGesture.setValue(list);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i4 = 3;
        registerBroadcastReceiver("android.intent.action.USER_SWITCHED", new Function0(this) { // from class: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ ShortcutHelperCoreStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List list;
                ShortcutHelperCoreStartable shortcutHelperCoreStartable = this.f$0;
                switch (i4) {
                    case 0:
                        int i22 = ShortcutHelperCoreStartable.$r8$clinit;
                        shortcutHelperCoreStartable.getClass();
                        shortcutHelperCoreStartable.activityStarter.dismissKeyguardThenExecute(new ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1(shortcutHelperCoreStartable, new ShortcutHelperCoreStartable$showShortcutHelper$1(shortcutHelperCoreStartable, null)), ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$2.INSTANCE, true);
                        break;
                    case 1:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    case 2:
                        shortcutHelperCoreStartable.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                        break;
                    default:
                        CustomInputGesturesRepository customInputGesturesRepository = shortcutHelperCoreStartable.customInputGesturesRepository;
                        customInputGesturesRepository.getClass();
                        if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
                            list = customInputGesturesRepository.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
                            list.getClass();
                        } else {
                            list = EmptyList.INSTANCE;
                        }
                        customInputGesturesRepository._customInputGesture.setValue(list);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$start$5
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void dismissKeyboardShortcutsMenu() {
                ShortcutHelperCoreStartable.this.stateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void toggleKeyboardShortcutsMenu(int i5) {
                Integer valueOf = Integer.valueOf(i5);
                int i6 = ShortcutHelperCoreStartable.$r8$clinit;
                ShortcutHelperCoreStartable shortcutHelperCoreStartable = ShortcutHelperCoreStartable.this;
                shortcutHelperCoreStartable.getClass();
                shortcutHelperCoreStartable.activityStarter.dismissKeyguardThenExecute(new ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1(shortcutHelperCoreStartable, new ShortcutHelperCoreStartable$toggleShortcutHelper$1(shortcutHelperCoreStartable, valueOf, null)), ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$2.INSTANCE, true);
            }
        });
    }
}
