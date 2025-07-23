package com.android.systemui.dreams.homecontrols.shared;

import android.content.ComponentName;
import com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsComponentInfo;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IHomeControlsRemoteProxyExtKt$controlsSettings$1$listener$1 extends IOnControlsSettingsChangeListener.Stub {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public IHomeControlsRemoteProxyExtKt$controlsSettings$1$listener$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener
    public final void onControlsSettingsChanged(ComponentName componentName, boolean z) {
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo3456trySendJP2dKIU(new HomeControlsComponentInfo(componentName, z));
    }
}
