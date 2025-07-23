package com.android.systemui.media.mediaoutput.analytics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SaScreen {
    public final String id;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChooseADevice extends SaScreen {
        public static final ChooseADevice INSTANCE = new ChooseADevice();

        private ChooseADevice() {
            super("Mo04", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ChooseADevice);
        }

        public final int hashCode() {
            return -2004507802;
        }

        public final String toString() {
            return "ChooseADevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaOutput extends SaScreen {
        public static final MediaOutput INSTANCE = new MediaOutput();

        private MediaOutput() {
            super("Mo01", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaOutput);
        }

        public final int hashCode() {
            return -950380821;
        }

        public final String toString() {
            return "MediaOutput";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaOutputSettings extends SaScreen {
        public static final MediaOutputSettings INSTANCE = new MediaOutputSettings();

        private MediaOutputSettings() {
            super("Mo02", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaOutputSettings);
        }

        public final int hashCode() {
            return 1054039086;
        }

        public final String toString() {
            return "MediaOutputSettings";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TvCard extends SaScreen {
        public static final TvCard INSTANCE = new TvCard();

        private TvCard() {
            super("Mo05", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TvCard);
        }

        public final int hashCode() {
            return -570386420;
        }

        public final String toString() {
            return "TvCard";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WifiSpeakerPlaybackPrefs extends SaScreen {
        public static final WifiSpeakerPlaybackPrefs INSTANCE = new WifiSpeakerPlaybackPrefs();

        private WifiSpeakerPlaybackPrefs() {
            super("Mo03", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof WifiSpeakerPlaybackPrefs);
        }

        public final int hashCode() {
            return 573917989;
        }

        public final String toString() {
            return "WifiSpeakerPlaybackPrefs";
        }
    }

    public /* synthetic */ SaScreen(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private SaScreen(String str) {
        this.id = str;
    }
}
