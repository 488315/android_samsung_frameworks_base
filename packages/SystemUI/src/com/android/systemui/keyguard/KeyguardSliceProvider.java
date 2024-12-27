package com.android.systemui.keyguard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.slice.builders.impl.TemplateBuilderImpl;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.util.wakelock.WakeLockLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public final class KeyguardSliceProvider extends SliceProvider implements NextAlarmController.NextAlarmChangeCallback, ZenModeController.Callback, NotificationMediaManager.MediaListener, StatusBarStateController.StateListener, SystemUIAppComponentFactoryBase.ContextInitializer {
    static final int ALARM_VISIBILITY_HOURS = 12;
    public static KeyguardSliceProvider sInstance;
    public static final Object sInstanceLock;
    public AlarmManager mAlarmManager;
    public Handler mBgHandler;
    public ContentResolver mContentResolver;
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback mContextAvailableCallback;
    public DateFormat mDateFormat;
    public String mDatePattern;
    public DozeParameters mDozeParameters;
    public boolean mDozing;
    public KeyguardBypassController mKeyguardBypassController;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mLastText;
    public CharSequence mMediaArtist;
    public boolean mMediaIsVisible;
    public NotificationMediaManager mMediaManager;
    public CharSequence mMediaTitle;
    protected SettableWakeLock mMediaWakeLock;
    public String mNextAlarm;
    public NextAlarmController mNextAlarmController;
    public AlarmManager.AlarmClockInfo mNextAlarmInfo;
    public PendingIntent mPendingIntent;
    public boolean mRegistered;
    public int mStatusBarState;
    public StatusBarStateController mStatusBarStateController;
    public UserTracker mUserTracker;
    public WakeLockLogger mWakeLockLogger;
    public ZenModeController mZenModeController;
    public final Date mCurrentTime = new Date();
    public final KeyguardSliceProvider$$ExternalSyntheticLambda2 mUpdateNextAlarm = new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda2
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            KeyguardSliceProvider.this.updateNextAlarm();
        }
    };
    final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.DATE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.updateClockLocked();
                }
            } else if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.cleanDateFormatLocked();
                }
            }
        }
    };
    final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider.2
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeChanged() {
            synchronized (this) {
                KeyguardSliceProvider.this.updateClockLocked();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeZoneChanged(TimeZone timeZone) {
            synchronized (this) {
                KeyguardSliceProvider.this.cleanDateFormatLocked();
            }
        }
    };
    public final Handler mHandler = new Handler();
    public final Handler mMediaHandler = new Handler();
    public final Uri mSliceUri = Uri.parse("content://com.android.systemui.keyguard/main");
    public final Uri mHeaderUri = Uri.parse("content://com.android.systemui.keyguard/header");
    public final Uri mDateUri = Uri.parse("content://com.android.systemui.keyguard/date");
    public final Uri mAlarmUri = Uri.parse("content://com.android.systemui.keyguard/alarm");
    public final Uri mDndUri = Uri.parse("content://com.android.systemui.keyguard/dnd");
    public final Uri mMediaUri = Uri.parse("content://com.android.systemui.keyguard/media");

    static {
        new StyleSpan(1);
        sInstanceLock = new Object();
    }

    public final void addMediaLocked(ListBuilder listBuilder) {
        String str;
        Icon icon;
        if (TextUtils.isEmpty(this.mMediaTitle)) {
            return;
        }
        ListBuilder.HeaderBuilder headerBuilder = new ListBuilder.HeaderBuilder(this.mHeaderUri);
        headerBuilder.mTitle = this.mMediaTitle;
        headerBuilder.mTitleLoading = false;
        listBuilder.mImpl.setHeader(headerBuilder);
        if (TextUtils.isEmpty(this.mMediaArtist)) {
            return;
        }
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mMediaUri);
        rowBuilder.mTitle = this.mMediaArtist;
        rowBuilder.mTitleLoading = false;
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        if (notificationMediaManager == null || (str = notificationMediaManager.mMediaNotificationKey) == null) {
            icon = null;
        } else {
            final int i = 0;
            Optional map = Optional.ofNullable(notificationMediaManager.mNotifPipeline.mNotifCollection.getEntry(str)).map(new Function() { // from class: com.android.systemui.statusbar.NotificationMediaManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    switch (i) {
                        case 0:
                            return ((NotificationEntry) obj).mIcons.mShelfIcon;
                        default:
                            return ((StatusBarIconView) obj).mIcon.icon;
                    }
                }
            });
            final int i2 = 1;
            icon = (Icon) map.map(new Function() { // from class: com.android.systemui.statusbar.NotificationMediaManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    switch (i2) {
                        case 0:
                            return ((NotificationEntry) obj).mIcons.mShelfIcon;
                        default:
                            return ((StatusBarIconView) obj).mIcon.icon;
                    }
                }
            }).orElse(null);
        }
        IconCompat createFromIcon = icon != null ? IconCompat.createFromIcon(getContext(), icon) : null;
        if (createFromIcon != null) {
            rowBuilder.addEndItem(createFromIcon);
        }
        listBuilder.mImpl.addRow(rowBuilder);
    }

    public void cleanDateFormatLocked() {
        this.mDateFormat = null;
    }

    public boolean isRegistered() {
        boolean z;
        synchronized (this) {
            z = this.mRegistered;
        }
        return z;
    }

    public final boolean needsMediaLocked() {
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        boolean z = keyguardBypassController != null && keyguardBypassController.getBypassEnabled() && this.mDozeParameters.getAlwaysOn();
        boolean z2 = this.mStatusBarState == 0 && this.mMediaIsVisible;
        if (TextUtils.isEmpty(this.mMediaTitle) || !this.mMediaIsVisible) {
            return false;
        }
        return this.mDozing || z || z2;
    }

    public final void notifyChange() {
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSliceProvider keyguardSliceProvider = KeyguardSliceProvider.this;
                keyguardSliceProvider.mContentResolver.notifyChange(keyguardSliceProvider.mSliceUri, null);
            }
        });
    }

    @Override // androidx.slice.SliceProvider
    public final Slice onBindSlice() {
        Slice slice = null;
        try {
            try {
                Trace.beginSection("KeyguardSliceProvider#onBindSlice");
                synchronized (this) {
                    try {
                        ListBuilder listBuilder = new ListBuilder(getContext(), this.mSliceUri, -1L);
                        if (needsMediaLocked()) {
                            addMediaLocked(listBuilder);
                        } else {
                            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mDateUri);
                            rowBuilder.mTitle = this.mLastText;
                            rowBuilder.mTitleLoading = false;
                            listBuilder.mImpl.addRow(rowBuilder);
                        }
                        if (!TextUtils.isEmpty(this.mNextAlarm)) {
                            IconCompat createWithResource = IconCompat.createWithResource(R.drawable.ic_access_alarms_big, getContext());
                            ListBuilder.RowBuilder rowBuilder2 = new ListBuilder.RowBuilder(this.mAlarmUri);
                            rowBuilder2.mTitle = this.mNextAlarm;
                            rowBuilder2.mTitleLoading = false;
                            rowBuilder2.addEndItem(createWithResource);
                            listBuilder.mImpl.addRow(rowBuilder2);
                        }
                        if (((ZenModeControllerImpl) this.mZenModeController).mZenMode != 0) {
                            ListBuilder.RowBuilder rowBuilder3 = new ListBuilder.RowBuilder(this.mDndUri);
                            rowBuilder3.mContentDescription = getContext().getResources().getString(R.string.accessibility_quick_settings_dnd);
                            rowBuilder3.addEndItem(IconCompat.createWithResource(R.drawable.stat_sys_dnd, getContext()));
                            listBuilder.mImpl.addRow(rowBuilder3);
                        }
                        SliceAction sliceAction = new SliceAction(this.mPendingIntent, IconCompat.createWithResource(R.drawable.ic_access_alarms_big, getContext()), 0, this.mLastText);
                        sliceAction.mSliceAction.mIsActivity = true;
                        ListBuilder.RowBuilder rowBuilder4 = new ListBuilder.RowBuilder(Uri.parse("content://com.android.systemui.keyguard/action"));
                        rowBuilder4.mPrimaryAction = sliceAction;
                        listBuilder.mImpl.addRow(rowBuilder4);
                        slice = ((TemplateBuilderImpl) listBuilder.mImpl).build();
                    } finally {
                    }
                }
            } catch (IllegalStateException e) {
                android.util.Log.w("KgdSliceProvider", "Could not initialize slice", e);
            }
            return slice;
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onConfigChanged(ZenModeConfig zenModeConfig) {
        notifyChange();
    }

    @Override // androidx.slice.SliceProvider
    public final void onCreateSliceProvider() {
        this.mContextAvailableCallback.onContextAvailable(getContext());
        this.mMediaWakeLock = new SettableWakeLock(WakeLock.createPartial(getContext(), this.mWakeLockLogger, "media"), "media");
        synchronized (sInstanceLock) {
            try {
                KeyguardSliceProvider keyguardSliceProvider = sInstance;
                if (keyguardSliceProvider != null) {
                    keyguardSliceProvider.onDestroy();
                }
                this.mDatePattern = getContext().getString(R.string.system_ui_aod_date_pattern);
                this.mPendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), (Class<?>) KeyguardSliceProvider.class), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
                NotificationMediaManager notificationMediaManager = this.mMediaManager;
                notificationMediaManager.mMediaListeners.add(this);
                Flags.FEATURE_FLAGS.getClass();
                notificationMediaManager.updateMediaMetaData(this);
                this.mStatusBarStateController.addCallback(this);
                ((NextAlarmControllerImpl) this.mNextAlarmController).addCallback(this);
                ((ZenModeControllerImpl) this.mZenModeController).addCallback(this);
                sInstance = this;
                registerClockUpdate();
                updateClockLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onDestroy() {
        synchronized (sInstanceLock) {
            try {
                ((NextAlarmControllerImpl) this.mNextAlarmController).removeCallback(this);
                ((ZenModeControllerImpl) this.mZenModeController).removeCallback(this);
                this.mMediaWakeLock.setAcquired(false);
                this.mAlarmManager.cancel(this.mUpdateNextAlarm);
                if (this.mRegistered) {
                    this.mRegistered = false;
                    this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
                    getContext().unregisterReceiver(this.mIntentReceiver);
                }
                sInstance = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        boolean z2;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mDozing = z;
            z2 = needsMediaLocked != needsMediaLocked();
        }
        if (z2) {
            notifyChange();
        }
    }

    @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        synchronized (this) {
            try {
                this.mNextAlarmInfo = alarmClockInfo;
                this.mAlarmManager.cancel(this.mUpdateNextAlarm);
                AlarmManager.AlarmClockInfo alarmClockInfo2 = this.mNextAlarmInfo;
                long triggerTime = alarmClockInfo2 == null ? -1L : alarmClockInfo2.getTriggerTime() - TimeUnit.HOURS.toMillis(12L);
                if (triggerTime > 0) {
                    this.mAlarmManager.setExact(1, triggerTime, "lock_screen_next_alarm", this.mUpdateNextAlarm, this.mHandler);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        updateNextAlarm();
    }

    public final void onPrimaryMetadataOrStateChanged(final MediaMetadata mediaMetadata, final int i) {
        synchronized (this) {
            try {
                boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
                this.mMediaHandler.removeCallbacksAndMessages(null);
                if (!this.mMediaIsVisible || isPlayingState || this.mStatusBarState == 0) {
                    this.mMediaWakeLock.setAcquired(false);
                    updateMediaStateLocked(mediaMetadata, i);
                } else {
                    this.mMediaWakeLock.setAcquired(true);
                    this.mMediaHandler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardSliceProvider keyguardSliceProvider = KeyguardSliceProvider.this;
                            MediaMetadata mediaMetadata2 = mediaMetadata;
                            int i2 = i;
                            synchronized (keyguardSliceProvider) {
                                keyguardSliceProvider.updateMediaStateLocked(mediaMetadata2, i2);
                                keyguardSliceProvider.mMediaWakeLock.setAcquired(false);
                            }
                        }
                    }, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        boolean z;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mStatusBarState = i;
            z = needsMediaLocked != needsMediaLocked();
        }
        if (z) {
            notifyChange();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onZenChanged(int i) {
        notifyChange();
    }

    public void registerClockUpdate() {
        synchronized (this) {
            try {
                if (this.mRegistered) {
                    return;
                }
                final IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.DATE_CHANGED");
                intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSliceProvider keyguardSliceProvider = KeyguardSliceProvider.this;
                        keyguardSliceProvider.getContext().registerReceiver(keyguardSliceProvider.mIntentReceiver, intentFilter, null, null);
                    }
                });
                this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
                this.mRegistered = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }

    public final void updateClockLocked() {
        if (this.mDateFormat == null) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.mDatePattern, Locale.getDefault());
            instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
            this.mDateFormat = instanceForSkeleton;
        }
        this.mCurrentTime.setTime(System.currentTimeMillis());
        String format = this.mDateFormat.format(this.mCurrentTime);
        if (format.equals(this.mLastText)) {
            return;
        }
        this.mLastText = format;
        notifyChange();
    }

    public final void updateMediaStateLocked(MediaMetadata mediaMetadata, int i) {
        CharSequence charSequence;
        boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
        if (mediaMetadata != null) {
            charSequence = mediaMetadata.getText("android.media.metadata.TITLE");
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = getContext().getResources().getString(R.string.music_controls_no_title);
            }
        } else {
            charSequence = null;
        }
        CharSequence text = mediaMetadata != null ? mediaMetadata.getText("android.media.metadata.ARTIST") : null;
        if (isPlayingState == this.mMediaIsVisible && TextUtils.equals(charSequence, this.mMediaTitle) && TextUtils.equals(text, this.mMediaArtist)) {
            return;
        }
        this.mMediaTitle = charSequence;
        this.mMediaArtist = text;
        this.mMediaIsVisible = isPlayingState;
        notifyChange();
    }

    public final void updateNextAlarm() {
        synchronized (this) {
            try {
                if (this.mNextAlarmInfo != null) {
                    if (this.mNextAlarmInfo.getTriggerTime() <= TimeUnit.HOURS.toMillis(12) + System.currentTimeMillis()) {
                        this.mNextAlarm = android.text.format.DateFormat.format(android.text.format.DateFormat.is24HourFormat(getContext(), ((UserTrackerImpl) this.mUserTracker).getUserId()) ? "HH:mm" : "h:mm", this.mNextAlarmInfo.getTriggerTime()).toString();
                    }
                }
                this.mNextAlarm = "";
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyChange();
    }
}
