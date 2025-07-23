package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ChipbarEndItem {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Button extends ChipbarEndItem {
        public final View.OnClickListener onClickListener;
        public final Text text;

        public Button(Text text, View.OnClickListener onClickListener) {
            super(null);
            this.text = text;
            this.onClickListener = onClickListener;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Button)) {
                return false;
            }
            Button button = (Button) obj;
            return Intrinsics.areEqual(this.text, button.text) && Intrinsics.areEqual(this.onClickListener, button.onClickListener);
        }

        public final int hashCode() {
            return this.onClickListener.hashCode() + (this.text.hashCode() * 31);
        }

        public final String toString() {
            return "Button(text=" + this.text + ", onClickListener=" + this.onClickListener + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Error extends ChipbarEndItem {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Loading extends ChipbarEndItem {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    public /* synthetic */ ChipbarEndItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ChipbarEndItem() {
    }
}
