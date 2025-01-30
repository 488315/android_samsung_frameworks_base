package com.android.systemui.shared.rotation;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.shared.rotation.RotationButtonController$TaskStackListenerImpl$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2489x8e6fda0e implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((ActivityManagerWrapper) obj).getRunningTask();
    }
}
