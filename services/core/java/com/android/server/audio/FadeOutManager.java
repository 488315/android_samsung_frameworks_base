package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.AudioPlaybackConfiguration;
import android.media.FadeManagerConfiguration;
import android.media.VolumeShaper;
import android.media.audiopolicy.Flags;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class FadeOutManager {
    public final Object mLock = new Object();
    public final SparseArray mUidToFadedAppsMap = new SparseArray();
    public final FadeConfigurations mFadeConfigurations = new FadeConfigurations();

    public final class FadedOutApp {
        public static final VolumeShaper.Operation PLAY_CREATE_IF_NEEDED;
        public static final VolumeShaper.Operation PLAY_SKIP_RAMP;
        public final SparseArray mFadedPlayers = new SparseArray();
        public final int mUid;

        static {
            VolumeShaper.Operation build =
                    new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY)
                            .createIfNeeded()
                            .build();
            PLAY_CREATE_IF_NEEDED = build;
            PLAY_SKIP_RAMP = new VolumeShaper.Operation.Builder(build).setXOffset(1.0f).build();
        }

        public FadedOutApp(int i) {
            this.mUid = i;
        }

        public static void logFadeEvent(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                int i,
                VolumeShaper.Configuration configuration,
                VolumeShaper.Operation operation,
                boolean z,
                String str) {
            if (str.equals("fading out")) {
                EventLogger eventLogger = PlaybackActivityMonitor.sEventLogger;
                PlaybackActivityMonitor.FadeInEvent fadeInEvent =
                        new PlaybackActivityMonitor.FadeInEvent(
                                audioPlaybackConfiguration, z, configuration, operation, 1);
                fadeInEvent.printLog(0, "AS.FadeOutManager");
                eventLogger.enqueue(fadeInEvent);
                return;
            }
            if (str.equals("fading in")) {
                EventLogger eventLogger2 = PlaybackActivityMonitor.sEventLogger;
                PlaybackActivityMonitor.FadeInEvent fadeInEvent2 =
                        new PlaybackActivityMonitor.FadeInEvent(
                                audioPlaybackConfiguration, z, configuration, operation, 0);
                fadeInEvent2.printLog(0, "AS.FadeOutManager");
                eventLogger2.enqueue(fadeInEvent2);
                return;
            }
            EventLogger eventLogger3 = PlaybackActivityMonitor.sEventLogger;
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent(
                            VpnManagerService$$ExternalSyntheticOutline0.m(i, str, " piid:"));
            stringEvent.printLog(0, "AS.FadeOutManager");
            eventLogger3.enqueue(stringEvent);
        }

        public final void addFade(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                boolean z,
                VolumeShaper.Configuration configuration) {
            int playerInterfaceId = audioPlaybackConfiguration.getPlayerInterfaceId();
            if (this.mFadedPlayers.indexOfKey(playerInterfaceId) >= 0) {
                Slog.v(
                        "AS.FadeOutManager",
                        "player piid:" + playerInterfaceId + " already faded out");
                return;
            }
            if (audioPlaybackConfiguration.getPlayerProxy() == null) {
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                playerInterfaceId,
                                "Error fading out player piid:",
                                ", player not found for uid "),
                        this.mUid,
                        "AS.FadeOutManager");
            } else {
                applyVolumeShaperInternal(
                        audioPlaybackConfiguration,
                        playerInterfaceId,
                        configuration,
                        z ? PLAY_SKIP_RAMP : PLAY_CREATE_IF_NEEDED,
                        z,
                        "fading out");
                this.mFadedPlayers.put(playerInterfaceId, configuration);
            }
        }

        public final void applyVolumeShaperInternal(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                int i,
                VolumeShaper.Configuration configuration,
                VolumeShaper.Operation operation,
                boolean z,
                String str) {
            VolumeShaper.Configuration configuration2 =
                    operation.equals(VolumeShaper.Operation.REVERSE)
                            ? (VolumeShaper.Configuration) this.mFadedPlayers.get(i)
                            : configuration;
            try {
                logFadeEvent(audioPlaybackConfiguration, i, configuration, operation, z, str);
                audioPlaybackConfiguration
                        .getPlayerProxy()
                        .applyVolumeShaper(configuration2, operation);
            } catch (Exception e) {
                StringBuilder m =
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i, "Error ", str, " piid:", " uid:");
                m.append(this.mUid);
                Slog.e("AS.FadeOutManager", m.toString(), e);
            }
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.print("\t uid:" + this.mUid + " piids:");
            for (int i = 0; i < this.mFadedPlayers.size(); i++) {
                printWriter.print(
                        "piid: "
                                + this.mFadedPlayers.keyAt(i)
                                + " Volume shaper: "
                                + this.mFadedPlayers.valueAt(i));
            }
            printWriter.println("");
        }

        public final void fadeInPlayer(
                AudioPlaybackConfiguration audioPlaybackConfiguration,
                VolumeShaper.Configuration configuration) {
            int playerInterfaceId = audioPlaybackConfiguration.getPlayerInterfaceId();
            boolean contains = this.mFadedPlayers.contains(playerInterfaceId);
            int i = this.mUid;
            if (!contains) {
                Slog.v(
                        "AS.FadeOutManager",
                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                playerInterfaceId,
                                i,
                                "Player (piid: ",
                                ") for uid (",
                                ") is not faded out, no need to fade in"));
                return;
            }
            VolumeShaper.Operation operation = VolumeShaper.Operation.REVERSE;
            if (configuration != null) {
                operation =
                        new VolumeShaper.Operation.Builder()
                                .replace(
                                        ((VolumeShaper.Configuration)
                                                        this.mFadedPlayers.get(playerInterfaceId))
                                                .getId(),
                                        true)
                                .build();
            }
            VolumeShaper.Operation operation2 = operation;
            this.mFadedPlayers.remove(playerInterfaceId);
            if (audioPlaybackConfiguration.getPlayerProxy() != null) {
                applyVolumeShaperInternal(
                        audioPlaybackConfiguration,
                        playerInterfaceId,
                        configuration,
                        operation2,
                        false,
                        "fading in");
                return;
            }
            Slog.v(
                    "AS.FadeOutManager",
                    "Error fading in player piid:"
                            + playerInterfaceId
                            + ", player not found for uid "
                            + i);
        }

        public final void removeUnfadeAll(Map map) {
            for (int i = 0; i < this.mFadedPlayers.size(); i++) {
                int keyAt = this.mFadedPlayers.keyAt(i);
                AudioPlaybackConfiguration audioPlaybackConfiguration =
                        (AudioPlaybackConfiguration) map.get(Integer.valueOf(keyAt));
                if (audioPlaybackConfiguration == null
                        || audioPlaybackConfiguration.getPlayerProxy() == null) {
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    keyAt,
                                    "Error unfading out player piid:",
                                    ", player not found for uid "),
                            this.mUid,
                            "AS.FadeOutManager");
                } else {
                    applyVolumeShaperInternal(
                            audioPlaybackConfiguration,
                            keyAt,
                            null,
                            VolumeShaper.Operation.REVERSE,
                            false,
                            "fading in");
                }
            }
            this.mFadedPlayers.clear();
        }
    }

    public final void checkFade(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        Slog.v(
                "AS.FadeOutManager",
                "checkFade() player piid:"
                        + audioPlaybackConfiguration.getPlayerInterfaceId()
                        + " uid:"
                        + audioPlaybackConfiguration.getClientUid());
        synchronized (this.mLock) {
            try {
                VolumeShaper.Configuration fadeOutVolumeShaperConfig =
                        this.mFadeConfigurations.getFadeOutVolumeShaperConfig(
                                audioPlaybackConfiguration.getAudioAttributes());
                FadedOutApp fadedOutApp =
                        (FadedOutApp)
                                this.mUidToFadedAppsMap.get(
                                        audioPlaybackConfiguration.getClientUid());
                if (fadedOutApp == null) {
                    return;
                }
                fadedOutApp.addFade(audioPlaybackConfiguration, true, fadeOutVolumeShaperConfig);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void fadeOutUid(int i, List list) {
        HermesService$3$$ExternalSyntheticOutline0.m(i, "fadeOutUid() uid:", "AS.FadeOutManager");
        synchronized (this.mLock) {
            try {
                if (!this.mUidToFadedAppsMap.contains(i)) {
                    this.mUidToFadedAppsMap.put(i, new FadedOutApp(i));
                }
                FadedOutApp fadedOutApp = (FadedOutApp) this.mUidToFadedAppsMap.get(i);
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) it.next();
                    fadedOutApp.addFade(
                            audioPlaybackConfiguration,
                            false,
                            this.mFadeConfigurations.getFadeOutVolumeShaperConfig(
                                    audioPlaybackConfiguration.getAudioAttributes()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeReleased(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        int clientUid = audioPlaybackConfiguration.getClientUid();
        Slog.v(
                "AS.FadeOutManager",
                "removedReleased() player piid: "
                        + audioPlaybackConfiguration.getPlayerInterfaceId()
                        + " uid:"
                        + clientUid);
        synchronized (this.mLock) {
            try {
                FadedOutApp fadedOutApp = (FadedOutApp) this.mUidToFadedAppsMap.get(clientUid);
                if (fadedOutApp == null) {
                    return;
                }
                fadedOutApp.mFadedPlayers.delete(audioPlaybackConfiguration.getPlayerInterfaceId());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unfadeOutUid(int i, Map map) {
        VolumeShaper.Configuration fadeInVolumeShaperConfigForUsage;
        HermesService$3$$ExternalSyntheticOutline0.m(i, "unfadeOutUid() uid:", "AS.FadeOutManager");
        synchronized (this.mLock) {
            try {
                FadedOutApp fadedOutApp = (FadedOutApp) this.mUidToFadedAppsMap.get(i);
                if (fadedOutApp == null) {
                    return;
                }
                this.mUidToFadedAppsMap.remove(i);
                if (!Flags.enableFadeManagerConfiguration()) {
                    fadedOutApp.removeUnfadeAll(map);
                    return;
                }
                ArrayList arrayList = new ArrayList(map.values());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration =
                            (AudioPlaybackConfiguration) arrayList.get(i2);
                    FadeConfigurations fadeConfigurations = this.mFadeConfigurations;
                    AudioAttributes audioAttributes =
                            audioPlaybackConfiguration.getAudioAttributes();
                    fadeConfigurations.getClass();
                    if (Flags.enableFadeManagerConfiguration()) {
                        synchronized (fadeConfigurations.mLock) {
                            try {
                                FadeManagerConfiguration updatedFadeManagerConfigLocked =
                                        fadeConfigurations.getUpdatedFadeManagerConfigLocked();
                                VolumeShaper.Configuration
                                        fadeInVolumeShaperConfigForAudioAttributes =
                                                updatedFadeManagerConfigLocked
                                                        .getFadeInVolumeShaperConfigForAudioAttributes(
                                                                audioAttributes);
                                fadeInVolumeShaperConfigForUsage =
                                        fadeInVolumeShaperConfigForAudioAttributes != null
                                                ? fadeInVolumeShaperConfigForAudioAttributes
                                                : updatedFadeManagerConfigLocked
                                                        .getFadeInVolumeShaperConfigForUsage(
                                                                audioAttributes.getSystemUsage());
                            } finally {
                            }
                        }
                    } else {
                        fadeInVolumeShaperConfigForUsage = null;
                    }
                    fadedOutApp.fadeInPlayer(
                            audioPlaybackConfiguration, fadeInVolumeShaperConfigForUsage);
                }
                if (fadedOutApp.mFadedPlayers.size() > 0) {
                    Slog.v(
                            "AS.FadeOutManager",
                            "Non empty faded players list being cleared! Faded out players:"
                                    + fadedOutApp.mFadedPlayers);
                }
                fadedOutApp.mFadedPlayers.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
