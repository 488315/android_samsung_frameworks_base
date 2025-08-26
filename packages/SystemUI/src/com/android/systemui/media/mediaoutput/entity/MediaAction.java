package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.ext.DrawableResourceConverterPainter;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.action.FastForwardKt;
import com.android.systemui.media.mediaoutput.icons.action.FastRewindKt;
import com.android.systemui.media.mediaoutput.icons.action.NoPlayKt;
import com.android.systemui.media.mediaoutput.icons.action.PauseKt;
import com.android.systemui.media.mediaoutput.icons.action.PlayKt;
import com.android.systemui.media.mediaoutput.icons.action.SkipNextKt;
import com.android.systemui.media.mediaoutput.icons.action.SkipPreviousKt;
import java.util.Collections;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaAction implements EntityString {
    public static final Companion Companion = new Companion(null);
    public static final MediaAction buffering;
    public static final MediaAction forward;
    public static final MediaAction next;
    public static final MediaAction noPlay;
    public static final MediaAction pause;
    public static final MediaAction play;
    public static final MediaAction previous;
    public static final MediaAction rewind;
    public final CharSequence description;
    public final boolean enabled;
    public final Painter icon;
    public final long id;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons.Action action = Icons.Action.INSTANCE;
        ImageVector imageVector = (ImageVector) FastRewindKt.FastRewind$delegate.getValue();
        companion.getClass();
        rewind = new MediaAction(8L, ImageVectorConverterPainter.Companion.toConverter(imageVector), new ResourceString(R.string.keyboard_key_media_rewind, null, 2, null), false, 8, null);
        previous = new MediaAction(16L, ImageVectorConverterPainter.Companion.toConverter((ImageVector) SkipPreviousKt.SkipPrevious$delegate.getValue()), new ResourceString(R.string.keyboard_key_media_previous, null, 2, null), false, 8, null);
        MediaAction mediaAction = new MediaAction(4L, ImageVectorConverterPainter.Companion.toConverter((ImageVector) PlayKt.Play$delegate.getValue()), new ResourceString(R.string.controls_media_button_play, null, 2, null), false, 8, null);
        play = mediaAction;
        pause = new MediaAction(2L, ImageVectorConverterPainter.Companion.toConverter((ImageVector) PauseKt.Pause$delegate.getValue()), new ResourceString(R.string.controls_media_button_pause, null, 2, null), false, 8, null);
        next = new MediaAction(32L, ImageVectorConverterPainter.Companion.toConverter((ImageVector) SkipNextKt.SkipNext$delegate.getValue()), new ResourceString(R.string.keyboard_key_media_next, null, 2, null), false, 8, null);
        forward = new MediaAction(64L, ImageVectorConverterPainter.Companion.toConverter((ImageVector) FastForwardKt.FastForward$delegate.getValue()), new ResourceString(R.string.keyboard_key_media_fast_forward, null, 2, null), false, 8, null);
        noPlay = copy$default(mediaAction, ImageVectorConverterPainter.Companion.toConverter((ImageVector) NoPlayKt.NoPlay$delegate.getValue()), false, 5);
        DrawableResourceConverterPainter.Companion.getClass();
        buffering = new MediaAction(-1L, new DrawableResourceConverterPainter(0, null), null, false, 12, null);
    }

    public MediaAction(long j, Painter painter, CharSequence charSequence, boolean z) {
        this.id = j;
        this.icon = painter;
        this.description = charSequence;
        this.enabled = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v3, types: [androidx.compose.ui.graphics.painter.Painter] */
    public static MediaAction copy$default(MediaAction mediaAction, ImageVectorConverterPainter imageVectorConverterPainter, boolean z, int i) {
        long j = (i & 1) != 0 ? mediaAction.id : -4L;
        ImageVectorConverterPainter imageVectorConverterPainter2 = imageVectorConverterPainter;
        if ((i & 2) != 0) {
            imageVectorConverterPainter2 = mediaAction.icon;
        }
        ImageVectorConverterPainter imageVectorConverterPainter3 = imageVectorConverterPainter2;
        CharSequence charSequence = mediaAction.description;
        if ((i & 8) != 0) {
            z = mediaAction.enabled;
        }
        mediaAction.getClass();
        return new MediaAction(j, imageVectorConverterPainter3, charSequence, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaAction)) {
            return false;
        }
        MediaAction mediaAction = (MediaAction) obj;
        return this.id == mediaAction.id && Intrinsics.areEqual(this.icon, mediaAction.icon) && Intrinsics.areEqual(this.description, mediaAction.description) && this.enabled == mediaAction.enabled;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return Collections.singletonList(new Pair("id", Long.valueOf(this.id)));
    }

    public final int hashCode() {
        return Boolean.hashCode(this.enabled) + ControlInfo$$ExternalSyntheticOutline0.m(AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.icon, Long.hashCode(this.id) * 31, 31), 31, this.description);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ MediaAction(long j, Painter painter, String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, painter, (i & 4) != 0 ? "" : str, (i & 8) != 0 ? true : z);
    }
}
