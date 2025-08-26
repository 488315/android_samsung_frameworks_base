package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BasicRowInflater extends LayoutInflater {
    public static final String[] sClassPrefixList;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        sClassPrefixList = new String[]{"android.widget.", "android.webkit.", "android.app."};
    }

    public BasicRowInflater(Context context) {
        super(context);
    }

    @Override // android.view.LayoutInflater
    public final LayoutInflater cloneInContext(Context context) {
        return new BasicRowInflater(context);
    }

    @Override // android.view.LayoutInflater
    public final View onCreateView(String str, AttributeSet attributeSet) throws InflateException, ClassNotFoundException {
        View viewCreateView;
        for (String str2 : sClassPrefixList) {
            str2.getClass();
            try {
                viewCreateView = createView(str, str2, attributeSet);
            } catch (ClassNotFoundException unused) {
            }
            if (viewCreateView != null) {
                return viewCreateView;
            }
        }
        return super.onCreateView(str, attributeSet);
    }
}
