package com.android.systemui.audio.soundcraft.interfaces.audio;

import android.content.Context;
import android.media.AudioPlaybackConfiguration;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.utils.PackageExt;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.samsung.android.game.SemGameManager;
import java.util.Iterator;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

public final class AudioPlaybackManager {
    public final Context context;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AudioPlaybackManager(Context context) {
        this.context = context;
    }

    public final String getPlayingAppPackage() {
        Object obj;
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Iterator<T> it = SystemServiceExtension.getAudioManager(context).getActivePlaybackConfigurations().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
            if (audioPlaybackConfiguration.semGetPlayerState() == 2) {
                PackageExt packageExt = PackageExt.INSTANCE;
                Context context2 = this.context;
                int semGetClientUid = audioPlaybackConfiguration.semGetClientUid();
                packageExt.getClass();
                String packageNameForUid = PackageExt.getPackageNameForUid(semGetClientUid, context2);
                if (packageNameForUid != null) {
                    PlaybackPackageUtils.INSTANCE.getClass();
                    if (PlaybackPackageUtils.PLAYBACK_BLOCKED_PACKAGE.contains(packageNameForUid)) {
                        continue;
                    } else {
                        String packageNameForUid2 = PackageExt.getPackageNameForUid(audioPlaybackConfiguration.semGetClientUid(), this.context);
                        if (packageNameForUid2 != null) {
                            SessionController.Companion.getClass();
                            if (SessionController.BLUETOOTH_MEDIA_SESSION_PACKAGE.contains(packageNameForUid2)) {
                                continue;
                            } else {
                                String packageNameForUid3 = PackageExt.getPackageNameForUid(audioPlaybackConfiguration.semGetClientUid(), this.context);
                                if (packageNameForUid3 != null) {
                                    try {
                                        int i = Result.$r8$clinit;
                                        if (SemGameManager.isAvailable() && SemGameManager.isGamePackage(packageNameForUid3)) {
                                        }
                                        if (!StringsKt__StringsKt.contains(packageNameForUid3, "dolbygametest", false)) {
                                            break;
                                        }
                                    } catch (Throwable th) {
                                        int i2 = Result.$r8$clinit;
                                        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
                                        if (m2527exceptionOrNullimpl != null) {
                                            m2527exceptionOrNullimpl.printStackTrace();
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) obj;
        if (audioPlaybackConfiguration2 != null) {
            PackageExt packageExt2 = PackageExt.INSTANCE;
            Context context3 = this.context;
            int semGetClientUid2 = audioPlaybackConfiguration2.semGetClientUid();
            ListPopupWindow$$ExternalSyntheticOutline0.m(semGetClientUid2, "uid=", "SoundCraft.AudioPlaybackManager");
            Unit unit = Unit.INSTANCE;
            packageExt2.getClass();
            String packageNameForUid4 = PackageExt.getPackageNameForUid(semGetClientUid2, context3);
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("packageName=", packageNameForUid4, "SoundCraft.AudioPlaybackManager");
            if (packageNameForUid4 != null) {
                return packageNameForUid4;
            }
        }
        Log.d("SoundCraft.AudioPlaybackManager", "package name is null");
        return null;
    }
}
