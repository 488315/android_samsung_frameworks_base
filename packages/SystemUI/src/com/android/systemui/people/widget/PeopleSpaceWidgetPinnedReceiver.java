package com.android.systemui.people.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PeopleSpaceWidgetPinnedReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;

    public PeopleSpaceWidgetPinnedReceiver(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int intExtra;
        if (context == null || intent == null || (intExtra = intent.getIntExtra("appWidgetId", -1)) == -1) {
            return;
        }
        PeopleTileKey peopleTileKey = new PeopleTileKey(intent.getStringExtra("android.intent.extra.shortcut.ID"), intent.getIntExtra("android.intent.extra.USER_ID", -1), intent.getStringExtra("android.intent.extra.PACKAGE_NAME"));
        if (PeopleTileKey.isValid(peopleTileKey)) {
            this.mPeopleSpaceWidgetManager.addNewWidget(intExtra, peopleTileKey);
        }
    }
}
