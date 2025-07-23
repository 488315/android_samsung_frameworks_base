package com.android.systemui.media.controls.ui.controller;

import android.view.ViewGroup;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardMediaController$attachSinglePaneContainer$1 extends FunctionReferenceImpl implements Function1 {
    public KeyguardMediaController$attachSinglePaneContainer$1(Object obj) {
        super(1, obj, KeyguardMediaController.class, "onMediaHostVisibilityChanged", "onMediaHostVisibilityChanged(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        KeyguardMediaController keyguardMediaController = (KeyguardMediaController) this.receiver;
        if (booleanValue) {
            UniqueObjectHostView uniqueObjectHostView = keyguardMediaController.mediaHost.hostView;
            if (uniqueObjectHostView == null) {
                uniqueObjectHostView = null;
            }
            ViewGroup.LayoutParams layoutParams = uniqueObjectHostView.getLayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -1;
        } else {
            keyguardMediaController.getClass();
        }
        return Unit.INSTANCE;
    }
}
