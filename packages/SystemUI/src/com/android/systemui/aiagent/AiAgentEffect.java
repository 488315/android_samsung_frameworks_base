package com.android.systemui.aiagent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.view.OneShotPreDrawListener;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.util.AnimHelper;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.sesl.transparentvideo.TransparentVideoView;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class AiAgentEffect {
    public static final AnimHelper.AnimProperty HIDE_ANIM_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_ANIM_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimationType[] INIT_PROPERTY_FIELDS;
    public static final AnimHelper.AnimProperty SHOW_ANIM_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty SHOW_ANIM_TRANSLATION_Y_PROPERTY;
    public final PrivacyItemController.Callback callback;
    public final Context context;
    public PowerManager.WakeLock drawWakeLock;
    public AnimatorSet hideAnimSet;
    public boolean isAnimating;
    public boolean isAttachedView;
    public boolean isShowAnimating;
    public boolean isVideoPlaying;
    public boolean isVisibleView;
    public int lastDensityDpi;
    public long lastTimeStampElapsed;
    public View layout;
    public final WindowManager.LayoutParams layoutParams;
    public final PowerManager powerManager;
    public final PrivacyItemController privacyController;
    public final AssetFileDescriptor resourceFd;
    public AnimatorSet showAnimSet;
    public TransparentVideoView videoView;
    public final WindowManager windowManager;
    public final ArrayList showAnimList = new ArrayList();
    public final ArrayList hideAnimList = new ArrayList();
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    public AnimHelper.AnimationState animState = AnimHelper.AnimationState.HIDING;
    public final TransparentVideoView.Configs config = new TransparentVideoView.Configs(0.2f, "putUniqueIdForThisView", null, false, true, true, Float.valueOf(30.0f));

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class HideAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public HideAnimatorListener(String str) {
            super("AiAgentEffect", str, true);
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            AiAgentEffect aiAgentEffect = AiAgentEffect.this;
            AnimHelper.AnimationType[] animationTypeArr = AiAgentEffect.INIT_PROPERTY_FIELDS;
            aiAgentEffect.getClass();
            AiAgentEffect.this.initAnimProperties(true);
            AiAgentEffect aiAgentEffect2 = AiAgentEffect.this;
            aiAgentEffect2.getClass();
            try {
                PowerManager.WakeLock wakeLock = aiAgentEffect2.drawWakeLock;
                if (wakeLock != null) {
                    wakeLock.release();
                }
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m("releaseDrawWakeLock exception = ", e, "AiAgentEffect");
            }
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            AiAgentEffect aiAgentEffect = AiAgentEffect.this;
            AnimHelper.AnimationType[] animationTypeArr = AiAgentEffect.INIT_PROPERTY_FIELDS;
            aiAgentEffect.getClass();
            AiAgentEffect.this.isShowAnimating = false;
        }
    }

    public final class ShowAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public ShowAnimatorListener(String str) {
            super("AiAgentEffect", str, true);
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            AnimatorSet animatorSet;
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                AiAgentEffect aiAgentEffect = AiAgentEffect.this;
                boolean z = aiAgentEffect.isAnimating;
                AnimatorSet animatorSet2 = aiAgentEffect.showAnimSet;
                Boolean valueOf = animatorSet2 != null ? Boolean.valueOf(animatorSet2.isRunning()) : null;
                Log.d(tag, logPrefix + " onAnimationCancel isAnimating = " + z + ", isRunning = " + valueOf + ", isAttachedView = " + AiAgentEffect.this.isAttachedView);
            }
            setCanceled(true);
            AiAgentEffect aiAgentEffect2 = AiAgentEffect.this;
            if (aiAgentEffect2.isAnimating && (animatorSet = aiAgentEffect2.showAnimSet) != null && animatorSet.isRunning()) {
                AnimatorSet animatorSet3 = AiAgentEffect.this.hideAnimSet;
                if (animatorSet3 != null) {
                    animatorSet3.start();
                    return;
                }
                return;
            }
            AiAgentEffect aiAgentEffect3 = AiAgentEffect.this;
            if (aiAgentEffect3.isAttachedView) {
                aiAgentEffect3.initAnimProperties(true);
            } else {
                aiAgentEffect3.isAnimating = false;
            }
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            TransparentVideoView transparentVideoView;
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                AiAgentEffect aiAgentEffect = AiAgentEffect.this;
                boolean z = aiAgentEffect.isVisibleView;
                boolean z2 = aiAgentEffect.isAttachedView;
                View view = aiAgentEffect.layout;
                Log.d(tag, logPrefix + " onAnimationStart isVisibleView = " + z + ", isAttachedView = " + z2 + ", visible = " + (view != null ? Integer.valueOf(view.getVisibility()) : null));
            }
            AiAgentEffect aiAgentEffect2 = AiAgentEffect.this;
            if (!aiAgentEffect2.isVisibleView) {
                aiAgentEffect2.isVisibleView = true;
                aiAgentEffect2.updateLayoutVisibility();
            }
            String tag2 = getTag();
            String logPrefix2 = getLogPrefix();
            View view2 = AiAgentEffect.this.layout;
            Integer valueOf = view2 != null ? Integer.valueOf(view2.getVisibility()) : null;
            Log.d(tag2, logPrefix2 + " onAnimationStart visible = " + valueOf + ", layout = " + AiAgentEffect.this.layout);
            AiAgentEffect.this.initAnimProperties(false);
            setCanceled(false);
            AiAgentEffect aiAgentEffect3 = AiAgentEffect.this;
            aiAgentEffect3.isShowAnimating = true;
            try {
                if (aiAgentEffect3.drawWakeLock == null) {
                    aiAgentEffect3.drawWakeLock = aiAgentEffect3.powerManager.newWakeLock(128, "AiAgentEffect");
                }
                PowerManager.WakeLock wakeLock = aiAgentEffect3.drawWakeLock;
                if (wakeLock != null) {
                    wakeLock.acquire();
                }
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m("applyDrawWakeLock exception = ", e, "AiAgentEffect");
            }
            AiAgentEffect aiAgentEffect4 = AiAgentEffect.this;
            if (aiAgentEffect4.isVideoPlaying || (transparentVideoView = aiAgentEffect4.videoView) == null) {
                return;
            }
            aiAgentEffect4.isVideoPlaying = true;
            transparentVideoView.load(aiAgentEffect4.resourceFd, aiAgentEffect4.config);
            transparentVideoView.play();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnimHelper.AnimationState.values().length];
            try {
                iArr[AnimHelper.AnimationState.SHOWING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnimHelper.AnimationState.HIDING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        AnimHelper.AnimationType animationType = AnimHelper.AnimationType.ALPHA;
        AnimHelper.AnimationType animationType2 = AnimHelper.AnimationType.TRANSLATION_Y;
        INIT_PROPERTY_FIELDS = new AnimHelper.AnimationType[]{animationType, animationType2};
        PathInterpolator pathInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.32f, 0.94f, 0.6f, 1.0f);
        PathInterpolator pathInterpolator3 = new PathInterpolator(0.16f, 0.0f, 0.3f, 1.0f);
        PathInterpolator pathInterpolator4 = new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f);
        SHOW_ANIM_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 500, 0, 0.0f, 1.0f, pathInterpolator2);
        SHOW_ANIM_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, 0.0f, -1.0f, pathInterpolator);
        HIDE_ANIM_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 200, 300, 1.0f, 0.0f, pathInterpolator4);
        HIDE_ANIM_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, -1.0f, 0.0f, pathInterpolator3);
    }

    public AiAgentEffect(Context context, PrivacyItemController privacyItemController, PowerManager powerManager) {
        this.context = context;
        this.privacyController = privacyItemController;
        this.powerManager = powerManager;
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.resourceFd = context.getResources().openRawResourceFd(R.raw.wave_mid);
        Log.d("AiAgentEffect", "init");
        this.lastDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.callback = new PrivacyItemController.Callback() { // from class: com.android.systemui.aiagent.AiAgentEffect$callback$1
            @Override // com.android.systemui.privacy.PrivacyItemController.Callback
            public final void onPrivacyItemsChanged(List list) {
                Object obj;
                ListPopupWindow$$ExternalSyntheticOutline0.m(list.size(), "onPrivacyItemsChanged privacyItems.size = ", "AiAgentEffect");
                AnimHelper.AnimationState animationState = AnimHelper.AnimationState.HIDING;
                AiAgentEffect aiAgentEffect = AiAgentEffect.this;
                aiAgentEffect.animState = animationState;
                if (list.isEmpty()) {
                    Log.d("AiAgentEffect", "onPrivacyItemsChanged privacyItems isEmpty");
                    aiAgentEffect.displayEffect(aiAgentEffect.animState);
                    aiAgentEffect.lastTimeStampElapsed = 0L;
                    return;
                }
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    PrivacyItem privacyItem = (PrivacyItem) obj;
                    if (privacyItem.privacyType == PrivacyType.TYPE_MICROPHONE) {
                        String str = privacyItem.application.packageName;
                        if (Intrinsics.areEqual(str, "com.google.android.googlequicksearchbox") || Intrinsics.areEqual(str, "com.samsung.android.bixby.wakeup")) {
                            break;
                        }
                    }
                }
                PrivacyItem privacyItem2 = (PrivacyItem) obj;
                aiAgentEffect.getClass();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("display usingMicItem = ", privacyItem2 != null ? privacyItem2.log : null, "AiAgentEffect");
                if (privacyItem2 != null) {
                    aiAgentEffect.animState = AnimHelper.AnimationState.NONE;
                    long j = aiAgentEffect.lastTimeStampElapsed;
                    long j2 = privacyItem2.timeStampElapsed;
                    if (j != j2) {
                        aiAgentEffect.lastTimeStampElapsed = j2;
                        aiAgentEffect.animState = AnimHelper.AnimationState.SHOWING;
                    }
                }
                aiAgentEffect.displayEffect(aiAgentEffect.animState);
            }
        };
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, VolteConstants.ErrorCode.REG_SUBSCRIBED, 1336, -3);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("AiAgentEffectView");
        layoutParams.setTrustedOverlay();
        this.layoutParams = layoutParams;
    }

    public final void displayEffect(AnimHelper.AnimationState animationState) {
        Log.d("AiAgentEffect", "displayEffect animState = " + animationState);
        int i = WhenMappings.$EnumSwitchMapping$0[animationState.ordinal()];
        if (i == 1) {
            showing();
            return;
        }
        if (i != 2) {
            return;
        }
        boolean z = this.isVisibleView;
        boolean z2 = this.isShowAnimating;
        boolean z3 = this.isAnimating;
        AnimatorSet animatorSet = this.showAnimSet;
        Boolean valueOf = animatorSet != null ? Boolean.valueOf(animatorSet.isRunning()) : null;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("hiding isVisibleView = ", ", isStartAnimating = ", ", isAnimating = ", z, z2);
        m.append(z3);
        m.append(", isShowRunning = ");
        m.append(valueOf);
        Log.d("AiAgentEffect", m.toString());
        if (this.settingsHelper.isRemoveAnimation() || !this.isAttachedView) {
            return;
        }
        if (this.isShowAnimating) {
            AnimatorSet animatorSet2 = this.showAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
            }
            AnimatorSet animatorSet3 = this.hideAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.start();
                return;
            }
            return;
        }
        if (!this.isVisibleView) {
            if (this.isAnimating) {
                this.isAnimating = false;
                return;
            }
            return;
        }
        AnimatorSet animatorSet4 = this.showAnimSet;
        if (animatorSet4 != null) {
            animatorSet4.cancel();
        }
        AnimatorSet animatorSet5 = this.hideAnimSet;
        if (animatorSet5 != null) {
            animatorSet5.start();
        }
    }

    public final void initAnimProperties(boolean z) {
        TransparentVideoView transparentVideoView;
        AnimHelper.INSTANCE.initProperty(INIT_PROPERTY_FIELDS, this.videoView);
        if (z) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("initAnimProperties VI layout visibility is GONE, isAnimating = ", ", isAttachedView = ", ", isVisibleView = ", this.isAnimating, this.isAttachedView), this.isVisibleView, "AiAgentEffect");
            this.isAnimating = false;
            if (this.isAttachedView) {
                this.isVisibleView = false;
                updateLayoutVisibility();
            }
            if (!this.isVideoPlaying || (transparentVideoView = this.videoView) == null) {
                return;
            }
            transparentVideoView.pause();
            TransparentVideoView.release$default(transparentVideoView);
            this.isVideoPlaying = false;
        }
    }

    public final void showing() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("showing isAnimating = ", " -> true, isAttachedView = ", ", isVisibleView = ", this.isAnimating, this.isAttachedView), this.isVisibleView, "AiAgentEffect");
        if (this.settingsHelper.isRemoveAnimation()) {
            return;
        }
        if (this.isAttachedView) {
            if (this.isAnimating) {
                Log.d("AiAgentEffect", "start cancel anim duplicated anim");
                AnimatorSet animatorSet = this.showAnimSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                AnimatorSet animatorSet2 = this.hideAnimSet;
                if (animatorSet2 != null) {
                    animatorSet2.cancel();
                }
            }
            initAnimProperties(false);
            this.isAnimating = true;
            AnimatorSet animatorSet3 = this.showAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.start();
                return;
            }
            return;
        }
        this.isAttachedView = true;
        Log.d("AiAgentEffect", "updateLayoutAndAnimators config = " + this.context.getResources().getConfiguration());
        this.layout = LayoutInflater.from(this.context).inflate(R.layout.ai_agent_effect_layout, (ViewGroup) null);
        updateLayoutVisibility();
        View view = this.layout;
        this.videoView = view != null ? (TransparentVideoView) view.findViewById(R.id.ai_agent_effect) : null;
        initAnimProperties(false);
        AnimHelper animHelper = AnimHelper.INSTANCE;
        AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(this.videoView, SHOW_ANIM_ALPHA_PROPERTY);
        TransparentVideoView transparentVideoView = this.videoView;
        AnimHelper.AnimProperty animProperty = SHOW_ANIM_TRANSLATION_Y_PROPERTY;
        animProperty.setToValue(this.context.getResources().getDimensionPixelSize(R.dimen.ai_agent_effect_translation_y) * 0.9f);
        Unit unit = Unit.INSTANCE;
        this.showAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, new AnimHelper.AnimPairSet(transparentVideoView, animProperty)}, this.showAnimList, new ShowAnimatorListener("Show"));
        AnimHelper.AnimPairSet animPairSet2 = new AnimHelper.AnimPairSet(this.videoView, HIDE_ANIM_ALPHA_PROPERTY);
        TransparentVideoView transparentVideoView2 = this.videoView;
        AnimHelper.AnimProperty animProperty2 = HIDE_ANIM_TRANSLATION_Y_PROPERTY;
        animProperty2.setFromValue(this.context.getResources().getDimensionPixelSize(R.dimen.ai_agent_effect_translation_y) * 0.9f);
        this.hideAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet2, new AnimHelper.AnimPairSet(transparentVideoView2, animProperty2)}, this.hideAnimList, new HideAnimatorListener("Hide"));
        final TransparentVideoView transparentVideoView3 = this.videoView;
        if (transparentVideoView3 != null) {
            OneShotPreDrawListener.add(transparentVideoView3, new Runnable() { // from class: com.android.systemui.aiagent.AiAgentEffect$updateLayoutAndAnimators$$inlined$doOnPreDraw$1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("AiAgentEffect", "doOnPreDraw");
                    AiAgentEffect aiAgentEffect = this;
                    aiAgentEffect.isVisibleView = true;
                    aiAgentEffect.updateLayoutVisibility();
                    this.showing();
                }
            });
        }
        this.windowManager.addView(this.layout, this.layoutParams);
    }

    public final void updateConfiguration(Configuration configuration) {
        if (this.settingsHelper.isRemoveAnimation() || !this.isAttachedView) {
            this.lastDensityDpi = configuration.densityDpi;
            return;
        }
        int i = this.lastDensityDpi;
        int i2 = configuration.densityDpi;
        if (i != i2) {
            this.lastDensityDpi = i2;
        }
    }

    public final void updateLayoutVisibility() {
        View view = this.layout;
        if (view == null) {
            return;
        }
        view.setVisibility(this.isVisibleView ? 0 : 8);
    }
}
