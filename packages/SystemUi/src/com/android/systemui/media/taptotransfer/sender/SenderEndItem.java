package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SenderEndItem {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Error extends SenderEndItem {
        public static final Error INSTANCE = new Error();

        private Error() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Loading extends SenderEndItem {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
