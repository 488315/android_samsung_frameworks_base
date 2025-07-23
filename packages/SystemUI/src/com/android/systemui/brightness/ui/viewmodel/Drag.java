package com.android.systemui.brightness.ui.viewmodel;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.brightness.shared.model.GammaBrightness;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Drag {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Dragging implements Drag {
        public final int brightness;

        private /* synthetic */ Dragging(int i) {
            this.brightness = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Dragging m1070boximpl(int i) {
            return new Dragging(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Dragging) {
                return this.brightness == ((Dragging) obj).brightness;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.brightness);
        }

        public final String toString() {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Dragging(brightness=", GammaBrightness.m1065toStringimpl(this.brightness), ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Stopped implements Drag {
        public final int brightness;

        private /* synthetic */ Stopped(int i) {
            this.brightness = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Stopped m1071boximpl(int i) {
            return new Stopped(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Stopped) {
                return this.brightness == ((Stopped) obj).brightness;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.brightness);
        }

        public final String toString() {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Stopped(brightness=", GammaBrightness.m1065toStringimpl(this.brightness), ")");
        }
    }
}
