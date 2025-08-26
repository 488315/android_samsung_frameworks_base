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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVoiceInteractionManagerService)) {
                return (IVoiceInteractionManagerService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showSession(bundle, i3, string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder = parcel.readStrongBinder();
                    IVoiceInteractionSession iVoiceInteractionSessionAsInterface = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    IVoiceInteractor iVoiceInteractorAsInterface = IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zDeliverNewSession = deliverNewSession(strongBinder, iVoiceInteractionSessionAsInterface, iVoiceInteractorAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeliverNewSession);
                    return true;
                case 3:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i4 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zShowSessionFromSession = showSessionFromSession(strongBinder2, bundle2, i4, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowSessionFromSession);
                    return true;
                case 4:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zHideSessionFromSession = hideSessionFromSession(strongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHideSessionFromSession);
                    return true;
                case 5:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStartVoiceActivity = startVoiceActivity(strongBinder4, intent, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartVoiceActivity);
                    return true;
                case 6:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartAssistantActivity = startAssistantActivity(strongBinder5, intent2, string5, string6, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartAssistantActivity);
                    return true;
                case 7:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeepAwake(strongBinder6, z);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(strongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    finish(strongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisabledShowContext(i5);
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
                    int i6 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = getKeyphraseSoundModel(i6, string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyphraseSoundModel, 1);
                    return true;
                case 14:
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel2 = (SoundTrigger.KeyphraseSoundModel) parcel.readTypedObject(SoundTrigger.KeyphraseSoundModel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iUpdateKeyphraseSoundModel = updateKeyphraseSoundModel(keyphraseSoundModel2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateKeyphraseSoundModel);
                    return true;
                case 15:
                    int i7 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iDeleteKeyphraseSoundModel = deleteKeyphraseSoundModel(i7, string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeleteKeyphraseSoundModel);
                    return true;
                case 16:
                    boolean z2 = parcel.readBoolean();
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setModelDatabaseForTestEnabled(z2, strongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i8 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEnrolledForKeyphrase = isEnrolledForKeyphrase(i8, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnrolledForKeyphrase);
                    return true;
                case 18:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyphraseMetadata enrolledKeyphraseMetadata = getEnrolledKeyphraseMetadata(string10, string11);
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
                    int i9 = parcel.readInt();
                    String string12 = parcel.readString();
                    IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallbackAsInterface = IVoiceInteractionSessionShowCallback.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zShowSessionForActiveService = showSessionForActiveService(bundle4, i9, string12, iVoiceInteractionSessionShowCallbackAsInterface, strongBinder10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowSessionForActiveService);
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
                    boolean zIsSessionRunning = isSessionRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSessionRunning);
                    return true;
                case 24:
                    boolean zActiveServiceSupportsAssist = activeServiceSupportsAssist();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActiveServiceSupportsAssist);
                    return true;
                case 25:
                    boolean zActiveServiceSupportsLaunchFromKeyguard = activeServiceSupportsLaunchFromKeyguard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActiveServiceSupportsLaunchFromKeyguard);
                    return true;
                case 26:
                    onLockscreenShown();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IVoiceInteractionSessionListener iVoiceInteractionSessionListenerAsInterface = IVoiceInteractionSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVoiceInteractionSessionListener(iVoiceInteractionSessionListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    IVoiceActionCheckCallback iVoiceActionCheckCallbackAsInterface = IVoiceActionCheckCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getActiveServiceSupportedActions(arrayListCreateStringArrayList, iVoiceActionCheckCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiHints(bundle5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    int i10 = parcel.readInt();
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDirectActions(strongBinder11, i10, strongBinder12, remoteCallback, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    String string13 = parcel.readString();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i11 = parcel.readInt();
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    performDirectAction(strongBinder13, string13, bundle6, i11, strongBinder14, remoteCallback3, remoteCallback4);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisabled(z3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    Identity identity = (Identity) parcel.readTypedObject(Identity.CREATOR);
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    SoundTrigger.ModuleProperties moduleProperties = (SoundTrigger.ModuleProperties) parcel.readTypedObject(SoundTrigger.ModuleProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractionSoundTriggerSession iVoiceInteractionSoundTriggerSessionCreateSoundTriggerSessionAsOriginator = createSoundTriggerSessionAsOriginator(identity, strongBinder15, moduleProperties);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iVoiceInteractionSoundTriggerSessionCreateSoundTriggerSessionAsOriginator);
                    return true;
                case 34:
                    Identity identity2 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SoundTrigger.ModuleProperties> listListModuleProperties = listModuleProperties(identity2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listListModuleProperties, 1);
                    return true;
                case 35:
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateState(persistableBundle, sharedMemory, strongBinder16);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    Identity identity3 = (Identity) parcel.readTypedObject(Identity.CREATOR);
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory2 = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallbackAsInterface = IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initAndVerifyDetector(identity3, persistableBundle2, sharedMemory2, strongBinder17, iHotwordRecognitionStatusCallbackAsInterface, i12);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    destroyDetector(strongBinder18);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    shutdownHotwordDetectionService();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListenerAsInterface = IVisualQueryRecognitionStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeVisualQueryRecognitionStatus(iVisualQueryRecognitionStatusListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListenerAsInterface = IVisualQueryDetectionAttentionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableVisualQueryDetection(iVisualQueryDetectionAttentionListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    disableVisualQueryDetection();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallbackAsInterface = IVisualQueryDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startPerceiving(iVisualQueryDetectionVoiceInteractionCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    stopPerceiving();
                    parcel2.writeNoException();
                    return true;
                case 44:
                    AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                    IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallbackAsInterface = IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startListeningFromMic(audioFormat, iMicrophoneHotwordDetectionVoiceInteractionCallbackAsInterface);
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
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallbackAsInterface2 = IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startListeningFromExternalSource(parcelFileDescriptor, audioFormat2, persistableBundle3, strongBinder19, iMicrophoneHotwordDetectionVoiceInteractionCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent = (SoundTrigger.KeyphraseRecognitionEvent) parcel.readTypedObject(SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
                    IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallbackAsInterface2 = IHotwordRecognitionStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    triggerHardwareRecognitionEventForTest(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startListeningVisibleActivityChanged(strongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopListeningVisibleActivityChanged(strongBinder21);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSessionWindowVisible(strongBinder22, z4);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActivityEventChanged(strongBinder23, i13);
                    return true;
                case 52:
                    boolean accessibilityDetectionEnabled = getAccessibilityDetectionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accessibilityDetectionEnabled);
                    return true;
                case 53:
                    IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListenerAsInterface = IVoiceInteractionAccessibilitySettingsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAccessibilityDetectionSettingsListener(iVoiceInteractionAccessibilitySettingsListenerAsInterface);
                    return true;
                case 54:
                    IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListenerAsInterface2 = IVoiceInteractionAccessibilitySettingsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAccessibilityDetectionSettingsListener(iVoiceInteractionAccessibilitySettingsListenerAsInterface2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean deliverNewSession(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iVoiceInteractionSession);
                    parcelObtain.writeStrongInterface(iVoiceInteractor);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean showSessionFromSession(IBinder iBinder, Bundle bundle, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean hideSessionFromSession(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int startVoiceActivity(IBinder iBinder, Intent intent, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int startAssistantActivity(IBinder iBinder, Intent intent, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setKeepAwake(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void closeSystemDialogs(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void finish(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setDisabledShowContext(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int getDisabledShowContext() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int getUserDisabledShowContext() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SoundTrigger.KeyphraseSoundModel) parcelObtain2.readTypedObject(SoundTrigger.KeyphraseSoundModel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyphraseSoundModel, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public int deleteKeyphraseSoundModel(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setModelDatabaseForTestEnabled(boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean isEnrolledForKeyphrase(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public KeyphraseMetadata getEnrolledKeyphraseMetadata(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyphraseMetadata) parcelObtain2.readTypedObject(KeyphraseMetadata.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public ComponentName getActiveServiceComponentName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean showSessionForActiveService(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iVoiceInteractionSessionShowCallback);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void hideCurrentSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void launchVoiceAssistFromKeyguard() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean isSessionRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean activeServiceSupportsAssist() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void onLockscreenShown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoiceInteractionSessionListener);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iVoiceActionCheckCallback);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setUiHints(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void requestDirectActions(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void performDirectAction(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setDisabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(Identity identity, IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(moduleProperties, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IVoiceInteractionSoundTriggerSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public List<SoundTrigger.ModuleProperties> listModuleProperties(Identity identity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SoundTrigger.ModuleProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeTypedObject(sharedMemory, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void initAndVerifyDetector(Identity identity, PersistableBundle persistableBundle, SharedMemory sharedMemory, IBinder iBinder, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(identity, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeTypedObject(sharedMemory, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void destroyDetector(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void shutdownHotwordDetectionService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void subscribeVisualQueryRecognitionStatus(IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVisualQueryRecognitionStatusListener);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void enableVisualQueryDetection(IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVisualQueryDetectionAttentionListener);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void disableVisualQueryDetection() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startPerceiving(IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVisualQueryDetectionVoiceInteractionCallback);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopPerceiving() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningFromMic(AudioFormat audioFormat, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFormat, 0);
                    parcelObtain.writeStrongInterface(iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopListeningFromMic() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningFromExternalSource(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, IBinder iBinder, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(audioFormat, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void triggerHardwareRecognitionEventForTest(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyphraseRecognitionEvent, 0);
                    parcelObtain.writeStrongInterface(iHotwordRecognitionStatusCallback);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void startListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void stopListeningVisibleActivityChanged(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void setSessionWindowVisible(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void notifyActivityEventChanged(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public boolean getAccessibilityDetectionEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void registerAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoiceInteractionAccessibilitySettingsListener);
                    this.mRemote.transact(53, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractionManagerService
            public void unregisterAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoiceInteractionAccessibilitySettingsListener);
                    this.mRemote.transact(54, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
