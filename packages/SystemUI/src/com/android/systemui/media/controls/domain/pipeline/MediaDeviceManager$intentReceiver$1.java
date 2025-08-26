package com.android.systemui.media.controls.domain.pipeline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.Unit;

/* loaded from: classes2.dex */
public final class MediaDeviceManager$intentReceiver$1 extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ MediaDeviceManager this$0;

    public MediaDeviceManager$intentReceiver$1(MediaDeviceManager mediaDeviceManager) {
        this.this$0 = mediaDeviceManager;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.hashCode() == -1315844839 && action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION") && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
            Log.d("MediaDeviceManager", "SEM_STREAM_DEVICES_CHANGED_ACTION");
            MediaDeviceManager mediaDeviceManager = this.this$0;
            synchronized (mediaDeviceManager.entries) {
                Map map = mediaDeviceManager.entries;
                final MediaDeviceManager$intentReceiver$1$$ExternalSyntheticLambda0 mediaDeviceManager$intentReceiver$1$$ExternalSyntheticLambda0 = new MediaDeviceManager$intentReceiver$1$$ExternalSyntheticLambda0();
                ((LinkedHashMap) map).forEach(new BiConsumer() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManagerKt$sam$java_util_function_BiConsumer$0
                    @Override // java.util.function.BiConsumer
                    public final /* synthetic */ void accept(Object obj, Object obj2) {
                        mediaDeviceManager$intentReceiver$1$$ExternalSyntheticLambda0.invoke(obj, obj2);
                    }
                });
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
