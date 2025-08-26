package com.android.systemui.media.mediaoutput.controller.media;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.view.KeyEvent;
import com.android.systemui.media.mediaoutput.entity.EntityString;
import java.util.Arrays;
import java.util.List;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public interface SessionController extends EntityString {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final List BLUETOOTH_MEDIA_SESSION_PACKAGE = Arrays.asList("com.android.bluetooth", "com.android.bluetooth.services");
        public static final List MEDIA_SESSION_BLOCKED_LIST = Arrays.asList("com.sec.android.app.sbrowser", "com.android.chrome", "com.nhn.android.search", "com.samsung.android.app.soundpicker", "com.baidu.searchbox", "com.uplus.onphone", "com.uplus.musicshow", "com.uplus.baseballhdtv", "com.uplus.ugolf", "de.telekom.t_online_de", "com.sec.android.gallery3d", "com.android.server.telecom", "com.sds.sdsmeeting", "com.sds.meeting", "com.sds.squaremeeting", "com.sds.proctormeeting", "com.sds.mysinglesquare", "com.google.android.apps.tachyon", "com.whatsapp", "com.google.android.talk", "org.telegram.messenger", "jp.naver.line.android", "com.facebook.katana", "com.kakao.talk", "com.google.android.apps.meetings", "us.zoom.videomeetings", "com.microsoft.teams", "com.sds.teams", "com.sds.mysinglesquare", "com.sds.squaremessenger", "com.sec.android.app.vepreload", "com.google.android.apps.photos", "com.samsung.android.bixby.agent", "com.harman.hkconnect", "com.samsung.android.audiomirroring", "com.sec.remotecast", "com.samsung.android.app.interpreter", "com.samsung.wearable.watch7plugin", "kr.co.captv.pooqV2", "ai.perplexity.app.android");
        public static final List LAUNCH_BLOCKED_LIST = Arrays.asList("com.google.android.videos", "com.samsung.android.video", "org.videolan.vlc", "com.gretech.gomplayerko", "com.mxtech.videoplayer.ad", "com.samsung.android.app.soundpicker", "com.baidu.searchbox", "com.samsung.android.bixby.agent", "com.uplus.onphone", "com.uplus.musicshow");
        public static final List RECENT_BLOCKED_LIST = Arrays.asList("com.google.android.youtube", "com.google.android.videos", "com.google.android.apps.youtube.music", "com.samsung.android.app.soundpicker", "com.baidu.searchbox", "vkr.co.millie.millieshelf", "mp3.music.download.player.music.search", "kr.co.kbs.kong");

        private Companion() {
        }

        public static void dispatchMediaButtonEvent(MediaController mediaController, int i) {
            mediaController.dispatchMediaButtonEvent(new KeyEvent(0, i));
            mediaController.dispatchMediaButtonEvent(new KeyEvent(1, i));
        }
    }

    void close();

    void execute(long j, long j2);

    Flow getActionsFlow();

    Flow getAppColorSchemeFlow();

    Flow getAppIconFlow();

    String getAppName();

    Flow getArtistFlow();

    default FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 getColorScheme() {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(getThumbColorSchemeFlow(), getAppColorSchemeFlow(), new SessionController$colorScheme$1(null));
    }

    default FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 getCurrentPosition() {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(getDurationFlow(), getPositionFlow(), new SessionController$currentPosition$1(null));
    }

    Flow getDurationFlow();

    String getId();

    Flow getMediaActionsFlow();

    String getPackageName();

    Flow getPlaybackStateFlow();

    Flow getPositionFlow();

    Flow getThumbColorSchemeFlow();

    Flow getThumbnailFlow();

    ReadonlyStateFlow getTitleFlow();

    default boolean isClosed() {
        return false;
    }

    default boolean isError() {
        return false;
    }

    default boolean isPlaying() {
        return false;
    }

    default boolean isSameToken(MediaSession.Token token) {
        return false;
    }

    default boolean isSupportAction(long j) {
        return false;
    }

    default void run() {
    }

    default void stop() {
    }
}
