package com.android.systemui.qs.bar;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
final /* synthetic */ class VideoCallMicModeBar$setClickListeners$1$1 extends FunctionReferenceImpl implements Function1 {
    public static final VideoCallMicModeBar$setClickListeners$1$1 INSTANCE = new VideoCallMicModeBar$setClickListeners$1$1();

    public VideoCallMicModeBar$setClickListeners$1$1() {
        super(1, Intrinsics.Kotlin.class, "sendEvent", "setClickListeners$sendEvent(Ljava/lang/String;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), (String) obj);
        return Unit.INSTANCE;
    }
}
