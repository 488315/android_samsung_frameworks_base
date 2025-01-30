package com.android.systemui.multishade.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ProxiedInputModel {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnDrag extends ProxiedInputModel {
        public final float xFraction;
        public final float yDragAmountPx;

        public OnDrag(float f, float f2) {
            super(null);
            this.xFraction = f;
            this.yDragAmountPx = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OnDrag)) {
                return false;
            }
            OnDrag onDrag = (OnDrag) obj;
            return Float.compare(this.xFraction, onDrag.xFraction) == 0 && Float.compare(this.yDragAmountPx, onDrag.yDragAmountPx) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.yDragAmountPx) + (Float.hashCode(this.xFraction) * 31);
        }

        public final String toString() {
            return "OnDrag(xFraction=" + this.xFraction + ", yDragAmountPx=" + this.yDragAmountPx + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnTap extends ProxiedInputModel {
        static {
            new OnTap();
        }

        private OnTap() {
            super(null);
        }
    }

    private ProxiedInputModel() {
    }

    public /* synthetic */ ProxiedInputModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
