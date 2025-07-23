package android.media.quality;

import android.annotation.SystemApi;
import android.content.Context;
import android.media.quality.IActiveProcessingPictureListener;
import android.media.quality.IAmbientBacklightCallback;
import android.media.quality.IPictureProfileCallback;
import android.media.quality.ISoundProfileCallback;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class MediaQualityManager {
    public static final int AMBIENT_BACKLIGHT_EVENT_DISABLED = 2;
    public static final int AMBIENT_BACKLIGHT_EVENT_ENABLED = 1;
    public static final int AMBIENT_BACKLIGHT_EVENT_INTERRUPTED = 4;
    public static final int AMBIENT_BACKLIGHT_EVENT_METADATA = 3;
    public static final String OPTION_INCLUDE_PARAMETERS = "include_parameters";
    private static final String TAG = "MediaQualityManager";
    private final Context mContext;
    private final IMediaQualityManager mService;
    private final UserHandle mUserHandle;
    private final Object mPpLock = new Object();
    private final Object mSpLock = new Object();
    private final Object mAbLock = new Object();
    private final Object mApLock = new Object();
    private final List<PictureProfileCallbackRecord> mPpCallbackRecords = new ArrayList();
    private final List<SoundProfileCallbackRecord> mSpCallbackRecords = new ArrayList();
    private final List<AmbientBacklightCallbackRecord> mAbCallbackRecords = new ArrayList();
    private final List<ActiveProcessingPictureListenerRecord> mApListenerRecords = new ArrayList();

    public interface AmbientBacklightCallback {
        void onAmbientBacklightEvent(AmbientBacklightEvent ambientBacklightEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AmbientBacklightEventTypes {
    }

    public static abstract class PictureProfileCallback {
        public void onError(String str, int i) {
        }

        public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) {
        }

        public void onPictureProfileAdded(String str, PictureProfile pictureProfile) {
        }

        public void onPictureProfileRemoved(String str, PictureProfile pictureProfile) {
        }

        public void onPictureProfileUpdated(String str, PictureProfile pictureProfile) {
        }
    }

    public static abstract class SoundProfileCallback {
        public void onError(String str, int i) {
        }

        public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) {
        }

        public void onSoundProfileAdded(String str, SoundProfile soundProfile) {
        }

        public void onSoundProfileRemoved(String str, SoundProfile soundProfile) {
        }

        public void onSoundProfileUpdated(String str, SoundProfile soundProfile) {
        }
    }

    public MediaQualityManager(Context context, IMediaQualityManager iMediaQualityManager) {
        this.mContext = context;
        this.mUserHandle = context.getUser();
        this.mService = iMediaQualityManager;
        IPictureProfileCallback.Stub stub = new IPictureProfileCallback.Stub() { // from class: android.media.quality.MediaQualityManager.1
            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileAdded(String str, PictureProfile pictureProfile) {
                synchronized (MediaQualityManager.this.mPpLock) {
                    Iterator it = MediaQualityManager.this.mPpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((PictureProfileCallbackRecord) it.next()).postPictureProfileAdded(str, pictureProfile);
                    }
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileUpdated(String str, PictureProfile pictureProfile) {
                synchronized (MediaQualityManager.this.mPpLock) {
                    Iterator it = MediaQualityManager.this.mPpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((PictureProfileCallbackRecord) it.next()).postPictureProfileUpdated(str, pictureProfile);
                    }
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileRemoved(String str, PictureProfile pictureProfile) {
                synchronized (MediaQualityManager.this.mPpLock) {
                    Iterator it = MediaQualityManager.this.mPpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((PictureProfileCallbackRecord) it.next()).postPictureProfileRemoved(str, pictureProfile);
                    }
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) {
                synchronized (MediaQualityManager.this.mPpLock) {
                    Iterator it = MediaQualityManager.this.mPpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((PictureProfileCallbackRecord) it.next()).postParameterCapabilitiesChanged(str, list);
                    }
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onError(String str, int i) {
                synchronized (MediaQualityManager.this.mPpLock) {
                    Iterator it = MediaQualityManager.this.mPpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((PictureProfileCallbackRecord) it.next()).postError(str, i);
                    }
                }
            }
        };
        ISoundProfileCallback.Stub stub2 = new ISoundProfileCallback.Stub() { // from class: android.media.quality.MediaQualityManager.2
            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileAdded(String str, SoundProfile soundProfile) {
                synchronized (MediaQualityManager.this.mSpLock) {
                    Iterator it = MediaQualityManager.this.mSpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((SoundProfileCallbackRecord) it.next()).postSoundProfileAdded(str, soundProfile);
                    }
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileUpdated(String str, SoundProfile soundProfile) {
                synchronized (MediaQualityManager.this.mSpLock) {
                    Iterator it = MediaQualityManager.this.mSpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((SoundProfileCallbackRecord) it.next()).postSoundProfileUpdated(str, soundProfile);
                    }
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileRemoved(String str, SoundProfile soundProfile) {
                synchronized (MediaQualityManager.this.mSpLock) {
                    Iterator it = MediaQualityManager.this.mSpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((SoundProfileCallbackRecord) it.next()).postSoundProfileRemoved(str, soundProfile);
                    }
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) {
                synchronized (MediaQualityManager.this.mSpLock) {
                    Iterator it = MediaQualityManager.this.mSpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((SoundProfileCallbackRecord) it.next()).postParameterCapabilitiesChanged(str, list);
                    }
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onError(String str, int i) {
                synchronized (MediaQualityManager.this.mSpLock) {
                    Iterator it = MediaQualityManager.this.mSpCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((SoundProfileCallbackRecord) it.next()).postError(str, i);
                    }
                }
            }
        };
        IAmbientBacklightCallback.Stub stub3 = new IAmbientBacklightCallback.Stub() { // from class: android.media.quality.MediaQualityManager.3
            @Override // android.media.quality.IAmbientBacklightCallback
            public void onAmbientBacklightEvent(AmbientBacklightEvent ambientBacklightEvent) {
                synchronized (MediaQualityManager.this.mAbLock) {
                    Iterator it = MediaQualityManager.this.mAbCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((AmbientBacklightCallbackRecord) it.next()).postAmbientBacklightEvent(ambientBacklightEvent);
                    }
                }
            }
        };
        IActiveProcessingPictureListener.Stub stub4 = new IActiveProcessingPictureListener.Stub() { // from class: android.media.quality.MediaQualityManager.4
            @Override // android.media.quality.IActiveProcessingPictureListener
            public void onActiveProcessingPicturesChanged(List<ActiveProcessingPicture> list) {
                ArrayList arrayList = new ArrayList();
                for (ActiveProcessingPicture activeProcessingPicture : list) {
                    if (!activeProcessingPicture.isForGlobal()) {
                        arrayList.add(activeProcessingPicture);
                    }
                }
                for (ActiveProcessingPictureListenerRecord activeProcessingPictureListenerRecord : MediaQualityManager.this.mApListenerRecords) {
                    if (activeProcessingPictureListenerRecord.mIsGlobal) {
                        activeProcessingPictureListenerRecord.postActiveProcessingPicturesChanged(list);
                    } else {
                        activeProcessingPictureListenerRecord.postActiveProcessingPicturesChanged(arrayList);
                    }
                }
            }
        };
        if (iMediaQualityManager != null) {
            try {
                iMediaQualityManager.registerPictureProfileCallback(stub);
                iMediaQualityManager.registerSoundProfileCallback(stub2);
                iMediaQualityManager.registerAmbientBacklightCallback(stub3);
                iMediaQualityManager.registerActiveProcessingPictureListener(stub4);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void registerPictureProfileCallback(Executor executor, PictureProfileCallback pictureProfileCallback) {
        Preconditions.checkNotNull(pictureProfileCallback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mPpLock) {
            this.mPpCallbackRecords.add(new PictureProfileCallbackRecord(pictureProfileCallback, executor));
        }
    }

    public void unregisterPictureProfileCallback(PictureProfileCallback pictureProfileCallback) {
        Preconditions.checkNotNull(pictureProfileCallback);
        synchronized (this.mPpLock) {
            Iterator<PictureProfileCallbackRecord> it = this.mPpCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == pictureProfileCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public PictureProfile getPictureProfile(int i, String str, ProfileQueryParams profileQueryParams) {
        boolean z;
        if (profileQueryParams != null) {
            try {
                if (!profileQueryParams.mParametersIncluded) {
                    z = false;
                    return this.mService.getPictureProfile(i, str, z, this.mUserHandle.getIdentifier());
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        z = true;
        return this.mService.getPictureProfile(i, str, z, this.mUserHandle.getIdentifier());
    }

    @SystemApi
    public List<PictureProfile> getPictureProfilesByPackage(String str, ProfileQueryParams profileQueryParams) {
        boolean z;
        if (profileQueryParams != null) {
            try {
                if (!profileQueryParams.mParametersIncluded) {
                    z = false;
                    return this.mService.getPictureProfilesByPackage(str, z, this.mUserHandle.getIdentifier());
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        z = true;
        return this.mService.getPictureProfilesByPackage(str, z, this.mUserHandle.getIdentifier());
    }

    public List<PictureProfile> getAvailablePictureProfiles(ProfileQueryParams profileQueryParams) {
        boolean z;
        if (profileQueryParams != null) {
            try {
                if (!profileQueryParams.mParametersIncluded) {
                    z = false;
                    return this.mService.getAvailablePictureProfiles(z, this.mUserHandle.getIdentifier());
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        z = true;
        return this.mService.getAvailablePictureProfiles(z, this.mUserHandle.getIdentifier());
    }

    @SystemApi
    public boolean setDefaultPictureProfile(String str) {
        try {
            return this.mService.setDefaultPictureProfile(str, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getPictureProfilePackageNames() {
        try {
            return this.mService.getPictureProfilePackageNames(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PictureProfileHandle> getPictureProfileHandle(String[] strArr) {
        try {
            return this.mService.getPictureProfileHandle(strArr, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getPictureProfileForTvInput(String str) {
        try {
            return this.mService.getPictureProfileForTvInput(str, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SoundProfileHandle> getSoundProfileHandle(String[] strArr) {
        try {
            return this.mService.getSoundProfileHandle(strArr, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createPictureProfile(PictureProfile pictureProfile) {
        try {
            this.mService.createPictureProfile(pictureProfile, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updatePictureProfile(String str, PictureProfile pictureProfile) {
        try {
            this.mService.updatePictureProfile(str, pictureProfile, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePictureProfile(String str) {
        try {
            this.mService.removePictureProfile(str, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerSoundProfileCallback(Executor executor, SoundProfileCallback soundProfileCallback) {
        Preconditions.checkNotNull(soundProfileCallback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mSpLock) {
            this.mSpCallbackRecords.add(new SoundProfileCallbackRecord(soundProfileCallback, executor));
        }
    }

    public void unregisterSoundProfileCallback(SoundProfileCallback soundProfileCallback) {
        Preconditions.checkNotNull(soundProfileCallback);
        synchronized (this.mSpLock) {
            Iterator<SoundProfileCallbackRecord> it = this.mSpCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == soundProfileCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public SoundProfile getSoundProfile(int i, String str, ProfileQueryParams profileQueryParams) {
        boolean z;
        if (profileQueryParams != null) {
            try {
                if (!profileQueryParams.mParametersIncluded) {
                    z = false;
                    return this.mService.getSoundProfile(i, str, z, this.mUserHandle.getIdentifier());
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        z = true;
        return this.mService.getSoundProfile(i, str, z, this.mUserHandle.getIdentifier());
    }

    @SystemApi
    public List<SoundProfile> getSoundProfilesByPackage(String str, ProfileQueryParams profileQueryParams) {
        boolean z;
        if (profileQueryParams != null) {
            try {
                if (!profileQueryParams.mParametersIncluded) {
                    z = false;
                    return this.mService.getSoundProfilesByPackage(str, z, this.mUserHandle.getIdentifier());
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        z = true;
        return this.mService.getSoundProfilesByPackage(str, z, this.mUserHandle.getIdentifier());
    }

    public List<SoundProfile> getAvailableSoundProfiles(ProfileQueryParams profileQueryParams) {
        boolean z;
        if (profileQueryParams != null) {
            try {
                if (!profileQueryParams.mParametersIncluded) {
                    z = false;
                    return this.mService.getAvailableSoundProfiles(z, this.mUserHandle.getIdentifier());
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        z = true;
        return this.mService.getAvailableSoundProfiles(z, this.mUserHandle.getIdentifier());
    }

    @SystemApi
    public boolean setDefaultSoundProfile(String str) {
        try {
            return this.mService.setDefaultSoundProfile(str, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getSoundProfilePackageNames() {
        try {
            return this.mService.getSoundProfilePackageNames(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createSoundProfile(SoundProfile soundProfile) {
        try {
            this.mService.createSoundProfile(soundProfile, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateSoundProfile(String str, SoundProfile soundProfile) {
        try {
            this.mService.updateSoundProfile(str, soundProfile, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeSoundProfile(String str) {
        try {
            this.mService.removeSoundProfile(str, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ParameterCapability> getParameterCapabilities(List<String> list) {
        try {
            return this.mService.getParameterCapabilities(list, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getPictureProfileAllowList() {
        try {
            return this.mService.getPictureProfileAllowList(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setPictureProfileAllowList(List<String> list) {
        try {
            this.mService.setPictureProfileAllowList(list, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getSoundProfileAllowList() {
        try {
            return this.mService.getSoundProfileAllowList(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setSoundProfileAllowList(List<String> list) {
        try {
            this.mService.setSoundProfileAllowList(list, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSupported() {
        try {
            return this.mService.isSupported(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAutoPictureQualityEnabled(boolean z) {
        try {
            this.mService.setAutoPictureQualityEnabled(z, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAutoPictureQualityEnabled() {
        try {
            return this.mService.isAutoPictureQualityEnabled(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setSuperResolutionEnabled(boolean z) {
        try {
            this.mService.setSuperResolutionEnabled(z, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSuperResolutionEnabled() {
        try {
            return this.mService.isSuperResolutionEnabled(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAutoSoundQualityEnabled(boolean z) {
        try {
            this.mService.setAutoSoundQualityEnabled(z, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAutoSoundQualityEnabled() {
        try {
            return this.mService.isAutoSoundQualityEnabled(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerAmbientBacklightCallback(Executor executor, AmbientBacklightCallback ambientBacklightCallback) {
        Preconditions.checkNotNull(ambientBacklightCallback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mAbLock) {
            this.mAbCallbackRecords.add(new AmbientBacklightCallbackRecord(ambientBacklightCallback, executor));
        }
    }

    public void unregisterAmbientBacklightCallback(AmbientBacklightCallback ambientBacklightCallback) {
        Preconditions.checkNotNull(ambientBacklightCallback);
        synchronized (this.mAbLock) {
            Iterator<AmbientBacklightCallbackRecord> it = this.mAbCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == ambientBacklightCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public void setAmbientBacklightSettings(AmbientBacklightSettings ambientBacklightSettings) {
        Preconditions.checkNotNull(ambientBacklightSettings);
        try {
            this.mService.setAmbientBacklightSettings(ambientBacklightSettings, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAmbientBacklightEnabled() {
        try {
            return this.mService.isAmbientBacklightEnabled(this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAmbientBacklightEnabled(boolean z) {
        try {
            this.mService.setAmbientBacklightEnabled(z, this.mUserHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class PictureProfileCallbackRecord {
        private final PictureProfileCallback mCallback;
        private final Executor mExecutor;

        PictureProfileCallbackRecord(PictureProfileCallback pictureProfileCallback, Executor executor) {
            this.mCallback = pictureProfileCallback;
            this.mExecutor = executor;
        }

        public PictureProfileCallback getCallback() {
            return this.mCallback;
        }

        public void postPictureProfileAdded(final String str, final PictureProfile pictureProfile) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.PictureProfileCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    PictureProfileCallbackRecord.this.mCallback.onPictureProfileAdded(str, pictureProfile);
                }
            });
        }

        public void postPictureProfileUpdated(final String str, final PictureProfile pictureProfile) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.PictureProfileCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    PictureProfileCallbackRecord.this.mCallback.onPictureProfileUpdated(str, pictureProfile);
                }
            });
        }

        public void postPictureProfileRemoved(final String str, final PictureProfile pictureProfile) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.PictureProfileCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    PictureProfileCallbackRecord.this.mCallback.onPictureProfileRemoved(str, pictureProfile);
                }
            });
        }

        public void postParameterCapabilitiesChanged(final String str, final List<ParameterCapability> list) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.PictureProfileCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    PictureProfileCallbackRecord.this.mCallback.onParameterCapabilitiesChanged(str, list);
                }
            });
        }

        public void postError(final String str, final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.PictureProfileCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    PictureProfileCallbackRecord.this.mCallback.onError(str, i);
                }
            });
        }
    }

    private static final class SoundProfileCallbackRecord {
        private final SoundProfileCallback mCallback;
        private final Executor mExecutor;

        SoundProfileCallbackRecord(SoundProfileCallback soundProfileCallback, Executor executor) {
            this.mCallback = soundProfileCallback;
            this.mExecutor = executor;
        }

        public SoundProfileCallback getCallback() {
            return this.mCallback;
        }

        public void postSoundProfileAdded(final String str, final SoundProfile soundProfile) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.SoundProfileCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    SoundProfileCallbackRecord.this.mCallback.onSoundProfileAdded(str, soundProfile);
                }
            });
        }

        public void postSoundProfileUpdated(final String str, final SoundProfile soundProfile) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.SoundProfileCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    SoundProfileCallbackRecord.this.mCallback.onSoundProfileUpdated(str, soundProfile);
                }
            });
        }

        public void postSoundProfileRemoved(final String str, final SoundProfile soundProfile) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.SoundProfileCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    SoundProfileCallbackRecord.this.mCallback.onSoundProfileRemoved(str, soundProfile);
                }
            });
        }

        public void postParameterCapabilitiesChanged(final String str, final List<ParameterCapability> list) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.SoundProfileCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    SoundProfileCallbackRecord.this.mCallback.onParameterCapabilitiesChanged(str, list);
                }
            });
        }

        public void postError(final String str, final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.SoundProfileCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    SoundProfileCallbackRecord.this.mCallback.onError(str, i);
                }
            });
        }
    }

    private static final class AmbientBacklightCallbackRecord {
        private final AmbientBacklightCallback mCallback;
        private final Executor mExecutor;

        AmbientBacklightCallbackRecord(AmbientBacklightCallback ambientBacklightCallback, Executor executor) {
            this.mCallback = ambientBacklightCallback;
            this.mExecutor = executor;
        }

        public AmbientBacklightCallback getCallback() {
            return this.mCallback;
        }

        public void postAmbientBacklightEvent(final AmbientBacklightEvent ambientBacklightEvent) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.AmbientBacklightCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    AmbientBacklightCallbackRecord.this.mCallback.onAmbientBacklightEvent(ambientBacklightEvent);
                }
            });
        }
    }

    public void addActiveProcessingPictureListener(Executor executor, Consumer<List<ActiveProcessingPicture>> consumer) {
        Preconditions.checkNotNull(consumer);
        Preconditions.checkNotNull(executor);
        synchronized (this.mApLock) {
            this.mApListenerRecords.add(new ActiveProcessingPictureListenerRecord(consumer, executor, false));
        }
    }

    @SystemApi
    public void addGlobalActiveProcessingPictureListener(Executor executor, Consumer<List<ActiveProcessingPicture>> consumer) {
        Preconditions.checkNotNull(consumer);
        Preconditions.checkNotNull(executor);
        synchronized (this.mApLock) {
            this.mApListenerRecords.add(new ActiveProcessingPictureListenerRecord(consumer, executor, true));
        }
    }

    public void removeActiveProcessingPictureListener(Consumer<List<ActiveProcessingPicture>> consumer) {
        Preconditions.checkNotNull(consumer);
        synchronized (this.mApLock) {
            Iterator<ActiveProcessingPictureListenerRecord> it = this.mApListenerRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getListener() == consumer) {
                    it.remove();
                    break;
                }
            }
        }
    }

    private static final class ActiveProcessingPictureListenerRecord {
        private final Executor mExecutor;
        private final boolean mIsGlobal;
        private final Consumer<List<ActiveProcessingPicture>> mListener;

        ActiveProcessingPictureListenerRecord(Consumer<List<ActiveProcessingPicture>> consumer, Executor executor, boolean z) {
            this.mListener = consumer;
            this.mExecutor = executor;
            this.mIsGlobal = z;
        }

        public Consumer<List<ActiveProcessingPicture>> getListener() {
            return this.mListener;
        }

        public void postActiveProcessingPicturesChanged(final List<ActiveProcessingPicture> list) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.quality.MediaQualityManager.ActiveProcessingPictureListenerRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    ActiveProcessingPictureListenerRecord.this.mListener.accept(list);
                }
            });
        }
    }

    public static final class ProfileQueryParams implements Parcelable {
        private final boolean mParametersIncluded;
        private static final ProfileQueryParams DEFAULT = new Builder().build();
        public static final Parcelable.Creator<ProfileQueryParams> CREATOR = new Parcelable.Creator<ProfileQueryParams>() { // from class: android.media.quality.MediaQualityManager.ProfileQueryParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProfileQueryParams createFromParcel(Parcel parcel) {
                return new ProfileQueryParams(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProfileQueryParams[] newArray(int i) {
                return new ProfileQueryParams[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private ProfileQueryParams(Parcel parcel) {
            this.mParametersIncluded = parcel.readBoolean();
        }

        public ProfileQueryParams(boolean z) {
            this.mParametersIncluded = z;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBoolean(this.mParametersIncluded);
        }

        public boolean areParametersIncluded() {
            return this.mParametersIncluded;
        }

        private Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(MediaQualityManager.OPTION_INCLUDE_PARAMETERS, this.mParametersIncluded);
            return bundle;
        }

        public static final class Builder {
            private boolean mParametersIncluded;

            public Builder setParametersIncluded(boolean z) {
                this.mParametersIncluded = z;
                return this;
            }

            public ProfileQueryParams build() {
                return new ProfileQueryParams(this.mParametersIncluded);
            }
        }
    }
}
