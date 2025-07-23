package com.android.server;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.tv.interactive.TvInteractiveAppService;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.FMPlayerNativeBase;
import com.samsung.android.audio.AudioConstants;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.lock.LsConstants;
import com.samsung.android.media.fmradio.internal.IFMEventListener;
import com.samsung.android.media.fmradio.internal.IFMPlayer;
import com.samsung.android.share.SemShareConstants;
import com.samsung.android.transcode.constants.EncodeConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class FMRadioService extends IFMPlayer.Stub {
    private static final String ACTINON_ALARM_PLAY = "com.sec.android.app.voicecommand";
    private static final String ACTION_ALL_SOUND_OFF = "android.settings.ALL_SOUND_MUTE";
    private static final String ACTION_CAMERA_START = "com.sec.android.app.camera.ACTION_CAMERA_START";
    private static final String ACTION_CAMERA_STOP = "com.sec.android.app.camera.ACTION_CAMERA_STOP";
    private static final String ACTION_SAVE_FMRECORDING_ONLY = "com.samsung.media.save_fmrecording_only";
    private static final String ACTION_VOLUME_LOCK = "com.sec.android.fm.volume_lock";
    private static final String ACTION_VOLUME_UNLOCK = "com.sec.android.fm.volume_unlock";
    private static final String APP_NAME = "com.sec.android.app.fm";
    private static final String AUDIO_FOCUS_NO_FADEOUT_TAG = "NO_FADEOUT_FROM_AUDIOFOCUS";
    private static final int AVC_MODE_ON = 1;
    public static final int BAND_76000_108000_kHz = 2;
    public static final int BAND_76000_90000_kHz = 3;
    public static final int BAND_87500_108000_kHz = 1;
    public static final int BAND_EXTERNALCHIPSET_64000_76000_kHz = 3;
    public static final int BAND_EXTERNALCHIPSET_76000_107000_kHz = 1;
    public static final int BAND_EXTERNALCHIPSET_76000_91000_kHz = 2;
    public static final int BAND_EXTERNALCHIPSET_87000_108000_kHz = 0;
    public static final int CHAN_SPACING_100_kHz = 10;
    public static final int CHAN_SPACING_200_kHz = 20;
    public static final int CHAN_SPACING_50_kHz = 5;
    public static final int CHAN_SPACING_EXTERNALCHIPSET_100_kHz = 1;
    public static final int CHAN_SPACING_EXTERNALCHIPSET_200_kHz = 0;
    public static final int CHAN_SPACING_EXTERNALCHIPSET_50_kHz = 2;
    private static final int CODE_SCAN_PROGRESS = 1;
    public static final boolean DEBUG = true;
    private static final boolean DEBUGGABLE;
    public static final int DE_TIME_CONSTANT_50 = 1;
    public static final int DE_TIME_CONSTANT_75 = 0;
    private static final int DISABLE_SLIMBUS_DATA_PORT = 0;
    private static final int ENABLE_SLIMBUS_DATA_PORT = 1;
    static final int EVENT_AF_RECEIVED = 14;
    static final int EVENT_AF_STARTED = 13;
    private static final int EVENT_CHANNEL_FOUND = 1;
    private static final int EVENT_EAR_PHONE_CONNECT = 8;
    private static final int EVENT_EAR_PHONE_DISCONNECT = 9;
    private static final int EVENT_OFF = 6;
    private static final int EVENT_ON = 5;
    static final int EVENT_PIECC_EVENT = 18;
    private static final int EVENT_RDS_DISABLED = 12;
    private static final int EVENT_RDS_ENABLED = 11;
    static final int EVENT_RDS_EVENT = 10;
    static final int EVENT_REC_FINISH = 17;
    static final int EVENT_RTPLUS_EVENT = 16;
    private static final int EVENT_SCAN_FINISHED = 3;
    private static final int EVENT_SCAN_STARTED = 2;
    private static final int EVENT_SCAN_STOPPED = 4;
    private static final int EVENT_TUNE = 7;
    public static final int EVENT_VOLUME_LOCK = 15;
    private static final String FACTORY_APP_NAME = "com.sec.factory.app.fm";
    private static final String FMRADIO_CTS_APP_NAME = "com.samsung.cts.SamsungMediaFmradio";
    private static final String FMTEST_APP_NAME = "com.sec.android.fmtestapp";
    private static final String FM_RADIO_AUDIO_FOCUS_TAG = "FM_RADIO";
    private static final String JAPANRADIO_APP_NAME = "jp.radiko.plusfm.player";
    private static final String JAPANRADIO_TUNER_NAME = "jp.radiko.radio.player";
    private static final String KEY_RETURNBACK_VOLUME = "com.sec.android.fm.return_back_volume";
    private static final String KNOX_MODE_USER_SWITCH = "android.intent.action.USER_SWITCHED";
    private static final String MDM_SPEAKER_ENABLED = "com.samsung.android.knox.intent.action.SET_DEVICE_SPEAKER_ENABLED";
    private static final String NEXTRADIO_NAME = "com.nextradioapp.nextradio";
    public static final int OFF_AIRPLANE_MODE_SET = 3;
    public static final int OFF_BATTERY_LOW = 7;
    public static final int OFF_CALL_ACTIVE = 1;
    public static final int OFF_DEVICE_SHUTDOWN = 6;
    public static final int OFF_EAR_PHONE_DISCONNECT = 2;
    public static final int OFF_MOTION_LISTENER = 21;
    public static final int OFF_NORMAL = 0;
    public static final int OFF_PAUSE_COMMAND = 5;
    public static final int OFF_STOP_COMMAND = 4;
    public static final int OFF_TV_OUT = 10;
    private static final String OMC_CHANGED_ACTION = "com.samsung.intent.action.OMC_CHANGED";
    private static final String PARAMETER_AFRMSSI_SAMPLES = "AFRMSSISamples";
    private static final String PARAMETER_AFRMSSI_TH = "AFRMSSIThreshold";
    private static final String PARAMETER_ATJ_CONFIG = "ATJCofig";
    private static final String PARAMETER_BLEND_PAMD_TH = "BlendPAMD_th";
    private static final String PARAMETER_BLEND_RMSSI = "BlendRmssi";
    private static final String PARAMETER_BLEND_RSSI_TH = "BlendRSSI_th";
    private static final String PARAMETER_BLEND_SINR = "BlendSinr";
    private static final String PARAMETER_CFO_TH = "CFOTh12";
    private static final String PARAMETER_CURRENT_RSSI = "CurrentRSSI";
    private static final String PARAMETER_CURRENT_SNR = "CurrentSNR";
    private static final String PARAMETER_DESENSE_LIST = "DeSenseList";
    private static final String PARAMETER_DE_CONSTANT = "DEConstant";
    private static final String PARAMETER_FAKE_CHANNEL = "FakeChannel";
    private static final String PARAMETER_FIRST_CNT_TH = "Cnt_th";
    private static final String PARAMETER_FIRST_RSSI_TH = "RSSI_th";
    private static final String PARAMETER_FIRST_SNR_TH = "SNR_th";
    public static final String PARAMETER_FREQUENCY_OFFSET_TH = "FrequencyOffset_th";
    private static final String PARAMETER_GOOD_CH_RMSSI_TH = "GoodChannelRMSSIThreshold";
    private static final String PARAMETER_HYBRID_SEARCH = "HybridSearch";
    public static final String PARAMETER_IF_COUNT_1 = "IFCount1";
    public static final String PARAMETER_IF_COUNT_2 = "IFCount2";
    public static final String PARAMETER_NOISE_POWER_TH = "NoisePower_th";
    private static final String PARAMETER_OFF_CHANNEL_TH = "OffChannelThreshold";
    private static final String PARAMETER_ON_CHANNEL_TH = "OnChannelThreshold";
    public static final String PARAMETER_PILOT_POWER_TH = "PilotPower_th";
    private static final String PARAMETER_RMSSI_FIRST_STAGE = "RMSSIFirstStage";
    private static final String PARAMETER_SEARCH_ALGO_TYPE = "SearchAlgoType";
    private static final String PARAMETER_SECOND_CNT_TH = "Cnt_th_2";
    private static final String PARAMETER_SECOND_RSSI_TH = "RSSI_th_2";
    private static final String PARAMETER_SECOND_SNR_TH = "SNR_th_2";
    private static final String PARAMETER_SEEK_DC = "SeekDC";
    private static final String PARAMETER_SEEK_DESENSE_RSSI = "SeekDesenseRSSI";
    private static final String PARAMETER_SEEK_QA = "SeekQA";
    private static final String PARAMETER_SEEK_RSSI = "SeekRSSI";
    private static final String PARAMETER_SEEK_SMG = "SeekSMG";
    private static final String PARAMETER_SEEK_SNR = "SeekSNR";
    private static final String PARAMETER_SINR_FIRST_STAGE = "SINRFirstStage";
    private static final String PARAMETER_SINR_SAMPLES = "SINRSamples";
    private static final String PARAMETER_SINR_TH = "SINRThreshold";
    private static final String PARAMETER_SKIP_TUNNING_VALUE = "SkipTuningValue";
    public static final String PARAMETER_SOFTMUTE_COEFF = "SoftMuteCoeff";
    private static final String PARAMETER_SOFTMUTE_TH = "Softmute_th";
    public static final String PARAMETER_SOFT_STEREO_BLEND_COEFF = "SoftStereoBlendCoeff";
    public static final String PARAMETER_SOFT_STEREO_BLEND_REF = "SoftStereoBlendRef";
    public static final int PAUSED = 11;
    private static final int RECORDING_END = 0;
    private static final int RECORDING_START = 1;
    private static final String RESET_SETTING = "android.intent.action.SETTINGS_SOFT_RESET";
    private static final String SA_ACTION = "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String SA_FEATURE = "SBKS";
    private static final String SA_PACKAGE = "com.sec.android.diagmonagent";
    private static final String SA_SERVICE_PACKAGE = "com.samsung.android.sdk.fmradio";
    private static final String SA_SM_SDK_ID = "Galaxy FM Radio SDK";
    private static final String SA_TRACKING_ID = "4M3-399-1025498";
    static final int VOLUME_FADEIN = 200;
    static final int VOLUME_FADEIN_DELAYTIME = 100;
    static final int VOLUME_FADEIN_FIRST_DELAYTIME = 800;
    private static final String VOLUME_UP_DOWN = "114,115";
    private static final String audioMute = "g_fmradio_mute=true";
    private static final String audioUnMute = "g_fmradio_mute=false";
    private static long curFreq;
    private static final boolean isFactoryBinary;
    private static final Object mFMRadioServiceLock;
    private final boolean FEATURE_INDIRECT_MODE;
    private final boolean SURVEY_MODE_ENABLE;
    private ContentObserver bmObserver;
    private boolean mAFEnable;
    private boolean mAirPlaneEnabled;
    private final BroadcastReceiver mAlarmReceiver;
    private final BroadcastReceiver mAllSoundOffReceiver;
    private AudioFocusHandler mAudioFocusHandler;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener;
    private AudioManager mAudioManager;
    private ContentObserver mAvrcpObserver;
    private BroadcastReceiver mButtonReceiver;
    private Context mContext;
    private long mCurrentResumeVol;
    private final BroadcastReceiver mDNDStatusReceiver;
    private HandlerThread mFMHandlerThread;
    final Handler mHandler;
    private Handler mHandlerSA;
    private boolean mIsBatteryLow;
    private boolean mIsExternalChipset;
    public boolean mIsMDMSpeakerEnabled;
    private boolean mIsMute;
    private boolean mIsOn;
    private boolean mIsSeeking;
    private boolean mIsSkipTunigVal;
    private boolean mIsTestMode;
    private Vector<ListenerRecord> mListeners;
    private final BroadcastReceiver mLowBatteryReceiver;
    private final BroadcastReceiver mMDMSpeakerEnabled;
    private final BroadcastReceiver mOMC_Changed_Receiver;
    private PhoneStateListener mPhoneListener;
    private PlayerExternalChipsetBase mPlayerExternalChipset;
    private FMPlayerNativeBase mPlayerNative;
    private PowerManager mPowerManager;
    private boolean mRDSEnable;
    private BroadcastReceiver mReceiver;
    private BroadcastReceiver mResetSettingReceiver;
    private long mResumeVol;
    private Runnable mSamsungAnalyticsRunnable;
    private ArrayList<Long> mScanChannelList;
    private long mScanFreq;
    private boolean mScanProgress;
    private Thread mScanThread;
    private final BroadcastReceiver mSetPropertyReceiver;
    private final BroadcastReceiver mSystemReceiver;
    private final BroadcastReceiver mSystemReceiver1;
    private TelephonyManager mTelephonyManager;
    private BroadcastReceiver mVolumeEventReceiver;
    private boolean mWaitPidDuringScanning;
    private PowerManager.WakeLock mWakeLock;
    private boolean mOnProgress = false;
    private boolean mOffProgress = false;
    private boolean mIsHeadsetPlugged = false;
    private boolean mIsMicrophoneConnected = false;
    private boolean mIsEarphoneConnected = false;
    private boolean mIsTvOutPlugged = false;
    private long mNeedResumeToFreq = -2;
    private long mExtSeekFreq = -1;
    private boolean mIsTransientPaused = false;
    private boolean mNeedToResumeFM = false;
    private boolean mBikeMode = false;
    private boolean mIsTransientDuck = false;
    private boolean mIsPhoneStateListenerRegistered = false;
    private boolean mAvrcpMode = false;
    private long mPreviousFoundFreq = 0;
    private long mCurrentFoundFreq = 0;
    boolean mRecFinishNotified = false;
    private boolean volumeLock = false;
    private boolean isRecording = false;
    private boolean alarmTTSPlay = false;
    private boolean mIsForcestop = false;
    private String SetPropertyPermission = "com.sec.android.app.fm.permission.setproperty";
    private String VolumePropertyname = "service.brcm.fm.volumetable";
    private int mRssi_th = 0;
    private int mSnr_th = 0;
    private int mCnt_th = 0;
    private int mRssi_th_2 = 0;
    private int mSnr_th_2 = 0;
    private int mCnt_th_2 = 0;
    private int mAlgo_type = 1;
    private int mCf0_th12 = 0;
    private int mAfRmssith_th = 0;
    private int mAfRmssisampleCnt_th = 0;
    private int mgoodChrmssi_th = 0;
    private boolean mIsSupportSoftmute = false;
    private String mSoftmutePath = "Speaker";
    private int mFreqOffset_th = 0;
    private int mNoisePwr_th = 0;
    private int mPilotPwr_th = 0;
    private int mSoftmute_th = 0;
    public int mBand = 1;
    public int mChannelSpacing = 10;
    public int mDEConstant = 1;
    private boolean mIsSetWakeKey = false;
    private boolean mIsFMAudioPathOn = false;
    private int mQualcomm_rmssi_firststate = -113;
    private int mQualcomm_onchannel = 109;
    private int mQualcomm_offchannel = 115;
    private int mQualcomm_sinr_samplecnt = 10;
    private int mQualcomm_cfoth12 = EncodeConstants.BitRate.MM_BITRATE_10_HEVC_HD_30;
    private int mQualcomm_af_rmssith = 53;
    private int mQualcomm_af_rmssisamplecnt = 80;
    private int mRichwave_seekDC = 64;
    private int mRichwave_seekQA = 80;
    private int mSlsi_ifcount1 = 5000;
    private int mSlsi_ifcount2 = 4800;
    private long mSlsi_blendcoeff = 3172;
    private long mSlsi_softmutecoeff = -1;
    private long mSlsi_softstereoblendref = 0;
    private int mMtk_seekdesenserssi = -96;
    private int mMtk_seeksmg = EncodeConstants.BitRate.MM_AVG_FHD_DATARATE;
    private long mMtkChipVolume = 31;
    private boolean mMtkSupportSetChipVolume = false;
    private int mMtk_blendrssi_th = -65;
    private int mMtk_blendpamd_th = -30;
    private int mMtk_ATJ_config = 1;

    private boolean isValidPackage() {
        return true;
    }

    static {
        DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
        isFactoryBinary = "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", LsConstants.TAG_UNKNOWN));
        curFreq = -1L;
        mFMRadioServiceLock = new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAvrcpMode() {
        int semGetRadioOutputPath = this.mAudioManager.semGetRadioOutputPath();
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z;
        if (semGetRadioOutputPath == 8) {
            if (z && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
                log("Avrcp mode enabled!!!");
                if (this.volumeLock) {
                    return;
                }
                this.mPlayerNative.setVolume(15L);
                return;
            }
            log("Avrcp mode disabled");
            if (this.volumeLock) {
                return;
            }
            int streamVolume = this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
            log("current_stream_volume: " + streamVolume);
            setVolume((long) streamVolume);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkUsbExternalChipset(UsbDevice usbDevice) {
        if (usbDevice.getVendorId() == 1256) {
            return usbDevice.getProductId() == 41044 || usbDevice.getProductId() == 41049 || usbDevice.getProductId() == 41051;
        }
        return false;
    }

    private void checkUSBDeviceConnected(Context context) {
        log("checkUSBDeviceConnected");
        try {
            UsbManager usbManager = (UsbManager) context.getSystemService("usb");
            if (usbManager == null) {
                log("mUsbManager null");
                return;
            }
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            if (deviceList == null) {
                log("USB Device null");
                return;
            }
            for (UsbDevice usbDevice : deviceList.values()) {
                log("Headset getProductId : " + usbDevice.getProductId());
                log("Headset getVendorId : " + usbDevice.getVendorId());
                if (this.mIsExternalChipset && checkUsbExternalChipset(usbDevice)) {
                    this.mIsHeadsetPlugged = true;
                    this.mPlayerExternalChipset.init(usbDevice);
                }
            }
        } catch (NullPointerException e) {
            Log.e("FMRadioService", "NullPointerException in checkUSBDeviceConnected() : " + e);
        }
    }

    private class AudioFocusHandler extends Handler {
        public static final int EVENT_AUDIOFOCUS_GAIN = 1;
        public static final int EVENT_AUDIOFOCUS_LOSS = -1;
        public static final int EVENT_AUDIOFOCUS_LOSS_TRANSIENT = -2;
        public static final int EVENT_AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;
        private static final String TAG = "mAudioFocusHandler:";

        public AudioFocusHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            FMRadioService.log("mAudioFocusHandler:mHandler(g.what=" + message.what + ") is called");
            int i = message.what;
            if (i == -3 || i == -2 || i == -1 || i == 1) {
                FMRadioService.log("mAudioFocusHandler:Fired  TIME = " + (SystemClock.uptimeMillis() / 1000));
                FMRadioService.this.responedFocusEvent(message.what);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMessageQueue() {
        this.mAudioFocusHandler.removeMessages(-1);
        this.mAudioFocusHandler.removeMessages(-2);
        this.mAudioFocusHandler.removeMessages(-3);
        this.mAudioFocusHandler.removeMessages(1);
        this.mHandler.removeMessages(200);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void responedFocusEvent(int i) {
        if (i == -3) {
            if (isOn()) {
                if (this.volumeLock) {
                    log("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK - recoding O");
                    return;
                }
                log("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK - recoding X");
                if (this.mScanProgress) {
                    this.mNeedResumeToFreq = this.mScanFreq;
                } else if (this.mIsSeeking) {
                    this.mNeedResumeToFreq = curFreq;
                } else {
                    this.mNeedResumeToFreq = getCurrentChannel();
                }
                this.mIsTransientDuck = true;
                mute(true);
                return;
            }
            return;
        }
        if (i == -2) {
            log("AUDIOFOCUS_LOSS_TRANSIENT ");
            if (isOn()) {
                if (this.mScanProgress) {
                    if (this.mIsExternalChipset || FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        cancelScan();
                    }
                    this.mNeedResumeToFreq = this.mScanFreq;
                } else if (this.mIsSeeking) {
                    if (this.mIsExternalChipset || FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        cancelSeek();
                    }
                    this.mNeedResumeToFreq = curFreq;
                } else {
                    this.mNeedResumeToFreq = getCurrentChannel();
                }
                offInternal(11, false);
            } else if (this.mOnProgress) {
                log("still FM on in progress");
                this.mAudioFocusHandler.removeMessages(i);
                this.mAudioFocusHandler.sendEmptyMessage(i);
            }
            this.mNeedToResumeFM = false;
            return;
        }
        if (i == -1) {
            log("AUDIOFOCUS_LOSS ");
            if (isOn()) {
                if (this.mScanProgress) {
                    if (this.mIsExternalChipset || FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        cancelScan();
                    }
                    this.mNeedResumeToFreq = this.mScanFreq;
                } else if (this.mIsSeeking) {
                    if (this.mIsExternalChipset || FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        cancelSeek();
                    }
                    this.mNeedResumeToFreq = curFreq;
                } else {
                    this.mNeedResumeToFreq = getCurrentChannel();
                }
                offInternal(0, true);
                return;
            }
            if (this.mOnProgress) {
                log("still FM on in progress");
                this.mAudioFocusHandler.removeMessages(i);
                this.mAudioFocusHandler.sendEmptyMessage(i);
                return;
            }
            return;
        }
        if (i != 1) {
            return;
        }
        log("AUDIOFOCUS_GAIN ");
        if (this.mIsExternalChipset) {
            setDelay(700L);
        }
        if (isOn() && this.mIsTransientDuck) {
            mute(false);
        }
        this.mIsTransientDuck = false;
        if (!isOn() && this.mNeedResumeToFreq != -2 && !this.mIsForcestop) {
            if (on(false)) {
                if (this.mIsTransientPaused) {
                    this.mResumeVol = this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
                    log("slowly increase the volume till :" + this.mResumeVol);
                    long j = this.mResumeVol;
                    if (j != 0) {
                        this.mCurrentResumeVol = j;
                        if (FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
                            if (!this.mIsExternalChipset) {
                                setVolume(0L);
                                this.mHandler.removeMessages(200);
                                this.mHandler.sendEmptyMessageDelayed(200, 800L);
                            }
                        } else {
                            setVolume(this.mResumeVol);
                        }
                    } else {
                        this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), (int) this.mResumeVol, 0);
                    }
                    this.mIsTransientPaused = false;
                } else {
                    this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)), 0);
                }
                if (this.mNeedResumeToFreq <= 0) {
                    this.mNeedResumeToFreq = 87500L;
                }
                if (this.mIsExternalChipset) {
                    this.mPlayerExternalChipset.tune(((int) this.mNeedResumeToFreq) / 10);
                    if (isUnMuteRadio()) {
                        mute(false);
                    } else {
                        mute(true);
                    }
                } else {
                    this.mPlayerNative.tune(this.mNeedResumeToFreq);
                }
                notifyEvent(7, Long.valueOf(this.mNeedResumeToFreq));
                this.mNeedResumeToFreq = -2L;
                return;
            }
            if (this.mNeedToResumeFM) {
                return;
            }
            log("Not able to resume FM player");
            this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
            return;
        }
        if (this.mOffProgress) {
            log("still FM off in progress");
            this.mAudioFocusHandler.removeMessages(i);
            this.mAudioFocusHandler.sendEmptyMessage(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inDirectModeBroadcast() {
        log("Broadcast audio focus loss intent");
        Intent intent = new Intent();
        intent.setAction("inDirect.mode.audioFocusLoss");
        intent.setClassName("com.sec.android.app.fm", "com.sec.android.app.fm.receiver.AudioFocusLossReceiver");
        this.mContext.sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAudioSystemMute() {
        log("releaseAudioSystemMute ");
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null && audioManager.getRingerMode() == 2 && this.mAudioManager.isStreamMute(1) && this.mAudioManager.isStreamMute(5)) {
            this.mAudioManager.adjustStreamVolume(1, 100, 0);
            this.mAudioManager.adjustStreamVolume(5, 100, 0);
        }
    }

    private void registerDNDStatusChangedListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED);
        intentFilter.addAction(NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED);
        this.mContext.registerReceiver(this.mDNDStatusReceiver, intentFilter, 4);
        log("registering DND Status change Listener");
    }

    private void unregisterDNDStatusChangedListener() {
        log("Unregistering DND Status change listner");
        this.mContext.unregisterReceiver(this.mDNDStatusReceiver);
    }

    private void registerAllSoundOffListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.settings.ALL_SOUND_MUTE");
        this.mContext.registerReceiver(this.mAllSoundOffReceiver, intentFilter, 4);
        log("registering AllSoundOff listener");
    }

    private void unregisterAllSoundOffListener() {
        log("Unregistering AllSoundOff listener");
        this.mContext.unregisterReceiver(this.mAllSoundOffReceiver);
    }

    private void registerAlarmListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTINON_ALARM_PLAY);
        this.mContext.registerReceiver(this.mAlarmReceiver, intentFilter, 4);
        log("registering Alarm play listener");
    }

    private void unregisterAlarmListener() {
        log("Unregistering Alarm play listener");
        this.mContext.unregisterReceiver(this.mAlarmReceiver);
    }

    private void registerBikeModeObserver() {
        log("register bike mode observer");
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(AudioConstants.SETTING_BIKE_MODE), false, this.bmObserver);
        this.mBikeMode = Settings.Secure.getInt(this.mContext.getContentResolver(), AudioConstants.SETTING_BIKE_MODE, 0) == 1;
    }

    private void unregisterBikeModeObserver() {
        log("unregister bike mode observer");
        this.mContext.getContentResolver().unregisterContentObserver(this.bmObserver);
    }

    private void registerAvrcpModeObserver() {
        log("register avrcp mode observer");
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("bluetooth_avc_mode"), false, this.mAvrcpObserver);
        this.mAvrcpMode = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
    }

    private void unregisterAvrcpModeObserver() {
        log("unregister avrcp mode observer");
        this.mContext.getContentResolver().unregisterContentObserver(this.mAvrcpObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readTuningParameters() {
        String string = SemCscFeature.getInstance().getString("CscFeature_FMRadio_SetLocalTunning");
        log("mCscTuningValue = " + string);
        if ("".equals(string)) {
            if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                this.mSnr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SNR"));
                this.mIsSupportSoftmute = FMRadioServiceFeature.FEATURE_SUPPORT_SOFTMUTE;
                this.mSoftmutePath = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SOFTMUTE_PATH");
                this.mAlgo_type = 1;
                this.mSnr_th_2 = -2;
                this.mRssi_th = this.mQualcomm_rmssi_firststate;
                this.mCnt_th = this.mQualcomm_onchannel;
                this.mCnt_th_2 = this.mQualcomm_offchannel;
                this.mRssi_th_2 = this.mQualcomm_sinr_samplecnt;
                this.mCf0_th12 = this.mQualcomm_cfoth12;
                this.mAfRmssith_th = this.mQualcomm_af_rmssith;
                this.mAfRmssisampleCnt_th = this.mQualcomm_af_rmssisamplecnt;
                this.mgoodChrmssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_GOODCH_RMSSITH"));
                return;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                this.mRssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_RSSI"));
                this.mRichwave_seekDC = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_DC"));
                this.mRichwave_seekQA = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_QA"));
                return;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                this.mRssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_RSSI"));
                this.mFreqOffset_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_FREQ_OFFSET"));
                this.mNoisePwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_NOISE_PWR"));
                this.mPilotPwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_PILOT_PWR"));
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                this.mRssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_RSSI"));
                this.mSlsi_ifcount1 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT1"));
                this.mSlsi_ifcount2 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT2"));
                this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                this.mSlsi_softmutecoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                return;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                    this.mMtkSupportSetChipVolume = true;
                    this.mMtkChipVolume = Integer.parseInt(r1);
                }
                this.mRssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_RSSI"));
                this.mMtk_seeksmg = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_SEEKSMG"));
                this.mMtk_seekdesenserssi = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_SEEKDESENSERSSI"));
                this.mSoftmute_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                return;
            }
            return;
        }
        String[] split = string.split(",");
        switch (split.length) {
            case 1:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(split[0]);
                    this.mIsSupportSoftmute = FMRadioServiceFeature.FEATURE_SUPPORT_SOFTMUTE;
                    this.mSoftmutePath = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SOFTMUTE_PATH");
                    this.mAlgo_type = 1;
                    this.mSnr_th_2 = -2;
                    this.mRssi_th = this.mQualcomm_rmssi_firststate;
                    this.mCnt_th = this.mQualcomm_onchannel;
                    this.mCnt_th_2 = this.mQualcomm_offchannel;
                    this.mRssi_th_2 = this.mQualcomm_sinr_samplecnt;
                    this.mCf0_th12 = this.mQualcomm_cfoth12;
                    this.mAfRmssith_th = this.mQualcomm_af_rmssith;
                    this.mAfRmssisampleCnt_th = this.mQualcomm_af_rmssisamplecnt;
                    this.mgoodChrmssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_GOODCH_RMSSITH"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mRichwave_seekDC = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_DC"));
                    this.mRichwave_seekQA = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_QA"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mFreqOffset_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_FREQ_OFFSET"));
                    this.mNoisePwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_NOISE_PWR"));
                    this.mPilotPwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_PILOT_PWR"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT1"));
                    this.mSlsi_ifcount2 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT2"));
                    this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                    }
                    this.mMtk_seeksmg = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_SEEKSMG"));
                    this.mMtk_seekdesenserssi = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_SEEKDESENSERSSI"));
                    this.mSoftmute_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                }
                break;
            case 2:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(split[0]);
                    this.mCnt_th = Integer.parseInt(split[1]);
                    this.mIsSupportSoftmute = FMRadioServiceFeature.FEATURE_SUPPORT_SOFTMUTE;
                    this.mSoftmutePath = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SOFTMUTE_PATH");
                    this.mAlgo_type = 1;
                    this.mSnr_th_2 = -2;
                    this.mRssi_th = this.mQualcomm_rmssi_firststate;
                    this.mCnt_th_2 = this.mQualcomm_offchannel;
                    this.mRssi_th_2 = this.mQualcomm_sinr_samplecnt;
                    this.mCf0_th12 = this.mQualcomm_cfoth12;
                    this.mAfRmssith_th = this.mQualcomm_af_rmssith;
                    this.mAfRmssisampleCnt_th = this.mQualcomm_af_rmssisamplecnt;
                    this.mgoodChrmssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_GOODCH_RMSSITH"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mMtk_seeksmg = Integer.parseInt(split[1]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                    }
                    this.mMtk_seekdesenserssi = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_SEEKDESENSERSSI"));
                    this.mSoftmute_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                }
                break;
            case 3:
                if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mRichwave_seekDC = Integer.parseInt(split[1]);
                    this.mRichwave_seekQA = Integer.parseInt(split[2]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(split[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(split[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mMtk_seeksmg = Integer.parseInt(split[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(split[2]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                    }
                    this.mSoftmute_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(split[0]);
                    this.mIsSupportSoftmute = Boolean.parseBoolean(split[1]);
                    this.mSoftmutePath = split[2];
                    this.mAlgo_type = 1;
                    this.mSnr_th_2 = -2;
                    this.mRssi_th = this.mQualcomm_rmssi_firststate;
                    this.mCnt_th = this.mQualcomm_onchannel;
                    this.mCnt_th_2 = this.mQualcomm_offchannel;
                    this.mRssi_th_2 = this.mQualcomm_sinr_samplecnt;
                    this.mCf0_th12 = this.mQualcomm_cfoth12;
                    this.mAfRmssith_th = this.mQualcomm_af_rmssith;
                    this.mAfRmssisampleCnt_th = this.mQualcomm_af_rmssisamplecnt;
                    this.mgoodChrmssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_GOODCH_RMSSITH"));
                    break;
                }
                break;
            case 4:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSnr_th_2 = Integer.parseInt(split[1]);
                    this.mSnr_th = Integer.parseInt(split[2]);
                    this.mAlgo_type = Integer.parseInt(split[3]);
                    this.mIsSupportSoftmute = FMRadioServiceFeature.FEATURE_SUPPORT_SOFTMUTE;
                    this.mSoftmutePath = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SOFTMUTE_PATH");
                    this.mCnt_th = this.mQualcomm_onchannel;
                    this.mCnt_th_2 = this.mQualcomm_offchannel;
                    this.mRssi_th_2 = this.mQualcomm_sinr_samplecnt;
                    this.mCf0_th12 = this.mQualcomm_cfoth12;
                    this.mAfRmssith_th = this.mQualcomm_af_rmssith;
                    this.mAfRmssisampleCnt_th = this.mQualcomm_af_rmssisamplecnt;
                    this.mgoodChrmssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_GOODCH_RMSSITH"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mFreqOffset_th = Integer.parseInt(split[1]);
                    this.mNoisePwr_th = Integer.parseInt(split[2]);
                    this.mPilotPwr_th = Integer.parseInt(split[3]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(split[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(split[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(split[3]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mMtk_seeksmg = Integer.parseInt(split[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(split[2]);
                    this.mSoftmute_th = Integer.parseInt(split[3]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                    }
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                }
                break;
            case 5:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSnr_th_2 = Integer.parseInt(split[1]);
                    this.mSnr_th = Integer.parseInt(split[2]);
                    this.mAlgo_type = Integer.parseInt(split[3]);
                    this.mgoodChrmssi_th = Integer.parseInt(split[4]);
                    this.mIsSupportSoftmute = FMRadioServiceFeature.FEATURE_SUPPORT_SOFTMUTE;
                    this.mSoftmutePath = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SOFTMUTE_PATH");
                    this.mCnt_th = this.mQualcomm_onchannel;
                    this.mCnt_th_2 = this.mQualcomm_offchannel;
                    this.mRssi_th_2 = this.mQualcomm_sinr_samplecnt;
                    this.mCf0_th12 = this.mQualcomm_cfoth12;
                    this.mAfRmssith_th = this.mQualcomm_af_rmssith;
                    this.mAfRmssisampleCnt_th = this.mQualcomm_af_rmssisamplecnt;
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(split[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(split[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(split[3]);
                    this.mSlsi_softmutecoeff = Integer.parseInt(split[4]);
                    break;
                }
                break;
            case 6:
                if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mMtk_seeksmg = Integer.parseInt(split[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(split[2]);
                    this.mSoftmute_th = Integer.parseInt(split[3]);
                    this.mMtk_blendrssi_th = Integer.parseInt(split[4]);
                    this.mMtk_blendpamd_th = Integer.parseInt(split[5]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                        break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(split[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(split[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(split[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(split[3]);
                    this.mSlsi_softmutecoeff = Integer.parseInt(split[4]);
                    this.mSlsi_softstereoblendref = Integer.parseInt(split[5]);
                    break;
                }
                break;
            default:
                log("Tuning value size: " + split.length);
                break;
        }
    }

    private void readParametersForCurrentRegion() {
        try {
            if (FMRadioServiceFeature.BANDWIDTHAS_87500_108000.equals(FMRadioServiceFeature.FEATURE_BANDWIDTH)) {
                if (!this.mIsExternalChipset) {
                    this.mBand = 1;
                } else {
                    this.mBand = 0;
                }
            } else if (FMRadioServiceFeature.BANDWIDTHAS_76000_108000.equals(FMRadioServiceFeature.FEATURE_BANDWIDTH)) {
                if (!this.mIsExternalChipset) {
                    this.mBand = 2;
                } else {
                    this.mBand = 1;
                }
            } else if (FMRadioServiceFeature.BANDWIDTHAS_76000_90000.equals(FMRadioServiceFeature.FEATURE_BANDWIDTH)) {
                if (!this.mIsExternalChipset) {
                    this.mBand = 3;
                } else {
                    this.mBand = 2;
                }
            } else if (!this.mIsExternalChipset) {
                this.mBand = 1;
            } else {
                this.mBand = 0;
            }
            int i = FMRadioServiceFeature.FEATURE_FREQUENCYSPACE;
            if (i == 50) {
                if (!this.mIsExternalChipset) {
                    this.mChannelSpacing = 5;
                    return;
                } else {
                    this.mChannelSpacing = 2;
                    return;
                }
            }
            if (i == 100) {
                if (!this.mIsExternalChipset) {
                    this.mChannelSpacing = 10;
                    return;
                } else {
                    this.mChannelSpacing = 1;
                    return;
                }
            }
            if (!this.mIsExternalChipset) {
                this.mChannelSpacing = 10;
            } else {
                this.mChannelSpacing = 1;
            }
        } catch (Exception e) {
            if (!this.mIsExternalChipset) {
                this.mBand = 1;
                this.mChannelSpacing = 10;
                this.mDEConstant = 1;
            } else {
                this.mBand = 0;
                this.mChannelSpacing = 1;
            }
            Log.e("FMRadioService", "Exception in readParametersForCurrentRegion() : " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueUpdate(int i, long j) {
        log("queueUpdate(" + i + "," + j + ") is called");
        if (i == 200) {
            log("queueUpdate ## VOLUME_FADEIN");
            this.mHandler.removeMessages(i);
        }
        this.mHandler.sendEmptyMessageDelayed(i, j);
    }

    private static class ListenerRecord {
        IBinder mBinder;
        IFMEventListener mListener;

        public ListenerRecord(IFMEventListener iFMEventListener, IBinder iBinder) {
            this.mBinder = iBinder;
            this.mListener = iFMEventListener;
        }
    }

    public static void log(String str) {
        Log.i("FMRadioService", str);
    }

    public Context getContext() {
        return this.mContext;
    }

    public FMRadioService(Context context) {
        this.mIsExternalChipset = false;
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
        this.SURVEY_MODE_ENABLE = z;
        this.FEATURE_INDIRECT_MODE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FMRADIO_SUPPORT_INDIRECT_MODE");
        this.mHandlerSA = null;
        this.mAudioFocusHandler = null;
        this.mFMHandlerThread = null;
        this.mAvrcpObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.server.FMRadioService.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z2) {
                super.onChange(z2);
                FMRadioService.this.handleAvrcpMode();
            }
        };
        this.mVolumeEventReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                FMRadioService.log("*** mVolumeEventReceiver: ACTION  - " + intent.getAction());
                if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", 10);
                    int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                    FMRadioService.log("Stream: " + intExtra + "  and volume: " + intExtra2);
                    if (intExtra == AudioManager.semGetStreamType(1) || (intExtra == 3 && FMRadioService.this.mIsOn)) {
                        if (!FMRadioService.this.volumeLock) {
                            int streamVolume = FMRadioService.this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
                            FMRadioService.log("current_stream_volume: " + streamVolume);
                            if (intExtra2 == streamVolume && !FMRadioService.this.isDNDEnable()) {
                                if (FMRadioService.this.mHandler.hasMessages(200)) {
                                    FMRadioService.this.mHandler.removeMessages(200);
                                }
                                FMRadioService.this.setVolume(intExtra2);
                            } else {
                                int semGetRadioOutputPath = FMRadioService.this.mAudioManager.semGetRadioOutputPath();
                                FMRadioService fMRadioService = FMRadioService.this;
                                fMRadioService.mAvrcpMode = Settings.Secure.getInt(fMRadioService.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
                                if (FMRadioService.this.mAvrcpMode && semGetRadioOutputPath == 8 && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
                                    FMRadioService.log("mAvrcpMode = true set chip volume 15");
                                    FMRadioService.this.mPlayerNative.setVolume(15L);
                                }
                            }
                        } else {
                            FMRadioService.this.notifyEvent(15, null);
                        }
                    }
                }
                if (FMRadioService.ACTION_VOLUME_LOCK.equals(intent.getAction())) {
                    FMRadioService.log("Volume Locked...");
                    FMRadioService.this.volumeLock = true;
                } else if (FMRadioService.ACTION_VOLUME_UNLOCK.equals(intent.getAction())) {
                    FMRadioService.log("Volume Unlocked...");
                    FMRadioService.this.volumeLock = false;
                }
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                FMRadioService.log("Headset action : " + intent.getAction());
                if ((intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED) || intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) && FMRadioService.this.mIsExternalChipset) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    FMRadioService.log("Headset getProductId : " + usbDevice.getProductId());
                    FMRadioService.log("Headset getVendorId : " + usbDevice.getVendorId());
                    if (!FMRadioService.this.checkUsbExternalChipset(usbDevice)) {
                        FMRadioService.log("Earphone is not compatible");
                        return;
                    }
                    FMRadioService.log("mReceiver: ACTION_USB_HEADSET");
                    FMRadioService.this.mIsHeadsetPlugged = intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED);
                    if (FMRadioService.this.mIsTestMode) {
                        FMRadioService.this.setSpeakerOn(!r14.mIsHeadsetPlugged);
                        StringBuilder sb = new StringBuilder("TestMode :- making setRadioSpeakerOn:");
                        sb.append(!FMRadioService.this.mIsHeadsetPlugged);
                        FMRadioService.log(sb.toString());
                        return;
                    }
                    FMRadioService.log("mIsExternalChipset " + FMRadioService.this.mIsExternalChipset + " mIsHeadsetPlug " + FMRadioService.this.mIsHeadsetPlugged);
                    if (FMRadioService.this.mIsHeadsetPlugged) {
                        FMRadioService.this.mPlayerExternalChipset.init(usbDevice);
                        FMRadioService.this.notifyEvent(8, null);
                        return;
                    }
                    if (FMRadioService.this.mIsOn) {
                        FMRadioService fMRadioService = FMRadioService.this;
                        fMRadioService.mBikeMode = Settings.Secure.getInt(fMRadioService.mContext.getContentResolver(), AudioConstants.SETTING_BIKE_MODE, 0) == 1;
                        FMRadioService.log("mReceiver: bike mode check : " + FMRadioService.this.mBikeMode);
                        if (!FMRadioService.this.mBikeMode) {
                            FMRadioService.this.notifyEvent(9, null);
                        }
                        FMRadioService.this.mPlayerExternalChipset.init(null);
                        if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                            FMRadioService.this.cancelScan();
                        } else {
                            FMRadioService.this.cancelSeek();
                        }
                        FMRadioService.this.offInternal(2, true);
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals("android.intent.action.HEADSET_PLUG") && !FMRadioService.this.mIsExternalChipset) {
                    FMRadioService.log("mReceiver: ACTION_HEADSET_PLUG");
                    FMRadioService.log("==> intent: " + intent);
                    FMRadioService.log("   state: " + intent.getIntExtra("state", 0));
                    FMRadioService.log("    name: " + intent.getStringExtra("name"));
                    FMRadioService.log("    portName: " + intent.getStringExtra("portName"));
                    if (intent.hasExtra("portName") && !intent.getStringExtra("portName").equals(AudioConstants.H2W)) {
                        FMRadioService.log("Not 3.5pi type, and audio not support play FM Radio via usb type c");
                        return;
                    }
                    if (intent.getIntExtra("microphone", 0) == 1) {
                        FMRadioService.this.mIsMicrophoneConnected = intent.getIntExtra("state", 0) == 1;
                    } else {
                        FMRadioService.this.mIsEarphoneConnected = intent.getIntExtra("state", 0) == 1;
                    }
                    FMRadioService fMRadioService2 = FMRadioService.this;
                    fMRadioService2.mIsHeadsetPlugged = fMRadioService2.mIsMicrophoneConnected || FMRadioService.this.mIsEarphoneConnected;
                    FMRadioService.log("mIsHeadsetPlugged :" + FMRadioService.this.mIsHeadsetPlugged);
                    if (FMRadioService.this.mIsTestMode) {
                        FMRadioService.this.setSpeakerOn(!r14.mIsHeadsetPlugged);
                        StringBuilder sb2 = new StringBuilder("TestMode :- making setRadioSpeakerOn:");
                        sb2.append(!FMRadioService.this.mIsHeadsetPlugged);
                        FMRadioService.log(sb2.toString());
                        return;
                    }
                    if (!FMRadioService.this.mIsHeadsetPlugged) {
                        int i = Settings.System.getInt(FMRadioService.this.mContext.getContentResolver(), "tv_out", 0);
                        FMRadioService.log("TV out setting value :" + i);
                        if (i == 1) {
                            return;
                        }
                        if (FMRadioService.this.volumeLock) {
                            FMRadioService.this.notifyRecFinish();
                        }
                        FMRadioService fMRadioService3 = FMRadioService.this;
                        fMRadioService3.mBikeMode = Settings.Secure.getInt(fMRadioService3.mContext.getContentResolver(), AudioConstants.SETTING_BIKE_MODE, 0) == 1;
                        FMRadioService.log("mReceiver: bike mode check : " + FMRadioService.this.mBikeMode);
                        if (!FMRadioService.this.mBikeMode) {
                            FMRadioService.this.notifyEvent(9, null);
                        }
                        if (FMRadioService.this.mIsOn) {
                            if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                                FMRadioService.this.cancelScan();
                            } else {
                                FMRadioService.this.cancelSeek();
                            }
                            FMRadioService.this.offInternal(2, true);
                            return;
                        }
                        FMRadioService.this.mAudioManager.abandonAudioFocus(FMRadioService.this.mAudioFocusListener);
                        FMRadioService.this.mIsTransientPaused = false;
                        return;
                    }
                    FMRadioService.this.notifyEvent(8, null);
                    return;
                }
                if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                    FMRadioService.log("mReceiver: ACTION_AIRPLANE_MODE_CHANGED");
                    FMRadioService fMRadioService4 = FMRadioService.this;
                    fMRadioService4.mAirPlaneEnabled = Settings.Global.getInt(fMRadioService4.mContext.getContentResolver(), "airplane_mode_on", 0) != 0;
                    FMRadioService.log("mAirPlaneEnabled flag :" + FMRadioService.this.mAirPlaneEnabled);
                    if (FMRadioService.this.mAirPlaneEnabled && FMRadioService.this.mIsOn) {
                        if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                            if (FMRadioService.this.mScanProgress) {
                                FMRadioService.this.cancelScan();
                            } else if (FMRadioService.this.mIsSeeking) {
                                FMRadioService.this.cancelSeek();
                            }
                        }
                        FMRadioService.this.offInternal(3, true);
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals("android.intent.action.HDMI_PLUGGED")) {
                    FMRadioService.log("mReceiver: ACTION_HDMI_PLUGGED");
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("==> intent: " + intent);
                    }
                    FMRadioService.log("   state: " + intent.getBooleanExtra("state", false));
                    if (FMRadioService.this.mIsTestMode) {
                        return;
                    }
                    FMRadioService.this.mIsTvOutPlugged = intent.getBooleanExtra("state", false);
                    if (FMRadioService.this.mIsTvOutPlugged && FMRadioService.this.mIsOn) {
                        if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                            FMRadioService.this.cancelScan();
                        } else {
                            FMRadioService.this.cancelSeek();
                        }
                        FMRadioService.this.offInternal(10, true);
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(FMRadioService.ACTION_SAVE_FMRECORDING_ONLY) || intent.getAction().equals("com.sec.android.app.camera.ACTION_CAMERA_START")) {
                    FMRadioService.log("mReceiver: ACTION_SAVE_FMRECORDING_ONLY ");
                    if (FMRadioService.this.isRecording) {
                        FMRadioService.log("mReceiver: Stop recording for Camera ");
                        FMRadioService.this.notifyRecFinish();
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals("android.intent.action.USER_SWITCHED")) {
                    FMRadioService.log("mReceiver: KNOX_MODE_USER_SWITCH - fmradio off");
                    if (FMRadioService.this.mIsOn) {
                        if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                            FMRadioService.this.cancelScan();
                        } else {
                            FMRadioService.this.cancelSeek();
                        }
                        FMRadioService.this.offInternal(4, true);
                    }
                }
            }
        };
        this.mButtonReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                    boolean booleanExtra = intent.getBooleanExtra("android.bluetooth.a2dp.extra.DISCONNECT_A2DP", false);
                    FMRadioService.log("ACTION_AUDIO_BECOMING_NOISE , Its from BT :" + booleanExtra);
                    boolean booleanExtra2 = intent.getBooleanExtra("DISCONNECT_DOCK", false);
                    FMRadioService.log("ACTION_AUDIO_BECOMING_NOISE , Its from Dock :" + booleanExtra2);
                    if (!FMRadioService.this.mIsOn || FMRadioService.this.mIsTestMode || booleanExtra || booleanExtra2) {
                        return;
                    }
                    if (FMRadioService.this.volumeLock) {
                        FMRadioService.this.notifyRecFinish();
                    }
                    FMRadioService.this.notifyEvent(9, null);
                    if (FMRadioService.this.mIsExternalChipset) {
                        FMRadioService.this.mPlayerExternalChipset.init(null);
                    }
                    if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        FMRadioService.this.cancelScan();
                    } else {
                        FMRadioService.this.cancelSeek();
                    }
                    FMRadioService.this.offInternal(2, true);
                }
            }
        };
        this.mResetSettingReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals(FMRadioService.RESET_SETTING)) {
                    FMRadioService.log("ACTION_RESET_SETTING");
                    off();
                }
            }

            private void off() {
                if (FMRadioService.this.mIsOn) {
                    FMRadioService.log("reset setting : stop FM");
                    if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        FMRadioService.this.cancelScan();
                    } else {
                        FMRadioService.this.cancelSeek();
                    }
                    FMRadioService.this.offInternal(6, true);
                    return;
                }
                FMRadioService.log("reset setting : remove audiofocus: FM");
                FMRadioService.this.mAudioManager.abandonAudioFocus(FMRadioService.this.mAudioFocusListener);
            }
        };
        this.mPhoneListener = new PhoneStateListener(null, Looper.getMainLooper()) { // from class: com.android.server.FMRadioService.6
            private boolean mIsPhoneCallRinging = false;

            @Override // android.telephony.PhoneStateListener
            public void onCallStateChanged(int i, String str) {
                FMRadioService.log("phone state : " + i + " mNeedToResumeFM: " + FMRadioService.this.mNeedToResumeFM + " mIsPhoneCallRinging : " + this.mIsPhoneCallRinging + " mIsForcestop : " + FMRadioService.this.mIsForcestop);
                if (i != 0) {
                    if (i != 1) {
                        return;
                    }
                    this.mIsPhoneCallRinging = true;
                    return;
                }
                if (FMRadioService.this.mNeedToResumeFM && !FMRadioService.this.isOn() && FMRadioService.this.mNeedResumeToFreq != -2 && !FMRadioService.this.mIsForcestop && this.mIsPhoneCallRinging) {
                    if (FMRadioService.this.on(false)) {
                        int semGetRadioOutputPath = FMRadioService.this.mAudioManager.semGetRadioOutputPath();
                        FMRadioService.log("onCallStateChanged() :: CALL_STATE_IDLE setPath() = " + semGetRadioOutputPath);
                        FMRadioService.this.mAudioManager.semSetRadioOutputPath(semGetRadioOutputPath);
                        if (FMRadioService.this.mIsTransientPaused) {
                            FMRadioService.this.mResumeVol = r8.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
                            FMRadioService.log("slowly increase the volume till :" + FMRadioService.this.mResumeVol);
                            if (FMRadioService.this.mResumeVol != 0) {
                                FMRadioService fMRadioService = FMRadioService.this;
                                fMRadioService.mCurrentResumeVol = fMRadioService.mResumeVol;
                                if (FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
                                    FMRadioService.this.setVolume(1L);
                                    FMRadioService.this.mHandler.removeMessages(200);
                                    FMRadioService.this.mHandler.sendEmptyMessageDelayed(200, 100L);
                                } else {
                                    FMRadioService fMRadioService2 = FMRadioService.this;
                                    fMRadioService2.setVolume(fMRadioService2.mResumeVol);
                                }
                            } else {
                                FMRadioService.this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), (int) FMRadioService.this.mResumeVol, 0);
                            }
                            FMRadioService.this.mIsTransientPaused = false;
                        } else {
                            FMRadioService.this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), FMRadioService.this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)), 0);
                        }
                        if (FMRadioService.this.mNeedResumeToFreq <= 0) {
                            FMRadioService.this.mNeedResumeToFreq = 87500L;
                        }
                        if (FMRadioService.this.mIsExternalChipset) {
                            FMRadioService.this.mPlayerExternalChipset.tune(((int) FMRadioService.this.mNeedResumeToFreq) / 10);
                        } else {
                            FMRadioService.this.mPlayerNative.tune(FMRadioService.this.mNeedResumeToFreq);
                        }
                        FMRadioService.log("tune from CALL_STATE_IDLE");
                        FMRadioService fMRadioService3 = FMRadioService.this;
                        fMRadioService3.notifyEvent(7, Long.valueOf(fMRadioService3.mNeedResumeToFreq));
                        FMRadioService.this.mNeedResumeToFreq = -2L;
                    } else {
                        FMRadioService.log("Not able to resume FM player");
                    }
                }
                FMRadioService.this.mNeedToResumeFM = false;
                this.mIsPhoneCallRinging = false;
            }
        };
        this.mIsMDMSpeakerEnabled = false;
        this.mMDMSpeakerEnabled = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Bundle extras;
                String action = intent.getAction();
                FMRadioService.log("*** mMDMSpeakerEnabled: ACTION  - " + intent.getAction());
                if (!action.equals(FMRadioService.MDM_SPEAKER_ENABLED) || (extras = intent.getExtras()) == null) {
                    return;
                }
                FMRadioService.this.mIsMDMSpeakerEnabled = ((Boolean) extras.get("state")).booleanValue();
            }
        };
        this.mOMC_Changed_Receiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                FMRadioService.log("mOMC_Changed_Receiver: ACTION  - " + intent.getAction());
                if (action.equals(FMRadioService.OMC_CHANGED_ACTION)) {
                    FMRadioService.this.readTuningParameters();
                }
            }
        };
        this.mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.android.server.FMRadioService.9
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public void onAudioFocusChange(int i) {
                FMRadioService.log("onAudioFocusChange : " + i);
                if (FMRadioService.this.volumeLock && (i == -1 || i == -2)) {
                    FMRadioService.this.mRecFinishNotified = true;
                    FMRadioService.this.notifyEvent(17, null);
                    FMRadioService.this.setDelay(100L);
                }
                if ((i == -1 || i == -2) && FMRadioServiceFeature.CHIP_VENDOR != 9 && !FMRadioService.this.mIsExternalChipset) {
                    if (FMRadioService.this.FEATURE_INDIRECT_MODE) {
                        FMRadioService.this.mute(true);
                        FMRadioService.this.inDirectModeBroadcast();
                    }
                    if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                        FMRadioService.log("onAudioFocusChange : set mute");
                        FMRadioService.this.mute(true);
                        if (SystemProperties.get("ro.board.platform").equals("universal3830")) {
                            FMRadioService.log("set 100ms delay for only universal3830 chipset");
                            FMRadioService.this.setDelay(100L);
                        }
                    }
                    if (FMRadioServiceFeature.CHIP_VENDOR != 7) {
                        FMRadioService.this.setFMAudioPath(false);
                    }
                }
                if (!FMRadioService.this.volumeLock && (i == -1 || i == -2)) {
                    FMRadioService.log("OnAudioFocusChangeListener : mute FM before turn off");
                    if (FMRadioService.this.mIsExternalChipset) {
                        FMRadioService.this.mPlayerExternalChipset.muteOn();
                        FMRadioService.this.setFMAudioPath(false);
                        FMRadioService.this.mPlayerExternalChipset.off();
                    }
                    FMRadioService.this.mAudioManager.setParameters(FMRadioService.audioMute);
                }
                if (i != 1 || !FMRadioService.this.mAudioFocusHandler.hasMessages(-2)) {
                    FMRadioService.this.clearMessageQueue();
                }
                Message obtain = Message.obtain();
                obtain.what = i;
                FMRadioService.this.mAudioFocusHandler.sendMessage(obtain);
                if (FMRadioService.DEBUGGABLE) {
                    FMRadioService.log("OnAudioFocusChangeListener switch off mAudioFocusListener :" + i + " stored freq:" + FMRadioService.this.mNeedResumeToFreq);
                }
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.server.FMRadioService.10
            long currentVolume = 0;

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                FMRadioService.log("mHandler(g.what=" + message.what + ") is called");
                if (message.what == 200) {
                    if (!FMRadioService.this.mIsOn) {
                        this.currentVolume = 0L;
                        return;
                    }
                    if (this.currentVolume < FMRadioService.this.mCurrentResumeVol) {
                        long j = this.currentVolume + 1;
                        this.currentVolume = j;
                        FMRadioService.this.setVolume(j);
                        FMRadioService.this.queueUpdate(200, 100L);
                        return;
                    }
                    long j2 = FMRadioService.this.mResumeVol;
                    this.currentVolume = j2;
                    FMRadioService.this.setVolume(j2);
                    this.currentVolume = 0L;
                }
            }
        };
        this.mSystemReceiver1 = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.11
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                try {
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if ((action.equals("android.intent.action.PACKAGE_REMOVED") || action.equals(Intent.ACTION_PACKAGE_RESTARTED)) && "com.sec.android.app.fm".equals(schemeSpecificPart)) {
                        FMRadioService.this.mIsForcestop = true;
                        off();
                        if (FMRadioService.this.volumeLock) {
                            FMRadioService.this.volumeLock = false;
                            FMRadioService.this.releaseAudioSystemMute();
                        }
                    }
                } catch (NullPointerException e) {
                    Log.e("FMRadioService", "NullPointerException in mSystemReceiver " + e);
                }
            }

            private void off() {
                if (FMRadioService.this.mIsOn) {
                    FMRadioService.log("mSystemReceiver1 force stop : making off FM");
                    if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        FMRadioService.this.cancelScan();
                    } else {
                        FMRadioService.this.cancelSeek();
                    }
                    FMRadioService.this.offInternal(6, true);
                    return;
                }
                FMRadioService.log("mSystemReceiver1 : remove audiofocus");
                FMRadioService.this.mAudioManager.abandonAudioFocus(FMRadioService.this.mAudioFocusListener);
            }
        };
        this.mSystemReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.12
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
                    off();
                }
            }

            private void off() {
                if (FMRadioService.this.mIsOn) {
                    FMRadioService.log("Powering off : stop FM");
                    if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        FMRadioService.this.cancelScan();
                    } else {
                        FMRadioService.this.cancelSeek();
                    }
                    FMRadioService.this.offInternal(6, true);
                    return;
                }
                FMRadioService.log("Powering off : remove audiofocus: FM");
                FMRadioService.this.mAudioManager.abandonAudioFocus(FMRadioService.this.mAudioFocusListener);
            }
        };
        this.mLowBatteryReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                FMRadioService.log("FMRadioService:mLowBatteryReceiver " + action);
                FMRadioService.log("Low batteryWarning Level :1");
                if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                    int intExtra = intent.getIntExtra("status", 1);
                    int intExtra2 = intent.getIntExtra("scale", 100);
                    int intExtra3 = intent.getIntExtra("level", intExtra2);
                    FMRadioService.log("Level = " + intExtra3 + "/" + intExtra2);
                    StringBuilder sb = new StringBuilder("Status = ");
                    sb.append(intExtra);
                    FMRadioService.log(sb.toString());
                    if (intExtra3 <= 1 && intExtra != 2) {
                        FMRadioService.this.mIsBatteryLow = true;
                        if (FMRadioService.this.mIsOn) {
                            if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                                if (FMRadioService.this.mScanProgress) {
                                    FMRadioService.this.cancelScan();
                                } else if (FMRadioService.this.mIsSeeking) {
                                    FMRadioService.this.cancelSeek();
                                }
                            }
                            FMRadioService.this.offInternal(7, true);
                            return;
                        }
                        return;
                    }
                    FMRadioService.this.mIsBatteryLow = false;
                }
            }
        };
        this.mSetPropertyReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.14
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                FMRadioService.log("mSetPropertyReceiver : action is " + action);
                if ("com.sec.android.app.fm.set_property".equals(action)) {
                    String stringExtra = intent.getStringExtra("key");
                    int intExtra = intent.getIntExtra("value", 0);
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("mSetPropertyReceiver :: " + stringExtra + "=" + intExtra);
                    }
                    if (stringExtra.startsWith("service.brcm.fm") || stringExtra.startsWith("service.mrvl.fm")) {
                        SystemProperties.set(stringExtra, String.valueOf(intExtra));
                        return;
                    }
                    return;
                }
                if ("com.sec.android.app.fm.set_volume".equals(action)) {
                    String stringExtra2 = intent.getStringExtra("key");
                    String stringExtra3 = intent.getStringExtra("volumetable");
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("mSetPropertyReceiver :: " + stringExtra2 + "=" + stringExtra3);
                    }
                    if (FMRadioService.this.VolumePropertyname.equals(stringExtra2)) {
                        SystemProperties.set(stringExtra2, stringExtra3);
                    }
                }
            }
        };
        this.mAllSoundOffReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.15
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("mute", 0);
                FMRadioService.log("mAllSoundOffReceiver :: " + intExtra);
                if (intExtra == 1) {
                    FMRadioService.log("FM chip mute");
                    FMRadioService.this.mute(true);
                    if (FMRadioService.this.volumeLock) {
                        FMRadioService.this.notifyRecFinish();
                        return;
                    }
                    return;
                }
                if (FMRadioService.this.isDNDEnable()) {
                    return;
                }
                FMRadioService.log("FM chip unmute");
                FMRadioService.this.mute(false);
            }
        };
        this.mDNDStatusReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.16
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int streamVolume;
                if (FMRadioService.this.isDNDEnable()) {
                    FMRadioService.log("mDNDStatusReceiver onReceive : DND Enable");
                    if (FMRadioService.this.volumeLock) {
                        FMRadioService.this.notifyRecFinish();
                    }
                    FMRadioService.this.mute(true);
                    return;
                }
                if (!FMRadioService.this.mIsMute || FMRadioService.this.isAllSoundOff()) {
                    return;
                }
                FMRadioService.this.mute(false);
                if (FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME && (streamVolume = FMRadioService.this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1))) != 0) {
                    FMRadioService.this.setVolume(streamVolume);
                }
                FMRadioService.log("mDNDStatusReceiver onReceive : DND Disable ");
            }
        };
        this.mAlarmReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.17
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                FMRadioService.log("Alarm onReceive");
                String stringExtra = intent.getStringExtra("command");
                if ("TTSstart".equals(stringExtra)) {
                    FMRadioService.log("TTSstart play");
                    FMRadioService.this.alarmTTSPlay = true;
                }
                if ("TTSstop".equals(stringExtra)) {
                    FMRadioService.log("TTSstop play");
                    FMRadioService.this.alarmTTSPlay = false;
                }
            }
        };
        this.mScanThread = null;
        this.mWaitPidDuringScanning = false;
        this.bmObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.server.FMRadioService.18
            @Override // android.database.ContentObserver
            public void onChange(boolean z2) {
                super.onChange(z2);
                FMRadioService.log("bike mode onChange");
                FMRadioService.this.handleBikeMode();
            }
        };
        this.mContext = context;
        this.mIsExternalChipset = FMRadioServiceFeature.FEATURE_FMRADIO_SUPPORT_EXTERNAL_RADIO_CHIPSET;
        log("mIsExternalChipset" + this.mIsExternalChipset);
        if (this.mIsExternalChipset) {
            if (FMRadioServiceFeature.CHIP_VENDOR == 5) {
                log("mIsExternalChipset CHIP_RICHWAVE");
                this.mPlayerExternalChipset = new PlayerExternalChipsetBesRichwave(this.mContext, this);
            }
        } else {
            this.mPlayerNative = new FMPlayerNative(this);
        }
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mWakeLock = this.mPowerManager.newWakeLock(1, "FMRadio Service");
        HandlerThread handlerThread = new HandlerThread("FMRadioService", 10);
        this.mFMHandlerThread = handlerThread;
        handlerThread.start();
        this.mAudioFocusHandler = new AudioFocusHandler(this.mFMHandlerThread.getLooper());
        if (z) {
            this.mHandlerSA = new Handler(Looper.getMainLooper());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.HDMI_PLUGGED");
        context.registerReceiver(this.mReceiver, intentFilter, 4);
        IntentFilter intentFilter2 = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        intentFilter2.setPriority(999);
        context.registerReceiver(this.mVolumeEventReceiver, intentFilter2, 4);
        context.registerReceiver(this.mVolumeEventReceiver, new IntentFilter(ACTION_VOLUME_LOCK), 4);
        context.registerReceiver(this.mVolumeEventReceiver, new IntentFilter(ACTION_VOLUME_UNLOCK), 4);
        this.mAirPlaneEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) != 0;
        log("mAirPlaneEnabled flag :" + this.mAirPlaneEnabled);
        context.registerReceiver(this.mReceiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        context.registerReceiver(this.mButtonReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
        context.registerReceiver(this.mResetSettingReceiver, new IntentFilter(RESET_SETTING), 4);
        context.registerReceiver(this.mReceiver, new IntentFilter(ACTION_SAVE_FMRECORDING_ONLY), 4);
        IntentFilter intentFilter3 = new IntentFilter("com.sec.android.app.camera.ACTION_CAMERA_START");
        intentFilter3.addAction("com.sec.android.app.camera.ACTION_CAMERA_STOP");
        context.registerReceiver(this.mReceiver, intentFilter3, 4);
        registerSystemListener();
        registerSetPropertyListener();
        registerMDMCommandRec();
        registerOMCChanged();
        readTuningParameters();
        readParametersForCurrentRegion();
        if (this.mIsExternalChipset) {
            checkUSBDeviceConnected(context);
        }
    }

    private void registerBatteryListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.mContext.registerReceiver(this.mLowBatteryReceiver, intentFilter);
        log("registering low battery listener");
    }

    private void unRegisterBatteryListener() {
        this.mContext.unregisterReceiver(this.mLowBatteryReceiver);
        log("unregistering low battery listener");
    }

    private void registerSystemListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(this.mSystemReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter2.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiver(this.mSystemReceiver1, intentFilter2);
    }

    private void unregisterSystemListener() {
        this.mContext.unregisterReceiver(this.mSystemReceiver);
    }

    private void registerSetPropertyListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sec.android.app.fm.set_property");
        intentFilter.addAction("com.sec.android.app.fm.set_volume");
        this.mContext.registerReceiver(this.mSetPropertyReceiver, intentFilter, this.SetPropertyPermission, null, 4);
        log("registering set property listener");
    }

    private void unRegisterSetPropertyListener() {
        this.mContext.unregisterReceiver(this.mSetPropertyReceiver);
        log("unregistering set property listener");
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void tune(long j) {
        if (DEBUGGABLE) {
            log(TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE + j);
        } else {
            log(TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE);
        }
        if (isValidPackage()) {
            if (this.mOffProgress || !this.mIsOn) {
                log("tune can not be processed becuase FM chipset is either off or off in process");
                return;
            }
            mute(true);
            if (this.mIsExternalChipset) {
                this.mPlayerExternalChipset.tune(((int) j) / 10);
            } else {
                this.mPlayerNative.tune(j);
            }
            mute(false);
            log("tune notify event tune");
            notifyEvent(7, Long.valueOf(j));
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void mute(boolean z) {
        log("mute - " + z);
        if (z) {
            if (this.mIsExternalChipset) {
                this.mPlayerExternalChipset.muteOn();
            } else {
                this.mPlayerNative.muteOn();
            }
            this.mAudioManager.setParameters(audioMute);
            this.mIsMute = true;
            return;
        }
        if (isDNDEnable() || isAllSoundOff()) {
            log("AllSoundOff or DND is enabled. So FMRadio is muted.");
            return;
        }
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.muteOff();
        } else {
            this.mPlayerNative.muteOff();
        }
        this.mAudioManager.setParameters(audioUnMute);
        this.mIsMute = false;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long[] getLastScanResult() {
        ArrayList<Long> arrayList = this.mScanChannelList;
        if (arrayList != null) {
            return convertToPrimitives((Long[]) arrayList.toArray(new Long[0]));
        }
        log("getLastScanResult - mScanChannelList null");
        return null;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long seekUp() {
        long seekUp;
        if (!isValidPackage()) {
            return -1L;
        }
        this.mIsSeeking = true;
        mute(true);
        if (this.mIsExternalChipset) {
            long seekUp2 = this.mPlayerExternalChipset.seekUp();
            this.mExtSeekFreq = seekUp2;
            seekUp = seekUp2 * 10;
        } else {
            seekUp = this.mPlayerNative.seekUp();
        }
        mute(false);
        this.mIsSeeking = false;
        notifyEvent(7, Long.valueOf(seekUp));
        return seekUp;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long seekDown() {
        long seekDown;
        if (!isValidPackage()) {
            return -1L;
        }
        this.mIsSeeking = true;
        mute(true);
        if (this.mIsExternalChipset) {
            long seekDown2 = this.mPlayerExternalChipset.seekDown();
            this.mExtSeekFreq = seekDown2;
            seekDown = seekDown2 * 10;
        } else {
            seekDown = this.mPlayerNative.seekDown();
        }
        mute(false);
        this.mIsSeeking = false;
        notifyEvent(7, Long.valueOf(seekDown));
        return seekDown;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void cancelSeek() {
        if (this.mIsExternalChipset) {
            log("result = " + this.mPlayerExternalChipset.stopSeek());
            return;
        }
        this.mPlayerNative.cancelSeek();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public int isBusy() {
        return this.mScanProgress ? 1 : -1;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isHeadsetPlugged() {
        return this.mIsHeadsetPlugged;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isTvOutPlugged() {
        return this.mIsTvOutPlugged;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isBatteryLow() {
        return this.mIsBatteryLow;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isAirPlaneMode() {
        return this.mAirPlaneEnabled;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getCurrentChannel() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getTunedFrequency() * 10;
        }
        return this.mPlayerNative.getCurrentChannel();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setListener(IFMEventListener iFMEventListener) {
        log("[FMRadioService] setListener :" + iFMEventListener);
        if (iFMEventListener != null) {
            synchronized (mFMRadioServiceLock) {
                if (this.mListeners == null) {
                    this.mListeners = new Vector<>();
                }
                this.mListeners.addElement(new ListenerRecord(iFMEventListener, iFMEventListener.asBinder()));
                log("no of listener:" + this.mListeners.size());
            }
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void removeListener(IFMEventListener iFMEventListener) {
        log("[FMRadioService] (removeListener) :" + iFMEventListener);
        if (iFMEventListener == null) {
            return;
        }
        remove(iFMEventListener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        if (r9.mTelephonyManager.getCallStateForSubscription() == 2) goto L14;
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean on_in_testmode() {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.on_in_testmode():boolean");
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean on() {
        if (isValidPackage()) {
            return on(true);
        }
        return false;
    }

    private boolean isCTSTestApp() {
        for (String str : getContext().getPackageManager().getPackagesForUid(Binder.getCallingUid())) {
            if (FMRADIO_CTS_APP_NAME.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFmTestApp() {
        return FMTEST_APP_NAME.equals(getContext().getPackageManager().getPackagesForUid(Binder.getCallingUid())[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x00c0, code lost:
    
        if (r11.mIsTransientPaused == false) goto L57;
     */
    /* JADX WARN: Removed duplicated region for block: B:126:0x040b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0131 A[Catch: Exception -> 0x0455, all -> 0x0490, TryCatch #4 {Exception -> 0x0455, blocks: (B:167:0x00be, B:165:0x0109, B:58:0x0115, B:60:0x011e, B:62:0x0129, B:64:0x0131, B:66:0x0150, B:67:0x0166, B:69:0x0186, B:72:0x018d, B:74:0x0191, B:75:0x01ab, B:79:0x01e8, B:80:0x0209, B:83:0x01f1, B:84:0x020d, B:87:0x015e, B:88:0x0211, B:90:0x0217, B:92:0x0221, B:94:0x022e, B:95:0x0262, B:96:0x024e, B:97:0x026d, B:100:0x0276, B:102:0x0280, B:104:0x0291, B:107:0x0296, B:109:0x029e, B:111:0x02c5, B:113:0x02c9, B:116:0x02cf, B:118:0x02d3, B:121:0x02db, B:123:0x02e0, B:124:0x03d9, B:128:0x040d, B:129:0x042e, B:132:0x0416, B:133:0x0432, B:136:0x02fe, B:138:0x0303, B:140:0x032c, B:141:0x0331, B:143:0x0337, B:144:0x033e, B:146:0x0344, B:148:0x037a, B:149:0x0382, B:150:0x038c, B:151:0x03a2, B:152:0x02a2, B:154:0x02a6, B:156:0x02b2, B:157:0x02bc, B:158:0x0436, B:160:0x043a, B:161:0x043d, B:50:0x00c2, B:52:0x00c8, B:54:0x00ce), top: B:166:0x00be, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0211 A[Catch: Exception -> 0x0455, all -> 0x0490, TRY_ENTER, TryCatch #4 {Exception -> 0x0455, blocks: (B:167:0x00be, B:165:0x0109, B:58:0x0115, B:60:0x011e, B:62:0x0129, B:64:0x0131, B:66:0x0150, B:67:0x0166, B:69:0x0186, B:72:0x018d, B:74:0x0191, B:75:0x01ab, B:79:0x01e8, B:80:0x0209, B:83:0x01f1, B:84:0x020d, B:87:0x015e, B:88:0x0211, B:90:0x0217, B:92:0x0221, B:94:0x022e, B:95:0x0262, B:96:0x024e, B:97:0x026d, B:100:0x0276, B:102:0x0280, B:104:0x0291, B:107:0x0296, B:109:0x029e, B:111:0x02c5, B:113:0x02c9, B:116:0x02cf, B:118:0x02d3, B:121:0x02db, B:123:0x02e0, B:124:0x03d9, B:128:0x040d, B:129:0x042e, B:132:0x0416, B:133:0x0432, B:136:0x02fe, B:138:0x0303, B:140:0x032c, B:141:0x0331, B:143:0x0337, B:144:0x033e, B:146:0x0344, B:148:0x037a, B:149:0x0382, B:150:0x038c, B:151:0x03a2, B:152:0x02a2, B:154:0x02a6, B:156:0x02b2, B:157:0x02bc, B:158:0x0436, B:160:0x043a, B:161:0x043d, B:50:0x00c2, B:52:0x00c8, B:54:0x00ce), top: B:166:0x00be, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean on(boolean r12) {
        /*
            Method dump skipped, instructions count: 1171
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.on(boolean):boolean");
    }

    private void registerTelephonyListener() {
        if (this.mIsPhoneStateListenerRegistered) {
            log("listner already registered");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mTelephonyManager.listen(this.mPhoneListener, 32);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mIsPhoneStateListenerRegistered = true;
            log("registering telephony listener..");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void unRegisterTelephonyListener() {
        if (!this.mIsPhoneStateListenerRegistered) {
            log("listner is not registered");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mTelephonyManager.listen(this.mPhoneListener, 0);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mIsPhoneStateListenerRegistered = false;
            log("unRegisterTelephonyListener ..");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void registerMDMCommandRec() {
        this.mContext.registerReceiver(this.mMDMSpeakerEnabled, new IntentFilter(MDM_SPEAKER_ENABLED), 4);
        log("MDM command reciever registered");
    }

    private void registerOMCChanged() {
        this.mContext.registerReceiver(this.mOMC_Changed_Receiver, new IntentFilter(OMC_CHANGED_ACTION), 4);
        log("OMC changed reciever registered");
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b5 A[Catch: NullPointerException -> 0x00b9, TRY_LEAVE, TryCatch #0 {NullPointerException -> 0x00b9, blocks: (B:19:0x0052, B:21:0x0056, B:23:0x005f, B:25:0x0063, B:26:0x0069, B:27:0x0085, B:29:0x008d, B:32:0x0094, B:35:0x009b, B:37:0x009f, B:40:0x00a3, B:42:0x00a7, B:46:0x00ab, B:48:0x00b5, B:54:0x0071, B:56:0x0075, B:57:0x007c, B:59:0x0080), top: B:18:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setVolume(long r9) {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "set chipset Volume : "
            r0.<init>(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            log(r0)
            boolean r0 = r8.mIsOn
            if (r0 != 0) goto L18
            goto Lcd
        L18:
            boolean r0 = r8.mScanProgress
            if (r0 == 0) goto L23
            java.lang.String r8 = "setVolume :: unset on ScanProgress"
            log(r8)
            return
        L23:
            r0 = 0
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 < 0) goto Lcd
            r1 = 15
            int r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r3 <= 0) goto L31
            goto Lcd
        L31:
            android.media.AudioManager r3 = r8.mAudioManager
            int r3 = r3.semGetRadioOutputPath()
            android.content.Context r4 = r8.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "bluetooth_avc_mode"
            r6 = 1
            int r4 = android.provider.Settings.Secure.getInt(r4, r5, r6)
            r5 = 0
            if (r4 != r6) goto L49
            r4 = r6
            goto L4a
        L49:
            r4 = r5
        L4a:
            r8.mAvrcpMode = r4
            r7 = 8
            if (r4 == 0) goto L71
            if (r3 != r7) goto L71
            boolean r3 = com.android.server.FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME     // Catch: java.lang.NullPointerException -> Lb9
            if (r3 == 0) goto L71
            java.lang.String r3 = "Avrcp on"
            log(r3)     // Catch: java.lang.NullPointerException -> Lb9
            boolean r3 = r8.isRecording     // Catch: java.lang.NullPointerException -> Lb9
            if (r3 != 0) goto L85
            boolean r3 = r8.mIsExternalChipset     // Catch: java.lang.NullPointerException -> Lb9
            if (r3 != 0) goto L69
            com.android.server.FMPlayerNativeBase r3 = r8.mPlayerNative     // Catch: java.lang.NullPointerException -> Lb9
            r3.setVolume(r1)     // Catch: java.lang.NullPointerException -> Lb9
            goto L85
        L69:
            com.android.server.PlayerExternalChipsetBase r1 = r8.mPlayerExternalChipset     // Catch: java.lang.NullPointerException -> Lb9
            r2 = 15
            r1.setVolume(r2)     // Catch: java.lang.NullPointerException -> Lb9
            goto L85
        L71:
            boolean r1 = r8.mIsExternalChipset     // Catch: java.lang.NullPointerException -> Lb9
            if (r1 == 0) goto L7c
            int r1 = (int) r9     // Catch: java.lang.NullPointerException -> Lb9
            com.android.server.PlayerExternalChipsetBase r2 = r8.mPlayerExternalChipset     // Catch: java.lang.NullPointerException -> Lb9
            r2.setVolume(r1)     // Catch: java.lang.NullPointerException -> Lb9
            goto L85
        L7c:
            int r1 = com.android.server.FMRadioServiceFeature.CHIP_VENDOR     // Catch: java.lang.NullPointerException -> Lb9
            if (r1 == r7) goto L85
            com.android.server.FMPlayerNativeBase r1 = r8.mPlayerNative     // Catch: java.lang.NullPointerException -> Lb9
            r1.setVolume(r9)     // Catch: java.lang.NullPointerException -> Lb9
        L85:
            r8.mResumeVol = r9     // Catch: java.lang.NullPointerException -> Lb9
            boolean r9 = r8.isAllSoundOff()     // Catch: java.lang.NullPointerException -> Lb9
            if (r9 != 0) goto Lab
            boolean r9 = r8.isDNDEnable()     // Catch: java.lang.NullPointerException -> Lb9
            if (r9 == 0) goto L94
            goto Lab
        L94:
            int r9 = com.android.server.FMRadioServiceFeature.CHIP_VENDOR     // Catch: java.lang.NullPointerException -> Lb9
            r10 = 3
            if (r9 == r10) goto Lcd
            if (r0 > 0) goto La3
            boolean r9 = r8.mIsMute     // Catch: java.lang.NullPointerException -> Lb9
            if (r9 != 0) goto Lcd
            r8.mute(r6)     // Catch: java.lang.NullPointerException -> Lb9
            return
        La3:
            boolean r9 = r8.mIsMute     // Catch: java.lang.NullPointerException -> Lb9
            if (r9 == 0) goto Lcd
            r8.mute(r5)     // Catch: java.lang.NullPointerException -> Lb9
            return
        Lab:
            java.lang.String r9 = "setVolume :: AllSoundOff or DND is enabled. So FMRadio is muted."
            log(r9)     // Catch: java.lang.NullPointerException -> Lb9
            boolean r9 = r8.mIsMute     // Catch: java.lang.NullPointerException -> Lb9
            if (r9 != 0) goto Lcd
            r8.mute(r6)     // Catch: java.lang.NullPointerException -> Lb9
            return
        Lb9:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "NullPointerException in setVolume() : "
            r9.<init>(r10)
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            java.lang.String r9 = "FMRadioService"
            android.util.Log.e(r9, r8)
        Lcd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.setVolume(long):void");
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getVolume() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getVolume();
        }
        return this.mPlayerNative.getVolume();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setSpeakerOn(boolean z) {
        log("setSpeakerOn : " + z);
        if (this.mIsExternalChipset) {
            if (isOn()) {
                int tunedFrequency = this.mPlayerExternalChipset.getTunedFrequency();
                if (this.mIsSeeking) {
                    this.mPlayerExternalChipset.stopSeek();
                    setDelay(30L);
                    tunedFrequency = (int) this.mExtSeekFreq;
                }
                if (z) {
                    this.mPlayerExternalChipset.off();
                    this.mPlayerExternalChipset.setRecordMode(true);
                } else {
                    this.mPlayerExternalChipset.setRecordMode(false);
                    this.mPlayerExternalChipset.on();
                }
                this.mPlayerExternalChipset.setRssiThreshold(this.mRssi_th);
                this.mPlayerExternalChipset.setBand(this.mBand);
                this.mPlayerExternalChipset.setChannelSpacing(this.mChannelSpacing);
                if (FMRadioServiceFeature.CHIP_VENDOR == 5) {
                    this.mPlayerExternalChipset.setSeekDC(this.mRichwave_seekDC);
                    this.mPlayerExternalChipset.setSeekQA(this.mRichwave_seekQA);
                }
                if (this.mIsMute) {
                    this.mPlayerExternalChipset.muteOn();
                }
                this.mPlayerExternalChipset.tune(tunedFrequency);
                if (this.mRDSEnable) {
                    this.mPlayerExternalChipset.setRdsEnable(true);
                }
            }
        } else {
            this.mPlayerNative.setSpeakerOn(z);
        }
        setSlimbusEnable(0);
        if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
            if (this.mIsSupportSoftmute) {
                if (isPathSupportSoftmute(z ? 2 : 3)) {
                    log("set softmute : true");
                    setSoftmute(true);
                }
            }
            log("set softmute : false");
            setSoftmute(false);
        }
        if (z) {
            if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                this.mAudioManager.setParameters("set_fm_speaker=1");
            } else {
                this.mAudioManager.semSetRadioOutputPath(2);
            }
        } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
            this.mAudioManager.setParameters("set_fm_speaker=0");
        } else {
            this.mAudioManager.semSetRadioOutputPath(3);
        }
        setSlimbusEnable(1);
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setRecordMode(boolean z) {
        String str;
        int i;
        if (this.mIsExternalChipset) {
            return;
        }
        if (z) {
            str = "fm_record=1";
            i = 1;
        } else {
            str = "fm_record=0";
            i = 0;
        }
        this.isRecording = z;
        if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
            this.mAudioManager.setParameters(str);
        }
        int semGetRadioOutputPath = this.mAudioManager.semGetRadioOutputPath();
        boolean z2 = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z2;
        if (z2 && semGetRadioOutputPath == 8 && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
            log(" setRecordMode avrcp on");
            if (this.isRecording) {
                this.mPlayerNative.setVolume(11L);
            }
        }
        this.mPlayerNative.setRecordMode(i);
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getMaxVolume() {
        if (this.mIsExternalChipset) {
            return 15L;
        }
        return this.mPlayerNative.getMaxVolume();
    }

    private void releaseWakeLock() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
                log("Lock is released");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void acquireWakeLock() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
                log("Lock is held");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isOn() {
        return this.mIsOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean offInternal(int i, boolean z) {
        if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
            cancelSeek();
            try {
                wait(10L);
            } catch (InterruptedException e) {
                Log.e("FMRadioService", "InterruptedException in wait() : " + e);
            }
        }
        log("offInternal :: reasonCode=" + i);
        this.mIsTransientDuck = false;
        try {
            if (!this.mIsOn) {
                if (z) {
                    log("offInternal :: remove audiofocus ");
                    this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                }
                return true;
            }
            try {
                this.mOffProgress = true;
                if (this.FEATURE_INDIRECT_MODE || this.mIsExternalChipset || FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    mute(true);
                    setDelay(10L);
                }
                this.mIsTransientPaused = !z;
                if (this.volumeLock && !this.mRecFinishNotified) {
                    notifyEvent(17, null);
                } else if (this.mRecFinishNotified) {
                    this.mRecFinishNotified = false;
                }
                if (FMRadioServiceFeature.CHIP_VENDOR != 7) {
                    setFMAudioPath(false);
                }
                log("offInternal Turning off FM radio");
                if (this.mIsExternalChipset) {
                    if (this.mRDSEnable) {
                        this.mPlayerExternalChipset.setRdsEnable(false);
                    }
                    this.mPlayerExternalChipset.stopNotifyThread(false);
                    if (this.mIsHeadsetPlugged) {
                        log("off external chip set" + this.mPlayerExternalChipset.off());
                        if (i == 11) {
                            this.mIsOn = false;
                        }
                        wait(200L);
                    }
                }
                if (!this.mIsExternalChipset) {
                    this.mPlayerNative.off();
                }
                if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    setFMAudioPath(false);
                }
                this.mOffProgress = false;
                this.mIsOn = false;
                log("off returned from native");
                this.mRDSEnable = false;
                this.mAFEnable = false;
                this.mIsMute = false;
                this.mIsSkipTunigVal = false;
                unregisterBikeModeObserver();
                unregisterAvrcpModeObserver();
                if (!this.mIsTestMode) {
                    unRegisterBatteryListener();
                    if (!this.alarmTTSPlay) {
                        unregisterAlarmListener();
                    }
                    unregisterAllSoundOffListener();
                    unregisterDNDStatusChangedListener();
                }
                this.mIsTestMode = false;
                if (z) {
                    this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                    unRegisterTelephonyListener();
                }
                notifyEvent(6, Integer.valueOf(i));
                if (!this.mIsForcestop) {
                    sendFMOFFBroadcast();
                }
                InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
                boolean z2 = this.mIsOn;
                if (z2 != this.mIsSetWakeKey && inputManager != null) {
                    try {
                        inputManager.semSetWakeKeyDynamically("com.sec.android.app.fm", z2, VOLUME_UP_DOWN);
                    } catch (SecurityException e2) {
                        log("Exception in semSetWakeKeyDynamically(): " + e2.toString());
                    }
                    this.mIsSetWakeKey = this.mIsOn;
                }
                return true;
            } catch (Exception e3) {
                Log.e("FMRadioService", "Exception in offInternal() : " + e3);
                this.mOffProgress = false;
                return false;
            }
        } finally {
            releaseWakeLock();
        }
    }

    private void sendFMOFFBroadcast() {
        log("Sending broadcast FM is in OFF state");
        Intent intent = new Intent("com.sec.android.fm.player_lock.status.off");
        intent.setClassName("com.sec.android.app.fm", "com.sec.android.app.fm.widget.FMRadioProvider");
        intent.setFlags(1073741824);
        this.mContext.sendBroadcast(intent);
    }

    private void sendFMONBroadcast(Object obj) {
        log("Sending broadcast FM is in ON state");
        if (obj != null) {
            Intent intent = new Intent("com.app.fm.auto.on");
            intent.setFlags(268435456);
            intent.setClassName("com.sec.android.app.fm", "com.sec.android.app.fm.receiver.AutoResumeReceiver");
            intent.putExtra("freq", (((Long) obj).longValue() / 1000.0f) + "");
            this.mContext.sendBroadcast(intent);
            return;
        }
        log("sendFMONBroadcast : data is null");
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean off() {
        if (isValidPackage()) {
            return offInternal(0, true);
        }
        return false;
    }

    private void unRegisterMDMCommandRec() {
        this.mContext.unregisterReceiver(this.mMDMSpeakerEnabled);
        log("MDM reciever un-registered");
    }

    private void unRegisterOMCChanged() {
        this.mContext.unregisterReceiver(this.mOMC_Changed_Receiver);
        log("OMC Changed reciever un-registered");
    }

    private void remove(IFMEventListener iFMEventListener) {
        synchronized (mFMRadioServiceLock) {
            Vector<ListenerRecord> vector = this.mListeners;
            if (vector != null && vector.size() != 0) {
                for (int i = 0; i < this.mListeners.size(); i++) {
                    if (this.mListeners.get(i).mBinder == iFMEventListener.asBinder()) {
                        log("[FMRadioService] deleted Listener :" + this.mListeners.remove(i));
                        return;
                    }
                }
            }
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void scan() {
        if (this.mScanProgress) {
            return;
        }
        if (FMRadioServiceFeature.CHIP_VENDOR != 9 || this.mPlayerNative.preInitialize() > 0) {
            this.mScanProgress = true;
            ScanThread scanThread = new ScanThread();
            this.mScanThread = scanThread;
            scanThread.start();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isScanning() {
        return this.mScanProgress;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isSeeking() {
        return this.mIsSeeking;
    }

    public long getCurrentRSSI() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getCurrentRSSI();
        }
        return this.mPlayerNative.getCurrentRSSI();
    }

    public long getCurrentSNR() {
        if (this.mIsExternalChipset) {
            return -1L;
        }
        return this.mPlayerNative.getCurrentSNR();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean cancelScan() {
        try {
            if (this.mScanProgress) {
                this.mScanProgress = false;
                if (this.mIsExternalChipset) {
                    this.mPlayerExternalChipset.stopSeek();
                } else {
                    this.mPlayerNative.cancelSeek();
                }
                ArrayList<Long> arrayList = this.mScanChannelList;
                if (arrayList == null) {
                    return true;
                }
                notifyEvent(4, arrayList.toArray(new Long[0]));
                return true;
            }
        } catch (Exception e) {
            Log.e("FMRadioService", "Exception in cancelScan() : " + e);
        }
        return false;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long searchUp() {
        if (isValidPackage() && this.mIsOn && !this.mIsExternalChipset) {
            return this.mPlayerNative.searchUp();
        }
        return -1L;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long searchAll() {
        if (!this.mIsOn) {
            return -1L;
        }
        if (!this.mIsExternalChipset) {
            return this.mPlayerNative.searchAll();
        }
        return this.mPlayerExternalChipset.searchAll() * 10;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long searchDown() {
        if (!isValidPackage() || this.mIsExternalChipset) {
            return -1L;
        }
        return this.mPlayerNative.searchDown();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void enableRDS() {
        if (isValidPackage()) {
            if (this.mRDSEnable) {
                log("RDS is already enabled");
                return;
            }
            if (this.mIsExternalChipset) {
                this.mPlayerExternalChipset.setRdsEnable(true);
            } else {
                this.mPlayerNative.enableRDS();
            }
            this.mRDSEnable = true;
            notifyEvent(11, null);
            acquireWakeLock();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void disableRDS() {
        if (isValidPackage()) {
            if (!this.mRDSEnable) {
                log("RDS is already disabled");
                return;
            }
            this.mRDSEnable = false;
            if (this.mIsExternalChipset) {
                this.mPlayerExternalChipset.setRdsEnable(false);
            } else {
                this.mPlayerNative.disableRDS();
            }
            notifyEvent(12, null);
            checkForWakeLockRelease();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void enableAF() {
        if (isValidPackage()) {
            if (this.mAFEnable) {
                log("AF is already enabled");
                return;
            }
            if (!this.mIsExternalChipset) {
                this.mPlayerNative.enableAF();
            }
            this.mAFEnable = true;
            acquireWakeLock();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void disableAF() {
        if (isValidPackage()) {
            this.mAFEnable = false;
            if (!this.mIsExternalChipset) {
                this.mPlayerNative.disableAF();
            }
            checkForWakeLockRelease();
        }
    }

    private void checkForWakeLockRelease() {
        if (this.mAFEnable || this.mRDSEnable) {
            return;
        }
        log("AF and RDS is off. release the wake lock");
        releaseWakeLock();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setBand(int i) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setBand(i);
        } else {
            this.mPlayerExternalChipset.setBand(i);
        }
        this.mBand = i;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setChannelSpacing(int i) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setChannelSpacing(i);
        } else {
            this.mPlayerExternalChipset.setChannelSpacing(i);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isRDSEnable() {
        return this.mRDSEnable;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isAFEnable() {
        return this.mAFEnable;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void cancelAFSwitching() {
        if (isValidPackage() && !this.mIsExternalChipset) {
            this.mPlayerNative.cancelAFSwitching();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setStereo() {
        if (isValidPackage()) {
            if (!this.mIsExternalChipset) {
                this.mPlayerNative.setStereo();
            } else {
                this.mPlayerExternalChipset.setSoundMode(0);
            }
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setMono() {
        if (isValidPackage()) {
            if (!this.mIsExternalChipset) {
                this.mPlayerNative.setMono();
            } else {
                this.mPlayerExternalChipset.setSoundMode(1);
            }
        }
    }

    public void notifyEvent(int i, Object obj) {
        Thread thread;
        if (this.mIsOn && i == 7) {
            if (!this.mOffProgress) {
                if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    if (isUnMuteRadio()) {
                        this.mAudioManager.setParameters(audioUnMute);
                    }
                } else {
                    setFMAudioPath(true);
                }
                log("notifyEvent Turning on FM radio");
            } else {
                log("Fail to notify turning on FM radio ");
            }
        }
        if (this.mIsExternalChipset && i == 3) {
            this.mPlayerExternalChipset.setVolume(this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)));
        }
        synchronized (mFMRadioServiceLock) {
            Vector<ListenerRecord> vector = this.mListeners;
            if (vector != null && vector.size() != 0) {
                log("Total listener:" + this.mListeners.size());
                for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                    log("Notifying listener:" + size);
                    switch (i) {
                        case 1:
                            long longValue = obj != null ? ((Long) obj).longValue() : 0L;
                            if (DEBUGGABLE) {
                                log("notifying :EVENT_CHANNEL_FOUND to : listener -->" + size + " : with freq:" + longValue + "-->" + this.mListeners.get(size).mListener.asBinder());
                            }
                            this.mListeners.get(size).mListener.onChannelFound(longValue);
                            continue;
                        case 2:
                            log("notifying :EVENT_SCAN_STARTED to : listener -->" + size + " :" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onScanStarted();
                            continue;
                        case 3:
                            if (obj != null) {
                                long[] convertToPrimitives = convertToPrimitives((Long[]) obj);
                                log("notifying :EVENT_SCAN_FINISHED to : listener -->" + size + " : with data array:" + (convertToPrimitives != null ? convertToPrimitives.length : 0) + "-->" + this.mListeners.get(size).mListener.asBinder());
                                this.mListeners.get(size).mListener.onScanFinished(convertToPrimitives);
                                continue;
                            } else {
                                log("notifying : EVENT_SCAN_FINISHED : data is null !!!");
                            }
                        case 4:
                            if (obj != null) {
                                long[] convertToPrimitives2 = convertToPrimitives((Long[]) obj);
                                log("notifying :EVENT_SCAN_STOPPED to : listener -->" + size + " : with data array:" + (convertToPrimitives2 != null ? convertToPrimitives2.length : 0) + "-->" + this.mListeners.get(size).mListener.asBinder());
                                this.mListeners.get(size).mListener.onScanStopped(convertToPrimitives2);
                                continue;
                            } else {
                                log("notifying : EVENT_SCAN_STOPPED : data is null !!!");
                            }
                        case 5:
                            log("notifying :EVENT_POWER_ON to : listener -->" + size + "-->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onRadioEnabled();
                            continue;
                        case 6:
                            log("notifying :EVENT_POWER_OFF to : listener -->" + size + "-->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onRadioDisabled(obj != null ? ((Integer) obj).intValue() : -1);
                            continue;
                        case 7:
                            if (obj != null) {
                                long longValue2 = ((Long) obj).longValue();
                                curFreq = longValue2;
                                if (DEBUGGABLE) {
                                    log("notifying :EVENT_TUNE to : listener -->" + size + " : with data array:" + longValue2 + "-->" + this.mListeners.get(size).mListener.asBinder());
                                }
                                this.mListeners.get(size).mListener.onTuned(longValue2);
                                continue;
                            } else {
                                log("notifying : EVENT_TUNE : data is null !!!");
                            }
                        case 8:
                            log("notifying :EVENT_EAR_PHONE_CONNECT to : listener -->" + size + ": -->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onHeadsetConnected();
                            continue;
                        case 9:
                            log("notifying :EVENT_EAR_PHONE_DISCONNECT to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onHeadsetDisconnected();
                            continue;
                        case 10:
                            log("notifying : EVENT_RDS_EVENT : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            if (obj != null) {
                                FMPlayerNativeBase.RDSData rDSData = (FMPlayerNativeBase.RDSData) obj;
                                this.mListeners.get(size).mListener.onRadioDataSystemReceived(rDSData.mFreq, rDSData.mChannelName, rDSData.mRadioText);
                                continue;
                            } else {
                                log("notifying : EVENT_RDS_EVENT : data is null !!!");
                            }
                        case 11:
                            log("notifying :EVENT_RDS_ENABLED to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onRadioDataSystemEnabled();
                            continue;
                        case 12:
                            log("notifying :EVENT_RDS_DISABLED to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onRadioDataSystemDisabled();
                            continue;
                        case 13:
                            log("notifying :EVENT_AF_STARTED to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onAlternateFrequencyStarted();
                            continue;
                        case 14:
                            log("notifying :EVENT_AF_RECEIVED to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            if (obj != null) {
                                this.mListeners.get(size).mListener.onAlternateFrequencyReceived(((Long) obj).longValue());
                                continue;
                            } else {
                                log("notifying : EVENT_AF_RECEIVED : data is null !!!");
                            }
                        case 15:
                            log("notifying :EVENT_VOLUME_LOCK to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onVolumeLocked();
                            continue;
                        case 16:
                            log("notifying :EVENT_RTPLUS_EVENT to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            if (obj != null) {
                                FMPlayerNativeBase.RTPlusData rTPlusData = (FMPlayerNativeBase.RTPlusData) obj;
                                this.mListeners.get(size).mListener.onRadioTextPlusReceived(rTPlusData.mContentType1, rTPlusData.mStartPos1, rTPlusData.mAdditionalLen1, rTPlusData.mContentType2, rTPlusData.mStartPos2, rTPlusData.mAdditionalLen2);
                                continue;
                            } else {
                                log("notifying : EVENT_RTPLUS_EVENT : data is null !!!");
                            }
                        case 17:
                            log("notifying :EVENT_REC_FINISH to : listener -->" + size + " : ->" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onRecordingFinished();
                            continue;
                        case 18:
                            if (obj != null) {
                                try {
                                    FMPlayerNativeBase.PIECCData pIECCData = (FMPlayerNativeBase.PIECCData) obj;
                                    this.mListeners.get(size).mListener.onProgrammeIdentificationExtendedCountryCodesReceived(pIECCData.mPI, pIECCData.mECC);
                                    if (this.mWaitPidDuringScanning && (thread = this.mScanThread) != null) {
                                        synchronized (thread) {
                                            this.mScanThread.notify();
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("FMRadioService", "Exception in notifyEvent() : " + e);
                                    Log.e("FMRadioService", "we loose " + size + " listener--ignore it :" + this.mListeners.get(size).mListener);
                                    remove(this.mListeners.get(size).mListener);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Remove done go for next i's value:");
                                    sb.append(size);
                                    log(sb.toString());
                                    if (this.mIsOn && i == 7) {
                                        sendFMONBroadcast(obj);
                                    }
                                }
                            } else {
                                log("notifying : EVENT_PIECC_EVENT : data is null !!!");
                            }
                            break;
                        default:
                    }
                }
                return;
            }
            if (this.mIsOn && i == 7) {
                sendFMONBroadcast(obj);
            }
        }
    }

    private long[] convertToPrimitives(Long[] lArr) {
        if (lArr == null) {
            return null;
        }
        int length = lArr.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = lArr[i].longValue();
        }
        return jArr;
    }

    private void setDEConstant(long j) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setDEConstant(j);
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getPlayedFreq() {
        return curFreq;
    }

    private void setSeekRSSI(long j) {
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.setRssiThreshold((int) j);
        } else {
            this.mPlayerNative.setSeekRSSI(j);
        }
    }

    private void setSeekSNR(long j) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setSeekSNR(j);
    }

    private void setRSSI_th(int i) {
        this.mRssi_th = i;
    }

    private void setSNR_th(int i) {
        this.mSnr_th = i;
    }

    private void setCnt_th(int i) {
        this.mCnt_th = i;
    }

    private void setRSSI_th_2(int i) {
        this.mRssi_th_2 = i;
    }

    private void setSNR_th_2(int i) {
        this.mSnr_th_2 = i;
    }

    private void setCnt_th_2(int i) {
        this.mCnt_th_2 = i;
    }

    private void SkipTuning_Value() {
        this.mIsSkipTunigVal = true;
        Log.e("FMRadioService", "SkipTuning_Value");
    }

    private int getRSSI_th() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getRssiThreshold();
        }
        return this.mRssi_th;
    }

    private int getSNR_th() {
        return this.mSnr_th;
    }

    private int getCnt_th() {
        return this.mCnt_th;
    }

    private int getRSSI_th_2() {
        return this.mRssi_th_2;
    }

    private int getSNR_th_2() {
        return this.mSnr_th_2;
    }

    private int getCnt_th_2() {
        return this.mCnt_th_2;
    }

    private void setAF_th(int i) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setAF_th(i);
    }

    private int getAF_th() {
        if (this.mIsExternalChipset) {
            return -1;
        }
        return this.mPlayerNative.getAF_th();
    }

    private void setAFValid_th(int i) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setAFValid_th(i);
    }

    private int getAFValid_th() {
        if (this.mIsExternalChipset) {
            return -1;
        }
        return this.mPlayerNative.getAFValid_th();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setFMIntenna(boolean z) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setFMIntenna(z);
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setSoftmute(boolean z) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setSoftmute(z);
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean getSoftMuteMode() {
        if (this.mIsExternalChipset) {
            return true;
        }
        return this.mPlayerNative.getSoftMuteMode();
    }

    private void setSearchAlgoType(int i) {
        if (this.mIsExternalChipset) {
            return;
        }
        this.mPlayerNative.setSearchAlgoType(i);
    }

    private int getSearchAlgoType() {
        return this.mPlayerNative.getSearchAlgoType();
    }

    private void setSINRSamples(int i) {
        this.mPlayerNative.setSINRSamples(i);
    }

    private int getSINRSamples() {
        return this.mPlayerNative.getSINRSamples();
    }

    private void setOnChannelThreshold(int i) {
        this.mPlayerNative.setOnChannelThreshold(i);
    }

    private int getOnChannelThreshold() {
        return this.mPlayerNative.getOnChannelThreshold();
    }

    private void setOffChannelThreshold(int i) {
        this.mPlayerNative.setOffChannelThreshold(i);
    }

    private int getOffChannelThreshold() {
        return this.mPlayerNative.getOffChannelThreshold();
    }

    private void setSINRThreshold(int i) {
        this.mPlayerNative.setSINRThreshold(i);
    }

    private int getSINRThreshold() {
        return this.mPlayerNative.getSINRThreshold();
    }

    private void setCFOTh12(int i) {
        this.mPlayerNative.setCFOTh12(i);
    }

    private int getCFOTh12() {
        return this.mPlayerNative.getCFOTh12();
    }

    private void setRMSSIFirstStage(int i) {
        this.mPlayerNative.setRMSSIFirstStage(i);
    }

    private int getRMSSIFirstStage() {
        return this.mPlayerNative.getRMSSIFirstStage();
    }

    private void setSINRFirstStage(int i) {
        this.mPlayerNative.setSINRFirstStage(i);
    }

    private int getSINRFirstStage() {
        return this.mPlayerNative.getSINRFirstStage();
    }

    private void setAFRMSSIThreshold(int i) {
        this.mPlayerNative.setAFRMSSIThreshold(i);
    }

    private int getAFRMSSIThreshold() {
        return this.mPlayerNative.getAFRMSSIThreshold();
    }

    private void setAFRMSSISamples(int i) {
        this.mPlayerNative.setAFRMSSISamples(i);
    }

    private int getAFRMSSISamples() {
        return this.mPlayerNative.getAFRMSSISamples();
    }

    private void setGoodChannelRMSSIThreshold(int i) {
        this.mPlayerNative.setGoodChannelRMSSIThreshold(i);
    }

    private int getGoodChannelRMSSIThreshold() {
        return this.mPlayerNative.getGoodChannelRMSSIThreshold();
    }

    private void setHybridSearch(String str) {
        this.mPlayerNative.setHybridSearch(str);
    }

    private String getHybridSearch() {
        return this.mPlayerNative.getHybridSearch();
    }

    private void setBlendRmssi(int i) {
        this.mPlayerNative.setBlendRmssi(i);
    }

    private int getBlendRmssi() {
        return this.mPlayerNative.getBlendRmssi();
    }

    private void setBlendSinr(int i) {
        this.mPlayerNative.setBlendSinr(i);
    }

    private int getBlendSinr() {
        return this.mPlayerNative.getBlendSinr();
    }

    private void setFrequencyOffsetThreshold(int i) {
        this.mPlayerNative.setFrequencyOffsetThreshold(i);
    }

    private int getFrequencyOffsetThreshold() {
        return this.mPlayerNative.getFrequencyOffsetThreshold();
    }

    private void setPilotPowerThreshold(int i) {
        this.mPlayerNative.setPilotPowerThreshold(i);
    }

    private int getPilotPowerThreshold() {
        return this.mPlayerNative.getPilotPowerThreshold();
    }

    private void setNoisePowerThreshold(int i) {
        this.mPlayerNative.setNoisePowerThreshold(i);
    }

    private int getNoisePowerThreshold() {
        return this.mPlayerNative.getNoisePowerThreshold();
    }

    private void setSeekDC(int i) {
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.setSeekDC(i);
        } else {
            this.mPlayerNative.setSeekDC(i);
        }
    }

    private int getSeekDC() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getSeekDC();
        }
        return this.mPlayerNative.getSeekDC();
    }

    private void setSeekQA(int i) {
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.setSeekQA(i);
        } else {
            this.mPlayerNative.setSeekQA(i);
        }
    }

    private int getSeekQA() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getSeekQA();
        }
        return this.mPlayerNative.getSeekQA();
    }

    private void setIFCount1(int i) {
        this.mSlsi_ifcount1 = i;
        this.mPlayerNative.setIFCount1(i);
    }

    private void setIFCount2(int i) {
        this.mSlsi_ifcount2 = i;
        this.mPlayerNative.setIFCount2(i);
    }

    private int getIFCount1() {
        return this.mSlsi_ifcount1;
    }

    private int getIFCount2() {
        return this.mSlsi_ifcount2;
    }

    private void setSoftStereoBlendCoeff(long j) {
        this.mSlsi_blendcoeff = j;
        this.mPlayerNative.setSoftStereoBlendCoeff(j);
    }

    private long getSoftStereoBlendCoeff() {
        return this.mSlsi_blendcoeff;
    }

    private void setSoftMuteCoeff(long j) {
        this.mSlsi_softmutecoeff = j;
        this.mPlayerNative.setSoftMuteCoeff(j);
    }

    private long getSoftMuteCoeff() {
        return this.mSlsi_softmutecoeff;
    }

    private void setSoftStereoBlendRef(long j) {
        this.mSlsi_softstereoblendref = j;
        this.mPlayerNative.setSoftStereoBlendRef(j);
    }

    private long getSoftStereoBlendRef() {
        return this.mSlsi_softstereoblendref;
    }

    private void setSeekDesenseRSSI(int i) {
        this.mMtk_seekdesenserssi = i;
        this.mPlayerNative.setSeekDesenseRSSI(i);
    }

    private int getSeekDesenseRSSI() {
        return this.mMtk_seekdesenserssi;
    }

    private void setSeekSMG(int i) {
        this.mMtk_seeksmg = i;
        this.mPlayerNative.setSeekSMG(i);
    }

    private int getSeekSMG() {
        return this.mMtk_seeksmg;
    }

    private void setSoftmute_th(int i) {
        this.mSoftmute_th = i;
        this.mPlayerNative.setSoftmute_th(i);
    }

    private int getSoftmute_th() {
        return this.mSoftmute_th;
    }

    private void setBlendRSSI_th(int i) {
        this.mMtk_blendrssi_th = i;
        this.mPlayerNative.setBlendRSSI_th(i);
    }

    private int getBlendRSSI_th() {
        return this.mMtk_blendrssi_th;
    }

    private void setBlendPAMD_th(int i) {
        this.mMtk_blendpamd_th = i;
        this.mPlayerNative.setBlendPAMD_th(i);
    }

    private int getBlendPAMD_th() {
        return this.mMtk_blendpamd_th;
    }

    private void setFakeChannel(String str) {
        this.mPlayerNative.setFakeChannel(str);
    }

    private String getFakeChannel() {
        return this.mPlayerNative.getFakeChannel();
    }

    private void setDeSenseList(String str) {
        this.mPlayerNative.setDeSenseList(str);
    }

    private String getDeSenseList() {
        return this.mPlayerNative.getDeSenseList();
    }

    private void setATJ(int i) {
        this.mMtk_ATJ_config = i;
        this.mPlayerNative.setATJ(i);
    }

    private int getATJ() {
        return this.mMtk_ATJ_config;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0243, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_OFF_CHANNEL_TH) == false) goto L125;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setIntegerTunningParameter(java.lang.String r17, int r18) {
        /*
            Method dump skipped, instructions count: 1016
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.setIntegerTunningParameter(java.lang.String, int):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x025b, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_SEARCH_ALGO_TYPE) == false) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0087, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_FREQUENCY_OFFSET_TH) == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0139, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_SEEK_SMG) == false) goto L73;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getIntegerTunningParameter(java.lang.String r17, int r18) {
        /*
            Method dump skipped, instructions count: 998
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.getIntegerTunningParameter(java.lang.String, int):int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x009c, code lost:
    
        if (r9.equals(com.android.server.FMRadioService.PARAMETER_SOFTMUTE_COEFF) == false) goto L34;
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setLongTunningParameter(java.lang.String r9, long r10) {
        /*
            r8 = this;
            boolean r0 = r8.isValidPackage()
            if (r0 != 0) goto L8
            goto Lda
        L8:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "long setLongTunningParameter: parameterName - "
            r0.<init>(r1)
            r0.append(r9)
            java.lang.String r1 = "  value: "
            r0.append(r1)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            log(r0)
            if (r9 != 0) goto L2a
            java.lang.String r8 = "setLongTunningParameter:  parameterName is null. So do nothing"
            log(r8)
            return
        L2a:
            r9.hashCode()
            int r0 = r9.hashCode()
            r1 = 2
            r2 = 1
            r3 = 0
            r4 = -1
            switch(r0) {
                case -1141489851: goto L50;
                case -658516033: goto L45;
                case 1060814575: goto L3a;
                default: goto L38;
            }
        L38:
            r0 = r4
            goto L5a
        L3a:
            java.lang.String r0 = "SeekRSSI"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto L43
            goto L38
        L43:
            r0 = r1
            goto L5a
        L45:
            java.lang.String r0 = "SeekSNR"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto L4e
            goto L38
        L4e:
            r0 = r2
            goto L5a
        L50:
            java.lang.String r0 = "DEConstant"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto L59
            goto L38
        L59:
            r0 = r3
        L5a:
            java.lang.String r5 = " for chip vendor - "
            java.lang.String r6 = "setLongTunningParameter() : invalid parameterName - "
            switch(r0) {
                case 0: goto L82;
                case 1: goto L7e;
                case 2: goto L7a;
                default: goto L62;
            }
        L62:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r6)
            r0.append(r9)
            r0.append(r5)
            int r7 = com.android.server.FMRadioServiceFeature.CHIP_VENDOR
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            log(r0)
            goto L85
        L7a:
            r8.setSeekRSSI(r10)
            goto L85
        L7e:
            r8.setSeekSNR(r10)
            goto L85
        L82:
            r8.setDEConstant(r10)
        L85:
            int r0 = com.android.server.FMRadioServiceFeature.CHIP_VENDOR
            r7 = 7
            if (r0 != r7) goto Lda
            r9.hashCode()
            int r0 = r9.hashCode()
            switch(r0) {
                case -681786198: goto Laa;
                case 1746788740: goto L9f;
                case 1777837110: goto L96;
                default: goto L94;
            }
        L94:
            r1 = r4
            goto Lb4
        L96:
            java.lang.String r0 = "SoftMuteCoeff"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto Lb4
            goto L94
        L9f:
            java.lang.String r0 = "SoftStereoBlendRef"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto La8
            goto L94
        La8:
            r1 = r2
            goto Lb4
        Laa:
            java.lang.String r0 = "SoftStereoBlendCoeff"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto Lb3
            goto L94
        Lb3:
            r1 = r3
        Lb4:
            switch(r1) {
                case 0: goto Ld7;
                case 1: goto Ld3;
                case 2: goto Lcf;
                default: goto Lb7;
            }
        Lb7:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r6)
            r8.append(r9)
            r8.append(r5)
            int r9 = com.android.server.FMRadioServiceFeature.CHIP_VENDOR
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            log(r8)
            return
        Lcf:
            r8.setSoftMuteCoeff(r10)
            return
        Ld3:
            r8.setSoftStereoBlendRef(r10)
            return
        Ld7:
            r8.setSoftStereoBlendCoeff(r10)
        Lda:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.setLongTunningParameter(java.lang.String, long):void");
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getLongTunningParameter(String str, long j) {
        log("long getLongTunningParameter: parameterName - " + str);
        if (str == null) {
            log("getLongTunningParameter:  parameterName is null. So do nothing");
            return j;
        }
        str.hashCode();
        if (str.equals(PARAMETER_CURRENT_RSSI)) {
            return getCurrentRSSI();
        }
        if (str.equals(PARAMETER_CURRENT_SNR)) {
            return getCurrentSNR();
        }
        log("getLongTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
        if (FMRadioServiceFeature.CHIP_VENDOR != 7) {
            return j;
        }
        str.hashCode();
        switch (str) {
            case "SoftStereoBlendCoeff":
                break;
            case "SoftStereoBlendRef":
                break;
            case "SoftMuteCoeff":
                break;
            default:
                log("getLongTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                break;
        }
        return j;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setStringTunningParameter(String str, String str2) {
        if (isValidPackage()) {
            log("setStringTunningParameter: parameterName - " + str + "  value: " + str2);
            if (str == null) {
                log("setStringTunningParameter:  parameterName is null. So do nothing");
                return;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                str.hashCode();
                if (str.equals(PARAMETER_HYBRID_SEARCH)) {
                    setHybridSearch(str2);
                    return;
                }
                log("setStringTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                return;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                str.hashCode();
                if (str.equals(PARAMETER_DESENSE_LIST)) {
                    setDeSenseList(str2);
                    return;
                }
                if (str.equals(PARAMETER_FAKE_CHANNEL)) {
                    setFakeChannel(str2);
                    return;
                }
                log("setStringTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                return;
            }
            log("setStringTunningParameter() : this parameter is not support yet - " + str + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public String getStringTunningParameter(String str, String str2) {
        log("getStringTunningParameter: parameterName - " + str);
        if (str == null) {
            log("getStringTunningParameter:  parameterName is null. So do nothing");
            return str2;
        }
        if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
            str.hashCode();
            if (str.equals(PARAMETER_HYBRID_SEARCH)) {
                return getHybridSearch();
            }
            log("getStringTunningParameter() : invalid parameterName - " + str + " for chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
            return str2;
        }
        if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
            str.hashCode();
            if (str.equals(PARAMETER_DESENSE_LIST)) {
                return getDeSenseList();
            }
            if (str.equals(PARAMETER_FAKE_CHANNEL)) {
                return getFakeChannel();
            }
            log("getStringTunningParameter() : invalid parameterName - " + str + " for chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
            return str2;
        }
        log("getStringTunningParameter() : this parameter is not support yet - " + str + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
        return str2;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        try {
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (wakeLock != null && wakeLock.isHeld()) {
                this.mWakeLock.release();
            }
            unregisterSystemListener();
            unRegisterSetPropertyListener();
            unRegisterMDMCommandRec();
            unRegisterOMCChanged();
            this.mScanProgress = false;
        } catch (Error e) {
            Log.e("FMRadioService", "Exception in finalize() : " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSignalSetting(int i, int i2, int i3) {
        if (this.mIsOn && !this.mIsExternalChipset) {
            this.mPlayerNative.setRSSI_th(i);
            this.mPlayerNative.setSNR_th(i2);
            this.mPlayerNative.setCnt_th(i3);
        }
    }

    class ScanThread extends Thread {
        ScanThread() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:75:0x01c7, code lost:
        
            r1 = r26.this$0;
            r1.notifyEvent(3, r1.mScanChannelList.toArray(new java.lang.Long[0]));
            java.lang.Thread.sleep(r20);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void doScan() throws java.lang.InterruptedException {
            /*
                Method dump skipped, instructions count: 791
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.ScanThread.doScan():void");
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0128, code lost:
        
            if (r3.isHeld() != false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0155, code lost:
        
            com.android.server.FMRadioService.log("Scanning Thread work is done...");
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x015a, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x014f, code lost:
        
            r3.release();
            com.android.server.FMRadioService.log("Scan thread released the dimmed screen lock");
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x014d, code lost:
        
            if (r3.isHeld() == false) goto L42;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 370
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.FMRadioService.ScanThread.run():void");
        }
    }

    public void notifyRecFinish() {
        log("notifyRecFinish EVENT_REC_FINISH");
        notifyEvent(17, null);
        this.mAudioManager.setParameters("fmradio_recoding=off");
    }

    private String getPropertyProductName() {
        return SystemProperties.get("ro.product.name");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBikeMode() {
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), AudioConstants.SETTING_BIKE_MODE, 0) == 1;
        this.mBikeMode = z;
        if (z) {
            log("bike mode enabled");
            offInternal(4, true);
        } else {
            log("bike mode disabled");
        }
    }

    public boolean isDNDEnable() {
        return (Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.ZEN_MODE, 0) == 1) && ((((NotificationManager) this.mContext.getSystemService("notification")).getNotificationPolicy().priorityCategories & 64) == 0);
    }

    public boolean isAllSoundOff() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFMAudioPath(boolean z) {
        String str;
        log("setFMAudioPath : " + z + " mIsFMAudioPathOn : " + this.mIsFMAudioPathOn);
        if (z == this.mIsFMAudioPathOn) {
            return;
        }
        this.mIsFMAudioPathOn = z;
        if (z) {
            str = "g_fmradio_enable=true";
        } else {
            str = "g_fmradio_enable=false";
        }
        this.mAudioManager.setParameters(str);
    }

    private boolean isCherokeeChip() {
        return FMRadioServiceFeature.CHIP_VENDOR == 9;
    }

    private void setSlimbusEnable(int i) {
        log("setSlimbusEnable " + i);
        log("isCherokeeChip: " + isCherokeeChip() + " volumeLock: " + this.volumeLock);
        if (isCherokeeChip() && !this.volumeLock) {
            this.mPlayerNative.setSlimbusEnable(i);
        } else {
            log("setSlimbusEnable : Not applicable");
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isDeviceSpeakerEnabled() {
        return this.mIsMDMSpeakerEnabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDelay(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            Log.e("FMRadioService", "InterruptedException in sleep() : " + e);
        }
    }

    public boolean isUnMuteRadio() {
        return (this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)) <= 0 || isAllSoundOff() || isDNDEnable()) ? false : true;
    }

    public boolean isPathSupportSoftmute(int i) {
        if ("Both".equals(this.mSoftmutePath)) {
            return true;
        }
        if ("Speaker".equals(this.mSoftmutePath) && i == 2) {
            return true;
        }
        return "Headset".equals(this.mSoftmutePath) && i == 3;
    }

    private class SamsungAnalyticsRunnable implements Runnable {
        private String packageName;
        private String version;

        public SamsungAnalyticsRunnable(String str, String str2) {
            this.packageName = str;
            this.version = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                FMRadioService.this.sendInfoSamsungAnalytics(this.packageName, this.version);
            } catch (Exception e) {
                Log.e("FMRadioService", "SamsungAnalyticsRunnable Exception: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInfoSamsungAnalytics(String str, String str2) {
        log("sendInfoSamsungAnalytics ,packageName : " + str + ", version : " + str2);
        Bundle bundle = new Bundle();
        bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SA_TRACKING_ID);
        bundle.putString("feature", SA_FEATURE);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sm_sdk_id", SA_SM_SDK_ID);
            jSONObject.put("sm_sdk_client_pkg_name", str);
            jSONObject.put("sm_sdk_client_pkg_version", str2);
        } catch (JSONException e) {
            Log.e("FMRadioService", "JSONException: " + e);
        }
        log("SALog jsonstring: " + jSONObject.toString());
        bundle.putString(SemShareConstants.SURVEY_CONTENT_EXTRA, jSONObject.toString());
        bundle.putString(SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE, SA_SERVICE_PACKAGE);
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.putExtras(bundle);
        intent.setPackage("com.sec.android.diagmonagent");
        log("SALog sendbroadcast");
        this.mContext.sendBroadcast(intent);
    }
}
