package android.preference;

import android.app.NotificationManager;
import android.app.PendingIntent$$ExternalSyntheticLambda1;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.database.ContentObserver;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.media.audiopolicy.AudioProductStrategy;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.Vibrator;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.SeekBar;
import com.android.internal.p029os.SomeArgs;
import com.android.internal.telecom.ITelecomService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

@Deprecated
/* loaded from: classes3.dex */
public class SeekBarVolumizer implements SeekBar.OnSeekBarChangeListener, Handler.Callback {
  private static final int BROADCAST_WITHOUT_LE_DEVICE = 2;
  private static final int BROADCAST_WITH_LE_DEVICE = 1;
  private static final int CHECK_RINGTONE_PLAYBACK_DELAY_MS = 1000;
  private static final int CHECK_UPDATE_SLIDER_LATER_MS = 500;
  private static final int FINEVOLUME_MAX_INDEX = 150;
  private static final int MSG_GROUP_VOLUME_CHANGED = 1;
  private static final int MSG_INIT_SAMPLE = 3;
  private static final int MSG_SET_STREAM_VOLUME = 0;
  private static final int MSG_START_SAMPLE = 1;
  private static final int MSG_STOP_SAMPLE = 2;
  private static final int MSG_UPDATE_SLIDER_MAYBE_LATER = 4;
  private static final int NO_BROADCAST = 0;
  private static final String TAG = "SeekBarVolumizer";
  private final String SIM_CHANGED_ACTION;
  private final String VOLUME_CHANGED_ACTION;
  private boolean isRingerUpdatedToAudio;
  private boolean mAffectedByRingerMode;
  private boolean mAllowAlarms;
  private boolean mAllowMedia;
  private boolean mAllowRinger;
  private boolean mAllowSystem;
  private AudioAttributes mAttributes;
  private final AudioManager mAudioManager;
  private final Callback mCallback;
  private final Context mContext;
  private int mCurrentSimSlot;
  private Uri mDefaultUri;
  private final boolean mDeviceHasProductStrategies;
  private int mEditMode;
  private Handler mHandler;
  private int mLastAudibleStreamVolume;
  private int mLastProgress;
  private int mLastWaitingToneVolume;
  private int mMaxStreamVolume;
  private boolean mMuted;
  private final NotificationManager mNotificationManager;
  private boolean mNotificationOrRing;
  private NotificationManager.Policy mNotificationPolicy;
  private int mOriginalLastAudibleStreamVolume;
  private int mOriginalNotificationVolume;
  private int mOriginalRingerMode;
  private int mOriginalStreamVolume;
  private int mOriginalSystemVolume;
  private boolean mPlaySample;
  private int mProfileMode;
  private final Receiver mReceiver;
  private int mRingerMode;
  private Ringtone mRingtone;
  private SeekBar mSeekBar;
  private final int mStreamType;
  private boolean mSystemSampleStarted;
  private ITelecomService mTelecomService;
  private ToneGenerator mToneGenerator;
  private final HandlerC2490H mUiHandler;
  private Vibrator mVibrator;
  private boolean mVoiceCapable;
  private int mVolumeBeforeMute;
  private final AudioManager.VolumeGroupCallback mVolumeGroupCallback;
  private int mVolumeGroupId;
  private final Handler mVolumeHandler;
  private Observer mVolumeObserver;
  private int mZenMode;
  private static long sStopVolumeTime = 0;
  private static final long SET_STREAM_VOLUME_DELAY_MS = TimeUnit.MILLISECONDS.toMillis(500);
  private static final long START_SAMPLE_DELAY_MS = TimeUnit.MILLISECONDS.toMillis(500);
  private static final long DURATION_TO_START_DELAYING = TimeUnit.MILLISECONDS.toMillis(2000);

  public interface Callback {
    void onMuted(boolean z, boolean z2);

    void onProgressChanged(SeekBar seekBar, int i, boolean z);

    void onSampleStarting(SeekBarVolumizer seekBarVolumizer);

    void onStartTrackingTouch(SeekBarVolumizer seekBarVolumizer);

    default void onStopTrackingTouch(SeekBarVolumizer sbv) {}
  }

  public SeekBarVolumizer(Context context, int streamType, Uri defaultUri, Callback callback) {
    this(context, streamType, defaultUri, callback, true);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public SeekBarVolumizer(Context context, int i, Uri uri, Callback callback, boolean z) {
    this.mVolumeHandler = new VolumeHandler();
    this.mVolumeGroupCallback =
        new AudioManager
            .VolumeGroupCallback() { // from class: android.preference.SeekBarVolumizer.1
          @Override // android.media.AudioManager.VolumeGroupCallback
          public void onAudioVolumeGroupChanged(int group, int flags) {
            if (SeekBarVolumizer.this.mHandler == null) {
              return;
            }
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = Integer.valueOf(group);
            args.arg2 = Integer.valueOf(flags);
            SeekBarVolumizer.this.mVolumeHandler.sendMessage(
                SeekBarVolumizer.this.mHandler.obtainMessage(1, args));
          }
        };
    this.mUiHandler = new HandlerC2490H();
    this.mReceiver = new Receiver();
    this.mLastProgress = -1;
    this.mVolumeBeforeMute = -1;
    this.mLastWaitingToneVolume = -1;
    this.mToneGenerator = null;
    this.isRingerUpdatedToAudio = true;
    this.VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    this.SIM_CHANGED_ACTION = "com.samsung.intent.action.DEFAULT_CS_SIM_CHANGED";
    this.mContext = context;
    AudioManager audioManager = (AudioManager) context.getSystemService(AudioManager.class);
    this.mAudioManager = audioManager;
    boolean hasAudioProductStrategies = hasAudioProductStrategies();
    this.mDeviceHasProductStrategies = hasAudioProductStrategies;
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(NotificationManager.class);
    this.mNotificationManager = notificationManager;
    this.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    this.mVoiceCapable = isVoiceCapable();
    NotificationManager.Policy consolidatedNotificationPolicy =
        notificationManager.getConsolidatedNotificationPolicy();
    this.mNotificationPolicy = consolidatedNotificationPolicy;
    this.mAllowAlarms = (consolidatedNotificationPolicy.priorityCategories & 32) != 0;
    this.mAllowMedia = (this.mNotificationPolicy.priorityCategories & 64) != 0;
    this.mAllowRinger =
        !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(this.mNotificationPolicy);
    this.mAllowSystem = (this.mNotificationPolicy.priorityCategories & 128) != 0;
    this.mStreamType = i;
    this.mTelecomService = getTelecomService();
    if (i == 3) {
      this.mMaxStreamVolume = 1500;
      this.mOriginalStreamVolume = audioManager.semGetFineVolume(3);
    } else {
      this.mMaxStreamVolume = audioManager.getStreamMaxVolume(i) * 100;
      this.mOriginalStreamVolume = audioManager.getStreamVolume(i);
    }
    this.mAffectedByRingerMode = audioManager.isStreamAffectedByRingerMode(i);
    boolean isNotificationOrRing = isNotificationOrRing(i);
    this.mNotificationOrRing = isNotificationOrRing;
    if (isNotificationOrRing) {
      this.mRingerMode = audioManager.getRingerModeInternal();
    }
    this.mZenMode = notificationManager.getZenMode();
    if (hasAudioProductStrategies) {
      this.mVolumeGroupId = getVolumeGroupIdForLegacyStreamType(i);
      this.mAttributes = getAudioAttributesForLegacyStreamType(i);
    }
    this.mCallback = callback;
    this.mLastWaitingToneVolume =
        Settings.System.getInt(
            context.getContentResolver(), Settings.System.VOLUME_WAITING_TONE, 2);
    this.mOriginalRingerMode = audioManager.getRingerMode();
    this.mLastAudibleStreamVolume = audioManager.getLastAudibleStreamVolume(i);
    boolean isStreamMute = audioManager.isStreamMute(i);
    this.mMuted = isStreamMute;
    this.mPlaySample = z;
    if (callback != null) {
      callback.onMuted(isStreamMute, isZenMuted());
    }
    this.mCurrentSimSlot =
        SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId());
    if (uri == null) {
      if (i == 2) {
        uri = getDefaultRingtoneUri();
      } else if (i == 5) {
        uri = getDefaultNotificationUri();
      } else {
        uri = Settings.System.DEFAULT_ALARM_ALERT_URI;
      }
    }
    this.mDefaultUri = uri;
    if (i == 5 && this.mMuted) {
      this.mOriginalNotificationVolume = audioManager.getLastAudibleStreamVolume(i);
      return;
    }
    if (i == 1 && this.mMuted) {
      this.mOriginalSystemVolume = audioManager.getLastAudibleStreamVolume(i);
    } else if (i == 2 && this.mMuted) {
      this.mOriginalLastAudibleStreamVolume = audioManager.getLastAudibleStreamVolume(i);
    }
  }

  private boolean hasAudioProductStrategies() {
    return AudioManager.getAudioProductStrategies().size() > 0;
  }

  private int getVolumeGroupIdForLegacyStreamType(int streamType) {
    for (AudioProductStrategy productStrategy : AudioManager.getAudioProductStrategies()) {
      int volumeGroupId = productStrategy.getVolumeGroupIdForLegacyStreamType(streamType);
      if (volumeGroupId != -1) {
        return volumeGroupId;
      }
    }
    return ((Integer)
            AudioManager.getAudioProductStrategies().stream()
                .map(
                    new Function() { // from class:
                                     // android.preference.SeekBarVolumizer$$ExternalSyntheticLambda0
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Integer valueOf;
                        valueOf =
                            Integer.valueOf(
                                ((AudioProductStrategy) obj)
                                    .getVolumeGroupIdForAudioAttributes(
                                        AudioProductStrategy.getDefaultAttributes()));
                        return valueOf;
                      }
                    })
                .filter(
                    new Predicate() { // from class:
                                      // android.preference.SeekBarVolumizer$$ExternalSyntheticLambda1
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return SeekBarVolumizer.lambda$getVolumeGroupIdForLegacyStreamType$1(
                            (Integer) obj);
                      }
                    })
                .findFirst()
                .orElse(-1))
        .intValue();
  }

  static /* synthetic */ boolean lambda$getVolumeGroupIdForLegacyStreamType$1(
      Integer volumeGroupId) {
    return volumeGroupId.intValue() != -1;
  }

  private AudioAttributes getAudioAttributesForLegacyStreamType(int streamType) {
    for (AudioProductStrategy productStrategy : AudioManager.getAudioProductStrategies()) {
      AudioAttributes aa = productStrategy.getAudioAttributesForLegacyStreamType(streamType);
      if (aa != null) {
        return aa;
      }
    }
    return new AudioAttributes.Builder().setContentType(0).setUsage(0).build();
  }

  private ColorStateList colorToColorStateList(int color) {
    int[][] EMPTY = {new int[0]};
    return new ColorStateList(EMPTY, new int[] {color});
  }

  /* JADX INFO: Access modifiers changed from: private */
  public Uri getDefaultRingtoneUri() {
    if (this.mCurrentSimSlot == 1) {
      return Settings.System.DEFAULT_RINGTONE_URI_2;
    }
    return Settings.System.DEFAULT_RINGTONE_URI;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public Uri getDefaultNotificationUri() {
    if (this.mCurrentSimSlot == 1) {
      return Settings.System.DEFAULT_NOTIFICATION_URI_2;
    }
    return Settings.System.DEFAULT_NOTIFICATION_URI;
  }

  private boolean isNotificationOrRing(int stream) {
    return this.mVoiceCapable ? stream == 2 : stream == 5;
  }

  private boolean isNotificationStream(int stream) {
    return stream == 5;
  }

  private static boolean isAlarmsStream(int stream) {
    return stream == 4;
  }

  private static boolean isMediaStream(int stream) {
    return stream == 3;
  }

  private static boolean isAssistantStream(int stream) {
    return stream == 11;
  }

  private static boolean isSystemStream(int stream) {
    return stream == 1;
  }

  public void setSeekBar(SeekBar seekBar) {
    SeekBar seekBar2 = this.mSeekBar;
    if (seekBar2 != null) {
      seekBar2.setOnSeekBarChangeListener(null);
    }
    this.mSeekBar = seekBar;
    seekBar.setOnSeekBarChangeListener(null);
    if (this.mStreamType == 8) {
      seekBar.setMax(4);
      SeekBar seekBar3 = this.mSeekBar;
      int i = this.mLastProgress;
      if (i <= -1) {
        i = this.mLastWaitingToneVolume;
      }
      seekBar3.setProgress(i);
    } else {
      this.mSeekBar.setMax(this.mMaxStreamVolume);
      updateSeekBar();
    }
    this.mSeekBar.setOnSeekBarChangeListener(this);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean isZenMuted() {
    int i;
    if ((this.mNotificationOrRing && this.mZenMode == 3) || (i = this.mZenMode) == 2) {
      return true;
    }
    if (i == 1) {
      if (!this.mAllowAlarms && isAlarmsStream(this.mStreamType)) {
        return true;
      }
      if (!this.mAllowMedia
          && (isMediaStream(this.mStreamType) || isAssistantStream(this.mStreamType))) {
        return true;
      }
      if (!this.mAllowRinger
          && isNotificationStream(this.mStreamType)
          && this.mAudioManager.shouldShowRingtoneVolume()) {
        return true;
      }
      if (!this.mAllowSystem && isSystemStream(this.mStreamType)) {
        return true;
      }
    }
    return false;
  }

  protected void updateSeekBar() {
    int i;
    this.mMuted = this.mAudioManager.isStreamMute(this.mStreamType);
    int ringerModeInternal = this.mAudioManager.getRingerModeInternal();
    this.mRingerMode = ringerModeInternal;
    boolean z = this.mNotificationOrRing;
    if (z && ringerModeInternal != 2) {
      this.mSeekBar.setProgress(0);
      return;
    }
    if (this.mMuted && !z && (i = this.mStreamType) != 3 && i != 11 && ringerModeInternal != 2) {
      this.mSeekBar.setEnabled(false);
      this.mSeekBar.setProgress(0);
      return;
    }
    int i2 = this.mStreamType;
    AudioManager audioManager = this.mAudioManager;
    if (i2 == 3) {
      int media_volume = audioManager.semGetFineVolume(3);
      int progress = this.mSeekBar.getProgress();
      this.mLastProgress = progress;
      if (progress != 0) {
        this.mLastProgress = getImpliedMediaVolumeLevel(progress);
      }
      if (this.mLastProgress != media_volume) {
        this.mSeekBar.setProgress(media_volume * 10);
        this.mLastProgress = media_volume;
      }
    } else {
      int impliedLevel = getImpliedLevel(this.mSeekBar.getProgress());
      this.mLastProgress = impliedLevel;
      if (impliedLevel != this.mAudioManager.getStreamVolume(this.mStreamType)) {
        this.mSeekBar.setProgress(this.mAudioManager.getStreamVolume(this.mStreamType) * 100);
        this.mLastProgress = getImpliedLevel(this.mSeekBar.getProgress());
      }
    }
    if (isZenMuted() || isMuteAllSoundEnabled() || isAuraCastSeekBarDisabled()) {
      if (this.mSeekBar.isEnabled()) {
        this.mSeekBar.setEnabled(false);
      }
    } else if (!this.mSeekBar.isEnabled()) {
      this.mSeekBar.setEnabled(true);
    }
  }

  public void setSeekBarVolume(int volume) {
    postSetVolume(volume);
    this.isRingerUpdatedToAudio = false;
  }

  @Override // android.os.Handler.Callback
  public boolean handleMessage(Message msg) {
    switch (msg.what) {
      case 0:
        boolean z = this.mMuted;
        if (z && this.mLastProgress > 0) {
          this.mAudioManager.adjustStreamVolume(this.mStreamType, 100, 0);
        } else if (!z && this.mLastProgress == 0) {
          this.mAudioManager.adjustStreamVolume(this.mStreamType, -100, 0);
        }
        this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 1024);
        this.isRingerUpdatedToAudio = true;
        if (this.mLastProgress == 0 && !this.mVibrator.hasVibrator()) {
          int i = this.mStreamType;
          if (i == 2) {
            this.mAudioManager.setRingerMode(0);
          } else if (!this.mVoiceCapable && i == 5) {
            this.mAudioManager.setRingerMode(0);
          }
        }
        return true;
      case 1:
        if (this.mPlaySample) {
          onStartSample();
        }
        return true;
      case 2:
        if (this.mPlaySample) {
          onStopSample();
        }
        return true;
      case 3:
        if (this.mPlaySample) {
          onInitSample();
        }
        return true;
      case 4:
        onUpdateSliderMaybeLater();
        return true;
      default:
        Log.m96e(TAG, "invalid SeekBarVolumizer message: " + msg.what);
        return true;
    }
  }

  private void onInitSample() {
    synchronized (this) {
      try {
        Ringtone ringtone = RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
        this.mRingtone = ringtone;
        if (ringtone != null) {
          ringtone.turnOffFadeIn();
          if (this.mStreamType != 11) {
            this.mRingtone.setAudioAttributes(
                new AudioAttributes.Builder()
                    .setInternalLegacyStreamType(this.mStreamType)
                    .build());
          } else {
            this.mRingtone.setAudioAttributes(
                new AudioAttributes.Builder()
                    .semAddAudioTag("BIXBY")
                    .setInternalLegacyStreamType(this.mStreamType)
                    .build());
          }
        }
      } catch (Exception e) {
        Log.m94d(TAG, "Exception happens in onInitSample() : " + e.toString());
      }
    }
  }

  private void postStartSample() {
    long j;
    if (this.mHandler == null || isUserInCall(this.mContext)) {
      return;
    }
    if (isSamplePlaying() && this.mStreamType == 11) {
      return;
    }
    this.mHandler.removeMessages(1);
    Handler handler = this.mHandler;
    Message obtainMessage = handler.obtainMessage(1);
    if (isSamplePlaying()) {
      j = 1000;
    } else {
      j = isDelay() ? START_SAMPLE_DELAY_MS : 0L;
    }
    handler.sendMessageDelayed(obtainMessage, j);
  }

  private void onUpdateSliderMaybeLater() {
    if (isDelay()) {
      postUpdateSliderMaybeLater();
    } else {
      updateSlider();
    }
  }

  private void postUpdateSliderMaybeLater() {
    Handler handler = this.mHandler;
    if (handler == null) {
      return;
    }
    handler.removeMessages(4);
    Handler handler2 = this.mHandler;
    handler2.sendMessageDelayed(handler2.obtainMessage(4), 500L);
  }

  private boolean isDelay() {
    long durationTime = System.currentTimeMillis() - sStopVolumeTime;
    return durationTime >= 0 && durationTime < DURATION_TO_START_DELAYING;
  }

  private void setStopVolumeTime() {
    int i = this.mStreamType;
    if (i == 0 || i == 2 || i == 5 || i == 4) {
      sStopVolumeTime = System.currentTimeMillis();
    }
  }

  private void onStartSample() {
    if (!isSamplePlaying()) {
      Callback callback = this.mCallback;
      if (callback != null) {
        callback.onSampleStarting(this);
      }
      synchronized (this) {
        if (this.mRingtone != null) {
          try {
            String opPackageName = this.mContext.getOpPackageName();
            String featureId = this.mContext.getAttributionTag();
            ITelecomService iTelecomService = this.mTelecomService;
            if (iTelecomService != null) {
              try {
                if (!iTelecomService.isRinging(opPackageName)
                    && !this.mTelecomService.isInCall(opPackageName, featureId)) {}
                Log.m94d(TAG, "don't play ringtone in volumepreference: return called");
                return;
              } catch (RemoteException ex) {
                Log.m103w(TAG, "ITelephony threw RemoteException", ex);
              }
            }
            int i = this.mStreamType;
            if (i != 1 && i != 8 && !this.mAudioManager.semIsFmRadioActive()) {
              Log.m94d(TAG, "sample : mRingtone.play()");
              Ringtone ringtone = this.mRingtone;
              ringtone.setAudioAttributes(
                  new AudioAttributes.Builder(ringtone.getAudioAttributes()).setFlags(128).build());
              this.mRingtone.play();
            }
          } catch (Throwable e) {
            Log.m103w(TAG, "Error playing ringtone, stream " + this.mStreamType, e);
          }
        }
      }
    }
  }

  private void postStopSample() {
    if (this.mHandler == null) {
      return;
    }
    setStopVolumeTime();
    this.mHandler.removeMessages(1);
    this.mHandler.removeMessages(2);
    Handler handler = this.mHandler;
    handler.sendMessage(handler.obtainMessage(2));
  }

  private void onStopSample() {
    synchronized (this) {
      Ringtone ringtone = this.mRingtone;
      if (ringtone != null) {
        ringtone.stop();
      }
    }
  }

  public void stop() {
    if (this.mHandler == null) {
      return;
    }
    postStopSample();
    stopToneGenerator();
    ToneGenerator toneGenerator = this.mToneGenerator;
    if (toneGenerator != null) {
      toneGenerator.release();
      this.mToneGenerator = null;
    }
    this.mContext.getContentResolver().unregisterContentObserver(this.mVolumeObserver);
    this.mReceiver.setListening(false);
    if (this.mDeviceHasProductStrategies) {
      unregisterVolumeGroupCb();
    }
    this.mSeekBar.setOnSeekBarChangeListener(null);
    this.mHandler.getLooper().quitSafely();
    this.mHandler = null;
    this.mVolumeObserver = null;
  }

  public void start() {
    if (this.mHandler != null) {
      return;
    }
    HandlerThread thread = new HandlerThread("SeekBarVolumizer.CallbackHandler");
    thread.start();
    Handler handler = new Handler(thread.getLooper(), this);
    this.mHandler = handler;
    handler.sendEmptyMessage(3);
    this.mVolumeObserver = new Observer(this.mHandler);
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor(Settings.System.VOLUME_SETTINGS_INT[this.mStreamType]),
            false,
            this.mVolumeObserver);
    this.mReceiver.setListening(true);
    if (this.mDeviceHasProductStrategies) {
      registerVolumeGroupCb();
    }
  }

  public void revertVolume() {
    int i = this.mStreamType;
    if (i == 8) {
      Settings.System.putInt(
          this.mContext.getContentResolver(),
          Settings.System.VOLUME_WAITING_TONE,
          this.mLastWaitingToneVolume);
      return;
    }
    if (i == 5 && this.mAudioManager.isStreamMute(i)) {
      if (!this.mVoiceCapable && this.mOriginalStreamVolume == 0 && this.mOriginalRingerMode != 2) {
        int ringerMode = this.mAudioManager.getRingerMode();
        int i2 = this.mOriginalRingerMode;
        if (ringerMode != i2) {
          this.mAudioManager.setRingerMode(i2);
        }
        this.mAudioManager.setStreamVolume(
            this.mStreamType, this.mOriginalNotificationVolume, 16777216);
        return;
      }
      this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalNotificationVolume, 0);
      return;
    }
    int i3 = this.mStreamType;
    if (i3 == 1 && this.mAudioManager.isStreamMute(i3)) {
      this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalSystemVolume, 0);
      return;
    }
    int i4 = this.mOriginalStreamVolume;
    if (i4 == 0 && this.mOriginalRingerMode != 2) {
      int ringerMode2 = this.mAudioManager.getRingerMode();
      int i5 = this.mOriginalRingerMode;
      if (ringerMode2 != i5) {
        this.mAudioManager.setRingerMode(i5);
      }
      this.mAudioManager.setStreamVolume(
          this.mStreamType, this.mOriginalLastAudibleStreamVolume, 16777216);
      return;
    }
    this.mAudioManager.setStreamVolume(this.mStreamType, i4, 0);
  }

  private int getImpliedLevel(int progress) {
    int i = this.mMaxStreamVolume;
    int n = (i / 100) - 1;
    if (progress == 0) {
      return 0;
    }
    if (progress == i) {
      int level = i / 100;
      return level;
    }
    int level2 = ((int) ((progress / i) * n)) + 1;
    return level2;
  }

  private int getImpliedMediaVolumeLevel(int progress) {
    int progress2 = progress / 10;
    if (progress2 == 0) {
      return 1;
    }
    return progress2;
  }

  @Override // android.widget.SeekBar.OnSeekBarChangeListener
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
    if (fromTouch) {
      if (this.mStreamType == 3) {
        if (progress != 0) {
          progress = getImpliedMediaVolumeLevel(progress);
        }
      } else {
        progress = getImpliedLevel(progress);
      }
      Log.m94d(
          TAG, "onProgressChanged : stream = " + this.mStreamType + ", progress = " + progress);
      stopToneGenerator();
      int i = this.mStreamType;
      if (i == 2) {
        setSeekBarVolume(progress);
      } else if (i == 5) {
        if (progress == 0) {
          stopSample();
        }
        this.mAudioManager.setStreamVolume(5, progress, 0);
        if (progress == 0 && !this.mVibrator.hasVibrator() && !this.mVoiceCapable) {
          this.mAudioManager.setRingerMode(0);
        }
        this.mLastProgress = progress;
      } else if (i == 1) {
        Log.m94d(TAG, "******onProgressChanged*****");
        Callback callback = this.mCallback;
        if (callback != null) {
          callback.onSampleStarting(this);
        }
        this.mAudioManager.setStreamVolume(1, progress, 0);
        String opPackageName = this.mContext.getOpPackageName();
        String featureId = this.mContext.getAttributionTag();
        try {
          ITelecomService iTelecomService = this.mTelecomService;
          if (iTelecomService != null
              && (iTelecomService.isRinging(opPackageName)
                  || this.mTelecomService.isInCall(opPackageName, featureId))) {
            Log.m94d(TAG, "don't play STREAM_SYSTEM in volumepreference.");
          } else {
            if (!this.mAudioManager.semIsFmRadioActive() && this.mLastProgress != progress) {
              this.mAudioManager.playSoundEffect(100, progress);
              this.mSystemSampleStarted = true;
              Log.m94d(TAG, "sample : playSoundEffect()");
            } else if (this.mLastProgress == progress) {
              this.mSystemSampleStarted = false;
            }
            this.mLastProgress = progress;
          }
        } catch (RemoteException ex) {
          Log.m103w(TAG, "ITelephony threw RemoteException", ex);
        }
        Log.m94d(
            TAG,
            "onProgressChanged : AudioManager.STREAM_SYSTEM["
                + this.mAudioManager.getStreamVolume(1)
                + NavigationBarInflaterView.SIZE_MOD_END);
      } else if (i == 8) {
        Settings.System.putInt(
            this.mContext.getContentResolver(), Settings.System.VOLUME_WAITING_TONE, progress);
        if (this.mToneGenerator == null) {
          this.mToneGenerator = new ToneGenerator(8, 0);
        }
        this.mToneGenerator.setVolume(this.mAudioManager.semGetSituationVolume(15, 0));
        this.mToneGenerator.startTone(22, 300);
        this.mLastProgress = progress;
      } else if (i == 3) {
        this.mAudioManager.semSetFineVolume(3, progress, 0);
        this.mLastProgress = progress;
      } else {
        postSetVolume(progress);
      }
      int i2 = this.mStreamType;
      if (i2 != 1
          && i2 != 8
          && !isSamplePlaying()
          && (!this.mAudioManager.isMusicActive() || this.mStreamType != 3)) {
        startSample();
      }
    }
    Callback callback2 = this.mCallback;
    if (callback2 != null) {
      callback2.onProgressChanged(seekBar, progress, fromTouch);
    }
  }

  private void stopToneGenerator() {
    ToneGenerator toneGenerator = this.mToneGenerator;
    if (toneGenerator != null) {
      toneGenerator.setVolume(0.0f);
      this.mToneGenerator.stopTone();
    }
  }

  private void postSetVolume(int progress) {
    Handler handler = this.mHandler;
    if (handler == null) {
      return;
    }
    this.mLastProgress = progress;
    handler.removeMessages(0);
    this.mHandler.removeMessages(1);
    this.mHandler.removeMessages(4);
    Handler handler2 = this.mHandler;
    handler2.sendMessageDelayed(
        handler2.obtainMessage(0), isDelay() ? SET_STREAM_VOLUME_DELAY_MS : 0L);
  }

  @Override // android.widget.SeekBar.OnSeekBarChangeListener
  public void onStartTrackingTouch(SeekBar seekBar) {
    Callback callback = this.mCallback;
    if (callback != null) {
      callback.onStartTrackingTouch(this);
    }
  }

  @Override // android.widget.SeekBar.OnSeekBarChangeListener
  public void onStopTrackingTouch(SeekBar seekBar) {
    Callback callback = this.mCallback;
    if (callback != null) {
      callback.onStopTrackingTouch(this);
    }
    if (this.mStreamType == 1 && !this.mSystemSampleStarted) {
      String opPackageName = this.mContext.getOpPackageName();
      String featureId = this.mContext.getAttributionTag();
      int progress = getImpliedLevel(seekBar.getProgress());
      try {
        ITelecomService iTelecomService = this.mTelecomService;
        if (iTelecomService != null
            && (iTelecomService.isRinging(opPackageName)
                || this.mTelecomService.isInCall(opPackageName, featureId))) {
          Log.m94d(TAG, "[onStopTrackingTouch]don't play STREAM_SYSTEM in volumepreference.");
        } else if (!this.mAudioManager.semIsFmRadioActive()) {
          this.mAudioManager.playSoundEffect(100, progress);
          Log.m94d(TAG, "[onStopTrackingTouch]sample : playSoundEffect() : " + progress);
        }
      } catch (RemoteException ex) {
        Log.m103w(TAG, "ITelephony threw RemoteException", ex);
      }
    }
    int progress2 = seekBar.getProgress();
    if (this.mStreamType == 3) {
      if (progress2 != 0) {
        int progress3 = getImpliedMediaVolumeLevel(progress2) * 10;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "volumelimit_on", 0) == 1) {
          new Handler(Looper.getMainLooper())
              .postDelayed(
                  new Runnable() { // from class: android.preference.SeekBarVolumizer.2
                    @Override // java.lang.Runnable
                    public void run() {
                      SeekBarVolumizer.this.updateSeekBar();
                    }
                  },
                  500L);
          return;
        } else {
          seekBar.setProgress(progress3);
          return;
        }
      }
      return;
    }
    seekBar.setProgress(getImpliedLevel(progress2) * 100, seekBar.hasWindowFocus());
  }

  private boolean isUserInCall(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
    boolean isVoiceCall = tm.getCallState() != 0;
    int audioMode = this.mAudioManager.getMode();
    boolean isVoIP = audioMode == 3 || audioMode == 2;
    return isVoiceCall || isVoIP;
  }

  public boolean isSamplePlaying() {
    boolean z;
    synchronized (this) {
      Ringtone ringtone = this.mRingtone;
      z = ringtone != null && ringtone.isPlaying();
    }
    return z;
  }

  public void startSample() {
    String opPackageName = this.mContext.getOpPackageName();
    String featureId = this.mContext.getAttributionTag();
    ITelecomService iTelecomService = this.mTelecomService;
    if (iTelecomService != null) {
      try {
        if (!iTelecomService.isRinging(opPackageName)
            && !this.mTelecomService.isInCall(opPackageName, featureId)) {}
        Log.m94d(TAG, "don't play ringtone in volumepreference: return called");
        return;
      } catch (RemoteException ex) {
        Log.m103w(TAG, "ITelephony threw RemoteException", ex);
      }
    }
    postStartSample();
  }

  private ITelecomService getTelecomService() {
    ITelecomService telepcomService =
        ITelecomService.Stub.asInterface(ServiceManager.checkService(Context.TELECOM_SERVICE));
    if (telepcomService == null) {
      Log.m102w(TAG, "Unable to find ITelephony interface.");
    }
    return telepcomService;
  }

  public void stopSample() {
    postStopSample();
  }

  public SeekBar getSeekBar() {
    return this.mSeekBar;
  }

  public void changeVolumeBy(int amount) {
    this.mSeekBar.incrementProgressBy(amount);
    postSetVolume(this.mSeekBar.getProgress());
    postStartSample();
    this.mVolumeBeforeMute = -1;
  }

  public void muteVolume() {
    int i = this.mVolumeBeforeMute;
    if (i != -1) {
      this.mSeekBar.setProgress(i, true);
      postSetVolume(this.mVolumeBeforeMute);
      postStartSample();
      this.mVolumeBeforeMute = -1;
      return;
    }
    this.mVolumeBeforeMute = this.mSeekBar.getProgress();
    this.mSeekBar.setProgress(0, true);
    postStopSample();
    postSetVolume(0);
  }

  public void onSaveInstanceState(VolumePreference.VolumeStore volumeStore) {
    int i = this.mLastProgress;
    if (i >= 0) {
      volumeStore.volume = i;
      volumeStore.originalVolume = this.mOriginalStreamVolume;
    }
  }

  public void onRestoreInstanceState(VolumePreference.VolumeStore volumeStore) {
    if (volumeStore.volume != -1) {
      this.mOriginalStreamVolume = volumeStore.originalVolume;
      int i = volumeStore.volume;
      this.mLastProgress = i;
      postSetVolume(i);
    }
  }

  /* renamed from: android.preference.SeekBarVolumizer$H */
  private final class HandlerC2490H extends Handler {
    private static final int UPDATE_SLIDER = 1;

    private HandlerC2490H() {}

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      if (msg.what == 1 && SeekBarVolumizer.this.mSeekBar != null) {
        SeekBarVolumizer.this.mLastProgress = msg.arg1;
        SeekBarVolumizer.this.mLastAudibleStreamVolume = msg.arg2;
        boolean muted = ((Boolean) msg.obj).booleanValue();
        if (muted != SeekBarVolumizer.this.mMuted) {
          SeekBarVolumizer.this.mMuted = muted;
          if (SeekBarVolumizer.this.mCallback != null) {
            SeekBarVolumizer.this.mCallback.onMuted(
                SeekBarVolumizer.this.mMuted, SeekBarVolumizer.this.isZenMuted());
          }
        }
        SeekBarVolumizer.this.updateSeekBar();
      }
    }

    public void postUpdateSlider(int volume, int lastAudibleVolume, boolean mute) {
      obtainMessage(1, volume, lastAudibleVolume, Boolean.valueOf(mute)).sendToTarget();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void updateSlider() {
    AudioManager audioManager;
    if (this.mSeekBar != null && (audioManager = this.mAudioManager) != null) {
      int volume = audioManager.getStreamVolume(this.mStreamType);
      int lastAudibleVolume = this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType);
      boolean mute = this.mAudioManager.isStreamMute(this.mStreamType);
      this.mUiHandler.postUpdateSlider(volume, lastAudibleVolume, mute);
    }
  }

  private final class Observer extends ContentObserver {
    public Observer(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean selfChange) {
      super.onChange(selfChange);
      if (SeekBarVolumizer.this.mStreamType != 8) {
        SeekBarVolumizer.this.updateSlider();
      }
    }
  }

  private final class Receiver extends BroadcastReceiver {
    private boolean mListening;

    private Receiver() {}

    public void setListening(boolean listening) {
      if (this.mListening == listening) {
        return;
      }
      this.mListening = listening;
      if (listening) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION);
        filter.addAction(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED);
        filter.addAction(NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED);
        filter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        filter.addAction("com.samsung.intent.action.DEFAULT_CS_SIM_CHANGED");
        SeekBarVolumizer.this.mContext.registerReceiver(this, filter);
        return;
      }
      SeekBarVolumizer.this.mContext.unregisterReceiver(this);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
        int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
        if (SeekBarVolumizer.this.mStreamType == streamType) {
          SeekBarVolumizer.this.updateSlider();
          return;
        }
        return;
      }
      if (AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION.equals(action)) {
        SeekBarVolumizer seekBarVolumizer = SeekBarVolumizer.this;
        seekBarVolumizer.mRingerMode = seekBarVolumizer.mAudioManager.getRingerModeInternal();
        if (SeekBarVolumizer.this.mStreamType != 8) {
          SeekBarVolumizer.this.updateSlider();
          return;
        }
        return;
      }
      if ("android.media.STREAM_DEVICES_CHANGED_ACTION".equals(action)) {
        int streamType2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
        if (SeekBarVolumizer.this.mStreamType == streamType2
            && SeekBarVolumizer.this.isRingerUpdatedToAudio) {
          SeekBarVolumizer.this.updateSlider();
          return;
        }
        return;
      }
      if ("com.samsung.intent.action.DEFAULT_CS_SIM_CHANGED".equals(action)) {
        if (SeekBarVolumizer.this.mStreamType == 2) {
          SeekBarVolumizer seekBarVolumizer2 = SeekBarVolumizer.this;
          seekBarVolumizer2.mDefaultUri = seekBarVolumizer2.getDefaultRingtoneUri();
        } else if (SeekBarVolumizer.this.mStreamType == 5) {
          SeekBarVolumizer seekBarVolumizer3 = SeekBarVolumizer.this;
          seekBarVolumizer3.mDefaultUri = seekBarVolumizer3.getDefaultNotificationUri();
        }
        try {
          if (SeekBarVolumizer.this.mRingtone != null) {
            SeekBarVolumizer.this.mRingtone.setUri(SeekBarVolumizer.this.mDefaultUri);
            SeekBarVolumizer.this.mRingtone.setAudioAttributes(
                new AudioAttributes.Builder()
                    .setInternalLegacyStreamType(SeekBarVolumizer.this.mStreamType)
                    .build());
            return;
          }
          return;
        } catch (Exception e) {
          Log.m94d(
              SeekBarVolumizer.TAG, "Exception happens in SIM_CHANGED_ACTION : " + e.toString());
          return;
        }
      }
      if (NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED.equals(action)) {
        SeekBarVolumizer seekBarVolumizer4 = SeekBarVolumizer.this;
        seekBarVolumizer4.mZenMode = seekBarVolumizer4.mNotificationManager.getZenMode();
        SeekBarVolumizer.this.updateSlider();
      } else if (NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED.equals(action)) {
        SeekBarVolumizer seekBarVolumizer5 = SeekBarVolumizer.this;
        seekBarVolumizer5.mNotificationPolicy =
            seekBarVolumizer5.mNotificationManager.getNotificationPolicy();
        SeekBarVolumizer seekBarVolumizer6 = SeekBarVolumizer.this;
        seekBarVolumizer6.mAllowAlarms =
            (seekBarVolumizer6.mNotificationPolicy.priorityCategories & 32) != 0;
        SeekBarVolumizer seekBarVolumizer7 = SeekBarVolumizer.this;
        seekBarVolumizer7.mAllowMedia =
            (seekBarVolumizer7.mNotificationPolicy.priorityCategories & 64) != 0;
        SeekBarVolumizer.this.mAllowRinger =
            !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(r2.mNotificationPolicy);
        SeekBarVolumizer.this.updateSlider();
      }
    }

    private void updateVolumeSlider(int streamType, int streamValue) {
      boolean muted = true;
      boolean streamMatch = streamType == SeekBarVolumizer.this.mStreamType;
      if (SeekBarVolumizer.this.mSeekBar != null && streamMatch && streamValue != -1) {
        if (!SeekBarVolumizer.this.mAudioManager.isStreamMute(SeekBarVolumizer.this.mStreamType)
            && streamValue != 0) {
          muted = false;
        }
        SeekBarVolumizer.this.mUiHandler.postUpdateSlider(
            streamValue, SeekBarVolumizer.this.mLastAudibleStreamVolume, muted);
      }
    }
  }

  private void registerVolumeGroupCb() {
    if (this.mVolumeGroupId != -1) {
      this.mAudioManager.registerVolumeGroupCallback(
          new PendingIntent$$ExternalSyntheticLambda1(), this.mVolumeGroupCallback);
      updateSlider();
    }
  }

  private void unregisterVolumeGroupCb() {
    if (this.mVolumeGroupId != -1) {
      this.mAudioManager.unregisterVolumeGroupCallback(this.mVolumeGroupCallback);
    }
  }

  private class VolumeHandler extends Handler {
    private VolumeHandler() {}

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      SomeArgs args = (SomeArgs) msg.obj;
      switch (msg.what) {
        case 1:
          int group = ((Integer) args.arg1).intValue();
          if (SeekBarVolumizer.this.mVolumeGroupId == group
              && SeekBarVolumizer.this.mVolumeGroupId != -1) {
            SeekBarVolumizer.this.updateSlider();
            break;
          }
          break;
      }
    }
  }

  private boolean isVoiceCapable() {
    return this.mAudioManager.shouldShowRingtoneVolume();
  }

  private boolean isMuteAllSoundEnabled() {
    return Settings.System.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) != 0;
  }

  private boolean isAuraCastSeekBarDisabled() {
    int mode = getBroadcastMode();
    if (mode == 2) {
      boolean shouldDisabled =
          isMediaStream(this.mStreamType)
              || isNotificationStream(this.mStreamType)
              || isSystemStream(this.mStreamType)
              || isAssistantStream(this.mStreamType);
      return shouldDisabled;
    }
    if (mode != 1) {
      return false;
    }
    boolean shouldDisabled2 =
        isNotificationStream(this.mStreamType) || isSystemStream(this.mStreamType);
    return shouldDisabled2;
  }

  private int getBroadcastMode() {
    boolean isBroadcasting = false;
    boolean isLeDeviceConnected = false;
    AudioDeviceInfo[] infoList = this.mAudioManager.getDevices(2);
    for (AudioDeviceInfo info : infoList) {
      if (info.getType() == 26) {
        isLeDeviceConnected = true;
      } else if (info.getType() == 30) {
        isBroadcasting = true;
      }
    }
    if (isBroadcasting) {
      return isLeDeviceConnected ? 1 : 2;
    }
    return 0;
  }
}
