package android.media;

import android.content.AttributionSourceState;
import android.media.audio.common.AudioConfig;
import android.media.audio.common.AudioConfigBase;
import android.media.audio.common.AudioDevice;
import android.media.audio.common.AudioDeviceDescription;
import android.media.audio.common.AudioFormatDescription;
import android.media.audio.common.AudioOffloadInfo;
import android.media.audio.common.AudioUuid;
import android.media.audio.common.Int;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IAudioPolicyService extends IInterface {
  public static final String DESCRIPTOR = "android$media$IAudioPolicyService".replace('$', '.');

  SoundTriggerSession acquireSoundTriggerSession() throws RemoteException;

  void addDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr)
      throws RemoteException;

  int addSourceDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2)
      throws RemoteException;

  int addStreamDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2)
      throws RemoteException;

  boolean canBeSpatialized(
      AudioAttributesInternal audioAttributesInternal,
      AudioConfig audioConfig,
      AudioDevice[] audioDeviceArr)
      throws RemoteException;

  void clearDevicesRoleForCapturePreset(int i, int i2) throws RemoteException;

  void clearDevicesRoleForStrategy(int i, int i2) throws RemoteException;

  void clearPreferredMixerAttributes(AudioAttributesInternal audioAttributesInternal, int i, int i2)
      throws RemoteException;

  int createAudioPatch(AudioPatchFw audioPatchFw, int i) throws RemoteException;

  String getAudioPolicyConfig(String str) throws RemoteException;

  AudioPortFw getAudioPort(int i) throws RemoteException;

  int getDeviceConnectionState(AudioDevice audioDevice) throws RemoteException;

  AudioDevice[] getDevicesForAttributes(AudioAttributesInternal audioAttributesInternal, boolean z)
      throws RemoteException;

  AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws RemoteException;

  AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws RemoteException;

  int getDirectPlaybackSupport(
      AudioAttributesInternal audioAttributesInternal, AudioConfig audioConfig)
      throws RemoteException;

  android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(
      AudioAttributesInternal audioAttributesInternal) throws RemoteException;

  int getForceUse(int i) throws RemoteException;

  AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(
      AudioDeviceDescription audioDeviceDescription) throws RemoteException;

  GetInputForAttrResponse getInputForAttr(
      AudioAttributesInternal audioAttributesInternal,
      int i,
      int i2,
      int i3,
      AttributionSourceState attributionSourceState,
      AudioConfigBase audioConfigBase,
      int i4,
      int i5)
      throws RemoteException;

  boolean getMasterMono() throws RemoteException;

  int getMaxVolumeIndexForAttributes(AudioAttributesInternal audioAttributesInternal)
      throws RemoteException;

  int getMinVolumeIndexForAttributes(AudioAttributesInternal audioAttributesInternal)
      throws RemoteException;

  int getOffloadSupport(AudioOffloadInfo audioOffloadInfo) throws RemoteException;

  int getOutput(int i) throws RemoteException;

  GetOutputForAttrResponse getOutputForAttr(
      AudioAttributesInternal audioAttributesInternal,
      int i,
      AttributionSourceState attributionSourceState,
      AudioConfig audioConfig,
      int i2,
      int i3)
      throws RemoteException;

  int getOutputForEffect(EffectDescriptor effectDescriptor) throws RemoteException;

  int getPhoneState() throws RemoteException;

  AudioMixerAttributesInternal getPreferredMixerAttributes(
      AudioAttributesInternal audioAttributesInternal, int i) throws RemoteException;

  int getProductStrategyFromAudioAttributes(
      AudioAttributesInternal audioAttributesInternal, boolean z) throws RemoteException;

  void getReportedSurroundFormats(Int r1, AudioFormatDescription[] audioFormatDescriptionArr)
      throws RemoteException;

  GetSpatializerResponse getSpatializer(INativeSpatializerCallback iNativeSpatializerCallback)
      throws RemoteException;

  int getStrategyForStream(int i) throws RemoteException;

  float getStreamVolumeDB(int i, int i2, AudioDeviceDescription audioDeviceDescription)
      throws RemoteException;

  int getStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription)
      throws RemoteException;

  AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws RemoteException;

  void getSurroundFormats(
      Int r1, AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr)
      throws RemoteException;

  int getVolumeGroupFromAudioAttributes(AudioAttributesInternal audioAttributesInternal, boolean z)
      throws RemoteException;

  int getVolumeIndexForAttributes(
      AudioAttributesInternal audioAttributesInternal,
      AudioDeviceDescription audioDeviceDescription)
      throws RemoteException;

  void handleDeviceConfigChange(
      AudioDevice audioDevice, String str, AudioFormatDescription audioFormatDescription)
      throws RemoteException;

  void initStreamVolume(int i, int i2, int i3) throws RemoteException;

  boolean isCallScreenModeSupported() throws RemoteException;

  boolean isDirectOutputSupported(
      AudioConfigBase audioConfigBase, AudioAttributesInternal audioAttributesInternal)
      throws RemoteException;

  boolean isHapticPlaybackSupported() throws RemoteException;

  boolean isHotwordStreamSupported(boolean z) throws RemoteException;

  boolean isSourceActive(int i) throws RemoteException;

  boolean isStreamActive(int i, int i2) throws RemoteException;

  boolean isStreamActiveRemotely(int i, int i2) throws RemoteException;

  boolean isUltrasoundSupported() throws RemoteException;

  int listAudioPatches(Int r1, AudioPatchFw[] audioPatchFwArr) throws RemoteException;

  int listAudioPorts(int i, int i2, Int r3, AudioPortFw[] audioPortFwArr) throws RemoteException;

  AudioProductStrategy[] listAudioProductStrategies() throws RemoteException;

  AudioVolumeGroup[] listAudioVolumeGroups() throws RemoteException;

  AudioPortFw[] listDeclaredDevicePorts(int i) throws RemoteException;

  void moveEffectsToIo(int[] iArr, int i) throws RemoteException;

  void onNewAudioModulesAvailable() throws RemoteException;

  EffectDescriptor[] queryDefaultPreProcessing(int i, Int r2) throws RemoteException;

  void registerClient(IAudioPolicyServiceClient iAudioPolicyServiceClient) throws RemoteException;

  void registerEffect(EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4)
      throws RemoteException;

  void registerPolicyMixes(AudioMix[] audioMixArr, boolean z) throws RemoteException;

  boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener iCaptureStateListener)
      throws RemoteException;

  void releaseAudioPatch(int i) throws RemoteException;

  void releaseInput(int i) throws RemoteException;

  void releaseOutput(int i) throws RemoteException;

  void releaseSoundTriggerSession(int i) throws RemoteException;

  void removeDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr)
      throws RemoteException;

  void removeDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr)
      throws RemoteException;

  void removeSourceDefaultEffect(int i) throws RemoteException;

  void removeStreamDefaultEffect(int i) throws RemoteException;

  void removeUidDeviceAffinities(int i) throws RemoteException;

  void removeUserIdDeviceAffinities(int i) throws RemoteException;

  void setA11yServicesUids(int[] iArr) throws RemoteException;

  void setActiveAssistantServicesUids(int[] iArr) throws RemoteException;

  void setAllowedCapturePolicy(int i, int i2) throws RemoteException;

  void setAssistantServicesUids(int[] iArr) throws RemoteException;

  void setAudioPolicyConfig(String str) throws RemoteException;

  void setAudioPortCallbacksEnabled(boolean z) throws RemoteException;

  void setAudioPortConfig(AudioPortConfigFw audioPortConfigFw) throws RemoteException;

  void setAudioVolumeGroupCallbacksEnabled(boolean z) throws RemoteException;

  void setCurrentImeUid(int i) throws RemoteException;

  void setDeviceConnectionState(
      int i,
      android.media.audio.common.AudioPort audioPort,
      AudioFormatDescription audioFormatDescription)
      throws RemoteException;

  void setDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr)
      throws RemoteException;

  void setDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr)
      throws RemoteException;

  void setEffectEnabled(int i, boolean z) throws RemoteException;

  void setForceUse(int i, int i2) throws RemoteException;

  void setMasterMono(boolean z) throws RemoteException;

  void setPhoneState(int i, int i2) throws RemoteException;

  void setPreferredMixerAttributes(
      AudioAttributesInternal audioAttributesInternal,
      int i,
      int i2,
      AudioMixerAttributesInternal audioMixerAttributesInternal)
      throws RemoteException;

  void setRttEnabled(boolean z) throws RemoteException;

  void setStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription, int i2)
      throws RemoteException;

  void setSupportedSystemUsages(int[] iArr) throws RemoteException;

  void setSurroundFormatEnabled(AudioFormatDescription audioFormatDescription, boolean z)
      throws RemoteException;

  void setUidDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException;

  void setUserIdDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException;

  void setVolumeIndexForAttributes(
      AudioAttributesInternal audioAttributesInternal,
      AudioDeviceDescription audioDeviceDescription,
      int i)
      throws RemoteException;

  int startAudioSource(
      AudioPortConfigFw audioPortConfigFw, AudioAttributesInternal audioAttributesInternal)
      throws RemoteException;

  void startInput(int i) throws RemoteException;

  void startOutput(int i) throws RemoteException;

  void stopAudioSource(int i) throws RemoteException;

  void stopInput(int i) throws RemoteException;

  void stopOutput(int i) throws RemoteException;

  void unregisterEffect(int i) throws RemoteException;

  public static class Default implements IAudioPolicyService {
    @Override // android.media.IAudioPolicyService
    public void onNewAudioModulesAvailable() throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setDeviceConnectionState(
        int state, android.media.audio.common.AudioPort port, AudioFormatDescription encodedFormat)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int getDeviceConnectionState(AudioDevice device) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void handleDeviceConfigChange(
        AudioDevice device, String deviceName, AudioFormatDescription encodedFormat)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setPhoneState(int state, int uid) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setForceUse(int usage, int config) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int getForceUse(int usage) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public int getOutput(int stream) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public GetOutputForAttrResponse getOutputForAttr(
        AudioAttributesInternal attr,
        int session,
        AttributionSourceState attributionSource,
        AudioConfig config,
        int flags,
        int selectedDeviceId)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void startOutput(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void stopOutput(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void releaseOutput(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public GetInputForAttrResponse getInputForAttr(
        AudioAttributesInternal attr,
        int input,
        int riid,
        int session,
        AttributionSourceState attributionSource,
        AudioConfigBase config,
        int flags,
        int selectedDeviceId)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void startInput(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void stopInput(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void releaseInput(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void initStreamVolume(int stream, int indexMin, int indexMax) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setStreamVolumeIndex(int stream, AudioDeviceDescription device, int index)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int getStreamVolumeIndex(int stream, AudioDeviceDescription device)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void setVolumeIndexForAttributes(
        AudioAttributesInternal attr, AudioDeviceDescription device, int index)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int getVolumeIndexForAttributes(
        AudioAttributesInternal attr, AudioDeviceDescription device) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public int getMaxVolumeIndexForAttributes(AudioAttributesInternal attr) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public int getMinVolumeIndexForAttributes(AudioAttributesInternal attr) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public int getStrategyForStream(int stream) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public AudioDevice[] getDevicesForAttributes(AudioAttributesInternal attr, boolean forVolume)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public int getOutputForEffect(EffectDescriptor desc) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void registerEffect(EffectDescriptor desc, int io, int strategy, int session, int id)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void unregisterEffect(int id) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setEffectEnabled(int id, boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void moveEffectsToIo(int[] ids, int io) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public boolean isStreamActive(int stream, int inPastMs) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public boolean isStreamActiveRemotely(int stream, int inPastMs) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public boolean isSourceActive(int source) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public EffectDescriptor[] queryDefaultPreProcessing(int audioSession, Int count)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public int addSourceDefaultEffect(
        AudioUuid type, String opPackageName, AudioUuid uuid, int priority, int source)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public int addStreamDefaultEffect(
        AudioUuid type, String opPackageName, AudioUuid uuid, int priority, int usage)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void removeSourceDefaultEffect(int id) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void removeStreamDefaultEffect(int id) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setSupportedSystemUsages(int[] systemUsages) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setAllowedCapturePolicy(int uid, int capturePolicy) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int getOffloadSupport(AudioOffloadInfo info) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public boolean isDirectOutputSupported(
        AudioConfigBase config, AudioAttributesInternal attributes) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public int listAudioPorts(int role, int type, Int count, AudioPortFw[] ports)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public AudioPortFw[] listDeclaredDevicePorts(int role) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public AudioPortFw getAudioPort(int portId) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public int createAudioPatch(AudioPatchFw patch, int handle) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void releaseAudioPatch(int handle) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int listAudioPatches(Int count, AudioPatchFw[] patches) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void setAudioPortConfig(AudioPortConfigFw config) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void registerClient(IAudioPolicyServiceClient client) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setAudioPortCallbacksEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setAudioVolumeGroupCallbacksEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public SoundTriggerSession acquireSoundTriggerSession() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void releaseSoundTriggerSession(int session) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int getPhoneState() throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void registerPolicyMixes(AudioMix[] mixes, boolean registration)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setUidDeviceAffinities(int uid, AudioDevice[] devices) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void removeUidDeviceAffinities(int uid) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setUserIdDeviceAffinities(int userId, AudioDevice[] devices)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void removeUserIdDeviceAffinities(int userId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public int startAudioSource(AudioPortConfigFw source, AudioAttributesInternal attributes)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void stopAudioSource(int portId) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setMasterMono(boolean mono) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public boolean getMasterMono() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public float getStreamVolumeDB(int stream, int index, AudioDeviceDescription device)
        throws RemoteException {
      return 0.0f;
    }

    @Override // android.media.IAudioPolicyService
    public void getSurroundFormats(
        Int count, AudioFormatDescription[] formats, boolean[] formatsEnabled)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void getReportedSurroundFormats(Int count, AudioFormatDescription[] formats)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(
        AudioDeviceDescription device) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void setSurroundFormatEnabled(AudioFormatDescription audioFormat, boolean enabled)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setAssistantServicesUids(int[] uids) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setActiveAssistantServicesUids(int[] activeUids) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setA11yServicesUids(int[] uids) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setCurrentImeUid(int uid) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public boolean isHapticPlaybackSupported() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public boolean isUltrasoundSupported() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public boolean isHotwordStreamSupported(boolean lookbackAudio) throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public AudioProductStrategy[] listAudioProductStrategies() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public int getProductStrategyFromAudioAttributes(
        AudioAttributesInternal aa, boolean fallbackOnDefault) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public AudioVolumeGroup[] listAudioVolumeGroups() throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public int getVolumeGroupFromAudioAttributes(
        AudioAttributesInternal aa, boolean fallbackOnDefault) throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public void setRttEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public boolean isCallScreenModeSupported() throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public void setDevicesRoleForStrategy(int strategy, int role, AudioDevice[] devices)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void removeDevicesRoleForStrategy(int strategy, int role, AudioDevice[] devices)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void clearDevicesRoleForStrategy(int strategy, int role) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public AudioDevice[] getDevicesForRoleAndStrategy(int strategy, int role)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void setDevicesRoleForCapturePreset(int audioSource, int role, AudioDevice[] devices)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void addDevicesRoleForCapturePreset(int audioSource, int role, AudioDevice[] devices)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void removeDevicesRoleForCapturePreset(int audioSource, int role, AudioDevice[] devices)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void clearDevicesRoleForCapturePreset(int audioSource, int role)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public AudioDevice[] getDevicesForRoleAndCapturePreset(int audioSource, int role)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener listener)
        throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public GetSpatializerResponse getSpatializer(INativeSpatializerCallback callback)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public boolean canBeSpatialized(
        AudioAttributesInternal attr, AudioConfig config, AudioDevice[] devices)
        throws RemoteException {
      return false;
    }

    @Override // android.media.IAudioPolicyService
    public int getDirectPlaybackSupport(AudioAttributesInternal attr, AudioConfig config)
        throws RemoteException {
      return 0;
    }

    @Override // android.media.IAudioPolicyService
    public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(
        AudioAttributesInternal attr) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public AudioMixerAttributesInternal[] getSupportedMixerAttributes(int portId)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void setPreferredMixerAttributes(
        AudioAttributesInternal attr, int portId, int uid, AudioMixerAttributesInternal mixerAttr)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public AudioMixerAttributesInternal getPreferredMixerAttributes(
        AudioAttributesInternal attr, int portId) throws RemoteException {
      return null;
    }

    @Override // android.media.IAudioPolicyService
    public void clearPreferredMixerAttributes(AudioAttributesInternal attr, int portId, int uid)
        throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public void setAudioPolicyConfig(String keys) throws RemoteException {}

    @Override // android.media.IAudioPolicyService
    public String getAudioPolicyConfig(String keys) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAudioPolicyService {
    static final int TRANSACTION_acquireSoundTriggerSession = 53;
    static final int TRANSACTION_addDevicesRoleForCapturePreset = 88;
    static final int TRANSACTION_addSourceDefaultEffect = 35;
    static final int TRANSACTION_addStreamDefaultEffect = 36;
    static final int TRANSACTION_canBeSpatialized = 94;
    static final int TRANSACTION_clearDevicesRoleForCapturePreset = 90;
    static final int TRANSACTION_clearDevicesRoleForStrategy = 85;
    static final int TRANSACTION_clearPreferredMixerAttributes = 100;
    static final int TRANSACTION_createAudioPatch = 46;
    static final int TRANSACTION_getAudioPolicyConfig = 102;
    static final int TRANSACTION_getAudioPort = 45;
    static final int TRANSACTION_getDeviceConnectionState = 3;
    static final int TRANSACTION_getDevicesForAttributes = 25;
    static final int TRANSACTION_getDevicesForRoleAndCapturePreset = 91;
    static final int TRANSACTION_getDevicesForRoleAndStrategy = 86;
    static final int TRANSACTION_getDirectPlaybackSupport = 95;
    static final int TRANSACTION_getDirectProfilesForAttributes = 96;
    static final int TRANSACTION_getForceUse = 7;
    static final int TRANSACTION_getHwOffloadFormatsSupportedForBluetoothMedia = 68;
    static final int TRANSACTION_getInputForAttr = 13;
    static final int TRANSACTION_getMasterMono = 64;
    static final int TRANSACTION_getMaxVolumeIndexForAttributes = 22;
    static final int TRANSACTION_getMinVolumeIndexForAttributes = 23;
    static final int TRANSACTION_getOffloadSupport = 41;
    static final int TRANSACTION_getOutput = 8;
    static final int TRANSACTION_getOutputForAttr = 9;
    static final int TRANSACTION_getOutputForEffect = 26;
    static final int TRANSACTION_getPhoneState = 55;
    static final int TRANSACTION_getPreferredMixerAttributes = 99;
    static final int TRANSACTION_getProductStrategyFromAudioAttributes = 78;
    static final int TRANSACTION_getReportedSurroundFormats = 67;
    static final int TRANSACTION_getSpatializer = 93;
    static final int TRANSACTION_getStrategyForStream = 24;
    static final int TRANSACTION_getStreamVolumeDB = 65;
    static final int TRANSACTION_getStreamVolumeIndex = 19;
    static final int TRANSACTION_getSupportedMixerAttributes = 97;
    static final int TRANSACTION_getSurroundFormats = 66;
    static final int TRANSACTION_getVolumeGroupFromAudioAttributes = 80;
    static final int TRANSACTION_getVolumeIndexForAttributes = 21;
    static final int TRANSACTION_handleDeviceConfigChange = 4;
    static final int TRANSACTION_initStreamVolume = 17;
    static final int TRANSACTION_isCallScreenModeSupported = 82;
    static final int TRANSACTION_isDirectOutputSupported = 42;
    static final int TRANSACTION_isHapticPlaybackSupported = 74;
    static final int TRANSACTION_isHotwordStreamSupported = 76;
    static final int TRANSACTION_isSourceActive = 33;
    static final int TRANSACTION_isStreamActive = 31;
    static final int TRANSACTION_isStreamActiveRemotely = 32;
    static final int TRANSACTION_isUltrasoundSupported = 75;
    static final int TRANSACTION_listAudioPatches = 48;
    static final int TRANSACTION_listAudioPorts = 43;
    static final int TRANSACTION_listAudioProductStrategies = 77;
    static final int TRANSACTION_listAudioVolumeGroups = 79;
    static final int TRANSACTION_listDeclaredDevicePorts = 44;
    static final int TRANSACTION_moveEffectsToIo = 30;
    static final int TRANSACTION_onNewAudioModulesAvailable = 1;
    static final int TRANSACTION_queryDefaultPreProcessing = 34;
    static final int TRANSACTION_registerClient = 50;
    static final int TRANSACTION_registerEffect = 27;
    static final int TRANSACTION_registerPolicyMixes = 56;
    static final int TRANSACTION_registerSoundTriggerCaptureStateListener = 92;
    static final int TRANSACTION_releaseAudioPatch = 47;
    static final int TRANSACTION_releaseInput = 16;
    static final int TRANSACTION_releaseOutput = 12;
    static final int TRANSACTION_releaseSoundTriggerSession = 54;
    static final int TRANSACTION_removeDevicesRoleForCapturePreset = 89;
    static final int TRANSACTION_removeDevicesRoleForStrategy = 84;
    static final int TRANSACTION_removeSourceDefaultEffect = 37;
    static final int TRANSACTION_removeStreamDefaultEffect = 38;
    static final int TRANSACTION_removeUidDeviceAffinities = 58;
    static final int TRANSACTION_removeUserIdDeviceAffinities = 60;
    static final int TRANSACTION_setA11yServicesUids = 72;
    static final int TRANSACTION_setActiveAssistantServicesUids = 71;
    static final int TRANSACTION_setAllowedCapturePolicy = 40;
    static final int TRANSACTION_setAssistantServicesUids = 70;
    static final int TRANSACTION_setAudioPolicyConfig = 101;
    static final int TRANSACTION_setAudioPortCallbacksEnabled = 51;
    static final int TRANSACTION_setAudioPortConfig = 49;
    static final int TRANSACTION_setAudioVolumeGroupCallbacksEnabled = 52;
    static final int TRANSACTION_setCurrentImeUid = 73;
    static final int TRANSACTION_setDeviceConnectionState = 2;
    static final int TRANSACTION_setDevicesRoleForCapturePreset = 87;
    static final int TRANSACTION_setDevicesRoleForStrategy = 83;
    static final int TRANSACTION_setEffectEnabled = 29;
    static final int TRANSACTION_setForceUse = 6;
    static final int TRANSACTION_setMasterMono = 63;
    static final int TRANSACTION_setPhoneState = 5;
    static final int TRANSACTION_setPreferredMixerAttributes = 98;
    static final int TRANSACTION_setRttEnabled = 81;
    static final int TRANSACTION_setStreamVolumeIndex = 18;
    static final int TRANSACTION_setSupportedSystemUsages = 39;
    static final int TRANSACTION_setSurroundFormatEnabled = 69;
    static final int TRANSACTION_setUidDeviceAffinities = 57;
    static final int TRANSACTION_setUserIdDeviceAffinities = 59;
    static final int TRANSACTION_setVolumeIndexForAttributes = 20;
    static final int TRANSACTION_startAudioSource = 61;
    static final int TRANSACTION_startInput = 14;
    static final int TRANSACTION_startOutput = 10;
    static final int TRANSACTION_stopAudioSource = 62;
    static final int TRANSACTION_stopInput = 15;
    static final int TRANSACTION_stopOutput = 11;
    static final int TRANSACTION_unregisterEffect = 28;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IAudioPolicyService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IAudioPolicyService)) {
        return (IAudioPolicyService) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      AudioPortFw[] _arg3;
      AudioPatchFw[] _arg1;
      AudioFormatDescription[] _arg12;
      boolean[] _arg2;
      AudioFormatDescription[] _arg13;
      String descriptor = DESCRIPTOR;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(descriptor);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(descriptor);
          return true;
        default:
          switch (code) {
            case 1:
              onNewAudioModulesAvailable();
              return true;
            case 2:
              int _arg0 = data.readInt();
              android.media.audio.common.AudioPort _arg14 =
                  (android.media.audio.common.AudioPort)
                      data.readTypedObject(android.media.audio.common.AudioPort.CREATOR);
              AudioFormatDescription _arg22 =
                  (AudioFormatDescription) data.readTypedObject(AudioFormatDescription.CREATOR);
              data.enforceNoDataAvail();
              setDeviceConnectionState(_arg0, _arg14, _arg22);
              reply.writeNoException();
              return true;
            case 3:
              AudioDevice _arg02 = (AudioDevice) data.readTypedObject(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              int _result = getDeviceConnectionState(_arg02);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 4:
              AudioDevice _arg03 = (AudioDevice) data.readTypedObject(AudioDevice.CREATOR);
              String _arg15 = data.readString();
              AudioFormatDescription _arg23 =
                  (AudioFormatDescription) data.readTypedObject(AudioFormatDescription.CREATOR);
              data.enforceNoDataAvail();
              handleDeviceConfigChange(_arg03, _arg15, _arg23);
              reply.writeNoException();
              return true;
            case 5:
              int _arg04 = data.readInt();
              int _arg16 = data.readInt();
              data.enforceNoDataAvail();
              setPhoneState(_arg04, _arg16);
              reply.writeNoException();
              return true;
            case 6:
              int _arg05 = data.readInt();
              int _arg17 = data.readInt();
              data.enforceNoDataAvail();
              setForceUse(_arg05, _arg17);
              reply.writeNoException();
              return true;
            case 7:
              int _arg06 = data.readInt();
              data.enforceNoDataAvail();
              int _result2 = getForceUse(_arg06);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 8:
              int _arg07 = data.readInt();
              data.enforceNoDataAvail();
              int _result3 = getOutput(_arg07);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 9:
              AudioAttributesInternal _arg08 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              int _arg18 = data.readInt();
              AttributionSourceState _arg24 =
                  (AttributionSourceState) data.readTypedObject(AttributionSourceState.CREATOR);
              AudioConfig _arg32 = (AudioConfig) data.readTypedObject(AudioConfig.CREATOR);
              int _arg4 = data.readInt();
              int _arg5 = data.readInt();
              data.enforceNoDataAvail();
              GetOutputForAttrResponse _result4 =
                  getOutputForAttr(_arg08, _arg18, _arg24, _arg32, _arg4, _arg5);
              reply.writeNoException();
              reply.writeTypedObject(_result4, 1);
              return true;
            case 10:
              int _arg09 = data.readInt();
              data.enforceNoDataAvail();
              startOutput(_arg09);
              reply.writeNoException();
              return true;
            case 11:
              int _arg010 = data.readInt();
              data.enforceNoDataAvail();
              stopOutput(_arg010);
              reply.writeNoException();
              return true;
            case 12:
              int _arg011 = data.readInt();
              data.enforceNoDataAvail();
              releaseOutput(_arg011);
              reply.writeNoException();
              return true;
            case 13:
              AudioAttributesInternal _arg012 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              int _arg19 = data.readInt();
              int _arg25 = data.readInt();
              int _arg33 = data.readInt();
              AttributionSourceState _arg42 =
                  (AttributionSourceState) data.readTypedObject(AttributionSourceState.CREATOR);
              AudioConfigBase _arg52 =
                  (AudioConfigBase) data.readTypedObject(AudioConfigBase.CREATOR);
              int _arg6 = data.readInt();
              int _arg7 = data.readInt();
              data.enforceNoDataAvail();
              GetInputForAttrResponse _result5 =
                  getInputForAttr(_arg012, _arg19, _arg25, _arg33, _arg42, _arg52, _arg6, _arg7);
              reply.writeNoException();
              reply.writeTypedObject(_result5, 1);
              return true;
            case 14:
              int _arg013 = data.readInt();
              data.enforceNoDataAvail();
              startInput(_arg013);
              reply.writeNoException();
              return true;
            case 15:
              int _arg014 = data.readInt();
              data.enforceNoDataAvail();
              stopInput(_arg014);
              reply.writeNoException();
              return true;
            case 16:
              int _arg015 = data.readInt();
              data.enforceNoDataAvail();
              releaseInput(_arg015);
              reply.writeNoException();
              return true;
            case 17:
              int _arg016 = data.readInt();
              int _arg110 = data.readInt();
              int _arg26 = data.readInt();
              data.enforceNoDataAvail();
              initStreamVolume(_arg016, _arg110, _arg26);
              reply.writeNoException();
              return true;
            case 18:
              int _arg017 = data.readInt();
              AudioDeviceDescription _arg111 =
                  (AudioDeviceDescription) data.readTypedObject(AudioDeviceDescription.CREATOR);
              int _arg27 = data.readInt();
              data.enforceNoDataAvail();
              setStreamVolumeIndex(_arg017, _arg111, _arg27);
              reply.writeNoException();
              return true;
            case 19:
              int _arg018 = data.readInt();
              AudioDeviceDescription _arg112 =
                  (AudioDeviceDescription) data.readTypedObject(AudioDeviceDescription.CREATOR);
              data.enforceNoDataAvail();
              int _result6 = getStreamVolumeIndex(_arg018, _arg112);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 20:
              AudioAttributesInternal _arg019 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              AudioDeviceDescription _arg113 =
                  (AudioDeviceDescription) data.readTypedObject(AudioDeviceDescription.CREATOR);
              int _arg28 = data.readInt();
              data.enforceNoDataAvail();
              setVolumeIndexForAttributes(_arg019, _arg113, _arg28);
              reply.writeNoException();
              return true;
            case 21:
              AudioAttributesInternal _arg020 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              AudioDeviceDescription _arg114 =
                  (AudioDeviceDescription) data.readTypedObject(AudioDeviceDescription.CREATOR);
              data.enforceNoDataAvail();
              int _result7 = getVolumeIndexForAttributes(_arg020, _arg114);
              reply.writeNoException();
              reply.writeInt(_result7);
              return true;
            case 22:
              AudioAttributesInternal _arg021 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              data.enforceNoDataAvail();
              int _result8 = getMaxVolumeIndexForAttributes(_arg021);
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 23:
              AudioAttributesInternal _arg022 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              data.enforceNoDataAvail();
              int _result9 = getMinVolumeIndexForAttributes(_arg022);
              reply.writeNoException();
              reply.writeInt(_result9);
              return true;
            case 24:
              int _arg023 = data.readInt();
              data.enforceNoDataAvail();
              int _result10 = getStrategyForStream(_arg023);
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 25:
              AudioAttributesInternal _arg024 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              boolean _arg115 = data.readBoolean();
              data.enforceNoDataAvail();
              AudioDevice[] _result11 = getDevicesForAttributes(_arg024, _arg115);
              reply.writeNoException();
              reply.writeTypedArray(_result11, 1);
              return true;
            case 26:
              EffectDescriptor _arg025 =
                  (EffectDescriptor) data.readTypedObject(EffectDescriptor.CREATOR);
              data.enforceNoDataAvail();
              int _result12 = getOutputForEffect(_arg025);
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 27:
              EffectDescriptor _arg026 =
                  (EffectDescriptor) data.readTypedObject(EffectDescriptor.CREATOR);
              int _arg116 = data.readInt();
              int _arg29 = data.readInt();
              int _arg34 = data.readInt();
              int _arg43 = data.readInt();
              data.enforceNoDataAvail();
              registerEffect(_arg026, _arg116, _arg29, _arg34, _arg43);
              reply.writeNoException();
              return true;
            case 28:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              unregisterEffect(_arg027);
              reply.writeNoException();
              return true;
            case 29:
              int _arg028 = data.readInt();
              boolean _arg117 = data.readBoolean();
              data.enforceNoDataAvail();
              setEffectEnabled(_arg028, _arg117);
              reply.writeNoException();
              return true;
            case 30:
              int[] _arg029 = data.createIntArray();
              int _arg118 = data.readInt();
              data.enforceNoDataAvail();
              moveEffectsToIo(_arg029, _arg118);
              reply.writeNoException();
              return true;
            case 31:
              int _arg030 = data.readInt();
              int _arg119 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result13 = isStreamActive(_arg030, _arg119);
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 32:
              int _arg031 = data.readInt();
              int _arg120 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result14 = isStreamActiveRemotely(_arg031, _arg120);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 33:
              int _arg032 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result15 = isSourceActive(_arg032);
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 34:
              int _arg033 = data.readInt();
              Int _arg121 = (Int) data.readTypedObject(Int.CREATOR);
              data.enforceNoDataAvail();
              EffectDescriptor[] _result16 = queryDefaultPreProcessing(_arg033, _arg121);
              reply.writeNoException();
              reply.writeTypedArray(_result16, 1);
              reply.writeTypedObject(_arg121, 1);
              return true;
            case 35:
              AudioUuid _arg034 = (AudioUuid) data.readTypedObject(AudioUuid.CREATOR);
              String _arg122 = data.readString();
              AudioUuid _arg210 = (AudioUuid) data.readTypedObject(AudioUuid.CREATOR);
              int _arg35 = data.readInt();
              int _arg44 = data.readInt();
              data.enforceNoDataAvail();
              int _result17 = addSourceDefaultEffect(_arg034, _arg122, _arg210, _arg35, _arg44);
              reply.writeNoException();
              reply.writeInt(_result17);
              return true;
            case 36:
              AudioUuid _arg035 = (AudioUuid) data.readTypedObject(AudioUuid.CREATOR);
              String _arg123 = data.readString();
              AudioUuid _arg211 = (AudioUuid) data.readTypedObject(AudioUuid.CREATOR);
              int _arg36 = data.readInt();
              int _arg45 = data.readInt();
              data.enforceNoDataAvail();
              int _result18 = addStreamDefaultEffect(_arg035, _arg123, _arg211, _arg36, _arg45);
              reply.writeNoException();
              reply.writeInt(_result18);
              return true;
            case 37:
              int _arg036 = data.readInt();
              data.enforceNoDataAvail();
              removeSourceDefaultEffect(_arg036);
              reply.writeNoException();
              return true;
            case 38:
              int _arg037 = data.readInt();
              data.enforceNoDataAvail();
              removeStreamDefaultEffect(_arg037);
              reply.writeNoException();
              return true;
            case 39:
              int[] _arg038 = data.createIntArray();
              data.enforceNoDataAvail();
              setSupportedSystemUsages(_arg038);
              reply.writeNoException();
              return true;
            case 40:
              int _arg039 = data.readInt();
              int _arg124 = data.readInt();
              data.enforceNoDataAvail();
              setAllowedCapturePolicy(_arg039, _arg124);
              reply.writeNoException();
              return true;
            case 41:
              AudioOffloadInfo _arg040 =
                  (AudioOffloadInfo) data.readTypedObject(AudioOffloadInfo.CREATOR);
              data.enforceNoDataAvail();
              int _result19 = getOffloadSupport(_arg040);
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 42:
              AudioConfigBase _arg041 =
                  (AudioConfigBase) data.readTypedObject(AudioConfigBase.CREATOR);
              AudioAttributesInternal _arg125 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              data.enforceNoDataAvail();
              boolean _result20 = isDirectOutputSupported(_arg041, _arg125);
              reply.writeNoException();
              reply.writeBoolean(_result20);
              return true;
            case 43:
              int _arg042 = data.readInt();
              int _arg126 = data.readInt();
              Int _arg212 = (Int) data.readTypedObject(Int.CREATOR);
              int _arg3_length = data.readInt();
              if (_arg3_length < 0) {
                _arg3 = null;
              } else {
                _arg3 = new AudioPortFw[_arg3_length];
              }
              data.enforceNoDataAvail();
              int _result21 = listAudioPorts(_arg042, _arg126, _arg212, _arg3);
              reply.writeNoException();
              reply.writeInt(_result21);
              reply.writeTypedObject(_arg212, 1);
              reply.writeTypedArray(_arg3, 1);
              return true;
            case 44:
              int _arg043 = data.readInt();
              data.enforceNoDataAvail();
              AudioPortFw[] _result22 = listDeclaredDevicePorts(_arg043);
              reply.writeNoException();
              reply.writeTypedArray(_result22, 1);
              return true;
            case 45:
              int _arg044 = data.readInt();
              data.enforceNoDataAvail();
              AudioPortFw _result23 = getAudioPort(_arg044);
              reply.writeNoException();
              reply.writeTypedObject(_result23, 1);
              return true;
            case 46:
              AudioPatchFw _arg045 = (AudioPatchFw) data.readTypedObject(AudioPatchFw.CREATOR);
              int _arg127 = data.readInt();
              data.enforceNoDataAvail();
              int _result24 = createAudioPatch(_arg045, _arg127);
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 47:
              int _arg046 = data.readInt();
              data.enforceNoDataAvail();
              releaseAudioPatch(_arg046);
              reply.writeNoException();
              return true;
            case 48:
              Int _arg047 = (Int) data.readTypedObject(Int.CREATOR);
              int _arg1_length = data.readInt();
              if (_arg1_length < 0) {
                _arg1 = null;
              } else {
                _arg1 = new AudioPatchFw[_arg1_length];
              }
              data.enforceNoDataAvail();
              int _result25 = listAudioPatches(_arg047, _arg1);
              reply.writeNoException();
              reply.writeInt(_result25);
              reply.writeTypedObject(_arg047, 1);
              reply.writeTypedArray(_arg1, 1);
              return true;
            case 49:
              AudioPortConfigFw _arg048 =
                  (AudioPortConfigFw) data.readTypedObject(AudioPortConfigFw.CREATOR);
              data.enforceNoDataAvail();
              setAudioPortConfig(_arg048);
              reply.writeNoException();
              return true;
            case 50:
              IAudioPolicyServiceClient _arg049 =
                  IAudioPolicyServiceClient.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerClient(_arg049);
              reply.writeNoException();
              return true;
            case 51:
              boolean _arg050 = data.readBoolean();
              data.enforceNoDataAvail();
              setAudioPortCallbacksEnabled(_arg050);
              reply.writeNoException();
              return true;
            case 52:
              boolean _arg051 = data.readBoolean();
              data.enforceNoDataAvail();
              setAudioVolumeGroupCallbacksEnabled(_arg051);
              reply.writeNoException();
              return true;
            case 53:
              SoundTriggerSession _result26 = acquireSoundTriggerSession();
              reply.writeNoException();
              reply.writeTypedObject(_result26, 1);
              return true;
            case 54:
              int _arg052 = data.readInt();
              data.enforceNoDataAvail();
              releaseSoundTriggerSession(_arg052);
              reply.writeNoException();
              return true;
            case 55:
              int _result27 = getPhoneState();
              reply.writeNoException();
              reply.writeInt(_result27);
              return true;
            case 56:
              AudioMix[] _arg053 = (AudioMix[]) data.createTypedArray(AudioMix.CREATOR);
              boolean _arg128 = data.readBoolean();
              data.enforceNoDataAvail();
              registerPolicyMixes(_arg053, _arg128);
              reply.writeNoException();
              return true;
            case 57:
              int _arg054 = data.readInt();
              AudioDevice[] _arg129 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              setUidDeviceAffinities(_arg054, _arg129);
              reply.writeNoException();
              return true;
            case 58:
              int _arg055 = data.readInt();
              data.enforceNoDataAvail();
              removeUidDeviceAffinities(_arg055);
              reply.writeNoException();
              return true;
            case 59:
              int _arg056 = data.readInt();
              AudioDevice[] _arg130 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              setUserIdDeviceAffinities(_arg056, _arg130);
              reply.writeNoException();
              return true;
            case 60:
              int _arg057 = data.readInt();
              data.enforceNoDataAvail();
              removeUserIdDeviceAffinities(_arg057);
              reply.writeNoException();
              return true;
            case 61:
              AudioPortConfigFw _arg058 =
                  (AudioPortConfigFw) data.readTypedObject(AudioPortConfigFw.CREATOR);
              AudioAttributesInternal _arg131 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              data.enforceNoDataAvail();
              int _result28 = startAudioSource(_arg058, _arg131);
              reply.writeNoException();
              reply.writeInt(_result28);
              return true;
            case 62:
              int _arg059 = data.readInt();
              data.enforceNoDataAvail();
              stopAudioSource(_arg059);
              reply.writeNoException();
              return true;
            case 63:
              boolean _arg060 = data.readBoolean();
              data.enforceNoDataAvail();
              setMasterMono(_arg060);
              reply.writeNoException();
              return true;
            case 64:
              boolean _result29 = getMasterMono();
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 65:
              int _arg061 = data.readInt();
              int _arg132 = data.readInt();
              AudioDeviceDescription _arg213 =
                  (AudioDeviceDescription) data.readTypedObject(AudioDeviceDescription.CREATOR);
              data.enforceNoDataAvail();
              float _result30 = getStreamVolumeDB(_arg061, _arg132, _arg213);
              reply.writeNoException();
              reply.writeFloat(_result30);
              return true;
            case 66:
              Int _arg062 = (Int) data.readTypedObject(Int.CREATOR);
              int _arg1_length2 = data.readInt();
              if (_arg1_length2 < 0) {
                _arg12 = null;
              } else {
                _arg12 = new AudioFormatDescription[_arg1_length2];
              }
              int _arg2_length = data.readInt();
              if (_arg2_length < 0) {
                _arg2 = null;
              } else {
                _arg2 = new boolean[_arg2_length];
              }
              data.enforceNoDataAvail();
              getSurroundFormats(_arg062, _arg12, _arg2);
              reply.writeNoException();
              reply.writeTypedObject(_arg062, 1);
              reply.writeTypedArray(_arg12, 1);
              reply.writeBooleanArray(_arg2);
              return true;
            case 67:
              Int _arg063 = (Int) data.readTypedObject(Int.CREATOR);
              int _arg1_length3 = data.readInt();
              if (_arg1_length3 < 0) {
                _arg13 = null;
              } else {
                _arg13 = new AudioFormatDescription[_arg1_length3];
              }
              data.enforceNoDataAvail();
              getReportedSurroundFormats(_arg063, _arg13);
              reply.writeNoException();
              reply.writeTypedObject(_arg063, 1);
              reply.writeTypedArray(_arg13, 1);
              return true;
            case 68:
              AudioDeviceDescription _arg064 =
                  (AudioDeviceDescription) data.readTypedObject(AudioDeviceDescription.CREATOR);
              data.enforceNoDataAvail();
              AudioFormatDescription[] _result31 =
                  getHwOffloadFormatsSupportedForBluetoothMedia(_arg064);
              reply.writeNoException();
              reply.writeTypedArray(_result31, 1);
              return true;
            case 69:
              AudioFormatDescription _arg065 =
                  (AudioFormatDescription) data.readTypedObject(AudioFormatDescription.CREATOR);
              boolean _arg133 = data.readBoolean();
              data.enforceNoDataAvail();
              setSurroundFormatEnabled(_arg065, _arg133);
              reply.writeNoException();
              return true;
            case 70:
              int[] _arg066 = data.createIntArray();
              data.enforceNoDataAvail();
              setAssistantServicesUids(_arg066);
              reply.writeNoException();
              return true;
            case 71:
              int[] _arg067 = data.createIntArray();
              data.enforceNoDataAvail();
              setActiveAssistantServicesUids(_arg067);
              reply.writeNoException();
              return true;
            case 72:
              int[] _arg068 = data.createIntArray();
              data.enforceNoDataAvail();
              setA11yServicesUids(_arg068);
              reply.writeNoException();
              return true;
            case 73:
              int _arg069 = data.readInt();
              data.enforceNoDataAvail();
              setCurrentImeUid(_arg069);
              reply.writeNoException();
              return true;
            case 74:
              boolean _result32 = isHapticPlaybackSupported();
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            case 75:
              boolean _result33 = isUltrasoundSupported();
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 76:
              boolean _arg070 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result34 = isHotwordStreamSupported(_arg070);
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 77:
              AudioProductStrategy[] _result35 = listAudioProductStrategies();
              reply.writeNoException();
              reply.writeTypedArray(_result35, 1);
              return true;
            case 78:
              AudioAttributesInternal _arg071 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              boolean _arg134 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result36 = getProductStrategyFromAudioAttributes(_arg071, _arg134);
              reply.writeNoException();
              reply.writeInt(_result36);
              return true;
            case 79:
              AudioVolumeGroup[] _result37 = listAudioVolumeGroups();
              reply.writeNoException();
              reply.writeTypedArray(_result37, 1);
              return true;
            case 80:
              AudioAttributesInternal _arg072 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              boolean _arg135 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result38 = getVolumeGroupFromAudioAttributes(_arg072, _arg135);
              reply.writeNoException();
              reply.writeInt(_result38);
              return true;
            case 81:
              boolean _arg073 = data.readBoolean();
              data.enforceNoDataAvail();
              setRttEnabled(_arg073);
              reply.writeNoException();
              return true;
            case 82:
              boolean _result39 = isCallScreenModeSupported();
              reply.writeNoException();
              reply.writeBoolean(_result39);
              return true;
            case 83:
              int _arg074 = data.readInt();
              int _arg136 = data.readInt();
              AudioDevice[] _arg214 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              setDevicesRoleForStrategy(_arg074, _arg136, _arg214);
              reply.writeNoException();
              return true;
            case 84:
              int _arg075 = data.readInt();
              int _arg137 = data.readInt();
              AudioDevice[] _arg215 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              removeDevicesRoleForStrategy(_arg075, _arg137, _arg215);
              reply.writeNoException();
              return true;
            case 85:
              int _arg076 = data.readInt();
              int _arg138 = data.readInt();
              data.enforceNoDataAvail();
              clearDevicesRoleForStrategy(_arg076, _arg138);
              reply.writeNoException();
              return true;
            case 86:
              int _arg077 = data.readInt();
              int _arg139 = data.readInt();
              data.enforceNoDataAvail();
              AudioDevice[] _result40 = getDevicesForRoleAndStrategy(_arg077, _arg139);
              reply.writeNoException();
              reply.writeTypedArray(_result40, 1);
              return true;
            case 87:
              int _arg078 = data.readInt();
              int _arg140 = data.readInt();
              AudioDevice[] _arg216 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              setDevicesRoleForCapturePreset(_arg078, _arg140, _arg216);
              reply.writeNoException();
              return true;
            case 88:
              int _arg079 = data.readInt();
              int _arg141 = data.readInt();
              AudioDevice[] _arg217 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              addDevicesRoleForCapturePreset(_arg079, _arg141, _arg217);
              reply.writeNoException();
              return true;
            case 89:
              int _arg080 = data.readInt();
              int _arg142 = data.readInt();
              AudioDevice[] _arg218 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              removeDevicesRoleForCapturePreset(_arg080, _arg142, _arg218);
              reply.writeNoException();
              return true;
            case 90:
              int _arg081 = data.readInt();
              int _arg143 = data.readInt();
              data.enforceNoDataAvail();
              clearDevicesRoleForCapturePreset(_arg081, _arg143);
              reply.writeNoException();
              return true;
            case 91:
              int _arg082 = data.readInt();
              int _arg144 = data.readInt();
              data.enforceNoDataAvail();
              AudioDevice[] _result41 = getDevicesForRoleAndCapturePreset(_arg082, _arg144);
              reply.writeNoException();
              reply.writeTypedArray(_result41, 1);
              return true;
            case 92:
              ICaptureStateListener _arg083 =
                  ICaptureStateListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result42 = registerSoundTriggerCaptureStateListener(_arg083);
              reply.writeNoException();
              reply.writeBoolean(_result42);
              return true;
            case 93:
              INativeSpatializerCallback _arg084 =
                  INativeSpatializerCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              GetSpatializerResponse _result43 = getSpatializer(_arg084);
              reply.writeNoException();
              reply.writeTypedObject(_result43, 1);
              return true;
            case 94:
              AudioAttributesInternal _arg085 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              AudioConfig _arg145 = (AudioConfig) data.readTypedObject(AudioConfig.CREATOR);
              AudioDevice[] _arg219 = (AudioDevice[]) data.createTypedArray(AudioDevice.CREATOR);
              data.enforceNoDataAvail();
              boolean _result44 = canBeSpatialized(_arg085, _arg145, _arg219);
              reply.writeNoException();
              reply.writeBoolean(_result44);
              return true;
            case 95:
              AudioAttributesInternal _arg086 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              AudioConfig _arg146 = (AudioConfig) data.readTypedObject(AudioConfig.CREATOR);
              data.enforceNoDataAvail();
              int _result45 = getDirectPlaybackSupport(_arg086, _arg146);
              reply.writeNoException();
              reply.writeInt(_result45);
              return true;
            case 96:
              AudioAttributesInternal _arg087 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              data.enforceNoDataAvail();
              android.media.audio.common.AudioProfile[] _result46 =
                  getDirectProfilesForAttributes(_arg087);
              reply.writeNoException();
              reply.writeTypedArray(_result46, 1);
              return true;
            case 97:
              int _arg088 = data.readInt();
              data.enforceNoDataAvail();
              AudioMixerAttributesInternal[] _result47 = getSupportedMixerAttributes(_arg088);
              reply.writeNoException();
              reply.writeTypedArray(_result47, 1);
              return true;
            case 98:
              AudioAttributesInternal _arg089 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              int _arg147 = data.readInt();
              int _arg220 = data.readInt();
              AudioMixerAttributesInternal _arg37 =
                  (AudioMixerAttributesInternal)
                      data.readTypedObject(AudioMixerAttributesInternal.CREATOR);
              data.enforceNoDataAvail();
              setPreferredMixerAttributes(_arg089, _arg147, _arg220, _arg37);
              reply.writeNoException();
              return true;
            case 99:
              AudioAttributesInternal _arg090 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              int _arg148 = data.readInt();
              data.enforceNoDataAvail();
              AudioMixerAttributesInternal _result48 =
                  getPreferredMixerAttributes(_arg090, _arg148);
              reply.writeNoException();
              reply.writeTypedObject(_result48, 1);
              return true;
            case 100:
              AudioAttributesInternal _arg091 =
                  (AudioAttributesInternal) data.readTypedObject(AudioAttributesInternal.CREATOR);
              int _arg149 = data.readInt();
              int _arg221 = data.readInt();
              data.enforceNoDataAvail();
              clearPreferredMixerAttributes(_arg091, _arg149, _arg221);
              reply.writeNoException();
              return true;
            case 101:
              String _arg092 = data.readString();
              data.enforceNoDataAvail();
              setAudioPolicyConfig(_arg092);
              reply.writeNoException();
              return true;
            case 102:
              String _arg093 = data.readString();
              data.enforceNoDataAvail();
              String _result49 = getAudioPolicyConfig(_arg093);
              reply.writeNoException();
              reply.writeString(_result49);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAudioPolicyService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // android.media.IAudioPolicyService
      public void onNewAudioModulesAvailable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setDeviceConnectionState(
          int state,
          android.media.audio.common.AudioPort port,
          AudioFormatDescription encodedFormat)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(state);
          _data.writeTypedObject(port, 0);
          _data.writeTypedObject(encodedFormat, 0);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getDeviceConnectionState(AudioDevice device) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void handleDeviceConfigChange(
          AudioDevice device, String deviceName, AudioFormatDescription encodedFormat)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          _data.writeString(deviceName);
          _data.writeTypedObject(encodedFormat, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setPhoneState(int state, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(state);
          _data.writeInt(uid);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setForceUse(int usage, int config) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(usage);
          _data.writeInt(config);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getForceUse(int usage) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(usage);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getOutput(int stream) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public GetOutputForAttrResponse getOutputForAttr(
          AudioAttributesInternal attr,
          int session,
          AttributionSourceState attributionSource,
          AudioConfig config,
          int flags,
          int selectedDeviceId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeInt(session);
          _data.writeTypedObject(attributionSource, 0);
          _data.writeTypedObject(config, 0);
          _data.writeInt(flags);
          _data.writeInt(selectedDeviceId);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          GetOutputForAttrResponse _result =
              (GetOutputForAttrResponse) _reply.readTypedObject(GetOutputForAttrResponse.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void startOutput(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void stopOutput(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void releaseOutput(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public GetInputForAttrResponse getInputForAttr(
          AudioAttributesInternal attr,
          int input,
          int riid,
          int session,
          AttributionSourceState attributionSource,
          AudioConfigBase config,
          int flags,
          int selectedDeviceId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeInt(input);
          _data.writeInt(riid);
          _data.writeInt(session);
          _data.writeTypedObject(attributionSource, 0);
          _data.writeTypedObject(config, 0);
          _data.writeInt(flags);
          _data.writeInt(selectedDeviceId);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          GetInputForAttrResponse _result =
              (GetInputForAttrResponse) _reply.readTypedObject(GetInputForAttrResponse.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void startInput(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void stopInput(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void releaseInput(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void initStreamVolume(int stream, int indexMin, int indexMax) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          _data.writeInt(indexMin);
          _data.writeInt(indexMax);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setStreamVolumeIndex(int stream, AudioDeviceDescription device, int index)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          _data.writeTypedObject(device, 0);
          _data.writeInt(index);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getStreamVolumeIndex(int stream, AudioDeviceDescription device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setVolumeIndexForAttributes(
          AudioAttributesInternal attr, AudioDeviceDescription device, int index)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeTypedObject(device, 0);
          _data.writeInt(index);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getVolumeIndexForAttributes(
          AudioAttributesInternal attr, AudioDeviceDescription device) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getMaxVolumeIndexForAttributes(AudioAttributesInternal attr)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getMinVolumeIndexForAttributes(AudioAttributesInternal attr)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getStrategyForStream(int stream) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioDevice[] getDevicesForAttributes(AudioAttributesInternal attr, boolean forVolume)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeBoolean(forVolume);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          AudioDevice[] _result = (AudioDevice[]) _reply.createTypedArray(AudioDevice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getOutputForEffect(EffectDescriptor desc) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(desc, 0);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void registerEffect(EffectDescriptor desc, int io, int strategy, int session, int id)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(desc, 0);
          _data.writeInt(io);
          _data.writeInt(strategy);
          _data.writeInt(session);
          _data.writeInt(id);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void unregisterEffect(int id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(id);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setEffectEnabled(int id, boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(id);
          _data.writeBoolean(enabled);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void moveEffectsToIo(int[] ids, int io) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeIntArray(ids);
          _data.writeInt(io);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isStreamActive(int stream, int inPastMs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          _data.writeInt(inPastMs);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isStreamActiveRemotely(int stream, int inPastMs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          _data.writeInt(inPastMs);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isSourceActive(int source) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(source);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public EffectDescriptor[] queryDefaultPreProcessing(int audioSession, Int count)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSession);
          _data.writeTypedObject(count, 0);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          EffectDescriptor[] _result =
              (EffectDescriptor[]) _reply.createTypedArray(EffectDescriptor.CREATOR);
          if (_reply.readInt() != 0) {
            count.readFromParcel(_reply);
          }
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int addSourceDefaultEffect(
          AudioUuid type, String opPackageName, AudioUuid uuid, int priority, int source)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(type, 0);
          _data.writeString(opPackageName);
          _data.writeTypedObject(uuid, 0);
          _data.writeInt(priority);
          _data.writeInt(source);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int addStreamDefaultEffect(
          AudioUuid type, String opPackageName, AudioUuid uuid, int priority, int usage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(type, 0);
          _data.writeString(opPackageName);
          _data.writeTypedObject(uuid, 0);
          _data.writeInt(priority);
          _data.writeInt(usage);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void removeSourceDefaultEffect(int id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(id);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void removeStreamDefaultEffect(int id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(id);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setSupportedSystemUsages(int[] systemUsages) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeIntArray(systemUsages);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setAllowedCapturePolicy(int uid, int capturePolicy) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(capturePolicy);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getOffloadSupport(AudioOffloadInfo info) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isDirectOutputSupported(
          AudioConfigBase config, AudioAttributesInternal attributes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(config, 0);
          _data.writeTypedObject(attributes, 0);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int listAudioPorts(int role, int type, Int count, AudioPortFw[] ports)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(role);
          _data.writeInt(type);
          _data.writeTypedObject(count, 0);
          _data.writeInt(ports.length);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          if (_reply.readInt() != 0) {
            count.readFromParcel(_reply);
          }
          _reply.readTypedArray(ports, AudioPortFw.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioPortFw[] listDeclaredDevicePorts(int role) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(role);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          AudioPortFw[] _result = (AudioPortFw[]) _reply.createTypedArray(AudioPortFw.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioPortFw getAudioPort(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          AudioPortFw _result = (AudioPortFw) _reply.readTypedObject(AudioPortFw.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int createAudioPatch(AudioPatchFw patch, int handle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(patch, 0);
          _data.writeInt(handle);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void releaseAudioPatch(int handle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(handle);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int listAudioPatches(Int count, AudioPatchFw[] patches) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(count, 0);
          _data.writeInt(patches.length);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          if (_reply.readInt() != 0) {
            count.readFromParcel(_reply);
          }
          _reply.readTypedArray(patches, AudioPatchFw.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setAudioPortConfig(AudioPortConfigFw config) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(config, 0);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void registerClient(IAudioPolicyServiceClient client) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(client);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setAudioPortCallbacksEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setAudioVolumeGroupCallbacksEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public SoundTriggerSession acquireSoundTriggerSession() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          SoundTriggerSession _result =
              (SoundTriggerSession) _reply.readTypedObject(SoundTriggerSession.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void releaseSoundTriggerSession(int session) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(session);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getPhoneState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void registerPolicyMixes(AudioMix[] mixes, boolean registration)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedArray(mixes, 0);
          _data.writeBoolean(registration);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setUidDeviceAffinities(int uid, AudioDevice[] devices) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void removeUidDeviceAffinities(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setUserIdDeviceAffinities(int userId, AudioDevice[] devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void removeUserIdDeviceAffinities(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int startAudioSource(AudioPortConfigFw source, AudioAttributesInternal attributes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(source, 0);
          _data.writeTypedObject(attributes, 0);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void stopAudioSource(int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setMasterMono(boolean mono) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(mono);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean getMasterMono() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public float getStreamVolumeDB(int stream, int index, AudioDeviceDescription device)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(stream);
          _data.writeInt(index);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void getSurroundFormats(
          Int count, AudioFormatDescription[] formats, boolean[] formatsEnabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(count, 0);
          _data.writeInt(formats.length);
          _data.writeInt(formatsEnabled.length);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
          if (_reply.readInt() != 0) {
            count.readFromParcel(_reply);
          }
          _reply.readTypedArray(formats, AudioFormatDescription.CREATOR);
          _reply.readBooleanArray(formatsEnabled);
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void getReportedSurroundFormats(Int count, AudioFormatDescription[] formats)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(count, 0);
          _data.writeInt(formats.length);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          if (_reply.readInt() != 0) {
            count.readFromParcel(_reply);
          }
          _reply.readTypedArray(formats, AudioFormatDescription.CREATOR);
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(
          AudioDeviceDescription device) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(device, 0);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
          AudioFormatDescription[] _result =
              (AudioFormatDescription[]) _reply.createTypedArray(AudioFormatDescription.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setSurroundFormatEnabled(AudioFormatDescription audioFormat, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(audioFormat, 0);
          _data.writeBoolean(enabled);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setAssistantServicesUids(int[] uids) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeIntArray(uids);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setActiveAssistantServicesUids(int[] activeUids) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeIntArray(activeUids);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setA11yServicesUids(int[] uids) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeIntArray(uids);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setCurrentImeUid(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isHapticPlaybackSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isUltrasoundSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isHotwordStreamSupported(boolean lookbackAudio) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(lookbackAudio);
          this.mRemote.transact(76, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioProductStrategy[] listAudioProductStrategies() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(77, _data, _reply, 0);
          _reply.readException();
          AudioProductStrategy[] _result =
              (AudioProductStrategy[]) _reply.createTypedArray(AudioProductStrategy.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getProductStrategyFromAudioAttributes(
          AudioAttributesInternal aa, boolean fallbackOnDefault) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(aa, 0);
          _data.writeBoolean(fallbackOnDefault);
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioVolumeGroup[] listAudioVolumeGroups() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(79, _data, _reply, 0);
          _reply.readException();
          AudioVolumeGroup[] _result =
              (AudioVolumeGroup[]) _reply.createTypedArray(AudioVolumeGroup.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getVolumeGroupFromAudioAttributes(
          AudioAttributesInternal aa, boolean fallbackOnDefault) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(aa, 0);
          _data.writeBoolean(fallbackOnDefault);
          this.mRemote.transact(80, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setRttEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(81, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean isCallScreenModeSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setDevicesRoleForStrategy(int strategy, int role, AudioDevice[] devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeInt(role);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(83, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void removeDevicesRoleForStrategy(int strategy, int role, AudioDevice[] devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeInt(role);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(84, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void clearDevicesRoleForStrategy(int strategy, int role) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeInt(role);
          this.mRemote.transact(85, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioDevice[] getDevicesForRoleAndStrategy(int strategy, int role)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(strategy);
          _data.writeInt(role);
          this.mRemote.transact(86, _data, _reply, 0);
          _reply.readException();
          AudioDevice[] _result = (AudioDevice[]) _reply.createTypedArray(AudioDevice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setDevicesRoleForCapturePreset(int audioSource, int role, AudioDevice[] devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSource);
          _data.writeInt(role);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void addDevicesRoleForCapturePreset(int audioSource, int role, AudioDevice[] devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSource);
          _data.writeInt(role);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void removeDevicesRoleForCapturePreset(
          int audioSource, int role, AudioDevice[] devices) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSource);
          _data.writeInt(role);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(89, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void clearDevicesRoleForCapturePreset(int audioSource, int role)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSource);
          _data.writeInt(role);
          this.mRemote.transact(90, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioDevice[] getDevicesForRoleAndCapturePreset(int audioSource, int role)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSource);
          _data.writeInt(role);
          this.mRemote.transact(91, _data, _reply, 0);
          _reply.readException();
          AudioDevice[] _result = (AudioDevice[]) _reply.createTypedArray(AudioDevice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(92, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public GetSpatializerResponse getSpatializer(INativeSpatializerCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(93, _data, _reply, 0);
          _reply.readException();
          GetSpatializerResponse _result =
              (GetSpatializerResponse) _reply.readTypedObject(GetSpatializerResponse.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public boolean canBeSpatialized(
          AudioAttributesInternal attr, AudioConfig config, AudioDevice[] devices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeTypedObject(config, 0);
          _data.writeTypedArray(devices, 0);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public int getDirectPlaybackSupport(AudioAttributesInternal attr, AudioConfig config)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeTypedObject(config, 0);
          this.mRemote.transact(95, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(
          AudioAttributesInternal attr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          this.mRemote.transact(96, _data, _reply, 0);
          _reply.readException();
          android.media.audio.common.AudioProfile[] _result =
              (android.media.audio.common.AudioProfile[])
                  _reply.createTypedArray(android.media.audio.common.AudioProfile.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioMixerAttributesInternal[] getSupportedMixerAttributes(int portId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(portId);
          this.mRemote.transact(97, _data, _reply, 0);
          _reply.readException();
          AudioMixerAttributesInternal[] _result =
              (AudioMixerAttributesInternal[])
                  _reply.createTypedArray(AudioMixerAttributesInternal.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setPreferredMixerAttributes(
          AudioAttributesInternal attr, int portId, int uid, AudioMixerAttributesInternal mixerAttr)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeInt(portId);
          _data.writeInt(uid);
          _data.writeTypedObject(mixerAttr, 0);
          this.mRemote.transact(98, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public AudioMixerAttributesInternal getPreferredMixerAttributes(
          AudioAttributesInternal attr, int portId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeInt(portId);
          this.mRemote.transact(99, _data, _reply, 0);
          _reply.readException();
          AudioMixerAttributesInternal _result =
              (AudioMixerAttributesInternal)
                  _reply.readTypedObject(AudioMixerAttributesInternal.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void clearPreferredMixerAttributes(AudioAttributesInternal attr, int portId, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(attr, 0);
          _data.writeInt(portId);
          _data.writeInt(uid);
          this.mRemote.transact(100, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public void setAudioPolicyConfig(String keys) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(keys);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IAudioPolicyService
      public String getAudioPolicyConfig(String keys) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(keys);
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
  }
}
