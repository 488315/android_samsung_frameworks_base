package com.android.systemui.settings.brightness;

import android.net.Uri;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class BrightnessController$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        Uri uri = BrightnessController.BRIGHTNESS_MODE_URI;
        return "%s brightness set in display %d to %.3f".formatted(logMessage.getBool1() ? "Starting" : "Finishing", Integer.valueOf(logMessage.getInt1()), Double.valueOf(logMessage.getDouble1()));
    }
}
