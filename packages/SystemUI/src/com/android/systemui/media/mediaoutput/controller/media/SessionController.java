package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.util.Log;
import android.view.KeyEvent;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SessionController {
    public final MutableLiveData _mediaInfo;
    public final Context context;
    public final ContextScope coroutineScope;
    public boolean isClosed;
    public boolean isGrayscaleThumbnail;
    public final MutableLiveData mediaInfo;
    public final MediaSession.Token token;
    public static final Companion Companion = new Companion(null);
    public static final List BLUETOOTH_MEDIA_SESSION_PACKAGE = CollectionsKt__CollectionsKt.listOf("com.android.bluetooth", "com.android.bluetooth.services");
    public static final List MEDIA_SESSION_BLOCKED_LIST = CollectionsKt__CollectionsKt.listOf("com.sec.android.app.sbrowser", "com.android.chrome", "com.nhn.android.search", "com.samsung.android.app.soundpicker", "com.baidu.searchbox", "com.uplus.onphone", "com.uplus.musicshow", "com.uplus.baseballhdtv", "com.uplus.ugolf", "de.telekom.t_online_de", "com.sec.android.gallery3d", "com.android.server.telecom", "com.sds.sdsmeeting", "com.sds.meeting", "com.sds.squaremeeting", "com.sds.proctormeeting", "com.sds.mysinglesquare", "com.google.android.apps.tachyon", "com.whatsapp", "com.google.android.talk", "org.telegram.messenger", "jp.naver.line.android", "com.facebook.katana", "com.kakao.talk", "com.google.android.apps.meetings", "us.zoom.videomeetings", "com.microsoft.teams", "com.sds.teams", "com.sds.mysinglesquare", "com.sds.squaremessenger", "com.sec.android.app.vepreload", "com.google.android.apps.photos", "com.samsung.android.bixby.agent", "com.harman.hkconnect", "com.samsung.android.audiomirroring", "com.sec.remotecast", "com.samsung.android.app.interpreter", "com.samsung.wearable.watch7plugin", "kr.co.captv.pooqV2");
    public static final List LAUNCH_BLOCKED_LIST = CollectionsKt__CollectionsKt.listOf("com.google.android.videos", "com.samsung.android.video", "org.videolan.vlc", "com.gretech.gomplayerko", "com.mxtech.videoplayer.ad", "com.samsung.android.app.soundpicker", "com.baidu.searchbox", "com.samsung.android.bixby.agent", "com.uplus.onphone", "com.uplus.musicshow");
    public static final List RECENT_BLOCKED_LIST = CollectionsKt__CollectionsKt.listOf("com.google.android.youtube", "com.google.android.videos", "com.google.android.apps.youtube.music", "com.samsung.android.app.soundpicker", "com.baidu.searchbox", "vkr.co.millie.millieshelf", "mp3.music.download.player.music.search", "kr.co.kbs.kong");

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static void dispatchMediaButtonEvent(MediaController mediaController, int i) {
            mediaController.dispatchMediaButtonEvent(new KeyEvent(0, i));
            mediaController.dispatchMediaButtonEvent(new KeyEvent(1, i));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SessionController(Context context, MediaSession.Token token) {
        this.context = context;
        this.token = token;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.IO);
        MutableLiveData mutableLiveData = new MutableLiveData();
        this._mediaInfo = mutableLiveData;
        this.mediaInfo = mutableLiveData;
    }

    public abstract void execute(long j, long j2);

    public abstract String getAppName();

    public abstract String getPackageName();

    public final void update(MediaInfo mediaInfo) {
        Log.d("SessionController", "update() - " + mediaInfo);
        this._mediaInfo.postValue(mediaInfo);
    }

    public /* synthetic */ SessionController(Context context, MediaSession.Token token, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : token);
    }

    public void close() {
    }
}
