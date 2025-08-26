package com.android.server;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
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
import android.os.SemSystemProperties;
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
        int iSemGetRadioOutputPath = this.mAudioManager.semGetRadioOutputPath();
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z;
        if (iSemGetRadioOutputPath == 8) {
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
        public void handleMessage(Message message) throws InterruptedException {
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
    public void responedFocusEvent(int i) throws InterruptedException {
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
        String[] strArrSplit = string.split(",");
        switch (strArrSplit.length) {
            case 1:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(strArrSplit[0]);
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mRichwave_seekDC = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_DC"));
                    this.mRichwave_seekQA = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_QA"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mFreqOffset_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_FREQ_OFFSET"));
                    this.mNoisePwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_NOISE_PWR"));
                    this.mPilotPwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_PILOT_PWR"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT1"));
                    this.mSlsi_ifcount2 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT2"));
                    this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
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
                    this.mSnr_th = Integer.parseInt(strArrSplit[0]);
                    this.mCnt_th = Integer.parseInt(strArrSplit[1]);
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mMtk_seeksmg = Integer.parseInt(strArrSplit[1]);
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mRichwave_seekDC = Integer.parseInt(strArrSplit[1]);
                    this.mRichwave_seekQA = Integer.parseInt(strArrSplit[2]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(strArrSplit[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(strArrSplit[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mMtk_seeksmg = Integer.parseInt(strArrSplit[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(strArrSplit[2]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                    }
                    this.mSoftmute_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(strArrSplit[0]);
                    this.mIsSupportSoftmute = Boolean.parseBoolean(strArrSplit[1]);
                    this.mSoftmutePath = strArrSplit[2];
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSnr_th_2 = Integer.parseInt(strArrSplit[1]);
                    this.mSnr_th = Integer.parseInt(strArrSplit[2]);
                    this.mAlgo_type = Integer.parseInt(strArrSplit[3]);
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mFreqOffset_th = Integer.parseInt(strArrSplit[1]);
                    this.mNoisePwr_th = Integer.parseInt(strArrSplit[2]);
                    this.mPilotPwr_th = Integer.parseInt(strArrSplit[3]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(strArrSplit[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(strArrSplit[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(strArrSplit[3]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mMtk_seeksmg = Integer.parseInt(strArrSplit[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(strArrSplit[2]);
                    this.mSoftmute_th = Integer.parseInt(strArrSplit[3]);
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSnr_th_2 = Integer.parseInt(strArrSplit[1]);
                    this.mSnr_th = Integer.parseInt(strArrSplit[2]);
                    this.mAlgo_type = Integer.parseInt(strArrSplit[3]);
                    this.mgoodChrmssi_th = Integer.parseInt(strArrSplit[4]);
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
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(strArrSplit[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(strArrSplit[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(strArrSplit[3]);
                    this.mSlsi_softmutecoeff = Integer.parseInt(strArrSplit[4]);
                    break;
                }
                break;
            case 6:
                if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mMtk_seeksmg = Integer.parseInt(strArrSplit[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(strArrSplit[2]);
                    this.mSoftmute_th = Integer.parseInt(strArrSplit[3]);
                    this.mMtk_blendrssi_th = Integer.parseInt(strArrSplit[4]);
                    this.mMtk_blendpamd_th = Integer.parseInt(strArrSplit[5]);
                    if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME"))) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(r1);
                        break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(strArrSplit[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(strArrSplit[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(strArrSplit[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(strArrSplit[3]);
                    this.mSlsi_softmutecoeff = Integer.parseInt(strArrSplit[4]);
                    this.mSlsi_softstereoblendref = Integer.parseInt(strArrSplit[5]);
                    break;
                }
                break;
            default:
                log("Tuning value size: " + strArrSplit.length);
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
                                int iSemGetRadioOutputPath = FMRadioService.this.mAudioManager.semGetRadioOutputPath();
                                FMRadioService fMRadioService = FMRadioService.this;
                                fMRadioService.mAvrcpMode = Settings.Secure.getInt(fMRadioService.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
                                if (FMRadioService.this.mAvrcpMode && iSemGetRadioOutputPath == 8 && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
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
            public void onReceive(Context context2, Intent intent) throws InterruptedException {
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
                        int iSemGetRadioOutputPath = FMRadioService.this.mAudioManager.semGetRadioOutputPath();
                        FMRadioService.log("onCallStateChanged() :: CALL_STATE_IDLE setPath() = " + iSemGetRadioOutputPath);
                        FMRadioService.this.mAudioManager.semSetRadioOutputPath(iSemGetRadioOutputPath);
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
            public void onAudioFocusChange(int i) throws InterruptedException {
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
                Message messageObtain = Message.obtain();
                messageObtain.what = i;
                FMRadioService.this.mAudioFocusHandler.sendMessage(messageObtain);
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
        context.registerReceiver(this.mVolumeEventReceiver, new IntentFilter(ACTION_VOLUME_LOCK), 2);
        context.registerReceiver(this.mVolumeEventReceiver, new IntentFilter(ACTION_VOLUME_UNLOCK), 2);
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
        long jSeekUp;
        if (!isValidPackage()) {
            return -1L;
        }
        this.mIsSeeking = true;
        mute(true);
        if (this.mIsExternalChipset) {
            long jSeekUp2 = this.mPlayerExternalChipset.seekUp();
            this.mExtSeekFreq = jSeekUp2;
            jSeekUp = jSeekUp2 * 10;
        } else {
            jSeekUp = this.mPlayerNative.seekUp();
        }
        mute(false);
        this.mIsSeeking = false;
        notifyEvent(7, Long.valueOf(jSeekUp));
        return jSeekUp;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long seekDown() {
        long jSeekDown;
        if (!isValidPackage()) {
            return -1L;
        }
        this.mIsSeeking = true;
        mute(true);
        if (this.mIsExternalChipset) {
            long jSeekDown2 = this.mPlayerExternalChipset.seekDown();
            this.mExtSeekFreq = jSeekDown2;
            jSeekDown = jSeekDown2 * 10;
        } else {
            jSeekDown = this.mPlayerNative.seekDown();
        }
        mute(false);
        this.mIsSeeking = false;
        notifyEvent(7, Long.valueOf(jSeekDown));
        return jSeekDown;
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
    
        if (r9.mTelephonyManager.getCallStateForSubscription() == 2) goto L14;
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean on_in_testmode() {
        if (!isFactoryBinary) {
            log("on_in_testmode is called with normal binary. This function is only for Factory binary. So just return");
            return false;
        }
        try {
            if (this.mTelephonyManager.getCallStateForSubscription() != 1) {
            }
            return false;
        } catch (Exception e) {
            Log.e("FMRadioService", "Exception in getCallStateForSubscription() : " + e);
        }
        if (this.mIsOn) {
            return true;
        }
        try {
            if (this.mIsExternalChipset) {
                if (this.mAudioManager.semGetRadioOutputPath() == 2) {
                    this.mPlayerExternalChipset.setRecordMode(true);
                    this.mIsOn = this.mPlayerExternalChipset.isOn();
                } else {
                    this.mIsOn = this.mPlayerExternalChipset.on();
                }
            } else {
                if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    if (this.mPlayerNative.preInitialize() > 0) {
                        setFMAudioPath(true);
                    } else {
                        log("FM preInitialize() failed");
                        return false;
                    }
                }
                this.mIsOn = this.mPlayerNative.on() > 0;
            }
            if (this.mIsOn) {
                setSoftmute(false);
                this.mIsTestMode = true;
                notifyEvent(5, null);
                mute(false);
                setFMAudioPath(true);
                log("on_in_testmode Turning on FM radio");
                return true;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                setFMAudioPath(false);
            }
            this.mIsFMAudioPathOn = false;
            this.mIsOn = false;
            releaseWakeLock();
            return false;
        } catch (Exception e2) {
            if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                setFMAudioPath(false);
            }
            this.mIsFMAudioPathOn = false;
            this.mIsOn = false;
            Log.e("FMRadioService", "Exception in on_in_testmode() : " + e2);
            releaseWakeLock();
            return false;
        }
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
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c2 A[Catch: Exception -> 0x0455, all -> 0x0490, TryCatch #4 {Exception -> 0x0455, blocks: (B:55:0x00be, B:62:0x0109, B:66:0x0115, B:68:0x011e, B:69:0x0129, B:71:0x0131, B:73:0x0150, B:75:0x0166, B:77:0x0186, B:80:0x018d, B:82:0x0191, B:83:0x01ab, B:86:0x01e8, B:90:0x0209, B:89:0x01f1, B:91:0x020d, B:74:0x015e, B:94:0x0211, B:96:0x0217, B:98:0x0221, B:100:0x022e, B:102:0x0262, B:101:0x024e, B:103:0x026d, B:106:0x0276, B:108:0x0280, B:110:0x0291, B:113:0x0296, B:115:0x029e, B:123:0x02c5, B:125:0x02c9, B:128:0x02cf, B:130:0x02d3, B:133:0x02db, B:135:0x02e0, B:152:0x03d9, B:155:0x040d, B:159:0x042e, B:158:0x0416, B:160:0x0432, B:136:0x02fe, B:138:0x0303, B:140:0x032c, B:141:0x0331, B:143:0x0337, B:144:0x033e, B:146:0x0344, B:148:0x037a, B:149:0x0382, B:150:0x038c, B:151:0x03a2, B:117:0x02a2, B:119:0x02a6, B:121:0x02b2, B:122:0x02bc, B:163:0x0436, B:165:0x043a, B:166:0x043d, B:57:0x00c2, B:59:0x00c8, B:61:0x00ce), top: B:188:0x00be, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0109 A[Catch: Exception -> 0x0455, all -> 0x0490, TryCatch #4 {Exception -> 0x0455, blocks: (B:55:0x00be, B:62:0x0109, B:66:0x0115, B:68:0x011e, B:69:0x0129, B:71:0x0131, B:73:0x0150, B:75:0x0166, B:77:0x0186, B:80:0x018d, B:82:0x0191, B:83:0x01ab, B:86:0x01e8, B:90:0x0209, B:89:0x01f1, B:91:0x020d, B:74:0x015e, B:94:0x0211, B:96:0x0217, B:98:0x0221, B:100:0x022e, B:102:0x0262, B:101:0x024e, B:103:0x026d, B:106:0x0276, B:108:0x0280, B:110:0x0291, B:113:0x0296, B:115:0x029e, B:123:0x02c5, B:125:0x02c9, B:128:0x02cf, B:130:0x02d3, B:133:0x02db, B:135:0x02e0, B:152:0x03d9, B:155:0x040d, B:159:0x042e, B:158:0x0416, B:160:0x0432, B:136:0x02fe, B:138:0x0303, B:140:0x032c, B:141:0x0331, B:143:0x0337, B:144:0x033e, B:146:0x0344, B:148:0x037a, B:149:0x0382, B:150:0x038c, B:151:0x03a2, B:117:0x02a2, B:119:0x02a6, B:121:0x02b2, B:122:0x02bc, B:163:0x0436, B:165:0x043a, B:166:0x043d, B:57:0x00c2, B:59:0x00c8, B:61:0x00ce), top: B:188:0x00be, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean on(boolean z) {
        log("on");
        if (this.SURVEY_MODE_ENABLE) {
            String str = getContext().getPackageManager().getPackagesForUid(Binder.getCallingUid())[0];
            String str2 = "";
            if (!"com.sec.android.app.fm".equals(str)) {
                try {
                    str2 = this.mContext.getPackageManager().getPackageInfo(str, 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("FMRadioService", "NameNotFoundException: " + e);
                }
                SamsungAnalyticsRunnable samsungAnalyticsRunnable = new SamsungAnalyticsRunnable(str, str2);
                this.mSamsungAnalyticsRunnable = samsungAnalyticsRunnable;
                this.mHandlerSA.post(samsungAnalyticsRunnable);
            }
        }
        log("SamsungAnalytics survey mode is not enable");
        if (this.mIsHeadsetPlugged && !this.mOnProgress) {
            if (this.mIsTvOutPlugged) {
                return false;
            }
            if (this.mAirPlaneEnabled) {
                return false;
            }
            registerBatteryListener();
            if (this.mIsBatteryLow) {
                return false;
            }
            try {
            } catch (Exception e2) {
                Log.e("FMRadioService", "Exception in getCallStateForSubscription() : " + e2);
            }
            if ((this.mTelephonyManager.getCallStateForSubscription() == 1 && !isDNDEnable()) || this.mTelephonyManager.getCallStateForSubscription() == 2) {
                if (this.mIsTransientPaused) {
                    this.mNeedToResumeFM = true;
                }
                return false;
            }
            if (this.alarmTTSPlay) {
                return false;
            }
            if (this.mIsOn) {
                return true;
            }
            if (!z) {
                try {
                    if (!this.mIsTransientPaused) {
                        if (!isFmTestApp() && !isCTSTestApp()) {
                            log("AudioFocusListener registered");
                            this.mAudioManager.requestAudioFocus(new AudioFocusRequest.Builder(1).setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(AudioManager.semGetStreamType(1)).semAddAudioTag("FM_RADIO").semAddAudioTag("NO_FADEOUT_FROM_AUDIOFOCUS").build()).setOnAudioFocusChangeListener(this.mAudioFocusListener).build());
                        } else {
                            log("AudioFocusListener : skip the requestAudioFocus");
                        }
                    }
                } catch (Exception e3) {
                    Log.e("FMRadioService", "Exception in on() : " + e3);
                    if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                        setFMAudioPath(false);
                    }
                    this.mIsOn = false;
                    this.mOnProgress = false;
                    this.mIsFMAudioPathOn = false;
                    log("on is failed by exception :: remove audiofocus ");
                    this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                    releaseWakeLock();
                    unRegisterBatteryListener();
                    return false;
                }
            }
            for (int i = 0; i < 50 && SemSystemProperties.getInt("service.media.dmb", 0) > 0; i++) {
                log("DMB enabled - waiting for DMB is closed");
                wait(50L);
            }
            this.mOnProgress = true;
            if (this.mIsExternalChipset) {
                log("on() mIsExternalChipset " + this.mIsExternalChipset);
                if (this.mAudioManager.semGetRadioOutputPath() == 2) {
                    this.mPlayerExternalChipset.setRecordMode(true);
                    this.mIsOn = this.mPlayerExternalChipset.isOn();
                } else {
                    this.mIsOn = this.mPlayerExternalChipset.on();
                }
                wait(20L);
                log("on state mPlayerExternalChipset " + this.mIsOn);
                if (!this.mIsOn) {
                    setFMAudioPath(false);
                    this.mOnProgress = false;
                    return false;
                }
                if (FMRadioServiceFeature.CHIP_VENDOR == 5) {
                    log("ext chip scan parameters setting");
                    this.mPlayerExternalChipset.setRssiThreshold(this.mRssi_th);
                    this.mPlayerExternalChipset.setSeekDC(this.mRichwave_seekDC);
                    this.mPlayerExternalChipset.setSeekQA(this.mRichwave_seekQA);
                }
                this.mPlayerExternalChipset.setBand(this.mBand);
                this.mPlayerExternalChipset.setChannelSpacing(this.mChannelSpacing);
                wait(50L);
                setFMAudioPath(true);
                this.mOnProgress = false;
                notifyEvent(5, null);
                registerBikeModeObserver();
                registerAvrcpModeObserver();
                registerAlarmListener();
                registerAllSoundOffListener();
                registerDNDStatusChangedListener();
                registerTelephonyListener();
                InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
                boolean z2 = this.mIsOn;
                if (z2 != this.mIsSetWakeKey && inputManager != null) {
                    try {
                        inputManager.semSetWakeKeyDynamically("com.sec.android.app.fm", z2, VOLUME_UP_DOWN);
                    } catch (SecurityException e4) {
                        log("Exception in semSetWakeKeyDynamically(): " + e4.toString());
                    }
                    this.mIsSetWakeKey = this.mIsOn;
                }
                this.mIsForcestop = false;
                return true;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                if (this.mPlayerNative.preInitialize() > 0) {
                    setFMAudioPath(true);
                    int iSemGetRadioOutputPath = this.mAudioManager.semGetRadioOutputPath();
                    if (DEBUGGABLE) {
                        log("OnAudioFocusChangeListener switch on mNeedResumeToFreq:" + this.mNeedResumeToFreq + "setOutputPath = " + iSemGetRadioOutputPath);
                    } else {
                        log("OnAudioFocusChangeListener switch setOutputPath = " + iSemGetRadioOutputPath);
                    }
                    this.mAudioManager.semSetRadioOutputPath(iSemGetRadioOutputPath);
                    wait(200L);
                } else {
                    log("FM preInitialize() failed");
                    this.mOnProgress = false;
                    return false;
                }
            }
            if (this.mPlayerNative.on() > 0) {
                log("on returned from native");
                this.mOnProgress = false;
                this.mIsOn = true;
                mute(true);
                if (!this.mIsHeadsetPlugged) {
                    offInternal(2, true);
                    return false;
                }
                notifyEvent(5, null);
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    if (this.mIsSupportSoftmute && isPathSupportSoftmute(this.mAudioManager.semGetRadioOutputPath())) {
                        log("set softmute : true");
                        setSoftmute(true);
                    } else {
                        log("set softmute : false");
                        setSoftmute(false);
                    }
                }
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    setSINRThreshold(this.mSnr_th);
                    setSearchAlgoType(this.mAlgo_type);
                    setSINRFirstStage(this.mSnr_th_2);
                    setRMSSIFirstStage(this.mRssi_th);
                    setOnChannelThreshold(this.mCnt_th);
                    setOffChannelThreshold(this.mCnt_th_2);
                    setSINRSamples(this.mRssi_th_2);
                    setCFOTh12(this.mCf0_th12);
                    setAFRMSSIThreshold(this.mAfRmssith_th);
                    setAFRMSSISamples(this.mAfRmssisampleCnt_th);
                    setGoodChannelRMSSIThreshold(this.mgoodChrmssi_th);
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                    this.mPlayerNative.setRSSI_th(this.mRssi_th);
                    this.mPlayerNative.setSeekDC(this.mRichwave_seekDC);
                    this.mPlayerNative.setSeekQA(this.mRichwave_seekQA);
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                    this.mPlayerNative.setRSSI_th(this.mRssi_th);
                    this.mPlayerNative.setFrequencyOffsetThreshold(this.mFreqOffset_th);
                    this.mPlayerNative.setNoisePowerThreshold(this.mNoisePwr_th);
                    this.mPlayerNative.setPilotPowerThreshold(this.mPilotPwr_th);
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mPlayerNative.setRSSI_th(this.mRssi_th);
                    this.mPlayerNative.setIFCount1(this.mSlsi_ifcount1);
                    this.mPlayerNative.setIFCount2(this.mSlsi_ifcount2);
                    this.mPlayerNative.setStereo();
                    this.mPlayerNative.setSoftStereoBlendCoeff(this.mSlsi_blendcoeff);
                    long j = this.mSlsi_softmutecoeff;
                    if (j != -1) {
                        this.mPlayerNative.setSoftMuteCoeff(j);
                    }
                    long j2 = this.mSlsi_softstereoblendref;
                    if (j2 != 0) {
                        this.mPlayerNative.setSoftStereoBlendRef(j2);
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mPlayerNative.setSeekRSSI(this.mRssi_th);
                    this.mPlayerNative.setSeekDesenseRSSI(this.mMtk_seekdesenserssi);
                    this.mPlayerNative.setSeekSMG(this.mMtk_seeksmg);
                    this.mPlayerNative.setSoftmute_th(this.mSoftmute_th);
                    this.mPlayerNative.setBlendRSSI_th(this.mMtk_blendrssi_th);
                    this.mPlayerNative.setBlendPAMD_th(this.mMtk_blendpamd_th);
                    this.mPlayerNative.setATJ(this.mMtk_ATJ_config);
                    if (this.mMtkSupportSetChipVolume) {
                        this.mPlayerNative.setVolume(this.mMtkChipVolume);
                    }
                } else {
                    setSignalSetting(this.mRssi_th, this.mSnr_th, this.mCnt_th);
                }
                setBand(this.mBand);
                setChannelSpacing(this.mChannelSpacing);
                setDEConstant(this.mDEConstant);
                registerBikeModeObserver();
                registerAvrcpModeObserver();
                registerAlarmListener();
                registerAllSoundOffListener();
                registerDNDStatusChangedListener();
                registerTelephonyListener();
                InputManager inputManager2 = (InputManager) this.mContext.getSystemService("input");
                boolean z3 = this.mIsOn;
                if (z3 != this.mIsSetWakeKey && inputManager2 != null) {
                    try {
                        inputManager2.semSetWakeKeyDynamically("com.sec.android.app.fm", z3, VOLUME_UP_DOWN);
                    } catch (SecurityException e5) {
                        log("Exception in semSetWakeKeyDynamically(): " + e5.toString());
                    }
                    this.mIsSetWakeKey = this.mIsOn;
                }
                this.mIsForcestop = false;
                return true;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                setFMAudioPath(false);
            }
            this.mOnProgress = false;
            this.mIsFMAudioPathOn = false;
            this.mIsOn = false;
            log("on is failed :: remove audiofocus ");
            this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
            releaseWakeLock();
            return false;
        }
        return false;
    }

    private void registerTelephonyListener() {
        if (this.mIsPhoneStateListenerRegistered) {
            log("listner already registered");
            return;
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mTelephonyManager.listen(this.mPhoneListener, 32);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            this.mIsPhoneStateListenerRegistered = true;
            log("registering telephony listener..");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    private void unRegisterTelephonyListener() {
        if (!this.mIsPhoneStateListenerRegistered) {
            log("listner is not registered");
            return;
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mTelephonyManager.listen(this.mPhoneListener, 0);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            this.mIsPhoneStateListenerRegistered = false;
            log("unRegisterTelephonyListener ..");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x0071 A[Catch: NullPointerException -> 0x00b9, TryCatch #0 {NullPointerException -> 0x00b9, blocks: (B:21:0x0052, B:23:0x0056, B:25:0x005f, B:27:0x0063, B:28:0x0069, B:35:0x0085, B:37:0x008d, B:40:0x0094, B:43:0x009b, B:45:0x009f, B:47:0x00a3, B:49:0x00a7, B:51:0x00ab, B:53:0x00b5, B:29:0x0071, B:31:0x0075, B:32:0x007c, B:34:0x0080), top: B:58:0x0052 }] */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setVolume(long j) {
        log("set chipset Volume : " + j);
        if (this.mIsOn) {
            if (this.mScanProgress) {
                log("setVolume :: unset on ScanProgress");
                return;
            }
            if (j < 0 || j > 15) {
                return;
            }
            int iSemGetRadioOutputPath = this.mAudioManager.semGetRadioOutputPath();
            boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
            this.mAvrcpMode = z;
            if (z && iSemGetRadioOutputPath == 8) {
                try {
                    if (FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
                        log("Avrcp on");
                        if (!this.isRecording) {
                            if (!this.mIsExternalChipset) {
                                this.mPlayerNative.setVolume(15L);
                            } else {
                                this.mPlayerExternalChipset.setVolume(15);
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    Log.e("FMRadioService", "NullPointerException in setVolume() : " + e);
                    return;
                }
            } else if (this.mIsExternalChipset) {
                this.mPlayerExternalChipset.setVolume((int) j);
            } else if (FMRadioServiceFeature.CHIP_VENDOR != 8) {
                this.mPlayerNative.setVolume(j);
            }
            this.mResumeVol = j;
            if (!isAllSoundOff() && !isDNDEnable()) {
                if (FMRadioServiceFeature.CHIP_VENDOR != 3) {
                    if (j <= 0) {
                        if (this.mIsMute) {
                            return;
                        }
                        mute(true);
                        return;
                    } else {
                        if (this.mIsMute) {
                            mute(false);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            log("setVolume :: AllSoundOff or DND is enabled. So FMRadio is muted.");
            if (this.mIsMute) {
                return;
            }
            mute(true);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getVolume() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getVolume();
        }
        return this.mPlayerNative.getVolume();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00ba  */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setSpeakerOn(boolean z) throws InterruptedException {
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
            } else {
                log("set softmute : false");
                setSoftmute(false);
            }
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
        int iSemGetRadioOutputPath = this.mAudioManager.semGetRadioOutputPath();
        boolean z2 = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z2;
        if (z2 && iSemGetRadioOutputPath == 8 && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
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
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
                log("Lock is released");
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private void acquireWakeLock() {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
                log("Lock is held");
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isOn() {
        return this.mIsOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean offInternal(int i, boolean z) {
        if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
            cancelSeek();
            try {
                wait(10L);
            } catch (InterruptedException e) {
                Log.e("FMRadioService", "InterruptedException in wait() : " + e);
            }
            log("offInternal :: reasonCode=" + i);
            this.mIsTransientDuck = false;
            try {
                if (this.mIsOn) {
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
        log("offInternal :: reasonCode=" + i);
        this.mIsTransientDuck = false;
        if (this.mIsOn) {
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
                            long jLongValue = obj != null ? ((Long) obj).longValue() : 0L;
                            if (DEBUGGABLE) {
                                log("notifying :EVENT_CHANNEL_FOUND to : listener -->" + size + " : with freq:" + jLongValue + "-->" + this.mListeners.get(size).mListener.asBinder());
                            }
                            this.mListeners.get(size).mListener.onChannelFound(jLongValue);
                            continue;
                        case 2:
                            log("notifying :EVENT_SCAN_STARTED to : listener -->" + size + " :" + this.mListeners.get(size).mListener.asBinder());
                            this.mListeners.get(size).mListener.onScanStarted();
                            continue;
                        case 3:
                            if (obj != null) {
                                long[] jArrConvertToPrimitives = convertToPrimitives((Long[]) obj);
                                log("notifying :EVENT_SCAN_FINISHED to : listener -->" + size + " : with data array:" + (jArrConvertToPrimitives != null ? jArrConvertToPrimitives.length : 0) + "-->" + this.mListeners.get(size).mListener.asBinder());
                                this.mListeners.get(size).mListener.onScanFinished(jArrConvertToPrimitives);
                                continue;
                            } else {
                                log("notifying : EVENT_SCAN_FINISHED : data is null !!!");
                            }
                        case 4:
                            if (obj != null) {
                                long[] jArrConvertToPrimitives2 = convertToPrimitives((Long[]) obj);
                                log("notifying :EVENT_SCAN_STOPPED to : listener -->" + size + " : with data array:" + (jArrConvertToPrimitives2 != null ? jArrConvertToPrimitives2.length : 0) + "-->" + this.mListeners.get(size).mListener.asBinder());
                                this.mListeners.get(size).mListener.onScanStopped(jArrConvertToPrimitives2);
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
                                long jLongValue2 = ((Long) obj).longValue();
                                curFreq = jLongValue2;
                                if (DEBUGGABLE) {
                                    log("notifying :EVENT_TUNE to : listener -->" + size + " : with data array:" + jLongValue2 + "-->" + this.mListeners.get(size).mListener.asBinder());
                                }
                                this.mListeners.get(size).mListener.onTuned(jLongValue2);
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
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x012d  */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setIntegerTunningParameter(String str, int i) {
        if (isValidPackage()) {
            log("setIntegerTunningParameter:  parameterName- " + str + "  value:- " + i);
            if (str == null) {
                log("setIntegerTunningParameter:  parameterName is null. So do nothing");
            }
            str.hashCode();
            switch (str) {
                case "RSSI_th":
                    setRSSI_th(i);
                    break;
                case "SNR_th":
                    setSNR_th(i);
                    break;
                case "SkipTuningValue":
                    SkipTuning_Value();
                    break;
                case "Cnt_th":
                    setCnt_th(i);
                    break;
                default:
                    if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                        str.hashCode();
                        if (!str.equals(PARAMETER_SEEK_DC)) {
                            if (str.equals(PARAMETER_SEEK_QA)) {
                                setSeekQA(i);
                                break;
                            } else {
                                log("setIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                            }
                        } else {
                            setSeekDC(i);
                            break;
                        }
                    } else {
                        char c = 6;
                        if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                            str.hashCode();
                            switch (str.hashCode()) {
                                case -2006318336:
                                    if (!str.equals(PARAMETER_AFRMSSI_SAMPLES)) {
                                        c = 65535;
                                        break;
                                    } else {
                                        c = 0;
                                        break;
                                    }
                                case -1620552413:
                                    if (str.equals(PARAMETER_GOOD_CH_RMSSI_TH)) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case -1471559147:
                                    if (str.equals(PARAMETER_SEARCH_ALGO_TYPE)) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case -1395228053:
                                    if (str.equals(PARAMETER_BLEND_SINR)) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case -1271273368:
                                    if (str.equals(PARAMETER_SINR_FIRST_STAGE)) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                                case -1237360035:
                                    if (str.equals(PARAMETER_SECOND_CNT_TH)) {
                                        c = 5;
                                        break;
                                    }
                                    break;
                                case -1075284457:
                                    if (!str.equals(PARAMETER_OFF_CHANNEL_TH)) {
                                    }
                                    break;
                                case -728425457:
                                    if (str.equals(PARAMETER_SECOND_RSSI_TH)) {
                                        c = 7;
                                        break;
                                    }
                                    break;
                                case -303196099:
                                    if (str.equals(PARAMETER_BLEND_RMSSI)) {
                                        c = '\b';
                                        break;
                                    }
                                    break;
                                case 612887239:
                                    if (str.equals(PARAMETER_ON_CHANNEL_TH)) {
                                        c = '\t';
                                        break;
                                    }
                                    break;
                                case 879837199:
                                    if (str.equals(PARAMETER_SINR_SAMPLES)) {
                                        c = '\n';
                                        break;
                                    }
                                    break;
                                case 1051458289:
                                    if (str.equals(PARAMETER_SINR_TH)) {
                                        c = 11;
                                        break;
                                    }
                                    break;
                                case 1412807169:
                                    if (str.equals(PARAMETER_CFO_TH)) {
                                        c = '\f';
                                        break;
                                    }
                                    break;
                                case 1569063695:
                                    if (str.equals(PARAMETER_SECOND_SNR_TH)) {
                                        c = '\r';
                                        break;
                                    }
                                    break;
                                case 1826319004:
                                    if (str.equals(PARAMETER_RMSSI_FIRST_STAGE)) {
                                        c = 14;
                                        break;
                                    }
                                    break;
                                case 2004862370:
                                    if (str.equals(PARAMETER_AFRMSSI_TH)) {
                                        c = 15;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    setAFRMSSISamples(i);
                                    break;
                                case 1:
                                    setGoodChannelRMSSIThreshold(i);
                                    break;
                                case 2:
                                    setSearchAlgoType(i);
                                    break;
                                case 3:
                                    setBlendSinr(i);
                                    break;
                                case 4:
                                    setSINRFirstStage(i);
                                    break;
                                case 5:
                                    setCnt_th_2(i);
                                    break;
                                case 6:
                                    if (!this.mIsExternalChipset) {
                                        this.mPlayerNative.setOffChannelThreshold(i);
                                        break;
                                    }
                                    break;
                                case 7:
                                    setRSSI_th_2(i);
                                    break;
                                case '\b':
                                    setBlendRmssi(i);
                                    break;
                                case '\t':
                                    setOnChannelThreshold(i);
                                    break;
                                case '\n':
                                    setSINRSamples(i);
                                    break;
                                case 11:
                                    setSINRThreshold(i);
                                    break;
                                case '\f':
                                    setCFOTh12(i);
                                    break;
                                case '\r':
                                    setSNR_th_2(i);
                                    break;
                                case 14:
                                    setRMSSIFirstStage(i);
                                    break;
                                case 15:
                                    setAFRMSSIThreshold(i);
                                    break;
                                default:
                                    log("setIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                    break;
                            }
                        } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                            str.hashCode();
                            switch (str) {
                                case "NoisePower_th":
                                    setNoisePowerThreshold(i);
                                    break;
                                case "PilotPower_th":
                                    setPilotPowerThreshold(i);
                                    break;
                                case "FrequencyOffset_th":
                                    setFrequencyOffsetThreshold(i);
                                    break;
                                default:
                                    log("setIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                    break;
                            }
                        } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                            str.hashCode();
                            if (!str.equals(PARAMETER_IF_COUNT_1)) {
                                if (str.equals(PARAMETER_IF_COUNT_2)) {
                                    setIFCount2(i);
                                    break;
                                } else {
                                    log("setIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                    break;
                                }
                            } else {
                                setIFCount1(i);
                                break;
                            }
                        } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                            str.hashCode();
                            switch (str) {
                                case "SeekDesenseRSSI":
                                    setSeekDesenseRSSI(i);
                                    break;
                                case "Softmute_th":
                                    setSoftmute_th(i);
                                    break;
                                case "SeekSMG":
                                    setSeekSMG(i);
                                    break;
                                case "BlendRSSI_th":
                                    setBlendRSSI_th(i);
                                    break;
                                case "ATJCofig":
                                    setATJ(i);
                                    break;
                                case "BlendPAMD_th":
                                    setBlendPAMD_th(i);
                                    break;
                                default:
                                    log("setIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                    break;
                            }
                        } else {
                            log("setIntegerTunningParameter() : this parameter is not support yet - " + str + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getIntegerTunningParameter(String str, int i) {
        char c;
        log("getIntegerTunningParameter: parameterName- " + str);
        if (str == null) {
            log("getIntegerTunningParameter:  parameterName is null. So do nothing");
            return i;
        }
        str.hashCode();
        c = 2;
        switch (str) {
            case "RSSI_th":
                break;
            case "SNR_th":
                break;
            case "Cnt_th":
                break;
            default:
                if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                    str.hashCode();
                    if (!str.equals(PARAMETER_SEEK_DC)) {
                        if (!str.equals(PARAMETER_SEEK_QA)) {
                            log("getIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    str.hashCode();
                    switch (str.hashCode()) {
                        case -2006318336:
                            if (!str.equals(PARAMETER_AFRMSSI_SAMPLES)) {
                                c = 65535;
                                break;
                            } else {
                                c = 0;
                                break;
                            }
                        case -1620552413:
                            if (str.equals(PARAMETER_GOOD_CH_RMSSI_TH)) {
                                c = 1;
                                break;
                            }
                            break;
                        case -1471559147:
                            if (!str.equals(PARAMETER_SEARCH_ALGO_TYPE)) {
                            }
                            break;
                        case -1395228053:
                            if (str.equals(PARAMETER_BLEND_SINR)) {
                                c = 3;
                                break;
                            }
                            break;
                        case -1271273368:
                            if (str.equals(PARAMETER_SINR_FIRST_STAGE)) {
                                c = 4;
                                break;
                            }
                            break;
                        case -1237360035:
                            if (str.equals(PARAMETER_SECOND_CNT_TH)) {
                                c = 5;
                                break;
                            }
                            break;
                        case -1075284457:
                            if (str.equals(PARAMETER_OFF_CHANNEL_TH)) {
                                c = 6;
                                break;
                            }
                            break;
                        case -728425457:
                            if (str.equals(PARAMETER_SECOND_RSSI_TH)) {
                                c = 7;
                                break;
                            }
                            break;
                        case -303196099:
                            if (str.equals(PARAMETER_BLEND_RMSSI)) {
                                c = '\b';
                                break;
                            }
                            break;
                        case 612887239:
                            if (str.equals(PARAMETER_ON_CHANNEL_TH)) {
                                c = '\t';
                                break;
                            }
                            break;
                        case 879837199:
                            if (str.equals(PARAMETER_SINR_SAMPLES)) {
                                c = '\n';
                                break;
                            }
                            break;
                        case 1051458289:
                            if (str.equals(PARAMETER_SINR_TH)) {
                                c = 11;
                                break;
                            }
                            break;
                        case 1412807169:
                            if (str.equals(PARAMETER_CFO_TH)) {
                                c = '\f';
                                break;
                            }
                            break;
                        case 1569063695:
                            if (str.equals(PARAMETER_SECOND_SNR_TH)) {
                                c = '\r';
                                break;
                            }
                            break;
                        case 1826319004:
                            if (str.equals(PARAMETER_RMSSI_FIRST_STAGE)) {
                                c = 14;
                                break;
                            }
                            break;
                        case 2004862370:
                            if (str.equals(PARAMETER_AFRMSSI_TH)) {
                                c = 15;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case '\b':
                            break;
                        case '\t':
                            break;
                        case '\n':
                            break;
                        case 11:
                            break;
                        case '\f':
                            break;
                        case '\r':
                            break;
                        case 14:
                            break;
                        case 15:
                            break;
                        default:
                            log("getIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                    str.hashCode();
                    switch (str.hashCode()) {
                        case -2033938168:
                            if (!str.equals(PARAMETER_NOISE_POWER_TH)) {
                                c = 65535;
                                break;
                            } else {
                                c = 0;
                                break;
                            }
                        case -752119130:
                            if (str.equals(PARAMETER_PILOT_POWER_TH)) {
                                c = 1;
                                break;
                            }
                            break;
                        case 68091844:
                            if (!str.equals(PARAMETER_FREQUENCY_OFFSET_TH)) {
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        default:
                            log("getIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    str.hashCode();
                    if (!str.equals(PARAMETER_IF_COUNT_1)) {
                        if (!str.equals(PARAMETER_IF_COUNT_2)) {
                            log("getIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    str.hashCode();
                    switch (str.hashCode()) {
                        case -1731989524:
                            if (!str.equals(PARAMETER_SEEK_DESENSE_RSSI)) {
                                c = 65535;
                                break;
                            } else {
                                c = 0;
                                break;
                            }
                        case -1416966448:
                            if (str.equals(PARAMETER_SOFTMUTE_TH)) {
                                c = 1;
                                break;
                            }
                            break;
                        case -658516075:
                            if (!str.equals(PARAMETER_SEEK_SMG)) {
                            }
                            break;
                        case -88842741:
                            if (str.equals(PARAMETER_BLEND_RSSI_TH)) {
                                c = 3;
                                break;
                            }
                            break;
                        case 1038261217:
                            if (str.equals(PARAMETER_ATJ_CONFIG)) {
                                c = 4;
                                break;
                            }
                            break;
                        case 1910102394:
                            if (str.equals(PARAMETER_BLEND_PAMD_TH)) {
                                c = 5;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        default:
                            log("setIntegerTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                    }
                } else {
                    log("getIntegerTunningParameter() : this parameter is not support yet - " + str + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
                }
                break;
        }
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0094  */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setLongTunningParameter(String str, long j) {
        if (isValidPackage()) {
            log("long setLongTunningParameter: parameterName - " + str + "  value: " + j);
            if (str == null) {
                log("setLongTunningParameter:  parameterName is null. So do nothing");
            }
            str.hashCode();
            char c = 2;
            switch (str) {
                case "DEConstant":
                    setDEConstant(j);
                    break;
                case "SeekSNR":
                    setSeekSNR(j);
                    break;
                case "SeekRSSI":
                    setSeekRSSI(j);
                    break;
                default:
                    log("setLongTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                str.hashCode();
                switch (str.hashCode()) {
                    case -681786198:
                        if (!str.equals(PARAMETER_SOFT_STEREO_BLEND_COEFF)) {
                            c = 65535;
                            break;
                        } else {
                            c = 0;
                            break;
                        }
                    case 1746788740:
                        if (str.equals(PARAMETER_SOFT_STEREO_BLEND_REF)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1777837110:
                        if (!str.equals(PARAMETER_SOFTMUTE_COEFF)) {
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        setSoftStereoBlendCoeff(j);
                        break;
                    case 1:
                        setSoftStereoBlendRef(j);
                        break;
                    case 2:
                        setSoftMuteCoeff(j);
                        break;
                    default:
                        log("setLongTunningParameter() : invalid parameterName - " + str + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                        break;
                }
            }
        }
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

        /* JADX WARN: Code restructure failed: missing block: B:93:0x01c7, code lost:
        
            r1 = r26.this$0;
            r1.notifyEvent(3, r1.mScanChannelList.toArray(new java.lang.Long[0]));
            java.lang.Thread.sleep(r20);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void doScan() throws InterruptedException {
            long j;
            long j2;
            long j3;
            long j4 = 90000;
            long j5 = 108000;
            long j6 = 87500;
            if (FMRadioService.this.mIsExternalChipset) {
                if (FMRadioService.this.mBand == 0) {
                    FMRadioService.this.mPlayerExternalChipset.tune(8750);
                }
                if (FMRadioService.this.mBand == 1 || FMRadioService.this.mBand == 2) {
                    FMRadioService.this.mPlayerExternalChipset.tune(7600);
                }
                if (FMRadioService.this.mBand == 3) {
                    FMRadioService.this.mPlayerExternalChipset.tune(6400);
                }
            } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                if (FMRadioService.this.mBand == 3) {
                    FMRadioService.this.mPlayerNative.tune(90000L);
                } else {
                    FMRadioService.this.mPlayerNative.tune(108000L);
                }
            } else if (FMRadioService.this.mBand == 1) {
                FMRadioService.this.mPlayerNative.tune(87500L);
            } else {
                FMRadioService.this.mPlayerNative.tune(76000L);
            }
            if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9 || FMRadioService.this.mIsExternalChipset) {
                FMRadioService.this.mPreviousFoundFreq = 0L;
                FMRadioService.this.mCurrentFoundFreq = 0L;
            }
            if (FMRadioService.this.mWaitPidDuringScanning && !FMRadioService.this.mIsExternalChipset) {
                FMRadioService.this.mPlayerNative.setScanning(true);
            }
            while (true) {
                if (!FMRadioService.this.mScanProgress) {
                    break;
                }
                long j7 = j4;
                long jSearchAll = FMRadioService.this.searchAll();
                if (FMRadioService.DEBUGGABLE) {
                    j = j5;
                    FMRadioService.log("Found channel :" + jSearchAll);
                } else {
                    j = j5;
                }
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9 || FMRadioService.this.mIsExternalChipset) {
                    j2 = 20;
                } else {
                    j2 = 20;
                    if (FMRadioService.this.mScanChannelList.contains(Long.valueOf(jSearchAll))) {
                        if (FMRadioService.DEBUGGABLE) {
                            FMRadioService.log("Duplicate channel :" + jSearchAll);
                        }
                        FMRadioService fMRadioService = FMRadioService.this;
                        fMRadioService.notifyEvent(3, fMRadioService.mScanChannelList.toArray(new Long[0]));
                        Thread.sleep(20L);
                    }
                }
                if (jSearchAll > 0) {
                    if (FMRadioService.this.mScanFreq <= 0) {
                        FMRadioService.this.mScanFreq = jSearchAll;
                    }
                    if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9 || FMRadioService.this.mIsExternalChipset) {
                        j3 = j6;
                        FMRadioService.this.mCurrentFoundFreq = jSearchAll;
                        if (FMRadioService.DEBUGGABLE) {
                            FMRadioService.log("scanning current and prev freq:" + FMRadioService.this.mCurrentFoundFreq + ", " + FMRadioService.this.mPreviousFoundFreq);
                        }
                        if (FMRadioService.this.mPreviousFoundFreq >= FMRadioService.this.mCurrentFoundFreq) {
                            FMRadioService.log("scanning finish");
                            if (FMRadioService.this.mCurrentFoundFreq == j3) {
                                FMRadioService.this.mScanChannelList.add(Long.valueOf(jSearchAll));
                                FMRadioService.this.notifyEvent(1, Long.valueOf(jSearchAll));
                            }
                            if (FMRadioService.this.mIsExternalChipset) {
                                FMRadioService.this.mPlayerExternalChipset.stopNotifyThread(true);
                            }
                            FMRadioService fMRadioService2 = FMRadioService.this;
                            fMRadioService2.notifyEvent(3, fMRadioService2.mScanChannelList.toArray(new Long[0]));
                            Thread.sleep(j2);
                        } else {
                            if (FMRadioService.this.mScanProgress) {
                                FMRadioService.log("scanning found channel");
                                FMRadioService fMRadioService3 = FMRadioService.this;
                                fMRadioService3.mPreviousFoundFreq = fMRadioService3.mCurrentFoundFreq;
                                FMRadioService.this.mScanChannelList.add(Long.valueOf(jSearchAll));
                                FMRadioService.this.notifyEvent(1, Long.valueOf(jSearchAll));
                                if (FMRadioService.this.mWaitPidDuringScanning && FMRadioService.this.mScanThread != null) {
                                    synchronized (FMRadioService.this.mScanThread) {
                                        FMRadioService.this.mScanThread.wait(250L);
                                    }
                                }
                            } else {
                                continue;
                            }
                            j4 = j7;
                            j5 = j;
                            j6 = j3;
                        }
                    } else {
                        if (FMRadioService.this.mScanProgress) {
                            j3 = j6;
                            FMRadioService.this.mScanChannelList.add(Long.valueOf(jSearchAll));
                            FMRadioService.this.notifyEvent(1, Long.valueOf(jSearchAll));
                            if (FMRadioService.this.mWaitPidDuringScanning && FMRadioService.this.mScanThread != null) {
                                synchronized (FMRadioService.this.mScanThread) {
                                    FMRadioService.this.mScanThread.wait(250L);
                                }
                            }
                        } else {
                            j3 = j6;
                        }
                        if (((FMRadioService.this.mBand == 1 || FMRadioService.this.mBand == 2) && jSearchAll == j) || (FMRadioService.this.mBand == 3 && jSearchAll == j7)) {
                            break;
                        }
                        j4 = j7;
                        j5 = j;
                        j6 = j3;
                    }
                } else {
                    FMRadioService.log("Testmode Skipp value : " + FMRadioService.this.mIsSkipTunigVal);
                    if (FMRadioService.this.mIsExternalChipset) {
                        FMRadioService.this.mPlayerExternalChipset.stopNotifyThread(true);
                    }
                    FMRadioService fMRadioService4 = FMRadioService.this;
                    fMRadioService4.notifyEvent(3, fMRadioService4.mScanChannelList.toArray(new Long[0]));
                    Thread.sleep(j2);
                }
            }
            if (!FMRadioService.this.mWaitPidDuringScanning || FMRadioService.this.mIsExternalChipset) {
                return;
            }
            FMRadioService.this.mPlayerNative.setScanning(false);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            PowerManager.WakeLock wakeLockNewWakeLock = FMRadioService.this.mPowerManager.newWakeLock(536870913, "FMRadio Service Scan Thread");
            wakeLockNewWakeLock.acquire();
            FMRadioService.log("Scan thread gets the dimmed screen lock");
            try {
                try {
                    FMRadioService.log("Scanning Thread started...");
                    FMRadioService.this.notifyEvent(2, null);
                    if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                        if (FMRadioService.this.isUnMuteRadio()) {
                            FMRadioService.this.mAudioManager.setParameters(FMRadioService.audioMute);
                        }
                    } else if (FMRadioService.this.mIsExternalChipset) {
                        FMRadioService.this.mPlayerExternalChipset.muteOn();
                    } else {
                        FMRadioService.this.setFMAudioPath(false);
                    }
                    FMRadioService.log("Scanning Thread started... - Turning off FM");
                    FMRadioService fMRadioService = FMRadioService.this;
                    fMRadioService.mScanFreq = fMRadioService.getCurrentChannel();
                    if (FMRadioService.this.mScanChannelList == null) {
                        FMRadioService.this.mScanChannelList = new ArrayList();
                    } else {
                        FMRadioService.this.mScanChannelList.clear();
                    }
                    if (FMRadioService.this.mIsSkipTunigVal && !FMRadioService.this.mIsExternalChipset) {
                        FMRadioService fMRadioService2 = FMRadioService.this;
                        fMRadioService2.setSignalSetting(fMRadioService2.mRssi_th, FMRadioService.this.mSnr_th, FMRadioService.this.mCnt_th);
                        FMRadioService.log("first scan no block channel with " + FMRadioService.this.mRssi_th + FMRadioService.this.mSnr_th + FMRadioService.this.mCnt_th);
                    }
                    if (!FMRadioService.this.mIsExternalChipset || FMRadioService.this.mPlayerExternalChipset.startNotifyThread(true)) {
                        doScan();
                    } else {
                        if (FMRadioService.this.mScanChannelList == null) {
                            FMRadioService.this.mScanChannelList = new ArrayList();
                        } else {
                            FMRadioService.this.mScanChannelList.clear();
                        }
                        FMRadioService fMRadioService3 = FMRadioService.this;
                        fMRadioService3.notifyEvent(3, fMRadioService3.mScanChannelList.toArray(new Long[0]));
                    }
                    FMRadioService.this.mScanProgress = false;
                    FMRadioService.this.mScanThread = null;
                } catch (Exception e) {
                    Log.e("FMRadioService", "Exception in run() : " + e);
                    FMRadioService.this.mScanProgress = false;
                    FMRadioService.this.mScanThread = null;
                    if (wakeLockNewWakeLock.isHeld()) {
                    }
                }
                if (wakeLockNewWakeLock.isHeld()) {
                    wakeLockNewWakeLock.release();
                    FMRadioService.log("Scan thread released the dimmed screen lock");
                }
                FMRadioService.log("Scanning Thread work is done...");
            } catch (Throwable th) {
                FMRadioService.this.mScanProgress = false;
                FMRadioService.this.mScanThread = null;
                if (wakeLockNewWakeLock.isHeld()) {
                    wakeLockNewWakeLock.release();
                    FMRadioService.log("Scan thread released the dimmed screen lock");
                }
                throw th;
            }
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
    public void setDelay(long j) throws InterruptedException {
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
    public void sendInfoSamsungAnalytics(String str, String str2) throws JSONException {
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
