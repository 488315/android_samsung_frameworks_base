package com.android.systemui.aibrief.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import androidx.core.view.OneShotPreDrawListener;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aibrief.data.NowBarData;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.animation.SineOut90;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BriefViewController {
    private static final float BRIGHTNESS_FOR_AOD = 0.2f;
    public static final String SUGGESTION_BACKGROUND_DARK_KEY = "backgroundForDark";
    public static final String SUGGESTION_BACKGROUND_KEY = "background";
    public static final String SUGGESTION_DATA_KEY = "data";
    public static final String SUGGESTION_EXPAND_REMOTE_VIEWS_KEY = "expandRemoteViews";
    public static final String SUGGESTION_NORMAL_COVER_REMOTE_VIEWS_KEY = "normalRemoteViewsCover";
    public static final String SUGGESTION_NORMAL_REMOTE_VIEWS_KEY = "normalRemoteViews";
    public static final String SUGGESTION_PENDING_INTENT = "pendingIntent";
    public static final String TAG = "BriefViewController";
    private ArrayList<Integer> background;
    private Pair<? extends Drawable, ? extends Drawable> backgroundDrawable;
    private ArrayList<Integer> backgroundForDark;
    private final Context context;
    private boolean isDarkModeApplied;
    private final KeyguardStateController keyguardStateController;
    private final Configuration lastConfiguration = new Configuration();
    private final BriefLogger logger;
    private GradientDrawable nowBarBg;
    private BriefNowBarBaseView nowBarCoverView;
    private NowBarData nowBarData;
    private BriefNowBarBaseView nowBarView;
    private FrameLayout suggestionContainerWidget;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public BriefViewController(Context context, BriefLogger briefLogger, KeyguardStateController keyguardStateController) {
        this.context = context;
        this.logger = briefLogger;
        this.keyguardStateController = keyguardStateController;
    }

    public static /* synthetic */ View createBriefNowBarView$default(BriefViewController briefViewController, NowBarData nowBarData, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return briefViewController.createBriefNowBarView(nowBarData, z);
    }

    public static /* synthetic */ List getBackground$default(BriefViewController briefViewController, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        return briefViewController.getBackground(z, z2);
    }

    private final int getConvertedHSBColor(Integer num, float f) {
        float[] fArr = new float[3];
        if (num == null) {
            Color.colorToHSV(0, fArr);
        } else {
            Color.colorToHSV(num.intValue(), fArr);
        }
        fArr[2] = f;
        return Color.HSVToColor(fArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final Drawable getDrawableFromUri(String str) {
        InputStream inputStreamOpenInputStream;
        if (str != null) {
            try {
                Uri uri = Uri.parse(str);
                inputStreamOpenInputStream = uri != null ? this.context.getContentResolver().openInputStream(uri) : null;
            } catch (Exception e) {
                BriefLogger briefLogger = this.logger;
                e.printStackTrace();
                briefLogger.e(TAG, "Exception :  " + Unit.INSTANCE);
                return null;
            }
        }
        return Drawable.createFromStream(inputStreamOpenInputStream, str);
    }

    public static /* synthetic */ GradientDrawable getNowBarBackground$default(BriefViewController briefViewController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return briefViewController.getNowBarBackground(z);
    }

    private final Context getSubDisplayContext() {
        return this.context.createDisplayContext(((DisplayManager) this.context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1]);
    }

    private final void initBackgroundDataForRemoteView(Bundle bundle) {
        this.background = bundle.getIntegerArrayList(SUGGESTION_BACKGROUND_KEY);
        this.backgroundForDark = bundle.getIntegerArrayList(SUGGESTION_BACKGROUND_DARK_KEY);
    }

    private final boolean isDarkMode() {
        return (this.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    private final boolean isRemoteAndHasBackground(boolean z) {
        return (!z || this.background == null || this.backgroundForDark == null) ? false : true;
    }

    private final boolean needToSkipAnimation(FrameLayout frameLayout) {
        if (!frameLayout.isAttachedToWindow() || frameLayout.getVisibility() == 8) {
            this.logger.w(TAG, "R.layout.suggestion_container_widget is not attached or View.GONE");
            return true;
        }
        View viewFindViewById = frameLayout.findViewById(R.id.suggestion_container);
        if (viewFindViewById == null || !viewFindViewById.isAttachedToWindow()) {
            this.logger.w(TAG, "R.id.suggestion_container is not attached");
            return true;
        }
        if (!((KeyguardStateControllerImpl) this.keyguardStateController).mOccluded) {
            return false;
        }
        this.logger.w(TAG, "keyguard occluded, skip animation");
        return true;
    }

    private final void setFullViewBackground() {
        FrameLayout frameLayout = this.suggestionContainerWidget;
        if (frameLayout != null) {
            Drawable drawable = null;
            if (isDarkMode()) {
                Pair<? extends Drawable, ? extends Drawable> pair = this.backgroundDrawable;
                if (pair != null) {
                    drawable = (Drawable) pair.getFirst();
                }
            } else {
                Pair<? extends Drawable, ? extends Drawable> pair2 = this.backgroundDrawable;
                if (pair2 != null) {
                    drawable = (Drawable) pair2.getSecond();
                }
            }
            if (drawable != null) {
                frameLayout.setBackground(drawable);
                return;
            }
            GradientDrawable gradientDrawable = this.nowBarBg;
            if (gradientDrawable != null) {
                frameLayout.setBackground(gradientDrawable);
            } else {
                frameLayout.setBackgroundResource(R.drawable.brief_morning_bg);
            }
        }
    }

    private final boolean uiModeChanged(Configuration configuration) {
        return configuration.isNightModeActive() != this.isDarkModeApplied;
    }

    public final View createBriefNowBarView(NowBarData nowBarData, boolean z) {
        if (z && !BasicRune.BASIC_FOLDABLE_TYPE_FLIP) {
            return null;
        }
        this.lastConfiguration.setTo(this.context.getResources().getConfiguration());
        final BriefNowBarBaseView briefNowBarBaseView = (BriefNowBarBaseView) View.inflate(z ? getSubDisplayContext() : this.context, z ? R.layout.ai_brief_nowbar_cover_layout : R.layout.ai_brief_nowbar_layout, null);
        if (z) {
            this.nowBarCoverView = briefNowBarBaseView;
        } else {
            this.nowBarView = briefNowBarBaseView;
        }
        this.nowBarData = nowBarData;
        this.isDarkModeApplied = isDarkMode();
        if (nowBarData != null) {
            this.backgroundDrawable = new Pair<>(getDrawableFromUri(nowBarData.getFullBackgroundForDark()), getDrawableFromUri(nowBarData.getFullBackground()));
            this.nowBarBg = getNowBarBackground$default(this, false, 1, null);
            this.logger.d(TAG, "bg should be empty by LOCKUI_NOW_BAR_SUPPORT_GUIDING_EFFECT");
            briefNowBarBaseView.updateNowBarData(nowBarData, null);
        }
        if (Settings.System.getInt(this.context.getContentResolver(), SettingsHelper.INDEX_REMOVE_ANIMATION, 0) == 1) {
            briefNowBarBaseView.resetViews();
            return briefNowBarBaseView;
        }
        OneShotPreDrawListener.add(briefNowBarBaseView, new Runnable() { // from class: com.android.systemui.aibrief.ui.BriefViewController$createBriefNowBarView$$inlined$doOnPreDraw$1
            @Override // java.lang.Runnable
            public final void run() {
                briefNowBarBaseView.initAnimatedViews();
                briefNowBarBaseView.startFourStarAnimation();
            }
        });
        return briefNowBarBaseView;
    }

    public final RemoteViews createExpandRemoteView(Bundle bundle) {
        return (RemoteViews) bundle.getParcelable(SUGGESTION_EXPAND_REMOTE_VIEWS_KEY, RemoteViews.class);
    }

    public final View createFullView() {
        this.suggestionContainerWidget = (FrameLayout) LayoutInflater.from(this.context).inflate(R.layout.suggestion_container_widget, (ViewGroup) null, false);
        setFullViewBackground();
        FrameLayout frameLayout = this.suggestionContainerWidget;
        frameLayout.getClass();
        return frameLayout;
    }

    public final RemoteViews createNormalCoverRemoteView(Bundle bundle) {
        return (RemoteViews) bundle.getParcelable(SUGGESTION_NORMAL_COVER_REMOTE_VIEWS_KEY, RemoteViews.class);
    }

    public final RemoteViews createNormalRemoteView(Bundle bundle) {
        initBackgroundDataForRemoteView(bundle);
        return (RemoteViews) bundle.getParcelable(SUGGESTION_NORMAL_REMOTE_VIEWS_KEY, RemoteViews.class);
    }

    public final PendingIntent createPendingIntent(Bundle bundle) {
        return (PendingIntent) bundle.getParcelable(SUGGESTION_PENDING_INTENT, PendingIntent.class);
    }

    public final Drawable getActivityIcon(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(BriefNowBarController.SUGGESTION_PACKAGE, BriefNowBarController.SUGGESTION_ACTIVITY));
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 0);
        if (resolveInfoResolveActivity != null) {
            return resolveInfoResolveActivity.loadIcon(packageManager);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<Integer> getBackground(boolean z, boolean z2) {
        ArrayList<Integer> background;
        this.logger.d(TAG, "isScreenOn " + z);
        if (this.nowBarData == null && !isRemoteAndHasBackground(z2)) {
            return null;
        }
        if (isDarkMode()) {
            if (z2) {
                background = this.backgroundForDark;
            } else {
                NowBarData nowBarData = this.nowBarData;
                background = nowBarData != null ? nowBarData.getBackgroundForDark() : null;
            }
        } else if (z2) {
            background = this.background;
        } else {
            NowBarData nowBarData2 = this.nowBarData;
            if (nowBarData2 != null) {
                background = nowBarData2.getBackground();
            }
        }
        if (z) {
            return background;
        }
        if (background == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(background, 10));
        int size = background.size();
        int i = 0;
        while (i < size) {
            Integer num = background.get(i);
            i++;
            arrayList.add(Integer.valueOf(getConvertedHSBColor(Integer.valueOf(num.intValue()), BRIGHTNESS_FOR_AOD)));
        }
        return arrayList;
    }

    public final GradientDrawable getNowBarBackground(boolean z) {
        List background$default = getBackground$default(this, z, false, 2, null);
        ArrayList arrayList = new ArrayList();
        if (background$default != null) {
            Iterator it = background$default.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((Number) it.next()).intValue()));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, CollectionsKt___CollectionsKt.toIntArray(arrayList));
    }

    public final void hideContainer() {
        FrameLayout frameLayout = this.suggestionContainerWidget;
        if (frameLayout != null) {
            frameLayout.setAlpha(0.0f);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        BriefNowBarBaseView briefNowBarBaseView;
        if ((this.lastConfiguration.diff(configuration) & 1073747456) != 0) {
            BriefNowBarBaseView briefNowBarBaseView2 = this.nowBarView;
            if (briefNowBarBaseView2 != null) {
                briefNowBarBaseView2.updateNowBarResources();
            }
            setFullViewBackground();
            if (BasicRune.BASIC_FOLDABLE_TYPE_FLIP && (briefNowBarBaseView = this.nowBarCoverView) != null) {
                briefNowBarBaseView.updateNowBarResources();
            }
        }
        this.lastConfiguration.setTo(configuration);
        this.isDarkModeApplied = isDarkMode();
    }

    public final void setNowBarBackground(GradientDrawable gradientDrawable) {
        BriefNowBarBaseView briefNowBarBaseView = this.nowBarView;
        if (briefNowBarBaseView != null) {
            if (gradientDrawable != null) {
                briefNowBarBaseView.setBackground(gradientDrawable);
            } else {
                briefNowBarBaseView.setBackgroundResource(R.drawable.brief_morning_bg);
            }
        }
    }

    public final void showCircleAnimation(final Runnable runnable) {
        final FrameLayout frameLayout = this.suggestionContainerWidget;
        if (frameLayout == null) {
            runnable.run();
            return;
        }
        if (needToSkipAnimation(frameLayout)) {
            runnable.run();
            return;
        }
        frameLayout.setVisibility(0);
        Object systemService = this.context.getSystemService((Class<Object>) WindowManager.class);
        systemService.getClass();
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        View viewFindViewById = frameLayout.findViewById(R.id.suggestion_container);
        int i = point.x;
        int i2 = i / 2;
        int i3 = point.y;
        float fHypot = (float) Math.hypot(i, i3);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewFindViewById, "alpha", 0.0f, 1.0f);
        objectAnimatorOfFloat.setDuration(400L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(viewFindViewById, i2, i3, 0.0f, fHypot);
        animatorCreateCircularReveal.setDuration(500L);
        animatorCreateCircularReveal.setInterpolator(new SineOut90());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorCreateCircularReveal, objectAnimatorOfFloat);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.aibrief.ui.BriefViewController$showCircleAnimation$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                this.this$0.logger.d(BriefViewController.TAG, "showCircleAnimation onAnimationEnd");
                runnable.run();
                frameLayout.setVisibility(8);
            }
        });
        animatorSet.start();
    }

    public final void updateViewAlpha(boolean z) {
        BriefNowBarBaseView briefNowBarBaseView;
        BriefNowBarBaseView briefNowBarBaseView2 = this.nowBarView;
        if (briefNowBarBaseView2 != null) {
            briefNowBarBaseView2.updateViewAlpha(z);
        }
        if (!BasicRune.BASIC_FOLDABLE_TYPE_FLIP || (briefNowBarBaseView = this.nowBarCoverView) == null) {
            return;
        }
        briefNowBarBaseView.updateViewAlpha(z);
    }
}
