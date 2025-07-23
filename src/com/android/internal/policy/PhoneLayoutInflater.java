package com.android.internal.policy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/* loaded from: classes5.dex */
public class PhoneLayoutInflater extends LayoutInflater {
    private static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

    public PhoneLayoutInflater(Context context) {
        super(context);
    }

    protected PhoneLayoutInflater(LayoutInflater layoutInflater, Context context) {
        super(layoutInflater, context);
    }

    @Override // android.view.LayoutInflater
    protected View onCreateView(String str, AttributeSet attributeSet) throws ClassNotFoundException {
        View createView;
        for (String str2 : sClassPrefixList) {
            try {
                createView = createView(str, str2, attributeSet);
            } catch (ClassNotFoundException unused) {
            }
            if (createView != null) {
                return createView;
            }
        }
        return super.onCreateView(str, attributeSet);
    }

    @Override // android.view.LayoutInflater
    public LayoutInflater cloneInContext(Context context) {
        return new PhoneLayoutInflater(this, context);
    }
}
