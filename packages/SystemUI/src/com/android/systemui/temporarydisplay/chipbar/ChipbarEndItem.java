package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public abstract class ChipbarEndItem {

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

    public final class Error extends ChipbarEndItem {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }

    public final class Loading extends ChipbarEndItem {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    private ChipbarEndItem() {
    }

    public /* synthetic */ ChipbarEndItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
