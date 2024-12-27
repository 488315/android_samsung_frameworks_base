package com.android.systemui.volume.panel.component.mediaoutput.domain.model;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public interface MediaControllerChangeModel {

    public final class AudioInfoChanged implements MediaControllerChangeModel {
        public final MediaController.PlaybackInfo info;

        public AudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            this.info = playbackInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof AudioInfoChanged) && Intrinsics.areEqual(this.info, ((AudioInfoChanged) obj).info);
        }

        public final int hashCode() {
            return this.info.hashCode();
        }

        public final String toString() {
            return "AudioInfoChanged(info=" + this.info + ")";
        }
    }

    public final class ExtrasChanged implements MediaControllerChangeModel {
        public final Bundle extras;

        public ExtrasChanged(Bundle bundle) {
            this.extras = bundle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ExtrasChanged) && Intrinsics.areEqual(this.extras, ((ExtrasChanged) obj).extras);
        }

        public final int hashCode() {
            Bundle bundle = this.extras;
            if (bundle == null) {
                return 0;
            }
            return bundle.hashCode();
        }

        public final String toString() {
            return "ExtrasChanged(extras=" + this.extras + ")";
        }
    }

    public final class MetadataChanged implements MediaControllerChangeModel {
        public final MediaMetadata metadata;

        public MetadataChanged(MediaMetadata mediaMetadata) {
            this.metadata = mediaMetadata;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MetadataChanged) && Intrinsics.areEqual(this.metadata, ((MetadataChanged) obj).metadata);
        }

        public final int hashCode() {
            MediaMetadata mediaMetadata = this.metadata;
            if (mediaMetadata == null) {
                return 0;
            }
            return mediaMetadata.hashCode();
        }

        public final String toString() {
            return "MetadataChanged(metadata=" + this.metadata + ")";
        }
    }

    public final class PlaybackStateChanged implements MediaControllerChangeModel {
        public final PlaybackState state;

        public PlaybackStateChanged(PlaybackState playbackState) {
            this.state = playbackState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof PlaybackStateChanged) && Intrinsics.areEqual(this.state, ((PlaybackStateChanged) obj).state);
        }

        public final int hashCode() {
            PlaybackState playbackState = this.state;
            if (playbackState == null) {
                return 0;
            }
            return playbackState.hashCode();
        }

        public final String toString() {
            return "PlaybackStateChanged(state=" + this.state + ")";
        }
    }

    public final class QueueChanged implements MediaControllerChangeModel {
        public final List queue;

        public QueueChanged(List<MediaSession.QueueItem> list) {
            this.queue = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof QueueChanged) && Intrinsics.areEqual(this.queue, ((QueueChanged) obj).queue);
        }

        public final int hashCode() {
            List list = this.queue;
            if (list == null) {
                return 0;
            }
            return list.hashCode();
        }

        public final String toString() {
            return "QueueChanged(queue=" + this.queue + ")";
        }
    }

    public final class QueueTitleChanged implements MediaControllerChangeModel {
        public final CharSequence title;

        public QueueTitleChanged(CharSequence charSequence) {
            this.title = charSequence;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof QueueTitleChanged) && Intrinsics.areEqual(this.title, ((QueueTitleChanged) obj).title);
        }

        public final int hashCode() {
            CharSequence charSequence = this.title;
            if (charSequence == null) {
                return 0;
            }
            return charSequence.hashCode();
        }

        public final String toString() {
            return "QueueTitleChanged(title=" + ((Object) this.title) + ")";
        }
    }

    public final class SessionDestroyed implements MediaControllerChangeModel {
        public static final SessionDestroyed INSTANCE = new SessionDestroyed();

        private SessionDestroyed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SessionDestroyed);
        }

        public final int hashCode() {
            return -1843492958;
        }

        public final String toString() {
            return "SessionDestroyed";
        }
    }

    public final class SessionEvent implements MediaControllerChangeModel {
        public final String event;
        public final Bundle extras;

        public SessionEvent(String str, Bundle bundle) {
            this.event = str;
            this.extras = bundle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SessionEvent)) {
                return false;
            }
            SessionEvent sessionEvent = (SessionEvent) obj;
            return Intrinsics.areEqual(this.event, sessionEvent.event) && Intrinsics.areEqual(this.extras, sessionEvent.extras);
        }

        public final int hashCode() {
            int hashCode = this.event.hashCode() * 31;
            Bundle bundle = this.extras;
            return hashCode + (bundle == null ? 0 : bundle.hashCode());
        }

        public final String toString() {
            return "SessionEvent(event=" + this.event + ", extras=" + this.extras + ")";
        }
    }
}
