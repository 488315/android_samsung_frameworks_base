package android.preference;

import android.app.NotificationManager;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audiopolicy.AudioProductStrategy;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.preference.VolumePreference;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import android.widget.SeekBar;
import com.android.internal.os.SomeArgs;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

@Deprecated
/* loaded from: classes3.dex */
public class SeekBarVolumizer implements SeekBar.OnSeekBarChangeListener, Handler.Callback {
    private static final int CHECK_RINGTONE_PLAYBACK_DELAY_MS = 1000;
    private static final int CHECK_UPDATE_SLIDER_LATER_MS = 500;
    private static final int MSG_GROUP_VOLUME_CHANGED = 1;
    private static final int MSG_INIT_SAMPLE = 3;
    private static final int MSG_SET_STREAM_VOLUME = 0;
    private static final int MSG_START_SAMPLE = 1;
    private static final int MSG_STOP_SAMPLE = 2;
    private static final int MSG_UPDATE_SLIDER_MAYBE_LATER = 4;
    private static final String TAG = "SeekBarVolumizer";
    private static long sStopVolumeTime;
    private boolean mAffectedByRingerMode;
    private boolean mAllowAlarms;
    private boolean mAllowMedia;
    private boolean mAllowRinger;
    private AudioAttributes mAttributes;
    private final AudioManager mAudioManager;
    private final Callback mCallback;
    private final Context mContext;
    private final Uri mDefaultUri;
    private final boolean mDeviceHasProductStrategies;
    private Handler mHandler;
    private int mLastAudibleStreamVolume;
    private int mLastProgress;
    private final int mMaxStreamVolume;
    private boolean mMuted;
    private final NotificationManager mNotificationManager;
    private boolean mNotificationOrRing;
    private NotificationManager.Policy mNotificationPolicy;
    private int mOriginalStreamVolume;
    private boolean mPlaySample;
    private final Receiver mReceiver;
    private int mRingerMode;
    private Ringtone mRingtone;
    private SeekBar mSeekBar;
    private final int mStreamType;
    private final H mUiHandler;
    private int mVolumeBeforeMute;
    private final AudioManager.VolumeGroupCallback mVolumeGroupCallback;
    private int mVolumeGroupId;
    private final Handler mVolumeHandler;
    private Observer mVolumeObserver;
    private int mZenMode;
    private static final long SET_STREAM_VOLUME_DELAY_MS = TimeUnit.MILLISECONDS.toMillis(500);
    private static final long START_SAMPLE_DELAY_MS = TimeUnit.MILLISECONDS.toMillis(500);
    private static final long DURATION_TO_START_DELAYING = TimeUnit.MILLISECONDS.toMillis(2000);

    public interface Callback {
        void onMuted(boolean z, boolean z2);

        void onProgressChanged(SeekBar seekBar, int i, boolean z);

        void onSampleStarting(SeekBarVolumizer seekBarVolumizer);

        void onStartTrackingTouch(SeekBarVolumizer seekBarVolumizer);

        default void onStopTrackingTouch(SeekBarVolumizer seekBarVolumizer) {
        }
    }

    private static boolean isAlarmsStream(int i) {
        return i == 4;
    }

    private static boolean isMediaStream(int i) {
        return i == 3;
    }

    private static boolean isNotificationOrRing(int i) {
        return i == 2 || i == 5;
    }

    public SeekBarVolumizer(Context context, int i, Uri uri, Callback callback) {
        this(context, i, uri, callback, true);
    }

    public SeekBarVolumizer(Context context, int i, Uri uri, Callback callback, boolean z) {
        this.mVolumeHandler = new VolumeHandler();
        this.mVolumeGroupCallback = new AudioManager.VolumeGroupCallback() { // from class: android.preference.SeekBarVolumizer.1
            @Override // android.media.AudioManager.VolumeGroupCallback
            public void onAudioVolumeGroupChanged(int i2, int i3) {
                if (SeekBarVolumizer.this.mHandler == null) {
                    return;
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = Integer.valueOf(i2);
                obtain.arg2 = Integer.valueOf(i3);
                SeekBarVolumizer.this.mVolumeHandler.sendMessage(SeekBarVolumizer.this.mHandler.obtainMessage(1, obtain));
            }
        };
        this.mUiHandler = new H();
        this.mReceiver = new Receiver();
        this.mLastProgress = -1;
        this.mVolumeBeforeMute = -1;
        this.mContext = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mAudioManager = audioManager;
        boolean hasAudioProductStrategies = hasAudioProductStrategies();
        this.mDeviceHasProductStrategies = hasAudioProductStrategies;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mNotificationManager = notificationManager;
        NotificationManager.Policy consolidatedNotificationPolicy = notificationManager.getConsolidatedNotificationPolicy();
        this.mNotificationPolicy = consolidatedNotificationPolicy;
        this.mAllowAlarms = (consolidatedNotificationPolicy.priorityCategories & 32) != 0;
        this.mAllowMedia = (this.mNotificationPolicy.priorityCategories & 64) != 0;
        this.mAllowRinger = !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(this.mNotificationPolicy);
        this.mStreamType = i;
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
        this.mMaxStreamVolume = audioManager.getStreamMaxVolume(i);
        this.mCallback = callback;
        this.mOriginalStreamVolume = audioManager.getStreamVolume(i);
        this.mLastAudibleStreamVolume = audioManager.getLastAudibleStreamVolume(i);
        boolean isStreamMute = audioManager.isStreamMute(i);
        this.mMuted = isStreamMute;
        this.mPlaySample = z;
        if (callback != null) {
            callback.onMuted(isStreamMute, isZenMuted());
        }
        if (uri == null) {
            if (i == 2) {
                uri = Settings.System.DEFAULT_RINGTONE_URI;
            } else if (i == 5) {
                uri = Settings.System.DEFAULT_NOTIFICATION_URI;
            } else {
                uri = Settings.System.DEFAULT_ALARM_ALERT_URI;
            }
        }
        this.mDefaultUri = uri;
    }

    private boolean hasAudioProductStrategies() {
        return AudioManager.getAudioProductStrategies().size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVolumeGroupIdForLegacyStreamType(int i) {
        Iterator<AudioProductStrategy> it = AudioManager.getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            int volumeGroupIdForLegacyStreamType = it.next().getVolumeGroupIdForLegacyStreamType(i);
            if (volumeGroupIdForLegacyStreamType != -1) {
                return volumeGroupIdForLegacyStreamType;
            }
        }
        return ((Integer) AudioManager.getAudioProductStrategies().stream().map(new Function() { // from class: android.preference.SeekBarVolumizer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                valueOf = Integer.valueOf(((AudioProductStrategy) obj).getVolumeGroupIdForAudioAttributes(AudioProductStrategy.getDefaultAttributes()));
                return valueOf;
            }
        }).filter(new Predicate() { // from class: android.preference.SeekBarVolumizer$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SeekBarVolumizer.lambda$getVolumeGroupIdForLegacyStreamType$1((Integer) obj);
            }
        }).findFirst().orElse(-1)).intValue();
    }

    static /* synthetic */ boolean lambda$getVolumeGroupIdForLegacyStreamType$1(Integer num) {
        return num.intValue() != -1;
    }

    private AudioAttributes getAudioAttributesForLegacyStreamType(int i) {
        Iterator<AudioProductStrategy> it = AudioManager.getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            AudioAttributes audioAttributesForLegacyStreamType = it.next().getAudioAttributesForLegacyStreamType(i);
            if (audioAttributesForLegacyStreamType != null) {
                return audioAttributesForLegacyStreamType;
            }
        }
        return new AudioAttributes.Builder().setContentType(0).setUsage(0).build();
    }

    public void setSeekBar(SeekBar seekBar) {
        SeekBar seekBar2 = this.mSeekBar;
        if (seekBar2 != null) {
            seekBar2.setOnSeekBarChangeListener(null);
        }
        this.mSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(null);
        this.mSeekBar.setMax(this.mMaxStreamVolume);
        updateSeekBar();
        this.mSeekBar.setOnSeekBarChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isZenMuted() {
        int i;
        if ((!this.mNotificationOrRing || this.mZenMode != 3) && (i = this.mZenMode) != 2) {
            if (i != 1) {
                return false;
            }
            if ((this.mAllowAlarms || !isAlarmsStream(this.mStreamType)) && ((this.mAllowMedia || !isMediaStream(this.mStreamType)) && (this.mAllowRinger || !isNotificationOrRing(this.mStreamType)))) {
                return false;
            }
        }
        return true;
    }

    protected void updateSeekBar() {
        boolean isZenMuted = isZenMuted();
        this.mSeekBar.setEnabled(!isZenMuted);
        if (isZenMuted) {
            this.mSeekBar.setProgress(this.mLastAudibleStreamVolume, true);
            return;
        }
        if (this.mNotificationOrRing && this.mRingerMode == 1) {
            int i = this.mStreamType;
            if (i == 2 || (i == 5 && this.mMuted)) {
                this.mSeekBar.setProgress(0, true);
                return;
            }
            return;
        }
        if (this.mMuted) {
            this.mSeekBar.setProgress(0, true);
            return;
        }
        SeekBar seekBar = this.mSeekBar;
        int i2 = this.mLastProgress;
        if (i2 <= -1) {
            i2 = this.mOriginalStreamVolume;
        }
        seekBar.setProgress(i2, true);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            boolean z = this.mMuted;
            if (z && this.mLastProgress > 0) {
                this.mAudioManager.adjustStreamVolume(this.mStreamType, 100, 0);
            } else if (!z && this.mLastProgress == 0) {
                this.mAudioManager.adjustStreamVolume(this.mStreamType, -100, 0);
            }
            this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 1024);
        } else if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        onUpdateSliderMaybeLater();
                    } else {
                        Log.e(TAG, "invalid SeekBarVolumizer message: " + message.what);
                    }
                } else if (this.mPlaySample) {
                    onInitSample();
                }
            } else if (this.mPlaySample) {
                onStopSample();
            }
        } else if (this.mPlaySample) {
            onStartSample();
        }
        return true;
    }

    private void onInitSample() {
        synchronized (this) {
            Ringtone ringtone = RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
            this.mRingtone = ringtone;
            if (ringtone != null) {
                ringtone.setStreamType(this.mStreamType);
            }
        }
    }

    private void postStartSample() {
        long j;
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(1);
        Handler handler2 = this.mHandler;
        Message obtainMessage = handler2.obtainMessage(1);
        if (isSamplePlaying()) {
            j = 1000;
        } else {
            j = isDelay() ? START_SAMPLE_DELAY_MS : 0L;
        }
        handler2.sendMessageDelayed(obtainMessage, j);
    }

    private void onUpdateSliderMaybeLater() {
        if (isDelay()) {
            postUpdateSliderMaybeLater();
        } else {
            updateSlider();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUpdateSliderMaybeLater() {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(4);
        Handler handler2 = this.mHandler;
        handler2.sendMessageDelayed(handler2.obtainMessage(4), 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDelay() {
        long currentTimeMillis = System.currentTimeMillis() - sStopVolumeTime;
        return currentTimeMillis >= 0 && currentTimeMillis < DURATION_TO_START_DELAYING;
    }

    private void setStopVolumeTime() {
        int i = this.mStreamType;
        if (i == 0 || i == 2 || i == 5 || i == 4) {
            sStopVolumeTime = System.currentTimeMillis();
        }
    }

    private void onStartSample() {
        if (isSamplePlaying()) {
            return;
        }
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onSampleStarting(this);
        }
        synchronized (this) {
            Ringtone ringtone = this.mRingtone;
            if (ringtone != null) {
                try {
                    ringtone.setAudioAttributes(new AudioAttributes.Builder(this.mRingtone.getAudioAttributes()).setFlags(128).addTag("VX_AOSP_SAMPLESOUND").build());
                    this.mRingtone.play();
                } catch (Throwable th) {
                    Log.w(TAG, "Error playing ringtone, stream " + this.mStreamType, th);
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
        HandlerThread handlerThread = new HandlerThread("SeekBarVolumizer.CallbackHandler");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper(), this);
        this.mHandler = handler;
        handler.sendEmptyMessage(3);
        this.mVolumeObserver = new Observer(this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.VOLUME_SETTINGS_INT[this.mStreamType]), false, this.mVolumeObserver);
        this.mReceiver.setListening(true);
        if (this.mDeviceHasProductStrategies) {
            registerVolumeGroupCb();
        }
    }

    public void revertVolume() {
        this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalStreamVolume, 0);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            postSetVolume(i);
        }
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onProgressChanged(seekBar, i, z);
        }
    }

    private void postSetVolume(int i) {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        this.mLastProgress = i;
        handler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(4);
        Handler handler2 = this.mHandler;
        handler2.sendMessageDelayed(handler2.obtainMessage(0), isDelay() ? SET_STREAM_VOLUME_DELAY_MS : 0L);
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
        postStartSample();
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onStopTrackingTouch(this);
        }
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
        postStartSample();
    }

    public void stopSample() {
        postStopSample();
    }

    public SeekBar getSeekBar() {
        return this.mSeekBar;
    }

    public void changeVolumeBy(int i) {
        this.mSeekBar.incrementProgressBy(i);
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

    private final class H extends Handler {
        private static final int UPDATE_SLIDER = 1;

        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1 || SeekBarVolumizer.this.mSeekBar == null) {
                return;
            }
            SeekBarVolumizer.this.mLastProgress = message.arg1;
            SeekBarVolumizer.this.mLastAudibleStreamVolume = message.arg2;
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            if (booleanValue != SeekBarVolumizer.this.mMuted) {
                SeekBarVolumizer.this.mMuted = booleanValue;
                if (SeekBarVolumizer.this.mCallback != null) {
                    SeekBarVolumizer.this.mCallback.onMuted(SeekBarVolumizer.this.mMuted, SeekBarVolumizer.this.isZenMuted());
                }
            }
            SeekBarVolumizer.this.updateSeekBar();
        }

        public void postUpdateSlider(int i, int i2, boolean z) {
            obtainMessage(1, i, i2, Boolean.valueOf(z)).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlider() {
        AudioManager audioManager;
        if (this.mSeekBar == null || (audioManager = this.mAudioManager) == null) {
            return;
        }
        this.mUiHandler.postUpdateSlider(audioManager.getStreamVolume(this.mStreamType), this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType), this.mAudioManager.isStreamMute(this.mStreamType));
    }

    private final class Observer extends ContentObserver {
        public Observer(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            SeekBarVolumizer.this.updateSlider();
        }
    }

    private final class Receiver extends BroadcastReceiver {
        private boolean mListening;

        private Receiver() {
        }

        public void setListening(boolean z) {
            if (this.mListening == z) {
                return;
            }
            this.mListening = z;
            if (z) {
                IntentFilter intentFilter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
                intentFilter.addAction(AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION);
                intentFilter.addAction(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED);
                intentFilter.addAction(NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED);
                intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
                SeekBarVolumizer.this.mContext.registerReceiver(this, intentFilter);
                return;
            }
            SeekBarVolumizer.this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
                int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1);
                if (!SeekBarVolumizer.this.mDeviceHasProductStrategies || SeekBarVolumizer.this.isDelay()) {
                    return;
                }
                updateVolumeSlider(intExtra, intExtra2);
                return;
            }
            if (AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION.equals(action)) {
                if (SeekBarVolumizer.this.mNotificationOrRing) {
                    SeekBarVolumizer seekBarVolumizer = SeekBarVolumizer.this;
                    seekBarVolumizer.mRingerMode = seekBarVolumizer.mAudioManager.getRingerModeInternal();
                }
                if (SeekBarVolumizer.this.mAffectedByRingerMode) {
                    SeekBarVolumizer.this.updateSlider();
                    return;
                }
                return;
            }
            if ("android.media.STREAM_DEVICES_CHANGED_ACTION".equals(action)) {
                int intExtra3 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                if (SeekBarVolumizer.this.mDeviceHasProductStrategies) {
                    if (SeekBarVolumizer.this.isDelay()) {
                        SeekBarVolumizer.this.postUpdateSliderMaybeLater();
                        return;
                    } else {
                        updateVolumeSlider(intExtra3, SeekBarVolumizer.this.mAudioManager.getStreamVolume(intExtra3));
                        return;
                    }
                }
                int volumeGroupIdForLegacyStreamType = SeekBarVolumizer.this.getVolumeGroupIdForLegacyStreamType(intExtra3);
                if (volumeGroupIdForLegacyStreamType == -1 || volumeGroupIdForLegacyStreamType != SeekBarVolumizer.this.mVolumeGroupId) {
                    return;
                }
                int streamVolume = SeekBarVolumizer.this.mAudioManager.getStreamVolume(intExtra3);
                if (SeekBarVolumizer.this.isDelay()) {
                    return;
                }
                updateVolumeSlider(intExtra3, streamVolume);
                return;
            }
            if (NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED.equals(action)) {
                SeekBarVolumizer seekBarVolumizer2 = SeekBarVolumizer.this;
                seekBarVolumizer2.mZenMode = seekBarVolumizer2.mNotificationManager.getZenMode();
                SeekBarVolumizer.this.updateSlider();
            } else if (NotificationManager.ACTION_NOTIFICATION_POLICY_CHANGED.equals(action)) {
                SeekBarVolumizer seekBarVolumizer3 = SeekBarVolumizer.this;
                seekBarVolumizer3.mNotificationPolicy = seekBarVolumizer3.mNotificationManager.getConsolidatedNotificationPolicy();
                SeekBarVolumizer seekBarVolumizer4 = SeekBarVolumizer.this;
                seekBarVolumizer4.mAllowAlarms = (seekBarVolumizer4.mNotificationPolicy.priorityCategories & 32) != 0;
                SeekBarVolumizer seekBarVolumizer5 = SeekBarVolumizer.this;
                seekBarVolumizer5.mAllowMedia = (seekBarVolumizer5.mNotificationPolicy.priorityCategories & 64) != 0;
                SeekBarVolumizer.this.mAllowRinger = !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(r4.mNotificationPolicy);
                SeekBarVolumizer.this.updateSlider();
            }
        }

        private void updateVolumeSlider(int i, int i2) {
            boolean z = true;
            boolean z2 = i == SeekBarVolumizer.this.mStreamType;
            if (SeekBarVolumizer.this.mSeekBar == null || !z2 || i2 == -1) {
                return;
            }
            if (!SeekBarVolumizer.this.mAudioManager.isStreamMute(SeekBarVolumizer.this.mStreamType) && i2 != 0) {
                z = false;
            }
            SeekBarVolumizer.this.mUiHandler.postUpdateSlider(i2, SeekBarVolumizer.this.mLastAudibleStreamVolume, z);
        }
    }

    private void registerVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.registerVolumeGroupCallback(new PendingIntent$$ExternalSyntheticLambda0(), this.mVolumeGroupCallback);
            updateSlider();
        }
    }

    private void unregisterVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.unregisterVolumeGroupCallback(this.mVolumeGroupCallback);
        }
    }

    private class VolumeHandler extends Handler {
        private VolumeHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SomeArgs someArgs = (SomeArgs) message.obj;
            if (message.what != 1) {
                return;
            }
            if (SeekBarVolumizer.this.mVolumeGroupId != ((Integer) someArgs.arg1).intValue() || SeekBarVolumizer.this.mVolumeGroupId == -1) {
                return;
            }
            SeekBarVolumizer.this.updateSlider();
        }
    }
}
