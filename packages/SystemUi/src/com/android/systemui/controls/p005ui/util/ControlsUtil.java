package com.android.systemui.controls.p005ui.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda2;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda5;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.io.ByteArrayInputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsUtil {
    public static final Companion Companion = new Companion(null);
    public final KeyguardStateController keyguardStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void setSize(View view, int i, int i2) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = i;
            layoutParams.height = i2;
            view.setLayoutParams(layoutParams);
        }

        public static void updateFontSize(TextView textView, int i, float f) {
            Float valueOf = Float.valueOf(textView.getResources().getConfiguration().fontScale);
            if (valueOf.floatValue() > f) {
                valueOf = null;
            }
            if (valueOf != null) {
                f = valueOf.floatValue();
            }
            textView.setTextSize(1, (textView.getResources().getDimensionPixelSize(i) / textView.getResources().getDisplayMetrics().scaledDensity) * f);
        }

        public static /* synthetic */ void updateFontSize$default(Companion companion, TextView textView, int i) {
            companion.getClass();
            updateFontSize(textView, i, 1.3f);
        }
    }

    public ControlsUtil(KeyguardStateController keyguardStateController) {
        this.keyguardStateController = keyguardStateController;
    }

    public static List getListOfServices(Context context, List list) {
        Object obj;
        final Collator collator = Collator.getInstance(context.getResources().getConfiguration().getLocales().get(0));
        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.controls.ui.util.ControlsUtil$getListOfServices$getLocaleComparator$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                return collator.compare(((ControlsServiceInfo) obj2).loadLabel(), ((ControlsServiceInfo) obj3).loadLabel());
            }
        });
        if (!BasicRune.CONTROLS_CUSTOM_SERVICES_INFO_ORDERING) {
            return sortedWith;
        }
        Iterator it = sortedWith.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual("com.samsung.android.oneconnect", ((ControlsServiceInfo) obj).componentName.getPackageName())) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        if (controlsServiceInfo == null) {
            return sortedWith;
        }
        ArrayList arrayList = new ArrayList(sortedWith);
        arrayList.remove(controlsServiceInfo);
        arrayList.add(0, controlsServiceInfo);
        return arrayList;
    }

    public static boolean isFoldDelta(Context context) {
        return context.getResources().getConfiguration().screenWidthDp == 320;
    }

    public static LottieAnimationView updateLottieIcon(Context context, ImageView imageView, View view, LottieAnimationView lottieAnimationView, String str, final String str2, int i, int i2, int i3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (lottieAnimationView == null) {
                LottieAnimationView lottieAnimationView2 = (LottieAnimationView) ((ViewStub) view.requireViewById(R.id.icon_custom_animation)).inflate();
                if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD && isFoldDelta(context)) {
                    int dimensionPixelSize = lottieAnimationView2.getContext().getResources().getDimensionPixelSize(R.dimen.control_custom_icon_size_fold);
                    Companion.getClass();
                    Companion.setSize(lottieAnimationView2, dimensionPixelSize, dimensionPixelSize);
                }
                lottieAnimationView = lottieAnimationView2;
            }
            imageView.setVisibility(8);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
            lottieAnimationView.getClass();
            lottieAnimationView.setCompositionTask(LottieCompositionFactory.cache(str2, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda1
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return LottieCompositionFactory.fromJsonInputStreamSync(byteArrayInputStream, str2);
                }
            }, new LottieCompositionFactory$$ExternalSyntheticLambda2(byteArrayInputStream)));
            lottieAnimationView.setVisibility(0);
            if (i3 > -1) {
                lottieAnimationView.setRepeatCount(i3);
            }
            if (i > -1 && i2 > -1) {
                LottieDrawable lottieDrawable = lottieAnimationView.lottieDrawable;
                if (lottieDrawable.composition == null) {
                    lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda5(lottieDrawable, i, i2));
                } else {
                    lottieDrawable.animator.setMinAndMaxFrames(i, i2 + 0.99f);
                }
            }
            lottieAnimationView.playAnimation();
        }
        return lottieAnimationView;
    }

    public final boolean isSecureLocked() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mShowing && ((KeyguardStateControllerImpl) keyguardStateController).mSecure;
    }
}
