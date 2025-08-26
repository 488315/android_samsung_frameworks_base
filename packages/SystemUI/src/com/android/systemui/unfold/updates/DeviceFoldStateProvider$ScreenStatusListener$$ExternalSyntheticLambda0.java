package com.android.systemui.unfold.updates;

import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceFoldStateProvider$ScreenStatusListener$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceFoldStateProvider f$0;

    public /* synthetic */ DeviceFoldStateProvider$ScreenStatusListener$$ExternalSyntheticLambda0(DeviceFoldStateProvider deviceFoldStateProvider, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceFoldStateProvider;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                DeviceFoldStateProvider deviceFoldStateProvider = this.f$0;
                if (!deviceFoldStateProvider.isFolded && !deviceFoldStateProvider.isUnfoldHandled) {
                    Iterator it = deviceFoldStateProvider.outputListeners.iterator();
                    while (it.hasNext()) {
                        ((FoldStateProvider.FoldUpdatesListener) it.next()).onUnfoldedScreenAvailable();
                    }
                    deviceFoldStateProvider.isUnfoldHandled = true;
                }
                break;
            case 1:
                DeviceFoldStateProvider deviceFoldStateProvider2 = this.f$0;
                deviceFoldStateProvider2.isScreenOn = false;
                deviceFoldStateProvider2.assertInProgressThread$2();
                boolean z = deviceFoldStateProvider2.isScreenOn;
                HingeAngleProvider hingeAngleProvider = deviceFoldStateProvider2.hingeAngleProvider;
                if (!z || deviceFoldStateProvider2.isFolded) {
                    hingeAngleProvider.stop();
                } else {
                    hingeAngleProvider.start();
                }
                break;
            default:
                DeviceFoldStateProvider deviceFoldStateProvider3 = this.f$0;
                deviceFoldStateProvider3.isScreenOn = true;
                deviceFoldStateProvider3.assertInProgressThread$2();
                boolean z2 = deviceFoldStateProvider3.isScreenOn;
                HingeAngleProvider hingeAngleProvider2 = deviceFoldStateProvider3.hingeAngleProvider;
                if (!z2 || deviceFoldStateProvider3.isFolded) {
                    hingeAngleProvider2.stop();
                } else {
                    hingeAngleProvider2.start();
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
