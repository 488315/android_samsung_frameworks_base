package com.android.systemui.statusbar.chips.mediaprojection.domain.model;

import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface MediaProjectionStopDialogModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Hidden implements MediaProjectionStopDialogModel {
        public static final Hidden INSTANCE = new Hidden();

        private Hidden() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Hidden);
        }

        public final int hashCode() {
            return -618354261;
        }

        public final String toString() {
            return "Hidden";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Shown implements MediaProjectionStopDialogModel {
        public final SystemUIDialog.Delegate dialogDelegate;
        public final Function0 onDismissAction;

        public Shown(SystemUIDialog.Delegate delegate, Function0 function0) {
            this.dialogDelegate = delegate;
            this.onDismissAction = function0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Shown)) {
                return false;
            }
            Shown shown = (Shown) obj;
            return Intrinsics.areEqual(this.dialogDelegate, shown.dialogDelegate) && Intrinsics.areEqual(this.onDismissAction, shown.onDismissAction);
        }

        public final int hashCode() {
            return this.onDismissAction.hashCode() + (this.dialogDelegate.hashCode() * 31);
        }

        public final String toString() {
            return "Shown(dialogDelegate=" + this.dialogDelegate + ", onDismissAction=" + this.onDismissAction + ")";
        }
    }
}
