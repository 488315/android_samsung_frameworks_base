package android.media;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.IMediaProjection;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.PermissionEnforcer;
import android.p009os.PersistableBundle;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.view.KeyEvent;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IAudioService extends IInterface {
  int abandonAudioFocus(
      IAudioFocusDispatcher iAudioFocusDispatcher,
      String str,
      AudioAttributes audioAttributes,
      String str2)
      throws RemoteException;

  int abandonAudioFocusForTest(
      IAudioFocusDispatcher iAudioFocusDispatcher,
      String str,
      AudioAttributes audioAttributes,
      String str2)
      throws RemoteException;

  void addAssistantServicesUids(int[] iArr) throws RemoteException;

  int addMixForPolicy(
      android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig,
      IAudioPolicyCallback iAudioPolicyCallback)
      throws RemoteException;

  void addOnDevicesForAttributesChangedListener(
      AudioAttributes audioAttributes, IDevicesForAttributesCallback iDevicesForAttributesCallback)
      throws RemoteException;

  void addPackage(int i, String str) throws RemoteException;

  void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  void adjustStreamVolume(int i, int i2, int i3, String str) throws RemoteException;

  void adjustStreamVolumeForUid(
      int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6)
      throws RemoteException;

  void adjustStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2)
      throws RemoteException;

  void adjustSuggestedStreamVolumeForUid(
      int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6)
      throws RemoteException;

  void adjustVolumeGroupVolume(int i, int i2, int i3, String str) throws RemoteException;

  boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException;

  boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat)
      throws RemoteException;

  void cancelMuteAwaitConnection(AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  int clearPreferredDevicesForCapturePreset(int i) throws RemoteException;

  int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i) throws RemoteException;

  void disableSafeMediaVolume(String str) throws RemoteException;

  void dismissVolumePanel() throws RemoteException;

  int dispatchFocusChange(
      AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback)
      throws RemoteException;

  void forceComputeCsdOnAllDevices(boolean z) throws RemoteException;

  void forceRemoteSubmixFullVolume(boolean z, IBinder iBinder) throws RemoteException;

  void forceUseFrameworkMel(boolean z) throws RemoteException;

  void forceVolumeControlStream(int i, IBinder iBinder) throws RemoteException;

  int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) throws RemoteException;

  int[] getActiveAssistantServiceUids() throws RemoteException;

  List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException;

  List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException;

  int getActualHeadTrackingMode() throws RemoteException;

  long getAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  int getAllowedCapturePolicy() throws RemoteException;

  int getAppDevice(int i) throws RemoteException;

  int getAppVolume(int i) throws RemoteException;

  int[] getAssistantServicesUids() throws RemoteException;

  List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies()
      throws RemoteException;

  String getAudioServiceConfig(String str) throws RemoteException;

  List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException;

  int[] getAvailableCommunicationDeviceIds() throws RemoteException;

  int getCommunicationDevice() throws RemoteException;

  float getCsd() throws RemoteException;

  int getCurrentAudioFocus() throws RemoteException;

  String getCurrentAudioFocusPackageName() throws RemoteException;

  VolumeInfo getDefaultVolumeInfo() throws RemoteException;

  int getDesiredHeadTrackingMode() throws RemoteException;

  int getDeviceMaskForStream(int i) throws RemoteException;

  VolumeInfo getDeviceVolume(
      VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str)
      throws RemoteException;

  int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

  List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes audioAttributes)
      throws RemoteException;

  List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(AudioAttributes audioAttributes)
      throws RemoteException;

  int getEarProtectLimit() throws RemoteException;

  int getEncodedSurroundMode(int i) throws RemoteException;

  List<String> getExcludedRingtoneTitles(int i) throws RemoteException;

  long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) throws RemoteException;

  int getFineVolume(int i, int i2) throws RemoteException;

  float[] getFloatVolumeTable() throws RemoteException;

  int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) throws RemoteException;

  List<AudioFocusInfo> getFocusStack() throws RemoteException;

  AudioHalVersionInfo getHalVersion() throws RemoteException;

  List getIndependentStreamTypes() throws RemoteException;

  int getLastAudibleStreamVolume(int i) throws RemoteException;

  int getLastAudibleVolumeForVolumeGroup(int i) throws RemoteException;

  long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

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

  void handleBluetoothActiveDeviceChanged(
      BluetoothDevice bluetoothDevice,
      BluetoothDevice bluetoothDevice2,
      BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo)
      throws RemoteException;

  void handleVolumeKey(KeyEvent keyEvent, boolean z, String str, String str2)
      throws RemoteException;

  boolean hasHapticChannels(Uri uri) throws RemoteException;

  boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

  boolean hasRegisteredDynamicPolicy() throws RemoteException;

  boolean isAlreadyInDB(String str) throws RemoteException;

  boolean isAppMute(int i) throws RemoteException;

  boolean isAudioServerRunning() throws RemoteException;

  boolean isBluetoothA2dpOn() throws RemoteException;

  boolean isBluetoothScoOn() throws RemoteException;

  boolean isBluetoothVariableLatencyEnabled() throws RemoteException;

  boolean isCallScreeningModeSupported() throws RemoteException;

  boolean isCameraSoundForced() throws RemoteException;

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

  boolean isSpatializerAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  boolean isSpatializerEnabled() throws RemoteException;

  boolean isSpeakerphoneOn() throws RemoteException;

  boolean isStreamAffectedByMute(int i) throws RemoteException;

  boolean isStreamAffectedByRingerMode(int i) throws RemoteException;

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

  void muteAwaitConnection(int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j)
      throws RemoteException;

  void nativeEvent(String str, String str2, int i) throws RemoteException;

  void notifySafetyVolumeDialogVisible(IVolumeController iVolumeController, boolean z)
      throws RemoteException;

  void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z)
      throws RemoteException;

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

  String registerAudioPolicy(
      android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig,
      IAudioPolicyCallback iAudioPolicyCallback,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      IMediaProjection iMediaProjection)
      throws RemoteException;

  void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher)
      throws RemoteException;

  void registerCapturePresetDevicesRoleDispatcher(
      ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher)
      throws RemoteException;

  void registerCommunicationDeviceDispatcher(
      ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException;

  void registerDeviceVolumeBehaviorDispatcher(
      boolean z, IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher)
      throws RemoteException;

  void registerDeviceVolumeDispatcherForAbsoluteVolume(
      boolean z,
      IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher,
      String str,
      AudioDeviceAttributes audioDeviceAttributes,
      List<VolumeInfo> list,
      boolean z2,
      int i)
      throws RemoteException;

  void registerHeadToSoundstagePoseCallback(
      ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback)
      throws RemoteException;

  void registerModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException;

  void registerMuteAwaitConnectionDispatcher(
      IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws RemoteException;

  void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher)
      throws RemoteException;

  void registerPlaybackCallbackWithPackage(
      IPlaybackConfigDispatcher iPlaybackConfigDispatcher, String str) throws RemoteException;

  void registerPreferredMixerAttributesDispatcher(
      IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher)
      throws RemoteException;

  void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher)
      throws RemoteException;

  void registerSpatializerCallback(ISpatializerCallback iSpatializerCallback)
      throws RemoteException;

  void registerSpatializerHeadTrackerAvailableCallback(
      ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z)
      throws RemoteException;

  void registerSpatializerHeadTrackingCallback(
      ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback)
      throws RemoteException;

  void registerSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback)
      throws RemoteException;

  void registerStrategyNonDefaultDevicesDispatcher(
      IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher)
      throws RemoteException;

  void registerStrategyPreferredDevicesDispatcher(
      IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher)
      throws RemoteException;

  void registerStreamAliasingDispatcher(
      IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws RemoteException;

  void releasePlayer(int i) throws RemoteException;

  void releaseRecorder(int i) throws RemoteException;

  void reloadAudioSettings() throws RemoteException;

  void removeAssistantServicesUids(int[] iArr) throws RemoteException;

  int removeDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  int removeMixForPolicy(
      android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig,
      IAudioPolicyCallback iAudioPolicyCallback)
      throws RemoteException;

  void removeOnDevicesForAttributesChangedListener(
      IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException;

  void removePackageForName(String str) throws RemoteException;

  int removePreferredDevicesForStrategy(int i) throws RemoteException;

  void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i)
      throws RemoteException;

  int removeUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i)
      throws RemoteException;

  int requestAudioFocus(
      AudioAttributes audioAttributes,
      int i,
      IBinder iBinder,
      IAudioFocusDispatcher iAudioFocusDispatcher,
      String str,
      String str2,
      String str3,
      int i2,
      IAudioPolicyCallback iAudioPolicyCallback,
      int i3)
      throws RemoteException;

  int requestAudioFocusForTest(
      AudioAttributes audioAttributes,
      int i,
      IBinder iBinder,
      IAudioFocusDispatcher iAudioFocusDispatcher,
      String str,
      String str2,
      int i2,
      int i3,
      int i4)
      throws RemoteException;

  int secGetActiveStreamType(int i) throws RemoteException;

  boolean sendFocusLoss(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback)
      throws RemoteException;

  void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3, String str)
      throws RemoteException;

  void setA2dpSuspended(boolean z) throws RemoteException;

  void setActiveAssistantServiceUids(int[] iArr) throws RemoteException;

  boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes, long j)
      throws RemoteException;

  int setAllowedCapturePolicy(int i) throws RemoteException;

  void setAppDevice(int i, int i2, boolean z) throws RemoteException;

  void setAppMute(int i, boolean z, String str) throws RemoteException;

  void setAppVolume(int i, int i2, String str) throws RemoteException;

  void setAudioServiceConfig(String str) throws RemoteException;

  void setBluetoothA2dpOn(boolean z) throws RemoteException;

  void setBluetoothScoOn(boolean z) throws RemoteException;

  void setBluetoothVariableLatencyEnabled(boolean z) throws RemoteException;

  void setBtOffloadEnable(int i) throws RemoteException;

  boolean setCommunicationDevice(IBinder iBinder, int i) throws RemoteException;

  void setCsd(float f) throws RemoteException;

  void setDesiredHeadTrackingMode(int i) throws RemoteException;

  int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

  int setDeviceToForceByUser(int i, String str, boolean z) throws RemoteException;

  void setDeviceVolume(
      VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str)
      throws RemoteException;

  void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str)
      throws RemoteException;

  boolean setEncodedSurroundMode(int i) throws RemoteException;

  void setFineVolume(int i, int i2, int i3, int i4, String str) throws RemoteException;

  int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback)
      throws RemoteException;

  void setFocusRequestResultFromExtPolicy(
      AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback)
      throws RemoteException;

  void setForceSpeakerOn(boolean z) throws RemoteException;

  int setHdmiSystemAudioSupported(boolean z) throws RemoteException;

  void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes)
      throws RemoteException;

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

  int setPreferredDevicesForCapturePreset(int i, List<AudioDeviceAttributes> list)
      throws RemoteException;

  int setPreferredDevicesForStrategy(int i, List<AudioDeviceAttributes> list)
      throws RemoteException;

  int setPreferredMixerAttributes(
      AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes)
      throws RemoteException;

  void setRadioOutputPath(int i) throws RemoteException;

  void setRemoteMic(boolean z) throws RemoteException;

  void setRingerModeExternal(int i, String str) throws RemoteException;

  void setRingerModeInternal(int i, String str) throws RemoteException;

  void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException;

  void setRttEnabled(boolean z) throws RemoteException;

  void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent)
      throws RemoteException;

  void setSpatializerEnabled(boolean z) throws RemoteException;

  void setSpatializerGlobalTransform(float[] fArr) throws RemoteException;

  void setSpatializerParameter(int i, byte[] bArr) throws RemoteException;

  void setSpeakerphoneOn(IBinder iBinder, boolean z) throws RemoteException;

  void setStreamVolume(int i, int i2, int i3, String str) throws RemoteException;

  void setStreamVolumeForDeviceWithAttribution(
      int i, int i2, int i3, String str, String str2, int i4) throws RemoteException;

  void setStreamVolumeForUid(
      int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6)
      throws RemoteException;

  void setStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2)
      throws RemoteException;

  void setSupportedSystemUsages(int[] iArr) throws RemoteException;

  boolean setSurroundFormatEnabled(int i, boolean z) throws RemoteException;

  void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z)
      throws RemoteException;

  int setUidDeviceAffinity(
      IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr)
      throws RemoteException;

  int setUserIdDeviceAffinity(
      IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr)
      throws RemoteException;

  void setVibrateSetting(int i, int i2) throws RemoteException;

  void setVolumeController(IVolumeController iVolumeController) throws RemoteException;

  void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2)
      throws RemoteException;

  void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException;

  void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str)
      throws RemoteException;

  boolean shouldShowRingtoneVolume() throws RemoteException;

  boolean shouldVibrate(int i) throws RemoteException;

  void startBluetoothSco(IBinder iBinder, int i) throws RemoteException;

  void startBluetoothScoVirtualCall(IBinder iBinder) throws RemoteException;

  AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver)
      throws RemoteException;

  void stopBluetoothSco(IBinder iBinder) throws RemoteException;

  boolean supportsBluetoothVariableLatency() throws RemoteException;

  int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException;

  int trackRecorder(IBinder iBinder) throws RemoteException;

  void unloadSoundEffects() throws RemoteException;

  void unregisterAudioFocusClient(String str) throws RemoteException;

  void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

  void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

  void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher)
      throws RemoteException;

  void unregisterCapturePresetDevicesRoleDispatcher(
      ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher)
      throws RemoteException;

  void unregisterCommunicationDeviceDispatcher(
      ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException;

  void unregisterHeadToSoundstagePoseCallback(
      ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback)
      throws RemoteException;

  void unregisterModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException;

  void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher)
      throws RemoteException;

  void unregisterPreferredMixerAttributesDispatcher(
      IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher)
      throws RemoteException;

  void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher)
      throws RemoteException;

  void unregisterSpatializerCallback(ISpatializerCallback iSpatializerCallback)
      throws RemoteException;

  void unregisterSpatializerHeadTrackingCallback(
      ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback)
      throws RemoteException;

  void unregisterSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback)
      throws RemoteException;

  void unregisterStrategyNonDefaultDevicesDispatcher(
      IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher)
      throws RemoteException;

  void unregisterStrategyPreferredDevicesDispatcher(
      IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher)
      throws RemoteException;

  public static class Default implements IAudioService {
    @Override // android.media.IAudioService
    public int trackPlayer(PlayerBase.PlayerIdCard pic) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void playerAttributes(int piid, AudioAttributes attr) throws RemoteException {}

    @Override // android.media.IAudioService
    public void playerEvent(int piid, int event, int eventId) throws RemoteException {}

    @Override // android.media.IAudioService
    public void releasePlayer(int piid) throws RemoteException {}

    @Override // android.media.IAudioService
    public int trackRecorder(IBinder recorder) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void recorderEvent(int riid, int event) throws RemoteException {}

    @Override // android.media.IAudioService
    public void releaseRecorder(int riid) throws RemoteException {}

    @Override // android.media.IAudioService
    public void playerSessionId(int piid, int sessionId) throws RemoteException {}

    @Override // android.media.IAudioService
    public void portEvent(int portId, int event, PersistableBundle extras) throws RemoteException {}

    @Override // android.media.IAudioService
    public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void adjustStreamVolumeWithAttribution(
        int streamType, int direction, int flags, String callingPackage, String attributionTag)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setStreamVolume(int streamType, int index, int flags, String callingPackage)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setStreamVolumeWithAttribution(
        int streamType, int index, int flags, String callingPackage, String attributionTag)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setDeviceVolume(VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public VolumeInfo getDeviceVolume(
        VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void handleVolumeKey(
        KeyEvent event, boolean isOnTv, String callingPackage, String caller)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isStreamMute(int streamType) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isMasterMute() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setMasterMute(
        boolean mute, int flags, String callingPackage, int userId, String attributionTag)
        throws RemoteException {}

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
    public List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups()
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void setVolumeGroupVolumeIndex(
        int groupId, int index, int flags, String callingPackage, String attributionTag)
        throws RemoteException {}

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
    public void adjustVolumeGroupVolume(
        int groupId, int direction, int flags, String callingPackage) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setSupportedSystemUsages(int[] systemUsages) throws RemoteException {}

    @Override // android.media.IAudioService
    public int[] getSupportedSystemUsages() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies()
        throws RemoteException {
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
    public void setMicrophoneMute(
        boolean on, String callingPackage, int userId, String attributionTag)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setMicrophoneMuteFromSwitch(boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {}

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
    public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getVibrateSetting(int vibrateType) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public boolean shouldVibrate(int vibrateType) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getMode() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void playSoundEffect(int effectType, int userId) throws RemoteException {}

    @Override // android.media.IAudioService
    public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean loadSoundEffects() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void unloadSoundEffects() throws RemoteException {}

    @Override // android.media.IAudioService
    public void reloadAudioSettings() throws RemoteException {}

    @Override // android.media.IAudioService
    public Map getSurroundFormats() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public List getReportedSurroundFormats() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public boolean setSurroundFormatEnabled(int audioFormat, boolean enabled)
        throws RemoteException {
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
    public void setSpeakerphoneOn(IBinder cb, boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isSpeakerphoneOn() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setBluetoothScoOn(boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setA2dpSuspended(boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setLeAudioSuspended(boolean enable) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isBluetoothScoOn() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setBluetoothA2dpOn(boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isBluetoothA2dpOn() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public int requestAudioFocus(
        AudioAttributes aa,
        int durationHint,
        IBinder cb,
        IAudioFocusDispatcher fd,
        String clientId,
        String callingPackageName,
        String attributionTag,
        int flags,
        IAudioPolicyCallback pcb,
        int sdk)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int abandonAudioFocus(
        IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void unregisterAudioFocusClient(String clientId) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getCurrentAudioFocus() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {}

    @Override // android.media.IAudioService
    public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void stopBluetoothSco(IBinder cb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {}

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
    public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher isad, boolean register)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setNotifAliasRingForTest(boolean alias) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setWiredDeviceConnectionState(AudioDeviceAttributes aa, int state, String caller)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public boolean isCameraSoundForced() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setVolumeController(IVolumeController controller) throws RemoteException {}

    @Override // android.media.IAudioService
    public IVolumeController getVolumeController() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void disableSafeMediaVolume(String callingPackage) throws RemoteException {}

    @Override // android.media.IAudioService
    public void lowerVolumeToRs1(String callingPackage) throws RemoteException {}

    @Override // android.media.IAudioService
    public float getOutputRs2UpperBound() throws RemoteException {
      return 0.0f;
    }

    @Override // android.media.IAudioService
    public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {}

    @Override // android.media.IAudioService
    public float getCsd() throws RemoteException {
      return 0.0f;
    }

    @Override // android.media.IAudioService
    public void setCsd(float csd) throws RemoteException {}

    @Override // android.media.IAudioService
    public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {}

    @Override // android.media.IAudioService
    public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isCsdEnabled() throws RemoteException {
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
    public String registerAudioPolicy(
        android.media.audiopolicy.AudioPolicyConfig policyConfig,
        IAudioPolicyCallback pcb,
        boolean hasFocusListener,
        boolean isFocusPolicy,
        boolean isTestFocusPolicy,
        boolean isVolumeController,
        IMediaProjection projection)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {}

    @Override // android.media.IAudioService
    public int addMixForPolicy(
        android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int removeMixForPolicy(
        android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setVolumePolicy(VolumePolicy policy) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean hasRegisteredDynamicPolicy() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public List<AudioRecordingConfiguration> getActiveRecordingConfigurations()
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {}

    @Override // android.media.IAudioService
    public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations()
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {}

    @Override // android.media.IAudioService
    public void handleBluetoothActiveDeviceChanged(
        BluetoothDevice newDevice,
        BluetoothDevice previousDevice,
        BluetoothProfileConnectionInfo info)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setFocusRequestResultFromExtPolicy(
        AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isAudioServerRunning() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public int setUidDeviceAffinity(
        IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int setUserIdDeviceAffinity(
        IAudioPolicyCallback pcb, int userId, int[] deviceTypes, String[] deviceAddresses)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int removeUserIdDeviceAffinity(IAudioPolicyCallback pcb, int userId)
        throws RemoteException {
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
    public int setPreferredDevicesForStrategy(int strategy, List<AudioDeviceAttributes> devices)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int removePreferredDevicesForStrategy(int strategy) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int strategy)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public int setDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int removeDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int strategy)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes attributes)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(
        AudioAttributes attributes) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void addOnDevicesForAttributesChangedListener(
        AudioAttributes attributes, IDevicesForAttributesCallback callback)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback callback)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public int setAllowedCapturePolicy(int capturePolicy) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int getAllowedCapturePolicy() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void registerStrategyPreferredDevicesDispatcher(
        IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterStrategyPreferredDevicesDispatcher(
        IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void registerStrategyNonDefaultDevicesDispatcher(
        IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterStrategyNonDefaultDevicesDispatcher(
        IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setRttEnabled(boolean rttEnabled) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setDeviceVolumeBehavior(
        AudioDeviceAttributes device, int deviceVolumeBehavior, String pkgName)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public int getDeviceVolumeBehavior(AudioDeviceAttributes device) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setMultiAudioFocusEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioService
    public int setPreferredDevicesForCapturePreset(
        int capturePreset, List<AudioDeviceAttributes> devices) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int clearPreferredDevicesForCapturePreset(int capturePreset) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int capturePreset)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void registerCapturePresetDevicesRoleDispatcher(
        ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterCapturePresetDevicesRoleDispatcher(
        ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void adjustStreamVolumeForUid(
        int streamType,
        int direction,
        int flags,
        String packageName,
        int uid,
        int pid,
        UserHandle userHandle,
        int targetSdkVersion)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void adjustSuggestedStreamVolumeForUid(
        int streamType,
        int direction,
        int flags,
        String packageName,
        int uid,
        int pid,
        UserHandle userHandle,
        int targetSdkVersion)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setStreamVolumeForUid(
        int streamType,
        int direction,
        int flags,
        String packageName,
        int uid,
        int pid,
        UserHandle userHandle,
        int targetSdkVersion)
        throws RemoteException {}

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
    public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setNavigationRepeatSoundEffectsEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isHomeSoundEffectEnabled() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setHomeSoundEffectEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes device, long delayMillis)
        throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes device)
        throws RemoteException {
      return 0L;
    }

    @Override // android.media.IAudioService
    public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes device)
        throws RemoteException {
      return 0L;
    }

    @Override // android.media.IAudioService
    public int requestAudioFocusForTest(
        AudioAttributes aa,
        int durationHint,
        IBinder cb,
        IAudioFocusDispatcher fd,
        String clientId,
        String callingPackageName,
        int flags,
        int uid,
        int sdk)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int abandonAudioFocusForTest(
        IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public long getFadeOutDurationOnFocusLossMillis(AudioAttributes aa) throws RemoteException {
      return 0L;
    }

    @Override // android.media.IAudioService
    public void registerModeDispatcher(IAudioModeDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterModeDispatcher(IAudioModeDispatcher dispatcher) throws RemoteException {}

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
    public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes device)
        throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public boolean hasHeadTracker(AudioDeviceAttributes device) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setHeadTrackerEnabled(boolean enabled, AudioDeviceAttributes device)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isHeadTrackerEnabled(AudioDeviceAttributes device) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public boolean isHeadTrackerAvailable() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void registerSpatializerHeadTrackerAvailableCallback(
        ISpatializerHeadTrackerAvailableCallback cb, boolean register) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setSpatializerEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean canBeSpatialized(AudioAttributes aa, AudioFormat af) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void registerSpatializerCallback(ISpatializerCallback cb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterSpatializerCallback(ISpatializerCallback cb) throws RemoteException {}

    @Override // android.media.IAudioService
    public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices()
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void setDesiredHeadTrackingMode(int mode) throws RemoteException {}

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
    public void setSpatializerGlobalTransform(float[] transform) throws RemoteException {}

    @Override // android.media.IAudioService
    public void recenterHeadTracker() throws RemoteException {}

    @Override // android.media.IAudioService
    public void setSpatializerParameter(int key, byte[] value) throws RemoteException {}

    @Override // android.media.IAudioService
    public void getSpatializerParameter(int key, byte[] value) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getSpatializerOutput() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void registerSpatializerOutputCallback(ISpatializerOutputCallback cb)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback cb)
        throws RemoteException {}

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
    public void muteAwaitConnection(int[] usagesToMute, AudioDeviceAttributes dev, long timeOutMs)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void cancelMuteAwaitConnection(AudioDeviceAttributes dev) throws RemoteException {}

    @Override // android.media.IAudioService
    public AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void registerMuteAwaitConnectionDispatcher(
        IMuteAwaitConnectionCallback cb, boolean register) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setTestDeviceConnectionState(AudioDeviceAttributes device, boolean connected)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void registerDeviceVolumeBehaviorDispatcher(
        boolean register, IDeviceVolumeBehaviorDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public List<AudioFocusInfo> getFocusStack() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public boolean sendFocusLoss(AudioFocusInfo focusLoser, IAudioPolicyCallback apcb)
        throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void addAssistantServicesUids(int[] assistantUID) throws RemoteException {}

    @Override // android.media.IAudioService
    public void removeAssistantServicesUids(int[] assistantUID) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setActiveAssistantServiceUids(int[] activeUids) throws RemoteException {}

    @Override // android.media.IAudioService
    public int[] getAssistantServicesUids() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public int[] getActiveAssistantServiceUids() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void registerDeviceVolumeDispatcherForAbsoluteVolume(
        boolean register,
        IAudioDeviceVolumeDispatcher cb,
        String packageName,
        AudioDeviceAttributes device,
        List<VolumeInfo> volumes,
        boolean handlesvolumeAdjustment,
        int volumeBehavior)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public AudioHalVersionInfo getHalVersion() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public int setPreferredMixerAttributes(
        AudioAttributes aa, int portId, AudioMixerAttributes mixerAttributes)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int clearPreferredMixerAttributes(AudioAttributes aa, int portId)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void registerPreferredMixerAttributesDispatcher(
        IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public void unregisterPreferredMixerAttributesDispatcher(
        IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean supportsBluetoothVariableLatency() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setBluetoothVariableLatencyEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isBluetoothVariableLatencyEnabled() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setAudioServiceConfig(String keyValuePairs) throws RemoteException {}

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
    public void setAppDevice(int uid, int device, boolean shouldShowNotification)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public int getAppDevice(int uid) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setAppVolume(int uid, int ratio, String callingPackage) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getAppVolume(int uid) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setAppMute(int uid, boolean shouldMute, String callingPackage)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isAppMute(int uid) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setMultiSoundOn(boolean on, boolean shouldShowNotification)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isMultiSoundOn() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setStreamVolumeForDeviceWithAttribution(
        int streamType,
        int index,
        int flags,
        String callingPackage,
        String attributionTag,
        int device)
        throws RemoteException {}

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
    public void addPackage(int uid, String packageName) throws RemoteException {}

    @Override // android.media.IAudioService
    public void removePackageForName(String packageName) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isAlreadyInDB(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public boolean isInAllowedList(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setFineVolume(
        int streamType, int index, int flags, int device, String callingPackage)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public int getFineVolume(int streamType, int device) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setForceSpeakerOn(boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isForceSpeakerOn() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public int setDeviceToForceByUser(int device, String address, boolean force)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setMuteInterval(int interval, String caller) throws RemoteException {}

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
    public void setSoundSettingEventBroadcastIntent(int type, PendingIntent broadcastIntent)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean setMediaVolumeSteps(int[] volumeSteps) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public int[] getMediaVolumeSteps() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void setRadioOutputPath(int path) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getRadioOutputPath() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void dismissVolumePanel() throws RemoteException {}

    @Override // android.media.IAudioService
    public String getCurrentAudioFocusPackageName() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public boolean isUsingAudio(int uid) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public void setA2dpDeviceVolume(
        BluetoothDevice device, int streamType, int index, int flags, String caller)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public int getA2dpDeviceVolume(BluetoothDevice device, int streamType) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public float[] getFloatVolumeTable() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void setRemoteMic(boolean on) throws RemoteException {}

    @Override // android.media.IAudioService
    public void recordRingtoneChanger(String log) throws RemoteException {}

    @Override // android.media.IAudioService
    public void registerPlaybackCallbackWithPackage(
        IPlaybackConfigDispatcher pcdb, String packageName) throws RemoteException {}

    @Override // android.media.IAudioService
    public void setBtOffloadEnable(int state) throws RemoteException {}

    @Override // android.media.IAudioService
    public boolean isSafeMediaVolumeStateActive() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioService
    public List<String> getExcludedRingtoneTitles(int type) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioService
    public void notifySafetyVolumeDialogVisible(IVolumeController controller, boolean visible)
        throws RemoteException {}

    @Override // android.media.IAudioService
    public void nativeEvent(String action, String key, int value) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getModeInternal() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public void setMicInputControlMode(int mode) throws RemoteException {}

    @Override // android.media.IAudioService
    public int getMicModeType() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioService
    public int getEarProtectLimit() throws RemoteException {
      return 0;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAudioService {
    public static final String DESCRIPTOR = "android.media.IAudioService";
    static final int TRANSACTION_abandonAudioFocus = 71;
    static final int TRANSACTION_abandonAudioFocusForTest = 176;
    static final int TRANSACTION_addAssistantServicesUids = 222;
    static final int TRANSACTION_addMixForPolicy = 108;
    static final int TRANSACTION_addOnDevicesForAttributesChangedListener = 141;
    static final int TRANSACTION_addPackage = 254;
    static final int TRANSACTION_addSpatializerCompatibleAudioDevice = 198;
    static final int TRANSACTION_adjustStreamVolume = 10;
    static final int TRANSACTION_adjustStreamVolumeForUid = 158;
    static final int TRANSACTION_adjustStreamVolumeWithAttribution = 11;
    static final int TRANSACTION_adjustSuggestedStreamVolumeForUid = 159;
    static final int TRANSACTION_adjustVolumeGroupVolume = 31;
    static final int TRANSACTION_areNavigationRepeatSoundEffectsEnabled = 168;
    static final int TRANSACTION_canBeSpatialized = 190;
    static final int TRANSACTION_cancelMuteAwaitConnection = 215;
    static final int TRANSACTION_clearPreferredDevicesForCapturePreset = 154;
    static final int TRANSACTION_clearPreferredMixerAttributes = 230;
    static final int TRANSACTION_disableSafeMediaVolume = 94;
    static final int TRANSACTION_dismissVolumePanel = 272;
    static final int TRANSACTION_dispatchFocusChange = 120;
    static final int TRANSACTION_forceComputeCsdOnAllDevices = 101;
    static final int TRANSACTION_forceRemoteSubmixFullVolume = 18;
    static final int TRANSACTION_forceUseFrameworkMel = 100;
    static final int TRANSACTION_forceVolumeControlStream = 77;
    static final int TRANSACTION_getA2dpDeviceVolume = 276;
    static final int TRANSACTION_getActiveAssistantServiceUids = 226;
    static final int TRANSACTION_getActivePlaybackConfigurations = 118;
    static final int TRANSACTION_getActiveRecordingConfigurations = 115;
    static final int TRANSACTION_getActualHeadTrackingMode = 203;
    static final int TRANSACTION_getAdditionalOutputDeviceDelay = 173;
    static final int TRANSACTION_getAllowedCapturePolicy = 144;
    static final int TRANSACTION_getAppDevice = 242;
    static final int TRANSACTION_getAppVolume = 244;
    static final int TRANSACTION_getAssistantServicesUids = 225;
    static final int TRANSACTION_getAudioProductStrategies = 35;
    static final int TRANSACTION_getAudioServiceConfig = 237;
    static final int TRANSACTION_getAudioVolumeGroups = 24;
    static final int TRANSACTION_getAvailableCommunicationDeviceIds = 163;
    static final int TRANSACTION_getCommunicationDevice = 165;
    static final int TRANSACTION_getCsd = 98;
    static final int TRANSACTION_getCurrentAudioFocus = 73;
    static final int TRANSACTION_getCurrentAudioFocusPackageName = 273;
    static final int TRANSACTION_getDefaultVolumeInfo = 212;
    static final int TRANSACTION_getDesiredHeadTrackingMode = 201;
    static final int TRANSACTION_getDeviceMaskForStream = 162;
    static final int TRANSACTION_getDeviceVolume = 15;
    static final int TRANSACTION_getDeviceVolumeBehavior = 151;
    static final int TRANSACTION_getDevicesForAttributes = 139;
    static final int TRANSACTION_getDevicesForAttributesUnprotected = 140;
    static final int TRANSACTION_getEarProtectLimit = 289;
    static final int TRANSACTION_getEncodedSurroundMode = 61;
    static final int TRANSACTION_getExcludedRingtoneTitles = 283;
    static final int TRANSACTION_getFadeOutDurationOnFocusLossMillis = 177;
    static final int TRANSACTION_getFineVolume = 259;
    static final int TRANSACTION_getFloatVolumeTable = 277;
    static final int TRANSACTION_getFocusRampTimeMs = 119;
    static final int TRANSACTION_getFocusStack = 220;
    static final int TRANSACTION_getHalVersion = 228;
    static final int TRANSACTION_getIndependentStreamTypes = 81;
    static final int TRANSACTION_getLastAudibleStreamVolume = 32;
    static final int TRANSACTION_getLastAudibleVolumeForVolumeGroup = 29;
    static final int TRANSACTION_getMaxAdditionalOutputDeviceDelay = 174;
    static final int TRANSACTION_getMediaVolumeSteps = 269;
    static final int TRANSACTION_getMicModeType = 288;
    static final int TRANSACTION_getMode = 50;
    static final int TRANSACTION_getModeInternal = 286;
    static final int TRANSACTION_getMuteInterval = 264;
    static final int TRANSACTION_getMutingExpectedDevice = 216;
    static final int TRANSACTION_getNonDefaultDevicesForStrategy = 138;
    static final int TRANSACTION_getOutputRs2UpperBound = 96;
    static final int TRANSACTION_getPinAppInfo = 251;
    static final int TRANSACTION_getPinDevice = 252;
    static final int TRANSACTION_getPreferredDevicesForCapturePreset = 155;
    static final int TRANSACTION_getPreferredDevicesForStrategy = 135;
    static final int TRANSACTION_getPrevRingerMode = 266;
    static final int TRANSACTION_getRadioOutputPath = 271;
    static final int TRANSACTION_getRemainingMuteIntervalMs = 265;
    static final int TRANSACTION_getReportedSurroundFormats = 57;
    static final int TRANSACTION_getRingerModeExternal = 43;
    static final int TRANSACTION_getRingerModeInternal = 44;
    static final int TRANSACTION_getRingtonePlayer = 79;
    static final int TRANSACTION_getSelectedAppList = 253;
    static final int TRANSACTION_getSpatializerCompatibleAudioDevices = 197;
    static final int TRANSACTION_getSpatializerImmersiveAudioLevel = 180;
    static final int TRANSACTION_getSpatializerOutput = 208;
    static final int TRANSACTION_getSpatializerParameter = 207;
    static final int TRANSACTION_getStreamMaxVolume = 23;
    static final int TRANSACTION_getStreamMinVolume = 22;
    static final int TRANSACTION_getStreamTypeAlias = 82;
    static final int TRANSACTION_getStreamVolume = 21;
    static final int TRANSACTION_getStreamVolumeForDevice = 250;
    static final int TRANSACTION_getSupportedHeadTrackingModes = 202;
    static final int TRANSACTION_getSupportedSystemUsages = 34;
    static final int TRANSACTION_getSurroundFormats = 56;
    static final int TRANSACTION_getUiSoundsStreamType = 80;
    static final int TRANSACTION_getUidForDevice = 240;
    static final int TRANSACTION_getVibrateSetting = 47;
    static final int TRANSACTION_getVolumeController = 90;
    static final int TRANSACTION_getVolumeGroupMaxVolumeIndex = 27;
    static final int TRANSACTION_getVolumeGroupMinVolumeIndex = 28;
    static final int TRANSACTION_getVolumeGroupVolumeIndex = 26;
    static final int TRANSACTION_handleBluetoothActiveDeviceChanged = 122;
    static final int TRANSACTION_handleVolumeKey = 16;
    static final int TRANSACTION_hasHapticChannels = 131;
    static final int TRANSACTION_hasHeadTracker = 184;
    static final int TRANSACTION_hasRegisteredDynamicPolicy = 112;
    static final int TRANSACTION_isAlreadyInDB = 256;
    static final int TRANSACTION_isAppMute = 246;
    static final int TRANSACTION_isAudioServerRunning = 126;
    static final int TRANSACTION_isBluetoothA2dpOn = 69;
    static final int TRANSACTION_isBluetoothScoOn = 67;
    static final int TRANSACTION_isBluetoothVariableLatencyEnabled = 235;
    static final int TRANSACTION_isCallScreeningModeSupported = 132;
    static final int TRANSACTION_isCameraSoundForced = 88;
    static final int TRANSACTION_isCsdEnabled = 102;
    static final int TRANSACTION_isForceSpeakerOn = 261;
    static final int TRANSACTION_isHdmiSystemAudioSupported = 104;
    static final int TRANSACTION_isHeadTrackerAvailable = 187;
    static final int TRANSACTION_isHeadTrackerEnabled = 186;
    static final int TRANSACTION_isHomeSoundEffectEnabled = 170;
    static final int TRANSACTION_isHotwordStreamSupported = 38;
    static final int TRANSACTION_isInAllowedList = 257;
    static final int TRANSACTION_isMasterMute = 19;
    static final int TRANSACTION_isMicrophoneMuted = 36;
    static final int TRANSACTION_isMultiSoundOn = 248;
    static final int TRANSACTION_isMusicActive = 161;
    static final int TRANSACTION_isPstnCallAudioInterceptable = 213;
    static final int TRANSACTION_isSafeMediaVolumeStateActive = 282;
    static final int TRANSACTION_isSpatializerAvailable = 182;
    static final int TRANSACTION_isSpatializerAvailableForDevice = 183;
    static final int TRANSACTION_isSpatializerEnabled = 181;
    static final int TRANSACTION_isSpeakerphoneOn = 63;
    static final int TRANSACTION_isStreamAffectedByMute = 93;
    static final int TRANSACTION_isStreamAffectedByRingerMode = 92;
    static final int TRANSACTION_isStreamMute = 17;
    static final int TRANSACTION_isSurroundFormatEnabled = 59;
    static final int TRANSACTION_isUltrasoundSupported = 37;
    static final int TRANSACTION_isUsingAudio = 274;
    static final int TRANSACTION_isValidRingerMode = 45;
    static final int TRANSACTION_isVolumeControlUsingVolumeGroups = 83;
    static final int TRANSACTION_isVolumeFixed = 211;
    static final int TRANSACTION_isVolumeGroupMuted = 30;
    static final int TRANSACTION_loadSoundEffects = 53;
    static final int TRANSACTION_lowerVolumeToRs1 = 95;
    static final int TRANSACTION_muteAwaitConnection = 214;
    static final int TRANSACTION_nativeEvent = 285;
    static final int TRANSACTION_notifySafetyVolumeDialogVisible = 284;
    static final int TRANSACTION_notifyVolumeControllerVisible = 91;
    static final int TRANSACTION_playSoundEffect = 51;
    static final int TRANSACTION_playSoundEffectVolume = 52;
    static final int TRANSACTION_playerAttributes = 2;
    static final int TRANSACTION_playerEvent = 3;
    static final int TRANSACTION_playerHasOpPlayAudio = 121;
    static final int TRANSACTION_playerSessionId = 8;
    static final int TRANSACTION_portEvent = 9;
    static final int TRANSACTION_recenterHeadTracker = 205;
    static final int TRANSACTION_recordRingtoneChanger = 279;
    static final int TRANSACTION_recorderEvent = 6;
    static final int TRANSACTION_registerAudioPolicy = 105;
    static final int TRANSACTION_registerAudioServerStateDispatcher = 124;
    static final int TRANSACTION_registerCapturePresetDevicesRoleDispatcher = 156;
    static final int TRANSACTION_registerCommunicationDeviceDispatcher = 166;
    static final int TRANSACTION_registerDeviceVolumeBehaviorDispatcher = 219;
    static final int TRANSACTION_registerDeviceVolumeDispatcherForAbsoluteVolume = 227;
    static final int TRANSACTION_registerHeadToSoundstagePoseCallback = 195;
    static final int TRANSACTION_registerModeDispatcher = 178;
    static final int TRANSACTION_registerMuteAwaitConnectionDispatcher = 217;
    static final int TRANSACTION_registerPlaybackCallback = 116;
    static final int TRANSACTION_registerPlaybackCallbackWithPackage = 280;
    static final int TRANSACTION_registerPreferredMixerAttributesDispatcher = 231;
    static final int TRANSACTION_registerRecordingCallback = 113;
    static final int TRANSACTION_registerSpatializerCallback = 191;
    static final int TRANSACTION_registerSpatializerHeadTrackerAvailableCallback = 188;
    static final int TRANSACTION_registerSpatializerHeadTrackingCallback = 193;
    static final int TRANSACTION_registerSpatializerOutputCallback = 209;
    static final int TRANSACTION_registerStrategyNonDefaultDevicesDispatcher = 147;
    static final int TRANSACTION_registerStrategyPreferredDevicesDispatcher = 145;
    static final int TRANSACTION_registerStreamAliasingDispatcher = 84;
    static final int TRANSACTION_releasePlayer = 4;
    static final int TRANSACTION_releaseRecorder = 7;
    static final int TRANSACTION_reloadAudioSettings = 55;
    static final int TRANSACTION_removeAssistantServicesUids = 223;
    static final int TRANSACTION_removeDeviceAsNonDefaultForStrategy = 137;
    static final int TRANSACTION_removeMixForPolicy = 109;
    static final int TRANSACTION_removeOnDevicesForAttributesChangedListener = 142;
    static final int TRANSACTION_removePackageForName = 255;
    static final int TRANSACTION_removePreferredDevicesForStrategy = 134;
    static final int TRANSACTION_removeSpatializerCompatibleAudioDevice = 199;
    static final int TRANSACTION_removeUidDeviceAffinity = 128;
    static final int TRANSACTION_removeUserIdDeviceAffinity = 130;
    static final int TRANSACTION_requestAudioFocus = 70;
    static final int TRANSACTION_requestAudioFocusForTest = 175;
    static final int TRANSACTION_secGetActiveStreamType = 239;
    static final int TRANSACTION_sendFocusLoss = 221;
    static final int TRANSACTION_setA2dpDeviceVolume = 275;
    static final int TRANSACTION_setA2dpSuspended = 65;
    static final int TRANSACTION_setActiveAssistantServiceUids = 224;
    static final int TRANSACTION_setAdditionalOutputDeviceDelay = 172;
    static final int TRANSACTION_setAllowedCapturePolicy = 143;
    static final int TRANSACTION_setAppDevice = 241;
    static final int TRANSACTION_setAppMute = 245;
    static final int TRANSACTION_setAppVolume = 243;
    static final int TRANSACTION_setAudioServiceConfig = 236;
    static final int TRANSACTION_setBluetoothA2dpOn = 68;
    static final int TRANSACTION_setBluetoothScoOn = 64;
    static final int TRANSACTION_setBluetoothVariableLatencyEnabled = 234;
    static final int TRANSACTION_setBtOffloadEnable = 281;
    static final int TRANSACTION_setCommunicationDevice = 164;
    static final int TRANSACTION_setCsd = 99;
    static final int TRANSACTION_setDesiredHeadTrackingMode = 200;
    static final int TRANSACTION_setDeviceAsNonDefaultForStrategy = 136;
    static final int TRANSACTION_setDeviceToForceByUser = 262;
    static final int TRANSACTION_setDeviceVolume = 14;
    static final int TRANSACTION_setDeviceVolumeBehavior = 150;
    static final int TRANSACTION_setEncodedSurroundMode = 60;
    static final int TRANSACTION_setFineVolume = 258;
    static final int TRANSACTION_setFocusPropertiesForPolicy = 110;
    static final int TRANSACTION_setFocusRequestResultFromExtPolicy = 123;
    static final int TRANSACTION_setForceSpeakerOn = 260;
    static final int TRANSACTION_setHdmiSystemAudioSupported = 103;
    static final int TRANSACTION_setHeadTrackerEnabled = 185;
    static final int TRANSACTION_setHomeSoundEffectEnabled = 171;
    static final int TRANSACTION_setLeAudioSuspended = 66;
    static final int TRANSACTION_setMasterMute = 20;
    static final int TRANSACTION_setMediaVolumeSteps = 268;
    static final int TRANSACTION_setMicInputControlMode = 287;
    static final int TRANSACTION_setMicrophoneMute = 39;
    static final int TRANSACTION_setMicrophoneMuteFromSwitch = 40;
    static final int TRANSACTION_setMode = 49;
    static final int TRANSACTION_setMultiAudioFocusEnabled = 152;
    static final int TRANSACTION_setMultiSoundOn = 247;
    static final int TRANSACTION_setMuteInterval = 263;
    static final int TRANSACTION_setNavigationRepeatSoundEffectsEnabled = 169;
    static final int TRANSACTION_setNotifAliasRingForTest = 85;
    static final int TRANSACTION_setOutputRs2UpperBound = 97;
    static final int TRANSACTION_setPreferredDevicesForCapturePreset = 153;
    static final int TRANSACTION_setPreferredDevicesForStrategy = 133;
    static final int TRANSACTION_setPreferredMixerAttributes = 229;
    static final int TRANSACTION_setRadioOutputPath = 270;
    static final int TRANSACTION_setRemoteMic = 278;
    static final int TRANSACTION_setRingerModeExternal = 41;
    static final int TRANSACTION_setRingerModeInternal = 42;
    static final int TRANSACTION_setRingtonePlayer = 78;
    static final int TRANSACTION_setRttEnabled = 149;
    static final int TRANSACTION_setSoundSettingEventBroadcastIntent = 267;
    static final int TRANSACTION_setSpatializerEnabled = 189;
    static final int TRANSACTION_setSpatializerGlobalTransform = 204;
    static final int TRANSACTION_setSpatializerParameter = 206;
    static final int TRANSACTION_setSpeakerphoneOn = 62;
    static final int TRANSACTION_setStreamVolume = 12;
    static final int TRANSACTION_setStreamVolumeForDeviceWithAttribution = 249;
    static final int TRANSACTION_setStreamVolumeForUid = 160;
    static final int TRANSACTION_setStreamVolumeWithAttribution = 13;
    static final int TRANSACTION_setSupportedSystemUsages = 33;
    static final int TRANSACTION_setSurroundFormatEnabled = 58;
    static final int TRANSACTION_setTestDeviceConnectionState = 218;
    static final int TRANSACTION_setUidDeviceAffinity = 127;
    static final int TRANSACTION_setUserIdDeviceAffinity = 129;
    static final int TRANSACTION_setVibrateSetting = 46;
    static final int TRANSACTION_setVolumeController = 89;
    static final int TRANSACTION_setVolumeGroupVolumeIndex = 25;
    static final int TRANSACTION_setVolumePolicy = 111;
    static final int TRANSACTION_setWiredDeviceConnectionState = 86;
    static final int TRANSACTION_shouldShowRingtoneVolume = 238;
    static final int TRANSACTION_shouldVibrate = 48;
    static final int TRANSACTION_startBluetoothSco = 74;
    static final int TRANSACTION_startBluetoothScoVirtualCall = 75;
    static final int TRANSACTION_startWatchingRoutes = 87;
    static final int TRANSACTION_stopBluetoothSco = 76;
    static final int TRANSACTION_supportsBluetoothVariableLatency = 233;
    static final int TRANSACTION_trackPlayer = 1;
    static final int TRANSACTION_trackRecorder = 5;
    static final int TRANSACTION_unloadSoundEffects = 54;
    static final int TRANSACTION_unregisterAudioFocusClient = 72;
    static final int TRANSACTION_unregisterAudioPolicy = 107;
    static final int TRANSACTION_unregisterAudioPolicyAsync = 106;
    static final int TRANSACTION_unregisterAudioServerStateDispatcher = 125;
    static final int TRANSACTION_unregisterCapturePresetDevicesRoleDispatcher = 157;
    static final int TRANSACTION_unregisterCommunicationDeviceDispatcher = 167;
    static final int TRANSACTION_unregisterHeadToSoundstagePoseCallback = 196;
    static final int TRANSACTION_unregisterModeDispatcher = 179;
    static final int TRANSACTION_unregisterPlaybackCallback = 117;
    static final int TRANSACTION_unregisterPreferredMixerAttributesDispatcher = 232;
    static final int TRANSACTION_unregisterRecordingCallback = 114;
    static final int TRANSACTION_unregisterSpatializerCallback = 192;
    static final int TRANSACTION_unregisterSpatializerHeadTrackingCallback = 194;
    static final int TRANSACTION_unregisterSpatializerOutputCallback = 210;
    static final int TRANSACTION_unregisterStrategyNonDefaultDevicesDispatcher = 148;
    static final int TRANSACTION_unregisterStrategyPreferredDevicesDispatcher = 146;
    private final PermissionEnforcer mEnforcer;
    static final String[] PERMISSIONS_setDeviceVolume = {
      Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED
    };
    static final String[] PERMISSIONS_getDeviceVolume = {
      Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED
    };
    static final String[] PERMISSIONS_setVolumeGroupVolumeIndex = {
      Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING
    };
    static final String[] PERMISSIONS_getVolumeGroupVolumeIndex = {
      Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING
    };
    static final String[] PERMISSIONS_getVolumeGroupMaxVolumeIndex = {
      Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING
    };
    static final String[] PERMISSIONS_getVolumeGroupMinVolumeIndex = {
      Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING
    };
    static final String[] PERMISSIONS_setDeviceVolumeBehavior = {
      Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED
    };
    static final String[] PERMISSIONS_getDeviceVolumeBehavior = {
      Manifest.permission.MODIFY_AUDIO_ROUTING,
      Manifest.permission.QUERY_AUDIO_STATE,
      Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED
    };

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
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

    @Override // android.p009os.IInterface
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
          return "disableSafeMediaVolume";
        case 95:
          return "lowerVolumeToRs1";
        case 96:
          return "getOutputRs2UpperBound";
        case 97:
          return "setOutputRs2UpperBound";
        case 98:
          return "getCsd";
        case 99:
          return "setCsd";
        case 100:
          return "forceUseFrameworkMel";
        case 101:
          return "forceComputeCsdOnAllDevices";
        case 102:
          return "isCsdEnabled";
        case 103:
          return "setHdmiSystemAudioSupported";
        case 104:
          return "isHdmiSystemAudioSupported";
        case 105:
          return "registerAudioPolicy";
        case 106:
          return "unregisterAudioPolicyAsync";
        case 107:
          return "unregisterAudioPolicy";
        case 108:
          return "addMixForPolicy";
        case 109:
          return "removeMixForPolicy";
        case 110:
          return "setFocusPropertiesForPolicy";
        case 111:
          return "setVolumePolicy";
        case 112:
          return "hasRegisteredDynamicPolicy";
        case 113:
          return "registerRecordingCallback";
        case 114:
          return "unregisterRecordingCallback";
        case 115:
          return "getActiveRecordingConfigurations";
        case 116:
          return "registerPlaybackCallback";
        case 117:
          return "unregisterPlaybackCallback";
        case 118:
          return "getActivePlaybackConfigurations";
        case 119:
          return "getFocusRampTimeMs";
        case 120:
          return "dispatchFocusChange";
        case 121:
          return "playerHasOpPlayAudio";
        case 122:
          return "handleBluetoothActiveDeviceChanged";
        case 123:
          return "setFocusRequestResultFromExtPolicy";
        case 124:
          return "registerAudioServerStateDispatcher";
        case 125:
          return "unregisterAudioServerStateDispatcher";
        case 126:
          return "isAudioServerRunning";
        case 127:
          return "setUidDeviceAffinity";
        case 128:
          return "removeUidDeviceAffinity";
        case 129:
          return "setUserIdDeviceAffinity";
        case 130:
          return "removeUserIdDeviceAffinity";
        case 131:
          return "hasHapticChannels";
        case 132:
          return "isCallScreeningModeSupported";
        case 133:
          return "setPreferredDevicesForStrategy";
        case 134:
          return "removePreferredDevicesForStrategy";
        case 135:
          return "getPreferredDevicesForStrategy";
        case 136:
          return "setDeviceAsNonDefaultForStrategy";
        case 137:
          return "removeDeviceAsNonDefaultForStrategy";
        case 138:
          return "getNonDefaultDevicesForStrategy";
        case 139:
          return "getDevicesForAttributes";
        case 140:
          return "getDevicesForAttributesUnprotected";
        case 141:
          return "addOnDevicesForAttributesChangedListener";
        case 142:
          return "removeOnDevicesForAttributesChangedListener";
        case 143:
          return "setAllowedCapturePolicy";
        case 144:
          return "getAllowedCapturePolicy";
        case 145:
          return "registerStrategyPreferredDevicesDispatcher";
        case 146:
          return "unregisterStrategyPreferredDevicesDispatcher";
        case 147:
          return "registerStrategyNonDefaultDevicesDispatcher";
        case 148:
          return "unregisterStrategyNonDefaultDevicesDispatcher";
        case 149:
          return "setRttEnabled";
        case 150:
          return "setDeviceVolumeBehavior";
        case 151:
          return "getDeviceVolumeBehavior";
        case 152:
          return "setMultiAudioFocusEnabled";
        case 153:
          return "setPreferredDevicesForCapturePreset";
        case 154:
          return "clearPreferredDevicesForCapturePreset";
        case 155:
          return "getPreferredDevicesForCapturePreset";
        case 156:
          return "registerCapturePresetDevicesRoleDispatcher";
        case 157:
          return "unregisterCapturePresetDevicesRoleDispatcher";
        case 158:
          return "adjustStreamVolumeForUid";
        case 159:
          return "adjustSuggestedStreamVolumeForUid";
        case 160:
          return "setStreamVolumeForUid";
        case 161:
          return "isMusicActive";
        case 162:
          return "getDeviceMaskForStream";
        case 163:
          return "getAvailableCommunicationDeviceIds";
        case 164:
          return "setCommunicationDevice";
        case 165:
          return "getCommunicationDevice";
        case 166:
          return "registerCommunicationDeviceDispatcher";
        case 167:
          return "unregisterCommunicationDeviceDispatcher";
        case 168:
          return "areNavigationRepeatSoundEffectsEnabled";
        case 169:
          return "setNavigationRepeatSoundEffectsEnabled";
        case 170:
          return "isHomeSoundEffectEnabled";
        case 171:
          return "setHomeSoundEffectEnabled";
        case 172:
          return "setAdditionalOutputDeviceDelay";
        case 173:
          return "getAdditionalOutputDeviceDelay";
        case 174:
          return "getMaxAdditionalOutputDeviceDelay";
        case 175:
          return "requestAudioFocusForTest";
        case 176:
          return "abandonAudioFocusForTest";
        case 177:
          return "getFadeOutDurationOnFocusLossMillis";
        case 178:
          return "registerModeDispatcher";
        case 179:
          return "unregisterModeDispatcher";
        case 180:
          return "getSpatializerImmersiveAudioLevel";
        case 181:
          return "isSpatializerEnabled";
        case 182:
          return "isSpatializerAvailable";
        case 183:
          return "isSpatializerAvailableForDevice";
        case 184:
          return "hasHeadTracker";
        case 185:
          return "setHeadTrackerEnabled";
        case 186:
          return "isHeadTrackerEnabled";
        case 187:
          return "isHeadTrackerAvailable";
        case 188:
          return "registerSpatializerHeadTrackerAvailableCallback";
        case 189:
          return "setSpatializerEnabled";
        case 190:
          return "canBeSpatialized";
        case 191:
          return "registerSpatializerCallback";
        case 192:
          return "unregisterSpatializerCallback";
        case 193:
          return "registerSpatializerHeadTrackingCallback";
        case 194:
          return "unregisterSpatializerHeadTrackingCallback";
        case 195:
          return "registerHeadToSoundstagePoseCallback";
        case 196:
          return "unregisterHeadToSoundstagePoseCallback";
        case 197:
          return "getSpatializerCompatibleAudioDevices";
        case 198:
          return "addSpatializerCompatibleAudioDevice";
        case 199:
          return "removeSpatializerCompatibleAudioDevice";
        case 200:
          return "setDesiredHeadTrackingMode";
        case 201:
          return "getDesiredHeadTrackingMode";
        case 202:
          return "getSupportedHeadTrackingModes";
        case 203:
          return "getActualHeadTrackingMode";
        case 204:
          return "setSpatializerGlobalTransform";
        case 205:
          return "recenterHeadTracker";
        case 206:
          return "setSpatializerParameter";
        case 207:
          return "getSpatializerParameter";
        case 208:
          return "getSpatializerOutput";
        case 209:
          return "registerSpatializerOutputCallback";
        case 210:
          return "unregisterSpatializerOutputCallback";
        case 211:
          return "isVolumeFixed";
        case 212:
          return "getDefaultVolumeInfo";
        case 213:
          return "isPstnCallAudioInterceptable";
        case 214:
          return "muteAwaitConnection";
        case 215:
          return "cancelMuteAwaitConnection";
        case 216:
          return "getMutingExpectedDevice";
        case 217:
          return "registerMuteAwaitConnectionDispatcher";
        case 218:
          return "setTestDeviceConnectionState";
        case 219:
          return "registerDeviceVolumeBehaviorDispatcher";
        case 220:
          return "getFocusStack";
        case 221:
          return "sendFocusLoss";
        case 222:
          return "addAssistantServicesUids";
        case 223:
          return "removeAssistantServicesUids";
        case 224:
          return "setActiveAssistantServiceUids";
        case 225:
          return "getAssistantServicesUids";
        case 226:
          return "getActiveAssistantServiceUids";
        case 227:
          return "registerDeviceVolumeDispatcherForAbsoluteVolume";
        case 228:
          return "getHalVersion";
        case 229:
          return "setPreferredMixerAttributes";
        case 230:
          return "clearPreferredMixerAttributes";
        case 231:
          return "registerPreferredMixerAttributesDispatcher";
        case 232:
          return "unregisterPreferredMixerAttributesDispatcher";
        case 233:
          return "supportsBluetoothVariableLatency";
        case 234:
          return "setBluetoothVariableLatencyEnabled";
        case 235:
          return "isBluetoothVariableLatencyEnabled";
        case 236:
          return "setAudioServiceConfig";
        case 237:
          return "getAudioServiceConfig";
        case 238:
          return "shouldShowRingtoneVolume";
        case 239:
          return "secGetActiveStreamType";
        case 240:
          return "getUidForDevice";
        case 241:
          return "setAppDevice";
        case 242:
          return "getAppDevice";
        case 243:
          return "setAppVolume";
        case 244:
          return "getAppVolume";
        case 245:
          return "setAppMute";
        case 246:
          return "isAppMute";
        case 247:
          return "setMultiSoundOn";
        case 248:
          return "isMultiSoundOn";
        case 249:
          return "setStreamVolumeForDeviceWithAttribution";
        case 250:
          return "getStreamVolumeForDevice";
        case 251:
          return "getPinAppInfo";
        case 252:
          return "getPinDevice";
        case 253:
          return "getSelectedAppList";
        case 254:
          return "addPackage";
        case 255:
          return "removePackageForName";
        case 256:
          return "isAlreadyInDB";
        case 257:
          return "isInAllowedList";
        case 258:
          return "setFineVolume";
        case 259:
          return "getFineVolume";
        case 260:
          return "setForceSpeakerOn";
        case 261:
          return "isForceSpeakerOn";
        case 262:
          return "setDeviceToForceByUser";
        case 263:
          return "setMuteInterval";
        case 264:
          return "getMuteInterval";
        case 265:
          return "getRemainingMuteIntervalMs";
        case 266:
          return "getPrevRingerMode";
        case 267:
          return "setSoundSettingEventBroadcastIntent";
        case 268:
          return "setMediaVolumeSteps";
        case 269:
          return "getMediaVolumeSteps";
        case 270:
          return "setRadioOutputPath";
        case 271:
          return "getRadioOutputPath";
        case 272:
          return "dismissVolumePanel";
        case 273:
          return "getCurrentAudioFocusPackageName";
        case 274:
          return "isUsingAudio";
        case 275:
          return "setA2dpDeviceVolume";
        case 276:
          return "getA2dpDeviceVolume";
        case 277:
          return "getFloatVolumeTable";
        case 278:
          return "setRemoteMic";
        case 279:
          return "recordRingtoneChanger";
        case 280:
          return "registerPlaybackCallbackWithPackage";
        case 281:
          return "setBtOffloadEnable";
        case 282:
          return "isSafeMediaVolumeStateActive";
        case 283:
          return "getExcludedRingtoneTitles";
        case 284:
          return "notifySafetyVolumeDialogVisible";
        case 285:
          return "nativeEvent";
        case 286:
          return "getModeInternal";
        case 287:
          return "setMicInputControlMode";
        case 288:
          return "getMicModeType";
        case 289:
          return "getEarProtectLimit";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              PlayerBase.PlayerIdCard _arg0 =
                  (PlayerBase.PlayerIdCard) data.readTypedObject(PlayerBase.PlayerIdCard.CREATOR);
              data.enforceNoDataAvail();
              int _result = trackPlayer(_arg0);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              AudioAttributes _arg1 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
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
              int _arg09 = data.readInt();
              int _arg15 = data.readInt();
              PersistableBundle _arg22 =
                  (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
              data.enforceNoDataAvail();
              portEvent(_arg09, _arg15, _arg22);
              return true;
            case 10:
              int _arg010 = data.readInt();
              int _arg16 = data.readInt();
              int _arg23 = data.readInt();
              String _arg3 = data.readString();
              data.enforceNoDataAvail();
              adjustStreamVolume(_arg010, _arg16, _arg23, _arg3);
              reply.writeNoException();
              return true;
            case 11:
              return onTransact$adjustStreamVolumeWithAttribution$(data, reply);
            case 12:
              int _arg011 = data.readInt();
              int _arg17 = data.readInt();
              int _arg24 = data.readInt();
              String _arg32 = data.readString();
              data.enforceNoDataAvail();
              setStreamVolume(_arg011, _arg17, _arg24, _arg32);
              reply.writeNoException();
              return true;
            case 13:
              return onTransact$setStreamVolumeWithAttribution$(data, reply);
            case 14:
              VolumeInfo _arg012 = (VolumeInfo) data.readTypedObject(VolumeInfo.CREATOR);
              AudioDeviceAttributes _arg18 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              String _arg25 = data.readString();
              data.enforceNoDataAvail();
              setDeviceVolume(_arg012, _arg18, _arg25);
              reply.writeNoException();
              return true;
            case 15:
              VolumeInfo _arg013 = (VolumeInfo) data.readTypedObject(VolumeInfo.CREATOR);
              AudioDeviceAttributes _arg19 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              String _arg26 = data.readString();
              data.enforceNoDataAvail();
              VolumeInfo _result3 = getDeviceVolume(_arg013, _arg19, _arg26);
              reply.writeNoException();
              reply.writeTypedObject(_result3, 1);
              return true;
            case 16:
              KeyEvent _arg014 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
              boolean _arg110 = data.readBoolean();
              String _arg27 = data.readString();
              String _arg33 = data.readString();
              data.enforceNoDataAvail();
              handleVolumeKey(_arg014, _arg110, _arg27, _arg33);
              return true;
            case 17:
              int _arg015 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result4 = isStreamMute(_arg015);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 18:
              boolean _arg016 = data.readBoolean();
              IBinder _arg111 = data.readStrongBinder();
              data.enforceNoDataAvail();
              forceRemoteSubmixFullVolume(_arg016, _arg111);
              reply.writeNoException();
              return true;
            case 19:
              boolean _result5 = isMasterMute();
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 20:
              return onTransact$setMasterMute$(data, reply);
            case 21:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              int _result6 = getStreamVolume(_arg017);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 22:
              int _arg018 = data.readInt();
              data.enforceNoDataAvail();
              int _result7 = getStreamMinVolume(_arg018);
              reply.writeNoException();
              reply.writeInt(_result7);
              return true;
            case 23:
              int _arg019 = data.readInt();
              data.enforceNoDataAvail();
              int _result8 = getStreamMaxVolume(_arg019);
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 24:
              List<android.media.audiopolicy.AudioVolumeGroup> _result9 = getAudioVolumeGroups();
              reply.writeNoException();
              reply.writeTypedList(_result9, 1);
              return true;
            case 25:
              return onTransact$setVolumeGroupVolumeIndex$(data, reply);
            case 26:
              int _arg020 = data.readInt();
              data.enforceNoDataAvail();
              int _result10 = getVolumeGroupVolumeIndex(_arg020);
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 27:
              int _arg021 = data.readInt();
              data.enforceNoDataAvail();
              int _result11 = getVolumeGroupMaxVolumeIndex(_arg021);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 28:
              int _arg022 = data.readInt();
              data.enforceNoDataAvail();
              int _result12 = getVolumeGroupMinVolumeIndex(_arg022);
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 29:
              int _arg023 = data.readInt();
              data.enforceNoDataAvail();
              int _result13 = getLastAudibleVolumeForVolumeGroup(_arg023);
              reply.writeNoException();
              reply.writeInt(_result13);
              return true;
            case 30:
              int _arg024 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result14 = isVolumeGroupMuted(_arg024);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 31:
              int _arg025 = data.readInt();
              int _arg112 = data.readInt();
              int _arg28 = data.readInt();
              String _arg34 = data.readString();
              data.enforceNoDataAvail();
              adjustVolumeGroupVolume(_arg025, _arg112, _arg28, _arg34);
              reply.writeNoException();
              return true;
            case 32:
              int _arg026 = data.readInt();
              data.enforceNoDataAvail();
              int _result15 = getLastAudibleStreamVolume(_arg026);
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 33:
              int[] _arg027 = data.createIntArray();
              data.enforceNoDataAvail();
              setSupportedSystemUsages(_arg027);
              reply.writeNoException();
              return true;
            case 34:
              int[] _result16 = getSupportedSystemUsages();
              reply.writeNoException();
              reply.writeIntArray(_result16);
              return true;
            case 35:
              List<android.media.audiopolicy.AudioProductStrategy> _result17 =
                  getAudioProductStrategies();
              reply.writeNoException();
              reply.writeTypedList(_result17, 1);
              return true;
            case 36:
              boolean _result18 = isMicrophoneMuted();
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 37:
              boolean _result19 = isUltrasoundSupported();
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 38:
              boolean _arg028 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result20 = isHotwordStreamSupported(_arg028);
              reply.writeNoException();
              reply.writeBoolean(_result20);
              return true;
            case 39:
              boolean _arg029 = data.readBoolean();
              String _arg113 = data.readString();
              int _arg29 = data.readInt();
              String _arg35 = data.readString();
              data.enforceNoDataAvail();
              setMicrophoneMute(_arg029, _arg113, _arg29, _arg35);
              reply.writeNoException();
              return true;
            case 40:
              boolean _arg030 = data.readBoolean();
              data.enforceNoDataAvail();
              setMicrophoneMuteFromSwitch(_arg030);
              return true;
            case 41:
              int _arg031 = data.readInt();
              String _arg114 = data.readString();
              data.enforceNoDataAvail();
              setRingerModeExternal(_arg031, _arg114);
              reply.writeNoException();
              return true;
            case 42:
              int _arg032 = data.readInt();
              String _arg115 = data.readString();
              data.enforceNoDataAvail();
              setRingerModeInternal(_arg032, _arg115);
              reply.writeNoException();
              return true;
            case 43:
              int _result21 = getRingerModeExternal();
              reply.writeNoException();
              reply.writeInt(_result21);
              return true;
            case 44:
              int _result22 = getRingerModeInternal();
              reply.writeNoException();
              reply.writeInt(_result22);
              return true;
            case 45:
              int _arg033 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result23 = isValidRingerMode(_arg033);
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 46:
              int _arg034 = data.readInt();
              int _arg116 = data.readInt();
              data.enforceNoDataAvail();
              setVibrateSetting(_arg034, _arg116);
              reply.writeNoException();
              return true;
            case 47:
              int _arg035 = data.readInt();
              data.enforceNoDataAvail();
              int _result24 = getVibrateSetting(_arg035);
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 48:
              int _arg036 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result25 = shouldVibrate(_arg036);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 49:
              int _arg037 = data.readInt();
              IBinder _arg117 = data.readStrongBinder();
              String _arg210 = data.readString();
              data.enforceNoDataAvail();
              setMode(_arg037, _arg117, _arg210);
              reply.writeNoException();
              return true;
            case 50:
              int _result26 = getMode();
              reply.writeNoException();
              reply.writeInt(_result26);
              return true;
            case 51:
              int _arg038 = data.readInt();
              int _arg118 = data.readInt();
              data.enforceNoDataAvail();
              playSoundEffect(_arg038, _arg118);
              return true;
            case 52:
              int _arg039 = data.readInt();
              float _arg119 = data.readFloat();
              data.enforceNoDataAvail();
              playSoundEffectVolume(_arg039, _arg119);
              return true;
            case 53:
              boolean _result27 = loadSoundEffects();
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 54:
              unloadSoundEffects();
              return true;
            case 55:
              reloadAudioSettings();
              return true;
            case 56:
              Map _result28 = getSurroundFormats();
              reply.writeNoException();
              reply.writeMap(_result28);
              return true;
            case 57:
              List _result29 = getReportedSurroundFormats();
              reply.writeNoException();
              reply.writeList(_result29);
              return true;
            case 58:
              int _arg040 = data.readInt();
              boolean _arg120 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result30 = setSurroundFormatEnabled(_arg040, _arg120);
              reply.writeNoException();
              reply.writeBoolean(_result30);
              return true;
            case 59:
              int _arg041 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result31 = isSurroundFormatEnabled(_arg041);
              reply.writeNoException();
              reply.writeBoolean(_result31);
              return true;
            case 60:
              int _arg042 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result32 = setEncodedSurroundMode(_arg042);
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            case 61:
              int _arg043 = data.readInt();
              data.enforceNoDataAvail();
              int _result33 = getEncodedSurroundMode(_arg043);
              reply.writeNoException();
              reply.writeInt(_result33);
              return true;
            case 62:
              IBinder _arg044 = data.readStrongBinder();
              boolean _arg121 = data.readBoolean();
              data.enforceNoDataAvail();
              setSpeakerphoneOn(_arg044, _arg121);
              reply.writeNoException();
              return true;
            case 63:
              boolean _result34 = isSpeakerphoneOn();
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 64:
              boolean _arg045 = data.readBoolean();
              data.enforceNoDataAvail();
              setBluetoothScoOn(_arg045);
              reply.writeNoException();
              return true;
            case 65:
              boolean _arg046 = data.readBoolean();
              data.enforceNoDataAvail();
              setA2dpSuspended(_arg046);
              reply.writeNoException();
              return true;
            case 66:
              boolean _arg047 = data.readBoolean();
              data.enforceNoDataAvail();
              setLeAudioSuspended(_arg047);
              reply.writeNoException();
              return true;
            case 67:
              boolean _result35 = isBluetoothScoOn();
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 68:
              boolean _arg048 = data.readBoolean();
              data.enforceNoDataAvail();
              setBluetoothA2dpOn(_arg048);
              reply.writeNoException();
              return true;
            case 69:
              boolean _result36 = isBluetoothA2dpOn();
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            case 70:
              return onTransact$requestAudioFocus$(data, reply);
            case 71:
              IAudioFocusDispatcher _arg049 =
                  IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
              String _arg122 = data.readString();
              AudioAttributes _arg211 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              String _arg36 = data.readString();
              data.enforceNoDataAvail();
              int _result37 = abandonAudioFocus(_arg049, _arg122, _arg211, _arg36);
              reply.writeNoException();
              reply.writeInt(_result37);
              return true;
            case 72:
              String _arg050 = data.readString();
              data.enforceNoDataAvail();
              unregisterAudioFocusClient(_arg050);
              reply.writeNoException();
              return true;
            case 73:
              int _result38 = getCurrentAudioFocus();
              reply.writeNoException();
              reply.writeInt(_result38);
              return true;
            case 74:
              IBinder _arg051 = data.readStrongBinder();
              int _arg123 = data.readInt();
              data.enforceNoDataAvail();
              startBluetoothSco(_arg051, _arg123);
              reply.writeNoException();
              return true;
            case 75:
              IBinder _arg052 = data.readStrongBinder();
              data.enforceNoDataAvail();
              startBluetoothScoVirtualCall(_arg052);
              reply.writeNoException();
              return true;
            case 76:
              IBinder _arg053 = data.readStrongBinder();
              data.enforceNoDataAvail();
              stopBluetoothSco(_arg053);
              reply.writeNoException();
              return true;
            case 77:
              int _arg054 = data.readInt();
              IBinder _arg124 = data.readStrongBinder();
              data.enforceNoDataAvail();
              forceVolumeControlStream(_arg054, _arg124);
              reply.writeNoException();
              return true;
            case 78:
              IRingtonePlayer _arg055 = IRingtonePlayer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setRingtonePlayer(_arg055);
              reply.writeNoException();
              return true;
            case 79:
              IRingtonePlayer _result39 = getRingtonePlayer();
              reply.writeNoException();
              reply.writeStrongInterface(_result39);
              return true;
            case 80:
              int _result40 = getUiSoundsStreamType();
              reply.writeNoException();
              reply.writeInt(_result40);
              return true;
            case 81:
              List _result41 = getIndependentStreamTypes();
              reply.writeNoException();
              reply.writeList(_result41);
              return true;
            case 82:
              int _arg056 = data.readInt();
              data.enforceNoDataAvail();
              int _result42 = getStreamTypeAlias(_arg056);
              reply.writeNoException();
              reply.writeInt(_result42);
              return true;
            case 83:
              boolean _result43 = isVolumeControlUsingVolumeGroups();
              reply.writeNoException();
              reply.writeBoolean(_result43);
              return true;
            case 84:
              IStreamAliasingDispatcher _arg057 =
                  IStreamAliasingDispatcher.Stub.asInterface(data.readStrongBinder());
              boolean _arg125 = data.readBoolean();
              data.enforceNoDataAvail();
              registerStreamAliasingDispatcher(_arg057, _arg125);
              reply.writeNoException();
              return true;
            case 85:
              boolean _arg058 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotifAliasRingForTest(_arg058);
              reply.writeNoException();
              return true;
            case 86:
              AudioDeviceAttributes _arg059 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              int _arg126 = data.readInt();
              String _arg212 = data.readString();
              data.enforceNoDataAvail();
              setWiredDeviceConnectionState(_arg059, _arg126, _arg212);
              reply.writeNoException();
              return true;
            case 87:
              IAudioRoutesObserver _arg060 =
                  IAudioRoutesObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              AudioRoutesInfo _result44 = startWatchingRoutes(_arg060);
              reply.writeNoException();
              reply.writeTypedObject(_result44, 1);
              return true;
            case 88:
              boolean _result45 = isCameraSoundForced();
              reply.writeNoException();
              reply.writeBoolean(_result45);
              return true;
            case 89:
              IVolumeController _arg061 =
                  IVolumeController.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setVolumeController(_arg061);
              reply.writeNoException();
              return true;
            case 90:
              IVolumeController _result46 = getVolumeController();
              reply.writeNoException();
              reply.writeStrongInterface(_result46);
              return true;
            case 91:
              IVolumeController _arg062 =
                  IVolumeController.Stub.asInterface(data.readStrongBinder());
              boolean _arg127 = data.readBoolean();
              data.enforceNoDataAvail();
              notifyVolumeControllerVisible(_arg062, _arg127);
              reply.writeNoException();
              return true;
            case 92:
              int _arg063 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result47 = isStreamAffectedByRingerMode(_arg063);
              reply.writeNoException();
              reply.writeBoolean(_result47);
              return true;
            case 93:
              int _arg064 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result48 = isStreamAffectedByMute(_arg064);
              reply.writeNoException();
              reply.writeBoolean(_result48);
              return true;
            case 94:
              String _arg065 = data.readString();
              data.enforceNoDataAvail();
              disableSafeMediaVolume(_arg065);
              reply.writeNoException();
              return true;
            case 95:
              String _arg066 = data.readString();
              data.enforceNoDataAvail();
              lowerVolumeToRs1(_arg066);
              reply.writeNoException();
              return true;
            case 96:
              float _result49 = getOutputRs2UpperBound();
              reply.writeNoException();
              reply.writeFloat(_result49);
              return true;
            case 97:
              float _arg067 = data.readFloat();
              data.enforceNoDataAvail();
              setOutputRs2UpperBound(_arg067);
              return true;
            case 98:
              float _result50 = getCsd();
              reply.writeNoException();
              reply.writeFloat(_result50);
              return true;
            case 99:
              float _arg068 = data.readFloat();
              data.enforceNoDataAvail();
              setCsd(_arg068);
              return true;
            case 100:
              boolean _arg069 = data.readBoolean();
              data.enforceNoDataAvail();
              forceUseFrameworkMel(_arg069);
              return true;
            case 101:
              boolean _arg070 = data.readBoolean();
              data.enforceNoDataAvail();
              forceComputeCsdOnAllDevices(_arg070);
              return true;
            case 102:
              boolean _result51 = isCsdEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result51);
              return true;
            case 103:
              boolean _arg071 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result52 = setHdmiSystemAudioSupported(_arg071);
              reply.writeNoException();
              reply.writeInt(_result52);
              return true;
            case 104:
              boolean _result53 = isHdmiSystemAudioSupported();
              reply.writeNoException();
              reply.writeBoolean(_result53);
              return true;
            case 105:
              return onTransact$registerAudioPolicy$(data, reply);
            case 106:
              IAudioPolicyCallback _arg072 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterAudioPolicyAsync(_arg072);
              return true;
            case 107:
              IAudioPolicyCallback _arg073 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterAudioPolicy(_arg073);
              reply.writeNoException();
              return true;
            case 108:
              android.media.audiopolicy.AudioPolicyConfig _arg074 =
                  (android.media.audiopolicy.AudioPolicyConfig)
                      data.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
              IAudioPolicyCallback _arg128 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result54 = addMixForPolicy(_arg074, _arg128);
              reply.writeNoException();
              reply.writeInt(_result54);
              return true;
            case 109:
              android.media.audiopolicy.AudioPolicyConfig _arg075 =
                  (android.media.audiopolicy.AudioPolicyConfig)
                      data.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
              IAudioPolicyCallback _arg129 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result55 = removeMixForPolicy(_arg075, _arg129);
              reply.writeNoException();
              reply.writeInt(_result55);
              return true;
            case 110:
              int _arg076 = data.readInt();
              IAudioPolicyCallback _arg130 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result56 = setFocusPropertiesForPolicy(_arg076, _arg130);
              reply.writeNoException();
              reply.writeInt(_result56);
              return true;
            case 111:
              VolumePolicy _arg077 = (VolumePolicy) data.readTypedObject(VolumePolicy.CREATOR);
              data.enforceNoDataAvail();
              setVolumePolicy(_arg077);
              reply.writeNoException();
              return true;
            case 112:
              boolean _result57 = hasRegisteredDynamicPolicy();
              reply.writeNoException();
              reply.writeBoolean(_result57);
              return true;
            case 113:
              IRecordingConfigDispatcher _arg078 =
                  IRecordingConfigDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerRecordingCallback(_arg078);
              reply.writeNoException();
              return true;
            case 114:
              IRecordingConfigDispatcher _arg079 =
                  IRecordingConfigDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterRecordingCallback(_arg079);
              return true;
            case 115:
              List<AudioRecordingConfiguration> _result58 = getActiveRecordingConfigurations();
              reply.writeNoException();
              reply.writeTypedList(_result58, 1);
              return true;
            case 116:
              IPlaybackConfigDispatcher _arg080 =
                  IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerPlaybackCallback(_arg080);
              reply.writeNoException();
              return true;
            case 117:
              IPlaybackConfigDispatcher _arg081 =
                  IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterPlaybackCallback(_arg081);
              return true;
            case 118:
              List<AudioPlaybackConfiguration> _result59 = getActivePlaybackConfigurations();
              reply.writeNoException();
              reply.writeTypedList(_result59, 1);
              return true;
            case 119:
              int _arg082 = data.readInt();
              AudioAttributes _arg131 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result60 = getFocusRampTimeMs(_arg082, _arg131);
              reply.writeNoException();
              reply.writeInt(_result60);
              return true;
            case 120:
              AudioFocusInfo _arg083 =
                  (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
              int _arg132 = data.readInt();
              IAudioPolicyCallback _arg213 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result61 = dispatchFocusChange(_arg083, _arg132, _arg213);
              reply.writeNoException();
              reply.writeInt(_result61);
              return true;
            case 121:
              int _arg084 = data.readInt();
              boolean _arg133 = data.readBoolean();
              data.enforceNoDataAvail();
              playerHasOpPlayAudio(_arg084, _arg133);
              return true;
            case 122:
              BluetoothDevice _arg085 =
                  (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
              BluetoothDevice _arg134 =
                  (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
              BluetoothProfileConnectionInfo _arg214 =
                  (BluetoothProfileConnectionInfo)
                      data.readTypedObject(BluetoothProfileConnectionInfo.CREATOR);
              data.enforceNoDataAvail();
              handleBluetoothActiveDeviceChanged(_arg085, _arg134, _arg214);
              reply.writeNoException();
              return true;
            case 123:
              AudioFocusInfo _arg086 =
                  (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
              int _arg135 = data.readInt();
              IAudioPolicyCallback _arg215 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setFocusRequestResultFromExtPolicy(_arg086, _arg135, _arg215);
              return true;
            case 124:
              IAudioServerStateDispatcher _arg087 =
                  IAudioServerStateDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerAudioServerStateDispatcher(_arg087);
              reply.writeNoException();
              return true;
            case 125:
              IAudioServerStateDispatcher _arg088 =
                  IAudioServerStateDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterAudioServerStateDispatcher(_arg088);
              return true;
            case 126:
              boolean _result62 = isAudioServerRunning();
              reply.writeNoException();
              reply.writeBoolean(_result62);
              return true;
            case 127:
              IAudioPolicyCallback _arg089 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              int _arg136 = data.readInt();
              int[] _arg216 = data.createIntArray();
              String[] _arg37 = data.createStringArray();
              data.enforceNoDataAvail();
              int _result63 = setUidDeviceAffinity(_arg089, _arg136, _arg216, _arg37);
              reply.writeNoException();
              reply.writeInt(_result63);
              return true;
            case 128:
              IAudioPolicyCallback _arg090 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              int _arg137 = data.readInt();
              data.enforceNoDataAvail();
              int _result64 = removeUidDeviceAffinity(_arg090, _arg137);
              reply.writeNoException();
              reply.writeInt(_result64);
              return true;
            case 129:
              IAudioPolicyCallback _arg091 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              int _arg138 = data.readInt();
              int[] _arg217 = data.createIntArray();
              String[] _arg38 = data.createStringArray();
              data.enforceNoDataAvail();
              int _result65 = setUserIdDeviceAffinity(_arg091, _arg138, _arg217, _arg38);
              reply.writeNoException();
              reply.writeInt(_result65);
              return true;
            case 130:
              IAudioPolicyCallback _arg092 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              int _arg139 = data.readInt();
              data.enforceNoDataAvail();
              int _result66 = removeUserIdDeviceAffinity(_arg092, _arg139);
              reply.writeNoException();
              reply.writeInt(_result66);
              return true;
            case 131:
              Uri _arg093 = (Uri) data.readTypedObject(Uri.CREATOR);
              data.enforceNoDataAvail();
              boolean _result67 = hasHapticChannels(_arg093);
              reply.writeNoException();
              reply.writeBoolean(_result67);
              return true;
            case 132:
              boolean _result68 = isCallScreeningModeSupported();
              reply.writeNoException();
              reply.writeBoolean(_result68);
              return true;
            case 133:
              int _arg094 = data.readInt();
              List<AudioDeviceAttributes> _arg140 =
                  data.createTypedArrayList(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result69 = setPreferredDevicesForStrategy(_arg094, _arg140);
              reply.writeNoException();
              reply.writeInt(_result69);
              return true;
            case 134:
              int _arg095 = data.readInt();
              data.enforceNoDataAvail();
              int _result70 = removePreferredDevicesForStrategy(_arg095);
              reply.writeNoException();
              reply.writeInt(_result70);
              return true;
            case 135:
              int _arg096 = data.readInt();
              data.enforceNoDataAvail();
              List<AudioDeviceAttributes> _result71 = getPreferredDevicesForStrategy(_arg096);
              reply.writeNoException();
              reply.writeTypedList(_result71, 1);
              return true;
            case 136:
              int _arg097 = data.readInt();
              AudioDeviceAttributes _arg141 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result72 = setDeviceAsNonDefaultForStrategy(_arg097, _arg141);
              reply.writeNoException();
              reply.writeInt(_result72);
              return true;
            case 137:
              int _arg098 = data.readInt();
              AudioDeviceAttributes _arg142 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result73 = removeDeviceAsNonDefaultForStrategy(_arg098, _arg142);
              reply.writeNoException();
              reply.writeInt(_result73);
              return true;
            case 138:
              int _arg099 = data.readInt();
              data.enforceNoDataAvail();
              List<AudioDeviceAttributes> _result74 = getNonDefaultDevicesForStrategy(_arg099);
              reply.writeNoException();
              reply.writeTypedList(_result74, 1);
              return true;
            case 139:
              AudioAttributes _arg0100 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              data.enforceNoDataAvail();
              List<AudioDeviceAttributes> _result75 = getDevicesForAttributes(_arg0100);
              reply.writeNoException();
              reply.writeTypedList(_result75, 1);
              return true;
            case 140:
              AudioAttributes _arg0101 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              data.enforceNoDataAvail();
              List<AudioDeviceAttributes> _result76 = getDevicesForAttributesUnprotected(_arg0101);
              reply.writeNoException();
              reply.writeTypedList(_result76, 1);
              return true;
            case 141:
              AudioAttributes _arg0102 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              IDevicesForAttributesCallback _arg143 =
                  IDevicesForAttributesCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              addOnDevicesForAttributesChangedListener(_arg0102, _arg143);
              reply.writeNoException();
              return true;
            case 142:
              IDevicesForAttributesCallback _arg0103 =
                  IDevicesForAttributesCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              removeOnDevicesForAttributesChangedListener(_arg0103);
              return true;
            case 143:
              int _arg0104 = data.readInt();
              data.enforceNoDataAvail();
              int _result77 = setAllowedCapturePolicy(_arg0104);
              reply.writeNoException();
              reply.writeInt(_result77);
              return true;
            case 144:
              int _result78 = getAllowedCapturePolicy();
              reply.writeNoException();
              reply.writeInt(_result78);
              return true;
            case 145:
              IStrategyPreferredDevicesDispatcher _arg0105 =
                  IStrategyPreferredDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerStrategyPreferredDevicesDispatcher(_arg0105);
              reply.writeNoException();
              return true;
            case 146:
              IStrategyPreferredDevicesDispatcher _arg0106 =
                  IStrategyPreferredDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterStrategyPreferredDevicesDispatcher(_arg0106);
              return true;
            case 147:
              IStrategyNonDefaultDevicesDispatcher _arg0107 =
                  IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerStrategyNonDefaultDevicesDispatcher(_arg0107);
              reply.writeNoException();
              return true;
            case 148:
              IStrategyNonDefaultDevicesDispatcher _arg0108 =
                  IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterStrategyNonDefaultDevicesDispatcher(_arg0108);
              return true;
            case 149:
              boolean _arg0109 = data.readBoolean();
              data.enforceNoDataAvail();
              setRttEnabled(_arg0109);
              return true;
            case 150:
              AudioDeviceAttributes _arg0110 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              int _arg144 = data.readInt();
              String _arg218 = data.readString();
              data.enforceNoDataAvail();
              setDeviceVolumeBehavior(_arg0110, _arg144, _arg218);
              reply.writeNoException();
              return true;
            case 151:
              AudioDeviceAttributes _arg0111 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result79 = getDeviceVolumeBehavior(_arg0111);
              reply.writeNoException();
              reply.writeInt(_result79);
              return true;
            case 152:
              boolean _arg0112 = data.readBoolean();
              data.enforceNoDataAvail();
              setMultiAudioFocusEnabled(_arg0112);
              return true;
            case 153:
              int _arg0113 = data.readInt();
              List<AudioDeviceAttributes> _arg145 =
                  data.createTypedArrayList(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result80 = setPreferredDevicesForCapturePreset(_arg0113, _arg145);
              reply.writeNoException();
              reply.writeInt(_result80);
              return true;
            case 154:
              int _arg0114 = data.readInt();
              data.enforceNoDataAvail();
              int _result81 = clearPreferredDevicesForCapturePreset(_arg0114);
              reply.writeNoException();
              reply.writeInt(_result81);
              return true;
            case 155:
              int _arg0115 = data.readInt();
              data.enforceNoDataAvail();
              List<AudioDeviceAttributes> _result82 = getPreferredDevicesForCapturePreset(_arg0115);
              reply.writeNoException();
              reply.writeTypedList(_result82, 1);
              return true;
            case 156:
              ICapturePresetDevicesRoleDispatcher _arg0116 =
                  ICapturePresetDevicesRoleDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerCapturePresetDevicesRoleDispatcher(_arg0116);
              reply.writeNoException();
              return true;
            case 157:
              ICapturePresetDevicesRoleDispatcher _arg0117 =
                  ICapturePresetDevicesRoleDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterCapturePresetDevicesRoleDispatcher(_arg0117);
              return true;
            case 158:
              return onTransact$adjustStreamVolumeForUid$(data, reply);
            case 159:
              return onTransact$adjustSuggestedStreamVolumeForUid$(data, reply);
            case 160:
              return onTransact$setStreamVolumeForUid$(data, reply);
            case 161:
              boolean _arg0118 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result83 = isMusicActive(_arg0118);
              reply.writeNoException();
              reply.writeBoolean(_result83);
              return true;
            case 162:
              int _arg0119 = data.readInt();
              data.enforceNoDataAvail();
              int _result84 = getDeviceMaskForStream(_arg0119);
              reply.writeNoException();
              reply.writeInt(_result84);
              return true;
            case 163:
              int[] _result85 = getAvailableCommunicationDeviceIds();
              reply.writeNoException();
              reply.writeIntArray(_result85);
              return true;
            case 164:
              IBinder _arg0120 = data.readStrongBinder();
              int _arg146 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result86 = setCommunicationDevice(_arg0120, _arg146);
              reply.writeNoException();
              reply.writeBoolean(_result86);
              return true;
            case 165:
              int _result87 = getCommunicationDevice();
              reply.writeNoException();
              reply.writeInt(_result87);
              return true;
            case 166:
              ICommunicationDeviceDispatcher _arg0121 =
                  ICommunicationDeviceDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerCommunicationDeviceDispatcher(_arg0121);
              reply.writeNoException();
              return true;
            case 167:
              ICommunicationDeviceDispatcher _arg0122 =
                  ICommunicationDeviceDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterCommunicationDeviceDispatcher(_arg0122);
              return true;
            case 168:
              boolean _result88 = areNavigationRepeatSoundEffectsEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result88);
              return true;
            case 169:
              boolean _arg0123 = data.readBoolean();
              data.enforceNoDataAvail();
              setNavigationRepeatSoundEffectsEnabled(_arg0123);
              return true;
            case 170:
              boolean _result89 = isHomeSoundEffectEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result89);
              return true;
            case 171:
              boolean _arg0124 = data.readBoolean();
              data.enforceNoDataAvail();
              setHomeSoundEffectEnabled(_arg0124);
              return true;
            case 172:
              AudioDeviceAttributes _arg0125 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              long _arg147 = data.readLong();
              data.enforceNoDataAvail();
              boolean _result90 = setAdditionalOutputDeviceDelay(_arg0125, _arg147);
              reply.writeNoException();
              reply.writeBoolean(_result90);
              return true;
            case 173:
              AudioDeviceAttributes _arg0126 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              long _result91 = getAdditionalOutputDeviceDelay(_arg0126);
              reply.writeNoException();
              reply.writeLong(_result91);
              return true;
            case 174:
              AudioDeviceAttributes _arg0127 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              long _result92 = getMaxAdditionalOutputDeviceDelay(_arg0127);
              reply.writeNoException();
              reply.writeLong(_result92);
              return true;
            case 175:
              return onTransact$requestAudioFocusForTest$(data, reply);
            case 176:
              IAudioFocusDispatcher _arg0128 =
                  IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
              String _arg148 = data.readString();
              AudioAttributes _arg219 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              String _arg39 = data.readString();
              data.enforceNoDataAvail();
              int _result93 = abandonAudioFocusForTest(_arg0128, _arg148, _arg219, _arg39);
              reply.writeNoException();
              reply.writeInt(_result93);
              return true;
            case 177:
              AudioAttributes _arg0129 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              data.enforceNoDataAvail();
              long _result94 = getFadeOutDurationOnFocusLossMillis(_arg0129);
              reply.writeNoException();
              reply.writeLong(_result94);
              return true;
            case 178:
              IAudioModeDispatcher _arg0130 =
                  IAudioModeDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerModeDispatcher(_arg0130);
              reply.writeNoException();
              return true;
            case 179:
              IAudioModeDispatcher _arg0131 =
                  IAudioModeDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterModeDispatcher(_arg0131);
              return true;
            case 180:
              int _result95 = getSpatializerImmersiveAudioLevel();
              reply.writeNoException();
              reply.writeInt(_result95);
              return true;
            case 181:
              boolean _result96 = isSpatializerEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result96);
              return true;
            case 182:
              boolean _result97 = isSpatializerAvailable();
              reply.writeNoException();
              reply.writeBoolean(_result97);
              return true;
            case 183:
              AudioDeviceAttributes _arg0132 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              boolean _result98 = isSpatializerAvailableForDevice(_arg0132);
              reply.writeNoException();
              reply.writeBoolean(_result98);
              return true;
            case 184:
              AudioDeviceAttributes _arg0133 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              boolean _result99 = hasHeadTracker(_arg0133);
              reply.writeNoException();
              reply.writeBoolean(_result99);
              return true;
            case 185:
              boolean _arg0134 = data.readBoolean();
              AudioDeviceAttributes _arg149 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              setHeadTrackerEnabled(_arg0134, _arg149);
              reply.writeNoException();
              return true;
            case 186:
              AudioDeviceAttributes _arg0135 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              boolean _result100 = isHeadTrackerEnabled(_arg0135);
              reply.writeNoException();
              reply.writeBoolean(_result100);
              return true;
            case 187:
              boolean _result101 = isHeadTrackerAvailable();
              reply.writeNoException();
              reply.writeBoolean(_result101);
              return true;
            case 188:
              ISpatializerHeadTrackerAvailableCallback _arg0136 =
                  ISpatializerHeadTrackerAvailableCallback.Stub.asInterface(
                      data.readStrongBinder());
              boolean _arg150 = data.readBoolean();
              data.enforceNoDataAvail();
              registerSpatializerHeadTrackerAvailableCallback(_arg0136, _arg150);
              reply.writeNoException();
              return true;
            case 189:
              boolean _arg0137 = data.readBoolean();
              data.enforceNoDataAvail();
              setSpatializerEnabled(_arg0137);
              reply.writeNoException();
              return true;
            case 190:
              AudioAttributes _arg0138 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              AudioFormat _arg151 = (AudioFormat) data.readTypedObject(AudioFormat.CREATOR);
              data.enforceNoDataAvail();
              boolean _result102 = canBeSpatialized(_arg0138, _arg151);
              reply.writeNoException();
              reply.writeBoolean(_result102);
              return true;
            case 191:
              ISpatializerCallback _arg0139 =
                  ISpatializerCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerSpatializerCallback(_arg0139);
              reply.writeNoException();
              return true;
            case 192:
              ISpatializerCallback _arg0140 =
                  ISpatializerCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterSpatializerCallback(_arg0140);
              reply.writeNoException();
              return true;
            case 193:
              ISpatializerHeadTrackingModeCallback _arg0141 =
                  ISpatializerHeadTrackingModeCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerSpatializerHeadTrackingCallback(_arg0141);
              reply.writeNoException();
              return true;
            case 194:
              ISpatializerHeadTrackingModeCallback _arg0142 =
                  ISpatializerHeadTrackingModeCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterSpatializerHeadTrackingCallback(_arg0142);
              reply.writeNoException();
              return true;
            case 195:
              ISpatializerHeadToSoundStagePoseCallback _arg0143 =
                  ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(
                      data.readStrongBinder());
              data.enforceNoDataAvail();
              registerHeadToSoundstagePoseCallback(_arg0143);
              reply.writeNoException();
              return true;
            case 196:
              ISpatializerHeadToSoundStagePoseCallback _arg0144 =
                  ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(
                      data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterHeadToSoundstagePoseCallback(_arg0144);
              reply.writeNoException();
              return true;
            case 197:
              List<AudioDeviceAttributes> _result103 = getSpatializerCompatibleAudioDevices();
              reply.writeNoException();
              reply.writeTypedList(_result103, 1);
              return true;
            case 198:
              AudioDeviceAttributes _arg0145 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              addSpatializerCompatibleAudioDevice(_arg0145);
              reply.writeNoException();
              return true;
            case 199:
              AudioDeviceAttributes _arg0146 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              removeSpatializerCompatibleAudioDevice(_arg0146);
              reply.writeNoException();
              return true;
            case 200:
              int _arg0147 = data.readInt();
              data.enforceNoDataAvail();
              setDesiredHeadTrackingMode(_arg0147);
              reply.writeNoException();
              return true;
            case 201:
              int _result104 = getDesiredHeadTrackingMode();
              reply.writeNoException();
              reply.writeInt(_result104);
              return true;
            case 202:
              int[] _result105 = getSupportedHeadTrackingModes();
              reply.writeNoException();
              reply.writeIntArray(_result105);
              return true;
            case 203:
              int _result106 = getActualHeadTrackingMode();
              reply.writeNoException();
              reply.writeInt(_result106);
              return true;
            case 204:
              float[] _arg0148 = data.createFloatArray();
              data.enforceNoDataAvail();
              setSpatializerGlobalTransform(_arg0148);
              return true;
            case 205:
              recenterHeadTracker();
              return true;
            case 206:
              int _arg0149 = data.readInt();
              byte[] _arg152 = data.createByteArray();
              data.enforceNoDataAvail();
              setSpatializerParameter(_arg0149, _arg152);
              reply.writeNoException();
              return true;
            case 207:
              int _arg0150 = data.readInt();
              byte[] _arg153 = data.createByteArray();
              data.enforceNoDataAvail();
              getSpatializerParameter(_arg0150, _arg153);
              reply.writeNoException();
              reply.writeByteArray(_arg153);
              return true;
            case 208:
              int _result107 = getSpatializerOutput();
              reply.writeNoException();
              reply.writeInt(_result107);
              return true;
            case 209:
              ISpatializerOutputCallback _arg0151 =
                  ISpatializerOutputCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerSpatializerOutputCallback(_arg0151);
              reply.writeNoException();
              return true;
            case 210:
              ISpatializerOutputCallback _arg0152 =
                  ISpatializerOutputCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterSpatializerOutputCallback(_arg0152);
              reply.writeNoException();
              return true;
            case 211:
              boolean _result108 = isVolumeFixed();
              reply.writeNoException();
              reply.writeBoolean(_result108);
              return true;
            case 212:
              VolumeInfo _result109 = getDefaultVolumeInfo();
              reply.writeNoException();
              reply.writeTypedObject(_result109, 1);
              return true;
            case 213:
              boolean _result110 = isPstnCallAudioInterceptable();
              reply.writeNoException();
              reply.writeBoolean(_result110);
              return true;
            case 214:
              int[] _arg0153 = data.createIntArray();
              AudioDeviceAttributes _arg154 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              long _arg220 = data.readLong();
              data.enforceNoDataAvail();
              muteAwaitConnection(_arg0153, _arg154, _arg220);
              return true;
            case 215:
              AudioDeviceAttributes _arg0154 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              data.enforceNoDataAvail();
              cancelMuteAwaitConnection(_arg0154);
              return true;
            case 216:
              AudioDeviceAttributes _result111 = getMutingExpectedDevice();
              reply.writeNoException();
              reply.writeTypedObject(_result111, 1);
              return true;
            case 217:
              IMuteAwaitConnectionCallback _arg0155 =
                  IMuteAwaitConnectionCallback.Stub.asInterface(data.readStrongBinder());
              boolean _arg155 = data.readBoolean();
              data.enforceNoDataAvail();
              registerMuteAwaitConnectionDispatcher(_arg0155, _arg155);
              reply.writeNoException();
              return true;
            case 218:
              AudioDeviceAttributes _arg0156 =
                  (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
              boolean _arg156 = data.readBoolean();
              data.enforceNoDataAvail();
              setTestDeviceConnectionState(_arg0156, _arg156);
              reply.writeNoException();
              return true;
            case 219:
              boolean _arg0157 = data.readBoolean();
              IDeviceVolumeBehaviorDispatcher _arg157 =
                  IDeviceVolumeBehaviorDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerDeviceVolumeBehaviorDispatcher(_arg0157, _arg157);
              reply.writeNoException();
              return true;
            case 220:
              List<AudioFocusInfo> _result112 = getFocusStack();
              reply.writeNoException();
              reply.writeTypedList(_result112, 1);
              return true;
            case 221:
              AudioFocusInfo _arg0158 =
                  (AudioFocusInfo) data.readTypedObject(AudioFocusInfo.CREATOR);
              IAudioPolicyCallback _arg158 =
                  IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result113 = sendFocusLoss(_arg0158, _arg158);
              reply.writeNoException();
              reply.writeBoolean(_result113);
              return true;
            case 222:
              int[] _arg0159 = data.createIntArray();
              data.enforceNoDataAvail();
              addAssistantServicesUids(_arg0159);
              reply.writeNoException();
              return true;
            case 223:
              int[] _arg0160 = data.createIntArray();
              data.enforceNoDataAvail();
              removeAssistantServicesUids(_arg0160);
              reply.writeNoException();
              return true;
            case 224:
              int[] _arg0161 = data.createIntArray();
              data.enforceNoDataAvail();
              setActiveAssistantServiceUids(_arg0161);
              reply.writeNoException();
              return true;
            case 225:
              int[] _result114 = getAssistantServicesUids();
              reply.writeNoException();
              reply.writeIntArray(_result114);
              return true;
            case 226:
              int[] _result115 = getActiveAssistantServiceUids();
              reply.writeNoException();
              reply.writeIntArray(_result115);
              return true;
            case 227:
              return onTransact$registerDeviceVolumeDispatcherForAbsoluteVolume$(data, reply);
            case 228:
              AudioHalVersionInfo _result116 = getHalVersion();
              reply.writeNoException();
              reply.writeTypedObject(_result116, 1);
              return true;
            case 229:
              AudioAttributes _arg0162 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              int _arg159 = data.readInt();
              AudioMixerAttributes _arg221 =
                  (AudioMixerAttributes) data.readTypedObject(AudioMixerAttributes.CREATOR);
              data.enforceNoDataAvail();
              int _result117 = setPreferredMixerAttributes(_arg0162, _arg159, _arg221);
              reply.writeNoException();
              reply.writeInt(_result117);
              return true;
            case 230:
              AudioAttributes _arg0163 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              int _arg160 = data.readInt();
              data.enforceNoDataAvail();
              int _result118 = clearPreferredMixerAttributes(_arg0163, _arg160);
              reply.writeNoException();
              reply.writeInt(_result118);
              return true;
            case 231:
              IPreferredMixerAttributesDispatcher _arg0164 =
                  IPreferredMixerAttributesDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerPreferredMixerAttributesDispatcher(_arg0164);
              reply.writeNoException();
              return true;
            case 232:
              IPreferredMixerAttributesDispatcher _arg0165 =
                  IPreferredMixerAttributesDispatcher.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterPreferredMixerAttributesDispatcher(_arg0165);
              return true;
            case 233:
              boolean _result119 = supportsBluetoothVariableLatency();
              reply.writeNoException();
              reply.writeBoolean(_result119);
              return true;
            case 234:
              boolean _arg0166 = data.readBoolean();
              data.enforceNoDataAvail();
              setBluetoothVariableLatencyEnabled(_arg0166);
              reply.writeNoException();
              return true;
            case 235:
              boolean _result120 = isBluetoothVariableLatencyEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result120);
              return true;
            case 236:
              String _arg0167 = data.readString();
              data.enforceNoDataAvail();
              setAudioServiceConfig(_arg0167);
              reply.writeNoException();
              return true;
            case 237:
              String _arg0168 = data.readString();
              data.enforceNoDataAvail();
              String _result121 = getAudioServiceConfig(_arg0168);
              reply.writeNoException();
              reply.writeString(_result121);
              return true;
            case 238:
              boolean _result122 = shouldShowRingtoneVolume();
              reply.writeNoException();
              reply.writeBoolean(_result122);
              return true;
            case 239:
              int _arg0169 = data.readInt();
              data.enforceNoDataAvail();
              int _result123 = secGetActiveStreamType(_arg0169);
              reply.writeNoException();
              reply.writeInt(_result123);
              return true;
            case 240:
              int _arg0170 = data.readInt();
              data.enforceNoDataAvail();
              int _result124 = getUidForDevice(_arg0170);
              reply.writeNoException();
              reply.writeInt(_result124);
              return true;
            case 241:
              int _arg0171 = data.readInt();
              int _arg161 = data.readInt();
              boolean _arg222 = data.readBoolean();
              data.enforceNoDataAvail();
              setAppDevice(_arg0171, _arg161, _arg222);
              reply.writeNoException();
              return true;
            case 242:
              int _arg0172 = data.readInt();
              data.enforceNoDataAvail();
              int _result125 = getAppDevice(_arg0172);
              reply.writeNoException();
              reply.writeInt(_result125);
              return true;
            case 243:
              int _arg0173 = data.readInt();
              int _arg162 = data.readInt();
              String _arg223 = data.readString();
              data.enforceNoDataAvail();
              setAppVolume(_arg0173, _arg162, _arg223);
              reply.writeNoException();
              return true;
            case 244:
              int _arg0174 = data.readInt();
              data.enforceNoDataAvail();
              int _result126 = getAppVolume(_arg0174);
              reply.writeNoException();
              reply.writeInt(_result126);
              return true;
            case 245:
              int _arg0175 = data.readInt();
              boolean _arg163 = data.readBoolean();
              String _arg224 = data.readString();
              data.enforceNoDataAvail();
              setAppMute(_arg0175, _arg163, _arg224);
              reply.writeNoException();
              return true;
            case 246:
              int _arg0176 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result127 = isAppMute(_arg0176);
              reply.writeNoException();
              reply.writeBoolean(_result127);
              return true;
            case 247:
              boolean _arg0177 = data.readBoolean();
              boolean _arg164 = data.readBoolean();
              data.enforceNoDataAvail();
              setMultiSoundOn(_arg0177, _arg164);
              reply.writeNoException();
              return true;
            case 248:
              boolean _result128 = isMultiSoundOn();
              reply.writeNoException();
              reply.writeBoolean(_result128);
              return true;
            case 249:
              return onTransact$setStreamVolumeForDeviceWithAttribution$(data, reply);
            case 250:
              int _arg0178 = data.readInt();
              int _arg165 = data.readInt();
              data.enforceNoDataAvail();
              int _result129 = getStreamVolumeForDevice(_arg0178, _arg165);
              reply.writeNoException();
              reply.writeInt(_result129);
              return true;
            case 251:
              int _arg0179 = data.readInt();
              data.enforceNoDataAvail();
              String _result130 = getPinAppInfo(_arg0179);
              reply.writeNoException();
              reply.writeString(_result130);
              return true;
            case 252:
              int _result131 = getPinDevice();
              reply.writeNoException();
              reply.writeInt(_result131);
              return true;
            case 253:
              String[] _result132 = getSelectedAppList();
              reply.writeNoException();
              reply.writeStringArray(_result132);
              return true;
            case 254:
              int _arg0180 = data.readInt();
              String _arg166 = data.readString();
              data.enforceNoDataAvail();
              addPackage(_arg0180, _arg166);
              reply.writeNoException();
              return true;
            case 255:
              String _arg0181 = data.readString();
              data.enforceNoDataAvail();
              removePackageForName(_arg0181);
              reply.writeNoException();
              return true;
            case 256:
              String _arg0182 = data.readString();
              data.enforceNoDataAvail();
              boolean _result133 = isAlreadyInDB(_arg0182);
              reply.writeNoException();
              reply.writeBoolean(_result133);
              return true;
            case 257:
              String _arg0183 = data.readString();
              data.enforceNoDataAvail();
              boolean _result134 = isInAllowedList(_arg0183);
              reply.writeNoException();
              reply.writeBoolean(_result134);
              return true;
            case 258:
              return onTransact$setFineVolume$(data, reply);
            case 259:
              int _arg0184 = data.readInt();
              int _arg167 = data.readInt();
              data.enforceNoDataAvail();
              int _result135 = getFineVolume(_arg0184, _arg167);
              reply.writeNoException();
              reply.writeInt(_result135);
              return true;
            case 260:
              boolean _arg0185 = data.readBoolean();
              data.enforceNoDataAvail();
              setForceSpeakerOn(_arg0185);
              reply.writeNoException();
              return true;
            case 261:
              boolean _result136 = isForceSpeakerOn();
              reply.writeNoException();
              reply.writeBoolean(_result136);
              return true;
            case 262:
              int _arg0186 = data.readInt();
              String _arg168 = data.readString();
              boolean _arg225 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result137 = setDeviceToForceByUser(_arg0186, _arg168, _arg225);
              reply.writeNoException();
              reply.writeInt(_result137);
              return true;
            case 263:
              int _arg0187 = data.readInt();
              String _arg169 = data.readString();
              data.enforceNoDataAvail();
              setMuteInterval(_arg0187, _arg169);
              reply.writeNoException();
              return true;
            case 264:
              int _result138 = getMuteInterval();
              reply.writeNoException();
              reply.writeInt(_result138);
              return true;
            case 265:
              int _result139 = getRemainingMuteIntervalMs();
              reply.writeNoException();
              reply.writeInt(_result139);
              return true;
            case 266:
              int _result140 = getPrevRingerMode();
              reply.writeNoException();
              reply.writeInt(_result140);
              return true;
            case 267:
              int _arg0188 = data.readInt();
              PendingIntent _arg170 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              data.enforceNoDataAvail();
              setSoundSettingEventBroadcastIntent(_arg0188, _arg170);
              return true;
            case 268:
              int[] _arg0189 = data.createIntArray();
              data.enforceNoDataAvail();
              boolean _result141 = setMediaVolumeSteps(_arg0189);
              reply.writeNoException();
              reply.writeBoolean(_result141);
              return true;
            case 269:
              int[] _result142 = getMediaVolumeSteps();
              reply.writeNoException();
              reply.writeIntArray(_result142);
              return true;
            case 270:
              int _arg0190 = data.readInt();
              data.enforceNoDataAvail();
              setRadioOutputPath(_arg0190);
              reply.writeNoException();
              return true;
            case 271:
              int _result143 = getRadioOutputPath();
              reply.writeNoException();
              reply.writeInt(_result143);
              return true;
            case 272:
              dismissVolumePanel();
              reply.writeNoException();
              return true;
            case 273:
              String _result144 = getCurrentAudioFocusPackageName();
              reply.writeNoException();
              reply.writeString(_result144);
              return true;
            case 274:
              int _arg0191 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result145 = isUsingAudio(_arg0191);
              reply.writeNoException();
              reply.writeBoolean(_result145);
              return true;
            case 275:
              return onTransact$setA2dpDeviceVolume$(data, reply);
            case 276:
              BluetoothDevice _arg0192 =
                  (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
              int _arg171 = data.readInt();
              data.enforceNoDataAvail();
              int _result146 = getA2dpDeviceVolume(_arg0192, _arg171);
              reply.writeNoException();
              reply.writeInt(_result146);
              return true;
            case 277:
              float[] _result147 = getFloatVolumeTable();
              reply.writeNoException();
              reply.writeFloatArray(_result147);
              return true;
            case 278:
              boolean _arg0193 = data.readBoolean();
              data.enforceNoDataAvail();
              setRemoteMic(_arg0193);
              reply.writeNoException();
              return true;
            case 279:
              String _arg0194 = data.readString();
              data.enforceNoDataAvail();
              recordRingtoneChanger(_arg0194);
              return true;
            case 280:
              IPlaybackConfigDispatcher _arg0195 =
                  IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
              String _arg172 = data.readString();
              data.enforceNoDataAvail();
              registerPlaybackCallbackWithPackage(_arg0195, _arg172);
              reply.writeNoException();
              return true;
            case 281:
              int _arg0196 = data.readInt();
              data.enforceNoDataAvail();
              setBtOffloadEnable(_arg0196);
              reply.writeNoException();
              return true;
            case 282:
              boolean _result148 = isSafeMediaVolumeStateActive();
              reply.writeNoException();
              reply.writeBoolean(_result148);
              return true;
            case 283:
              int _arg0197 = data.readInt();
              data.enforceNoDataAvail();
              List<String> _result149 = getExcludedRingtoneTitles(_arg0197);
              reply.writeNoException();
              reply.writeStringList(_result149);
              return true;
            case 284:
              IVolumeController _arg0198 =
                  IVolumeController.Stub.asInterface(data.readStrongBinder());
              boolean _arg173 = data.readBoolean();
              data.enforceNoDataAvail();
              notifySafetyVolumeDialogVisible(_arg0198, _arg173);
              return true;
            case 285:
              String _arg0199 = data.readString();
              String _arg174 = data.readString();
              int _arg226 = data.readInt();
              data.enforceNoDataAvail();
              nativeEvent(_arg0199, _arg174, _arg226);
              reply.writeNoException();
              return true;
            case 286:
              int _result150 = getModeInternal();
              reply.writeNoException();
              reply.writeInt(_result150);
              return true;
            case 287:
              int _arg0200 = data.readInt();
              data.enforceNoDataAvail();
              setMicInputControlMode(_arg0200);
              reply.writeNoException();
              return true;
            case 288:
              int _result151 = getMicModeType();
              reply.writeNoException();
              reply.writeInt(_result151);
              return true;
            case 289:
              int _result152 = getEarProtectLimit();
              reply.writeNoException();
              reply.writeInt(_result152);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAudioService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
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
      public void portEvent(int portId, int event, PersistableBundle extras)
          throws RemoteException {
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
      public void adjustStreamVolume(
          int streamType, int direction, int flags, String callingPackage) throws RemoteException {
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
      public void adjustStreamVolumeWithAttribution(
          int streamType, int direction, int flags, String callingPackage, String attributionTag)
          throws RemoteException {
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
      public void setStreamVolume(int streamType, int index, int flags, String callingPackage)
          throws RemoteException {
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
      public void setStreamVolumeWithAttribution(
          int streamType, int index, int flags, String callingPackage, String attributionTag)
          throws RemoteException {
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
      public void setDeviceVolume(VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage)
          throws RemoteException {
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
      public VolumeInfo getDeviceVolume(
          VolumeInfo vi, AudioDeviceAttributes ada, String callingPackage) throws RemoteException {
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
      public void handleVolumeKey(
          KeyEvent event, boolean isOnTv, String callingPackage, String caller)
          throws RemoteException {
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
      public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb)
          throws RemoteException {
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
      public void setMasterMute(
          boolean mute, int flags, String callingPackage, int userId, String attributionTag)
          throws RemoteException {
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
      public List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups()
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          List<android.media.audiopolicy.AudioVolumeGroup> _result =
              _reply.createTypedArrayList(android.media.audiopolicy.AudioVolumeGroup.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setVolumeGroupVolumeIndex(
          int groupId, int index, int flags, String callingPackage, String attributionTag)
          throws RemoteException {
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
      public void adjustVolumeGroupVolume(
          int groupId, int direction, int flags, String callingPackage) throws RemoteException {
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
      public List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies()
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          List<android.media.audiopolicy.AudioProductStrategy> _result =
              _reply.createTypedArrayList(android.media.audiopolicy.AudioProductStrategy.CREATOR);
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
      public void setMicrophoneMute(
          boolean on, String callingPackage, int userId, String attributionTag)
          throws RemoteException {
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
      public boolean setSurroundFormatEnabled(int audioFormat, boolean enabled)
          throws RemoteException {
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
      public int requestAudioFocus(
          AudioAttributes aa,
          int durationHint,
          IBinder cb,
          IAudioFocusDispatcher fd,
          String clientId,
          String callingPackageName,
          String attributionTag,
          int flags,
          IAudioPolicyCallback pcb,
          int sdk)
          throws RemoteException {
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
      public int abandonAudioFocus(
          IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName)
          throws RemoteException {
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
      public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher isad, boolean register)
          throws RemoteException {
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
      public void setWiredDeviceConnectionState(AudioDeviceAttributes aa, int state, String caller)
          throws RemoteException {
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
      public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
          AudioRoutesInfo _result =
              (AudioRoutesInfo) _reply.readTypedObject(AudioRoutesInfo.CREATOR);
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
      public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible)
          throws RemoteException {
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
      public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPackage);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void lowerVolumeToRs1(String callingPackage) throws RemoteException {
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
      public float getOutputRs2UpperBound() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(96, _data, _reply, 0);
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
          this.mRemote.transact(97, _data, null, 1);
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
          this.mRemote.transact(98, _data, _reply, 0);
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
          this.mRemote.transact(99, _data, null, 1);
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
          this.mRemote.transact(100, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(computeCsdOnAllDevices);
          this.mRemote.transact(101, _data, null, 1);
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
          this.mRemote.transact(102, _data, _reply, 0);
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
          this.mRemote.transact(103, _data, _reply, 0);
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
      public String registerAudioPolicy(
          android.media.audiopolicy.AudioPolicyConfig policyConfig,
          IAudioPolicyCallback pcb,
          boolean hasFocusListener,
          boolean isFocusPolicy,
          boolean isTestFocusPolicy,
          boolean isVolumeController,
          IMediaProjection projection)
          throws RemoteException {
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
          this.mRemote.transact(105, _data, _reply, 0);
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
          this.mRemote.transact(106, _data, null, 1);
        } finally {
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
          this.mRemote.transact(107, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int addMixForPolicy(
          android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(policyConfig, 0);
          _data.writeStrongInterface(pcb);
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
      public int removeMixForPolicy(
          android.media.audiopolicy.AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(policyConfig, 0);
          _data.writeStrongInterface(pcb);
          this.mRemote.transact(109, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(duckingBehavior);
          _data.writeStrongInterface(pcb);
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
      public void setVolumePolicy(VolumePolicy policy) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(policy, 0);
          this.mRemote.transact(111, _data, _reply, 0);
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
          this.mRemote.transact(112, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerRecordingCallback(IRecordingConfigDispatcher rcdb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(rcdb);
          this.mRemote.transact(113, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(rcdb);
          this.mRemote.transact(114, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioRecordingConfiguration> getActiveRecordingConfigurations()
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(115, _data, _reply, 0);
          _reply.readException();
          List<AudioRecordingConfiguration> _result =
              _reply.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
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
          this.mRemote.transact(116, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(pcdb);
          this.mRemote.transact(117, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations()
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(118, _data, _reply, 0);
          _reply.readException();
          List<AudioPlaybackConfiguration> _result =
              _reply.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
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
      public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(afi, 0);
          _data.writeInt(focusChange);
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
      public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(piid);
          _data.writeBoolean(hasOpPlayAudio);
          this.mRemote.transact(121, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void handleBluetoothActiveDeviceChanged(
          BluetoothDevice newDevice,
          BluetoothDevice previousDevice,
          BluetoothProfileConnectionInfo info)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(newDevice, 0);
          _data.writeTypedObject(previousDevice, 0);
          _data.writeTypedObject(info, 0);
          this.mRemote.transact(122, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setFocusRequestResultFromExtPolicy(
          AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(afi, 0);
          _data.writeInt(requestResult);
          _data.writeStrongInterface(pcb);
          this.mRemote.transact(123, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(asd);
          this.mRemote.transact(124, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(asd);
          this.mRemote.transact(125, _data, null, 1);
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
          this.mRemote.transact(126, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setUidDeviceAffinity(
          IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(pcb);
          _data.writeInt(uid);
          _data.writeIntArray(deviceTypes);
          _data.writeStringArray(deviceAddresses);
          this.mRemote.transact(127, _data, _reply, 0);
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
          this.mRemote.transact(128, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setUserIdDeviceAffinity(
          IAudioPolicyCallback pcb, int userId, int[] deviceTypes, String[] deviceAddresses)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(pcb);
          _data.writeInt(userId);
          _data.writeIntArray(deviceTypes);
          _data.writeStringArray(deviceAddresses);
          this.mRemote.transact(129, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int removeUserIdDeviceAffinity(IAudioPolicyCallback pcb, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(pcb);
          _data.writeInt(userId);
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
      public boolean hasHapticChannels(Uri uri) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(uri, 0);
          this.mRemote.transact(131, _data, _reply, 0);
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
          this.mRemote.transact(132, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setPreferredDevicesForStrategy(int strategy, List<AudioDeviceAttributes> devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeTypedList(devices, 0);
          this.mRemote.transact(133, _data, _reply, 0);
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
          this.mRemote.transact(134, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int strategy)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(strategy);
          this.mRemote.transact(135, _data, _reply, 0);
          _reply.readException();
          List<AudioDeviceAttributes> _result =
              _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(136, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int removeDeviceAsNonDefaultForStrategy(int strategy, AudioDeviceAttributes device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(137, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int strategy)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(strategy);
          this.mRemote.transact(138, _data, _reply, 0);
          _reply.readException();
          List<AudioDeviceAttributes> _result =
              _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes attributes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(attributes, 0);
          this.mRemote.transact(139, _data, _reply, 0);
          _reply.readException();
          List<AudioDeviceAttributes> _result =
              _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(
          AudioAttributes attributes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(attributes, 0);
          this.mRemote.transact(140, _data, _reply, 0);
          _reply.readException();
          List<AudioDeviceAttributes> _result =
              _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void addOnDevicesForAttributesChangedListener(
          AudioAttributes attributes, IDevicesForAttributesCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(attributes, 0);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(141, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void removeOnDevicesForAttributesChangedListener(
          IDevicesForAttributesCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(142, _data, null, 1);
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
          this.mRemote.transact(143, _data, _reply, 0);
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
          this.mRemote.transact(144, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerStrategyPreferredDevicesDispatcher(
          IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(145, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterStrategyPreferredDevicesDispatcher(
          IStrategyPreferredDevicesDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(146, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerStrategyNonDefaultDevicesDispatcher(
          IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(147, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterStrategyNonDefaultDevicesDispatcher(
          IStrategyNonDefaultDevicesDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(148, _data, null, 1);
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
          this.mRemote.transact(149, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setDeviceVolumeBehavior(
          AudioDeviceAttributes device, int deviceVolumeBehavior, String pkgName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          _data.writeInt(deviceVolumeBehavior);
          _data.writeString(pkgName);
          this.mRemote.transact(150, _data, _reply, 0);
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
          this.mRemote.transact(151, _data, _reply, 0);
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
          this.mRemote.transact(152, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setPreferredDevicesForCapturePreset(
          int capturePreset, List<AudioDeviceAttributes> devices) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(capturePreset);
          _data.writeTypedList(devices, 0);
          this.mRemote.transact(153, _data, _reply, 0);
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
          this.mRemote.transact(154, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int capturePreset)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(capturePreset);
          this.mRemote.transact(155, _data, _reply, 0);
          _reply.readException();
          List<AudioDeviceAttributes> _result =
              _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerCapturePresetDevicesRoleDispatcher(
          ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(156, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterCapturePresetDevicesRoleDispatcher(
          ICapturePresetDevicesRoleDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(157, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void adjustStreamVolumeForUid(
          int streamType,
          int direction,
          int flags,
          String packageName,
          int uid,
          int pid,
          UserHandle userHandle,
          int targetSdkVersion)
          throws RemoteException {
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
          this.mRemote.transact(158, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void adjustSuggestedStreamVolumeForUid(
          int streamType,
          int direction,
          int flags,
          String packageName,
          int uid,
          int pid,
          UserHandle userHandle,
          int targetSdkVersion)
          throws RemoteException {
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
          this.mRemote.transact(159, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setStreamVolumeForUid(
          int streamType,
          int direction,
          int flags,
          String packageName,
          int uid,
          int pid,
          UserHandle userHandle,
          int targetSdkVersion)
          throws RemoteException {
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
          this.mRemote.transact(160, _data, null, 1);
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
          this.mRemote.transact(161, _data, _reply, 0);
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
          this.mRemote.transact(162, _data, _reply, 0);
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
          this.mRemote.transact(163, _data, _reply, 0);
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
          this.mRemote.transact(164, _data, _reply, 0);
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
      public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(166, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher dispatcher)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(167, _data, null, 1);
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
          this.mRemote.transact(168, _data, _reply, 0);
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
          this.mRemote.transact(169, _data, null, 1);
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
          this.mRemote.transact(170, _data, _reply, 0);
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
          this.mRemote.transact(171, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes device, long delayMillis)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          _data.writeLong(delayMillis);
          this.mRemote.transact(172, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(173, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(174, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int requestAudioFocusForTest(
          AudioAttributes aa,
          int durationHint,
          IBinder cb,
          IAudioFocusDispatcher fd,
          String clientId,
          String callingPackageName,
          int flags,
          int uid,
          int sdk)
          throws RemoteException {
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
          this.mRemote.transact(175, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int abandonAudioFocusForTest(
          IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(fd);
          _data.writeString(clientId);
          _data.writeTypedObject(aa, 0);
          _data.writeString(callingPackageName);
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
      public long getFadeOutDurationOnFocusLossMillis(AudioAttributes aa) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(aa, 0);
          this.mRemote.transact(177, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
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
          this.mRemote.transact(178, _data, _reply, 0);
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
          this.mRemote.transact(179, _data, null, 1);
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
          this.mRemote.transact(180, _data, _reply, 0);
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
          this.mRemote.transact(181, _data, _reply, 0);
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
      public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(183, _data, _reply, 0);
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
      public void setHeadTrackerEnabled(boolean enabled, AudioDeviceAttributes device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(185, _data, _reply, 0);
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
      public boolean isHeadTrackerAvailable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(187, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerSpatializerHeadTrackerAvailableCallback(
          ISpatializerHeadTrackerAvailableCallback cb, boolean register) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          _data.writeBoolean(register);
          this.mRemote.transact(188, _data, _reply, 0);
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
          this.mRemote.transact(189, _data, _reply, 0);
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
          this.mRemote.transact(190, _data, _reply, 0);
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
          this.mRemote.transact(191, _data, _reply, 0);
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
          this.mRemote.transact(192, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          this.mRemote.transact(193, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback cb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          this.mRemote.transact(194, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback cb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          this.mRemote.transact(195, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterHeadToSoundstagePoseCallback(
          ISpatializerHeadToSoundStagePoseCallback cb) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          this.mRemote.transact(196, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices()
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(197, _data, _reply, 0);
          _reply.readException();
          List<AudioDeviceAttributes> _result =
              _reply.createTypedArrayList(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(ada, 0);
          this.mRemote.transact(198, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes ada)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(ada, 0);
          this.mRemote.transact(199, _data, _reply, 0);
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
          this.mRemote.transact(200, _data, _reply, 0);
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
          this.mRemote.transact(201, _data, _reply, 0);
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
          this.mRemote.transact(202, _data, _reply, 0);
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
          this.mRemote.transact(203, _data, _reply, 0);
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
          this.mRemote.transact(204, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void recenterHeadTracker() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(205, _data, null, 1);
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
          this.mRemote.transact(206, _data, _reply, 0);
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
          this.mRemote.transact(207, _data, _reply, 0);
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
          this.mRemote.transact(208, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerSpatializerOutputCallback(ISpatializerOutputCallback cb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          this.mRemote.transact(209, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback cb)
          throws RemoteException {
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
      public boolean isVolumeFixed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(211, _data, _reply, 0);
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
          this.mRemote.transact(212, _data, _reply, 0);
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
          this.mRemote.transact(213, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void muteAwaitConnection(int[] usagesToMute, AudioDeviceAttributes dev, long timeOutMs)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeIntArray(usagesToMute);
          _data.writeTypedObject(dev, 0);
          _data.writeLong(timeOutMs);
          this.mRemote.transact(214, _data, null, 1);
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
          this.mRemote.transact(215, _data, null, 1);
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
          this.mRemote.transact(216, _data, _reply, 0);
          _reply.readException();
          AudioDeviceAttributes _result =
              (AudioDeviceAttributes) _reply.readTypedObject(AudioDeviceAttributes.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerMuteAwaitConnectionDispatcher(
          IMuteAwaitConnectionCallback cb, boolean register) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          _data.writeBoolean(register);
          this.mRemote.transact(217, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setTestDeviceConnectionState(AudioDeviceAttributes device, boolean connected)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          _data.writeBoolean(connected);
          this.mRemote.transact(218, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerDeviceVolumeBehaviorDispatcher(
          boolean register, IDeviceVolumeBehaviorDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(register);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(219, _data, _reply, 0);
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
          this.mRemote.transact(220, _data, _reply, 0);
          _reply.readException();
          List<AudioFocusInfo> _result = _reply.createTypedArrayList(AudioFocusInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public boolean sendFocusLoss(AudioFocusInfo focusLoser, IAudioPolicyCallback apcb)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(focusLoser, 0);
          _data.writeStrongInterface(apcb);
          this.mRemote.transact(221, _data, _reply, 0);
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
          this.mRemote.transact(222, _data, _reply, 0);
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
          this.mRemote.transact(223, _data, _reply, 0);
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
          this.mRemote.transact(224, _data, _reply, 0);
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
          this.mRemote.transact(225, _data, _reply, 0);
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
          this.mRemote.transact(226, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerDeviceVolumeDispatcherForAbsoluteVolume(
          boolean register,
          IAudioDeviceVolumeDispatcher cb,
          String packageName,
          AudioDeviceAttributes device,
          List<VolumeInfo> volumes,
          boolean handlesvolumeAdjustment,
          int volumeBehavior)
          throws RemoteException {
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
          this.mRemote.transact(227, _data, _reply, 0);
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
          this.mRemote.transact(228, _data, _reply, 0);
          _reply.readException();
          AudioHalVersionInfo _result =
              (AudioHalVersionInfo) _reply.readTypedObject(AudioHalVersionInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setPreferredMixerAttributes(
          AudioAttributes aa, int portId, AudioMixerAttributes mixerAttributes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(aa, 0);
          _data.writeInt(portId);
          _data.writeTypedObject(mixerAttributes, 0);
          this.mRemote.transact(229, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int clearPreferredMixerAttributes(AudioAttributes aa, int portId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(aa, 0);
          _data.writeInt(portId);
          this.mRemote.transact(230, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerPreferredMixerAttributesDispatcher(
          IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(231, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void unregisterPreferredMixerAttributesDispatcher(
          IPreferredMixerAttributesDispatcher dispatcher) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(dispatcher);
          this.mRemote.transact(232, _data, null, 1);
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
          this.mRemote.transact(233, _data, _reply, 0);
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
          this.mRemote.transact(234, _data, _reply, 0);
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
          this.mRemote.transact(235, _data, _reply, 0);
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
          this.mRemote.transact(236, _data, _reply, 0);
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
          this.mRemote.transact(237, _data, _reply, 0);
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
          this.mRemote.transact(238, _data, _reply, 0);
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
          this.mRemote.transact(239, _data, _reply, 0);
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
          this.mRemote.transact(240, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setAppDevice(int uid, int device, boolean shouldShowNotification)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(device);
          _data.writeBoolean(shouldShowNotification);
          this.mRemote.transact(241, _data, _reply, 0);
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
          this.mRemote.transact(242, _data, _reply, 0);
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
          this.mRemote.transact(243, _data, _reply, 0);
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
          this.mRemote.transact(244, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setAppMute(int uid, boolean shouldMute, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(shouldMute);
          _data.writeString(callingPackage);
          this.mRemote.transact(245, _data, _reply, 0);
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
          this.mRemote.transact(246, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setMultiSoundOn(boolean on, boolean shouldShowNotification)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(on);
          _data.writeBoolean(shouldShowNotification);
          this.mRemote.transact(247, _data, _reply, 0);
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
          this.mRemote.transact(248, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setStreamVolumeForDeviceWithAttribution(
          int streamType,
          int index,
          int flags,
          String callingPackage,
          String attributionTag,
          int device)
          throws RemoteException {
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
          this.mRemote.transact(249, _data, _reply, 0);
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
          this.mRemote.transact(250, _data, _reply, 0);
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
          this.mRemote.transact(251, _data, _reply, 0);
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
          this.mRemote.transact(252, _data, _reply, 0);
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
          this.mRemote.transact(253, _data, _reply, 0);
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
          this.mRemote.transact(254, _data, _reply, 0);
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
          this.mRemote.transact(255, _data, _reply, 0);
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
          this.mRemote.transact(256, _data, _reply, 0);
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
          this.mRemote.transact(257, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setFineVolume(
          int streamType, int index, int flags, int device, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(streamType);
          _data.writeInt(index);
          _data.writeInt(flags);
          _data.writeInt(device);
          _data.writeString(callingPackage);
          this.mRemote.transact(258, _data, _reply, 0);
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
          this.mRemote.transact(259, _data, _reply, 0);
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
          this.mRemote.transact(260, _data, _reply, 0);
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
          this.mRemote.transact(261, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int setDeviceToForceByUser(int device, String address, boolean force)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(device);
          _data.writeString(address);
          _data.writeBoolean(force);
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
      public void setMuteInterval(int interval, String caller) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(interval);
          _data.writeString(caller);
          this.mRemote.transact(263, _data, _reply, 0);
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
          this.mRemote.transact(264, _data, _reply, 0);
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
          this.mRemote.transact(265, _data, _reply, 0);
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
          this.mRemote.transact(266, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setSoundSettingEventBroadcastIntent(int type, PendingIntent broadcastIntent)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeTypedObject(broadcastIntent, 0);
          this.mRemote.transact(267, _data, null, 1);
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
      public int[] getMediaVolumeSteps() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(269, _data, _reply, 0);
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
          this.mRemote.transact(270, _data, _reply, 0);
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
          this.mRemote.transact(271, _data, _reply, 0);
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
          this.mRemote.transact(272, _data, _reply, 0);
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
          this.mRemote.transact(273, _data, _reply, 0);
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
          this.mRemote.transact(274, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void setA2dpDeviceVolume(
          BluetoothDevice device, int streamType, int index, int flags, String caller)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          _data.writeInt(streamType);
          _data.writeInt(index);
          _data.writeInt(flags);
          _data.writeString(caller);
          this.mRemote.transact(275, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int getA2dpDeviceVolume(BluetoothDevice device, int streamType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          _data.writeInt(streamType);
          this.mRemote.transact(276, _data, _reply, 0);
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
          this.mRemote.transact(277, _data, _reply, 0);
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
          this.mRemote.transact(278, _data, _reply, 0);
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
          this.mRemote.transact(279, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void registerPlaybackCallbackWithPackage(
          IPlaybackConfigDispatcher pcdb, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(pcdb);
          _data.writeString(packageName);
          this.mRemote.transact(280, _data, _reply, 0);
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
          this.mRemote.transact(281, _data, _reply, 0);
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
          this.mRemote.transact(282, _data, _reply, 0);
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
          this.mRemote.transact(283, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void notifySafetyVolumeDialogVisible(IVolumeController controller, boolean visible)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(controller);
          _data.writeBoolean(visible);
          this.mRemote.transact(284, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public void nativeEvent(String action, String key, int value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(action);
          _data.writeString(key);
          _data.writeInt(value);
          this.mRemote.transact(285, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioService
      public int getModeInternal() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(286, _data, _reply, 0);
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
          this.mRemote.transact(287, _data, _reply, 0);
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
          this.mRemote.transact(288, _data, _reply, 0);
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
          this.mRemote.transact(289, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    private boolean onTransact$adjustStreamVolumeWithAttribution$(Parcel data, Parcel reply)
        throws RemoteException {
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

    private boolean onTransact$setStreamVolumeWithAttribution$(Parcel data, Parcel reply)
        throws RemoteException {
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

    protected void setDeviceVolume_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_setDeviceVolume, getCallingPid(), getCallingUid());
    }

    protected void getDeviceVolume_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_getDeviceVolume, getCallingPid(), getCallingUid());
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
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getAudioVolumeGroups_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    private boolean onTransact$setVolumeGroupVolumeIndex$(Parcel data, Parcel reply)
        throws RemoteException {
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
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_setVolumeGroupVolumeIndex, getCallingPid(), getCallingUid());
    }

    protected void getVolumeGroupVolumeIndex_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_getVolumeGroupVolumeIndex, getCallingPid(), getCallingUid());
    }

    protected void getVolumeGroupMaxVolumeIndex_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_getVolumeGroupMaxVolumeIndex, getCallingPid(), getCallingUid());
    }

    protected void getVolumeGroupMinVolumeIndex_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_getVolumeGroupMinVolumeIndex, getCallingPid(), getCallingUid());
    }

    protected void getLastAudibleVolumeForVolumeGroup_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
    }

    protected void getLastAudibleStreamVolume_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
    }

    protected void setSupportedSystemUsages_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getSupportedSystemUsages_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getAudioProductStrategies_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void isUltrasoundSupported_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.ACCESS_ULTRASOUND, getCallingPid(), getCallingUid());
    }

    protected void isHotwordStreamSupported_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.CAPTURE_AUDIO_HOTWORD, getCallingPid(), getCallingUid());
    }

    protected void setA2dpSuspended_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
    }

    protected void setLeAudioSuspended_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
    }

    private boolean onTransact$requestAudioFocus$(Parcel data, Parcel reply)
        throws RemoteException {
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
      int _result =
          requestAudioFocus(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
      reply.writeNoException();
      reply.writeInt(_result);
      return true;
    }

    protected void getIndependentStreamTypes_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void getStreamTypeAlias_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void isVolumeControlUsingVolumeGroups_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void registerStreamAliasingDispatcher_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void setNotifAliasRingForTest_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void setWiredDeviceConnectionState_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getOutputRs2UpperBound_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void setOutputRs2UpperBound_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void getCsd_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void setCsd_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void forceUseFrameworkMel_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void forceComputeCsdOnAllDevices_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    protected void isCsdEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
    }

    private boolean onTransact$registerAudioPolicy$(Parcel data, Parcel reply)
        throws RemoteException {
      android.media.audiopolicy.AudioPolicyConfig _arg0 =
          (android.media.audiopolicy.AudioPolicyConfig)
              data.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
      IAudioPolicyCallback _arg1 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
      boolean _arg2 = data.readBoolean();
      boolean _arg3 = data.readBoolean();
      boolean _arg4 = data.readBoolean();
      boolean _arg5 = data.readBoolean();
      IMediaProjection _arg6 = IMediaProjection.Stub.asInterface(data.readStrongBinder());
      data.enforceNoDataAvail();
      String _result = registerAudioPolicy(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
      reply.writeNoException();
      reply.writeString(_result);
      return true;
    }

    protected void setPreferredDevicesForStrategy_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void removePreferredDevicesForStrategy_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getPreferredDevicesForStrategy_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void setDeviceAsNonDefaultForStrategy_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void removeDeviceAsNonDefaultForStrategy_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getNonDefaultDevicesForStrategy_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void setDeviceVolumeBehavior_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_setDeviceVolumeBehavior, getCallingPid(), getCallingUid());
    }

    protected void getDeviceVolumeBehavior_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermissionAnyOf(
          PERMISSIONS_getDeviceVolumeBehavior, getCallingPid(), getCallingUid());
    }

    protected void setMultiAudioFocusEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void clearPreferredDevicesForCapturePreset_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getPreferredDevicesForCapturePreset_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    private boolean onTransact$adjustStreamVolumeForUid$(Parcel data, Parcel reply)
        throws RemoteException {
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

    private boolean onTransact$adjustSuggestedStreamVolumeForUid$(Parcel data, Parcel reply)
        throws RemoteException {
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

    private boolean onTransact$setStreamVolumeForUid$(Parcel data, Parcel reply)
        throws RemoteException {
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

    private boolean onTransact$requestAudioFocusForTest$(Parcel data, Parcel reply)
        throws RemoteException {
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
      int _result =
          requestAudioFocusForTest(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
      reply.writeNoException();
      reply.writeInt(_result);
      return true;
    }

    protected void isSpatializerAvailableForDevice_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void hasHeadTracker_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void setHeadTrackerEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void isHeadTrackerEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void setSpatializerEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void registerSpatializerHeadTrackingCallback_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void unregisterSpatializerHeadTrackingCallback_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void registerHeadToSoundstagePoseCallback_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void unregisterHeadToSoundstagePoseCallback_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void getSpatializerCompatibleAudioDevices_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void addSpatializerCompatibleAudioDevice_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void removeSpatializerCompatibleAudioDevice_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void setDesiredHeadTrackingMode_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void getDesiredHeadTrackingMode_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void getSupportedHeadTrackingModes_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void getActualHeadTrackingMode_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void setSpatializerGlobalTransform_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void recenterHeadTracker_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void setSpatializerParameter_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void getSpatializerParameter_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void getSpatializerOutput_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void registerSpatializerOutputCallback_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void unregisterSpatializerOutputCallback_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
    }

    protected void isPstnCallAudioInterceptable_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.CALL_AUDIO_INTERCEPTION, getCallingPid(), getCallingUid());
    }

    protected void getMutingExpectedDevice_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void registerMuteAwaitConnectionDispatcher_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getFocusStack_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void addAssistantServicesUids_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void removeAssistantServicesUids_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void setActiveAssistantServiceUids_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getAssistantServicesUids_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void getActiveAssistantServiceUids_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    private boolean onTransact$registerDeviceVolumeDispatcherForAbsoluteVolume$(
        Parcel data, Parcel reply) throws RemoteException {
      boolean _arg0 = data.readBoolean();
      IAudioDeviceVolumeDispatcher _arg1 =
          IAudioDeviceVolumeDispatcher.Stub.asInterface(data.readStrongBinder());
      String _arg2 = data.readString();
      AudioDeviceAttributes _arg3 =
          (AudioDeviceAttributes) data.readTypedObject(AudioDeviceAttributes.CREATOR);
      List<VolumeInfo> _arg4 = data.createTypedArrayList(VolumeInfo.CREATOR);
      boolean _arg5 = data.readBoolean();
      int _arg6 = data.readInt();
      data.enforceNoDataAvail();
      registerDeviceVolumeDispatcherForAbsoluteVolume(
          _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
      reply.writeNoException();
      return true;
    }

    protected void supportsBluetoothVariableLatency_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void setBluetoothVariableLatencyEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    protected void isBluetoothVariableLatencyEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
    }

    private boolean onTransact$setStreamVolumeForDeviceWithAttribution$(Parcel data, Parcel reply)
        throws RemoteException {
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

    private boolean onTransact$setA2dpDeviceVolume$(Parcel data, Parcel reply)
        throws RemoteException {
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

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 288;
    }
  }
}
