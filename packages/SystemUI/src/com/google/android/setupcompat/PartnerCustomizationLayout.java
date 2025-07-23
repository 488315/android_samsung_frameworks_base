package com.google.android.setupcompat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentLifecycleCallbacksDispatcher;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.internal.LifecycleFragment;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PartnerCustomizationLayout extends TemplateLayout {
    public static final Logger LOG = new Logger("PartnerCustomizationLayout");
    public Activity activity;
    public int footerBarPaddingBottom;
    FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks;
    public boolean useDynamicColor;
    public boolean useFullDynamicColorAttr;
    public boolean usePartnerResourceAttr;
    final ViewTreeObserver.OnWindowFocusChangeListener windowFocusChangeListener;

    public static void $r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(PartnerCustomizationLayout partnerCustomizationLayout, boolean z) {
        final SetupCompatServiceInvoker setupCompatServiceInvoker = SetupCompatServiceInvoker.get(partnerCustomizationLayout.getContext());
        final String shortString = partnerCustomizationLayout.activity.getComponentName().toShortString();
        Activity activity = partnerCustomizationLayout.activity;
        final Bundle bundle = new Bundle();
        bundle.putString("packageName", activity.getComponentName().getPackageName());
        bundle.putString("screenName", activity.getComponentName().getShortClassName());
        bundle.putInt("hash", partnerCustomizationLayout.hashCode());
        bundle.putBoolean("focus", z);
        bundle.putLong("timeInMillis", System.currentTimeMillis());
        setupCompatServiceInvoker.getClass();
        try {
            setupCompatServiceInvoker.loggingExecutor.execute(new Runnable() { // from class: com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SetupCompatServiceInvoker setupCompatServiceInvoker2 = SetupCompatServiceInvoker.this;
                    String str = shortString;
                    Bundle bundle2 = bundle;
                    Logger logger = SetupCompatServiceInvoker.LOG;
                    try {
                        ISetupCompatService service = SetupCompatServiceProvider.getInstance(setupCompatServiceInvoker2.context).getService(setupCompatServiceInvoker2.waitTimeInMillisForServiceConnection, TimeUnit.MILLISECONDS);
                        if (service != null) {
                            ((ISetupCompatService.Stub.Proxy) service).onFocusStatusChanged(bundle2);
                        } else {
                            logger.w("Report focusChange failed since service reference is null. Are the permission valid?");
                        }
                    } catch (RemoteException | InterruptedException | UnsupportedOperationException | TimeoutException e) {
                        logger.e("Exception occurred while " + str + " trying report windowFocusChange to SetupWizard.", e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            SetupCompatServiceInvoker.LOG.e(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Screen ", shortString, " report focus changed failed."), e);
        }
    }

    public PartnerCustomizationLayout(Context context) {
        this(context, 0, 0);
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.suc_layout_content;
        }
        return (ViewGroup) findViewById(i);
    }

    public PersistableBundle getLayoutTypeMetrics() {
        return null;
    }

    public final void init$2(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        this.footerBarPaddingBottom = obtainStyledAttributes2.getDimensionPixelSize(13, obtainStyledAttributes2.getDimensionPixelSize(17, 0));
        obtainStyledAttributes2.recycle();
        if (z) {
            setSystemUiVisibility(1024);
        }
        registerMixin(StatusBarMixin.class, new StatusBarMixin(this, this.activity.getWindow(), attributeSet, i));
        registerMixin(SystemNavBarMixin.class, new SystemNavBarMixin(this, this.activity.getWindow()));
        registerMixin(FooterBarMixin.class, new FooterBarMixin(this, attributeSet, i));
        ((SystemNavBarMixin) getMixin(SystemNavBarMixin.class)).applyPartnerCustomizations(attributeSet, i);
        this.activity.getWindow().addFlags(Integer.MIN_VALUE);
        this.activity.getWindow().clearFlags(67108864);
        this.activity.getWindow().clearFlags(134217728);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext()) && windowInsets.getSystemWindowInsetBottom() > 0) {
            LOG.atDebug("NavigationBarHeight: " + windowInsets.getSystemWindowInsetBottom());
            LinearLayout linearLayout = ((FooterBarMixin) getMixin(FooterBarMixin.class)).buttonContainer;
            if (linearLayout != null) {
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(getContext());
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    this.footerBarPaddingBottom = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig, 0.0f);
                }
                linearLayout.setPadding(linearLayout.getPaddingLeft(), linearLayout.getPaddingTop(), linearLayout.getPaddingRight(), windowInsets.getSystemWindowInsetBottom() + this.footerBarPaddingBottom);
            }
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        android.app.FragmentManager fragmentManager;
        super.onAttachedToWindow();
        Activity activity = this.activity;
        PartnerCustomizationLayout$$ExternalSyntheticLambda1 partnerCustomizationLayout$$ExternalSyntheticLambda1 = new PartnerCustomizationLayout$$ExternalSyntheticLambda1(this);
        Logger logger = LifecycleFragment.LOG;
        LifecycleFragment lifecycleFragment = null;
        if (!WizardManagerHelper.isAnySetupWizard(activity.getIntent()) || (fragmentManager = activity.getFragmentManager()) == null || fragmentManager.isDestroyed()) {
            LifecycleFragment.LOG.atDebug("Skip attach " + activity.getClass().getSimpleName() + " because it's not in SUW flow.");
        } else {
            Fragment findFragmentByTag = fragmentManager.findFragmentByTag("lifecycle_monitor");
            if (findFragmentByTag == null) {
                LifecycleFragment lifecycleFragment2 = new LifecycleFragment();
                lifecycleFragment2.lifecycleChangeListener = partnerCustomizationLayout$$ExternalSyntheticLambda1;
                try {
                    fragmentManager.beginTransaction().add(lifecycleFragment2, "lifecycle_monitor").commitNow();
                    findFragmentByTag = lifecycleFragment2;
                } catch (IllegalStateException e) {
                    LifecycleFragment.LOG.e("Error occurred when attach to Activity:" + activity.getComponentName(), e);
                }
            } else if (findFragmentByTag instanceof LifecycleFragment) {
                LifecycleFragment.LOG.atDebug("Find an existing fragment that belongs to ".concat(activity.getClass().getSimpleName()));
            } else {
                Log.wtf("LifecycleFragment", activity.getClass().getSimpleName().concat(" Incorrect instance on lifecycle fragment."));
            }
            lifecycleFragment = (LifecycleFragment) findFragmentByTag;
        }
        if (lifecycleFragment == null) {
            Logger logger2 = LOG;
            Activity activity2 = this.activity;
            logger2.atDebug("Unable to attach lifecycle fragment to the host activity. Activity=".concat(activity2 != null ? activity2.getClass().getSimpleName() : "null"));
        }
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            getViewTreeObserver().addOnWindowFocusChangeListener(this.windowFocusChangeListener);
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
        FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
        boolean isPrimaryButtonVisible = footerBarMixin.isPrimaryButtonVisible();
        boolean equals = footerBarMixinMetrics.primaryButtonVisibility.equals(C2paManifestList.UNKNOWN_VALUE);
        String str = ActionResults.RESULT_LAUNCHER_INVISIBLE;
        footerBarMixinMetrics.primaryButtonVisibility = equals ? isPrimaryButtonVisible ? ActionResults.RESULT_LAUNCHER_VISIBLE : ActionResults.RESULT_LAUNCHER_INVISIBLE : footerBarMixinMetrics.primaryButtonVisibility;
        FooterBarMixinMetrics footerBarMixinMetrics2 = footerBarMixin.metrics;
        boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
        if (!footerBarMixinMetrics2.secondaryButtonVisibility.equals(C2paManifestList.UNKNOWN_VALUE)) {
            str = footerBarMixinMetrics2.secondaryButtonVisibility;
        } else if (isSecondaryButtonVisible) {
            str = ActionResults.RESULT_LAUNCHER_VISIBLE;
        }
        footerBarMixinMetrics2.secondaryButtonVisibility = str;
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public final void onBeforeTemplateInflated(AttributeSet attributeSet, int i) {
        boolean z = true;
        this.usePartnerResourceAttr = true;
        this.activity = PartnerConfigHelper.lookupActivityFromContext(getContext());
        Logger logger = LOG;
        logger.atDebug("Flag of isEnhancedSetupDesignMetricsEnabled=" + PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext()));
        if (PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext())) {
            Activity activity = this.activity;
            if (activity instanceof FragmentActivity) {
                this.fragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout.1
                    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
                    public final void onFragmentAttached(androidx.fragment.app.Fragment fragment) {
                        Logger logger2 = PartnerCustomizationLayout.LOG;
                        PartnerCustomizationLayout partnerCustomizationLayout = PartnerCustomizationLayout.this;
                        partnerCustomizationLayout.getClass();
                        if (fragment != null) {
                            int i2 = fragment.mFragmentId;
                            String resourceEntryName = i2 == 0 ? "" : partnerCustomizationLayout.getResources().getResourceEntryName(i2);
                            PartnerCustomizationLayout.LOG.atDebug("onFragmentAttached fragment name=" + fragment.getClass().getSimpleName() + ", tag=" + fragment.mTag + ", id=" + fragment.mFragmentId + ", name=" + resourceEntryName);
                        }
                        FooterBarMixin footerBarMixin = (FooterBarMixin) partnerCustomizationLayout.getMixin(FooterBarMixin.class);
                        footerBarMixin.getClass();
                        if (fragment != null) {
                            footerBarMixin.hostFragmentName = fragment.getClass().getSimpleName();
                            footerBarMixin.hostFragmentTag = fragment.mTag;
                        }
                    }
                };
                ((FragmentActivity) activity).getSupportFragmentManager().mLifecycleCallbacksDispatcher.lifecycleCallbacks.add(new FragmentLifecycleCallbacksDispatcher.FragmentLifecycleCallbacksHolder(this.fragmentLifecycleCallbacks, true));
                logger.atDebug("Register the onFragmentAttached lifecycle callbacks to ".concat(activity.getClass().getSimpleName()));
            }
        }
        boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(this.activity.getIntent());
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        if (!obtainStyledAttributes.hasValue(2)) {
            logger.e("Attribute sucUsePartnerResource not found in " + this.activity.getComponentName());
        }
        if (!isAnySetupWizard && !obtainStyledAttributes.getBoolean(2, true)) {
            z = false;
        }
        this.usePartnerResourceAttr = z;
        this.useDynamicColor = obtainStyledAttributes.hasValue(0);
        this.useFullDynamicColorAttr = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        logger.atDebug("activity=" + this.activity.getClass().getSimpleName() + " isSetupFlow=" + isAnySetupWizard + " enablePartnerResourceLoading=true usePartnerResourceAttr=" + this.usePartnerResourceAttr + " useDynamicColor=" + this.useDynamicColor + " useFullDynamicColorAttr=" + this.useFullDynamicColorAttr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int i = 0;
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
            FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
            boolean isPrimaryButtonVisible = footerBarMixin.isPrimaryButtonVisible();
            boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
            footerBarMixinMetrics.primaryButtonVisibility = FooterBarMixinMetrics.updateButtonVisibilityState(footerBarMixinMetrics.primaryButtonVisibility, isPrimaryButtonVisible);
            footerBarMixinMetrics.secondaryButtonVisibility = FooterBarMixinMetrics.updateButtonVisibilityState(footerBarMixinMetrics.secondaryButtonVisibility, isSecondaryButtonVisible);
            FooterButton footerButton = footerBarMixin.primaryButton;
            FooterButton footerButton2 = footerBarMixin.secondaryButton;
            SetupMetricsLogger.logCustomEvent(getContext(), CustomEvent.create(MetricKey.get("SetupCompatMetrics", this.activity), PersistableBundles.mergeBundles(footerBarMixin.getLoggingMetrics(), footerButton != null ? footerButton.getMetrics("PrimaryFooterButton") : PersistableBundle.EMPTY, footerButton2 != null ? footerButton2.getMetrics("SecondaryFooterButton") : PersistableBundle.EMPTY, PersistableBundle.EMPTY)));
        }
        getViewTreeObserver().removeOnWindowFocusChangeListener(this.windowFocusChangeListener);
        if (PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext())) {
            Activity activity = this.activity;
            if (activity instanceof FragmentActivity) {
                FragmentManagerImpl supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks = this.fragmentLifecycleCallbacks;
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = supportFragmentManager.mLifecycleCallbacksDispatcher;
                synchronized (fragmentLifecycleCallbacksDispatcher.lifecycleCallbacks) {
                    try {
                        int size = fragmentLifecycleCallbacksDispatcher.lifecycleCallbacks.size();
                        while (true) {
                            if (i >= size) {
                                break;
                            }
                            if (((FragmentLifecycleCallbacksDispatcher.FragmentLifecycleCallbacksHolder) fragmentLifecycleCallbacksDispatcher.lifecycleCallbacks.get(i)).callback == fragmentLifecycleCallbacks) {
                                fragmentLifecycleCallbacksDispatcher.lifecycleCallbacks.remove(i);
                                break;
                            }
                            i++;
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = R.layout.partner_customization_layout;
        }
        return inflateTemplate(layoutInflater, 0, i);
    }

    public final boolean shouldApplyDynamicColor() {
        if (PartnerConfigHelper.get(getContext()).isAvailable()) {
            return this.useDynamicColor || PartnerConfigHelper.isSetupWizardDynamicColorEnabled(getContext());
        }
        return false;
    }

    public final boolean shouldApplyPartnerResource() {
        return this.usePartnerResourceAttr && PartnerConfigHelper.get(getContext()).isAvailable();
    }

    public final boolean useFullDynamicColor() {
        if (shouldApplyDynamicColor()) {
            return this.useFullDynamicColorAttr || PartnerConfigHelper.isSetupWizardFullDynamicColorEnabled(getContext());
        }
        return false;
    }

    public PartnerCustomizationLayout(Context context, int i) {
        this(context, i, 0);
    }

    public PartnerCustomizationLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                PartnerCustomizationLayout.$r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(PartnerCustomizationLayout.this, z);
            }
        };
        init$2(null, R.attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                PartnerCustomizationLayout.$r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(PartnerCustomizationLayout.this, z);
            }
        };
        init$2(attributeSet, R.attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                PartnerCustomizationLayout.$r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(PartnerCustomizationLayout.this, z);
            }
        };
        init$2(attributeSet, i);
    }
}
