package com.android.systemui.dextouchpad.manager;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.util.Utils;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarIconManager$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Context context = (Context) obj;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.navbar_icon_touchpad);
        remoteViews.setImageViewResource(R.id.navbar_icon_touchpad, R.drawable.ic_navbar_touchpad);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(0);
        remoteViews.setOnClickPendingIntent(R.id.navbar_icon_touchpad, PendingIntent.getActivityAsUser(context, 0, Utils.getTouchActivityIntent(), 67108864, makeBasic.toBundle(), UserHandle.CURRENT));
        return remoteViews;
    }
}
