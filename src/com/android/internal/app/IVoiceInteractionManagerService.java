package com.android.internal.app;

import android.Manifest;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback;
import android.service.voice.IVisualQueryDetectionVoiceInteractionCallback;
import android.service.voice.IVoiceInteractionSession;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVisualQueryDetectionAttentionListener;
import com.android.internal.app.IVisualQueryRecognitionStatusListener;
import com.android.internal.app.IVoiceActionCheckCallback;
import com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.app.IVoiceInteractionSoundTriggerSession;
import com.android.internal.app.IVoiceInteractor;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IVoiceInteractionManagerService extends IInterface {

    public static class Default implements IVoiceInteractionManagerService {
        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean activeServiceSupportsAssist() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void closeSystemDialogs(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(Identity identity, IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int deleteKeyphraseSoundModel(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean deliverNewSession(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void destroyDetector(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void disableVisualQueryDetection() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void enableVisualQueryDetection(IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void finish(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean getAccessibilityDetectionEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public ComponentName getActiveServiceComponentName() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int getDisabledShowContext() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public KeyphraseMetadata getEnrolledKeyphraseMetadata(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int getUserDisabledShowContext() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void hideCurrentSession() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean hideSessionFromSession(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void initAndVerifyDetector(Identity identity, PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean isEnrolledForKeyphrase(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean isSessionRunning() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void launchVoiceAssistFromKeyguard() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void notifyActivityEventChanged(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void onLockscreenShown() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void performDirectAction(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void registerAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void requestDirectActions(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setDisabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setDisabledShowContext(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setKeepAwake(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setModelDatabaseForTestEnabled(boolean z, IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setSessionWindowVisible(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void setUiHints(Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void showSession(Bundle bundle, int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean showSessionForActiveService(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public boolean showSessionFromSession(IBinder iBinder, Bundle bundle, int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void shutdownHotwordDetectionService() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int startAssistantActivity(IBinder iBinder, Intent intent, String str, String str2, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startListeningFromExternalSource(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, IBinder iBinder, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startListeningFromMic(AudioFormat audioFormat, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void startPerceiving(IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int startVoiceActivity(IBinder iBinder, Intent intent, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void stopListeningFromMic() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void stopListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void stopPerceiving() throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void subscribeVisualQueryRecognitionStatus(IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void triggerHardwareRecognitionEventForTest(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void unregisterAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IVoiceInteractionManagerService
        public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder) throws RemoteException {
        }
    }

    boolean activeServiceSupportsAssist() throws RemoteException;

    boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException;

    void closeSystemDialogs(IBinder iBinder) throws RemoteException;

    IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(Identity identity, IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties) throws RemoteException;

    int deleteKeyphraseSoundModel(int i, String str) throws RemoteException;

    boolean deliverNewSession(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) throws RemoteException;

    void destroyDetector(IBinder iBinder) throws RemoteException;

    void disableVisualQueryDetection() throws RemoteException;

    void enableVisualQueryDetection(IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws RemoteException;

    void finish(IBinder iBinder) throws RemoteException;

    boolean getAccessibilityDetectionEnabled() throws RemoteException;

    ComponentName getActiveServiceComponentName() throws RemoteException;

    void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException;

    int getDisabledShowContext() throws RemoteException;

    KeyphraseMetadata getEnrolledKeyphraseMetadata(String str, String str2) throws RemoteException;

    SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str) throws RemoteException;

    int getUserDisabledShowContext() throws RemoteException;

    void hideCurrentSession() throws RemoteException;

    boolean hideSessionFromSession(IBinder iBinder) throws RemoteException;

    void initAndVerifyDetector(Identity identity, PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws RemoteException;

    boolean isEnrolledForKeyphrase(int i, String str) throws RemoteException;

    boolean isSessionRunning() throws RemoteException;

    void launchVoiceAssistFromKeyguard() throws RemoteException;

    List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException;

    void notifyActivityEventChanged(IBinder iBinder, int i) throws RemoteException;

    void onLockscreenShown() throws RemoteException;

    void performDirectAction(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void registerAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException;

    void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws RemoteException;

    void requestDirectActions(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void setDisabled(boolean z) throws RemoteException;

    void setDisabledShowContext(int i) throws RemoteException;

    void setKeepAwake(IBinder iBinder, boolean z) throws RemoteException;

    void setModelDatabaseForTestEnabled(boolean z, IBinder iBinder) throws RemoteException;

    void setSessionWindowVisible(IBinder iBinder, boolean z) throws RemoteException;

    void setUiHints(Bundle bundle) throws RemoteException;

    void showSession(Bundle bundle, int i, String str) throws RemoteException;

    boolean showSessionForActiveService(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) throws RemoteException;

    boolean showSessionFromSession(IBinder iBinder, Bundle bundle, int i, String str) throws RemoteException;

    void shutdownHotwordDetectionService() throws RemoteException;

    int startAssistantActivity(IBinder iBinder, Intent intent, String str, String str2, Bundle bundle) throws RemoteException;

    void startListeningFromExternalSource(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, IBinder iBinder, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException;

    void startListeningFromMic(AudioFormat audioFormat, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException;

    void startListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException;

    void startPerceiving(IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws RemoteException;

    int startVoiceActivity(IBinder iBinder, Intent intent, String str, String str2) throws RemoteException;

    void stopListeningFromMic() throws RemoteException;

    void stopListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException;

    void stopPerceiving() throws RemoteException;

    void subscribeVisualQueryRecognitionStatus(IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws RemoteException;

    void triggerHardwareRecognitionEventForTest(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException;

    void unregisterAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException;

    int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException;

    void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractionManagerService {
        public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionManagerService";
        static final int TRANSACTION_activeServiceSupportsAssist = 24;
        static final int TRANSACTION_activeServiceSupportsLaunchFromKeyguard = 25;
        static final int TRANSACTION_closeSystemDialogs = 8;
        static final int TRANSACTION_createSoundTriggerSessionAsOriginator = 33;
        static final int TRANSACTION_deleteKeyphraseSoundModel = 15;
        static final int TRANSACTION_deliverNewSession = 2;
        static final int TRANSACTION_destroyDetector = 37;
        static final int TRANSACTION_disableVisualQueryDetection = 41;
        static final int TRANSACTION_enableVisualQueryDetection = 40;
        static final int TRANSACTION_finish = 9;
        static final int TRANSACTION_getAccessibilityDetectionEnabled = 52;
        static final int TRANSACTION_getActiveServiceComponentName = 19;
        static final int TRANSACTION_getActiveServiceSupportedActions = 28;
        static final int TRANSACTION_getDisabledShowContext = 11;
        static final int TRANSACTION_getEnrolledKeyphraseMetadata = 18;
        static final int TRANSACTION_getKeyphraseSoundModel = 13;
        static final int TRANSACTION_getUserDisabledShowContext = 12;
        static final int TRANSACTION_hideCurrentSession = 21;
        static final int TRANSACTION_hideSessionFromSession = 4;
        static final int TRANSACTION_initAndVerifyDetector = 36;
        static final int TRANSACTION_isEnrolledForKeyphrase = 17;
        static final int TRANSACTION_isSessionRunning = 23;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 22;
        static final int TRANSACTION_listModuleProperties = 34;
        static final int TRANSACTION_notifyActivityEventChanged = 51;
        static final int TRANSACTION_onLockscreenShown = 26;
        static final int TRANSACTION_performDirectAction = 31;
        static final int TRANSACTION_registerAccessibilityDetectionSettingsListener = 53;
        static final int TRANSACTION_registerVoiceInteractionSessionListener = 27;
        static final int TRANSACTION_requestDirectActions = 30;
        static final int TRANSACTION_setDisabled = 32;
        static final int TRANSACTION_setDisabledShowContext = 10;
        static final int TRANSACTION_setKeepAwake = 7;
        static final int TRANSACTION_setModelDatabaseForTestEnabled = 16;
        static final int TRANSACTION_setSessionWindowVisible = 50;
        static final int TRANSACTION_setUiHints = 29;
        static final int TRANSACTION_showSession = 1;
        static final int TRANSACTION_showSessionForActiveService = 20;
        static final int TRANSACTION_showSessionFromSession = 3;
        static final int TRANSACTION_shutdownHotwordDetectionService = 38;
        static final int TRANSACTION_startAssistantActivity = 6;
        static final int TRANSACTION_startListeningFromExternalSource = 46;
        static final int TRANSACTION_startListeningFromMic = 44;
        static final int TRANSACTION_startListeningVisibleActivityChanged = 48;
        static final int TRANSACTION_startPerceiving = 42;
        static final int TRANSACTION_startVoiceActivity = 5;
        static final int TRANSACTION_stopListeningFromMic = 45;
        static final int TRANSACTION_stopListeningVisibleActivityChanged = 49;
        static final int TRANSACTION_stopPerceiving = 43;
        static final int TRANSACTION_subscribeVisualQueryRecognitionStatus = 39;
        static final int TRANSACTION_triggerHardwareRecognitionEventForTest = 47;
        static final int TRANSACTION_unregisterAccessibilityDetectionSettingsListener = 54;
        static final int TRANSACTION_updateKeyphraseSoundModel = 14;
        static final int TRANSACTION_updateState = 35;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 53;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IVoiceInteractionManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractionManagerService)) {
                return (IVoiceInteractionManagerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showSession";
                case 2:
                    return "deliverNewSession";
                case 3:
                    return "showSessionFromSession";
                case 4:
                    return "hideSessionFromSession";
                case 5:
                    return "startVoiceActivity";
                case 6:
                    return "startAssistantActivity";
                case 7:
                    return "setKeepAwake";
                case 8:
                    return "closeSystemDialogs";
                case 9:
                    return "finish";
                case 10:
                    return "setDisabledShowContext";
                case 11:
                    return "getDisabledShowContext";
                case 12:
                    return "getUserDisabledShowContext";
                case 13:
                    return "getKeyphraseSoundModel";
                case 14:
                    return "updateKeyphraseSoundModel";
                case 15:
                    return "deleteKeyphraseSoundModel";
                case 16:
                    return "setModelDatabaseForTestEnabled";
                case 17:
                    return "isEnrolledForKeyphrase";
                case 18:
                    return "getEnrolledKeyphraseMetadata";
                case 19:
                    return "getActiveServiceComponentName";
                case 20:
                    return "showSessionForActiveService";
                case 21:
                    return "hideCurrentSession";
                case 22:
                    return "launchVoiceAssistFromKeyguard";
                case 23:
                    return "isSessionRunning";
                case 24:
                    return "activeServiceSupportsAssist";
                case 25:
                    return "activeServiceSupportsLaunchFromKeyguard";
                case 26:
                    return "onLockscreenShown";
                case 27:
                    return "registerVoiceInteractionSessionListener";
                case 28:
                    return "getActiveServiceSupportedActions";
                case 29:
                    return "setUiHints";
                case 30:
                    return "requestDirectActions";
                case 31:
                    return "performDirectAction";
                case 32:
                    return "setDisabled";
                case 33:
                    return "createSoundTriggerSessionAsOriginator";
                case 34:
                    return "listModuleProperties";
                case 35:
                    return "updateState";
                case 36:
                    return "initAndVerifyDetector";
                case 37:
                    return "destroyDetector";
                case 38:
                    return "shutdownHotwordDetectionService";
                case 39:
                    return "subscribeVisualQueryRecognitionStatus";
                case 40:
                    return "enableVisualQueryDetection";
                case 41:
                    return "disableVisualQueryDetection";
                case 42:
                    return "startPerceiving";
                case 43:
                    return "stopPerceiving";
                case 44:
                    return "startListeningFromMic";
                case 45:
                    return "stopListeningFromMic";
                case 46:
                    return "startListeningFromExternalSource";
                case 47:
                    return "triggerHardwareRecognitionEventForTest";
                case 48:
                    return "startListeningVisibleActivityChanged";
                case 49:
                    return "stopListeningVisibleActivityChanged";
                case 50:
                    return "setSessionWindowVisible";
                case 51:
                    return "notifyActivityEventChanged";
                case 52:
                    return "getAccessibilityDetectionEnabled";
                case 53:
                    return "registerAccessibilityDetectionSettingsListener";
                case 54:
                    return "unregisterAccessibilityDetectionSettingsListener";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showSession(bundle, readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IVoiceInteractionSession asInterface = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    IVoiceInteractor asInterface2 = IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean deliverNewSession = deliverNewSession(readStrongBinder, asInterface, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deliverNewSession);
                    return true;
                case 3:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean showSessionFromSession = showSessionFromSession(readStrongBinder2, bundle2, readInt2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showSessionFromSession);
                    return true;
                case 4:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean hideSessionFromSession = hideSessionFromSession(readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideSessionFromSession);
                    return true;
                case 5:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startVoiceActivity = startVoiceActivity(readStrongBinder4, intent, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(startVoiceActivity);
                    return true;
                case 6:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startAssistantActivity = startAssistantActivity(readStrongBinder5, intent2, readString5, readString6, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAssistantActivity);
                    return true;
                case 7:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeepAwake(readStrongBinder6, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    finish(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisabledShowContext(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int disabledShowContext = getDisabledShowContext();
                    parcel2.writeNoException();
                    parcel2.writeInt(disabledShowContext);
                    return true;
                case 12:
                    int userDisabledShowContext = getUserDisabledShowContext();
                    parcel2.writeNoException();
                    parcel2.writeInt(userDisabledShowContext);
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = getKeyphraseSoundModel(readInt4, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyphraseSoundModel, 1);
                    return true;
                case 14:
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel2 = (SoundTrigger.KeyphraseSoundModel) parcel.readTypedObject(SoundTrigger.KeyphraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int updateKeyphraseSoundModel = updateKeyphraseSoundModel(keyphraseSoundModel2);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateKeyphraseSoundModel);
                    return true;
                case 15:
                    int readInt5 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteKeyphraseSoundModel = deleteKeyphraseSoundModel(readInt5, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteKeyphraseSoundModel);
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setModelDatabaseForTestEnabled(readBoolean2, readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt6 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEnrolledForKeyphrase = isEnrolledForKeyphrase(readInt6, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnrolledForKeyphrase);
                    return true;
                case 18:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyphraseMetadata enrolledKeyphraseMetadata = getEnrolledKeyphraseMetadata(readString10, readString11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enrolledKeyphraseMetadata, 1);
                    return true;
                case 19:
                    ComponentName activeServiceComponentName = getActiveServiceComponentName();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeServiceComponentName, 1);
                    return true;
                case 20:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt7 = parcel.readInt();
                    String readString12 = parcel.readString();
                    IVoiceInteractionSessionShowCallback asInterface3 = IVoiceInteractionSessionShowCallback.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean showSessionForActiveService = showSessionForActiveService(bundle4, readInt7, readString12, asInterface3, readStrongBinder10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showSessionForActiveService);
                    return true;
                case 21:
                    hideCurrentSession();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    launchVoiceAssistFromKeyguard();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean isSessionRunning = isSessionRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSessionRunning);
                    return true;
                case 24:
                    boolean activeServiceSupportsAssist = activeServiceSupportsAssist();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeServiceSupportsAssist);
                    return true;
                case 25:
                    boolean activeServiceSupportsLaunchFromKeyguard = activeServiceSupportsLaunchFromKeyguard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeServiceSupportsLaunchFromKeyguard);
                    return true;
                case 26:
                    onLockscreenShown();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IVoiceInteractionSessionListener asInterface4 = IVoiceInteractionSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVoiceInteractionSessionListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    IVoiceActionCheckCallback asInterface5 = IVoiceActionCheckCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getActiveServiceSupportedActions(createStringArrayList, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiHints(bundle5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDirectActions(readStrongBinder11, readInt8, readStrongBinder12, remoteCallback, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    String readString13 = parcel.readString();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt9 = parcel.readInt();
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    performDirectAction(readStrongBinder13, readString13, bundle6, readInt9, readStrongBinder14, remoteCallback3, remoteCallback4);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    Identity identity = (Identity) parcel.readTypedObject(Identity.CREATOR);
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    SoundTrigger.ModuleProperties moduleProperties = (SoundTrigger.ModuleProperties) parcel.readTypedObject(SoundTrigger.ModuleProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator = createSoundTriggerSessionAsOriginator(identity, readStrongBinder15, moduleProperties);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createSoundTriggerSessionAsOriginator);
                    return true;
                case 34:
                    Identity identity2 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SoundTrigger.ModuleProperties> listModuleProperties = listModuleProperties(identity2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listModuleProperties, 1);
                    return true;
                case 35:
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateState(persistableBundle, sharedMemory, readStrongBinder16);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    Identity identity3 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory2 = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    IHotwordRecognitionStatusCallback asInterface6 = IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initAndVerifyDetector(identity3, persistableBundle2, sharedMemory2, readStrongBinder17, asInterface6, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    destroyDetector(readStrongBinder18);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    shutdownHotwordDetectionService();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IVisualQueryRecognitionStatusListener asInterface7 = IVisualQueryRecognitionStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeVisualQueryRecognitionStatus(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IVisualQueryDetectionAttentionListener asInterface8 = IVisualQueryDetectionAttentionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableVisualQueryDetection(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    disableVisualQueryDetection();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IVisualQueryDetectionVoiceInteractionCallback asInterface9 = IVisualQueryDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startPerceiving(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    stopPerceiving();
                    parcel2.writeNoException();
                    return true;
                case 44:
                    AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                    IMicrophoneHotwordDetectionVoiceInteractionCallback asInterface10 = IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startListeningFromMic(audioFormat, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    stopListeningFromMic();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    AudioFormat audioFormat2 = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                    PersistableBundle persistableBundle3 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    IMicrophoneHotwordDetectionVoiceInteractionCallback asInterface11 = IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startListeningFromExternalSource(parcelFileDescriptor, audioFormat2, persistableBundle3, readStrongBinder19, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    IHotwordRecognitionStatusCallback asInterface12 = IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    triggerHardwareRecognitionEventForTest(keyphraseRecognitionEvent, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startListeningVisibleActivityChanged(readStrongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopListeningVisibleActivityChanged(readStrongBinder21);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder readStrongBinder22 = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSessionWindowVisible(readStrongBinder22, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder readStrongBinder23 = parcel.readStrongBinder();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActivityEventChanged(readStrongBinder23, readInt11);
                    return true;
                case 52:
                    boolean accessibilityDetectionEnabled = getAccessibilityDetectionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accessibilityDetectionEnabled);
                    return true;
                case 53:
                    IVoiceInteractionAccessibilitySettingsListener asInterface13 = IVoiceInteractionAccessibilitySettingsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAccessibilityDetectionSettingsListener(asInterface13);
                    return true;
                case 54:
                    IVoiceInteractionAccessibilitySettingsListener asInterface14 = IVoiceInteractionAccessibilitySettingsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAccessibilityDetectionSettingsListener(asInterface14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoiceInteractionManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void showSession(Bundle bundle, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean deliverNewSession(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    obtain.writeStrongInterface(iVoiceInteractor);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean showSessionFromSession(IBinder iBinder, Bundle bundle, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean hideSessionFromSession(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int startVoiceActivity(IBinder iBinder, Intent intent, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int startAssistantActivity(IBinder iBinder, Intent intent, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setKeepAwake(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void closeSystemDialogs(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void finish(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setDisabledShowContext(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int getDisabledShowContext() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int getUserDisabledShowContext() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTrigger.KeyphraseSoundModel) obtain2.readTypedObject(SoundTrigger.KeyphraseSoundModel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseSoundModel, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int deleteKeyphraseSoundModel(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setModelDatabaseForTestEnabled(boolean z, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean isEnrolledForKeyphrase(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public KeyphraseMetadata getEnrolledKeyphraseMetadata(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyphraseMetadata) obtain2.readTypedObject(KeyphraseMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public ComponentName getActiveServiceComponentName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean showSessionForActiveService(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractionSessionShowCallback);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void hideCurrentSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void launchVoiceAssistFromKeyguard() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean isSessionRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean activeServiceSupportsAssist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void onLockscreenShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionSessionListener);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iVoiceActionCheckCallback);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setUiHints(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void requestDirectActions(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void performDirectAction(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setDisabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(Identity identity, IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(moduleProperties, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoiceInteractionSoundTriggerSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void initAndVerifyDetector(Identity identity, PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void destroyDetector(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void shutdownHotwordDetectionService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void subscribeVisualQueryRecognitionStatus(IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVisualQueryRecognitionStatusListener);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void enableVisualQueryDetection(IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVisualQueryDetectionAttentionListener);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void disableVisualQueryDetection() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startPerceiving(IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVisualQueryDetectionVoiceInteractionCallback);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopPerceiving() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningFromMic(AudioFormat audioFormat, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeStrongInterface(iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopListeningFromMic() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningFromExternalSource(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, IBinder iBinder, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void triggerHardwareRecognitionEventForTest(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    obtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setSessionWindowVisible(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void notifyActivityEventChanged(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean getAccessibilityDetectionEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void registerAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionAccessibilitySettingsListener);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void unregisterAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionAccessibilitySettingsListener);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void setModelDatabaseForTestEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_VOICE_KEYPHRASES, getCallingPid(), getCallingUid());
        }

        protected void showSessionForActiveService_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void hideCurrentSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void launchVoiceAssistFromKeyguard_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void isSessionRunning_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void activeServiceSupportsAssist_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void activeServiceSupportsLaunchFromKeyguard_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void onLockscreenShown_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void registerVoiceInteractionSessionListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void getActiveServiceSupportedActions_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void setDisabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void updateState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_HOTWORD_DETECTION, getCallingPid(), getCallingUid());
        }

        protected void initAndVerifyDetector_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_HOTWORD_DETECTION, getCallingPid(), getCallingUid());
        }

        protected void subscribeVisualQueryRecognitionStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void enableVisualQueryDetection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void disableVisualQueryDetection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VOICE_INTERACTION_SERVICE, getCallingPid(), getCallingUid());
        }
    }
}
