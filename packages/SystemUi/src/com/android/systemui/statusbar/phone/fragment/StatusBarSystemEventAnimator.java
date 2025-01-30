package com.android.systemui.statusbar.phone.fragment;

import android.content.res.Resources;
import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarSystemEventAnimator extends StatusBarSystemEventDefaultAnimator {
    public final View animatedView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventAnimator$1 */
    public /* synthetic */ class C31871 extends FunctionReferenceImpl implements Function1 {
        public C31871(Object obj) {
            super(1, obj, View.class, "setAlpha", "setAlpha(F)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((View) this.receiver).setAlpha(((Number) obj).floatValue());
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventAnimator$2 */
    public /* synthetic */ class C31882 extends FunctionReferenceImpl implements Function1 {
        public C31882(Object obj) {
            super(1, obj, View.class, "setTranslationX", "setTranslationX(F)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((View) this.receiver).setTranslationX(((Number) obj).floatValue());
            return Unit.INSTANCE;
        }
    }

    public StatusBarSystemEventAnimator(View view, Resources resources) {
        this(view, resources, false, 4, null);
    }

    public /* synthetic */ StatusBarSystemEventAnimator(View view, Resources resources, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, resources, (i & 4) != 0 ? false : z);
    }

    public StatusBarSystemEventAnimator(View view, Resources resources, boolean z) {
        super(resources, new C31871(view), new C31882(view), z);
        this.animatedView = view;
    }
}
