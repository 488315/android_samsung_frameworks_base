package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.widget.AccountViewAdapter;

/* loaded from: classes6.dex */
public class AccountItemView extends LinearLayout {
    private ImageView mAccountIcon;
    private TextView mAccountName;
    private TextView mAccountNumber;

    public AccountItemView(Context context) {
        this(context, null);
    }

    public AccountItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_account_item, (ViewGroup) null);
        addView(inflate);
        initViewItem(inflate);
    }

    private void initViewItem(View view) {
        this.mAccountIcon = (ImageView) view.findViewById(16908294);
        this.mAccountName = (TextView) view.findViewById(16908310);
        this.mAccountNumber = (TextView) view.findViewById(16908304);
    }

    public void setViewItem(AccountViewAdapter.AccountElements accountElements) {
        Drawable drawable = accountElements.getDrawable();
        if (drawable != null) {
            setAccountIcon(drawable);
        } else {
            setAccountIcon(accountElements.getIcon());
        }
        setAccountName(accountElements.getName());
        setAccountNumber(accountElements.getNumber());
    }

    public void setAccountIcon(int i) {
        this.mAccountIcon.setImageResource(i);
    }

    public void setAccountIcon(Drawable drawable) {
        this.mAccountIcon.setBackgroundDrawable(drawable);
    }

    public void setAccountName(String str) {
        setText(this.mAccountName, str);
    }

    public void setAccountNumber(String str) {
        setText(this.mAccountNumber, str);
    }

    private void setText(TextView textView, String str) {
        if (TextUtils.isEmpty(str)) {
            textView.setVisibility(8);
        } else {
            textView.lambda$setTextAsync$0(str);
            textView.setVisibility(0);
        }
    }
}
