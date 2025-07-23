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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAudioPolicyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAudioPolicyService)) {
                return (IAudioPolicyService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    android.media.audio.common.AudioPort audioPort = (android.media.audio.common.AudioPort) parcel.readTypedObject(android.media.audio.common.AudioPort.CREATOR);
                    AudioFormatDescription audioFormatDescription = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceConnectionState(readInt, audioPort, audioFormatDescription, readBoolean);
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
                    String readString = parcel.readString();
                    AudioFormatDescription audioFormatDescription2 = (AudioFormatDescription) parcel.readTypedObject(AudioFormatDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleDeviceConfigChange(audioDevice2, readString, audioFormatDescription2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPhoneState(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForceUse(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int forceUse = getForceUse(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceUse);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int output = getOutput(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(output);
                    return true;
                case 9:
                    android.media.audio.common.AudioAttributes audioAttributes = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt8 = parcel.readInt();
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    AudioConfig audioConfig = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                    int readInt9 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    GetOutputForAttrResponse outputForAttr = getOutputForAttr(audioAttributes, readInt8, attributionSourceState, audioConfig, readInt9, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(outputForAttr, 1);
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startOutput(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOutput(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseOutput(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.media.audio.common.AudioAttributes audioAttributes2 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    AudioConfigBase audioConfigBase = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    GetInputForAttrResponse inputForAttr = getInputForAttr(audioAttributes2, readInt13, readInt14, readInt15, attributionSourceState2, audioConfigBase, readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputForAttr, 1);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startInput(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopInput(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseInput(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    AudioDevice audioDevice3 = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceAbsoluteVolumeEnabled(audioDevice3, readBoolean2, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initStreamVolume(readInt22, readInt23, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt25 = parcel.readInt();
                    AudioDeviceDescription audioDeviceDescription = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    int readInt26 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStreamVolumeIndex(readInt25, audioDeviceDescription, readInt26, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt27 = parcel.readInt();
                    AudioDeviceDescription audioDeviceDescription2 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    int streamVolumeIndex = getStreamVolumeIndex(readInt27, audioDeviceDescription2);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamVolumeIndex);
                    return true;
                case 21:
                    android.media.audio.common.AudioAttributes audioAttributes3 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    AudioDeviceDescription audioDeviceDescription3 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    int readInt28 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVolumeIndexForAttributes(audioAttributes3, audioDeviceDescription3, readInt28, readBoolean4);
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
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int strategyForStream = getStrategyForStream(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeInt(strategyForStream);
                    return true;
                case 26:
                    android.media.audio.common.AudioAttributes audioAttributes7 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    AudioDevice[] devicesForAttributes = getDevicesForAttributes(audioAttributes7, readBoolean5);
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
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerEffect(effectDescriptor2, readInt30, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterEffect(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt35 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEffectEnabled(readInt35, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int[] createIntArray2 = parcel.createIntArray();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveEffectsToIo(createIntArray2, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamActive = isStreamActive(readInt37, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamActive);
                    return true;
                case 33:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamActiveRemotely = isStreamActiveRemotely(readInt39, readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamActiveRemotely);
                    return true;
                case 34:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSourceActive = isSourceActive(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSourceActive);
                    return true;
                case 35:
                    int readInt42 = parcel.readInt();
                    Int r12 = (Int) parcel.readTypedObject(Int.CREATOR);
                    parcel.enforceNoDataAvail();
                    EffectDescriptor[] queryDefaultPreProcessing = queryDefaultPreProcessing(readInt42, r12);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(queryDefaultPreProcessing, 1);
                    parcel2.writeTypedObject(r12, 1);
                    return true;
                case 36:
                    AudioUuid audioUuid = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    String readString2 = parcel.readString();
                    AudioUuid audioUuid2 = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addSourceDefaultEffect = addSourceDefaultEffect(audioUuid, readString2, audioUuid2, readInt43, readInt44);
                    parcel2.writeNoException();
                    parcel2.writeInt(addSourceDefaultEffect);
                    return true;
                case 37:
                    AudioUuid audioUuid3 = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    String readString3 = parcel.readString();
                    AudioUuid audioUuid4 = (AudioUuid) parcel.readTypedObject(AudioUuid.CREATOR);
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addStreamDefaultEffect = addStreamDefaultEffect(audioUuid3, readString3, audioUuid4, readInt45, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeInt(addStreamDefaultEffect);
                    return true;
                case 38:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSourceDefaultEffect(readInt47);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeStreamDefaultEffect(readInt48);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSupportedSystemUsages(createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedCapturePolicy(readInt49, readInt50);
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
                    boolean isDirectOutputSupported = isDirectOutputSupported(audioConfigBase2, audioAttributes8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDirectOutputSupported);
                    return true;
                case 44:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    Int r4 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int readInt53 = parcel.readInt();
                    if (readInt53 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt53);
                    }
                    AudioPortFw[] audioPortFwArr = readInt53 >= 0 ? new AudioPortFw[readInt53] : null;
                    parcel.enforceNoDataAvail();
                    int listAudioPorts = listAudioPorts(readInt51, readInt52, r4, audioPortFwArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(listAudioPorts);
                    parcel2.writeTypedObject(r4, 1);
                    parcel2.writeTypedArray(audioPortFwArr, 1);
                    return true;
                case 45:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioPortFw[] listDeclaredDevicePorts = listDeclaredDevicePorts(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listDeclaredDevicePorts, 1);
                    return true;
                case 46:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioPortFw audioPort2 = getAudioPort(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(audioPort2, 1);
                    return true;
                case 47:
                    AudioPatchFw audioPatchFw = (AudioPatchFw) parcel.readTypedObject(AudioPatchFw.CREATOR);
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createAudioPatch = createAudioPatch(audioPatchFw, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeInt(createAudioPatch);
                    return true;
                case 48:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseAudioPatch(readInt57);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    Int r122 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int readInt58 = parcel.readInt();
                    if (readInt58 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt58);
                    }
                    AudioPatchFw[] audioPatchFwArr = readInt58 >= 0 ? new AudioPatchFw[readInt58] : null;
                    parcel.enforceNoDataAvail();
                    int listAudioPatches = listAudioPatches(r122, audioPatchFwArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(listAudioPatches);
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
                    IAudioPolicyServiceClient asInterface = IAudioPolicyServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClient(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAudioPortCallbacksEnabled(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAudioVolumeGroupCallbacksEnabled(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    SoundTriggerSession acquireSoundTriggerSession = acquireSoundTriggerSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(acquireSoundTriggerSession, 1);
                    return true;
                case 55:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSoundTriggerSession(readInt59);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int phoneState = getPhoneState();
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneState);
                    return true;
                case 57:
                    AudioMix[] audioMixArr = (AudioMix[]) parcel.createTypedArray(AudioMix.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerPolicyMixes(audioMixArr, readBoolean9);
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
                    int readInt60 = parcel.readInt();
                    AudioDevice[] audioDeviceArr = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUidDeviceAffinities(readInt60, audioDeviceArr);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidDeviceAffinities(readInt61);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt62 = parcel.readInt();
                    AudioDevice[] audioDeviceArr2 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIdDeviceAffinities(readInt62, audioDeviceArr2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUserIdDeviceAffinities(readInt63);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    AudioPortConfigFw audioPortConfigFw2 = (AudioPortConfigFw) parcel.readTypedObject(AudioPortConfigFw.CREATOR);
                    android.media.audio.common.AudioAttributes audioAttributes9 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startAudioSource = startAudioSource(audioPortConfigFw2, audioAttributes9);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAudioSource);
                    return true;
                case 65:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAudioSource(readInt64);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterMono(readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    boolean masterMono = getMasterMono();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterMono);
                    return true;
                case 68:
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    AudioDeviceDescription audioDeviceDescription5 = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    float streamVolumeDB = getStreamVolumeDB(readInt65, readInt66, audioDeviceDescription5);
                    parcel2.writeNoException();
                    parcel2.writeFloat(streamVolumeDB);
                    return true;
                case 69:
                    Int r123 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int readInt67 = parcel.readInt();
                    if (readInt67 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt67);
                    }
                    AudioFormatDescription[] audioFormatDescriptionArr = readInt67 < 0 ? null : new AudioFormatDescription[readInt67];
                    int readInt68 = parcel.readInt();
                    if (readInt68 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt68);
                    }
                    boolean[] zArr = readInt68 >= 0 ? new boolean[readInt68] : null;
                    parcel.enforceNoDataAvail();
                    getSurroundFormats(r123, audioFormatDescriptionArr, zArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(r123, 1);
                    parcel2.writeTypedArray(audioFormatDescriptionArr, 1);
                    parcel2.writeBooleanArray(zArr);
                    return true;
                case 70:
                    Int r124 = (Int) parcel.readTypedObject(Int.CREATOR);
                    int readInt69 = parcel.readInt();
                    if (readInt69 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt69);
                    }
                    AudioFormatDescription[] audioFormatDescriptionArr2 = readInt69 >= 0 ? new AudioFormatDescription[readInt69] : null;
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
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSurroundFormatEnabled(audioFormatDescription3, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setAssistantServicesUids(createIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int[] createIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setActiveAssistantServicesUids(createIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int[] createIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setA11yServicesUids(createIntArray6);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentImeUid(readInt70);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    boolean isHapticPlaybackSupported = isHapticPlaybackSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHapticPlaybackSupported);
                    return true;
                case 78:
                    boolean isUltrasoundSupported = isUltrasoundSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUltrasoundSupported);
                    return true;
                case 79:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isHotwordStreamSupported = isHotwordStreamSupported(readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHotwordStreamSupported);
                    return true;
                case 80:
                    AudioProductStrategy[] listAudioProductStrategies = listAudioProductStrategies();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAudioProductStrategies, 1);
                    return true;
                case 81:
                    android.media.audio.common.AudioAttributes audioAttributes10 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int productStrategyFromAudioAttributes = getProductStrategyFromAudioAttributes(audioAttributes10, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeInt(productStrategyFromAudioAttributes);
                    return true;
                case 82:
                    AudioVolumeGroup[] listAudioVolumeGroups = listAudioVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAudioVolumeGroups, 1);
                    return true;
                case 83:
                    android.media.audio.common.AudioAttributes audioAttributes11 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int volumeGroupFromAudioAttributes = getVolumeGroupFromAudioAttributes(audioAttributes11, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupFromAudioAttributes);
                    return true;
                case 84:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRttEnabled(readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean isCallScreenModeSupported = isCallScreenModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallScreenModeSupported);
                    return true;
                case 86:
                    int readInt71 = parcel.readInt();
                    int readInt72 = parcel.readInt();
                    AudioDevice[] audioDeviceArr3 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDevicesRoleForStrategy(readInt71, readInt72, audioDeviceArr3);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    AudioDevice[] audioDeviceArr4 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicesRoleForStrategy(readInt73, readInt74, audioDeviceArr4);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDevicesRoleForStrategy(readInt75, readInt76);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioDevice[] devicesForRoleAndStrategy = getDevicesForRoleAndStrategy(readInt77, readInt78);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForRoleAndStrategy, 1);
                    return true;
                case 90:
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    AudioDevice[] audioDeviceArr5 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDevicesRoleForCapturePreset(readInt79, readInt80, audioDeviceArr5);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    int readInt81 = parcel.readInt();
                    int readInt82 = parcel.readInt();
                    AudioDevice[] audioDeviceArr6 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDevicesRoleForCapturePreset(readInt81, readInt82, audioDeviceArr6);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    AudioDevice[] audioDeviceArr7 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicesRoleForCapturePreset(readInt83, readInt84, audioDeviceArr7);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int readInt85 = parcel.readInt();
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDevicesRoleForCapturePreset(readInt85, readInt86);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int readInt87 = parcel.readInt();
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioDevice[] devicesForRoleAndCapturePreset = getDevicesForRoleAndCapturePreset(readInt87, readInt88);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForRoleAndCapturePreset, 1);
                    return true;
                case 95:
                    ICaptureStateListener asInterface2 = ICaptureStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSoundTriggerCaptureStateListener = registerSoundTriggerCaptureStateListener(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSoundTriggerCaptureStateListener);
                    return true;
                case 96:
                    INativeSpatializerCallback asInterface3 = INativeSpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    GetSpatializerResponse spatializer = getSpatializer(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(spatializer, 1);
                    return true;
                case 97:
                    android.media.audio.common.AudioAttributes audioAttributes12 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    AudioConfig audioConfig2 = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                    AudioDevice[] audioDeviceArr8 = (AudioDevice[]) parcel.createTypedArray(AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean canBeSpatialized = canBeSpatialized(audioAttributes12, audioConfig2, audioDeviceArr8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canBeSpatialized);
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
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioMixerAttributesInternal[] supportedMixerAttributes = getSupportedMixerAttributes(readInt89);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(supportedMixerAttributes, 1);
                    return true;
                case 101:
                    android.media.audio.common.AudioAttributes audioAttributes15 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt90 = parcel.readInt();
                    int readInt91 = parcel.readInt();
                    AudioMixerAttributesInternal audioMixerAttributesInternal = (AudioMixerAttributesInternal) parcel.readTypedObject(AudioMixerAttributesInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredMixerAttributes(audioAttributes15, readInt90, readInt91, audioMixerAttributesInternal);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    android.media.audio.common.AudioAttributes audioAttributes16 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioMixerAttributesInternal preferredMixerAttributes = getPreferredMixerAttributes(audioAttributes16, readInt92);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preferredMixerAttributes, 1);
                    return true;
                case 103:
                    android.media.audio.common.AudioAttributes audioAttributes17 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt93 = parcel.readInt();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPreferredMixerAttributes(audioAttributes17, readInt93, readInt94);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    INativePermissionController permissionController = getPermissionController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(permissionController);
                    return true;
                case 105:
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AudioMMapPolicyInfo[] mmapPolicyInfos = getMmapPolicyInfos(readInt95);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(mmapPolicyInfos, 1);
                    return true;
                case 106:
                    int readInt96 = parcel.readInt();
                    AudioMMapPolicyInfo audioMMapPolicyInfo = (AudioMMapPolicyInfo) parcel.readTypedObject(AudioMMapPolicyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getMmapPolicyForDevice(readInt96, audioMMapPolicyInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(audioMMapPolicyInfo, 1);
                    return true;
                case 107:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnableHardening(readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAudioPolicyConfig(readString4);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String audioPolicyConfig = getAudioPolicyConfig(readString5);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioPort, 0);
                    obtain.writeTypedObject(audioFormatDescription, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getDeviceConnectionState(AudioDevice audioDevice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDevice, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void handleDeviceConfigChange(AudioDevice audioDevice, String str, AudioFormatDescription audioFormatDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDevice, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioFormatDescription, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setPhoneState(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setForceUse(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getForceUse(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOutput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, AttributionSourceState attributionSourceState, AudioConfig audioConfig, int i2, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeTypedObject(audioConfig, 0);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (GetOutputForAttrResponse) obtain2.readTypedObject(GetOutputForAttrResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void startOutput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopOutput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseOutput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, AttributionSourceState attributionSourceState, AudioConfigBase audioConfigBase, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeTypedObject(audioConfigBase, 0);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (GetInputForAttrResponse) obtain2.readTypedObject(GetInputForAttrResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void startInput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopInput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseInput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDeviceAbsoluteVolumeEnabled(AudioDevice audioDevice, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDevice, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void initStreamVolume(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getStreamVolumeIndex(int i, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getStrategyForStream(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioDevice[]) obtain2.createTypedArray(AudioDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOutputForEffect(EffectDescriptor effectDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(effectDescriptor, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerEffect(EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(effectDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void unregisterEffect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setEffectEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void moveEffectsToIo(int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isStreamActive(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isStreamActiveRemotely(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isSourceActive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public EffectDescriptor[] queryDefaultPreProcessing(int i, Int r5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(r5, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    EffectDescriptor[] effectDescriptorArr = (EffectDescriptor[]) obtain2.createTypedArray(EffectDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        r5.readFromParcel(obtain2);
                    }
                    return effectDescriptorArr;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int addSourceDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioUuid, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioUuid2, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int addStreamDefaultEffect(AudioUuid audioUuid, String str, AudioUuid audioUuid2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioUuid, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioUuid2, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeSourceDefaultEffect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeStreamDefaultEffect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setSupportedSystemUsages(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAllowedCapturePolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOffloadSupport(AudioOffloadInfo audioOffloadInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioOffloadInfo, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isDirectOutputSupported(AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioConfigBase, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int listAudioPorts(int i, int i2, Int r6, AudioPortFw[] audioPortFwArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(r6, 0);
                    obtain.writeInt(audioPortFwArr.length);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        r6.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioPortFwArr, AudioPortFw.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioPortFw[] listDeclaredDevicePorts(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioPortFw[]) obtain2.createTypedArray(AudioPortFw.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioPortFw getAudioPort(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioPortFw) obtain2.readTypedObject(AudioPortFw.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int createAudioPatch(AudioPatchFw audioPatchFw, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioPatchFw, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseAudioPatch(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int listAudioPatches(Int r5, AudioPatchFw[] audioPatchFwArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(r5, 0);
                    obtain.writeInt(audioPatchFwArr.length);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        r5.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioPatchFwArr, AudioPatchFw.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPortConfig(AudioPortConfigFw audioPortConfigFw) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioPortConfigFw, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerClient(IAudioPolicyServiceClient iAudioPolicyServiceClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyServiceClient);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPortCallbacksEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioVolumeGroupCallbacksEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public SoundTriggerSession acquireSoundTriggerSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoundTriggerSession) obtain2.readTypedObject(SoundTriggerSession.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseSoundTriggerSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getPhoneState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerPolicyMixes(AudioMix[] audioMixArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedArray(audioMixArr, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public List<AudioMix> getRegisteredPolicyMixes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AudioMix.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void updatePolicyMixes(AudioMixUpdate[] audioMixUpdateArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedArray(audioMixUpdateArr, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setUidDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeUidDeviceAffinities(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setUserIdDeviceAffinities(int i, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeUserIdDeviceAffinities(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int startAudioSource(AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioPortConfigFw, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopAudioSource(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setMasterMono(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean getMasterMono() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public float getStreamVolumeDB(int i, int i2, AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getSurroundFormats(Int r5, AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(r5, 0);
                    obtain.writeInt(audioFormatDescriptionArr.length);
                    obtain.writeInt(zArr.length);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        r5.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioFormatDescriptionArr, AudioFormatDescription.CREATOR);
                    obtain2.readBooleanArray(zArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getReportedSurroundFormats(Int r5, AudioFormatDescription[] audioFormatDescriptionArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(r5, 0);
                    obtain.writeInt(audioFormatDescriptionArr.length);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        r5.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioFormatDescriptionArr, AudioFormatDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(AudioDeviceDescription audioDeviceDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioFormatDescription[]) obtain2.createTypedArray(AudioFormatDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setSurroundFormatEnabled(AudioFormatDescription audioFormatDescription, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioFormatDescription, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAssistantServicesUids(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setActiveAssistantServicesUids(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setA11yServicesUids(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setCurrentImeUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isHapticPlaybackSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isUltrasoundSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isHotwordStreamSupported(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioProductStrategy[] listAudioProductStrategies() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioProductStrategy[]) obtain2.createTypedArray(AudioProductStrategy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioVolumeGroup[] listAudioVolumeGroups() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioVolumeGroup[]) obtain2.createTypedArray(AudioVolumeGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setRttEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isCallScreenModeSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeDevicesRoleForStrategy(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearDevicesRoleForStrategy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioDevice[]) obtain2.createTypedArray(AudioDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void addDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeDevicesRoleForCapturePreset(int i, int i2, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearDevicesRoleForCapturePreset(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioDevice[]) obtain2.createTypedArray(AudioDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean registerSoundTriggerCaptureStateListener(ICaptureStateListener iCaptureStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCaptureStateListener);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public GetSpatializerResponse getSpatializer(INativeSpatializerCallback iNativeSpatializerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iNativeSpatializerCallback);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return (GetSpatializerResponse) obtain2.readTypedObject(GetSpatializerResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig, AudioDevice[] audioDeviceArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioConfig, 0);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, AudioConfig audioConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioConfig, 0);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.audio.common.AudioProfile[]) obtain2.createTypedArray(android.media.audio.common.AudioProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioMixerAttributesInternal[]) obtain2.createTypedArray(AudioMixerAttributesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, AudioMixerAttributesInternal audioMixerAttributesInternal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(audioMixerAttributesInternal, 0);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioMixerAttributesInternal) obtain2.readTypedObject(AudioMixerAttributesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public INativePermissionController getPermissionController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return INativePermissionController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public AudioMMapPolicyInfo[] getMmapPolicyInfos(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AudioMMapPolicyInfo[]) obtain2.createTypedArray(AudioMMapPolicyInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getMmapPolicyForDevice(int i, AudioMMapPolicyInfo audioMMapPolicyInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioMMapPolicyInfo, 0);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        audioMMapPolicyInfo.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setEnableHardening(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPolicyConfig(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public String getAudioPolicyConfig(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAudioPolicyService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
