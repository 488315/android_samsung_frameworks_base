package com.android.systemui.qs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Trace;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.dagger.QSFragmentComponent;
import com.android.systemui.util.LifecycleFragment;
import java.util.function.Consumer;
import javax.inject.Provider;

public final class QSFragmentLegacy extends LifecycleFragment implements QS {
    public final QSFragmentComponent.Factory mQsComponentFactory;
    public QSImpl mQsImpl;
    public final Provider mQsImplProvider;

    public QSFragmentLegacy(Provider provider, QSFragmentComponent.Factory factory) {
        this.mQsComponentFactory = factory;
        this.mQsImplProvider = provider;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void animateHeaderSlidingOut() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.animateHeaderSlidingOut();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void closeCustomizer() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.closeDetail();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void closeDetail() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.closeDetail();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getDesiredHeight() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.getDesiredHeight();
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final View getHeader() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.mHeader;
        }
        return null;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getHeightDiff() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.getHeightDiff();
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final int getQsMinExpansionHeight() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.getQsMinExpansionHeight();
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void hideImmediately() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.hideImmediately();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isCustomizing() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.mQSCustomizerController.isCustomizing();
        }
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isFullyCollapsed() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.isFullyCollapsed();
        }
        return true;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final boolean isShowingDetail() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            return qSImpl.isShowingDetail();
        }
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void notifyCustomizeChanged() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.notifyCustomizeChanged();
        }
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            Trace.beginSection("QSFragment#onCreateView");
            return layoutInflater.cloneInContext(new ContextThemeWrapper(getContext(), R.style.Theme_SystemUI_QuickSettings)).inflate(R.layout.qs_panel, viewGroup, false);
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.app.Fragment
    public final void onDestroyView() {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.onDestroy();
            this.mQsImpl = null;
        }
        super.onDestroyView();
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.onSaveInstanceState(bundle);
        }
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        QSFragmentComponent create = this.mQsComponentFactory.create(getView());
        QSImpl qSImpl = (QSImpl) this.mQsImplProvider.get();
        this.mQsImpl = qSImpl;
        DumpManager dumpManager = qSImpl.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "QSImpl", qSImpl);
        this.mQsImpl.onComponentCreated(create, bundle);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setCollapseExpandAction(Runnable runnable) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setCollapseExpandAction(runnable);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setCollapsedMediaVisibilityChangedListener(Consumer consumer) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.getClass();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setContainerController(QSContainerController qSContainerController) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setContainerController(qSContainerController);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setExpanded(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setExpanded(z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setFancyClipping(i, i2, i3, i4, i5, z, z2);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHasNotifications(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.getClass();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeaderClickable(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.getClass();
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeaderListening(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setHeaderListening(z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setHeightOverride(int i) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setHeightOverride(i);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setInSplitShade(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setInSplitShade(z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setIsNotificationPanelFullWidth(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.mIsSmallScreen = z;
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setListening(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setListening(z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setOverScrollAmount(int i) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setOverScrollAmount(i);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setOverscrolling(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setOverscrolling(z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setPanelView(QS.HeightListener heightListener) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.mPanelView = heightListener;
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setQsExpansion(float f, float f2, float f3, float f4) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setQsExpansion(f, f2, f3, f4);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setQsVisible(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setQsVisible(z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setScrollListener(QS.ScrollListener scrollListener) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.mScrollListener = scrollListener;
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setShouldUpdateSquishinessOnMedia(boolean z) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.mShouldUpdateMediaSquishiness = z;
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public final void setTransitionToFullShadeProgress(boolean z, float f, float f2) {
        QSImpl qSImpl = this.mQsImpl;
        if (qSImpl != null) {
            qSImpl.setTransitionToFullShadeProgress(z, f, f2);
        }
    }
}
