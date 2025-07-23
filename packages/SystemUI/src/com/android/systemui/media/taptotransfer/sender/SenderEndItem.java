package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SenderEndItem {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Error extends SenderEndItem {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Loading extends SenderEndItem {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public /* synthetic */ SenderEndItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private SenderEndItem() {
    }
}
