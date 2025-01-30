package com.android.systemui.keyguard.ui.preview;

import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class KeyguardRemotePreviewManager$preview$2 extends FunctionReferenceImpl implements Function1 {
    public KeyguardRemotePreviewManager$preview$2(Object obj) {
        super(1, obj, KeyguardRemotePreviewManager.class, "destroyObserver", "destroyObserver(Lcom/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager$PreviewLifecycleObserver;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        KeyguardRemotePreviewManager keyguardRemotePreviewManager = (KeyguardRemotePreviewManager) this.receiver;
        int i = KeyguardRemotePreviewManager.$r8$clinit;
        keyguardRemotePreviewManager.destroyObserver((KeyguardRemotePreviewManager.PreviewLifecycleObserver) obj);
        return Unit.INSTANCE;
    }
}
