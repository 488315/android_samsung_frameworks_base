package com.android.systemui.qp.customize;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
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
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/* loaded from: classes2.dex */
public class SubscreenCustomizerController extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public boolean mDragStart;
    public final QSHost mHost;
    public final AnonymousClass1 mLongClickListener;
    public QSTileView mLongClickedView;
    public SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord mLongClickedViewInfo;
    public final SubscreenCustomizerController$$ExternalSyntheticLambda0 mQSHostCallback;
    public final ArrayList mSubscreenRecords;
    public final UiEventLogger mUiEventLogger;

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.qp.customize.SubscreenCustomizerController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qp.customize.SubscreenCustomizerController$1] */
    public SubscreenCustomizerController(SubscreenCustomizer subscreenCustomizer, QSHost qSHost) {
        super(subscreenCustomizer);
        this.mSubscreenRecords = new ArrayList();
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
        this.mQSHostCallback = new QSHost.Callback() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.QSHost.Callback
            public final void onTilesChanged() {
                this.f$0.setTiles();
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
                SubscreenCustomizerController.this.mLongClickedViewInfo.getClass();
                SubscreenCustomizerController.this.mLongClickedViewInfo.getClass();
                SubscreenCustomizer subscreenCustomizer2 = (SubscreenCustomizer) ((ViewController) SubscreenCustomizerController.this).mView;
                SubscreenCustomizerController subscreenCustomizerController2 = SubscreenCustomizerController.this;
                subscreenCustomizer2.mLongClickedViewInfo = subscreenCustomizerController2.mLongClickedViewInfo;
                SubscreenCustomizer subscreenCustomizer3 = (SubscreenCustomizer) ((ViewController) subscreenCustomizerController2).mView;
                SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord = SubscreenCustomizerController.this.mLongClickedViewInfo;
                subscreenCustomizer3.getClass();
                SubscreenCustomizer.MessageObjectAnim messageObjectAnim = new SubscreenCustomizer.MessageObjectAnim();
                messageObjectAnim.animationType = 200;
                messageObjectAnim.longClickedTileInfo = subscreenTileRecord;
                if (subscreenCustomizer3.mHandler.hasMessages(100)) {
                    subscreenCustomizer3.mHandler.removeMessages(100);
                }
                subscreenCustomizer3.mHandler.sendMessage(subscreenCustomizer3.mHandler.obtainMessage(100, messageObjectAnim));
                SubscreenCustomizerController subscreenCustomizerController3 = SubscreenCustomizerController.this;
                final QSTileView qSTileView = subscreenCustomizerController3.mLongClickedView;
                final float f = -Math.abs(qSTileView.getWidth() * 0.125f);
                final float f2 = -Math.abs(qSTileView.getHeight() * 0.25f);
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.setDuration(80L);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController$$ExternalSyntheticLambda1
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
                valueAnimatorOfFloat.addListener(subscreenCustomizerController3.new AnonymousClass2(qSTileView));
                valueAnimatorOfFloat.start();
                return true;
            }
        };
        this.mHost = qSHost;
        this.mContext = getContext();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() throws Resources.NotFoundException {
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
                    ArrayList arrayList = subscreenTileLayout.mRecords;
                    int size2 = arrayList.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ViewParent viewParent = ((QSPanelControllerBase$TileRecord) obj).tileView;
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
        subscreenCustomizer.updatePageIndicator$1();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        Objects.toString(this.mView);
        QSHost qSHost = this.mHost;
        if (qSHost != null) {
            qSHost.addCallback(this.mQSHostCallback);
        }
        setTiles();
        SubscreenCustomizer subscreenCustomizer = (SubscreenCustomizer) this.mView;
        if (!subscreenCustomizer.mListening) {
            subscreenCustomizer.mListening = true;
            SubscreenPagedTileLayout subscreenPagedTileLayout = subscreenCustomizer.mTileLayout;
            if (subscreenPagedTileLayout != null) {
                subscreenPagedTileLayout.setListening(true, this.mUiEventLogger);
            }
            if (((SubscreenCustomizer) this.mView).mListening) {
                ArrayList arrayList = this.mSubscreenRecords;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) obj;
                    if (!qSPanelControllerBase$TileRecord.tile.isListening()) {
                        qSPanelControllerBase$TileRecord.tile.refreshState();
                    }
                }
            }
        }
        SubscreenCustomizer subscreenCustomizer2 = (SubscreenCustomizer) this.mView;
        SubscreenPagedTileLayout subscreenPagedTileLayout2 = subscreenCustomizer2.mTileLayout;
        if (subscreenPagedTileLayout2 != null) {
            if (subscreenPagedTileLayout2.getParent() != null) {
                ((ViewGroup) subscreenPagedTileLayout2.getParent()).removeView(subscreenPagedTileLayout2);
            }
            subscreenCustomizer2.mQuickSettingsContainer.addView(subscreenPagedTileLayout2);
            subscreenCustomizer2.mTileLayout.getClass();
            subscreenCustomizer2.updatePageIndicator$1();
            subscreenCustomizer2.mTileLayout.setCurrentItem(0, false);
        }
        SubscreenCustomizer subscreenCustomizer3 = (SubscreenCustomizer) this.mView;
        subscreenCustomizer3.updatePageIndicator$1();
        if (subscreenCustomizer3.mTileLayout != null) {
            int dimension = (int) subscreenCustomizer3.getResources().getDimension(R.dimen.qs_customizer_tile_page_layout_height);
            SubscreenPagedTileLayout subscreenPagedTileLayout3 = subscreenCustomizer3.mTileLayout;
            if (subscreenPagedTileLayout3 != null) {
                subscreenPagedTileLayout3.getClass();
                Log.d("SubscreenPagedTileLayout", "setTilePageHeight pageHeight: " + dimension);
                int i2 = subscreenPagedTileLayout3.mPageHeight;
                if (i2 != dimension) {
                    subscreenPagedTileLayout3.mLastMaxHeight = i2;
                    subscreenPagedTileLayout3.mPageHeight = dimension;
                }
            }
        }
        SubscreenPagedTileLayout subscreenPagedTileLayout4 = subscreenCustomizer3.mTileLayout;
        if (subscreenPagedTileLayout4 != null) {
            subscreenPagedTileLayout4.updateResources();
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
        QSHost qSHost = this.mHost;
        if (qSHost != null) {
            qSHost.removeCallback(this.mQSHostCallback);
        }
        removeAllTileViews$1();
    }

    public final void removeAllTileViews$1() {
        ArrayList arrayList = this.mSubscreenRecords;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) obj;
            SubscreenPagedTileLayout subscreenPagedTileLayout = ((SubscreenCustomizer) this.mView).mTileLayout;
            if (subscreenPagedTileLayout.mTiles.remove(qSPanelControllerBase$TileRecord)) {
                subscreenPagedTileLayout.mDistributeTiles = true;
                subscreenPagedTileLayout.requestLayout();
            }
            qSPanelControllerBase$TileRecord.tile.removeCallback(qSPanelControllerBase$TileRecord.callback);
        }
        this.mSubscreenRecords.clear();
    }

    public final void setTiles() {
        QSHost qSHost = this.mHost;
        if (qSHost != null) {
            Collection tiles = qSHost.getTiles();
            removeAllTileViews$1();
            ArrayList arrayList = (ArrayList) tiles;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                QSTile qSTile = (QSTile) obj;
                Log.d("SubscreenCustomizerController", "adding Tilespec : " + qSTile.getTileSpec());
                SubRoomQsTileBaseView subRoomQsTileBaseView = new SubRoomQsTileBaseView(this.mContext, new QSIconViewImpl(this.mContext), false);
                SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord = new SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord(qSTile, subRoomQsTileBaseView, this.mLongClickListener);
                subRoomQsTileBaseView.setTag(subscreenTileRecord);
                ((SubscreenCustomizer) this.mView).addTile(subscreenTileRecord);
                this.mSubscreenRecords.add(subscreenTileRecord);
                subscreenTileRecord.tileView.getIcon().setOnClickListener(null);
                Log.d("SubscreenCustomizerController", "addTile tile.getTileSpec():  record: " + subscreenTileRecord + " mSubscreenRecords.size(): " + this.mSubscreenRecords.size());
            }
        }
    }

    /* renamed from: com.android.systemui.qp.customize.SubscreenCustomizerController$2, reason: invalid class name */
    public class AnonymousClass2 implements Animator.AnimatorListener {
        public final /* synthetic */ View val$view;

        public AnonymousClass2(View view) {
            this.val$view = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SubscreenCustomizer subscreenCustomizer = (SubscreenCustomizer) ((ViewController) SubscreenCustomizerController.this).mView;
            QSTileView qSTileView = SubscreenCustomizerController.this.mLongClickedView;
            subscreenCustomizer.getClass();
            SubscreenCustomizer.AnonymousClass3 anonymousClass3 = new View.DragShadowBuilder(subscreenCustomizer, qSTileView, qSTileView, 0.0f, 0.0f) { // from class: com.android.systemui.qp.customize.SubscreenCustomizer.3
                public final /* synthetic */ float val$pointX;
                public final /* synthetic */ float val$pointY;
                public final /* synthetic */ View val$v;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(SubscreenCustomizer subscreenCustomizer2, View qSTileView2, View qSTileView22, float f, float f2) {
                    super(qSTileView22);
                    this.val$v = qSTileView22;
                    this.val$pointX = f;
                    this.val$pointY = f2;
                }

                @Override // android.view.View.DragShadowBuilder
                public final void onProvideShadowMetrics(Point point, Point point2) {
                    int width = this.val$v.getWidth();
                    int height = this.val$v.getHeight();
                    point.set(width, height);
                    point2.set(((int) this.val$pointX) + ((int) (width * 0.125f)), ((int) this.val$pointY) + ((int) (height * 0.25f)));
                }
            };
            SubscreenCustomizerController.this.mDragStart = this.val$view.startDrag(null, anonymousClass3, null, 1048576);
            SubscreenCustomizerController subscreenCustomizerController = SubscreenCustomizerController.this;
            if (!subscreenCustomizerController.mDragStart) {
                ((SubscreenCustomizer) ((ViewController) subscreenCustomizerController).mView).requestLayout();
            }
            this.val$view.setAlpha(0.0f);
            this.val$view.postDelayed(new Runnable() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenCustomizerController.AnonymousClass2 anonymousClass2 = this.f$0;
                    if (((SubscreenCustomizer) ((ViewController) SubscreenCustomizerController.this).mView).mIsDragging) {
                        return;
                    }
                    ((SubscreenCustomizer) ((ViewController) SubscreenCustomizerController.this).mView).animationDrop(SubscreenCustomizerController.this.mLongClickedViewInfo);
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
