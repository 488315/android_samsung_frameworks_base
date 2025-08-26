package com.android.systemui.qp.customize;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView;
import com.android.systemui.qp.SubscreenPagedTileLayout;
import com.android.systemui.qp.SubscreenPagedTileLayout$$ExternalSyntheticLambda0;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.SubscreenTileLayout;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.SecPageIndicator;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class SubscreenCustomizer extends QSPanel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass2 mDragListener;
    public SecPageIndicator mFooterPageIndicator;
    public final AnonymousClass1 mHandler;
    public boolean mIsDragging;
    public boolean mIsDroppedOnView;
    public SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord mLongClickedViewInfo;
    public LinearLayout mQuickSettingsContainer;
    public final SubscreenQsPanelController mSubscreenQsPanelController;

    public class MessageObjectAnim {
        public int animationType;
        public SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord longClickedTileInfo;
        public int touchedPos;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qp.customize.SubscreenCustomizer$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qp.customize.SubscreenCustomizer$2] */
    public SubscreenCustomizer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsDroppedOnView = false;
        this.mIsDragging = false;
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.systemui.qp.customize.SubscreenCustomizer.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                RecyclerView$$ExternalSyntheticOutline0.m(message.what, "SubscreenCustomizer", new StringBuilder("handleMessage() msg.what="));
                int i = SubscreenCustomizer.$r8$clinit;
                SubscreenPagedTileLayout subscreenPagedTileLayout = SubscreenCustomizer.this.mTileLayout;
                switch (message.what) {
                    case 100:
                    case 101:
                    case 102:
                        MessageObjectAnim messageObjectAnim = (MessageObjectAnim) message.obj;
                        int currentItem = subscreenPagedTileLayout.getCurrentItem();
                        int i2 = messageObjectAnim.touchedPos;
                        SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord = messageObjectAnim.longClickedTileInfo;
                        StringBuilder sb = new StringBuilder("handleAnimate addInfo:spec-");
                        sb.append(subscreenTileRecord.mTilespec);
                        sb.append(",animation type = ");
                        RecyclerView$$ExternalSyntheticOutline0.m(messageObjectAnim.animationType, "SubscreenPagedTileLayout", sb);
                        int i3 = messageObjectAnim.animationType;
                        if (i3 == 202) {
                            SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(currentItem);
                            int iIndexOf = subscreenTileLayout.indexOf(subscreenTileRecord);
                            if (iIndexOf < 0) {
                                return;
                            }
                            int iMin = Math.min(subscreenTileLayout.mColumns * subscreenTileLayout.mRows, subscreenTileLayout.mRecords.size());
                            int i4 = iMin - 1;
                            if (i2 > i4) {
                                i2 = i4;
                            }
                            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iIndexOf, i2, "moveTile from = ", " to  = ", "total = ");
                            sbM.append(iMin);
                            sbM.append("fromtileInfo = ");
                            sbM.append(subscreenTileRecord.mTilespec);
                            Log.d("SubscreenTileLayout", sbM.toString());
                            AnimatorSet animatorSet = new AnimatorSet();
                            if (iIndexOf < i2) {
                                int i5 = iIndexOf;
                                while (i5 < i2) {
                                    int i6 = i5 + 1;
                                    QSTileView qSTileView = ((QSPanelControllerBase$TileRecord) subscreenTileLayout.mRecords.get(i6)).tileView;
                                    animatorSet.playTogether(ObjectAnimator.ofFloat(qSTileView, "x", ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(i6)).getLeft(), ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(i5)).getLeft()));
                                    animatorSet.playTogether(ObjectAnimator.ofFloat(qSTileView, "y", ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(i6)).getTop(), ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(i5)).getTop()));
                                    i5 = i6;
                                }
                            } else {
                                for (int i7 = iIndexOf; i7 > i2; i7--) {
                                    QSTileView qSTileView2 = ((QSPanelControllerBase$TileRecord) subscreenTileLayout.mRecords.get(i7 - 1)).tileView;
                                    animatorSet.playTogether(ObjectAnimator.ofFloat(qSTileView2, "x", ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(r12)).getLeft(), ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(i7)).getLeft()));
                                    animatorSet.playTogether(ObjectAnimator.ofFloat(qSTileView2, "y", ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(r12)).getTop(), ((FrameLayout) subscreenTileLayout.mBoundaryBox.get(i7)).getTop()));
                                }
                            }
                            animatorSet.setDuration(200L);
                            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qp.SubscreenTileLayout.3
                                public final /* synthetic */ int val$emptyPos;

                                public AnonymousClass3(int i22) {
                                    i = i22;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    Log.d("SubscreenTileLayout", "moveTile onAnimationStart");
                                    SubscreenTileLayout subscreenTileLayout2 = SubscreenTileLayout.this;
                                    int i8 = SubscreenTileLayout.$r8$clinit;
                                    subscreenTileLayout2.getClass();
                                    ((FrameLayout) SubscreenTileLayout.this.mBoundaryBox.get(i)).getLeft();
                                    throw null;
                                }
                            });
                            animatorSet.start();
                            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) subscreenTileLayout.mRecords.get(iIndexOf);
                            subscreenTileLayout.mRecords.remove(iIndexOf);
                            subscreenTileLayout.mRecords.add(i22, qSPanelControllerBase$TileRecord);
                            return;
                        }
                        if (i3 == 201) {
                            SubscreenTileLayout subscreenTileLayout2 = (SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(currentItem);
                            int iIndexOf2 = subscreenTileLayout2.indexOf(subscreenTileRecord);
                            if (iIndexOf2 < 0) {
                                return;
                            }
                            QSTileView qSTileView3 = ((QSPanelControllerBase$TileRecord) subscreenTileLayout2.mRecords.get(iIndexOf2)).tileView;
                            Log.d("SubscreenTileLayout", "dropTile tileView =  " + qSTileView3 + ",position=" + iIndexOf2);
                            qSTileView3.setAlpha(1.0f);
                            throw null;
                        }
                        if (i3 != 203 && i3 != 204) {
                            if (i3 == 200) {
                                ((SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(currentItem)).selectTile(subscreenTileRecord, false);
                                return;
                            }
                            return;
                        }
                        int currentItem2 = subscreenPagedTileLayout.getCurrentItem();
                        int i8 = messageObjectAnim.animationType;
                        if (i8 == 204) {
                            if (currentItem2 == subscreenPagedTileLayout.mPages.size() - 1) {
                                return;
                            }
                        } else if (currentItem2 == 0) {
                            return;
                        }
                        int i9 = i8 == 204 ? 1 : -1;
                        int columnCount = i8 == 204 ? 0 : (subscreenPagedTileLayout.getColumnCount() * 2) - 1;
                        int columnCount2 = i8 == 204 ? (subscreenPagedTileLayout.getColumnCount() * 2) - 1 : 0;
                        int columnCount3 = (subscreenPagedTileLayout.getColumnCount() * 2) - 1;
                        StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(currentItem2, i9, "movePage, cur = ", ",pageOffset = ", ",removePos = ");
                        ViewPager$$ExternalSyntheticOutline0.m(sbM2, columnCount, ",curAddPos = ", columnCount2, ",longClickedAddPos = ");
                        RecyclerView$$ExternalSyntheticOutline0.m(columnCount3, "SubscreenPagedTileLayout", sbM2);
                        int i10 = currentItem2 + i9;
                        SubscreenTileLayout subscreenTileLayout3 = (SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i10);
                        subscreenTileLayout3.getClass();
                        Log.d("SubscreenTileLayout", "getInfo position = " + columnCount);
                        int size = subscreenTileLayout3.mRecords.size() - 1;
                        if (columnCount > size) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(size, "position is invalid position is  ", "SubscreenTileLayout");
                            columnCount = size;
                        }
                        SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord2 = (SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord) ((QSPanelControllerBase$TileRecord) subscreenTileLayout3.mRecords.get(columnCount));
                        if (subscreenTileRecord2 == null) {
                            return;
                        }
                        ((SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i10)).removeTile(subscreenTileRecord2);
                        SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord3 = messageObjectAnim.longClickedTileInfo;
                        ((SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i10)).addTile(subscreenTileRecord3, columnCount3);
                        ((SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i10)).selectTile(subscreenTileRecord3, true);
                        ((SubscreenTileLayout) subscreenPagedTileLayout.mPages.get(i10)).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qp.SubscreenPagedTileLayout.1
                            public final /* synthetic */ SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord val$addTileRecord;
                            public final /* synthetic */ int val$cur;
                            public final /* synthetic */ int val$curAddPos;
                            public final /* synthetic */ int val$pageOffset;
                            public final /* synthetic */ SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord val$removeTileRecord;

                            public AnonymousClass1(int currentItem22, int i92, SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord32, SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord22, int columnCount22) {
                                i = currentItem22;
                                i = i92;
                                subscreenTileRecord = subscreenTileRecord32;
                                subscreenTileRecord = subscreenTileRecord22;
                                i = columnCount22;
                            }

                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(View view, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
                                SubscreenPagedTileLayout.this.setCurrentItem(i + i, true);
                                ((SubscreenTileLayout) SubscreenPagedTileLayout.this.mPages.get(i)).removeTile(subscreenTileRecord);
                                ((SubscreenTileLayout) SubscreenPagedTileLayout.this.mPages.get(i)).addTile(subscreenTileRecord, i);
                                ((SubscreenTileLayout) SubscreenPagedTileLayout.this.mPages.get(i)).getClass();
                                ((SubscreenTileLayout) SubscreenPagedTileLayout.this.mPages.get(i + i)).removeOnLayoutChangeListener(this);
                            }
                        });
                        subscreenPagedTileLayout.postDelayed(new Runnable() { // from class: com.android.systemui.qp.SubscreenPagedTileLayout.2
                            public final /* synthetic */ int val$cur;
                            public final /* synthetic */ int val$pageOffset;

                            public AnonymousClass2(int currentItem22, int i92) {
                                i = currentItem22;
                                i = i92;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                StringBuilder sb2 = new StringBuilder("cur ");
                                sb2.append(i);
                                sb2.append("pageOffset");
                                EmergencyButtonController$$ExternalSyntheticOutline0.m(sb2, i, "requestLayout", "SubscreenPagedTileLayout");
                                if (i >= SubscreenPagedTileLayout.this.mPages.size() || SubscreenPagedTileLayout.this.mPages.get(i) == null) {
                                    return;
                                }
                                ((SubscreenTileLayout) SubscreenPagedTileLayout.this.mPages.get(i)).requestLayout();
                            }
                        }, 300L);
                        return;
                    default:
                        return;
                }
            }
        };
        this.mDragListener = new View.OnDragListener() { // from class: com.android.systemui.qp.customize.SubscreenCustomizer.2
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                QSHost qSHost;
                SubscreenCustomizer.this.mLongClickedViewInfo.getClass();
                SubscreenCustomizer.this.mLongClickedViewInfo.getClass();
                int action = dragEvent.getAction();
                int iIntValue = ((Integer) view.getTag()).intValue();
                View view2 = (View) view.getParent().getParent();
                Log.d("SubscreenCustomizer", MutableVectorKt$$ExternalSyntheticOutline0.m(action, iIntValue, "onDrag,event-", ",pos-", ",x-0.0,y=0.0"));
                if (action == 1) {
                    SubscreenCustomizer subscreenCustomizer = SubscreenCustomizer.this;
                    subscreenCustomizer.mIsDroppedOnView = false;
                    subscreenCustomizer.mIsDragging = true;
                    return true;
                }
                if (action == 3) {
                    SubscreenCustomizer subscreenCustomizer2 = SubscreenCustomizer.this;
                    subscreenCustomizer2.mIsDroppedOnView = true;
                    subscreenCustomizer2.animationDrop(subscreenCustomizer2.mLongClickedViewInfo);
                    return true;
                }
                if (action == 4) {
                    if (!SubscreenCustomizer.this.mIsDroppedOnView) {
                        Log.d("SubscreenCustomizer", "ACTION_DRAG_ENDED");
                        SubscreenCustomizer subscreenCustomizer3 = SubscreenCustomizer.this;
                        subscreenCustomizer3.animationDrop(subscreenCustomizer3.mLongClickedViewInfo);
                        SubscreenCustomizer.this.mIsDroppedOnView = true;
                    }
                    SubscreenCustomizer subscreenCustomizer4 = SubscreenCustomizer.this;
                    subscreenCustomizer4.mIsDragging = false;
                    SubscreenPagedTileLayout subscreenPagedTileLayout = subscreenCustomizer4.mTileLayout;
                    if (subscreenPagedTileLayout != null && (qSHost = subscreenCustomizer4.mSubscreenQsPanelController.mHost) != null) {
                        subscreenPagedTileLayout.getClass();
                        ArrayList arrayList = new ArrayList();
                        subscreenPagedTileLayout.mPages.forEach(new SubscreenPagedTileLayout$$ExternalSyntheticLambda0(arrayList, 0));
                        qSHost.changeTilesByUser(new ArrayList(), arrayList);
                    }
                } else if (action == 5 && view2.getId() == R.id.subscreen_customize_qs_paged) {
                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(iIntValue, "ACTION_DRAG_ENTERED: pos: ", ",animateCurrentPage", "SubscreenCustomizer");
                    SubscreenCustomizer subscreenCustomizer5 = SubscreenCustomizer.this;
                    SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord = subscreenCustomizer5.mLongClickedViewInfo;
                    MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
                    messageObjectAnim.touchedPos = iIntValue;
                    messageObjectAnim.longClickedTileInfo = subscreenTileRecord;
                    messageObjectAnim.animationType = 202;
                    if (subscreenCustomizer5.mHandler.hasMessages(102)) {
                        subscreenCustomizer5.mHandler.removeMessages(102);
                    }
                    subscreenCustomizer5.mHandler.sendMessage(subscreenCustomizer5.mHandler.obtainMessage(102, messageObjectAnim));
                    return true;
                }
                return true;
            }
        };
        Log.d("SubscreenCustomizer", "SubscreenCustomizer");
        this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class);
    }

    public final void animationDrop(SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord) {
        MessageObjectAnim messageObjectAnim = new MessageObjectAnim();
        messageObjectAnim.animationType = 201;
        messageObjectAnim.longClickedTileInfo = subscreenTileRecord;
        if (hasMessages(101)) {
            removeMessages(101);
        }
        sendMessage(obtainMessage(101, messageObjectAnim));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("SubscreenCustomizer", "onFinishInflate");
        this.mQuickSettingsContainer = (LinearLayout) findViewById(R.id.subscreen_customize_tile_layout);
        this.mFooterPageIndicator = (SecPageIndicator) findViewById(R.id.subscreen_customize_page_indicator);
    }

    public final void updatePageIndicator$1() throws Resources.NotFoundException {
        SecPageIndicator secPageIndicator;
        SubscreenPagedTileLayout subscreenPagedTileLayout = this.mTileLayout;
        if (subscreenPagedTileLayout == null || (secPageIndicator = this.mFooterPageIndicator) == null) {
            return;
        }
        subscreenPagedTileLayout.setPageIndicator(secPageIndicator);
    }
}
