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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;

public final class LockscreenNotificationManager implements ConfigurationController.ConfigurationListener, StatusBarStateController.StateListener, WakefulnessLifecycle.Observer, SemWindowManager.FoldStateListener {
    public static int mCurrentNotificationType;
    public int mBarState;
    public final Context mContext;
    public final KeyguardUpdateMonitorCallback mCoverCallback;
    public int mCurrentOrientation;
    public UserTracker.Callback mCurrentUserTrackerCallback;
    public boolean mIsFolded;
    public boolean mIsFolderStateOpen;
    public final boolean mIsKeyguardSupportLandscapePhone;
    public LockScreenNotiIconCoordinator mLockScreenNotificationStateListener;
    public final LockscreenNotificationManagerLogger mLogger;
    public final Uri mNotificationSettingUri;
    public int mSemDisplayDeviceType;
    public int mSettingNotificationType;
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListenerForNotificationStyle;
    public final StatusBarStateController mStatusBarStateController;
    public final Object mLock = new Object();
    public final ArrayList mCallbacks = new ArrayList();
    public final LockscreenNotificationMgrHandler mHandler = new LockscreenNotificationMgrHandler(this, Looper.getMainLooper(), 0);

    /* renamed from: com.android.systemui.statusbar.LockscreenNotificationManager$3, reason: invalid class name */
    class AnonymousClass3 implements UserTracker.Callback {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.statusbar.LockscreenNotificationManager$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsHelper.OnChangedCallback onChangedCallback;
                    LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
                    onChangedCallback = lockscreenNotificationManager.mSettingsListenerForNotificationStyle;
                    onChangedCallback.onChanged(lockscreenNotificationManager.mNotificationSettingUri);
                }
            });
        }
    }

    public interface Callback {
        void onNotificationInfoUpdated(ArrayList arrayList);

        void onNotificationTypeChanged(int i);
    }

    public final class LockscreenNotificationMgrHandler extends Handler {
        public /* synthetic */ LockscreenNotificationMgrHandler(LockscreenNotificationManager lockscreenNotificationManager, Looper looper, int i) {
            this(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
            if (i == 100) {
                while (i2 < lockscreenNotificationManager.mCallbacks.size()) {
                    ((Callback) lockscreenNotificationManager.mCallbacks.get(i2)).onNotificationTypeChanged(message.arg1);
                    i2++;
                }
                return;
            }
            if (i != 101) {
                return;
            }
            ArrayList arrayList = (ArrayList) message.obj;
            LockscreenNotificationManagerLogger lockscreenNotificationManagerLogger = lockscreenNotificationManager.mLogger;
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
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("NOTIF LOCK LIST ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = lockscreenNotificationManagerLogger.buffer;
            LogMessage obtain = logBuffer.obtain("LockNotifManager", logLevel, lockscreenNotificationManagerLogger$logNotifList$2, null);
            ((LogMessageImpl) obtain).str1 = stringBuffer.toString();
            logBuffer.commit(obtain);
            while (i2 < lockscreenNotificationManager.mCallbacks.size()) {
                ((Callback) lockscreenNotificationManager.mCallbacks.get(i2)).onNotificationInfoUpdated(arrayList);
                i2++;
            }
        }

        private LockscreenNotificationMgrHandler(Looper looper) {
            super(looper);
        }
    }

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
            try {
                Drawable icon = statusBarIconView.getIcon(statusBarIconView.mIcon.clone());
                this.mColor = NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(this.mContext)) ? statusBarNotification.getNotification().color : 0;
                int i = statusBarNotification.getNotification().iconLevel;
                boolean z = statusBarIconView.mShowsConversation;
                ImageView imageView = new ImageView(this.mContext);
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
                if (this.mIconArray.contains(imageView)) {
                    return;
                }
                this.mIconArray.add(imageView);
            } catch (NullPointerException e) {
                Log.e("LockscreenNotificationManager", "createImageViewIcon NullPointerException");
                e.printStackTrace();
            }
        }
    }

    public LockscreenNotificationManager(Context context, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, LockscreenNotificationManagerLogger lockscreenNotificationManagerLogger, UserTracker userTracker, Handler handler, ConfigurationController configurationController) {
        Uri uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION);
        this.mNotificationSettingUri = uriFor;
        this.mIsFolded = false;
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.LockscreenNotificationManager.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
                if (uri.equals(lockscreenNotificationManager.mNotificationSettingUri)) {
                    lockscreenNotificationManager.mSettingNotificationType = Settings.System.getIntForUser(lockscreenNotificationManager.mContext.getContentResolver(), SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION, 1, -2);
                    Log.d("LockscreenNotificationManager", " setting updated : " + lockscreenNotificationManager.mSettingNotificationType);
                    lockscreenNotificationManager.updateNotificationType();
                }
            }
        };
        this.mSettingsListenerForNotificationStyle = onChangedCallback;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback(this) { // from class: com.android.systemui.statusbar.LockscreenNotificationManager.4
        };
        this.mCoverCallback = keyguardUpdateMonitorCallback;
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        this.mSettingsHelper = settingsHelper;
        this.mLogger = lockscreenNotificationManagerLogger;
        this.mIsKeyguardSupportLandscapePhone = DeviceState.shouldEnableKeyguardScreenRotation(context) && !DeviceType.isTablet();
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.LockscreenNotificationManager.2
                @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
                public final void onFolderStateChanged(boolean z) {
                    LockscreenNotificationManager lockscreenNotificationManager = LockscreenNotificationManager.this;
                    lockscreenNotificationManager.mIsFolderStateOpen = z;
                    if (lockscreenNotificationManager.mStatusBarStateController.getState() == 1) {
                        lockscreenNotificationManager.updateNotificationType();
                    }
                }
            });
            this.mIsFolderStateOpen = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
        }
        statusBarStateController.addCallback(this);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        onConfigChanged(context.getResources().getConfiguration());
        settingsHelper.registerCallback(onChangedCallback, uriFor);
        onChangedCallback.onChanged(uriFor);
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mCurrentUserTrackerCallback = anonymousClass3;
        ((UserTrackerImpl) userTracker).addCallback(anonymousClass3, new HandlerExecutor(handler));
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_NOTIFICATION) {
            SemWindowManager.getInstance().registerFoldStateListener(this, (Handler) null);
        }
    }

    public static boolean isNotificationIconsOnlyShowing() {
        int i = mCurrentNotificationType;
        return i == 1 || i == 3 || i == 2 || i == 4;
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
            RecyclerView$$ExternalSyntheticOutline0.m(this.mSemDisplayDeviceType, "LockscreenNotificationManager", new StringBuilder("Fold state updated : "));
            this.mIsFolded = this.mSemDisplayDeviceType == 5;
            updateNotificationType();
        }
    }

    public final void onFoldStateChanged(boolean z) {
        Log.d("LockscreenNotificationManager", " FOLD STATE - ".concat(z ? "FOLD " : "UNFOLD "));
        if (this.mIsFolded != z) {
            this.mIsFolded = z;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.mBarState != i) {
            this.mBarState = i;
            Log.d("LockscreenNotificationManager", "BarState updated : " + this.mBarState);
            updateNotificationType();
        }
    }

    public final void updateNotificationType() {
        int i;
        int i2 = mCurrentNotificationType;
        if (this.mIsKeyguardSupportLandscapePhone && this.mCurrentOrientation == 2 && !this.mIsFolderStateOpen) {
            int i3 = this.mSettingNotificationType;
            i = (i3 == 0 || i3 == 1) ? 3 : i3 == 2 ? 4 : i2;
        } else {
            i = this.mSettingNotificationType;
        }
        if (i != i2) {
            mCurrentNotificationType = i;
            synchronized (this.mLock) {
                this.mHandler.removeMessages(100);
                this.mHandler.obtainMessage(100, mCurrentNotificationType, 0, null).sendToTarget();
            }
            LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = this.mLockScreenNotificationStateListener;
            if (lockScreenNotiIconCoordinator != null) {
                lockScreenNotiIconCoordinator.onLockScreenNotiStateChanged();
            }
        }
    }

    public final void onTableModeChanged(boolean z) {
    }
}
