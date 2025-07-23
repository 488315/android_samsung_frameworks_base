package com.android.systemui.keyguard.ui.preview;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PreviewLifecycleObserver implements Handler.Callback, IBinder.DeathRecipient {
    public boolean isDestroyedOrDestroying;
    public final CoroutineDispatcher mainDispatcher;
    public Function1 onDestroy;
    public KeyguardPreviewRenderer renderer;
    public final CoroutineScope scope;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public PreviewLifecycleObserver(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, KeyguardPreviewRenderer keyguardPreviewRenderer, Function1 function1) {
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.renderer = keyguardPreviewRenderer;
        this.onDestroy = function1;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Function1 function1 = this.onDestroy;
        if (function1 != null) {
            function1.mo779invoke(this);
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Function1 function1;
        if (!this.isDestroyedOrDestroying) {
            final KeyguardPreviewRenderer keyguardPreviewRenderer = this.renderer;
            if (keyguardPreviewRenderer == null || (function1 = this.onDestroy) == null) {
                Log.wtf("KeyguardRemotePreviewManager", "Renderer/onDestroy should not be null.");
                return true;
            }
            int i = message.what;
            KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = keyguardPreviewRenderer.quickAffordancesCombinedViewModel;
            if (i == 214) {
                String string = message.getData().getString("initially_selected_slot_id");
                keyguardQuickAffordancesCombinedViewModel.enablePreviewMode(string != null ? string : "bottom_start", true);
                return true;
            }
            if (i == 707) {
                keyguardQuickAffordancesCombinedViewModel.previewAffordances.setValue(MapsKt__MapsKt.emptyMap());
                keyguardQuickAffordancesCombinedViewModel.enablePreviewMode(null, false);
                return true;
            }
            if (i == 1111) {
                final boolean z = message.getData().getBoolean("hide_smart_space");
                keyguardPreviewRenderer.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$hideSmartspace$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        View view = KeyguardPreviewRenderer.this.smartSpaceView;
                        if (view != null) {
                            view.setVisibility(z ? 4 : 0);
                        }
                    }
                });
                return true;
            }
            if (i == 1119) {
                String string2 = message.getData().getString("clock_size");
                if (string2 != null) {
                    ClockSizeSetting clockSizeSetting = string2.equals("clock_size_dynamic") ? ClockSizeSetting.DYNAMIC : string2.equals("clock_size_small") ? ClockSizeSetting.SMALL : null;
                    if (clockSizeSetting != null) {
                        KeyguardPreviewRenderer keyguardPreviewRenderer2 = this.renderer;
                        if (keyguardPreviewRenderer2 == null) {
                            throw new IllegalStateException("Required value was null.");
                        }
                        keyguardPreviewRenderer2.smartspaceViewModel.overrideClockSize.updateState(null, clockSizeSetting);
                        return true;
                    }
                }
            } else if (i == 1337) {
                String string3 = message.getData().getString("slot_id");
                if (string3 != null) {
                    KeyguardPreviewRenderer keyguardPreviewRenderer3 = this.renderer;
                    if (keyguardPreviewRenderer3 == null) {
                        throw new IllegalStateException("Required value was null.");
                    }
                    keyguardPreviewRenderer3.quickAffordancesCombinedViewModel.selectedPreviewSlotId.updateState(null, string3);
                    return true;
                }
            } else {
                if (i != 1988) {
                    function1.mo779invoke(this);
                    return true;
                }
                String string4 = message.getData().getString("slot_id");
                String string5 = message.getData().getString("quick_affordance_id");
                if (string4 != null && string5 != null) {
                    KeyguardPreviewRenderer keyguardPreviewRenderer4 = this.renderer;
                    if (keyguardPreviewRenderer4 == null) {
                        throw new IllegalStateException("Required value was null.");
                    }
                    KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel2 = keyguardPreviewRenderer4.quickAffordancesCombinedViewModel;
                    keyguardQuickAffordancesCombinedViewModel2.getClass();
                    KeyguardQuickAffordancePosition.Companion.getClass();
                    KeyguardQuickAffordancePosition keyguardQuickAffordancePosition = string4.equals("bottom_start") ? KeyguardQuickAffordancePosition.BOTTOM_START : string4.equals("bottom_end") ? KeyguardQuickAffordancePosition.BOTTOM_END : null;
                    if (keyguardQuickAffordancePosition != null) {
                        StateFlowImpl stateFlowImpl = keyguardQuickAffordancesCombinedViewModel2.previewAffordances;
                        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) stateFlowImpl.getValue());
                        linkedHashMap.put(keyguardQuickAffordancePosition, string5);
                        stateFlowImpl.updateState(null, new HashMap(linkedHashMap));
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public static /* synthetic */ void getOnDestroy$annotations() {
    }

    public static /* synthetic */ void getRenderer$annotations() {
    }
}
