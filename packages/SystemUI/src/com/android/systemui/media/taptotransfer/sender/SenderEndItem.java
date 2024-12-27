package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SenderEndItem {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Error extends SenderEndItem {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Loading extends SenderEndItem {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class UndoButton extends SenderEndItem {
        public final int newState;
        public final UiEventLogger.UiEventEnum uiEventOnClick;

        public UndoButton(UiEventLogger.UiEventEnum uiEventEnum, int i) {
            super(null);
            this.uiEventOnClick = uiEventEnum;
            this.newState = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UndoButton)) {
                return false;
            }
            UndoButton undoButton = (UndoButton) obj;
            return Intrinsics.areEqual(this.uiEventOnClick, undoButton.uiEventOnClick) && this.newState == undoButton.newState;
        }

        public final int hashCode() {
            return Integer.hashCode(this.newState) + (this.uiEventOnClick.hashCode() * 31);
        }

        public final String toString() {
            return "UndoButton(uiEventOnClick=" + this.uiEventOnClick + ", newState=" + this.newState + ")";
        }
    }

    private SenderEndItem() {
    }

    public /* synthetic */ SenderEndItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
