package com.android.systemui.audio.soundcraft.interfaces.audio;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlaybackPackageUtils {
    public static final PlaybackPackageUtils INSTANCE = new PlaybackPackageUtils();
    public static final List PLAYBACK_BLOCKED_PACKAGE = Arrays.asList("com.samsung.android.app.soundpicker", "com.baidu.searchbox", "com.android.server.telecom", "com.sds.sdsmeeting", "com.sds.meeting", "com.sds.squaremeeting", "com.sds.proctormeeting", "com.sds.mysinglesquare", "com.sds.teams", "com.sds.mysinglesquare", "com.sds.squaremessenger", "com.samsung.android.bixby.agent", "com.harman.hkconnect", "com.samsung.android.audiomirroring", "com.sec.remotecast", "com.samsung.android.app.interpreter", "com.samsung.wearable.watch7plugin", "com.sec.hearingadjust");
    public static final ConcurrentHashMap gamePackageHashMap = new ConcurrentHashMap();

    private PlaybackPackageUtils() {
    }
}
