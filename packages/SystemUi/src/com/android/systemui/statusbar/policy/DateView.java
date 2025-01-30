package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.R$styleable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.DateView;
import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DateView extends TextView {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Date mCurrentTime;
    public DateFormat mDateFormat;
    public final String mDatePattern;
    public final C33811 mIntentReceiver;
    public String mLastText;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.DateView$1 */
    public final class C33811 extends BroadcastReceiver {
        public C33811() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Handler handler = DateView.this.getHandler();
            if (handler == null) {
                return;
            }
            String action = intent.getAction();
            if ("android.intent.action.TIME_TICK".equals(action) || "android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action) || "android.intent.action.LOCALE_CHANGED".equals(action)) {
                if ("android.intent.action.LOCALE_CHANGED".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                    final int i = 0;
                    handler.post(new Runnable(this) { // from class: com.android.systemui.statusbar.policy.DateView$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ DateView.C33811 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i) {
                                case 0:
                                    DateView.this.mDateFormat = null;
                                    break;
                                default:
                                    DateView.this.updateClock();
                                    break;
                            }
                        }
                    });
                }
                final int i2 = 1;
                handler.post(new Runnable(this) { // from class: com.android.systemui.statusbar.policy.DateView$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ DateView.C33811 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                DateView.this.mDateFormat = null;
                                break;
                            default:
                                DateView.this.updateClock();
                                break;
                        }
                    }
                });
            }
        }
    }

    public DateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentTime = new Date();
        this.mIntentReceiver = new C33811();
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DateView, 0, 0);
        try {
            String string = obtainStyledAttributes.getString(0);
            this.mDatePattern = string;
            if (string == null) {
                this.mDatePattern = getContext().getString(R.string.system_ui_date_pattern);
            }
            this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mIntentReceiver, intentFilter, (Handler) Dependency.get(Dependency.TIME_TICK_HANDLER));
        updateClock();
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDateFormat = null;
        this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
    }

    public final void updateClock() {
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
        setText(format);
        this.mLastText = format;
    }
}
