package com.android.systemui.statusbar.iconsOnly;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginControllerImpl;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutModel;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LockscreenNotificationIconsOnlyController implements LockscreenNotificationManager.Callback, PluginLockListener.State {
    public final Context mContext;
    public final KeyguardStatusBarNioLayoutRepository mKeyguardStatusBarNioLayoutRepository;
    public final KeyguardTouchAnimator mKeyguardTouchAnimator;
    public NotificationPanelViewController mNPVController;
    public final FaceWidgetNotificationControllerWrapper mNotificationControllerWrapper;
    public LockscreenNotificationManager.NotificationIconData mNotificationIconData;
    public final PluginLockData mPluginLockData;
    public final PluginLockStarManager mPluginLockStarManager;
    public final LockscreenShadeTransitionController mShadeController;
    public LockscreenNotificationManager.NotificationIconData mSubUiNotificationIconData;
    public final AnonymousClass1 mWakefulnessObserver;
    public ArrayList mKeyguardIconArray = new ArrayList();
    public ArrayList mSubUiIconArray = new ArrayList();
    public final FaceWidgetNotificationController mFaceWidgetNotificationController = ((FaceWidgetPluginControllerImpl) Dependency.sDependency.getDependencyInner(FaceWidgetPluginControllerImpl.class)).mNotificationManager;
    public boolean mScreenOn = false;

    static {
        new AnimationProperties();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v8, types: [com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController$1, java.lang.Object] */
    public LockscreenNotificationIconsOnlyController(Context context, LockscreenShadeTransitionController lockscreenShadeTransitionController, KeyguardWallpaper keyguardWallpaper, PluginLockData pluginLockData, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository, KeyguardTouchAnimator keyguardTouchAnimator, PluginLockStarManager pluginLockStarManager) {
        ?? r4 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                Log.d("LockscreenNotificationIconsOnlyController", "mWakefulnessObserver onFinishedWakingUp ");
                LockscreenNotificationIconsOnlyController.this.mScreenOn = true;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                Log.d("LockscreenNotificationIconsOnlyController", "mWakefulnessObserver onStartedGoingToSleep ");
                LockscreenNotificationIconsOnlyController.this.mScreenOn = false;
            }
        };
        this.mWakefulnessObserver = r4;
        this.mContext = context;
        this.mShadeController = lockscreenShadeTransitionController;
        new TextView(context);
        this.mPluginLockData = pluginLockData;
        this.mNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.mKeyguardStatusBarNioLayoutRepository = keyguardStatusBarNioLayoutRepository;
        this.mKeyguardTouchAnimator = keyguardTouchAnimator;
        this.mPluginLockStarManager = pluginLockStarManager;
        faceWidgetNotificationControllerWrapper.mLockscreenNotificationIconsOnlyController = this;
        ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).addObserver(r4);
    }

    public final View getIconContainer() {
        PluginNotificationController pluginNotificationController = this.mNotificationControllerWrapper.mNotificationController;
        if (pluginNotificationController != null) {
            return pluginNotificationController.getIconContainer();
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
    public final void onNotificationInfoUpdated(ArrayList arrayList) {
        boolean z;
        Log.i("LockscreenNotificationIconsOnlyController", "onNotificationInfoUpdated " + arrayList.size());
        boolean z2 = LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY;
        int i = 0;
        FaceWidgetNotificationController faceWidgetNotificationController = this.mFaceWidgetNotificationController;
        if (z2 && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            if (this.mSubUiNotificationIconData == null) {
                this.mSubUiNotificationIconData = new LockscreenNotificationManager.NotificationIconData(this.mContext);
            }
            if (this.mSubUiIconArray == null) {
                this.mSubUiIconArray = new ArrayList();
            }
            if (this.mSubUiIconArray.size() > 0) {
                this.mSubUiIconArray.clear();
            }
            LockscreenNotificationManager.NotificationIconData notificationIconData = this.mSubUiNotificationIconData;
            notificationIconData.mTagFreshDrawable = R.id.tag_fresh_drawable;
            notificationIconData.mTagIsAppColor = R.id.tag_is_appcolor;
            notificationIconData.mTagShowConversation = R.id.tag_shows_conversation;
            if (notificationIconData.mIconArray.size() > 0) {
                this.mSubUiNotificationIconData.mIconArray.clear();
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (faceWidgetNotificationController != null) {
                    ((LockscreenNotificationInfo) arrayList.get(i2)).getClass();
                }
                StatusBarIconView statusBarIconView = ((LockscreenNotificationInfo) arrayList.get(i2)).mStatusBarIcon;
                StatusBarNotification statusBarNotification = ((LockscreenNotificationInfo) arrayList.get(i2)).mSbn;
                String str = ((LockscreenNotificationInfo) arrayList.get(i2)).mKey;
                this.mSubUiNotificationIconData.getClass();
                LockscreenNotificationManager.NotificationIconData notificationIconData2 = this.mSubUiNotificationIconData;
                ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;
                notificationIconData2.createImageViewIcon(statusBarIconView, statusBarNotification);
            }
            while (i < this.mSubUiNotificationIconData.mIconArray.size()) {
                ImageView imageView = new ImageView(this.mContext);
                if (((Drawable) ((ImageView) this.mSubUiNotificationIconData.mIconArray.get(i)).getTag(R.id.tag_fresh_drawable)) != null) {
                    imageView.setImageDrawable(((ImageView) this.mSubUiNotificationIconData.mIconArray.get(i)).getDrawable());
                    imageView.setTag(R.id.tag_fresh_drawable, ((ImageView) this.mSubUiNotificationIconData.mIconArray.get(i)).getDrawable());
                    imageView.setTag(R.id.tag_shows_conversation, ((ImageView) this.mSubUiNotificationIconData.mIconArray.get(i)).getTag(R.id.tag_shows_conversation));
                    this.mSubUiIconArray.add(imageView);
                }
                i++;
            }
            if (faceWidgetNotificationController != null) {
                try {
                    if (((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).getNotificationManager() != null) {
                        ((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).getNotificationManager().subLauncherUpdateNotification(this.mSubUiIconArray);
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    Log.e("LockscreenNotificationIconsOnlyController", "PluginSubScreen updateNotification error!!\n" + Log.getStackTraceString(th));
                    return;
                }
            }
            return;
        }
        if (this.mNotificationIconData == null) {
            this.mNotificationIconData = new LockscreenNotificationManager.NotificationIconData(this.mContext);
        }
        if (this.mKeyguardIconArray == null) {
            this.mKeyguardIconArray = new ArrayList();
        }
        if (this.mKeyguardIconArray.size() > 0) {
            this.mKeyguardIconArray.clear();
        }
        LockscreenNotificationManager.NotificationIconData notificationIconData3 = this.mNotificationIconData;
        notificationIconData3.mTagFreshDrawable = R.id.tag_fresh_drawable;
        notificationIconData3.mTagIsAppColor = R.id.tag_is_appcolor;
        notificationIconData3.mTagShowConversation = R.id.tag_shows_conversation;
        if (notificationIconData3.mIconArray.size() > 0) {
            this.mNotificationIconData.mIconArray.clear();
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            if (faceWidgetNotificationController != null) {
                ((LockscreenNotificationInfo) arrayList.get(i3)).getClass();
            }
            int i4 = LockscreenNotificationManager.mCurrentNotificationType;
            if ((i4 == 2 || i4 == 4) && ((LockscreenNotificationInfo) arrayList.get(i3)).mRow != null) {
                if (((LockscreenNotificationInfo) arrayList.get(i3)).mRow.mEntry.getAttachedNotifChildren() != null) {
                    List attachedNotifChildren = ((LockscreenNotificationInfo) arrayList.get(i3)).mRow.mEntry.getAttachedNotifChildren();
                    Objects.requireNonNull(attachedNotifChildren);
                    ArrayList arrayList2 = (ArrayList) attachedNotifChildren;
                    int size = arrayList2.size();
                    int i5 = 0;
                    while (true) {
                        while (i5 < size) {
                            Object obj = arrayList2.get(i5);
                            i5++;
                            z = z && ((NotificationEntry) obj).mIsReaded;
                        }
                    }
                    if (z) {
                        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("skip to show. all child is read notification when dot type. key = "), ((LockscreenNotificationInfo) arrayList.get(i3)).mKey, "LockscreenNotificationIconsOnlyController");
                    }
                } else if (((LockscreenNotificationInfo) arrayList.get(i3)).mRow.mEntry.mIsReaded) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("skip to show. read notification when dot type. key = "), ((LockscreenNotificationInfo) arrayList.get(i3)).mKey, "LockscreenNotificationIconsOnlyController");
                }
            }
            StatusBarIconView statusBarIconView2 = ((LockscreenNotificationInfo) arrayList.get(i3)).mStatusBarIcon;
            StatusBarNotification statusBarNotification2 = ((LockscreenNotificationInfo) arrayList.get(i3)).mSbn;
            String str2 = ((LockscreenNotificationInfo) arrayList.get(i3)).mKey;
            this.mNotificationIconData.getClass();
            LockscreenNotificationManager.NotificationIconData notificationIconData4 = this.mNotificationIconData;
            ImageView.ScaleType scaleType2 = ImageView.ScaleType.FIT_CENTER;
            notificationIconData4.createImageViewIcon(statusBarIconView2, statusBarNotification2);
        }
        while (i < this.mNotificationIconData.mIconArray.size()) {
            ImageView imageView2 = new ImageView(this.mContext);
            if (((Drawable) ((ImageView) this.mNotificationIconData.mIconArray.get(i)).getTag(R.id.tag_fresh_drawable)) != null) {
                imageView2.setImageDrawable(((ImageView) this.mNotificationIconData.mIconArray.get(i)).getDrawable());
                imageView2.setTag(R.id.tag_fresh_drawable, ((ImageView) this.mNotificationIconData.mIconArray.get(i)).getDrawable());
                imageView2.setTag(R.id.tag_shows_conversation, ((ImageView) this.mNotificationIconData.mIconArray.get(i)).getTag(R.id.tag_shows_conversation));
                this.mKeyguardIconArray.add(imageView2);
            }
            i++;
        }
        final int size2 = this.mKeyguardIconArray.size();
        final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.mKeyguardStatusBarNioLayoutRepository.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$updateKeyguardNioSize$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                int i6 = size2;
                if (keyguardStatusBarNioLayoutModel2.numberOfNio != i6) {
                    keyguardStatusBarNioLayoutModel2.numberOfNio = i6;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            }
        });
        if (faceWidgetNotificationController != null) {
            try {
                if (((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).getNotificationManager() != null) {
                    ((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).getNotificationManager().updateNotification(this.mKeyguardIconArray);
                }
            } catch (Throwable th2) {
                Log.e("LockscreenNotificationIconsOnlyController", "PluginSubScreen updateNotification error!!\n" + Log.getStackTraceString(th2));
            }
        }
    }

    @Override // com.android.systemui.statusbar.LockscreenNotificationManager.Callback
    public final void onNotificationTypeChanged(int i) {
        FaceWidgetNotificationController faceWidgetNotificationController = this.mFaceWidgetNotificationController;
        if (faceWidgetNotificationController != null) {
            try {
                if (((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).getNotificationManager() != null) {
                    ((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).getNotificationManager().lockscreenNotificationTypeChanged(i);
                    this.mKeyguardTouchAnimator.views.remove(3);
                }
            } catch (Throwable th) {
                Log.e("LockscreenNotificationIconsOnlyController", "PluginSubScreen updateNotification error!!\n" + Log.getStackTraceString(th));
            }
        }
    }
}
