package com.android.systemui.qs.bar.soundcraft.interfaces.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.qs.bar.soundcraft.utils.PackageExt;
import com.android.systemui.qs.bar.soundcraft.utils.SystemServiceExtension;
import java.util.Iterator;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioPlaybackManager {
    public final Context context;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        Object failure;
        SystemServiceExtension.INSTANCE.getClass();
        Context context = this.context;
        Iterator<T> it = ((AudioManager) context.getSystemService(AudioManager.class)).getActivePlaybackConfigurations().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((AudioPlaybackConfiguration) obj).semGetPlayerState() == 2) {
                break;
            }
        }
        AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
        if (audioPlaybackConfiguration != null) {
            PackageExt packageExt = PackageExt.INSTANCE;
            int semGetClientUid = audioPlaybackConfiguration.semGetClientUid();
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("uid=", semGetClientUid, "SoundCraft.AudioPlaybackManager");
            Unit unit = Unit.INSTANCE;
            packageExt.getClass();
            try {
                int i = Result.$r8$clinit;
                Object[] packagesForUid = context.getPackageManager().getPackagesForUid(semGetClientUid);
                failure = packagesForUid != null ? packagesForUid[0] : null;
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            String str = (String) failure;
            AbstractC0000x2c234b15.m3m("packageName=", str, "SoundCraft.AudioPlaybackManager");
            if (str != null) {
                return str;
            }
        }
        Log.d("SoundCraft.AudioPlaybackManager", "package name is null");
        return null;
    }
}
