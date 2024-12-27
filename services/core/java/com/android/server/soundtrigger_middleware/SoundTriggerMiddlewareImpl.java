package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.Properties;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.util.Slog;

import com.android.server.soundtrigger_middleware.SoundTriggerModule.Session;

import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerMiddlewareImpl implements ISoundTriggerMiddlewareInternal {
    public final SoundTriggerModule[] mModules;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class AudioSessionProvider {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class AudioSession {
            public final int mDeviceHandle;
            public final int mIoHandle;
            public final int mSessionHandle;

            public AudioSession(int i, int i2, int i3) {
                this.mSessionHandle = i;
                this.mIoHandle = i2;
                this.mDeviceHandle = i3;
            }
        }

        public abstract AudioSession acquireSession();

        public abstract void releaseSession(int i);
    }

    public SoundTriggerMiddlewareImpl(
            HalFactory[] halFactoryArr, AudioSessionProvider audioSessionProvider) {
        ArrayList arrayList = new ArrayList(halFactoryArr.length);
        for (HalFactory halFactory : halFactoryArr) {
            try {
                arrayList.add(new SoundTriggerModule(halFactory, audioSessionProvider));
            } catch (Exception e) {
                Slog.e(
                        "SoundTriggerMiddlewareImpl",
                        "Failed to add a SoundTriggerModule instance",
                        e);
            }
        }
        this.mModules = (SoundTriggerModule[]) arrayList.toArray(new SoundTriggerModule[0]);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final ISoundTriggerModule attach(
            int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        SoundTriggerModule.Session session;
        SoundTriggerModule soundTriggerModule = this.mModules[i];
        synchronized (soundTriggerModule) {
            session = soundTriggerModule.new Session(iSoundTriggerCallback);
            ((HashSet) soundTriggerModule.mActiveSessions).add(session);
        }
        return session;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final SoundTriggerModuleDescriptor[] listModules() {
        Properties properties;
        SoundTriggerModuleDescriptor[] soundTriggerModuleDescriptorArr =
                new SoundTriggerModuleDescriptor[this.mModules.length];
        for (int i = 0; i < this.mModules.length; i++) {
            SoundTriggerModuleDescriptor soundTriggerModuleDescriptor =
                    new SoundTriggerModuleDescriptor();
            soundTriggerModuleDescriptor.handle = i;
            SoundTriggerModule soundTriggerModule = this.mModules[i];
            synchronized (soundTriggerModule) {
                properties = soundTriggerModule.mProperties;
            }
            soundTriggerModuleDescriptor.properties = properties;
            soundTriggerModuleDescriptorArr[i] = soundTriggerModuleDescriptor;
        }
        return soundTriggerModuleDescriptorArr;
    }
}
