package com.android.systemui.statusbar.chips.ui.model;

import android.view.View;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class OngoingActivityChipModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Hidden extends OngoingActivityChipModel {
        public static final Hidden INSTANCE = new Hidden();

        private Hidden() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Hidden);
        }

        public final int hashCode() {
            return 1733264729;
        }

        public final String toString() {
            return "Hidden";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Shown extends OngoingActivityChipModel {
        public final Icon icon;
        public final View.OnClickListener onClickListener;
        public final long startTimeMs;

        public Shown(Icon icon, long j, View.OnClickListener onClickListener) {
            super(null);
            this.icon = icon;
            this.startTimeMs = j;
            this.onClickListener = onClickListener;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Shown)) {
                return false;
            }
            Shown shown = (Shown) obj;
            return Intrinsics.areEqual(this.icon, shown.icon) && this.startTimeMs == shown.startTimeMs && Intrinsics.areEqual(this.onClickListener, shown.onClickListener);
        }

        public final int hashCode() {
            return this.onClickListener.hashCode() + Scale$$ExternalSyntheticOutline0.m(this.icon.hashCode() * 31, 31, this.startTimeMs);
        }

        public final String toString() {
            return "Shown(icon=" + this.icon + ", startTimeMs=" + this.startTimeMs + ", onClickListener=" + this.onClickListener + ")";
        }
    }

    private OngoingActivityChipModel() {
    }

    public /* synthetic */ OngoingActivityChipModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
