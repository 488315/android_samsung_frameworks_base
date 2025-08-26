package android.media;

import android.content.AttributionSourceState;
import android.media.IAudioPolicyServiceClient;
import android.media.ICaptureStateListener;
import android.media.INativeSpatializerCallback;
import android.media.audio.common.AudioConfig;
import android.media.audio.common.AudioConfigBase;
import android.media.audio.common.AudioDevice;
import android.media.audio.common.AudioDeviceDescription;
import android.media.audio.common.AudioFormatDescription;
import android.media.audio.common.AudioMMapPolicyInfo;
import android.media.audio.common.AudioOffloadInfo;
import android.media.audio.common.AudioUuid;
import android.media.audio.common.Int;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.media.permission.INativePermissionController;
import java.util.List;

/* loaded from: classes2.dex */
public interface IAudioPolicyService extends IInterface {
    public static final String DESCRIPTOR = "android.media.IAudioPolicyService";

    public static class Default implements IAudioPolicyService {
        @Override // android.media.IAudioPolicyService
        public SoundTriggerSession acquireSoundTriggerSession() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void addDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int addSourceDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int addStreamDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig, AudioDevice[] audioDeviceArr) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public void clearDevicesRoleForCapturePreset(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void clearDevicesRoleForStrategy(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int createAudioPatch(AudioPatchFw audioPatchFw, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public String getAudioPolicyConfig(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public AudioPortFw getAudioPort(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getDeviceConnectionState(AudioDevice audioDevice) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getForceUse(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(AudioDeviceDescription audioDeviceDescription) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, AttributionSourceState attributionSourceState, AudioConfigBase audioConfigBase, int i4, int i5) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public boolean getMasterMono() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void getMmapPolicyForDevice(int i, AudioMMapPolicyInfo audioMMapPolicyInfo) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public AudioMMapPolicyInfo[] getMmapPolicyInfos(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getOffloadSupport(AudioOffloadInfo audioOffloadInfo) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getOutput(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, AttributionSourceState attributionSourceState, AudioConfig audioConfig, int i2, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getOutputForEffect(EffectDescriptor effectDescriptor) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public INativePermissionController getPermissionController() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getPhoneState() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public List<AudioMix> getRegisteredPolicyMixes() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void getReportedSurroundFormats(Int r1, AudioFormatDescription[] audioFormatDescriptionArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public GetSpatializerResponse getSpatializer(INativeSpatializerCallback iNativeSpatializerCallback) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getStrategyForStream(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public float getStreamVolumeDB(int i, int i2, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioPolicyService
        public int getStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void getSurroundFormats(Int r1, AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void handleDeviceConfigChange(AudioDevice audioDevice, String str, AudioFormatDescription audioFormatDescription) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void initStreamVolume(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public boolean isCallScreenModeSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isDirectOutputSupported(AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isHapticPlaybackSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isHotwordStreamSupported(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isSourceActive(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isStreamActive(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isStreamActiveRemotely(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isUltrasoundSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public int listAudioPatches(Int r1, AudioPatchFw[] audioPatchFwArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int listAudioPorts(int i, int i2, Int r3, AudioPortFw[] audioPortFwArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public AudioProductStrategy[] listAudioProductStrategies() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public AudioVolumeGroup[] listAudioVolumeGroups() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public AudioPortFw[] listDeclaredDevicePorts(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void moveEffectsToIo(int[] iArr, int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void onNewAudioModulesAvailable() throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public EffectDescriptor[] queryDefaultPreProcessing(int i, Int r2) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void registerClient(IAudioPolicyServiceClient iAudioPolicyServiceClient) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void registerEffect(EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void registerPolicyMixes(AudioMix[] audioMixArr, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener iCaptureStateListener) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public void releaseAudioPatch(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void releaseInput(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void releaseOutput(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void releaseSoundTriggerSession(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeSourceDefaultEffect(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeStreamDefaultEffect(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeUidDeviceAffinities(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeUserIdDeviceAffinities(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setA11yServicesUids(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setActiveAssistantServicesUids(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAllowedCapturePolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAssistantServicesUids(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioPolicyConfig(String str) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioPortCallbacksEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioPortConfig(AudioPortConfigFw audioPortConfigFw) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioVolumeGroupCallbacksEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setCurrentImeUid(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setDeviceAbsoluteVolumeEnabled(AudioDevice audioDevice, boolean z, int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setEffectEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setEnableHardening(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setForceUse(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setMasterMono(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setPhoneState(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, AudioMixerAttributesInternal audioMixerAttributesInternal) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setRttEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription, int i2, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setSupportedSystemUsages(int[] iArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setSurroundFormatEnabled(AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setUidDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setUserIdDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription, int i, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int startAudioSource(AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void startInput(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void startOutput(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void stopAudioSource(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void stopInput(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void stopOutput(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void unregisterEffect(int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void updatePolicyMixes(AudioMixUpdate[] audioMixUpdateArr) throws RemoteException {
        }
    }

    SoundTriggerSession acquireSoundTriggerSession() throws RemoteException;

    void addDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException;

    int addSourceDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException;

    int addStreamDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException;

    boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig, AudioDevice[] audioDeviceArr) throws RemoteException;

    void clearDevicesRoleForCapturePreset(int i, int i2) throws RemoteException;

    void clearDevicesRoleForStrategy(int i, int i2) throws RemoteException;

    void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws RemoteException;

    int createAudioPatch(AudioPatchFw audioPatchFw, int i) throws RemoteException;

    String getAudioPolicyConfig(String str) throws RemoteException;

    AudioPortFw getAudioPort(int i) throws RemoteException;

    int getDeviceConnectionState(AudioDevice audioDevice) throws RemoteException;

    AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException;

    AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws RemoteException;

    AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws RemoteException;

    int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig) throws RemoteException;

    android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException;

    int getForceUse(int i) throws RemoteException;

    AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(AudioDeviceDescription audioDeviceDescription) throws RemoteException;

    GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, AttributionSourceState attributionSourceState, AudioConfigBase audioConfigBase, int i4, int i5) throws RemoteException;

    boolean getMasterMono() throws RemoteException;

    int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException;

    int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException;

    void getMmapPolicyForDevice(int i, AudioMMapPolicyInfo audioMMapPolicyInfo) throws RemoteException;

    AudioMMapPolicyInfo[] getMmapPolicyInfos(int i) throws RemoteException;

    int getOffloadSupport(AudioOffloadInfo audioOffloadInfo) throws RemoteException;

    int getOutput(int i) throws RemoteException;

    GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, AttributionSourceState attributionSourceState, AudioConfig audioConfig, int i2, int[] iArr) throws RemoteException;

    int getOutputForEffect(EffectDescriptor effectDescriptor) throws RemoteException;

    INativePermissionController getPermissionController() throws RemoteException;

    int getPhoneState() throws RemoteException;

    AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws RemoteException;

    int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException;

    List<AudioMix> getRegisteredPolicyMixes() throws RemoteException;

    void getReportedSurroundFormats(Int r1, AudioFormatDescription[] audioFormatDescriptionArr) throws RemoteException;

    GetSpatializerResponse getSpatializer(INativeSpatializerCallback iNativeSpatializerCallback) throws RemoteException;

    int getStrategyForStream(int i) throws RemoteException;

    float getStreamVolumeDB(int i, int i2, AudioDeviceDescription audioDeviceDescription) throws RemoteException;

    int getStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription) throws RemoteException;

    AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws RemoteException;

    void getSurroundFormats(Int r1, AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws RemoteException;

    int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException;

    int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription) throws RemoteException;

    void handleDeviceConfigChange(AudioDevice audioDevice, String str, AudioFormatDescription audioFormatDescription) throws RemoteException;

    void initStreamVolume(int i, int i2, int i3) throws RemoteException;

    boolean isCallScreenModeSupported() throws RemoteException;

    boolean isDirectOutputSupported(AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException;

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

    void registerEffect(EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws RemoteException;

    void registerPolicyMixes(AudioMix[] audioMixArr, boolean z) throws RemoteException;

    boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener iCaptureStateListener) throws RemoteException;

    void releaseAudioPatch(int i) throws RemoteException;

    void releaseInput(int i) throws RemoteException;

    void releaseOutput(int i) throws RemoteException;

    void releaseSoundTriggerSession(int i) throws RemoteException;

    void removeDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException;

    void removeDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException;

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

    void setDeviceAbsoluteVolumeEnabled(AudioDevice audioDevice, boolean z, int i) throws RemoteException;

    void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException;

    void setDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException;

    void setDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException;

    void setEffectEnabled(int i, boolean z) throws RemoteException;

    void setEnableHardening(boolean z) throws RemoteException;

    void setForceUse(int i, int i2) throws RemoteException;

    void setMasterMono(boolean z) throws RemoteException;

    void setPhoneState(int i, int i2) throws RemoteException;

    void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, AudioMixerAttributesInternal audioMixerAttributesInternal) throws RemoteException;

    void setRttEnabled(boolean z) throws RemoteException;

    void setStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription, int i2, boolean z) throws RemoteException;

    void setSupportedSystemUsages(int[] iArr) throws RemoteException;

    void setSurroundFormatEnabled(AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException;

    void setUidDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException;

    void setUserIdDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException;

    void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription, int i, boolean z) throws RemoteException;

    int startAudioSource(AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException;

    void startInput(int i) throws RemoteException;

    void startOutput(int i) throws RemoteException;

    void stopAudioSource(int i) throws RemoteException;

    void stopInput(int i) throws RemoteException;

    void stopOutput(int i) throws RemoteException;

    void unregisterEffect(int i) throws RemoteException;

    void updatePolicyMixes(AudioMixUpdate[] audioMixUpdateArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioPolicyService {
        static final int TRANSACTION_acquireSoundTriggerSession = 54;
        static final int TRANSACTION_addDevicesRoleForCapturePreset = 91;
        static final int TRANSACTION_addSourceDefaultEffect = 36;
        static final int TRANSACTION_addStreamDefaultEffect = 37;
        static final int TRANSACTION_canBeSpatialized = 97;
        static final int TRANSACTION_clearDevicesRoleForCapturePreset = 93;
        static final int TRANSACTION_clearDevicesRoleForStrategy = 88;
        static final int TRANSACTION_clearPreferredMixerAttributes = 103;
        static final int TRANSACTION_createAudioPatch = 47;
        static final int TRANSACTION_getAudioPolicyConfig = 109;
        static final int TRANSACTION_getAudioPort = 46;
        static final int TRANSACTION_getDeviceConnectionState = 3;
        static final int TRANSACTION_getDevicesForAttributes = 26;
        static final int TRANSACTION_getDevicesForRoleAndCapturePreset = 94;
        static final int TRANSACTION_getDevicesForRoleAndStrategy = 89;
        static final int TRANSACTION_getDirectPlaybackSupport = 98;
        static final int TRANSACTION_getDirectProfilesForAttributes = 99;
        static final int TRANSACTION_getForceUse = 7;
        static final int TRANSACTION_getHwOffloadFormatsSupportedForBluetoothMedia = 71;
        static final int TRANSACTION_getInputForAttr = 13;
        static final int TRANSACTION_getMasterMono = 67;
        static final int TRANSACTION_getMaxVolumeIndexForAttributes = 23;
        static final int TRANSACTION_getMinVolumeIndexForAttributes = 24;
        static final int TRANSACTION_getMmapPolicyForDevice = 106;
        static final int TRANSACTION_getMmapPolicyInfos = 105;
        static final int TRANSACTION_getOffloadSupport = 42;
        static final int TRANSACTION_getOutput = 8;
        static final int TRANSACTION_getOutputForAttr = 9;
        static final int TRANSACTION_getOutputForEffect = 27;
        static final int TRANSACTION_getPermissionController = 104;
        static final int TRANSACTION_getPhoneState = 56;
        static final int TRANSACTION_getPreferredMixerAttributes = 102;
        static final int TRANSACTION_getProductStrategyFromAudioAttributes = 81;
        static final int TRANSACTION_getRegisteredPolicyMixes = 58;
        static final int TRANSACTION_getReportedSurroundFormats = 70;
        static final int TRANSACTION_getSpatializer = 96;
        static final int TRANSACTION_getStrategyForStream = 25;
        static final int TRANSACTION_getStreamVolumeDB = 68;
        static final int TRANSACTION_getStreamVolumeIndex = 20;
        static final int TRANSACTION_getSupportedMixerAttributes = 100;
        static final int TRANSACTION_getSurroundFormats = 69;
        static final int TRANSACTION_getVolumeGroupFromAudioAttributes = 83;
        static final int TRANSACTION_getVolumeIndexForAttributes = 22;
        static final int TRANSACTION_handleDeviceConfigChange = 4;
        static final int TRANSACTION_initStreamVolume = 18;
        static final int TRANSACTION_isCallScreenModeSupported = 85;
        static final int TRANSACTION_isDirectOutputSupported = 43;
        static final int TRANSACTION_isHapticPlaybackSupported = 77;
        static final int TRANSACTION_isHotwordStreamSupported = 79;
        static final int TRANSACTION_isSourceActive = 34;
        static final int TRANSACTION_isStreamActive = 32;
        static final int TRANSACTION_isStreamActiveRemotely = 33;
        static final int TRANSACTION_isUltrasoundSupported = 78;
        static final int TRANSACTION_listAudioPatches = 49;
        static final int TRANSACTION_listAudioPorts = 44;
        static final int TRANSACTION_listAudioProductStrategies = 80;
        static final int TRANSACTION_listAudioVolumeGroups = 82;
        static final int TRANSACTION_listDeclaredDevicePorts = 45;
        static final int TRANSACTION_moveEffectsToIo = 31;
        static final int TRANSACTION_onNewAudioModulesAvailable = 1;
        static final int TRANSACTION_queryDefaultPreProcessing = 35;
        static final int TRANSACTION_registerClient = 51;
        static final int TRANSACTION_registerEffect = 28;
        static final int TRANSACTION_registerPolicyMixes = 57;
        static final int TRANSACTION_registerSoundTriggerCaptureStateListener = 95;
        static final int TRANSACTION_releaseAudioPatch = 48;
        static final int TRANSACTION_releaseInput = 16;
        static final int TRANSACTION_releaseOutput = 12;
        static final int TRANSACTION_releaseSoundTriggerSession = 55;
        static final int TRANSACTION_removeDevicesRoleForCapturePreset = 92;
        static final int TRANSACTION_removeDevicesRoleForStrategy = 87;
        static final int TRANSACTION_removeSourceDefaultEffect = 38;
        static final int TRANSACTION_removeStreamDefaultEffect = 39;
        static final int TRANSACTION_removeUidDeviceAffinities = 61;
        static final int TRANSACTION_removeUserIdDeviceAffinities = 63;
        static final int TRANSACTION_setA11yServicesUids = 75;
        static final int TRANSACTION_setActiveAssistantServicesUids = 74;
        static final int TRANSACTION_setAllowedCapturePolicy = 41;
        static final int TRANSACTION_setAssistantServicesUids = 73;
        static final int TRANSACTION_setAudioPolicyConfig = 108;
        static final int TRANSACTION_setAudioPortCallbacksEnabled = 52;
        static final int TRANSACTION_setAudioPortConfig = 50;
        static final int TRANSACTION_setAudioVolumeGroupCallbacksEnabled = 53;
        static final int TRANSACTION_setCurrentImeUid = 76;
        static final int TRANSACTION_setDeviceAbsoluteVolumeEnabled = 17;
        static final int TRANSACTION_setDeviceConnectionState = 2;
        static final int TRANSACTION_setDevicesRoleForCapturePreset = 90;
        static final int TRANSACTION_setDevicesRoleForStrategy = 86;
        static final int TRANSACTION_setEffectEnabled = 30;
        static final int TRANSACTION_setEnableHardening = 107;
        static final int TRANSACTION_setForceUse = 6;
        static final int TRANSACTION_setMasterMono = 66;
        static final int TRANSACTION_setPhoneState = 5;
        static final int TRANSACTION_setPreferredMixerAttributes = 101;
        static final int TRANSACTION_setRttEnabled = 84;
        static final int TRANSACTION_setStreamVolumeIndex = 19;
        static final int TRANSACTION_setSupportedSystemUsages = 40;
        static final int TRANSACTION_setSurroundFormatEnabled = 72;
        static final int TRANSACTION_setUidDeviceAffinities = 60;
        static final int TRANSACTION_setUserIdDeviceAffinities = 62;
        static final int TRANSACTION_setVolumeIndexForAttributes = 21;
        static final int TRANSACTION_startAudioSource = 64;
        static final int TRANSACTION_startInput = 14;
        static final int TRANSACTION_startOutput = 10;
        static final int TRANSACTION_stopAudioSource = 65;
        static final int TRANSACTION_stopInput = 15;
        static final int TRANSACTION_stopOutput = 11;
        static final int TRANSACTION_unregisterEffect = 29;
        static final int TRANSACTION_updatePolicyMixes = 59;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAudioPolicyService.DESCRIPTOR);
        }

        public static IAudioPolicyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAudioPolicyService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioPolicyService)) {
                return (IAudioPolicyService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioPolicyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioPolicyService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onNewAudioModulesAvailable();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    android.media.audio.common.AudioPort audioPort = (android.media.audio.common.AudioPort) parcel.readTypedObject(android.media.audio.common.AudioPort.CREATOR);
                    AudioFormatDescription audioFormatDescription = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceConnectionState(i3, audioPort, audioFormatDescription, z);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    AudioDevice audioDevice = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceConnectionState = getDeviceConnectionState(audioDevice);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceConnectionState);
                    return true;
                case 4:
                    AudioDevice audioDevice2 = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                    String string = parcel.readString();
                    AudioFormatDescription audioFormatDescription2 = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleDeviceConfigChange(audioDevice2, string, audioFormatDescription2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPhoneState(i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForceUse(i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int forceUse = getForceUse(i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceUse);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int output = getOutput(i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(output);
                    return true;
                case 9:
                    android.media.audio.common.AudioAttributes audioAttributes = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int i10 = parcel.readInt();
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    AudioConfig audioConfig = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                    int i11 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    GetOutputForAttrResponse outputForAttr = getOutputForAttr(audioAttributes, i10, attributionSourceState, audioConfig, i11, iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(outputForAttr, 1);
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startOutput(i12);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOutput(i13);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseOutput(i14);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.media.audio.common.AudioAttributes audioAttributes2 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    AudioConfigBase audioConfigBase = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    GetInputForAttrResponse inputForAttr = getInputForAttr(audioAttributes2, i15, i16, i17, attributionSourceState2, audioConfigBase, i18, i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputForAttr, 1);
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startInput(i20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopInput(i21);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseInput(i22);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    AudioDevice audioDevice3 = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceAbsoluteVolumeEnabled(audioDevice3, z2, i23);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initStreamVolume(i24, i25, i26);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i27 = parcel.readInt();
                    AudioDeviceDescription audioDeviceDescription = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    int i28 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStreamVolumeIndex(i27, audioDeviceDescription, i28, z3);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i29 = parcel.readInt();
                    AudioDeviceDescription audioDeviceDescription2 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    int streamVolumeIndex = getStreamVolumeIndex(i29, audioDeviceDescription2);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamVolumeIndex);
                    return true;
                case 21:
                    android.media.audio.common.AudioAttributes audioAttributes3 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    AudioDeviceDescription audioDeviceDescription3 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    int i30 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVolumeIndexForAttributes(audioAttributes3, audioDeviceDescription3, i30, z4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.media.audio.common.AudioAttributes audioAttributes4 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    AudioDeviceDescription audioDeviceDescription4 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    int volumeIndexForAttributes = getVolumeIndexForAttributes(audioAttributes4, audioDeviceDescription4);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeIndexForAttributes);
                    return true;
                case 23:
                    android.media.audio.common.AudioAttributes audioAttributes5 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maxVolumeIndexForAttributes = getMaxVolumeIndexForAttributes(audioAttributes5);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxVolumeIndexForAttributes);
                    return true;
                case 24:
                    android.media.audio.common.AudioAttributes audioAttributes6 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int minVolumeIndexForAttributes = getMinVolumeIndexForAttributes(audioAttributes6);
                    parcel2.writeNoException();
                    parcel2.writeInt(minVolumeIndexForAttributes);
                    return true;
                case 25:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int strategyForStream = getStrategyForStream(i31);
                    parcel2.writeNoException();
                    parcel2.writeInt(strategyForStream);
                    return true;
                case 26:
                    android.media.audio.common.AudioAttributes audioAttributes7 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    AudioDevice[] devicesForAttributes = getDevicesForAttributes(audioAttributes7, z5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForAttributes, 1);
                    return true;
                case 27:
                    EffectDescriptor effectDescriptor = (EffectDescriptor) parcel.readTypedObject(EffectDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    int outputForEffect = getOutputForEffect(effectDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeInt(outputForEffect);
                    return true;
                case 28:
                    EffectDescriptor effectDescriptor2 = (EffectDescriptor) parcel.readTypedObject(EffectDescriptor.CREATOR);
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerEffect(effectDescriptor2, i32, i33, i34, i35);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterEffect(i36);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i37 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEffectEnabled(i37, z6);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveEffectsToIo(iArrCreateIntArray2, i38);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStreamActive = isStreamActive(i39, i40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStreamActive);
                    return true;
                case 33:
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStreamActiveRemotely = isStreamActiveRemotely(i41, i42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStreamActiveRemotely);
                    return true;
                case 34:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSourceActive = isSourceActive(i43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSourceActive);
                    return true;
                case 35:
                    int i44 = parcel.readInt();
                    Int r12 = (Int) parcel.readTypedObject(Int.CREATOR);
                    parcel.enforceNoDataAvail();
                    EffectDescriptor[] effectDescriptorArrQueryDefaultPreProcessing = queryDefaultPreProcessing(i44, r12);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(effectDescriptorArrQueryDefaultPreProcessing, 1);
                    parcel2.writeTypedObject(r12, 1);
                    return true;
                case 36:
                    AudioUuid audioUuid = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    String string2 = parcel.readString();
                    AudioUuid audioUuid2 = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddSourceDefaultEffect = addSourceDefaultEffect(audioUuid, string2, audioUuid2, i45, i46);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddSourceDefaultEffect);
                    return true;
                case 37:
                    AudioUuid audioUuid3 = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    String string3 = parcel.readString();
                    AudioUuid audioUuid4 = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddStreamDefaultEffect = addStreamDefaultEffect(audioUuid3, string3, audioUuid4, i47, i48);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddStreamDefaultEffect);
                    return true;
                case 38:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSourceDefaultEffect(i49);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeStreamDefaultEffect(i50);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSupportedSystemUsages(iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i51 = parcel.readInt();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedCapturePolicy(i51, i52);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    AudioOffloadInfo audioOffloadInfo = (AudioOffloadInfo) parcel.readTypedObject(AudioOffloadInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int offloadSupport = getOffloadSupport(audioOffloadInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(offloadSupport);
                    return true;
                case 43:
                    AudioConfigBase audioConfigBase2 = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    android.media.audio.common.AudioAttributes audioAttributes8 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDirectOutputSupported = isDirectOutputSupported(audioConfigBase2, audioAttributes8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDirectOutputSupported);
                    return true;
                case 44:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    Int r4 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int i55 = parcel.readInt();
                    if (i55 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i55);
                    }
                    AudioPortFw[] audioPortFwArr = i55 >= 0 ? new AudioPortFw[i55] : null;
                    parcel.enforceNoDataAvail();
                    int iListAudioPorts = listAudioPorts(i53, i54, r4, audioPortFwArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iListAudioPorts);
                    parcel2.writeTypedObject(r4, 1);
                    parcel2.writeTypedArray(audioPortFwArr, 1);
                    return true;
                case 45:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioPortFw[] audioPortFwArrListDeclaredDevicePorts = listDeclaredDevicePorts(i56);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(audioPortFwArrListDeclaredDevicePorts, 1);
                    return true;
                case 46:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioPortFw audioPort2 = getAudioPort(i57);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(audioPort2, 1);
                    return true;
                case 47:
                    AudioPatchFw audioPatchFw = (AudioPatchFw) parcel.readTypedObject(AudioPatchFw.CREATOR);
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCreateAudioPatch = createAudioPatch(audioPatchFw, i58);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateAudioPatch);
                    return true;
                case 48:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseAudioPatch(i59);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    Int r122 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int i60 = parcel.readInt();
                    if (i60 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i60);
                    }
                    AudioPatchFw[] audioPatchFwArr = i60 >= 0 ? new AudioPatchFw[i60] : null;
                    parcel.enforceNoDataAvail();
                    int iListAudioPatches = listAudioPatches(r122, audioPatchFwArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iListAudioPatches);
                    parcel2.writeTypedObject(r122, 1);
                    parcel2.writeTypedArray(audioPatchFwArr, 1);
                    return true;
                case 50:
                    AudioPortConfigFw audioPortConfigFw = (AudioPortConfigFw) parcel.readTypedObject(AudioPortConfigFw.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioPortConfig(audioPortConfigFw);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IAudioPolicyServiceClient iAudioPolicyServiceClientAsInterface = IAudioPolicyServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClient(iAudioPolicyServiceClientAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAudioPortCallbacksEnabled(z7);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAudioVolumeGroupCallbacksEnabled(z8);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    SoundTriggerSession soundTriggerSessionAcquireSoundTriggerSession = acquireSoundTriggerSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(soundTriggerSessionAcquireSoundTriggerSession, 1);
                    return true;
                case 55:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSoundTriggerSession(i61);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int phoneState = getPhoneState();
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneState);
                    return true;
                case 57:
                    AudioMix[] audioMixArr = (AudioMix[]) parcel.createTypedArray(AudioMix.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerPolicyMixes(audioMixArr, z9);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    List<AudioMix> registeredPolicyMixes = getRegisteredPolicyMixes();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(registeredPolicyMixes, 1);
                    return true;
                case 59:
                    AudioMixUpdate[] audioMixUpdateArr = (AudioMixUpdate[]) parcel.createTypedArray(AudioMixUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePolicyMixes(audioMixUpdateArr);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i62 = parcel.readInt();
                    AudioDevice[] audioDeviceArr = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUidDeviceAffinities(i62, audioDeviceArr);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidDeviceAffinities(i63);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i64 = parcel.readInt();
                    AudioDevice[] audioDeviceArr2 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIdDeviceAffinities(i64, audioDeviceArr2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUserIdDeviceAffinities(i65);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    AudioPortConfigFw audioPortConfigFw2 = (AudioPortConfigFw) parcel.readTypedObject(AudioPortConfigFw.CREATOR);
                    android.media.audio.common.AudioAttributes audioAttributes9 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartAudioSource = startAudioSource(audioPortConfigFw2, audioAttributes9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartAudioSource);
                    return true;
                case 65:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAudioSource(i66);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterMono(z10);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    boolean masterMono = getMasterMono();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterMono);
                    return true;
                case 68:
                    int i67 = parcel.readInt();
                    int i68 = parcel.readInt();
                    AudioDeviceDescription audioDeviceDescription5 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    float streamVolumeDB = getStreamVolumeDB(i67, i68, audioDeviceDescription5);
                    parcel2.writeNoException();
                    parcel2.writeFloat(streamVolumeDB);
                    return true;
                case 69:
                    Int r123 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int i69 = parcel.readInt();
                    if (i69 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i69);
                    }
                    AudioFormatDescription[] audioFormatDescriptionArr = i69 < 0 ? null : new AudioFormatDescription[i69];
                    int i70 = parcel.readInt();
                    if (i70 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i70);
                    }
                    boolean[] zArr = i70 >= 0 ? new boolean[i70] : null;
                    parcel.enforceNoDataAvail();
                    getSurroundFormats(r123, audioFormatDescriptionArr, zArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(r123, 1);
                    parcel2.writeTypedArray(audioFormatDescriptionArr, 1);
                    parcel2.writeBooleanArray(zArr);
                    return true;
                case 70:
                    Int r124 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int i71 = parcel.readInt();
                    if (i71 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i71);
                    }
                    AudioFormatDescription[] audioFormatDescriptionArr2 = i71 >= 0 ? new AudioFormatDescription[i71] : null;
                    parcel.enforceNoDataAvail();
                    getReportedSurroundFormats(r124, audioFormatDescriptionArr2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(r124, 1);
                    parcel2.writeTypedArray(audioFormatDescriptionArr2, 1);
                    return true;
                case 71:
                    AudioDeviceDescription audioDeviceDescription6 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    AudioFormatDescription[] hwOffloadFormatsSupportedForBluetoothMedia = getHwOffloadFormatsSupportedForBluetoothMedia(audioDeviceDescription6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(hwOffloadFormatsSupportedForBluetoothMedia, 1);
                    return true;
                case 72:
                    AudioFormatDescription audioFormatDescription3 = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSurroundFormatEnabled(audioFormatDescription3, z11);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setAssistantServicesUids(iArrCreateIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setActiveAssistantServicesUids(iArrCreateIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int[] iArrCreateIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setA11yServicesUids(iArrCreateIntArray6);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentImeUid(i72);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    boolean zIsHapticPlaybackSupported = isHapticPlaybackSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHapticPlaybackSupported);
                    return true;
                case 78:
                    boolean zIsUltrasoundSupported = isUltrasoundSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUltrasoundSupported);
                    return true;
                case 79:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsHotwordStreamSupported = isHotwordStreamSupported(z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHotwordStreamSupported);
                    return true;
                case 80:
                    AudioProductStrategy[] audioProductStrategyArrListAudioProductStrategies = listAudioProductStrategies();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(audioProductStrategyArrListAudioProductStrategies, 1);
                    return true;
                case 81:
                    android.media.audio.common.AudioAttributes audioAttributes10 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int productStrategyFromAudioAttributes = getProductStrategyFromAudioAttributes(audioAttributes10, z13);
                    parcel2.writeNoException();
                    parcel2.writeInt(productStrategyFromAudioAttributes);
                    return true;
                case 82:
                    AudioVolumeGroup[] audioVolumeGroupArrListAudioVolumeGroups = listAudioVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(audioVolumeGroupArrListAudioVolumeGroups, 1);
                    return true;
                case 83:
                    android.media.audio.common.AudioAttributes audioAttributes11 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int volumeGroupFromAudioAttributes = getVolumeGroupFromAudioAttributes(audioAttributes11, z14);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupFromAudioAttributes);
                    return true;
                case 84:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRttEnabled(z15);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean zIsCallScreenModeSupported = isCallScreenModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallScreenModeSupported);
                    return true;
                case 86:
                    int i73 = parcel.readInt();
                    int i74 = parcel.readInt();
                    AudioDevice[] audioDeviceArr3 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDevicesRoleForStrategy(i73, i74, audioDeviceArr3);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int i75 = parcel.readInt();
                    int i76 = parcel.readInt();
                    AudioDevice[] audioDeviceArr4 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicesRoleForStrategy(i75, i76, audioDeviceArr4);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int i77 = parcel.readInt();
                    int i78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDevicesRoleForStrategy(i77, i78);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int i79 = parcel.readInt();
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioDevice[] devicesForRoleAndStrategy = getDevicesForRoleAndStrategy(i79, i80);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForRoleAndStrategy, 1);
                    return true;
                case 90:
                    int i81 = parcel.readInt();
                    int i82 = parcel.readInt();
                    AudioDevice[] audioDeviceArr5 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDevicesRoleForCapturePreset(i81, i82, audioDeviceArr5);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    int i83 = parcel.readInt();
                    int i84 = parcel.readInt();
                    AudioDevice[] audioDeviceArr6 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDevicesRoleForCapturePreset(i83, i84, audioDeviceArr6);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int i85 = parcel.readInt();
                    int i86 = parcel.readInt();
                    AudioDevice[] audioDeviceArr7 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicesRoleForCapturePreset(i85, i86, audioDeviceArr7);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int i87 = parcel.readInt();
                    int i88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDevicesRoleForCapturePreset(i87, i88);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int i89 = parcel.readInt();
                    int i90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioDevice[] devicesForRoleAndCapturePreset = getDevicesForRoleAndCapturePreset(i89, i90);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForRoleAndCapturePreset, 1);
                    return true;
                case 95:
                    ICaptureStateListener iCaptureStateListenerAsInterface = ICaptureStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterSoundTriggerCaptureStateListener = registerSoundTriggerCaptureStateListener(iCaptureStateListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterSoundTriggerCaptureStateListener);
                    return true;
                case 96:
                    INativeSpatializerCallback iNativeSpatializerCallbackAsInterface = INativeSpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    GetSpatializerResponse spatializer = getSpatializer(iNativeSpatializerCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(spatializer, 1);
                    return true;
                case 97:
                    android.media.audio.common.AudioAttributes audioAttributes12 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    AudioConfig audioConfig2 = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                    AudioDevice[] audioDeviceArr8 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCanBeSpatialized = canBeSpatialized(audioAttributes12, audioConfig2, audioDeviceArr8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanBeSpatialized);
                    return true;
                case 98:
                    android.media.audio.common.AudioAttributes audioAttributes13 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    AudioConfig audioConfig3 = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    int directPlaybackSupport = getDirectPlaybackSupport(audioAttributes13, audioConfig3);
                    parcel2.writeNoException();
                    parcel2.writeInt(directPlaybackSupport);
                    return true;
                case 99:
                    android.media.audio.common.AudioAttributes audioAttributes14 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.audio.common.AudioProfile[] directProfilesForAttributes = getDirectProfilesForAttributes(audioAttributes14);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(directProfilesForAttributes, 1);
                    return true;
                case 100:
                    int i91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioMixerAttributesInternal[] supportedMixerAttributes = getSupportedMixerAttributes(i91);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(supportedMixerAttributes, 1);
                    return true;
                case 101:
                    android.media.audio.common.AudioAttributes audioAttributes15 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int i92 = parcel.readInt();
                    int i93 = parcel.readInt();
                    AudioMixerAttributesInternal audioMixerAttributesInternal = (AudioMixerAttributesInternal) parcel.readTypedObject(AudioMixerAttributesInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredMixerAttributes(audioAttributes15, i92, i93, audioMixerAttributesInternal);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    android.media.audio.common.AudioAttributes audioAttributes16 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int i94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioMixerAttributesInternal preferredMixerAttributes = getPreferredMixerAttributes(audioAttributes16, i94);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preferredMixerAttributes, 1);
                    return true;
                case 103:
                    android.media.audio.common.AudioAttributes audioAttributes17 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int i95 = parcel.readInt();
                    int i96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPreferredMixerAttributes(audioAttributes17, i95, i96);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    INativePermissionController permissionController = getPermissionController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(permissionController);
                    return true;
                case 105:
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioMMapPolicyInfo[] mmapPolicyInfos = getMmapPolicyInfos(i97);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(mmapPolicyInfos, 1);
                    return true;
                case 106:
                    int i98 = parcel.readInt();
                    AudioMMapPolicyInfo audioMMapPolicyInfo = (AudioMMapPolicyInfo) parcel.readTypedObject(AudioMMapPolicyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getMmapPolicyForDevice(i98, audioMMapPolicyInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(audioMMapPolicyInfo, 1);
                    return true;
                case 107:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnableHardening(z16);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAudioPolicyConfig(string4);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String audioPolicyConfig = getAudioPolicyConfig(string5);
                    parcel2.writeNoException();
                    parcel2.writeString(audioPolicyConfig);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAudioPolicyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioPolicyService.DESCRIPTOR;
            }

            @Override // android.media.IAudioPolicyService
            public void onNewAudioModulesAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioPort, 0);
                    parcelObtain.writeTypedObject(audioFormatDescription, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getDeviceConnectionState(AudioDevice audioDevice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDevice, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void handleDeviceConfigChange(AudioDevice audioDevice, String str, AudioFormatDescription audioFormatDescription) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDevice, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(audioFormatDescription, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setPhoneState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setForceUse(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getForceUse(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOutput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, AttributionSourceState attributionSourceState, AudioConfig audioConfig, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeTypedObject(audioConfig, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GetOutputForAttrResponse) parcelObtain2.readTypedObject(GetOutputForAttrResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void startOutput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopOutput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseOutput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, AttributionSourceState attributionSourceState, AudioConfigBase audioConfigBase, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeTypedObject(audioConfigBase, 0);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GetInputForAttrResponse) parcelObtain2.readTypedObject(GetInputForAttrResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void startInput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopInput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseInput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDeviceAbsoluteVolumeEnabled(AudioDevice audioDevice, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDevice, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void initStreamVolume(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioDeviceDescription, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeTypedObject(audioDeviceDescription, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getStrategyForStream(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioDevice[]) parcelObtain2.createTypedArray(AudioDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOutputForEffect(EffectDescriptor effectDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(effectDescriptor, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerEffect(EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(effectDescriptor, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void unregisterEffect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setEffectEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void moveEffectsToIo(int[] iArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isStreamActive(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isStreamActiveRemotely(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isSourceActive(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public EffectDescriptor[] queryDefaultPreProcessing(int i, Int r5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(r5, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    EffectDescriptor[] effectDescriptorArr = (EffectDescriptor[]) parcelObtain2.createTypedArray(EffectDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        r5.readFromParcel(parcelObtain2);
                    }
                    return effectDescriptorArr;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int addSourceDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioUuid, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(audioUuid2, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int addStreamDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioUuid, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(audioUuid2, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeSourceDefaultEffect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeStreamDefaultEffect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setSupportedSystemUsages(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAllowedCapturePolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOffloadSupport(AudioOffloadInfo audioOffloadInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioOffloadInfo, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isDirectOutputSupported(AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioConfigBase, 0);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int listAudioPorts(int i, int i2, Int r6, AudioPortFw[] audioPortFwArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(r6, 0);
                    parcelObtain.writeInt(audioPortFwArr.length);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        r6.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readTypedArray(audioPortFwArr, AudioPortFw.CREATOR);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioPortFw[] listDeclaredDevicePorts(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioPortFw[]) parcelObtain2.createTypedArray(AudioPortFw.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioPortFw getAudioPort(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioPortFw) parcelObtain2.readTypedObject(AudioPortFw.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int createAudioPatch(AudioPatchFw audioPatchFw, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioPatchFw, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseAudioPatch(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int listAudioPatches(Int r5, AudioPatchFw[] audioPatchFwArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(r5, 0);
                    parcelObtain.writeInt(audioPatchFwArr.length);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        r5.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readTypedArray(audioPatchFwArr, AudioPatchFw.CREATOR);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPortConfig(AudioPortConfigFw audioPortConfigFw) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioPortConfigFw, 0);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerClient(IAudioPolicyServiceClient iAudioPolicyServiceClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAudioPolicyServiceClient);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPortCallbacksEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioVolumeGroupCallbacksEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public SoundTriggerSession acquireSoundTriggerSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SoundTriggerSession) parcelObtain2.readTypedObject(SoundTriggerSession.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseSoundTriggerSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getPhoneState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerPolicyMixes(AudioMix[] audioMixArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedArray(audioMixArr, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public List<AudioMix> getRegisteredPolicyMixes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AudioMix.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void updatePolicyMixes(AudioMixUpdate[] audioMixUpdateArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedArray(audioMixUpdateArr, 0);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setUidDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeUidDeviceAffinities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setUserIdDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeUserIdDeviceAffinities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int startAudioSource(AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioPortConfigFw, 0);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopAudioSource(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setMasterMono(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean getMasterMono() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public float getStreamVolumeDB(int i, int i2, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getSurroundFormats(Int r5, AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(r5, 0);
                    parcelObtain.writeInt(audioFormatDescriptionArr.length);
                    parcelObtain.writeInt(zArr.length);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        r5.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readTypedArray(audioFormatDescriptionArr, AudioFormatDescription.CREATOR);
                    parcelObtain2.readBooleanArray(zArr);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getReportedSurroundFormats(Int r5, AudioFormatDescription[] audioFormatDescriptionArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(r5, 0);
                    parcelObtain.writeInt(audioFormatDescriptionArr.length);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        r5.readFromParcel(parcelObtain2);
                    }
                    parcelObtain2.readTypedArray(audioFormatDescriptionArr, AudioFormatDescription.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioFormatDescription[]) parcelObtain2.createTypedArray(AudioFormatDescription.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setSurroundFormatEnabled(AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioFormatDescription, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAssistantServicesUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setActiveAssistantServicesUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setA11yServicesUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setCurrentImeUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isHapticPlaybackSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isUltrasoundSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isHotwordStreamSupported(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioProductStrategy[] listAudioProductStrategies() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioProductStrategy[]) parcelObtain2.createTypedArray(AudioProductStrategy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioVolumeGroup[] listAudioVolumeGroups() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioVolumeGroup[]) parcelObtain2.createTypedArray(AudioVolumeGroup.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setRttEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isCallScreenModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearDevicesRoleForStrategy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioDevice[]) parcelObtain2.createTypedArray(AudioDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void addDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearDevicesRoleForCapturePreset(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioDevice[]) parcelObtain2.createTypedArray(AudioDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener iCaptureStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCaptureStateListener);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public GetSpatializerResponse getSpatializer(INativeSpatializerCallback iNativeSpatializerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNativeSpatializerCallback);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GetSpatializerResponse) parcelObtain2.readTypedObject(GetSpatializerResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeTypedObject(audioConfig, 0);
                    parcelObtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeTypedObject(audioConfig, 0);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (android.media.audio.common.AudioProfile[]) parcelObtain2.createTypedArray(android.media.audio.common.AudioProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioMixerAttributesInternal[]) parcelObtain2.createTypedArray(AudioMixerAttributesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, AudioMixerAttributesInternal audioMixerAttributesInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(audioMixerAttributesInternal, 0);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioMixerAttributesInternal) parcelObtain2.readTypedObject(AudioMixerAttributesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public INativePermissionController getPermissionController() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return INativePermissionController.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioMMapPolicyInfo[] getMmapPolicyInfos(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AudioMMapPolicyInfo[]) parcelObtain2.createTypedArray(AudioMMapPolicyInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getMmapPolicyForDevice(int i, AudioMMapPolicyInfo audioMMapPolicyInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(audioMMapPolicyInfo, 0);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        audioMMapPolicyInfo.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setEnableHardening(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPolicyConfig(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public String getAudioPolicyConfig(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
