package android.media.quality;

import android.media.quality.IActiveProcessingPictureListener;
import android.media.quality.IAmbientBacklightCallback;
import android.media.quality.IPictureProfileCallback;
import android.media.quality.ISoundProfileCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IMediaQualityManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.quality.IMediaQualityManager";

    public static class Default implements IMediaQualityManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public void createPictureProfile(PictureProfile pictureProfile, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void createSoundProfile(SoundProfile soundProfile, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<PictureProfile> getAvailablePictureProfiles(boolean z, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<SoundProfile> getAvailableSoundProfiles(boolean z, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public long getDefaultPictureProfileHandleValue(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<ParameterCapability> getParameterCapabilities(List<String> list, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public PictureProfile getPictureProfile(int i, String str, boolean z, int i2) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<String> getPictureProfileAllowList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public long getPictureProfileForTvInput(String str, int i) throws RemoteException {
            return 0L;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<PictureProfileHandle> getPictureProfileHandle(String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public long getPictureProfileHandleValue(String str, int i) throws RemoteException {
            return 0L;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<String> getPictureProfilePackageNames(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<PictureProfile> getPictureProfilesByPackage(String str, boolean z, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public SoundProfile getSoundProfile(int i, String str, boolean z, int i2) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<String> getSoundProfileAllowList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<SoundProfileHandle> getSoundProfileHandle(String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<String> getSoundProfilePackageNames(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public List<SoundProfile> getSoundProfilesByPackage(String str, boolean z, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean isAmbientBacklightEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean isAutoPictureQualityEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean isAutoSoundQualityEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean isSuperResolutionEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean isSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public void notifyPictureProfileHandleSelection(long j, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void registerActiveProcessingPictureListener(IActiveProcessingPictureListener iActiveProcessingPictureListener) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void registerAmbientBacklightCallback(IAmbientBacklightCallback iAmbientBacklightCallback) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void registerPictureProfileCallback(IPictureProfileCallback iPictureProfileCallback) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void registerSoundProfileCallback(ISoundProfileCallback iSoundProfileCallback) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void removePictureProfile(String str, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void removeSoundProfile(String str, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setAmbientBacklightEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setAmbientBacklightSettings(AmbientBacklightSettings ambientBacklightSettings, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setAutoPictureQualityEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setAutoSoundQualityEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean setDefaultPictureProfile(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public boolean setDefaultSoundProfile(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setPictureProfileAllowList(List<String> list, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setSoundProfileAllowList(List<String> list, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void setSuperResolutionEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void updatePictureProfile(String str, PictureProfile pictureProfile, int i) throws RemoteException {
        }

        @Override // android.media.quality.IMediaQualityManager
        public void updateSoundProfile(String str, SoundProfile soundProfile, int i) throws RemoteException {
        }
    }

    void createPictureProfile(PictureProfile pictureProfile, int i) throws RemoteException;

    void createSoundProfile(SoundProfile soundProfile, int i) throws RemoteException;

    List<PictureProfile> getAvailablePictureProfiles(boolean z, int i) throws RemoteException;

    List<SoundProfile> getAvailableSoundProfiles(boolean z, int i) throws RemoteException;

    long getDefaultPictureProfileHandleValue(int i) throws RemoteException;

    List<ParameterCapability> getParameterCapabilities(List<String> list, int i) throws RemoteException;

    PictureProfile getPictureProfile(int i, String str, boolean z, int i2) throws RemoteException;

    List<String> getPictureProfileAllowList(int i) throws RemoteException;

    long getPictureProfileForTvInput(String str, int i) throws RemoteException;

    List<PictureProfileHandle> getPictureProfileHandle(String[] strArr, int i) throws RemoteException;

    long getPictureProfileHandleValue(String str, int i) throws RemoteException;

    List<String> getPictureProfilePackageNames(int i) throws RemoteException;

    List<PictureProfile> getPictureProfilesByPackage(String str, boolean z, int i) throws RemoteException;

    SoundProfile getSoundProfile(int i, String str, boolean z, int i2) throws RemoteException;

    List<String> getSoundProfileAllowList(int i) throws RemoteException;

    List<SoundProfileHandle> getSoundProfileHandle(String[] strArr, int i) throws RemoteException;

    List<String> getSoundProfilePackageNames(int i) throws RemoteException;

    List<SoundProfile> getSoundProfilesByPackage(String str, boolean z, int i) throws RemoteException;

    boolean isAmbientBacklightEnabled(int i) throws RemoteException;

    boolean isAutoPictureQualityEnabled(int i) throws RemoteException;

    boolean isAutoSoundQualityEnabled(int i) throws RemoteException;

    boolean isSuperResolutionEnabled(int i) throws RemoteException;

    boolean isSupported(int i) throws RemoteException;

    void notifyPictureProfileHandleSelection(long j, int i) throws RemoteException;

    void registerActiveProcessingPictureListener(IActiveProcessingPictureListener iActiveProcessingPictureListener) throws RemoteException;

    void registerAmbientBacklightCallback(IAmbientBacklightCallback iAmbientBacklightCallback) throws RemoteException;

    void registerPictureProfileCallback(IPictureProfileCallback iPictureProfileCallback) throws RemoteException;

    void registerSoundProfileCallback(ISoundProfileCallback iSoundProfileCallback) throws RemoteException;

    void removePictureProfile(String str, int i) throws RemoteException;

    void removeSoundProfile(String str, int i) throws RemoteException;

    void setAmbientBacklightEnabled(boolean z, int i) throws RemoteException;

    void setAmbientBacklightSettings(AmbientBacklightSettings ambientBacklightSettings, int i) throws RemoteException;

    void setAutoPictureQualityEnabled(boolean z, int i) throws RemoteException;

    void setAutoSoundQualityEnabled(boolean z, int i) throws RemoteException;

    boolean setDefaultPictureProfile(String str, int i) throws RemoteException;

    boolean setDefaultSoundProfile(String str, int i) throws RemoteException;

    void setPictureProfileAllowList(List<String> list, int i) throws RemoteException;

    void setSoundProfileAllowList(List<String> list, int i) throws RemoteException;

    void setSuperResolutionEnabled(boolean z, int i) throws RemoteException;

    void updatePictureProfile(String str, PictureProfile pictureProfile, int i) throws RemoteException;

    void updateSoundProfile(String str, SoundProfile soundProfile, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaQualityManager {
        static final int TRANSACTION_createPictureProfile = 1;
        static final int TRANSACTION_createSoundProfile = 16;
        static final int TRANSACTION_getAvailablePictureProfiles = 7;
        static final int TRANSACTION_getAvailableSoundProfiles = 22;
        static final int TRANSACTION_getDefaultPictureProfileHandleValue = 13;
        static final int TRANSACTION_getParameterCapabilities = 31;
        static final int TRANSACTION_getPictureProfile = 5;
        static final int TRANSACTION_getPictureProfileAllowList = 9;
        static final int TRANSACTION_getPictureProfileForTvInput = 15;
        static final int TRANSACTION_getPictureProfileHandle = 11;
        static final int TRANSACTION_getPictureProfileHandleValue = 12;
        static final int TRANSACTION_getPictureProfilePackageNames = 8;
        static final int TRANSACTION_getPictureProfilesByPackage = 6;
        static final int TRANSACTION_getSoundProfile = 20;
        static final int TRANSACTION_getSoundProfileAllowList = 24;
        static final int TRANSACTION_getSoundProfileHandle = 26;
        static final int TRANSACTION_getSoundProfilePackageNames = 23;
        static final int TRANSACTION_getSoundProfilesByPackage = 21;
        static final int TRANSACTION_isAmbientBacklightEnabled = 41;
        static final int TRANSACTION_isAutoPictureQualityEnabled = 34;
        static final int TRANSACTION_isAutoSoundQualityEnabled = 38;
        static final int TRANSACTION_isSuperResolutionEnabled = 36;
        static final int TRANSACTION_isSupported = 32;
        static final int TRANSACTION_notifyPictureProfileHandleSelection = 14;
        static final int TRANSACTION_registerActiveProcessingPictureListener = 30;
        static final int TRANSACTION_registerAmbientBacklightCallback = 29;
        static final int TRANSACTION_registerPictureProfileCallback = 27;
        static final int TRANSACTION_registerSoundProfileCallback = 28;
        static final int TRANSACTION_removePictureProfile = 3;
        static final int TRANSACTION_removeSoundProfile = 18;
        static final int TRANSACTION_setAmbientBacklightEnabled = 40;
        static final int TRANSACTION_setAmbientBacklightSettings = 39;
        static final int TRANSACTION_setAutoPictureQualityEnabled = 33;
        static final int TRANSACTION_setAutoSoundQualityEnabled = 37;
        static final int TRANSACTION_setDefaultPictureProfile = 4;
        static final int TRANSACTION_setDefaultSoundProfile = 19;
        static final int TRANSACTION_setPictureProfileAllowList = 10;
        static final int TRANSACTION_setSoundProfileAllowList = 25;
        static final int TRANSACTION_setSuperResolutionEnabled = 35;
        static final int TRANSACTION_updatePictureProfile = 2;
        static final int TRANSACTION_updateSoundProfile = 17;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMediaQualityManager.DESCRIPTOR);
        }

        public static IMediaQualityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMediaQualityManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaQualityManager)) {
                return (IMediaQualityManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMediaQualityManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMediaQualityManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    PictureProfile pictureProfile = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createPictureProfile(pictureProfile, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString = parcel.readString();
                    PictureProfile pictureProfile2 = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePictureProfile(readString, pictureProfile2, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePictureProfile(readString2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean defaultPictureProfile = setDefaultPictureProfile(readString3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultPictureProfile);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    String readString4 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PictureProfile pictureProfile3 = getPictureProfile(readInt5, readString4, readBoolean, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pictureProfile3, 1);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PictureProfile> pictureProfilesByPackage = getPictureProfilesByPackage(readString5, readBoolean2, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pictureProfilesByPackage, 1);
                    return true;
                case 7:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PictureProfile> availablePictureProfiles = getAvailablePictureProfiles(readBoolean3, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availablePictureProfiles, 1);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> pictureProfilePackageNames = getPictureProfilePackageNames(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeStringList(pictureProfilePackageNames);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> pictureProfileAllowList = getPictureProfileAllowList(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeStringList(pictureProfileAllowList);
                    return true;
                case 10:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPictureProfileAllowList(createStringArrayList, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String[] createStringArray = parcel.createStringArray();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PictureProfileHandle> pictureProfileHandle = getPictureProfileHandle(createStringArray, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pictureProfileHandle, 1);
                    return true;
                case 12:
                    String readString6 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long pictureProfileHandleValue = getPictureProfileHandleValue(readString6, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeLong(pictureProfileHandleValue);
                    return true;
                case 13:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long defaultPictureProfileHandleValue = getDefaultPictureProfileHandleValue(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeLong(defaultPictureProfileHandleValue);
                    return true;
                case 14:
                    long readLong = parcel.readLong();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPictureProfileHandleSelection(readLong, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString7 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long pictureProfileForTvInput = getPictureProfileForTvInput(readString7, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeLong(pictureProfileForTvInput);
                    return true;
                case 16:
                    SoundProfile soundProfile = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSoundProfile(soundProfile, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString8 = parcel.readString();
                    SoundProfile soundProfile2 = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSoundProfile(readString8, soundProfile2, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString9 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSoundProfile(readString9, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString10 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean defaultSoundProfile = setDefaultSoundProfile(readString10, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultSoundProfile);
                    return true;
                case 20:
                    int readInt21 = parcel.readInt();
                    String readString11 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SoundProfile soundProfile3 = getSoundProfile(readInt21, readString11, readBoolean4, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(soundProfile3, 1);
                    return true;
                case 21:
                    String readString12 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SoundProfile> soundProfilesByPackage = getSoundProfilesByPackage(readString12, readBoolean5, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(soundProfilesByPackage, 1);
                    return true;
                case 22:
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SoundProfile> availableSoundProfiles = getAvailableSoundProfiles(readBoolean6, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableSoundProfiles, 1);
                    return true;
                case 23:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> soundProfilePackageNames = getSoundProfilePackageNames(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeStringList(soundProfilePackageNames);
                    return true;
                case 24:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> soundProfileAllowList = getSoundProfileAllowList(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeStringList(soundProfileAllowList);
                    return true;
                case 25:
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSoundProfileAllowList(createStringArrayList2, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String[] createStringArray2 = parcel.createStringArray();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SoundProfileHandle> soundProfileHandle = getSoundProfileHandle(createStringArray2, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(soundProfileHandle, 1);
                    return true;
                case 27:
                    IPictureProfileCallback asInterface = IPictureProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPictureProfileCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    ISoundProfileCallback asInterface2 = ISoundProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSoundProfileCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IAmbientBacklightCallback asInterface3 = IAmbientBacklightCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAmbientBacklightCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IActiveProcessingPictureListener asInterface4 = IActiveProcessingPictureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerActiveProcessingPictureListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ParameterCapability> parameterCapabilities = getParameterCapabilities(createStringArrayList3, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(parameterCapabilities, 1);
                    return true;
                case 32:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSupported = isSupported(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupported);
                    return true;
                case 33:
                    boolean readBoolean7 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoPictureQualityEnabled(readBoolean7, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAutoPictureQualityEnabled = isAutoPictureQualityEnabled(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAutoPictureQualityEnabled);
                    return true;
                case 35:
                    boolean readBoolean8 = parcel.readBoolean();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSuperResolutionEnabled(readBoolean8, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSuperResolutionEnabled = isSuperResolutionEnabled(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSuperResolutionEnabled);
                    return true;
                case 37:
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoSoundQualityEnabled(readBoolean9, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAutoSoundQualityEnabled = isAutoSoundQualityEnabled(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAutoSoundQualityEnabled);
                    return true;
                case 39:
                    AmbientBacklightSettings ambientBacklightSettings = (AmbientBacklightSettings) parcel.readTypedObject(AmbientBacklightSettings.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAmbientBacklightSettings(ambientBacklightSettings, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAmbientBacklightEnabled(readBoolean10, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAmbientBacklightEnabled = isAmbientBacklightEnabled(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientBacklightEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaQualityManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMediaQualityManager.DESCRIPTOR;
            }

            @Override // android.media.quality.IMediaQualityManager
            public void createPictureProfile(PictureProfile pictureProfile, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeTypedObject(pictureProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void updatePictureProfile(String str, PictureProfile pictureProfile, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pictureProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void removePictureProfile(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean setDefaultPictureProfile(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public PictureProfile getPictureProfile(int i, String str, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PictureProfile) obtain2.readTypedObject(PictureProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<PictureProfile> getPictureProfilesByPackage(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PictureProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<PictureProfile> getAvailablePictureProfiles(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PictureProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getPictureProfilePackageNames(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getPictureProfileAllowList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setPictureProfileAllowList(List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<PictureProfileHandle> getPictureProfileHandle(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PictureProfileHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public long getPictureProfileHandleValue(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public long getDefaultPictureProfileHandleValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void notifyPictureProfileHandleSelection(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public long getPictureProfileForTvInput(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void createSoundProfile(SoundProfile soundProfile, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeTypedObject(soundProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void updateSoundProfile(String str, SoundProfile soundProfile, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(soundProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void removeSoundProfile(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean setDefaultSoundProfile(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public SoundProfile getSoundProfile(int i, String str, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundProfile) obtain2.readTypedObject(SoundProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<SoundProfile> getSoundProfilesByPackage(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SoundProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<SoundProfile> getAvailableSoundProfiles(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SoundProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getSoundProfilePackageNames(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getSoundProfileAllowList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setSoundProfileAllowList(List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<SoundProfileHandle> getSoundProfileHandle(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SoundProfileHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerPictureProfileCallback(IPictureProfileCallback iPictureProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iPictureProfileCallback);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerSoundProfileCallback(ISoundProfileCallback iSoundProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSoundProfileCallback);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerAmbientBacklightCallback(IAmbientBacklightCallback iAmbientBacklightCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAmbientBacklightCallback);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerActiveProcessingPictureListener(IActiveProcessingPictureListener iActiveProcessingPictureListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iActiveProcessingPictureListener);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<ParameterCapability> getParameterCapabilities(List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ParameterCapability.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAutoPictureQualityEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isAutoPictureQualityEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setSuperResolutionEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isSuperResolutionEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAutoSoundQualityEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isAutoSoundQualityEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAmbientBacklightSettings(AmbientBacklightSettings ambientBacklightSettings, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeTypedObject(ambientBacklightSettings, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAmbientBacklightEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isAmbientBacklightEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
