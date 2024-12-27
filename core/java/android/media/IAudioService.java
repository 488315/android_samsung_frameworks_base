package android.media;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.AttributionSource;
import android.media.IAudioDeviceVolumeDispatcher;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioModeDispatcher;
import android.media.IAudioRoutesObserver;
import android.media.IAudioServerStateDispatcher;
import android.media.ICapturePresetDevicesRoleDispatcher;
import android.media.ICommunicationDeviceDispatcher;
import android.media.IDeviceVolumeBehaviorDispatcher;
import android.media.IDevicesForAttributesCallback;
import android.media.ILoudnessCodecUpdatesDispatcher;
import android.media.IMuteAwaitConnectionCallback;
import android.media.IPlaybackConfigDispatcher;
import android.media.IPreferredMixerAttributesDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IRingtonePlayer;
import android.media.ISpatializerCallback;
import android.media.ISpatializerHeadToSoundStagePoseCallback;
import android.media.ISpatializerHeadTrackerAvailableCallback;
import android.media.ISpatializerHeadTrackingModeCallback;
import android.media.ISpatializerOutputCallback;
import android.media.IStrategyNonDefaultDevicesDispatcher;
import android.media.IStrategyPreferredDevicesDispatcher;
import android.media.IStreamAliasingDispatcher;
import android.media.IVolumeController;
import android.media.PlayerBase;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.IMediaProjection;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.view.KeyEvent;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IAudioService extends IInterface {
    int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException;

    int abandonAudioFocusForTest(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException;

    void addAssistantServicesUids(int[] iArr) throws RemoteException;

    void addLoudnessCodecInfo(int i, int i2, LoudnessCodecInfo loudnessCodecInfo) throws RemoteException;

    int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException;

    void addPackage(int i, String str) throws RemoteException;

    void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    void adjustStreamVolume(int i, int i2, int i3, String str) throws RemoteException;

    void adjustStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException;

    void adjustStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) throws RemoteException;

    void adjustSuggestedStreamVolume(int i, int i2, int i3) throws RemoteException;

    void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException;

    void adjustVolume(int i, int i2) throws RemoteException;

    void adjustVolumeGroupVolume(int i, int i2, int i3, String str) throws RemoteException;

    boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException;

    boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat) throws RemoteException;

    void cancelMuteAwaitConnection(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int clearFadeManagerConfigurationForFocusLoss() throws RemoteException;

    int clearPreferredDevicesForCapturePreset(int i) throws RemoteException;

    int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i) throws RemoteException;

    void disableSafeMediaVolume(String str) throws RemoteException;

    void dismissVolumePanel() throws RemoteException;

    int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    int dispatchFocusChangeWithFade(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback, List<AudioFocusInfo> list, FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException;

    boolean enterAudioFocusFreezeForTest(IBinder iBinder, int[] iArr) throws RemoteException;

    boolean exitAudioFocusFreezeForTest(IBinder iBinder) throws RemoteException;

    void forceComputeCsdOnAllDevices(boolean z) throws RemoteException;

    void forceRemoteSubmixFullVolume(boolean z, IBinder iBinder) throws RemoteException;

    void forceUseFrameworkMel(boolean z) throws RemoteException;

    void forceVolumeControlStream(int i, IBinder iBinder) throws RemoteException;

    int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

    int[] getActiveAssistantServiceUids() throws RemoteException;

    List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException;

    List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException;

    int getActualHeadTrackingMode() throws RemoteException;

    long getAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int getAllowedCapturePolicy() throws RemoteException;

    int getAppDevice(int i) throws RemoteException;

    int getAppVolume(int i) throws RemoteException;

    int[] getAssistantServicesUids() throws RemoteException;

    List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws RemoteException;

    String getAudioServiceConfig(String str) throws RemoteException;

    List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException;

    int[] getAvailableCommunicationDeviceIds() throws RemoteException;

    int getBluetoothAudioDeviceCategory(String str) throws RemoteException;

    int getBluetoothAudioDeviceCategory_legacy(String str, boolean z) throws RemoteException;

    int getCommunicationDevice() throws RemoteException;

    float getCsd() throws RemoteException;

    int getCurrentAudioFocus() throws RemoteException;

    String getCurrentAudioFocusPackageName() throws RemoteException;

    VolumeInfo getDefaultVolumeInfo() throws RemoteException;

    int getDesiredHeadTrackingMode() throws RemoteException;

    int getDeviceMaskForStream(int i) throws RemoteException;

    VolumeInfo getDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException;

    int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes audioAttributes) throws RemoteException;

    List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(AudioAttributes audioAttributes) throws RemoteException;

    int getEarProtectLimit() throws RemoteException;

    int getEncodedSurroundMode(int i) throws RemoteException;

    List<String> getExcludedRingtoneTitles(int i) throws RemoteException;

    FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws RemoteException;

    long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) throws RemoteException;

    int getFineVolume(int i, int i2) throws RemoteException;

    float[] getFloatVolumeTable() throws RemoteException;

    List getFocusDuckedUidsForTest() throws RemoteException;

    long getFocusFadeOutDurationForTest() throws RemoteException;

    int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) throws RemoteException;

    List<AudioFocusInfo> getFocusStack() throws RemoteException;

    long getFocusUnmuteDelayAfterFadeOutForTest() throws RemoteException;

    AudioHalVersionInfo getHalVersion() throws RemoteException;

    List getIndependentStreamTypes() throws RemoteException;

    int getLastAudibleStreamVolume(int i) throws RemoteException;

    int getLastAudibleVolumeForVolumeGroup(int i) throws RemoteException;

    PersistableBundle getLoudnessParams(LoudnessCodecInfo loudnessCodecInfo) throws RemoteException;

    long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int[] getMediaVolumeSteps() throws RemoteException;

    int getMicModeType() throws RemoteException;

    int getMode() throws RemoteException;

    int getModeInternal() throws RemoteException;

    int getMuteInterval() throws RemoteException;

    AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException;

    List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) throws RemoteException;

    float getOutputRs2UpperBound() throws RemoteException;

    String getPinAppInfo(int i) throws RemoteException;

    int getPinDevice() throws RemoteException;

    List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) throws RemoteException;

    List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) throws RemoteException;

    int getPrevRingerMode() throws RemoteException;

    int getRadioOutputPath() throws RemoteException;

    List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws RemoteException;

    int getRemainingMuteIntervalMs() throws RemoteException;

    List getReportedSurroundFormats() throws RemoteException;

    int getRingerModeExternal() throws RemoteException;

    int getRingerModeInternal() throws RemoteException;

    IRingtonePlayer getRingtonePlayer() throws RemoteException;

    String[] getSelectedAppList() throws RemoteException;

    List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws RemoteException;

    int getSpatializerImmersiveAudioLevel() throws RemoteException;

    int getSpatializerOutput() throws RemoteException;

    void getSpatializerParameter(int i, byte[] bArr) throws RemoteException;

    int getStreamMaxVolume(int i) throws RemoteException;

    int getStreamMinVolume(int i) throws RemoteException;

    int getStreamTypeAlias(int i) throws RemoteException;

    int getStreamVolume(int i) throws RemoteException;

    int getStreamVolumeForDevice(int i, int i2) throws RemoteException;

    int[] getSupportedHeadTrackingModes() throws RemoteException;

    int[] getSupportedSystemUsages() throws RemoteException;

    Map getSurroundFormats() throws RemoteException;

    int getUiSoundsStreamType() throws RemoteException;

    int getUidForDevice(int i) throws RemoteException;

    int getVibrateSetting(int i) throws RemoteException;

    IVolumeController getVolumeController() throws RemoteException;

    int getVolumeGroupMaxVolumeIndex(int i) throws RemoteException;

    int getVolumeGroupMinVolumeIndex(int i) throws RemoteException;

    int getVolumeGroupVolumeIndex(int i) throws RemoteException;

    void handleBluetoothActiveDeviceChanged(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) throws RemoteException;

    void handleVolumeKey(KeyEvent keyEvent, boolean z, String str, String str2) throws RemoteException;

    boolean hasHapticChannels(Uri uri) throws RemoteException;

    boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    boolean hasRegisteredDynamicPolicy() throws RemoteException;

    boolean isAlreadyInDB(String str) throws RemoteException;

    boolean isAppMute(int i) throws RemoteException;

    boolean isAudioServerRunning() throws RemoteException;

    boolean isBluetoothA2dpOn() throws RemoteException;

    boolean isBluetoothAudioDeviceCategoryFixed(String str) throws RemoteException;

    boolean isBluetoothScoOn() throws RemoteException;

    boolean isBluetoothVariableLatencyEnabled() throws RemoteException;

    boolean isCallScreeningModeSupported() throws RemoteException;

    boolean isCameraSoundForced() throws RemoteException;

    boolean isCsdAsAFeatureAvailable() throws RemoteException;

    boolean isCsdAsAFeatureEnabled() throws RemoteException;

    boolean isCsdEnabled() throws RemoteException;

    boolean isForceSpeakerOn() throws RemoteException;

    boolean isHdmiSystemAudioSupported() throws RemoteException;

    boolean isHeadTrackerAvailable() throws RemoteException;

    boolean isHeadTrackerEnabled(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    boolean isHomeSoundEffectEnabled() throws RemoteException;

    boolean isHotwordStreamSupported(boolean z) throws RemoteException;

    boolean isInAllowedList(String str) throws RemoteException;

    boolean isMasterMute() throws RemoteException;

    boolean isMicrophoneMuted() throws RemoteException;

    boolean isMultiSoundOn() throws RemoteException;

    boolean isMusicActive(boolean z) throws RemoteException;

    boolean isPstnCallAudioInterceptable() throws RemoteException;

    boolean isSafeMediaVolumeStateActive() throws RemoteException;

    boolean isSpatializerAvailable() throws RemoteException;

    boolean isSpatializerAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    boolean isSpatializerEnabled() throws RemoteException;

    boolean isSpeakerphoneOn() throws RemoteException;

    boolean isStreamAffectedByMute(int i) throws RemoteException;

    boolean isStreamAffectedByRingerMode(int i) throws RemoteException;

    boolean isStreamMutableByUi(int i) throws RemoteException;

    boolean isStreamMute(int i) throws RemoteException;

    boolean isSurroundFormatEnabled(int i) throws RemoteException;

    boolean isUltrasoundSupported() throws RemoteException;

    boolean isUsingAudio(int i) throws RemoteException;

    boolean isValidRingerMode(int i) throws RemoteException;

    boolean isVolumeControlUsingVolumeGroups() throws RemoteException;

    boolean isVolumeFixed() throws RemoteException;

    boolean isVolumeGroupMuted(int i) throws RemoteException;

    boolean loadSoundEffects() throws RemoteException;

    void lowerVolumeToRs1(String str) throws RemoteException;

    void muteAwaitConnection(int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j) throws RemoteException;

    void notifySafetyVolumeDialogVisible(IVolumeController iVolumeController, boolean z) throws RemoteException;

    void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) throws RemoteException;

    void playSoundEffect(int i, int i2) throws RemoteException;

    void playSoundEffectVolume(int i, float f) throws RemoteException;

    void playerAttributes(int i, AudioAttributes audioAttributes) throws RemoteException;

    void playerEvent(int i, int i2, int i3) throws RemoteException;

    void playerHasOpPlayAudio(int i, boolean z) throws RemoteException;

    void playerSessionId(int i, int i2) throws RemoteException;

    void portEvent(int i, int i2, PersistableBundle persistableBundle) throws RemoteException;

    void recenterHeadTracker() throws RemoteException;

    void recordRingtoneChanger(String str) throws RemoteException;

    void recorderEvent(int i, int i2) throws RemoteException;

    String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection, AttributionSource attributionSource) throws RemoteException;

    void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException;

    void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws RemoteException;

    void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException;

    void registerDeviceVolumeBehaviorDispatcher(boolean z, IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) throws RemoteException;

    void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, String str, AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, boolean z2, int i) throws RemoteException;

    void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws RemoteException;

    void registerLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws RemoteException;

    void registerModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException;

    void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws RemoteException;

    void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException;

    void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher iPlaybackConfigDispatcher, String str) throws RemoteException;

    void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws RemoteException;

    void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException;

    void registerSpatializerCallback(ISpatializerCallback iSpatializerCallback) throws RemoteException;

    void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) throws RemoteException;

    void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws RemoteException;

    void registerSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) throws RemoteException;

    void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws RemoteException;

    void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws RemoteException;

    void registerStreamAliasingDispatcher(IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws RemoteException;

    void releasePlayer(int i) throws RemoteException;

    void releaseRecorder(int i) throws RemoteException;

    void reloadAudioSettings() throws RemoteException;

    void removeAssistantServicesUids(int[] iArr) throws RemoteException;

    int removeDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    void removeLoudnessCodecInfo(int i, LoudnessCodecInfo loudnessCodecInfo) throws RemoteException;

    int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException;

    void removePackageForName(String str) throws RemoteException;

    int removePreferredDevicesForStrategy(int i) throws RemoteException;

    void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException;

    int removeUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException;

    int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, String str3, int i2, IAudioPolicyCallback iAudioPolicyCallback, int i3) throws RemoteException;

    int requestAudioFocusForTest(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, int i3, int i4) throws RemoteException;

    int secGetActiveStreamType(int i) throws RemoteException;

    boolean sendFocusLoss(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3, String str) throws RemoteException;

    void setA2dpSuspended(boolean z) throws RemoteException;

    void setActiveAssistantServiceUids(int[] iArr) throws RemoteException;

    boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes, long j) throws RemoteException;

    int setAllowedCapturePolicy(int i) throws RemoteException;

    void setAppDevice(int i, int i2, boolean z) throws RemoteException;

    void setAppMute(int i, boolean z, String str) throws RemoteException;

    void setAppVolume(int i, int i2, String str) throws RemoteException;

    void setAudioServiceConfig(String str) throws RemoteException;

    void setBluetoothA2dpOn(boolean z) throws RemoteException;

    boolean setBluetoothAudioDeviceCategory(String str, int i) throws RemoteException;

    void setBluetoothAudioDeviceCategory_legacy(String str, boolean z, int i) throws RemoteException;

    void setBluetoothScoOn(boolean z) throws RemoteException;

    void setBluetoothVariableLatencyEnabled(boolean z) throws RemoteException;

    void setBtOffloadEnable(int i) throws RemoteException;

    boolean setCommunicationDevice(IBinder iBinder, int i) throws RemoteException;

    void setCsd(float f) throws RemoteException;

    void setCsdAsAFeatureEnabled(boolean z) throws RemoteException;

    void setDesiredHeadTrackingMode(int i) throws RemoteException;

    int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int setDeviceToForceByUser(int i, String str, boolean z) throws RemoteException;

    void setDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException;

    void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException;

    boolean setEncodedSurroundMode(int i) throws RemoteException;

    int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException;

    void setFineVolume(int i, int i2, int i3, int i4, String str) throws RemoteException;

    int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void setForceSpeakerOn(boolean z) throws RemoteException;

    int setHdmiSystemAudioSupported(boolean z) throws RemoteException;

    void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    void setHomeSoundEffectEnabled(boolean z) throws RemoteException;

    void setLeAudioSuspended(boolean z) throws RemoteException;

    void setMasterMute(boolean z, int i, String str, int i2, String str2) throws RemoteException;

    boolean setMediaVolumeSteps(int[] iArr) throws RemoteException;

    void setMicInputControlMode(int i) throws RemoteException;

    void setMicrophoneMute(boolean z, String str, int i, String str2) throws RemoteException;

    void setMicrophoneMuteFromSwitch(boolean z) throws RemoteException;

    void setMode(int i, IBinder iBinder, String str) throws RemoteException;

    void setMultiAudioFocusEnabled(boolean z) throws RemoteException;

    void setMultiSoundOn(boolean z, boolean z2) throws RemoteException;

    void setMuteInterval(int i, String str) throws RemoteException;

    void setNavigationRepeatSoundEffectsEnabled(boolean z) throws RemoteException;

    void setNotifAliasRingForTest(boolean z) throws RemoteException;

    void setOutputRs2UpperBound(float f) throws RemoteException;

    int setPreferredDevicesForCapturePreset(int i, List<AudioDeviceAttributes> list) throws RemoteException;

    int setPreferredDevicesForStrategy(int i, List<AudioDeviceAttributes> list) throws RemoteException;

    int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) throws RemoteException;

    void setRadioOutputPath(int i) throws RemoteException;

    void setRemoteMic(boolean z) throws RemoteException;

    void setRingerModeExternal(int i, String str) throws RemoteException;

    void setRingerModeInternal(int i, String str) throws RemoteException;

    void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException;

    void setRttEnabled(boolean z) throws RemoteException;

    void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent) throws RemoteException;

    void setSpatializerEnabled(boolean z) throws RemoteException;

    void setSpatializerGlobalTransform(float[] fArr) throws RemoteException;

    void setSpatializerParameter(int i, byte[] bArr) throws RemoteException;

    void setSpeakerphoneOn(IBinder iBinder, boolean z) throws RemoteException;

    void setStreamVolume(int i, int i2, int i3, String str) throws RemoteException;

    void setStreamVolumeForDeviceWithAttribution(int i, int i2, int i3, String str, String str2, int i4) throws RemoteException;

    void setStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException;

    void setStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) throws RemoteException;

    void setSupportedSystemUsages(int[] iArr) throws RemoteException;

    boolean setSurroundFormatEnabled(int i, boolean z) throws RemoteException;

    void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z) throws RemoteException;

    int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException;

    int setUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException;

    void setVibrateSetting(int i, int i2) throws RemoteException;

    void setVolumeController(IVolumeController iVolumeController) throws RemoteException;

    void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2) throws RemoteException;

    void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException;

    void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException;

    boolean shouldNotificationSoundPlay(AudioAttributes audioAttributes) throws RemoteException;

    boolean shouldShowRingtoneVolume() throws RemoteException;

    boolean shouldVibrate(int i) throws RemoteException;

    void startBluetoothSco(IBinder iBinder, int i) throws RemoteException;

    void startBluetoothScoVirtualCall(IBinder iBinder) throws RemoteException;

    void startLoudnessCodecUpdates(int i) throws RemoteException;

    AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) throws RemoteException;

    void stopBluetoothSco(IBinder iBinder) throws RemoteException;

    void stopLoudnessCodecUpdates(int i) throws RemoteException;

    boolean supportsBluetoothVariableLatency() throws RemoteException;

    int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException;

    int trackRecorder(IBinder iBinder) throws RemoteException;

    void unloadSoundEffects() throws RemoteException;

    void unregisterAudioFocusClient(String str) throws RemoteException;

    void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException;

    void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws RemoteException;

    void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException;

    void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws RemoteException;

    void unregisterLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws RemoteException;

    void unregisterModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException;

    void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException;

    void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws RemoteException;

    void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException;

    void unregisterSpatializerCallback(ISpatializerCallback iSpatializerCallback) throws RemoteException;

    void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws RemoteException;

    void unregisterSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) throws RemoteException;

    void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws RemoteException;

    void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws RemoteException;

    int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] audioMixArr, AudioMixingRule[] audioMixingRuleArr, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    public static class Default implements IAudioService {
        @Override // android.media.IAudioService
        public int trackPlayer(PlayerBase.PlayerIdCard pic) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playerAttributes(int piid, AudioAttributes attr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerEvent(int piid, int event, int eventId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void releasePlayer(int piid) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int trackRecorder(IBinder recorder) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void recorderEvent(int riid, int event) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void releaseRecorder(int riid) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerSessionId(int piid, int sessionId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void portEvent(int portId, int event, PersistableBundle extras) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolumeWithAttribution(int streamType, int direction, int flags, String callingPackage, String attributionTag) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolume(int streamType, int index, int flags, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeWithAttribution(int streamType, int index, int flags, String callingPackage, String attributionTag) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDeviceVolume(VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public VolumeInfo getDeviceVolume(VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void handleVolumeKey(KeyEvent event, boolean isOnTv, String callingPackage, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isStreamMute(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isMasterMute() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMasterMute(boolean mute, int flags, String callingPackage, int userId, String attributionTag) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getStreamVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMinVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMaxVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void setVolumeGroupVolumeIndex(int groupId, int index, int flags, String callingPackage, String attributionTag) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupVolumeIndex(int groupId) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupMaxVolumeIndex(int groupId) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupMinVolumeIndex(int groupId) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getLastAudibleVolumeForVolumeGroup(int groupId) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeGroupMuted(int groupId) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void adjustVolumeGroupVolume(int groupId, int direction, int flags, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSupportedSystemUsages(int[] systemUsages) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int[] getSupportedSystemUsages() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isMicrophoneMuted() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isUltrasoundSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHotwordStreamSupported(boolean lookbackAudio) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMute(boolean on, String callingPackage, int userId, String attributionTag) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMuteFromSwitch(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getRingerModeExternal() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getRingerModeInternal() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isValidRingerMode(int ringerMode) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getVibrateSetting(int vibrateType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean shouldVibrate(int vibrateType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playSoundEffect(int effectType, int userId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean loadSoundEffects() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void unloadSoundEffects() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void reloadAudioSettings() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public Map getSurroundFormats() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List getReportedSurroundFormats() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean setSurroundFormatEnabled(int audioFormat, boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSurroundFormatEnabled(int audioFormat) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean setEncodedSurroundMode(int mode) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getEncodedSurroundMode(int targetSdkVersion) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSpeakerphoneOn(IBinder cb, boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isSpeakerphoneOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothScoOn(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setA2dpSuspended(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setLeAudioSuspended(boolean enable) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothScoOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothA2dpOn(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothA2dpOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocus(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, String attributionTag, int flags, IAudioPolicyCallback pcb, int sdk) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int abandonAudioFocus(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioFocusClient(String clientId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getCurrentAudioFocus() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void stopBluetoothSco(IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public IRingtonePlayer getRingtonePlayer() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getUiSoundsStreamType() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List getIndependentStreamTypes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getStreamTypeAlias(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeControlUsingVolumeGroups() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher isad, boolean register) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setNotifAliasRingForTest(boolean alias) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setWiredDeviceConnectionState(AudioDeviceAttributes aa, int state, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isCameraSoundForced() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setVolumeController(IVolumeController controller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public IVolumeController getVolumeController() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamMutableByUi(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void lowerVolumeToRs1(String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public float getOutputRs2UpperBound() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioService
        public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public float getCsd() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioService
        public void setCsd(float csd) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isCsdEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCsdAsAFeatureAvailable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCsdAsAFeatureEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setCsdAsAFeatureEnabled(boolean csdToggleValue) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBluetoothAudioDeviceCategory_legacy(String address, boolean isBle, int deviceCategory) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getBluetoothAudioDeviceCategory_legacy(String address, boolean isBle) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean setBluetoothAudioDeviceCategory(String address, int deviceCategory) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getBluetoothAudioDeviceCategory(String address) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothAudioDeviceCategoryFixed(String address) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setHdmiSystemAudioSupported(boolean on) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isHdmiSystemAudioSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb, boolean hasFocusListener, boolean isFocusPolicy, boolean isTestFocusPolicy, boolean isVolumeController, IMediaProjection projection, AttributionSource attributionSource) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] mixesToUpdate, AudioMixingRule[] updatedMixingRules, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setVolumePolicy(VolumePolicy policy) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean hasRegisteredDynamicPolicy() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChangeWithFade(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb, List<AudioFocusInfo> otherActiveAfis, FadeManagerConfiguration transientFadeMgrConfig) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void handleBluetoothActiveDeviceChanged(BluetoothDevice newDevice, BluetoothDevice previousDevice, BluetoothProfileConnectionInfo info) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setFocusRequestResultFromExtPolicy(AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isAudioServerRunning() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setUidDeviceAffinity(IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setUserIdDeviceAffinity(IAudioPolicyCallback pcb, int userId, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeUserIdDeviceAffinity(IAudioPolicyCallback pcb, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean hasHapticChannels(Uri uri) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCallScreeningModeSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setPreferredDevicesForStrategy(int strategy, List<AudioDeviceAttributes> devices) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removePreferredDevicesForStrategy(int strategy) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int strategy) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int setDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int strategy) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes attributes) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(AudioAttributes attributes) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void addOnDevicesForAttributesChangedListener(AudioAttributes attributes, IDevicesForAttributesCallback callback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback callback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setAllowedCapturePolicy(int capturePolicy) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getAllowedCapturePolicy() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRttEnabled(boolean rttEnabled) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDeviceVolumeBehavior(AudioDeviceAttributes device, int deviceVolumeBehavior, String pkgName) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getDeviceVolumeBehavior(AudioDeviceAttributes device) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setMultiAudioFocusEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setPreferredDevicesForCapturePreset(int capturePreset, List<AudioDeviceAttributes> devices) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearPreferredDevicesForCapturePreset(int capturePreset) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int capturePreset) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolumeForUid(int streamType, int direction, int flags, String packageName, int uid, int pid, UserHandle userHandle, int targetSdkVersion) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolumeForUid(int streamType, int direction, int flags, String packageName, int uid, int pid, UserHandle userHandle, int targetSdkVersion) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeForUid(int streamType, int direction, int flags, String packageName, int uid, int pid, UserHandle userHandle, int targetSdkVersion) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustVolume(int direction, int flags) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isMusicActive(boolean remotely) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getDeviceMaskForStream(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getAvailableCommunicationDeviceIds() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean setCommunicationDevice(IBinder cb, int portId) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getCommunicationDevice() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setNavigationRepeatSoundEffectsEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isHomeSoundEffectEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setHomeSoundEffectEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes device, long delayMillis) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes device) throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes device) throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocusForTest(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, int flags, int uid, int sdk) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int abandonAudioFocusForTest(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public long getFadeOutDurationOnFocusLossMillis(AudioAttributes aa) throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public List getFocusDuckedUidsForTest() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public long getFocusFadeOutDurationForTest() throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public long getFocusUnmuteDelayAfterFadeOutForTest() throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public boolean enterAudioFocusFreezeForTest(IBinder cb, int[] uids) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean exitAudioFocusFreezeForTest(IBinder cb) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerModeDispatcher(IAudioModeDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterModeDispatcher(IAudioModeDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getSpatializerImmersiveAudioLevel() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerAvailable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes device) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean hasHeadTracker(AudioDeviceAttributes device) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setHeadTrackerEnabled(boolean enabled, AudioDeviceAttributes device) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isHeadTrackerEnabled(AudioDeviceAttributes device) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHeadTrackerAvailable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback cb, boolean register) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean canBeSpatialized(AudioAttributes aa, AudioFormat af) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerSpatializerCallback(ISpatializerCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerCallback(ISpatializerCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDesiredHeadTrackingMode(int mode) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getDesiredHeadTrackingMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getSupportedHeadTrackingModes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getActualHeadTrackingMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSpatializerGlobalTransform(float[] transform) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void recenterHeadTracker() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerParameter(int key, byte[] value) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void getSpatializerParameter(int key, byte[] value) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getSpatializerOutput() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerSpatializerOutputCallback(ISpatializerOutputCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isVolumeFixed() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public VolumeInfo getDefaultVolumeInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isPstnCallAudioInterceptable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void muteAwaitConnection(int[] usagesToMute, AudioDeviceAttributes dev, long timeOutMs) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void cancelMuteAwaitConnection(AudioDeviceAttributes dev) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback cb, boolean register) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setTestDeviceConnectionState(AudioDeviceAttributes device, boolean connected) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerDeviceVolumeBehaviorDispatcher(boolean register, IDeviceVolumeBehaviorDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<AudioFocusInfo> getFocusStack() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean sendFocusLoss(AudioFocusInfo focusLoser, IAudioPolicyCallback apcb) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void addAssistantServicesUids(int[] assistantUID) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeAssistantServicesUids(int[] assistantUID) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setActiveAssistantServiceUids(int[] activeUids) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int[] getAssistantServicesUids() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int[] getActiveAssistantServiceUids() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean register, IAudioDeviceVolumeDispatcher cb, String packageName, AudioDeviceAttributes device, List<VolumeInfo> volumes, boolean handlesvolumeAdjustment, int volumeBehavior) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public AudioHalVersionInfo getHalVersion() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int setPreferredMixerAttributes(AudioAttributes aa, int portId, AudioMixerAttributes mixerAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearPreferredMixerAttributes(AudioAttributes aa, int portId) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean supportsBluetoothVariableLatency() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothVariableLatencyEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothVariableLatencyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher dispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void startLoudnessCodecUpdates(int sessionId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void stopLoudnessCodecUpdates(int sessionId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void addLoudnessCodecInfo(int sessionId, int mediaCodecHash, LoudnessCodecInfo codecInfo) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeLoudnessCodecInfo(int sessionId, LoudnessCodecInfo codecInfo) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public PersistableBundle getLoudnessParams(LoudnessCodecInfo codecInfo) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fmcForFocusLoss) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearFadeManagerConfigurationForFocusLoss() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean shouldNotificationSoundPlay(AudioAttributes aa) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setAudioServiceConfig(String keyValuePairs) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public String getAudioServiceConfig(String keys) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean shouldShowRingtoneVolume() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int secGetActiveStreamType(int suggestedStreamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getUidForDevice(int device) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setAppDevice(int uid, int device, boolean shouldShowNotification) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getAppDevice(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setAppVolume(int uid, int ratio, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getAppVolume(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setAppMute(int uid, boolean shouldMute, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isAppMute(int uid) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMultiSoundOn(boolean on, boolean shouldShowNotification) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isMultiSoundOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeForDeviceWithAttribution(int streamType, int index, int flags, String callingPackage, String attributionTag, int device) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getStreamVolumeForDevice(int streamType, int device) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public String getPinAppInfo(int device) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getPinDevice() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public String[] getSelectedAppList() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void addPackage(int uid, String packageName) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removePackageForName(String packageName) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isAlreadyInDB(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isInAllowedList(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setFineVolume(int streamType, int index, int flags, int device, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getFineVolume(int streamType, int device) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setForceSpeakerOn(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isForceSpeakerOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setDeviceToForceByUser(int device, String address, boolean force) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setMuteInterval(int interval, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getMuteInterval() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getRemainingMuteIntervalMs() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getPrevRingerMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSoundSettingEventBroadcastIntent(int type, PendingIntent broadcastIntent) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setMediaVolumeSteps(int[] volumeSteps) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int[] getMediaVolumeSteps() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void setRadioOutputPath(int path) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getRadioOutputPath() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void dismissVolumePanel() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public String getCurrentAudioFocusPackageName() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isUsingAudio(int uid) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setA2dpDeviceVolume(BluetoothDevice device, int streamType, int index, int flags, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getA2dpDeviceVolume(BluetoothDevice device, int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public float[] getFloatVolumeTable() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void setRemoteMic(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void recordRingtoneChanger(String log) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher pcdb, String packageName) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBtOffloadEnable(int state) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isSafeMediaVolumeStateActive() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public List<String> getExcludedRingtoneTitles(int type) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void notifySafetyVolumeDialogVisible(IVolumeController controller, boolean visible) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getModeInternal() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setMicInputControlMode(int mode) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getMicModeType() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getEarProtectLimit() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAudioService {
        public static final String DESCRIPTOR = "android.media.IAudioService";
        static final int TRANSACTION_abandonAudioFocus = 71;
        static final int TRANSACTION_abandonAudioFocusForTest = 190;
        static final int TRANSACTION_addAssistantServicesUids = 241;
        static final int TRANSACTION_addLoudnessCodecInfo = 259;
        static final int TRANSACTION_addMixForPolicy = 118;
        static final int TRANSACTION_addOnDevicesForAttributesChangedListener = 153;
        static final int TRANSACTION_addPackage = 284;
        static final int TRANSACTION_addSpatializerCompatibleAudioDevice = 217;
        static final int TRANSACTION_adjustStreamVolume = 10;
        static final int TRANSACTION_adjustStreamVolumeForUid = 170;
        static final int TRANSACTION_adjustStreamVolumeWithAttribution = 11;
        static final int TRANSACTION_adjustSuggestedStreamVolume = 174;
        static final int TRANSACTION_adjustSuggestedStreamVolumeForUid = 171;
        static final int TRANSACTION_adjustVolume = 173;
        static final int TRANSACTION_adjustVolumeGroupVolume = 31;
        static final int TRANSACTION_areNavigationRepeatSoundEffectsEnabled = 182;
        static final int TRANSACTION_canBeSpatialized = 209;
        static final int TRANSACTION_cancelMuteAwaitConnection = 234;
        static final int TRANSACTION_clearFadeManagerConfigurationForFocusLoss = 263;
        static final int TRANSACTION_clearPreferredDevicesForCapturePreset = 166;
        static final int TRANSACTION_clearPreferredMixerAttributes = 249;
        static final int TRANSACTION_disableSafeMediaVolume = 95;
        static final int TRANSACTION_dismissVolumePanel = 302;
        static final int TRANSACTION_dispatchFocusChange = 131;
        static final int TRANSACTION_dispatchFocusChangeWithFade = 132;
        static final int TRANSACTION_enterAudioFocusFreezeForTest = 195;
        static final int TRANSACTION_exitAudioFocusFreezeForTest = 196;
        static final int TRANSACTION_forceComputeCsdOnAllDevices = 102;
        static final int TRANSACTION_forceRemoteSubmixFullVolume = 18;
        static final int TRANSACTION_forceUseFrameworkMel = 101;
        static final int TRANSACTION_forceVolumeControlStream = 77;
        static final int TRANSACTION_getA2dpDeviceVolume = 306;
        static final int TRANSACTION_getActiveAssistantServiceUids = 245;
        static final int TRANSACTION_getActivePlaybackConfigurations = 129;
        static final int TRANSACTION_getActiveRecordingConfigurations = 126;
        static final int TRANSACTION_getActualHeadTrackingMode = 222;
        static final int TRANSACTION_getAdditionalOutputDeviceDelay = 187;
        static final int TRANSACTION_getAllowedCapturePolicy = 156;
        static final int TRANSACTION_getAppDevice = 272;
        static final int TRANSACTION_getAppVolume = 274;
        static final int TRANSACTION_getAssistantServicesUids = 244;
        static final int TRANSACTION_getAudioProductStrategies = 35;
        static final int TRANSACTION_getAudioServiceConfig = 267;
        static final int TRANSACTION_getAudioVolumeGroups = 24;
        static final int TRANSACTION_getAvailableCommunicationDeviceIds = 177;
        static final int TRANSACTION_getBluetoothAudioDeviceCategory = 110;
        static final int TRANSACTION_getBluetoothAudioDeviceCategory_legacy = 108;
        static final int TRANSACTION_getCommunicationDevice = 179;
        static final int TRANSACTION_getCsd = 99;
        static final int TRANSACTION_getCurrentAudioFocus = 73;
        static final int TRANSACTION_getCurrentAudioFocusPackageName = 303;
        static final int TRANSACTION_getDefaultVolumeInfo = 231;
        static final int TRANSACTION_getDesiredHeadTrackingMode = 220;
        static final int TRANSACTION_getDeviceMaskForStream = 176;
        static final int TRANSACTION_getDeviceVolume = 15;
        static final int TRANSACTION_getDeviceVolumeBehavior = 163;
        static final int TRANSACTION_getDevicesForAttributes = 151;
        static final int TRANSACTION_getDevicesForAttributesUnprotected = 152;
        static final int TRANSACTION_getEarProtectLimit = 318;
        static final int TRANSACTION_getEncodedSurroundMode = 61;
        static final int TRANSACTION_getExcludedRingtoneTitles = 313;
        static final int TRANSACTION_getFadeManagerConfigurationForFocusLoss = 264;
        static final int TRANSACTION_getFadeOutDurationOnFocusLossMillis = 191;
        static final int TRANSACTION_getFineVolume = 289;
        static final int TRANSACTION_getFloatVolumeTable = 307;
        static final int TRANSACTION_getFocusDuckedUidsForTest = 192;
        static final int TRANSACTION_getFocusFadeOutDurationForTest = 193;
        static final int TRANSACTION_getFocusRampTimeMs = 130;
        static final int TRANSACTION_getFocusStack = 239;
        static final int TRANSACTION_getFocusUnmuteDelayAfterFadeOutForTest = 194;
        static final int TRANSACTION_getHalVersion = 247;
        static final int TRANSACTION_getIndependentStreamTypes = 81;
        static final int TRANSACTION_getLastAudibleStreamVolume = 32;
        static final int TRANSACTION_getLastAudibleVolumeForVolumeGroup = 29;
        static final int TRANSACTION_getLoudnessParams = 261;
        static final int TRANSACTION_getMaxAdditionalOutputDeviceDelay = 188;
        static final int TRANSACTION_getMediaVolumeSteps = 299;
        static final int TRANSACTION_getMicModeType = 317;
        static final int TRANSACTION_getMode = 50;
        static final int TRANSACTION_getModeInternal = 315;
        static final int TRANSACTION_getMuteInterval = 294;
        static final int TRANSACTION_getMutingExpectedDevice = 235;
        static final int TRANSACTION_getNonDefaultDevicesForStrategy = 150;
        static final int TRANSACTION_getOutputRs2UpperBound = 97;
        static final int TRANSACTION_getPinAppInfo = 281;
        static final int TRANSACTION_getPinDevice = 282;
        static final int TRANSACTION_getPreferredDevicesForCapturePreset = 167;
        static final int TRANSACTION_getPreferredDevicesForStrategy = 147;
        static final int TRANSACTION_getPrevRingerMode = 296;
        static final int TRANSACTION_getRadioOutputPath = 301;
        static final int TRANSACTION_getRegisteredPolicyMixes = 116;
        static final int TRANSACTION_getRemainingMuteIntervalMs = 295;
        static final int TRANSACTION_getReportedSurroundFormats = 57;
        static final int TRANSACTION_getRingerModeExternal = 43;
        static final int TRANSACTION_getRingerModeInternal = 44;
        static final int TRANSACTION_getRingtonePlayer = 79;
        static final int TRANSACTION_getSelectedAppList = 283;
        static final int TRANSACTION_getSpatializerCompatibleAudioDevices = 216;
        static final int TRANSACTION_getSpatializerImmersiveAudioLevel = 199;
        static final int TRANSACTION_getSpatializerOutput = 227;
        static final int TRANSACTION_getSpatializerParameter = 226;
        static final int TRANSACTION_getStreamMaxVolume = 23;
        static final int TRANSACTION_getStreamMinVolume = 22;
        static final int TRANSACTION_getStreamTypeAlias = 82;
        static final int TRANSACTION_getStreamVolume = 21;
        static final int TRANSACTION_getStreamVolumeForDevice = 280;
        static final int TRANSACTION_getSupportedHeadTrackingModes = 221;
        static final int TRANSACTION_getSupportedSystemUsages = 34;
        static final int TRANSACTION_getSurroundFormats = 56;
        static final int TRANSACTION_getUiSoundsStreamType = 80;
        static final int TRANSACTION_getUidForDevice = 270;
        static final int TRANSACTION_getVibrateSetting = 47;
        static final int TRANSACTION_getVolumeController = 90;
        static final int TRANSACTION_getVolumeGroupMaxVolumeIndex = 27;
        static final int TRANSACTION_getVolumeGroupMinVolumeIndex = 28;
        static final int TRANSACTION_getVolumeGroupVolumeIndex = 26;
        static final int TRANSACTION_handleBluetoothActiveDeviceChanged = 134;
        static final int TRANSACTION_handleVolumeKey = 16;
        static final int TRANSACTION_hasHapticChannels = 143;
        static final int TRANSACTION_hasHeadTracker = 203;
        static final int TRANSACTION_hasRegisteredDynamicPolicy = 123;
        static final int TRANSACTION_isAlreadyInDB = 286;
        static final int TRANSACTION_isAppMute = 276;
        static final int TRANSACTION_isAudioServerRunning = 138;
        static final int TRANSACTION_isBluetoothA2dpOn = 69;
        static final int TRANSACTION_isBluetoothAudioDeviceCategoryFixed = 111;
        static final int TRANSACTION_isBluetoothScoOn = 67;
        static final int TRANSACTION_isBluetoothVariableLatencyEnabled = 254;
        static final int TRANSACTION_isCallScreeningModeSupported = 144;
        static final int TRANSACTION_isCameraSoundForced = 88;
        static final int TRANSACTION_isCsdAsAFeatureAvailable = 104;
        static final int TRANSACTION_isCsdAsAFeatureEnabled = 105;
        static final int TRANSACTION_isCsdEnabled = 103;
        static final int TRANSACTION_isForceSpeakerOn = 291;
        static final int TRANSACTION_isHdmiSystemAudioSupported = 113;
        static final int TRANSACTION_isHeadTrackerAvailable = 206;
        static final int TRANSACTION_isHeadTrackerEnabled = 205;
        static final int TRANSACTION_isHomeSoundEffectEnabled = 184;
        static final int TRANSACTION_isHotwordStreamSupported = 38;
        static final int TRANSACTION_isInAllowedList = 287;
        static final int TRANSACTION_isMasterMute = 19;
        static final int TRANSACTION_isMicrophoneMuted = 36;
        static final int TRANSACTION_isMultiSoundOn = 278;
        static final int TRANSACTION_isMusicActive = 175;
        static final int TRANSACTION_isPstnCallAudioInterceptable = 232;
        static final int TRANSACTION_isSafeMediaVolumeStateActive = 312;
        static final int TRANSACTION_isSpatializerAvailable = 201;
        static final int TRANSACTION_isSpatializerAvailableForDevice = 202;
        static final int TRANSACTION_isSpatializerEnabled = 200;
        static final int TRANSACTION_isSpeakerphoneOn = 63;
        static final int TRANSACTION_isStreamAffectedByMute = 93;
        static final int TRANSACTION_isStreamAffectedByRingerMode = 92;
        static final int TRANSACTION_isStreamMutableByUi = 94;
        static final int TRANSACTION_isStreamMute = 17;
        static final int TRANSACTION_isSurroundFormatEnabled = 59;
        static final int TRANSACTION_isUltrasoundSupported = 37;
        static final int TRANSACTION_isUsingAudio = 304;
        static final int TRANSACTION_isValidRingerMode = 45;
        static final int TRANSACTION_isVolumeControlUsingVolumeGroups = 83;
        static final int TRANSACTION_isVolumeFixed = 230;
        static final int TRANSACTION_isVolumeGroupMuted = 30;
        static final int TRANSACTION_loadSoundEffects = 53;
        static final int TRANSACTION_lowerVolumeToRs1 = 96;
        static final int TRANSACTION_muteAwaitConnection = 233;
        static final int TRANSACTION_notifySafetyVolumeDialogVisible = 314;
        static final int TRANSACTION_notifyVolumeControllerVisible = 91;
        static final int TRANSACTION_playSoundEffect = 51;
        static final int TRANSACTION_playSoundEffectVolume = 52;
        static final int TRANSACTION_playerAttributes = 2;
        static final int TRANSACTION_playerEvent = 3;
        static final int TRANSACTION_playerHasOpPlayAudio = 133;
        static final int TRANSACTION_playerSessionId = 8;
        static final int TRANSACTION_portEvent = 9;
        static final int TRANSACTION_recenterHeadTracker = 224;
        static final int TRANSACTION_recordRingtoneChanger = 309;
        static final int TRANSACTION_recorderEvent = 6;
        static final int TRANSACTION_registerAudioPolicy = 114;
        static final int TRANSACTION_registerAudioServerStateDispatcher = 136;
        static final int TRANSACTION_registerCapturePresetDevicesRoleDispatcher = 168;
        static final int TRANSACTION_registerCommunicationDeviceDispatcher = 180;
        static final int TRANSACTION_registerDeviceVolumeBehaviorDispatcher = 238;
        static final int TRANSACTION_registerDeviceVolumeDispatcherForAbsoluteVolume = 246;
        static final int TRANSACTION_registerHeadToSoundstagePoseCallback = 214;
        static final int TRANSACTION_registerLoudnessCodecUpdatesDispatcher = 255;
        static final int TRANSACTION_registerModeDispatcher = 197;
        static final int TRANSACTION_registerMuteAwaitConnectionDispatcher = 236;
        static final int TRANSACTION_registerPlaybackCallback = 127;
        static final int TRANSACTION_registerPlaybackCallbackWithPackage = 310;
        static final int TRANSACTION_registerPreferredMixerAttributesDispatcher = 250;
        static final int TRANSACTION_registerRecordingCallback = 124;
        static final int TRANSACTION_registerSpatializerCallback = 210;
        static final int TRANSACTION_registerSpatializerHeadTrackerAvailableCallback = 207;
        static final int TRANSACTION_registerSpatializerHeadTrackingCallback = 212;
        static final int TRANSACTION_registerSpatializerOutputCallback = 228;
        static final int TRANSACTION_registerStrategyNonDefaultDevicesDispatcher = 159;
        static final int TRANSACTION_registerStrategyPreferredDevicesDispatcher = 157;
        static final int TRANSACTION_registerStreamAliasingDispatcher = 84;
        static final int TRANSACTION_releasePlayer = 4;
        static final int TRANSACTION_releaseRecorder = 7;
        static final int TRANSACTION_reloadAudioSettings = 55;
        static final int TRANSACTION_removeAssistantServicesUids = 242;
        static final int TRANSACTION_removeDeviceAsNonDefaultForStrategy = 149;
        static final int TRANSACTION_removeLoudnessCodecInfo = 260;
        static final int TRANSACTION_removeMixForPolicy = 119;
        static final int TRANSACTION_removeOnDevicesForAttributesChangedListener = 154;
        static final int TRANSACTION_removePackageForName = 285;
        static final int TRANSACTION_removePreferredDevicesForStrategy = 146;
        static final int TRANSACTION_removeSpatializerCompatibleAudioDevice = 218;
        static final int TRANSACTION_removeUidDeviceAffinity = 140;
        static final int TRANSACTION_removeUserIdDeviceAffinity = 142;
        static final int TRANSACTION_requestAudioFocus = 70;
        static final int TRANSACTION_requestAudioFocusForTest = 189;
        static final int TRANSACTION_secGetActiveStreamType = 269;
        static final int TRANSACTION_sendFocusLoss = 240;
        static final int TRANSACTION_setA2dpDeviceVolume = 305;
        static final int TRANSACTION_setA2dpSuspended = 65;
        static final int TRANSACTION_setActiveAssistantServiceUids = 243;
        static final int TRANSACTION_setAdditionalOutputDeviceDelay = 186;
        static final int TRANSACTION_setAllowedCapturePolicy = 155;
        static final int TRANSACTION_setAppDevice = 271;
        static final int TRANSACTION_setAppMute = 275;
        static final int TRANSACTION_setAppVolume = 273;
        static final int TRANSACTION_setAudioServiceConfig = 266;
        static final int TRANSACTION_setBluetoothA2dpOn = 68;
        static final int TRANSACTION_setBluetoothAudioDeviceCategory = 109;
        static final int TRANSACTION_setBluetoothAudioDeviceCategory_legacy = 107;
        static final int TRANSACTION_setBluetoothScoOn = 64;
        static final int TRANSACTION_setBluetoothVariableLatencyEnabled = 253;
        static final int TRANSACTION_setBtOffloadEnable = 311;
        static final int TRANSACTION_setCommunicationDevice = 178;
        static final int TRANSACTION_setCsd = 100;
        static final int TRANSACTION_setCsdAsAFeatureEnabled = 106;
        static final int TRANSACTION_setDesiredHeadTrackingMode = 219;
        static final int TRANSACTION_setDeviceAsNonDefaultForStrategy = 148;
        static final int TRANSACTION_setDeviceToForceByUser = 292;
        static final int TRANSACTION_setDeviceVolume = 14;
        static final int TRANSACTION_setDeviceVolumeBehavior = 162;
        static final int TRANSACTION_setEncodedSurroundMode = 60;
        static final int TRANSACTION_setFadeManagerConfigurationForFocusLoss = 262;
        static final int TRANSACTION_setFineVolume = 288;
        static final int TRANSACTION_setFocusPropertiesForPolicy = 121;
        static final int TRANSACTION_setFocusRequestResultFromExtPolicy = 135;
        static final int TRANSACTION_setForceSpeakerOn = 290;
        static final int TRANSACTION_setHdmiSystemAudioSupported = 112;
        static final int TRANSACTION_setHeadTrackerEnabled = 204;
        static final int TRANSACTION_setHomeSoundEffectEnabled = 185;
        static final int TRANSACTION_setLeAudioSuspended = 66;
        static final int TRANSACTION_setMasterMute = 20;
        static final int TRANSACTION_setMediaVolumeSteps = 298;
        static final int TRANSACTION_setMicInputControlMode = 316;
        static final int TRANSACTION_setMicrophoneMute = 39;
        static final int TRANSACTION_setMicrophoneMuteFromSwitch = 40;
        static final int TRANSACTION_setMode = 49;
        static final int TRANSACTION_setMultiAudioFocusEnabled = 164;
        static final int TRANSACTION_setMultiSoundOn = 277;
        static final int TRANSACTION_setMuteInterval = 293;
        static final int TRANSACTION_setNavigationRepeatSoundEffectsEnabled = 183;
        static final int TRANSACTION_setNotifAliasRingForTest = 85;
        static final int TRANSACTION_setOutputRs2UpperBound = 98;
        static final int TRANSACTION_setPreferredDevicesForCapturePreset = 165;
        static final int TRANSACTION_setPreferredDevicesForStrategy = 145;
        static final int TRANSACTION_setPreferredMixerAttributes = 248;
        static final int TRANSACTION_setRadioOutputPath = 300;
        static final int TRANSACTION_setRemoteMic = 308;
        static final int TRANSACTION_setRingerModeExternal = 41;
        static final int TRANSACTION_setRingerModeInternal = 42;
        static final int TRANSACTION_setRingtonePlayer = 78;
        static final int TRANSACTION_setRttEnabled = 161;
        static final int TRANSACTION_setSoundSettingEventBroadcastIntent = 297;
        static final int TRANSACTION_setSpatializerEnabled = 208;
        static final int TRANSACTION_setSpatializerGlobalTransform = 223;
        static final int TRANSACTION_setSpatializerParameter = 225;
        static final int TRANSACTION_setSpeakerphoneOn = 62;
        static final int TRANSACTION_setStreamVolume = 12;
        static final int TRANSACTION_setStreamVolumeForDeviceWithAttribution = 279;
        static final int TRANSACTION_setStreamVolumeForUid = 172;
        static final int TRANSACTION_setStreamVolumeWithAttribution = 13;
        static final int TRANSACTION_setSupportedSystemUsages = 33;
        static final int TRANSACTION_setSurroundFormatEnabled = 58;
        static final int TRANSACTION_setTestDeviceConnectionState = 237;
        static final int TRANSACTION_setUidDeviceAffinity = 139;
        static final int TRANSACTION_setUserIdDeviceAffinity = 141;
        static final int TRANSACTION_setVibrateSetting = 46;
        static final int TRANSACTION_setVolumeController = 89;
        static final int TRANSACTION_setVolumeGroupVolumeIndex = 25;
        static final int TRANSACTION_setVolumePolicy = 122;
        static final int TRANSACTION_setWiredDeviceConnectionState = 86;
        static final int TRANSACTION_shouldNotificationSoundPlay = 265;
        static final int TRANSACTION_shouldShowRingtoneVolume = 268;
        static final int TRANSACTION_shouldVibrate = 48;
        static final int TRANSACTION_startBluetoothSco = 74;
        static final int TRANSACTION_startBluetoothScoVirtualCall = 75;
        static final int TRANSACTION_startLoudnessCodecUpdates = 257;
        static final int TRANSACTION_startWatchingRoutes = 87;
        static final int TRANSACTION_stopBluetoothSco = 76;
        static final int TRANSACTION_stopLoudnessCodecUpdates = 258;
        static final int TRANSACTION_supportsBluetoothVariableLatency = 252;
        static final int TRANSACTION_trackPlayer = 1;
        static final int TRANSACTION_trackRecorder = 5;
        static final int TRANSACTION_unloadSoundEffects = 54;
        static final int TRANSACTION_unregisterAudioFocusClient = 72;
        static final int TRANSACTION_unregisterAudioPolicy = 117;
        static final int TRANSACTION_unregisterAudioPolicyAsync = 115;
        static final int TRANSACTION_unregisterAudioServerStateDispatcher = 137;
        static final int TRANSACTION_unregisterCapturePresetDevicesRoleDispatcher = 169;
        static final int TRANSACTION_unregisterCommunicationDeviceDispatcher = 181;
        static final int TRANSACTION_unregisterHeadToSoundstagePoseCallback = 215;
        static final int TRANSACTION_unregisterLoudnessCodecUpdatesDispatcher = 256;
        static final int TRANSACTION_unregisterModeDispatcher = 198;
        static final int TRANSACTION_unregisterPlaybackCallback = 128;
        static final int TRANSACTION_unregisterPreferredMixerAttributesDispatcher = 251;
        static final int TRANSACTION_unregisterRecordingCallback = 125;
        static final int TRANSACTION_unregisterSpatializerCallback = 211;
        static final int TRANSACTION_unregisterSpatializerHeadTrackingCallback = 213;
        static final int TRANSACTION_unregisterSpatializerOutputCallback = 229;
        static final int TRANSACTION_unregisterStrategyNonDefaultDevicesDispatcher = 160;
        static final int TRANSACTION_unregisterStrategyPreferredDevicesDispatcher = 158;
        static final int TRANSACTION_updateMixingRulesForPolicy = 120;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_setDeviceVolume = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final String[] PERMISSIONS_getDeviceVolume = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final String[] PERMISSIONS_getAudioVolumeGroups = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_setVolumeGroupVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_getVolumeGroupVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_getVolumeGroupMaxVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_getVolumeGroupMinVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_setDeviceVolumeBehavior = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final String[] PERMISSIONS_getDeviceVolumeBehavior = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.QUERY_AUDIO_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAudioService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAudioService)) {
                return (IAudioService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "trackPlayer";
                case 2:
                    return "playerAttributes";
                case 3:
                    return "playerEvent";
                case 4:
                    return "releasePlayer";
                case 5:
                    return "trackRecorder";
                case 6:
                    return "recorderEvent";
                case 7:
                    return "releaseRecorder";
                case 8:
                    return "playerSessionId";
                case 9:
                    return "portEvent";
                case 10:
                    return "adjustStreamVolume";
                case 11:
                    return "adjustStreamVolumeWithAttribution";
                case 12:
                    return "setStreamVolume";
                case 13:
                    return "setStreamVolumeWithAttribution";
                case 14:
                    return "setDeviceVolume";
                case 15:
                    return "getDeviceVolume";
                case 16:
                    return "handleVolumeKey";
                case 17:
                    return "isStreamMute";
                case 18:
                    return "forceRemoteSubmixFullVolume";
                case 19:
                    return "isMasterMute";
                case 20:
                    return "setMasterMute";
                case 21:
                    return "getStreamVolume";
                case 22:
                    return "getStreamMinVolume";
                case 23:
                    return "getStreamMaxVolume";
                case 24:
                    return "getAudioVolumeGroups";
                case 25:
                    return "setVolumeGroupVolumeIndex";
                case 26:
                    return "getVolumeGroupVolumeIndex";
                case 27:
                    return "getVolumeGroupMaxVolumeIndex";
                case 28:
                    return "getVolumeGroupMinVolumeIndex";
                case 29:
                    return "getLastAudibleVolumeForVolumeGroup";
                case 30:
                    return "isVolumeGroupMuted";
                case 31:
                    return "adjustVolumeGroupVolume";
                case 32:
                    return "getLastAudibleStreamVolume";
                case 33:
                    return "setSupportedSystemUsages";
                case 34:
                    return "getSupportedSystemUsages";
                case 35:
                    return "getAudioProductStrategies";
                case 36:
                    return "isMicrophoneMuted";
                case 37:
                    return "isUltrasoundSupported";
                case 38:
                    return "isHotwordStreamSupported";
                case 39:
                    return "setMicrophoneMute";
                case 40:
                    return "setMicrophoneMuteFromSwitch";
                case 41:
                    return "setRingerModeExternal";
                case 42:
                    return "setRingerModeInternal";
                case 43:
                    return "getRingerModeExternal";
                case 44:
                    return "getRingerModeInternal";
                case 45:
                    return "isValidRingerMode";
                case 46:
                    return "setVibrateSetting";
                case 47:
                    return "getVibrateSetting";
                case 48:
                    return "shouldVibrate";
                case 49:
                    return "setMode";
                case 50:
                    return "getMode";
                case 51:
                    return "playSoundEffect";
                case 52:
                    return "playSoundEffectVolume";
                case 53:
                    return "loadSoundEffects";
                case 54:
                    return "unloadSoundEffects";
                case 55:
                    return "reloadAudioSettings";
                case 56:
                    return "getSurroundFormats";
                case 57:
                    return "getReportedSurroundFormats";
                case 58:
                    return "setSurroundFormatEnabled";
                case 59:
                    return "isSurroundFormatEnabled";
                case 60:
                    return "setEncodedSurroundMode";
                case 61:
                    return "getEncodedSurroundMode";
                case 62:
                    return "setSpeakerphoneOn";
                case 63:
                    return "isSpeakerphoneOn";
                case 64:
                    return "setBluetoothScoOn";
                case 65:
                    return "setA2dpSuspended";
                case 66:
                    return "setLeAudioSuspended";
                case 67:
                    return "isBluetoothScoOn";
                case 68:
                    return "setBluetoothA2dpOn";
                case 69:
                    return "isBluetoothA2dpOn";
                case 70:
                    return "requestAudioFocus";
                case 71:
                    return "abandonAudioFocus";
                case 72:
                    return "unregisterAudioFocusClient";
                case 73:
                    return "getCurrentAudioFocus";
                case 74:
                    return "startBluetoothSco";
                case 75:
                    return "startBluetoothScoVirtualCall";
                case 76:
                    return "stopBluetoothSco";
                case 77:
                    return "forceVolumeControlStream";
                case 78:
                    return "setRingtonePlayer";
                case 79:
                    return "getRingtonePlayer";
                case 80:
                    return "getUiSoundsStreamType";
                case 81:
                    return "getIndependentStreamTypes";
                case 82:
                    return "getStreamTypeAlias";
                case 83:
                    return "isVolumeControlUsingVolumeGroups";
                case 84:
                    return "registerStreamAliasingDispatcher";
                case 85:
                    return "setNotifAliasRingForTest";
                case 86:
                    return "setWiredDeviceConnectionState";
                case 87:
                    return "startWatchingRoutes";
                case 88:
                    return "isCameraSoundForced";
                case 89:
                    return "setVolumeController";
                case 90:
                    return "getVolumeController";
                case 91:
                    return "notifyVolumeControllerVisible";
                case 92:
                    return "isStreamAffectedByRingerMode";
                case 93:
                    return "isStreamAffectedByMute";
                case 94:
                    return "isStreamMutableByUi";
                case 95:
                    return "disableSafeMediaVolume";
                case 96:
                    return "lowerVolumeToRs1";
                case 97:
                    return "getOutputRs2UpperBound";
                case 98:
                    return "setOutputRs2UpperBound";
                case 99:
                    return "getCsd";
                case 100:
                    return "setCsd";
                case 101:
                    return "forceUseFrameworkMel";
                case 102:
                    return "forceComputeCsdOnAllDevices";
                case 103:
                    return "isCsdEnabled";
                case 104:
                    return "isCsdAsAFeatureAvailable";
                case 105:
                    return "isCsdAsAFeatureEnabled";
                case 106:
                    return "setCsdAsAFeatureEnabled";
                case 107:
                    return "setBluetoothAudioDeviceCategory_legacy";
                case 108:
                    return "getBluetoothAudioDeviceCategory_legacy";
                case 109:
                    return "setBluetoothAudioDeviceCategory";
                case 110:
                    return "getBluetoothAudioDeviceCategory";
                case 111:
                    return "isBluetoothAudioDeviceCategoryFixed";
                case 112:
                    return "setHdmiSystemAudioSupported";
                case 113:
                    return "isHdmiSystemAudioSupported";
                case 114:
                    return "registerAudioPolicy";
                case 115:
                    return "unregisterAudioPolicyAsync";
                case 116:
                    return "getRegisteredPolicyMixes";
                case 117:
                    return "unregisterAudioPolicy";
                case 118:
                    return "addMixForPolicy";
                case 119:
                    return "removeMixForPolicy";
                case 120:
                    return "updateMixingRulesForPolicy";
                case 121:
                    return "setFocusPropertiesForPolicy";
                case 122:
                    return "setVolumePolicy";
                case 123:
                    return "hasRegisteredDynamicPolicy";
                case 124:
                    return "registerRecordingCallback";
                case 125:
                    return "unregisterRecordingCallback";
                case 126:
                    return "getActiveRecordingConfigurations";
                case 127:
                    return "registerPlaybackCallback";
                case 128:
                    return "unregisterPlaybackCallback";
                case 129:
                    return "getActivePlaybackConfigurations";
                case 130:
                    return "getFocusRampTimeMs";
                case 131:
                    return "dispatchFocusChange";
                case 132:
                    return "dispatchFocusChangeWithFade";
                case 133:
                    return "playerHasOpPlayAudio";
                case 134:
                    return "handleBluetoothActiveDeviceChanged";
                case 135:
                    return "setFocusRequestResultFromExtPolicy";
                case 136:
                    return "registerAudioServerStateDispatcher";
                case 137:
                    return "unregisterAudioServerStateDispatcher";
                case 138:
                    return "isAudioServerRunning";
                case 139:
                    return "setUidDeviceAffinity";
                case 140:
                    return "removeUidDeviceAffinity";
                case 141:
                    return "setUserIdDeviceAffinity";
                case 142:
                    return "removeUserIdDeviceAffinity";
                case 143:
                    return "hasHapticChannels";
                case 144:
                    return "isCallScreeningModeSupported";
                case 145:
                    return "setPreferredDevicesForStrategy";
                case 146:
                    return "removePreferredDevicesForStrategy";
                case 147:
                    return "getPreferredDevicesForStrategy";
                case 148:
                    return "setDeviceAsNonDefaultForStrategy";
                case 149:
                    return "removeDeviceAsNonDefaultForStrategy";
                case 150:
                    return "getNonDefaultDevicesForStrategy";
                case 151:
                    return "getDevicesForAttributes";
                case 152:
                    return "getDevicesForAttributesUnprotected";
                case 153:
                    return "addOnDevicesForAttributesChangedListener";
                case 154:
                    return "removeOnDevicesForAttributesChangedListener";
                case 155:
                    return "setAllowedCapturePolicy";
                case 156:
                    return "getAllowedCapturePolicy";
                case 157:
                    return "registerStrategyPreferredDevicesDispatcher";
                case 158:
                    return "unregisterStrategyPreferredDevicesDispatcher";
                case 159:
                    return "registerStrategyNonDefaultDevicesDispatcher";
                case 160:
                    return "unregisterStrategyNonDefaultDevicesDispatcher";
                case 161:
                    return "setRttEnabled";
                case 162:
                    return "setDeviceVolumeBehavior";
                case 163:
                    return "getDeviceVolumeBehavior";
                case 164:
                    return "setMultiAudioFocusEnabled";
                case 165:
                    return "setPreferredDevicesForCapturePreset";
                case 166:
                    return "clearPreferredDevicesForCapturePreset";
                case 167:
                    return "getPreferredDevicesForCapturePreset";
                case 168:
                    return "registerCapturePresetDevicesRoleDispatcher";
                case 169:
                    return "unregisterCapturePresetDevicesRoleDispatcher";
                case 170:
                    return "adjustStreamVolumeForUid";
                case 171:
                    return "adjustSuggestedStreamVolumeForUid";
                case 172:
                    return "setStreamVolumeForUid";
                case 173:
                    return "adjustVolume";
                case 174:
                    return "adjustSuggestedStreamVolume";
                case 175:
                    return "isMusicActive";
                case 176:
                    return "getDeviceMaskForStream";
                case 177:
                    return "getAvailableCommunicationDeviceIds";
                case 178:
                    return "setCommunicationDevice";
                case 179:
                    return "getCommunicationDevice";
                case 180:
                    return "registerCommunicationDeviceDispatcher";
                case 181:
                    return "unregisterCommunicationDeviceDispatcher";
                case 182:
                    return "areNavigationRepeatSoundEffectsEnabled";
                case 183:
                    return "setNavigationRepeatSoundEffectsEnabled";
                case 184:
                    return "isHomeSoundEffectEnabled";
                case 185:
                    return "setHomeSoundEffectEnabled";
                case 186:
                    return "setAdditionalOutputDeviceDelay";
                case 187:
                    return "getAdditionalOutputDeviceDelay";
                case 188:
                    return "getMaxAdditionalOutputDeviceDelay";
                case 189:
                    return "requestAudioFocusForTest";
                case 190:
                    return "abandonAudioFocusForTest";
                case 191:
                    return "getFadeOutDurationOnFocusLossMillis";
                case 192:
                    return "getFocusDuckedUidsForTest";
                case 193:
                    return "getFocusFadeOutDurationForTest";
                case 194:
                    return "getFocusUnmuteDelayAfterFadeOutForTest";
                case 195:
                    return "enterAudioFocusFreezeForTest";
                case 196:
                    return "exitAudioFocusFreezeForTest";
                case 197:
                    return "registerModeDispatcher";
                case 198:
                    return "unregisterModeDispatcher";
                case 199:
                    return "getSpatializerImmersiveAudioLevel";
                case 200:
                    return "isSpatializerEnabled";
                case 201:
                    return "isSpatializerAvailable";
                case 202:
                    return "isSpatializerAvailableForDevice";
                case 203:
                    return "hasHeadTracker";
                case 204:
                    return "setHeadTrackerEnabled";
                case 205:
                    return "isHeadTrackerEnabled";
                case 206:
                    return "isHeadTrackerAvailable";
                case 207:
                    return "registerSpatializerHeadTrackerAvailableCallback";
                case 208:
                    return "setSpatializerEnabled";
                case 209:
                    return "canBeSpatialized";
                case 210:
                    return "registerSpatializerCallback";
                case 211:
                    return "unregisterSpatializerCallback";
                case 212:
                    return "registerSpatializerHeadTrackingCallback";
                case 213:
                    return "unregisterSpatializerHeadTrackingCallback";
                case 214:
                    return "registerHeadToSoundstagePoseCallback";
                case 215:
                    return "unregisterHeadToSoundstagePoseCallback";
                case 216:
                    return "getSpatializerCompatibleAudioDevices";
                case 217:
                    return "addSpatializerCompatibleAudioDevice";
                case 218:
                    return "removeSpatializerCompatibleAudioDevice";
                case 219:
                    return "setDesiredHeadTrackingMode";
                case 220:
                    return "getDesiredHeadTrackingMode";
                case 221:
                    return "getSupportedHeadTrackingModes";
                case 222:
                    return "getActualHeadTrackingMode";
                case 223:
                    return "setSpatializerGlobalTransform";
                case 224:
                    return "recenterHeadTracker";
                case 225:
                    return "setSpatializerParameter";
                case 226:
                    return "getSpatializerParameter";
                case 227:
                    return "getSpatializerOutput";
                case 228:
                    return "registerSpatializerOutputCallback";
                case 229:
                    return "unregisterSpatializerOutputCallback";
                case 230:
                    return "isVolumeFixed";
                case 231:
                    return "getDefaultVolumeInfo";
                case 232:
                    return "isPstnCallAudioInterceptable";
                case 233:
                    return "muteAwaitConnection";
                case 234:
                    return "cancelMuteAwaitConnection";
                case 235:
                    return "getMutingExpectedDevice";
                case 236:
                    return "registerMuteAwaitConnectionDispatcher";
                case 237:
                    return "setTestDeviceConnectionState";
                case 238:
                    return "registerDeviceVolumeBehaviorDispatcher";
                case 239:
                    return "getFocusStack";
                case 240:
                    return "sendFocusLoss";
                case 241:
                    return "addAssistantServicesUids";
                case 242:
                    return "removeAssistantServicesUids";
                case 243:
                    return "setActiveAssistantServiceUids";
                case 244:
                    return "getAssistantServicesUids";
                case 245:
                    return "getActiveAssistantServiceUids";
                case 246:
                    return "registerDeviceVolumeDispatcherForAbsoluteVolume";
                case 247:
                    return "getHalVersion";
                case 248:
                    return "setPreferredMixerAttributes";
                case 249:
                    return "clearPreferredMixerAttributes";
                case 250:
                    return "registerPreferredMixerAttributesDispatcher";
                case 251:
                    return "unregisterPreferredMixerAttributesDispatcher";
                case 252:
                    return "supportsBluetoothVariableLatency";
                case 253:
                    return "setBluetoothVariableLatencyEnabled";
                case 254:
                    return "isBluetoothVariableLatencyEnabled";
                case 255:
                    return "registerLoudnessCodecUpdatesDispatcher";
                case 256:
                    return "unregisterLoudnessCodecUpdatesDispatcher";
                case 257:
                    return "startLoudnessCodecUpdates";
                case 258:
                    return "stopLoudnessCodecUpdates";
                case 259:
                    return "addLoudnessCodecInfo";
                case 260:
                    return "removeLoudnessCodecInfo";
                case 261:
                    return "getLoudnessParams";
                case 262:
                    return "setFadeManagerConfigurationForFocusLoss";
                case 263:
                    return "clearFadeManagerConfigurationForFocusLoss";
                case 264:
                    return "getFadeManagerConfigurationForFocusLoss";
                case 265:
                    return "shouldNotificationSoundPlay";
                case 266:
                    return "setAudioServiceConfig";
                case 267:
                    return "getAudioServiceConfig";
                case 268:
                    return "shouldShowRingtoneVolume";
                case 269:
                    return "secGetActiveStreamType";
                case 270:
                    return "getUidForDevice";
                case 271:
                    return "setAppDevice";
                case 272:
                    return "getAppDevice";
                case 273:
                    return "setAppVolume";
                case 274:
                    return "getAppVolume";
                case 275:
                    return "setAppMute";
                case 276:
                    return "isAppMute";
                case 277:
                    return "setMultiSoundOn";
                case 278:
                    return "isMultiSoundOn";
                case 279:
                    return "setStreamVolumeForDeviceWithAttribution";
                case 280:
                    return "getStreamVolumeForDevice";
                case 281:
                    return "getPinAppInfo";
                case 282:
                    return "getPinDevice";
                case 283:
                    return "getSelectedAppList";
                case 284:
                    return "addPackage";
                case 285:
                    return "removePackageForName";
                case 286:
                    return "isAlreadyInDB";
                case 287:
                    return "isInAllowedList";
                case 288:
                    return "setFineVolume";
                case 289:
                    return "getFineVolume";
                case 290:
                    return "setForceSpeakerOn";
                case 291:
                    return "isForceSpeakerOn";
                case 292:
                    return "setDeviceToForceByUser";
                case 293:
                    return "setMuteInterval";
                case 294:
                    return "getMuteInterval";
                case 295:
                    return "getRemainingMuteIntervalMs";
                case 296:
                    return "getPrevRingerMode";
                case 297:
                    return "setSoundSettingEventBroadcastIntent";
                case 298:
                    return "setMediaVolumeSteps";
                case 299:
                    return "getMediaVolumeSteps";
                case 300:
                    return "setRadioOutputPath";
                case 301:
                    return "getRadioOutputPath";
                case 302:
                    return "dismissVolumePanel";
                case 303:
                    return "getCurrentAudioFocusPackageName";
                case 304:
                    return "isUsingAudio";
                case 305:
                    return "setA2dpDeviceVolume";
                case 306:
                    return "getA2dpDeviceVolume";
                case 307:
                    return "getFloatVolumeTable";
                case 308:
                    return "setRemoteMic";
                case 309:
                    return "recordRingtoneChanger";
                case 310:
                    return "registerPlaybackCallbackWithPackage";
                case 311:
                    return "setBtOffloadEnable";
                case 312:
                    return "isSafeMediaVolumeStateActive";
                case 313:
                    return "getExcludedRingtoneTitles";
                case 314:
                    return "notifySafetyVolumeDialogVisible";
                case 315:
                    return "getModeInternal";
                case 316:
                    return "setMicInputControlMode";
                case 317:
                    return "getMicModeType";
                case 318:
                    return "getEarProtectLimit";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PlayerBase.PlayerIdCard _arg0 = (PlayerBase.PlayerIdCard) data.readTypedObject(PlayerBase.PlayerIdCard.CREATOR);
                    data.enforceNoDataAvail();
                    int _result = trackPlayer(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    AudioAttributes _arg1 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    playerAttributes(_arg02, _arg1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    playerEvent(_arg03, _arg12, _arg2);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    releasePlayer(_arg04);
                    return true;
                case 5:
                    IBinder _arg05 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result2 = trackRecorder(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    recorderEvent(_arg06, _arg13);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    releaseRecorder(_arg07);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    playerSessionId(_arg08, _arg14);
                    return true;
                case 9:
                    return onTransact$portEvent$(data, reply);
                case 10:
                    return onTransact$adjustStreamVolume$(data, reply);
                case 11:
                    return onTransact$adjustStreamVolumeWithAttribution$(data, reply);
                case 12:
                    return onTransact$setStreamVolume$(data, reply);
                case 13:
                    return onTransact$setStreamVolumeWithAttribution$(data, reply);
                case 14:
                    return onTransact$setDeviceVolume$(data, reply);
                case 15:
                    return onTransact$getDeviceVolume$(data, reply);
                case 16:
                    return onTransact$handleVolumeKey$(data, reply);
                case 17:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = isStreamMute(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 18:
                    boolean _arg010 = data.readBoolean();
                    IBinder _arg15 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    forceRemoteSubmixFullVolume(_arg010, _arg15);
                    reply.writeNoException();
                    return true;
                case 19:
                    boolean _result4 = isMasterMute();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 20:
                    return onTransact$setMasterMute$(data, reply);
                case 21:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = getStreamVolume(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 22:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result6 = getStreamMinVolume(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 23:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result7 = getStreamMaxVolume(_arg013);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 24:
                    List<android.media.audiopolicy.AudioVolumeGroup> _result8 = getAudioVolumeGroups();
                    reply.writeNoException();
                    reply.writeTypedList(_result8, 1);
                    return true;
                case 25:
                    return onTransact$setVolumeGroupVolumeIndex$(data, reply);
                case 26:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result9 = getVolumeGroupVolumeIndex(_arg014);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 27:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result10 = getVolumeGroupMaxVolumeIndex(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 28:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result11 = getVolumeGroupMinVolumeIndex(_arg016);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 29:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result12 = getLastAudibleVolumeForVolumeGroup(_arg017);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 30:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result13 = isVolumeGroupMuted(_arg018);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 31:
                    return onTransact$adjustVolumeGroupVolume$(data, reply);
                case 32:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result14 = getLastAudibleStreamVolume(_arg019);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 33:
                    int[] _arg020 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setSupportedSystemUsages(_arg020);
                    reply.writeNoException();
                    return true;
                case 34:
                    int[] _result15 = getSupportedSystemUsages();
                    reply.writeNoException();
                    reply.writeIntArray(_result15);
                    return true;
                case 35:
                    List<android.media.audiopolicy.AudioProductStrategy> _result16 = getAudioProductStrategies();
                    reply.writeNoException();
                    reply.writeTypedList(_result16, 1);
                    return true;
                case 36:
                    boolean _result17 = isMicrophoneMuted();
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 37:
                    boolean _result18 = isUltrasoundSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 38:
                    boolean _arg021 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result19 = isHotwordStreamSupported(_arg021);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 39:
                    return onTransact$setMicrophoneMute$(data, reply);
                case 40:
                    boolean _arg022 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMicrophoneMuteFromSwitch(_arg022);
                    return true;
                case 41:
                    int _arg023 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    setRingerModeExternal(_arg023, _arg16);
                    reply.writeNoException();
                    return true;
                case 42:
                    int _arg024 = data.readInt();
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    setRingerModeInternal(_arg024, _arg17);
                    reply.writeNoException();
                    return true;
                case 43:
                    int _result20 = getRingerModeExternal();
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 44:
                    int _result21 = getRingerModeInternal();
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 45:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result22 = isValidRingerMode(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 46:
                    int _arg026 = data.readInt();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    setVibrateSetting(_arg026, _arg18);
                    reply.writeNoException();
                    return true;
                case 47:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result23 = getVibrateSetting(_arg027);
                    reply.writeNoException();
                    reply.writeInt(_result23);
                    return true;
                case 48:
                    int _arg028 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result24 = shouldVibrate(_arg028);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 49:
                    return onTransact$setMode$(data, reply);
                case 50:
                    int _result25 = getMode();
                    reply.writeNoException();
                    reply.writeInt(_result25);
                    return true;
                case 51:
                    int _arg029 = data.readInt();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    playSoundEffect(_arg029, _arg19);
                    return true;
                case 52:
                    int _arg030 = data.readInt();
                    float _arg110 = data.readFloat();
                    data.enforceNoDataAvail();
                    playSoundEffectVolume(_arg030, _arg110);
                    return true;
                case 53:
                    boolean _result26 = loadSoundEffects();
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 54:
                    unloadSoundEffects();
                    return true;
                case 55:
                    reloadAudioSettings();
                    return true;
                case 56:
                    Map _result27 = getSurroundFormats();
                    reply.writeNoException();
                    reply.writeMap(_result27);
                    return true;
                case 57:
                    List _result28 = getReportedSurroundFormats();
                    reply.writeNoException();
                    reply.writeList(_result28);
                    return true;
                case 58:
                    int _arg031 = data.readInt();
                    boolean _arg111 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result29 = setSurroundFormatEnabled(_arg031, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 59:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result30 = isSurroundFormatEnabled(_arg032);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 60:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result31 = setEncodedSurroundMode(_arg033);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 61:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result32 = getEncodedSurroundMode(_arg034);
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 62:
                    IBinder _arg035 = data.readStrongBinder();
                    boolean _arg112 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSpeakerphoneOn(_arg035, _arg112);
                    reply.writeNoException();
                    return true;
                case 63:
                    boolean _result33 = isSpeakerphoneOn();
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 64:
                    boolean _arg036 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBluetoothScoOn(_arg036);
                    reply.writeNoException();
                    return true;
                case 65:
                    boolean _arg037 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setA2dpSuspended(_arg037);
                    reply.writeNoException();
                    return true;
                case 66:
                    boolean _arg038 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLeAudioSuspended(_arg038);
                    reply.writeNoException();
                    return true;
                case 67:
                    boolean _result34 = isBluetoothScoOn();
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 68:
                    boolean _arg039 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBluetoothA2dpOn(_arg039);
                    reply.writeNoException();
                    return true;
                case 69:
                    boolean _result35 = isBluetoothA2dpOn();
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 70:
                    return onTransact$requestAudioFocus$(data, reply);
                case 71:
                    return onTransact$abandonAudioFocus$(data, reply);
                case 72:
                    String _arg040 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterAudioFocusClient(_arg040);
                    reply.writeNoException();
                    return true;
                case 73:
                    int _result36 = getCurrentAudioFocus();
                    reply.writeNoException();
                    reply.writeInt(_result36);
                    return true;
                case 74:
                    IBinder _arg041 = data.readStrongBinder();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    startBluetoothSco(_arg041, _arg113);
                    reply.writeNoException();
                    return true;
                case 75:
                    IBinder _arg042 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    startBluetoothScoVirtualCall(_arg042);
                    reply.writeNoException();
                    return true;
                case 76:
                    IBinder _arg043 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    stopBluetoothSco(_arg043);
                    reply.writeNoException();
                    return true;
                case 77:
                    int _arg044 = data.readInt();
                    IBinder _arg114 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    forceVolumeControlStream(_arg044, _arg114);
                    reply.writeNoException();
                    return true;
                case 78:
                    IRingtonePlayer _arg045 = IRingtonePlayer.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setRingtonePlayer(_arg045);
                    reply.writeNoException();
                    return true;
                case 79:
                    IRingtonePlayer _result37 = getRingtonePlayer();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result37);
                    return true;
                case 80:
                    int _result38 = getUiSoundsStreamType();
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                case 81:
                    List _result39 = getIndependentStreamTypes();
                    reply.writeNoException();
                    reply.writeList(_result39);
                    return true;
                case 82:
                    int _arg046 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result40 = getStreamTypeAlias(_arg046);
                    reply.writeNoException();
                    reply.writeInt(_result40);
                    return true;
                case 83:
                    boolean _result41 = isVolumeControlUsingVolumeGroups();
                    reply.writeNoException();
                    reply.writeBoolean(_result41);
                    return true;
                case 84:
                    IStreamAliasingDispatcher _arg047 = IStreamAliasingDispatcher.Stub.asInterface(data.readStrongBinder());
                    boolean _arg115 = data.readBoolean();
                    data.enforceNoDataAvail();
                    registerStreamAliasingDispatcher(_arg047, _arg115);
                    reply.writeNoException();
                    return true;
                case 85:
                    boolean _arg048 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setNotifAliasRingForTest(_arg048);
                    reply.writeNoException();
                    return true;
                case 86:
                    return onTransact$setWiredDeviceConnectionState$(data, reply);
                case 87:
                    IAudioRoutesObserver _arg049 = IAudioRoutesObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    AudioRoutesInfo _result42 = startWatchingRoutes(_arg049);
                    reply.writeNoException();
                    reply.writeTypedObject(_result42, 1);
                    return true;
                case 88:
                    boolean _result43 = isCameraSoundForced();
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 89:
                    IVolumeController _arg050 = IVolumeController.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setVolumeController(_arg050);
                    reply.writeNoException();
                    return true;
                case 90:
                    IVolumeController _result44 = getVolumeController();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result44);
                    return true;
                case 91:
                    IVolumeController _arg051 = IVolumeController.Stub.asInterface(data.readStrongBinder());
                    boolean _arg116 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyVolumeControllerVisible(_arg051, _arg116);
                    reply.writeNoException();
                    return true;
                case 92:
                    int _arg052 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result45 = isStreamAffectedByRingerMode(_arg052);
                    reply.writeNoException();
                    reply.writeBoolean(_result45);
                    return true;
                case 93:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result46 = isStreamAffectedByMute(_arg053);
                    reply.writeNoException();
                    reply.writeBoolean(_result46);
                    return true;
                case 94:
                    int _arg054 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result47 = isStreamMutableByUi(_arg054);
                    reply.writeNoException();
                    reply.writeBoolean(_result47);
                    return true;
                case 95:
                    String _arg055 = data.readString();
                    data.enforceNoDataAvail();
                    disableSafeMediaVolume(_arg055);
                    reply.writeNoException();
                    return true;
                case 96:
                    String _arg056 = data.readString();
                    data.enforceNoDataAvail();
                    lowerVolumeToRs1(_arg056);
                    return true;
                case 97:
                    float _result48 = getOutputRs2UpperBound();
                    reply.writeNoException();
                    reply.writeFloat(_result48);
                    return true;
                case 98:
                    float _arg057 = data.readFloat();
                    data.enforceNoDataAvail();
                    setOutputRs2UpperBound(_arg057);
                    return true;
                case 99:
                    float _result49 = getCsd();
                    reply.writeNoException();
                    reply.writeFloat(_result49);
                    return true;
                case 100:
                    float _arg058 = data.readFloat();
                    data.enforceNoDataAvail();
                    setCsd(_arg058);
                    return true;
                case 101:
                    boolean _arg059 = data.readBoolean();
                    data.enforceNoDataAvail();
                    forceUseFrameworkMel(_arg059);
                    return true;
                case 102:
                    boolean _arg060 = data.readBoolean();
                    data.enforceNoDataAvail();
                    forceComputeCsdOnAllDevices(_arg060);
                    return true;
                case 103:
                    boolean _result50 = isCsdEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result50);
                    return true;
                case 104:
                    boolean _result51 = isCsdAsAFeatureAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result51);
                    return true;
                case 105:
                    boolean _result52 = isCsdAsAFeatureEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 106:
                    boolean _arg061 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCsdAsAFeatureEnabled(_arg061);
                    return true;
                case 107:
                    return onTransact$setBluetoothAudioDeviceCategory_legacy$(data, reply);
                case 108:
                    String _arg062 = data.readString();
                    boolean _arg117 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result53 = getBluetoothAudioDeviceCategory_legacy(_arg062, _arg117);
                    reply.writeNoException();
                    reply.writeInt(_result53);
                    return true;
                case 109:
                    String _arg063 = data.readString();
                    int _arg118 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result54 = setBluetoothAudioDeviceCategory(_arg063, _arg118);
                    reply.writeNoException();
                    reply.writeBoolean(_result54);
                    return true;
                case 110:
                    String _arg064 = data.readString();
                    data.enforceNoDataAvail();
                    int _result55 = getBluetoothAudioDeviceCategory(_arg064);
                    reply.writeNoException();
                    reply.writeInt(_result55);
                    return true;
                case 111:
                    String _arg065 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result56 = isBluetoothAudioDeviceCategoryFixed(_arg065);
                    reply.writeNoException();
                    reply.writeBoolean(_result56);
                    return true;
                case 112:
                    boolean _arg066 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result57 = setHdmiSystemAudioSupported(_arg066);
                    reply.writeNoException();
                    reply.writeInt(_result57);
                    return true;
                case 113:
                    boolean _result58 = isHdmiSystemAudioSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result58);
                    return true;
                case 114:
                    return onTransact$registerAudioPolicy$(data, reply);
                case 115:
                    IAudioPolicyCallback _arg067 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterAudioPolicyAsync(_arg067);
                    return true;
                case 116:
                    List<android.media.audiopolicy.AudioMix> _result59 = getRegisteredPolicyMixes();
                    reply.writeNoException();
                    reply.writeTypedList(_result59, 1);
                    return true;
                case 117:
                    IAudioPolicyCallback _arg068 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterAudioPolicy(_arg068);
                    reply.writeNoException();
                    return true;
                case 118:
                    android.media.audiopolicy.AudioPolicyConfig _arg069 = (android.media.audiopolicy.AudioPolicyConfig) data.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    IAudioPolicyCallback _arg119 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result60 = addMixForPolicy(_arg069, _arg119);
                    reply.writeNoException();
                    reply.writeInt(_result60);
                    return true;
                case 119:
                    android.media.audiopolicy.AudioPolicyConfig _arg070 = (android.media.audiopolicy.AudioPolicyConfig) data.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    IAudioPolicyCallback _arg120 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result61 = removeMixForPolicy(_arg070, _arg120);
                    reply.writeNoException();
                    reply.writeInt(_result61);
                    return true;
                case 120:
                    return onTransact$updateMixingRulesForPolicy$(data, reply);
                case 121:
                    int _arg071 = data.readInt();
                    IAudioPolicyCallback _arg121 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result62 = setFocusPropertiesForPolicy(_arg071, _arg121);
                    reply.writeNoException();
                    reply.writeInt(_result62);
                    return true;
                case 122:
                    VolumePolicy _arg072 = (VolumePolicy) data.readTypedObject(VolumePolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setVolumePolicy(_arg072);
                    reply.writeNoException();
                    return true;
                case 123:
                    boolean _result63 = hasRegisteredDynamicPolicy();
                    reply.writeNoException();
                    reply.writeBoolean(_result63);
                    return true;
                case 124:
                    IRecordingConfigDispatcher _arg073 = IRecordingConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerRecordingCallback(_arg073);
                    reply.writeNoException();
                    return true;
                case 125:
                    IRecordingConfigDispatcher _arg074 = IRecordingConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterRecordingCallback(_arg074);
                    return true;
                case 126:
                    List<AudioRecordingConfiguration> _result64 = getActiveRecordingConfigurations();
                    reply.writeNoException();
                    reply.writeTypedList(_result64, 1);
                    return true;
                case 127:
                    IPlaybackConfigDispatcher _arg075 = IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerPlaybackCallback(_arg075);
                    reply.writeNoException();
                    return true;
                case 128:
                    IPlaybackConfigDispatcher _arg076 = IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterPlaybackCallback(_arg076);
                    return true;
                case 129:
                    List<AudioPlaybackConfiguration> _result65 = getActivePlaybackConfigurations();
                    reply.writeNoException();
                    reply.writeTypedList(_result65, 1);
                    return true;
                case 130:
                    int _arg077 = data.readInt();
                    AudioAttributes _arg122 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    int _result66 = getFocusRampTimeMs(_arg077, _arg122);
                    reply.writeNoException();
                    reply.writeInt(_result66);
                    return true;
                case 131:
                    return onTransact$dispatchFocusChange$(data, reply);
                case 132:
                    return onTransact$dispatchFocusChangeWithFade$(data, reply);
                case 133:
                    int _arg078 = data.readInt();
                    boolean _arg123 = data.readBoolean();
                    data.enforceNoDataAvail();
                    playerHasOpPlayAudio(_arg078, _arg123);
                    return true;
                case 134:
                    return onTransact$handleBluetoothActiveDeviceChanged$(data, reply);
                case 135:
                    return onTransact$setFocusRequestResultFromExtPolicy$(data, reply);
                case 136:
                    IAudioServerStateDispatcher _arg079 = IAudioServerStateDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerAudioServerStateDispatcher(_arg079);
                    reply.writeNoException();
                    return true;
                case 137:
                    IAudioServerStateDispatcher _arg080 = IAudioServerStateDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterAudioServerStateDispatcher(_arg080);
                    return true;
                case 138:
                    boolean _result67 = isAudioServerRunning();
                    reply.writeNoException();
                    reply.writeBoolean(_result67);
                    return true;
                case 139:
                    return onTransact$setUidDeviceAffinity$(data, reply);
                case 140:
                    IAudioPolicyCallback _arg081 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg124 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result68 = removeUidDeviceAffinity(_arg081, _arg124);
                    reply.writeNoException();
                    reply.writeInt(_result68);
                    return true;
                case 141:
                    return onTransact$setUserIdDeviceAffinity$(data, reply);
                case 142:
                    IAudioPolicyCallback _arg082 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result69 = removeUserIdDeviceAffinity(_arg082, _arg125);
                    reply.writeNoException();
                    reply.writeInt(_result69);
                    return true;
                case 143:
                    Uri _arg083 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result70 = hasHapticChannels(_arg083);
                    reply.writeNoException();
                    reply.writeBoolean(_result70);
                    return true;
                case 144:
                    boolean _result71 = isCallScreeningModeSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result71);
                    return true;
                case 145:
                    int _arg084 = data.readInt();
                    List<AudioDeviceAttributes> _arg126 = data.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    int _result72 = setPreferredDevicesForStrategy(_arg084, _arg126);
                    reply.writeNoException();
                    reply.writeInt(_result72);
                    return true;
                case 146:
                    int _arg085 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result73 = removePreferredDevicesForStrategy(_arg085);
                    reply.writeNoException();
                    reply.writeInt(_result73);
                    return true;
                case 147:
                    int _arg086 = data.readInt();
                    data.enforceNoDataAvail();
                    List<AudioDeviceAttributes> _result74 = getPreferredDevicesForStrategy(_arg086);
                    reply.writeNoException();
                    reply.writeTypedList(_result74, 1);
                    return true;
                case 148:
                    int _arg087 = data.readInt();
                    AudioDeviceAttributes _arg127 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    int _result75 = setDeviceAsNonDefaultForStrategy(_arg087, _arg127);
                    reply.writeNoException();
                    reply.writeInt(_result75);
                    return true;
                case 149:
                    int _arg088 = data.readInt();
                    AudioDeviceAttributes _arg128 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    int _result76 = removeDeviceAsNonDefaultForStrategy(_arg088, _arg128);
                    reply.writeNoException();
                    reply.writeInt(_result76);
                    return true;
                case 150:
                    int _arg089 = data.readInt();
                    data.enforceNoDataAvail();
                    List<AudioDeviceAttributes> _result77 = getNonDefaultDevicesForStrategy(_arg089);
                    reply.writeNoException();
                    reply.writeTypedList(_result77, 1);
                    return true;
                case 151:
                    AudioAttributes _arg090 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    List<AudioDeviceAttributes> _result78 = getDevicesForAttributes(_arg090);
                    reply.writeNoException();
                    reply.writeTypedList(_result78, 1);
                    return true;
                case 152:
                    AudioAttributes _arg091 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    List<AudioDeviceAttributes> _result79 = getDevicesForAttributesUnprotected(_arg091);
                    reply.writeNoException();
                    reply.writeTypedList(_result79, 1);
                    return true;
                case 153:
                    AudioAttributes _arg092 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    IDevicesForAttributesCallback _arg129 = IDevicesForAttributesCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addOnDevicesForAttributesChangedListener(_arg092, _arg129);
                    reply.writeNoException();
                    return true;
                case 154:
                    IDevicesForAttributesCallback _arg093 = IDevicesForAttributesCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeOnDevicesForAttributesChangedListener(_arg093);
                    return true;
                case 155:
                    int _arg094 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result80 = setAllowedCapturePolicy(_arg094);
                    reply.writeNoException();
                    reply.writeInt(_result80);
                    return true;
                case 156:
                    int _result81 = getAllowedCapturePolicy();
                    reply.writeNoException();
                    reply.writeInt(_result81);
                    return true;
                case 157:
                    IStrategyPreferredDevicesDispatcher _arg095 = IStrategyPreferredDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerStrategyPreferredDevicesDispatcher(_arg095);
                    reply.writeNoException();
                    return true;
                case 158:
                    IStrategyPreferredDevicesDispatcher _arg096 = IStrategyPreferredDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterStrategyPreferredDevicesDispatcher(_arg096);
                    return true;
                case 159:
                    IStrategyNonDefaultDevicesDispatcher _arg097 = IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerStrategyNonDefaultDevicesDispatcher(_arg097);
                    reply.writeNoException();
                    return true;
                case 160:
                    IStrategyNonDefaultDevicesDispatcher _arg098 = IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterStrategyNonDefaultDevicesDispatcher(_arg098);
                    return true;
                case 161:
                    boolean _arg099 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRttEnabled(_arg099);
                    return true;
                case 162:
                    return onTransact$setDeviceVolumeBehavior$(data, reply);
                case 163:
                    AudioDeviceAttributes _arg0100 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    int _result82 = getDeviceVolumeBehavior(_arg0100);
                    reply.writeNoException();
                    reply.writeInt(_result82);
                    return true;
                case 164:
                    boolean _arg0101 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMultiAudioFocusEnabled(_arg0101);
                    return true;
                case 165:
                    int _arg0102 = data.readInt();
                    List<AudioDeviceAttributes> _arg130 = data.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    int _result83 = setPreferredDevicesForCapturePreset(_arg0102, _arg130);
                    reply.writeNoException();
                    reply.writeInt(_result83);
                    return true;
                case 166:
                    int _arg0103 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result84 = clearPreferredDevicesForCapturePreset(_arg0103);
                    reply.writeNoException();
                    reply.writeInt(_result84);
                    return true;
                case 167:
                    int _arg0104 = data.readInt();
                    data.enforceNoDataAvail();
                    List<AudioDeviceAttributes> _result85 = getPreferredDevicesForCapturePreset(_arg0104);
                    reply.writeNoException();
                    reply.writeTypedList(_result85, 1);
                    return true;
                case 168:
                    ICapturePresetDevicesRoleDispatcher _arg0105 = ICapturePresetDevicesRoleDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCapturePresetDevicesRoleDispatcher(_arg0105);
                    reply.writeNoException();
                    return true;
                case 169:
                    ICapturePresetDevicesRoleDispatcher _arg0106 = ICapturePresetDevicesRoleDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCapturePresetDevicesRoleDispatcher(_arg0106);
                    return true;
                case 170:
                    return onTransact$adjustStreamVolumeForUid$(data, reply);
                case 171:
                    return onTransact$adjustSuggestedStreamVolumeForUid$(data, reply);
                case 172:
                    return onTransact$setStreamVolumeForUid$(data, reply);
                case 173:
                    int _arg0107 = data.readInt();
                    int _arg131 = data.readInt();
                    data.enforceNoDataAvail();
                    adjustVolume(_arg0107, _arg131);
                    return true;
                case 174:
                    return onTransact$adjustSuggestedStreamVolume$(data, reply);
                case 175:
                    boolean _arg0108 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result86 = isMusicActive(_arg0108);
                    reply.writeNoException();
                    reply.writeBoolean(_result86);
                    return true;
                case 176:
                    int _arg0109 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result87 = getDeviceMaskForStream(_arg0109);
                    reply.writeNoException();
                    reply.writeInt(_result87);
                    return true;
                case 177:
                    int[] _result88 = getAvailableCommunicationDeviceIds();
                    reply.writeNoException();
                    reply.writeIntArray(_result88);
                    return true;
                case 178:
                    IBinder _arg0110 = data.readStrongBinder();
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result89 = setCommunicationDevice(_arg0110, _arg132);
                    reply.writeNoException();
                    reply.writeBoolean(_result89);
                    return true;
                case 179:
                    int _result90 = getCommunicationDevice();
                    reply.writeNoException();
                    reply.writeInt(_result90);
                    return true;
                case 180:
                    ICommunicationDeviceDispatcher _arg0111 = ICommunicationDeviceDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCommunicationDeviceDispatcher(_arg0111);
                    reply.writeNoException();
                    return true;
                case 181:
                    ICommunicationDeviceDispatcher _arg0112 = ICommunicationDeviceDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCommunicationDeviceDispatcher(_arg0112);
                    return true;
                case 182:
                    boolean _result91 = areNavigationRepeatSoundEffectsEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result91);
                    return true;
                case 183:
                    boolean _arg0113 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setNavigationRepeatSoundEffectsEnabled(_arg0113);
                    return true;
                case 184:
                    boolean _result92 = isHomeSoundEffectEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result92);
                    return true;
                case 185:
                    boolean _arg0114 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setHomeSoundEffectEnabled(_arg0114);
                    return true;
                case 186:
                    AudioDeviceAttributes _arg0115 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    long _arg133 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result93 = setAdditionalOutputDeviceDelay(_arg0115, _arg133);
                    reply.writeNoException();
                    reply.writeBoolean(_result93);
                    return true;
                case 187:
                    AudioDeviceAttributes _arg0116 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    long _result94 = getAdditionalOutputDeviceDelay(_arg0116);
                    reply.writeNoException();
                    reply.writeLong(_result94);
                    return true;
                case 188:
                    AudioDeviceAttributes _arg0117 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    long _result95 = getMaxAdditionalOutputDeviceDelay(_arg0117);
                    reply.writeNoException();
                    reply.writeLong(_result95);
                    return true;
                case 189:
                    return onTransact$requestAudioFocusForTest$(data, reply);
                case 190:
                    return onTransact$abandonAudioFocusForTest$(data, reply);
                case 191:
                    AudioAttributes _arg0118 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    long _result96 = getFadeOutDurationOnFocusLossMillis(_arg0118);
                    reply.writeNoException();
                    reply.writeLong(_result96);
                    return true;
                case 192:
                    List _result97 = getFocusDuckedUidsForTest();
                    reply.writeNoException();
                    reply.writeList(_result97);
                    return true;
                case 193:
                    long _result98 = getFocusFadeOutDurationForTest();
                    reply.writeNoException();
                    reply.writeLong(_result98);
                    return true;
                case 194:
                    long _result99 = getFocusUnmuteDelayAfterFadeOutForTest();
                    reply.writeNoException();
                    reply.writeLong(_result99);
                    return true;
                case 195:
                    IBinder _arg0119 = data.readStrongBinder();
                    int[] _arg134 = data.createIntArray();
                    data.enforceNoDataAvail();
                    boolean _result100 = enterAudioFocusFreezeForTest(_arg0119, _arg134);
                    reply.writeNoException();
                    reply.writeBoolean(_result100);
                    return true;
                case 196:
                    IBinder _arg0120 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result101 = exitAudioFocusFreezeForTest(_arg0120);
                    reply.writeNoException();
                    reply.writeBoolean(_result101);
                    return true;
                case 197:
                    IAudioModeDispatcher _arg0121 = IAudioModeDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerModeDispatcher(_arg0121);
                    reply.writeNoException();
                    return true;
                case 198:
                    IAudioModeDispatcher _arg0122 = IAudioModeDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterModeDispatcher(_arg0122);
                    return true;
                case 199:
                    int _result102 = getSpatializerImmersiveAudioLevel();
                    reply.writeNoException();
                    reply.writeInt(_result102);
                    return true;
                case 200:
                    boolean _result103 = isSpatializerEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result103);
                    return true;
                case 201:
                    boolean _result104 = isSpatializerAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result104);
                    return true;
                case 202:
                    AudioDeviceAttributes _arg0123 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result105 = isSpatializerAvailableForDevice(_arg0123);
                    reply.writeNoException();
                    reply.writeBoolean(_result105);
                    return true;
                case 203:
                    AudioDeviceAttributes _arg0124 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result106 = hasHeadTracker(_arg0124);
                    reply.writeNoException();
                    reply.writeBoolean(_result106);
                    return true;
                case 204:
                    boolean _arg0125 = data.readBoolean();
                    AudioDeviceAttributes _arg135 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    setHeadTrackerEnabled(_arg0125, _arg135);
                    reply.writeNoException();
                    return true;
                case 205:
                    AudioDeviceAttributes _arg0126 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result107 = isHeadTrackerEnabled(_arg0126);
                    reply.writeNoException();
                    reply.writeBoolean(_result107);
                    return true;
                case 206:
                    boolean _result108 = isHeadTrackerAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result108);
                    return true;
                case 207:
                    ISpatializerHeadTrackerAvailableCallback _arg0127 = ISpatializerHeadTrackerAvailableCallback.Stub.asInterface(data.readStrongBinder());
                    boolean _arg136 = data.readBoolean();
                    data.enforceNoDataAvail();
                    registerSpatializerHeadTrackerAvailableCallback(_arg0127, _arg136);
                    reply.writeNoException();
                    return true;
                case 208:
                    boolean _arg0128 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSpatializerEnabled(_arg0128);
                    reply.writeNoException();
                    return true;
                case 209:
                    AudioAttributes _arg0129 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    AudioFormat _arg137 = (AudioFormat) data.readTypedObject(AudioFormat.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result109 = canBeSpatialized(_arg0129, _arg137);
                    reply.writeNoException();
                    reply.writeBoolean(_result109);
                    return true;
                case 210:
                    ISpatializerCallback _arg0130 = ISpatializerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerSpatializerCallback(_arg0130);
                    reply.writeNoException();
                    return true;
                case 211:
                    ISpatializerCallback _arg0131 = ISpatializerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterSpatializerCallback(_arg0131);
                    reply.writeNoException();
                    return true;
                case 212:
                    ISpatializerHeadTrackingModeCallback _arg0132 = ISpatializerHeadTrackingModeCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerSpatializerHeadTrackingCallback(_arg0132);
                    reply.writeNoException();
                    return true;
                case 213:
                    ISpatializerHeadTrackingModeCallback _arg0133 = ISpatializerHeadTrackingModeCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterSpatializerHeadTrackingCallback(_arg0133);
                    reply.writeNoException();
                    return true;
                case 214:
                    ISpatializerHeadToSoundStagePoseCallback _arg0134 = ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerHeadToSoundstagePoseCallback(_arg0134);
                    reply.writeNoException();
                    return true;
                case 215:
                    ISpatializerHeadToSoundStagePoseCallback _arg0135 = ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterHeadToSoundstagePoseCallback(_arg0135);
                    reply.writeNoException();
                    return true;
                case 216:
                    List<AudioDeviceAttributes> _result110 = getSpatializerCompatibleAudioDevices();
                    reply.writeNoException();
                    reply.writeTypedList(_result110, 1);
                    return true;
                case 217:
                    AudioDeviceAttributes _arg0136 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    addSpatializerCompatibleAudioDevice(_arg0136);
                    reply.writeNoException();
                    return true;
                case 218:
                    AudioDeviceAttributes _arg0137 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    removeSpatializerCompatibleAudioDevice(_arg0137);
                    reply.writeNoException();
                    return true;
                case 219:
                    int _arg0138 = data.readInt();
                    data.enforceNoDataAvail();
                    setDesiredHeadTrackingMode(_arg0138);
                    reply.writeNoException();
                    return true;
                case 220:
                    int _result111 = getDesiredHeadTrackingMode();
                    reply.writeNoException();
                    reply.writeInt(_result111);
                    return true;
                case 221:
                    int[] _result112 = getSupportedHeadTrackingModes();
                    reply.writeNoException();
                    reply.writeIntArray(_result112);
                    return true;
                case 222:
                    int _result113 = getActualHeadTrackingMode();
                    reply.writeNoException();
                    reply.writeInt(_result113);
                    return true;
                case 223:
                    float[] _arg0139 = data.createFloatArray();
                    data.enforceNoDataAvail();
                    setSpatializerGlobalTransform(_arg0139);
                    return true;
                case 224:
                    recenterHeadTracker();
                    return true;
                case 225:
                    int _arg0140 = data.readInt();
                    byte[] _arg138 = data.createByteArray();
                    data.enforceNoDataAvail();
                    setSpatializerParameter(_arg0140, _arg138);
                    reply.writeNoException();
                    return true;
                case 226:
                    int _arg0141 = data.readInt();
                    byte[] _arg139 = data.createByteArray();
                    data.enforceNoDataAvail();
                    getSpatializerParameter(_arg0141, _arg139);
                    reply.writeNoException();
                    reply.writeByteArray(_arg139);
                    return true;
                case 227:
                    int _result114 = getSpatializerOutput();
                    reply.writeNoException();
                    reply.writeInt(_result114);
                    return true;
                case 228:
                    ISpatializerOutputCallback _arg0142 = ISpatializerOutputCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerSpatializerOutputCallback(_arg0142);
                    reply.writeNoException();
                    return true;
                case 229:
                    ISpatializerOutputCallback _arg0143 = ISpatializerOutputCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterSpatializerOutputCallback(_arg0143);
                    reply.writeNoException();
                    return true;
                case 230:
                    boolean _result115 = isVolumeFixed();
                    reply.writeNoException();
                    reply.writeBoolean(_result115);
                    return true;
                case 231:
                    VolumeInfo _result116 = getDefaultVolumeInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result116, 1);
                    return true;
                case 232:
                    boolean _result117 = isPstnCallAudioInterceptable();
                    reply.writeNoException();
                    reply.writeBoolean(_result117);
                    return true;
                case 233:
                    return onTransact$muteAwaitConnection$(data, reply);
                case 234:
                    AudioDeviceAttributes _arg0144 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    cancelMuteAwaitConnection(_arg0144);
                    return true;
                case 235:
                    AudioDeviceAttributes _result118 = getMutingExpectedDevice();
                    reply.writeNoException();
                    reply.writeTypedObject(_result118, 1);
                    return true;
                case 236:
                    IMuteAwaitConnectionCallback _arg0145 = IMuteAwaitConnectionCallback.Stub.asInterface(data.readStrongBinder());
                    boolean _arg140 = data.readBoolean();
                    data.enforceNoDataAvail();
                    registerMuteAwaitConnectionDispatcher(_arg0145, _arg140);
                    reply.writeNoException();
                    return true;
                case 237:
                    AudioDeviceAttributes _arg0146 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
                    boolean _arg141 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setTestDeviceConnectionState(_arg0146, _arg141);
                    reply.writeNoException();
                    return true;
                case 238:
                    boolean _arg0147 = data.readBoolean();
                    IDeviceVolumeBehaviorDispatcher _arg142 = IDeviceVolumeBehaviorDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerDeviceVolumeBehaviorDispatcher(_arg0147, _arg142);
                    reply.writeNoException();
                    return true;
                case 239:
                    List<AudioFocusInfo> _result119 = getFocusStack();
                    reply.writeNoException();
                    reply.writeTypedList(_result119, 1);
                    return true;
                case 240:
                    AudioFocusInfo _arg0148 = (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
                    IAudioPolicyCallback _arg143 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result120 = sendFocusLoss(_arg0148, _arg143);
                    reply.writeNoException();
                    reply.writeBoolean(_result120);
                    return true;
                case 241:
                    int[] _arg0149 = data.createIntArray();
                    data.enforceNoDataAvail();
                    addAssistantServicesUids(_arg0149);
                    reply.writeNoException();
                    return true;
                case 242:
                    int[] _arg0150 = data.createIntArray();
                    data.enforceNoDataAvail();
                    removeAssistantServicesUids(_arg0150);
                    reply.writeNoException();
                    return true;
                case 243:
                    int[] _arg0151 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setActiveAssistantServiceUids(_arg0151);
                    reply.writeNoException();
                    return true;
                case 244:
                    int[] _result121 = getAssistantServicesUids();
                    reply.writeNoException();
                    reply.writeIntArray(_result121);
                    return true;
                case 245:
                    int[] _result122 = getActiveAssistantServiceUids();
                    reply.writeNoException();
                    reply.writeIntArray(_result122);
                    return true;
                case 246:
                    return onTransact$registerDeviceVolumeDispatcherForAbsoluteVolume$(data, reply);
                case 247:
                    AudioHalVersionInfo _result123 = getHalVersion();
                    reply.writeNoException();
                    reply.writeTypedObject(_result123, 1);
                    return true;
                case 248:
                    return onTransact$setPreferredMixerAttributes$(data, reply);
                case 249:
                    AudioAttributes _arg0152 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    int _arg144 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result124 = clearPreferredMixerAttributes(_arg0152, _arg144);
                    reply.writeNoException();
                    reply.writeInt(_result124);
                    return true;
                case 250:
                    IPreferredMixerAttributesDispatcher _arg0153 = IPreferredMixerAttributesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerPreferredMixerAttributesDispatcher(_arg0153);
                    reply.writeNoException();
                    return true;
                case 251:
                    IPreferredMixerAttributesDispatcher _arg0154 = IPreferredMixerAttributesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterPreferredMixerAttributesDispatcher(_arg0154);
                    return true;
                case 252:
                    boolean _result125 = supportsBluetoothVariableLatency();
                    reply.writeNoException();
                    reply.writeBoolean(_result125);
                    return true;
                case 253:
                    boolean _arg0155 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBluetoothVariableLatencyEnabled(_arg0155);
                    reply.writeNoException();
                    return true;
                case 254:
                    boolean _result126 = isBluetoothVariableLatencyEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result126);
                    return true;
                case 255:
                    ILoudnessCodecUpdatesDispatcher _arg0156 = ILoudnessCodecUpdatesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerLoudnessCodecUpdatesDispatcher(_arg0156);
                    reply.writeNoException();
                    return true;
                case 256:
                    ILoudnessCodecUpdatesDispatcher _arg0157 = ILoudnessCodecUpdatesDispatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterLoudnessCodecUpdatesDispatcher(_arg0157);
                    reply.writeNoException();
                    return true;
                case 257:
                    int _arg0158 = data.readInt();
                    data.enforceNoDataAvail();
                    startLoudnessCodecUpdates(_arg0158);
                    return true;
                case 258:
                    int _arg0159 = data.readInt();
                    data.enforceNoDataAvail();
                    stopLoudnessCodecUpdates(_arg0159);
                    return true;
                case 259:
                    return onTransact$addLoudnessCodecInfo$(data, reply);
                case 260:
                    int _arg0160 = data.readInt();
                    LoudnessCodecInfo _arg145 = (LoudnessCodecInfo) data.readTypedObject(LoudnessCodecInfo.CREATOR);
                    data.enforceNoDataAvail();
                    removeLoudnessCodecInfo(_arg0160, _arg145);
                    return true;
                case 261:
                    LoudnessCodecInfo _arg0161 = (LoudnessCodecInfo) data.readTypedObject(LoudnessCodecInfo.CREATOR);
                    data.enforceNoDataAvail();
                    PersistableBundle _result127 = getLoudnessParams(_arg0161);
                    reply.writeNoException();
                    reply.writeTypedObject(_result127, 1);
                    return true;
                case 262:
                    FadeManagerConfiguration _arg0162 = (FadeManagerConfiguration) data.readTypedObject(FadeManagerConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    int _result128 = setFadeManagerConfigurationForFocusLoss(_arg0162);
                    reply.writeNoException();
                    reply.writeInt(_result128);
                    return true;
                case 263:
                    int _result129 = clearFadeManagerConfigurationForFocusLoss();
                    reply.writeNoException();
                    reply.writeInt(_result129);
                    return true;
                case 264:
                    FadeManagerConfiguration _result130 = getFadeManagerConfigurationForFocusLoss();
                    reply.writeNoException();
                    reply.writeTypedObject(_result130, 1);
                    return true;
                case 265:
                    AudioAttributes _arg0163 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result131 = shouldNotificationSoundPlay(_arg0163);
                    reply.writeNoException();
                    reply.writeBoolean(_result131);
                    return true;
                case 266:
                    String _arg0164 = data.readString();
                    data.enforceNoDataAvail();
                    setAudioServiceConfig(_arg0164);
                    reply.writeNoException();
                    return true;
                case 267:
                    String _arg0165 = data.readString();
                    data.enforceNoDataAvail();
                    String _result132 = getAudioServiceConfig(_arg0165);
                    reply.writeNoException();
                    reply.writeString(_result132);
                    return true;
                case 268:
                    boolean _result133 = shouldShowRingtoneVolume();
                    reply.writeNoException();
                    reply.writeBoolean(_result133);
                    return true;
                case 269:
                    int _arg0166 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result134 = secGetActiveStreamType(_arg0166);
                    reply.writeNoException();
                    reply.writeInt(_result134);
                    return true;
                case 270:
                    int _arg0167 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result135 = getUidForDevice(_arg0167);
                    reply.writeNoException();
                    reply.writeInt(_result135);
                    return true;
                case 271:
                    return onTransact$setAppDevice$(data, reply);
                case 272:
                    int _arg0168 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result136 = getAppDevice(_arg0168);
                    reply.writeNoException();
                    reply.writeInt(_result136);
                    return true;
                case 273:
                    return onTransact$setAppVolume$(data, reply);
                case 274:
                    int _arg0169 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result137 = getAppVolume(_arg0169);
                    reply.writeNoException();
                    reply.writeInt(_result137);
                    return true;
                case 275:
                    return onTransact$setAppMute$(data, reply);
                case 276:
                    int _arg0170 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result138 = isAppMute(_arg0170);
                    reply.writeNoException();
                    reply.writeBoolean(_result138);
                    return true;
                case 277:
                    boolean _arg0171 = data.readBoolean();
                    boolean _arg146 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMultiSoundOn(_arg0171, _arg146);
                    reply.writeNoException();
                    return true;
                case 278:
                    boolean _result139 = isMultiSoundOn();
                    reply.writeNoException();
                    reply.writeBoolean(_result139);
                    return true;
                case 279:
                    return onTransact$setStreamVolumeForDeviceWithAttribution$(data, reply);
                case 280:
                    int _arg0172 = data.readInt();
                    int _arg147 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result140 = getStreamVolumeForDevice(_arg0172, _arg147);
                    reply.writeNoException();
                    reply.writeInt(_result140);
                    return true;
                case 281:
                    int _arg0173 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result141 = getPinAppInfo(_arg0173);
                    reply.writeNoException();
                    reply.writeString(_result141);
                    return true;
                case 282:
                    int _result142 = getPinDevice();
                    reply.writeNoException();
                    reply.writeInt(_result142);
                    return true;
                case 283:
                    String[] _result143 = getSelectedAppList();
                    reply.writeNoException();
                    reply.writeStringArray(_result143);
                    return true;
                case 284:
                    int _arg0174 = data.readInt();
                    String _arg148 = data.readString();
                    data.enforceNoDataAvail();
                    addPackage(_arg0174, _arg148);
                    reply.writeNoException();
                    return true;
                case 285:
                    String _arg0175 = data.readString();
                    data.enforceNoDataAvail();
                    removePackageForName(_arg0175);
                    reply.writeNoException();
                    return true;
                case 286:
                    String _arg0176 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result144 = isAlreadyInDB(_arg0176);
                    reply.writeNoException();
                    reply.writeBoolean(_result144);
                    return true;
                case 287:
                    String _arg0177 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result145 = isInAllowedList(_arg0177);
                    reply.writeNoException();
                    reply.writeBoolean(_result145);
                    return true;
                case 288:
                    return onTransact$setFineVolume$(data, reply);
                case 289:
                    int _arg0178 = data.readInt();
                    int _arg149 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result146 = getFineVolume(_arg0178, _arg149);
                    reply.writeNoException();
                    reply.writeInt(_result146);
                    return true;
                case 290:
                    boolean _arg0179 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setForceSpeakerOn(_arg0179);
                    reply.writeNoException();
                    return true;
                case 291:
                    boolean _result147 = isForceSpeakerOn();
                    reply.writeNoException();
                    reply.writeBoolean(_result147);
                    return true;
                case 292:
                    return onTransact$setDeviceToForceByUser$(data, reply);
                case 293:
                    int _arg0180 = data.readInt();
                    String _arg150 = data.readString();
                    data.enforceNoDataAvail();
                    setMuteInterval(_arg0180, _arg150);
                    reply.writeNoException();
                    return true;
                case 294:
                    int _result148 = getMuteInterval();
                    reply.writeNoException();
                    reply.writeInt(_result148);
                    return true;
                case 295:
                    int _result149 = getRemainingMuteIntervalMs();
                    reply.writeNoException();
                    reply.writeInt(_result149);
                    return true;
                case 296:
                    int _result150 = getPrevRingerMode();
                    reply.writeNoException();
                    reply.writeInt(_result150);
                    return true;
                case 297:
                    int _arg0181 = data.readInt();
                    PendingIntent _arg151 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    setSoundSettingEventBroadcastIntent(_arg0181, _arg151);
                    return true;
                case 298:
                    int[] _arg0182 = data.createIntArray();
                    data.enforceNoDataAvail();
                    boolean _result151 = setMediaVolumeSteps(_arg0182);
                    reply.writeNoException();
                    reply.writeBoolean(_result151);
                    return true;
                case 299:
                    int[] _result152 = getMediaVolumeSteps();
                    reply.writeNoException();
                    reply.writeIntArray(_result152);
                    return true;
                case 300:
                    int _arg0183 = data.readInt();
                    data.enforceNoDataAvail();
                    setRadioOutputPath(_arg0183);
                    reply.writeNoException();
                    return true;
                case 301:
                    int _result153 = getRadioOutputPath();
                    reply.writeNoException();
                    reply.writeInt(_result153);
                    return true;
                case 302:
                    dismissVolumePanel();
                    reply.writeNoException();
                    return true;
                case 303:
                    String _result154 = getCurrentAudioFocusPackageName();
                    reply.writeNoException();
                    reply.writeString(_result154);
                    return true;
                case 304:
                    int _arg0184 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result155 = isUsingAudio(_arg0184);
                    reply.writeNoException();
                    reply.writeBoolean(_result155);
                    return true;
                case 305:
                    return onTransact$setA2dpDeviceVolume$(data, reply);
                case 306:
                    BluetoothDevice _arg0185 = (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
                    int _arg152 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result156 = getA2dpDeviceVolume(_arg0185, _arg152);
                    reply.writeNoException();
                    reply.writeInt(_result156);
                    return true;
                case 307:
                    float[] _result157 = getFloatVolumeTable();
                    reply.writeNoException();
                    reply.writeFloatArray(_result157);
                    return true;
                case 308:
                    boolean _arg0186 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRemoteMic(_arg0186);
                    reply.writeNoException();
                    return true;
                case 309:
                    String _arg0187 = data.readString();
                    data.enforceNoDataAvail();
                    recordRingtoneChanger(_arg0187);
                    return true;
                case 310:
                    IPlaybackConfigDispatcher _arg0188 = IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    String _arg153 = data.readString();
                    data.enforceNoDataAvail();
                    registerPlaybackCallbackWithPackage(_arg0188, _arg153);
                    reply.writeNoException();
                    return true;
                case 311:
                    int _arg0189 = data.readInt();
                    data.enforceNoDataAvail();
                    setBtOffloadEnable(_arg0189);
                    reply.writeNoException();
                    return true;
                case 312:
                    boolean _result158 = isSafeMediaVolumeStateActive();
                    reply.writeNoException();
                    reply.writeBoolean(_result158);
                    return true;
                case 313:
                    int _arg0190 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result159 = getExcludedRingtoneTitles(_arg0190);
                    reply.writeNoException();
                    reply.writeStringList(_result159);
                    return true;
                case 314:
                    IVolumeController _arg0191 = IVolumeController.Stub.asInterface(data.readStrongBinder());
                    boolean _arg154 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifySafetyVolumeDialogVisible(_arg0191, _arg154);
                    return true;
                case 315:
                    int _result160 = getModeInternal();
                    reply.writeNoException();
                    reply.writeInt(_result160);
                    return true;
                case 316:
                    int _arg0192 = data.readInt();
                    data.enforceNoDataAvail();
                    setMicInputControlMode(_arg0192);
                    reply.writeNoException();
                    return true;
                case 317:
                    int _result161 = getMicModeType();
                    reply.writeNoException();
                    reply.writeInt(_result161);
                    return true;
                case 318:
                    int _result162 = getEarProtectLimit();
                    reply.writeNoException();
                    reply.writeInt(_result162);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAudioService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.IAudioService
            public int trackPlayer(PlayerBase.PlayerIdCard pic) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(pic, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerAttributes(int piid, AudioAttributes attr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeTypedObject(attr, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerEvent(int piid, int event, int eventId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeInt(event);
                    _data.writeInt(eventId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releasePlayer(int piid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int trackRecorder(IBinder recorder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(recorder);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recorderEvent(int riid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(riid);
                    _data.writeInt(event);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releaseRecorder(int riid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(riid);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerSessionId(int piid, int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void portEvent(int portId, int event, PersistableBundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(portId);
                    _data.writeInt(event);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolumeWithAttribution(int streamType, int direction, int flags, String callingPackage, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolume(int streamType, int index, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeWithAttribution(int streamType, int index, int flags, String callingPackage, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDeviceVolume(VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(vi, 0);
                    _data.writeTypedObject(ada, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public VolumeInfo getDeviceVolume(VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(vi, 0);
                    _data.writeTypedObject(ada, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    VolumeInfo _result = (VolumeInfo) _reply.readTypedObject(VolumeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleVolumeKey(KeyEvent event, boolean isOnTv, String callingPackage, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    _data.writeBoolean(isOnTv);
                    _data.writeString(callingPackage);
                    _data.writeString(caller);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamMute(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(startForcing);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMasterMute() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMasterMute(boolean mute, int flags, String callingPackage, int userId, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(mute);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMinVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMaxVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    List<android.media.audiopolicy.AudioVolumeGroup> _result = _reply.createTypedArrayList(android.media.audiopolicy.AudioVolumeGroup.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeGroupVolumeIndex(int groupId, int index, int flags, String callingPackage, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupVolumeIndex(int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupMaxVolumeIndex(int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupMinVolumeIndex(int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleVolumeForVolumeGroup(int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeGroupMuted(int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustVolumeGroupVolume(int groupId, int direction, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSupportedSystemUsages(int[] systemUsages) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(systemUsages);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getSupportedSystemUsages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    List<android.media.audiopolicy.AudioProductStrategy> _result = _reply.createTypedArrayList(android.media.audiopolicy.AudioProductStrategy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMicrophoneMuted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isUltrasoundSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHotwordStreamSupported(boolean lookbackAudio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(lookbackAudio);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMute(boolean on, String callingPackage, int userId, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMuteFromSwitch(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(40, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    _data.writeString(caller);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    _data.writeString(caller);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeExternal() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeInternal() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isValidRingerMode(int ringerMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    _data.writeInt(vibrateSetting);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVibrateSetting(int vibrateType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldVibrate(int vibrateType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeStrongBinder(cb);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffect(int effectType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectType);
                    _data.writeInt(userId);
                    this.mRemote.transact(51, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectType);
                    _data.writeFloat(volume);
                    this.mRemote.transact(52, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean loadSoundEffects() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unloadSoundEffects() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void reloadAudioSettings() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public Map getSurroundFormats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getReportedSurroundFormats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setSurroundFormatEnabled(int audioFormat, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioFormat);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSurroundFormatEnabled(int audioFormat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioFormat);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setEncodedSurroundMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getEncodedSurroundMode(int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpeakerphoneOn(IBinder cb, boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    _data.writeBoolean(on);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpeakerphoneOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothScoOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setA2dpSuspended(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setLeAudioSuspended(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothScoOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothA2dpOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothA2dpOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocus(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, String attributionTag, int flags, IAudioPolicyCallback pcb, int sdk) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    _data.writeInt(durationHint);
                    _data.writeStrongBinder(cb);
                    _data.writeStrongInterface(fd);
                    _data.writeString(clientId);
                    _data.writeString(callingPackageName);
                    _data.writeString(attributionTag);
                    _data.writeInt(flags);
                    _data.writeStrongInterface(pcb);
                    _data.writeInt(sdk);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocus(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(fd);
                    _data.writeString(clientId);
                    _data.writeTypedObject(aa, 0);
                    _data.writeString(callingPackageName);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioFocusClient(String clientId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(clientId);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCurrentAudioFocus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopBluetoothSco(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(player);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public IRingtonePlayer getRingtonePlayer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    IRingtonePlayer _result = IRingtonePlayer.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getUiSoundsStreamType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getIndependentStreamTypes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamTypeAlias(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeControlUsingVolumeGroups() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher isad, boolean register) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(isad);
                    _data.writeBoolean(register);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setNotifAliasRingForTest(boolean alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(alias);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setWiredDeviceConnectionState(AudioDeviceAttributes aa, int state, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    _data.writeInt(state);
                    _data.writeString(caller);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    AudioRoutesInfo _result = (AudioRoutesInfo) _reply.readTypedObject(AudioRoutesInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCameraSoundForced() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeController(IVolumeController controller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public IVolumeController getVolumeController() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    IVolumeController _result = IVolumeController.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamMutableByUi(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void lowerVolumeToRs1(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(96, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float getOutputRs2UpperBound() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(rs2Value);
                    this.mRemote.transact(98, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float getCsd() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setCsd(float csd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(csd);
                    this.mRemote.transact(100, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(useFrameworkMel);
                    this.mRemote.transact(101, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(computeCsdOnAllDevices);
                    this.mRemote.transact(102, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdAsAFeatureAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdAsAFeatureEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setCsdAsAFeatureEnabled(boolean csdToggleValue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(csdToggleValue);
                    this.mRemote.transact(106, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothAudioDeviceCategory_legacy(String address, boolean isBle, int deviceCategory) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeBoolean(isBle);
                    _data.writeInt(deviceCategory);
                    this.mRemote.transact(107, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getBluetoothAudioDeviceCategory_legacy(String address, boolean isBle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeBoolean(isBle);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setBluetoothAudioDeviceCategory(String address, int deviceCategory) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(deviceCategory);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getBluetoothAudioDeviceCategory(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothAudioDeviceCategoryFixed(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setHdmiSystemAudioSupported(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHdmiSystemAudioSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb, boolean hasFocusListener, boolean isFocusPolicy, boolean isTestFocusPolicy, boolean isVolumeController, IMediaProjection projection, AttributionSource attributionSource) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyConfig, 0);
                    _data.writeStrongInterface(pcb);
                    _data.writeBoolean(hasFocusListener);
                    _data.writeBoolean(isFocusPolicy);
                    _data.writeBoolean(isTestFocusPolicy);
                    _data.writeBoolean(isVolumeController);
                    _data.writeStrongInterface(projection);
                    _data.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(115, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                    List<android.media.audiopolicy.AudioMix> _result = _reply.createTypedArrayList(android.media.audiopolicy.AudioMix.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyConfig, 0);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policyConfig, 0);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] mixesToUpdate, AudioMixingRule[] updatedMixingRules, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(mixesToUpdate, 0);
                    _data.writeTypedArray(updatedMixingRules, 0);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(duckingBehavior);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumePolicy(VolumePolicy policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasRegisteredDynamicPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(rcdb);
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(rcdb);
                    this.mRemote.transact(125, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                    List<AudioRecordingConfiguration> _result = _reply.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcdb);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcdb);
                    this.mRemote.transact(128, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                    List<AudioPlaybackConfiguration> _result = _reply.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(focusGain);
                    _data.writeTypedObject(attr, 0);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(afi, 0);
                    _data.writeInt(focusChange);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChangeWithFade(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb, List<AudioFocusInfo> otherActiveAfis, FadeManagerConfiguration transientFadeMgrConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(afi, 0);
                    _data.writeInt(focusChange);
                    _data.writeStrongInterface(pcb);
                    _data.writeTypedList(otherActiveAfis, 0);
                    _data.writeTypedObject(transientFadeMgrConfig, 0);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeBoolean(hasOpPlayAudio);
                    this.mRemote.transact(133, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleBluetoothActiveDeviceChanged(BluetoothDevice newDevice, BluetoothDevice previousDevice, BluetoothProfileConnectionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(newDevice, 0);
                    _data.writeTypedObject(previousDevice, 0);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setFocusRequestResultFromExtPolicy(AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(afi, 0);
                    _data.writeInt(requestResult);
                    _data.writeStrongInterface(pcb);
                    this.mRemote.transact(135, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(asd);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(asd);
                    this.mRemote.transact(137, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAudioServerRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUidDeviceAffinity(IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcb);
                    _data.writeInt(uid);
                    _data.writeIntArray(deviceTypes);
                    _data.writeStringArray(deviceAddresses);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcb);
                    _data.writeInt(uid);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUserIdDeviceAffinity(IAudioPolicyCallback pcb, int userId, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcb);
                    _data.writeInt(userId);
                    _data.writeIntArray(deviceTypes);
                    _data.writeStringArray(deviceAddresses);
                    this.mRemote.transact(141, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUserIdDeviceAffinity(IAudioPolicyCallback pcb, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcb);
                    _data.writeInt(userId);
                    this.mRemote.transact(142, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHapticChannels(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    this.mRemote.transact(143, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCallScreeningModeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(144, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredDevicesForStrategy(int strategy, List<AudioDeviceAttributes> devices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strategy);
                    _data.writeTypedList(devices, 0);
                    this.mRemote.transact(145, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removePreferredDevicesForStrategy(int strategy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strategy);
                    this.mRemote.transact(146, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int strategy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strategy);
                    this.mRemote.transact(147, _data, _reply, 0);
                    _reply.readException();
                    List<AudioDeviceAttributes> _result = _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strategy);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(148, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strategy);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(149, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int strategy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strategy);
                    this.mRemote.transact(150, _data, _reply, 0);
                    _reply.readException();
                    List<AudioDeviceAttributes> _result = _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes attributes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(attributes, 0);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                    List<AudioDeviceAttributes> _result = _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(AudioAttributes attributes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(attributes, 0);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                    List<AudioDeviceAttributes> _result = _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addOnDevicesForAttributesChangedListener(AudioAttributes attributes, IDevicesForAttributesCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(attributes, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(154, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setAllowedCapturePolicy(int capturePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capturePolicy);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAllowedCapturePolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(158, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(160, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRttEnabled(boolean rttEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(rttEnabled);
                    this.mRemote.transact(161, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDeviceVolumeBehavior(AudioDeviceAttributes device, int deviceVolumeBehavior, String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(deviceVolumeBehavior);
                    _data.writeString(pkgName);
                    this.mRemote.transact(162, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDeviceVolumeBehavior(AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(163, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMultiAudioFocusEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(164, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredDevicesForCapturePreset(int capturePreset, List<AudioDeviceAttributes> devices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capturePreset);
                    _data.writeTypedList(devices, 0);
                    this.mRemote.transact(165, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearPreferredDevicesForCapturePreset(int capturePreset) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capturePreset);
                    this.mRemote.transact(166, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int capturePreset) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capturePreset);
                    this.mRemote.transact(167, _data, _reply, 0);
                    _reply.readException();
                    List<AudioDeviceAttributes> _result = _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(168, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(169, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolumeForUid(int streamType, int direction, int flags, String packageName, int uid, int pid, UserHandle userHandle, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeTypedObject(userHandle, 0);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(170, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolumeForUid(int streamType, int direction, int flags, String packageName, int uid, int pid, UserHandle userHandle, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeTypedObject(userHandle, 0);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(171, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeForUid(int streamType, int direction, int flags, String packageName, int uid, int pid, UserHandle userHandle, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeTypedObject(userHandle, 0);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(172, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustVolume(int direction, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    this.mRemote.transact(173, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    _data.writeInt(suggestedStreamType);
                    _data.writeInt(flags);
                    this.mRemote.transact(174, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMusicActive(boolean remotely) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(remotely);
                    this.mRemote.transact(175, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDeviceMaskForStream(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    this.mRemote.transact(176, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getAvailableCommunicationDeviceIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(177, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setCommunicationDevice(IBinder cb, int portId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    _data.writeInt(portId);
                    this.mRemote.transact(178, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCommunicationDevice() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(179, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(180, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(181, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(182, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setNavigationRepeatSoundEffectsEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(183, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHomeSoundEffectEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(184, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setHomeSoundEffectEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(185, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes device, long delayMillis) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeLong(delayMillis);
                    this.mRemote.transact(186, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(187, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(188, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocusForTest(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, int flags, int uid, int sdk) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    _data.writeInt(durationHint);
                    _data.writeStrongBinder(cb);
                    _data.writeStrongInterface(fd);
                    _data.writeString(clientId);
                    _data.writeString(callingPackageName);
                    _data.writeInt(flags);
                    _data.writeInt(uid);
                    _data.writeInt(sdk);
                    this.mRemote.transact(189, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocusForTest(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(fd);
                    _data.writeString(clientId);
                    _data.writeTypedObject(aa, 0);
                    _data.writeString(callingPackageName);
                    this.mRemote.transact(190, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFadeOutDurationOnFocusLossMillis(AudioAttributes aa) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    this.mRemote.transact(191, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getFocusDuckedUidsForTest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(192, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFocusFadeOutDurationForTest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(193, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFocusUnmuteDelayAfterFadeOutForTest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(194, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean enterAudioFocusFreezeForTest(IBinder cb, int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(195, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean exitAudioFocusFreezeForTest(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(196, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerModeDispatcher(IAudioModeDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(197, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterModeDispatcher(IAudioModeDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(198, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getSpatializerImmersiveAudioLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(199, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(200, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(201, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHeadTracker(AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(203, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setHeadTrackerEnabled(boolean enabled, AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(204, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHeadTrackerEnabled(AudioDeviceAttributes device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHeadTrackerAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback cb, boolean register) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    _data.writeBoolean(register);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(208, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean canBeSpatialized(AudioAttributes aa, AudioFormat af) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    _data.writeTypedObject(af, 0);
                    this.mRemote.transact(209, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerCallback(ISpatializerCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerCallback(ISpatializerCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(211, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(212, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(213, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(214, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(215, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(216, _data, _reply, 0);
                    _reply.readException();
                    List<AudioDeviceAttributes> _result = _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ada, 0);
                    this.mRemote.transact(217, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ada, 0);
                    this.mRemote.transact(218, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDesiredHeadTrackingMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(219, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDesiredHeadTrackingMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(220, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getSupportedHeadTrackingModes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(221, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getActualHeadTrackingMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(222, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerGlobalTransform(float[] transform) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloatArray(transform);
                    this.mRemote.transact(223, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recenterHeadTracker() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(224, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerParameter(int key, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeByteArray(value);
                    this.mRemote.transact(225, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void getSpatializerParameter(int key, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeByteArray(value);
                    this.mRemote.transact(226, _data, _reply, 0);
                    _reply.readException();
                    _reply.readByteArray(value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getSpatializerOutput() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(227, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerOutputCallback(ISpatializerOutputCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(228, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(229, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeFixed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(230, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public VolumeInfo getDefaultVolumeInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(231, _data, _reply, 0);
                    _reply.readException();
                    VolumeInfo _result = (VolumeInfo) _reply.readTypedObject(VolumeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isPstnCallAudioInterceptable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(232, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void muteAwaitConnection(int[] usagesToMute, AudioDeviceAttributes dev, long timeOutMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(usagesToMute);
                    _data.writeTypedObject(dev, 0);
                    _data.writeLong(timeOutMs);
                    this.mRemote.transact(233, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void cancelMuteAwaitConnection(AudioDeviceAttributes dev) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(dev, 0);
                    this.mRemote.transact(234, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(235, _data, _reply, 0);
                    _reply.readException();
                    AudioDeviceAttributes _result = (AudioDeviceAttributes) _reply.readTypedObject(AudioDeviceAttributes.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback cb, boolean register) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    _data.writeBoolean(register);
                    this.mRemote.transact(236, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setTestDeviceConnectionState(AudioDeviceAttributes device, boolean connected) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeBoolean(connected);
                    this.mRemote.transact(237, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerDeviceVolumeBehaviorDispatcher(boolean register, IDeviceVolumeBehaviorDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(register);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(238, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioFocusInfo> getFocusStack() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(239, _data, _reply, 0);
                    _reply.readException();
                    List<AudioFocusInfo> _result = _reply.createTypedArrayList(AudioFocusInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean sendFocusLoss(AudioFocusInfo focusLoser, IAudioPolicyCallback apcb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(focusLoser, 0);
                    _data.writeStrongInterface(apcb);
                    this.mRemote.transact(240, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addAssistantServicesUids(int[] assistantUID) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(assistantUID);
                    this.mRemote.transact(241, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeAssistantServicesUids(int[] assistantUID) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(assistantUID);
                    this.mRemote.transact(242, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setActiveAssistantServiceUids(int[] activeUids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(activeUids);
                    this.mRemote.transact(243, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getAssistantServicesUids() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(244, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getActiveAssistantServiceUids() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(245, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean register, IAudioDeviceVolumeDispatcher cb, String packageName, AudioDeviceAttributes device, List<VolumeInfo> volumes, boolean handlesvolumeAdjustment, int volumeBehavior) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(register);
                    _data.writeStrongInterface(cb);
                    _data.writeString(packageName);
                    _data.writeTypedObject(device, 0);
                    _data.writeTypedList(volumes, 0);
                    _data.writeBoolean(handlesvolumeAdjustment);
                    _data.writeInt(volumeBehavior);
                    this.mRemote.transact(246, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioHalVersionInfo getHalVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(247, _data, _reply, 0);
                    _reply.readException();
                    AudioHalVersionInfo _result = (AudioHalVersionInfo) _reply.readTypedObject(AudioHalVersionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredMixerAttributes(AudioAttributes aa, int portId, AudioMixerAttributes mixerAttributes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    _data.writeInt(portId);
                    _data.writeTypedObject(mixerAttributes, 0);
                    this.mRemote.transact(248, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearPreferredMixerAttributes(AudioAttributes aa, int portId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    _data.writeInt(portId);
                    this.mRemote.transact(249, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(250, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(251, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean supportsBluetoothVariableLatency() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(252, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothVariableLatencyEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(253, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothVariableLatencyEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(254, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(255, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher dispatcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(dispatcher);
                    this.mRemote.transact(256, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startLoudnessCodecUpdates(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(257, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopLoudnessCodecUpdates(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(258, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addLoudnessCodecInfo(int sessionId, int mediaCodecHash, LoudnessCodecInfo codecInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(mediaCodecHash);
                    _data.writeTypedObject(codecInfo, 0);
                    this.mRemote.transact(259, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeLoudnessCodecInfo(int sessionId, LoudnessCodecInfo codecInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(codecInfo, 0);
                    this.mRemote.transact(260, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public PersistableBundle getLoudnessParams(LoudnessCodecInfo codecInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(codecInfo, 0);
                    this.mRemote.transact(261, _data, _reply, 0);
                    _reply.readException();
                    PersistableBundle _result = (PersistableBundle) _reply.readTypedObject(PersistableBundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fmcForFocusLoss) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fmcForFocusLoss, 0);
                    this.mRemote.transact(262, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearFadeManagerConfigurationForFocusLoss() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(263, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(264, _data, _reply, 0);
                    _reply.readException();
                    FadeManagerConfiguration _result = (FadeManagerConfiguration) _reply.readTypedObject(FadeManagerConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldNotificationSoundPlay(AudioAttributes aa) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aa, 0);
                    this.mRemote.transact(265, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAudioServiceConfig(String keyValuePairs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(keyValuePairs);
                    this.mRemote.transact(266, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String getAudioServiceConfig(String keys) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(keys);
                    this.mRemote.transact(267, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldShowRingtoneVolume() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(268, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int secGetActiveStreamType(int suggestedStreamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(suggestedStreamType);
                    this.mRemote.transact(269, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getUidForDevice(int device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(device);
                    this.mRemote.transact(270, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAppDevice(int uid, int device, boolean shouldShowNotification) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(device);
                    _data.writeBoolean(shouldShowNotification);
                    this.mRemote.transact(271, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAppDevice(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(272, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAppVolume(int uid, int ratio, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(ratio);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(273, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAppVolume(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(274, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAppMute(int uid, boolean shouldMute, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(shouldMute);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(275, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAppMute(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(276, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMultiSoundOn(boolean on, boolean shouldShowNotification) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeBoolean(shouldShowNotification);
                    this.mRemote.transact(277, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMultiSoundOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(278, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeForDeviceWithAttribution(int streamType, int index, int flags, String callingPackage, String attributionTag, int device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeString(attributionTag);
                    _data.writeInt(device);
                    this.mRemote.transact(279, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamVolumeForDevice(int streamType, int device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(device);
                    this.mRemote.transact(280, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String getPinAppInfo(int device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(device);
                    this.mRemote.transact(281, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getPinDevice() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(282, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String[] getSelectedAppList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(283, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addPackage(int uid, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    this.mRemote.transact(284, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removePackageForName(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(285, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAlreadyInDB(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(286, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isInAllowedList(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(287, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setFineVolume(int streamType, int index, int flags, int device, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeInt(device);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(288, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getFineVolume(int streamType, int device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(device);
                    this.mRemote.transact(289, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setForceSpeakerOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(290, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isForceSpeakerOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(291, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setDeviceToForceByUser(int device, String address, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(device);
                    _data.writeString(address);
                    _data.writeBoolean(force);
                    this.mRemote.transact(292, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMuteInterval(int interval, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(interval);
                    _data.writeString(caller);
                    this.mRemote.transact(293, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMuteInterval() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(294, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRemainingMuteIntervalMs() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(295, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getPrevRingerMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(296, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSoundSettingEventBroadcastIntent(int type, PendingIntent broadcastIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(broadcastIntent, 0);
                    this.mRemote.transact(297, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setMediaVolumeSteps(int[] volumeSteps) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(volumeSteps);
                    this.mRemote.transact(298, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getMediaVolumeSteps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(299, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRadioOutputPath(int path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(path);
                    this.mRemote.transact(300, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRadioOutputPath() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(301, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void dismissVolumePanel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(302, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String getCurrentAudioFocusPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(303, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isUsingAudio(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(304, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setA2dpDeviceVolume(BluetoothDevice device, int streamType, int index, int flags, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(caller);
                    this.mRemote.transact(305, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getA2dpDeviceVolume(BluetoothDevice device, int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(streamType);
                    this.mRemote.transact(306, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float[] getFloatVolumeTable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(307, _data, _reply, 0);
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRemoteMic(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(308, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recordRingtoneChanger(String log) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(log);
                    this.mRemote.transact(309, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher pcdb, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(pcdb);
                    _data.writeString(packageName);
                    this.mRemote.transact(310, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBtOffloadEnable(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(311, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSafeMediaVolumeStateActive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(312, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<String> getExcludedRingtoneTitles(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(313, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void notifySafetyVolumeDialogVisible(IVolumeController controller, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(314, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getModeInternal() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(315, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicInputControlMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(316, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMicModeType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(317, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getEarProtectLimit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(318, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        private boolean onTransact$portEvent$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            PersistableBundle _arg2 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
            data.enforceNoDataAvail();
            portEvent(_arg0, _arg1, _arg2);
            return true;
        }

        private boolean onTransact$adjustStreamVolume$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            adjustStreamVolume(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$adjustStreamVolumeWithAttribution$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            adjustStreamVolumeWithAttribution(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setStreamVolume$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            setStreamVolume(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setStreamVolumeWithAttribution$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            setStreamVolumeWithAttribution(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setDeviceVolume$(Parcel data, Parcel reply) throws RemoteException {
            VolumeInfo _arg0 = (VolumeInfo) data.readTypedObject(VolumeInfo.CREATOR);
            AudioDeviceAttributes _arg1 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setDeviceVolume(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        protected void setDeviceVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setDeviceVolume, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$getDeviceVolume$(Parcel data, Parcel reply) throws RemoteException {
            VolumeInfo _arg0 = (VolumeInfo) data.readTypedObject(VolumeInfo.CREATOR);
            AudioDeviceAttributes _arg1 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            VolumeInfo _result = getDeviceVolume(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        protected void getDeviceVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getDeviceVolume, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$handleVolumeKey$(Parcel data, Parcel reply) throws RemoteException {
            KeyEvent _arg0 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
            boolean _arg1 = data.readBoolean();
            String _arg2 = data.readString();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            handleVolumeKey(_arg0, _arg1, _arg2, _arg3);
            return true;
        }

        private boolean onTransact$setMasterMute$(Parcel data, Parcel reply) throws RemoteException {
            boolean _arg0 = data.readBoolean();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            setMasterMute(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        protected void setMasterMute_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAudioVolumeGroups_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getAudioVolumeGroups, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setVolumeGroupVolumeIndex$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            setVolumeGroupVolumeIndex(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        protected void setVolumeGroupVolumeIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setVolumeGroupVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getVolumeGroupVolumeIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getVolumeGroupVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getVolumeGroupMaxVolumeIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getVolumeGroupMaxVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getVolumeGroupMinVolumeIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getVolumeGroupMinVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getLastAudibleVolumeForVolumeGroup_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$adjustVolumeGroupVolume$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            adjustVolumeGroupVolume(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        protected void getLastAudibleStreamVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void setSupportedSystemUsages_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getSupportedSystemUsages_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAudioProductStrategies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void isUltrasoundSupported_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_ULTRASOUND, getCallingPid(), getCallingUid());
        }

        protected void isHotwordStreamSupported_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CAPTURE_AUDIO_HOTWORD, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setMicrophoneMute$(Parcel data, Parcel reply) throws RemoteException {
            boolean _arg0 = data.readBoolean();
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            setMicrophoneMute(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setMode$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            IBinder _arg1 = data.readStrongBinder();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setMode(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        protected void setEncodedSurroundMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setA2dpSuspended_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        protected void setLeAudioSuspended_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$requestAudioFocus$(Parcel data, Parcel reply) throws RemoteException {
            AudioAttributes _arg0 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
            int _arg1 = data.readInt();
            IBinder _arg2 = data.readStrongBinder();
            IAudioFocusDispatcher _arg3 = IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
            String _arg4 = data.readString();
            String _arg5 = data.readString();
            String _arg6 = data.readString();
            int _arg7 = data.readInt();
            IAudioPolicyCallback _arg8 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            int _arg9 = data.readInt();
            data.enforceNoDataAvail();
            int _result = requestAudioFocus(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$abandonAudioFocus$(Parcel data, Parcel reply) throws RemoteException {
            IAudioFocusDispatcher _arg0 = IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            AudioAttributes _arg2 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            int _result = abandonAudioFocus(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        protected void setRingtonePlayer_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REMOTE_AUDIO_PLAYBACK, getCallingPid(), getCallingUid());
        }

        protected void getIndependentStreamTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getStreamTypeAlias_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isVolumeControlUsingVolumeGroups_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void registerStreamAliasingDispatcher_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setNotifAliasRingForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setWiredDeviceConnectionState$(Parcel data, Parcel reply) throws RemoteException {
            AudioDeviceAttributes _arg0 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setWiredDeviceConnectionState(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        protected void setWiredDeviceConnectionState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getOutputRs2UpperBound_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setOutputRs2UpperBound_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getCsd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setCsd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void forceUseFrameworkMel_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void forceComputeCsdOnAllDevices_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isCsdEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isCsdAsAFeatureAvailable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isCsdAsAFeatureEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setCsdAsAFeatureEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setBluetoothAudioDeviceCategory_legacy$(Parcel data, Parcel reply) throws RemoteException {
            String _arg0 = data.readString();
            boolean _arg1 = data.readBoolean();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setBluetoothAudioDeviceCategory_legacy(_arg0, _arg1, _arg2);
            return true;
        }

        protected void setBluetoothAudioDeviceCategory_legacy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothAudioDeviceCategory_legacy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setBluetoothAudioDeviceCategory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothAudioDeviceCategory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isBluetoothAudioDeviceCategoryFixed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$registerAudioPolicy$(Parcel data, Parcel reply) throws RemoteException {
            android.media.audiopolicy.AudioPolicyConfig _arg0 = (android.media.audiopolicy.AudioPolicyConfig) data.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
            IAudioPolicyCallback _arg1 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            boolean _arg2 = data.readBoolean();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            boolean _arg5 = data.readBoolean();
            IMediaProjection _arg6 = IMediaProjection.Stub.asInterface(data.readStrongBinder());
            AttributionSource _arg7 = (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
            data.enforceNoDataAvail();
            String _result = registerAudioPolicy(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            reply.writeString(_result);
            return true;
        }

        private boolean onTransact$updateMixingRulesForPolicy$(Parcel data, Parcel reply) throws RemoteException {
            android.media.audiopolicy.AudioMix[] _arg0 = (android.media.audiopolicy.AudioMix[]) data.createTypedArray(android.media.audiopolicy.AudioMix.CREATOR);
            AudioMixingRule[] _arg1 = (AudioMixingRule[]) data.createTypedArray(AudioMixingRule.CREATOR);
            IAudioPolicyCallback _arg2 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            int _result = updateMixingRulesForPolicy(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        protected void updateMixingRulesForPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$dispatchFocusChange$(Parcel data, Parcel reply) throws RemoteException {
            AudioFocusInfo _arg0 = (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
            int _arg1 = data.readInt();
            IAudioPolicyCallback _arg2 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            int _result = dispatchFocusChange(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$dispatchFocusChangeWithFade$(Parcel data, Parcel reply) throws RemoteException {
            AudioFocusInfo _arg0 = (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
            int _arg1 = data.readInt();
            IAudioPolicyCallback _arg2 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            List<AudioFocusInfo> _arg3 = data.createTypedArrayList(AudioFocusInfo.CREATOR);
            FadeManagerConfiguration _arg4 = (FadeManagerConfiguration) data.readTypedObject(FadeManagerConfiguration.CREATOR);
            data.enforceNoDataAvail();
            int _result = dispatchFocusChangeWithFade(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        protected void dispatchFocusChangeWithFade_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$handleBluetoothActiveDeviceChanged$(Parcel data, Parcel reply) throws RemoteException {
            BluetoothDevice _arg0 = (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
            BluetoothDevice _arg1 = (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
            BluetoothProfileConnectionInfo _arg2 = (BluetoothProfileConnectionInfo) data.readTypedObject(BluetoothProfileConnectionInfo.CREATOR);
            data.enforceNoDataAvail();
            handleBluetoothActiveDeviceChanged(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        protected void handleBluetoothActiveDeviceChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setFocusRequestResultFromExtPolicy$(Parcel data, Parcel reply) throws RemoteException {
            AudioFocusInfo _arg0 = (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
            int _arg1 = data.readInt();
            IAudioPolicyCallback _arg2 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            setFocusRequestResultFromExtPolicy(_arg0, _arg1, _arg2);
            return true;
        }

        private boolean onTransact$setUidDeviceAffinity$(Parcel data, Parcel reply) throws RemoteException {
            IAudioPolicyCallback _arg0 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            int _arg1 = data.readInt();
            int[] _arg2 = data.createIntArray();
            String[] _arg3 = data.createStringArray();
            data.enforceNoDataAvail();
            int _result = setUidDeviceAffinity(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setUserIdDeviceAffinity$(Parcel data, Parcel reply) throws RemoteException {
            IAudioPolicyCallback _arg0 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
            int _arg1 = data.readInt();
            int[] _arg2 = data.createIntArray();
            String[] _arg3 = data.createStringArray();
            data.enforceNoDataAvail();
            int _result = setUserIdDeviceAffinity(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        protected void setPreferredDevicesForStrategy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void removePreferredDevicesForStrategy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getPreferredDevicesForStrategy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setDeviceAsNonDefaultForStrategy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void removeDeviceAsNonDefaultForStrategy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getNonDefaultDevicesForStrategy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setDeviceVolumeBehavior$(Parcel data, Parcel reply) throws RemoteException {
            AudioDeviceAttributes _arg0 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setDeviceVolumeBehavior(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        protected void setDeviceVolumeBehavior_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setDeviceVolumeBehavior, getCallingPid(), getCallingUid());
        }

        protected void getDeviceVolumeBehavior_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getDeviceVolumeBehavior, getCallingPid(), getCallingUid());
        }

        protected void setMultiAudioFocusEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void clearPreferredDevicesForCapturePreset_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getPreferredDevicesForCapturePreset_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$adjustStreamVolumeForUid$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            int _arg5 = data.readInt();
            UserHandle _arg6 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
            int _arg7 = data.readInt();
            data.enforceNoDataAvail();
            adjustStreamVolumeForUid(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            return true;
        }

        private boolean onTransact$adjustSuggestedStreamVolumeForUid$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            int _arg5 = data.readInt();
            UserHandle _arg6 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
            int _arg7 = data.readInt();
            data.enforceNoDataAvail();
            adjustSuggestedStreamVolumeForUid(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            return true;
        }

        private boolean onTransact$setStreamVolumeForUid$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            int _arg5 = data.readInt();
            UserHandle _arg6 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
            int _arg7 = data.readInt();
            data.enforceNoDataAvail();
            setStreamVolumeForUid(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            return true;
        }

        private boolean onTransact$adjustSuggestedStreamVolume$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            adjustSuggestedStreamVolume(_arg0, _arg1, _arg2);
            return true;
        }

        private boolean onTransact$requestAudioFocusForTest$(Parcel data, Parcel reply) throws RemoteException {
            AudioAttributes _arg0 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
            int _arg1 = data.readInt();
            IBinder _arg2 = data.readStrongBinder();
            IAudioFocusDispatcher _arg3 = IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
            String _arg4 = data.readString();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            int _arg7 = data.readInt();
            int _arg8 = data.readInt();
            data.enforceNoDataAvail();
            int _result = requestAudioFocusForTest(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$abandonAudioFocusForTest$(Parcel data, Parcel reply) throws RemoteException {
            IAudioFocusDispatcher _arg0 = IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            AudioAttributes _arg2 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            int _result = abandonAudioFocusForTest(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        protected void getFocusDuckedUidsForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void getFocusFadeOutDurationForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void getFocusUnmuteDelayAfterFadeOutForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void enterAudioFocusFreezeForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void exitAudioFocusFreezeForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isSpatializerAvailableForDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void hasHeadTracker_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setHeadTrackerEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void isHeadTrackerEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setSpatializerEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void registerSpatializerHeadTrackingCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void unregisterSpatializerHeadTrackingCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void registerHeadToSoundstagePoseCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void unregisterHeadToSoundstagePoseCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSpatializerCompatibleAudioDevices_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void addSpatializerCompatibleAudioDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void removeSpatializerCompatibleAudioDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setDesiredHeadTrackingMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getDesiredHeadTrackingMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSupportedHeadTrackingModes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getActualHeadTrackingMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setSpatializerGlobalTransform_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void recenterHeadTracker_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setSpatializerParameter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSpatializerParameter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSpatializerOutput_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void registerSpatializerOutputCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void unregisterSpatializerOutputCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void isPstnCallAudioInterceptable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CALL_AUDIO_INTERCEPTION, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$muteAwaitConnection$(Parcel data, Parcel reply) throws RemoteException {
            int[] _arg0 = data.createIntArray();
            AudioDeviceAttributes _arg1 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
            long _arg2 = data.readLong();
            data.enforceNoDataAvail();
            muteAwaitConnection(_arg0, _arg1, _arg2);
            return true;
        }

        protected void getMutingExpectedDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void registerMuteAwaitConnectionDispatcher_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getFocusStack_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void addAssistantServicesUids_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void removeAssistantServicesUids_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setActiveAssistantServiceUids_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAssistantServicesUids_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getActiveAssistantServiceUids_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$registerDeviceVolumeDispatcherForAbsoluteVolume$(Parcel data, Parcel reply) throws RemoteException {
            boolean _arg0 = data.readBoolean();
            IAudioDeviceVolumeDispatcher _arg1 = IAudioDeviceVolumeDispatcher.Stub.asInterface(data.readStrongBinder());
            String _arg2 = data.readString();
            AudioDeviceAttributes _arg3 = (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
            List<VolumeInfo> _arg4 = data.createTypedArrayList(VolumeInfo.CREATOR);
            boolean _arg5 = data.readBoolean();
            int _arg6 = data.readInt();
            data.enforceNoDataAvail();
            registerDeviceVolumeDispatcherForAbsoluteVolume(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setPreferredMixerAttributes$(Parcel data, Parcel reply) throws RemoteException {
            AudioAttributes _arg0 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
            int _arg1 = data.readInt();
            AudioMixerAttributes _arg2 = (AudioMixerAttributes) data.readTypedObject(AudioMixerAttributes.CREATOR);
            data.enforceNoDataAvail();
            int _result = setPreferredMixerAttributes(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        protected void supportsBluetoothVariableLatency_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setBluetoothVariableLatencyEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void isBluetoothVariableLatencyEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$addLoudnessCodecInfo$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            LoudnessCodecInfo _arg2 = (LoudnessCodecInfo) data.readTypedObject(LoudnessCodecInfo.CREATOR);
            data.enforceNoDataAvail();
            addLoudnessCodecInfo(_arg0, _arg1, _arg2);
            return true;
        }

        protected void setFadeManagerConfigurationForFocusLoss_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void clearFadeManagerConfigurationForFocusLoss_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getFadeManagerConfigurationForFocusLoss_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void shouldNotificationSoundPlay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setAppDevice$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            setAppDevice(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setAppVolume$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setAppVolume(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setAppMute$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            boolean _arg1 = data.readBoolean();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setAppMute(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setStreamVolumeForDeviceWithAttribution$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            String _arg4 = data.readString();
            int _arg5 = data.readInt();
            data.enforceNoDataAvail();
            setStreamVolumeForDeviceWithAttribution(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setFineVolume$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            setFineVolume(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setDeviceToForceByUser$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = setDeviceToForceByUser(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setA2dpDeviceVolume$(Parcel data, Parcel reply) throws RemoteException {
            BluetoothDevice _arg0 = (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            setA2dpDeviceVolume(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 317;
        }
    }
}
