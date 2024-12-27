package com.android.systemui.qp.customize;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qp.SubRoomQsTileBaseView;
import com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView;
import com.android.systemui.qp.SubscreenPagedTileLayout;
import com.android.systemui.qp.SubscreenQSControllerContract$BaseViewController;
import com.android.systemui.qp.SubscreenTileLayout;
import com.android.systemui.qp.customize.SubscreenCustomizer;
import com.android.systemui.qp.customize.SubscreenCustomizerController;
import com.android.systemui.qs.QSEvents;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecSubScreenQSTileHost;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.ViewController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenCustomizerController extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public boolean mDragStart;
    public final QSTileHost mHost;
    public final AnonymousClass1 mLongClickListener;
    public QSTileView mLongClickedView;
    public SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord mLongClickedViewInfo;
    public float mPointX;
    public float mPointY;
    public final SubscreenCustomizerController$$ExternalSyntheticLambda0 mQSHostCallback;
    public final SecSubScreenQSTileHost mSubScreenTileHost;
    public final ArrayList mSubscreenRecords;
    public final UiEventLogger mUiEventLogger;

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.qp.customize.SubscreenCustomizerController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qp.customize.SubscreenCustomizerController$1] */
    public SubscreenCustomizerController(SubscreenCustomizer subscreenCustomizer, QSTileHost qSTileHost) {
        super(subscreenCustomizer);
        this.mSubscreenRecords = new ArrayList();
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
        this.mQSHostCallback = new QSHost.Callback() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.QSHost.Callback
            public final void onTilesChanged() {
                SubscreenCustomizerController.this.setTiles();
            }
        };
        this.mDragStart = false;
        this.mLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController.1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                view.setBackgroundResource(0);
                SubscreenCustomizerController.this.mLongClickedView = (QSTileView) view.getParent().getParent();
                SubscreenCustomizerController subscreenCustomizerController = SubscreenCustomizerController.this;
                subscreenCustomizerController.mLongClickedViewInfo = (SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord) subscreenCustomizerController.mLongClickedView.getTag();
                SubscreenCustomizerController subscreenCustomizerController2 = SubscreenCustomizerController.this;
                subscreenCustomizerController2.mLongClickedViewInfo.getClass();
                subscreenCustomizerController2.mPointX = 0.0f;
                SubscreenCustomizerController subscreenCustomizerController3 = SubscreenCustomizerController.this;
                subscreenCustomizerController3.mLongClickedViewInfo.getClass();
                subscreenCustomizerController3.mPointY = 0.0f;
                SubscreenCustomizer subscreenCustomizer2 = (SubscreenCustomizer) ((ViewController) SubscreenCustomizerController.this).mView;
                SubscreenCustomizerController subscreenCustomizerController4 = SubscreenCustomizerController.this;
                subscreenCustomizer2.mLongClickedViewInfo = subscreenCustomizerController4.mLongClickedViewInfo;
                SubscreenCustomizer subscreenCustomizer3 = (SubscreenCustomizer) ((ViewController) subscreenCustomizerController4).mView;
                SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord = SubscreenCustomizerController.this.mLongClickedViewInfo;
                subscreenCustomizer3.getClass();
                SubscreenCustomizer.MessageObjectAnim messageObjectAnim = new SubscreenCustomizer.MessageObjectAnim();
                messageObjectAnim.animationType = 200;
                messageObjectAnim.longClickedTileInfo = subscreenTileRecord;
                if (subscreenCustomizer3.mHandler.hasMessages(100)) {
                    subscreenCustomizer3.mHandler.removeMessages(100);
                }
                subscreenCustomizer3.mHandler.sendMessage(subscreenCustomizer3.mHandler.obtainMessage(100, messageObjectAnim));
                SubscreenCustomizerController subscreenCustomizerController5 = SubscreenCustomizerController.this;
                final QSTileView qSTileView = subscreenCustomizerController5.mLongClickedView;
                final float f = -Math.abs(qSTileView.getWidth() * 0.125f);
                final float f2 = -Math.abs(qSTileView.getHeight() * 0.25f);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(80L);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        View view2 = qSTileView;
                        float f3 = f;
                        float f4 = f2;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        view2.setTranslationX(f3 * animatedFraction);
                        view2.setTranslationY(f4 * animatedFraction);
                    }
                });
                ofFloat.addListener(subscreenCustomizerController5.new AnonymousClass2(qSTileView));
                ofFloat.start();
                return true;
            }
        };
        this.mHost = qSTileHost;
        this.mSubScreenTileHost = qSTileHost.mSubScreenTileHost;
        this.mContext = getContext();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        Objects.toString(this.mView);
        SubscreenCustomizer subscreenCustomizer = (SubscreenCustomizer) this.mView;
        if (subscreenCustomizer.mTileLayout == null) {
            SubscreenPagedTileLayout subscreenPagedTileLayout = (SubscreenPagedTileLayout) subscreenCustomizer.findViewById(R.id.subscreen_customize_qs_paged);
            subscreenCustomizer.mTileLayout = subscreenPagedTileLayout;
            int size = subscreenPagedTileLayout.mPages.size();
            for (int i = 0; i < size; i++) {
                SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i);
                if (Float.compare(subscreenTileLayout.mSquishinessFraction, 1.0f) != 0) {
                    subscreenTileLayout.mSquishinessFraction = 1.0f;
                    subscreenTileLayout.layoutTileRecords$1(subscreenTileLayout.mRecords.size(), false);
                    Iterator it = subscreenTileLayout.mRecords.iterator();
                    while (it.hasNext()) {
                        ViewParent viewParent = ((QSPanelControllerBase$TileRecord) it.next()).tileView;
                        if (viewParent instanceof HeightOverrideable) {
                            float f = subscreenTileLayout.mSquishinessFraction;
                            QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((HeightOverrideable) viewParent);
                            if (qSTileViewImpl.squishinessFraction != f) {
                                qSTileViewImpl.squishinessFraction = f;
                                qSTileViewImpl.updateHeight();
                            }
                        }
                    }
                }
            }
        }
        subscreenCustomizer.mTileLayout = subscreenCustomizer.mTileLayout;
        subscreenCustomizer.updatePageIndicator$1$1();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Objects.toString(this.mView);
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            ((ArrayList) secSubScreenQSTileHost.mCallbacks).add(this.mQSHostCallback);
        }
        setTiles();
        SubscreenCustomizer subscreenCustomizer = (SubscreenCustomizer) this.mView;
        if (!subscreenCustomizer.mListening) {
            subscreenCustomizer.mListening = true;
            QSPanel.QSTileLayout qSTileLayout = subscreenCustomizer.mTileLayout;
            if (qSTileLayout != null) {
                qSTileLayout.setListening(true, this.mUiEventLogger);
            }
            if (((SubscreenCustomizer) this.mView).mListening) {
                Iterator it = this.mSubscreenRecords.iterator();
                while (it.hasNext()) {
                    QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) it.next();
                    if (!qSPanelControllerBase$TileRecord.tile.isListening()) {
                        qSPanelControllerBase$TileRecord.tile.refreshState();
                    }
                }
            }
        }
        SubscreenCustomizer subscreenCustomizer2 = (SubscreenCustomizer) this.mView;
        Object obj = subscreenCustomizer2.mTileLayout;
        if (obj != null) {
            View view = (View) obj;
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            subscreenCustomizer2.mQuickSettingsContainer.addView(view);
            ((SubscreenPagedTileLayout) subscreenCustomizer2.mTileLayout).getClass();
            subscreenCustomizer2.updatePageIndicator$1$1();
            ((SubscreenPagedTileLayout) subscreenCustomizer2.mTileLayout).setCurrentItem(0, false);
        }
        SubscreenCustomizer subscreenCustomizer3 = (SubscreenCustomizer) this.mView;
        subscreenCustomizer3.updatePageIndicator$1$1();
        if (subscreenCustomizer3.mTileLayout != null) {
            int dimension = (int) subscreenCustomizer3.getResources().getDimension(R.dimen.qs_customizer_tile_page_layout_height);
            QSPanel.QSTileLayout qSTileLayout2 = subscreenCustomizer3.mTileLayout;
            if (qSTileLayout2 instanceof SubscreenPagedTileLayout) {
                SubscreenPagedTileLayout subscreenPagedTileLayout = (SubscreenPagedTileLayout) qSTileLayout2;
                subscreenPagedTileLayout.getClass();
                Log.d("SubscreenPagedTileLayout", "setTilePageHeight pageHeight: " + dimension);
                int i = subscreenPagedTileLayout.mPageHeight;
                if (i != dimension) {
                    subscreenPagedTileLayout.mLastMaxHeight = i;
                    subscreenPagedTileLayout.mPageHeight = dimension;
                }
            }
        }
        QSPanel.QSTileLayout qSTileLayout3 = subscreenCustomizer3.mTileLayout;
        if (qSTileLayout3 != null) {
            qSTileLayout3.updateResources();
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            ((ArrayList) secSubScreenQSTileHost.mCallbacks).remove(this.mQSHostCallback);
        }
        removeAllTileViews$1();
    }

    public final void removeAllTileViews$1() {
        Iterator it = this.mSubscreenRecords.iterator();
        while (it.hasNext()) {
            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) it.next();
            ((SubscreenCustomizer) this.mView).mTileLayout.removeTile(qSPanelControllerBase$TileRecord);
            qSPanelControllerBase$TileRecord.tile.removeCallback(qSPanelControllerBase$TileRecord.callback);
        }
        this.mSubscreenRecords.clear();
    }

    public final void setTiles() {
        SecSubScreenQSTileHost secSubScreenQSTileHost = this.mSubScreenTileHost;
        if (secSubScreenQSTileHost != null) {
            Collection<QSTile> values = secSubScreenQSTileHost.mTiles.values();
            removeAllTileViews$1();
            for (QSTile qSTile : values) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("adding Tilespec : ", qSTile.getTileSpec(), "SubscreenCustomizerController");
                Context context = this.mContext;
                this.mHost.getClass();
                SubRoomQsTileBaseView subRoomQsTileBaseView = new SubRoomQsTileBaseView(context, new QSIconViewImpl(context), false);
                SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord = new SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord(qSTile, subRoomQsTileBaseView, this.mLongClickListener);
                subRoomQsTileBaseView.setTag(subscreenTileRecord);
                ((SubscreenCustomizer) this.mView).addTile(subscreenTileRecord);
                this.mSubscreenRecords.add(subscreenTileRecord);
                subscreenTileRecord.tileView.getIcon().setOnClickListener(null);
                Log.d("SubscreenCustomizerController", "addTile tile.getTileSpec():  record: " + subscreenTileRecord + " mSubscreenRecords.size(): " + this.mSubscreenRecords.size());
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qp.customize.SubscreenCustomizerController$2, reason: invalid class name */
    public final class AnonymousClass2 implements Animator.AnimatorListener {
        public final /* synthetic */ View val$view;

        public AnonymousClass2(View view) {
            this.val$view = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SubscreenCustomizer subscreenCustomizer = (SubscreenCustomizer) ((ViewController) SubscreenCustomizerController.this).mView;
            SubscreenCustomizerController subscreenCustomizerController = SubscreenCustomizerController.this;
            QSTileView qSTileView = subscreenCustomizerController.mLongClickedView;
            float f = subscreenCustomizerController.mPointX;
            float f2 = subscreenCustomizerController.mPointY;
            subscreenCustomizer.getClass();
            SubscreenCustomizer.AnonymousClass3 anonymousClass3 = new View.DragShadowBuilder(subscreenCustomizer, qSTileView, qSTileView, f, f2) { // from class: com.android.systemui.qp.customize.SubscreenCustomizer.3
                public final /* synthetic */ float val$pointX;
                public final /* synthetic */ float val$pointY;
                public final /* synthetic */ View val$v;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(SubscreenCustomizer subscreenCustomizer2, View qSTileView2, View qSTileView22, float f3, float f22) {
                    super(qSTileView22);
                    this.val$v = qSTileView22;
                    this.val$pointX = f3;
                    this.val$pointY = f22;
                }

                @Override // android.view.View.DragShadowBuilder
                public final void onProvideShadowMetrics(Point point, Point point2) {
                    int width = this.val$v.getWidth();
                    int height = this.val$v.getHeight();
                    point.set(width, height);
                    point2.set(((int) this.val$pointX) + ((int) (width * 0.125f)), ((int) this.val$pointY) + ((int) (height * 0.25f)));
                }
            };
            SubscreenCustomizerController.this.mDragStart = this.val$view.startDrag(null, anonymousClass3, null, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
            SubscreenCustomizerController subscreenCustomizerController2 = SubscreenCustomizerController.this;
            if (!subscreenCustomizerController2.mDragStart) {
                ((SubscreenCustomizer) ((ViewController) subscreenCustomizerController2).mView).requestLayout();
            }
            this.val$view.setAlpha(0.0f);
            this.val$view.postDelayed(new Runnable() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    View view;
                    View view2;
                    SubscreenCustomizerController.AnonymousClass2 anonymousClass2 = SubscreenCustomizerController.AnonymousClass2.this;
                    view = ((ViewController) SubscreenCustomizerController.this).mView;
                    if (((SubscreenCustomizer) view).mIsDragging) {
                        return;
                    }
                    view2 = ((ViewController) SubscreenCustomizerController.this).mView;
                    ((SubscreenCustomizer) view2).animationDrop(SubscreenCustomizerController.this.mLongClickedViewInfo);
                }
            }, 100L);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }
    }
}
