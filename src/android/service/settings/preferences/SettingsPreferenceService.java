package android.service.settings.preferences;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.OutcomeReceiver;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.service.settings.preferences.ISettingsPreferenceService;

/* loaded from: classes3.dex */
public abstract class SettingsPreferenceService extends Service {
    public static final String ACTION_PREFERENCE_SERVICE = "android.service.settings.preferences.action.PREFERENCE_SERVICE";

    public abstract void onGetAllPreferenceMetadata(MetadataRequest metadataRequest, OutcomeReceiver<MetadataResult, Exception> outcomeReceiver);

    public abstract void onGetPreferenceValue(GetValueRequest getValueRequest, OutcomeReceiver<GetValueResult, Exception> outcomeReceiver);

    public abstract void onSetPreferenceValue(SetValueRequest setValueRequest, OutcomeReceiver<SetValueResult, Exception> outcomeReceiver);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new ISettingsPreferenceService.Stub(PermissionEnforcer.fromContext(getApplicationContext())) { // from class: android.service.settings.preferences.SettingsPreferenceService.1
            @Override // android.service.settings.preferences.ISettingsPreferenceService
            public void getAllPreferenceMetadata(MetadataRequest metadataRequest, final IMetadataCallback iMetadataCallback) {
                getAllPreferenceMetadata_enforcePermission();
                SettingsPreferenceService.this.onGetAllPreferenceMetadata(metadataRequest, new OutcomeReceiver<MetadataResult, Exception>(this) { // from class: android.service.settings.preferences.SettingsPreferenceService.1.1
                    @Override // android.os.OutcomeReceiver
                    public void onResult(MetadataResult metadataResult) {
                        try {
                            iMetadataCallback.onSuccess(metadataResult);
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(Exception exc) {
                        try {
                            iMetadataCallback.onFailure();
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }
                });
            }

            @Override // android.service.settings.preferences.ISettingsPreferenceService
            public void getPreferenceValue(GetValueRequest getValueRequest, final IGetValueCallback iGetValueCallback) {
                getPreferenceValue_enforcePermission();
                SettingsPreferenceService.this.onGetPreferenceValue(getValueRequest, new OutcomeReceiver<GetValueResult, Exception>(this) { // from class: android.service.settings.preferences.SettingsPreferenceService.1.2
                    @Override // android.os.OutcomeReceiver
                    public void onResult(GetValueResult getValueResult) {
                        try {
                            iGetValueCallback.onSuccess(getValueResult);
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(Exception exc) {
                        try {
                            iGetValueCallback.onFailure();
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }
                });
            }

            @Override // android.service.settings.preferences.ISettingsPreferenceService
            public void setPreferenceValue(SetValueRequest setValueRequest, final ISetValueCallback iSetValueCallback) {
                setPreferenceValue_enforcePermission();
                SettingsPreferenceService.this.onSetPreferenceValue(setValueRequest, new OutcomeReceiver<SetValueResult, Exception>(this) { // from class: android.service.settings.preferences.SettingsPreferenceService.1.3
                    @Override // android.os.OutcomeReceiver
                    public void onResult(SetValueResult setValueResult) {
                        try {
                            iSetValueCallback.onSuccess(setValueResult);
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(Exception exc) {
                        try {
                            iSetValueCallback.onFailure();
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }
                });
            }
        };
    }
}
