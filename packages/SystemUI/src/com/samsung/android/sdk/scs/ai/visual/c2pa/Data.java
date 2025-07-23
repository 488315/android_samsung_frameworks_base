package com.samsung.android.sdk.scs.ai.visual.c2pa;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Data {
    private final List<Action> actions;

    public Data(List<Action> list) {
        this.actions = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Data copy$default(Data data, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = data.actions;
        }
        return data.copy(list);
    }

    public final List<Action> component1() {
        return this.actions;
    }

    public final Data copy(List<Action> list) {
        return new Data(list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Data) && Intrinsics.areEqual(this.actions, ((Data) obj).actions);
    }

    public final List<Action> getActions() {
        return this.actions;
    }

    public int hashCode() {
        List<Action> list = this.actions;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    public String toString() {
        return "Data(actions=" + this.actions + ')';
    }
}
