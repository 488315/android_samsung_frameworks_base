package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class RingerViewModelState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Available extends RingerViewModelState {
        public final int orientation;
        public final RingerViewModel uiModel;

        public Available(RingerViewModel ringerViewModel, int i) {
            super(null);
            this.uiModel = ringerViewModel;
            this.orientation = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Available)) {
                return false;
            }
            Available available = (Available) obj;
            return Intrinsics.areEqual(this.uiModel, available.uiModel) && this.orientation == available.orientation;
        }

        public final int hashCode() {
            return Integer.hashCode(this.orientation) + (this.uiModel.hashCode() * 31);
        }

        public final String toString() {
            return "Available(uiModel=" + this.uiModel + ", orientation=" + this.orientation + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Unavailable extends RingerViewModelState {
        public static final Unavailable INSTANCE = new Unavailable();

        private Unavailable() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unavailable);
        }

        public final int hashCode() {
            return 1154574160;
        }

        public final String toString() {
            return "Unavailable";
        }
    }

    public /* synthetic */ RingerViewModelState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private RingerViewModelState() {
    }
}
