package com.android.server;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p002pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.p008tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.p009os.BatteryManager;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.PowerManager;
import android.p009os.SemSystemProperties;
import android.p009os.SystemClock;
import android.p009os.SystemProperties;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.FMPlayerNativeBase;
import com.samsung.android.audio.AudioConstants;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.fmradio.internal.IFMEventListener;
import com.samsung.android.media.fmradio.internal.IFMPlayer;
import com.samsung.android.share.SemShareConstants;
import com.samsung.android.transcode.constants.EncodeConstants;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
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
    private static boolean mIsTransientPaused;
    private static boolean mNeedToResumeFM;
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

    static {
        DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
        isFactoryBinary = "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"));
        curFreq = -1L;
        mIsTransientPaused = false;
        mNeedToResumeFM = false;
        mFMRadioServiceLock = new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAvrcpMode() {
        int type = this.mAudioManager.semGetRadioOutputPath();
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z;
        if (type == 8) {
            if (z && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
                log("Avrcp mode enabled!!!");
                if (!this.volumeLock) {
                    this.mPlayerNative.setVolume(15L);
                    return;
                }
                return;
            }
            log("Avrcp mode disabled");
            if (!this.volumeLock) {
                int current_stream_volume = this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
                log("current_stream_volume: " + current_stream_volume);
                setVolume(current_stream_volume);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkUsbExternalChipset(UsbDevice usbDevice) {
        if (usbDevice.getVendorId() == 1256) {
            if (usbDevice.getProductId() == 41044 || usbDevice.getProductId() == 41049 || usbDevice.getProductId() == 41051) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void checkUSBDeviceConnected(Context context) {
        log("checkUSBDeviceConnected");
        try {
            UsbManager mUsbManager = (UsbManager) context.getSystemService("usb");
            if (mUsbManager == null) {
                log("mUsbManager null");
                return;
            }
            Map<String, UsbDevice> devices = mUsbManager.getDeviceList();
            if (devices == null) {
                log("USB Device null");
                return;
            }
            for (UsbDevice usbDevice : devices.values()) {
                log("Headset getProductId : " + usbDevice.getProductId());
                log("Headset getVendorId : " + usbDevice.getVendorId());
                if (this.mIsExternalChipset && checkUsbExternalChipset(usbDevice)) {
                    this.mIsHeadsetPlugged = true;
                    this.mPlayerExternalChipset.init(usbDevice);
                }
            }
        } catch (NullPointerException e) {
            Log.m96e("FMRadioService", "NullPointerException in checkUSBDeviceConnected() : " + e);
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

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            FMRadioService.log("mAudioFocusHandler:mHandler(g.what=" + msg.what + ") is called");
            switch (msg.what) {
                case -3:
                case -2:
                case -1:
                case 1:
                    FMRadioService.log("mAudioFocusHandler:Fired  TIME = " + (SystemClock.uptimeMillis() / 1000));
                    FMRadioService.this.responedFocusEvent(msg.what);
                    break;
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
    public void responedFocusEvent(int focusEvent) {
        switch (focusEvent) {
            case -3:
                if (isOn()) {
                    if (this.volumeLock) {
                        log("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK - recoding O");
                        break;
                    } else {
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
                        break;
                    }
                }
                break;
            case -2:
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
                    this.mAudioFocusHandler.removeMessages(focusEvent);
                    this.mAudioFocusHandler.sendEmptyMessage(focusEvent);
                }
                mNeedToResumeFM = false;
                break;
            case -1:
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
                    break;
                } else if (this.mOnProgress) {
                    log("still FM on in progress");
                    this.mAudioFocusHandler.removeMessages(focusEvent);
                    this.mAudioFocusHandler.sendEmptyMessage(focusEvent);
                    break;
                }
                break;
            case 1:
                log("AUDIOFOCUS_GAIN ");
                if (this.mIsExternalChipset) {
                    setDelay(700L);
                }
                if (isOn() && this.mIsTransientDuck) {
                    mute(false);
                }
                this.mIsTransientDuck = false;
                if (!isOn() && this.mNeedResumeToFreq != -2 && !this.mIsForcestop) {
                    if (m233on(false)) {
                        if (mIsTransientPaused) {
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
                            mIsTransientPaused = false;
                        } else {
                            this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)), 0);
                        }
                        if (this.mNeedResumeToFreq <= 0) {
                            this.mNeedResumeToFreq = 87500L;
                        }
                        if (this.mIsExternalChipset) {
                            int freqExt = ((int) this.mNeedResumeToFreq) / 10;
                            this.mPlayerExternalChipset.tune(freqExt);
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
                        break;
                    } else if (!mNeedToResumeFM) {
                        log("Not able to resume FM player");
                        this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                        break;
                    }
                } else if (this.mOffProgress) {
                    log("still FM off in progress");
                    this.mAudioFocusHandler.removeMessages(focusEvent);
                    this.mAudioFocusHandler.sendEmptyMessage(focusEvent);
                    break;
                }
                break;
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
        if (audioManager != null) {
            int ringermode = audioManager.getRingerMode();
            if (ringermode == 2 && this.mAudioManager.isStreamMute(1) && this.mAudioManager.isStreamMute(5)) {
                this.mAudioManager.adjustStreamVolume(1, 100, 0);
                this.mAudioManager.adjustStreamVolume(5, 100, 0);
            }
        }
    }

    private void registerDNDStatusChangedListener() {
        IntentFilter intentDNDFilter = new IntentFilter();
        intentDNDFilter.addAction(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED);
        intentDNDFilter.addAction(NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED);
        this.mContext.registerReceiver(this.mDNDStatusReceiver, intentDNDFilter);
        log("registering DND Status change Listener");
    }

    private void unregisterDNDStatusChangedListener() {
        log("Unregistering DND Status change listner");
        this.mContext.unregisterReceiver(this.mDNDStatusReceiver);
    }

    private void registerAllSoundOffListener() {
        IntentFilter intentAllSoundOffFilter = new IntentFilter();
        intentAllSoundOffFilter.addAction("android.settings.ALL_SOUND_MUTE");
        this.mContext.registerReceiver(this.mAllSoundOffReceiver, intentAllSoundOffFilter);
        log("registering AllSoundOff listener");
    }

    private void unregisterAllSoundOffListener() {
        log("Unregistering AllSoundOff listener");
        this.mContext.unregisterReceiver(this.mAllSoundOffReceiver);
    }

    private void registerAlarmListener() {
        IntentFilter intentAlarmFilter = new IntentFilter();
        intentAlarmFilter.addAction(ACTINON_ALARM_PLAY);
        this.mContext.registerReceiver(this.mAlarmReceiver, intentAlarmFilter);
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

    private void readTuningParameters() {
        if ("".equals(FMRadioServiceFeature.FEATURE_SETLOCALTUNNING)) {
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
                return;
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
                String tempMtkChipVolume = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME");
                if (!"".equals(tempMtkChipVolume)) {
                    this.mMtkSupportSetChipVolume = true;
                    this.mMtkChipVolume = Integer.parseInt(tempMtkChipVolume);
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
        String[] Local_Tunning_vals = FMRadioServiceFeature.FEATURE_SETLOCALTUNNING.split(",");
        log("Tuning value size: " + Local_Tunning_vals.length);
        switch (Local_Tunning_vals.length) {
            case 1:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(Local_Tunning_vals[0]);
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
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mRichwave_seekDC = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_DC"));
                    this.mRichwave_seekQA = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_RICHWAVE_SEEK_QA"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mFreqOffset_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_FREQ_OFFSET"));
                    this.mNoisePwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_NOISE_PWR"));
                    this.mPilotPwr_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SPRD_PILOT_PWR"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT1"));
                    this.mSlsi_ifcount2 = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_IFCOUNT2"));
                    this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    String tempMtkChipVolume2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME");
                    if (!"".equals(tempMtkChipVolume2)) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(tempMtkChipVolume2);
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
                    this.mSnr_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mCnt_th = Integer.parseInt(Local_Tunning_vals[1]);
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
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mMtk_seeksmg = Integer.parseInt(Local_Tunning_vals[1]);
                    String tempMtkChipVolume3 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME");
                    if (!"".equals(tempMtkChipVolume3)) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(tempMtkChipVolume3);
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
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mRichwave_seekDC = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mRichwave_seekQA = Integer.parseInt(Local_Tunning_vals[2]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_SLSI_BLENDCOEF"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mMtk_seeksmg = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(Local_Tunning_vals[2]);
                    String tempMtkChipVolume4 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME");
                    if (!"".equals(tempMtkChipVolume4)) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(tempMtkChipVolume4);
                    }
                    this.mSoftmute_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH"));
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mSnr_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mIsSupportSoftmute = Boolean.parseBoolean(Local_Tunning_vals[1]);
                    this.mSoftmutePath = Local_Tunning_vals[2];
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
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSnr_th_2 = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mSnr_th = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mAlgo_type = Integer.parseInt(Local_Tunning_vals[3]);
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
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mFreqOffset_th = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mNoisePwr_th = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mPilotPwr_th = Integer.parseInt(Local_Tunning_vals[3]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(Local_Tunning_vals[3]);
                    break;
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mMtk_seeksmg = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mSoftmute_th = Integer.parseInt(Local_Tunning_vals[3]);
                    String tempMtkChipVolume5 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME");
                    if (!"".equals(tempMtkChipVolume5)) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(tempMtkChipVolume5);
                    }
                    this.mMtk_blendrssi_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDRSSI_TH"));
                    this.mMtk_blendpamd_th = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_BLENDPAMD_TH"));
                    break;
                }
                break;
            case 5:
                if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSnr_th_2 = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mSnr_th = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mAlgo_type = Integer.parseInt(Local_Tunning_vals[3]);
                    this.mgoodChrmssi_th = Integer.parseInt(Local_Tunning_vals[4]);
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
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(Local_Tunning_vals[3]);
                    this.mSlsi_softmutecoeff = Integer.parseInt(Local_Tunning_vals[4]);
                    break;
                }
                break;
            case 6:
                if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mMtk_seeksmg = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mMtk_seekdesenserssi = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mSoftmute_th = Integer.parseInt(Local_Tunning_vals[3]);
                    this.mMtk_blendrssi_th = Integer.parseInt(Local_Tunning_vals[4]);
                    this.mMtk_blendpamd_th = Integer.parseInt(Local_Tunning_vals[5]);
                    String tempMtkChipVolume6 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_CHIPVOLUME");
                    if (!"".equals(tempMtkChipVolume6)) {
                        this.mMtkSupportSetChipVolume = true;
                        this.mMtkChipVolume = Integer.parseInt(tempMtkChipVolume6);
                        break;
                    }
                } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    this.mRssi_th = Integer.parseInt(Local_Tunning_vals[0]);
                    this.mSlsi_ifcount1 = Integer.parseInt(Local_Tunning_vals[1]);
                    this.mSlsi_ifcount2 = Integer.parseInt(Local_Tunning_vals[2]);
                    this.mSlsi_blendcoeff = Integer.parseInt(Local_Tunning_vals[3]);
                    this.mSlsi_softmutecoeff = Integer.parseInt(Local_Tunning_vals[4]);
                    this.mSlsi_softstereoblendref = Integer.parseInt(Local_Tunning_vals[5]);
                    break;
                }
                break;
            default:
                log("Tuning value size: " + Local_Tunning_vals.length);
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
            switch (FMRadioServiceFeature.FEATURE_FREQUENCYSPACE) {
                case 50:
                    if (!this.mIsExternalChipset) {
                        this.mChannelSpacing = 5;
                        break;
                    } else {
                        this.mChannelSpacing = 2;
                        break;
                    }
                case 100:
                    if (!this.mIsExternalChipset) {
                        this.mChannelSpacing = 10;
                        break;
                    } else {
                        this.mChannelSpacing = 1;
                        break;
                    }
                default:
                    if (!this.mIsExternalChipset) {
                        this.mChannelSpacing = 10;
                        break;
                    } else {
                        this.mChannelSpacing = 1;
                        break;
                    }
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
            Log.m96e("FMRadioService", "Exception in readParametersForCurrentRegion() : " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueUpdate(int what, long delay) {
        log("queueUpdate(" + what + "," + delay + ") is called");
        if (what == 200) {
            log("queueUpdate ## VOLUME_FADEIN");
            this.mHandler.removeMessages(what);
        }
        this.mHandler.sendEmptyMessageDelayed(what, delay);
    }

    private static class ListenerRecord {
        IBinder mBinder;
        IFMEventListener mListener;

        public ListenerRecord(IFMEventListener listener, IBinder binder) {
            this.mBinder = binder;
            this.mListener = listener;
        }
    }

    public static void log(String str) {
        Log.m98i("FMRadioService", str);
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
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                FMRadioService.this.handleAvrcpMode();
            }
        };
        this.mVolumeEventReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                FMRadioService.log("*** mVolumeEventReceiver: ACTION  - " + intent.getAction());
                if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
                    int stream = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", 10);
                    int volume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                    FMRadioService.log("Stream: " + stream + "  and volume: " + volume);
                    if (stream == AudioManager.semGetStreamType(1) || (stream == 3 && FMRadioService.this.mIsOn)) {
                        if (!FMRadioService.this.volumeLock) {
                            int current_stream_volume = FMRadioService.this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
                            FMRadioService.log("current_stream_volume: " + current_stream_volume);
                            if (volume == current_stream_volume && !FMRadioService.this.isDNDEnable()) {
                                if (FMRadioService.this.mHandler.hasMessages(200)) {
                                    FMRadioService.this.mHandler.removeMessages(200);
                                }
                                FMRadioService.this.setVolume(volume);
                            } else {
                                int type = FMRadioService.this.mAudioManager.semGetRadioOutputPath();
                                FMRadioService fMRadioService = FMRadioService.this;
                                fMRadioService.mAvrcpMode = Settings.Secure.getInt(fMRadioService.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
                                if (FMRadioService.this.mAvrcpMode && type == 8 && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
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
                    if (!FMRadioService.this.mIsTestMode) {
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
                    FMRadioService.this.setSpeakerOn(!r3.mIsHeadsetPlugged);
                    FMRadioService.log("TestMode :- making setRadioSpeakerOn:" + (!FMRadioService.this.mIsHeadsetPlugged));
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
                    boolean isMicrophoneEvent = intent.getIntExtra("microphone", 0) == 1;
                    if (isMicrophoneEvent) {
                        FMRadioService.this.mIsMicrophoneConnected = intent.getIntExtra("state", 0) == 1;
                    } else {
                        FMRadioService.this.mIsEarphoneConnected = intent.getIntExtra("state", 0) == 1;
                    }
                    FMRadioService fMRadioService2 = FMRadioService.this;
                    fMRadioService2.mIsHeadsetPlugged = fMRadioService2.mIsMicrophoneConnected || FMRadioService.this.mIsEarphoneConnected;
                    FMRadioService.log("mIsHeadsetPlugged :" + FMRadioService.this.mIsHeadsetPlugged);
                    if (FMRadioService.this.mIsTestMode) {
                        FMRadioService.this.setSpeakerOn(!r3.mIsHeadsetPlugged);
                        FMRadioService.log("TestMode :- making setRadioSpeakerOn:" + (!FMRadioService.this.mIsHeadsetPlugged));
                        return;
                    }
                    if (!FMRadioService.this.mIsHeadsetPlugged) {
                        int tvstatus = Settings.System.getInt(FMRadioService.this.mContext.getContentResolver(), "tv_out", 0);
                        FMRadioService.log("TV out setting value :" + tvstatus);
                        if (tvstatus == 1) {
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
                        if (!FMRadioService.this.mIsOn) {
                            FMRadioService.this.mAudioManager.abandonAudioFocus(FMRadioService.this.mAudioFocusListener);
                            FMRadioService.mIsTransientPaused = false;
                            return;
                        }
                        if (FMRadioService.this.mScanProgress && FMRadioServiceFeature.CHIP_VENDOR == 6) {
                            FMRadioService.this.cancelScan();
                        } else {
                            FMRadioService.this.cancelSeek();
                        }
                        FMRadioService.this.offInternal(2, true);
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
                    boolean isFromBT = intent.getBooleanExtra("android.bluetooth.a2dp.extra.DISCONNECT_A2DP", false);
                    FMRadioService.log("ACTION_AUDIO_BECOMING_NOISE , Its from BT :" + isFromBT);
                    boolean isFromDock = intent.getBooleanExtra("DISCONNECT_DOCK", false);
                    FMRadioService.log("ACTION_AUDIO_BECOMING_NOISE , Its from Dock :" + isFromDock);
                    if (FMRadioService.this.mIsOn && !FMRadioService.this.mIsTestMode && !isFromBT && !isFromDock) {
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
            public void onCallStateChanged(int state, String incomingNumber) {
                FMRadioService.log("phone state : " + state + " mNeedToResumeFM: " + FMRadioService.mNeedToResumeFM + " mIsPhoneCallRinging : " + this.mIsPhoneCallRinging + " mIsForcestop : " + FMRadioService.this.mIsForcestop);
                switch (state) {
                    case 0:
                        if (FMRadioService.mNeedToResumeFM && !FMRadioService.this.isOn() && FMRadioService.this.mNeedResumeToFreq != -2 && !FMRadioService.this.mIsForcestop && this.mIsPhoneCallRinging) {
                            if (FMRadioService.this.m233on(false)) {
                                int outputPath = FMRadioService.this.mAudioManager.semGetRadioOutputPath();
                                FMRadioService.log("onCallStateChanged() :: CALL_STATE_IDLE setPath() = " + outputPath);
                                FMRadioService.this.mAudioManager.semSetRadioOutputPath(outputPath);
                                if (FMRadioService.mIsTransientPaused) {
                                    FMRadioService.this.mResumeVol = r3.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1));
                                    FMRadioService.log("slowly increase the volume till :" + FMRadioService.this.mResumeVol);
                                    if (FMRadioService.this.mResumeVol == 0) {
                                        FMRadioService.this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), (int) FMRadioService.this.mResumeVol, 0);
                                    } else {
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
                                    }
                                    FMRadioService.mIsTransientPaused = false;
                                } else {
                                    FMRadioService.this.mAudioManager.setStreamVolume(AudioManager.semGetStreamType(1), FMRadioService.this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)), 0);
                                }
                                if (FMRadioService.this.mNeedResumeToFreq <= 0) {
                                    FMRadioService.this.mNeedResumeToFreq = 87500L;
                                }
                                if (FMRadioService.this.mIsExternalChipset) {
                                    int freqExt = ((int) FMRadioService.this.mNeedResumeToFreq) / 10;
                                    FMRadioService.this.mPlayerExternalChipset.tune(freqExt);
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
                        FMRadioService.mNeedToResumeFM = false;
                        this.mIsPhoneCallRinging = false;
                        break;
                    case 1:
                        this.mIsPhoneCallRinging = true;
                        break;
                }
            }
        };
        this.mIsMDMSpeakerEnabled = false;
        this.mMDMSpeakerEnabled = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Bundle bundle;
                String action = intent.getAction();
                FMRadioService.log("*** mMDMSpeakerEnabled: ACTION  - " + intent.getAction());
                if (action.equals(FMRadioService.MDM_SPEAKER_ENABLED) && (bundle = intent.getExtras()) != null) {
                    FMRadioService.this.mIsMDMSpeakerEnabled = ((Boolean) bundle.get("state")).booleanValue();
                }
            }
        };
        this.mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.android.server.FMRadioService.8
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public void onAudioFocusChange(int focusChange) {
                FMRadioService.log("onAudioFocusChange : " + focusChange);
                if (FMRadioService.this.volumeLock && (focusChange == -1 || focusChange == -2)) {
                    FMRadioService.this.mRecFinishNotified = true;
                    FMRadioService.this.notifyEvent(17, null);
                    FMRadioService.this.setDelay(100L);
                }
                if ((focusChange == -1 || focusChange == -2) && FMRadioServiceFeature.CHIP_VENDOR != 9 && !FMRadioService.this.mIsExternalChipset) {
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
                if (!FMRadioService.this.volumeLock && (focusChange == -1 || focusChange == -2)) {
                    FMRadioService.log("OnAudioFocusChangeListener : mute FM before turn off");
                    if (FMRadioService.this.mIsExternalChipset) {
                        FMRadioService.this.mPlayerExternalChipset.muteOn();
                        FMRadioService.this.setFMAudioPath(false);
                        FMRadioService.this.mPlayerExternalChipset.off();
                    }
                    FMRadioService.this.mAudioManager.setParameters(FMRadioService.audioMute);
                }
                if (focusChange != 1 || !FMRadioService.this.mAudioFocusHandler.hasMessages(-2)) {
                    FMRadioService.this.clearMessageQueue();
                }
                Message msg = Message.obtain();
                msg.what = focusChange;
                FMRadioService.this.mAudioFocusHandler.sendMessage(msg);
                if (FMRadioService.DEBUGGABLE) {
                    FMRadioService.log("OnAudioFocusChangeListener switch off mAudioFocusListener :" + focusChange + " stored freq:" + FMRadioService.this.mNeedResumeToFreq);
                }
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.server.FMRadioService.9
            long currentVolume = 0;

            @Override // android.p009os.Handler
            public void handleMessage(Message msg) {
                FMRadioService.log("mHandler(g.what=" + msg.what + ") is called");
                if (msg.what == 200) {
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
        this.mSystemReceiver1 = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Boolean.valueOf(intent.getBooleanExtra(Intent.EXTRA_DONT_KILL_APP, false));
                try {
                    Uri uri = intent.getData();
                    String packageName = uri.getSchemeSpecificPart();
                    if ((action.equals("android.intent.action.PACKAGE_REMOVED") || action.equals(Intent.ACTION_PACKAGE_RESTARTED)) && "com.sec.android.app.fm".equals(packageName)) {
                        FMRadioService.this.mIsForcestop = true;
                        off();
                        if (FMRadioService.this.volumeLock) {
                            FMRadioService.this.volumeLock = false;
                            FMRadioService.this.releaseAudioSystemMute();
                        }
                    }
                } catch (NullPointerException e) {
                    Log.m96e("FMRadioService", "NullPointerException in mSystemReceiver " + e);
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
        this.mSystemReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.11
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
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
        this.mLowBatteryReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.12
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                FMRadioService.log("FMRadioService:mLowBatteryReceiver " + action);
                FMRadioService.log("Low batteryWarning Level :1");
                if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                    int battStatus = intent.getIntExtra("status", 1);
                    int battScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                    int battLevel = intent.getIntExtra("level", battScale);
                    FMRadioService.log("Level = " + battLevel + "/" + battScale);
                    FMRadioService.log("Status = " + battStatus);
                    if (battLevel <= 1 && battStatus != 2) {
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
        this.mSetPropertyReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                FMRadioService.log("mSetPropertyReceiver : action is " + action);
                if ("com.sec.android.app.fm.set_property".equals(action)) {
                    String key = intent.getStringExtra("key");
                    int value = intent.getIntExtra("value", 0);
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("mSetPropertyReceiver :: " + key + "=" + value);
                    }
                    if (key.startsWith("service.brcm.fm") || key.startsWith("service.mrvl.fm")) {
                        SystemProperties.set(key, String.valueOf(value));
                        return;
                    }
                    return;
                }
                if ("com.sec.android.app.fm.set_volume".equals(action)) {
                    String key2 = intent.getStringExtra("key");
                    String volumetable = intent.getStringExtra("volumetable");
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("mSetPropertyReceiver :: " + key2 + "=" + volumetable);
                    }
                    if (FMRadioService.this.VolumePropertyname.equals(key2)) {
                        SystemProperties.set(key2, volumetable);
                    }
                }
            }
        };
        this.mAllSoundOffReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.14
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int AllSoundOff = intent.getIntExtra("mute", 0);
                FMRadioService.log("mAllSoundOffReceiver :: " + AllSoundOff);
                if (AllSoundOff == 1) {
                    FMRadioService.log("FM chip mute");
                    FMRadioService.this.mute(true);
                    if (FMRadioService.this.volumeLock) {
                        FMRadioService.this.notifyRecFinish();
                        return;
                    }
                    return;
                }
                if (!FMRadioService.this.isDNDEnable()) {
                    FMRadioService.log("FM chip unmute");
                    FMRadioService.this.mute(false);
                }
            }
        };
        this.mDNDStatusReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.15
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int volume;
                if (FMRadioService.this.isDNDEnable()) {
                    FMRadioService.log("mDNDStatusReceiver onReceive : DND Enable");
                    if (FMRadioService.this.volumeLock) {
                        FMRadioService.this.notifyRecFinish();
                    }
                    FMRadioService.this.mute(true);
                    return;
                }
                if (FMRadioService.this.mIsMute && !FMRadioService.this.isAllSoundOff()) {
                    FMRadioService.this.mute(false);
                    if (FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME && (volume = FMRadioService.this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1))) != 0) {
                        FMRadioService.this.setVolume(volume);
                    }
                    FMRadioService.log("mDNDStatusReceiver onReceive : DND Disable ");
                }
            }
        };
        this.mAlarmReceiver = new BroadcastReceiver() { // from class: com.android.server.FMRadioService.16
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                FMRadioService.log("Alarm onReceive");
                String cmdStr = intent.getStringExtra("command");
                if ("TTSstart".equals(cmdStr)) {
                    FMRadioService.log("TTSstart play");
                    FMRadioService.this.alarmTTSPlay = true;
                }
                if ("TTSstop".equals(cmdStr)) {
                    FMRadioService.log("TTSstop play");
                    FMRadioService.this.alarmTTSPlay = false;
                }
            }
        };
        this.mScanThread = null;
        this.mWaitPidDuringScanning = false;
        this.bmObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.server.FMRadioService.17
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
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
        context.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilterVol = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        intentFilterVol.setPriority(999);
        context.registerReceiver(this.mVolumeEventReceiver, intentFilterVol);
        context.registerReceiver(this.mVolumeEventReceiver, new IntentFilter(ACTION_VOLUME_LOCK));
        context.registerReceiver(this.mVolumeEventReceiver, new IntentFilter(ACTION_VOLUME_UNLOCK));
        this.mAirPlaneEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) != 0;
        log("mAirPlaneEnabled flag :" + this.mAirPlaneEnabled);
        context.registerReceiver(this.mReceiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        context.registerReceiver(this.mButtonReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
        context.registerReceiver(this.mResetSettingReceiver, new IntentFilter(RESET_SETTING));
        context.registerReceiver(this.mReceiver, new IntentFilter(ACTION_SAVE_FMRECORDING_ONLY));
        IntentFilter intentFilter2 = new IntentFilter("com.sec.android.app.camera.ACTION_CAMERA_START");
        intentFilter2.addAction("com.sec.android.app.camera.ACTION_CAMERA_STOP");
        context.registerReceiver(this.mReceiver, intentFilter2);
        registerSystemListener();
        registerSetPropertyListener();
        registerMDMCommandRec();
        readTuningParameters();
        readParametersForCurrentRegion();
        if (this.mIsExternalChipset) {
            checkUSBDeviceConnected(context);
        }
    }

    private void registerBatteryListener() {
        IntentFilter intentFilterBattery = new IntentFilter();
        intentFilterBattery.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.mContext.registerReceiver(this.mLowBatteryReceiver, intentFilterBattery);
        log("registering low battery listener");
    }

    private void unRegisterBatteryListener() {
        this.mContext.unregisterReceiver(this.mLowBatteryReceiver);
        log("unregistering low battery listener");
    }

    private void registerSystemListener() {
        IntentFilter intentSystemFilter = new IntentFilter();
        intentSystemFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(this.mSystemReceiver, intentSystemFilter);
        IntentFilter intentSystemFilter2 = new IntentFilter();
        intentSystemFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentSystemFilter2.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentSystemFilter2.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        intentSystemFilter2.addDataScheme("package");
        this.mContext.registerReceiver(this.mSystemReceiver1, intentSystemFilter2);
    }

    private void unregisterSystemListener() {
        this.mContext.unregisterReceiver(this.mSystemReceiver);
    }

    private void registerSetPropertyListener() {
        IntentFilter intentFilterSetProperty = new IntentFilter();
        intentFilterSetProperty.addAction("com.sec.android.app.fm.set_property");
        intentFilterSetProperty.addAction("com.sec.android.app.fm.set_volume");
        this.mContext.registerReceiver(this.mSetPropertyReceiver, intentFilterSetProperty, this.SetPropertyPermission, null);
        log("registering set property listener");
    }

    private void unRegisterSetPropertyListener() {
        this.mContext.unregisterReceiver(this.mSetPropertyReceiver);
        log("unregistering set property listener");
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void tune(long freq) {
        if (DEBUGGABLE) {
            log(TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE + freq);
        } else {
            log(TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE);
        }
        if (!isValidPackage()) {
            return;
        }
        if (this.mOffProgress || !this.mIsOn) {
            log("tune can not be processed becuase FM chipset is either off or off in process");
            return;
        }
        mute(true);
        if (this.mIsExternalChipset) {
            int freqExt = ((int) freq) / 10;
            this.mPlayerExternalChipset.tune(freqExt);
        } else {
            this.mPlayerNative.tune(freq);
        }
        mute(false);
        log("tune notify event tune");
        notifyEvent(7, Long.valueOf(freq));
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void mute(boolean value) {
        log("mute - " + value);
        if (value) {
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
            Long[] arryL = (Long[]) arrayList.toArray(new Long[0]);
            return convertToPrimitives(arryL);
        }
        log("getLastScanResult - mScanChannelList null");
        return null;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long seekUp() {
        long freq;
        if (!isValidPackage()) {
            return -1L;
        }
        this.mIsSeeking = true;
        mute(true);
        if (this.mIsExternalChipset) {
            long seekUp = this.mPlayerExternalChipset.seekUp();
            this.mExtSeekFreq = seekUp;
            freq = seekUp * 10;
        } else {
            freq = this.mPlayerNative.seekUp();
        }
        mute(false);
        this.mIsSeeking = false;
        notifyEvent(7, Long.valueOf(freq));
        return freq;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long seekDown() {
        long freq;
        if (!isValidPackage()) {
            return -1L;
        }
        this.mIsSeeking = true;
        mute(true);
        if (this.mIsExternalChipset) {
            long seekDown = this.mPlayerExternalChipset.seekDown();
            this.mExtSeekFreq = seekDown;
            freq = seekDown * 10;
        } else {
            freq = this.mPlayerNative.seekDown();
        }
        mute(false);
        this.mIsSeeking = false;
        notifyEvent(7, Long.valueOf(freq));
        return freq;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void cancelSeek() {
        if (this.mIsExternalChipset) {
            boolean result = this.mPlayerExternalChipset.stopSeek();
            log("result = " + result);
        } else {
            this.mPlayerNative.cancelSeek();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public int isBusy() {
        if (this.mScanProgress) {
            return 1;
        }
        return -1;
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
            long freqExt = this.mPlayerExternalChipset.getTunedFrequency() * 10;
            return freqExt;
        }
        return this.mPlayerNative.getCurrentChannel();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setListener(IFMEventListener listener) {
        log("[FMRadioService] setListener :" + listener);
        if (listener != null) {
            synchronized (mFMRadioServiceLock) {
                if (this.mListeners == null) {
                    this.mListeners = new Vector<>();
                }
                ListenerRecord record = new ListenerRecord(listener, listener.asBinder());
                this.mListeners.addElement(record);
                log("no of listener:" + this.mListeners.size());
            }
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void removeListener(IFMEventListener listener) {
        log("[FMRadioService] (removeListener) :" + listener);
        if (listener == null) {
            return;
        }
        remove(listener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001e, code lost:
    
        if (r8.mTelephonyManager.getCallStateForSubscription() == 2) goto L15;
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
            Log.m96e("FMRadioService", "Exception in getCallStateForSubscription() : " + e);
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
                    this.mIsOn = this.mPlayerExternalChipset.mo235on();
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
                this.mIsOn = this.mPlayerNative.mo232on() > 0;
            }
            if (!this.mIsOn) {
                if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                    setFMAudioPath(false);
                }
                this.mIsFMAudioPathOn = false;
                this.mIsOn = false;
                releaseWakeLock();
                return false;
            }
            setSoftmute(false);
            this.mIsTestMode = true;
            notifyEvent(5, null);
            mute(false);
            setFMAudioPath(true);
            log("on_in_testmode Turning on FM radio");
            return true;
        } catch (Exception e2) {
            if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                setFMAudioPath(false);
            }
            this.mIsFMAudioPathOn = false;
            this.mIsOn = false;
            Log.m96e("FMRadioService", "Exception in on_in_testmode() : " + e2);
            releaseWakeLock();
            return false;
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /* renamed from: on */
    public boolean mo234on() {
        if (!isValidPackage()) {
            return false;
        }
        return m233on(true);
    }

    private boolean isValidPackage() {
        return true;
    }

    private boolean isCTSTestApp() {
        int caller = Binder.getCallingUid();
        String[] pkgName = getContext().getPackageManager().getPackagesForUid(caller);
        for (String mPackageName : pkgName) {
            if (FMRADIO_CTS_APP_NAME.equals(mPackageName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFmTestApp() {
        int caller = Binder.getCallingUid();
        String[] pkgName = getContext().getPackageManager().getPackagesForUid(caller);
        String mPackageName = pkgName[0];
        return FMTEST_APP_NAME.equals(mPackageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x00cc, code lost:
    
        if (com.android.server.FMRadioService.mIsTransientPaused == false) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0429 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0124 A[Catch: Exception -> 0x0475, all -> 0x04b2, TryCatch #4 {Exception -> 0x0475, blocks: (B:167:0x00ca, B:165:0x0117, B:58:0x0124, B:60:0x012e, B:62:0x0139, B:64:0x0141, B:66:0x0162, B:67:0x0178, B:69:0x019a, B:72:0x01a1, B:74:0x01a5, B:75:0x01bf, B:79:0x01fd, B:80:0x0220, B:83:0x0206, B:84:0x0224, B:87:0x0170, B:88:0x0228, B:90:0x022e, B:92:0x0238, B:94:0x0245, B:95:0x027f, B:96:0x0269, B:97:0x028a, B:100:0x0293, B:102:0x029d, B:104:0x02ae, B:107:0x02b3, B:109:0x02bb, B:111:0x02e2, B:113:0x02e6, B:116:0x02ec, B:118:0x02f0, B:121:0x02f8, B:123:0x02fd, B:124:0x03f6, B:128:0x042b, B:129:0x044e, B:132:0x0434, B:133:0x0452, B:136:0x031b, B:138:0x0320, B:140:0x0349, B:141:0x034e, B:143:0x0354, B:144:0x035b, B:146:0x0361, B:148:0x0397, B:149:0x039f, B:150:0x03a9, B:151:0x03bf, B:152:0x02bf, B:154:0x02c3, B:156:0x02cf, B:157:0x02d9, B:158:0x0456, B:160:0x045a, B:161:0x045d, B:50:0x00ce, B:52:0x00d4, B:54:0x00da), top: B:166:0x00ca, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0141 A[Catch: Exception -> 0x0475, all -> 0x04b2, TryCatch #4 {Exception -> 0x0475, blocks: (B:167:0x00ca, B:165:0x0117, B:58:0x0124, B:60:0x012e, B:62:0x0139, B:64:0x0141, B:66:0x0162, B:67:0x0178, B:69:0x019a, B:72:0x01a1, B:74:0x01a5, B:75:0x01bf, B:79:0x01fd, B:80:0x0220, B:83:0x0206, B:84:0x0224, B:87:0x0170, B:88:0x0228, B:90:0x022e, B:92:0x0238, B:94:0x0245, B:95:0x027f, B:96:0x0269, B:97:0x028a, B:100:0x0293, B:102:0x029d, B:104:0x02ae, B:107:0x02b3, B:109:0x02bb, B:111:0x02e2, B:113:0x02e6, B:116:0x02ec, B:118:0x02f0, B:121:0x02f8, B:123:0x02fd, B:124:0x03f6, B:128:0x042b, B:129:0x044e, B:132:0x0434, B:133:0x0452, B:136:0x031b, B:138:0x0320, B:140:0x0349, B:141:0x034e, B:143:0x0354, B:144:0x035b, B:146:0x0361, B:148:0x0397, B:149:0x039f, B:150:0x03a9, B:151:0x03bf, B:152:0x02bf, B:154:0x02c3, B:156:0x02cf, B:157:0x02d9, B:158:0x0456, B:160:0x045a, B:161:0x045d, B:50:0x00ce, B:52:0x00d4, B:54:0x00da), top: B:166:0x00ca, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0228 A[Catch: Exception -> 0x0475, all -> 0x04b2, TRY_ENTER, TryCatch #4 {Exception -> 0x0475, blocks: (B:167:0x00ca, B:165:0x0117, B:58:0x0124, B:60:0x012e, B:62:0x0139, B:64:0x0141, B:66:0x0162, B:67:0x0178, B:69:0x019a, B:72:0x01a1, B:74:0x01a5, B:75:0x01bf, B:79:0x01fd, B:80:0x0220, B:83:0x0206, B:84:0x0224, B:87:0x0170, B:88:0x0228, B:90:0x022e, B:92:0x0238, B:94:0x0245, B:95:0x027f, B:96:0x0269, B:97:0x028a, B:100:0x0293, B:102:0x029d, B:104:0x02ae, B:107:0x02b3, B:109:0x02bb, B:111:0x02e2, B:113:0x02e6, B:116:0x02ec, B:118:0x02f0, B:121:0x02f8, B:123:0x02fd, B:124:0x03f6, B:128:0x042b, B:129:0x044e, B:132:0x0434, B:133:0x0452, B:136:0x031b, B:138:0x0320, B:140:0x0349, B:141:0x034e, B:143:0x0354, B:144:0x035b, B:146:0x0361, B:148:0x0397, B:149:0x039f, B:150:0x03a9, B:151:0x03bf, B:152:0x02bf, B:154:0x02c3, B:156:0x02cf, B:157:0x02d9, B:158:0x0456, B:160:0x045a, B:161:0x045d, B:50:0x00ce, B:52:0x00d4, B:54:0x00da), top: B:166:0x00ca, outer: #1 }] */
    /* renamed from: on */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean m233on(boolean isAudioFocusNeeded) {
        int i;
        InputManager inputManager;
        boolean z;
        log("on");
        if (this.SURVEY_MODE_ENABLE) {
            int caller = Binder.getCallingUid();
            String[] pkgName = getContext().getPackageManager().getPackagesForUid(caller);
            String mPackageName = pkgName[0];
            String mPackageVersion = "";
            if (!"com.sec.android.app.fm".equals(mPackageName)) {
                try {
                    mPackageVersion = this.mContext.getPackageManager().getPackageInfo(mPackageName, 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.m96e("FMRadioService", "NameNotFoundException: " + e);
                }
                SamsungAnalyticsRunnable samsungAnalyticsRunnable = new SamsungAnalyticsRunnable(mPackageName, mPackageVersion);
                this.mSamsungAnalyticsRunnable = samsungAnalyticsRunnable;
                this.mHandlerSA.post(samsungAnalyticsRunnable);
            }
        } else {
            log("SamsungAnalytics survey mode is not enable");
        }
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
                if ((this.mTelephonyManager.getCallStateForSubscription() == 1 && !isDNDEnable()) || this.mTelephonyManager.getCallStateForSubscription() == 2) {
                    if (mIsTransientPaused) {
                        mNeedToResumeFM = true;
                    }
                    return false;
                }
            } catch (Exception e2) {
                Log.m96e("FMRadioService", "Exception in getCallStateForSubscription() : " + e2);
            }
            if (this.alarmTTSPlay) {
                return false;
            }
            if (this.mIsOn) {
                return true;
            }
            if (!isAudioFocusNeeded) {
                try {
                } catch (Exception e3) {
                    Log.m96e("FMRadioService", "Exception in on() : " + e3);
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
            if (!isFmTestApp() && !isCTSTestApp()) {
                log("AudioFocusListener registered");
                AudioAttributes attributes = new AudioAttributes.Builder().setLegacyStreamType(AudioManager.semGetStreamType(1)).semAddAudioTag("FM_RADIO").semAddAudioTag("NO_FADEOUT_FROM_AUDIOFOCUS").build();
                AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(1).setAudioAttributes(attributes).setOnAudioFocusChangeListener(this.mAudioFocusListener).build();
                this.mAudioManager.requestAudioFocus(audioFocusRequest);
                for (i = 0; i < 50; i++) {
                    int dmbEnabled = SemSystemProperties.getInt("service.media.dmb", 0);
                    if (dmbEnabled <= 0) {
                        break;
                    }
                    log("DMB enabled - waiting for DMB is closed");
                    wait(50L);
                }
                this.mOnProgress = true;
                if (this.mIsExternalChipset) {
                    if (FMRadioServiceFeature.CHIP_VENDOR == 9) {
                        if (this.mPlayerNative.preInitialize() > 0) {
                            setFMAudioPath(true);
                            int outputPath = this.mAudioManager.semGetRadioOutputPath();
                            if (DEBUGGABLE) {
                                log("OnAudioFocusChangeListener switch on mNeedResumeToFreq:" + this.mNeedResumeToFreq + "setOutputPath = " + outputPath);
                            } else {
                                log("OnAudioFocusChangeListener switch setOutputPath = " + outputPath);
                            }
                            this.mAudioManager.semSetRadioOutputPath(outputPath);
                            wait(200L);
                        } else {
                            log("FM preInitialize() failed");
                            this.mOnProgress = false;
                            return false;
                        }
                    }
                    if (this.mPlayerNative.mo232on() <= 0) {
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
                    if (FMRadioServiceFeature.CHIP_VENDOR != 4 && FMRadioServiceFeature.CHIP_VENDOR != 9) {
                        if (FMRadioServiceFeature.CHIP_VENDOR != 5 && FMRadioServiceFeature.CHIP_VENDOR != 10) {
                            if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
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
                            inputManager = (InputManager) this.mContext.getSystemService("input");
                            z = this.mIsOn;
                            if (z != this.mIsSetWakeKey && inputManager != null) {
                                try {
                                    inputManager.semSetWakeKeyDynamically("com.sec.android.app.fm", z, VOLUME_UP_DOWN);
                                } catch (SecurityException se) {
                                    log("Exception in semSetWakeKeyDynamically(): " + se.toString());
                                }
                                this.mIsSetWakeKey = this.mIsOn;
                            }
                            this.mIsForcestop = false;
                            return true;
                        }
                        this.mPlayerNative.setRSSI_th(this.mRssi_th);
                        this.mPlayerNative.setSeekDC(this.mRichwave_seekDC);
                        this.mPlayerNative.setSeekQA(this.mRichwave_seekQA);
                        setBand(this.mBand);
                        setChannelSpacing(this.mChannelSpacing);
                        setDEConstant(this.mDEConstant);
                        registerBikeModeObserver();
                        registerAvrcpModeObserver();
                        registerAlarmListener();
                        registerAllSoundOffListener();
                        registerDNDStatusChangedListener();
                        registerTelephonyListener();
                        inputManager = (InputManager) this.mContext.getSystemService("input");
                        z = this.mIsOn;
                        if (z != this.mIsSetWakeKey) {
                            inputManager.semSetWakeKeyDynamically("com.sec.android.app.fm", z, VOLUME_UP_DOWN);
                            this.mIsSetWakeKey = this.mIsOn;
                        }
                        this.mIsForcestop = false;
                        return true;
                    }
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
                    setBand(this.mBand);
                    setChannelSpacing(this.mChannelSpacing);
                    setDEConstant(this.mDEConstant);
                    registerBikeModeObserver();
                    registerAvrcpModeObserver();
                    registerAlarmListener();
                    registerAllSoundOffListener();
                    registerDNDStatusChangedListener();
                    registerTelephonyListener();
                    inputManager = (InputManager) this.mContext.getSystemService("input");
                    z = this.mIsOn;
                    if (z != this.mIsSetWakeKey) {
                    }
                    this.mIsForcestop = false;
                    return true;
                }
                log("on() mIsExternalChipset " + this.mIsExternalChipset);
                if (this.mAudioManager.semGetRadioOutputPath() == 2) {
                    this.mPlayerExternalChipset.setRecordMode(true);
                    this.mIsOn = this.mPlayerExternalChipset.isOn();
                } else {
                    this.mIsOn = this.mPlayerExternalChipset.mo235on();
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
                InputManager inputManager2 = (InputManager) this.mContext.getSystemService("input");
                boolean z2 = this.mIsOn;
                if (z2 != this.mIsSetWakeKey && inputManager2 != null) {
                    try {
                        inputManager2.semSetWakeKeyDynamically("com.sec.android.app.fm", z2, VOLUME_UP_DOWN);
                    } catch (SecurityException se2) {
                        log("Exception in semSetWakeKeyDynamically(): " + se2.toString());
                    }
                    this.mIsSetWakeKey = this.mIsOn;
                }
                this.mIsForcestop = false;
                return true;
            }
            log("AudioFocusListener : skip the requestAudioFocus");
            while (i < 50) {
            }
            this.mOnProgress = true;
            if (this.mIsExternalChipset) {
            }
        }
        return false;
    }

    private void registerTelephonyListener() {
        if (this.mIsPhoneStateListenerRegistered) {
            log("listner already registered");
            return;
        }
        long id = Binder.clearCallingIdentity();
        try {
            this.mTelephonyManager.listen(this.mPhoneListener, 32);
            Binder.restoreCallingIdentity(id);
            this.mIsPhoneStateListenerRegistered = true;
            log("registering telephony listener..");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(id);
            throw th;
        }
    }

    private void unRegisterTelephonyListener() {
        if (!this.mIsPhoneStateListenerRegistered) {
            log("listner is not registered");
            return;
        }
        long id = Binder.clearCallingIdentity();
        try {
            this.mTelephonyManager.listen(this.mPhoneListener, 0);
            Binder.restoreCallingIdentity(id);
            this.mIsPhoneStateListenerRegistered = false;
            log("unRegisterTelephonyListener ..");
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(id);
            throw th;
        }
    }

    private void registerMDMCommandRec() {
        IntentFilter intentFilter = new IntentFilter(MDM_SPEAKER_ENABLED);
        this.mContext.registerReceiver(this.mMDMSpeakerEnabled, intentFilter);
        log("MDM command reciever registered");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00bc A[Catch: NullPointerException -> 0x00c1, TRY_LEAVE, TryCatch #0 {NullPointerException -> 0x00c1, blocks: (B:20:0x0056, B:22:0x005a, B:24:0x0063, B:26:0x0067, B:27:0x006d, B:28:0x008a, B:30:0x0092, B:33:0x0099, B:37:0x00a2, B:39:0x00a6, B:42:0x00aa, B:44:0x00ae, B:48:0x00b2, B:50:0x00bc, B:56:0x0075, B:58:0x0079, B:59:0x0081, B:61:0x0085), top: B:19:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setVolume(long val) {
        log("set chipset Volume : " + val);
        if (!this.mIsOn) {
            return;
        }
        if (this.mScanProgress) {
            log("setVolume :: unset on ScanProgress");
            return;
        }
        if (val < 0 || val > 15) {
            return;
        }
        int type = this.mAudioManager.semGetRadioOutputPath();
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z;
        if (z && type == 8) {
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
                    this.mResumeVol = val;
                    if (!isAllSoundOff() && !isDNDEnable()) {
                        if (FMRadioServiceFeature.CHIP_VENDOR == 3) {
                            if (val <= 0) {
                                if (!this.mIsMute) {
                                    mute(true);
                                    return;
                                }
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
                        mute(true);
                        return;
                    }
                    return;
                }
            } catch (NullPointerException e) {
                Log.m96e("FMRadioService", "NullPointerException in setVolume() : " + e);
                return;
            }
        }
        if (!this.mIsExternalChipset) {
            if (FMRadioServiceFeature.CHIP_VENDOR != 8) {
                this.mPlayerNative.setVolume(val);
            }
        } else {
            int value = (int) val;
            this.mPlayerExternalChipset.setVolume(value);
        }
        this.mResumeVol = val;
        if (!isAllSoundOff()) {
            if (FMRadioServiceFeature.CHIP_VENDOR == 3) {
            }
        }
        log("setVolume :: AllSoundOff or DND is enabled. So FMRadio is muted.");
        if (this.mIsMute) {
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getVolume() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getVolume();
        }
        return this.mPlayerNative.getVolume();
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setSpeakerOn(boolean bSpeakerOn) {
        log("setSpeakerOn : " + bSpeakerOn);
        if (this.mIsExternalChipset) {
            if (isOn()) {
                int freqExt = this.mPlayerExternalChipset.getTunedFrequency();
                if (this.mIsSeeking) {
                    this.mPlayerExternalChipset.stopSeek();
                    setDelay(30L);
                    freqExt = (int) this.mExtSeekFreq;
                }
                if (bSpeakerOn) {
                    this.mPlayerExternalChipset.off();
                    this.mPlayerExternalChipset.setRecordMode(true);
                } else {
                    this.mPlayerExternalChipset.setRecordMode(false);
                    this.mPlayerExternalChipset.mo235on();
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
                this.mPlayerExternalChipset.tune(freqExt);
                if (this.mRDSEnable) {
                    this.mPlayerExternalChipset.setRdsEnable(true);
                }
            }
        } else {
            this.mPlayerNative.setSpeakerOn(bSpeakerOn);
        }
        setSlimbusEnable(0);
        if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
            if (this.mIsSupportSoftmute) {
                if (isPathSupportSoftmute(bSpeakerOn ? 2 : 3)) {
                    log("set softmute : true");
                    setSoftmute(true);
                }
            }
            log("set softmute : false");
            setSoftmute(false);
        }
        if (bSpeakerOn) {
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
    public void setRecordMode(boolean isRecord) {
        String keyValuePairs;
        if (this.mIsExternalChipset) {
            return;
        }
        int value = 0;
        if (isRecord) {
            keyValuePairs = "fm_record=1";
            value = 1;
        } else {
            keyValuePairs = "fm_record=0";
        }
        this.isRecording = isRecord;
        if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
            this.mAudioManager.setParameters(keyValuePairs);
        }
        int type = this.mAudioManager.semGetRadioOutputPath();
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_avc_mode", 1) == 1;
        this.mAvrcpMode = z;
        if (z && type == 8 && FMRadioServiceFeature.FEATURE_USE_CHIPSET_VOLUME) {
            log(" setRecordMode avrcp on");
            if (this.isRecording) {
                this.mPlayerNative.setVolume(11L);
            }
        }
        this.mPlayerNative.setRecordMode(value);
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getMaxVolume() {
        if (this.mIsExternalChipset) {
            return 15L;
        }
        return this.mPlayerNative.getMaxVolume();
    }

    private void releaseWakeLock() {
        long id = Binder.clearCallingIdentity();
        try {
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
                log("Lock is released");
            }
        } finally {
            Binder.restoreCallingIdentity(id);
        }
    }

    private void acquireWakeLock() {
        long id = Binder.clearCallingIdentity();
        try {
            if (!this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
                log("Lock is held");
            }
        } finally {
            Binder.restoreCallingIdentity(id);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isOn() {
        return this.mIsOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0065, code lost:
    
        if (com.android.server.FMRadioServiceFeature.CHIP_VENDOR != 7) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean offInternal(int reasonCode, boolean needToRemoveFocusListener) {
        if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
            cancelSeek();
            try {
                wait(10L);
            } catch (InterruptedException e) {
                Log.m96e("FMRadioService", "InterruptedException in wait() : " + e);
            }
        }
        log("offInternal :: reasonCode=" + reasonCode);
        this.mIsTransientDuck = false;
        try {
            if (!this.mIsOn) {
                if (needToRemoveFocusListener) {
                    log("offInternal :: remove audiofocus ");
                    this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                }
                return true;
            }
            try {
                this.mOffProgress = true;
                if (!this.FEATURE_INDIRECT_MODE) {
                    try {
                        if (!this.mIsExternalChipset) {
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.m96e("FMRadioService", "Exception in offInternal() : " + e);
                        this.mOffProgress = false;
                        releaseWakeLock();
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        releaseWakeLock();
                        throw th;
                    }
                }
                mute(true);
                setDelay(10L);
                mIsTransientPaused = !needToRemoveFocusListener;
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
                        boolean offState = this.mPlayerExternalChipset.off();
                        log("off external chip set" + offState);
                        if (reasonCode == 11) {
                            this.mIsOn = false;
                        }
                        wait(200L);
                    }
                }
                boolean offState2 = this.mIsExternalChipset;
                if (!offState2) {
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
                if (needToRemoveFocusListener) {
                    this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                    unRegisterTelephonyListener();
                }
                notifyEvent(6, Integer.valueOf(reasonCode));
                if (!this.mIsForcestop) {
                    sendFMOFFBroadcast();
                }
                InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
                boolean z = this.mIsOn;
                if (z != this.mIsSetWakeKey && inputManager != null) {
                    try {
                        inputManager.semSetWakeKeyDynamically("com.sec.android.app.fm", z, VOLUME_UP_DOWN);
                    } catch (SecurityException se) {
                        log("Exception in semSetWakeKeyDynamically(): " + se.toString());
                    }
                    this.mIsSetWakeKey = this.mIsOn;
                }
                releaseWakeLock();
                return true;
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void sendFMOFFBroadcast() {
        log("Sending broadcast FM is in OFF state");
        Intent intent = new Intent("com.sec.android.fm.player_lock.status.off");
        intent.setClassName("com.sec.android.app.fm", "com.sec.android.app.fm.widget.FMRadioProvider");
        intent.setFlags(1073741824);
        this.mContext.sendBroadcast(intent);
    }

    private void sendFMONBroadcast(Object data) {
        log("Sending broadcast FM is in ON state");
        if (data != null) {
            Intent intent = new Intent("com.app.fm.auto.on");
            intent.setFlags(268435456);
            intent.setClassName("com.sec.android.app.fm", "com.sec.android.app.fm.receiver.AutoResumeReceiver");
            intent.putExtra("freq", (((Long) data).longValue() / 1000.0f) + "");
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

    private void remove(IFMEventListener listener) {
        synchronized (mFMRadioServiceLock) {
            Vector<ListenerRecord> vector = this.mListeners;
            if (vector != null && vector.size() != 0) {
                for (int i = 0; i < this.mListeners.size(); i++) {
                    ListenerRecord record = this.mListeners.get(i);
                    if (record.mBinder == listener.asBinder()) {
                        ListenerRecord delRecord = this.mListeners.remove(i);
                        log("[FMRadioService] deleted Listener :" + delRecord);
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
        if (FMRadioServiceFeature.CHIP_VENDOR == 9 && this.mPlayerNative.preInitialize() <= 0) {
            return;
        }
        this.mScanProgress = true;
        ScanThread scanThread = new ScanThread();
        this.mScanThread = scanThread;
        scanThread.start();
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
        if (!this.mIsExternalChipset) {
            return this.mPlayerNative.getCurrentSNR();
        }
        return -1L;
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
                if (arrayList != null) {
                    notifyEvent(4, arrayList.toArray(new Long[0]));
                    return true;
                }
                return true;
            }
        } catch (Exception e) {
            Log.m96e("FMRadioService", "Exception in cancelScan() : " + e);
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
        if (this.mIsOn) {
            if (!this.mIsExternalChipset) {
                return this.mPlayerNative.searchAll();
            }
            return this.mPlayerExternalChipset.searchAll() * 10;
        }
        return -1L;
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
        if (!isValidPackage()) {
            return;
        }
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

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void disableRDS() {
        if (!isValidPackage()) {
            return;
        }
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

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void enableAF() {
        if (!isValidPackage()) {
            return;
        }
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

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void disableAF() {
        if (!isValidPackage()) {
            return;
        }
        this.mAFEnable = false;
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.disableAF();
        }
        checkForWakeLockRelease();
    }

    private void checkForWakeLockRelease() {
        if (!this.mAFEnable && !this.mRDSEnable) {
            log("AF and RDS is off. release the wake lock");
            releaseWakeLock();
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setBand(int band) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setBand(band);
        } else {
            this.mPlayerExternalChipset.setBand(band);
        }
        this.mBand = band;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setChannelSpacing(int spacing) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setChannelSpacing(spacing);
        } else {
            this.mPlayerExternalChipset.setChannelSpacing(spacing);
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
        if (!isValidPackage()) {
            return;
        }
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setStereo();
        } else {
            this.mPlayerExternalChipset.setSoundMode(0);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setMono() {
        if (!isValidPackage()) {
            return;
        }
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setMono();
        } else {
            this.mPlayerExternalChipset.setSoundMode(1);
        }
    }

    public void notifyEvent(int type, Object data) {
        Thread thread;
        if (this.mIsOn && type == 7) {
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
        if (this.mIsExternalChipset && type == 3) {
            this.mPlayerExternalChipset.setVolume(this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)));
        }
        synchronized (mFMRadioServiceLock) {
            Vector<ListenerRecord> vector = this.mListeners;
            if (vector != null && vector.size() != 0) {
                log("Total listener:" + this.mListeners.size());
                int size = this.mListeners.size();
                for (int i = size - 1; i >= 0; i--) {
                    log("Notifying listener:" + i);
                    switch (type) {
                        case 1:
                            long freq = 0;
                            if (data != null) {
                                freq = ((Long) data).longValue();
                            }
                            if (DEBUGGABLE) {
                                log("notifying :EVENT_CHANNEL_FOUND to : listener -->" + i + " : with freq:" + freq + "-->" + this.mListeners.get(i).mListener.asBinder());
                            }
                            this.mListeners.get(i).mListener.onChannelFound(freq);
                            continue;
                        case 2:
                            log("notifying :EVENT_SCAN_STARTED to : listener -->" + i + " :" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onScanStarted();
                            continue;
                        case 3:
                            if (data != null) {
                                Long[] Ifreq = (Long[]) data;
                                long[] freqArry = convertToPrimitives(Ifreq);
                                int count = 0;
                                if (freqArry != null) {
                                    count = freqArry.length;
                                }
                                log("notifying :EVENT_SCAN_FINISHED to : listener -->" + i + " : with data array:" + count + "-->" + this.mListeners.get(i).mListener.asBinder());
                                this.mListeners.get(i).mListener.onScanFinished(freqArry);
                                continue;
                            } else {
                                log("notifying : EVENT_SCAN_FINISHED : data is null !!!");
                            }
                        case 4:
                            if (data != null) {
                                Long[] Ifreq2 = (Long[]) data;
                                long[] freqArry2 = convertToPrimitives(Ifreq2);
                                int count2 = 0;
                                if (freqArry2 != null) {
                                    count2 = freqArry2.length;
                                }
                                log("notifying :EVENT_SCAN_STOPPED to : listener -->" + i + " : with data array:" + count2 + "-->" + this.mListeners.get(i).mListener.asBinder());
                                this.mListeners.get(i).mListener.onScanStopped(freqArry2);
                                continue;
                            } else {
                                log("notifying : EVENT_SCAN_STOPPED : data is null !!!");
                            }
                        case 5:
                            log("notifying :EVENT_POWER_ON to : listener -->" + i + "-->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onRadioEnabled();
                            continue;
                        case 6:
                            log("notifying :EVENT_POWER_OFF to : listener -->" + i + "-->" + this.mListeners.get(i).mListener.asBinder());
                            int reasonCode = -1;
                            if (data != null) {
                                reasonCode = ((Integer) data).intValue();
                            }
                            this.mListeners.get(i).mListener.onRadioDisabled(reasonCode);
                            continue;
                        case 7:
                            if (data != null) {
                                long freq2 = ((Long) data).longValue();
                                curFreq = freq2;
                                if (DEBUGGABLE) {
                                    log("notifying :EVENT_TUNE to : listener -->" + i + " : with data array:" + freq2 + "-->" + this.mListeners.get(i).mListener.asBinder());
                                }
                                this.mListeners.get(i).mListener.onTuned(freq2);
                                continue;
                            } else {
                                log("notifying : EVENT_TUNE : data is null !!!");
                            }
                        case 8:
                            log("notifying :EVENT_EAR_PHONE_CONNECT to : listener -->" + i + ": -->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onHeadsetConnected();
                            continue;
                        case 9:
                            log("notifying :EVENT_EAR_PHONE_DISCONNECT to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onHeadsetDisconnected();
                            continue;
                        case 10:
                            log("notifying : EVENT_RDS_EVENT : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            if (data != null) {
                                FMPlayerNativeBase.RDSData rdsData = (FMPlayerNativeBase.RDSData) data;
                                this.mListeners.get(i).mListener.onRadioDataSystemReceived(rdsData.mFreq, rdsData.mChannelName, rdsData.mRadioText);
                                continue;
                            } else {
                                log("notifying : EVENT_RDS_EVENT : data is null !!!");
                            }
                        case 11:
                            log("notifying :EVENT_RDS_ENABLED to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onRadioDataSystemEnabled();
                            continue;
                        case 12:
                            log("notifying :EVENT_RDS_DISABLED to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onRadioDataSystemDisabled();
                            continue;
                        case 13:
                            log("notifying :EVENT_AF_STARTED to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onAlternateFrequencyStarted();
                            continue;
                        case 14:
                            log("notifying :EVENT_AF_RECEIVED to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            if (data != null) {
                                long freq3 = ((Long) data).longValue();
                                this.mListeners.get(i).mListener.onAlternateFrequencyReceived(freq3);
                                continue;
                            } else {
                                log("notifying : EVENT_AF_RECEIVED : data is null !!!");
                            }
                        case 15:
                            log("notifying :EVENT_VOLUME_LOCK to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onVolumeLocked();
                            continue;
                        case 16:
                            log("notifying :EVENT_RTPLUS_EVENT to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            if (data != null) {
                                FMPlayerNativeBase.RTPlusData rtplusData = (FMPlayerNativeBase.RTPlusData) data;
                                this.mListeners.get(i).mListener.onRadioTextPlusReceived(rtplusData.mContentType1, rtplusData.mStartPos1, rtplusData.mAdditionalLen1, rtplusData.mContentType2, rtplusData.mStartPos2, rtplusData.mAdditionalLen2);
                                continue;
                            } else {
                                log("notifying : EVENT_RTPLUS_EVENT : data is null !!!");
                            }
                        case 17:
                            log("notifying :EVENT_REC_FINISH to : listener -->" + i + " : ->" + this.mListeners.get(i).mListener.asBinder());
                            this.mListeners.get(i).mListener.onRecordingFinished();
                            continue;
                        case 18:
                            if (data != null) {
                                try {
                                    FMPlayerNativeBase.PIECCData pieccData = (FMPlayerNativeBase.PIECCData) data;
                                    this.mListeners.get(i).mListener.onProgrammeIdentificationExtendedCountryCodesReceived(pieccData.mPI, pieccData.mECC);
                                    if (this.mWaitPidDuringScanning && (thread = this.mScanThread) != null) {
                                        synchronized (thread) {
                                            this.mScanThread.notify();
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.m96e("FMRadioService", "Exception in notifyEvent() : " + e);
                                    Log.m96e("FMRadioService", "we loose " + i + " listener--ignore it :" + this.mListeners.get(i).mListener);
                                    remove(this.mListeners.get(i).mListener);
                                    log("Remove done go for next i's value:" + i);
                                    if (this.mIsOn && type == 7) {
                                        sendFMONBroadcast(data);
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
            if (this.mIsOn && type == 7) {
                sendFMONBroadcast(data);
            }
        }
    }

    private long[] convertToPrimitives(Long[] longObArray) {
        if (longObArray != null) {
            long[] longArray = new long[longObArray.length];
            for (int i = 0; i < longArray.length; i++) {
                longArray[i] = longObArray[i].longValue();
            }
            return longArray;
        }
        return null;
    }

    private void setDEConstant(long value) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setDEConstant(value);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public long getPlayedFreq() {
        return curFreq;
    }

    private void setSeekRSSI(long value) {
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.setRssiThreshold((int) value);
        } else {
            this.mPlayerNative.setSeekRSSI(value);
        }
    }

    private void setSeekSNR(long value) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setSeekSNR(value);
        }
    }

    private void setRSSI_th(int value) {
        this.mRssi_th = value;
    }

    private void setSNR_th(int value) {
        this.mSnr_th = value;
    }

    private void setCnt_th(int value) {
        this.mCnt_th = value;
    }

    private void setRSSI_th_2(int value) {
        this.mRssi_th_2 = value;
    }

    private void setSNR_th_2(int value) {
        this.mSnr_th_2 = value;
    }

    private void setCnt_th_2(int value) {
        this.mCnt_th_2 = value;
    }

    private void SkipTuning_Value() {
        this.mIsSkipTunigVal = true;
        Log.m96e("FMRadioService", "SkipTuning_Value");
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

    private void setAF_th(int value) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setAF_th(value);
        }
    }

    private int getAF_th() {
        if (!this.mIsExternalChipset) {
            return this.mPlayerNative.getAF_th();
        }
        return -1;
    }

    private void setAFValid_th(int value) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setAFValid_th(value);
        }
    }

    private int getAFValid_th() {
        if (!this.mIsExternalChipset) {
            return this.mPlayerNative.getAFValid_th();
        }
        return -1;
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setFMIntenna(boolean setFMIntenna) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setFMIntenna(setFMIntenna);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public void setSoftmute(boolean setSoftmute) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setSoftmute(setSoftmute);
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean getSoftMuteMode() {
        if (!this.mIsExternalChipset) {
            return this.mPlayerNative.getSoftMuteMode();
        }
        return true;
    }

    private void setSearchAlgoType(int value) {
        if (!this.mIsExternalChipset) {
            this.mPlayerNative.setSearchAlgoType(value);
        }
    }

    private int getSearchAlgoType() {
        return this.mPlayerNative.getSearchAlgoType();
    }

    private void setSINRSamples(int value) {
        this.mPlayerNative.setSINRSamples(value);
    }

    private int getSINRSamples() {
        return this.mPlayerNative.getSINRSamples();
    }

    private void setOnChannelThreshold(int value) {
        this.mPlayerNative.setOnChannelThreshold(value);
    }

    private int getOnChannelThreshold() {
        return this.mPlayerNative.getOnChannelThreshold();
    }

    private void setOffChannelThreshold(int value) {
        this.mPlayerNative.setOffChannelThreshold(value);
    }

    private int getOffChannelThreshold() {
        return this.mPlayerNative.getOffChannelThreshold();
    }

    private void setSINRThreshold(int value) {
        this.mPlayerNative.setSINRThreshold(value);
    }

    private int getSINRThreshold() {
        return this.mPlayerNative.getSINRThreshold();
    }

    private void setCFOTh12(int value) {
        this.mPlayerNative.setCFOTh12(value);
    }

    private int getCFOTh12() {
        return this.mPlayerNative.getCFOTh12();
    }

    private void setRMSSIFirstStage(int value) {
        this.mPlayerNative.setRMSSIFirstStage(value);
    }

    private int getRMSSIFirstStage() {
        return this.mPlayerNative.getRMSSIFirstStage();
    }

    private void setSINRFirstStage(int value) {
        this.mPlayerNative.setSINRFirstStage(value);
    }

    private int getSINRFirstStage() {
        return this.mPlayerNative.getSINRFirstStage();
    }

    private void setAFRMSSIThreshold(int value) {
        this.mPlayerNative.setAFRMSSIThreshold(value);
    }

    private int getAFRMSSIThreshold() {
        return this.mPlayerNative.getAFRMSSIThreshold();
    }

    private void setAFRMSSISamples(int value) {
        this.mPlayerNative.setAFRMSSISamples(value);
    }

    private int getAFRMSSISamples() {
        return this.mPlayerNative.getAFRMSSISamples();
    }

    private void setGoodChannelRMSSIThreshold(int value) {
        this.mPlayerNative.setGoodChannelRMSSIThreshold(value);
    }

    private int getGoodChannelRMSSIThreshold() {
        return this.mPlayerNative.getGoodChannelRMSSIThreshold();
    }

    private void setHybridSearch(String value) {
        this.mPlayerNative.setHybridSearch(value);
    }

    private String getHybridSearch() {
        return this.mPlayerNative.getHybridSearch();
    }

    private void setBlendRmssi(int value) {
        this.mPlayerNative.setBlendRmssi(value);
    }

    private int getBlendRmssi() {
        return this.mPlayerNative.getBlendRmssi();
    }

    private void setBlendSinr(int value) {
        this.mPlayerNative.setBlendSinr(value);
    }

    private int getBlendSinr() {
        return this.mPlayerNative.getBlendSinr();
    }

    private void setFrequencyOffsetThreshold(int value) {
        this.mPlayerNative.setFrequencyOffsetThreshold(value);
    }

    private int getFrequencyOffsetThreshold() {
        return this.mPlayerNative.getFrequencyOffsetThreshold();
    }

    private void setPilotPowerThreshold(int value) {
        this.mPlayerNative.setPilotPowerThreshold(value);
    }

    private int getPilotPowerThreshold() {
        return this.mPlayerNative.getPilotPowerThreshold();
    }

    private void setNoisePowerThreshold(int value) {
        this.mPlayerNative.setNoisePowerThreshold(value);
    }

    private int getNoisePowerThreshold() {
        return this.mPlayerNative.getNoisePowerThreshold();
    }

    private void setSeekDC(int value) {
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.setSeekDC(value);
        } else {
            this.mPlayerNative.setSeekDC(value);
        }
    }

    private int getSeekDC() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getSeekDC();
        }
        return this.mPlayerNative.getSeekDC();
    }

    private void setSeekQA(int value) {
        if (this.mIsExternalChipset) {
            this.mPlayerExternalChipset.setSeekQA(value);
        } else {
            this.mPlayerNative.setSeekQA(value);
        }
    }

    private int getSeekQA() {
        if (this.mIsExternalChipset) {
            return this.mPlayerExternalChipset.getSeekQA();
        }
        return this.mPlayerNative.getSeekQA();
    }

    private void setIFCount1(int value) {
        this.mSlsi_ifcount1 = value;
        this.mPlayerNative.setIFCount1(value);
    }

    private void setIFCount2(int value) {
        this.mSlsi_ifcount2 = value;
        this.mPlayerNative.setIFCount2(value);
    }

    private int getIFCount1() {
        return this.mSlsi_ifcount1;
    }

    private int getIFCount2() {
        return this.mSlsi_ifcount2;
    }

    private void setSoftStereoBlendCoeff(long value) {
        this.mSlsi_blendcoeff = value;
        this.mPlayerNative.setSoftStereoBlendCoeff(value);
    }

    private long getSoftStereoBlendCoeff() {
        return this.mSlsi_blendcoeff;
    }

    private void setSoftMuteCoeff(long value) {
        this.mSlsi_softmutecoeff = value;
        this.mPlayerNative.setSoftMuteCoeff(value);
    }

    private long getSoftMuteCoeff() {
        return this.mSlsi_softmutecoeff;
    }

    private void setSoftStereoBlendRef(long value) {
        this.mSlsi_softstereoblendref = value;
        this.mPlayerNative.setSoftStereoBlendRef(value);
    }

    private long getSoftStereoBlendRef() {
        return this.mSlsi_softstereoblendref;
    }

    private void setSeekDesenseRSSI(int value) {
        this.mMtk_seekdesenserssi = value;
        this.mPlayerNative.setSeekDesenseRSSI(value);
    }

    private int getSeekDesenseRSSI() {
        return this.mMtk_seekdesenserssi;
    }

    private void setSeekSMG(int value) {
        this.mMtk_seeksmg = value;
        this.mPlayerNative.setSeekSMG(value);
    }

    private int getSeekSMG() {
        return this.mMtk_seeksmg;
    }

    private void setSoftmute_th(int value) {
        this.mSoftmute_th = value;
        this.mPlayerNative.setSoftmute_th(value);
    }

    private int getSoftmute_th() {
        return this.mSoftmute_th;
    }

    private void setBlendRSSI_th(int value) {
        this.mMtk_blendrssi_th = value;
        this.mPlayerNative.setBlendRSSI_th(value);
    }

    private int getBlendRSSI_th() {
        return this.mMtk_blendrssi_th;
    }

    private void setBlendPAMD_th(int value) {
        this.mMtk_blendpamd_th = value;
        this.mPlayerNative.setBlendPAMD_th(value);
    }

    private int getBlendPAMD_th() {
        return this.mMtk_blendpamd_th;
    }

    private void setFakeChannel(String value) {
        this.mPlayerNative.setFakeChannel(value);
    }

    private String getFakeChannel() {
        return this.mPlayerNative.getFakeChannel();
    }

    private void setDeSenseList(String value) {
        this.mPlayerNative.setDeSenseList(value);
    }

    private String getDeSenseList() {
        return this.mPlayerNative.getDeSenseList();
    }

    private void setATJ(int bATJOn) {
        this.mMtk_ATJ_config = bATJOn;
        this.mPlayerNative.setATJ(bATJOn);
    }

    private int getATJ() {
        return this.mMtk_ATJ_config;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x02a3, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_AFRMSSI_SAMPLES) != false) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x031e, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_SEEK_QA) != false) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x010e, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_IF_COUNT_2) != false) goto L77;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setIntegerTunningParameter(String parameterName, int value) {
        boolean z;
        if (!isValidPackage()) {
        }
        log("setIntegerTunningParameter:  parameterName- " + parameterName + "  value:- " + value);
        if (parameterName == null) {
            log("setIntegerTunningParameter:  parameterName is null. So do nothing");
            return;
        }
        z = true;
        switch (parameterName) {
            case "RSSI_th":
                setRSSI_th(value);
                break;
            case "SNR_th":
                setSNR_th(value);
                break;
            case "Cnt_th":
                setCnt_th(value);
                break;
            case "SkipTuningValue":
                SkipTuning_Value();
                break;
            default:
                if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                    switch (parameterName.hashCode()) {
                        case -1822358249:
                            if (parameterName.equals(PARAMETER_SEEK_DC)) {
                                z = false;
                                break;
                            }
                            z = -1;
                            break;
                        case -1822357848:
                            break;
                        default:
                            z = -1;
                            break;
                    }
                    switch (z) {
                        case false:
                            setSeekDC(value);
                            break;
                        case true:
                            setSeekQA(value);
                            break;
                        default:
                            log("setIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                    }
                } else {
                    char c = '\t';
                    if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                        switch (parameterName.hashCode()) {
                            case -2006318336:
                                break;
                            case -1620552413:
                                if (parameterName.equals(PARAMETER_GOOD_CH_RMSSI_TH)) {
                                    c = '\n';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1471559147:
                                if (parameterName.equals(PARAMETER_SEARCH_ALGO_TYPE)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1395228053:
                                if (parameterName.equals(PARAMETER_BLEND_SINR)) {
                                    c = 15;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1271273368:
                                if (parameterName.equals(PARAMETER_SINR_FIRST_STAGE)) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1237360035:
                                if (parameterName.equals(PARAMETER_SECOND_CNT_TH)) {
                                    c = '\r';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1075284457:
                                if (parameterName.equals(PARAMETER_OFF_CHANNEL_TH)) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -728425457:
                                if (parameterName.equals(PARAMETER_SECOND_RSSI_TH)) {
                                    c = 11;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -303196099:
                                if (parameterName.equals(PARAMETER_BLEND_RMSSI)) {
                                    c = 14;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 612887239:
                                if (parameterName.equals(PARAMETER_ON_CHANNEL_TH)) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 879837199:
                                if (parameterName.equals(PARAMETER_SINR_SAMPLES)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1051458289:
                                if (parameterName.equals(PARAMETER_SINR_TH)) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1412807169:
                                if (parameterName.equals(PARAMETER_CFO_TH)) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1569063695:
                                if (parameterName.equals(PARAMETER_SECOND_SNR_TH)) {
                                    c = '\f';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1826319004:
                                if (parameterName.equals(PARAMETER_RMSSI_FIRST_STAGE)) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2004862370:
                                if (parameterName.equals(PARAMETER_AFRMSSI_TH)) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                setSearchAlgoType(value);
                                break;
                            case 1:
                                setSINRSamples(value);
                                break;
                            case 2:
                                setOnChannelThreshold(value);
                                break;
                            case 3:
                                if (!this.mIsExternalChipset) {
                                    this.mPlayerNative.setOffChannelThreshold(value);
                                    break;
                                }
                                break;
                            case 4:
                                setSINRThreshold(value);
                                break;
                            case 5:
                                setCFOTh12(value);
                                break;
                            case 6:
                                setRMSSIFirstStage(value);
                                break;
                            case 7:
                                setSINRFirstStage(value);
                                break;
                            case '\b':
                                setAFRMSSIThreshold(value);
                                break;
                            case '\t':
                                setAFRMSSISamples(value);
                                break;
                            case '\n':
                                setGoodChannelRMSSIThreshold(value);
                                break;
                            case 11:
                                setRSSI_th_2(value);
                                break;
                            case '\f':
                                setSNR_th_2(value);
                                break;
                            case '\r':
                                setCnt_th_2(value);
                                break;
                            case 14:
                                setBlendRmssi(value);
                                break;
                            case 15:
                                setBlendSinr(value);
                                break;
                            default:
                                log("setIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        switch (parameterName) {
                            case "FrequencyOffset_th":
                                setFrequencyOffsetThreshold(value);
                                break;
                            case "NoisePower_th":
                                setNoisePowerThreshold(value);
                                break;
                            case "PilotPower_th":
                                setPilotPowerThreshold(value);
                                break;
                            default:
                                log("setIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                        switch (parameterName.hashCode()) {
                            case -1898857345:
                                if (parameterName.equals(PARAMETER_IF_COUNT_1)) {
                                    z = false;
                                    break;
                                }
                                z = -1;
                                break;
                            case -1898857344:
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                setIFCount1(value);
                                break;
                            case true:
                                setIFCount2(value);
                                break;
                            default:
                                log("setIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                        switch (parameterName) {
                            case "SeekDesenseRSSI":
                                setSeekDesenseRSSI(value);
                                break;
                            case "SeekSMG":
                                setSeekSMG(value);
                                break;
                            case "Softmute_th":
                                setSoftmute_th(value);
                                break;
                            case "BlendRSSI_th":
                                setBlendRSSI_th(value);
                                break;
                            case "BlendPAMD_th":
                                setBlendPAMD_th(value);
                                break;
                            case "ATJCofig":
                                setATJ(value);
                                break;
                            default:
                                log("setIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else {
                        log("setIntegerTunningParameter() : this parameter is not support yet - " + parameterName + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                        break;
                    }
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x022f, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_ON_CHANNEL_TH) != false) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0313, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_SEEK_QA) != false) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_PILOT_POWER_TH) != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f1, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_IF_COUNT_2) != false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0166, code lost:
    
        if (r17.equals(com.android.server.FMRadioService.PARAMETER_SOFTMUTE_TH) != false) goto L101;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getIntegerTunningParameter(String parameterName, int defaultValue) {
        char c;
        boolean z;
        log("getIntegerTunningParameter: parameterName- " + parameterName);
        if (parameterName != null) {
            c = 2;
            z = true;
            switch (parameterName) {
                case "RSSI_th":
                    return getRSSI_th();
                case "SNR_th":
                    return getSNR_th();
                case "Cnt_th":
                    return getCnt_th();
                default:
                    if (FMRadioServiceFeature.CHIP_VENDOR == 5 || FMRadioServiceFeature.CHIP_VENDOR == 10) {
                        switch (parameterName.hashCode()) {
                            case -1822358249:
                                if (parameterName.equals(PARAMETER_SEEK_DC)) {
                                    z = false;
                                    break;
                                }
                                z = -1;
                                break;
                            case -1822357848:
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                return getSeekDC();
                            case true:
                                return getSeekQA();
                            default:
                                log("getIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
                        switch (parameterName.hashCode()) {
                            case -2006318336:
                                if (parameterName.equals(PARAMETER_AFRMSSI_SAMPLES)) {
                                    c = '\t';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1620552413:
                                if (parameterName.equals(PARAMETER_GOOD_CH_RMSSI_TH)) {
                                    c = '\n';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1471559147:
                                if (parameterName.equals(PARAMETER_SEARCH_ALGO_TYPE)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1395228053:
                                if (parameterName.equals(PARAMETER_BLEND_SINR)) {
                                    c = 15;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1271273368:
                                if (parameterName.equals(PARAMETER_SINR_FIRST_STAGE)) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1237360035:
                                if (parameterName.equals(PARAMETER_SECOND_CNT_TH)) {
                                    c = '\r';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1075284457:
                                if (parameterName.equals(PARAMETER_OFF_CHANNEL_TH)) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -728425457:
                                if (parameterName.equals(PARAMETER_SECOND_RSSI_TH)) {
                                    c = 11;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -303196099:
                                if (parameterName.equals(PARAMETER_BLEND_RMSSI)) {
                                    c = 14;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 612887239:
                                break;
                            case 879837199:
                                if (parameterName.equals(PARAMETER_SINR_SAMPLES)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1051458289:
                                if (parameterName.equals(PARAMETER_SINR_TH)) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1412807169:
                                if (parameterName.equals(PARAMETER_CFO_TH)) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1569063695:
                                if (parameterName.equals(PARAMETER_SECOND_SNR_TH)) {
                                    c = '\f';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1826319004:
                                if (parameterName.equals(PARAMETER_RMSSI_FIRST_STAGE)) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2004862370:
                                if (parameterName.equals(PARAMETER_AFRMSSI_TH)) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                return getSearchAlgoType();
                            case 1:
                                return getSINRSamples();
                            case 2:
                                return getOnChannelThreshold();
                            case 3:
                                return getOffChannelThreshold();
                            case 4:
                                return getSINRThreshold();
                            case 5:
                                return getCFOTh12();
                            case 6:
                                return getRMSSIFirstStage();
                            case 7:
                                return getSINRFirstStage();
                            case '\b':
                                return getAFRMSSIThreshold();
                            case '\t':
                                return getAFRMSSISamples();
                            case '\n':
                                return getGoodChannelRMSSIThreshold();
                            case 11:
                                return getRSSI_th_2();
                            case '\f':
                                return getSNR_th_2();
                            case '\r':
                                return getCnt_th_2();
                            case 14:
                                return getBlendRmssi();
                            case 15:
                                return getBlendSinr();
                            default:
                                log("getIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 6) {
                        switch (parameterName.hashCode()) {
                            case -2033938168:
                                if (parameterName.equals(PARAMETER_NOISE_POWER_TH)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -752119130:
                                break;
                            case 68091844:
                                if (parameterName.equals(PARAMETER_FREQUENCY_OFFSET_TH)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                return getFrequencyOffsetThreshold();
                            case 1:
                                return getNoisePowerThreshold();
                            case 2:
                                return getPilotPowerThreshold();
                            default:
                                log("getIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                        switch (parameterName.hashCode()) {
                            case -1898857345:
                                if (parameterName.equals(PARAMETER_IF_COUNT_1)) {
                                    z = false;
                                    break;
                                }
                                z = -1;
                                break;
                            case -1898857344:
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                return getIFCount1();
                            case true:
                                return getIFCount2();
                            default:
                                log("getIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
                        switch (parameterName.hashCode()) {
                            case -1731989524:
                                if (parameterName.equals(PARAMETER_SEEK_DESENSE_RSSI)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1416966448:
                                break;
                            case -658516075:
                                if (parameterName.equals(PARAMETER_SEEK_SMG)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -88842741:
                                if (parameterName.equals(PARAMETER_BLEND_RSSI_TH)) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1038261217:
                                if (parameterName.equals(PARAMETER_ATJ_CONFIG)) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1910102394:
                                if (parameterName.equals(PARAMETER_BLEND_PAMD_TH)) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                return getSeekDesenseRSSI();
                            case 1:
                                return getSeekSMG();
                            case 2:
                                return getSoftmute_th();
                            case 3:
                                return getBlendRSSI_th();
                            case 4:
                                return getBlendPAMD_th();
                            case 5:
                                return getATJ();
                            default:
                                log("setIntegerTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                                break;
                        }
                    } else {
                        log("getIntegerTunningParameter() : this parameter is not support yet - " + parameterName + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    }
                    return defaultValue;
            }
        }
        log("getIntegerTunningParameter:  parameterName is null. So do nothing");
        return defaultValue;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b6, code lost:
    
        if (r9.equals(com.android.server.FMRadioService.PARAMETER_SOFT_STEREO_BLEND_COEFF) != false) goto L43;
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setLongTunningParameter(String parameterName, long value) {
        if (!isValidPackage()) {
        }
        log("long setLongTunningParameter: parameterName - " + parameterName + "  value: " + value);
        if (parameterName == null) {
            log("setLongTunningParameter:  parameterName is null. So do nothing");
            return;
        }
        boolean z = false;
        switch (parameterName) {
            case "SeekRSSI":
                setSeekRSSI(value);
                break;
            case "SeekSNR":
                setSeekSNR(value);
                break;
            case "DEConstant":
                setDEConstant(value);
                break;
            default:
                log("setLongTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                break;
        }
        if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
            switch (parameterName.hashCode()) {
                case -681786198:
                    break;
                case 1746788740:
                    if (parameterName.equals(PARAMETER_SOFT_STEREO_BLEND_REF)) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case 1777837110:
                    if (parameterName.equals(PARAMETER_SOFTMUTE_COEFF)) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    setSoftStereoBlendCoeff(value);
                    break;
                case true:
                    setSoftMuteCoeff(value);
                    break;
                case true:
                    setSoftStereoBlendRef(value);
                    break;
                default:
                    log("setLongTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0097, code lost:
    
        if (r8.equals(com.android.server.FMRadioService.PARAMETER_SOFT_STEREO_BLEND_COEFF) != false) goto L36;
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long getLongTunningParameter(String parameterName, long defaultValue) {
        char c;
        log("long getLongTunningParameter: parameterName - " + parameterName);
        if (parameterName == null) {
            log("getLongTunningParameter:  parameterName is null. So do nothing");
            return defaultValue;
        }
        c = 0;
        switch (parameterName) {
            case "CurrentSNR":
                return getCurrentSNR();
            case "CurrentRSSI":
                return getCurrentRSSI();
            default:
                log("getLongTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                if (FMRadioServiceFeature.CHIP_VENDOR == 7) {
                    switch (parameterName.hashCode()) {
                        case -681786198:
                            break;
                        case 1746788740:
                            if (parameterName.equals(PARAMETER_SOFT_STEREO_BLEND_REF)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1777837110:
                            if (parameterName.equals(PARAMETER_SOFTMUTE_COEFF)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            return getSoftStereoBlendCoeff();
                        case 1:
                            return getSoftMuteCoeff();
                        case 2:
                            return getSoftStereoBlendRef();
                        default:
                            log("getLongTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                            break;
                    }
                }
                return defaultValue;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
    
        if (r7.equals(com.android.server.FMRadioService.PARAMETER_FAKE_CHANNEL) != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c4, code lost:
    
        if (r7.equals(com.android.server.FMRadioService.PARAMETER_HYBRID_SEARCH) != false) goto L39;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setStringTunningParameter(String parameterName, String value) {
        if (!isValidPackage()) {
            return;
        }
        log("setStringTunningParameter: parameterName - " + parameterName + "  value: " + value);
        if (parameterName == null) {
            log("setStringTunningParameter:  parameterName is null. So do nothing");
            return;
        }
        boolean z = false;
        if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
            switch (parameterName.hashCode()) {
                case -313550108:
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    setHybridSearch(value);
                    break;
                default:
                    log("setStringTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
            }
            return;
        }
        if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
            switch (parameterName.hashCode()) {
                case -2137129525:
                    if (parameterName.equals(PARAMETER_DESENSE_LIST)) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case -904795186:
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    setFakeChannel(value);
                    break;
                case true:
                    setDeSenseList(value);
                    break;
                default:
                    log("setStringTunningParameter() : invalid parameterName - " + parameterName + " for chip vendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
            }
            return;
        }
        log("setStringTunningParameter() : this parameter is not support yet - " + parameterName + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0048, code lost:
    
        if (r7.equals(com.android.server.FMRadioService.PARAMETER_FAKE_CHANNEL) != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
    
        if (r7.equals(com.android.server.FMRadioService.PARAMETER_HYBRID_SEARCH) != false) goto L37;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getStringTunningParameter(String parameterName, String defaultValue) {
        log("getStringTunningParameter: parameterName - " + parameterName);
        if (parameterName == null) {
            log("getStringTunningParameter:  parameterName is null. So do nothing");
            return defaultValue;
        }
        boolean z = false;
        if (FMRadioServiceFeature.CHIP_VENDOR == 4 || FMRadioServiceFeature.CHIP_VENDOR == 9) {
            switch (parameterName.hashCode()) {
                case -313550108:
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    return getHybridSearch();
                default:
                    log("getStringTunningParameter() : invalid parameterName - " + parameterName + " for chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
            }
        } else if (FMRadioServiceFeature.CHIP_VENDOR == 8) {
            switch (parameterName.hashCode()) {
                case -2137129525:
                    if (parameterName.equals(PARAMETER_DESENSE_LIST)) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case -904795186:
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    return getFakeChannel();
                case true:
                    return getDeSenseList();
                default:
                    log("getStringTunningParameter() : invalid parameterName - " + parameterName + " for chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
                    break;
            }
        } else {
            log("getStringTunningParameter() : this parameter is not support yet - " + parameterName + " chipvendor - " + FMRadioServiceFeature.CHIP_VENDOR);
        }
        return defaultValue;
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
            this.mScanProgress = false;
        } catch (Error e) {
            Log.m96e("FMRadioService", "Exception in finalize() : " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSignalSetting(int rssi, int snr, int cnt) {
        if (this.mIsOn && !this.mIsExternalChipset) {
            this.mPlayerNative.setRSSI_th(rssi);
            this.mPlayerNative.setSNR_th(snr);
            this.mPlayerNative.setCnt_th(cnt);
        }
    }

    class ScanThread extends Thread {
        ScanThread() {
        }

        private void doScan() throws InterruptedException {
            long j;
            long j2;
            long j3;
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
            int i = 9;
            int i2 = 4;
            long j4 = 0;
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
                long freq = FMRadioService.this.searchAll();
                if (FMRadioService.DEBUGGABLE) {
                    FMRadioService.log("Found channel :" + freq);
                }
                if (FMRadioServiceFeature.CHIP_VENDOR != i2 && FMRadioServiceFeature.CHIP_VENDOR != i && !FMRadioService.this.mIsExternalChipset && FMRadioService.this.mScanChannelList.contains(Long.valueOf(freq))) {
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("Duplicate channel :" + freq);
                    }
                    FMRadioService fMRadioService = FMRadioService.this;
                    fMRadioService.notifyEvent(3, fMRadioService.mScanChannelList.toArray(new Long[0]));
                    Thread.sleep(20L);
                } else if (freq <= j4) {
                    FMRadioService.log("Testmode Skipp value : " + FMRadioService.this.mIsSkipTunigVal);
                    if (FMRadioService.this.mIsExternalChipset) {
                        FMRadioService.this.mPlayerExternalChipset.stopNotifyThread(true);
                    }
                    FMRadioService fMRadioService2 = FMRadioService.this;
                    fMRadioService2.notifyEvent(3, fMRadioService2.mScanChannelList.toArray(new Long[0]));
                    Thread.sleep(20L);
                } else {
                    if (FMRadioService.this.mScanFreq <= j4) {
                        FMRadioService.this.mScanFreq = freq;
                    }
                    if (FMRadioServiceFeature.CHIP_VENDOR != i2 && FMRadioServiceFeature.CHIP_VENDOR != i) {
                        if (FMRadioService.this.mIsExternalChipset) {
                            j = 90000;
                            j2 = 108000;
                        } else {
                            if (FMRadioService.this.mScanProgress) {
                                FMRadioService.this.mScanChannelList.add(Long.valueOf(freq));
                                FMRadioService.this.notifyEvent(1, Long.valueOf(freq));
                                if (FMRadioService.this.mWaitPidDuringScanning && FMRadioService.this.mScanThread != null) {
                                    synchronized (FMRadioService.this.mScanThread) {
                                        FMRadioService.this.mScanThread.wait(250L);
                                    }
                                }
                            }
                            if (FMRadioService.this.mBand == 1 || FMRadioService.this.mBand == 2) {
                                j2 = 108000;
                                if (freq == 108000) {
                                    break;
                                }
                            } else {
                                j2 = 108000;
                            }
                            if (FMRadioService.this.mBand == 3) {
                                j = 90000;
                                if (freq == 90000) {
                                    break;
                                }
                            } else {
                                j = 90000;
                            }
                            j3 = 87500;
                            i = 9;
                            i2 = 4;
                            j4 = 0;
                        }
                    } else {
                        j = 90000;
                        j2 = 108000;
                    }
                    FMRadioService.this.mCurrentFoundFreq = freq;
                    if (FMRadioService.DEBUGGABLE) {
                        FMRadioService.log("scanning current and prev freq:" + FMRadioService.this.mCurrentFoundFreq + ", " + FMRadioService.this.mPreviousFoundFreq);
                    }
                    if (FMRadioService.this.mPreviousFoundFreq >= FMRadioService.this.mCurrentFoundFreq) {
                        FMRadioService.log("scanning finish");
                        if (FMRadioService.this.mCurrentFoundFreq == 87500) {
                            FMRadioService.this.mScanChannelList.add(Long.valueOf(freq));
                            FMRadioService.this.notifyEvent(1, Long.valueOf(freq));
                        }
                        if (FMRadioService.this.mIsExternalChipset) {
                            FMRadioService.this.mPlayerExternalChipset.stopNotifyThread(true);
                        }
                        FMRadioService fMRadioService3 = FMRadioService.this;
                        fMRadioService3.notifyEvent(3, fMRadioService3.mScanChannelList.toArray(new Long[0]));
                        Thread.sleep(20L);
                    } else {
                        j3 = 87500;
                        if (FMRadioService.this.mScanProgress) {
                            FMRadioService.log("scanning found channel");
                            FMRadioService fMRadioService4 = FMRadioService.this;
                            fMRadioService4.mPreviousFoundFreq = fMRadioService4.mCurrentFoundFreq;
                            FMRadioService.this.mScanChannelList.add(Long.valueOf(freq));
                            FMRadioService.this.notifyEvent(1, Long.valueOf(freq));
                            if (FMRadioService.this.mWaitPidDuringScanning && FMRadioService.this.mScanThread != null) {
                                synchronized (FMRadioService.this.mScanThread) {
                                    FMRadioService.this.mScanThread.wait(250L);
                                }
                            }
                        } else {
                            continue;
                        }
                        i = 9;
                        i2 = 4;
                        j4 = 0;
                    }
                }
            }
            FMRadioService fMRadioService5 = FMRadioService.this;
            fMRadioService5.notifyEvent(3, fMRadioService5.mScanChannelList.toArray(new Long[0]));
            Thread.sleep(20L);
            if (FMRadioService.this.mWaitPidDuringScanning && !FMRadioService.this.mIsExternalChipset) {
                FMRadioService.this.mPlayerNative.setScanning(false);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x012d, code lost:
        
            if (r1.isHeld() != false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0162, code lost:
        
            com.android.server.FMRadioService.log("Scanning Thread work is done...");
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0167, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x015c, code lost:
        
            r1.release();
            com.android.server.FMRadioService.log("Scan thread released the dimmed screen lock");
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x015a, code lost:
        
            if (r1.isHeld() == false) goto L42;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            PowerManager.WakeLock wakelock = FMRadioService.this.mPowerManager.newWakeLock(536870913, "FMRadio Service Scan Thread");
            wakelock.acquire();
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
                    if (!FMRadioService.this.mIsExternalChipset) {
                        doScan();
                    } else if (FMRadioService.this.mPlayerExternalChipset.startNotifyThread(true)) {
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
                    Log.m96e("FMRadioService", "Exception in run() : " + e);
                    FMRadioService.this.mScanProgress = false;
                    FMRadioService.this.mScanThread = null;
                }
            } catch (Throwable th) {
                FMRadioService.this.mScanProgress = false;
                FMRadioService.this.mScanThread = null;
                if (wakelock.isHeld()) {
                    wakelock.release();
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
        boolean dndOn = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.ZEN_MODE, 0) == 1;
        NotificationManager mNm = (NotificationManager) this.mContext.getSystemService("notification");
        NotificationManager.Policy zenPolicy = mNm.getNotificationPolicy();
        boolean muteMedia = (zenPolicy.priorityCategories & 64) == 0;
        return dndOn && muteMedia;
    }

    public boolean isAllSoundOff() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFMAudioPath(boolean isOn) {
        String keyValuePairs;
        log("setFMAudioPath : " + isOn + " mIsFMAudioPathOn : " + this.mIsFMAudioPathOn);
        if (isOn == this.mIsFMAudioPathOn) {
            return;
        }
        this.mIsFMAudioPathOn = isOn;
        if (isOn) {
            keyValuePairs = "g_fmradio_enable=true";
        } else {
            keyValuePairs = "g_fmradio_enable=false";
        }
        this.mAudioManager.setParameters(keyValuePairs);
    }

    private boolean isCherokeeChip() {
        return FMRadioServiceFeature.CHIP_VENDOR == 9;
    }

    private void setSlimbusEnable(int mode) {
        log("setSlimbusEnable " + mode);
        log("isCherokeeChip: " + isCherokeeChip() + " volumeLock: " + this.volumeLock);
        if (isCherokeeChip() && !this.volumeLock) {
            this.mPlayerNative.setSlimbusEnable(mode);
        } else {
            log("setSlimbusEnable : Not applicable");
        }
    }

    @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
    public boolean isDeviceSpeakerEnabled() {
        return this.mIsMDMSpeakerEnabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDelay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Log.m96e("FMRadioService", "InterruptedException in sleep() : " + e);
        }
    }

    public boolean isUnMuteRadio() {
        return (this.mAudioManager.getStreamVolume(AudioManager.semGetStreamType(1)) <= 0 || isAllSoundOff() || isDNDEnable()) ? false : true;
    }

    public boolean isPathSupportSoftmute(int path) {
        if (!"Both".equals(this.mSoftmutePath)) {
            if (!"Speaker".equals(this.mSoftmutePath) || path != 2) {
                if ("Headset".equals(this.mSoftmutePath) && path == 3) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private class SamsungAnalyticsRunnable implements Runnable {
        private String packageName;
        private String version;

        public SamsungAnalyticsRunnable(String PackageName, String Version) {
            this.packageName = PackageName;
            this.version = Version;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                FMRadioService.this.sendInfoSamsungAnalytics(this.packageName, this.version);
            } catch (Exception e) {
                Log.m96e("FMRadioService", "SamsungAnalyticsRunnable Exception: " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInfoSamsungAnalytics(String packageName, String version) {
        log("sendInfoSamsungAnalytics ,packageName : " + packageName + ", version : " + version);
        Bundle bundle = new Bundle();
        bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SA_TRACKING_ID);
        bundle.putString("feature", SA_FEATURE);
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("sm_sdk_id", SA_SM_SDK_ID);
            jobj.put("sm_sdk_client_pkg_name", packageName);
            jobj.put("sm_sdk_client_pkg_version", version);
        } catch (JSONException e) {
            Log.m96e("FMRadioService", "JSONException: " + e);
        }
        log("SALog jsonstring: " + jobj.toString());
        bundle.putString(SemShareConstants.SURVEY_CONTENT_EXTRA, jobj.toString());
        bundle.putString(SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE, SA_SERVICE_PACKAGE);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        broadcastIntent.putExtras(bundle);
        broadcastIntent.setPackage("com.sec.android.diagmonagent");
        log("SALog sendbroadcast");
        this.mContext.sendBroadcast(broadcastIntent);
    }
}
