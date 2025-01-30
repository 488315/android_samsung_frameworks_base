package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LockscreenNotificationManager implements ConfigurationController.ConfigurationListener, StatusBarStateController.StateListener, WakefulnessLifecycle.Observer, SemWindowManager.FoldStateListener {
    public int mBarState;
    public final Context mContext;
    public final KeyguardUpdateMonitorCallback mCoverCallback;
    public int mCurrentNotificationType;
    public int mCurrentOrientation;
    public UserTracker.Callback mCurrentUserTrackerCallback;
    public final LockscreenNotificationMgrHandler mHandler;
    public boolean mIsCovered;
    public boolean mIsFolded;
    public boolean mIsFolderStateOpen;
    public final boolean mIsKeyguardSupportLandscapePhone;
    public FaceWidgetContainerWrapper mKeyguardStatusBase;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LockScreenNotiIconCoordinator mLockScreenNotificationStateListener;
    public final LockscreenNotificationManagerLogger mLogger;
    public final Uri mNotificationSettingUri;
    public int mSemDisplayDeviceType;
    public int mSettingNotificationType;
    public final C25631 mSettingsListenerForNotificationStyle;
    public NotificationStackScrollLayout mStackScrollLayout;
    public final StatusBarStateController mStatusBarStateController;
    public final Object mLock = new Object();
    public final ArrayList mCallbacks = new ArrayList();
    public boolean mIsDetail = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.LockscreenNotificationManager$1 */
    public final class C25631 implements SettingsHelper.OnChangedCallback {
        public C25631() {
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
            if (uri.equals(lockscreenNotificationManager.mNotificationSettingUri)) {
                lockscreenNotificationManager.mSettingNotificationType = Settings.System.getIntForUser(lockscreenNotificationManager.mContext.getContentResolver(), "lockscreen_minimizing_notification", 1, -2);
                Log.d("LockscreenNotificationManager", " setting updated : " + lockscreenNotificationManager.mSettingNotificationType);
                lockscreenNotificationManager.updateNotificationType();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.LockscreenNotificationManager$3 */
    public final class C25653 implements UserTracker.Callback {
        public C25653() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.statusbar.LockscreenNotificationManager$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
                    lockscreenNotificationManager.mSettingsListenerForNotificationStyle.onChanged(lockscreenNotificationManager.mNotificationSettingUri);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onNotificationInfoUpdated(ArrayList arrayList);

        void onNotificationTypeChanged(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LockscreenNotificationMgrHandler extends Handler {
        public /* synthetic */ LockscreenNotificationMgrHandler(LockscreenNotificationManager lockscreenNotificationManager, Looper looper, int i) {
            this(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            if (i == 100) {
                while (i2 < LockscreenNotificationManager.this.mCallbacks.size()) {
                    ((Callback) LockscreenNotificationManager.this.mCallbacks.get(i2)).onNotificationTypeChanged(message.arg1);
                    i2++;
                }
                return;
            }
            if (i != 101) {
                return;
            }
            ArrayList arrayList = (ArrayList) message.obj;
            LockscreenNotificationManagerLogger lockscreenNotificationManagerLogger = LockscreenNotificationManager.this.mLogger;
            lockscreenNotificationManagerLogger.getClass();
            StringBuffer stringBuffer = new StringBuffer();
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                stringBuffer.append("[" + i3 + "] " + ((LockscreenNotificationInfo) arrayList.get(i3)).mKey + "\n");
            }
            if (lockscreenNotificationManagerLogger.DEBUG) {
                Log.d("LockNotifManager", stringBuffer.toString());
            }
            LogLevel logLevel = LogLevel.INFO;
            LockscreenNotificationManagerLogger$logNotifList$2 lockscreenNotificationManagerLogger$logNotifList$2 = new Function1() { // from class: com.android.systemui.statusbar.LockscreenNotificationManagerLogger$logNotifList$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("NOTIF LOCK LIST ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = lockscreenNotificationManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("LockNotifManager", logLevel, lockscreenNotificationManagerLogger$logNotifList$2, null);
            obtain.setStr1(stringBuffer.toString());
            logBuffer.commit(obtain);
            while (i2 < LockscreenNotificationManager.this.mCallbacks.size()) {
                ((Callback) LockscreenNotificationManager.this.mCallbacks.get(i2)).onNotificationInfoUpdated(arrayList);
                i2++;
            }
        }

        private LockscreenNotificationMgrHandler(Looper looper) {
            super(looper);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotificationIconData {
        public int mColor;
        public final Context mContext;
        public final ArrayList mIconArray = new ArrayList();
        public int mTagFreshDrawable;
        public int mTagIsAppColor;
        public int mTagShowConversation;

        public NotificationIconData(Context context) {
            this.mContext = context;
        }

        public final void createImageViewIcon(StatusBarIconView statusBarIconView, StatusBarNotification statusBarNotification, ImageView.ScaleType scaleType) {
            Drawable icon = statusBarIconView.getIcon(statusBarIconView.mIcon.clone());
            Context context = this.mContext;
            this.mColor = NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(context)) ? statusBarNotification.getNotification().color : 0;
            int i = statusBarNotification.getNotification().iconLevel;
            boolean z = statusBarIconView.mShowsConversation;
            ImageView imageView = new ImageView(context);
            Drawable mutate = icon != null ? icon.mutate() : null;
            if (mutate instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) mutate;
                int numberOfFrames = animationDrawable.getNumberOfFrames();
                Drawable[] drawableArr = new Drawable[numberOfFrames];
                for (int i2 = 0; i2 < numberOfFrames; i2++) {
                    Drawable frame = animationDrawable.getFrame(i2);
                    if (frame != null) {
                        frame.clearColorFilter();
                        frame.setTintList(null);
                    }
                    drawableArr[i2] = frame;
                }
                mutate = new LayerDrawable(drawableArr);
                imageView.setImageDrawable(mutate);
            } else {
                if (mutate != null) {
                    mutate.clearColorFilter();
                    mutate.setTintList(null);
                }
                imageView.setImageDrawable(mutate);
                if (i != 0) {
                    imageView.setImageLevel(i);
                }
            }
            imageView.setTag(this.mTagIsAppColor, Integer.valueOf(this.mColor));
            imageView.setTag(this.mTagFreshDrawable, mutate);
            imageView.setTag(this.mTagShowConversation, Boolean.valueOf(z));
            imageView.setScaleType(scaleType);
            ArrayList arrayList = this.mIconArray;
            if (arrayList.contains(imageView)) {
                return;
            }
            arrayList.add(imageView);
        }
    }

    public LockscreenNotificationManager(Context context, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, LockscreenNotificationManagerLogger lockscreenNotificationManagerLogger, UserTracker userTracker, Handler handler, ConfigurationController configurationController) {
        boolean z = false;
        z = false;
        this.mHandler = new LockscreenNotificationMgrHandler(this, Looper.getMainLooper(), z ? 1 : 0);
        Uri uriFor = Settings.System.getUriFor("lockscreen_minimizing_notification");
        this.mNotificationSettingUri = uriFor;
        this.mIsCovered = false;
        this.mIsFolded = false;
        C25631 c25631 = new C25631();
        this.mSettingsListenerForNotificationStyle = c25631;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.LockscreenNotificationManager.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUpdateCoverState(CoverState coverState) {
                if (coverState == null) {
                    return;
                }
                boolean z2 = !coverState.switchState;
                LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
                if (lockscreenNotificationManager.mIsCovered != z2) {
                    lockscreenNotificationManager.mIsCovered = z2;
                    LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = lockscreenNotificationManager.mLockScreenNotificationStateListener;
                    if (lockScreenNotiIconCoordinator != null) {
                        lockScreenNotiIconCoordinator.mNotifFilter.invalidateList("LockScreenNotiStateChanged");
                    }
                }
            }
        };
        this.mCoverCallback = keyguardUpdateMonitorCallback;
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        this.mLogger = lockscreenNotificationManagerLogger;
        if (DeviceState.shouldEnableKeyguardScreenRotation(context) && !DeviceType.isTablet()) {
            z = true;
        }
        this.mIsKeyguardSupportLandscapePhone = z;
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.LockscreenNotificationManager.2
                @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
                public final void onFolderStateChanged(boolean z2) {
                    LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
                    lockscreenNotificationManager.mIsFolderStateOpen = z2;
                    if (lockscreenNotificationManager.mStatusBarStateController.getState() == 1) {
                        lockscreenNotificationManager.updateNotificationType();
                    }
                }
            });
            this.mIsFolderStateOpen = ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened;
        }
        statusBarStateController.addCallback(this);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        onConfigChanged(context.getResources().getConfiguration());
        settingsHelper.registerCallback(c25631, uriFor);
        c25631.onChanged(uriFor);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        C25653 c25653 = new C25653();
        this.mCurrentUserTrackerCallback = c25653;
        ((UserTrackerImpl) userTracker).addCallback(c25653, new HandlerExecutor(handler));
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_NOTIFICATION) {
            SemWindowManager.getInstance().registerFoldStateListener(this, (Handler) null);
        }
    }

    public final void addCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            return;
        }
        this.mCallbacks.add(callback);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mCurrentOrientation = i2;
            Log.d("LockscreenNotificationManager", "Orientation updated : " + this.mCurrentOrientation);
            updateNotificationType();
        }
        int i3 = this.mSemDisplayDeviceType;
        int i4 = configuration.semDisplayDeviceType;
        if (i3 != i4) {
            this.mSemDisplayDeviceType = i4;
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Fold state updated : "), this.mSemDisplayDeviceType, "LockscreenNotificationManager");
            this.mIsFolded = this.mSemDisplayDeviceType == 5;
            updateNotificationType();
        }
    }

    public final void onFoldStateChanged(boolean z) {
        LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator;
        Log.d("LockscreenNotificationManager", " FOLD STATE - ".concat(z ? "FOLD " : "UNFOLD "));
        if (this.mIsFolded != z) {
            this.mIsFolded = z;
            if (this.mKeyguardUpdateMonitor.isLockscreenDisabled() && z && (lockScreenNotiIconCoordinator = this.mLockScreenNotificationStateListener) != null) {
                lockScreenNotiIconCoordinator.mNotifFilter.invalidateList("LockScreenNotiStateChanged");
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.mBarState != i) {
            this.mBarState = i;
            this.mIsDetail = false;
            Log.d("LockscreenNotificationManager", "BarState updated : " + this.mBarState);
            updateNotificationType();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0081 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateNotificationType() {
        int i;
        int i2;
        if (this.mStatusBarStateController.getState() == 1) {
            if (this.mIsKeyguardSupportLandscapePhone && this.mCurrentOrientation == 2 && !this.mIsFolderStateOpen) {
                i = 2;
            } else if (this.mSettingNotificationType == 1 && !this.mIsDetail) {
                i = 1;
            }
            i2 = this.mCurrentNotificationType;
            if (i == i2) {
                boolean z = i == 0 && i2 == 2;
                this.mCurrentNotificationType = i;
                synchronized (this.mLock) {
                    this.mHandler.removeMessages(100);
                    this.mHandler.obtainMessage(100, this.mCurrentNotificationType, 0, null).sendToTarget();
                }
                LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = this.mLockScreenNotificationStateListener;
                if (lockScreenNotiIconCoordinator != null) {
                    lockScreenNotiIconCoordinator.mNotifFilter.invalidateList("LockScreenNotiStateChanged");
                }
                if (z) {
                    this.mStackScrollLayout.updateVisibility();
                    NotificationStackScrollLayout notificationStackScrollLayout = this.mStackScrollLayout;
                    notificationStackScrollLayout.mSpeedBumpIndexDirty = true;
                    if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled) {
                        notificationStackScrollLayout.mEverythingNeedsAnimation = false;
                        notificationStackScrollLayout.mNeedsAnimation = false;
                        notificationStackScrollLayout.requestChildrenUpdate();
                    }
                }
                if (this.mCurrentNotificationType == 2) {
                    ((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).resetTransformAnimation();
                    return;
                }
                return;
            }
            return;
        }
        i = 0;
        i2 = this.mCurrentNotificationType;
        if (i == i2) {
        }
    }

    public final void onTableModeChanged(boolean z) {
    }
}
