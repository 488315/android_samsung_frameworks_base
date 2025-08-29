package com.android.systemui.dreams.homecontrols.shared;

import android.content.ComponentName;
import com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsComponentInfo;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
public final class IHomeControlsRemoteProxyExtKt$controlsSettings$1$listener$1 extends IOnControlsSettingsChangeListener.Stub {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public IHomeControlsRemoteProxyExtKt$controlsSettings$1$listener$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener
    public final void onControlsSettingsChanged(ComponentName componentName, boolean z) {
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo3475trySendJP2dKIU(new HomeControlsComponentInfo(componentName, z));
    }
}
