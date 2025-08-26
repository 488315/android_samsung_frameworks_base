package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class RingerViewModelState {

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
