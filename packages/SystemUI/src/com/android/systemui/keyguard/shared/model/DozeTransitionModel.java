package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DozeTransitionModel {
    public final DozeStateModel from;
    public final DozeStateModel to;

    public DozeTransitionModel() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DozeTransitionModel)) {
            return false;
        }
        DozeTransitionModel dozeTransitionModel = (DozeTransitionModel) obj;
        return this.from == dozeTransitionModel.from && this.to == dozeTransitionModel.to;
    }

    public final int hashCode() {
        return this.to.hashCode() + (this.from.hashCode() * 31);
    }

    public final String toString() {
        return "DozeTransitionModel(from=" + this.from + ", to=" + this.to + ")";
    }

    public DozeTransitionModel(DozeStateModel dozeStateModel, DozeStateModel dozeStateModel2) {
        this.from = dozeStateModel;
        this.to = dozeStateModel2;
    }

    public /* synthetic */ DozeTransitionModel(DozeStateModel dozeStateModel, DozeStateModel dozeStateModel2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DozeStateModel.UNINITIALIZED : dozeStateModel, (i & 2) != 0 ? DozeStateModel.UNINITIALIZED : dozeStateModel2);
    }
}
