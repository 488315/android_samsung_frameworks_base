package com.android.systemui.unfold;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.Trace;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.unfold.FullscreenLightRevealAnimationController;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.concurrency.ThreadFactory;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UnfoldLightRevealOverlayAnimation implements FullscreenLightRevealAnimation {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ContentResolver contentResolver;
    public final FeatureFlagsClassic featureFlags;
    public final boolean isUnfoldHandled;
    public AddOverlayReason overlayAddReason;
    public final ThreadFactory threadFactory;
    public final Handler unfoldProgressHandler;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class AddOverlayReason {
        public static final /* synthetic */ AddOverlayReason[] $VALUES;
        public static final AddOverlayReason FOLD;
        public static final AddOverlayReason UNFOLD;

        static {
            AddOverlayReason addOverlayReason = new AddOverlayReason("FOLD", 0);
            FOLD = addOverlayReason;
            AddOverlayReason addOverlayReason2 = new AddOverlayReason("UNFOLD", 1);
            UNFOLD = addOverlayReason2;
            AddOverlayReason[] addOverlayReasonArr = {addOverlayReason, addOverlayReason2};
            $VALUES = addOverlayReasonArr;
            EnumEntriesKt.enumEntries(addOverlayReasonArr);
        }

        private AddOverlayReason(String str, int i) {
        }

        public static AddOverlayReason valueOf(String str) {
            return (AddOverlayReason) Enum.valueOf(AddOverlayReason.class, str);
        }

        public static AddOverlayReason[] values() {
            return (AddOverlayReason[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public TransitionListener() {
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinished() {
            final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$TransitionListener$onTransitionFinished$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
                    unfoldLightRevealOverlayAnimation2.getClass();
                    throw null;
                }
            };
            int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
            unfoldLightRevealOverlayAnimation.executeInBackground(function0);
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionProgress(final float f) {
            final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$TransitionListener$onTransitionProgress$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
                    unfoldLightRevealOverlayAnimation2.getClass();
                    UnfoldLightRevealOverlayAnimation.this.calculateRevealAmount(Float.valueOf(f));
                    throw null;
                }
            };
            int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
            unfoldLightRevealOverlayAnimation.executeInBackground(function0);
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionStarted() {
            int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
            UnfoldLightRevealOverlayAnimation.this.getClass();
            throw null;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AddOverlayReason.values().length];
            try {
                iArr[AddOverlayReason.FOLD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AddOverlayReason.UNFOLD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public UnfoldLightRevealOverlayAnimation(Context context, FeatureFlagsClassic featureFlagsClassic, ContentResolver contentResolver, Handler handler, Provider provider, Provider provider2, DeviceStateManager deviceStateManager, ThreadFactory threadFactory, FullscreenLightRevealAnimationController.Factory factory) {
        this.featureFlags = featureFlagsClassic;
        this.contentResolver = contentResolver;
        this.unfoldProgressHandler = handler;
        this.threadFactory = threadFactory;
        new TransitionListener();
        this.isUnfoldHandled = true;
        this.overlayAddReason = AddOverlayReason.UNFOLD;
    }

    public final float calculateRevealAmount(Float f) {
        AddOverlayReason addOverlayReason = this.overlayAddReason;
        if (f != null) {
            Flags flags = Flags.INSTANCE;
            this.featureFlags.getClass();
            if (addOverlayReason == AddOverlayReason.FOLD) {
                return 1.0f;
            }
            return f.floatValue();
        }
        int i = WhenMappings.$EnumSwitchMapping$0[addOverlayReason.ordinal()];
        if (i == 1) {
            return 1.0f;
        }
        if (i == 2) {
            return 0.0f;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final void executeInBackground(final Function0 function0) {
        Handler handler = this.unfoldProgressHandler;
        if (handler.getLooper().isCurrentThread()) {
            function0.invoke();
        } else {
            handler.post(new Runnable() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$sam$java_lang_Runnable$0
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    Function0.this.invoke();
                }
            });
        }
    }

    @Override // com.android.systemui.unfold.FullscreenLightRevealAnimation
    public final void onScreenTurningOn(final Runnable runnable) {
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return;
        }
        executeInBackground(new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$onScreenTurningOn$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Trace.beginSection("UnfoldLightRevealOverlayAnimation#onScreenTurningOn");
                try {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
                    int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
                    unfoldLightRevealOverlayAnimation.getClass();
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    if (!unfoldLightRevealOverlayAnimation2.isUnfoldHandled) {
                        ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
                        ContentResolver contentResolver = unfoldLightRevealOverlayAnimation2.contentResolver;
                        companion.getClass();
                        if (ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver)) {
                            UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation3 = UnfoldLightRevealOverlayAnimation.this;
                            unfoldLightRevealOverlayAnimation3.overlayAddReason = UnfoldLightRevealOverlayAnimation.AddOverlayReason.UNFOLD;
                            unfoldLightRevealOverlayAnimation3.calculateRevealAmount(null);
                            throw null;
                        }
                    }
                    UnfoldLightRevealOverlayAnimation.this.getClass();
                    throw null;
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            }
        });
    }
}
