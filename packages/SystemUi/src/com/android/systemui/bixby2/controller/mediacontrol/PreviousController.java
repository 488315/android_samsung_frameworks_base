package com.android.systemui.bixby2.controller.mediacontrol;

import android.media.session.PlaybackState;
import android.util.Log;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PreviousController extends MediaCommandType {
    private static final long LIMIT_PREV_MOVE = 5000;
    private static final String TAG = "MediaCommand.PreviousController";
    public static final Companion Companion = new Companion(null);
    private static final String[] CHINESE_APP_LIST = {"com.kugou.android", "com.netease.cloudmusic", "com.ximalaya.ting.android", "fm.xiami.main", "com.tencent.qqmusic", "fm.qingting.qtradio", "cn.kuwo.player", "cmccwm.mobilemusic"};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final boolean isChineseApp(String str) {
        return ArraysKt___ArraysKt.contains(CHINESE_APP_LIST, str);
    }

    private final boolean isPositionWithIn5sec() {
        PlaybackState playbackState = MediaCommandType.Companion.getMediaController().getPlaybackState();
        if ((playbackState != null ? playbackState.getPosition() : 0L) < LIMIT_PREV_MOVE) {
            return true;
        }
        Log.d(TAG, "position has passed 5 seconds.");
        return false;
    }

    private final boolean shouldSendPreviousEventOnce() {
        return isChineseApp(MediaCommandType.Companion.getMediaController().getPackageName()) || isPositionWithIn5sec();
    }

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        if (!isMusicAvailable()) {
            return new CommandActionResponse(2, ActionResults.RESULT_NO_MEDIA_EXISTS);
        }
        if (shouldSendPreviousEventOnce()) {
            sendMediaKeyEvent(88);
        } else {
            sendMediaKeyEvent(88);
            sendMediaKeyEvent(88);
        }
        return new CommandActionResponse(1, "success");
    }
}
