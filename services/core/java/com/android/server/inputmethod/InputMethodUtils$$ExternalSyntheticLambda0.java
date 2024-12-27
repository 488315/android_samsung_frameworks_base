package com.android.server.inputmethod;

import android.util.ArraySet;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class InputMethodUtils$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((ArrayList) obj2).add((String) obj);
                break;
            default:
                ((ArraySet) obj2).add((String) obj);
                break;
        }
    }
}
