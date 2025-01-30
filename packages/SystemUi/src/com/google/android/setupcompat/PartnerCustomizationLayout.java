package com.google.android.setupcompat;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.google.android.setupcompat.internal.LifecycleFragment;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda1;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PartnerCustomizationLayout extends TemplateLayout {
    public static final Logger LOG = new Logger("PartnerCustomizationLayout");
    public Activity activity;
    public boolean useDynamicColor;
    public boolean useFullDynamicColorAttr;
    public boolean usePartnerResourceAttr;
    final ViewTreeObserver.OnWindowFocusChangeListener windowFocusChangeListener;

    /* renamed from: $r8$lambda$0I8CGaPG-55DphyAapa3mtQ-RWk, reason: not valid java name */
    public static void m2767$r8$lambda$0I8CGaPG55DphyAapa3mtQRWk(PartnerCustomizationLayout partnerCustomizationLayout, boolean z) {
        SetupCompatServiceInvoker setupCompatServiceInvoker = SetupCompatServiceInvoker.get(partnerCustomizationLayout.getContext());
        String shortString = partnerCustomizationLayout.activity.getComponentName().toShortString();
        Activity activity = partnerCustomizationLayout.activity;
        Bundle bundle = new Bundle();
        bundle.putString("packageName", activity.getComponentName().getPackageName());
        bundle.putString("screenName", activity.getComponentName().getShortClassName());
        bundle.putInt("hash", partnerCustomizationLayout.hashCode());
        bundle.putBoolean("focus", z);
        bundle.putLong("timeInMillis", System.currentTimeMillis());
        setupCompatServiceInvoker.getClass();
        try {
            setupCompatServiceInvoker.loggingExecutor.execute(new SetupCompatServiceInvoker$$ExternalSyntheticLambda1(setupCompatServiceInvoker, shortString, bundle, 1));
        } catch (RejectedExecutionException e) {
            SetupCompatServiceInvoker.LOG.m238e(String.format("Screen %s report focus changed failed.", shortString), e);
        }
    }

    public PartnerCustomizationLayout(Context context) {
        this(context, 0, 0);
    }

    public static Activity lookupActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return lookupActivityFromContext(((ContextWrapper) context).getBaseContext());
        }
        throw new IllegalArgumentException("Cannot find instance of Activity in parent tree");
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.suc_layout_content;
        }
        return super.findContainer(i);
    }

    public PersistableBundle getLayoutTypeMetrics() {
        return null;
    }

    public final void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.recycle();
        if (z) {
            setSystemUiVisibility(1024);
        }
        registerMixin(StatusBarMixin.class, new StatusBarMixin(this, this.activity.getWindow(), attributeSet, i));
        registerMixin(SystemNavBarMixin.class, new SystemNavBarMixin(this, this.activity.getWindow()));
        registerMixin(FooterBarMixin.class, new FooterBarMixin(this, attributeSet, i));
        ((SystemNavBarMixin) getMixin(SystemNavBarMixin.class)).applyPartnerCustomizations(attributeSet, i);
        this.activity.getWindow().addFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
        this.activity.getWindow().clearFlags(QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        this.activity.getWindow().clearFlags(134217728);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Activity activity = this.activity;
        int i = LifecycleFragment.$r8$clinit;
        if (WizardManagerHelper.isAnySetupWizard(activity.getIntent())) {
            SetupCompatServiceInvoker setupCompatServiceInvoker = SetupCompatServiceInvoker.get(activity.getApplicationContext());
            String componentName = activity.getComponentName().toString();
            Bundle bundle = new Bundle();
            bundle.putString("screenName", activity.getComponentName().toString());
            bundle.putString("intentAction", activity.getIntent().getAction());
            setupCompatServiceInvoker.getClass();
            try {
                setupCompatServiceInvoker.loggingExecutor.execute(new SetupCompatServiceInvoker$$ExternalSyntheticLambda1(setupCompatServiceInvoker, componentName, bundle, 0));
            } catch (RejectedExecutionException e) {
                SetupCompatServiceInvoker.LOG.m238e(String.format("Screen %s bind back fail.", componentName), e);
            }
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager != null && !fragmentManager.isDestroyed()) {
                Fragment findFragmentByTag = fragmentManager.findFragmentByTag("lifecycle_monitor");
                if (findFragmentByTag == null) {
                    LifecycleFragment lifecycleFragment = new LifecycleFragment();
                    try {
                        fragmentManager.beginTransaction().add(lifecycleFragment, "lifecycle_monitor").commitNow();
                        findFragmentByTag = lifecycleFragment;
                    } catch (IllegalStateException e2) {
                        Log.e("LifecycleFragment", "Error occurred when attach to Activity:" + activity.getComponentName(), e2);
                    }
                } else if (!(findFragmentByTag instanceof LifecycleFragment)) {
                    Log.wtf("LifecycleFragment", activity.getClass().getSimpleName().concat(" Incorrect instance on lifecycle fragment."));
                }
            }
        }
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            getViewTreeObserver().addOnWindowFocusChangeListener(this.windowFocusChangeListener);
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
        FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
        boolean isPrimaryButtonVisible = footerBarMixin.isPrimaryButtonVisible();
        boolean equals = footerBarMixinMetrics.primaryButtonVisibility.equals("Unknown");
        String str = ActionResults.RESULT_LAUNCHER_VISIBLE;
        footerBarMixinMetrics.primaryButtonVisibility = equals ? isPrimaryButtonVisible ? ActionResults.RESULT_LAUNCHER_VISIBLE : ActionResults.RESULT_LAUNCHER_INVISIBLE : footerBarMixinMetrics.primaryButtonVisibility;
        FooterBarMixinMetrics footerBarMixinMetrics2 = footerBarMixin.metrics;
        boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
        if (!footerBarMixinMetrics2.secondaryButtonVisibility.equals("Unknown")) {
            str = footerBarMixinMetrics2.secondaryButtonVisibility;
        } else if (!isSecondaryButtonVisible) {
            str = ActionResults.RESULT_LAUNCHER_INVISIBLE;
        }
        footerBarMixinMetrics2.secondaryButtonVisibility = str;
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public final void onBeforeTemplateInflated(AttributeSet attributeSet, int i) {
        boolean z = true;
        this.usePartnerResourceAttr = true;
        Activity lookupActivityFromContext = lookupActivityFromContext(getContext());
        this.activity = lookupActivityFromContext;
        boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(lookupActivityFromContext.getIntent());
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        if (!obtainStyledAttributes.hasValue(2)) {
            LOG.m237e("Attribute sucUsePartnerResource not found in " + this.activity.getComponentName());
        }
        if (!isAnySetupWizard && !obtainStyledAttributes.getBoolean(2, true)) {
            z = false;
        }
        this.usePartnerResourceAttr = z;
        this.useDynamicColor = obtainStyledAttributes.hasValue(0);
        this.useFullDynamicColorAttr = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        Logger logger = LOG;
        String str = "activity=" + this.activity.getClass().getSimpleName() + " isSetupFlow=" + isAnySetupWizard + " enablePartnerResourceLoading=true usePartnerResourceAttr=" + this.usePartnerResourceAttr + " useDynamicColor=" + this.useDynamicColor + " useFullDynamicColorAttr=" + this.useFullDynamicColorAttr;
        if (Log.isLoggable("SetupLibrary", 3)) {
            Log.d("SetupLibrary", logger.prefix.concat(str));
        } else {
            logger.getClass();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
            FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
            boolean isPrimaryButtonVisible = footerBarMixin.isPrimaryButtonVisible();
            boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
            footerBarMixinMetrics.primaryButtonVisibility = FooterBarMixinMetrics.updateButtonVisibilityState(footerBarMixinMetrics.primaryButtonVisibility, isPrimaryButtonVisible);
            footerBarMixinMetrics.secondaryButtonVisibility = FooterBarMixinMetrics.updateButtonVisibilityState(footerBarMixinMetrics.secondaryButtonVisibility, isSecondaryButtonVisible);
            FooterButton footerButton = footerBarMixin.primaryButton;
            FooterButton footerButton2 = footerBarMixin.secondaryButton;
            PersistableBundle metrics = footerButton != null ? footerButton.getMetrics("PrimaryFooterButton") : PersistableBundle.EMPTY;
            PersistableBundle metrics2 = footerButton2 != null ? footerButton2.getMetrics("SecondaryFooterButton") : PersistableBundle.EMPTY;
            PersistableBundle persistableBundle = PersistableBundle.EMPTY;
            FooterBarMixinMetrics footerBarMixinMetrics2 = footerBarMixin.metrics;
            footerBarMixinMetrics2.getClass();
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putString(FooterBarMixinMetrics.EXTRA_PRIMARY_BUTTON_VISIBILITY, footerBarMixinMetrics2.primaryButtonVisibility);
            persistableBundle2.putString(FooterBarMixinMetrics.EXTRA_SECONDARY_BUTTON_VISIBILITY, footerBarMixinMetrics2.secondaryButtonVisibility);
            PersistableBundle[] persistableBundleArr = {metrics2, persistableBundle};
            Logger logger = PersistableBundles.LOG;
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(Arrays.asList(persistableBundle2, metrics));
            Collections.addAll(arrayList, persistableBundleArr);
            PersistableBundle persistableBundle3 = new PersistableBundle();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PersistableBundle persistableBundle4 = (PersistableBundle) it.next();
                Iterator<String> it2 = persistableBundle4.keySet().iterator();
                while (it2.hasNext()) {
                    Preconditions.checkArgument(String.format("Found duplicate key [%s] while attempting to merge bundles.", it2.next()), !persistableBundle3.containsKey(r4));
                }
                persistableBundle3.putAll(persistableBundle4);
            }
            SetupMetricsLogger.logCustomEvent(getContext(), CustomEvent.create(MetricKey.get("SetupCompatMetrics", this.activity), persistableBundle3));
        }
        getViewTreeObserver().removeOnWindowFocusChangeListener(this.windowFocusChangeListener);
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = R.layout.partner_customization_layout;
        }
        return inflateTemplate(layoutInflater, 0, i);
    }

    public final boolean shouldApplyDynamicColor() {
        return this.useDynamicColor && PartnerConfigHelper.get(getContext()).isAvailable();
    }

    public final boolean shouldApplyPartnerResource() {
        return this.usePartnerResourceAttr && PartnerConfigHelper.get(getContext()).isAvailable();
    }

    public final boolean useFullDynamicColor() {
        return shouldApplyDynamicColor() && this.useFullDynamicColorAttr;
    }

    public PartnerCustomizationLayout(Context context, int i) {
        this(context, i, 0);
    }

    public PartnerCustomizationLayout(Context context, int i, int i2) {
        super(context, i, i2);
        final int i3 = 0;
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener(this) { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ PartnerCustomizationLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                switch (i3) {
                }
                PartnerCustomizationLayout.m2767$r8$lambda$0I8CGaPG55DphyAapa3mtQRWk(this.f$0, z);
            }
        };
        init(null, R.attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 1;
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener(this) { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ PartnerCustomizationLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                switch (i) {
                }
                PartnerCustomizationLayout.m2767$r8$lambda$0I8CGaPG55DphyAapa3mtQRWk(this.f$0, z);
            }
        };
        init(attributeSet, R.attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        final int i2 = 2;
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener(this) { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ PartnerCustomizationLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                switch (i2) {
                }
                PartnerCustomizationLayout.m2767$r8$lambda$0I8CGaPG55DphyAapa3mtQRWk(this.f$0, z);
            }
        };
        init(attributeSet, i);
    }
}
