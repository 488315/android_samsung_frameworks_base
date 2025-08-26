package android.media;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.AttributionSource;
import android.media.IAudioDeviceVolumeDispatcher;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioManagerNative;
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
import android.media.audiopolicy.IAudioVolumeChangeDispatcher;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IAudioService extends IInterface {

    public static class Default implements IAudioService {
        @Override // android.media.IAudioService
        public int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int abandonAudioFocusForTest(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void addAssistantServicesUids(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void addLoudnessCodecInfo(int i, int i2, LoudnessCodecInfo loudnessCodecInfo) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void addPackage(int i, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolume(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolume(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustVolume(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustVolumeGroupVolume(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void cancelMuteAwaitConnection(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int clearFadeManagerConfigurationForFocusLoss() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearPreferredDevicesForCapturePreset(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void disableSafeMediaVolume(String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void dismissVolumePanel() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChangeWithFade(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback, List<AudioFocusInfo> list, FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean enterAudioFocusFreezeForTest(IBinder iBinder, int[] iArr) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean exitAudioFocusFreezeForTest(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void forceComputeCsdOnAllDevices(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceRemoteSubmixFullVolume(boolean z, IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceUseFrameworkMel(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceVolumeControlStream(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getActiveAssistantServiceUids() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getActualHeadTrackingMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public int getAllowedCapturePolicy() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getAppDevice(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getAppVolume(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getAssistantServicesUids() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public String getAudioServiceConfig(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int[] getAvailableCommunicationDeviceIds() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getBluetoothAudioDeviceCategory(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getCommunicationDevice() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public float getCsd() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioService
        public int getCurrentAudioFocus() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public String getCurrentAudioFocusPackageName() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public VolumeInfo getDefaultVolumeInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getDesiredHeadTrackingMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getDeviceMaskForStream(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public VolumeInfo getDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes audioAttributes) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(AudioAttributes audioAttributes) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getEarProtectLimit() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getEncodedSurroundMode(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<String> getExcludedRingtoneTitles(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public int getFineVolume(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public float[] getFloatVolumeTable() throws RemoteException {
            return null;
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
        public int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioFocusInfo> getFocusStack() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public long getFocusUnmuteDelayAfterFadeOutForTest() throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public AudioHalVersionInfo getHalVersion() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List getIndependentStreamTypes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getInputGainIndex(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getLastAudibleStreamVolume(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getLastAudibleVolumeForVolumeGroup(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public PersistableBundle getLoudnessParams(LoudnessCodecInfo loudnessCodecInfo) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public int getMaxInputGainIndex() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getMediaVolumeSteps() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getMicModeType() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getMinInputGainIndex() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getModeInternal() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getMuteInterval() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public IAudioManagerNative getNativeInterface() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public float getOutputRs2UpperBound() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioService
        public String getPinAppInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getPinDevice() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getPrevRingerMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getRadioOutputPath() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getRemainingMuteIntervalMs() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List getReportedSurroundFormats() throws RemoteException {
            return null;
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
        public IRingtonePlayer getRingtonePlayer() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public String[] getSelectedAppList() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List getSpatializedChannelMasks() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getSpatializerImmersiveAudioLevel() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getSpatializerOutput() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void getSpatializerParameter(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getStreamMaxVolume(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMinVolume(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamTypeAlias(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamVolume(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamVolumeForDevice(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getSupportedHeadTrackingModes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int[] getSupportedSystemUsages() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public Map getSurroundFormats() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getUiSoundsStreamType() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getUidForDevice(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVibrateSetting(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public IVolumeController getVolumeController() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupMaxVolumeIndex(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupMinVolumeIndex(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupVolumeIndex(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public VolumePolicy getVolumePolicy() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void handleBluetoothActiveDeviceChanged(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void handleVolumeKey(KeyEvent keyEvent, boolean z, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean hasHapticChannels(Uri uri) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean hasRegisteredDynamicPolicy() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isAlreadyInDB(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isAppMute(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isAudioServerRunning() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothA2dpOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothAudioDeviceCategoryFixed(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothScoOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothVariableLatencyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCallScreeningModeSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCameraSoundForced() throws RemoteException {
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
        public boolean isCsdEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isForceSpeakerOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHdmiSystemAudioSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHeadTrackerAvailable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHeadTrackerEnabled(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHomeSoundEffectEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHotwordStreamSupported(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isInAllowedList(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isInputGainFixed(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isMasterMute() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isMicrophoneMuted() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isMultiSoundOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isMusicActive(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isPstnCallAudioInterceptable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSafeMediaVolumeStateActive() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerAvailable() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerEnabled() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpeakerphoneOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByMute(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByRingerMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamMutableByUi(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamMute(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSurroundFormatEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isUltrasoundSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isUsingAudio(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isValidRingerMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeControlUsingVolumeGroups() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeFixed() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeGroupMuted(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean loadSoundEffects() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void lowerVolumeToRs1(String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void muteAwaitConnection(int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void notifySafetyVolumeDialogVisible(IVolumeController iVolumeController, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void permissionUpdateBarrier() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playSoundEffect(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playSoundEffectVolume(int i, float f) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerAttributes(int i, AudioAttributes audioAttributes) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerEvent(int i, int i2, int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerHasOpPlayAudio(int i, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerSessionId(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void portEvent(int i, int i2, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void recenterHeadTracker() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void recordRingtoneChanger(String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void recorderEvent(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection, AttributionSource attributionSource) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerAudioVolumeCallback(IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerDeviceVolumeBehaviorDispatcher(boolean z, IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, String str, AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, boolean z2, int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher iPlaybackConfigDispatcher, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerSpatializerCallback(ISpatializerCallback iSpatializerCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void releasePlayer(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void releaseRecorder(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void reloadAudioSettings() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeAssistantServicesUids(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int removeDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void removeLoudnessCodecInfo(int i, LoudnessCodecInfo loudnessCodecInfo) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void removePackageForName(String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int removePreferredDevicesForStrategy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, String str3, int i2, IAudioPolicyCallback iAudioPolicyCallback, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocusForTest(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int secGetActiveStreamType(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean sendFocusLoss(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void sendFocusLossAndUpdate(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setA2dpSuspended(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setActiveAssistantServiceUids(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes, long j) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setAllowedCapturePolicy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setAppDevice(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setAppMute(int i, boolean z, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setAppVolume(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setAudioServiceConfig(String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBluetoothA2dpOn(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setBluetoothAudioDeviceCategory(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothScoOn(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBluetoothVariableLatencyEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBtOffloadEnable(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setCommunicationDevice(IBinder iBinder, int i, AttributionSource attributionSource) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setCsd(float f) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setCsdAsAFeatureEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDesiredHeadTrackingMode(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setDeviceToForceByUser(int i, String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setEnableHardening(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setEncodedSurroundMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setFineVolume(int i, int i2, int i3, int i4, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setForceSpeakerOn(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setHdmiSystemAudioSupported(boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setHomeSoundEffectEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setInputGainIndex(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setLeAudioSuspended(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMasterMute(boolean z, int i, String str, int i2, String str2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setMediaVolumeSteps(int[] iArr) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMicInputControlMode(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMute(boolean z, String str, int i, String str2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMuteFromSwitch(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMode(int i, IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMultiAudioFocusEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMultiSoundOn(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMuteInterval(int i, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setNavigationRepeatSoundEffectsEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setNotifAliasRingForTest(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setOutputRs2UpperBound(float f) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setPreferredDevicesForCapturePreset(int i, List<AudioDeviceAttributes> list) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setPreferredDevicesForStrategy(int i, List<AudioDeviceAttributes> list) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setRadioOutputPath(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRemoteMic(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeExternal(int i, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeInternal(int i, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRttEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerGlobalTransform(float[] fArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerParameter(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpeakerphoneOn(IBinder iBinder, boolean z, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolume(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeForDeviceWithAttribution(int i, int i2, int i3, String str, String str2, int i4) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSupportedSystemUsages(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setSurroundFormatEnabled(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setVibrateSetting(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setVolumeController(IVolumeController iVolumeController) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setVolumeControllerLongPressTimeoutEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean shouldNotificationSoundPlay(AudioAttributes audioAttributes) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean shouldShowRingtoneVolume() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean shouldVibrate(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void startBluetoothSco(IBinder iBinder, int i, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void startBluetoothScoVirtualCall(IBinder iBinder, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void startLoudnessCodecUpdates(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void stopBluetoothSco(IBinder iBinder, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void stopLoudnessCodecUpdates(int i) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean supportsBluetoothVariableLatency() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int trackRecorder(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void unloadSoundEffects() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioFocusClient(String str) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioVolumeCallback(IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerCallback(ISpatializerCallback iSpatializerCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] audioMixArr, AudioMixingRule[] audioMixingRuleArr, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
            return 0;
        }
    }

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

    int getInputGainIndex(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int getLastAudibleStreamVolume(int i) throws RemoteException;

    int getLastAudibleVolumeForVolumeGroup(int i) throws RemoteException;

    PersistableBundle getLoudnessParams(LoudnessCodecInfo loudnessCodecInfo) throws RemoteException;

    long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int getMaxInputGainIndex() throws RemoteException;

    int[] getMediaVolumeSteps() throws RemoteException;

    int getMicModeType() throws RemoteException;

    int getMinInputGainIndex() throws RemoteException;

    int getMode() throws RemoteException;

    int getModeInternal() throws RemoteException;

    int getMuteInterval() throws RemoteException;

    AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException;

    IAudioManagerNative getNativeInterface() throws RemoteException;

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

    List getSpatializedChannelMasks() throws RemoteException;

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

    VolumePolicy getVolumePolicy() throws RemoteException;

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

    boolean isInputGainFixed(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

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

    void permissionUpdateBarrier() throws RemoteException;

    void playSoundEffect(int i, int i2) throws RemoteException;

    void playSoundEffectVolume(int i, float f) throws RemoteException;

    void playerAttributes(int i, AudioAttributes audioAttributes) throws RemoteException;

    void playerEvent(int i, int i2, int[] iArr) throws RemoteException;

    void playerHasOpPlayAudio(int i, boolean z) throws RemoteException;

    void playerSessionId(int i, int i2) throws RemoteException;

    void portEvent(int i, int i2, PersistableBundle persistableBundle) throws RemoteException;

    void recenterHeadTracker() throws RemoteException;

    void recordRingtoneChanger(String str) throws RemoteException;

    void recorderEvent(int i, int i2) throws RemoteException;

    String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection, AttributionSource attributionSource) throws RemoteException;

    void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException;

    void registerAudioVolumeCallback(IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcher) throws RemoteException;

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

    void sendFocusLossAndUpdate(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

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

    void setBluetoothScoOn(boolean z) throws RemoteException;

    void setBluetoothVariableLatencyEnabled(boolean z) throws RemoteException;

    void setBtOffloadEnable(int i) throws RemoteException;

    boolean setCommunicationDevice(IBinder iBinder, int i, AttributionSource attributionSource) throws RemoteException;

    void setCsd(float f) throws RemoteException;

    void setCsdAsAFeatureEnabled(boolean z) throws RemoteException;

    void setDesiredHeadTrackingMode(int i) throws RemoteException;

    int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    int setDeviceToForceByUser(int i, String str, boolean z) throws RemoteException;

    void setDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException;

    void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException;

    void setEnableHardening(boolean z) throws RemoteException;

    boolean setEncodedSurroundMode(int i) throws RemoteException;

    int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException;

    void setFineVolume(int i, int i2, int i3, int i4, String str) throws RemoteException;

    int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void setForceSpeakerOn(boolean z) throws RemoteException;

    int setHdmiSystemAudioSupported(boolean z) throws RemoteException;

    void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException;

    void setHomeSoundEffectEnabled(boolean z) throws RemoteException;

    void setInputGainIndex(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException;

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

    void setSpeakerphoneOn(IBinder iBinder, boolean z, AttributionSource attributionSource) throws RemoteException;

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

    void setVolumeControllerLongPressTimeoutEnabled(boolean z) throws RemoteException;

    void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2) throws RemoteException;

    void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException;

    void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException;

    boolean shouldNotificationSoundPlay(AudioAttributes audioAttributes) throws RemoteException;

    boolean shouldShowRingtoneVolume() throws RemoteException;

    boolean shouldVibrate(int i) throws RemoteException;

    void startBluetoothSco(IBinder iBinder, int i, AttributionSource attributionSource) throws RemoteException;

    void startBluetoothScoVirtualCall(IBinder iBinder, AttributionSource attributionSource) throws RemoteException;

    void startLoudnessCodecUpdates(int i) throws RemoteException;

    AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) throws RemoteException;

    void stopBluetoothSco(IBinder iBinder, AttributionSource attributionSource) throws RemoteException;

    void stopLoudnessCodecUpdates(int i) throws RemoteException;

    boolean supportsBluetoothVariableLatency() throws RemoteException;

    int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException;

    int trackRecorder(IBinder iBinder) throws RemoteException;

    void unloadSoundEffects() throws RemoteException;

    void unregisterAudioFocusClient(String str) throws RemoteException;

    void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException;

    void unregisterAudioVolumeCallback(IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcher) throws RemoteException;

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

    public static abstract class Stub extends Binder implements IAudioService {
        public static final String DESCRIPTOR = "android.media.IAudioService";
        static final int TRANSACTION_abandonAudioFocus = 78;
        static final int TRANSACTION_abandonAudioFocusForTest = 199;
        static final int TRANSACTION_addAssistantServicesUids = 252;
        static final int TRANSACTION_addLoudnessCodecInfo = 270;
        static final int TRANSACTION_addMixForPolicy = 124;
        static final int TRANSACTION_addOnDevicesForAttributesChangedListener = 162;
        static final int TRANSACTION_addPackage = 296;
        static final int TRANSACTION_addSpatializerCompatibleAudioDevice = 227;
        static final int TRANSACTION_adjustStreamVolume = 12;
        static final int TRANSACTION_adjustStreamVolumeForUid = 179;
        static final int TRANSACTION_adjustStreamVolumeWithAttribution = 13;
        static final int TRANSACTION_adjustSuggestedStreamVolume = 183;
        static final int TRANSACTION_adjustSuggestedStreamVolumeForUid = 180;
        static final int TRANSACTION_adjustVolume = 182;
        static final int TRANSACTION_adjustVolumeGroupVolume = 33;
        static final int TRANSACTION_areNavigationRepeatSoundEffectsEnabled = 191;
        static final int TRANSACTION_canBeSpatialized = 218;
        static final int TRANSACTION_cancelMuteAwaitConnection = 244;
        static final int TRANSACTION_clearFadeManagerConfigurationForFocusLoss = 274;
        static final int TRANSACTION_clearPreferredDevicesForCapturePreset = 175;
        static final int TRANSACTION_clearPreferredMixerAttributes = 260;
        static final int TRANSACTION_disableSafeMediaVolume = 103;
        static final int TRANSACTION_dismissVolumePanel = 314;
        static final int TRANSACTION_dispatchFocusChange = 138;
        static final int TRANSACTION_dispatchFocusChangeWithFade = 139;
        static final int TRANSACTION_enterAudioFocusFreezeForTest = 204;
        static final int TRANSACTION_exitAudioFocusFreezeForTest = 205;
        static final int TRANSACTION_forceComputeCsdOnAllDevices = 110;
        static final int TRANSACTION_forceRemoteSubmixFullVolume = 20;
        static final int TRANSACTION_forceUseFrameworkMel = 109;
        static final int TRANSACTION_forceVolumeControlStream = 84;
        static final int TRANSACTION_getA2dpDeviceVolume = 318;
        static final int TRANSACTION_getActiveAssistantServiceUids = 256;
        static final int TRANSACTION_getActivePlaybackConfigurations = 136;
        static final int TRANSACTION_getActiveRecordingConfigurations = 133;
        static final int TRANSACTION_getActualHeadTrackingMode = 232;
        static final int TRANSACTION_getAdditionalOutputDeviceDelay = 196;
        static final int TRANSACTION_getAllowedCapturePolicy = 165;
        static final int TRANSACTION_getAppDevice = 284;
        static final int TRANSACTION_getAppVolume = 286;
        static final int TRANSACTION_getAssistantServicesUids = 255;
        static final int TRANSACTION_getAudioProductStrategies = 37;
        static final int TRANSACTION_getAudioServiceConfig = 279;
        static final int TRANSACTION_getAudioVolumeGroups = 26;
        static final int TRANSACTION_getAvailableCommunicationDeviceIds = 186;
        static final int TRANSACTION_getBluetoothAudioDeviceCategory = 116;
        static final int TRANSACTION_getCommunicationDevice = 188;
        static final int TRANSACTION_getCsd = 107;
        static final int TRANSACTION_getCurrentAudioFocus = 80;
        static final int TRANSACTION_getCurrentAudioFocusPackageName = 315;
        static final int TRANSACTION_getDefaultVolumeInfo = 241;
        static final int TRANSACTION_getDesiredHeadTrackingMode = 230;
        static final int TRANSACTION_getDeviceMaskForStream = 185;
        static final int TRANSACTION_getDeviceVolume = 17;
        static final int TRANSACTION_getDeviceVolumeBehavior = 172;
        static final int TRANSACTION_getDevicesForAttributes = 160;
        static final int TRANSACTION_getDevicesForAttributesUnprotected = 161;
        static final int TRANSACTION_getEarProtectLimit = 330;
        static final int TRANSACTION_getEncodedSurroundMode = 68;
        static final int TRANSACTION_getExcludedRingtoneTitles = 325;
        static final int TRANSACTION_getFadeManagerConfigurationForFocusLoss = 275;
        static final int TRANSACTION_getFadeOutDurationOnFocusLossMillis = 200;
        static final int TRANSACTION_getFineVolume = 301;
        static final int TRANSACTION_getFloatVolumeTable = 319;
        static final int TRANSACTION_getFocusDuckedUidsForTest = 201;
        static final int TRANSACTION_getFocusFadeOutDurationForTest = 202;
        static final int TRANSACTION_getFocusRampTimeMs = 137;
        static final int TRANSACTION_getFocusStack = 249;
        static final int TRANSACTION_getFocusUnmuteDelayAfterFadeOutForTest = 203;
        static final int TRANSACTION_getHalVersion = 258;
        static final int TRANSACTION_getIndependentStreamTypes = 88;
        static final int TRANSACTION_getInputGainIndex = 43;
        static final int TRANSACTION_getLastAudibleStreamVolume = 34;
        static final int TRANSACTION_getLastAudibleVolumeForVolumeGroup = 31;
        static final int TRANSACTION_getLoudnessParams = 272;
        static final int TRANSACTION_getMaxAdditionalOutputDeviceDelay = 197;
        static final int TRANSACTION_getMaxInputGainIndex = 44;
        static final int TRANSACTION_getMediaVolumeSteps = 311;
        static final int TRANSACTION_getMicModeType = 329;
        static final int TRANSACTION_getMinInputGainIndex = 45;
        static final int TRANSACTION_getMode = 57;
        static final int TRANSACTION_getModeInternal = 327;
        static final int TRANSACTION_getMuteInterval = 306;
        static final int TRANSACTION_getMutingExpectedDevice = 245;
        static final int TRANSACTION_getNativeInterface = 1;
        static final int TRANSACTION_getNonDefaultDevicesForStrategy = 159;
        static final int TRANSACTION_getOutputRs2UpperBound = 105;
        static final int TRANSACTION_getPinAppInfo = 293;
        static final int TRANSACTION_getPinDevice = 294;
        static final int TRANSACTION_getPreferredDevicesForCapturePreset = 176;
        static final int TRANSACTION_getPreferredDevicesForStrategy = 156;
        static final int TRANSACTION_getPrevRingerMode = 308;
        static final int TRANSACTION_getRadioOutputPath = 313;
        static final int TRANSACTION_getRegisteredPolicyMixes = 122;
        static final int TRANSACTION_getRemainingMuteIntervalMs = 307;
        static final int TRANSACTION_getReportedSurroundFormats = 64;
        static final int TRANSACTION_getRingerModeExternal = 50;
        static final int TRANSACTION_getRingerModeInternal = 51;
        static final int TRANSACTION_getRingtonePlayer = 86;
        static final int TRANSACTION_getSelectedAppList = 295;
        static final int TRANSACTION_getSpatializedChannelMasks = 219;
        static final int TRANSACTION_getSpatializerCompatibleAudioDevices = 226;
        static final int TRANSACTION_getSpatializerImmersiveAudioLevel = 208;
        static final int TRANSACTION_getSpatializerOutput = 237;
        static final int TRANSACTION_getSpatializerParameter = 236;
        static final int TRANSACTION_getStreamMaxVolume = 25;
        static final int TRANSACTION_getStreamMinVolume = 24;
        static final int TRANSACTION_getStreamTypeAlias = 89;
        static final int TRANSACTION_getStreamVolume = 23;
        static final int TRANSACTION_getStreamVolumeForDevice = 292;
        static final int TRANSACTION_getSupportedHeadTrackingModes = 231;
        static final int TRANSACTION_getSupportedSystemUsages = 36;
        static final int TRANSACTION_getSurroundFormats = 63;
        static final int TRANSACTION_getUiSoundsStreamType = 87;
        static final int TRANSACTION_getUidForDevice = 282;
        static final int TRANSACTION_getVibrateSetting = 54;
        static final int TRANSACTION_getVolumeController = 97;
        static final int TRANSACTION_getVolumeGroupMaxVolumeIndex = 29;
        static final int TRANSACTION_getVolumeGroupMinVolumeIndex = 30;
        static final int TRANSACTION_getVolumeGroupVolumeIndex = 28;
        static final int TRANSACTION_getVolumePolicy = 129;
        static final int TRANSACTION_handleBluetoothActiveDeviceChanged = 141;
        static final int TRANSACTION_handleVolumeKey = 18;
        static final int TRANSACTION_hasHapticChannels = 152;
        static final int TRANSACTION_hasHeadTracker = 212;
        static final int TRANSACTION_hasRegisteredDynamicPolicy = 130;
        static final int TRANSACTION_isAlreadyInDB = 298;
        static final int TRANSACTION_isAppMute = 288;
        static final int TRANSACTION_isAudioServerRunning = 145;
        static final int TRANSACTION_isBluetoothA2dpOn = 76;
        static final int TRANSACTION_isBluetoothAudioDeviceCategoryFixed = 117;
        static final int TRANSACTION_isBluetoothScoOn = 74;
        static final int TRANSACTION_isBluetoothVariableLatencyEnabled = 265;
        static final int TRANSACTION_isCallScreeningModeSupported = 153;
        static final int TRANSACTION_isCameraSoundForced = 95;
        static final int TRANSACTION_isCsdAsAFeatureAvailable = 112;
        static final int TRANSACTION_isCsdAsAFeatureEnabled = 113;
        static final int TRANSACTION_isCsdEnabled = 111;
        static final int TRANSACTION_isForceSpeakerOn = 303;
        static final int TRANSACTION_isHdmiSystemAudioSupported = 119;
        static final int TRANSACTION_isHeadTrackerAvailable = 215;
        static final int TRANSACTION_isHeadTrackerEnabled = 214;
        static final int TRANSACTION_isHomeSoundEffectEnabled = 193;
        static final int TRANSACTION_isHotwordStreamSupported = 40;
        static final int TRANSACTION_isInAllowedList = 299;
        static final int TRANSACTION_isInputGainFixed = 46;
        static final int TRANSACTION_isMasterMute = 21;
        static final int TRANSACTION_isMicrophoneMuted = 38;
        static final int TRANSACTION_isMultiSoundOn = 290;
        static final int TRANSACTION_isMusicActive = 184;
        static final int TRANSACTION_isPstnCallAudioInterceptable = 242;
        static final int TRANSACTION_isSafeMediaVolumeStateActive = 324;
        static final int TRANSACTION_isSpatializerAvailable = 210;
        static final int TRANSACTION_isSpatializerAvailableForDevice = 211;
        static final int TRANSACTION_isSpatializerEnabled = 209;
        static final int TRANSACTION_isSpeakerphoneOn = 70;
        static final int TRANSACTION_isStreamAffectedByMute = 101;
        static final int TRANSACTION_isStreamAffectedByRingerMode = 100;
        static final int TRANSACTION_isStreamMutableByUi = 102;
        static final int TRANSACTION_isStreamMute = 19;
        static final int TRANSACTION_isSurroundFormatEnabled = 66;
        static final int TRANSACTION_isUltrasoundSupported = 39;
        static final int TRANSACTION_isUsingAudio = 316;
        static final int TRANSACTION_isValidRingerMode = 52;
        static final int TRANSACTION_isVolumeControlUsingVolumeGroups = 90;
        static final int TRANSACTION_isVolumeFixed = 240;
        static final int TRANSACTION_isVolumeGroupMuted = 32;
        static final int TRANSACTION_loadSoundEffects = 60;
        static final int TRANSACTION_lowerVolumeToRs1 = 104;
        static final int TRANSACTION_muteAwaitConnection = 243;
        static final int TRANSACTION_notifySafetyVolumeDialogVisible = 326;
        static final int TRANSACTION_notifyVolumeControllerVisible = 98;
        static final int TRANSACTION_permissionUpdateBarrier = 11;
        static final int TRANSACTION_playSoundEffect = 58;
        static final int TRANSACTION_playSoundEffectVolume = 59;
        static final int TRANSACTION_playerAttributes = 3;
        static final int TRANSACTION_playerEvent = 4;
        static final int TRANSACTION_playerHasOpPlayAudio = 140;
        static final int TRANSACTION_playerSessionId = 9;
        static final int TRANSACTION_portEvent = 10;
        static final int TRANSACTION_recenterHeadTracker = 234;
        static final int TRANSACTION_recordRingtoneChanger = 321;
        static final int TRANSACTION_recorderEvent = 7;
        static final int TRANSACTION_registerAudioPolicy = 120;
        static final int TRANSACTION_registerAudioServerStateDispatcher = 143;
        static final int TRANSACTION_registerAudioVolumeCallback = 146;
        static final int TRANSACTION_registerCapturePresetDevicesRoleDispatcher = 177;
        static final int TRANSACTION_registerCommunicationDeviceDispatcher = 189;
        static final int TRANSACTION_registerDeviceVolumeBehaviorDispatcher = 248;
        static final int TRANSACTION_registerDeviceVolumeDispatcherForAbsoluteVolume = 257;
        static final int TRANSACTION_registerHeadToSoundstagePoseCallback = 224;
        static final int TRANSACTION_registerLoudnessCodecUpdatesDispatcher = 266;
        static final int TRANSACTION_registerModeDispatcher = 206;
        static final int TRANSACTION_registerMuteAwaitConnectionDispatcher = 246;
        static final int TRANSACTION_registerPlaybackCallback = 134;
        static final int TRANSACTION_registerPlaybackCallbackWithPackage = 322;
        static final int TRANSACTION_registerPreferredMixerAttributesDispatcher = 261;
        static final int TRANSACTION_registerRecordingCallback = 131;
        static final int TRANSACTION_registerSpatializerCallback = 220;
        static final int TRANSACTION_registerSpatializerHeadTrackerAvailableCallback = 216;
        static final int TRANSACTION_registerSpatializerHeadTrackingCallback = 222;
        static final int TRANSACTION_registerSpatializerOutputCallback = 238;
        static final int TRANSACTION_registerStrategyNonDefaultDevicesDispatcher = 168;
        static final int TRANSACTION_registerStrategyPreferredDevicesDispatcher = 166;
        static final int TRANSACTION_registerStreamAliasingDispatcher = 91;
        static final int TRANSACTION_releasePlayer = 5;
        static final int TRANSACTION_releaseRecorder = 8;
        static final int TRANSACTION_reloadAudioSettings = 62;
        static final int TRANSACTION_removeAssistantServicesUids = 253;
        static final int TRANSACTION_removeDeviceAsNonDefaultForStrategy = 158;
        static final int TRANSACTION_removeLoudnessCodecInfo = 271;
        static final int TRANSACTION_removeMixForPolicy = 125;
        static final int TRANSACTION_removeOnDevicesForAttributesChangedListener = 163;
        static final int TRANSACTION_removePackageForName = 297;
        static final int TRANSACTION_removePreferredDevicesForStrategy = 155;
        static final int TRANSACTION_removeSpatializerCompatibleAudioDevice = 228;
        static final int TRANSACTION_removeUidDeviceAffinity = 149;
        static final int TRANSACTION_removeUserIdDeviceAffinity = 151;
        static final int TRANSACTION_requestAudioFocus = 77;
        static final int TRANSACTION_requestAudioFocusForTest = 198;
        static final int TRANSACTION_secGetActiveStreamType = 281;
        static final int TRANSACTION_sendFocusLoss = 251;
        static final int TRANSACTION_sendFocusLossAndUpdate = 250;
        static final int TRANSACTION_setA2dpDeviceVolume = 317;
        static final int TRANSACTION_setA2dpSuspended = 72;
        static final int TRANSACTION_setActiveAssistantServiceUids = 254;
        static final int TRANSACTION_setAdditionalOutputDeviceDelay = 195;
        static final int TRANSACTION_setAllowedCapturePolicy = 164;
        static final int TRANSACTION_setAppDevice = 283;
        static final int TRANSACTION_setAppMute = 287;
        static final int TRANSACTION_setAppVolume = 285;
        static final int TRANSACTION_setAudioServiceConfig = 278;
        static final int TRANSACTION_setBluetoothA2dpOn = 75;
        static final int TRANSACTION_setBluetoothAudioDeviceCategory = 115;
        static final int TRANSACTION_setBluetoothScoOn = 71;
        static final int TRANSACTION_setBluetoothVariableLatencyEnabled = 264;
        static final int TRANSACTION_setBtOffloadEnable = 323;
        static final int TRANSACTION_setCommunicationDevice = 187;
        static final int TRANSACTION_setCsd = 108;
        static final int TRANSACTION_setCsdAsAFeatureEnabled = 114;
        static final int TRANSACTION_setDesiredHeadTrackingMode = 229;
        static final int TRANSACTION_setDeviceAsNonDefaultForStrategy = 157;
        static final int TRANSACTION_setDeviceToForceByUser = 304;
        static final int TRANSACTION_setDeviceVolume = 16;
        static final int TRANSACTION_setDeviceVolumeBehavior = 171;
        static final int TRANSACTION_setEnableHardening = 277;
        static final int TRANSACTION_setEncodedSurroundMode = 67;
        static final int TRANSACTION_setFadeManagerConfigurationForFocusLoss = 273;
        static final int TRANSACTION_setFineVolume = 300;
        static final int TRANSACTION_setFocusPropertiesForPolicy = 127;
        static final int TRANSACTION_setFocusRequestResultFromExtPolicy = 142;
        static final int TRANSACTION_setForceSpeakerOn = 302;
        static final int TRANSACTION_setHdmiSystemAudioSupported = 118;
        static final int TRANSACTION_setHeadTrackerEnabled = 213;
        static final int TRANSACTION_setHomeSoundEffectEnabled = 194;
        static final int TRANSACTION_setInputGainIndex = 42;
        static final int TRANSACTION_setLeAudioSuspended = 73;
        static final int TRANSACTION_setMasterMute = 22;
        static final int TRANSACTION_setMediaVolumeSteps = 310;
        static final int TRANSACTION_setMicInputControlMode = 328;
        static final int TRANSACTION_setMicrophoneMute = 41;
        static final int TRANSACTION_setMicrophoneMuteFromSwitch = 47;
        static final int TRANSACTION_setMode = 56;
        static final int TRANSACTION_setMultiAudioFocusEnabled = 173;
        static final int TRANSACTION_setMultiSoundOn = 289;
        static final int TRANSACTION_setMuteInterval = 305;
        static final int TRANSACTION_setNavigationRepeatSoundEffectsEnabled = 192;
        static final int TRANSACTION_setNotifAliasRingForTest = 92;
        static final int TRANSACTION_setOutputRs2UpperBound = 106;
        static final int TRANSACTION_setPreferredDevicesForCapturePreset = 174;
        static final int TRANSACTION_setPreferredDevicesForStrategy = 154;
        static final int TRANSACTION_setPreferredMixerAttributes = 259;
        static final int TRANSACTION_setRadioOutputPath = 312;
        static final int TRANSACTION_setRemoteMic = 320;
        static final int TRANSACTION_setRingerModeExternal = 48;
        static final int TRANSACTION_setRingerModeInternal = 49;
        static final int TRANSACTION_setRingtonePlayer = 85;
        static final int TRANSACTION_setRttEnabled = 170;
        static final int TRANSACTION_setSoundSettingEventBroadcastIntent = 309;
        static final int TRANSACTION_setSpatializerEnabled = 217;
        static final int TRANSACTION_setSpatializerGlobalTransform = 233;
        static final int TRANSACTION_setSpatializerParameter = 235;
        static final int TRANSACTION_setSpeakerphoneOn = 69;
        static final int TRANSACTION_setStreamVolume = 14;
        static final int TRANSACTION_setStreamVolumeForDeviceWithAttribution = 291;
        static final int TRANSACTION_setStreamVolumeForUid = 181;
        static final int TRANSACTION_setStreamVolumeWithAttribution = 15;
        static final int TRANSACTION_setSupportedSystemUsages = 35;
        static final int TRANSACTION_setSurroundFormatEnabled = 65;
        static final int TRANSACTION_setTestDeviceConnectionState = 247;
        static final int TRANSACTION_setUidDeviceAffinity = 148;
        static final int TRANSACTION_setUserIdDeviceAffinity = 150;
        static final int TRANSACTION_setVibrateSetting = 53;
        static final int TRANSACTION_setVolumeController = 96;
        static final int TRANSACTION_setVolumeControllerLongPressTimeoutEnabled = 99;
        static final int TRANSACTION_setVolumeGroupVolumeIndex = 27;
        static final int TRANSACTION_setVolumePolicy = 128;
        static final int TRANSACTION_setWiredDeviceConnectionState = 93;
        static final int TRANSACTION_shouldNotificationSoundPlay = 276;
        static final int TRANSACTION_shouldShowRingtoneVolume = 280;
        static final int TRANSACTION_shouldVibrate = 55;
        static final int TRANSACTION_startBluetoothSco = 81;
        static final int TRANSACTION_startBluetoothScoVirtualCall = 82;
        static final int TRANSACTION_startLoudnessCodecUpdates = 268;
        static final int TRANSACTION_startWatchingRoutes = 94;
        static final int TRANSACTION_stopBluetoothSco = 83;
        static final int TRANSACTION_stopLoudnessCodecUpdates = 269;
        static final int TRANSACTION_supportsBluetoothVariableLatency = 263;
        static final int TRANSACTION_trackPlayer = 2;
        static final int TRANSACTION_trackRecorder = 6;
        static final int TRANSACTION_unloadSoundEffects = 61;
        static final int TRANSACTION_unregisterAudioFocusClient = 79;
        static final int TRANSACTION_unregisterAudioPolicy = 123;
        static final int TRANSACTION_unregisterAudioPolicyAsync = 121;
        static final int TRANSACTION_unregisterAudioServerStateDispatcher = 144;
        static final int TRANSACTION_unregisterAudioVolumeCallback = 147;
        static final int TRANSACTION_unregisterCapturePresetDevicesRoleDispatcher = 178;
        static final int TRANSACTION_unregisterCommunicationDeviceDispatcher = 190;
        static final int TRANSACTION_unregisterHeadToSoundstagePoseCallback = 225;
        static final int TRANSACTION_unregisterLoudnessCodecUpdatesDispatcher = 267;
        static final int TRANSACTION_unregisterModeDispatcher = 207;
        static final int TRANSACTION_unregisterPlaybackCallback = 135;
        static final int TRANSACTION_unregisterPreferredMixerAttributesDispatcher = 262;
        static final int TRANSACTION_unregisterRecordingCallback = 132;
        static final int TRANSACTION_unregisterSpatializerCallback = 221;
        static final int TRANSACTION_unregisterSpatializerHeadTrackingCallback = 223;
        static final int TRANSACTION_unregisterSpatializerOutputCallback = 239;
        static final int TRANSACTION_unregisterStrategyNonDefaultDevicesDispatcher = 169;
        static final int TRANSACTION_unregisterStrategyPreferredDevicesDispatcher = 167;
        static final int TRANSACTION_updateMixingRulesForPolicy = 126;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_setDeviceVolume = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final String[] PERMISSIONS_getDeviceVolume = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final String[] PERMISSIONS_getAudioVolumeGroups = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_setVolumeGroupVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_getVolumeGroupVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_getVolumeGroupMaxVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_getVolumeGroupMinVolumeIndex = {Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final String[] PERMISSIONS_addOnDevicesForAttributesChangedListener = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.QUERY_AUDIO_STATE};
        static final String[] PERMISSIONS_setDeviceVolumeBehavior = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final String[] PERMISSIONS_getDeviceVolumeBehavior = {Manifest.permission.MODIFY_AUDIO_ROUTING, Manifest.permission.QUERY_AUDIO_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 329;
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

        public static IAudioService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioService)) {
                return (IAudioService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getNativeInterface";
                case 2:
                    return "trackPlayer";
                case 3:
                    return "playerAttributes";
                case 4:
                    return "playerEvent";
                case 5:
                    return "releasePlayer";
                case 6:
                    return "trackRecorder";
                case 7:
                    return "recorderEvent";
                case 8:
                    return "releaseRecorder";
                case 9:
                    return "playerSessionId";
                case 10:
                    return "portEvent";
                case 11:
                    return "permissionUpdateBarrier";
                case 12:
                    return "adjustStreamVolume";
                case 13:
                    return "adjustStreamVolumeWithAttribution";
                case 14:
                    return "setStreamVolume";
                case 15:
                    return "setStreamVolumeWithAttribution";
                case 16:
                    return "setDeviceVolume";
                case 17:
                    return "getDeviceVolume";
                case 18:
                    return "handleVolumeKey";
                case 19:
                    return "isStreamMute";
                case 20:
                    return "forceRemoteSubmixFullVolume";
                case 21:
                    return "isMasterMute";
                case 22:
                    return "setMasterMute";
                case 23:
                    return AudioManager.VOLUME_CACHING_API;
                case 24:
                    return AudioManager.VOLUME_MIN_CACHING_API;
                case 25:
                    return AudioManager.VOLUME_MAX_CACHING_API;
                case 26:
                    return "getAudioVolumeGroups";
                case 27:
                    return "setVolumeGroupVolumeIndex";
                case 28:
                    return "getVolumeGroupVolumeIndex";
                case 29:
                    return "getVolumeGroupMaxVolumeIndex";
                case 30:
                    return "getVolumeGroupMinVolumeIndex";
                case 31:
                    return "getLastAudibleVolumeForVolumeGroup";
                case 32:
                    return "isVolumeGroupMuted";
                case 33:
                    return "adjustVolumeGroupVolume";
                case 34:
                    return "getLastAudibleStreamVolume";
                case 35:
                    return "setSupportedSystemUsages";
                case 36:
                    return "getSupportedSystemUsages";
                case 37:
                    return "getAudioProductStrategies";
                case 38:
                    return "isMicrophoneMuted";
                case 39:
                    return "isUltrasoundSupported";
                case 40:
                    return "isHotwordStreamSupported";
                case 41:
                    return "setMicrophoneMute";
                case 42:
                    return "setInputGainIndex";
                case 43:
                    return "getInputGainIndex";
                case 44:
                    return "getMaxInputGainIndex";
                case 45:
                    return "getMinInputGainIndex";
                case 46:
                    return "isInputGainFixed";
                case 47:
                    return "setMicrophoneMuteFromSwitch";
                case 48:
                    return "setRingerModeExternal";
                case 49:
                    return "setRingerModeInternal";
                case 50:
                    return "getRingerModeExternal";
                case 51:
                    return "getRingerModeInternal";
                case 52:
                    return "isValidRingerMode";
                case 53:
                    return "setVibrateSetting";
                case 54:
                    return "getVibrateSetting";
                case 55:
                    return "shouldVibrate";
                case 56:
                    return "setMode";
                case 57:
                    return "getMode";
                case 58:
                    return "playSoundEffect";
                case 59:
                    return "playSoundEffectVolume";
                case 60:
                    return "loadSoundEffects";
                case 61:
                    return "unloadSoundEffects";
                case 62:
                    return "reloadAudioSettings";
                case 63:
                    return "getSurroundFormats";
                case 64:
                    return "getReportedSurroundFormats";
                case 65:
                    return "setSurroundFormatEnabled";
                case 66:
                    return "isSurroundFormatEnabled";
                case 67:
                    return "setEncodedSurroundMode";
                case 68:
                    return "getEncodedSurroundMode";
                case 69:
                    return "setSpeakerphoneOn";
                case 70:
                    return "isSpeakerphoneOn";
                case 71:
                    return "setBluetoothScoOn";
                case 72:
                    return "setA2dpSuspended";
                case 73:
                    return "setLeAudioSuspended";
                case 74:
                    return "isBluetoothScoOn";
                case 75:
                    return "setBluetoothA2dpOn";
                case 76:
                    return "isBluetoothA2dpOn";
                case 77:
                    return "requestAudioFocus";
                case 78:
                    return "abandonAudioFocus";
                case 79:
                    return "unregisterAudioFocusClient";
                case 80:
                    return "getCurrentAudioFocus";
                case 81:
                    return "startBluetoothSco";
                case 82:
                    return "startBluetoothScoVirtualCall";
                case 83:
                    return "stopBluetoothSco";
                case 84:
                    return "forceVolumeControlStream";
                case 85:
                    return "setRingtonePlayer";
                case 86:
                    return "getRingtonePlayer";
                case 87:
                    return "getUiSoundsStreamType";
                case 88:
                    return "getIndependentStreamTypes";
                case 89:
                    return "getStreamTypeAlias";
                case 90:
                    return "isVolumeControlUsingVolumeGroups";
                case 91:
                    return "registerStreamAliasingDispatcher";
                case 92:
                    return "setNotifAliasRingForTest";
                case 93:
                    return "setWiredDeviceConnectionState";
                case 94:
                    return "startWatchingRoutes";
                case 95:
                    return "isCameraSoundForced";
                case 96:
                    return "setVolumeController";
                case 97:
                    return "getVolumeController";
                case 98:
                    return "notifyVolumeControllerVisible";
                case 99:
                    return "setVolumeControllerLongPressTimeoutEnabled";
                case 100:
                    return "isStreamAffectedByRingerMode";
                case 101:
                    return "isStreamAffectedByMute";
                case 102:
                    return "isStreamMutableByUi";
                case 103:
                    return "disableSafeMediaVolume";
                case 104:
                    return "lowerVolumeToRs1";
                case 105:
                    return "getOutputRs2UpperBound";
                case 106:
                    return "setOutputRs2UpperBound";
                case 107:
                    return "getCsd";
                case 108:
                    return "setCsd";
                case 109:
                    return "forceUseFrameworkMel";
                case 110:
                    return "forceComputeCsdOnAllDevices";
                case 111:
                    return "isCsdEnabled";
                case 112:
                    return "isCsdAsAFeatureAvailable";
                case 113:
                    return "isCsdAsAFeatureEnabled";
                case 114:
                    return "setCsdAsAFeatureEnabled";
                case 115:
                    return "setBluetoothAudioDeviceCategory";
                case 116:
                    return "getBluetoothAudioDeviceCategory";
                case 117:
                    return "isBluetoothAudioDeviceCategoryFixed";
                case 118:
                    return "setHdmiSystemAudioSupported";
                case 119:
                    return "isHdmiSystemAudioSupported";
                case 120:
                    return "registerAudioPolicy";
                case 121:
                    return "unregisterAudioPolicyAsync";
                case 122:
                    return "getRegisteredPolicyMixes";
                case 123:
                    return "unregisterAudioPolicy";
                case 124:
                    return "addMixForPolicy";
                case 125:
                    return "removeMixForPolicy";
                case 126:
                    return "updateMixingRulesForPolicy";
                case 127:
                    return "setFocusPropertiesForPolicy";
                case 128:
                    return "setVolumePolicy";
                case 129:
                    return "getVolumePolicy";
                case 130:
                    return "hasRegisteredDynamicPolicy";
                case 131:
                    return "registerRecordingCallback";
                case 132:
                    return "unregisterRecordingCallback";
                case 133:
                    return "getActiveRecordingConfigurations";
                case 134:
                    return "registerPlaybackCallback";
                case 135:
                    return "unregisterPlaybackCallback";
                case 136:
                    return "getActivePlaybackConfigurations";
                case 137:
                    return "getFocusRampTimeMs";
                case 138:
                    return "dispatchFocusChange";
                case 139:
                    return "dispatchFocusChangeWithFade";
                case 140:
                    return "playerHasOpPlayAudio";
                case 141:
                    return "handleBluetoothActiveDeviceChanged";
                case 142:
                    return "setFocusRequestResultFromExtPolicy";
                case 143:
                    return "registerAudioServerStateDispatcher";
                case 144:
                    return "unregisterAudioServerStateDispatcher";
                case 145:
                    return "isAudioServerRunning";
                case 146:
                    return "registerAudioVolumeCallback";
                case 147:
                    return "unregisterAudioVolumeCallback";
                case 148:
                    return "setUidDeviceAffinity";
                case 149:
                    return "removeUidDeviceAffinity";
                case 150:
                    return "setUserIdDeviceAffinity";
                case 151:
                    return "removeUserIdDeviceAffinity";
                case 152:
                    return "hasHapticChannels";
                case 153:
                    return "isCallScreeningModeSupported";
                case 154:
                    return "setPreferredDevicesForStrategy";
                case 155:
                    return "removePreferredDevicesForStrategy";
                case 156:
                    return "getPreferredDevicesForStrategy";
                case 157:
                    return "setDeviceAsNonDefaultForStrategy";
                case 158:
                    return "removeDeviceAsNonDefaultForStrategy";
                case 159:
                    return "getNonDefaultDevicesForStrategy";
                case 160:
                    return "getDevicesForAttributes";
                case 161:
                    return "getDevicesForAttributesUnprotected";
                case 162:
                    return "addOnDevicesForAttributesChangedListener";
                case 163:
                    return "removeOnDevicesForAttributesChangedListener";
                case 164:
                    return "setAllowedCapturePolicy";
                case 165:
                    return "getAllowedCapturePolicy";
                case 166:
                    return "registerStrategyPreferredDevicesDispatcher";
                case 167:
                    return "unregisterStrategyPreferredDevicesDispatcher";
                case 168:
                    return "registerStrategyNonDefaultDevicesDispatcher";
                case 169:
                    return "unregisterStrategyNonDefaultDevicesDispatcher";
                case 170:
                    return "setRttEnabled";
                case 171:
                    return "setDeviceVolumeBehavior";
                case 172:
                    return "getDeviceVolumeBehavior";
                case 173:
                    return "setMultiAudioFocusEnabled";
                case 174:
                    return "setPreferredDevicesForCapturePreset";
                case 175:
                    return "clearPreferredDevicesForCapturePreset";
                case 176:
                    return "getPreferredDevicesForCapturePreset";
                case 177:
                    return "registerCapturePresetDevicesRoleDispatcher";
                case 178:
                    return "unregisterCapturePresetDevicesRoleDispatcher";
                case 179:
                    return "adjustStreamVolumeForUid";
                case 180:
                    return "adjustSuggestedStreamVolumeForUid";
                case 181:
                    return "setStreamVolumeForUid";
                case 182:
                    return "adjustVolume";
                case 183:
                    return "adjustSuggestedStreamVolume";
                case 184:
                    return "isMusicActive";
                case 185:
                    return "getDeviceMaskForStream";
                case 186:
                    return "getAvailableCommunicationDeviceIds";
                case 187:
                    return "setCommunicationDevice";
                case 188:
                    return "getCommunicationDevice";
                case 189:
                    return "registerCommunicationDeviceDispatcher";
                case 190:
                    return "unregisterCommunicationDeviceDispatcher";
                case 191:
                    return "areNavigationRepeatSoundEffectsEnabled";
                case 192:
                    return "setNavigationRepeatSoundEffectsEnabled";
                case 193:
                    return "isHomeSoundEffectEnabled";
                case 194:
                    return "setHomeSoundEffectEnabled";
                case 195:
                    return "setAdditionalOutputDeviceDelay";
                case 196:
                    return "getAdditionalOutputDeviceDelay";
                case 197:
                    return "getMaxAdditionalOutputDeviceDelay";
                case 198:
                    return "requestAudioFocusForTest";
                case 199:
                    return "abandonAudioFocusForTest";
                case 200:
                    return "getFadeOutDurationOnFocusLossMillis";
                case 201:
                    return "getFocusDuckedUidsForTest";
                case 202:
                    return "getFocusFadeOutDurationForTest";
                case 203:
                    return "getFocusUnmuteDelayAfterFadeOutForTest";
                case 204:
                    return "enterAudioFocusFreezeForTest";
                case 205:
                    return "exitAudioFocusFreezeForTest";
                case 206:
                    return "registerModeDispatcher";
                case 207:
                    return "unregisterModeDispatcher";
                case 208:
                    return "getSpatializerImmersiveAudioLevel";
                case 209:
                    return "isSpatializerEnabled";
                case 210:
                    return "isSpatializerAvailable";
                case 211:
                    return "isSpatializerAvailableForDevice";
                case 212:
                    return "hasHeadTracker";
                case 213:
                    return "setHeadTrackerEnabled";
                case 214:
                    return "isHeadTrackerEnabled";
                case 215:
                    return "isHeadTrackerAvailable";
                case 216:
                    return "registerSpatializerHeadTrackerAvailableCallback";
                case 217:
                    return "setSpatializerEnabled";
                case 218:
                    return "canBeSpatialized";
                case 219:
                    return "getSpatializedChannelMasks";
                case 220:
                    return "registerSpatializerCallback";
                case 221:
                    return "unregisterSpatializerCallback";
                case 222:
                    return "registerSpatializerHeadTrackingCallback";
                case 223:
                    return "unregisterSpatializerHeadTrackingCallback";
                case 224:
                    return "registerHeadToSoundstagePoseCallback";
                case 225:
                    return "unregisterHeadToSoundstagePoseCallback";
                case 226:
                    return "getSpatializerCompatibleAudioDevices";
                case 227:
                    return "addSpatializerCompatibleAudioDevice";
                case 228:
                    return "removeSpatializerCompatibleAudioDevice";
                case 229:
                    return "setDesiredHeadTrackingMode";
                case 230:
                    return "getDesiredHeadTrackingMode";
                case 231:
                    return "getSupportedHeadTrackingModes";
                case 232:
                    return "getActualHeadTrackingMode";
                case 233:
                    return "setSpatializerGlobalTransform";
                case 234:
                    return "recenterHeadTracker";
                case 235:
                    return "setSpatializerParameter";
                case 236:
                    return "getSpatializerParameter";
                case 237:
                    return "getSpatializerOutput";
                case 238:
                    return "registerSpatializerOutputCallback";
                case 239:
                    return "unregisterSpatializerOutputCallback";
                case 240:
                    return "isVolumeFixed";
                case 241:
                    return "getDefaultVolumeInfo";
                case 242:
                    return "isPstnCallAudioInterceptable";
                case 243:
                    return "muteAwaitConnection";
                case 244:
                    return "cancelMuteAwaitConnection";
                case 245:
                    return "getMutingExpectedDevice";
                case 246:
                    return "registerMuteAwaitConnectionDispatcher";
                case 247:
                    return "setTestDeviceConnectionState";
                case 248:
                    return "registerDeviceVolumeBehaviorDispatcher";
                case 249:
                    return "getFocusStack";
                case 250:
                    return "sendFocusLossAndUpdate";
                case 251:
                    return "sendFocusLoss";
                case 252:
                    return "addAssistantServicesUids";
                case 253:
                    return "removeAssistantServicesUids";
                case 254:
                    return "setActiveAssistantServiceUids";
                case 255:
                    return "getAssistantServicesUids";
                case 256:
                    return "getActiveAssistantServiceUids";
                case 257:
                    return "registerDeviceVolumeDispatcherForAbsoluteVolume";
                case 258:
                    return "getHalVersion";
                case 259:
                    return "setPreferredMixerAttributes";
                case 260:
                    return "clearPreferredMixerAttributes";
                case 261:
                    return "registerPreferredMixerAttributesDispatcher";
                case 262:
                    return "unregisterPreferredMixerAttributesDispatcher";
                case 263:
                    return "supportsBluetoothVariableLatency";
                case 264:
                    return "setBluetoothVariableLatencyEnabled";
                case 265:
                    return "isBluetoothVariableLatencyEnabled";
                case 266:
                    return "registerLoudnessCodecUpdatesDispatcher";
                case 267:
                    return "unregisterLoudnessCodecUpdatesDispatcher";
                case 268:
                    return "startLoudnessCodecUpdates";
                case 269:
                    return "stopLoudnessCodecUpdates";
                case 270:
                    return "addLoudnessCodecInfo";
                case 271:
                    return "removeLoudnessCodecInfo";
                case 272:
                    return "getLoudnessParams";
                case 273:
                    return "setFadeManagerConfigurationForFocusLoss";
                case 274:
                    return "clearFadeManagerConfigurationForFocusLoss";
                case 275:
                    return "getFadeManagerConfigurationForFocusLoss";
                case 276:
                    return "shouldNotificationSoundPlay";
                case 277:
                    return "setEnableHardening";
                case 278:
                    return "setAudioServiceConfig";
                case 279:
                    return "getAudioServiceConfig";
                case 280:
                    return "shouldShowRingtoneVolume";
                case 281:
                    return "secGetActiveStreamType";
                case 282:
                    return "getUidForDevice";
                case 283:
                    return "setAppDevice";
                case 284:
                    return "getAppDevice";
                case 285:
                    return "setAppVolume";
                case 286:
                    return "getAppVolume";
                case 287:
                    return "setAppMute";
                case 288:
                    return "isAppMute";
                case 289:
                    return "setMultiSoundOn";
                case 290:
                    return "isMultiSoundOn";
                case 291:
                    return "setStreamVolumeForDeviceWithAttribution";
                case 292:
                    return "getStreamVolumeForDevice";
                case 293:
                    return "getPinAppInfo";
                case 294:
                    return "getPinDevice";
                case 295:
                    return "getSelectedAppList";
                case 296:
                    return "addPackage";
                case 297:
                    return "removePackageForName";
                case 298:
                    return "isAlreadyInDB";
                case 299:
                    return "isInAllowedList";
                case 300:
                    return "setFineVolume";
                case 301:
                    return "getFineVolume";
                case 302:
                    return "setForceSpeakerOn";
                case 303:
                    return "isForceSpeakerOn";
                case 304:
                    return "setDeviceToForceByUser";
                case 305:
                    return "setMuteInterval";
                case 306:
                    return "getMuteInterval";
                case 307:
                    return "getRemainingMuteIntervalMs";
                case 308:
                    return "getPrevRingerMode";
                case 309:
                    return "setSoundSettingEventBroadcastIntent";
                case 310:
                    return "setMediaVolumeSteps";
                case 311:
                    return "getMediaVolumeSteps";
                case 312:
                    return "setRadioOutputPath";
                case 313:
                    return "getRadioOutputPath";
                case 314:
                    return "dismissVolumePanel";
                case 315:
                    return "getCurrentAudioFocusPackageName";
                case 316:
                    return "isUsingAudio";
                case 317:
                    return "setA2dpDeviceVolume";
                case 318:
                    return "getA2dpDeviceVolume";
                case 319:
                    return "getFloatVolumeTable";
                case 320:
                    return "setRemoteMic";
                case 321:
                    return "recordRingtoneChanger";
                case 322:
                    return "registerPlaybackCallbackWithPackage";
                case 323:
                    return "setBtOffloadEnable";
                case 324:
                    return "isSafeMediaVolumeStateActive";
                case 325:
                    return "getExcludedRingtoneTitles";
                case 326:
                    return "notifySafetyVolumeDialogVisible";
                case 327:
                    return "getModeInternal";
                case 328:
                    return "setMicInputControlMode";
                case 329:
                    return "getMicModeType";
                case 330:
                    return "getEarProtectLimit";
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
                    IAudioManagerNative nativeInterface = getNativeInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(nativeInterface);
                    return true;
                case 2:
                    PlayerBase.PlayerIdCard playerIdCard = (PlayerBase.PlayerIdCard) parcel.readTypedObject(PlayerBase.PlayerIdCard.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iTrackPlayer = trackPlayer(playerIdCard);
                    parcel2.writeNoException();
                    parcel2.writeInt(iTrackPlayer);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    playerAttributes(i3, audioAttributes);
                    return true;
                case 4:
                    return onTransact$playerEvent$(parcel, parcel2);
                case 5:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePlayer(i4);
                    return true;
                case 6:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int iTrackRecorder = trackRecorder(strongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(iTrackRecorder);
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    recorderEvent(i5, i6);
                    return true;
                case 8:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseRecorder(i7);
                    return true;
                case 9:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playerSessionId(i8, i9);
                    return true;
                case 10:
                    return onTransact$portEvent$(parcel, parcel2);
                case 11:
                    permissionUpdateBarrier();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    return onTransact$adjustStreamVolume$(parcel, parcel2);
                case 13:
                    return onTransact$adjustStreamVolumeWithAttribution$(parcel, parcel2);
                case 14:
                    return onTransact$setStreamVolume$(parcel, parcel2);
                case 15:
                    return onTransact$setStreamVolumeWithAttribution$(parcel, parcel2);
                case 16:
                    return onTransact$setDeviceVolume$(parcel, parcel2);
                case 17:
                    return onTransact$getDeviceVolume$(parcel, parcel2);
                case 18:
                    return onTransact$handleVolumeKey$(parcel, parcel2);
                case 19:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStreamMute = isStreamMute(i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStreamMute);
                    return true;
                case 20:
                    boolean z = parcel.readBoolean();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    forceRemoteSubmixFullVolume(z, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean zIsMasterMute = isMasterMute();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMasterMute);
                    return true;
                case 22:
                    return onTransact$setMasterMute$(parcel, parcel2);
                case 23:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamVolume = getStreamVolume(i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamVolume);
                    return true;
                case 24:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamMinVolume = getStreamMinVolume(i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamMinVolume);
                    return true;
                case 25:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamMaxVolume = getStreamMaxVolume(i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamMaxVolume);
                    return true;
                case 26:
                    List<android.media.audiopolicy.AudioVolumeGroup> audioVolumeGroups = getAudioVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(audioVolumeGroups, 1);
                    return true;
                case 27:
                    return onTransact$setVolumeGroupVolumeIndex$(parcel, parcel2);
                case 28:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeGroupVolumeIndex = getVolumeGroupVolumeIndex(i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupVolumeIndex);
                    return true;
                case 29:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeGroupMaxVolumeIndex = getVolumeGroupMaxVolumeIndex(i15);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupMaxVolumeIndex);
                    return true;
                case 30:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeGroupMinVolumeIndex = getVolumeGroupMinVolumeIndex(i16);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupMinVolumeIndex);
                    return true;
                case 31:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lastAudibleVolumeForVolumeGroup = getLastAudibleVolumeForVolumeGroup(i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(lastAudibleVolumeForVolumeGroup);
                    return true;
                case 32:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVolumeGroupMuted = isVolumeGroupMuted(i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVolumeGroupMuted);
                    return true;
                case 33:
                    return onTransact$adjustVolumeGroupVolume$(parcel, parcel2);
                case 34:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lastAudibleStreamVolume = getLastAudibleStreamVolume(i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(lastAudibleStreamVolume);
                    return true;
                case 35:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSupportedSystemUsages(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int[] supportedSystemUsages = getSupportedSystemUsages();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedSystemUsages);
                    return true;
                case 37:
                    List<android.media.audiopolicy.AudioProductStrategy> audioProductStrategies = getAudioProductStrategies();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(audioProductStrategies, 1);
                    return true;
                case 38:
                    boolean zIsMicrophoneMuted = isMicrophoneMuted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMicrophoneMuted);
                    return true;
                case 39:
                    boolean zIsUltrasoundSupported = isUltrasoundSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUltrasoundSupported);
                    return true;
                case 40:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsHotwordStreamSupported = isHotwordStreamSupported(z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHotwordStreamSupported);
                    return true;
                case 41:
                    return onTransact$setMicrophoneMute$(parcel, parcel2);
                case 42:
                    AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInputGainIndex(audioDeviceAttributes, i20);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    AudioDeviceAttributes audioDeviceAttributes2 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int inputGainIndex = getInputGainIndex(audioDeviceAttributes2);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputGainIndex);
                    return true;
                case 44:
                    int maxInputGainIndex = getMaxInputGainIndex();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxInputGainIndex);
                    return true;
                case 45:
                    int minInputGainIndex = getMinInputGainIndex();
                    parcel2.writeNoException();
                    parcel2.writeInt(minInputGainIndex);
                    return true;
                case 46:
                    AudioDeviceAttributes audioDeviceAttributes3 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsInputGainFixed = isInputGainFixed(audioDeviceAttributes3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInputGainFixed);
                    return true;
                case 47:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMicrophoneMuteFromSwitch(z3);
                    return true;
                case 48:
                    int i21 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRingerModeExternal(i21, string);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int i22 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRingerModeInternal(i22, string2);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int ringerModeExternal = getRingerModeExternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(ringerModeExternal);
                    return true;
                case 51:
                    int ringerModeInternal = getRingerModeInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(ringerModeInternal);
                    return true;
                case 52:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsValidRingerMode = isValidRingerMode(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsValidRingerMode);
                    return true;
                case 53:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVibrateSetting(i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vibrateSetting = getVibrateSetting(i26);
                    parcel2.writeNoException();
                    parcel2.writeInt(vibrateSetting);
                    return true;
                case 55:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldVibrate = shouldVibrate(i27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldVibrate);
                    return true;
                case 56:
                    return onTransact$setMode$(parcel, parcel2);
                case 57:
                    int mode = getMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(mode);
                    return true;
                case 58:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playSoundEffect(i28, i29);
                    return true;
                case 59:
                    int i30 = parcel.readInt();
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    playSoundEffectVolume(i30, f);
                    return true;
                case 60:
                    boolean zLoadSoundEffects = loadSoundEffects();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zLoadSoundEffects);
                    return true;
                case 61:
                    unloadSoundEffects();
                    return true;
                case 62:
                    reloadAudioSettings();
                    return true;
                case 63:
                    Map surroundFormats = getSurroundFormats();
                    parcel2.writeNoException();
                    parcel2.writeMap(surroundFormats);
                    return true;
                case 64:
                    List reportedSurroundFormats = getReportedSurroundFormats();
                    parcel2.writeNoException();
                    parcel2.writeList(reportedSurroundFormats);
                    return true;
                case 65:
                    int i31 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean surroundFormatEnabled = setSurroundFormatEnabled(i31, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(surroundFormatEnabled);
                    return true;
                case 66:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSurroundFormatEnabled = isSurroundFormatEnabled(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSurroundFormatEnabled);
                    return true;
                case 67:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean encodedSurroundMode = setEncodedSurroundMode(i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(encodedSurroundMode);
                    return true;
                case 68:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int encodedSurroundMode2 = getEncodedSurroundMode(i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(encodedSurroundMode2);
                    return true;
                case 69:
                    return onTransact$setSpeakerphoneOn$(parcel, parcel2);
                case 70:
                    boolean zIsSpeakerphoneOn = isSpeakerphoneOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSpeakerphoneOn);
                    return true;
                case 71:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothScoOn(z5);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setA2dpSuspended(z6);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLeAudioSuspended(z7);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    boolean zIsBluetoothScoOn = isBluetoothScoOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothScoOn);
                    return true;
                case 75:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothA2dpOn(z8);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    boolean zIsBluetoothA2dpOn = isBluetoothA2dpOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothA2dpOn);
                    return true;
                case 77:
                    return onTransact$requestAudioFocus$(parcel, parcel2);
                case 78:
                    return onTransact$abandonAudioFocus$(parcel, parcel2);
                case 79:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAudioFocusClient(string3);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int currentAudioFocus = getCurrentAudioFocus();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentAudioFocus);
                    return true;
                case 81:
                    return onTransact$startBluetoothSco$(parcel, parcel2);
                case 82:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    startBluetoothScoVirtualCall(strongBinder3, attributionSource);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    AttributionSource attributionSource2 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopBluetoothSco(strongBinder4, attributionSource2);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    int i35 = parcel.readInt();
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    forceVolumeControlStream(i35, strongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    IRingtonePlayer iRingtonePlayerAsInterface = IRingtonePlayer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRingtonePlayer(iRingtonePlayerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    IRingtonePlayer ringtonePlayer = getRingtonePlayer();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ringtonePlayer);
                    return true;
                case 87:
                    int uiSoundsStreamType = getUiSoundsStreamType();
                    parcel2.writeNoException();
                    parcel2.writeInt(uiSoundsStreamType);
                    return true;
                case 88:
                    List independentStreamTypes = getIndependentStreamTypes();
                    parcel2.writeNoException();
                    parcel2.writeList(independentStreamTypes);
                    return true;
                case 89:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamTypeAlias = getStreamTypeAlias(i36);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamTypeAlias);
                    return true;
                case 90:
                    boolean zIsVolumeControlUsingVolumeGroups = isVolumeControlUsingVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVolumeControlUsingVolumeGroups);
                    return true;
                case 91:
                    IStreamAliasingDispatcher iStreamAliasingDispatcherAsInterface = IStreamAliasingDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerStreamAliasingDispatcher(iStreamAliasingDispatcherAsInterface, z9);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotifAliasRingForTest(z10);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    return onTransact$setWiredDeviceConnectionState$(parcel, parcel2);
                case 94:
                    IAudioRoutesObserver iAudioRoutesObserverAsInterface = IAudioRoutesObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    AudioRoutesInfo audioRoutesInfoStartWatchingRoutes = startWatchingRoutes(iAudioRoutesObserverAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(audioRoutesInfoStartWatchingRoutes, 1);
                    return true;
                case 95:
                    boolean zIsCameraSoundForced = isCameraSoundForced();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraSoundForced);
                    return true;
                case 96:
                    IVolumeController iVolumeControllerAsInterface = IVolumeController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setVolumeController(iVolumeControllerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    IVolumeController volumeController = getVolumeController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(volumeController);
                    return true;
                case 98:
                    IVolumeController iVolumeControllerAsInterface2 = IVolumeController.Stub.asInterface(parcel.readStrongBinder());
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyVolumeControllerVisible(iVolumeControllerAsInterface2, z11);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVolumeControllerLongPressTimeoutEnabled(z12);
                    return true;
                case 100:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStreamAffectedByRingerMode = isStreamAffectedByRingerMode(i37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStreamAffectedByRingerMode);
                    return true;
                case 101:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStreamAffectedByMute = isStreamAffectedByMute(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStreamAffectedByMute);
                    return true;
                case 102:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStreamMutableByUi = isStreamMutableByUi(i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStreamMutableByUi);
                    return true;
                case 103:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableSafeMediaVolume(string4);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    lowerVolumeToRs1(string5);
                    return true;
                case 105:
                    float outputRs2UpperBound = getOutputRs2UpperBound();
                    parcel2.writeNoException();
                    parcel2.writeFloat(outputRs2UpperBound);
                    return true;
                case 106:
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setOutputRs2UpperBound(f2);
                    return true;
                case 107:
                    float csd = getCsd();
                    parcel2.writeNoException();
                    parcel2.writeFloat(csd);
                    return true;
                case 108:
                    float f3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setCsd(f3);
                    return true;
                case 109:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceUseFrameworkMel(z13);
                    return true;
                case 110:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceComputeCsdOnAllDevices(z14);
                    return true;
                case 111:
                    boolean zIsCsdEnabled = isCsdEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCsdEnabled);
                    return true;
                case 112:
                    boolean zIsCsdAsAFeatureAvailable = isCsdAsAFeatureAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCsdAsAFeatureAvailable);
                    return true;
                case 113:
                    boolean zIsCsdAsAFeatureEnabled = isCsdAsAFeatureEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCsdAsAFeatureEnabled);
                    return true;
                case 114:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCsdAsAFeatureEnabled(z15);
                    return true;
                case 115:
                    String string6 = parcel.readString();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothAudioDeviceCategory = setBluetoothAudioDeviceCategory(string6, i40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothAudioDeviceCategory);
                    return true;
                case 116:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int bluetoothAudioDeviceCategory2 = getBluetoothAudioDeviceCategory(string7);
                    parcel2.writeNoException();
                    parcel2.writeInt(bluetoothAudioDeviceCategory2);
                    return true;
                case 117:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothAudioDeviceCategoryFixed = isBluetoothAudioDeviceCategoryFixed(string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothAudioDeviceCategoryFixed);
                    return true;
                case 118:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int hdmiSystemAudioSupported = setHdmiSystemAudioSupported(z16);
                    parcel2.writeNoException();
                    parcel2.writeInt(hdmiSystemAudioSupported);
                    return true;
                case 119:
                    boolean zIsHdmiSystemAudioSupported = isHdmiSystemAudioSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHdmiSystemAudioSupported);
                    return true;
                case 120:
                    return onTransact$registerAudioPolicy$(parcel, parcel2);
                case 121:
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioPolicyAsync(iAudioPolicyCallbackAsInterface);
                    return true;
                case 122:
                    List<android.media.audiopolicy.AudioMix> registeredPolicyMixes = getRegisteredPolicyMixes();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(registeredPolicyMixes, 1);
                    return true;
                case 123:
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface2 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioPolicy(iAudioPolicyCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig = (android.media.audiopolicy.AudioPolicyConfig) parcel.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface3 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iAddMixForPolicy = addMixForPolicy(audioPolicyConfig, iAudioPolicyCallbackAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddMixForPolicy);
                    return true;
                case 125:
                    android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig2 = (android.media.audiopolicy.AudioPolicyConfig) parcel.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface4 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRemoveMixForPolicy = removeMixForPolicy(audioPolicyConfig2, iAudioPolicyCallbackAsInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveMixForPolicy);
                    return true;
                case 126:
                    return onTransact$updateMixingRulesForPolicy$(parcel, parcel2);
                case 127:
                    int i41 = parcel.readInt();
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface5 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int focusPropertiesForPolicy = setFocusPropertiesForPolicy(i41, iAudioPolicyCallbackAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(focusPropertiesForPolicy);
                    return true;
                case 128:
                    VolumePolicy volumePolicy = (VolumePolicy) parcel.readTypedObject(VolumePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVolumePolicy(volumePolicy);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    VolumePolicy volumePolicy2 = getVolumePolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(volumePolicy2, 1);
                    return true;
                case 130:
                    boolean zHasRegisteredDynamicPolicy = hasRegisteredDynamicPolicy();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasRegisteredDynamicPolicy);
                    return true;
                case 131:
                    IRecordingConfigDispatcher iRecordingConfigDispatcherAsInterface = IRecordingConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRecordingCallback(iRecordingConfigDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    IRecordingConfigDispatcher iRecordingConfigDispatcherAsInterface2 = IRecordingConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRecordingCallback(iRecordingConfigDispatcherAsInterface2);
                    return true;
                case 133:
                    List<AudioRecordingConfiguration> activeRecordingConfigurations = getActiveRecordingConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeRecordingConfigurations, 1);
                    return true;
                case 134:
                    IPlaybackConfigDispatcher iPlaybackConfigDispatcherAsInterface = IPlaybackConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPlaybackCallback(iPlaybackConfigDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 135:
                    IPlaybackConfigDispatcher iPlaybackConfigDispatcherAsInterface2 = IPlaybackConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPlaybackCallback(iPlaybackConfigDispatcherAsInterface2);
                    return true;
                case 136:
                    List<AudioPlaybackConfiguration> activePlaybackConfigurations = getActivePlaybackConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activePlaybackConfigurations, 1);
                    return true;
                case 137:
                    int i42 = parcel.readInt();
                    AudioAttributes audioAttributes2 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int focusRampTimeMs = getFocusRampTimeMs(i42, audioAttributes2);
                    parcel2.writeNoException();
                    parcel2.writeInt(focusRampTimeMs);
                    return true;
                case 138:
                    return onTransact$dispatchFocusChange$(parcel, parcel2);
                case 139:
                    return onTransact$dispatchFocusChangeWithFade$(parcel, parcel2);
                case 140:
                    int i43 = parcel.readInt();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    playerHasOpPlayAudio(i43, z17);
                    return true;
                case 141:
                    return onTransact$handleBluetoothActiveDeviceChanged$(parcel, parcel2);
                case 142:
                    return onTransact$setFocusRequestResultFromExtPolicy$(parcel, parcel2);
                case 143:
                    IAudioServerStateDispatcher iAudioServerStateDispatcherAsInterface = IAudioServerStateDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAudioServerStateDispatcher(iAudioServerStateDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    IAudioServerStateDispatcher iAudioServerStateDispatcherAsInterface2 = IAudioServerStateDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioServerStateDispatcher(iAudioServerStateDispatcherAsInterface2);
                    return true;
                case 145:
                    boolean zIsAudioServerRunning = isAudioServerRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAudioServerRunning);
                    return true;
                case 146:
                    IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcherAsInterface = IAudioVolumeChangeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAudioVolumeCallback(iAudioVolumeChangeDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 147:
                    IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcherAsInterface2 = IAudioVolumeChangeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioVolumeCallback(iAudioVolumeChangeDispatcherAsInterface2);
                    return true;
                case 148:
                    return onTransact$setUidDeviceAffinity$(parcel, parcel2);
                case 149:
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface6 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRemoveUidDeviceAffinity = removeUidDeviceAffinity(iAudioPolicyCallbackAsInterface6, i44);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveUidDeviceAffinity);
                    return true;
                case 150:
                    return onTransact$setUserIdDeviceAffinity$(parcel, parcel2);
                case 151:
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface7 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRemoveUserIdDeviceAffinity = removeUserIdDeviceAffinity(iAudioPolicyCallbackAsInterface7, i45);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveUserIdDeviceAffinity);
                    return true;
                case 152:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zHasHapticChannels = hasHapticChannels(uri);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasHapticChannels);
                    return true;
                case 153:
                    boolean zIsCallScreeningModeSupported = isCallScreeningModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallScreeningModeSupported);
                    return true;
                case 154:
                    int i46 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int preferredDevicesForStrategy = setPreferredDevicesForStrategy(i46, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDevicesForStrategy);
                    return true;
                case 155:
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRemovePreferredDevicesForStrategy = removePreferredDevicesForStrategy(i47);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePreferredDevicesForStrategy);
                    return true;
                case 156:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AudioDeviceAttributes> preferredDevicesForStrategy2 = getPreferredDevicesForStrategy(i48);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(preferredDevicesForStrategy2, 1);
                    return true;
                case 157:
                    int i49 = parcel.readInt();
                    AudioDeviceAttributes audioDeviceAttributes4 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceAsNonDefaultForStrategy = setDeviceAsNonDefaultForStrategy(i49, audioDeviceAttributes4);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceAsNonDefaultForStrategy);
                    return true;
                case 158:
                    int i50 = parcel.readInt();
                    AudioDeviceAttributes audioDeviceAttributes5 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveDeviceAsNonDefaultForStrategy = removeDeviceAsNonDefaultForStrategy(i50, audioDeviceAttributes5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveDeviceAsNonDefaultForStrategy);
                    return true;
                case 159:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AudioDeviceAttributes> nonDefaultDevicesForStrategy = getNonDefaultDevicesForStrategy(i51);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(nonDefaultDevicesForStrategy, 1);
                    return true;
                case 160:
                    AudioAttributes audioAttributes3 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AudioDeviceAttributes> devicesForAttributes = getDevicesForAttributes(audioAttributes3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(devicesForAttributes, 1);
                    return true;
                case 161:
                    AudioAttributes audioAttributes4 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<AudioDeviceAttributes> devicesForAttributesUnprotected = getDevicesForAttributesUnprotected(audioAttributes4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(devicesForAttributesUnprotected, 1);
                    return true;
                case 162:
                    AudioAttributes audioAttributes5 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    IDevicesForAttributesCallback iDevicesForAttributesCallbackAsInterface = IDevicesForAttributesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnDevicesForAttributesChangedListener(audioAttributes5, iDevicesForAttributesCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 163:
                    IDevicesForAttributesCallback iDevicesForAttributesCallbackAsInterface2 = IDevicesForAttributesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnDevicesForAttributesChangedListener(iDevicesForAttributesCallbackAsInterface2);
                    return true;
                case 164:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int allowedCapturePolicy = setAllowedCapturePolicy(i52);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedCapturePolicy);
                    return true;
                case 165:
                    int allowedCapturePolicy2 = getAllowedCapturePolicy();
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedCapturePolicy2);
                    return true;
                case 166:
                    IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcherAsInterface = IStrategyPreferredDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 167:
                    IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcherAsInterface2 = IStrategyPreferredDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcherAsInterface2);
                    return true;
                case 168:
                    IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcherAsInterface = IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 169:
                    IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcherAsInterface2 = IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcherAsInterface2);
                    return true;
                case 170:
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRttEnabled(z18);
                    return true;
                case 171:
                    return onTransact$setDeviceVolumeBehavior$(parcel, parcel2);
                case 172:
                    AudioDeviceAttributes audioDeviceAttributes6 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceVolumeBehavior = getDeviceVolumeBehavior(audioDeviceAttributes6);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceVolumeBehavior);
                    return true;
                case 173:
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMultiAudioFocusEnabled(z19);
                    return true;
                case 174:
                    int i53 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int preferredDevicesForCapturePreset = setPreferredDevicesForCapturePreset(i53, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDevicesForCapturePreset);
                    return true;
                case 175:
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iClearPreferredDevicesForCapturePreset = clearPreferredDevicesForCapturePreset(i54);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearPreferredDevicesForCapturePreset);
                    return true;
                case 176:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AudioDeviceAttributes> preferredDevicesForCapturePreset2 = getPreferredDevicesForCapturePreset(i55);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(preferredDevicesForCapturePreset2, 1);
                    return true;
                case 177:
                    ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcherAsInterface = ICapturePresetDevicesRoleDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 178:
                    ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcherAsInterface2 = ICapturePresetDevicesRoleDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcherAsInterface2);
                    return true;
                case 179:
                    return onTransact$adjustStreamVolumeForUid$(parcel, parcel2);
                case 180:
                    return onTransact$adjustSuggestedStreamVolumeForUid$(parcel, parcel2);
                case 181:
                    return onTransact$setStreamVolumeForUid$(parcel, parcel2);
                case 182:
                    int i56 = parcel.readInt();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustVolume(i56, i57);
                    return true;
                case 183:
                    return onTransact$adjustSuggestedStreamVolume$(parcel, parcel2);
                case 184:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsMusicActive = isMusicActive(z20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMusicActive);
                    return true;
                case 185:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceMaskForStream = getDeviceMaskForStream(i58);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceMaskForStream);
                    return true;
                case 186:
                    int[] availableCommunicationDeviceIds = getAvailableCommunicationDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableCommunicationDeviceIds);
                    return true;
                case 187:
                    return onTransact$setCommunicationDevice$(parcel, parcel2);
                case 188:
                    int communicationDevice = getCommunicationDevice();
                    parcel2.writeNoException();
                    parcel2.writeInt(communicationDevice);
                    return true;
                case 189:
                    ICommunicationDeviceDispatcher iCommunicationDeviceDispatcherAsInterface = ICommunicationDeviceDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCommunicationDeviceDispatcher(iCommunicationDeviceDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    ICommunicationDeviceDispatcher iCommunicationDeviceDispatcherAsInterface2 = ICommunicationDeviceDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCommunicationDeviceDispatcher(iCommunicationDeviceDispatcherAsInterface2);
                    return true;
                case 191:
                    boolean zAreNavigationRepeatSoundEffectsEnabled = areNavigationRepeatSoundEffectsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreNavigationRepeatSoundEffectsEnabled);
                    return true;
                case 192:
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationRepeatSoundEffectsEnabled(z21);
                    return true;
                case 193:
                    boolean zIsHomeSoundEffectEnabled = isHomeSoundEffectEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHomeSoundEffectEnabled);
                    return true;
                case 194:
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHomeSoundEffectEnabled(z22);
                    return true;
                case 195:
                    AudioDeviceAttributes audioDeviceAttributes7 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean additionalOutputDeviceDelay = setAdditionalOutputDeviceDelay(audioDeviceAttributes7, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(additionalOutputDeviceDelay);
                    return true;
                case 196:
                    AudioDeviceAttributes audioDeviceAttributes8 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    long additionalOutputDeviceDelay2 = getAdditionalOutputDeviceDelay(audioDeviceAttributes8);
                    parcel2.writeNoException();
                    parcel2.writeLong(additionalOutputDeviceDelay2);
                    return true;
                case 197:
                    AudioDeviceAttributes audioDeviceAttributes9 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    long maxAdditionalOutputDeviceDelay = getMaxAdditionalOutputDeviceDelay(audioDeviceAttributes9);
                    parcel2.writeNoException();
                    parcel2.writeLong(maxAdditionalOutputDeviceDelay);
                    return true;
                case 198:
                    return onTransact$requestAudioFocusForTest$(parcel, parcel2);
                case 199:
                    return onTransact$abandonAudioFocusForTest$(parcel, parcel2);
                case 200:
                    AudioAttributes audioAttributes6 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    long fadeOutDurationOnFocusLossMillis = getFadeOutDurationOnFocusLossMillis(audioAttributes6);
                    parcel2.writeNoException();
                    parcel2.writeLong(fadeOutDurationOnFocusLossMillis);
                    return true;
                case 201:
                    List focusDuckedUidsForTest = getFocusDuckedUidsForTest();
                    parcel2.writeNoException();
                    parcel2.writeList(focusDuckedUidsForTest);
                    return true;
                case 202:
                    long focusFadeOutDurationForTest = getFocusFadeOutDurationForTest();
                    parcel2.writeNoException();
                    parcel2.writeLong(focusFadeOutDurationForTest);
                    return true;
                case 203:
                    long focusUnmuteDelayAfterFadeOutForTest = getFocusUnmuteDelayAfterFadeOutForTest();
                    parcel2.writeNoException();
                    parcel2.writeLong(focusUnmuteDelayAfterFadeOutForTest);
                    return true;
                case 204:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean zEnterAudioFocusFreezeForTest = enterAudioFocusFreezeForTest(strongBinder6, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnterAudioFocusFreezeForTest);
                    return true;
                case 205:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zExitAudioFocusFreezeForTest = exitAudioFocusFreezeForTest(strongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zExitAudioFocusFreezeForTest);
                    return true;
                case 206:
                    IAudioModeDispatcher iAudioModeDispatcherAsInterface = IAudioModeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerModeDispatcher(iAudioModeDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 207:
                    IAudioModeDispatcher iAudioModeDispatcherAsInterface2 = IAudioModeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterModeDispatcher(iAudioModeDispatcherAsInterface2);
                    return true;
                case 208:
                    int spatializerImmersiveAudioLevel = getSpatializerImmersiveAudioLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(spatializerImmersiveAudioLevel);
                    return true;
                case 209:
                    boolean zIsSpatializerEnabled = isSpatializerEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSpatializerEnabled);
                    return true;
                case 210:
                    boolean zIsSpatializerAvailable = isSpatializerAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSpatializerAvailable);
                    return true;
                case 211:
                    AudioDeviceAttributes audioDeviceAttributes10 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSpatializerAvailableForDevice = isSpatializerAvailableForDevice(audioDeviceAttributes10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSpatializerAvailableForDevice);
                    return true;
                case 212:
                    AudioDeviceAttributes audioDeviceAttributes11 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zHasHeadTracker = hasHeadTracker(audioDeviceAttributes11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasHeadTracker);
                    return true;
                case 213:
                    boolean z23 = parcel.readBoolean();
                    AudioDeviceAttributes audioDeviceAttributes12 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setHeadTrackerEnabled(z23, audioDeviceAttributes12);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    AudioDeviceAttributes audioDeviceAttributes13 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsHeadTrackerEnabled = isHeadTrackerEnabled(audioDeviceAttributes13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeadTrackerEnabled);
                    return true;
                case 215:
                    boolean zIsHeadTrackerAvailable = isHeadTrackerAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeadTrackerAvailable);
                    return true;
                case 216:
                    ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallbackAsInterface = ISpatializerHeadTrackerAvailableCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerSpatializerHeadTrackerAvailableCallback(iSpatializerHeadTrackerAvailableCallbackAsInterface, z24);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpatializerEnabled(z25);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    AudioAttributes audioAttributes7 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCanBeSpatialized = canBeSpatialized(audioAttributes7, audioFormat);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanBeSpatialized);
                    return true;
                case 219:
                    List spatializedChannelMasks = getSpatializedChannelMasks();
                    parcel2.writeNoException();
                    parcel2.writeList(spatializedChannelMasks);
                    return true;
                case 220:
                    ISpatializerCallback iSpatializerCallbackAsInterface = ISpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSpatializerCallback(iSpatializerCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 221:
                    ISpatializerCallback iSpatializerCallbackAsInterface2 = ISpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSpatializerCallback(iSpatializerCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 222:
                    ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallbackAsInterface = ISpatializerHeadTrackingModeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSpatializerHeadTrackingCallback(iSpatializerHeadTrackingModeCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 223:
                    ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallbackAsInterface2 = ISpatializerHeadTrackingModeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSpatializerHeadTrackingCallback(iSpatializerHeadTrackingModeCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 224:
                    ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallbackAsInterface = ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHeadToSoundstagePoseCallback(iSpatializerHeadToSoundStagePoseCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallbackAsInterface2 = ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterHeadToSoundstagePoseCallback(iSpatializerHeadToSoundStagePoseCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 226:
                    List<AudioDeviceAttributes> spatializerCompatibleAudioDevices = getSpatializerCompatibleAudioDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(spatializerCompatibleAudioDevices, 1);
                    return true;
                case 227:
                    AudioDeviceAttributes audioDeviceAttributes14 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    addSpatializerCompatibleAudioDevice(audioDeviceAttributes14);
                    parcel2.writeNoException();
                    return true;
                case 228:
                    AudioDeviceAttributes audioDeviceAttributes15 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeSpatializerCompatibleAudioDevice(audioDeviceAttributes15);
                    parcel2.writeNoException();
                    return true;
                case 229:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDesiredHeadTrackingMode(i59);
                    parcel2.writeNoException();
                    return true;
                case 230:
                    int desiredHeadTrackingMode = getDesiredHeadTrackingMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(desiredHeadTrackingMode);
                    return true;
                case 231:
                    int[] supportedHeadTrackingModes = getSupportedHeadTrackingModes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedHeadTrackingModes);
                    return true;
                case 232:
                    int actualHeadTrackingMode = getActualHeadTrackingMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(actualHeadTrackingMode);
                    return true;
                case 233:
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setSpatializerGlobalTransform(fArrCreateFloatArray);
                    return true;
                case 234:
                    recenterHeadTracker();
                    return true;
                case 235:
                    int i60 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setSpatializerParameter(i60, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    int i61 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getSpatializerParameter(i61, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrCreateByteArray2);
                    return true;
                case 237:
                    int spatializerOutput = getSpatializerOutput();
                    parcel2.writeNoException();
                    parcel2.writeInt(spatializerOutput);
                    return true;
                case 238:
                    ISpatializerOutputCallback iSpatializerOutputCallbackAsInterface = ISpatializerOutputCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSpatializerOutputCallback(iSpatializerOutputCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 239:
                    ISpatializerOutputCallback iSpatializerOutputCallbackAsInterface2 = ISpatializerOutputCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSpatializerOutputCallback(iSpatializerOutputCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 240:
                    boolean zIsVolumeFixed = isVolumeFixed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVolumeFixed);
                    return true;
                case 241:
                    VolumeInfo defaultVolumeInfo = getDefaultVolumeInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultVolumeInfo, 1);
                    return true;
                case 242:
                    boolean zIsPstnCallAudioInterceptable = isPstnCallAudioInterceptable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPstnCallAudioInterceptable);
                    return true;
                case 243:
                    return onTransact$muteAwaitConnection$(parcel, parcel2);
                case 244:
                    AudioDeviceAttributes audioDeviceAttributes16 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelMuteAwaitConnection(audioDeviceAttributes16);
                    return true;
                case 245:
                    AudioDeviceAttributes mutingExpectedDevice = getMutingExpectedDevice();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mutingExpectedDevice, 1);
                    return true;
                case 246:
                    IMuteAwaitConnectionCallback iMuteAwaitConnectionCallbackAsInterface = IMuteAwaitConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerMuteAwaitConnectionDispatcher(iMuteAwaitConnectionCallbackAsInterface, z26);
                    parcel2.writeNoException();
                    return true;
                case 247:
                    AudioDeviceAttributes audioDeviceAttributes17 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTestDeviceConnectionState(audioDeviceAttributes17, z27);
                    parcel2.writeNoException();
                    return true;
                case 248:
                    boolean z28 = parcel.readBoolean();
                    IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcherAsInterface = IDeviceVolumeBehaviorDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDeviceVolumeBehaviorDispatcher(z28, iDeviceVolumeBehaviorDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 249:
                    List<AudioFocusInfo> focusStack = getFocusStack();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(focusStack, 1);
                    return true;
                case 250:
                    AudioFocusInfo audioFocusInfo = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface8 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendFocusLossAndUpdate(audioFocusInfo, iAudioPolicyCallbackAsInterface8);
                    return true;
                case 251:
                    AudioFocusInfo audioFocusInfo2 = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
                    IAudioPolicyCallback iAudioPolicyCallbackAsInterface9 = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zSendFocusLoss = sendFocusLoss(audioFocusInfo2, iAudioPolicyCallbackAsInterface9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendFocusLoss);
                    return true;
                case 252:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    addAssistantServicesUids(iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 253:
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeAssistantServicesUids(iArrCreateIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 254:
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setActiveAssistantServiceUids(iArrCreateIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 255:
                    int[] assistantServicesUids = getAssistantServicesUids();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(assistantServicesUids);
                    return true;
                case 256:
                    int[] activeAssistantServiceUids = getActiveAssistantServiceUids();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(activeAssistantServiceUids);
                    return true;
                case 257:
                    return onTransact$registerDeviceVolumeDispatcherForAbsoluteVolume$(parcel, parcel2);
                case 258:
                    AudioHalVersionInfo halVersion = getHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(halVersion, 1);
                    return true;
                case 259:
                    return onTransact$setPreferredMixerAttributes$(parcel, parcel2);
                case 260:
                    AudioAttributes audioAttributes8 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iClearPreferredMixerAttributes = clearPreferredMixerAttributes(audioAttributes8, i62);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearPreferredMixerAttributes);
                    return true;
                case 261:
                    IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcherAsInterface = IPreferredMixerAttributesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPreferredMixerAttributesDispatcher(iPreferredMixerAttributesDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 262:
                    IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcherAsInterface2 = IPreferredMixerAttributesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPreferredMixerAttributesDispatcher(iPreferredMixerAttributesDispatcherAsInterface2);
                    return true;
                case 263:
                    boolean zSupportsBluetoothVariableLatency = supportsBluetoothVariableLatency();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsBluetoothVariableLatency);
                    return true;
                case 264:
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothVariableLatencyEnabled(z29);
                    parcel2.writeNoException();
                    return true;
                case 265:
                    boolean zIsBluetoothVariableLatencyEnabled = isBluetoothVariableLatencyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothVariableLatencyEnabled);
                    return true;
                case 266:
                    ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcherAsInterface = ILoudnessCodecUpdatesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerLoudnessCodecUpdatesDispatcher(iLoudnessCodecUpdatesDispatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 267:
                    ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcherAsInterface2 = ILoudnessCodecUpdatesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterLoudnessCodecUpdatesDispatcher(iLoudnessCodecUpdatesDispatcherAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 268:
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startLoudnessCodecUpdates(i63);
                    parcel2.writeNoException();
                    return true;
                case 269:
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopLoudnessCodecUpdates(i64);
                    parcel2.writeNoException();
                    return true;
                case 270:
                    return onTransact$addLoudnessCodecInfo$(parcel, parcel2);
                case 271:
                    int i65 = parcel.readInt();
                    LoudnessCodecInfo loudnessCodecInfo = (LoudnessCodecInfo) parcel.readTypedObject(LoudnessCodecInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeLoudnessCodecInfo(i65, loudnessCodecInfo);
                    parcel2.writeNoException();
                    return true;
                case 272:
                    LoudnessCodecInfo loudnessCodecInfo2 = (LoudnessCodecInfo) parcel.readTypedObject(LoudnessCodecInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    PersistableBundle loudnessParams = getLoudnessParams(loudnessCodecInfo2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(loudnessParams, 1);
                    return true;
                case 273:
                    FadeManagerConfiguration fadeManagerConfiguration = (FadeManagerConfiguration) parcel.readTypedObject(FadeManagerConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int fadeManagerConfigurationForFocusLoss = setFadeManagerConfigurationForFocusLoss(fadeManagerConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(fadeManagerConfigurationForFocusLoss);
                    return true;
                case 274:
                    int iClearFadeManagerConfigurationForFocusLoss = clearFadeManagerConfigurationForFocusLoss();
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearFadeManagerConfigurationForFocusLoss);
                    return true;
                case 275:
                    FadeManagerConfiguration fadeManagerConfigurationForFocusLoss2 = getFadeManagerConfigurationForFocusLoss();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fadeManagerConfigurationForFocusLoss2, 1);
                    return true;
                case 276:
                    AudioAttributes audioAttributes9 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zShouldNotificationSoundPlay = shouldNotificationSoundPlay(audioAttributes9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldNotificationSoundPlay);
                    return true;
                case 277:
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnableHardening(z30);
                    parcel2.writeNoException();
                    return true;
                case 278:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAudioServiceConfig(string9);
                    parcel2.writeNoException();
                    return true;
                case 279:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String audioServiceConfig = getAudioServiceConfig(string10);
                    parcel2.writeNoException();
                    parcel2.writeString(audioServiceConfig);
                    return true;
                case 280:
                    boolean zShouldShowRingtoneVolume = shouldShowRingtoneVolume();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldShowRingtoneVolume);
                    return true;
                case 281:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSecGetActiveStreamType = secGetActiveStreamType(i66);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSecGetActiveStreamType);
                    return true;
                case 282:
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int uidForDevice = getUidForDevice(i67);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidForDevice);
                    return true;
                case 283:
                    return onTransact$setAppDevice$(parcel, parcel2);
                case 284:
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appDevice = getAppDevice(i68);
                    parcel2.writeNoException();
                    parcel2.writeInt(appDevice);
                    return true;
                case 285:
                    return onTransact$setAppVolume$(parcel, parcel2);
                case 286:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appVolume = getAppVolume(i69);
                    parcel2.writeNoException();
                    parcel2.writeInt(appVolume);
                    return true;
                case 287:
                    return onTransact$setAppMute$(parcel, parcel2);
                case 288:
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAppMute = isAppMute(i70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppMute);
                    return true;
                case 289:
                    return onTransact$setMultiSoundOn$(parcel, parcel2);
                case 290:
                    boolean zIsMultiSoundOn = isMultiSoundOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMultiSoundOn);
                    return true;
                case 291:
                    return onTransact$setStreamVolumeForDeviceWithAttribution$(parcel, parcel2);
                case 292:
                    return onTransact$getStreamVolumeForDevice$(parcel, parcel2);
                case 293:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String pinAppInfo = getPinAppInfo(i71);
                    parcel2.writeNoException();
                    parcel2.writeString(pinAppInfo);
                    return true;
                case 294:
                    int pinDevice = getPinDevice();
                    parcel2.writeNoException();
                    parcel2.writeInt(pinDevice);
                    return true;
                case 295:
                    String[] selectedAppList = getSelectedAppList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(selectedAppList);
                    return true;
                case 296:
                    return onTransact$addPackage$(parcel, parcel2);
                case 297:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePackageForName(string11);
                    parcel2.writeNoException();
                    return true;
                case 298:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAlreadyInDB = isAlreadyInDB(string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAlreadyInDB);
                    return true;
                case 299:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInAllowedList = isInAllowedList(string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInAllowedList);
                    return true;
                case 300:
                    return onTransact$setFineVolume$(parcel, parcel2);
                case 301:
                    return onTransact$getFineVolume$(parcel, parcel2);
                case 302:
                    boolean z31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setForceSpeakerOn(z31);
                    parcel2.writeNoException();
                    return true;
                case 303:
                    boolean zIsForceSpeakerOn = isForceSpeakerOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsForceSpeakerOn);
                    return true;
                case 304:
                    return onTransact$setDeviceToForceByUser$(parcel, parcel2);
                case 305:
                    return onTransact$setMuteInterval$(parcel, parcel2);
                case 306:
                    int muteInterval = getMuteInterval();
                    parcel2.writeNoException();
                    parcel2.writeInt(muteInterval);
                    return true;
                case 307:
                    int remainingMuteIntervalMs = getRemainingMuteIntervalMs();
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingMuteIntervalMs);
                    return true;
                case 308:
                    int prevRingerMode = getPrevRingerMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(prevRingerMode);
                    return true;
                case 309:
                    return onTransact$setSoundSettingEventBroadcastIntent$(parcel, parcel2);
                case 310:
                    int[] iArrCreateIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean mediaVolumeSteps = setMediaVolumeSteps(iArrCreateIntArray6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mediaVolumeSteps);
                    return true;
                case 311:
                    int[] mediaVolumeSteps2 = getMediaVolumeSteps();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(mediaVolumeSteps2);
                    return true;
                case 312:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRadioOutputPath(i72);
                    parcel2.writeNoException();
                    return true;
                case 313:
                    int radioOutputPath = getRadioOutputPath();
                    parcel2.writeNoException();
                    parcel2.writeInt(radioOutputPath);
                    return true;
                case 314:
                    dismissVolumePanel();
                    parcel2.writeNoException();
                    return true;
                case 315:
                    String currentAudioFocusPackageName = getCurrentAudioFocusPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(currentAudioFocusPackageName);
                    return true;
                case 316:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsingAudio = isUsingAudio(i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsingAudio);
                    return true;
                case 317:
                    return onTransact$setA2dpDeviceVolume$(parcel, parcel2);
                case 318:
                    return onTransact$getA2dpDeviceVolume$(parcel, parcel2);
                case 319:
                    float[] floatVolumeTable = getFloatVolumeTable();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(floatVolumeTable);
                    return true;
                case 320:
                    boolean z32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRemoteMic(z32);
                    parcel2.writeNoException();
                    return true;
                case 321:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    recordRingtoneChanger(string14);
                    return true;
                case 322:
                    return onTransact$registerPlaybackCallbackWithPackage$(parcel, parcel2);
                case 323:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBtOffloadEnable(i74);
                    parcel2.writeNoException();
                    return true;
                case 324:
                    boolean zIsSafeMediaVolumeStateActive = isSafeMediaVolumeStateActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSafeMediaVolumeStateActive);
                    return true;
                case 325:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> excludedRingtoneTitles = getExcludedRingtoneTitles(i75);
                    parcel2.writeNoException();
                    parcel2.writeStringList(excludedRingtoneTitles);
                    return true;
                case 326:
                    return onTransact$notifySafetyVolumeDialogVisible$(parcel, parcel2);
                case 327:
                    int modeInternal = getModeInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(modeInternal);
                    return true;
                case 328:
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMicInputControlMode(i76);
                    parcel2.writeNoException();
                    return true;
                case 329:
                    int micModeType = getMicModeType();
                    parcel2.writeNoException();
                    parcel2.writeInt(micModeType);
                    return true;
                case 330:
                    int earProtectLimit = getEarProtectLimit();
                    parcel2.writeNoException();
                    parcel2.writeInt(earProtectLimit);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAudioService {
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

            @Override // android.media.IAudioService
            public IAudioManagerNative getNativeInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IAudioManagerNative.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(playerIdCard, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerAttributes(int i, AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerEvent(int i, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releasePlayer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int trackRecorder(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recorderEvent(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releaseRecorder(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerSessionId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void portEvent(int i, int i2, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void permissionUpdateBarrier() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolume(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolume(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(volumeInfo, 0);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public VolumeInfo getDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(volumeInfo, 0);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VolumeInfo) parcelObtain2.readTypedObject(VolumeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleVolumeKey(KeyEvent keyEvent, boolean z, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamMute(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceRemoteSubmixFullVolume(boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMasterMute() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMasterMute(boolean z, int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMinVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMaxVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(android.media.audiopolicy.AudioVolumeGroup.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupVolumeIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupMaxVolumeIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupMinVolumeIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleVolumeForVolumeGroup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeGroupMuted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustVolumeGroupVolume(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleStreamVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSupportedSystemUsages(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getSupportedSystemUsages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(android.media.audiopolicy.AudioProductStrategy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMicrophoneMuted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isUltrasoundSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHotwordStreamSupported(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMute(boolean z, String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setInputGainIndex(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getInputGainIndex(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMaxInputGainIndex() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMinInputGainIndex() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isInputGainFixed(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMuteFromSwitch(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeExternal(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeInternal(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeExternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeInternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isValidRingerMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVibrateSetting(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVibrateSetting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldVibrate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMode(int i, IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffect(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(58, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffectVolume(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(59, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean loadSoundEffects() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unloadSoundEffects() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void reloadAudioSettings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public Map getSurroundFormats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getReportedSurroundFormats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setSurroundFormatEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSurroundFormatEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setEncodedSurroundMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getEncodedSurroundMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpeakerphoneOn(IBinder iBinder, boolean z, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpeakerphoneOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothScoOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setA2dpSuspended(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setLeAudioSuspended(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothScoOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothA2dpOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothA2dpOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, String str3, int i2, IAudioPolicyCallback iAudioPolicyCallback, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iAudioFocusDispatcher);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioFocusDispatcher);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioFocusClient(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCurrentAudioFocus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothSco(IBinder iBinder, int i, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothScoVirtualCall(IBinder iBinder, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopBluetoothSco(IBinder iBinder, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceVolumeControlStream(int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRingtonePlayer);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public IRingtonePlayer getRingtonePlayer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRingtonePlayer.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getUiSoundsStreamType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getIndependentStreamTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamTypeAlias(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeControlUsingVolumeGroups() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStreamAliasingDispatcher);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setNotifAliasRingForTest(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioRoutesObserver);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioRoutesInfo) parcelObtain2.readTypedObject(AudioRoutesInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCameraSoundForced() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeController(IVolumeController iVolumeController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVolumeController);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public IVolumeController getVolumeController() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IVolumeController.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVolumeController);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeControllerLongPressTimeoutEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(99, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByRingerMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByMute(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamMutableByUi(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void disableSafeMediaVolume(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void lowerVolumeToRs1(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(104, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float getOutputRs2UpperBound() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setOutputRs2UpperBound(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(106, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float getCsd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setCsd(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(108, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceUseFrameworkMel(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(109, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceComputeCsdOnAllDevices(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(110, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdAsAFeatureAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdAsAFeatureEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setCsdAsAFeatureEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(114, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setBluetoothAudioDeviceCategory(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getBluetoothAudioDeviceCategory(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothAudioDeviceCategoryFixed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setHdmiSystemAudioSupported(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHdmiSystemAudioSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioPolicyConfig, 0);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeStrongInterface(iMediaProjection);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(121, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(android.media.audiopolicy.AudioMix.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioPolicyConfig, 0);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioPolicyConfig, 0);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] audioMixArr, AudioMixingRule[] audioMixingRuleArr, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(audioMixArr, 0);
                    parcelObtain.writeTypedArray(audioMixingRuleArr, 0);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(volumePolicy, 0);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public VolumePolicy getVolumePolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VolumePolicy) parcelObtain2.readTypedObject(VolumePolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasRegisteredDynamicPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRecordingConfigDispatcher);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRecordingConfigDispatcher);
                    this.mRemote.transact(132, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPlaybackConfigDispatcher);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPlaybackConfigDispatcher);
                    this.mRemote.transact(135, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFocusInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChangeWithFade(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback, List<AudioFocusInfo> list, FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFocusInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(fadeManagerConfiguration, 0);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerHasOpPlayAudio(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(140, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleBluetoothActiveDeviceChanged(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bluetoothDevice, 0);
                    parcelObtain.writeTypedObject(bluetoothDevice2, 0);
                    parcelObtain.writeTypedObject(bluetoothProfileConnectionInfo, 0);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFocusInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(142, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioServerStateDispatcher);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioServerStateDispatcher);
                    this.mRemote.transact(144, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAudioServerRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerAudioVolumeCallback(IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioVolumeChangeDispatcher);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioVolumeCallback(IAudioVolumeChangeDispatcher iAudioVolumeChangeDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioVolumeChangeDispatcher);
                    this.mRemote.transact(147, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHapticChannels(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCallScreeningModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredDevicesForStrategy(int i, List<AudioDeviceAttributes> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removePreferredDevicesForStrategy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getDevicesForAttributesUnprotected(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeStrongInterface(iDevicesForAttributesCallback);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback iDevicesForAttributesCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDevicesForAttributesCallback);
                    this.mRemote.transact(163, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setAllowedCapturePolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAllowedCapturePolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStrategyPreferredDevicesDispatcher);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStrategyPreferredDevicesDispatcher);
                    this.mRemote.transact(167, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStrategyNonDefaultDevicesDispatcher);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStrategyNonDefaultDevicesDispatcher);
                    this.mRemote.transact(169, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRttEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(170, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMultiAudioFocusEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(173, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredDevicesForCapturePreset(int i, List<AudioDeviceAttributes> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearPreferredDevicesForCapturePreset(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCapturePresetDevicesRoleDispatcher);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCapturePresetDevicesRoleDispatcher);
                    this.mRemote.transact(178, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(179, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(180, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(181, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustVolume(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(182, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolume(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(183, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMusicActive(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDeviceMaskForStream(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getAvailableCommunicationDeviceIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setCommunicationDevice(IBinder iBinder, int i, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCommunicationDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCommunicationDeviceDispatcher);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCommunicationDeviceDispatcher);
                    this.mRemote.transact(190, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean areNavigationRepeatSoundEffectsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setNavigationRepeatSoundEffectsEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(192, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHomeSoundEffectEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setHomeSoundEffectEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(194, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocusForTest(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iAudioFocusDispatcher);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocusForTest(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioFocusDispatcher);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getFocusDuckedUidsForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFocusFadeOutDurationForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFocusUnmuteDelayAfterFadeOutForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean enterAudioFocusFreezeForTest(IBinder iBinder, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean exitAudioFocusFreezeForTest(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioModeDispatcher);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioModeDispatcher);
                    this.mRemote.transact(207, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getSpatializerImmersiveAudioLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHeadTrackerEnabled(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHeadTrackerAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerHeadTrackerAvailableCallback);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeTypedObject(audioFormat, 0);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List getSpatializedChannelMasks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerCallback(ISpatializerCallback iSpatializerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerCallback);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerCallback(ISpatializerCallback iSpatializerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerCallback);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerHeadTrackingModeCallback);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerHeadTrackingModeCallback);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerHeadToSoundStagePoseCallback);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerHeadToSoundStagePoseCallback);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(227, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(228, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDesiredHeadTrackingMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDesiredHeadTrackingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getSupportedHeadTrackingModes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(231, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getActualHeadTrackingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(232, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerGlobalTransform(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(233, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recenterHeadTracker() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(234, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerParameter(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(235, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void getSpatializerParameter(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(236, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readByteArray(bArr);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getSpatializerOutput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(237, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerOutputCallback);
                    this.mRemote.transact(238, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerOutputCallback);
                    this.mRemote.transact(239, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeFixed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(240, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public VolumeInfo getDefaultVolumeInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(241, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VolumeInfo) parcelObtain2.readTypedObject(VolumeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isPstnCallAudioInterceptable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(242, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void muteAwaitConnection(int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(243, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void cancelMuteAwaitConnection(AudioDeviceAttributes audioDeviceAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(244, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioDeviceAttributes getMutingExpectedDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(245, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioDeviceAttributes) parcelObtain2.readTypedObject(AudioDeviceAttributes.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMuteAwaitConnectionCallback);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(246, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(247, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerDeviceVolumeBehaviorDispatcher(boolean z, IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iDeviceVolumeBehaviorDispatcher);
                    this.mRemote.transact(248, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioFocusInfo> getFocusStack() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(249, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioFocusInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void sendFocusLossAndUpdate(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFocusInfo, 0);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(250, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean sendFocusLoss(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFocusInfo, 0);
                    parcelObtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(251, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addAssistantServicesUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(252, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeAssistantServicesUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(253, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setActiveAssistantServiceUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(254, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getAssistantServicesUids() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(255, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getActiveAssistantServiceUids() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(256, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, String str, AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, boolean z2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iAudioDeviceVolumeDispatcher);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(257, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioHalVersionInfo getHalVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(258, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioHalVersionInfo) parcelObtain2.readTypedObject(AudioHalVersionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioMixerAttributes, 0);
                    this.mRemote.transact(259, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(260, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPreferredMixerAttributesDispatcher);
                    this.mRemote.transact(261, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPreferredMixerAttributesDispatcher);
                    this.mRemote.transact(262, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean supportsBluetoothVariableLatency() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(263, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothVariableLatencyEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(264, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothVariableLatencyEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(265, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iLoudnessCodecUpdatesDispatcher);
                    this.mRemote.transact(266, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iLoudnessCodecUpdatesDispatcher);
                    this.mRemote.transact(267, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startLoudnessCodecUpdates(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(268, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopLoudnessCodecUpdates(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(269, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addLoudnessCodecInfo(int i, int i2, LoudnessCodecInfo loudnessCodecInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(loudnessCodecInfo, 0);
                    this.mRemote.transact(270, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeLoudnessCodecInfo(int i, LoudnessCodecInfo loudnessCodecInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(loudnessCodecInfo, 0);
                    this.mRemote.transact(271, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public PersistableBundle getLoudnessParams(LoudnessCodecInfo loudnessCodecInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(loudnessCodecInfo, 0);
                    this.mRemote.transact(272, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fadeManagerConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fadeManagerConfiguration, 0);
                    this.mRemote.transact(273, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearFadeManagerConfigurationForFocusLoss() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(274, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(275, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FadeManagerConfiguration) parcelObtain2.readTypedObject(FadeManagerConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldNotificationSoundPlay(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(276, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setEnableHardening(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(277, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAudioServiceConfig(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(278, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String getAudioServiceConfig(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(279, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldShowRingtoneVolume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(280, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int secGetActiveStreamType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(281, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getUidForDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(282, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAppDevice(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(283, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAppDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(284, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAppVolume(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(285, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAppVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(286, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setAppMute(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(287, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAppMute(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(288, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMultiSoundOn(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(289, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMultiSoundOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(290, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeForDeviceWithAttribution(int i, int i2, int i3, String str, String str2, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(291, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamVolumeForDevice(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(292, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String getPinAppInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(293, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getPinDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(294, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String[] getSelectedAppList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(295, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addPackage(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(296, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removePackageForName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(297, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAlreadyInDB(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(298, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isInAllowedList(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(299, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setFineVolume(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(300, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getFineVolume(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(301, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setForceSpeakerOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(302, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isForceSpeakerOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(303, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setDeviceToForceByUser(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(304, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMuteInterval(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(305, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMuteInterval() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(306, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRemainingMuteIntervalMs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(307, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getPrevRingerMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(308, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(309, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setMediaVolumeSteps(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(310, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getMediaVolumeSteps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(311, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRadioOutputPath(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(312, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRadioOutputPath() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(313, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void dismissVolumePanel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(314, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String getCurrentAudioFocusPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(315, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isUsingAudio(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(316, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bluetoothDevice, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(317, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bluetoothDevice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(318, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float[] getFloatVolumeTable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(319, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRemoteMic(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(320, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recordRingtoneChanger(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(321, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher iPlaybackConfigDispatcher, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPlaybackConfigDispatcher);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(322, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBtOffloadEnable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(323, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSafeMediaVolumeStateActive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(324, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<String> getExcludedRingtoneTitles(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(325, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void notifySafetyVolumeDialogVisible(IVolumeController iVolumeController, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVolumeController);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(326, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getModeInternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(327, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicInputControlMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(328, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMicModeType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(329, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getEarProtectLimit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(330, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        private boolean onTransact$playerEvent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int[] iArrCreateIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            playerEvent(i, i2, iArrCreateIntArray);
            return true;
        }

        private boolean onTransact$portEvent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
            parcel.enforceNoDataAvail();
            portEvent(i, i2, persistableBundle);
            return true;
        }

        private boolean onTransact$adjustStreamVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            adjustStreamVolume(i, i2, i3, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$adjustStreamVolumeWithAttribution$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            adjustStreamVolumeWithAttribution(i, i2, i3, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setStreamVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setStreamVolume(i, i2, i3, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setStreamVolumeWithAttribution$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setStreamVolumeWithAttribution(i, i2, i3, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDeviceVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            VolumeInfo volumeInfo = (VolumeInfo) parcel.readTypedObject(VolumeInfo.CREATOR);
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setDeviceVolume(volumeInfo, audioDeviceAttributes, string);
            parcel2.writeNoException();
            return true;
        }

        protected void setDeviceVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setDeviceVolume, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$getDeviceVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            VolumeInfo volumeInfo = (VolumeInfo) parcel.readTypedObject(VolumeInfo.CREATOR);
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            VolumeInfo deviceVolume = getDeviceVolume(volumeInfo, audioDeviceAttributes, string);
            parcel2.writeNoException();
            parcel2.writeTypedObject(deviceVolume, 1);
            return true;
        }

        protected void getDeviceVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getDeviceVolume, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$handleVolumeKey$(Parcel parcel, Parcel parcel2) throws RemoteException {
            KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            handleVolumeKey(keyEvent, z, string, string2);
            return true;
        }

        private boolean onTransact$setMasterMute$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            String string = parcel.readString();
            int i2 = parcel.readInt();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setMasterMute(z, i, string, i2, string2);
            parcel2.writeNoException();
            return true;
        }

        protected void setMasterMute_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAudioVolumeGroups_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getAudioVolumeGroups, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setVolumeGroupVolumeIndex$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setVolumeGroupVolumeIndex(i, i2, i3, string, string2);
            parcel2.writeNoException();
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

        private boolean onTransact$adjustVolumeGroupVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            adjustVolumeGroupVolume(i, i2, i3, string);
            parcel2.writeNoException();
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

        private boolean onTransact$setMicrophoneMute$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            int i = parcel.readInt();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setMicrophoneMute(z, string, i, string2);
            parcel2.writeNoException();
            return true;
        }

        protected void setInputGainIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getInputGainIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getMaxInputGainIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getMinInputGainIndex_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isInputGainFixed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IBinder strongBinder = parcel.readStrongBinder();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setMode(i, strongBinder, string);
            parcel2.writeNoException();
            return true;
        }

        protected void setEncodedSurroundMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SETTINGS, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setSpeakerphoneOn$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder strongBinder = parcel.readStrongBinder();
            boolean z = parcel.readBoolean();
            AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
            parcel.enforceNoDataAvail();
            setSpeakerphoneOn(strongBinder, z, attributionSource);
            parcel2.writeNoException();
            return true;
        }

        protected void setA2dpSuspended_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        protected void setLeAudioSuspended_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$requestAudioFocus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
            int i = parcel.readInt();
            IBinder strongBinder = parcel.readStrongBinder();
            IAudioFocusDispatcher iAudioFocusDispatcherAsInterface = IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            int i2 = parcel.readInt();
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iRequestAudioFocus = requestAudioFocus(audioAttributes, i, strongBinder, iAudioFocusDispatcherAsInterface, string, string2, string3, i2, iAudioPolicyCallbackAsInterface, i3);
            parcel2.writeNoException();
            parcel2.writeInt(iRequestAudioFocus);
            return true;
        }

        private boolean onTransact$abandonAudioFocus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IAudioFocusDispatcher iAudioFocusDispatcherAsInterface = IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iAbandonAudioFocus = abandonAudioFocus(iAudioFocusDispatcherAsInterface, string, audioAttributes, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iAbandonAudioFocus);
            return true;
        }

        private boolean onTransact$startBluetoothSco$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder strongBinder = parcel.readStrongBinder();
            int i = parcel.readInt();
            AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
            parcel.enforceNoDataAvail();
            startBluetoothSco(strongBinder, i, attributionSource);
            parcel2.writeNoException();
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

        private boolean onTransact$setWiredDeviceConnectionState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setWiredDeviceConnectionState(audioDeviceAttributes, i, string);
            parcel2.writeNoException();
            return true;
        }

        protected void setWiredDeviceConnectionState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setVolumeControllerLongPressTimeoutEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
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

        protected void setBluetoothAudioDeviceCategory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothAudioDeviceCategory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isBluetoothAudioDeviceCategoryFixed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$registerAudioPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig = (android.media.audiopolicy.AudioPolicyConfig) parcel.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            boolean z4 = parcel.readBoolean();
            IMediaProjection iMediaProjectionAsInterface = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
            AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
            parcel.enforceNoDataAvail();
            String strRegisterAudioPolicy = registerAudioPolicy(audioPolicyConfig, iAudioPolicyCallbackAsInterface, z, z2, z3, z4, iMediaProjectionAsInterface, attributionSource);
            parcel2.writeNoException();
            parcel2.writeString(strRegisterAudioPolicy);
            return true;
        }

        private boolean onTransact$updateMixingRulesForPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            android.media.audiopolicy.AudioMix[] audioMixArr = (android.media.audiopolicy.AudioMix[]) parcel.createTypedArray(android.media.audiopolicy.AudioMix.CREATOR);
            AudioMixingRule[] audioMixingRuleArr = (AudioMixingRule[]) parcel.createTypedArray(AudioMixingRule.CREATOR);
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int iUpdateMixingRulesForPolicy = updateMixingRulesForPolicy(audioMixArr, audioMixingRuleArr, iAudioPolicyCallbackAsInterface);
            parcel2.writeNoException();
            parcel2.writeInt(iUpdateMixingRulesForPolicy);
            return true;
        }

        protected void updateMixingRulesForPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$dispatchFocusChange$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioFocusInfo audioFocusInfo = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
            int i = parcel.readInt();
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int iDispatchFocusChange = dispatchFocusChange(audioFocusInfo, i, iAudioPolicyCallbackAsInterface);
            parcel2.writeNoException();
            parcel2.writeInt(iDispatchFocusChange);
            return true;
        }

        private boolean onTransact$dispatchFocusChangeWithFade$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioFocusInfo audioFocusInfo = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
            int i = parcel.readInt();
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioFocusInfo.CREATOR);
            FadeManagerConfiguration fadeManagerConfiguration = (FadeManagerConfiguration) parcel.readTypedObject(FadeManagerConfiguration.CREATOR);
            parcel.enforceNoDataAvail();
            int iDispatchFocusChangeWithFade = dispatchFocusChangeWithFade(audioFocusInfo, i, iAudioPolicyCallbackAsInterface, arrayListCreateTypedArrayList, fadeManagerConfiguration);
            parcel2.writeNoException();
            parcel2.writeInt(iDispatchFocusChangeWithFade);
            return true;
        }

        protected void dispatchFocusChangeWithFade_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$handleBluetoothActiveDeviceChanged$(Parcel parcel, Parcel parcel2) throws RemoteException {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
            BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo = (BluetoothProfileConnectionInfo) parcel.readTypedObject(BluetoothProfileConnectionInfo.CREATOR);
            parcel.enforceNoDataAvail();
            handleBluetoothActiveDeviceChanged(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo);
            parcel2.writeNoException();
            return true;
        }

        protected void handleBluetoothActiveDeviceChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setFocusRequestResultFromExtPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioFocusInfo audioFocusInfo = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
            int i = parcel.readInt();
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setFocusRequestResultFromExtPolicy(audioFocusInfo, i, iAudioPolicyCallbackAsInterface);
            return true;
        }

        private boolean onTransact$setUidDeviceAffinity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            int i = parcel.readInt();
            int[] iArrCreateIntArray = parcel.createIntArray();
            String[] strArrCreateStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            int uidDeviceAffinity = setUidDeviceAffinity(iAudioPolicyCallbackAsInterface, i, iArrCreateIntArray, strArrCreateStringArray);
            parcel2.writeNoException();
            parcel2.writeInt(uidDeviceAffinity);
            return true;
        }

        private boolean onTransact$setUserIdDeviceAffinity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IAudioPolicyCallback iAudioPolicyCallbackAsInterface = IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
            int i = parcel.readInt();
            int[] iArrCreateIntArray = parcel.createIntArray();
            String[] strArrCreateStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            int userIdDeviceAffinity = setUserIdDeviceAffinity(iAudioPolicyCallbackAsInterface, i, iArrCreateIntArray, strArrCreateStringArray);
            parcel2.writeNoException();
            parcel2.writeInt(userIdDeviceAffinity);
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

        protected void addOnDevicesForAttributesChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_addOnDevicesForAttributesChangedListener, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setDeviceVolumeBehavior$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setDeviceVolumeBehavior(audioDeviceAttributes, i, string);
            parcel2.writeNoException();
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

        private boolean onTransact$adjustStreamVolumeForUid$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
            int i6 = parcel.readInt();
            parcel.enforceNoDataAvail();
            adjustStreamVolumeForUid(i, i2, i3, string, i4, i5, userHandle, i6);
            return true;
        }

        private boolean onTransact$adjustSuggestedStreamVolumeForUid$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
            int i6 = parcel.readInt();
            parcel.enforceNoDataAvail();
            adjustSuggestedStreamVolumeForUid(i, i2, i3, string, i4, i5, userHandle, i6);
            return true;
        }

        private boolean onTransact$setStreamVolumeForUid$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
            int i6 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setStreamVolumeForUid(i, i2, i3, string, i4, i5, userHandle, i6);
            return true;
        }

        private boolean onTransact$adjustSuggestedStreamVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            adjustSuggestedStreamVolume(i, i2, i3);
            return true;
        }

        private boolean onTransact$setCommunicationDevice$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder strongBinder = parcel.readStrongBinder();
            int i = parcel.readInt();
            AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
            parcel.enforceNoDataAvail();
            boolean communicationDevice = setCommunicationDevice(strongBinder, i, attributionSource);
            parcel2.writeNoException();
            parcel2.writeBoolean(communicationDevice);
            return true;
        }

        private boolean onTransact$requestAudioFocusForTest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
            int i = parcel.readInt();
            IBinder strongBinder = parcel.readStrongBinder();
            IAudioFocusDispatcher iAudioFocusDispatcherAsInterface = IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iRequestAudioFocusForTest = requestAudioFocusForTest(audioAttributes, i, strongBinder, iAudioFocusDispatcherAsInterface, string, string2, i2, i3, i4);
            parcel2.writeNoException();
            parcel2.writeInt(iRequestAudioFocusForTest);
            return true;
        }

        private boolean onTransact$abandonAudioFocusForTest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IAudioFocusDispatcher iAudioFocusDispatcherAsInterface = IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iAbandonAudioFocusForTest = abandonAudioFocusForTest(iAudioFocusDispatcherAsInterface, string, audioAttributes, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iAbandonAudioFocusForTest);
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

        private boolean onTransact$muteAwaitConnection$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int[] iArrCreateIntArray = parcel.createIntArray();
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            muteAwaitConnection(iArrCreateIntArray, audioDeviceAttributes, j);
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

        protected void sendFocusLossAndUpdate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void sendFocusLoss_enforcePermission() throws SecurityException {
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

        private boolean onTransact$registerDeviceVolumeDispatcherForAbsoluteVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcherAsInterface = IAudioDeviceVolumeDispatcher.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(VolumeInfo.CREATOR);
            boolean z2 = parcel.readBoolean();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            registerDeviceVolumeDispatcherForAbsoluteVolume(z, iAudioDeviceVolumeDispatcherAsInterface, string, audioDeviceAttributes, arrayListCreateTypedArrayList, z2, i);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setPreferredMixerAttributes$(Parcel parcel, Parcel parcel2) throws RemoteException {
            AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
            int i = parcel.readInt();
            AudioMixerAttributes audioMixerAttributes = (AudioMixerAttributes) parcel.readTypedObject(AudioMixerAttributes.CREATOR);
            parcel.enforceNoDataAvail();
            int preferredMixerAttributes = setPreferredMixerAttributes(audioAttributes, i, audioMixerAttributes);
            parcel2.writeNoException();
            parcel2.writeInt(preferredMixerAttributes);
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

        private boolean onTransact$addLoudnessCodecInfo$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            LoudnessCodecInfo loudnessCodecInfo = (LoudnessCodecInfo) parcel.readTypedObject(LoudnessCodecInfo.CREATOR);
            parcel.enforceNoDataAvail();
            addLoudnessCodecInfo(i, i2, loudnessCodecInfo);
            parcel2.writeNoException();
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

        protected void setEnableHardening_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$setAppDevice$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAppDevice(i, i2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAppVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setAppVolume(i, i2, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAppMute$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setAppMute(i, z, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMultiSoundOn$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMultiSoundOn(z, z2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setStreamVolumeForDeviceWithAttribution$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setStreamVolumeForDeviceWithAttribution(i, i2, i3, string, string2, i4);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getStreamVolumeForDevice$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int streamVolumeForDevice = getStreamVolumeForDevice(i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(streamVolumeForDevice);
            return true;
        }

        private boolean onTransact$addPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            addPackage(i, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setFineVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setFineVolume(i, i2, i3, i4, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getFineVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int fineVolume = getFineVolume(i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(fineVolume);
            return true;
        }

        private boolean onTransact$setDeviceToForceByUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int deviceToForceByUser = setDeviceToForceByUser(i, string, z);
            parcel2.writeNoException();
            parcel2.writeInt(deviceToForceByUser);
            return true;
        }

        private boolean onTransact$setMuteInterval$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setMuteInterval(i, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSoundSettingEventBroadcastIntent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
            parcel.enforceNoDataAvail();
            setSoundSettingEventBroadcastIntent(i, pendingIntent);
            return true;
        }

        private boolean onTransact$setA2dpDeviceVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setA2dpDeviceVolume(bluetoothDevice, i, i2, i3, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getA2dpDeviceVolume$(Parcel parcel, Parcel parcel2) throws RemoteException {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int a2dpDeviceVolume = getA2dpDeviceVolume(bluetoothDevice, i);
            parcel2.writeNoException();
            parcel2.writeInt(a2dpDeviceVolume);
            return true;
        }

        private boolean onTransact$registerPlaybackCallbackWithPackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IPlaybackConfigDispatcher iPlaybackConfigDispatcherAsInterface = IPlaybackConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            registerPlaybackCallbackWithPackage(iPlaybackConfigDispatcherAsInterface, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$notifySafetyVolumeDialogVisible$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IVolumeController iVolumeControllerAsInterface = IVolumeController.Stub.asInterface(parcel.readStrongBinder());
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            notifySafetyVolumeDialogVisible(iVolumeControllerAsInterface, z);
            return true;
        }
    }
}
