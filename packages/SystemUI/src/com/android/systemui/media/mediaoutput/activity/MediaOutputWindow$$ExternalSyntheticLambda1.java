package com.android.systemui.media.mediaoutput.activity;

import android.widget.PopupWindow;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputWindow$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaOutputWindow$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(((MediaOutputWindow) this.f$0).context.getResources().getConfiguration().isNightModeActive());
            case 1:
                PopupWindow popupWindow = ((MediaOutputWindow) this.f$0).popupWindow;
                if (popupWindow == null) {
                    popupWindow = null;
                }
                popupWindow.dismiss();
                return Unit.INSTANCE;
            default:
                MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1 mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1 = (MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1) this.f$0;
                return Arrays.asList(mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1.SYSTEM_DIALOG_REASON_RECENT_APPS, mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1.SYSTEM_DIALOG_REASON_HOME_KEY, mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1.SYSTEM_DIALOG_REASON_DREAM);
        }
    }
}
