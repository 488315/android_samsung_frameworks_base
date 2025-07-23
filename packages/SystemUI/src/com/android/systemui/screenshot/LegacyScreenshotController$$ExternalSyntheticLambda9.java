package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.screenshot.scroll.LongScreenshotActivity;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyScreenshotController$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LegacyScreenshotController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ LegacyScreenshotController$$ExternalSyntheticLambda9(LegacyScreenshotController legacyScreenshotController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = legacyScreenshotController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LegacyScreenshotController legacyScreenshotController = this.f$0;
                ScreenshotData screenshotData = (ScreenshotData) this.f$1;
                legacyScreenshotController.getClass();
                int identifier = screenshotData.userHandle.getIdentifier();
                ScreenshotShelfViewProxy screenshotShelfViewProxy = legacyScreenshotController.mViewProxy;
                Objects.requireNonNull(screenshotShelfViewProxy);
                LegacyScreenshotController$$ExternalSyntheticLambda10 legacyScreenshotController$$ExternalSyntheticLambda10 = new LegacyScreenshotController$$ExternalSyntheticLambda10(screenshotShelfViewProxy, 1);
                AnnouncementResolver announcementResolver = legacyScreenshotController.mAnnouncementResolver;
                announcementResolver.getClass();
                CoroutineTracingKt.launchTraced$default(announcementResolver.mainScope, null, null, new AnnouncementResolver$getScreenshotAnnouncement$2(legacyScreenshotController$$ExternalSyntheticLambda10, announcementResolver, identifier, null), 7);
                break;
            case 1:
                LegacyScreenshotController legacyScreenshotController2 = this.f$0;
                legacyScreenshotController2.mMessageContainerController.onScreenshotTaken((ScreenshotData) this.f$1);
                break;
            default:
                LegacyScreenshotController legacyScreenshotController3 = this.f$0;
                UserHandle userHandle = (UserHandle) this.f$1;
                ActionIntentCreator actionIntentCreator = legacyScreenshotController3.mActionIntentCreator;
                actionIntentCreator.getClass();
                legacyScreenshotController3.mContext.startActivity(new Intent(actionIntentCreator.context, (Class<?>) LongScreenshotActivity.class).putExtra("screenshot-userhandle", userHandle).addFlags(268435456).addFlags(67108864).addFlags(65536));
                break;
        }
    }
}
