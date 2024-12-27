package com.android.systemui.keyguard.ui.preview;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.android.systemui.Flags;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class PreviewLifecycleObserver implements Handler.Callback, IBinder.DeathRecipient {
    public boolean isDestroyedOrDestroying;
    public final CoroutineDispatcher mainDispatcher;
    public Function1 onDestroy;
    public KeyguardPreviewRenderer renderer;
    public final CoroutineScope scope;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            function1.invoke(this);
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Function1 function1;
        if (this.isDestroyedOrDestroying) {
            return true;
        }
        final KeyguardPreviewRenderer keyguardPreviewRenderer = this.renderer;
        if (keyguardPreviewRenderer == null || (function1 = this.onDestroy) == null) {
            Log.wtf("KeyguardRemotePreviewManager", "Renderer/onDestroy should not be null.");
            return true;
        }
        int i = message.what;
        if (i == 1111) {
            final boolean z = message.getData().getBoolean("hide_smart_space");
            keyguardPreviewRenderer.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$hideSmartspace$1
                @Override // java.lang.Runnable
                public final void run() {
                    View view = KeyguardPreviewRenderer.this.smartSpaceView;
                    if (view == null) {
                        return;
                    }
                    view.setVisibility(z ? 4 : 0);
                }
            });
        } else if (i != 1337) {
            function1.invoke(this);
        } else {
            String string = message.getData().getString("slot_id");
            if (string != null) {
                KeyguardPreviewRenderer keyguardPreviewRenderer2 = this.renderer;
                if (keyguardPreviewRenderer2 == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                Flags.keyguardBottomAreaRefactor();
                keyguardPreviewRenderer2.bottomAreaViewModel.selectedPreviewSlotId.updateState(null, string);
            }
        }
        return true;
    }

    public static /* synthetic */ void getOnDestroy$annotations() {
    }

    public static /* synthetic */ void getRenderer$annotations() {
    }
}
