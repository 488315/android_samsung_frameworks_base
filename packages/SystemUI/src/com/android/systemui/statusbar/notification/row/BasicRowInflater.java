package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BasicRowInflater extends LayoutInflater {
    public static final String[] sClassPrefixList;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final View onCreateView(String str, AttributeSet attributeSet) {
        View createView;
        for (String str2 : sClassPrefixList) {
            str2.getClass();
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
}
