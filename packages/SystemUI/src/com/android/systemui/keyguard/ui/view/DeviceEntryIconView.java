package com.android.systemui.keyguard.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieListener;
import com.android.systemui.R;
import com.android.systemui.common.ui.view.TouchHandlingView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.log.TouchHandlingViewLogger;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class DeviceEntryIconView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityHintType accessibilityHintType;
    public final LottieDrawable aodFpDrawable;
    public final ImageView bgView;
    public final ImageView iconView;
    public final TouchHandlingView touchHandlingView;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class AccessibilityHintType {
        public static final /* synthetic */ AccessibilityHintType[] $VALUES;
        public static final AccessibilityHintType BOUNCER;
        public static final AccessibilityHintType ENTER;
        public static final AccessibilityHintType NONE;

        static {
            AccessibilityHintType accessibilityHintType = new AccessibilityHintType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = accessibilityHintType;
            AccessibilityHintType accessibilityHintType2 = new AccessibilityHintType("BOUNCER", 1);
            BOUNCER = accessibilityHintType2;
            AccessibilityHintType accessibilityHintType3 = new AccessibilityHintType("ENTER", 2);
            ENTER = accessibilityHintType3;
            AccessibilityHintType[] accessibilityHintTypeArr = {accessibilityHintType, accessibilityHintType2, accessibilityHintType3};
            $VALUES = accessibilityHintTypeArr;
            EnumEntriesKt.enumEntries(accessibilityHintTypeArr);
        }

        private AccessibilityHintType(String str, int i) {
        }

        public static AccessibilityHintType valueOf(String str) {
            return (AccessibilityHintType) Enum.valueOf(AccessibilityHintType.class, str);
        }

        public static AccessibilityHintType[] values() {
            return (AccessibilityHintType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class IconType {
        public static final /* synthetic */ IconType[] $VALUES;
        public static final IconType FINGERPRINT;
        public static final IconType LOCK;
        public static final IconType NONE;
        public static final IconType UNLOCK;
        private final int contentDescriptionResId;

        static {
            IconType iconType = new IconType("LOCK", 0, R.string.accessibility_lock_icon);
            LOCK = iconType;
            IconType iconType2 = new IconType("UNLOCK", 1, R.string.accessibility_unlock_button);
            UNLOCK = iconType2;
            IconType iconType3 = new IconType("FINGERPRINT", 2, R.string.accessibility_fingerprint_label);
            FINGERPRINT = iconType3;
            IconType iconType4 = new IconType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 3, -1);
            NONE = iconType4;
            IconType[] iconTypeArr = {iconType, iconType2, iconType3, iconType4};
            $VALUES = iconTypeArr;
            EnumEntriesKt.enumEntries(iconTypeArr);
        }

        private IconType(String str, int i, int i2) {
            this.contentDescriptionResId = i2;
        }

        public static IconType valueOf(String str) {
            return (IconType) Enum.valueOf(IconType.class, str);
        }

        public static IconType[] values() {
            return (IconType[]) $VALUES.clone();
        }

        public final int getContentDescriptionResId() {
            return this.contentDescriptionResId;
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IconType.values().length];
            try {
                iArr[IconType.LOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IconType.UNLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IconType.FINGERPRINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IconType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DeviceEntryIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, null, 12, null);
    }

    public static int[] getIconState(IconType iconType, boolean z) {
        int[] iArr = new int[2];
        int i = WhenMappings.$EnumSwitchMapping$0[iconType.ordinal()];
        if (i == 1) {
            iArr[0] = 16842916;
        } else if (i == 2) {
            iArr[0] = 16842918;
        } else {
            if (i != 3) {
                if (i == 4) {
                    return StateSet.NOTHING;
                }
                throw new NoWhenBranchMatchedException();
            }
            iArr[0] = 16842917;
        }
        if (z) {
            iArr[1] = 16842915;
            return iArr;
        }
        iArr[1] = -16842915;
        return iArr;
    }

    public DeviceEntryIconView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, null, 8, null);
    }

    public /* synthetic */ DeviceEntryIconView(Context context, AttributeSet attributeSet, int i, TouchHandlingViewLogger touchHandlingViewLogger, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i2 & 4) != 0 ? 0 : i, (i2 & 8) != 0 ? null : touchHandlingViewLogger);
    }

    public DeviceEntryIconView(Context context, AttributeSet attributeSet, int i, TouchHandlingViewLogger touchHandlingViewLogger) {
        super(context, attributeSet, i);
        TouchHandlingView touchHandlingView = new TouchHandlingView(context, attributeSet, new DeviceEntryIconView$$ExternalSyntheticLambda0(), ViewConfiguration.get(context).getScaledTouchSlop(), touchHandlingViewLogger);
        this.touchHandlingView = touchHandlingView;
        ImageView imageView = new ImageView(context, attributeSet);
        imageView.setId(R.id.device_entry_icon_fg);
        this.iconView = imageView;
        ImageView imageView2 = new ImageView(context, attributeSet);
        imageView2.setId(R.id.device_entry_icon_bg);
        this.bgView = imageView2;
        LottieDrawable lottieDrawable = new LottieDrawable();
        this.aodFpDrawable = lottieDrawable;
        this.accessibilityHintType = AccessibilityHintType.NONE;
        AnimatedStateListDrawable animatedStateListDrawable = new AnimatedStateListDrawable();
        IconType iconType = IconType.LOCK;
        int[] iconState = getIconState(iconType, false);
        Drawable drawable = getContext().getDrawable(R.drawable.ic_lock);
        drawable.getClass();
        animatedStateListDrawable.addState(iconState, drawable, R.id.locked);
        IconType iconType2 = IconType.UNLOCK;
        int[] iconState2 = getIconState(iconType2, false);
        Drawable drawable2 = getContext().getDrawable(R.drawable.ic_unlocked);
        drawable2.getClass();
        animatedStateListDrawable.addState(iconState2, drawable2, R.id.unlocked);
        IconType iconType3 = IconType.FINGERPRINT;
        int[] iconState3 = getIconState(iconType3, false);
        Drawable drawable3 = getContext().getDrawable(R.drawable.ic_fingerprint);
        drawable3.getClass();
        animatedStateListDrawable.addState(iconState3, drawable3, R.id.locked_fp);
        int[] iconState4 = getIconState(iconType, true);
        Drawable drawable4 = getContext().getDrawable(R.drawable.ic_lock_aod);
        drawable4.getClass();
        animatedStateListDrawable.addState(iconState4, drawable4, R.id.locked_aod);
        int[] iconState5 = getIconState(iconType2, true);
        Drawable drawable5 = getContext().getDrawable(R.drawable.ic_unlocked_aod);
        drawable5.getClass();
        animatedStateListDrawable.addState(iconState5, drawable5, R.id.unlocked_aod);
        LottieCompositionFactory.fromRawRes(R.raw.udfps_aod_fp, ((FrameLayout) this).mContext).addListener(new LottieListener() { // from class: com.android.systemui.keyguard.ui.view.DeviceEntryIconView$setupIconStates$1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                this.this$0.aodFpDrawable.setComposition((LottieComposition) obj);
            }
        });
        animatedStateListDrawable.addState(getIconState(iconType3, true), lottieDrawable, R.id.udfps_aod_fp);
        int[] iArr = StateSet.WILD_CARD;
        Drawable drawable6 = getContext().getDrawable(R.color.transparent);
        drawable6.getClass();
        animatedStateListDrawable.addState(iArr, drawable6, R.id.no_icon);
        animatedStateListDrawable.addTransition(R.id.locked_fp, R.id.unlocked, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.fp_to_unlock), false);
        animatedStateListDrawable.addTransition(R.id.unlocked, R.id.locked_fp, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.unlock_to_fp), false);
        animatedStateListDrawable.addTransition(R.id.locked_aod, R.id.locked, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.lock_aod_to_ls), false);
        animatedStateListDrawable.addTransition(R.id.locked, R.id.locked_aod, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.lock_ls_to_aod), false);
        animatedStateListDrawable.addTransition(R.id.unlocked_aod, R.id.unlocked, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.unlocked_aod_to_ls), false);
        animatedStateListDrawable.addTransition(R.id.unlocked, R.id.unlocked_aod, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.unlocked_ls_to_aod), false);
        animatedStateListDrawable.addTransition(R.id.locked, R.id.unlocked, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.lock_to_unlock), false);
        animatedStateListDrawable.addTransition(R.id.unlocked, R.id.locked, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.unlocked_to_locked), false);
        animatedStateListDrawable.addTransition(R.id.locked_fp, R.id.locked, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.fp_to_locked), false);
        animatedStateListDrawable.addTransition(R.id.unlocked, R.id.locked_aod, (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.unlocked_to_aod_lock), false);
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.keyguard.ui.view.DeviceEntryIconView$setupAccessibilityDelegate$1
            public final AccessibilityNodeInfo.AccessibilityAction accessibilityBouncerHint;
            public final AccessibilityNodeInfo.AccessibilityAction accessibilityEnterHint;

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[DeviceEntryIconView.AccessibilityHintType.values().length];
                    try {
                        iArr[DeviceEntryIconView.AccessibilityHintType.BOUNCER.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[DeviceEntryIconView.AccessibilityHintType.ENTER.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[DeviceEntryIconView.AccessibilityHintType.NONE.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            {
                this.accessibilityBouncerHint = new AccessibilityNodeInfo.AccessibilityAction(16, this.this$0.getResources().getString(R.string.accessibility_bouncer));
                this.accessibilityEnterHint = new AccessibilityNodeInfo.AccessibilityAction(16, this.this$0.getResources().getString(R.string.accessibility_enter_hint));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                int i2 = WhenMappings.$EnumSwitchMapping$0[this.this$0.accessibilityHintType.ordinal()];
                if (i2 == 1) {
                    accessibilityNodeInfo.addAction(this.accessibilityBouncerHint);
                } else if (i2 == 2) {
                    accessibilityNodeInfo.addAction(this.accessibilityEnterHint);
                } else if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
            }
        });
        imageView2.setImageDrawable(getContext().getDrawable(R.drawable.fingerprint_bg));
        addView(imageView2);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView2.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = -1;
        imageView2.setLayoutParams(layoutParams);
        imageView2.setAlpha(0.0f);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(animatedStateListDrawable);
        addView(imageView);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams2.height = -1;
        layoutParams2.width = -1;
        layoutParams2.gravity = 17;
        imageView.setLayoutParams(layoutParams2);
        addView(touchHandlingView);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) touchHandlingView.getLayoutParams();
        layoutParams3.height = -1;
        layoutParams3.width = -1;
        touchHandlingView.setLayoutParams(layoutParams3);
    }
}
