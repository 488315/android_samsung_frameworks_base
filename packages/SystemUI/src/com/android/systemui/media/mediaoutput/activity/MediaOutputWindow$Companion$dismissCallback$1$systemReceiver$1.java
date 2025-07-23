package com.android.systemui.media.mediaoutput.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.ext.BundleExtKt;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.popup.util.PopupUIUtil;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;
    public final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    public final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    public final String SYSTEM_DIALOG_REASON_DREAM = BcSmartspaceDataPlugin.UI_SURFACE_DREAM;
    public final Lazy reasons$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputWindow$$ExternalSyntheticLambda1(this, 2));

    public MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1(ProducerScope producerScope) {
        this.$$this$callbackFlow = producerScope;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String stringExtra;
        MediaSessions$H$$ExternalSyntheticOutline0.m("onReceive() - action = ", intent.getAction(), " - ", BundleExtKt.getSerialize(intent.getExtras()), "MediaOutputWindow");
        if (!Intrinsics.areEqual(intent.getAction(), PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            intent = null;
        }
        if (intent != null && (stringExtra = intent.getStringExtra(this.SYSTEM_DIALOG_REASON_KEY)) != null) {
            Log.d("MediaOutputWindow", this.SYSTEM_DIALOG_REASON_KEY + " = " + stringExtra);
            if (((List) this.reasons$delegate.getValue()).contains(stringExtra)) {
                stringExtra = null;
            }
            if (stringExtra != null) {
                return;
            }
        }
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1$onReceive$5(producerScope, null), 3);
    }
}
