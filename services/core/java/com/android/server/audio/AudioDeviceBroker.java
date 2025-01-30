package com.android.server.audio;

import android.app.compat.CompatChanges;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.media.AudioRoutesInfo;
import android.media.AudioSystem;
import android.media.BluetoothProfileConnectionInfo;
import android.media.IAudioRoutesObserver;
import android.media.ICapturePresetDevicesRoleDispatcher;
import android.media.ICommunicationDeviceDispatcher;
import android.media.IStrategyNonDefaultDevicesDispatcher;
import android.media.IStrategyPreferredDevicesDispatcher;
import android.media.MediaMetrics;
import android.media.audiopolicy.AudioProductStrategy;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.PrintWriterPrinter;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.DualA2dpVolumeManager;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.BtUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class AudioDeviceBroker {
  public static final Set MESSAGES_SAR_RCV_CONTROL;
  public static long sLastDeviceConnectMsgTime;
  public AudioDeviceInfo mActiveCommunicationDevice;
  public final AudioService mAudioService;
  public final AudioSystemAdapter mAudioSystem;
  public boolean mBluetoothA2dpEnabled;
  public boolean mBluetoothA2dpSuspendedApplied;
  public boolean mBluetoothA2dpSuspendedExt;
  public boolean mBluetoothA2dpSuspendedInt;
  public boolean mBluetoothLeSuspendedApplied;
  public boolean mBluetoothLeSuspendedExt;
  public boolean mBluetoothLeSuspendedInt;
  public boolean mBluetoothScoOn;
  public boolean mBluetoothScoOnApplied;
  public PowerManager.WakeLock mBrokerEventWakeLock;
  public BrokerHandler mBrokerHandler;
  public BrokerThread mBrokerThread;
  public final Context mContext;
  public AudioDeviceAttributes mPreferredCommunicationDevice;
  public final SystemServerAdapter mSystemServer;
  public static final Object sLastDeviceConnectionMsgTimeLock = new Object();
  public static final int[] VALID_COMMUNICATION_DEVICE_TYPES = {
    2, 7, 3, 22, 1, 4, 23, 26, 11, 27, 5, 9, 19
  };
  public static final Set MESSAGES_MUTE_MUSIC = new HashSet();
  public int mCommunicationStrategyId = -1;
  public int mAccessibilityStrategyId = -1;
  public final Object mDeviceStateLock = new Object();
  public final Object mSetModeLock = new Object();
  public AudioModeInfo mAudioModeOwner = new AudioModeInfo(0, 0, 0);
  public Object mCommunicationDeviceLock = new Object();
  public int mCommunicationDeviceUpdateCount = 0;
  public final Object mBluetoothAudioStateLock = new Object();
  public final RemoteCallbackList mCommDevDispatchers = new RemoteCallbackList();
  public int mCurCommunicationPortId = -1;
  public AtomicBoolean mMusicMuted = new AtomicBoolean(false);
  public final LinkedList mCommunicationRouteClients = new LinkedList();
  public AudioDeviceAttributes mCurrentCallDevice = null;
  public Boolean mReceiverSupported = Boolean.FALSE;
  public int mRequestScoUid = 0;
  public int mRequestScoCount = 0;
  public IBinder mScoCb = null;
  public boolean mCurRCVBackOffState = false;
  public boolean mSarBackoffParam = false;
  public DualA2dpVolumeManager mDualA2dpManager = null;
  public String mModeRequesterPackage = "";
  public final BtHelper mBtHelper = new BtHelper(this);
  public final AudioDeviceInventory mDeviceInventory = new AudioDeviceInventory(this);

  public static boolean isMessageHandledUnderWakelock(int i) {
    return i == 2 || i == 29 || i == 31 || i == 35 || i == 49 || i == 6 || i == 7 || i == 10
        || i == 11;
  }

  static {
    HashSet hashSet = new HashSet();
    MESSAGES_SAR_RCV_CONTROL = hashSet;
    hashSet.add(2);
    hashSet.add(4);
    hashSet.add(5);
    hashSet.add(42);
  }

  public final class AudioModeInfo {
    public final int mMode;
    public final int mPid;
    public final int mUid;

    public AudioModeInfo(int i, int i2, int i3) {
      this.mMode = i;
      this.mPid = i2;
      this.mUid = i3;
    }

    public String toString() {
      return "AudioModeInfo: mMode="
          + AudioSystem.modeToString(this.mMode)
          + ", mPid="
          + this.mPid
          + ", mUid="
          + this.mUid;
    }
  }

  public AudioDeviceBroker(
      Context context, AudioService audioService, AudioSystemAdapter audioSystemAdapter) {
    this.mContext = context;
    this.mAudioService = audioService;
    this.mSystemServer = SystemServerAdapter.getDefaultAdapter(context);
    this.mAudioSystem = audioSystemAdapter;
    init();
  }

  public final void initRoutingStrategyIds() {
    List<AudioProductStrategy> audioProductStrategies =
        AudioProductStrategy.getAudioProductStrategies();
    this.mCommunicationStrategyId = -1;
    this.mAccessibilityStrategyId = -1;
    for (AudioProductStrategy audioProductStrategy : audioProductStrategies) {
      if (this.mCommunicationStrategyId == -1
          && audioProductStrategy.getAudioAttributesForLegacyStreamType(0) != null) {
        this.mCommunicationStrategyId = audioProductStrategy.getId();
      }
      if (this.mAccessibilityStrategyId == -1
          && audioProductStrategy.getAudioAttributesForLegacyStreamType(10) != null) {
        this.mAccessibilityStrategyId = audioProductStrategy.getId();
      }
    }
  }

  public final void init() {
    setupMessaging(this.mContext);
    initAudioHalBluetoothState();
    initRoutingStrategyIds();
    this.mPreferredCommunicationDevice = null;
    updateActiveCommunicationDevice();
    this.mSystemServer.registerUserStartedReceiver(this.mContext);
    this.mBrokerHandler.sendEmptyMessage(2759);
  }

  public Context getContext() {
    return this.mContext;
  }

  public void onSystemReady() {
    synchronized (this.mSetModeLock) {
      synchronized (this.mDeviceStateLock) {
        this.mAudioModeOwner = this.mAudioService.getAudioModeOwner();
        this.mBtHelper.onSystemReady();
      }
    }
    this.mDeviceInventory.onSystemReady();
  }

  public void onAudioServerDied() {
    sendMsgNoDelay(1, 0);
  }

  public void setForceUse_Async(int i, int i2, String str) {
    sendIILMsgNoDelay(4, 2, i, i2, str);
  }

  public void toggleHdmiIfConnected_Async() {
    sendMsgNoDelay(6, 2);
  }

  public void receiveBtEvent(Intent intent) {
    synchronized (this.mSetModeLock) {
      synchronized (this.mDeviceStateLock) {
        this.mBtHelper.receiveBtEvent(intent);
      }
    }
  }

  public void setBluetoothA2dpOn_Async(boolean z, String str) {
    synchronized (this.mDeviceStateLock) {
      if (this.mBluetoothA2dpEnabled == z) {
        return;
      }
      this.mBluetoothA2dpEnabled = z;
      this.mBrokerHandler.removeMessages(5);
      sendIILMsgNoDelay(5, 2, 1, this.mBluetoothA2dpEnabled ? 0 : 10, str);
      if (isForceSpeakerOn() && !z) {
        sendMsgForForceSpeaker();
      }
    }
  }

  public void setSpeakerphoneOn(IBinder iBinder, int i, boolean z, boolean z2, String str) {
    Log.v("AS.AudioDeviceBroker", "setSpeakerphoneOn, on: " + z + " uid: " + i);
    postSetCommunicationDeviceForClient(
        new CommunicationDeviceInfo(iBinder, i, new AudioDeviceAttributes(2, ""), z, -1, str, z2));
  }

  public boolean setCommunicationDevice(
      IBinder iBinder, int i, AudioDeviceInfo audioDeviceInfo, boolean z, String str) {
    Log.v(
        "AS.AudioDeviceBroker",
        "setCommunicationDevice, device: " + audioDeviceInfo + ", uid: " + i);
    synchronized (this.mDeviceStateLock) {
      if (audioDeviceInfo == null) {
        if (getCommunicationRouteClientForUid(i) == null) {
          return false;
        }
      }
      synchronized (this.mCommunicationDeviceLock) {
        this.mCommunicationDeviceUpdateCount++;
        postSetCommunicationDeviceForClient(
            new CommunicationDeviceInfo(
                iBinder,
                i,
                audioDeviceInfo != null ? new AudioDeviceAttributes(audioDeviceInfo) : null,
                audioDeviceInfo != null,
                -1,
                str,
                z));
      }
      return true;
    }
  }

  public void onSetCommunicationDeviceForClient(CommunicationDeviceInfo communicationDeviceInfo) {
    Log.v("AS.AudioDeviceBroker", "onSetCommunicationDeviceForClient: " + communicationDeviceInfo);
    if (!communicationDeviceInfo.mOn) {
      CommunicationRouteClient communicationRouteClientForUid =
          getCommunicationRouteClientForUid(communicationDeviceInfo.mUid);
      if (communicationRouteClientForUid == null) {
        return;
      }
      AudioDeviceAttributes audioDeviceAttributes = communicationDeviceInfo.mDevice;
      if (audioDeviceAttributes != null
          && !audioDeviceAttributes.equals(communicationRouteClientForUid.getDevice())) {
        return;
      }
    }
    setCommunicationRouteForClient(
        communicationDeviceInfo.mCb,
        communicationDeviceInfo.mUid,
        communicationDeviceInfo.mOn ? communicationDeviceInfo.mDevice : null,
        communicationDeviceInfo.mScoAudioMode,
        communicationDeviceInfo.mIsPrivileged,
        communicationDeviceInfo.mEventSource);
  }

  public void setCommunicationRouteForClient(
      IBinder iBinder,
      int i,
      AudioDeviceAttributes audioDeviceAttributes,
      int i2,
      boolean z,
      String str) {
    AudioDeviceAttributes audioDeviceAttributes2;
    boolean z2;
    CommunicationRouteClient removeCommunicationRouteClient;
    Log.v(
        "AS.AudioDeviceBroker",
        "setCommunicationRouteForClient: device: "
            + audioDeviceAttributes
            + ", eventSource: "
            + str);
    AudioService.sDeviceLogger.enqueue(
        new EventLogger.StringEvent(
                "setCommunicationRouteForClient for uid: "
                    + i
                    + " device: "
                    + audioDeviceAttributes
                    + " isPrivileged: "
                    + z
                    + " from API: "
                    + str)
            .printLog("AS.AudioDeviceBroker"));
    boolean isBluetoothScoRequested = isBluetoothScoRequested();
    CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
    if (communicationRouteClientForUid != null) {
      audioDeviceAttributes2 = communicationRouteClientForUid.getDevice();
      z2 = communicationRouteClientForUid.isPrivileged();
    } else {
      audioDeviceAttributes2 = null;
      z2 = false;
    }
    if (audioDeviceAttributes != null) {
      if (audioDeviceAttributes.getType() == 7 && this.mBrokerHandler.hasMessages(2764)) {
        this.mBrokerHandler.removeMessages(2764);
        Log.d(
            "AS.AudioDeviceBroker",
            "MSG_CHECK_ABNORMAL_SCO removeMessages when creating Sco client.");
      }
      removeCommunicationRouteClient =
          addCommunicationRouteClient(iBinder, i, audioDeviceAttributes, z);
      if (removeCommunicationRouteClient == null) {
        Log.w(
            "AS.AudioDeviceBroker",
            "setCommunicationRouteForClient: could not add client for uid: "
                + i
                + " and device: "
                + audioDeviceAttributes);
      }
    } else {
      removeCommunicationRouteClient = removeCommunicationRouteClient(iBinder, true, i);
    }
    if (removeCommunicationRouteClient == null) {
      return;
    }
    if (audioDeviceAttributes != null
        && audioDeviceAttributes.getType() == 7
        && str.contains("receiveBtEvent")) {
      checkAndInitRequestBtScoInfo(i, iBinder);
      this.mRequestScoCount++;
    } else {
      resetRequestBtSco();
    }
    boolean isBluetoothScoRequested2 = isBluetoothScoRequested();
    if (!isBluetoothScoRequested2 || (isBluetoothScoRequested && isBluetoothScoActive())) {
      if (!isBluetoothScoRequested2 && isBluetoothScoRequested) {
        this.mBtHelper.stopBluetoothSco(str);
      }
    } else if (!this.mBtHelper.startBluetoothSco(i2, str)) {
      Log.w(
          "AS.AudioDeviceBroker",
          "setCommunicationRouteForClient: failure to start BT SCO for uid: " + i);
      if (audioDeviceAttributes2 != null) {
        addCommunicationRouteClient(iBinder, i, audioDeviceAttributes2, z2);
      } else {
        removeCommunicationRouteClient(iBinder, true, i);
      }
      postBroadcastScoConnectionState(0);
    }
    if (isBluetoothLeAudioRequested() && audioDeviceAttributes != null) {
      int bluetoothContextualVolumeStream = this.mAudioService.getBluetoothContextualVolumeStream();
      int vssVolumeForDevice =
          getVssVolumeForDevice(
              bluetoothContextualVolumeStream, audioDeviceAttributes.getInternalType());
      int maxVssVolumeForStream = getMaxVssVolumeForStream(bluetoothContextualVolumeStream);
      Log.v(
          "AS.AudioDeviceBroker",
          "setCommunicationRouteForClient restoring LE Audio device volume lvl.");
      postSetLeAudioVolumeIndex(
          vssVolumeForDevice, maxVssVolumeForStream, bluetoothContextualVolumeStream);
    }
    updateCommunicationRoute(str);
  }

  public final CommunicationRouteClient topCommunicationRouteClient() {
    Iterator it = this.mCommunicationRouteClients.iterator();
    while (it.hasNext()) {
      CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
      if (communicationRouteClient.getUid() == this.mAudioModeOwner.mUid) {
        return communicationRouteClient;
      }
    }
    if (!this.mCommunicationRouteClients.isEmpty()
        && this.mAudioModeOwner.mPid == 0
        && ((CommunicationRouteClient) this.mCommunicationRouteClients.get(0)).isActive()) {
      return (CommunicationRouteClient) this.mCommunicationRouteClients.get(0);
    }
    return null;
  }

  public final AudioDeviceAttributes requestedCommunicationDevice() {
    CommunicationRouteClient communicationRouteClient = topCommunicationRouteClient();
    AudioDeviceAttributes device =
        communicationRouteClient != null ? communicationRouteClient.getDevice() : null;
    Log.v(
        "AS.AudioDeviceBroker",
        "requestedCommunicationDevice: "
            + device
            + " mAudioModeOwner: "
            + this.mAudioModeOwner.toString());
    return device;
  }

  public static boolean isValidCommunicationDevice(AudioDeviceInfo audioDeviceInfo) {
    return isValidCommunicationDeviceType(audioDeviceInfo.getType());
  }

  public static boolean isValidCommunicationDeviceType(int i) {
    for (int i2 : VALID_COMMUNICATION_DEVICE_TYPES) {
      if (i == i2) {
        return true;
      }
    }
    return false;
  }

  public void postCheckCommunicationDeviceRemoval(AudioDeviceAttributes audioDeviceAttributes) {
    if (isValidCommunicationDeviceType(
        AudioDeviceInfo.convertInternalDeviceToDeviceType(
            audioDeviceAttributes.getInternalType()))) {
      sendLMsgNoDelay(53, 2, audioDeviceAttributes);
    }
  }

  public void onCheckCommunicationDeviceRemoval(AudioDeviceAttributes audioDeviceAttributes) {
    Log.v(
        "AS.AudioDeviceBroker",
        "onCheckCommunicationDeviceRemoval device: " + audioDeviceAttributes.toString());
    Iterator it = this.mCommunicationRouteClients.iterator();
    while (it.hasNext()) {
      CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
      if (audioDeviceAttributes.equals(communicationRouteClient.getDevice())) {
        Log.v(
            "AS.AudioDeviceBroker",
            "onCheckCommunicationDeviceRemoval removing client: "
                + communicationRouteClient.toString());
        postSetCommunicationDeviceForClient(
            new CommunicationDeviceInfo(
                communicationRouteClient.getBinder(),
                communicationRouteClient.getUid(),
                audioDeviceAttributes,
                false,
                -1,
                "onCheckCommunicationDeviceRemoval",
                communicationRouteClient.isPrivileged()));
      }
    }
  }

  public void postCheckCommunicationRouteClientState(int i, boolean z, int i2) {
    CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
    if (communicationRouteClientForUid != null) {
      sendMsgForCheckClientState(56, 0, i, z ? 1 : 0, communicationRouteClientForUid, i2);
    }
  }

  public void onCheckCommunicationRouteClientState(int i, boolean z) {
    CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
    if (communicationRouteClientForUid == null) {
      return;
    }
    updateCommunicationRouteClientState(communicationRouteClientForUid, z);
  }

  public void updateCommunicationRouteClientState(
      CommunicationRouteClient communicationRouteClient, boolean z) {
    boolean isBluetoothScoRequested = isBluetoothScoRequested();
    communicationRouteClient.setPlaybackActive(
        this.mAudioService.isPlaybackActiveForUid(communicationRouteClient.getUid()));
    communicationRouteClient.setRecordingActive(
        this.mAudioService.isRecordingActiveForUid(communicationRouteClient.getUid()));
    if (z != communicationRouteClient.isActive()) {
      postUpdateCommunicationRouteClient(
          isBluetoothScoRequested, "updateCommunicationRouteClientState");
    }
  }

  public void setForceCommunicationClientStateAndDelayedCheck(
      CommunicationRouteClient communicationRouteClient, boolean z, boolean z2) {
    if (communicationRouteClient == null) {
      return;
    }
    if (z) {
      communicationRouteClient.setPlaybackActive(true);
    }
    if (z2) {
      communicationRouteClient.setRecordingActive(true);
    }
    postCheckCommunicationRouteClientState(
        communicationRouteClient.getUid(), communicationRouteClient.isActive(), 6000);
  }

  public static List getAvailableCommunicationDevices() {
    ArrayList arrayList = new ArrayList();
    for (AudioDeviceInfo audioDeviceInfo : AudioManager.getDevicesStatic(2)) {
      if (isValidCommunicationDevice(audioDeviceInfo)) {
        arrayList.add(audioDeviceInfo);
      }
    }
    return arrayList;
  }

  public static /* synthetic */ boolean lambda$getCommunicationDeviceOfType$0(
      int i, AudioDeviceInfo audioDeviceInfo) {
    return audioDeviceInfo.getType() == i;
  }

  public final AudioDeviceInfo getCommunicationDeviceOfType(final int i) {
    return (AudioDeviceInfo)
        getAvailableCommunicationDevices().stream()
            .filter(
                new Predicate() { // from class:
                  // com.android.server.audio.AudioDeviceBroker$$ExternalSyntheticLambda1
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$getCommunicationDeviceOfType$0;
                    lambda$getCommunicationDeviceOfType$0 =
                        AudioDeviceBroker.lambda$getCommunicationDeviceOfType$0(
                            i, (AudioDeviceInfo) obj);
                    return lambda$getCommunicationDeviceOfType$0;
                  }
                })
            .findFirst()
            .orElse(null);
  }

  public AudioDeviceInfo getCommunicationDevice() {
    AudioDeviceInfo communicationDeviceInt;
    synchronized (this.mCommunicationDeviceLock) {
      long currentTimeMillis = System.currentTimeMillis();
      long j = 0;
      while (true) {
        if (this.mCommunicationDeviceUpdateCount <= 0) {
          break;
        }
        try {
          this.mCommunicationDeviceLock.wait(3000 - j);
        } catch (InterruptedException unused) {
          Log.w(
              "AS.AudioDeviceBroker", "Interrupted while waiting for communication device update.");
        }
        j = System.currentTimeMillis() - currentTimeMillis;
        if (j >= 3000) {
          Log.e("AS.AudioDeviceBroker", "Timeout waiting for communication device update.");
          break;
        }
      }
    }
    synchronized (this.mDeviceStateLock) {
      communicationDeviceInt = getCommunicationDeviceInt();
    }
    return communicationDeviceInt;
  }

  public final AudioDeviceInfo getCommunicationDeviceInt() {
    updateActiveCommunicationDevice();
    AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
    if (audioDeviceInfo != null && audioDeviceInfo.getType() == 13) {
      audioDeviceInfo = getCommunicationDeviceOfType(2);
    }
    if (audioDeviceInfo != null && isValidCommunicationDevice(audioDeviceInfo)) {
      return audioDeviceInfo;
    }
    AudioDeviceInfo communicationDeviceOfType = getCommunicationDeviceOfType(1);
    if (communicationDeviceOfType != null) {
      return communicationDeviceOfType;
    }
    List availableCommunicationDevices = getAvailableCommunicationDevices();
    return !availableCommunicationDevices.isEmpty()
        ? (AudioDeviceInfo) availableCommunicationDevices.get(0)
        : communicationDeviceOfType;
  }

  public void updateActiveCommunicationDevice() {
    AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
    if (preferredCommunicationDevice == null) {
      ArrayList devicesForAttributes =
          this.mAudioSystem.getDevicesForAttributes(
              AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(0), false);
      if (devicesForAttributes.isEmpty()) {
        if (this.mAudioService.isPlatformVoice()) {
          Log.w(
              "AS.AudioDeviceBroker",
              "updateActiveCommunicationDevice(): no device for phone strategy");
        }
        this.mActiveCommunicationDevice = null;
        return;
      }
      preferredCommunicationDevice = (AudioDeviceAttributes) devicesForAttributes.get(0);
    }
    this.mActiveCommunicationDevice =
        AudioManager.getDeviceInfoFromTypeAndAddress(
            preferredCommunicationDevice.getType(), preferredCommunicationDevice.getAddress());
  }

  public final boolean isDeviceRequestedForCommunication(int i) {
    boolean z;
    synchronized (this.mDeviceStateLock) {
      AudioDeviceAttributes requestedCommunicationDevice = requestedCommunicationDevice();
      z = requestedCommunicationDevice != null && requestedCommunicationDevice.getType() == i;
    }
    return z;
  }

  public final boolean isDeviceOnForCommunication(int i) {
    boolean z;
    synchronized (this.mDeviceStateLock) {
      AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
      z = preferredCommunicationDevice != null && preferredCommunicationDevice.getType() == i;
    }
    return z;
  }

  public final boolean isDeviceActiveForCommunication(int i) {
    AudioDeviceAttributes audioDeviceAttributes;
    AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
    return audioDeviceInfo != null
        && audioDeviceInfo.getType() == i
        && (audioDeviceAttributes = this.mPreferredCommunicationDevice) != null
        && audioDeviceAttributes.getType() == i;
  }

  public boolean isSpeakerphoneOn() {
    return isDeviceOnForCommunication(2);
  }

  public final boolean isSpeakerphoneActive() {
    return isDeviceActiveForCommunication(2);
  }

  public boolean isBluetoothScoRequested() {
    return isDeviceRequestedForCommunication(7);
  }

  public boolean isBluetoothLeAudioRequested() {
    return isDeviceRequestedForCommunication(26) || isDeviceRequestedForCommunication(27);
  }

  public boolean isBluetoothScoOn() {
    return isDeviceOnForCommunication(7);
  }

  public boolean isBluetoothScoActive() {
    return isDeviceActiveForCommunication(7);
  }

  public boolean isDeviceConnected(AudioDeviceAttributes audioDeviceAttributes) {
    boolean isDeviceConnected;
    synchronized (this.mDeviceStateLock) {
      isDeviceConnected = this.mDeviceInventory.isDeviceConnected(audioDeviceAttributes);
    }
    return isDeviceConnected;
  }

  public void setWiredDeviceConnectionState(
      AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
    synchronized (this.mDeviceStateLock) {
      this.mDeviceInventory.setWiredDeviceConnectionState(audioDeviceAttributes, i, str);
    }
  }

  public void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i) {
    synchronized (this.mDeviceStateLock) {
      this.mDeviceInventory.setTestDeviceConnectionState(audioDeviceAttributes, i);
    }
  }

  public final class BleVolumeInfo {
    public final int mIndex;
    public final int mMaxIndex;
    public final int mStreamType;

    public BleVolumeInfo(int i, int i2, int i3) {
      this.mIndex = i;
      this.mMaxIndex = i2;
      this.mStreamType = i3;
    }
  }

  public final class BtDeviceChangedData {
    public final String mEventSource;
    public final BluetoothProfileConnectionInfo mInfo;
    public final BluetoothDevice mNewDevice;
    public final BluetoothDevice mPreviousDevice;

    public BtDeviceChangedData(
        BluetoothDevice bluetoothDevice,
        BluetoothDevice bluetoothDevice2,
        BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo,
        String str) {
      this.mNewDevice = bluetoothDevice;
      this.mPreviousDevice = bluetoothDevice2;
      this.mInfo = bluetoothProfileConnectionInfo;
      this.mEventSource = str;
    }

    public String toString() {
      return "BtDeviceChangedData profile="
          + BluetoothProfile.getProfileName(this.mInfo.getProfile())
          + ", switch device: ["
          + this.mPreviousDevice
          + "] -> ["
          + this.mNewDevice
          + "]";
    }
  }

  public final class BtDeviceInfo {
    public final int mAudioSystemDevice;
    public final int mCodec;
    public final BluetoothDevice mDevice;
    public final String mEventSource;
    public final boolean mIsLeOutput;
    public final int mMusicDevice;
    public final int mProfile;
    public final int mState;
    public final boolean mSupprNoisy;
    public final int mVolume;

    public BtDeviceInfo(
        BtDeviceChangedData btDeviceChangedData,
        BluetoothDevice bluetoothDevice,
        int i,
        int i2,
        int i3) {
      this.mDevice = bluetoothDevice;
      this.mState = i;
      this.mProfile = btDeviceChangedData.mInfo.getProfile();
      this.mSupprNoisy = btDeviceChangedData.mInfo.isSuppressNoisyIntent();
      this.mVolume = btDeviceChangedData.mInfo.getVolume();
      this.mIsLeOutput = btDeviceChangedData.mInfo.isLeOutput();
      this.mEventSource = btDeviceChangedData.mEventSource;
      this.mAudioSystemDevice = i2;
      this.mMusicDevice = 0;
      this.mCodec = i3;
    }

    public BtDeviceInfo(BluetoothDevice bluetoothDevice, int i) {
      this.mDevice = bluetoothDevice;
      this.mProfile = i;
      this.mEventSource = "";
      this.mMusicDevice = 0;
      this.mCodec = 0;
      this.mAudioSystemDevice = 0;
      this.mState = 0;
      this.mSupprNoisy = false;
      this.mVolume = -1;
      this.mIsLeOutput = false;
    }

    public BtDeviceInfo(BtDeviceInfo btDeviceInfo, int i) {
      this.mDevice = btDeviceInfo.mDevice;
      this.mState = i;
      this.mProfile = btDeviceInfo.mProfile;
      this.mSupprNoisy = btDeviceInfo.mSupprNoisy;
      this.mVolume = btDeviceInfo.mVolume;
      this.mIsLeOutput = btDeviceInfo.mIsLeOutput;
      this.mEventSource = btDeviceInfo.mEventSource;
      this.mAudioSystemDevice = btDeviceInfo.mAudioSystemDevice;
      this.mMusicDevice = btDeviceInfo.mMusicDevice;
      this.mCodec = btDeviceInfo.mCodec;
    }

    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof BtDeviceInfo)) {
        return false;
      }
      BtDeviceInfo btDeviceInfo = (BtDeviceInfo) obj;
      return this.mProfile == btDeviceInfo.mProfile && this.mDevice.equals(btDeviceInfo.mDevice);
    }
  }

  public BtDeviceInfo createBtDeviceInfo(
      BtDeviceChangedData btDeviceChangedData, BluetoothDevice bluetoothDevice, int i) {
    int a2dpCodec;
    int i2;
    int profile = btDeviceChangedData.mInfo.getProfile();
    if (profile != 2) {
      a2dpCodec = 0;
      if (profile == 11) {
        i2 = -2147352576;
      } else if (profile == 26) {
        i2 = 536870914;
      } else if (profile == 21) {
        i2 = 134217728;
      } else if (profile == 22) {
        i2 = btDeviceChangedData.mInfo.isLeOutput() ? 536870912 : -1610612736;
      } else {
        throw new IllegalArgumentException(
            "Invalid profile " + btDeviceChangedData.mInfo.getProfile());
      }
    } else {
      synchronized (this.mDeviceStateLock) {
        a2dpCodec = this.mBtHelper.getA2dpCodec(bluetoothDevice);
      }
      i2 = 128;
    }
    return new BtDeviceInfo(btDeviceChangedData, bluetoothDevice, i, i2, a2dpCodec);
  }

  public final void btMediaMetricRecord(
      BluetoothDevice bluetoothDevice, String str, BtDeviceChangedData btDeviceChangedData) {
    new MediaMetrics.Item("audio.device.queueOnBluetoothActiveDeviceChanged")
        .set(MediaMetrics.Property.STATE, str)
        .set(MediaMetrics.Property.STATUS, Integer.valueOf(btDeviceChangedData.mInfo.getProfile()))
        .set(MediaMetrics.Property.NAME, TextUtils.emptyIfNull(bluetoothDevice.getName()))
        .record();
  }

  public void queueOnBluetoothActiveDeviceChanged(BtDeviceChangedData btDeviceChangedData) {
    BluetoothDevice bluetoothDevice;
    BluetoothDevice bluetoothDevice2;
    if (btDeviceChangedData.mInfo.getProfile() == 2
        && (bluetoothDevice2 = btDeviceChangedData.mPreviousDevice) != null
        && bluetoothDevice2.equals(btDeviceChangedData.mNewDevice)) {
      new MediaMetrics.Item("audio.device.queueOnBluetoothActiveDeviceChanged_update")
          .set(
              MediaMetrics.Property.NAME,
              TextUtils.emptyIfNull(btDeviceChangedData.mNewDevice.getName()))
          .set(
              MediaMetrics.Property.STATUS, Integer.valueOf(btDeviceChangedData.mInfo.getProfile()))
          .record();
      synchronized (this.mDeviceStateLock) {
        Log.i("AS.AudioDeviceBroker", "queueOnBluetoothActiveDeviceChanged A2DP config change");
        postBluetoothDeviceConfigChange(
            createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
      }
      AudioService.mBtDeviceChangedData = btDeviceChangedData;
      return;
    }
    synchronized (this.mDeviceStateLock) {
      if (btDeviceChangedData.mInfo.getProfile() == 22
          && (bluetoothDevice = btDeviceChangedData.mPreviousDevice) != null
          && bluetoothDevice.equals(btDeviceChangedData.mNewDevice)) {
        btMediaMetricRecord(btDeviceChangedData.mNewDevice, "connected", btDeviceChangedData);
        Log.i(
            "AS.AudioDeviceBroker",
            "queueOnBluetoothActiveDeviceChanged BLE STATE_CONNECTED_IMPLICIT");
        sendLMsgNoDelay(
            45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 99));
        return;
      }
      BluetoothDevice bluetoothDevice3 = btDeviceChangedData.mPreviousDevice;
      if (bluetoothDevice3 != null) {
        btMediaMetricRecord(bluetoothDevice3, "disconnected", btDeviceChangedData);
        sendLMsgNoDelay(
            45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mPreviousDevice, 0));
      }
      BluetoothDevice bluetoothDevice4 = btDeviceChangedData.mNewDevice;
      if (bluetoothDevice4 != null) {
        btMediaMetricRecord(bluetoothDevice4, "connected", btDeviceChangedData);
        sendLMsgNoDelay(
            45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
      }
    }
  }

  public final void initAudioHalBluetoothState() {
    synchronized (this.mBluetoothAudioStateLock) {
      this.mBluetoothScoOnApplied = false;
      AudioSystem.setParameters("BT_SCO=off");
      this.mBluetoothA2dpSuspendedApplied = false;
      AudioSystem.setParameters("A2dpSuspended=false");
      this.mBluetoothLeSuspendedApplied = false;
      AudioSystem.setParameters("LeAudioSuspended=false");
    }
  }

  public final void updateAudioHalBluetoothState() {
    boolean z = true;
    if (this.mBluetoothScoOn != this.mBluetoothScoOnApplied) {
      Log.v(
          "AS.AudioDeviceBroker",
          "updateAudioHalBluetoothState() mBluetoothScoOn: "
              + this.mBluetoothScoOn
              + ", mBluetoothScoOnApplied: "
              + this.mBluetoothScoOnApplied);
      if (this.mBluetoothScoOn) {
        if (!this.mBluetoothA2dpSuspendedApplied) {
          AudioSystem.setParameters("A2dpSuspended=true");
          this.mBluetoothA2dpSuspendedApplied = true;
        }
        if (!this.mBluetoothLeSuspendedApplied) {
          AudioSystem.setParameters("LeAudioSuspended=true");
          this.mBluetoothLeSuspendedApplied = true;
        }
        if (!isBluetoothScoOnForAs()) {
          AudioSystem.setParameters("BT_SCO=off");
        } else {
          AudioSystem.setParameters("BT_SCO=on");
        }
      } else {
        AudioSystem.setParameters("BT_SCO=off");
      }
      this.mBluetoothScoOnApplied = this.mBluetoothScoOn;
    }
    if (this.mBluetoothScoOnApplied) {
      return;
    }
    if ((this.mBluetoothA2dpSuspendedExt || this.mBluetoothA2dpSuspendedInt)
        != this.mBluetoothA2dpSuspendedApplied) {
      Log.v(
          "AS.AudioDeviceBroker",
          "updateAudioHalBluetoothState() mBluetoothA2dpSuspendedExt: "
              + this.mBluetoothA2dpSuspendedExt
              + ", mBluetoothA2dpSuspendedInt: "
              + this.mBluetoothA2dpSuspendedInt
              + ", mBluetoothA2dpSuspendedApplied: "
              + this.mBluetoothA2dpSuspendedApplied);
      boolean z2 = this.mBluetoothA2dpSuspendedExt || this.mBluetoothA2dpSuspendedInt;
      this.mBluetoothA2dpSuspendedApplied = z2;
      if (z2) {
        AudioSystem.setParameters("A2dpSuspended=true");
      } else {
        AudioSystem.setParameters("A2dpSuspended=false");
      }
    }
    if ((this.mBluetoothLeSuspendedExt || this.mBluetoothLeSuspendedInt)
        != this.mBluetoothLeSuspendedApplied) {
      Log.v(
          "AS.AudioDeviceBroker",
          "updateAudioHalBluetoothState() mBluetoothLeSuspendedExt: "
              + this.mBluetoothLeSuspendedExt
              + ", mBluetoothLeSuspendedInt: "
              + this.mBluetoothLeSuspendedInt
              + ", mBluetoothLeSuspendedApplied: "
              + this.mBluetoothLeSuspendedApplied);
      if (!this.mBluetoothLeSuspendedExt && !this.mBluetoothLeSuspendedInt) {
        z = false;
      }
      this.mBluetoothLeSuspendedApplied = z;
      if (z) {
        AudioSystem.setParameters("LeAudioSuspended=true");
      } else {
        AudioSystem.setParameters("LeAudioSuspended=false");
      }
    }
  }

  public void setBluetoothScoOn(boolean z, String str) {
    Log.v("AS.AudioDeviceBroker", "setBluetoothScoOn: " + z + " " + str);
    boolean isBluetoothScoRequested = isBluetoothScoRequested();
    synchronized (this.mBluetoothAudioStateLock) {
      this.mBluetoothScoOn = z;
      updateAudioHalBluetoothState();
      postUpdateCommunicationRouteClient(isBluetoothScoRequested, str);
      resetBtScoOnByApp();
    }
  }

  public void setA2dpSuspended(boolean z, boolean z2, String str) {
    synchronized (this.mBluetoothAudioStateLock) {
      Log.v(
          "AS.AudioDeviceBroker",
          "setA2dpSuspended source: "
              + str
              + ", enable: "
              + z
              + ", internal: "
              + z2
              + ", mBluetoothA2dpSuspendedInt: "
              + this.mBluetoothA2dpSuspendedInt
              + ", mBluetoothA2dpSuspendedExt: "
              + this.mBluetoothA2dpSuspendedExt);
      if (z2) {
        this.mBluetoothA2dpSuspendedInt = z;
      } else {
        this.mBluetoothA2dpSuspendedExt = z;
      }
      updateAudioHalBluetoothState();
    }
  }

  public void clearA2dpSuspended(boolean z) {
    Log.v("AS.AudioDeviceBroker", "clearA2dpSuspended, internalOnly: " + z);
    synchronized (this.mBluetoothAudioStateLock) {
      this.mBluetoothA2dpSuspendedInt = false;
      if (!z) {
        this.mBluetoothA2dpSuspendedExt = false;
      }
      updateAudioHalBluetoothState();
    }
  }

  public void setLeAudioSuspended(boolean z, boolean z2, String str) {
    synchronized (this.mBluetoothAudioStateLock) {
      Log.v(
          "AS.AudioDeviceBroker",
          "setLeAudioSuspended source: "
              + str
              + ", enable: "
              + z
              + ", internal: "
              + z2
              + ", mBluetoothLeSuspendedInt: "
              + this.mBluetoothA2dpSuspendedInt
              + ", mBluetoothLeSuspendedExt: "
              + this.mBluetoothA2dpSuspendedExt);
      if (z2) {
        this.mBluetoothLeSuspendedInt = z;
      } else {
        this.mBluetoothLeSuspendedExt = z;
      }
      updateAudioHalBluetoothState();
    }
  }

  public void clearLeAudioSuspended(boolean z) {
    Log.v("AS.AudioDeviceBroker", "clearLeAudioSuspended, internalOnly: " + z);
    synchronized (this.mBluetoothAudioStateLock) {
      this.mBluetoothLeSuspendedInt = false;
      if (!z) {
        this.mBluetoothLeSuspendedExt = false;
      }
      updateAudioHalBluetoothState();
    }
  }

  public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) {
    AudioRoutesInfo startWatchingRoutes;
    synchronized (this.mDeviceStateLock) {
      startWatchingRoutes = this.mDeviceInventory.startWatchingRoutes(iAudioRoutesObserver);
    }
    return startWatchingRoutes;
  }

  public AudioRoutesInfo getCurAudioRoutes() {
    AudioRoutesInfo curAudioRoutes;
    synchronized (this.mDeviceStateLock) {
      curAudioRoutes = this.mDeviceInventory.getCurAudioRoutes();
    }
    return curAudioRoutes;
  }

  public boolean isBluetoothA2dpOn() {
    boolean z;
    synchronized (this.mDeviceStateLock) {
      z = this.mBluetoothA2dpEnabled;
    }
    return z;
  }

  public void postSetAvrcpAbsoluteVolumeIndex(int i) {
    sendIMsgNoDelay(15, 0, i);
  }

  public void postSetHearingAidVolumeIndex(int i, int i2) {
    sendIIMsgNoDelay(14, 0, i, i2);
  }

  public void postSetLeAudioVolumeIndex(int i, int i2, int i3) {
    sendLMsgNoDelay(46, 0, new BleVolumeInfo(i, i2, i3));
  }

  public void postSetModeOwner(int i, int i2, int i3) {
    sendLMsgNoDelay(16, 0, new AudioModeInfo(i, i2, i3));
  }

  public void postBluetoothDeviceConfigChange(BtDeviceInfo btDeviceInfo) {
    sendLMsgNoDelay(11, 2, btDeviceInfo);
  }

  public void startBluetoothScoForClient(IBinder iBinder, int i, int i2, boolean z, String str) {
    Log.v("AS.AudioDeviceBroker", "startBluetoothScoForClient, uid: " + i);
    postSetCommunicationDeviceForClient(
        new CommunicationDeviceInfo(
            iBinder, i, new AudioDeviceAttributes(16, ""), true, i2, str, z));
  }

  public void stopBluetoothScoForClient(IBinder iBinder, int i, boolean z, String str) {
    Log.v("AS.AudioDeviceBroker", "stopBluetoothScoForClient, uid: " + i);
    postSetCommunicationDeviceForClient(
        new CommunicationDeviceInfo(
            iBinder, i, new AudioDeviceAttributes(16, ""), false, -1, str, z));
  }

  public int setPreferredDevicesForStrategySync(int i, List list) {
    return this.mDeviceInventory.setPreferredDevicesForStrategyAndSave(i, list);
  }

  public int removePreferredDevicesForStrategySync(int i) {
    return this.mDeviceInventory.removePreferredDevicesForStrategyAndSave(i);
  }

  public int setDeviceAsNonDefaultForStrategySync(
      int i, AudioDeviceAttributes audioDeviceAttributes) {
    return this.mDeviceInventory.setDeviceAsNonDefaultForStrategyAndSave(i, audioDeviceAttributes);
  }

  public int removeDeviceAsNonDefaultForStrategySync(
      int i, AudioDeviceAttributes audioDeviceAttributes) {
    return this.mDeviceInventory.removeDeviceAsNonDefaultForStrategyAndSave(
        i, audioDeviceAttributes);
  }

  public void registerStrategyPreferredDevicesDispatcher(
      IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
    this.mDeviceInventory.registerStrategyPreferredDevicesDispatcher(
        iStrategyPreferredDevicesDispatcher);
  }

  public void unregisterStrategyPreferredDevicesDispatcher(
      IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
    this.mDeviceInventory.unregisterStrategyPreferredDevicesDispatcher(
        iStrategyPreferredDevicesDispatcher);
  }

  public void registerStrategyNonDefaultDevicesDispatcher(
      IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
    this.mDeviceInventory.registerStrategyNonDefaultDevicesDispatcher(
        iStrategyNonDefaultDevicesDispatcher);
  }

  public void unregisterStrategyNonDefaultDevicesDispatcher(
      IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
    this.mDeviceInventory.unregisterStrategyNonDefaultDevicesDispatcher(
        iStrategyNonDefaultDevicesDispatcher);
  }

  public int setPreferredDevicesForCapturePresetSync(int i, List list) {
    return this.mDeviceInventory.setPreferredDevicesForCapturePresetAndSave(i, list);
  }

  public int clearPreferredDevicesForCapturePresetSync(int i) {
    return this.mDeviceInventory.clearPreferredDevicesForCapturePresetAndSave(i);
  }

  public void registerCapturePresetDevicesRoleDispatcher(
      ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
    this.mDeviceInventory.registerCapturePresetDevicesRoleDispatcher(
        iCapturePresetDevicesRoleDispatcher);
  }

  public void unregisterCapturePresetDevicesRoleDispatcher(
      ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
    this.mDeviceInventory.unregisterCapturePresetDevicesRoleDispatcher(
        iCapturePresetDevicesRoleDispatcher);
  }

  public void registerCommunicationDeviceDispatcher(
      ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
    this.mCommDevDispatchers.register(iCommunicationDeviceDispatcher);
  }

  public void unregisterCommunicationDeviceDispatcher(
      ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
    this.mCommDevDispatchers.unregister(iCommunicationDeviceDispatcher);
  }

  public final void dispatchCommunicationDevice() {
    AudioDeviceInfo communicationDeviceInt = getCommunicationDeviceInt();
    int id = communicationDeviceInt != null ? communicationDeviceInt.getId() : 0;
    if (id == this.mCurCommunicationPortId) {
      return;
    }
    this.mCurCommunicationPortId = id;
    int beginBroadcast = this.mCommDevDispatchers.beginBroadcast();
    for (int i = 0; i < beginBroadcast; i++) {
      try {
        this.mCommDevDispatchers.getBroadcastItem(i).dispatchCommunicationDeviceChanged(id);
      } catch (RemoteException unused) {
      }
    }
    this.mCommDevDispatchers.finishBroadcast();
  }

  public void postAccessoryPlugMediaUnmute(int i) {
    this.mAudioService.postAccessoryPlugMediaUnmute(i);
  }

  public int getVssVolumeForDevice(int i, int i2) {
    return this.mAudioService.getVssVolumeForDevice(i, i2);
  }

  public int getMaxVssVolumeForStream(int i) {
    return this.mAudioService.getMaxVssVolumeForStream(i);
  }

  public int getDeviceForStream(int i) {
    return this.mAudioService.getDeviceForStream(i);
  }

  public void postApplyVolumeOnDevice(int i, int i2, String str) {
    this.mAudioService.postApplyVolumeOnDevice(i, i2, str);
  }

  public void postSetVolumeIndexOnDevice(int i, int i2, int i3, String str) {
    this.mAudioService.postSetVolumeIndexOnDevice(i, i2, i3, str);
  }

  public void postObserveDevicesForAllStreams() {
    this.mAudioService.postObserveDevicesForAllStreams();
  }

  public boolean isInCommunication() {
    return this.mAudioService.isInCommunication() || this.mAudioService.isRingtoneMode();
  }

  public boolean hasMediaDynamicPolicy() {
    return this.mAudioService.hasMediaDynamicPolicy();
  }

  public ContentResolver getContentResolver() {
    return this.mAudioService.getContentResolver();
  }

  public void checkMusicActive(int i, String str) {
    this.mAudioService.checkMusicActive(i, str);
  }

  public void checkVolumeCecOnHdmiConnection(int i, String str) {
    this.mAudioService.postCheckVolumeCecOnHdmiConnection(i, str);
  }

  public boolean hasAudioFocusUsers() {
    return this.mAudioService.hasAudioFocusUsers();
  }

  public void postBroadcastScoConnectionState(int i) {
    sendIMsgNoDelay(3, 2, i);
  }

  public void postBroadcastBecomingNoisy() {
    sendMsgNoDelay(12, 0);
  }

  public void postBluetoothActiveDevice(BtDeviceInfo btDeviceInfo, int i) {
    sendLMsg(7, 2, btDeviceInfo, i);
  }

  public void postSetWiredDeviceConnectionState(
      AudioDeviceInventory.WiredDeviceConnectionState wiredDeviceConnectionState, int i) {
    sendLMsg(2, 2, wiredDeviceConnectionState, i);
  }

  public void postBtProfileDisconnected(int i) {
    sendIMsgNoDelay(22, 2, i);
  }

  public void postBtProfileConnected(int i, BluetoothProfile bluetoothProfile) {
    sendILMsgNoDelay(23, 2, i, bluetoothProfile);
  }

  public void postCommunicationRouteClientDied(CommunicationRouteClient communicationRouteClient) {
    sendLMsgNoDelay(34, 2, communicationRouteClient);
  }

  public void postSaveSetPreferredDevicesForStrategy(int i, List list) {
    sendILMsgNoDelay(32, 2, i, list);
  }

  public void postSaveRemovePreferredDevicesForStrategy(int i) {
    sendIMsgNoDelay(33, 2, i);
  }

  public void postSaveSetDeviceAsNonDefaultForStrategy(
      int i, AudioDeviceAttributes audioDeviceAttributes) {
    sendILMsgNoDelay(47, 2, i, audioDeviceAttributes);
  }

  public void postSaveRemoveDeviceAsNonDefaultForStrategy(
      int i, AudioDeviceAttributes audioDeviceAttributes) {
    sendILMsgNoDelay(48, 2, i, audioDeviceAttributes);
  }

  public void postSaveSetPreferredDevicesForCapturePreset(int i, List list) {
    sendILMsgNoDelay(37, 2, i, list);
  }

  public void postSaveClearPreferredDevicesForCapturePreset(int i) {
    sendIMsgNoDelay(38, 2, i);
  }

  public void postUpdateCommunicationRouteClient(boolean z, String str) {
    sendILMsgNoDelay(43, 2, z ? 1 : 0, str);
  }

  public void postSetCommunicationDeviceForClient(CommunicationDeviceInfo communicationDeviceInfo) {
    sendLMsgNoDelay(42, 2, communicationDeviceInfo);
  }

  public void postScoAudioStateChanged(int i) {
    sendIMsgNoDelay(44, 2, i);
  }

  public void postNotifyPreferredAudioProfileApplied(BluetoothDevice bluetoothDevice) {
    sendLMsgNoDelay(52, 2, bluetoothDevice);
  }

  public final class CommunicationDeviceInfo {
    public final IBinder mCb;
    public final AudioDeviceAttributes mDevice;
    public final String mEventSource;
    public final boolean mIsPrivileged;
    public final boolean mOn;
    public final int mScoAudioMode;
    public final int mUid;

    public CommunicationDeviceInfo(
        IBinder iBinder,
        int i,
        AudioDeviceAttributes audioDeviceAttributes,
        boolean z,
        int i2,
        String str,
        boolean z2) {
      this.mCb = iBinder;
      this.mUid = i;
      this.mDevice = audioDeviceAttributes;
      this.mOn = z;
      this.mScoAudioMode = i2;
      this.mIsPrivileged = z2;
      this.mEventSource = str;
    }

    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof CommunicationDeviceInfo)) {
        return false;
      }
      CommunicationDeviceInfo communicationDeviceInfo = (CommunicationDeviceInfo) obj;
      return this.mCb.equals(communicationDeviceInfo.mCb)
          && this.mUid == communicationDeviceInfo.mUid;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("CommunicationDeviceInfo mCb=");
      sb.append(this.mCb.toString());
      sb.append(" mUid=");
      sb.append(this.mUid);
      sb.append(" mDevice=[");
      AudioDeviceAttributes audioDeviceAttributes = this.mDevice;
      sb.append(audioDeviceAttributes != null ? audioDeviceAttributes.toString() : "null");
      sb.append("] mOn=");
      sb.append(this.mOn);
      sb.append(" mScoAudioMode=");
      sb.append(this.mScoAudioMode);
      sb.append(" mIsPrivileged=");
      sb.append(this.mIsPrivileged);
      sb.append(" mEventSource=");
      sb.append(this.mEventSource);
      return sb.toString();
    }
  }

  public void setBluetoothA2dpOnInt(boolean z, boolean z2, String str) {
    String str2 =
        "setBluetoothA2dpOn("
            + z
            + ") from u/pid:"
            + Binder.getCallingUid()
            + "/"
            + Binder.getCallingPid()
            + " src:"
            + str;
    synchronized (this.mDeviceStateLock) {
      this.mBluetoothA2dpEnabled = z;
      this.mBrokerHandler.removeMessages(5);
      onSetForceUse(1, this.mBluetoothA2dpEnabled ? 0 : 10, z2, str2);
    }
  }

  public boolean handleDeviceConnection(
      AudioDeviceAttributes audioDeviceAttributes, boolean z, BluetoothDevice bluetoothDevice) {
    boolean handleDeviceConnection;
    synchronized (this.mDeviceStateLock) {
      handleDeviceConnection =
          this.mDeviceInventory.handleDeviceConnection(
              audioDeviceAttributes, z, false, bluetoothDevice);
    }
    return handleDeviceConnection;
  }

  public void handleFailureToConnectToBtHeadsetService(int i) {
    sendMsg(9, 0, i);
  }

  public void handleCancelFailureToConnectToBtHeadsetService() {
    this.mBrokerHandler.removeMessages(9);
  }

  public void postReportNewRoutes(boolean z) {
    sendMsgNoDelay(z ? 36 : 13, 1);
  }

  public boolean hasScheduledA2dpConnection(BluetoothDevice bluetoothDevice) {
    return this.mBrokerHandler.hasEqualMessages(7, new BtDeviceInfo(bluetoothDevice, 2));
  }

  public void setA2dpTimeout(String str, int i, int i2) {
    sendILMsg(10, 2, i, str, i2);
  }

  public void setLeAudioTimeout(String str, int i, int i2) {
    sendILMsg(49, 2, i, str, i2);
  }

  public boolean getBluetoothA2dpEnabled() {
    boolean z;
    synchronized (this.mDeviceStateLock) {
      z = this.mBluetoothA2dpEnabled;
    }
    return z;
  }

  public void broadcastStickyIntentToCurrentProfileGroup(Intent intent) {
    this.mSystemServer.broadcastStickyIntentToCurrentProfileGroup(intent);
  }

  public void dump(final PrintWriter printWriter, final String str) {
    if (this.mBrokerHandler != null) {
      printWriter.println(str + "Message handler (watch for unhandled messages):");
      this.mBrokerHandler.dump(new PrintWriterPrinter(printWriter), str + "  ");
    } else {
      printWriter.println("Message handler is null");
    }
    this.mDeviceInventory.dump(printWriter, str);
    printWriter.println(
        KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Communication route clients:");
    this.mCommunicationRouteClients.forEach(
        new Consumer() { // from class:
          // com.android.server.audio.AudioDeviceBroker$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            AudioDeviceBroker.lambda$dump$1(
                printWriter, str, (AudioDeviceBroker.CommunicationRouteClient) obj);
          }
        });
    printWriter.println(
        KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
            + str
            + "Computed Preferred communication device: "
            + preferredCommunicationDevice());
    printWriter.println(
        KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
            + str
            + "Applied Preferred communication device: "
            + this.mPreferredCommunicationDevice);
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append("Active communication device: ");
    sb.append(
        (Object)
            (this.mActiveCommunicationDevice == null
                ? "None"
                : new AudioDeviceAttributes(this.mActiveCommunicationDevice)));
    printWriter.println(sb.toString());
    printWriter.println(str + "mCommunicationStrategyId: " + this.mCommunicationStrategyId);
    printWriter.println(str + "mAccessibilityStrategyId: " + this.mAccessibilityStrategyId);
    printWriter.println(
        KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
            + str
            + "mAudioModeOwner: "
            + this.mAudioModeOwner);
    this.mBtHelper.dump(printWriter, str);
  }

  public static /* synthetic */ void lambda$dump$1(
      PrintWriter printWriter, String str, CommunicationRouteClient communicationRouteClient) {
    printWriter.println("  " + str + communicationRouteClient.toString());
  }

  public final void onSetForceUse(int i, int i2, boolean z, String str) {
    if (i == 1) {
      postReportNewRoutes(z);
    }
    AudioService.sForceUseLogger.enqueue(new AudioServiceEvents$ForceUseEvent(i, i2, str));
    new MediaMetrics.Item("audio.forceUse." + AudioSystem.forceUseUsageToString(i))
        .set(MediaMetrics.Property.EVENT, "onSetForceUse")
        .set(MediaMetrics.Property.FORCE_USE_DUE_TO, str)
        .set(MediaMetrics.Property.FORCE_USE_MODE, AudioSystem.forceUseConfigToString(i2))
        .record();
    Log.v(
        "AS.AudioDeviceBroker",
        "onSetForceUse(useCase<"
            + i
            + ">, config<"
            + i2
            + ">, fromA2dp<"
            + z
            + ">, eventSource<"
            + str
            + ">)");
    this.mAudioSystem.setForceUse(i, i2);
  }

  public final void setupMessaging(Context context) {
    this.mBrokerEventWakeLock =
        ((PowerManager) context.getSystemService("power")).newWakeLock(1, "handleAudioDeviceEvent");
    BrokerThread brokerThread = new BrokerThread();
    this.mBrokerThread = brokerThread;
    brokerThread.start();
    waitForBrokerHandlerCreation();
  }

  public final void waitForBrokerHandlerCreation() {
    synchronized (this) {
      while (this.mBrokerHandler == null) {
        try {
          wait();
        } catch (InterruptedException unused) {
          Log.e("AS.AudioDeviceBroker", "Interruption while waiting on BrokerHandler");
        }
      }
    }
  }

  public class BrokerThread extends Thread {
    public BrokerThread() {
      super("AudioDeviceBroker");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      Looper.prepare();
      synchronized (AudioDeviceBroker.this) {
        AudioDeviceBroker.this.mBrokerHandler = new BrokerHandler();
        AudioDeviceBroker.this.notify();
      }
      Looper.loop();
    }
  }

  public class BrokerHandler extends Handler {
    public BrokerHandler() {}

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0330  */
    @Override // android.os.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleMessage(Message message) {
      int i;
      int bluetoothContextualVolumeStream;
      int i2;
      int i3 = message.what;
      if (i3 != 22) {
        if (i3 != 23) {
          if (i3 == 52) {
            BtHelper.onNotifyPreferredAudioProfileApplied((BluetoothDevice) message.obj);
          } else if (i3 == 53) {
            synchronized (AudioDeviceBroker.this.mSetModeLock) {
              synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                AudioDeviceBroker.this.onCheckCommunicationDeviceRemoval(
                    (AudioDeviceAttributes) message.obj);
              }
            }
          } else if (i3 == 56) {
            synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
              AudioDeviceBroker audioDeviceBroker = AudioDeviceBroker.this;
              int i4 = message.arg1;
              if (message.arg2 != 1) {
                r3 = false;
              }
              audioDeviceBroker.onCheckCommunicationRouteClientState(i4, r3);
            }
          } else if (i3 == 2763) {
            AudioDeviceBroker.this.checkRCVStateForSARTest();
          } else if (i3 != 2764) {
            switch (i3) {
              case 1:
                synchronized (AudioDeviceBroker.this.mSetModeLock) {
                  synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                    AudioDeviceBroker.this.initRoutingStrategyIds();
                    AudioDeviceBroker.this.updateActiveCommunicationDevice();
                    AudioDeviceBroker.this.mDeviceInventory.onRestoreDevices();
                    AudioDeviceBroker.this.mBtHelper.onAudioServerDiedRestoreA2dp();
                    AudioDeviceBroker.this.updateCommunicationRoute("MSG_RESTORE_DEVICES");
                  }
                  break;
                }
              case 2:
                synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                  AudioDeviceBroker.this.mDeviceInventory.onSetWiredDeviceConnectionState(
                      (AudioDeviceInventory.WiredDeviceConnectionState) message.obj);
                }
                break;
              case 3:
                synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                  AudioDeviceBroker.this.mBtHelper.onBroadcastScoConnectionState(message.arg1);
                }
                break;
              case 4:
              case 5:
                AudioDeviceBroker.this.onSetForceUse(
                    message.arg1, message.arg2, i3 == 5, (String) message.obj);
                break;
              case 6:
                synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                  AudioDeviceBroker.this.mDeviceInventory.onToggleHdmi();
                }
                break;
              case 7:
                synchronized (AudioDeviceBroker.this.mSetModeLock) {
                  synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                    BtDeviceInfo btDeviceInfo = (BtDeviceInfo) message.obj;
                    AudioDeviceInventory audioDeviceInventory =
                        AudioDeviceBroker.this.mDeviceInventory;
                    if (btDeviceInfo.mProfile == 22 && !btDeviceInfo.mIsLeOutput) {
                      bluetoothContextualVolumeStream = -1;
                      audioDeviceInventory.onSetBtActiveDevice(
                          btDeviceInfo, bluetoothContextualVolumeStream);
                      i2 = btDeviceInfo.mProfile;
                      if (i2 != 22 || i2 == 21) {
                        AudioDeviceBroker audioDeviceBroker2 = AudioDeviceBroker.this;
                        audioDeviceBroker2.onUpdateCommunicationRouteClient(
                            audioDeviceBroker2.isBluetoothScoRequested(),
                            "setBluetoothActiveDevice");
                      }
                    }
                    bluetoothContextualVolumeStream =
                        AudioDeviceBroker.this.mAudioService.getBluetoothContextualVolumeStream();
                    audioDeviceInventory.onSetBtActiveDevice(
                        btDeviceInfo, bluetoothContextualVolumeStream);
                    i2 = btDeviceInfo.mProfile;
                    if (i2 != 22) {}
                    AudioDeviceBroker audioDeviceBroker22 = AudioDeviceBroker.this;
                    audioDeviceBroker22.onUpdateCommunicationRouteClient(
                        audioDeviceBroker22.isBluetoothScoRequested(), "setBluetoothActiveDevice");
                  }
                  break;
                }
              default:
                switch (i3) {
                  case 9:
                    synchronized (AudioDeviceBroker.this.mSetModeLock) {
                      synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                        AudioDeviceBroker.this.mBtHelper.resetBluetoothSco();
                      }
                      break;
                    }
                  case 10:
                    synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                      AudioDeviceBroker.this.mDeviceInventory.onMakeA2dpDeviceUnavailableNow(
                          (String) message.obj, message.arg1);
                    }
                    break;
                  case 11:
                    synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                      AudioDeviceBroker.this.mDeviceInventory.onBluetoothDeviceConfigChange(
                          (BtDeviceInfo) message.obj, 0);
                    }
                    break;
                  case 12:
                    AudioDeviceBroker.this.onSendBecomingNoisyIntent(message.arg1);
                    break;
                  case 13:
                    synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                      AudioDeviceBroker.this.mDeviceInventory.onReportNewRoutes();
                    }
                    break;
                  case 14:
                    synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                      AudioDeviceBroker.this.mBtHelper.setHearingAidVolume(
                          message.arg1,
                          message.arg2,
                          AudioDeviceBroker.this.mDeviceInventory.isHearingAidConnected());
                    }
                    break;
                  case 15:
                    synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                      AudioDeviceBroker.this.mBtHelper.setAvrcpAbsoluteVolumeIndex(message.arg1);
                      if (AudioDeviceBroker.this.isDualModeActive()) {
                        AudioDeviceBroker.this.mBtHelper.setLeAudioVolume(
                            message.arg1,
                            AudioDeviceBroker.this.mAudioService.getMaxVssVolumeForStream(3),
                            3);
                      }
                    }
                    break;
                  case 16:
                    synchronized (AudioDeviceBroker.this.mSetModeLock) {
                      synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                        boolean isBluetoothScoRequested =
                            AudioDeviceBroker.this.isBluetoothScoRequested();
                        AudioDeviceBroker.this.mAudioModeOwner = (AudioModeInfo) message.obj;
                        if (!AudioDeviceBroker.this.mReceiverSupported.booleanValue()) {
                          AudioDeviceBroker.this.mCurrentCallDevice = null;
                        }
                        if (AudioDeviceBroker.this.mAudioModeOwner.mUid
                            != AudioDeviceBroker.this.mRequestScoUid) {
                          AudioDeviceBroker.this.resetRequestBtSco();
                        }
                        if (AudioDeviceBroker.this.mAudioModeOwner.mMode != 1) {
                          AudioDeviceBroker.this.onUpdateCommunicationRouteClient(
                              isBluetoothScoRequested, "setNewModeOwner");
                        }
                      }
                      break;
                    }
                  default:
                    switch (i3) {
                      case 32:
                        AudioDeviceBroker.this.mDeviceInventory.onSaveSetPreferredDevices(
                            message.arg1, (List) message.obj);
                        break;
                      case 33:
                        AudioDeviceBroker.this.mDeviceInventory.onSaveRemovePreferredDevices(
                            message.arg1);
                        break;
                      case 34:
                        synchronized (AudioDeviceBroker.this.mSetModeLock) {
                          synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                            AudioDeviceBroker.this.onCommunicationRouteClientDied(
                                (CommunicationRouteClient) message.obj);
                          }
                          break;
                        }
                      case 35:
                        AudioDeviceBroker.this.checkMessagesMuteMusic(0);
                        break;
                      case 36:
                        break;
                      case 37:
                        AudioDeviceBroker.this.mDeviceInventory
                            .onSaveSetPreferredDevicesForCapturePreset(
                                message.arg1, (List) message.obj);
                        break;
                      case 38:
                        AudioDeviceBroker.this.mDeviceInventory
                            .onSaveClearPreferredDevicesForCapturePreset(message.arg1);
                        break;
                      default:
                        switch (i3) {
                          case 42:
                            CommunicationDeviceInfo communicationDeviceInfo =
                                (CommunicationDeviceInfo) message.obj;
                            synchronized (AudioDeviceBroker.this.mSetModeLock) {
                              synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                                AudioDeviceBroker.this.onSetCommunicationDeviceForClient(
                                    communicationDeviceInfo);
                              }
                            }
                            synchronized (AudioDeviceBroker.this.mCommunicationDeviceLock) {
                              if (AudioDeviceBroker.this.mCommunicationDeviceUpdateCount > 0) {
                                AudioDeviceBroker.this.mCommunicationDeviceUpdateCount--;
                              } else {
                                Log.e(
                                    "AS.AudioDeviceBroker",
                                    "mCommunicationDeviceUpdateCount already 0 in"
                                        + " MSG_L_SET_COMMUNICATION_DEVICE_FOR_CLIENT");
                              }
                              AudioDeviceBroker.this.mCommunicationDeviceLock.notify();
                            }
                            break;
                          case 43:
                            synchronized (AudioDeviceBroker.this.mSetModeLock) {
                              synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                                AudioDeviceBroker audioDeviceBroker3 = AudioDeviceBroker.this;
                                if (message.arg1 != 1) {
                                  r3 = false;
                                }
                                audioDeviceBroker3.onUpdateCommunicationRouteClient(
                                    r3, (String) message.obj);
                              }
                              break;
                            }
                          case 44:
                            synchronized (AudioDeviceBroker.this.mSetModeLock) {
                              synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                                AudioDeviceBroker.this.mBtHelper.onScoAudioStateChanged(
                                    message.arg1);
                              }
                              break;
                            }
                          case 45:
                            BtDeviceInfo btDeviceInfo2 = (BtDeviceInfo) message.obj;
                            if (btDeviceInfo2.mDevice != null) {
                              AudioService.sDeviceLogger.enqueue(
                                  new EventLogger.StringEvent(
                                          "msg: onBluetoothActiveDeviceChange  state="
                                              + btDeviceInfo2.mState
                                              + " addr="
                                              + btDeviceInfo2.mDevice.getAddress()
                                              + " prof="
                                              + btDeviceInfo2.mProfile
                                              + " supprNoisy="
                                              + btDeviceInfo2.mSupprNoisy
                                              + " src="
                                              + btDeviceInfo2.mEventSource)
                                      .printLog("AS.AudioDeviceBroker"));
                              synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                                if (btDeviceInfo2.mProfile == 26 && btDeviceInfo2.mState == 2) {
                                  AudioExecutor.execute(
                                      new Runnable() { // from class:
                                        // com.android.server.audio.AudioDeviceBroker$BrokerHandler$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                          AudioDeviceBroker.BrokerHandler.this
                                              .lambda$handleMessage$0();
                                        }
                                      });
                                }
                                AudioDeviceBroker.this.mDeviceInventory.setBluetoothActiveDevice(
                                    btDeviceInfo2);
                              }
                              break;
                            }
                            break;
                          case 46:
                            BleVolumeInfo bleVolumeInfo = (BleVolumeInfo) message.obj;
                            synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                              AudioDeviceBroker.this.mBtHelper.setLeAudioVolume(
                                  bleVolumeInfo.mIndex,
                                  bleVolumeInfo.mMaxIndex,
                                  bleVolumeInfo.mStreamType);
                              if (AudioDeviceBroker.this.isDualModeActive()
                                  && bleVolumeInfo.mStreamType == 3) {
                                AudioDeviceBroker.this.mBtHelper.setAvrcpAbsoluteVolumeIndex(
                                    bleVolumeInfo.mIndex);
                              }
                            }
                            break;
                          case 47:
                            AudioDeviceBroker.this.mDeviceInventory.onSaveSetDeviceAsNonDefault(
                                message.arg1, (AudioDeviceAttributes) message.obj);
                            break;
                          case 48:
                            AudioDeviceBroker.this.mDeviceInventory.onSaveRemoveDeviceAsNonDefault(
                                message.arg1, (AudioDeviceAttributes) message.obj);
                            break;
                          case 49:
                            synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                              AudioDeviceBroker.this.mDeviceInventory
                                  .onMakeLeAudioDeviceUnavailableNow(
                                      (String) message.obj, message.arg1);
                            }
                            break;
                          default:
                            switch (i3) {
                              case 2759:
                                AudioDeviceBroker.this.mDualA2dpManager =
                                    new DualA2dpVolumeManager(
                                        AudioDeviceBroker.this.mContext,
                                        AudioDeviceBroker.this.mBrokerHandler,
                                        AudioService.MAX_STREAM_VOLUME[3]);
                                break;
                              case 2760:
                                synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                                  AudioDeviceBroker.this.mDeviceInventory
                                      .onSetDeviceConnectionStateForceByUser(
                                          (AudioDeviceInventory.SetForceDeviceState) message.obj);
                                }
                                break;
                              case 2761:
                                AudioDeviceBroker.this.setActiveBluetoothDevice(
                                    (BluetoothDevice) message.obj);
                                break;
                              default:
                                Log.wtf("AS.AudioDeviceBroker", "Invalid message " + message.what);
                                break;
                            }
                        }
                    }
                }
            }
          } else {
            int i5 = message.arg1;
            if (AudioDeviceBroker.this.mAudioModeOwner.mPid != 0) {
              AudioService.sScoPreventionLogger.enqueue(
                  new EventLogger.StringEvent(
                          "MSG_CHECK_ABNORMAL_SCO skipped to remove SCO client mode 0 uid = "
                              + i5
                              + " mAudioModeOwner  = "
                              + AudioDeviceBroker.this.mAudioModeOwner)
                      .printLog("AS.AudioDeviceBroker"));
            } else {
              synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
                Iterator it = AudioDeviceBroker.this.mCommunicationRouteClients.iterator();
                i = 0;
                while (it.hasNext()) {
                  CommunicationRouteClient communicationRouteClient =
                      (CommunicationRouteClient) it.next();
                  if (communicationRouteClient.getDevice().getType() == 7) {
                    i++;
                    if (i5 == communicationRouteClient.getUid()) {
                      communicationRouteClient.unregisterDeathRecipient();
                      it.remove();
                      i--;
                      AudioService.sScoPreventionLogger.enqueue(
                          new EventLogger.StringEvent(
                                  "MSG_CHECK_ABNORMAL_SCO removed SCO client uid = "
                                      + communicationRouteClient.getUid())
                              .printLog("AS.AudioDeviceBroker"));
                    }
                  }
                }
              }
              if (i == 0
                  && AudioDeviceBroker.this.mBtHelper.isBluetoothScoOn()
                  && AudioDeviceBroker.this.mBtHelper.isScoStateInternal()) {
                AudioService.sScoPreventionLogger.enqueue(
                    new EventLogger.StringEvent(
                            "MSG_CHECK_ABNORMAL_SCO stopScoUsingVirtualVoiceCall()")
                        .printLog("AS.AudioDeviceBroker"));
                AudioDeviceBroker.this.mBtHelper.forceCloseSco();
              }
            }
          }
        } else if (message.arg1 != 1) {
          synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
            AudioDeviceBroker.this.mBtHelper.onBtProfileConnected(
                message.arg1, (BluetoothProfile) message.obj);
          }
        } else {
          synchronized (AudioDeviceBroker.this.mSetModeLock) {
            synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
              AudioDeviceBroker.this.mBtHelper.onHeadsetProfileConnected(
                  (BluetoothHeadset) message.obj);
            }
          }
        }
      } else if (message.arg1 != 1) {
        synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
          AudioDeviceBroker.this.mBtHelper.onBtProfileDisconnected(message.arg1);
          AudioDeviceBroker.this.mDeviceInventory.onBtProfileDisconnected(message.arg1);
        }
      } else {
        synchronized (AudioDeviceBroker.this.mSetModeLock) {
          synchronized (AudioDeviceBroker.this.mDeviceStateLock) {
            AudioDeviceBroker.this.mBtHelper.disconnectHeadset();
            AudioDeviceBroker.this.resetBtScoOnByApp();
          }
        }
      }
      if (AudioDeviceBroker.MESSAGES_MUTE_MUSIC.contains(Integer.valueOf(message.what))) {
        AudioDeviceBroker.this.sendMsg(35, 0, 100);
      }
      if (AudioDeviceBroker.MESSAGES_SAR_RCV_CONTROL.contains(Integer.valueOf(message.what))) {
        AudioDeviceBroker.this.postSarControl();
      }
      if (AudioDeviceBroker.isMessageHandledUnderWakelock(message.what)) {
        try {
          AudioDeviceBroker.this.mBrokerEventWakeLock.release();
        } catch (Exception e) {
          Log.e("AS.AudioDeviceBroker", "Exception releasing wakelock", e);
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleMessage$0() {
      AudioDeviceBroker.this.mBtHelper.updateBtAppList(AudioDeviceBroker.this.mContext);
    }
  }

  public final void sendMsg(int i, int i2, int i3) {
    sendIILMsg(i, i2, 0, 0, null, i3);
  }

  public final void sendILMsg(int i, int i2, int i3, Object obj, int i4) {
    sendIILMsg(i, i2, i3, 0, obj, i4);
  }

  public final void sendLMsg(int i, int i2, Object obj, int i3) {
    sendIILMsg(i, i2, 0, 0, obj, i3);
  }

  public final void sendMsgNoDelay(int i, int i2) {
    sendIILMsg(i, i2, 0, 0, null, 0);
  }

  public final void sendIMsgNoDelay(int i, int i2, int i3) {
    sendIILMsg(i, i2, i3, 0, null, 0);
  }

  public final void sendIIMsgNoDelay(int i, int i2, int i3, int i4) {
    sendIILMsg(i, i2, i3, i4, null, 0);
  }

  public final void sendILMsgNoDelay(int i, int i2, int i3, Object obj) {
    sendIILMsg(i, i2, i3, 0, obj, 0);
  }

  public final void sendLMsgNoDelay(int i, int i2, Object obj) {
    sendIILMsg(i, i2, 0, 0, obj, 0);
  }

  public final void sendIILMsgNoDelay(int i, int i2, int i3, int i4, Object obj) {
    sendIILMsg(i, i2, i3, i4, obj, 0);
  }

  public final void sendIILMsg(int i, int i2, int i3, int i4, Object obj, int i5) {
    if (i2 == 0) {
      this.mBrokerHandler.removeMessages(i);
    } else if (i2 == 1 && this.mBrokerHandler.hasMessages(i)) {
      return;
    }
    if (isMessageHandledUnderWakelock(i)) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          this.mBrokerEventWakeLock.acquire(5000L);
        } catch (Exception e) {
          Log.e("AS.AudioDeviceBroker", "Exception acquiring wakelock", e);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    if (MESSAGES_MUTE_MUSIC.contains(Integer.valueOf(i))) {
      checkMessagesMuteMusic(i);
    }
    synchronized (sLastDeviceConnectionMsgTimeLock) {
      long uptimeMillis = SystemClock.uptimeMillis() + i5;
      if (i == 2 || i == 7 || i == 49 || i == 2760 || i == 10 || i == 11) {
        long j = sLastDeviceConnectMsgTime;
        if (j >= uptimeMillis) {
          uptimeMillis = j + 30;
        }
        sLastDeviceConnectMsgTime = uptimeMillis;
      }
      BrokerHandler brokerHandler = this.mBrokerHandler;
      brokerHandler.sendMessageAtTime(brokerHandler.obtainMessage(i, i3, i4, obj), uptimeMillis);
    }
  }

  public final void removeMsgForCheckClientState(int i) {
    CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
    if (communicationRouteClientForUid != null) {
      this.mBrokerHandler.removeMessages(56, communicationRouteClientForUid);
    }
  }

  public final void sendMsgForCheckClientState(int i, int i2, int i3, int i4, Object obj, int i5) {
    if (i2 == 0 && obj != null) {
      this.mBrokerHandler.removeMessages(i, obj);
    }
    long uptimeMillis = SystemClock.uptimeMillis() + i5;
    BrokerHandler brokerHandler = this.mBrokerHandler;
    brokerHandler.sendMessageAtTime(brokerHandler.obtainMessage(i, i3, i4, obj), uptimeMillis);
  }

  public static boolean hasIntersection(Set set, Set set2) {
    Iterator it = set.iterator();
    while (it.hasNext()) {
      if (set2.contains(it.next())) {
        return true;
      }
    }
    return false;
  }

  public boolean messageMutesMusic(int i) {
    if (i == 0) {
      return false;
    }
    return ((i == 7 || i == 29 || i == 11)
            && AudioSystem.isStreamActive(3, 0)
            && hasIntersection(
                AudioDeviceInventory.DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET,
                this.mAudioService.getDeviceSetForStream(3)))
        ? false
        : true;
  }

  public final void checkMessagesMuteMusic(int i) {
    boolean messageMutesMusic = messageMutesMusic(i);
    if (!messageMutesMusic) {
      Iterator it = MESSAGES_MUTE_MUSIC.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        int intValue = ((Integer) it.next()).intValue();
        if (this.mBrokerHandler.hasMessages(intValue) && messageMutesMusic(intValue)) {
          messageMutesMusic = true;
          break;
        }
      }
    }
    if (messageMutesMusic != this.mMusicMuted.getAndSet(messageMutesMusic)) {
      this.mAudioService.setMusicMute(messageMutesMusic);
    }
  }

  public class CommunicationRouteClient implements IBinder.DeathRecipient {
    public final IBinder mCb;
    public AudioDeviceAttributes mDevice;
    public final boolean mIsPrivileged;
    public boolean mPlaybackActive;
    public boolean mRecordingActive;
    public final int mUid;

    public CommunicationRouteClient(
        IBinder iBinder, int i, AudioDeviceAttributes audioDeviceAttributes, boolean z) {
      this.mCb = iBinder;
      this.mUid = i;
      this.mDevice = audioDeviceAttributes;
      this.mIsPrivileged = z;
      this.mPlaybackActive = AudioDeviceBroker.this.mAudioService.isPlaybackActiveForUid(i);
      this.mRecordingActive = AudioDeviceBroker.this.mAudioService.isRecordingActiveForUid(i);
    }

    public boolean registerDeathRecipient() {
      try {
        this.mCb.linkToDeath(this, 0);
        return true;
      } catch (RemoteException unused) {
        Log.w(
            "AS.AudioDeviceBroker",
            "CommunicationRouteClient could not link to " + this.mCb + " binder death");
        return false;
      }
    }

    public void unregisterDeathRecipient() {
      try {
        this.mCb.unlinkToDeath(this, 0);
      } catch (NoSuchElementException unused) {
        Log.w(
            "AS.AudioDeviceBroker",
            "CommunicationRouteClient could not not unregistered to binder");
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      AudioDeviceBroker.this.postCommunicationRouteClientDied(this);
    }

    public IBinder getBinder() {
      return this.mCb;
    }

    public int getUid() {
      return this.mUid;
    }

    public boolean isPrivileged() {
      return this.mIsPrivileged;
    }

    public AudioDeviceAttributes getDevice() {
      return this.mDevice;
    }

    public void setPlaybackActive(boolean z) {
      this.mPlaybackActive = z;
    }

    public void setRecordingActive(boolean z) {
      this.mRecordingActive = z;
    }

    public boolean isActive() {
      return this.mIsPrivileged || this.mRecordingActive || this.mPlaybackActive;
    }

    public String toString() {
      return "[CommunicationRouteClient: mUid: "
          + this.mUid
          + " mDevice: "
          + this.mDevice.toString()
          + " mIsPrivileged: "
          + this.mIsPrivileged
          + " mPlaybackActive: "
          + this.mPlaybackActive
          + " mRecordingActive: "
          + this.mRecordingActive
          + "]";
    }
  }

  public final void onCommunicationRouteClientDied(
      CommunicationRouteClient communicationRouteClient) {
    if (communicationRouteClient == null) {
      return;
    }
    Log.w("AS.AudioDeviceBroker", "Communication client died");
    setCommunicationRouteForClient(
        communicationRouteClient.getBinder(),
        communicationRouteClient.getUid(),
        null,
        -1,
        communicationRouteClient.isPrivileged(),
        "onCommunicationRouteClientDied");
  }

  public final AudioDeviceAttributes preferredCommunicationDevice() {
    boolean z;
    AudioDeviceAttributes headsetAudioDevice;
    boolean isBluetoothScoOn = this.mBtHelper.isBluetoothScoOn();
    synchronized (this.mBluetoothAudioStateLock) {
      if (isBluetoothScoOn) {
        try {
          z = this.mBluetoothScoOn;
        } catch (Throwable th) {
          throw th;
        }
      }
    }
    if (z && (headsetAudioDevice = this.mBtHelper.getHeadsetAudioDevice()) != null) {
      return headsetAudioDevice;
    }
    AudioDeviceAttributes requestedCommunicationDevice = requestedCommunicationDevice();
    if (requestedCommunicationDevice == null || requestedCommunicationDevice.getType() == 7) {
      return null;
    }
    return requestedCommunicationDevice;
  }

  public final void updateCommunicationRoute(String str) {
    AudioDeviceAttributes audioDeviceAttributes;
    AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
    Log.v(
        "AS.AudioDeviceBroker",
        "updateCommunicationRoute, preferredCommunicationDevice: "
            + preferredCommunicationDevice
            + " eventSource: "
            + str);
    AudioService.sDeviceLogger.enqueue(
        new EventLogger.StringEvent(
            "updateCommunicationRoute, preferredCommunicationDevice: "
                + preferredCommunicationDevice
                + " eventSource: "
                + str));
    if (this.mReceiverSupported.booleanValue()) {
      if (preferredCommunicationDevice == null) {
        AudioDeviceAttributes defaultCommunicationDevice = getDefaultCommunicationDevice();
        if (defaultCommunicationDevice != null && !isBluetoothScoRequested()) {
          Log.i(
              "AS.AudioDeviceBroker",
              "updateCommunicationRoute set default Device = "
                  + defaultCommunicationDevice.getType());
          this.mDeviceInventory.setPreferredDevicesForStrategyInt(
              this.mCommunicationStrategyId, Arrays.asList(defaultCommunicationDevice));
          this.mDeviceInventory.setPreferredDevicesForStrategyInt(
              this.mAccessibilityStrategyId, Arrays.asList(defaultCommunicationDevice));
        } else {
          this.mDeviceInventory.removePreferredDevicesForStrategyInt(this.mCommunicationStrategyId);
          this.mDeviceInventory.removePreferredDevicesForStrategyInt(this.mAccessibilityStrategyId);
        }
        this.mDeviceInventory.applyConnectedDevicesRoles();
        this.mDeviceInventory.reapplyExternalDevicesRoles();
      } else {
        if (this.mAudioModeOwner.mUid == 1000
            && (audioDeviceAttributes = this.mPreferredCommunicationDevice) != null
            && audioDeviceAttributes.getType() == 2
            && preferredCommunicationDevice.getType() == 7) {
          Iterator it = this.mCommunicationRouteClients.iterator();
          while (true) {
            if (!it.hasNext()) {
              break;
            }
            CommunicationRouteClient communicationRouteClient =
                (CommunicationRouteClient) it.next();
            if (communicationRouteClient.getDevice().getType() == 2
                && communicationRouteClient.getUid() == 1000) {
              this.mCommunicationRouteClients.remove(communicationRouteClient);
              Log.i("AS.AudioDeviceBroker", "updateCommunicationRoute removed SPK client");
              break;
            }
          }
        }
        this.mDeviceInventory.setPreferredDevicesForStrategyInt(
            this.mCommunicationStrategyId, Arrays.asList(preferredCommunicationDevice));
        this.mDeviceInventory.setPreferredDevicesForStrategyInt(
            this.mAccessibilityStrategyId, Arrays.asList(preferredCommunicationDevice));
      }
      postSarControl();
    } else if (preferredCommunicationDevice == null) {
      this.mCurrentCallDevice = null;
      AudioDeviceAttributes defaultCommunicationDevice2 = getDefaultCommunicationDevice();
      if (defaultCommunicationDevice2 != null && !isBluetoothScoRequested()) {
        Log.i(
            "AS.AudioDeviceBroker",
            "updateCommunicationRoute set default Device = "
                + defaultCommunicationDevice2.getType());
        this.mDeviceInventory.setPreferredDevicesForStrategyInt(
            this.mCommunicationStrategyId, Arrays.asList(defaultCommunicationDevice2));
      } else {
        this.mDeviceInventory.removePreferredDevicesForStrategyInt(this.mCommunicationStrategyId);
      }
      this.mDeviceInventory.applyConnectedDevicesRoles();
      this.mDeviceInventory.reapplyExternalDevicesRoles();
    } else {
      this.mCurrentCallDevice = preferredCommunicationDevice;
      Log.i(
          "AS.AudioDeviceBroker",
          "updateCommunicationRoute return already mCurrentCallDevice = "
              + this.mCurrentCallDevice);
      this.mDeviceInventory.setPreferredDevicesForStrategyInt(
          this.mCommunicationStrategyId, Arrays.asList(preferredCommunicationDevice));
    }
    onUpdatePhoneStrategyDevice(preferredCommunicationDevice);
  }

  public final void onUpdateCommunicationRouteClient(boolean z, String str) {
    AudioDeviceAttributes audioDeviceAttributes;
    CommunicationRouteClient communicationRouteClient = topCommunicationRouteClient();
    Log.v(
        "AS.AudioDeviceBroker",
        "onUpdateCommunicationRouteClient, crc: "
            + communicationRouteClient
            + " wasBtScoRequested: "
            + z
            + " eventSource: "
            + str);
    if (communicationRouteClient != null) {
      if (communicationRouteClient.getDevice().getType() == 7
          && this.mBtHelper.isWatchOrBudsWearingOff()) {
        updateCommunicationRoute(str);
        return;
      }
      if (!this.mReceiverSupported.booleanValue()
          && communicationRouteClient.getDevice() != null
          && (audioDeviceAttributes = this.mCurrentCallDevice) != null
          && audioDeviceAttributes.equals(communicationRouteClient.getDevice())) {
        Log.i(
            "AS.AudioDeviceBroker",
            "onUpdateCommunicationRouteClient return mCurrentCallDevice = "
                + this.mCurrentCallDevice);
        updateCommunicationRoute(str);
        return;
      }
      setCommunicationRouteForClient(
          communicationRouteClient.getBinder(),
          communicationRouteClient.getUid(),
          communicationRouteClient.getDevice(),
          -1,
          communicationRouteClient.isPrivileged(),
          str);
      return;
    }
    if (!isBluetoothScoRequested() && z) {
      this.mBtHelper.stopBluetoothSco(str);
    }
    updateCommunicationRoute(str);
  }

  public final void onUpdatePhoneStrategyDevice(AudioDeviceAttributes audioDeviceAttributes) {
    boolean isSpeakerphoneActive = isSpeakerphoneActive();
    this.mPreferredCommunicationDevice = audioDeviceAttributes;
    updateActiveCommunicationDevice();
    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
      AudioDeviceAttributes audioDeviceAttributes2 = this.mPreferredCommunicationDevice;
      if (audioDeviceAttributes2 != null) {
        this.mAudioService.setCommunicationDevice(audioDeviceAttributes2.getType());
      } else {
        AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
        if (audioDeviceInfo != null) {
          this.mAudioService.setCommunicationDevice(audioDeviceInfo.getType());
        }
      }
    }
    if (isSpeakerphoneActive != isSpeakerphoneActive()) {
      try {
        Intent flags =
            new Intent("android.media.action.SPEAKERPHONE_STATE_CHANGED").setFlags(1073741824);
        flags.putExtra(
            "modeOwner",
            this.mAudioModeOwner.mPid == 0
                ? this.mModeRequesterPackage
                : this.mAudioService.getPackageNameModeOwner());
        this.mContext.sendBroadcastAsUser(flags, UserHandle.ALL);
      } catch (Exception e) {
        Log.w(
            "AS.AudioDeviceBroker", "failed to broadcast ACTION_SPEAKERPHONE_STATE_CHANGED: " + e);
      }
    }
    this.mAudioService.postUpdateRingerModeServiceInt();
    dispatchCommunicationDevice();
  }

  public final CommunicationRouteClient removeCommunicationRouteClient(
      IBinder iBinder, boolean z, int i) {
    Iterator it = this.mCommunicationRouteClients.iterator();
    while (it.hasNext()) {
      CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
      if (communicationRouteClient.getUid() == i) {
        if (z) {
          communicationRouteClient.unregisterDeathRecipient();
        }
        removeMsgForCheckClientState(communicationRouteClient.getUid());
        this.mCommunicationRouteClients.remove(communicationRouteClient);
        return communicationRouteClient;
      }
    }
    return null;
  }

  public final CommunicationRouteClient addCommunicationRouteClient(
      IBinder iBinder, int i, AudioDeviceAttributes audioDeviceAttributes, boolean z) {
    removeCommunicationRouteClient(iBinder, true, i);
    CommunicationRouteClient communicationRouteClient =
        new CommunicationRouteClient(iBinder, i, audioDeviceAttributes, z);
    if (!communicationRouteClient.registerDeathRecipient()) {
      return null;
    }
    this.mCommunicationRouteClients.add(0, communicationRouteClient);
    if (!communicationRouteClient.isActive()) {
      setForceCommunicationClientStateAndDelayedCheck(
          communicationRouteClient,
          !this.mAudioService.isPlaybackActiveForUid(communicationRouteClient.getUid()),
          !this.mAudioService.isRecordingActiveForUid(communicationRouteClient.getUid()));
    }
    return communicationRouteClient;
  }

  public final CommunicationRouteClient getCommunicationRouteClientForUid(int i) {
    Iterator it = this.mCommunicationRouteClients.iterator();
    while (it.hasNext()) {
      CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
      if (communicationRouteClient.getUid() == i) {
        return communicationRouteClient;
      }
    }
    return null;
  }

  public final boolean communnicationDeviceCompatOn() {
    AudioModeInfo audioModeInfo = this.mAudioModeOwner;
    return (audioModeInfo.mMode != 3
            || CompatChanges.isChangeEnabled(243827847L, audioModeInfo.mUid)
            || this.mAudioModeOwner.mUid == 1000)
        ? false
        : true;
  }

  public AudioDeviceAttributes getDefaultCommunicationDevice() {
    if (communnicationDeviceCompatOn()) {
      return this.mDeviceInventory.getDeviceOfType(134217728);
    }
    return null;
  }

  public void updateCommunicationRouteClientsActivity(List list, List list2) {
    boolean z;
    synchronized (this.mSetModeLock) {
      synchronized (this.mDeviceStateLock) {
        Iterator it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
          CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
          boolean isActive = communicationRouteClient.isActive();
          if (list != null) {
            communicationRouteClient.setPlaybackActive(false);
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
              AudioPlaybackConfiguration audioPlaybackConfiguration =
                  (AudioPlaybackConfiguration) it2.next();
              if (audioPlaybackConfiguration.getClientUid() == communicationRouteClient.getUid()
                  && audioPlaybackConfiguration.isActive()) {
                communicationRouteClient.setPlaybackActive(true);
                z = true;
                break;
              }
            }
          }
          z = false;
          if (list2 != null) {
            communicationRouteClient.setRecordingActive(false);
            Iterator it3 = list2.iterator();
            while (true) {
              if (!it3.hasNext()) {
                break;
              }
              AudioRecordingConfiguration audioRecordingConfiguration =
                  (AudioRecordingConfiguration) it3.next();
              if (audioRecordingConfiguration.getClientUid() == communicationRouteClient.getUid()
                  && !audioRecordingConfiguration.isClientSilenced()) {
                communicationRouteClient.setRecordingActive(true);
                z = true;
                break;
              }
            }
          }
          if (z) {
            removeMsgForCheckClientState(communicationRouteClient.getUid());
            updateCommunicationRouteClientState(communicationRouteClient, isActive);
          } else if (isActive) {
            setForceCommunicationClientStateAndDelayedCheck(
                communicationRouteClient, list != null, list2 != null);
          }
        }
      }
    }
  }

  public UUID getDeviceSensorUuid(AudioDeviceAttributes audioDeviceAttributes) {
    UUID deviceSensorUuid;
    synchronized (this.mDeviceStateLock) {
      deviceSensorUuid = this.mDeviceInventory.getDeviceSensorUuid(audioDeviceAttributes);
    }
    return deviceSensorUuid;
  }

  public void dispatchPreferredMixerAttributesChangedCausedByDeviceRemoved(
      AudioDeviceInfo audioDeviceInfo) {
    this.mAudioService.dispatchPreferredMixerAttributesChanged(
        new AudioAttributes.Builder().setUsage(1).build(), audioDeviceInfo.getId(), null);
  }

  public void updateReceiverSupported(boolean z) {
    this.mReceiverSupported = Boolean.valueOf(z);
  }

  public boolean isRemoteVolumeControlSupported() {
    boolean isRemoteVolumeControlSupported;
    synchronized (this.mDeviceStateLock) {
      isRemoteVolumeControlSupported = this.mBtHelper.isRemoteVolumeControlSupported();
    }
    return isRemoteVolumeControlSupported;
  }

  public void postCheckAbnormalSco(int i) {
    sendIILMsg(2764, 0, i, 0, null, 6000);
  }

  public void checkAndInitRequestBtScoInfo(int i, IBinder iBinder) {
    int i2 = this.mRequestScoUid;
    if (i2 == 0 || i2 != i) {
      this.mRequestScoUid = i;
      this.mRequestScoCount = 0;
      this.mScoCb = iBinder;
    }
  }

  public void checkAndResetBtSco() {
    IBinder iBinder;
    if (this.mRequestScoCount <= 4 || (iBinder = this.mScoCb) == null) {
      return;
    }
    CommunicationRouteClient removeCommunicationRouteClient =
        removeCommunicationRouteClient(iBinder, true, this.mRequestScoUid);
    if (removeCommunicationRouteClient != null) {
      AudioService.sScoPreventionLogger.enqueue(
          new EventLogger.StringEvent(
                  "checkAndResetBtSco remove SCO client of uid = "
                      + removeCommunicationRouteClient.getUid())
              .printLog("AS.AudioDeviceBroker"));
    }
    resetRequestBtSco();
  }

  public void resetRequestBtSco() {
    this.mRequestScoUid = 0;
    this.mRequestScoCount = 0;
    this.mScoCb = null;
  }

  public boolean isBluetoothScoSupported() {
    boolean isBluetoothScoSupported;
    synchronized (this.mDeviceStateLock) {
      isBluetoothScoSupported = this.mBtHelper.isBluetoothScoSupported();
    }
    return isBluetoothScoSupported;
  }

  public void resetBtScoOnByApp() {
    this.mAudioService.resetBtScoOnByApp();
  }

  public boolean isBluetoothScoOnForAs() {
    return this.mBtHelper.isBluetoothScoOn();
  }

  public boolean checkDeviceConnected(int i) {
    boolean checkDeviceConnected;
    synchronized (this.mDeviceStateLock) {
      checkDeviceConnected = this.mDeviceInventory.checkDeviceConnected(i);
    }
    return checkDeviceConnected;
  }

  public void postSarControl() {
    sendMsg(2763, 0, 0);
  }

  public final int getCurOutDevice() {
    try {
      return Integer.parseInt(SemAudioSystem.getPolicyParameters("l_device_current_output"));
    } catch (NumberFormatException unused) {
      Log.i("AS.AudioDeviceBroker", "getCurOutDevice : Can't get outDevice");
      return -1;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x000f, code lost:

     if (getCurOutDevice() == 1) goto L10;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void checkRCVStateForSARTest() {
    boolean z = isInCommunication() || this.mSarBackoffParam;
    if (this.mCurRCVBackOffState == z) {
      return;
    }
    Intent intent = new Intent("android.samsung.media.action.receiver_sar");
    intent.putExtra("android.samsung.media.extra.receiver", z);
    AudioUtils.sendBroadcastToUser(this.mContext, intent, UserHandle.CURRENT, null);
    this.mCurRCVBackOffState = z;
    Log.d("AS.AudioDeviceBroker", "Send receiver_sar state " + z);
  }

  public void muteRingtoneDuringVibration() {
    this.mAudioService.muteRingtoneDuringVibration();
  }

  public void handleFmRadioDeviceConnection(int i, int i2, String str) {
    synchronized (this.mDeviceStateLock) {
      this.mDeviceInventory.handleFmRadioDeviceConnection(i, i2, str);
    }
  }

  public boolean isForceSpeakerOn() {
    return this.mAudioService.isForceSpeakerOn();
  }

  public void sendMsgForForceSpeaker() {
    this.mAudioService.sendMsgForForceSpeaker();
  }

  public void updateDeviceQuickConnection(boolean z, int i, String str, String str2, int i2) {
    synchronized (this.mDeviceStateLock) {
      this.mDeviceInventory.updateDeviceQuickConnection(z, i, str, str2, i2);
    }
  }

  public BluetoothA2dp getA2dp() {
    BluetoothA2dp a2dp;
    synchronized (this.mDeviceStateLock) {
      a2dp = this.mBtHelper.getA2dp();
    }
    return a2dp;
  }

  public BluetoothLeAudio getLeAudio() {
    return this.mBtHelper.getLeAudio();
  }

  public BluetoothHeadset getBTHeadset() {
    BluetoothHeadset bTHeadset;
    synchronized (this.mDeviceStateLock) {
      bTHeadset = this.mBtHelper.getBTHeadset();
    }
    return bTHeadset;
  }

  public String getAddressForDevice(int i) {
    return this.mDeviceInventory.getAddressForDevice(i);
  }

  public int setDeviceToForceByUser(int i, String str, boolean z) {
    int deviceToForceByUser;
    synchronized (this.mDeviceStateLock) {
      deviceToForceByUser = this.mDeviceInventory.setDeviceToForceByUser(i, str, z);
    }
    return deviceToForceByUser;
  }

  public Set getAvailableDeviceSetForQuickSoundPath() {
    Set availableDeviceSetForQuickSoundPath;
    synchronized (this.mDeviceStateLock) {
      availableDeviceSetForQuickSoundPath =
          this.mDeviceInventory.getAvailableDeviceSetForQuickSoundPath();
    }
    return availableDeviceSetForQuickSoundPath;
  }

  public void postSetDeviceConnectionStateForceByUser(
      AudioDeviceInventory.SetForceDeviceState setForceDeviceState, int i) {
    sendLMsg(2760, 2, setForceDeviceState, i);
  }

  public void updateDexState() {
    this.mAudioService.updateDexState();
  }

  public void postBroadcastBecomingNoisy(int i) {
    sendIMsgNoDelay(12, 0, i);
  }

  public final void onSendBecomingNoisyIntent(int i) {
    if (this.mAudioService.isMultiSoundOnInternal() && this.mAudioService.getPinDevice() != i) {
      this.mAudioService.sendBecomingNoisyIntentToUnpinApps(i);
      AudioService.sDeviceLogger.enqueue(
          new EventLogger.StringEvent("broadcast ACTION_AUDIO_BECOMING_NOISY to unppin apps")
              .printLog("AS.AudioDeviceBroker"));
      return;
    }
    AudioService.sDeviceLogger.enqueue(
        new EventLogger.StringEvent("broadcast ACTION_AUDIO_BECOMING_NOISY")
            .printLog("AS.AudioDeviceBroker"));
    this.mSystemServer.sendDeviceBecomingSemNoisyIntent();
    Intent intent = new Intent("android.media.AUDIO_BECOMING_NOISY");
    if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
      intent.putExtra("android.bluetooth.a2dp.extra.DISCONNECT_A2DP", true);
    }
    this.mSystemServer.sendDeviceBecomingNoisyIntent(intent);
  }

  public boolean isMultiSoundOn() {
    return this.mAudioService.isMultiSoundOn();
  }

  public int getPinDevice() {
    return this.mAudioService.getPinDeviceInternal();
  }

  public void sendBecomingNoisyToPinnedApp(int i) {
    if (this.mSystemServer.isPrivileged()) {
      this.mAudioService.sendBecomingNoisyToPinnedApp(i);
    }
  }

  public int getPriorityDevice(int i) {
    return this.mDeviceInventory.getPriorityDevice(i);
  }

  public int getConnectedDevice() {
    return this.mDeviceInventory.getConnectedDevice();
  }

  public int checkSendBecomingNoisyIntent(int i, int i2, int i3) {
    return this.mDeviceInventory.checkSendBecomingNoisyIntent(i, i2, i3);
  }

  public void setBtOffloadEnable(int i) {
    this.mBtHelper.setBtOffloadEnable(i);
  }

  public void updateBluetoothA2dpDeviceConfigChange(BtDeviceChangedData btDeviceChangedData) {
    synchronized (this.mDeviceStateLock) {
      postBluetoothDeviceConfigChange(
          createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
    }
  }

  public ArrayMap getA2dpDevices() {
    return this.mDualA2dpManager.getA2dpDevices();
  }

  public void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) {
    this.mDualA2dpManager.setA2dpDeviceVolume(bluetoothDevice, i);
  }

  public boolean shouldVolumeChangedIntent() {
    return this.mDualA2dpManager.shouldVolumeChangedIntent();
  }

  public int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice) {
    return this.mDualA2dpManager.getA2dpDeviceVolume(bluetoothDevice);
  }

  public int getMainA2dpVolume() {
    return this.mDualA2dpManager.getMainVolume();
  }

  public void dumpA2dpDevicesVolume(PrintWriter printWriter) {
    this.mDualA2dpManager.dump(printWriter);
  }

  public void updateIndividualA2dpVolumes(int i) {
    this.mDualA2dpManager.updateIndividualA2dpVolumes(i);
  }

  public boolean isDualA2dpMode() {
    return this.mDualA2dpManager.isDualA2dpMode();
  }

  public void setDualA2dpMode(boolean z, BluetoothDevice bluetoothDevice) {
    BluetoothA2dp a2dp;
    synchronized (this.mDeviceStateLock) {
      this.mDualA2dpManager.setDualA2dpMode(z, bluetoothDevice);
      if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR && (a2dp = getA2dp()) != null) {
        List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
        if (connectedDevices.size() != 0) {
          boolean z2 = true;
          if (z) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
              if (it.next().semGetAudioType() == 2) {
                break;
              }
            }
            z2 = false;
            BtUtils.setBtVolumeMonitor(z2);
          } else {
            BluetoothDevice activeDevice = a2dp.getActiveDevice();
            Iterator<BluetoothDevice> it2 = connectedDevices.iterator();
            while (true) {
              if (!it2.hasNext()) {
                break;
              }
              BluetoothDevice next = it2.next();
              if (next.equals(activeDevice)) {
                if (next.semGetAudioType() == 2) {}
              }
            }
            z2 = false;
            BtUtils.setBtVolumeMonitor(z2);
          }
        }
      }
    }
    postSetVolumeIndexForA2dpDevice(false, "setDualA2dpMode");
  }

  public void connectA2dpDevice(BluetoothDevice bluetoothDevice, int i, int i2) {
    if (this.mSystemServer.isPrivileged()) {
      if (i2 != -1) {
        i2 *= 10;
      }
      int i3 = i2;
      int vssVolumeForDevice = getVssVolumeForDevice(3, 128);
      BluetoothDevice activeA2dpDevice = this.mBtHelper.getActiveA2dpDevice();
      this.mDualA2dpManager.connectA2dpDevice(
          bluetoothDevice,
          i,
          i3,
          vssVolumeForDevice,
          this.mAudioService.isSafeMediaVolumeStateActive(),
          activeA2dpDevice);
      if (activeA2dpDevice != null && i == 2) {
        setActiveBluetoothDevice(activeA2dpDevice);
      }
      updateAvrcpAbsoluteVolumeSupported();
    }
  }

  public void postActiveBluetoothDeviceChanged(BluetoothDevice bluetoothDevice) {
    sendLMsgNoDelay(2761, 2, bluetoothDevice);
  }

  public void setActiveBluetoothDevice(BluetoothDevice bluetoothDevice) {
    synchronized (this.mDeviceStateLock) {
      if (this.mDualA2dpManager.setActiveDevice(bluetoothDevice)) {
        postSetVolumeIndexForA2dpDevice(
            !updateAvrcpAbsoluteVolumeSupported(), "setActiveBluetoothDevice");
      }
    }
  }

  public boolean updateAvrcpAbsoluteVolumeSupported() {
    boolean isAvrcpAbsoluteVolumeSupportedForActiveDevice =
        this.mDualA2dpManager.isAvrcpAbsoluteVolumeSupportedForActiveDevice();
    this.mBtHelper.setAvrcpAbsoluteVolumeSupported(isAvrcpAbsoluteVolumeSupportedForActiveDevice);
    this.mAudioService.updateAvrcpAbsoluteVolumeSupported(
        isAvrcpAbsoluteVolumeSupportedForActiveDevice);
    return isAvrcpAbsoluteVolumeSupportedForActiveDevice;
  }

  public final void postSetVolumeIndexForA2dpDevice(boolean z, String str) {
    int mainVolume = this.mDualA2dpManager.getMainVolume();
    if (mainVolume != -1) {
      if (z) {
        AudioSystem.setStreamVolumeIndexAS(3, 0, 128);
      }
      postSetVolumeIndexOnDevice(3, mainVolume, 128, str);
      Log.i("AS.AudioDeviceBroker", "update volume " + mainVolume + " from " + str);
    }
  }

  public boolean setAvrcpAbsoluteVolumeSupported(boolean z, String str) {
    boolean isAvrcpAbsoluteVolumeSupportedForActiveDevice;
    synchronized (this.mDeviceStateLock) {
      this.mDualA2dpManager.setAvrcpAbsoluteVolumeSupported(str, z);
      isAvrcpAbsoluteVolumeSupportedForActiveDevice =
          this.mDualA2dpManager.isAvrcpAbsoluteVolumeSupportedForActiveDevice();
      this.mBtHelper.setAvrcpAbsoluteVolumeSupported(isAvrcpAbsoluteVolumeSupportedForActiveDevice);
      this.mAudioService.setAvrcpAbsoluteVolumeSupported(
          isAvrcpAbsoluteVolumeSupportedForActiveDevice);
    }
    return isAvrcpAbsoluteVolumeSupportedForActiveDevice;
  }

  public boolean skipSendingAVRCPVolume(int i) {
    return this.mDualA2dpManager.skipSendingAVRCPVolume(i);
  }

  public boolean isDualModeActive() {
    BluetoothDevice activeA2dpDevice = this.mBtHelper.getActiveA2dpDevice();
    return activeA2dpDevice != null && activeA2dpDevice.isLeAudioDualMode();
  }

  public void removeBleCommunicationRouteClient() {
    Iterator it = this.mCommunicationRouteClients.iterator();
    while (it.hasNext()) {
      CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
      int type = communicationRouteClient.getDevice().getType();
      if (type == 26 || type == 27) {
        communicationRouteClient.unregisterDeathRecipient();
        it.remove();
        AudioService.sDeviceLogger.enqueue(
            new EventLogger.StringEvent("ble route removed on CommunicationRouteClients")
                .printLog("AS.AudioDeviceBroker"));
      }
    }
  }

  public boolean isBluetoothCastState() {
    return this.mAudioService.isBluetoothCastState();
  }

  public void setBluetoothCastState(boolean z) {
    this.mAudioService.setBluetoothCastState(z);
  }

  public void setLeBroadcasting(boolean z) {
    this.mAudioService.setLeBroadcasting(z);
  }

  public AudioModeInfo getModeOwner() {
    return this.mAudioModeOwner;
  }

  public void setModeRequesterPackage(String str) {
    Log.i("AS.AudioDeviceBroker", "setModeRequesterPackage requesterPackage=" + str);
    this.mModeRequesterPackage = str;
  }
}
