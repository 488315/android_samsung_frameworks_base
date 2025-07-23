package com.samsung.android.sdk.scs.ai.visual.c2pa;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class C2paAssertion {
    private final Data data;
    private final String label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        private List<Action> actions;
        private final String label = "c2pa.actions";

        public final Builder actions(List<Action> list) {
            this.actions = list;
            return this;
        }

        public final C2paAssertion build() {
            return new C2paAssertion(this.label, new Data(this.actions));
        }
    }

    public C2paAssertion(String str, Data data) {
        this.label = str;
        this.data = data;
    }

    public static /* synthetic */ C2paAssertion copy$default(C2paAssertion c2paAssertion, String str, Data data, int i, Object obj) {
        if ((i & 1) != 0) {
            str = c2paAssertion.label;
        }
        if ((i & 2) != 0) {
            data = c2paAssertion.data;
        }
        return c2paAssertion.copy(str, data);
    }

    public final String component1() {
        return this.label;
    }

    public final Data component2() {
        return this.data;
    }

    public final C2paAssertion copy(String str, Data data) {
        return new C2paAssertion(str, data);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2paAssertion)) {
            return false;
        }
        C2paAssertion c2paAssertion = (C2paAssertion) obj;
        return Intrinsics.areEqual(this.label, c2paAssertion.label) && Intrinsics.areEqual(this.data, c2paAssertion.data);
    }

    public final Data getData() {
        return this.data;
    }

    public final String getLabel() {
        return this.label;
    }

    public int hashCode() {
        String str = this.label;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        Data data = this.data;
        return hashCode + (data != null ? data.hashCode() : 0);
    }

    public String toString() {
        return "C2paAssertion(label=" + this.label + ", data=" + this.data + ')';
    }
}
