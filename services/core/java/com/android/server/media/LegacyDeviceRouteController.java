package com.android.server.media;

import android.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRoutesInfo;
import android.media.IAudioRoutesObserver;
import android.media.IAudioService;
import android.media.MediaRoute2Info;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.media.flags.Flags;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LegacyDeviceRouteController implements DeviceRouteController {
    public final AudioManager mAudioManager;
    public final int mBuiltInSpeakerSuitabilityStatus;
    public final Context mContext;
    public MediaRoute2Info mDeviceRoute;
    public int mDeviceVolume;
    public final SystemMediaRoute2Provider$$ExternalSyntheticLambda2 mOnDeviceRouteChangedListener;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioRoutesObserver extends IAudioRoutesObserver.Stub {
        public AudioRoutesObserver() {
        }

        public final void dispatchAudioRoutesChanged(AudioRoutesInfo audioRoutesInfo) {
            LegacyDeviceRouteController legacyDeviceRouteController;
            MediaRoute2Info createRouteFromAudioInfo = LegacyDeviceRouteController.this.createRouteFromAudioInfo(audioRoutesInfo);
            synchronized (LegacyDeviceRouteController.this) {
                legacyDeviceRouteController = LegacyDeviceRouteController.this;
                legacyDeviceRouteController.mDeviceRoute = createRouteFromAudioInfo;
            }
            SystemMediaRoute2Provider systemMediaRoute2Provider = legacyDeviceRouteController.mOnDeviceRouteChangedListener.f$0;
            systemMediaRoute2Provider.mHandler.post(new SystemMediaRoute2Provider$$ExternalSyntheticLambda1(systemMediaRoute2Provider, 2));
        }
    }

    public LegacyDeviceRouteController(Context context, AudioManager audioManager, IAudioService iAudioService, SystemMediaRoute2Provider$$ExternalSyntheticLambda2 systemMediaRoute2Provider$$ExternalSyntheticLambda2) {
        AudioRoutesInfo audioRoutesInfo;
        int integer;
        AudioRoutesObserver audioRoutesObserver = new AudioRoutesObserver();
        Objects.requireNonNull(context);
        Objects.requireNonNull(audioManager);
        Objects.requireNonNull(iAudioService);
        this.mContext = context;
        this.mOnDeviceRouteChangedListener = systemMediaRoute2Provider$$ExternalSyntheticLambda2;
        this.mAudioManager = audioManager;
        int i = 0;
        if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses() && ((integer = context.getResources().getInteger(R.integer.config_ntpPollingIntervalShorter)) == 0 || integer == 1 || integer == 2)) {
            i = integer;
        }
        this.mBuiltInSpeakerSuitabilityStatus = i;
        try {
            audioRoutesInfo = iAudioService.startWatchingRoutes(audioRoutesObserver);
        } catch (RemoteException e) {
            Slog.w("LDeviceRouteController", "Cannot connect to audio service to start listen to routes", e);
            audioRoutesInfo = null;
        }
        this.mDeviceRoute = createRouteFromAudioInfo(audioRoutesInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.media.MediaRoute2Info createRouteFromAudioInfo(android.media.AudioRoutesInfo r7) {
        /*
            r6 = this;
            r0 = 3
            r1 = 2
            if (r7 == 0) goto L33
            int r7 = r7.mainType
            r2 = r7 & 2
            r3 = 17040453(0x1040445, float:2.4247634E-38)
            if (r2 == 0) goto Lf
            r7 = 4
            goto L37
        Lf:
            r2 = r7 & 1
            if (r2 == 0) goto L15
            r7 = r0
            goto L37
        L15:
            r2 = r7 & 4
            if (r2 == 0) goto L1f
            r7 = 13
            r3 = 17040451(0x1040443, float:2.424763E-38)
            goto L37
        L1f:
            r2 = r7 & 8
            if (r2 == 0) goto L29
            r7 = 9
            r3 = 17040452(0x1040444, float:2.4247631E-38)
            goto L37
        L29:
            r7 = r7 & 16
            if (r7 == 0) goto L33
            r7 = 11
            r3 = 17040454(0x1040446, float:2.4247637E-38)
            goto L37
        L33:
            r3 = 17040450(0x1040442, float:2.4247626E-38)
            r7 = r1
        L37:
            monitor-enter(r6)
            android.media.MediaRoute2Info$Builder r2 = new android.media.MediaRoute2Info$Builder     // Catch: java.lang.Throwable -> L8b
            java.lang.String r4 = "DEVICE_ROUTE"
            android.content.Context r5 = r6.mContext     // Catch: java.lang.Throwable -> L8b
            android.content.res.Resources r5 = r5.getResources()     // Catch: java.lang.Throwable -> L8b
            java.lang.CharSequence r3 = r5.getText(r3)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L8b
            r2.<init>(r4, r3)     // Catch: java.lang.Throwable -> L8b
            android.media.AudioManager r3 = r6.mAudioManager     // Catch: java.lang.Throwable -> L8b
            boolean r3 = r3.isVolumeFixed()     // Catch: java.lang.Throwable -> L8b
            r3 = r3 ^ 1
            android.media.MediaRoute2Info$Builder r2 = r2.setVolumeHandling(r3)     // Catch: java.lang.Throwable -> L8b
            int r3 = r6.mDeviceVolume     // Catch: java.lang.Throwable -> L8b
            android.media.MediaRoute2Info$Builder r2 = r2.setVolume(r3)     // Catch: java.lang.Throwable -> L8b
            android.media.AudioManager r3 = r6.mAudioManager     // Catch: java.lang.Throwable -> L8b
            int r0 = r3.getStreamMaxVolume(r0)     // Catch: java.lang.Throwable -> L8b
            android.media.MediaRoute2Info$Builder r0 = r2.setVolumeMax(r0)     // Catch: java.lang.Throwable -> L8b
            android.media.MediaRoute2Info$Builder r0 = r0.setType(r7)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r2 = "android.media.route.feature.LIVE_AUDIO"
            android.media.MediaRoute2Info$Builder r0 = r0.addFeature(r2)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r2 = "android.media.route.feature.LIVE_VIDEO"
            android.media.MediaRoute2Info$Builder r0 = r0.addFeature(r2)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r2 = "android.media.route.feature.LOCAL_PLAYBACK"
            android.media.MediaRoute2Info$Builder r0 = r0.addFeature(r2)     // Catch: java.lang.Throwable -> L8b
            android.media.MediaRoute2Info$Builder r0 = r0.setConnectionState(r1)     // Catch: java.lang.Throwable -> L8b
            if (r7 != r1) goto L8d
            int r7 = r6.mBuiltInSpeakerSuitabilityStatus     // Catch: java.lang.Throwable -> L8b
            r0.setSuitabilityStatus(r7)     // Catch: java.lang.Throwable -> L8b
            goto L8d
        L8b:
            r7 = move-exception
            goto L93
        L8d:
            android.media.MediaRoute2Info r7 = r0.build()     // Catch: java.lang.Throwable -> L8b
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L8b
            return r7
        L93:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L8b
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.media.LegacyDeviceRouteController.createRouteFromAudioInfo(android.media.AudioRoutesInfo):android.media.MediaRoute2Info");
    }

    @Override // com.android.server.media.DeviceRouteController
    public final synchronized List getAvailableRoutes() {
        return Collections.emptyList();
    }

    @Override // com.android.server.media.DeviceRouteController
    public final synchronized MediaRoute2Info getSelectedRoute() {
        return this.mDeviceRoute;
    }

    @Override // com.android.server.media.DeviceRouteController
    public final void start(UserHandle userHandle) {
    }

    @Override // com.android.server.media.DeviceRouteController
    public final void stop() {
    }

    @Override // com.android.server.media.DeviceRouteController
    public final synchronized void transferTo(String str) {
    }

    @Override // com.android.server.media.DeviceRouteController
    public final synchronized boolean updateVolume(int i) {
        if (this.mDeviceVolume == i) {
            return false;
        }
        this.mDeviceVolume = i;
        this.mDeviceRoute = new MediaRoute2Info.Builder(this.mDeviceRoute).setVolume(i).build();
        return true;
    }
}
