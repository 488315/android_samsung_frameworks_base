package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.RingerMode;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface RingerDrawerState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Closed implements RingerDrawerState {
        public final int currentMode;
        public final int previousMode;

        public /* synthetic */ Closed(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Closed)) {
                return false;
            }
            Closed closed = (Closed) obj;
            int i = closed.currentMode;
            Set set = RingerMode.supportedRingerModes;
            return this.currentMode == i && this.previousMode == closed.previousMode;
        }

        public final int hashCode() {
            Set set = RingerMode.supportedRingerModes;
            return Integer.hashCode(this.previousMode) + (Integer.hashCode(this.currentMode) * 31);
        }

        public final String toString() {
            return MotionLayout$$ExternalSyntheticOutline0.m("Closed(currentMode=", RingerMode.m992toStringimpl(this.currentMode), ", previousMode=", RingerMode.m992toStringimpl(this.previousMode), ")");
        }

        private Closed(int i, int i2) {
            this.currentMode = i;
            this.previousMode = i2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Initial extends RingerDrawerState {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion implements Initial {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Open implements RingerDrawerState {
        public final int mode;

        public /* synthetic */ Open(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Open)) {
                return false;
            }
            int i = ((Open) obj).mode;
            Set set = RingerMode.supportedRingerModes;
            return this.mode == i;
        }

        public final int hashCode() {
            Set set = RingerMode.supportedRingerModes;
            return Integer.hashCode(this.mode);
        }

        public final String toString() {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Open(mode=", RingerMode.m992toStringimpl(this.mode), ")");
        }

        private Open(int i) {
            this.mode = i;
        }
    }
}
