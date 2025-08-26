package com.samsung.android.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.widget.SemTipPopup;

/* loaded from: classes6.dex */
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

    public void init(View view, String str) {
        this.mPopup = new SemTipPopup(view);
        this.mKey = str;
        this.mPrefrerences = this.mContext.getSharedPreferences(str, 0);
        this.mPopup.setBackgroundColor(Color.rgb(0, 140, 255));
        this.mPopup.setOutsideTouchEnabled(false);
    }

    public void setTitle(String str) {
        SpannableString spannableString = new SpannableString(str);
        this.mTitle = spannableString;
        spannableString.setSpan(new RelativeSizeSpan(1.25f), 0, str.length(), 33);
        this.mTitle.setSpan(new StyleSpan(1), 0, str.length(), 33);
    }

    public void setContent(String str) {
        this.mContent = new SpannableString(str);
    }

    public void show(int i) {
        SemTipPopup semTipPopup = this.mPopup;
        if (semTipPopup == null) {
            return;
        }
        SpannableString spannableString = this.mTitle;
        if (spannableString != null) {
            semTipPopup.setMessage(TextUtils.concat(spannableString, "\n\n", this.mContent));
        } else {
            semTipPopup.setMessage(this.mContent);
        }
        int i2 = this.mPrefrerences.getInt(Contract.Events.Projection.COUNT_ONLY, 1);
        if (i2 >= 5) {
            return;
        }
        if (i2 == 1) {
            this.mPopup.setExpanded(true);
        } else {
            this.mPopup.setExpanded(false);
        }
        this.mPopup.show(i);
        this.mPopup.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.samsung.android.globalactions.util.SemTipPopupWrapper$$ExternalSyntheticLambda0
            @Override // com.samsung.android.widget.SemTipPopup.OnStateChangeListener
            public final void onStateChanged(int i3) {
                this.f$0.lambda$show$0(i3);
            }
        });
        addCount(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(int i) {
        if (i == 2) {
            hideTipPermanently();
        }
    }

    private void addCount(int i) {
        SharedPreferences.Editor editorEdit = this.mPrefrerences.edit();
        editorEdit.putInt(Contract.Events.Projection.COUNT_ONLY, i + 1);
        editorEdit.apply();
    }

    public void hideTipPermanently() {
        SharedPreferences.Editor editorEdit = this.mPrefrerences.edit();
        editorEdit.putInt(Contract.Events.Projection.COUNT_ONLY, 5);
        editorEdit.apply();
    }

    public void update() throws Resources.NotFoundException {
        this.mPopup.update();
    }

    public void close() {
        if (this.mPopup.isShowing()) {
            this.mPopup.dismiss(false);
        }
    }
}
