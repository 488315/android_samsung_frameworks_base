package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.os.Handler;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* loaded from: classes.dex */
public class DexListenerWrapper extends ExternalService {
    private final SemDesktopModeManager.DesktopModeListener desktopModeListener;

    public DexListenerWrapper(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        super(context, listener, handler);
        this.desktopModeListener = new AnonymousClass1();
    }

    @Override // com.samsung.android.hardware.secinputdev.external.ExternalService
    public String register() throws Exception {
        SemDesktopModeManager desktopModeManager =
                (SemDesktopModeManager) this.context.getSystemService("desktopmode");
        if (desktopModeManager == null) {
            throw new Exception("SemDesktopModeManager is null");
        }
        desktopModeManager.registerListener(this.desktopModeListener);
        return "";
    }

    /* renamed from: com.samsung.android.hardware.secinputdev.external.DexListenerWrapper$1, reason: invalid class name */
    class AnonymousClass1 implements SemDesktopModeManager.DesktopModeListener {
        AnonymousClass1() {}

        public void onDesktopModeStateChanged(SemDesktopModeState state) {
            if ((state.state == 40 && state.enabled == 4)
                    || (state.state == 20 && state.enabled == 1)) {
                final int mode = state.enabled != 4 ? 0 : 1;
                DexListenerWrapper.this.handler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.hardware.secinputdev.external.DexListenerWrapper$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DexListenerWrapper.AnonymousClass1.this
                                        .lambda$onDesktopModeStateChanged$0(mode);
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDesktopModeStateChanged$0(int mode) {
            DexListenerWrapper.this.listener.onDesktopModeStateChanged(mode);
        }
    }
}
