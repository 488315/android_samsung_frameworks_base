package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeTransitionModel {
    public final DozeStateModel from;

    /* renamed from: to */
    public final DozeStateModel f300to;

    /* JADX WARN: Multi-variable type inference failed */
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
        return this.from == dozeTransitionModel.from && this.f300to == dozeTransitionModel.f300to;
    }

    public final int hashCode() {
        return this.f300to.hashCode() + (this.from.hashCode() * 31);
    }

    public final String toString() {
        return "DozeTransitionModel(from=" + this.from + ", to=" + this.f300to + ")";
    }

    public DozeTransitionModel(DozeStateModel dozeStateModel, DozeStateModel dozeStateModel2) {
        this.from = dozeStateModel;
        this.f300to = dozeStateModel2;
    }

    public /* synthetic */ DozeTransitionModel(DozeStateModel dozeStateModel, DozeStateModel dozeStateModel2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DozeStateModel.UNINITIALIZED : dozeStateModel, (i & 2) != 0 ? DozeStateModel.UNINITIALIZED : dozeStateModel2);
    }
}
