package com.android.systemui.p016qs.buttons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.SecTouchLogHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSButtonsContainer extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public CloseTooltipWindow mCloseTooltipWindow;
    public final C21161 mDismissReceiver;
    public QSEditButton mEditButton;
    public boolean mExpanded;
    public boolean mListening;
    public final StringBuilder mLogBuilder;
    public QSMumButton mMumButton;
    public QSPowerButton mPowerButton;
    public QSSettingsButton mSettingsButton;
    public final SecTouchLogHelper mTouchLogHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CloseTooltipWindow {
        void closeTooltip();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.buttons.QSButtonsContainer$1] */
    public QSButtonsContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchLogHelper = new SecTouchLogHelper();
        this.mLogBuilder = new StringBuilder();
        this.mDismissReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.buttons.QSButtonsContainer.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                CloseTooltipWindow closeTooltipWindow;
                Log.d("QSButtonsContainer", "action:" + intent.getAction());
                if (!"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction()) || (closeTooltipWindow = QSButtonsContainer.this.mCloseTooltipWindow) == null) {
                    return;
                }
                closeTooltipWindow.closeTooltip();
                QSButtonsContainer.this.mCloseTooltipWindow = null;
            }
        };
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
    }

    public final String getTouchLogText() {
        this.mLogBuilder.setLength(0);
        StringBuilder sb = this.mLogBuilder;
        sb.append("mExpanded: ");
        sb.append(this.mExpanded);
        sb.append(", mListening: ");
        sb.append(this.mListening);
        sb.append(", mQsDisabled: ");
        sb.append(false);
        return this.mLogBuilder.toString();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.intent.action.CLOSE_SYSTEM_DIALOGS"), this.mDismissReceiver);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        post(new QSButtonsContainer$$ExternalSyntheticLambda0(this));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mCloseTooltipWindow != null) {
            this.mCloseTooltipWindow = null;
        }
        this.mBroadcastDispatcher.unregisterReceiver(this.mDismissReceiver);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSettingsButton = (QSSettingsButton) findViewById(R.id.settings_button_container);
        this.mEditButton = (QSEditButton) findViewById(R.id.edit_button_container);
        this.mPowerButton = (QSPowerButton) findViewById(R.id.power_button_container);
        this.mMumButton = (QSMumButton) findViewById(R.id.mum_button_container);
        post(new QSButtonsContainer$$ExternalSyntheticLambda0(this));
        int color = ((LinearLayout) this).mContext.getColor(R.color.sec_qs_header_tint_color);
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setColorFilter(color);
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setColorFilter(color);
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setColorFilter(color);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.mTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "QSButtonsContainer", getTouchLogText());
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mTouchLogHelper.printOnTouchEventLog(motionEvent, "QSButtonsContainer", getTouchLogText());
        return super.onTouchEvent(motionEvent);
    }
}
