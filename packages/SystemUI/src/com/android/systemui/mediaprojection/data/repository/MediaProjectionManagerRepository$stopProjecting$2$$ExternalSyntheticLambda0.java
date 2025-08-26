package com.android.systemui.mediaprojection.data.repository;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "Requesting MediaProjectionManager#stopActiveProjection";
            case 1:
                int i = MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1.$r8$clinit;
                return "Callback#onStop";
            case 2:
                int i2 = MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1.$r8$clinit;
                return "Callback#onStart";
            case 3:
                int i3 = MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Callback#onMediaProjectionEvent : ", logMessage.getStr1());
            default:
                int i4 = MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Callback#onSessionSet: ", logMessage.getStr1());
        }
    }
}
