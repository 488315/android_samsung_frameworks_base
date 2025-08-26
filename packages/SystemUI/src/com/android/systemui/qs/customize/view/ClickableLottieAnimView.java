package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ClickableLottieAnimView extends LottieAnimationView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnimType animType;

    public /* synthetic */ ClickableLottieAnimView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public ClickableLottieAnimView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animType = AnimType.SEPARATE;
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.customize.view.ClickableLottieAnimView$getButtonAccessibilityDelegate$1

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AnimType.values().length];
                    try {
                        iArr[AnimType.SEPARATE.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AnimType.TOGETHER.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                int i;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                int i2 = WhenMappings.$EnumSwitchMapping$0[this.this$0.animType.ordinal()];
                if (i2 == 1) {
                    i = R.string.qs_edit_separate_anim_name;
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i = R.string.qs_edit_together_anim_name;
                }
                accessibilityNodeInfo.setText(this.this$0.getContext().getText(i));
            }
        });
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isRemoveAnimation()) {
            setFocusable(1);
        } else {
            setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.view.ClickableLottieAnimView$playNstopClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i;
                    ClickableLottieAnimView clickableLottieAnimView = this.this$0;
                    int i2 = ClickableLottieAnimView.$r8$clinit;
                    clickableLottieAnimView.getClass();
                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isRemoveAnimation()) {
                        return;
                    }
                    if (this.this$0.isAnimating()) {
                        this.this$0.pauseAnimation();
                    } else {
                        this.this$0.resumeAnimation();
                    }
                    ClickableLottieAnimView clickableLottieAnimView2 = this.this$0;
                    boolean zIsAnimating = clickableLottieAnimView2.isAnimating();
                    if (zIsAnimating) {
                        i = R.string.qs_edit_anim_play;
                    } else {
                        if (zIsAnimating) {
                            throw new NoWhenBranchMatchedException();
                        }
                        i = R.string.qs_edit_anim_stop;
                    }
                    clickableLottieAnimView2.announceForAccessibility(this.this$0.getContext().getText(i));
                }
            });
        }
    }
}
