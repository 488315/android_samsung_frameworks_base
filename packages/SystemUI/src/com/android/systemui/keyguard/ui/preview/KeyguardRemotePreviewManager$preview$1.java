package com.android.systemui.keyguard.ui.preview;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class KeyguardRemotePreviewManager$preview$1 extends FunctionReferenceImpl implements Function1 {
    public KeyguardRemotePreviewManager$preview$1(Object obj) {
        super(1, obj, KeyguardRemotePreviewManager.class, "destroyObserver", "destroyObserver(Lcom/android/systemui/keyguard/ui/preview/PreviewLifecycleObserver;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        KeyguardRemotePreviewManager keyguardRemotePreviewManager = (KeyguardRemotePreviewManager) this.receiver;
        int i = KeyguardRemotePreviewManager.$r8$clinit;
        keyguardRemotePreviewManager.destroyObserver((PreviewLifecycleObserver) obj);
        return Unit.INSTANCE;
    }
}
