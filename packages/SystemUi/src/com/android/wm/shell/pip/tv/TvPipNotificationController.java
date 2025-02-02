package com.android.wm.shell.pip.tv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.wm.shell.pip.PipMediaController;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.tv.TvPipActionsProvider;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipNotificationController implements TvPipActionsProvider.Listener {
    public Bitmap mActivityIcon;
    public final Context mContext;
    public String mDefaultTitle;
    public boolean mIsNotificationShown;
    public MediaSession.Token mMediaSessionToken;
    public final Notification.Builder mNotificationBuilder;
    public final NotificationManager mNotificationManager;
    public final PackageManager mPackageManager;
    public String mPackageName;
    public Notification.Action[] mPipActions = new Notification.Action[0];
    public String mPipSubtitle;
    public String mPipTitle;
    public TvPipActionsProvider mTvPipActionsProvider;

    public TvPipNotificationController(Context context, PipMediaController pipMediaController, PipParamsChangedForwarder pipParamsChangedForwarder) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mNotificationBuilder = new Notification.Builder(context, "TVPIP").setLocalOnly(true).setOngoing(true).setCategory("sys").setShowWhen(true).setOnlyAlertOnce(true).setSmallIcon(R.drawable.pip_icon).setAllowSystemGeneratedContextualActions(false).setContentIntent(createPendingIntent(context, "com.android.wm.shell.pip.tv.notification.action.FULLSCREEN"));
        TvPipNotificationController$$ExternalSyntheticLambda0 tvPipNotificationController$$ExternalSyntheticLambda0 = new TvPipNotificationController$$ExternalSyntheticLambda0(this);
        ArrayList arrayList = pipMediaController.mTokenListeners;
        if (!arrayList.contains(tvPipNotificationController$$ExternalSyntheticLambda0)) {
            arrayList.add(tvPipNotificationController$$ExternalSyntheticLambda0);
            MediaController mediaController = pipMediaController.mMediaController;
            this.mMediaSessionToken = mediaController == null ? null : mediaController.getSessionToken();
            updateNotificationContent();
        }
        pipParamsChangedForwarder.addListener(new PipParamsChangedForwarder.PipParamsChangedCallback() { // from class: com.android.wm.shell.pip.tv.TvPipNotificationController.1
            @Override // com.android.wm.shell.pip.PipParamsChangedForwarder.PipParamsChangedCallback
            public final void onSubtitleChanged(String str) {
                TvPipNotificationController tvPipNotificationController = TvPipNotificationController.this;
                tvPipNotificationController.mPipSubtitle = str;
                tvPipNotificationController.updateNotificationContent();
            }

            @Override // com.android.wm.shell.pip.PipParamsChangedForwarder.PipParamsChangedCallback
            public final void onTitleChanged(String str) {
                TvPipNotificationController tvPipNotificationController = TvPipNotificationController.this;
                tvPipNotificationController.mPipTitle = str;
                tvPipNotificationController.updateNotificationContent();
            }
        });
        this.mDefaultTitle = context.getResources().getString(R.string.pip_notification_unknown_title);
        updateNotificationContent();
    }

    public static PendingIntent createPendingIntent(Context context, String str) {
        return PendingIntent.getBroadcast(context, 0, new Intent(str).setPackage(context.getPackageName()), 201326592);
    }

    @Override // com.android.wm.shell.pip.tv.TvPipActionsProvider.Listener
    public final void onActionsChanged(int i, int i2, int i3) {
        ArrayList arrayList = (ArrayList) this.mTvPipActionsProvider.mActionsList;
        this.mPipActions = new Notification.Action[arrayList.size()];
        int i4 = 0;
        while (true) {
            Notification.Action[] actionArr = this.mPipActions;
            if (i4 >= actionArr.length) {
                updateNotificationContent();
                return;
            } else {
                actionArr[i4] = ((TvPipAction) arrayList.get(i4)).toNotificationAction(this.mContext);
                i4++;
            }
        }
    }

    public final void updateNotificationContent() {
        String str;
        String str2;
        PackageManager packageManager = this.mPackageManager;
        if (packageManager == null || !this.mIsNotificationShown) {
            return;
        }
        String str3 = null;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            if (TextUtils.isEmpty(this.mPipTitle)) {
                try {
                    str2 = packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mPackageName, 0)).toString();
                } catch (PackageManager.NameNotFoundException unused) {
                    str2 = null;
                }
                if (TextUtils.isEmpty(str2)) {
                    str2 = this.mDefaultTitle;
                }
            } else {
                str2 = this.mPipTitle;
            }
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1328080840, 0, "%s: update(), title: %s, subtitle: %s, mediaSessionToken: %s, #actions: %s", "TvPipNotificationController", String.valueOf(str2), String.valueOf(this.mPipSubtitle), String.valueOf(this.mMediaSessionToken), String.valueOf(this.mPipActions.length));
        }
        long currentTimeMillis = System.currentTimeMillis();
        Notification.Builder builder = this.mNotificationBuilder;
        Notification.Builder when = builder.setWhen(currentTimeMillis);
        if (TextUtils.isEmpty(this.mPipTitle)) {
            try {
                str = packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mPackageName, 0)).toString();
            } catch (PackageManager.NameNotFoundException unused2) {
                str = null;
            }
            if (TextUtils.isEmpty(str)) {
                str = this.mDefaultTitle;
            }
        } else {
            str = this.mPipTitle;
        }
        Notification.Builder contentText = when.setContentTitle(str).setContentText(this.mPipSubtitle);
        try {
            str3 = packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mPackageName, 0)).toString();
        } catch (PackageManager.NameNotFoundException unused3) {
        }
        contentText.setSubText(str3).setActions(this.mPipActions);
        Bitmap bitmap = this.mActivityIcon;
        Context context = this.mContext;
        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
        } else {
            builder.setLargeIcon(Icon.createWithResource(context, R.drawable.pip_icon));
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.mediaSession", this.mMediaSessionToken);
        builder.setExtras(bundle);
        PendingIntent pendingIntent = ((TvPipAction) ((ArrayList) this.mTvPipActionsProvider.mActionsList).get(1)).getPendingIntent();
        builder.setDeleteIntent(pendingIntent);
        builder.extend(new Notification.TvExtender().setContentIntent(createPendingIntent(context, "com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU")).setDeleteIntent(pendingIntent));
        this.mNotificationManager.notify("TvPip", 1100, builder.build());
    }
}
