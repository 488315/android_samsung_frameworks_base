package com.android.systemui.model;

import com.android.systemui.model.SysUiState;
import kotlin.Function;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SysUIStateOverride$sam$com_android_systemui_model_SysUiState_SysUiStateCallback$0 implements SysUiState.SysUiStateCallback, FunctionAdapter {
    public final /* synthetic */ Function2 function;

    public SysUIStateOverride$sam$com_android_systemui_model_SysUiState_SysUiStateCallback$0(Function2 function2) {
        this.function = function2;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof SysUiState.SysUiStateCallback) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return this.function;
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
    public final /* synthetic */ void onSystemUiStateChanged(int i, long j) {
        this.function.invoke(Long.valueOf(j), Integer.valueOf(i));
    }
}
