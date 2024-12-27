package com.samsung.android.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;

import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.widget.SemTipPopup;

public class SemTipPopupWrapper {
    private final Context mContext;
    private String mKey;
    private SemTipPopup mPopup;
    private SharedPreferences mPrefrerences;
    private final int BIXBY_TOOLTIP_DISPLAY_LIMIT_COUNT = 5;
    private SpannableString mTitle = null;
    private SpannableString mContent = null;

    public SemTipPopupWrapper(Context context) {
        this.mContext = context;
    }

    public void init(View view, String key) {
        this.mPopup = new SemTipPopup(view);
        this.mKey = key;
        this.mPrefrerences = this.mContext.getSharedPreferences(this.mKey, 0);
        this.mPopup.setBackgroundColor(Color.rgb(0, 140, 255));
        this.mPopup.setOutsideTouchEnabled(false);
    }

    public void setTitle(String msg) {
        this.mTitle = new SpannableString(msg);
        this.mTitle.setSpan(new RelativeSizeSpan(1.25f), 0, msg.length(), 33);
        this.mTitle.setSpan(new StyleSpan(1), 0, msg.length(), 33);
    }

    public void setContent(String msg) {
        this.mContent = new SpannableString(msg);
    }

    public void show(int direction) {
        if (this.mPopup == null) {
            return;
        }
        if (this.mTitle != null) {
            this.mPopup.setMessage(TextUtils.concat(this.mTitle, "\n\n", this.mContent));
        } else {
            this.mPopup.setMessage(this.mContent);
        }
        int count = this.mPrefrerences.getInt(Contract.Events.Projection.COUNT_ONLY, 1);
        if (count >= 5) {
            return;
        }
        if (count == 1) {
            this.mPopup.setExpanded(true);
        } else {
            this.mPopup.setExpanded(false);
        }
        this.mPopup.show(direction);
        this.mPopup.setOnStateChangeListener(
                new SemTipPopup
                        .OnStateChangeListener() { // from class:
                                                   // com.samsung.android.globalactions.util.SemTipPopupWrapper$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.widget.SemTipPopup.OnStateChangeListener
                    public final void onStateChanged(int i) {
                        SemTipPopupWrapper.this.lambda$show$0(i);
                    }
                });
        addCount(count);
    }

    public /* synthetic */ void lambda$show$0(int v) {
        if (v == 2) {
            hideTipPermanently();
        }
    }

    private void addCount(int count) {
        SharedPreferences.Editor editor = this.mPrefrerences.edit();
        editor.putInt(Contract.Events.Projection.COUNT_ONLY, count + 1);
        editor.apply();
    }

    public void hideTipPermanently() {
        SharedPreferences.Editor editor = this.mPrefrerences.edit();
        editor.putInt(Contract.Events.Projection.COUNT_ONLY, 5);
        editor.apply();
    }

    public void update() {
        this.mPopup.update();
    }

    public void close() {
        if (this.mPopup.isShowing()) {
            this.mPopup.dismiss(false);
        }
    }
}
