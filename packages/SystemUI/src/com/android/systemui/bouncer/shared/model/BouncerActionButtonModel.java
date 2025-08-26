package com.android.systemui.bouncer.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class BouncerActionButtonModel {

    public final class EmergencyButtonModel extends BouncerActionButtonModel {
        public final int labelResourceId;

        public EmergencyButtonModel(int i) {
            super(i, null);
            this.labelResourceId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof EmergencyButtonModel) && this.labelResourceId == ((EmergencyButtonModel) obj).labelResourceId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.labelResourceId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.labelResourceId, ")", new StringBuilder("EmergencyButtonModel(labelResourceId="));
        }
    }

    public final class ReturnToCallButtonModel extends BouncerActionButtonModel {
        public final int labelResourceId;

        public ReturnToCallButtonModel(int i) {
            super(i, null);
            this.labelResourceId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ReturnToCallButtonModel) && this.labelResourceId == ((ReturnToCallButtonModel) obj).labelResourceId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.labelResourceId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.labelResourceId, ")", new StringBuilder("ReturnToCallButtonModel(labelResourceId="));
        }
    }

    public /* synthetic */ BouncerActionButtonModel(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private BouncerActionButtonModel(int i) {
    }
}
