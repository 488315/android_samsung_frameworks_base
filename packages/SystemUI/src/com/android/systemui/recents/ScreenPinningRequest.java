package com.android.systemui.recents;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.RemoteException;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawable;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.leak.RotationUtils;
import dagger.Lazy;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class ScreenPinningRequest implements View.OnClickListener, NavigationModeController.ModeChangedListener, CoreStartable, ConfigurationController.ConfigurationListener {
    public final AccessibilityManager mAccessibilityService;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public int mNavBarMode;
    public final Lazy mNavigationBarControllerLazy;
    public RequestWindowView mRequestWindow;
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.recents.ScreenPinningRequest.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ScreenPinningRequest.this.clearPrompt();
        }
    };
    public final UserTracker mUserTracker;
    public final WindowManager mWindowManager;
    public int taskId;

    public class RequestWindowView extends FrameLayout {
        public final ColorDrawable mColor;
        public ViewGroup mLayout;
        public final AnonymousClass3 mReceiver;
        public final boolean mShowCancel;
        public final AnonymousClass2 mUpdateLayoutRunnable;

        public /* synthetic */ RequestWindowView(ScreenPinningRequest screenPinningRequest, Context context) {
            this(context, true);
        }

        public static int getRotation(Context context) {
            if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                return 0;
            }
            return RotationUtils.getRotation(context);
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x00a4  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void inflateView(int i) throws Resources.NotFoundException {
            int i2;
            boolean zHasNavigationBar;
            ViewGroup viewGroup = (ViewGroup) View.inflate(getContext(), i == 3 ? R.layout.screen_pinning_request_sea_phone : i == 1 ? R.layout.screen_pinning_request_land_phone : R.layout.screen_pinning_request, null);
            this.mLayout = viewGroup;
            viewGroup.setClickable(true);
            this.mLayout.setLayoutDirection(0);
            this.mLayout.findViewById(R.id.screen_pinning_text_area).setLayoutDirection(3);
            View viewFindViewById = this.mLayout.findViewById(R.id.screen_pinning_buttons);
            if (!QuickStepContract.isGesturalMode(ScreenPinningRequest.this.mNavBarMode)) {
                try {
                    zHasNavigationBar = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(((FrameLayout) this).mContext.getDisplayId());
                } catch (RemoteException e) {
                    Log.e("ScreenPinningRequest", "Failed to check soft navigation bar", e);
                    zHasNavigationBar = false;
                }
                if (!zHasNavigationBar || Utilities.isLargeScreen(((FrameLayout) this).mContext)) {
                    viewFindViewById.setVisibility(8);
                } else {
                    viewFindViewById.setLayoutDirection(3);
                    if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(((FrameLayout) this).mContext) == 1) {
                        LinearLayout linearLayout = (LinearLayout) viewFindViewById;
                        if (linearLayout.getOrientation() == 1) {
                            int childCount = linearLayout.getChildCount();
                            ArrayList arrayList = new ArrayList(childCount);
                            for (int i3 = 0; i3 < childCount; i3++) {
                                arrayList.add(linearLayout.getChildAt(i3));
                            }
                            linearLayout.removeAllViews();
                            for (int i4 = childCount - 1; i4 >= 0; i4--) {
                                linearLayout.addView((View) arrayList.get(i4));
                            }
                        }
                    }
                }
            }
            ((Button) this.mLayout.findViewById(R.id.screen_pinning_ok_button)).setOnClickListener(ScreenPinningRequest.this);
            if (this.mShowCancel) {
                ((Button) this.mLayout.findViewById(R.id.screen_pinning_cancel_button)).setOnClickListener(ScreenPinningRequest.this);
            } else {
                ((Button) this.mLayout.findViewById(R.id.screen_pinning_cancel_button)).setVisibility(4);
            }
            int displayId = ((FrameLayout) this).mContext.getDisplayId();
            NavigationBarControllerImpl navigationBarControllerImpl = (NavigationBarControllerImpl) ((NavigationBarController) ScreenPinningRequest.this.mNavigationBarControllerLazy.get());
            NavigationBarView navigationBarView = navigationBarControllerImpl.getNavigationBarView(displayId);
            boolean zIsOverviewEnabled = navigationBarView != null ? navigationBarView.isOverviewEnabled() : (navigationBarControllerImpl.mTaskbarDelegate.mSysUiState.getFlags() & 16777216) == 0;
            boolean zIsTouchExplorationEnabled = ScreenPinningRequest.this.mAccessibilityService.isTouchExplorationEnabled();
            if (QuickStepContract.isGesturalMode(ScreenPinningRequest.this.mNavBarMode)) {
                i2 = R.string.screen_pinning_description_gestural;
            } else if (zIsOverviewEnabled) {
                this.mLayout.findViewById(R.id.screen_pinning_recents_group).setVisibility(0);
                this.mLayout.findViewById(R.id.screen_pinning_home_bg_light).setVisibility(4);
                this.mLayout.findViewById(R.id.screen_pinning_home_bg).setVisibility(4);
                i2 = zIsTouchExplorationEnabled ? R.string.screen_pinning_description_accessible : R.string.screen_pinning_description;
            } else {
                this.mLayout.findViewById(R.id.screen_pinning_recents_group).setVisibility(4);
                this.mLayout.findViewById(R.id.screen_pinning_home_bg_light).setVisibility(0);
                this.mLayout.findViewById(R.id.screen_pinning_home_bg).setVisibility(0);
                i2 = zIsTouchExplorationEnabled ? R.string.screen_pinning_description_recents_invisible_accessible : R.string.screen_pinning_description_recents_invisible;
            }
            NavigationBarView navigationBarView2 = ((NavigationBarControllerImpl) ((NavigationBarController) ScreenPinningRequest.this.mNavigationBarControllerLazy.get())).getNavigationBarView(displayId);
            if (navigationBarView2 != null) {
                ImageView imageView = (ImageView) this.mLayout.findViewById(R.id.screen_pinning_back_icon);
                KeyButtonDrawable drawable = navigationBarView2.getDrawable(R.drawable.ic_sysbar_back);
                navigationBarView2.orientBackButton(drawable);
                imageView.setImageDrawable(drawable);
                ImageView imageView2 = (ImageView) this.mLayout.findViewById(R.id.screen_pinning_home_icon);
                KeyButtonDrawable drawable2 = navigationBarView2.mShowSwipeUpUi ? navigationBarView2.getDrawable(R.drawable.ic_sysbar_home_quick_step) : navigationBarView2.getDrawable(R.drawable.ic_sysbar_home);
                navigationBarView2.orientHomeButton(drawable2);
                imageView2.setImageDrawable(drawable2);
            }
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.screen_pinning_description_bullet_gap_width);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(getContext().getText(i2), new BulletSpan(dimensionPixelSize), 0);
            spannableStringBuilder.append((CharSequence) System.lineSeparator());
            spannableStringBuilder.append(getContext().getText(R.string.screen_pinning_exposes_personal_data), new BulletSpan(dimensionPixelSize), 0);
            spannableStringBuilder.append((CharSequence) System.lineSeparator());
            spannableStringBuilder.append(getContext().getText(R.string.screen_pinning_can_open_other_apps), new BulletSpan(dimensionPixelSize), 0);
            ((TextView) this.mLayout.findViewById(R.id.screen_pinning_description)).setText(spannableStringBuilder);
            int i5 = zIsTouchExplorationEnabled ? 4 : 0;
            this.mLayout.findViewById(R.id.screen_pinning_back_bg).setVisibility(i5);
            this.mLayout.findViewById(R.id.screen_pinning_back_bg_light).setVisibility(i5);
            ViewGroup viewGroup2 = this.mLayout;
            ScreenPinningRequest.this.getClass();
            addView(viewGroup2, new FrameLayout.LayoutParams(-2, -2, i == 3 ? 19 : i == 1 ? 21 : 81));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() throws Resources.NotFoundException {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ScreenPinningRequest.this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            int rotation = getRotation(((FrameLayout) this).mContext);
            inflateView(rotation);
            int color = ((FrameLayout) this).mContext.getColor(R.color.screen_pinning_request_window_bg);
            if (ActivityManager.isHighEndGfx()) {
                this.mLayout.setAlpha(0.0f);
                if (rotation == 3) {
                    this.mLayout.setTranslationX(f * (-96.0f));
                } else if (rotation == 1) {
                    this.mLayout.setTranslationX(f * 96.0f);
                } else {
                    this.mLayout.setTranslationY(f * 96.0f);
                }
                this.mLayout.animate().alpha(1.0f).translationX(0.0f).translationY(0.0f).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
                ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new ArgbEvaluator(), 0, Integer.valueOf(color));
                valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        RequestWindowView.this.mColor.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
                valueAnimatorOfObject.setDuration(1000L);
                valueAnimatorOfObject.start();
            } else {
                this.mColor.setColor(color);
            }
            IntentFilter intentFilter = new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            ScreenPinningRequest.this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mReceiver);
            ScreenPinningRequest screenPinningRequest = ScreenPinningRequest.this;
            ((UserTrackerImpl) screenPinningRequest.mUserTracker).addCallback(screenPinningRequest.mUserChangedCallback, ((FrameLayout) this).mContext.getMainExecutor());
        }

        public final void onConfigurationChanged() throws Resources.NotFoundException {
            removeAllViews();
            inflateView(getRotation(((FrameLayout) this).mContext));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onDetachedFromWindow() {
            ScreenPinningRequest.this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
            ScreenPinningRequest screenPinningRequest = ScreenPinningRequest.this;
            ((UserTrackerImpl) screenPinningRequest.mUserTracker).removeCallback(screenPinningRequest.mUserChangedCallback);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.recents.ScreenPinningRequest$RequestWindowView$2] */
        /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.recents.ScreenPinningRequest$RequestWindowView$3] */
        private RequestWindowView(Context context, boolean z) {
            super(context);
            ColorDrawable colorDrawable = new ColorDrawable(0);
            this.mColor = colorDrawable;
            this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.2
                @Override // java.lang.Runnable
                public final void run() {
                    ViewGroup viewGroup = RequestWindowView.this.mLayout;
                    if (viewGroup == null || viewGroup.getParent() == null) {
                        return;
                    }
                    RequestWindowView requestWindowView = RequestWindowView.this;
                    ViewGroup viewGroup2 = requestWindowView.mLayout;
                    ScreenPinningRequest screenPinningRequest = ScreenPinningRequest.this;
                    int rotation = RequestWindowView.getRotation(((FrameLayout) requestWindowView).mContext);
                    screenPinningRequest.getClass();
                    viewGroup2.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, rotation == 3 ? 19 : rotation == 1 ? 21 : 81));
                }
            };
            this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        RequestWindowView requestWindowView = RequestWindowView.this;
                        requestWindowView.post(requestWindowView.mUpdateLayoutRunnable);
                    } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                        ScreenPinningRequest.this.clearPrompt();
                    }
                }
            };
            setClickable(true);
            setOnClickListener(ScreenPinningRequest.this);
            setBackground(colorDrawable);
            this.mShowCancel = z;
        }
    }

    public ScreenPinningRequest(Context context, NavigationModeController navigationModeController, Lazy lazy, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, WindowManager windowManager) {
        this.mContext = context;
        this.mNavigationBarControllerLazy = lazy;
        this.mAccessibilityService = (AccessibilityManager) context.getSystemService("accessibility");
        this.mWindowManager = windowManager;
        this.mNavBarMode = navigationModeController.addListener(this);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
    }

    public final void clearPrompt() {
        RequestWindowView requestWindowView = this.mRequestWindow;
        if (requestWindowView != null) {
            this.mWindowManager.removeView(requestWindowView);
            this.mRequestWindow = null;
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.screen_pinning_ok_button || this.mRequestWindow == view) {
            try {
                ActivityTaskManager.getService().startSystemLockTaskMode(this.taskId);
            } catch (RemoteException unused) {
            }
        }
        clearPrompt();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) throws Resources.NotFoundException {
        RequestWindowView requestWindowView = this.mRequestWindow;
        if (requestWindowView != null) {
            requestWindowView.onConfigurationChanged();
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
