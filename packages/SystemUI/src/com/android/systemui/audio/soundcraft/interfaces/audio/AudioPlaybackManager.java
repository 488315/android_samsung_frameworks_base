package com.android.systemui.audio.soundcraft.interfaces.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.utils.PackageExt;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.samsung.android.game.SemGameManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Result;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioPlaybackManager {
    public final Context context;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AudioPlaybackManager(Context context) {
        this.context = context;
    }

    public static boolean isValidPlayback(AudioPlaybackConfiguration audioPlaybackConfiguration, String str) {
        boolean z;
        ConcurrentHashMap concurrentHashMap;
        boolean z2;
        if (AudioAttributes.toLegacyStreamType(audioPlaybackConfiguration.getAudioAttributes()) != 3 || audioPlaybackConfiguration.semGetPlayerState() != 2) {
            return false;
        }
        PlaybackPackageUtils.INSTANCE.getClass();
        if (PlaybackPackageUtils.PLAYBACK_BLOCKED_PACKAGE.contains(str)) {
            return false;
        }
        SessionController.Companion.getClass();
        if (SessionController.Companion.BLUETOOTH_MEDIA_SESSION_PACKAGE.contains(str)) {
            return false;
        }
        try {
            int i = Result.$r8$clinit;
            concurrentHashMap = PlaybackPackageUtils.gamePackageHashMap;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(new Result.Failure(th));
            if (m3422exceptionOrNullimpl != null) {
                m3422exceptionOrNullimpl.printStackTrace();
            }
        }
        if (concurrentHashMap.containsKey(str)) {
            Boolean bool = (Boolean) concurrentHashMap.get(str);
            if (bool != null) {
                z = bool.booleanValue();
            }
            z = false;
        } else {
            if (SemGameManager.isAvailable()) {
                if (!SemGameManager.isGamePackage(str) && !StringsKt__StringsKt.contains(str, "dolbygametest", false)) {
                    z2 = false;
                    concurrentHashMap.put(str, Boolean.valueOf(z2));
                    Log.d("SoundCraft.PlaybackPackageUtils", str + " set to game package " + z2);
                    z = z2;
                }
                z2 = true;
                concurrentHashMap.put(str, Boolean.valueOf(z2));
                Log.d("SoundCraft.PlaybackPackageUtils", str + " set to game package " + z2);
                z = z2;
            }
            z = false;
        }
        return !z;
    }

    public final int findValidPlaybackUid(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
            int semGetClientUid = audioPlaybackConfiguration.semGetClientUid();
            if (semGetClientUid > 1000 && semGetClientUid != 1002) {
                PackageExt packageExt = PackageExt.INSTANCE;
                Context context = this.context;
                int semGetClientUid2 = audioPlaybackConfiguration.semGetClientUid();
                packageExt.getClass();
                String packageNameForUid = PackageExt.getPackageNameForUid(semGetClientUid2, context);
                if (packageNameForUid != null && isValidPlayback(audioPlaybackConfiguration, packageNameForUid)) {
                    return audioPlaybackConfiguration.semGetClientUid();
                }
            }
        }
        return -1;
    }

    public final String getPlayingAppPackage() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Object systemService = context.getSystemService((Class<Object>) AudioManager.class);
        systemService.getClass();
        for (AudioPlaybackConfiguration audioPlaybackConfiguration : ((AudioManager) systemService).getActivePlaybackConfigurations()) {
            int semGetClientUid = audioPlaybackConfiguration.semGetClientUid();
            if (semGetClientUid > 1000 && semGetClientUid != 1002) {
                PackageExt packageExt = PackageExt.INSTANCE;
                Context context2 = this.context;
                int semGetClientUid2 = audioPlaybackConfiguration.semGetClientUid();
                packageExt.getClass();
                String packageNameForUid = PackageExt.getPackageNameForUid(semGetClientUid2, context2);
                if (packageNameForUid != null && isValidPlayback(audioPlaybackConfiguration, packageNameForUid)) {
                    KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(audioPlaybackConfiguration.semGetClientUid(), "uid=", ", packageName=", packageNameForUid, "SoundCraft.AudioPlaybackManager");
                    return packageNameForUid;
                }
            }
        }
        return null;
    }
}
