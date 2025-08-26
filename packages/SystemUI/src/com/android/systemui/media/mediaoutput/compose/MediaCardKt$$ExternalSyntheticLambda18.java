package com.android.systemui.media.mediaoutput.compose;

import android.content.Intent;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCardKt$$ExternalSyntheticLambda18 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaCardKt$$ExternalSyntheticLambda18(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final SessionController sessionController = (SessionController) this.f$0;
                System.out.println((Object) AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("DisposableEffect - ", sessionController.getPackageName()));
                SessionController sessionController2 = sessionController.isPlaying() ? sessionController : null;
                if (sessionController2 != null) {
                    sessionController2.run();
                }
                return new DisposableEffectResult() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$lambda$63$lambda$62$lambda$61$$inlined$onDispose$1
                    @Override // androidx.compose.runtime.DisposableEffectResult
                    public final void dispose() {
                        SessionController sessionController3 = sessionController;
                        System.out.println((Object) AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("DisposableEffect - dispose - ", sessionController3.getPackageName()));
                        sessionController3.stop();
                    }
                };
            default:
                ((AudioPathInteraction) this.f$0).goToApp((Intent) obj);
                return Unit.INSTANCE;
        }
    }
}
