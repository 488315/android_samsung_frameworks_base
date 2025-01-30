package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.Date;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VariableDateViewController extends ViewController {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Date currentTime;
    public DateFormat dateFormat;
    public String datePattern;
    public final VariableDateViewController$intentReceiver$1 intentReceiver;
    public String lastText;
    public int lastWidth;
    public final VariableDateViewController$onMeasureListener$1 onMeasureListener;
    public final SystemClock systemClock;
    public final Handler timeTickHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final Handler handler;
        public final SystemClock systemClock;

        public Factory(SystemClock systemClock, BroadcastDispatcher broadcastDispatcher, Handler handler) {
            this.systemClock = systemClock;
            this.broadcastDispatcher = broadcastDispatcher;
            this.handler = handler;
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.policy.VariableDateViewController$intentReceiver$1] */
    public VariableDateViewController(SystemClock systemClock, BroadcastDispatcher broadcastDispatcher, Handler handler, VariableDateView variableDateView) {
        super(variableDateView);
        this.systemClock = systemClock;
        this.broadcastDispatcher = broadcastDispatcher;
        this.timeTickHandler = handler;
        this.datePattern = variableDateView.longerPattern;
        this.lastWidth = Integer.MAX_VALUE;
        this.lastText = "";
        this.currentTime = new Date();
        this.intentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.VariableDateViewController$intentReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Handler handler2 = ((VariableDateView) VariableDateViewController.this.mView).getHandler();
                if (handler2 == null) {
                    return;
                }
                String action = intent.getAction();
                if (Intrinsics.areEqual("android.intent.action.TIME_TICK", action) || Intrinsics.areEqual("android.intent.action.TIME_SET", action) || Intrinsics.areEqual("android.intent.action.TIMEZONE_CHANGED", action) || Intrinsics.areEqual("android.intent.action.LOCALE_CHANGED", action)) {
                    if (Intrinsics.areEqual("android.intent.action.LOCALE_CHANGED", action) || Intrinsics.areEqual("android.intent.action.TIMEZONE_CHANGED", action)) {
                        final VariableDateViewController variableDateViewController = VariableDateViewController.this;
                        handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.VariableDateViewController$intentReceiver$1$onReceive$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                VariableDateViewController.this.dateFormat = null;
                            }
                        });
                    }
                    final VariableDateViewController variableDateViewController2 = VariableDateViewController.this;
                    handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.VariableDateViewController$intentReceiver$1$onReceive$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            VariableDateViewController.access$updateClock(VariableDateViewController.this);
                        }
                    });
                }
            }
        };
        this.onMeasureListener = new VariableDateViewController$onMeasureListener$1(this);
    }

    public static final void access$updateClock(VariableDateViewController variableDateViewController) {
        if (variableDateViewController.dateFormat == null) {
            variableDateViewController.dateFormat = VariableDateViewControllerKt.getFormatFromPattern(variableDateViewController.datePattern);
        }
        Date date = variableDateViewController.currentTime;
        ((SystemClockImpl) variableDateViewController.systemClock).getClass();
        date.setTime(System.currentTimeMillis());
        DateFormat dateFormat = variableDateViewController.dateFormat;
        Intrinsics.checkNotNull(dateFormat);
        String textForFormat = VariableDateViewControllerKt.getTextForFormat(date, dateFormat);
        if (Intrinsics.areEqual(textForFormat, variableDateViewController.lastText)) {
            return;
        }
        ((VariableDateView) variableDateViewController.mView).setText(textForFormat);
        variableDateViewController.lastText = textForFormat;
    }

    public final void changePattern(String str) {
        if (str.equals(this.datePattern) || Intrinsics.areEqual(this.datePattern, str)) {
            return;
        }
        this.datePattern = str;
        this.dateFormat = null;
        View view = this.mView;
        if (view != null && view.isAttachedToWindow()) {
            VariableDateViewController$datePattern$1 variableDateViewController$datePattern$1 = new VariableDateViewController$datePattern$1(this);
            Handler handler = ((VariableDateView) this.mView).getHandler();
            if (handler != null) {
                handler.post(new VariableDateViewControllerKt$sam$java_lang_Runnable$0(variableDateViewController$datePattern$1));
            }
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.intentReceiver, intentFilter, new HandlerExecutor(this.timeTickHandler), UserHandle.SYSTEM, 0, null, 48);
        VariableDateViewController$onViewAttached$1 variableDateViewController$onViewAttached$1 = new VariableDateViewController$onViewAttached$1(this);
        Handler handler = ((VariableDateView) this.mView).getHandler();
        if (handler != null) {
            handler.post(new VariableDateViewControllerKt$sam$java_lang_Runnable$0(variableDateViewController$onViewAttached$1));
        }
        ((VariableDateView) this.mView).onMeasureListener = this.onMeasureListener;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.dateFormat = null;
        ((VariableDateView) this.mView).onMeasureListener = null;
        this.broadcastDispatcher.unregisterReceiver(this.intentReceiver);
    }
}
