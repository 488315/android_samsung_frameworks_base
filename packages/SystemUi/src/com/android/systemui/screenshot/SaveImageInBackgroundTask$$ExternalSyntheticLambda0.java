package com.android.systemui.screenshot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.systemui.screenshot.ScreenshotController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SaveImageInBackgroundTask$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ SaveImageInBackgroundTask f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ Uri f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Resources f$4;

    public /* synthetic */ SaveImageInBackgroundTask$$ExternalSyntheticLambda0(SaveImageInBackgroundTask saveImageInBackgroundTask, Context context, Uri uri, boolean z, Resources resources) {
        this.f$0 = saveImageInBackgroundTask;
        this.f$1 = context;
        this.f$2 = uri;
        this.f$3 = z;
        this.f$4 = resources;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                SaveImageInBackgroundTask saveImageInBackgroundTask = this.f$0;
                Context context = this.f$1;
                Uri uri = this.f$2;
                boolean z = this.f$3;
                Resources resources = this.f$4;
                ScreenshotController.SavedImageData.ActionTransition actionTransition = (ScreenshotController.SavedImageData.ActionTransition) saveImageInBackgroundTask.mSharedElementTransition.get();
                String string = context.getString(R.string.config_screenshotEditor);
                Intent intent = new Intent("android.intent.action.EDIT");
                if (!TextUtils.isEmpty(string)) {
                    intent.setComponent(ComponentName.unflattenFromString(string));
                }
                intent.setDataAndType(uri, "image/png");
                intent.addFlags(1);
                intent.addFlags(2);
                intent.addFlags(268468224);
                new Notification.Action.Builder(Icon.createWithResource(resources, R.drawable.ic_screenshot_edit), resources.getString(17042527), PendingIntent.getBroadcastAsUser(context, saveImageInBackgroundTask.mContext.getUserId(), new Intent(context, (Class<?>) ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context, 0, intent, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, actionTransition.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_id", saveImageInBackgroundTask.mScreenshotId).putExtra("android:smart_actions_enabled", z).putExtra("android:screenshot_override_transition", true).setAction("android.intent.action.EDIT").addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), 335544320, UserHandle.SYSTEM)).build();
                return actionTransition;
            default:
                SaveImageInBackgroundTask saveImageInBackgroundTask2 = this.f$0;
                Uri uri2 = this.f$2;
                Context context2 = this.f$1;
                boolean z2 = this.f$3;
                Resources resources2 = this.f$4;
                ScreenshotController.SavedImageData.ActionTransition actionTransition2 = (ScreenshotController.SavedImageData.ActionTransition) saveImageInBackgroundTask2.mSharedElementTransition.get();
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setDataAndType(uri2, "image/png");
                intent2.putExtra("android.intent.extra.STREAM", uri2);
                intent2.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uri2)));
                intent2.putExtra("android.intent.extra.SUBJECT", SaveImageInBackgroundTask.getSubjectString(saveImageInBackgroundTask2.mImageTime));
                intent2.addFlags(1).addFlags(2);
                new Notification.Action.Builder(Icon.createWithResource(resources2, R.drawable.ic_screenshot_share), resources2.getString(17042801), PendingIntent.getBroadcastAsUser(context2, context2.getUserId(), new Intent(context2, (Class<?>) ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context2, 0, Intent.createChooser(intent2, null).addFlags(268468224).addFlags(1), 335544320, actionTransition2.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_disallow_enter_pip", true).putExtra("android:screenshot_id", saveImageInBackgroundTask2.mScreenshotId).putExtra("android:smart_actions_enabled", z2).setAction("android.intent.action.SEND").addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), 335544320, UserHandle.SYSTEM)).build();
                return actionTransition2;
        }
    }

    public /* synthetic */ SaveImageInBackgroundTask$$ExternalSyntheticLambda0(SaveImageInBackgroundTask saveImageInBackgroundTask, Uri uri, Context context, boolean z, Resources resources) {
        this.f$0 = saveImageInBackgroundTask;
        this.f$2 = uri;
        this.f$1 = context;
        this.f$3 = z;
        this.f$4 = resources;
    }
}
