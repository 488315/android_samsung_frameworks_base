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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMediaQualityManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaQualityManager)) {
                return (IMediaQualityManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createPictureProfile(pictureProfile, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string = parcel.readString();
                    PictureProfile pictureProfile2 = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePictureProfile(string, pictureProfile2, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePictureProfile(string2, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean defaultPictureProfile = setDefaultPictureProfile(string3, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultPictureProfile);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    String string4 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PictureProfile pictureProfile3 = getPictureProfile(i7, string4, z, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pictureProfile3, 1);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PictureProfile> pictureProfilesByPackage = getPictureProfilesByPackage(string5, z2, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pictureProfilesByPackage, 1);
                    return true;
                case 7:
                    boolean z3 = parcel.readBoolean();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PictureProfile> availablePictureProfiles = getAvailablePictureProfiles(z3, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availablePictureProfiles, 1);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> pictureProfilePackageNames = getPictureProfilePackageNames(i11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(pictureProfilePackageNames);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> pictureProfileAllowList = getPictureProfileAllowList(i12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(pictureProfileAllowList);
                    return true;
                case 10:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPictureProfileAllowList(arrayListCreateStringArrayList, i13);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PictureProfileHandle> pictureProfileHandle = getPictureProfileHandle(strArrCreateStringArray, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pictureProfileHandle, 1);
                    return true;
                case 12:
                    String string6 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long pictureProfileHandleValue = getPictureProfileHandleValue(string6, i15);
                    parcel2.writeNoException();
                    parcel2.writeLong(pictureProfileHandleValue);
                    return true;
                case 13:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long defaultPictureProfileHandleValue = getDefaultPictureProfileHandleValue(i16);
                    parcel2.writeNoException();
                    parcel2.writeLong(defaultPictureProfileHandleValue);
                    return true;
                case 14:
                    long j = parcel.readLong();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPictureProfileHandleSelection(j, i17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string7 = parcel.readString();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long pictureProfileForTvInput = getPictureProfileForTvInput(string7, i18);
                    parcel2.writeNoException();
                    parcel2.writeLong(pictureProfileForTvInput);
                    return true;
                case 16:
                    SoundProfile soundProfile = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSoundProfile(soundProfile, i19);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string8 = parcel.readString();
                    SoundProfile soundProfile2 = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSoundProfile(string8, soundProfile2, i20);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string9 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSoundProfile(string9, i21);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string10 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean defaultSoundProfile = setDefaultSoundProfile(string10, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultSoundProfile);
                    return true;
                case 20:
                    int i23 = parcel.readInt();
                    String string11 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SoundProfile soundProfile3 = getSoundProfile(i23, string11, z4, i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(soundProfile3, 1);
                    return true;
                case 21:
                    String string12 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SoundProfile> soundProfilesByPackage = getSoundProfilesByPackage(string12, z5, i25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(soundProfilesByPackage, 1);
                    return true;
                case 22:
                    boolean z6 = parcel.readBoolean();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SoundProfile> availableSoundProfiles = getAvailableSoundProfiles(z6, i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableSoundProfiles, 1);
                    return true;
                case 23:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> soundProfilePackageNames = getSoundProfilePackageNames(i27);
                    parcel2.writeNoException();
                    parcel2.writeStringList(soundProfilePackageNames);
                    return true;
                case 24:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> soundProfileAllowList = getSoundProfileAllowList(i28);
                    parcel2.writeNoException();
                    parcel2.writeStringList(soundProfileAllowList);
                    return true;
                case 25:
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSoundProfileAllowList(arrayListCreateStringArrayList2, i29);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SoundProfileHandle> soundProfileHandle = getSoundProfileHandle(strArrCreateStringArray2, i30);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(soundProfileHandle, 1);
                    return true;
                case 27:
                    IPictureProfileCallback iPictureProfileCallbackAsInterface = IPictureProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPictureProfileCallback(iPictureProfileCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    ISoundProfileCallback iSoundProfileCallbackAsInterface = ISoundProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSoundProfileCallback(iSoundProfileCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IAmbientBacklightCallback iAmbientBacklightCallbackAsInterface = IAmbientBacklightCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAmbientBacklightCallback(iAmbientBacklightCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IActiveProcessingPictureListener iActiveProcessingPictureListenerAsInterface = IActiveProcessingPictureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerActiveProcessingPictureListener(iActiveProcessingPictureListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ParameterCapability> parameterCapabilities = getParameterCapabilities(arrayListCreateStringArrayList3, i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(parameterCapabilities, 1);
                    return true;
                case 32:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSupported = isSupported(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupported);
                    return true;
                case 33:
                    boolean z7 = parcel.readBoolean();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoPictureQualityEnabled(z7, i33);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAutoPictureQualityEnabled = isAutoPictureQualityEnabled(i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAutoPictureQualityEnabled);
                    return true;
                case 35:
                    boolean z8 = parcel.readBoolean();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSuperResolutionEnabled(z8, i35);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSuperResolutionEnabled = isSuperResolutionEnabled(i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSuperResolutionEnabled);
                    return true;
                case 37:
                    boolean z9 = parcel.readBoolean();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutoSoundQualityEnabled(z9, i37);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAutoSoundQualityEnabled = isAutoSoundQualityEnabled(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAutoSoundQualityEnabled);
                    return true;
                case 39:
                    AmbientBacklightSettings ambientBacklightSettings = (AmbientBacklightSettings) parcel.readTypedObject(AmbientBacklightSettings.CREATOR);
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAmbientBacklightSettings(ambientBacklightSettings, i39);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean z10 = parcel.readBoolean();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAmbientBacklightEnabled(z10, i40);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAmbientBacklightEnabled = isAmbientBacklightEnabled(i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAmbientBacklightEnabled);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pictureProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void updatePictureProfile(String str, PictureProfile pictureProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pictureProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void removePictureProfile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean setDefaultPictureProfile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public PictureProfile getPictureProfile(int i, String str, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PictureProfile) parcelObtain2.readTypedObject(PictureProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<PictureProfile> getPictureProfilesByPackage(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PictureProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<PictureProfile> getAvailablePictureProfiles(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PictureProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getPictureProfilePackageNames(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getPictureProfileAllowList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setPictureProfileAllowList(List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<PictureProfileHandle> getPictureProfileHandle(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PictureProfileHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public long getPictureProfileHandleValue(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public long getDefaultPictureProfileHandleValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void notifyPictureProfileHandleSelection(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public long getPictureProfileForTvInput(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void createSoundProfile(SoundProfile soundProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(soundProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void updateSoundProfile(String str, SoundProfile soundProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(soundProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void removeSoundProfile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean setDefaultSoundProfile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public SoundProfile getSoundProfile(int i, String str, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SoundProfile) parcelObtain2.readTypedObject(SoundProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<SoundProfile> getSoundProfilesByPackage(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SoundProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<SoundProfile> getAvailableSoundProfiles(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SoundProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getSoundProfilePackageNames(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<String> getSoundProfileAllowList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setSoundProfileAllowList(List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<SoundProfileHandle> getSoundProfileHandle(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SoundProfileHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerPictureProfileCallback(IPictureProfileCallback iPictureProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPictureProfileCallback);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerSoundProfileCallback(ISoundProfileCallback iSoundProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSoundProfileCallback);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerAmbientBacklightCallback(IAmbientBacklightCallback iAmbientBacklightCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAmbientBacklightCallback);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void registerActiveProcessingPictureListener(IActiveProcessingPictureListener iActiveProcessingPictureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iActiveProcessingPictureListener);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public List<ParameterCapability> getParameterCapabilities(List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ParameterCapability.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAutoPictureQualityEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isAutoPictureQualityEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setSuperResolutionEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isSuperResolutionEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAutoSoundQualityEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isAutoSoundQualityEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAmbientBacklightSettings(AmbientBacklightSettings ambientBacklightSettings, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(ambientBacklightSettings, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public void setAmbientBacklightEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IMediaQualityManager
            public boolean isAmbientBacklightEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMediaQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
