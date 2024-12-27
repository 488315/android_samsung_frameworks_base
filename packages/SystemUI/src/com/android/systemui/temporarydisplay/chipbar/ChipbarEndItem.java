package com.android.systemui.temporarydisplay.chipbar;

import android.view.View;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ChipbarEndItem {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Error extends ChipbarEndItem {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
