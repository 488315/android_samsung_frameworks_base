package com.android.systemui.bixby2.controller.mediacontrol;

import android.media.session.PlaybackState;
import android.os.Bundle;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MoveFromCurrentPositionController extends MediaCommandType {
    public static final Companion Companion = new Companion(null);
    private static final long INVALID_POSITION = -1;
    public static final String NETFLIX_CUSTOM_ACTION = "customActionSeek";
    private static final String NETFLIX_PACKAGE_NAME = "com.netflix.mediaclient";
    public static final String OFFSET = "offset";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final long getPosition() {
        PlaybackState playbackState = MediaCommandType.Companion.getMediaController().getPlaybackState();
        if (playbackState != null) {
            return playbackState.getPosition();
        }
        return -1L;
    }

    private final boolean isNetflixController() {
        return Intrinsics.areEqual(NETFLIX_PACKAGE_NAME, MediaCommandType.Companion.getMediaController().getPackageName());
    }

    private final CommandActionResponse sendCustomAction() {
        Bundle bundle = new Bundle();
        MediaCommandType.Companion companion = MediaCommandType.Companion;
        bundle.putInt(OFFSET, (int) companion.getMediaInfo().time);
        companion.getMediaController().getTransportControls().sendCustomAction(NETFLIX_CUSTOM_ACTION, bundle);
        return new CommandActionResponse(1, "success");
    }

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        if (!isPlayingOrFocused()) {
            return new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_PLAYING);
        }
        if (isNetflixController()) {
            return sendCustomAction();
        }
        long position = getPosition();
        return position != -1 ? seekTo(position + MediaCommandType.Companion.getMediaInfo().time) : new CommandActionResponse(2, ActionResults.RESULT_NO_SUPPORT_FEATURE);
    }
}
