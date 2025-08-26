package com.android.systemui.scene.shared.logger;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class SceneLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = SceneLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Scene transition started: ", logMessage.getStr1(), " → ", logMessage.getStr2());
            case 1:
                int i2 = SceneLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Scene transition idle on: ", logMessage.getStr1(), ", overlays: ", logMessage.getStr2());
            case 2:
                int i3 = SceneLogger.$r8$clinit;
                StringBuilder sb = new StringBuilder("Overlay change requested: ");
                if (logMessage.getStr1() != null) {
                    sb.append(logMessage.getStr1());
                    sb.append(logMessage.getStr2() == null ? " (hidden)" : AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" → ", logMessage.getStr2()));
                } else {
                    sb.append(logMessage.getStr2() + " (shown)");
                }
                sb.append(", reason: " + logMessage.getStr3());
                return sb.toString();
            case 3:
                int i4 = SceneLogger.$r8$clinit;
                StringBuilder sb2 = new StringBuilder("REJECTED ");
                sb2.append(logMessage.getBool1() ? "overlay " : "scene ");
                sb2.append(MotionLayout$$ExternalSyntheticOutline0.m("change ", logMessage.getStr1(), " because \"", logMessage.getStr2(), "\""));
                if (logMessage.getStr3() != null) {
                    sb2.append(" (original change reason: \"" + logMessage.getStr3() + "\")");
                }
                return sb2.toString();
            case 4:
                int i5 = SceneLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("CANCELED scene change. scene: ", logMessage.getStr1(), ", sceneState: ", logMessage.getStr2());
            default:
                int i6 = SceneLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Scene framework is ", logMessage.getBool1() ? "enabled" : "disabled", logMessage.getStr1() != null ? AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" ", logMessage.getStr1()) : "");
        }
    }
}
