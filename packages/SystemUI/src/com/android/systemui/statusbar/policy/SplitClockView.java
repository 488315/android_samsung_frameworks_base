package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextClock;
import com.android.systemui.R;

public class SplitClockView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextClock mAmPmView;
    public final AnonymousClass1 mIntentReceiver;
    public TextClock mTimeView;

    public SplitClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.SplitClockView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action) || "android.intent.action.LOCALE_CHANGED".equals(action) || "android.intent.action.CONFIGURATION_CHANGED".equals(action) || "android.intent.action.USER_SWITCHED".equals(action)) {
                    SplitClockView splitClockView = SplitClockView.this;
                    int i = SplitClockView.$r8$clinit;
                    splitClockView.updatePatterns();
                }
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        getContext().registerReceiverAsUser(this.mIntentReceiver, UserHandle.ALL, intentFilter, null, null);
        updatePatterns();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(this.mIntentReceiver);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeView = (TextClock) findViewById(R.id.time_view);
        this.mAmPmView = (TextClock) findViewById(R.id.am_pm_view);
        this.mTimeView.setShowCurrentUserTime(true);
        this.mAmPmView.setShowCurrentUserTime(true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:
    
        r4 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePatterns() {
        /*
            r9 = this;
            android.content.Context r0 = r9.getContext()
            int r1 = android.app.ActivityManager.getCurrentUser()
            java.lang.String r0 = android.text.format.DateFormat.getTimeFormatString(r0, r1)
            int r1 = r0.length()
            r2 = 1
            int r1 = r1 - r2
            r3 = 0
            r4 = r1
            r5 = r3
        L15:
            r6 = -1
            if (r4 < 0) goto L3a
            char r7 = r0.charAt(r4)
            r8 = 97
            if (r7 != r8) goto L22
            r8 = r2
            goto L23
        L22:
            r8 = r3
        L23:
            boolean r7 = java.lang.Character.isWhitespace(r7)
            if (r8 == 0) goto L2a
            r5 = r2
        L2a:
            if (r8 != 0) goto L37
            if (r7 == 0) goto L2f
            goto L37
        L2f:
            if (r4 != r1) goto L33
        L31:
            r4 = r6
            goto L3d
        L33:
            if (r5 == 0) goto L31
            int r4 = r4 + r2
            goto L3d
        L37:
            int r4 = r4 + (-1)
            goto L15
        L3a:
            if (r5 == 0) goto L31
            r4 = r3
        L3d:
            if (r4 != r6) goto L44
            java.lang.String r1 = ""
            r2 = r1
            r1 = r0
            goto L4c
        L44:
            java.lang.String r1 = r0.substring(r3, r4)
            java.lang.String r2 = r0.substring(r4)
        L4c:
            android.widget.TextClock r3 = r9.mTimeView
            r3.setFormat12Hour(r1)
            android.widget.TextClock r3 = r9.mTimeView
            r3.setFormat24Hour(r1)
            android.widget.TextClock r1 = r9.mTimeView
            r1.setContentDescriptionFormat12Hour(r0)
            android.widget.TextClock r1 = r9.mTimeView
            r1.setContentDescriptionFormat24Hour(r0)
            android.widget.TextClock r0 = r9.mAmPmView
            r0.setFormat12Hour(r2)
            android.widget.TextClock r9 = r9.mAmPmView
            r9.setFormat24Hour(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SplitClockView.updatePatterns():void");
    }
}
