package com.android.systemui.bouncer.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BouncerActionButtonModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
