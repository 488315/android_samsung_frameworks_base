package com.android.systemui.audio.soundcraft.interfaces.audio;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PlaybackPackageUtils {
    public static final PlaybackPackageUtils INSTANCE = new PlaybackPackageUtils();
    public static final List PLAYBACK_BLOCKED_PACKAGE = CollectionsKt__CollectionsKt.listOf("com.samsung.android.app.soundpicker", "com.baidu.searchbox", "com.android.server.telecom", "com.sds.sdsmeeting", "com.sds.meeting", "com.sds.squaremeeting", "com.sds.proctormeeting", "com.sds.mysinglesquare", "com.sds.teams", "com.sds.mysinglesquare", "com.sds.squaremessenger", "com.samsung.android.bixby.agent", "com.harman.hkconnect", "com.samsung.android.audiomirroring", "com.sec.remotecast", "com.samsung.android.app.interpreter", "com.samsung.wearable.watch7plugin");

    private PlaybackPackageUtils() {
    }
}
